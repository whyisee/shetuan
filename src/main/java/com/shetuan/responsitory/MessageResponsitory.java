package com.shetuan.responsitory;

import com.shetuan.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/15 6:13
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface MessageResponsitory extends JpaRepository<MessageEntity,String> {

    @Query(value="update tc_acct_message set status='0' where msg_id=? ",nativeQuery = true)
    public void updateDelById(String msgId);
}
