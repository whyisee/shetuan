package com.shetuan.responsitory;

import com.shetuan.entity.CommunityClassEntity;
import com.shetuan.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 22:54
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface CommunityClassResponsitory extends JpaRepository<CommunityClassEntity,String> {
    @Query(value = "select a.* from tc_comm_class a where status='1' and show_order_no>0 order by show_order_no limit 5 ",nativeQuery = true)
    List<CommunityClassEntity> getIndexCommunityClass();

}
