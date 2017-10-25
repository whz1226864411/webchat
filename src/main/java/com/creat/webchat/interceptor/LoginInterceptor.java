package com.creat.webchat.interceptor;

import com.creat.webchat.po.Message;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by whz on 2017/9/24.
 */
public abstract class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(alreadyLogin(request)){
            return true;
        }else {
            sendLoginMessage(response);
            return false;
        }
    }
    private void sendLoginMessage(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        Message message = new Message();
        message.setCode(Message.NO_LOGIN);
        message.setMsg("您还未登录！");
        printWriter.write(JSONObject.fromObject(message).toString());
        printWriter.flush();
        printWriter.close();
    }
    protected abstract boolean alreadyLogin(HttpServletRequest httpServletRequest);
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
