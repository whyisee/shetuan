package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.MemberEntity;
import com.shetuan.mapper.MemberMapper;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 13:11
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@RestController
@RequestMapping(value="/member")
public class MemberController extends BaseController{

    @Autowired
    MemberMapper memberMapper;


    @RequestMapping("/regist")
    public String registMember(ModelMap modelMap, HttpServletRequest request, MemberEntity memberEntity){
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);

        MemberEntity member= JSON.parseObject(param.toString(), MemberEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        member.setCreateDate(dateNowStr);
        memberMapper.insert(member);
        modelMap.put("columnInfo", "");
        System.out.println("Test--------15:56--->:"+param);
        return "/index.jsp";
    }
}
