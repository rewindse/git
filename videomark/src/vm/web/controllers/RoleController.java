package vm.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;

import vm.db.po.Role;
import vm.services.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	private RoleService roleService = new RoleService();
	
	@RequestMapping(value="/list")
	@ResponseBody
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String roleId = request.getParameter("role_id");
		List<Role> roles = this.roleService.search(roleId, null, null, null);
		if (roles == null) {
			roles = new Vector<Role>();
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(JSONArray.fromObject(roles).toString());
		out.flush();
		out.close();
	}
}
