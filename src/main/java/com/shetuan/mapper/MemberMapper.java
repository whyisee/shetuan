package com.shetuan.mapper;

import com.shetuan.entity.MemberEntity;

import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 13:39
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public interface MemberMapper {
    List<MemberEntity> getAll();

    MemberEntity getOne(Long id);

    /**
     * use for : 新增
     *@author zoukh
     *@Created in:  2020/2/14 18:05
     *@Modified By:
     *@version 1.0
     *@used in: MemberMapper
     */

    void insert(MemberEntity user);

    void update(MemberEntity user);
    void delete(Long id);
}
