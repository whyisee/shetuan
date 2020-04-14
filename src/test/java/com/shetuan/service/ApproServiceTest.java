package com.shetuan.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/15 3:10
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
class ApproServiceTest {

    @Autowired
    private ApproService approService;
    @Test
    public void findAll() throws Exception {
        Map param=new HashMap();
        param.put("loginName","admin");
        param.put("roleId","5001");

       System.out.println("Test--------3:13--->:"+approService.findAll(param,null));
    }
}