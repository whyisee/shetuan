package com.shetuan.responsitory;

import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/16 19:06
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface MemberResponsitory extends JpaRepository<MemberEntity,String> {
    List<MemberEntity> findByLoginName(String LoginName);
    @Query(value=" select login_id from tc_acct_login a where a.status='1' and a.login_name =? ",nativeQuery = true)
    String getIdByLoginName(String LoginName);

    @Query(value=" select user_name from tc_acct_member a where a.status='1' and a.login_name =? ",nativeQuery = true)
    String getUserNameByLoginName(String LoginName);

}
