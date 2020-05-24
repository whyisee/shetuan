package com.shetuan.responsitory;

import com.shetuan.entity.CommunityEntity;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 22:21
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface CommunityResponsitory extends JpaRepository<CommunityEntity,String> {
   //     " SET @row_number=0, @class_no=0;                              " +

    @Query(value = "" +
            " select x.* from (                                            " +
            "     select a.*,                                              " +
            "       @row_number /**/ \\:=/**/ CASE                                    " +
            "        WHEN @class_no = a.comm_class_id THEN @row_number + 1 " +
            "          ELSE 1                                              " +
            "           END                      AS num,                   " +
            "       @class_no /**/ \\:=/**/ a.comm_class_id AS class_no               " +
            "     from tc_comm_community a                                 " +
            "      where 1=1 and a.status='1'         " +
            "    ORDER BY a.comm_class_id                                  " +
            "              ) x                                             " +
            "                                            " +
            "", nativeQuery = true)
    List<CommunityEntity> getIndexCommunity();



}
