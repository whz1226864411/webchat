package com.creat.webchat.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by whz on 2017/9/24.
 */
public class UserInterceptor extends LoginInterceptor{

    @Override
    protected boolean alreadyLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userInfo") != null){
            return true;
        }
        return false;
    }

}
