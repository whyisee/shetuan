package com.shetuan.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 13:35
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data

public class MemberEntity {
    //登录ID
    private String loginId;

    //登录名
    private String loginName;

    //姓名
    private String userName;

    //学号
    private String studentId;

    //性别
    private String sex;

    //入学年份
    private String inDate;

    //学院
    private String college;

    //专业
    private String specially;

    //电话
    private String phone;

    //地址
    private String address;

    //email
    private String email;

    //创建时间
    private String createDate;

    //是否信息完整
    private String isAddInfo;
}
