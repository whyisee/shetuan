package com.shetuan.service;

import com.shetuan.entity.MessageEntity;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.util.Page;
import com.shetuan.util.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/15 6:14
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Service
public class MessageService {

    @Autowired
    private ManageSqlTools manageSqlTools;
    public MessageEntity add(Map param){

        return null;
    }
    public List<MessageEntity> list(Map param, Page page) throws Exception {
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL(" select a.* from tc_acct_message a ");
        sqlParser.addSQL(" where 1=1 and a.status='1' ");
        sqlParser.addSQL(" and a.login_name=:loginName ");
        sqlParser.addSQL(" and a.flow_id=:flowId ");
        sqlParser.addSQL(" and replace(substr(msg_date,1,10),'-','')<=:msgDate ");
        return manageSqlTools.queryList(sqlParser,param,MessageEntity.class,page);
    }

}
