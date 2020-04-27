package com.shetuan.entity;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:09
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
@Entity(name="tc_comm_activity")
@Proxy(lazy = false)
public class ActivityEntity {
    @Id
    @Column(name="activity_id")
    private String activityId;


    @Column(name="activity_name")
    private String activityName;

    @Column(name="comm_id")
    private String commId;

    @Column(name="comm_name")
    private String commName;

    @Column(name="activity_date")
    private String activityDate;

    @Column(name="activity_addr")
    private String activityAddr;

    @Column(name="activity_sign_date")
    private String activitySignDate;

    @Column(name="activity_info")
    private String activityInfo;

    @Column(name="activity_persion_num")
    private String activityPersionNum;

    @Column(name="activity_persion_now")
    private String activityPersionNow;

    @Column(name="is_notcomm_can_sign")
    private String isNotcommCanSign;

    @Column(name="is_notstudent_can_sign")
    private String isNotstudentCanSign;

    @Column(name="create_persion_name")
    private String createPersionName;

    @Column(name="create_date")
    private String createDate;

    @Column(name="status")
    private String status;

}
