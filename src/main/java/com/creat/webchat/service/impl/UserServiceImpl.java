package com.creat.webchat.service.impl;

import com.creat.webchat.dao.UserDAO;
import com.creat.webchat.po.UserAccount;
import com.creat.webchat.po.UserInfo;
import com.creat.webchat.service.UserService;
import com.creat.webchat.utils.md5.Encryption;
import com.creat.webchat.utils.sms.MessageSendUtil;
import com.creat.webchat.utils.sms.SMSException;
import com.creat.webchat.utils.sms.SMSParam;
import com.creat.webchat.utils.uutil.UUIDUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whz on 2017/10/19.
 */
@Service
public class UserServiceImpl implements UserService {
    //注入短信参数
    @Autowired
    private SMSParam smsParam;
    @Autowired
    private UserDAO userDAO;

    //发送短信
    @Override
    public void sendSMS(String userTel,String codeValue) throws SMSException {
        Map<String,String> param = new HashMap<>();
        param.put(smsParam.getParamName(),codeValue);
        MessageSendUtil messageSend = new MessageSendUtil(smsParam);
        messageSend.send(JSONObject.fromObject(param).toString(),userTel);
    }

    //手机号是否被注册
    @Override
    @Transactional
    public boolean telRegistered(String userTel) {
        return userDAO.selectUserInfoByTel(userTel) == null ? false : true;
    }

    //注册
    @Override
    @Transactional
    public void register(UserAccount userAccount, UserInfo userInfo) throws UserException, NoSuchAlgorithmException {
        //用户名不能被注册
        if(userDAO.selectUserAccountByUserName(userAccount.getUserName()) == null){
            finishUserAccount(userAccount);
            userDAO.saveUserAccount(userAccount);
            finishUserInfo(userInfo,userAccount.getId());
            userDAO.saveUserInfo(userInfo);
        }else {
            throw new UserException(UserException.USER_NAME_HAS_BEEN_REGISTER);
        }
    }

    //登录
    @Override
    @Transactional
    public UserInfo login(UserAccount userAccount) throws NoSuchAlgorithmException {
        UserAccount accountHave = userDAO.selectUserAccountByUserName(userAccount.getUserName());
        if(accountHave != null && Encryption.equalMD5(userAccount.getUserPwd(),accountHave.getUserPwd())){
            return userDAO.selectUserInfoByUserAccountId(accountHave.getId());
        }else {
            return null;
        }
    }

    private void finishUserAccount(UserAccount userAccount) throws NoSuchAlgorithmException {
        userAccount.setUserPwd(Encryption.stringMD5(userAccount.getUserPwd()));
        userAccount.setGmtCreate(new Date());
        userAccount.setGmtModified(new Date());
    }

    private void finishUserInfo(UserInfo userInfo, Long userAccountId){
        userInfo.setNickname(UUIDUtil.getUUID().substring(0,10));
        userInfo.setSex(true);
        userInfo.setUserAccountId(userAccountId);
        userInfo.setGmtCreate(new Date());
        userInfo.setGmtModified(new Date());
    }
}
