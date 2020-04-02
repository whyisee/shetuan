package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import com.shetuan.responsitory.LoginResponsitory;
import com.shetuan.responsitory.MemberResponsitory;
import com.shetuan.responsitory.SeqFactory;
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
import java.util.Map;

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
        loginResponsitory.saveAcctRole(loginID,param.getString("username"),"1");


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
                modelMap.put("info", "OK");
                modelMap.put("roleId",loginResponsitory.getRoleIdbyLoginName(loginName));
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

    @RequestMapping("/update" )
    public @ResponseBody String update(ModelMap modelMap, HttpServletRequest request,@RequestBody MemberEntity memberEntity){
        //JSONObject param = ParamUtils.getParamObjectWithFormData(request);
       // MemberEntity member= JSON.parseObject(param.toString(), MemberEntity.class);
        memberEntity.setIsAddInfo("1");
        return JSONObject.toJSON(memberResponsitory.save(memberEntity)).toString();
    }

    @RequestMapping("/updatePWD" )
    public @ResponseBody String updatePWD(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object>  loginEntity){
        String loginName=loginEntity.get("loginName").toString();
        String passwdOld=loginEntity.get("passwdOld").toString();
        String passwdNew=loginEntity.get("passwdNew").toString();
        LoginEntity loginUsers= loginResponsitory.findByLoginName(loginName).get(0);
        ModelMap modelMap1=new ModelMap();

        if(!loginUsers.getLoginPass().equals(MD5Utils.getMD5(passwdOld))){
            modelMap1.put("status","0");
            modelMap1.put("info","原密码错误");
        }else{
            loginUsers.setLoginPass(MD5Utils.getMD5(passwdNew));
            loginResponsitory.save(loginUsers);
            modelMap1.put("status","1");
            modelMap1.put("info","OK");
        }




        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/getInfo")
    public @ResponseBody
    List<MemberEntity> getInfo(ModelMap modelMap, HttpServletRequest request,@RequestBody MemberEntity memberEntity){
        return memberResponsitory.findByLoginName(memberEntity.getLoginName());
    }


    public void test(){
        String loginID=entityManager.createNativeQuery(SeqFactory.SEQ_LOGIN_ID).getResultList().get(0).toString();
        System.out.println("Test--------11:58--->:"+loginID);
    }

}
