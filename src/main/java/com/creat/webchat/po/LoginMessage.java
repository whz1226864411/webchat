package com.creat.webchat.po;

/**
 * Created by whz on 2017/10/19.
 */
public class LoginMessage extends Message{

    public static final String NAME_OR_PWD_ERROR = "400";
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
