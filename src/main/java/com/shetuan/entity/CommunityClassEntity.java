package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 22:51
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Data
@Entity(name="tc_comm_class")
public class CommunityClassEntity {
    @Id
    @Column(name="comm_class_id")
    private String commClassId;


    @Column(name="comm_class_name")
    private String commClassName;

    @Column(name="update_persion_id")
    private String updatePersionId;

    @Column(name="update_date")
    private String updateDate;

    @Column(name="remark")
    private String remark;

    @Column(name="show_order_no")
    private String showOrderNo;


}
