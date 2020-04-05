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
 * Created in:  2020/4/4 11:58
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Data
@Entity(name="tc_comm_member")
@IdClass(PrimaryKeyLoginCommID.class)
public class CommMemberEntity {
    @Id
    @Column(name="login_id")
    private String loginId;

    @Column(name="login_name")
    private String loginName;

    @Id
    @Column(name="comm_id")
    private String commId;

    @Column(name="comm_name")
    private String commName;

    @Column(name="comm_worker_id")
    private String commWorkerId;

    @Column(name="comm_worker")
    private String commWorker;

    @Column(name="comm_remark")
    private String commRemark;

    @Column(name="comm_person_remark")
    private String commPersonRemark;

    @Column(name="is_create")
    private String isCreate;

    @Column(name="status")
    private String status;


}
