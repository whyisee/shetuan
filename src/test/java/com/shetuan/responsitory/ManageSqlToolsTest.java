package com.shetuan.responsitory;

import com.shetuan.util.SQLParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.HashMap;

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

        HashMap<String,String> param=new HashMap<String,String>();
        param.put("a","11");
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL("select * from test ");
        sqlParser.addSQL("where 1=1 ");
        sqlParser.addSQL(" and a=:a ");
        sqlParser.addSQL(" and b=:b ");
        System.out.println("Test--------21:22--->:"+sqlParser.getSQL());

        manageSqlTools.sqlSingleList("select nextval('seq_test1_num2') aaa;");
        //Query query=entityManager.createNativeQuery("select nextval('seq_test1_num2') aaa;");

        //System.out.println("Test--------0:41--->:"+query.getResultList());
    }
}