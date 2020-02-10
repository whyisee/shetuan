package com.shetuan.util;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/3 20:56
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public abstract class JSONUtil {

    /**
     * 默认json格式化方式
     */
    public static final SerializerFeature[] DEFAULT_FORMAT = { SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.QuoteFieldNames, SerializerFeature.SkipTransientField,
            SerializerFeature.SortField, SerializerFeature.PrettyFormat };

    /**
     * @Title: getStringFromJSONObject
     * @Description: 从json获取指定key的字符串
     * @param json json字符串
     * @param key 字符串的key
     * @return 指定key的值
     */
    public static Object getStringFromJSONObject(final String json, final String key) {
        return JSON.parseObject(json).getString(key);
    }

    /**
     * @Title: getJSONFromString
     * @Description: 将字符串转换成JSON字符串
     * @param jsonString json字符串
     * @return 转换成的json对象
     */
    public static JSONObject getJSONFromString(final String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return new JSONObject();
        }
        return JSON.parseObject(jsonString);
    }

    /**
     * @Title: toBean
     * @Description: 将json字符串，转换成指定java bean
     * @param jsonStr json串对象
     * @param beanClass 指定的bean
     * @param <T> 任意bean的类型
     * @return 转换后的java bean对象
     */
    public static <T> T toBean(final String jsonStr, final Class<T> beanClass) {
        JSONObject jo = JSON.parseObject(jsonStr);
        jo.put(JSON.DEFAULT_TYPE_KEY, beanClass.getName());
        return JSON.parseObject(jo.toJSONString(), beanClass);
    }

    /**
     * @Title: toJson
     * @param obj 需要转换的java bean
     * @return 对应的json字符串
     */
    public static <T> String toJson(final T obj) {
        return JSON.toJSONString(obj, DEFAULT_FORMAT);
    }

    /**
     * 通过Map生成一个json字符串
     * @param map
     * @return
     */
    public static String toJson(final Map<String, Object> map) {
        return JSON.toJSONString(map, DEFAULT_FORMAT);
    }

    /**
     * 美化传入的json,使得该json字符串容易查看
     * @param jsonString
     * @return
     */
    public static String prettyFormatJson(final String jsonString) {
        return JSON.toJSONString(getJSONFromString(jsonString), true);
    }

    /**
     * 将传入的json字符串转换成Map
     * @param jsonString
     * @return
     */
    public static Map<String, Object> toMap(final String jsonString) {
        return getJSONFromString(jsonString);
    }

}
