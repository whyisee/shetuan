package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import com.shetuan.mapper.MemberMapper;
import com.shetuan.responsitory.LoginResponsitory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.responsitory.MemberResponsitory;
import com.shetuan.util.JSONUtil;
import com.shetuan.util.MD5Utils;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/14 13:11
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Controller
@RequestMapping(value="/member")
public class MemberController extends BaseController{

    //@Autowired
    //MemberMapper memberMapper;

    @Autowired
    MemberResponsitory memberResponsitory;
    @Autowired
    private LoginResponsitory loginResponsitory;

    @PersistenceContext
    private EntityManager entityManager;

    /*
    注册接口
    */
    @RequestMapping("/regist")
    public String registMember(ModelMap modelMap, HttpServletRequest request, MemberEntity memberEntity){
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);

        MemberEntity member= JSON.parseObject(param.toString(), MemberEntity.class);
        LoginEntity login= JSON.parseObject(param.toString(), LoginEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        //获取用户id
        String loginID=entityManager.createNativeQuery(SeqFactory.SEQ_LOGIN_ID).getResultList().get(0).toString();

        //设置member表信息,保存
        member.setLoginId(loginID);
        member.setLoginName(param.getString("username"));
        member.setCreateDate(dateNowStr);
        member.setIsAddInfo("0");
        member.setEmail(param.getString("email"));
        member.setPhone(param.getString("phone"));
        memberResponsitory.save(member);

        //设置login表信息,保存
        login.setLoginId(loginID);
        login.setLoginName(param.getString("username"));
        login.setLoginPass(MD5Utils.getMD5(param.getString("userpass")));
        login.setStatus("1");

        loginResponsitory.save(login);


        modelMap.put("login", login);
        System.out.println("Test--------15:56--->:"+loginID+param);
        return "/index";
    }

    @RequestMapping("/delete")
    public String deleteMember(ModelMap modelMap, HttpServletRequest request, MemberEntity memberEntity){
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);

        MemberEntity member= JSON.parseObject(param.toString(), MemberEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        //member.setCreateDate(dateNowStr);
        member.setLoginId( param.get("loginId").toString());
        member.setLoginName("123");
        member.setIsAddInfo("0");
        memberResponsitory.deleteById(param.get("loginId").toString());
        modelMap.put("columnInfo", "");
        System.out.println("Test--------15:56--->:"+param);
        return "/index.jsp";
    }

    @GetMapping("/findAll")
    public @ResponseBody
    List<MemberEntity> findAll(){
        return memberResponsitory.findAll();
    }


    @RequestMapping("/login" )
    public String login(ModelMap modelMap, HttpServletRequest request,LoginEntity loginEntity){
        //modelMap = new ModelMap();
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);
        //System.out.println("Test--------22:23--->:"+param+loginEntity);
        String loginName= param.get("username").toString();
        String loginPass=MD5Utils.getMD5(param.get("userpass").toString());

        loginEntity.setLoginName(loginName);
        List<LoginEntity> loginUsers= loginResponsitory.findByLoginName(loginName);
        modelMap.put("status","0");
        if(loginUsers.isEmpty()){
            modelMap.put("info", "用户不存在");
        }else{
            if(loginPass.equals(loginUsers.get(0).getLoginPass())){
                modelMap.put("status","1");
                modelMap.put("info", "登录成功");
                modelMap.put("managerId","0");
                modelMap.put("login", loginEntity);

                return "/index";
            }else{
                modelMap.put("info", "密码错误");
            }

        }

        //JSONArray jsonObject = JSONArray.toJSON("");
        //System.out.println("Test--------23:09--->:"+JSONUtil.toJson(modelMap));
        return "/login";
    }

}
