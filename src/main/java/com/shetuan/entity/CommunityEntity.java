package com.shetuan.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 22:17
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
@Entity(name="tc_comm_community")
public class CommunityEntity {
    @Id
    @Column(name="comm_id")
    private String commId;

    //登录名
    @Column(name="comm_name")
    private String commName;

    @Column(name="create_persion_id")
    private String createPersionId;

    @Column(name="create_persion_name")
    private String createPersionName;

    @Column(name="boss_id")
    private String bossId;

    @Column(name="boss_name")
    private String bossName;

    @Column(name="create_date")
    private String createDate;

    @Column(name="comm_people_num")
    private String commPeopleNum;

    @Column(name="comm_class_id")
    private String commClassId;

/*    @Column(name="comm_class_name")
    private String commClassName;*/


    @Column(name="comm_info")
    private String commInfo;

    @Column(name="comm_pic")
    private String commPic;

    @Column(name="comm_special_act")
    private String commSpecialAct;

    @Column(name="show_order_no")
    private String showOrderNo;

    @Column(name="status")
    private String status;
}
