package com.shetuan.responsitory;

import com.shetuan.entity.LoginEntity;
import com.shetuan.util.Page;
import com.shetuan.util.SQLParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/27 0:39
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@SpringBootTest
public class ManageSqlToolsTest {
    @Autowired
    private ManageSqlTools manageSqlTools;
    @Autowired
    private MemberResponsitory memberResponsitory;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ztest() throws Exception {
        //ManageSqlTools manageSqlTools= new ManageSqlTools();

        HashMap<String,Object> param=new HashMap<String,Object>();
        param.put("login_name","1111");
        //param.put("login_id","100001");


        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL("select * ,'11' a from tc_acct_login ");
        sqlParser.addSQL("where 1=1 ");
        sqlParser.addSQL(" and login_name=:login_name ");
        sqlParser.addSQL(" and login_id like  :login_id");

        sqlParser.addSQL(" and b=:b ");
        System.out.println("Test--------21:22--->:"+sqlParser.getSQL());

        Page page =new Page();
        page.setPageCurrent(2);
        List loginEntity= manageSqlTools.queryList(sqlParser.getSQL(),param,LoginEntity.class,page);

        //Query query=entityManager.createNativeQuery("select nextval('seq_test1_num2') aaa;");

        System.out.println("Test--------0:41--->:"+page+loginEntity);
    }
}