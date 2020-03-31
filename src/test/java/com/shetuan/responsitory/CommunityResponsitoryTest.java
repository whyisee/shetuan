package com.shetuan.responsitory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 22:26
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
class CommunityResponsitoryTest {
    @Autowired
    private CommunityResponsitory communityResponsitory;
    @Autowired
    private CommunityClassResponsitory communityClassResponsitory;

    @Test
    public void test(){
        //System.out.println("Test--------19:22--->:"+communityResponsitory.findA("classId = ?","11").fetch());
        System.out.println("Test--------22:27--->:"+communityResponsitory.getIndexCommunity());
        System.out.println("Test--------23:46--->:"+communityClassResponsitory.getIndexCommunityClass());

    }

}