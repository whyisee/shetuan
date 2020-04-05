package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.*;
import com.shetuan.responsitory.*;
import com.shetuan.service.CommunityService;
import com.shetuan.util.JSONUtil;
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
                System.out.println("Test--------0:26--->:"+jsonArray2);

                jsonArray.add(jsonObject);
            }
        }
        //System.out.println("Test--------0:03--->:"+jsonArray);
        return jsonArray.toJSONString();
    }

    /**
     * use for : 所有社团查询
     *@Created in:  2020/4/3 19:32
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping("/findAll")
    public @ResponseBody
    List<CommunityEntity> findAll(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        return communityService.findAllMoreCondition(params,null);
    }

    /**
     * use for : 查询社团展示的成员信息
     *@author zoukh
     *@Created in:  2020/4/3 21:29
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping("/findMemberShow")
    public @ResponseBody
    List findMemberShow(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        params.put("comm_worker_id",ConfigFactory.COMM_WORKER_ID);
        return communityService.findMemberByComm(params,null);
    }

    /**
     * use for : 查询社团所有的成员信息
     *@author zoukh
     *@Created in:  2020/4/3 21:29
     *@Modified By:
     *@version 1.0
     *@used in: CommunityController
     */
    @RequestMapping("/findMemberAll")
    public @ResponseBody
    List<MemberEntity> findMemberAll(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        return communityService.findMemberByComm(params,null);
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
        //再判断是否有申请通过记录
        String approStatus=approResponsitory.getApproStatusByID(params.get("approId").toString());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)) {
            communityResponsitory.deleteById(params.get("commId").toString());
        }
        return rs;
    }

    @RequestMapping("/commAdd")
    public @ResponseBody
    String commAdd(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        String rs="";
        CommunityEntity comm = new CommunityEntity();
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());

        //再判断是否有申请通过记录
        String approStatus=approResponsitory.getApproStatusByID(params.get("approId").toString());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            comm = ParamUtils.mapToBean(params,comm);
            communityResponsitory.save(comm);
        }
        return rs;
    }

    @RequestMapping("/commUpdate")
    public @ResponseBody
    String commUpdate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        String rs="";
        CommunityEntity comm = new CommunityEntity();
        //先判断下账号是否有权限,管理员或者社团管理员可以修改
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||roleId.equals(ConfigFactory.ROLE_CODE_COMM_ADMIN)) {
            comm = ParamUtils.mapToBean(params,comm);
            communityResponsitory.save(comm);
        }
        return rs;
    }
    @RequestMapping("/memberAdd")
    public @ResponseBody
    String memberAdd(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        String rs="";
        MemberEntity member = new MemberEntity();
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());

        //再判断是否有申请通过记录
        String approStatus=approResponsitory.getApproStatusByID(params.get("approId").toString());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            communityService.memberAdd(params);
        }
        return rs;
    }

    @RequestMapping("/memberDel")
    public @ResponseBody
    String memberDel(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params ,HttpSession session) throws Exception {
        String rs="";
        MemberEntity member = new MemberEntity();
        //先判断下账号是否有权限
        LoginEntity login=(LoginEntity)session.getAttribute("login");
        String roleId=loginResponsitory.getRoleIdbyLoginName(login.getLoginName());

        //再判断是否有申请通过记录
        String approStatus=approResponsitory.getApproStatusByID(params.get("approId").toString());
        if(roleId.equals(ConfigFactory.ROLE_CODE_ADMIN)||approStatus.equals(ConfigFactory.APPRO_STATUS)){
            communityService.memberDel(params);
        }
        return rs;
    }

    @RequestMapping("/memberInfo")
    public @ResponseBody
    CommMemberEntity memberInfo(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params , HttpSession session) throws Exception {
        String rs="";
        PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(params.get("commId").toString());
        primaryKeyLoginCommID.setLoginId(params.get("LoginId").toString());
        Optional <CommMemberEntity> commM=commMemberResponsitory.findById(primaryKeyLoginCommID);
        return commM.get();
    }

    @RequestMapping("/memberUpdate")
    public @ResponseBody
    CommMemberEntity memberUpdate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params , HttpSession session) throws Exception {
        String rs="";
        PrimaryKeyLoginCommID primaryKeyLoginCommID=new PrimaryKeyLoginCommID();
        primaryKeyLoginCommID.setCommId(params.get("commId").toString());
        primaryKeyLoginCommID.setLoginId(params.get("LoginId").toString());
        Optional <CommMemberEntity> commM=commMemberResponsitory.findById(primaryKeyLoginCommID);
        return commM.get();
    }
    
}