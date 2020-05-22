package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/3 23:31
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
@Entity(name="tc_flow_appro")
public class ApproEntity {
    @Id
    @Column(name="appro_id")
    private String approId;

    @Column(name="flow_id")
    private String flowId;

    @Column(name="appro_name")
    private String approName;

    @Column(name="create_login_name")
    private String createLoginName;

    @Column(name="create_date")
    private String createDate;

    @Column(name="appro_login_name")
    private String approLoginName;

    @Column(name="appro_status")
    private String approStatus;

    @Column(name="appro_info")
    private String approInfo;

    @Column(name="appro_date")
    private String approDate;

    @Column(name="appro_type")
    private String approType;


    @Column(name="appro_remark")
    private String approRemark;

    @Column(name="status")
    private String status;
}
