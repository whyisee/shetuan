package com.shetuan.web;

import com.shetuan.entity.ActivityEntity;
import com.shetuan.entity.LoginEntity;
import com.shetuan.responsitory.ActivityResponsitory;
import com.shetuan.responsitory.ApproResponsitory;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.responsitory.LoginResponsitory;
import com.shetuan.service.ActivityService;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * use for : 展示活动列表
     *@author zoukh
     *@Created in:  2020/4/4 15:31
     *@Modified By:
     *@version 1.0
     *@used in: ActivityController
     */
    @RequestMapping("/findAll")
    public @ResponseBody
    List<ActivityEntity> findAll(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        return activityService.findAllMoreCondition(params,null);
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
        String rs="";
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());

        //再判断是否有申请通过记录
        String approStatus=approResponsitory.getApproStatusByID(params.get("approId").toString());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            activityService.sign(params);
        }

        return rs;
    }

    @RequestMapping("/create")
    public @ResponseBody
    String create(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity= ParamUtils.mapToBean(params,activityEntity);
        activityResponsitory.save(activityEntity);
        return rs;
    }

    @RequestMapping("/update")
    public @ResponseBody
    String update(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity= ParamUtils.mapToBean(params,activityEntity);
        activityResponsitory.save(activityEntity);
        return rs;
    }

    @RequestMapping("/info")
    public @ResponseBody
    ActivityEntity info(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        return activityResponsitory.getOne(params.get("activity_id").toString());
    }

    @RequestMapping("/end")
    public @ResponseBody
    String end(ModelMap modelMap, HttpServletRequest request,@RequestBody Map<String,Object> params) throws Exception {
        String rs="";
        //先查出,修改状态后保存
        ActivityEntity activityEntity = activityResponsitory.getOne(params.get("activity_id").toString());
        activityEntity.setStatus("0");
        activityResponsitory.save(activityEntity);
        return rs;
    }

}
