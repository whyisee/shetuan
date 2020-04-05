package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:10
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
@Entity(name="tc_act_member")
@IdClass(PrimaryKeyLoginActivityID.class)
public class ActMemberEntity {
    @Id
    @Column(name="login_id")
    private String loginId;

    @Column(name="login_name")
    private String loginName;

    @Id
    @Column(name="activity_id")
    private String activityId;


    @Column(name="activity_name")
    private String activityName;

    @Column(name="sign_info")
    private String signInfo;

    @Column(name="create_date")
    private String createDate;

    @Column(name="status")
    private String status;
}
