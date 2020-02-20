package com.shetuan.responsitory;

import com.shetuan.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

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


}
