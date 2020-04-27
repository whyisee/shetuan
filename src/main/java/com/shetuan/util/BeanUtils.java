package com.shetuan.util;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * use for : bean工具类
 *
 * @author zoukh
 * Created in:  2020/4/14 0:23
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class BeanUtils {
    /**
     * use for :
     *@author zoukh
     *@Created in:  2020/4/14 8:07
     *@Modified By:
     *@version 1.0
     *@used in: BeanUtils
     */
    public static <T> T beanMarge(T bean1,T bean2,String margeType) {
        //先转换成map,map合并之后再转换成bean
        Map map1=beanToMap(bean1);
        Map map2=beanToMap(bean2);

        if("LEFT".equals(margeType)){
            mapToBean2(map1,bean2);
            return bean2;
        }else if("RIGHT".equals(margeType)){
            mapToBean2(map2,bean1);
            return bean1;
        }
        //mapToBean(map1,bean2);
        return bean1;
    }
    public static <T> T beanMarge(T bean1,T bean2)  {
        return beanMarge(bean1,bean2,"LEFT");
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
    /**
     * use for : 会保留原来bean中的数据
     *@author zoukh
     *@Created in:  2020/4/14 8:36
     *@Modified By:
     *@version 1.0
     *@used in: BeanUtils
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean){
        BeanMap beanMap=BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> T mapToBean2(Map<String, Object> map, T bean){
        BeanMap beanMap=BeanMap.create(bean);

        for (Object key : map.keySet()) {
            if(null!=map.get(key)&&!"".equals(map.get(key))) {
                beanMap.put(key + "", map.get(key));
            }
        }
        //beanMap.putAll(map);
        return bean;
    }
}
