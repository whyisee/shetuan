package com.shetuan.web;

import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.ActMemberEntity;
import com.shetuan.entity.ActivityEntity;
import com.shetuan.entity.LoginEntity;
import com.shetuan.responsitory.*;
import com.shetuan.service.ActivityService;
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

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:28
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Controller
@RequestMapping(value="/activity")
public class ActivityController extends BaseController {

    @Autowired
    private ActivityResponsitory activityResponsitory;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ApproResponsitory approResponsitory;

    @Autowired
    private LoginResponsitory loginResponsitory;

    @Autowired
    private ManageSqlTools manageSqlTools;

    @Autowired
    private MemberResponsitory memberResponsitory;

    @Autowired
    private ActMemberResponsitory actMemberResponsitory;

    /**
     * use for : 展示活动列表
     *@author zoukh
     *@Created in:  2020/4/4 15:31
     *@Modified By:
     *@version 1.0
     *@used in: ActivityController
     */
    @RequestMapping(value="/findAll",method = {RequestMethod.GET})
    public @ResponseBody
    List<ActivityEntity> findAllForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam Map<String,Object> params) throws Exception {
        Page page=null;
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return activityService.findAllMoreCondition(params,page);
    }
    @RequestMapping(value="/findAll",method = {RequestMethod.POST})
    public @ResponseBody
    List<ActivityEntity> findAll(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        Page page=null;
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return activityService.findAllMoreCondition(params,page);
    }

    /**
     * use for : 活动参与,审核通过时调用
     *@author zoukh
     *@Created in:  2020/4/4 16:29
     *@Modified By:
     *@version 1.0
     *@used in: ActivityController
     */
    @RequestMapping("/sign")
    public @ResponseBody
    String sign(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params , HttpSession session) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());
        String approStatus="0";
        //再判断是否有申请通过记录
        if(null!=params.get("approId")){
             approStatus = approResponsitory.getApproStatusByID(params.get("approId").toString());
        }
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            //保存活动人员信息
            ActMemberEntity actMemberEntity=new ActMemberEntity();
            actMemberEntity= BeanUtils.mapToBean(params,actMemberEntity);
            //actMemberEntity.setActivityId(manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID));
            actMemberEntity.setCreateDate(dateNowStr);
            actMemberEntity.setStatus("1");
            actMemberResponsitory.save(actMemberEntity);
            activityService.sign(params);
        }

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();    }

    @RequestMapping("/create")
    public @ResponseBody
    String create(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        String login_id = memberResponsitory.getIdByLoginName(login_name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        params.put("loginName",login_name);
        params.put("loginId",login_id);

        ActivityEntity activityEntity = new ActivityEntity();
        String activityId=manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID);

        activityEntity= BeanUtils.mapToBean(params,activityEntity);
        activityEntity.setActivityId(activityId);
        activityEntity.setActivityPersionNow("1");
        activityEntity.setCreatePersionName(login_name);
        activityEntity.setCreateDate(dateNowStr);
        activityEntity.setStatus(ConfigFactory.COMM_APPRO_STATUS);
        activityResponsitory.save(activityEntity);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping("/update")
    public @ResponseBody
    String update(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity= BeanUtils.mapToBean(params,activityEntity);
        ActivityEntity activityEntityOld=activityResponsitory.findById(params.get("activityId").toString()).get();
        activityEntity=BeanUtils.beanMarge(activityEntity,activityEntityOld);
        activityResponsitory.save(activityEntity);

        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/info",method = {RequestMethod.GET})
    public @ResponseBody
    ActivityEntity infoForGet(ModelMap modelMap, HttpServletRequest request,@RequestParam Map<String,Object> params) throws Exception {
        String rs="";
        return activityResponsitory.getOne(params.get("activityId").toString());
    }

    @RequestMapping(value="/info",method = {RequestMethod.POST})
    public @ResponseBody
    ActivityEntity info(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        return activityResponsitory.getOne(params.get("activityId").toString());
    }

    @RequestMapping("/end")
    public @ResponseBody
    String end(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        //先查出,修改状态后保存
        ActivityEntity activityEntity = activityResponsitory.getOne(params.get("activityId").toString());
        activityEntity.setStatus("0");
        activityResponsitory.save(activityEntity);
        return rs;
    }

}
