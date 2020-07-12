/**
 *
 */
package com.mtons.mblog.service.comp.configure.impl;

import com.mtons.mblog.service.comp.configure.IUploadXmlConfig;
import com.mtons.mblog.service.configuration.data.xml.UploadConfigModelData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.xml.bind.*;
import java.io.InputStreamReader;

/**
 * xml 配置文件
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年9月22日 下午5:22:52
 *
 */
@Configuration
@EnableAsync
@Slf4j
public class UploadXmlConfiguration implements IUploadXmlConfig {
	/**
	 * 先从配置中心获取。取不到使用默认值
	 *
	 * @return
	 */
	@Override
	public UploadConfigModelData getConfigModelData() {
		return xmlConfigModelData;
	}

	private UploadConfigModelData xmlConfigModelData = null;

	/**
	 * 构造方法执行， 先于 InitializingBean.afterPropertiesSet
	 */
	@PostConstruct
	public void loadConfig() {
		xmlConfigModelData = uploadConfig("/config/xml/upload.xml");//.getValue();
    }

	private UploadConfigModelData uploadConfig(String xmlSettingPaths) {
		UploadConfigModelData uploadConfig = null;

		// load configuration
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(UploadXmlConfiguration.class.getResourceAsStream(xmlSettingPaths), "UTF-8");

			uploadConfig = JAXB.unmarshal(reader, UploadConfigModelData.class);
			log.debug("uploadConfig 配置: {}.", uploadConfig);
		} catch (final Exception e) {
			log.error("加载uploadConfig异常，默认配置置为空！", e);
		} finally {
			IOUtils.closeQuietly(reader);
		}

//		try {
//			final JAXBContext cxt = JAXBContext.newInstance(UploadConfigConfiguration.class);
//
//			final Unmarshaller unmarshaller = cxt.createUnmarshaller();
//			final XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
//			final InputStream is = UploadConfigConfiguration.class.getResourceAsStream(xmlSettingPaths);
//
//			XMLStreamReader reader;
//			try {
//				reader = xmlFactory.createXMLStreamReader(is);
//				uploadConfig = unmarshaller.unmarshal(reader, UploadConfigModelData.class);
//			} catch (final XMLStreamException e) {
//				e.printStackTrace();
//			}
//
//		} catch (final JAXBException e) {
//			e.printStackTrace();
//		}

		return uploadConfig;
	}

}
