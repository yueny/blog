/**
 *
 */
package com.mtons.mblog.service.config.options.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(value = { "serialVersionUID", "uploadMaps" })
@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class UploadConfigModelData {
	private static Map<String, UploadMethodTypeData> uploadMaps;

	/**
	 * 上传根路径，可以指定绝对路径，比如 /var/www/attached/<br>
	 * 如果为空，默认为 System.getProperty("user.dir")
	 * 此处为文件保存根目录路径<br>
	 * 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\
	 * 文件夹中
	 */
	@Setter
	@XmlElement(name = "location")
	private String location;
	/**b
	 * 上传目录,'upload's
	 */
	@Getter
	@Setter
	@XmlElement(name = "upload-base", required = true)
	private String uploadBase;
	/**
	 *
	 */
	@Setter
	@XmlElementWrapper(name = "uploads")
	@XmlElement(name = "upload", required = true)
	private List<UploadMethodTypeData> uploadBeans;

	/**
	 * 文件服务器uri,根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
	 */
	@Getter
	@Setter
	@XmlElement(name = "location-uri", required = true)
	private String locationUri;

	public String getLocation() {
		if(StringUtils.isNotEmpty(location)){
			return location;
		}

		String la = System.getProperty("user.dir");
		return la;
	}

	/**
	 * @param type
	 *            上传类型,如image
	 */
	public UploadMethodTypeData getUploadByType(final String type) {
		return assemblyUploadMaps().get(type);
	}

	private Map<String, UploadMethodTypeData> assemblyUploadMaps() {
		if (uploadMaps != null) {
			return uploadMaps;
		}

		final Map<String, UploadMethodTypeData> maps = Maps.newHashMap();
		for (final UploadMethodTypeData uploadMethodTypeData : uploadBeans) {
			if (uploadMethodTypeData.getExtTypes() == null) {
				uploadMethodTypeData.setExtTypes(Collections.emptyList());
			}
			maps.put(uploadMethodTypeData.getType(), uploadMethodTypeData);
		}
		return maps;
	}

}
