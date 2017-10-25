package com.creat.webchat.utils.sms;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by whz on 2017/9/21.
 */
public class MessageSendUtil {
    /**
     * 包装后的短信参数信息
     */
    private SMSParam smsParam;

    private MessageSendUtil(){};

    public MessageSendUtil(SMSParam smsParam){
        this.smsParam = smsParam;
    }


    public void send(String paramString,String recNum) throws SMSException {
        CloseableHttpResponse response = null;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        try {
            //构造url
            String url = buildUrl(paramString,recNum);
            HttpGet httpGet = new HttpGet(url);
            //设置请求头
            httpGet.setHeader("Authorization",smsParam.getAuthorization());
            //发送请求
            response =closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"utf-8");
            JSONObject rel = null;
            if(result != null && (rel = JSONObject.fromObject(result)).
                            getBoolean("success")){
            }else {
               throw new SMSException("发送失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SMSException("发送失败!");
        } finally {
            try {
                if(response != null){
                    response.close();
                }
                if(closeableHttpClient != null){
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildUrl(String paramString,String recNum) throws UnsupportedEncodingException {
        StringBuilder params = new StringBuilder();
        params.append(smsParam.getUrl());
        params.append("?");
        params.append("ParamString="+ URLEncoder.encode(paramString,"utf-8"));
        params.append("&RecNum="+recNum);
        params.append("&TemplateCode="+smsParam.getTemplateCode());
        params.append("&SignName="+smsParam.getSignName());
        return params.toString();
    }

}
