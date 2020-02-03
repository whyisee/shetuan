package com.shetuan.servelt;

import com.alibaba.fastjson.JSON;
import com.shetuan.bean.User;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/2 21:06
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class Jsontest  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受前台发送的数据，通过流的形式接收数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));	//将数据使用流进行传递
        StringBuffer strb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {	//遍历数据
            strb = strb.append(line);			//数据暂存StringBuffer
        }

        System.out.println("接受数据为"+strb);
        //JSONObject s = new JSONObject();
        User o= JSON.parseObject(strb.toString(), User.class);
        //只设置响应编码格式，前台接收到的是一个字串
        //  response.setCharacterEncoding("utf-8");
        //设置之后前端接收到的是一个对象
        response.setContentType("application/json;charset=UTF-8");
        //System.out.println(o);
        PrintWriter out = response.getWriter();

        out.write(JSON.toJSONString(o));//通过流的形式响应
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
