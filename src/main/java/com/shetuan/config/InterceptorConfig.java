package com.shetuan.config;

import com.shetuan.interceptor.LoginInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/13 19:10
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Configuration
@ConfigurationProperties(prefix = "interceptor.path")  //读取配置文件
public class InterceptorConfig implements WebMvcConfigurer {

    private List<String> normal = new ArrayList();

    private List<String> special = new ArrayList();

    private List<String> exclude = new ArrayList();

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }

    public void setNormal(List<String> normal) {
        this.normal = normal;
    }

    public void setSpecial(List<String> special) {
        this.special = special;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/css/**","/js/**","/images/**","/upload/**")//排除样式、脚本、图片等资源文件
                .excludePathPatterns("/*.jsp","/community/index","/activity/findAll")//首页
                .excludePathPatterns("/member/login","/member/regist","/member/checkUser")//排除登录,注册页面
                .excludePathPatterns("/servlet/validateCodeServlet");//排除验证码

    }
}
