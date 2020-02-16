package com.shetuan.mapper;

import com.shetuan.entity.MemberEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void testMybatis() throws Exception{
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId("11123");
        memberEntity.setLoginName("test");
        memberEntity.setUserName("zzzz");
        memberEntity.setIsAddInfo("0");

        //memberEntity.

        memberMapper.insert(memberEntity);
       // Assert.assertEquals(2, memberMapper.getAll().size());
        System.out.println("Test--------19:58--->:"+memberMapper.getAll());

    }
}
