package com.shetuan.util;

import com.shetuan.entity.LoginEntity;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/14 8:09
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
class BeanUtilsTest {

    @Test
    void beanMarge() throws Throwable {
        LoginEntity loginEntity1=new LoginEntity();
        loginEntity1.setLoginId("123");
        //System.out.println("Test--------8:34--->:"+loginEntity1);
        LoginEntity loginEntity2=new LoginEntity();
        loginEntity2.setLoginId("321");
        loginEntity2.setLoginName("321");

        Map map=new HashMap();
        map.put("loginId","333");
        map.put("loginName","444");


        LoginEntity loginEntity3=BeanUtils.beanMarge(loginEntity1,loginEntity2,"RIGHT");
        //BeanUtils.mapToBean(map,loginEntity1);
        System.out.println("Test--------8:34--->:"+loginEntity3);

    }
}