package com.ameliawiki.wiki.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("user login name already exited!"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
