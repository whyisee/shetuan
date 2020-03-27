package com.shetuan.responsitory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @Test
    public void getByLoginName(){
        loginResponsitory.findByLoginName("");
        System.out.println("Test--------22:05--->:"+loginResponsitory.getByLoginName("333"));

    }

}