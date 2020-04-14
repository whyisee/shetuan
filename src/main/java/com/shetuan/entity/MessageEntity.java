package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/15 6:11
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Data
@Entity(name="tc_acct_message")
public class MessageEntity {
    //登录ID
    @Id
    @Column(name="msg_id")
    private String msgId;

    @Column(name="login_name")
    private String loginName;

    @Column(name="flow_id")
    private String flowId;

    @Column(name="msg_info")
    private String msgInfo;

    @Column(name="msg_date")
    private String msgDate;

    @Column(name="status")
    private String status;
}
