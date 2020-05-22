package com.shetuan.service;

import com.shetuan.entity.ApproEntity;
import com.shetuan.responsitory.ApproResponsitory;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.util.BeanUtils;
import com.shetuan.util.Page;
import com.shetuan.util.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/14 22:19
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Service
public class ApproService {

    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private ApproResponsitory approResponsitory;


    public List<Map<String,String >> getApproPrersion(Map<String,Object> param) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.login_name loginName,a.user_name userName from tc_acct_member a ,tc_acct_role b ");
        sqlParser.addSQL(" where 1=1 and a.status='1' and b.status='1' and a.login_id=b.login_id ");

        //社团创建/删除审核人--系统管理员角色
        //社团加入/退出审核人--社团管理员角色
        //活动创建/删除审核人--系统管理员角色
        //活动加入/退出审核人--社团管理员角色
        String approType=param.get("approType").toString();

        //管理员
        if(approType.equals(ConfigFactory.APPRO_TYPE_COMM_ADD)
                ||approType.equals(ConfigFactory.APPRO_TYPE_COMM_DEL)
                ||approType.equals(ConfigFactory.APPRO_TYPE_ACT_ADD)
                ||approType.equals(ConfigFactory.APPRO_TYPE_ACT_DEL)){
            sqlParser.addSQL( " and b.role_id ='100' " );

        }else if(approType.equals(ConfigFactory.APPRO_TYPE_COMM_SIGN)
                ||approType.equals(ConfigFactory.APPRO_TYPE_COMM_QUIT)
                ||approType.equals(ConfigFactory.APPRO_TYPE_ACT_SIGN)
                ||approType.equals(ConfigFactory.APPRO_TYPE_ACT_QUIT)){
            sqlParser.addSQL( " and b.role_id ='200' " );
            sqlParser.addSQL( " and b.login_name in (select login_name from tc_comm_member where status='1' and comm_id =:commId ) " );
        }
        List  persions= manageSqlTools.queryList(sqlParser,param, null,null);
        return persions;
    }

    public int saveAppro(Map<String,Object> param) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        //保存审核信息
        ApproEntity approEntity = new ApproEntity();
        approEntity = BeanUtils.mapToBean(param, approEntity);
        String approId = manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID);
        approEntity.setApproId(approId);
        approEntity.setCreateDate(dateNowStr);
        approEntity.setStatus("1");
        approEntity.setApproStatus("0");
        approEntity.setCreateLoginName(param.get("loginName").toString());

        return approResponsitory.save(approEntity) == null ? 0 : 1;
    }

    /**
     * use for : 查询是否有未审核的申请,防止重复申请
     *@author zoukh
     *@Created in:  2020/4/15 2:10
     *@Modified By:
     *@version 1.0
     *@used in: ApproService
     */
    public int getIsHaveAppro(Map<String,Object> param) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.* from tc_flow_appro a ");
        sqlParser.addSQL(" where 1=1 and a.status='1' and appro_status='0' ");
        sqlParser.addSQL(" and flow_id =:flowId ");
        sqlParser.addSQL(" and appro_type =:approType ");
        sqlParser.addSQL(" and create_login_name =:loginame ");


        return manageSqlTools.queryList(sqlParser,param, null,null).size();

    }
    public List findAll(Map<String,Object> param, Page page) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.* ,case when appro_login_name=:loginName or '100'=:roleId then '1' else '0' end isCanAppro  ");
        sqlParser.addSQL("  from tc_flow_appro a where 1=1 and a.status='1' ");
        sqlParser.addSQL(" and appro_id =:approId ");
        sqlParser.addSQL(" and flow_id =:flowId ");
        sqlParser.addSQL(" and create_login_name =:createLoginName ");
        sqlParser.addSQL(" and appro_login_name =:approLoginName ");
        sqlParser.addSQL(" and appro_status =:approStatus ");
        sqlParser.addSQL(" and replace(substr(create_date,1,10),'-','') >=:createDate ");
        sqlParser.addSQL(" and replace(substr(appro_date,1,10),'-','') >=:approDate ");
        sqlParser.addSQL(" and appro_type =:approType ");


        return manageSqlTools.queryList(sqlParser,param, null,page);

    }

}
