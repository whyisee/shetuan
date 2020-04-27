package com.shetuan.service;

import com.shetuan.responsitory.LoginResponsitory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.util.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 21:45
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Service
public class MemberService {
    @Autowired
    private LoginResponsitory loginResponsitory;

    @Autowired
    private ManageSqlTools manageSqlTools;

    public List getShowLinkByRoleName(String RoleId) throws Exception {
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("role_code",RoleId);
        SQLParser sqlParser=new SQLParser(param);
        sqlParser.addSQL("select * from  td_b_rolefuncright a ");
        sqlParser.addSQL(" where 1=1 and a.status='1' and  a.role_code=:role_code ");
        return manageSqlTools.queryList(sqlParser,param);

    }
}
