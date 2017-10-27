package vm.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@RequestMapping(value="/context_path")
	@ResponseBody
	public void getServletContextPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String contextPath = request.getServletContext().getContextPath();
		PrintWriter out = response.getWriter();
		out.write(contextPath);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/messages")
	@ResponseBody
	public void messages(String msgKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (msgKey == null || "".equals(msgKey.trim())) {
			out.write("");
		}
		if ("login.faild.password.error".equals(msgKey)) {
			out.write("用户名或密码错误！");
		}
		if ("login.faild.system.error".equals(msgKey)) {
			out.write("登录失败，系统错误！");
		}
		if ("login.faild.nouser".equals(msgKey)) {
			out.write("用户不存在！");
		}
		out.flush();
		out.close();
	}

}
