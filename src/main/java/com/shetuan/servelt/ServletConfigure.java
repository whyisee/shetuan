package com.shetuan.servelt;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 10:30
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Configuration
public class ServletConfigure {
    @Bean
    public ServletRegistrationBean validateCodeServlet() {
        return new ServletRegistrationBean(new ValidateCodeServlet(), "/servlet/validateCodeServlet");
    }

}
