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
 * Created in:  2020/3/27 13:22
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface LoginResponsitory extends JpaRepository<LoginEntity,String> {
    @Query(value = "select * from tc_acct_login where login_name = ?", nativeQuery = true)
    List<LoginEntity> getByLoginName(String LoginName);

    List<LoginEntity> findByLoginName(String LoginName);

}
