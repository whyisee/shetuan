package com.shetuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/13 23:50
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@MapperScan("com.shetuan.mapper")

@SpringBootApplication
public class WebApplication {
    public static void main(String args[]){
    SpringApplication.run(WebApplication.class);
    }
}
