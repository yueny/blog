/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.mtons.mblog.base.tree;

import com.yueny.superclub.api.pojo.IBo;
import com.yueny.superclub.api.pojo.instance.AbstractBo;
import lombok.*;

import java.util.List;

/**
 * 满足于树结构的对象
 */
@Builder
@Getter
public class QTree extends AbstractBo implements IBo {
	/**
     * id
	 */
	private int id;

	/**
	 * tree组件一般用于菜单，url为菜单对应的地址
	 */
	private String url;

	/**
	 * 显示文字
	 */
	private String text;

	/**
	 * 是否选中T
	 */
	private boolean checked = false;
	/**
	 * 子tree
	 */
	@Singular
	@Setter
	private List<QTree> children;

}
