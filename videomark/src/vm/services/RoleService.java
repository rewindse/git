package vm.services;

import java.util.List;
import java.util.Vector;

import vm.db.dao.RoleDAO;
import vm.db.po.Role;

public class RoleService {
	private RoleDAO roleDao = new RoleDAO();
	
	public List<Role> search(String roleId, String roleName, Long cStart, Long cEnd) {
		List<Role> allRoles = this.roleDao.findAll();
		
		List<Role> roles = new Vector<Role>();
		for (Role role : allRoles) {
			if (((roleId == null) ? true : role.getRoleID().equals(roleId))
					&& (roleName == null) ? true : role.getRolename().equals(roleName)
					&& (this.isCtime(cStart, cEnd, role.getCtime()))) {
				roles.add(role);
			}
		}
		return roles;
	}
	
	private boolean isCtime(Long cStart, Long cEnd, Long cTime) {
		if (cStart == null && cEnd != null && cTime <= cEnd) {
			return true;
		}
		if (cEnd == null && cStart != null && cTime >= cStart) {
			return true;
		}
		if (cStart != null && cEnd != null && cTime <= cEnd && cTime >= cStart) {
			return true;
		}
		if (cStart == null && cEnd == null) {
			return true;
		}
		return false;
	}
}
