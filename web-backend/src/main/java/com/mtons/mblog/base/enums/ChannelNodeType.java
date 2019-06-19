package main.java.com.mtons.mblog.base.enums;

import com.yueny.superclub.api.annnotation.EnumValue;
import com.yueny.superclub.api.enums.core.IEnumType;
import lombok.Getter;

/**
 * 渠道节点类型 idLeaf
 */
public enum ChannelNodeType implements IEnumType {
    /**
     * 叶子节点，此节点包含对外展示的有效路径
     */
    LEAF_NODE(1, "叶子节点"),
    /**
     * 子节点
     */
    V_NODE(0, "子节点");

        /**
     * 是否为叶子节点。1为是叶子节点，0为不是叶子节点(子节点，存在后续分支)
     */
    private int isLeaf;
    /**
     * 描述
     */
    @Getter
    private String desc;

    ChannelNodeType(int isLeaf, String desc){
        this.isLeaf = isLeaf;
        this.desc = desc;
    }

    @EnumValue
    public int getIsLeaf() {
        return isLeaf;
    }

}
