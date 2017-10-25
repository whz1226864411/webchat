package com.creat.webchat.service;

import com.creat.webchat.po.UserAccount;
import com.creat.webchat.po.UserInfo;
import com.creat.webchat.service.impl.UserException;
import com.creat.webchat.utils.sms.SMSException;

import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/10/19.
 */
public interface UserService {
    void sendSMS(String userTel,String codeValue) throws SMSException;
    boolean telRegistered(String userTel);
    void register(UserAccount userAccount, UserInfo userInfo) throws UserException, NoSuchAlgorithmException;
    UserInfo login(UserAccount userAccount) throws NoSuchAlgorithmException;
}
