package com.shetuan.web;

import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.MemberEntity;
import com.shetuan.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 15:38
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class BaseController {
    @Value("${service.md5.key}")
    private String md5Key;
    /**
     * logger日志类
     */
    public Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * debug日志
     *
     * @param msg 日志信息
     */
    protected void debug(String msg)
    {
        logger.debug(msg);
    }

    /**
     * multipartResolver
     */
    @Autowired
    private MultipartResolver multipartResolver;

    /**
     * 转换请求参数为json格式
     *
     * @param request 请求
     * @return 请求参数
     */
    protected JSONObject getParamObject(HttpServletRequest request)
    {
        JSONObject jsonObject = getParamObjectWithFormData(request);
        String payload = getRequestPayload(request);
        if (!StringUtils.isEmpty(payload))
        {

            jsonObject.putAll(parseBodyParamPair(payload));
        }

        return jsonObject;
    }

    /**
     * 转换请求参数中的FormData参数为json格式
     *
     * @param request 请求
     * @return 请求参数
     */
    protected JSONObject getParamObjectWithFormData(HttpServletRequest request)
    {
        JSONObject jsonObject = new JSONObject();
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet())
        {
            String[] values = params.get(key);
            StringBuffer value = new StringBuffer("");
            for (int i = 0; i < values.length; i++)
            {
                value.append(values[i]);
            }
            jsonObject.put(key, value.toString());
        }
        return jsonObject;
    }

    private JSONObject parseBodyParamPair(String payloadStr)
    {
        JSONObject paramJson = new JSONObject();
        if (payloadStr.indexOf("{") != 0 && payloadStr.indexOf("&") >= 0)
        {
            String[] paramPairStrArray = payloadStr.split("&");
            for (String paramStr : paramPairStrArray)
            {
                if (!StringUtils.isEmpty(paramStr) && paramStr.indexOf("=") >= 0)
                {
                    String[] paramPairStr = paramStr.split("=");
                    paramJson.put(paramPairStr[0], paramPairStr[1]);
                }
            }
        }
        else
        {
            paramJson = JSONObject.parseObject(payloadStr);
        }
        return paramJson;
    }

    /**
     * 获取request payload中的json
     *
     * @param req
     * @return
     */
    private String getRequestPayload(HttpServletRequest req)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1)
            {
                sb.append(buff, 0, len);
            }
        }
        catch (IOException e)
        {
            logger.error("getRequestPayload.exception.2",e);
        }
        return sb.toString();
    }

    /**
     * @param request request
     * @return boolean
     */
    protected boolean isMultipartHttpServletRequest(HttpServletRequest request)
    {
        return multipartResolver.isMultipart(request);
    }

    /**
     * @param request 请求
     * @return MultipartHttpServletRequest
     */
    protected MultipartHttpServletRequest processMultipartHttpServletRequest(HttpServletRequest
                                                                                     request)
    {
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        if (multipartResolver.isMultipart(request))
        {
            multipartHttpServletRequest = multipartResolver.resolveMultipart(request);
        }
        return multipartHttpServletRequest;
    }
    protected JSONObject rootJsonObj(JSONObject param, MemberEntity member)
    {
        JSONObject reqHead = new JSONObject(true);
        reqHead.put("operateTime", System.currentTimeMillis());
        reqHead.put("loginId", member.getLoginId());
        reqHead.put("loginName", member.getLoginName());
        //rootJson 请求参数  reqBody  查询条件参数
        JSONObject rootJson = new JSONObject(true);
        rootJson.put("reqBody", param);
        rootJson.put("reqHead", reqHead);
        String md5key = rootJson.toJSONString() + md5Key;

        String md5 = MD5Utils.getMD5(md5key);
        reqHead.put("sign", md5);
        return rootJson;
    }
}
