package com.shetuan.responsitory;

import com.shetuan.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:26
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface ActivityResponsitory extends JpaRepository<ActivityEntity,String> {

    @Query(value = "select count(1)  from  tc_act_member where activity_id=? and status='1'",nativeQuery = true)
    public String getNowNumByActivityId(String activityId);
}
