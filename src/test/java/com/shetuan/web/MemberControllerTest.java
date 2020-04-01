package com.shetuan.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/1 11:59
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberController memberController;
    @Test
    void test1() {
        memberController.test();
    }
}