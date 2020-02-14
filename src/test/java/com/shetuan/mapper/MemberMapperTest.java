package com.shetuan.mapper;

import com.shetuan.entity.MemberEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 13:48
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void testMybatis() throws Exception{
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId("1112");
        memberEntity.setLoginName("test");
        memberEntity.setUserName("zzzz");
        //memberEntity.

        memberMapper.insert(memberEntity);
        Assert.assertEquals(2, memberMapper.getAll().size());

    }
}