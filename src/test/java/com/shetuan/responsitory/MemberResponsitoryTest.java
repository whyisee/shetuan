package com.shetuan.responsitory;

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
    @Test
    void findAll(){
        System.out.println("Test--------19:09--->:"+memberResponsitory.findAll());
    }
}