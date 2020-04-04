package com.shetuan.responsitory;

import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying
    @Query(value = "insert into  tc_acct_role value(?1,?2,?3,'1')", nativeQuery = true)
    int saveAcctRole(String loginId,String LoginName,String roleCode);

    /**
     * use for : 根据登录名查询角色ID
     *@Created in:  2020/4/1 22:39
     *@Modified By:
     *@version 1.0
     *@used in: LoginResponsitory
     */
    @Query(value = "select role_id from tc_acct_role where login_name=?", nativeQuery = true)
    String getRoleIdbyLoginName(String LoginName);


    /**
     * use for : 根据登录名查询链接是否展示
     *@Created in:  2020/4/1 12:33
     *@Modified By:
     *@version 1.0
     *@used in: LoginResponsitory
     */
    @Query(value = "select a.* from td_b_rolefuncright a join tc_acct_login b on a.role_id=b.role_id " +
            "where 1=1 and a.status='1' and b.status='1' " +
            " b.login_name=? ", nativeQuery = true)
    List getShowLink(String LoginName);

}
