package com.shetuan.responsitory;

import com.shetuan.entity.ApproEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/3 23:36
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface ApproResponsitory extends JpaRepository<ApproEntity,String> {

    @Query(value="select appro_status from tc_flow_appro where appro_id=? ",nativeQuery = true)
    public String getApproStatusByID(String appro_id);
}
