package com.example.biztool.importdata;

/**
 * @author linzhou
 * @ClassName ImportStateEnum.java
 * @createTime 2021年11月17日 11:56:00
 * @Description
 */
public enum ImportStateEnum {

    LOADING((byte) 1, "处理中"),
    FINISH((byte) 0, "处理完成");
    /**
     *
     */
    private Byte type;
    /**
     * 描述
     */
    private String desc;

    ImportStateEnum(Byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
