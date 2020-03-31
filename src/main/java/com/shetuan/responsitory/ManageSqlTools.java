package com.shetuan.responsitory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/27 0:31
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Transactional
public class ManageSqlTools {
    @Autowired
    private MemberResponsitory memberResponsitory;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public ManageSqlTools(){
        System.out.println("Test--------1:10--->:"+"sss");
    }

/*    public List<Object[]> sqlArrayList(String sql) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery(sql);
        List<Object[]> list = query.getResultList();
        em.close();
        return list;
    }
    public List sqlObjectList(String sql ,Object obj){
        EntityManager em =emf.createEntityManager();
        Query query = em.createNamedQuery(sql,obj.getClass());
        List list = query.getResultList();
        em.close();
        return list;
    }*/
    public List sqlSingleList(String sql){
        System.out.println("Test--------1:10--->:"+"zzz");

        //EntityManager em = new EntityManager();
        //Query query = em.createNamedQuery(sql);
        Query query = entityManager.createNativeQuery(sql);
        String loginID=entityManager.createNativeQuery("select nextval('seq_test1_num2')").getResultList().get(0).toString();

        //List list = query.getResultList();
        query.getResultList();

        //entityManager.close();
        return null;
    }

}

