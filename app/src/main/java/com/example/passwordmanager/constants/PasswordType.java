package com.example.passwordmanager.constants;

public enum PasswordType {

    NUMBER(0, "数字"),
    LOWER(1, "小写字母"),
    UPPER(2, "大写字母"),
    SYMBOL(3, "符号");

    PasswordType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    private int type;

    private String desc;
}
