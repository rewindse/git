/**
 * 
 */
package vm.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.Map.Entry;

import vm.db.dao.RoleDAO;
import vm.db.dao.UserDAO;
import vm.db.po.Role;
import vm.db.po.User;

/**
 * @author Administrator
 *
 */
public class UserService {

	private UserDAO userDao = new UserDAO();

	public List<User> search(String userId, Long cStart, Long cEnd,
			String userName, String password, String realName, String email,
			String tel, String collage) {
		List<User> allUsers = this.userDao.findAll();

		List<User> users = new Vector<User>();
		for (User user : allUsers) {
			if (((userId == null) ? true : user.getUserID().equals(userId))
					&& (this.isCtime(cStart, cEnd, user.getCtime()))
					&& ((userName == null) ? true : user.getUsername().equals(
							userName))
					&& ((password == null) ? true : user.getPassword().equals(
							password))
					&& ((realName == null) ? true : user.getRealname().equals(
							realName))
					&& ((email == null) ? true : user.getEmail().equals(email))
					&& ((collage == null) ? true : user.getCollage().equals(
							collage))) {
				users.add(user);
			}
		}
		return users;
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

	public boolean addUSer(String userName, String passWord, String email,
			String realName, String tel, String collage) {
		boolean isAdd = userDao.findByUserName(userName).isEmpty();
		if (isAdd) {
			User user = new User();
			long a = System.currentTimeMillis();
			user.setUserID(UUID.randomUUID().toString());
			user.setUsername(userName);
			user.setPassword(passWord);
			user.setEmail(email);
			user.setRealname(realName);
			user.setCollage(collage);
			user.setTel(tel);
			user.setCtime(a);
			user.setUtime(a);
			user.setNote("");
			user.setRoleID("B");
			isAdd = userDao.insert(user);
			if (isAdd) {
				return true;
			}
		}
		return false;

	}

	public List<Map<Object, Object>> getAllUser() {
		List<Map<Object, Object>> json = new Vector<Map<Object, Object>>();
		List<User> userList = new ArrayList<User>();
		userList = userDao.findByUserName("");
		for (int i = 0; i < userList.size(); i++) {
			Map<Object, Object> user = new Hashtable<Object, Object>();
			user.put("userid", userList.get(i).getUserID());
			long a = userList.get(i).getCtime();
			Date nowTime = new Date(a);
			RoleDAO roleDao = new RoleDAO();
			Role role = roleDao.findRolebyUserID(userList.get(i).getUserID());
			String rolename = new String();
			rolename = role.getRolename();

			SimpleDateFormat sdFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			String retStrFormatNowDate = sdFormatter.format(nowTime);
			user.put("ctime", retStrFormatNowDate);
			user.put("utime", userList.get(i).getUtime());
			user.put("username", userList.get(i).getUsername());
			user.put("password", userList.get(i).getPassword());
			user.put("email", userList.get(i).getEmail());
			user.put("realname", userList.get(i).getRealname());
			user.put("tel", userList.get(i).getTel());
			user.put("collage", userList.get(i).getCollage());
			user.put("roleid", rolename);
			json.add(user);
		}

		return json;
	}

	public String getAllUserToString() {
		List<Map<Object, Object>> userList = this.getAllUser();
		StringBuffer sbuf = new StringBuffer();
		if (userList != null) {
			sbuf.append("[");
			for (Iterator<Map<Object, Object>> iter = userList.iterator(); iter
					.hasNext();) {
				Map<Object, Object> user = iter.next();
				sbuf.append("{");
				for (Iterator<Entry<Object, Object>> uIter = user.entrySet()
						.iterator(); uIter.hasNext();) {
					Entry<Object, Object> entry = uIter.next();
					sbuf.append("\"" + entry.getKey() + "\":\""
							+ entry.getValue() + "\"");
					if (uIter.hasNext()) {
						sbuf.append(",");
					}
				}
				sbuf.append("}");
				if (iter.hasNext()) {
					sbuf.append(",");
				}
			}
			sbuf.append("]");

		}
		return sbuf.toString();
	}

	public boolean updateUser(User user) {
		if ("".equals(user.getPassword()) || "".equals(user.getUsername())) {
			return false;
		}
		User olduser = new User();
		olduser = userDao.findByUserName(user.getUsername()).get(0);
		user.setUserID(olduser.getUserID());
		user.setCtime(olduser.getCtime());
		long a = System.currentTimeMillis();
		user.setUtime(a);
		user.setNote(olduser.getNote());
		user.setRoleID("B");

		return userDao.updateByUserName(user);
	}

	public boolean deleteUser(String userID) {
		if (userID == null || "".equals(userID)) {
			return false;
		}
		User user = userDao.findByUserID(userID);
		user.setFlag(0); // update the field value of flag is 0
		return userDao.updateByUserID(userID, user);
	}

	public List<User> findUser(String userName) {
		List<User> userList = new ArrayList<User>();
		if (userName == null || "".equals(userName)) {
			return userList;
		}

		userList = userDao.findByUserName(userName);
		return userList;
	}

	public String findUserToString(String userName) {
		List<User> userList = this.findUser(userName);
		String json = "";
		if (userList.size() <= 0) {
			userList = null;
		} else {
			json = "[";
			for (int i = 0; i < userList.size(); i++) {
				json += "{\"username\":\"" + userList.get(i).getUsername()
						+ "\",";
				json += "\"email\":\"" + userList.get(i).getEmail() + "\",";
				json += "\"realname\":\"" + userList.get(i).getRealname()
						+ "\",";
				json += "\"tel\":\"" + userList.get(i).getTel() + "\",";
				json += "\"collage\":\"" + userList.get(i).getCollage() + "\"";
				if (i == userList.size() - 1) {
					json += "}";
				} else {
					json += "},";
				}
			}
			json += "]";
		}
		return json;
	}

	public static void main(String[] args) {
		UserService s = new UserService();
		List<User> users = s.search(null, null, null, "lifang", "56", "李放",
				null, null, null);
		System.out.println(users.size());
	}

}
