package com.shetuan.responsitory;

import com.shetuan.entity.ActivityEntity;
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
 * Created in:  2020/3/27 22:03
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
class LoginResponsitoryTest {
    @Autowired
    private LoginResponsitory loginResponsitory;
    @Autowired
    private ActivityResponsitory activityResponsitory ;
    @Test
    public void getByLoginName(){
        //loginResponsitory.findByLoginName("");
        Map params=new HashMap();
        params.put("activity_id","100001");

        loginResponsitory.updateAcctRole("333","200");
        //ActivityEntity activityEntity=activityResponsitory.getOne(params.get("activity_id").toString());
        System.out.println("Test--------22:00--->:"+loginResponsitory.getRoleIdbyLoginName("333"));
        //loginResponsitory.saveAcctRole("11","11","1");
        //System.out.println("Test--------18:15--->:"+loginResponsitory.getRoleIdbyLoginName("11"));
        //System.out.println("Test--------22:05--->:"+loginResponsitory.getByLoginName("333"));

    }

}