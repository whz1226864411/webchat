package com.creat.webchat.po;

import java.util.Date;

/**
 * Created by whz on 2017/9/21.
 */
public class VerifyCode {
    //创建时间
    private Date createTime;
    //验证码值
    private String codeValue;
    //有效时间，单位:分钟
    private int validTime;
    //手机号
    private String tel;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
