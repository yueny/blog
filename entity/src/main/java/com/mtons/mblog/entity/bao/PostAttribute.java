package com.mtons.mblog.entity.bao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yueny.kapo.api.annnotation.EntryPk;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.SortableField;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by langhsu on 2015/10/25.
 */
@Entity
@TableName("mto_post_attribute")
@Getter
@Setter
public class PostAttribute
        extends com.yueny.kapo.api.pojo.instance.Entity
        implements Serializable {
	private static final long serialVersionUID = 7829351358884064647L;

    /** 自然主键， 非自增， 来自 博文的 postId， 故不继承 AbstractIDPlusEntry */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.INPUT)
    @SortableField
    @NumericField
    @EntryPk
    private Long id;

	@Column(length = 16, columnDefinition = "varchar(16) default 'tinymce'")
	private String editor;

    /**
     * 内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String content; // 内容

}
