package com.shetuan.responsitory;

import com.shetuan.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/16 19:07
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
public class MemberResponsitoryTest {

    @Autowired
    private MemberResponsitory memberResponsitory;
    @Autowired
    private LoginResponsitory loginResponsitory;
    @Test
    void findAll(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId("123");
        memberEntity.setLoginName("123");
        memberEntity.setIsAddInfo("0");
        memberResponsitory.saveAndFlush(memberEntity);
        //memberResponsitory.
        System.out.println("Test--------19:09--->:"+memberResponsitory.findAll());

    }
}