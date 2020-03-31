package com.shetuan.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shetuan.entity.CommunityClassEntity;
import com.shetuan.entity.CommunityEntity;
import com.shetuan.entity.MemberEntity;
import com.shetuan.responsitory.CommunityClassResponsitory;
import com.shetuan.responsitory.CommunityResponsitory;
import com.shetuan.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}