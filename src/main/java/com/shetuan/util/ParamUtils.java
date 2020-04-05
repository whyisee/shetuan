package com.shetuan.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cglib.beans.BeanMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 15:51
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class ParamUtils {
    public static JSONObject getParamObjectWithFormData(HttpServletRequest request)
    {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            String[] values =  entry.getValue();
            StringBuilder value = new StringBuilder("");
            for (int i = 0; i < values.length; i++)
            {
                value.append(values[i]);
            }
            jsonObject.put(key, value.toString());
        }

        return jsonObject;
    }
    public static <T> T mapToBean(Map<String, Object> map,T bean){
        BeanMap beanMap=BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
}
