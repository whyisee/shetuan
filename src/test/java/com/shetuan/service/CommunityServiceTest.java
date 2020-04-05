package com.shetuan.service;

import com.shetuan.entity.PrimaryKeyLoginCommID;
import com.shetuan.responsitory.CommMemberResponsitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/3 20:38
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
class CommunityServiceTest {
    @Autowired
    private CommunityService communityService;

    @Autowired
    private CommMemberResponsitory commMemberResponsitory;

    @Test
    public void test() throws Exception {
        Map param=new HashMap();
        param.put("comm_id","100001");

        PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setLoginId("100001");
        primaryKeyLoginCommID.setCommId("100001");
        System.out.println("Test--------15:17--->:"+commMemberResponsitory.findById(primaryKeyLoginCommID));

        List rs=communityService.findMemberByComm(param,null);
        System.out.println("Test--------20:41--->:"+rs);
    }


}