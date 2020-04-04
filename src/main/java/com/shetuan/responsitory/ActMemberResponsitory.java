package com.shetuan.responsitory;

import com.shetuan.entity.ActMemberEntity;
import com.shetuan.entity.PrimaryKeyLoginActivityID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:27
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface ActMemberResponsitory extends JpaRepository<ActMemberEntity, PrimaryKeyLoginActivityID> {
}
