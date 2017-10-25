package com.creat.webchat.controller;

import com.creat.webchat.po.*;
import com.creat.webchat.service.UserService;
import com.creat.webchat.service.impl.UserException;
import com.creat.webchat.utils.random.RandomNum;
import com.creat.webchat.utils.sms.SMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by whz on 2017/10/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //日志输出
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //退出
    @RequestMapping(value = "/quit",method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Message quit(HttpSession session){
        Message message = new Message();
        session.invalidate();
        message.setCode(Message.SUCCESS);
        message.setMsg("退出成功!");
        return message;
    }

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Message login(UserAccount userAccount,HttpSession session){
        LoginMessage message = new LoginMessage();
        try {
            UserInfo userInfo = userService.login(userAccount);
            if(userInfo != null){
                message.setCode(Message.SUCCESS);
                message.setMsg("登录成功!");
                message.setUserInfo(userInfo);
                session.setAttribute("userInfo",userInfo);
            }else {
                message.setCode(LoginMessage.NAME_OR_PWD_ERROR);
                message.setMsg("账号或密码错误!");
            }
        } catch (NoSuchAlgorithmException e) {
            message.setCode(Message.UNKNOWN_ERROR);
            message.setMsg("未知错误!");
        }
        return message;
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Message register(UserAccount userAccount, UserInfo userInfo, String verifyCode, HttpSession session){
        Message message = null;
        final String verifyCodeIn = verifyCode;
        VerifyCode verifyCodeSession = (VerifyCode) session.getAttribute("verifyCode");
        message = verifyCode(verifyCodeSession,verifyCodeIn, userInfo.getTel());
        try {
            if(message.getCode().equals(Message.SUCCESS)){
                userService.register(userAccount, userInfo);
                message.setMsg("注册成功!");
                session.removeAttribute("verifyCode");
            }else {
                message.setMsg("验证码错误！");
            }
        } catch (UserException e) {
            if(e.getMessage().equals(UserException.USER_NAME_HAS_BEEN_REGISTER)){
                message.setCode(Message.USER_NAME_HAS_BEEN_REGISTER);
                message.setMsg("用户名已经被注册!");
            }
        } catch (NoSuchAlgorithmException e) {
            message.setCode(Message.UNKNOWN_ERROR);
            message.setMsg("未知错误!");
        }
        return message;
    }

    //发送短信验证码
    @RequestMapping(value = "/sendSMS",method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Message sendSMS(String tel, HttpSession session){
        Message message = new Message();
        final String userTel = tel;
        if(userTel == null || userTel.trim().length() != 11){
            message.setCode(Message.PARAMS_ERROR);
            message.setMsg("手机号不能为空!必须为11位");
        }
        try {
            if(userService.telRegistered(userTel)){
                message.setCode(Message.TEL_HAS_BEEN_REGISTER);
                message.setMsg("手机号已被注册!");
                return message;
            }
            VerifyCode verifyCodeInSession = (VerifyCode) session.getAttribute("verifyCode");
            if(verifyCodeInSession != null
                    && (verifyCodeInSession.getCreateTime().getTime()+60*1000) > new Date().getTime()){
                message.setCode(Message.CAN_NOT_SEND_ERROR);
                message.setMsg("60内无法发送！");
                return message;
            }
            String codeValue = new RandomNum().getNum(6);
            userService.sendSMS(userTel,codeValue);
            VerifyCode verifyCode = wrapVerifyCode(codeValue,userTel);
            session.setAttribute("verifyCode",verifyCode);
            message.setCode(Message.SUCCESS);
        } catch (SMSException e) {
            message.setCode(Message.UNKNOWN_ERROR);
            message.setMsg(e.getMessage());
            logger.error("手机号:"+userTel+"->"+e.getMessage());
        }
        return message;
    }

    //封装验证码
    private VerifyCode wrapVerifyCode(String codeValue, String userTel){
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCodeValue(codeValue);
        verifyCode.setCreateTime(new Date());
        verifyCode.setValidTime(5);
        verifyCode.setTel(userTel);
        return verifyCode;
    }

    //校验验证码
    private Message verifyCode(VerifyCode verifyCodeSession,String verifyCodeIn, String tel){
        Message message = new Message();
        if(verifyCodeSession != null
                && (verifyCodeSession.getCreateTime().getTime()+
                verifyCodeSession.getValidTime()*60*1000)> new Date().getTime()
                && verifyCodeSession.getTel().equals(tel)
                && verifyCodeSession.getCodeValue().equals(verifyCodeIn)){
            message.setCode(Message.SUCCESS);
        }else {
            message.setCode(Message.VERIFY_CODE_ERROR);
            message.setMsg("验证码错误！");
        }
        return message;
    }
}
