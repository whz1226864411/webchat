package com.creat.webchat.po;

/**
 * Created by whz on 2017/9/21.
 */
public class Message {
    public static final String SUCCESS = "200";
    public static final String UNKNOWN_ERROR = "201";
    public static final String PARAMS_ERROR = "202";
    public static final String VERIFY_CODE_ERROR = "203";
    public static final String CAN_NOT_SEND_ERROR = "204";
    public static final String TEL_HAS_BEEN_REGISTER = "205";
    public static final String USER_NAME_HAS_BEEN_REGISTER = "206";
    public static final String NO_LOGIN = "207";
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
