package com.shetuan.service;

import com.shetuan.entity.ActMemberEntity;
import com.shetuan.entity.ActivityEntity;
import com.shetuan.entity.CommunityEntity;
import com.shetuan.responsitory.ActMemberResponsitory;
import com.shetuan.responsitory.ActivityResponsitory;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.util.BeanUtils;
import com.shetuan.util.Page;
import com.shetuan.util.ParamUtils;
import com.shetuan.util.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:34
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Service
public class ActivityService {
    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private ActivityResponsitory activityResponsitory;

    @Autowired
    private ActMemberResponsitory actMemberResponsitory;


    public List<ActivityEntity> findAllMoreCondition(Map<String,Object> param, Page page) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.* from tc_comm_activity a ");
        sqlParser.addSQL(" where 1=1 and status='1'");
        sqlParser.addSQL(" and a.activity_id =:activityId ");
        sqlParser.addSQL(" and a.comm_id =:commId ");
        sqlParser.addSQL(" and a.activity_date <=:activityDate ");
        sqlParser.addSQL(" and a.activity_sign_date <=:activitySignDate ");
        sqlParser.addSQL(" and a.activity_persion_num <=:activityPersionNum ");
        sqlParser.addSQL(" and a.is_notcomm_can_sign =:isNotcommCanSign ");
        sqlParser.addSQL(" and a.is_notstudent_can_sign <=:isNotstudentCanSign ");
        sqlParser.addSQL(" and a.create_persion_name =:createPersionPame ");

        //暂不支持like条件,手工处理
        if(null!=param.get("commName")&&!param.get("commName").equals("")){
            sqlParser.addSQL(" and a.comm_name  like %"+param.get("commName")+"% ");
        }
        if(null!=param.get("activityName")&&!param.get("activityName").equals("")){
            sqlParser.addSQL(" and a.activity_name  like %"+param.get("activityName")+"% ");
        }
        List<ActivityEntity>  activitys= manageSqlTools.queryList(sqlParser,param, ActivityEntity.class,page);
        return activitys;
    }

    public String sign(Map<String,Object> param) throws Exception {
        String rs="";
        //活动参加
        //1.先在活动成员关系表中增加记录
        //2.再到活动中更新社团成员数量
/*        ActMemberEntity actMemberEntity= new ActMemberEntity();
        actMemberEntity= BeanUtils.mapToBean(param,actMemberEntity);
        actMemberResponsitory.save(actMemberEntity);*/


        ActivityEntity activityEntity=activityResponsitory.getOne(param.get("activityId").toString());
        activityEntity.setActivityPersionNow(activityResponsitory.getNowNumByActivityId(param.get("activityId").toString()));
        activityResponsitory.save(activityEntity);


        return rs;
    }


}
