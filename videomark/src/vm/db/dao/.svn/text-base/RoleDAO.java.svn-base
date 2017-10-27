package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import vm.db.common.DBConn;
import vm.db.po.Role;
import vm.db.po.User;

public class RoleDAO {
	/**
	 * 目前只有两个角色：管理员和标注员。
	 * 不需要单独提供DAO接口。只在需要的时候往数据库中插入记录即可。
	 * 
	 * insert into role values(uuid(), ctime, utime, "", 1, "administrator");
	 * insert into role values(uuid(), ctime, utime, "", 1, "marker");
	 */
	private DBConn dbConn = null;

	public RoleDAO() {
		dbConn = new DBConn();
	}
	public Role findRolebyUserID(String userID){
		UserDAO dao = new UserDAO();
		User user = new User();
		user = dao.findByUserID(userID);
		String roleid = user.getRoleID();
		String sql = null;
		sql = "select * from role where roleID = ? and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, roleid);
			ResultSet rs = ps.executeQuery();
			Role role = new Role();
			if (rs.next()) {
				String rid = rs.getString("roleID");
				role.setRoleID(rid == null ? "" : rid);
				role.setCtime(rs.getLong("ctime"));
				role.setUtime(rs.getLong("utime"));
				role.setNote(rs.getString("note"));
				role.setFlag(rs.getInt("flag"));
				String rolename = rs.getString("rolename");
				role.setRolename(rolename == null ? "" : rolename);
			}
			this.dbConn.release();
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public List<Role> findAll() {
		String sql = "select * from role where role.flag <> 0;";
		List<Role> roles = new Vector<Role>();
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setRoleID(rs.getString("roleID"));
				role.setCtime(rs.getLong("ctime"));
				role.setUtime(rs.getLong("utime"));
				role.setFlag(rs.getInt("flag"));
				role.setNote(rs.getString("note"));
				role.setRolename(rs.getString("rolename"));
				roles.add(role);
			}
			rs.close();
			this.dbConn.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}
	
	public static void main(String[] args) {
		RoleDAO d = new RoleDAO();
		List<Role> roles = d.findAll();
		System.out.println(roles.size());
	}
}

