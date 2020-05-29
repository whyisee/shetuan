package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.*;
import com.shetuan.responsitory.*;
import com.shetuan.service.CommunityService;
import com.shetuan.util.BeanUtils;
import com.shetuan.util.JSONUtil;
import com.shetuan.util.Page;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/28 21:48
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Controller
@RequestMapping(value="/community")
public class CommunityController extends BaseController {
    @Autowired
    private CommunityResponsitory communityResponsitory;
    @Autowired
    private CommunityClassResponsitory communityClassResponsitory;

    @Autowired
    private LoginResponsitory loginResponsitory;

    @Autowired
    private ApproResponsitory approResponsitory;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private  CommMemberResponsitory commMemberResponsitory;

    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private MemberResponsitory memberResponsitory;


    @RequestMapping("/index")
    public @ResponseBody
    String index(ModelMap modelMap, HttpServletRequest request, MemberEntity memberEntity) {

        List<CommunityEntity>  communityEntities=communityResponsitory.getIndexCommunity();
        List<CommunityClassEntity> communityClassEntities=communityClassResponsitory.getIndexCommunityClass();
        JSONArray jsonArray=new JSONArray();

        if(!communityClassEntities.isEmpty()){
            for(CommunityClassEntity communityClassEntity :communityClassEntities){
                JSONObject jsonObject= JSONObject.parseObject(JSONObject.toJSON(communityClassEntity).toString());


                JSONArray jsonArray2=new JSONArray();
                for(CommunityEntity communityEntity:communityEntities){
                    if(communityEntity.getCommClassId().equals(communityClassEntity.getCommClassId())){
                        jsonArray2.add(JSONObject.parseObject(JSONObject.toJSON(communityEntity).toString()));
                    }
                }
                jsonObject.put("communitys",jsonArray2);

                jsonArray.add(jsonObject);
            }
        }
        return jsonArray.toJSONString();
    }

    /**
     * use for : 所有社团查询
     *@Created in:  2020/4/3 19:32
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping(value="/findAll",method = {RequestMethod.GET})
    public @ResponseBody
    List<CommunityEntity> findAllForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception {
        Page page=null;
        //get请求不支持分页
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findAllMoreCondition(params,page);
    }

    @RequestMapping(value="/findAll",method = {RequestMethod.POST})
    public @ResponseBody
    List<CommunityEntity> findAll(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        Page page=null;
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findAllMoreCondition(params,page);
    }

    /**
     * use for : 查询社团展示的成员信息
     *@author zoukh
     *@Created in:  2020/4/3 21:29
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping(value="/findMemberShow",method = {RequestMethod.GET})
    public @ResponseBody
    List findMemberShowForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception {
        params.put("commWorkerId",ConfigFactory.COMM_WORKER_ID);
        Page page=null;
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findMemberByComm(params,page);
    }
    @RequestMapping(value="/findMemberShow",method = {RequestMethod.POST})
    public @ResponseBody
    List findMemberShow(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        params.put("commWorkerId",ConfigFactory.COMM_WORKER_ID);
        Page page=null;
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findMemberByComm(params,page);
    }

    /**
     * use for : 查询社团所有的成员信息
     *@author zoukh
     *@Created in:  2020/4/3 21:29
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping(value="/findMemberAll",method = {RequestMethod.GET})
    public @ResponseBody
    List<MemberEntity> findMemberAllForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception {
        Page page=null;
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findMemberByComm(params,page);
    }

    @RequestMapping(value="/findMemberAll",method = {RequestMethod.POST})
    public @ResponseBody
    List<MemberEntity> findMemberAll(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        Page page=null;
        if(null!=params.get("page")){
            page=JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return communityService.findMemberByComm(params,page);
    }

    @RequestMapping("/commInfo")
    public @ResponseBody
    CommunityEntity commInfo(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        Optional<CommunityEntity> comm= communityResponsitory.findById(params.get("commId").toString());
        return comm.get();
    }
    @RequestMapping("/commDel")
    public @ResponseBody
    String commDel(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params,HttpSession session) throws Exception {
        String rs="";
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());
        String approStatus="0";
        //再判断是否有申请通过记录
        if(null!=params.get("approId")){
            approStatus = approResponsitory.getApproStatusByID(params.get("approId").toString());
        }
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)) {
            communityResponsitory.deleteById(params.get("commId").toString());
        }
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/commAdd")
    public @ResponseBody
    String commAdd(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        //String login_name = login.getLoginName();
        String login_name = params.get("loginName").toString();

        String login_id = memberResponsitory.getIdByLoginName(login_name);
        String user_name=memberResponsitory.getUserNameByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        CommunityEntity comm = new CommunityEntity();
        //先判断下账号是否有权限
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());

        String approStatus="0";
        //再判断是否有申请通过记录
        if(null!=params.get("approId")){
            approStatus = approResponsitory.getApproStatusByID(params.get("approId").toString());
        }
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN )||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            //comm = BeanUtils.mapToBean(params,comm);

            //CommunityEntity comm = new CommunityEntity();
            comm = BeanUtils.mapToBean(params, comm);

            comm.setStatus("1");//
            String commId = manageSqlTools.getSeqId(ConfigFactory.SEQ_COMM_ID);
            comm.setCommId(commId);
            comm.setCreateDate(dateNowStr);
            comm.setCommPeopleNum("1");
            comm.setBossId(login_id);
            comm.setBossName(user_name);
            comm.setCreatePersionId(login_id);
            comm.setCreatePersionName(login_name);
            //保存社团信息
            communityResponsitory.save(comm);

            communityResponsitory.save(comm);
        }
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/commUpdate")
    public @ResponseBody
    String commUpdate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        String rs="";
        CommunityEntity comm = new CommunityEntity();
        //先判断下账号是否有权限,管理员或者社团管理员可以修改
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String loginName=login.getLoginName();
        loginName = URLDecoder.decode(loginName,"UTF-8");
        String roleId=loginResponsitory.getRoleIdbyLoginName(loginName);
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)) {

            comm = BeanUtils.mapToBean(params,comm);
            CommunityEntity comm2 = communityResponsitory.findById(params.get("commId").toString()).get();

            comm=BeanUtils.beanMarge(comm,comm2);
            communityResponsitory.save(comm);
        }
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }
    @RequestMapping("/memberAdd")
    public @ResponseBody
    String memberAdd(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        MemberEntity member = new MemberEntity();
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String loginName=login.getLoginName();
        loginName = URLDecoder.decode(loginName,"UTF-8");
        String roleId=loginResponsitory.getRoleIdbyLoginName(loginName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        String approStatus="0";
        //再判断是否有申请通过记录
        if(null!=params.get("approId")){
            approStatus = approResponsitory.getApproStatusByID(params.get("approId").toString());
        }
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            //保存团员信息
            CommMemberEntity commMemberEntity=new CommMemberEntity();
            commMemberEntity= BeanUtils.mapToBean(params,commMemberEntity);
            commMemberEntity.setCommWorkerId("0");//0-普通团员
            commMemberEntity.setCommWorker("普通团员");//0-普通团员
            commMemberEntity.setIsCreate("0");
            commMemberEntity.setCreateDate(dateNowStr);
            commMemberEntity.setStatus("1");
            commMemberResponsitory.save(commMemberEntity);
            communityService.memberAdd(params);
        }
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/memberDel")
    public @ResponseBody
    String memberDel(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        MemberEntity member = new MemberEntity();
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        //params.put("dealLoginId",dealLoginId);

        params.put("loginId",login_id);
        String approStatus="0";
        //再判断是否有申请通过记录
        if(null!=params.get("approId")){
            approStatus = approResponsitory.getApproStatusByID(params.get("approId").toString());
        }
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            communityService.memberDel(params);
        }
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/memberInfo")
    public @ResponseBody
    CommMemberEntity memberInfo(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        String user_name=memberResponsitory.getUserNameByLoginName(login_name);

        PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(params.get("commId").toString());
        primaryKeyLoginCommID.setLoginId(login_id);
        Optional <CommMemberEntity> commM=commMemberResponsitory.findById(primaryKeyLoginCommID);
        return commM.get();
    }

    @RequestMapping("/memberUpdate")
    public @ResponseBody
    String memberUpdate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(params.get("commId").toString());
        //primaryKeyLoginCommID.setLoginId(login_id);
        primaryKeyLoginCommID.setLoginId(params.get("dealLoginId").toString());

        CommMemberEntity commM=commMemberResponsitory.findById(primaryKeyLoginCommID).get();;
        CommMemberEntity  comm=new CommMemberEntity();
        comm = BeanUtils.mapToBean(params,comm);
        comm=BeanUtils.beanMarge(comm,commM);
        commMemberResponsitory.save(comm);
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/checkComm",method = {RequestMethod.GET})
    public @ResponseBody
    String checkCommForGet(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ) throws Exception {
        ModelMap modelMap1=new ModelMap();
        List<CommunityEntity> list=communityService.findAllMoreCondition(params,null);
        if(list.size()>0){
            modelMap1.put("status","0");
            modelMap1.put("info","社团已存在");
        }else {
            modelMap1.put("status","1");
            modelMap1.put("info","社团不存在,可创建");
        }
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/checkComm",method = {RequestMethod.POST})
    public @ResponseBody
    String checkComm(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params , HttpSession session) throws Exception {
        ModelMap modelMap1=new ModelMap();
        List<CommunityEntity> list=communityService.findAllMoreCondition(params,null);
        if(list.size()>0){
            modelMap1.put("status","0");
            modelMap1.put("info","社团已存在");
        }else {
            modelMap1.put("status","1");
            modelMap1.put("info","社团不存在,可创建");
        }
        return JSONObject.toJSON(modelMap1).toString();
    }


    @RequestMapping(value="/roleChange",method = {RequestMethod.POST})
    public @ResponseBody
    String roleChange(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params , HttpSession session) throws Exception {
        ModelMap modelMap1=new ModelMap();
        //两种情况,1.普通成员变为社团管理员,先将原来的管理员变为普通成员
        //2.社团管理员变为普通成员

        //
        String newRole = params.get("newRole").toString();
        String loginName = params.get("loginName").toString();
        String commId = params.get("commId").toString();
        //String login_id = memberResponsitory.getIdByLoginName(loginName);
        MemberEntity memberEntity = memberResponsitory.findByLoginName(loginName).get(0);
        CommunityEntity comm = communityResponsitory.getOne(commId);
        PrimaryKeyLoginCommID primaryKeyLoginCommID = new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(commId);
        primaryKeyLoginCommID.setLoginId(memberEntity.getLoginId());
        CommMemberEntity commM = commMemberResponsitory.getOne(primaryKeyLoginCommID);

        if ("社团管理员".equals(newRole)){
            comm.setBossId(memberEntity.getLoginId());
            comm.setBossName(memberEntity.getUserName());
            loginResponsitory.updateAcctRole(loginName,ConfigFactory.ROLE_CODE_COMM_ADMIN);
            commM.setCommWorkerId("101");
            commM.setCommWorker("会长");
            commMemberResponsitory.save(commM);


        }else if ("普通成员".equals(newRole)){
            comm.setBossId("");
            comm.setBossName("");
            loginResponsitory.updateAcctRole(loginName,ConfigFactory.ROLE_CODE_PERSION);
            commM.setCommWorkerId("0");
            commM.setCommWorkerId("普通团员");
            commMemberResponsitory.save(commM);
        }
        communityResponsitory.save(comm);

        modelMap1.put("status","0");
        modelMap1.put("info","修改成功");

        return JSONObject.toJSON(modelMap1).toString();
    }



}