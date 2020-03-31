package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/27 13:18
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
@Entity(name="tc_acct_login")
public class LoginEntity {
    //登录ID
    @Id
    @Column(name="login_id")
    private String loginId;

    //登录名
    @Column(name="login_name")
    private String loginName;

    //密码
    @Column(name="login_pass")
    private String loginPass;

    //是否有效
    @Column(name="status")
    private String status;
}
