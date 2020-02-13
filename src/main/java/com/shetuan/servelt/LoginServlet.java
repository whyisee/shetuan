package com.shetuan.servelt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.shetuan.bean.Login;
import com.shetuan.bean.Member;
import com.shetuan.bean.User;
import com.shetuan.dao.LoginDao;
import com.shetuan.dao.MemberDao;
import com.shetuan.util.JSONUtil;

/**
 * 登录的servlet
 * 
 * @author Administrator
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//接受前台发送的数据，通过流的形式接收数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));	//将数据使用流进行传递
		StringBuffer strb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {	//遍历数据
			strb = strb.append(line);			//数据暂存StringBuffer
		}
		System.out.println("接受数据为"+strb);
		Login o= JSONUtil.toBean(strb.toString(), Login.class);
		System.out.println("接受数据为22222"+o);

		LoginDao logindao = new LoginDao();
		MemberDao memberdao = new MemberDao();
		Login login = logindao.login(o.getLoginName(), o.getLoginPass());
		
		//System.out.println("Test--------21:10--->:"+login);
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(JSONUtil.toJson(login));//通过流的形式响应
		out.close();

		return;

	}

}
