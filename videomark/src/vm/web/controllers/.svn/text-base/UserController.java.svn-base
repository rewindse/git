package vm.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vm.db.po.User;
import vm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService = new UserService();
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<User> users = this.userService.search(null, null, null, username, null, null, null, null, null);
		if (users == null) {
			users = new ArrayList<User>();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if (users.size() == 0) {
			out.write("{\"msg\":\"login.faild.nouser\"}");
		} else if (users.size() == 1) {
			User user = users.get(0);
			if (user.getPassword().equals(password)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				out.write("{\"msg\":\"login.success\", \"userId\":\"" + user.getUserID() + "\"}");
			} else {
				out.write("{\"msg\":\"login.faild.password.error\"}");
			}
		} else {
			out.write("{\"msg\" : \"login.faild.system.error\"}");
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		if (session != null) {
			session.invalidate();
		}
	}
	
	@RequestMapping(value="/listAll")
	@ResponseBody
	public void userList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();		
		String userList = userService.getAllUserToString();
		out.write(userList);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/register")
	@ResponseBody
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String email = request.getParameter("email");
		String realName = request.getParameter("realname");
		String tel = request.getParameter("tel");
		String collage = request.getParameter("collage");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		boolean insertFlag=false;
		insertFlag=userService.addUSer(userName, passWord, email, realName, tel, collage);

		out.write( "[{\"flag\":\"" + insertFlag + "\"}]");
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/unregister")
	@ResponseBody
	public void unregister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userID=request.getParameter("userID");
		boolean deleteFlag=false;
		deleteFlag=userService.deleteUser(userID);
		PrintWriter out = response.getWriter();
		out.write( "[{\"flag\":\"" + deleteFlag + "\"}]");
		out.flush();
		out.close();
	} 
	
	@RequestMapping(value="/update")
	@ResponseBody
	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		String email = request.getParameter("email");
		String realName = request.getParameter("realname");
		String tel = request.getParameter("tel");
		String collage = request.getParameter("collage");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		boolean updateFlag=false;
		
		User user= new User(null, 0, 0, null, 0, userName, passWord, realName, email, tel, collage, null);
		updateFlag=userService.updateUser(user);
		out.write("[{\"flag\":\"" + updateFlag + "\"}]");
		out.flush();
	}
	
	@RequestMapping(value="/current_user")
	@ResponseBody
	public void currentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(JSONObject.fromObject(user).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/getUser")
	@ResponseBody
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("user_name");
		String userList = userService.findUserToString(userName);
		
		out.write(userList);
		out.flush();
		out.close();
	}
}
