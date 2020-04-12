package com.shetuan.service;

import com.shetuan.entity.CommMemberEntity;
import com.shetuan.entity.CommunityEntity;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.PrimaryKeyLoginCommID;
import com.shetuan.responsitory.CommMemberResponsitory;
import com.shetuan.responsitory.CommunityResponsitory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.util.Page;
import com.shetuan.util.ParamUtils;
import com.shetuan.util.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/3 19:39
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Service
public class CommunityService {
    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private CommMemberResponsitory commMemberResponsitory;

    @Autowired
    private CommunityResponsitory communityResponsitory;

    public List<CommunityEntity> findAllMoreCondition(Map<String,Object> param, Page page) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.* from tc_comm_community a ");
        sqlParser.addSQL(" where 1=1 and status='1'");
        sqlParser.addSQL(" and a.comm_id =:commId ");
        sqlParser.addSQL(" and a.create_persion_name =:createPersionName ");
        sqlParser.addSQL(" and a.boss_name  =:bossName ");
        sqlParser.addSQL(" and a.comm_class_id  =:commClassId ");
        //暂不支持like条件,手工处理
        if(null!=param.get("commName")&&!param.get("commName").equals("")){
            sqlParser.addSQL(" and a.comm_name  like '%"+param.get("commName")+"%' ");
        }
        List<CommunityEntity>  communitys= manageSqlTools.queryList(sqlParser,param, CommunityEntity.class,page);
        return communitys;
    }


    public List findMemberByComm(Map<String,Object> param, Page page) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL("  select a.*,b.user_name from tc_comm_member a         ");
        sqlParser.addSQL("  left join tc_acct_member b on a.login_id=b.login_id  ");
        sqlParser.addSQL("  where 1=1                                            ");
        sqlParser.addSQL("  and a.status='1'                                     ");
        sqlParser.addSQL("  and b.status='1'                                     ");
        sqlParser.addSQL("  and a.comm_id =:commId                              ");
        sqlParser.addSQL("  and a.comm_worker_id >:commWorkerId                ");
        sqlParser.addSQL("  order by comm_worker_id,create_date                                 ");

        List  rs= manageSqlTools.queryList(sqlParser,param, null,page);
        return rs;
    }

    public String memberAdd(Map<String,Object> param) throws Exception {
        //社团成员增加
        //1.先在社团成员关系表中增加记录
        //2.在社团表中更新社团成员数量
        CommMemberEntity commMemberEntity=new CommMemberEntity();
        commMemberEntity= ParamUtils.mapToBean(param,commMemberEntity);
        commMemberResponsitory.save(commMemberEntity);

        Optional<CommunityEntity> comm=communityResponsitory.findById(param.get("commId").toString());
        CommunityEntity commNew=comm.get();
        commNew.setCommPeopleNum(commMemberResponsitory.getCommMemberNumByCommId(param.get("commId").toString()));
        communityResponsitory.save(commNew);
        return "";
    }
    public String memberDel(Map<String,Object> param) throws Exception {
        //社团成员增加
        //1.先在社团成员关系表中删除记录
        //2.在社团表中更新社团成员数量
        CommMemberEntity commMemberEntity=new CommMemberEntity();
        commMemberEntity= ParamUtils.mapToBean(param,commMemberEntity);
        PrimaryKeyLoginCommID primaryKeyLoginCommID = new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(param.get("commId").toString());
        primaryKeyLoginCommID.setLoginId(param.get("LoginId").toString());

        commMemberResponsitory.deleteById(primaryKeyLoginCommID);

        Optional<CommunityEntity> comm=communityResponsitory.findById(param.get("commId").toString());
        CommunityEntity commNew=comm.get();
        commNew.setCommPeopleNum(commMemberResponsitory.getCommMemberNumByCommId(param.get("commId").toString()));
        communityResponsitory.save(commNew);
        return "";
    }

}
