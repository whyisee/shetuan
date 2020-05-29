package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.bean.Member;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MemberEntity;
import com.shetuan.responsitory.LoginResponsitory;
import com.shetuan.responsitory.MemberResponsitory;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.service.MemberService;
import com.shetuan.util.BeanUtils;
import com.shetuan.util.MD5Utils;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private MemberService memberService;

    @PersistenceContext
    private EntityManager entityManager;




    @RequestMapping(value="/checkUser",method = {RequestMethod.GET})
    public @ResponseBody String checkUserForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam Map param){
        ModelMap modelMap1=new ModelMap();
        List<LoginEntity> loginEntities =loginResponsitory.getByLoginName("loginName");
        if(loginEntities.size()>0){
            modelMap1.put("status","0");
            modelMap1.put("info","用户名已存在");
        }else {
            modelMap1.put("status","1");
            modelMap1.put("info","用户名未注册,可使用");
        }
        return JSONObject.toJSON(modelMap1).toString();

    }

    @RequestMapping(value="/checkUser",method = {RequestMethod.POST})
    public @ResponseBody String checkUser(ModelMap modelMap, HttpServletRequest request,@RequestBody Map param){
        ModelMap modelMap1=new ModelMap();
        List<LoginEntity> loginEntities =loginResponsitory.getByLoginName("loginName");
        if(loginEntities.size()>0){
            modelMap1.put("status","0");
            modelMap1.put("info","用户名已存在");
        }else {
            modelMap1.put("status","1");
            modelMap1.put("info","用户名未注册,可使用");
        }
        return JSONObject.toJSON(modelMap1).toString();
    }
    /*
    注册接口
    */
    @RequestMapping("/regist")
    public String registMember(ModelMap modelMap, HttpServletRequest request, MemberEntity memberEntity,HttpSession session) throws Exception {
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);
        //System.out.println("Test--------22:49--->:"+param);
        MemberEntity member= JSON.parseObject(param.toString(), MemberEntity.class);
        LoginEntity login= JSON.parseObject(param.toString(), LoginEntity.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        //param.getString()

        //获取用户id
        String loginID=entityManager.createNativeQuery(ConfigFactory.SEQ_LOGIN_ID).getResultList().get(0).toString();
        login.setLoginName(param.getString("username"));

        //先判断是否是学生
        Map params= new HashMap();
        params.put("studentId",param.getString("studentId"));
        params.put("userName",param.getString("userName"));
        //System.out.println("Test--------22:52--->:"+params);
        List students=memberService.checkStudent(params);
        if(students.isEmpty()){
            modelMap.put("status","0");
            modelMap.put("info","该学号非本校学生");
            session.setAttribute("login",1);
            return "/regist";
        }

        //再判断是否已注册
        List members=memberService.checkMembers(params);

        if(!members.isEmpty()){
            modelMap.put("status","0");
            modelMap.put("info","该学号已被注册");
            session.setAttribute("login",login);
            return "/regist";
        }
        //设置member表信息,保存
        member.setLoginId(loginID);
        member.setLoginName(param.getString("username"));
        member.setCreateDate(dateNowStr);
        member.setIsAddInfo("0");
        member.setStatus("1");
        //member.setEmail();
        //member.setPhone(param.getString("phone"));
        memberResponsitory.save(member);

        //设置login表信息,保存
        login.setLoginId(loginID);
        login.setLoginName(param.getString("username"));
        login.setLoginPass(MD5Utils.getMD5(param.getString("userpass")));
        login.setStatus("1");

        loginResponsitory.save(login);
        loginResponsitory.saveAcctRole(loginID,param.getString("username"),ConfigFactory.ROLE_CODE_PERSION);


        modelMap.put("login", login);
        modelMap.put("roleId", ConfigFactory.ROLE_CODE_PERSION);

        session.setAttribute("login",login);
        //设置权限信息
        List funcright=memberService.getShowLinkByRoleName(ConfigFactory.ROLE_CODE_PERSION);
        session.setAttribute("funcright",funcright);
        session.setAttribute("roleId",ConfigFactory.ROLE_CODE_PERSION);

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
        return "/index.jsp";
    }

    @GetMapping("/findAll")
    public @ResponseBody
    List<MemberEntity> findAll(){
        return memberResponsitory.findAll();
    }


    @RequestMapping("/login" )
    public String login(ModelMap modelMap, HttpServletRequest request, LoginEntity loginEntity, HttpSession session, HttpServletResponse response) throws Exception {
        JSONObject param = ParamUtils.getParamObjectWithFormData(request);
        String loginName= param.get("username").toString();
        String loginPass=MD5Utils.getMD5(param.get("userpass").toString());

        loginEntity.setLoginName(loginName);
        List<LoginEntity> loginUsers= loginResponsitory.findByLoginName(loginName);
        modelMap.put("status","0");
        if(loginUsers.isEmpty()){
            modelMap.put("info", "用户不存在");
        }else{
            if(loginPass.equals(loginUsers.get(0).getLoginPass())){
                String roleId=loginResponsitory.getRoleIdbyLoginName(loginName);
                modelMap.put("status","1");
                modelMap.put("info", "OK");
                modelMap.put("roleId",roleId);
                modelMap.put("login", loginEntity);

                //设置用户信息
                session.setAttribute("login",loginEntity);
                session.setAttribute("roleId",roleId);

                //设置权限信息
                List funcright= memberService.getShowLinkByRoleName(roleId);
                session.setAttribute("funcright",funcright);
                session.getId();

                Cookie cookie=new Cookie("name",loginEntity.getLoginName());
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
                //DefaultSingletonBeanRegistry;
                return "/index";
            }else{
                modelMap.put("info", "密码错误");
            }

        }

        return "/login";
    }

    @RequestMapping(value = "/update",method = {RequestMethod.GET})
    public @ResponseBody String updateForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam MemberEntity memberEntity){
        memberEntity.setIsAddInfo("1");
        String loginName=memberEntity.getLoginName();
        MemberEntity memberEntity1=memberResponsitory.findByLoginName(loginName).get(0);
        MemberEntity memberEntity2= BeanUtils.beanMarge(memberEntity,memberEntity1);
        return JSONObject.toJSON(memberResponsitory.save(memberEntity2)).toString();
    }

    @RequestMapping(value="/update",method = {RequestMethod.POST} )
    public @ResponseBody String update(ModelMap modelMap, HttpServletRequest request,@RequestBody MemberEntity memberEntity)  {
        memberEntity.setIsAddInfo("1");
        String loginName=memberEntity.getLoginName();
        MemberEntity memberEntity1=memberResponsitory.findByLoginName(loginName).get(0);
        MemberEntity memberEntity2= BeanUtils.beanMarge(memberEntity,memberEntity1);
        return JSONObject.toJSON(memberResponsitory.save(memberEntity2)).toString();
    }

    @RequestMapping("/updatePWD" )
    public @ResponseBody String updatePWD(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object>  params,HttpSession session){
        //不能通过传用户名的方式直接修改密码,可能出现调用接口修改他人密码的情况
        //若传了用户名则先判断是否和session中的用户名一致,若一致则允许修改
        //若不一致则查询当前用户角色是否为管理员,管理员可以修改他人密码,且不做旧密码想等验证
        ModelMap modelMap1=new ModelMap();
        LoginEntity loginEntity=((LoginEntity)session.getAttribute("login"));
        if(null==loginEntity){
            modelMap1.put("status","0");
            modelMap1.put("info","用户未登陆");
            return JSONObject.toJSON(modelMap1).toString();
        }
        String loginName=params.get("loginName")==null?null:params.get("loginName").toString();
        if(null==loginName||loginName.length()==0){
            loginName=loginEntity.getLoginName();
        }
        String passwdOld=params.get("passwdOld").toString();
        String passwdNew=params.get("passwdNew").toString();
        List<LoginEntity> loginUsers= loginResponsitory.findByLoginName(loginName);
        if(loginUsers.size()==0){
            modelMap1.put("status","0");
            modelMap1.put("info","用户不存在");
            return JSONObject.toJSON(modelMap1).toString();
        }
        LoginEntity loginUser=loginUsers.get(0);
        String roleId=loginResponsitory.getRoleIdbyLoginName(loginName);


        if(!loginUser.getLoginPass().equals(MD5Utils.getMD5(passwdOld))){
            modelMap1.put("status","0");
            modelMap1.put("info","原密码错误");
        }else if (!loginName.equals(loginEntity.getLoginName())&&roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)){
            loginUser.setLoginPass(MD5Utils.getMD5(passwdNew));
            loginResponsitory.save(loginUser);
            modelMap1.put("status","1");
            modelMap1.put("info","管理员修改密码成功");
        }else{
            loginUser.setLoginPass(MD5Utils.getMD5(passwdNew));
            loginResponsitory.save(loginUser);
            modelMap1.put("status","1");
            modelMap1.put("info","修改密码成功");
        }




        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/getInfo",method = {RequestMethod.GET})
    public @ResponseBody
    MemberEntity getInfoForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam Map<String,Object> params,HttpSession session) throws UnsupportedEncodingException {
        ModelMap modelMap1=new ModelMap();
        MemberEntity memberEntity=new MemberEntity();
        LoginEntity loginEntity=((LoginEntity)session.getAttribute("login"));
        if(null==loginEntity){
            modelMap1.put("status","0");
            modelMap1.put("info","用户未登陆");
            return memberEntity;
        }
        String loginName=params.get("loginName")==null?null:params.get("loginName").toString();
        String loginNameNow=loginEntity.getLoginName();
        loginName = URLDecoder.decode(loginName,"UTF-8");

 /*       String roleId="";
        if(null!=loginName&&loginName.length()>0&&!loginName.equals(loginNameNow)){
            roleId=loginResponsitory.getRoleIdbyLoginName(loginNameNow);
            if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)){
                memberEntity=memberResponsitory.findByLoginName(loginName).get(0);
            }
        }else{
            memberEntity=memberResponsitory.findByLoginName(loginNameNow).get(0);
        }*/
        System.out.println("111"+loginName);
        memberEntity=memberResponsitory.findByLoginName(loginName).get(0);

        return memberEntity;
    }
    @RequestMapping(value="/getInfo",method = {RequestMethod.POST})
    public @ResponseBody
    MemberEntity getInfo(ModelMap modelMap, HttpServletRequest request,@RequestBody  Map<String,Object> params,HttpSession session) throws UnsupportedEncodingException {
        ModelMap modelMap1=new ModelMap();
        MemberEntity memberEntity=new MemberEntity();
        LoginEntity loginEntity=((LoginEntity)session.getAttribute("login"));
        if(null==loginEntity){
            modelMap1.put("status","0");
            modelMap1.put("info","用户未登陆");
            return memberEntity;
        }
        String loginName=params.get("loginName")==null?null:params.get("loginName").toString();
        loginName = URLDecoder.decode(loginName,"UTF-8");
 /*       String loginNameNow=loginEntity.getLoginName();
        String roleId="";
        if(null!=loginName&&loginName.length()>0&&!loginName.equals(loginNameNow)){
            roleId=loginResponsitory.getRoleIdbyLoginName(loginNameNow);
            if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)){
                memberEntity=memberResponsitory.findByLoginName(loginName).get(0);
            }
        }else{
            memberEntity=memberResponsitory.findByLoginName(loginNameNow).get(0);
        }*/
        System.out.println("111"+loginName);

        memberEntity=memberResponsitory.findByLoginName(loginName).get(0);

        return memberEntity;
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("login");
        session.removeAttribute("funcright");
        session.removeAttribute("roleId");


        return "/index";
    }
    @RequestMapping(value="/checkStudent",method = {RequestMethod.GET})
    public @ResponseBody
    List checkStudentForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam Map<String,Object> params) throws Exception {
        List students=memberService.checkStudent(params);
        return students;
    }

    @RequestMapping(value="/checkStudent",method = {RequestMethod.POST})
    public @ResponseBody
    List checkStudent(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        List students=memberService.checkStudent(params);
        return students;
    }






}
