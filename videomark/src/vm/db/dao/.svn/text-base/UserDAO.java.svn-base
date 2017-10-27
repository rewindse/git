package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import vm.db.common.DBConn;
import vm.db.po.User;

/**
 * @author youzi
 *
 */
public class UserDAO {
	private DBConn dbConn = null;

	public UserDAO() {
		dbConn = new DBConn();
	}

	public boolean insert(User user) {
		boolean executeResultFlag = false;
		String sql = "insert into user values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, user.getUserID());
			ps.setLong(2, user.getCtime());
			ps.setLong(3, user.getUtime());
			ps.setString(4, user.getNote());
			ps.setInt(5, 1);
			ps.setString(6, user.getUsername());
			ps.setString(7, user.getPassword());
			ps.setString(8, user.getRealname());
			ps.setString(9, user.getEmail());
			ps.setString(10, user.getTel());
			ps.setString(11, user.getCollage());
			ps.setString(12, user.getRoleID());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}

	public boolean deleteByUserName(String userName) {
		if (userName == null || "".equals(userName)) {
			return false;
		}
		long a =System.currentTimeMillis();
		User user = this.findByUserID(userName);
		user.setFlag(0); // update the field value of flag is 0
		user.setUtime(a);
		return this.updateByUserName(user);
	}

	// update methods
	public boolean updateByUserID(String userId, User user) {
		String sql = "update user set ctime = ?, utime = ?, note = ?, "
				+ "flag = ?, username = ?, password = ?, "
				+ "realname = ?, email = ?, tel = ?, collage = ?, "
				+ "roleID = ? where userID = ? and flag <> 0";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setLong(1, user.getCtime());
			ps.setLong(2, System.currentTimeMillis());
			ps.setString(3, user.getNote());
			ps.setInt(4, user.getFlag());
			ps.setString(5, user.getUsername());
			ps.setString(6, user.getPassword());
			ps.setString(7, user.getRealname());
			ps.setString(8, user.getEmail());
			ps.setString(9, user.getTel());
			ps.setString(10, user.getCollage());
			ps.setString(11, user.getRoleID());
			ps.setString(12, user.getUserID());
			isUpdated = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	public boolean updateByUserName(User user) {
		String sql = "update user set ctime = ?, utime = ?, note = ?, "
				+ "flag = ?,  password = ?, "
				+ "realname = ?, email = ?, tel = ?, collage = ? "
				+ " where username = ? and flag <> 0";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setLong(1, user.getCtime());
			ps.setLong(2, System.currentTimeMillis());
			ps.setString(3, user.getNote());
			ps.setInt(4, 1);
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getRealname());
			ps.setString(7, user.getEmail());
			ps.setString(8, user.getTel());
			ps.setString(9, user.getCollage());
			ps.setString(10, user.getUsername());
			isUpdated = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	// find methods
	public User findByUserID(String userID) {
		String sql = null;
		 sql = "select * from user where userID = ? and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			User user = new User();
			if (rs.next()) {
				String uid = rs.getString("userID");
				user.setUserID(uid == null ? "" : uid);
				user.setCtime(rs.getLong("ctime"));
				user.setUtime(rs.getLong("utime"));
				user.setNote(rs.getString("note"));
				user.setFlag(rs.getInt("flag"));
				String username = rs.getString("username");
				user.setUsername(username == null ? "" : username);
				String password = rs.getString("password");
				user.setPassword(password == null ? "" : password);
				String realname = rs.getString("realname");
				user.setRealname(realname == null ? "" : realname);
				String email = rs.getString("email");
				user.setEmail(email == null ? "" : email);
				String tel = rs.getString("tel");
				user.setTel(tel == null ? "" : tel);
				String collage = rs.getString("collage");
				user.setCollage(collage == null ? "" : collage);
				String roleID = rs.getString("roleID");
				user.setRoleID(roleID == null ? "" : roleID);
			}
			this.dbConn.release();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> findByUserName(String userName) {
		String sql = new String();
		if (userName == null || "".equals(userName)) {
			sql = "select * from user where flag <> 0;";
		} else {
			sql = "select * from user where userName = ? and flag <> 0;";
		}
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			if (!(userName == null || "".equals(userName))) {
				ps.setString(1, userName);
			}
			ResultSet rs = ps.executeQuery();
			List<User> list = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				String uid = rs.getString("userID");
				user.setUserID(uid == null ? "" : uid);
				user.setCtime(rs.getLong("ctime"));
				user.setUtime(rs.getLong("utime"));
				user.setNote(rs.getString("note"));
				user.setFlag(rs.getInt("flag"));
				String username = rs.getString("username");
				user.setUsername(username == null ? "" : username);
				String password = rs.getString("password");
				user.setPassword(password == null ? "" : password);
				String realname = rs.getString("realname");
				user.setRealname(realname == null ? "" : realname);
				String email = rs.getString("email");
				user.setEmail(email == null ? "" : email);
				String tel = rs.getString("tel");
				user.setTel(tel == null ? "" : tel);
				String collage = rs.getString("collage");
				user.setCollage(collage == null ? "" : collage);
				String roleID = rs.getString("roleID");
				user.setRoleID(roleID == null ? "" : roleID);
				list.add(user);
			}
			this.dbConn.release();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//通过名字找id
	public String findUserIDByUserName(String userName) {
		String uid = new String();
		String sql = new String();
		if (userName == null || "".equals(userName)) {
			sql = "select userID from user where flag <> 0;";
		} else {
			sql = "select userID from user where userName = ? and flag <> 0;";
		}
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			if (!(userName == null || "".equals(userName))) {
				ps.setString(1, userName);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				 uid = rs.getString("userID");	
			}
			this.dbConn.release();
			return uid;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findUserIdByRealName(String realName) {
		String uid = new String();
		String sql = "select userID from user where realName = ? and flag <> 0;";
	
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			if (!(realName == null || "".equals(realName))) {
				ps.setString(1, realName);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				 uid = rs.getString("userID");	
			}
			this.dbConn.release();
			return uid;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User> findAll() {
		String sql = "select * from user where user.flag <> 0;";
		List<User> users = new Vector<User>();
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getString(1));
				user.setCtime(rs.getLong(2));
				user.setUtime(rs.getLong(3));
				user.setNote(rs.getString(4));
				user.setFlag(rs.getInt(5));
				user.setUsername(rs.getString(6));
				user.setPassword(rs.getString(7));
				user.setRealname(rs.getString(8));
				user.setEmail(rs.getString(9));
				user.setTel(rs.getString(10));
				user.setCollage(rs.getString(11));
				user.setRoleID(rs.getString(12));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	// unit test main method
	public static void main(String[] args) {
		/*UserDAO tDAO = new UserDAO();
		User dl = new User();
		dl.setCollage("111");
		dl.setEmail("1221");
		dl.setCtime(2323);
		dl.setFlag(1);
		dl.setNote("23");
		dl.setPassword("121");
		dl.setRealname("端到端");
		dl.setRoleID("1");
		dl.setTel("2323");
		dl.setUserID("23232323232323");
		dl.setUsername("asdasdasdas");
		dl.setUtime(232323232);
		tDAO.insert(dl);
		String a= tDAO.findUserIDByUserName("asdasdasdas");
		System.out.println(a);*/

	}

}
