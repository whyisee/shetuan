package com.shetuan.web;

import com.shetuan.entity.CommunityEntity;
import com.shetuan.responsitory.CommunityResponsitory;
import com.shetuan.responsitory.ConfigFactory;
import com.shetuan.util.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
public class ApproController extends BaseController{


    @Autowired
    private CommunityResponsitory communityResponsitory;

    /**
     * use for : 申请创建社团
     *@author zoukh
     *@Created in:  2020/4/11 21:50
     *@Modified By:
     *@version 1.0
     *@used in: ApproController
     */
    @RequestMapping("commCreate")
    public @ResponseBody
    String commCreate(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String,Object> params, HttpSession session){
        String rs="";
        CommunityEntity comm = new CommunityEntity();
        comm = ParamUtils.mapToBean(params,comm);
        comm.setStatus(ConfigFactory.COMM_APPRO_STATUS);
        //保存社团信息
        communityResponsitory.save(comm);


        return rs;
    }
}
