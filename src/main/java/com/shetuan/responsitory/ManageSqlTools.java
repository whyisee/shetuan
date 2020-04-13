package com.shetuan.responsitory;

import com.shetuan.util.Page;
import com.shetuan.util.SQLParser;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * use for :
 * 1. 动态条件处理问题
 * 2. 数据分页
 *
 * @author zoukh
 * Created in:  2020/3/27 0:31
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Repository
public class ManageSqlTools  {
    @Autowired
    private MemberResponsitory memberResponsitory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;




    //获取条件参数
    private String[] preprocStatement(String sqlstr) throws Exception {
        Pattern patParam = Pattern.compile("(:[a-zA-Z_0-9\\$]*)");
        Pattern patQuote = Pattern.compile("('[^']*')");
        List quoteRanges = new ArrayList();
        Matcher matcher = patQuote.matcher(sqlstr);
        String [] names;
        class QuoteRange {
            int start;
            int length;
            String text;

            QuoteRange() {
            }
        }

        while(matcher.find()) {
            QuoteRange r = new QuoteRange();
            r.start = matcher.start();
            r.text = matcher.group();
            r.length = r.text.length();
            quoteRanges.add(r);
        }

        matcher = patParam.matcher(sqlstr);
        ArrayList keys = new ArrayList();

        while(true) {
            String key;
            boolean skip;
            do {
                if (!matcher.find()) {
                    //this.sql = sqlstr;
                    //System.out.println("Test--------18:31--->:"+sqlstr);
                    names = (String[])((String[])keys.toArray(new String[0]));
                    return names;
                }

                key = matcher.group().substring(1);
                if (quoteRanges.isEmpty()) {
                    break;
                }

                skip = false;
                int pos = matcher.start();
                Iterator it = quoteRanges.iterator();

                while(it.hasNext()) {
                    QuoteRange r = (QuoteRange)it.next();
                    if (pos >= r.start && pos < r.start + r.length) {
                        skip = true;
                        break;
                    }
                }
            } while(skip);

            //sqlstr = sqlstr.replaceFirst(":" + key.replaceAll("\\Q$\\E", "\\\\\\$"), "?");
            keys.add(key);
        }
    }

    public List queryList(SQLParser sql, Map<String,Object> param,Page page) throws Exception {
        return this.queryList(sql,param,null,page);
    }

    public List queryList(SQLParser sql, Map<String,Object> param) throws Exception {
        return this.queryList(sql,param,null,null);
    }

    public List queryList(SQLParser sql, Map<String,Object> param,Class clazz) throws Exception {
        return this.queryList(sql,param,clazz,null);
    }

    /**
     * use for : 简单查询,返回实体类型的list,like,null 此类条件未处理
     *@author zoukh
     *@Created in:  2020/4/2 18:42
     *@Modified By:
     *@version 1.0
     *@used in: ManageSqlTools
     */
    public List queryList(SQLParser sqlparser, Map<String,Object> param, Class clazz, Page page) throws Exception {
        List rs;
        Query query;

        String sql=sqlparser.getSQL();
        String[] names=preprocStatement(sql);
        //是否实体化
        if(null!=clazz) {
            query = entityManager.createNativeQuery(sql, clazz);
        }else {
            query = entityManager.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }

        //设置条件
        for(int i = 0; i < names.length; ++i) {
            Object value = param.get(names[i]);
            query.setParameter(names[i], value);
        }
        //是否分页
        if(null!=page) {
            rs=queryList(sqlparser,param,clazz,null);
            page.setItemCont(rs.size());
            query.setFirstResult(page.getItemStart());
            query.setMaxResults(page.getItemEnd());

        }
        rs=query.getResultList();
        return rs;
    }

    public void test(){
        String loginID=entityManager.createNativeQuery(ConfigFactory.SEQ_LOGIN_ID).getResultList().get(0).toString();
        System.out.println("Test--------11:58--->:"+loginID);
    }

}

