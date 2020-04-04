package com.shetuan.responsitory;

import com.shetuan.entity.CommMemberEntity;
import com.shetuan.entity.PrimaryKeyLoginCommID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 12:13
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface CommMemberResponsitory extends JpaRepository<CommMemberEntity, PrimaryKeyLoginCommID> {

    @Query(value="select count(1) from tc_comm_member where status='1' and comm_id=? ",nativeQuery = true)
    String getCommMemberNumByCommId(String commId);
}
