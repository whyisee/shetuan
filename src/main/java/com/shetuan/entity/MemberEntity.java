package com.shetuan.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity(name="tc_acct_member")
public class MemberEntity {
    //登录ID
    @Id
    @Column(name="login_id")
    private String loginId;

    //登录名
    @Column(name="login_name")
    private String loginName;

    //姓名
    @Column(name="user_name")
    private String userName;

    //学号
    @Column(name="student_id")
    private String studentId;

    //性别
    @Column(name="sex")
    private String sex;

    //入学年份
    @Column(name="in_date")
    private String inDate;

    //学院
    @Column(name="college")
    private String college;

    //专业
    @Column(name="specially")
    private String specially;

    //电话
    @Column(name="phone")
    private String phone;

    //地址
    @Column(name="address")
    private String address;

    //email
    @Column(name="email")
    private String email;

    //创建时间
    @Column(name="create_date")
    private String createDate;

    //是否信息完整
    @Column(name="is_add_info")
    private String isAddInfo;


    @Column(name="head_pic")
    private String headPic;

    @Column(name="status")
    private String status;


}
