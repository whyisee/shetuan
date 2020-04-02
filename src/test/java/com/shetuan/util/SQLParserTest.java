package com.shetuan.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/1 21:16
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
class SQLParserTest {

   // private SQLParser sqlParser = new SQLParser("");
    @Test
    void getSQL() throws Exception {

        HashMap<String,Object> param=new HashMap<String,Object>();
        param.put("a","11");
        SQLParser sqlParser = new SQLParser(param);
        sqlParser.addSQL("select * from test ");
        sqlParser.addSQL("where 1=1 ");
        sqlParser.addSQL(" and a=:a ");
        sqlParser.addSQL(" and b=:b ");
        System.out.println("Test--------21:22--->:"+sqlParser.getSQL());




    }
}