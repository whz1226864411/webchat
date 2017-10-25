package com.creat;

import com.creat.webchat.interceptor.AllowOriginInterceptor;
import com.creat.webchat.interceptor.UserInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@MapperScan("com.creat.webchat.mapper")
public class WebchatApplication extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AllowOriginInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new UserInterceptor()).addPathPatterns("/user/quit");
	}

	public static void main(String[] args) {
		SpringApplication.run(WebchatApplication.class, args);
	}
}
