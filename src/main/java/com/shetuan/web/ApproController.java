package com.shetuan.web;

import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.*;
import com.shetuan.responsitory.*;
import com.shetuan.service.ActivityService;
import com.shetuan.service.ApproService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import  com.shetuan.responsitory.ConfigFactory;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/11 21:47
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */



@Controller

@RequestMapping(value="/appro")
public class ApproController extends BaseController {


    @Autowired
    private CommunityResponsitory communityResponsitory;

    @Autowired
    private ApproResponsitory approResponsitory;

    @Autowired
    private ApproService approService;

    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private MemberResponsitory memberResponsitory;

    @Autowired
    private CommMemberResponsitory commMemberResponsitory;

    @Autowired
    private ActivityResponsitory activityResponsitory;

    @Autowired
    private ActMemberResponsitory actMemberResponsitory;

    @Autowired
    private LoginResponsitory loginResponsitory;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ActivityService activityService;

    /**
     * use for : 申请创建社团
     *
     * @author zoukh
     * @Created in:  2020/4/11 21:50
     * @Modified By:
     * @version 1.0
     * @used in: ApproController
     */
    @RequestMapping("/commCreate")
    public @ResponseBody
    String commCreate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {


        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        String user_name=memberResponsitory.getUserNameByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        CommunityEntity comm = new CommunityEntity();
        comm = BeanUtils.mapToBean(params, comm);

        comm.setStatus(ConfigFactory.COMM_APPRO_STATUS);//
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
        //保存审核信息
        params.put("flowId",commId);
        params.put("approName","社团创建申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_COMM_ADD);
        approService.saveAppro(params);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");

        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/getApproPersion",method = {RequestMethod.GET})
    public @ResponseBody
    List getApproPersionForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> params) throws Exception {
        List persions=approService.getApproPrersion(params);
        return persions;
    }
    @RequestMapping(value="/getApproPersion",method = {RequestMethod.POST})
    public @ResponseBody
    List getApproPersion(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        List persions=approService.getApproPrersion(params);
        return persions;
    }

    @RequestMapping(value="/memberAdd",method = {RequestMethod.POST})
    public @ResponseBody
    String commSign(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        System.out.println(login);
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        //保存审核信息
        params.put("flowId",params.get("commId"));
        params.put("approName","加入社团申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_COMM_SIGN);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);

        //保存团员信息
        CommMemberEntity commMemberEntity=new CommMemberEntity();
        commMemberEntity= BeanUtils.mapToBean(params,commMemberEntity);
        commMemberEntity.setCommWorkerId("0");//0-普通团员
        commMemberEntity.setCommWorker("普通团员");//0-普通团员
        commMemberEntity.setIsCreate("0");
        commMemberEntity.setCreateDate(dateNowStr);
        commMemberEntity.setStatus(ConfigFactory.COMM_APPRO_STATUS);
        commMemberResponsitory.save(commMemberEntity);



        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }


    @RequestMapping(value="/commDel",method = {RequestMethod.POST})
    public @ResponseBody
    String commDel(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);


        //保存审核信息
        params.put("flowId",params.get("commId"));
        params.put("approName","解散社团申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_COMM_DEL);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/memberDel",method = {RequestMethod.POST})
    public @ResponseBody
    String memberDel(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        //保存审核信息
        params.put("flowId",params.get("commId"));
        params.put("approName","退出社团申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_COMM_QUIT);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }

    /**
     * use for : 发布活动
     *@author zoukh
     *@Created in:  2020/4/15 2:35
     *@Modified By:
     *@version 1.0
     *@used in: ApproController
     */
    @RequestMapping(value="/actCreate",method = {RequestMethod.POST})
    public @ResponseBody
    String actCreate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);
        String activityId=manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        //保存审核信息
        params.put("flowId",activityId);
        params.put("approName","发布活动申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_ACT_ADD);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);

        //保存活动信息
        ActivityEntity activityEntity=new ActivityEntity();
        activityEntity= BeanUtils.mapToBean(params,activityEntity);
        activityEntity.setActivityId(activityId);
        activityEntity.setActivityPersionNow("1");
        activityEntity.setCreatePersionName(login_name);
        activityEntity.setCreateDate(dateNowStr);
        activityEntity.setStatus(ConfigFactory.COMM_APPRO_STATUS);
        activityResponsitory.save(activityEntity);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }


    @RequestMapping(value="/actSign",method = {RequestMethod.POST})
    public @ResponseBody
    String actSign(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        //保存审核信息
        params.put("flowId",params.get("activityId"));
        params.put("approName","报名活动申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_ACT_SIGN);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);

        //保存活动人员信息
        ActMemberEntity actMemberEntity=new ActMemberEntity();
        actMemberEntity= BeanUtils.mapToBean(params,actMemberEntity);
        //actMemberEntity.setActivityId(manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID));
        actMemberEntity.setCreateDate(dateNowStr);
        actMemberEntity.setStatus(ConfigFactory.COMM_APPRO_STATUS);
        actMemberResponsitory.save(actMemberEntity);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/actSignOff",method = {RequestMethod.POST})
    public @ResponseBody
    String actSignOff(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);

        //保存审核信息
        params.put("flowId",params.get("activityId"));
        params.put("approName","报名活动申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_ACT_QUIT);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/actEnd",method = {RequestMethod.POST})
    public @ResponseBody
    String actEnd(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        List<Map<String,String>> persions=approService.getApproPrersion(params);
        String approLoginName= persions.size() > 0 ? persions.get(0).get("loginName") :"admin";
        params.put("approLoginName",approLoginName);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        //保存审核信息
        params.put("flowId",params.get("activityId"));
        params.put("approName","终止活动申请");
        params.put("approType",ConfigFactory.APPRO_TYPE_ACT_DEL);
        int isHaveAppro=approService.getIsHaveAppro(params);
        if(isHaveAppro>0){
            ModelMap modelMap1 = new ModelMap();
            modelMap1.put("status", "0");
            modelMap1.put("info", "已有申请等待审批中,请勿重复申请");
            return JSONObject.toJSON(modelMap1).toString();
        }
        approService.saveAppro(params);
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "申请已提交");
        return JSONObject.toJSON(modelMap1).toString();
    }
    @RequestMapping(value="/list",method = {RequestMethod.GET})
    public @ResponseBody
    List listForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> params) throws Exception {
        //查询申请,管理员可以看到所有,并且可以审核所有的
        //社团管理员,只能看到自己申请的和自己审核的
        //普通角色,只能看到自己申请的
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        params.put("loginName",login_name);
        params.put("loginId",login_id);
        String role_id=loginResponsitory.getRoleIdbyLoginName(login_name);
        params.put("roleId",role_id);

        if(!role_id.equals(ConfigFactory.ROLE_CODE_ADMIN)){
            params.put("approLoginName",login_name);
        }else if(!role_id.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)){
            params.put("createLoginName",login_name);
        }

        Page page=null;
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return approService.findAll(params,page);
    }

    @RequestMapping(value="/list",method = {RequestMethod.POST})
    public @ResponseBody
    List list(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        //查询申请,管理员可以看到所有,并且可以审核所有的
        //社团管理员,只能看到自己申请的和自己审核的
        //普通角色,只能看到自己申请的
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        params.put("loginName",login_name);
        params.put("loginId",login_id);
        String role_id=loginResponsitory.getRoleIdbyLoginName(login_name);
        params.put("roleId",role_id);
        if(!role_id.equals(ConfigFactory.ROLE_CODE_ADMIN)){
            params.put("approLoginName",login_name);
        }else if(!role_id.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)){
            params.put("createLoginName",login_name);
        }
        Page page=null;
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return approService.findAll(params,page);
    }


    /**
     * use for : 处理所有审批,最复杂的业务吧
     *@author zoukh
     *@Created in:  2020/4/15 3:40
     *@Modified By:
     *@version 1.0
     *@used in: ApproController
     */
    @RequestMapping(value="/deal",method = {RequestMethod.POST})
    public @ResponseBody
    String deal(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        //params.put("loginName",login_name);
        //params.put("loginId",login_id);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);

        //保存审核信息
        ApproEntity approEntity=approResponsitory.findById(params.get("approId").toString()).get();
        String approStatus=params.get("approStatus").toString();
        approEntity.setApproStatus(approStatus);
        approEntity.setApproDate(dateNowStr);
        approResponsitory.save(approEntity);
        String approType=approEntity.getApproType();
        String fllowId=approEntity.getFlowId();
        String create_login_name=approEntity.getCreateLoginName();
        String create_login_id = memberResponsitory.getIdByLoginName(create_login_name);

        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_COMM_ADD)){
            CommunityEntity communityEntity=communityResponsitory.findById(fllowId).get();
            communityEntity.setStatus("1");
            communityResponsitory.save(communityEntity);
        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_COMM_DEL)){
            CommunityEntity communityEntity=communityResponsitory.findById(fllowId).get();
            communityEntity.setStatus("0");
            communityResponsitory.save(communityEntity);
        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_COMM_SIGN)){
            PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
            primaryKeyLoginCommID.setCommId(fllowId);
            primaryKeyLoginCommID.setLoginId(create_login_id);
            CommMemberEntity commMemberEntity=commMemberResponsitory.findById(primaryKeyLoginCommID).get();
            commMemberEntity.setStatus("1");
            commMemberResponsitory.save(commMemberEntity);
            communityService.memberAdd(params);

        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_COMM_QUIT)){
            PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
            primaryKeyLoginCommID.setCommId(fllowId);
            primaryKeyLoginCommID.setLoginId(create_login_id);
            CommMemberEntity commMemberEntity=commMemberResponsitory.findById(primaryKeyLoginCommID).get();
            commMemberEntity.setStatus("0");
            commMemberResponsitory.save(commMemberEntity);
            communityService.memberAdd(params);
        }
        //
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_ACT_ADD)){
            ActivityEntity activityEntity=activityResponsitory.findById(fllowId).get();
            activityEntity.setStatus("1");
            activityResponsitory.save(activityEntity);
        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_ACT_DEL)){
            ActivityEntity activityEntity=activityResponsitory.findById(fllowId).get();
            activityEntity.setStatus("0");
            activityResponsitory.save(activityEntity);
        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_ACT_SIGN)){
            PrimaryKeyLoginActivityID primaryKeyLoginActivityID=new PrimaryKeyLoginActivityID();
            primaryKeyLoginActivityID.setActivityId(fllowId);
            primaryKeyLoginActivityID.setLoginId(create_login_id);
            ActMemberEntity actMemberEntity=actMemberResponsitory.findById(primaryKeyLoginActivityID).get();
            actMemberEntity.setStatus("1");
            actMemberResponsitory.save(actMemberEntity);
            activityService.sign(params);

        }
        if("1".equals(approStatus)&&approType.equals(ConfigFactory.APPRO_TYPE_ACT_QUIT)){
            PrimaryKeyLoginActivityID primaryKeyLoginActivityID=new PrimaryKeyLoginActivityID();
            primaryKeyLoginActivityID.setActivityId(fllowId);
            primaryKeyLoginActivityID.setLoginId(create_login_id);
            ActMemberEntity actMemberEntity=actMemberResponsitory.findById(primaryKeyLoginActivityID).get();
            actMemberEntity.setStatus("0");
            actMemberResponsitory.save(actMemberEntity);
            activityService.sign(params);
        }

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "审核已结束");
        return JSONObject.toJSON(modelMap1).toString();
    }

}
