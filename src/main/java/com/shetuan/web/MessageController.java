package com.shetuan.web;

import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.LoginEntity;
import com.shetuan.entity.MessageEntity;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.responsitory.ManageSqlTools;
import com.shetuan.responsitory.MessageResponsitory;
import com.shetuan.service.MessageService;
import com.shetuan.util.BeanUtils;
import com.shetuan.util.JSONUtil;
import com.shetuan.util.Page;
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
 * Created in:  2020/4/15 6:14
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageResponsitory messageResponsitory;
    @Autowired
    private ManageSqlTools manageSqlTools;

    @RequestMapping(value="/add",method = {RequestMethod.GET})
    public @ResponseBody String addForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params){
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        MessageEntity msg=new MessageEntity();
        String msg_id=manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID);

        msg= BeanUtils.mapToBean(params,msg);
        msg.setMsgDate(dateNowStr);
        msg.setLoginName(login_name);
        msg.setStatus("1");
        msg.setMsgId(msg_id);

        messageResponsitory.save(msg);
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }
    @RequestMapping(value="/add",method = {RequestMethod.POST})
    public @ResponseBody String add(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params){
        HttpSession session = request.getSession();
        LoginEntity login = (LoginEntity) session.getAttribute("login");
        String login_name = login.getLoginName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String msg_id=manageSqlTools.getSeqId(ConfigFactory.SEQ_APPRO_ID);
        Date d = new Date();
        String dateNowStr = sdf.format(d);
        MessageEntity msg=new MessageEntity();
        msg= BeanUtils.mapToBean(params,msg);
        msg.setMsgDate(dateNowStr);
        msg.setLoginName(login_name);

        msg.setStatus("1");
        msg.setMsgId(msg_id);
        //System.out.println("Test--------6:56--->:"+msg);
        messageResponsitory.save(msg);
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/list",method = {RequestMethod.GET})
    public @ResponseBody List<MessageEntity> listForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params) throws Exception {
        Page page=null;
        //get请求不支持分页
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return messageService.list(params,page);
    }

    @RequestMapping(value="/list",method = {RequestMethod.POST})
    public @ResponseBody List<MessageEntity> list(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params) throws Exception {
        Page page=null;
        //get请求不支持分页
        if(null!=params.get("page")){
            page= JSONUtil.toBean((JSONObject.toJSONString(params.get("page"))), Page.class);
        }
        return messageService.list(params,page);
    }

    @RequestMapping(value="/del",method = {RequestMethod.GET})
    public @ResponseBody String delForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String,Object> params){

        MessageEntity msg=new MessageEntity();
        msg= BeanUtils.mapToBean(params,msg);
        messageResponsitory.updateDelById(params.get("msgId").toString());
        //msg.setStatus();
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }

    @RequestMapping(value="/del",method = {RequestMethod.POST})
    public @ResponseBody String del(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params){

        MessageEntity msg=new MessageEntity();
        msg= BeanUtils.mapToBean(params,msg);
        messageResponsitory.updateDelById(params.get("msgId").toString());
        //msg.setStatus();
        ModelMap modelMap1 = new ModelMap();
        modelMap1.put("status", "1");
        modelMap1.put("info", "成功");
        return JSONObject.toJSON(modelMap1).toString();
    }
}
