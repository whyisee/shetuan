package com.shetuan.util;

import com.shetuan.entity.ApproEntity;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 0:25
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
class ParamUtilsTest {
    @Test
    void test(){
        Map map=new HashMap();
        map.put("approId","111");
        ApproEntity approEntity = new ApproEntity();
        approEntity=ParamUtils.mapToBean(map,approEntity);
        System.out.println("Test--------0:27--->:"+approEntity);
    }

}