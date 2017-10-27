package vm.web.handlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import vm.db.dao.CacheTaskDAO;
import vm.db.dao.OriginalFileDAO;
import vm.db.dao.ResultFileDAO;
import vm.db.dao.RoleDAO;
import vm.db.dao.TaskDAO;
import vm.db.dao.TaskStatusDAO;
import vm.db.dao.UserDAO;
import vm.db.po.CacheTask;
import vm.db.po.OriginalFile;
import vm.db.po.ResultFile;
import vm.db.po.Task;
import vm.db.po.TaskStatus;
import vm.db.po.User;
import vm.util.Const;

@Controller
public class TaskHandler {
	TaskDAO taskDao = new TaskDAO();
	TaskStatusDAO tsDao = new TaskStatusDAO();
	RoleDAO roleDao = new RoleDAO();
	UserDAO userDao = new UserDAO();
	
	@RequestMapping("task.do")
	public String task(HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskID");
		List<User> userList = new ArrayList<User>();
		String userID = new String();
		String note = new String();
		String taskName = new String();
		if(taskId != null) {		
			List<Task> taskList = taskDao.findTask(taskId);
			Task task = new Task();
			if(taskList.size() > 0) {
				task = taskList.get(0);
				userID = task.getUserID();
				note = task.getNote();
				taskName = task.getTaskName();
			}	
		}
		userList = userDao.findByUserName("");
		request.setAttribute("userList", userList);
		request.setAttribute("userID", userID);
		request.setAttribute("note", note);
		request.setAttribute("taskname", taskName);
		request.setAttribute("taskID", taskId);
		return "task";
	}

	@RequestMapping("addorupdatetask.do") 
	public String addOrUpdateTask(HttpServletRequest request,
			HttpServletResponse response){
		String taskId = request.getParameter("taskID");
		if("".equals(taskId) || taskId ==null) {
			this.addTask(request, response);
		}else {
			this.updateTask(request, response);
		}
		return "task_list";
	}
	
	public void addTask(HttpServletRequest request,
			HttpServletResponse response) {
		String taskName = request.getParameter("taskName");
		String userID = request.getParameter("userID");
		String note = request.getParameter("note");
		Task task = new Task();
		List<TaskStatus> ts = new ArrayList<TaskStatus>();
		long cur = System.currentTimeMillis();
		String taskID = UUID.randomUUID().toString();
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		String origianlfileId = request.getParameter("originalfile");
		String[] originalFileID = new String[origianlfileId.split(";&").length - 1];
		if (!"".equals(origianlfileId)) {
			originalFileID = origianlfileId.split(";&");
		}
		if (userID == "empty" || "empty".equals(userID)) {
			userID = "";
		}
		task.setTaskID(taskID);
		task.setTaskName(taskName);
		task.setCtime(cur);
		task.setUtime(cur);
		task.setNote(note);
		task.setFlag(1);
		task.setUserID(userID);
		task.setResultTypeID("");

		ts = tsDao.findByStatusNname("coming");
		if (ts.size() > 0) {
			task.setStatusID(ts.get(0).getStatusID());
		} else {
			task.setStatusID("");
		}

		if (taskDao.insert(task)) {
			for (int i = 0; i < originalFileID.length; i++) {
				OriginalFile of = new OriginalFile();
				if (!"".equals(originalFileID[i])) {
					of = originalFileDao
							.findByOriginalFileID(originalFileID[i]);
					of.setTaskID(taskID);
					originalFileDao.updateByOriginalFileId(
							originalFileID[i], of);
				}
			}
		}

	}

	public void updateTask(HttpServletRequest request,
			HttpServletResponse response) {
		String taskID = request.getParameter("taskID");
		String taskName = request.getParameter("taskName");
		String userID = request.getParameter("userID");
		String note = request.getParameter("note");
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		String origianlfileId = request.getParameter("originalfile");
		String[] originalFileID = new String[origianlfileId.split(";&").length - 1];
		if (!"".equals(origianlfileId)) {
			originalFileID = origianlfileId.split(";&");
		}
		List<Task> taskList = new ArrayList<Task>();
		Task task = new Task();
		taskList = taskDao.findTask(taskID);
		task = taskList.get(0);
		task.setTaskName(taskName);

		if ("empty".equals(userID))
			userID = null;
		task.setUtime(System.currentTimeMillis());
		task.setUserID(userID);
		task.setNote(note);
		taskDao.updateTask(taskID, task);
		originalFileDao.deletAllTaskID(taskID);
		for (int i = 0; i < originalFileID.length; i++) {
			OriginalFile of = new OriginalFile();
			of = originalFileDao.findByOriginalFileID(originalFileID[i]);
			of.setTaskID(taskID);
			originalFileDao.updateByOriginalFileId(originalFileID[i], of);
		}
		this.checkTaskStatus(taskID);
	}
	
	@RequestMapping("checkRolePage.do")
	public void checkRolePage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User user = (User)request.getSession().getAttribute("user");
		String userid = new String();
		if("".equals(user) || user == null) {
			userid = null;
		}else {
			userid = user.getUserID();
		}
		boolean flag = false;
		if (checkRole(userid)) {
			flag = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		String json = JSONObject.fromObject(map).toString();
		response.setCharacterEncoding("UTF-8");
		response.flushBuffer();
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	@RequestMapping("selecttask.do")
	public void selectTask(HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {
		response.setCharacterEncoding("utf-8");
		User user = (User)request.getSession().getAttribute("user");
		String userID = new String();
		if("".equals(user) || user == null) {
			userID = null;
		}else {
			userID = user.getUserID();
		}
		PrintWriter out = response.getWriter();
		List<Task> taskList = new ArrayList<Task>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userRole = "marker";
		if (checkRole(userID)) {
			taskList = taskDao.findTask("");
			userRole = "administrator";
		} else {
			taskList = taskDao.findByUserID(userID);
		}
		String json = "[";
		for (int i = 0; i < taskList.size(); i++) {
			json += "{ \"taskID\"" + " : " + "\"" + taskList.get(i).getTaskID()
					+ "\",";
			json += "\"taskname\"" + " : " + "\""
					+ taskList.get(i).getTaskName() + "\",";
			json += "\"ctime\"" + " : " + "\""
					+ format.format(taskList.get(i).getCtime()) + "\",";
			json += "\"utime\"" + " : " + "\""
					+ format.format(taskList.get(i).getUtime()) + "\",";
			json += "\"userName\"" + " : " + "\""
					+ forGetUserName(taskList.get(i).getUserID()) + "\",";
			json += "\"statusName\"" + " : " + "\""
					+ forGetStatus(taskList.get(i).getStatusID()) + "\"";
			if (i == taskList.size() - 1) {
				json += "}";
			} else {
				json += "},";
			}
		}
		json += "]";
		
		model.addAttribute("userRole", userRole);
		out.write(json);
		out.flush();
	}

	/*
	 * 通过userID获取用户的姓名
	 */
	public String forGetUserName(String userID) {
		User user = userDao.findByUserID(userID);
		String username = user.getRealname();
		return username;
	}

	/*
	 * 通过任务状态id获取任务名
	 */
	public String forGetStatus(String statusID) {
		List<TaskStatus> taskStatusList = tsDao.findByTaskStatusID(statusID);
		TaskStatus ts = new TaskStatus();
		String statusName = new String();
		if (taskStatusList.size() == 1) {
			ts = taskStatusList.get(0);
			statusName = ts.getStatusName();
		}
		if ("coming".equals(statusName)) {
			statusName = "未执行";
		} else if ("running".equals(statusName)) {
			statusName = "执行中";
		} else if ("finished".equals(statusName)) {
			statusName = "已完成";
		}
		return statusName;
	}

	@RequestMapping("taskmessage.do")
	public String taskmessage(HttpServletRequest request,
			HttpServletResponse response, String taskID, Model model) {
		if (taskID == null || "".equals(taskID)) {
			taskID = (String) request.getParameter("taskID");
		}
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		ResultFileDAO resultFileDao = new ResultFileDAO();
		List<OriginalFile> originalFileList = new ArrayList<OriginalFile>();
		List<Task> taskList = new ArrayList<Task>();
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		resultFileList = resultFileDao.findByResultFileID("");

		if (resultFileList.size() < 1) {
			resultFileList = null;
		} else {
			for (int i = 0; i < resultFileList.size(); i++) {
				String note = resultFileList.get(i).getNote();
				try {
					note = note.split(Const.RESULT_FILE_NOTE_SEP)[0];
					note = note.split(":")[1];
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				if (taskID.equals(resultFileList.get(i).getTaskID())) {
					resultFileList.get(i).setNote(note);
				}
			}
		}
		taskList = taskDao.findTask(taskID);
		String taskName = taskList.get(0).getTaskName();
		originalFileList = originalFileDao.findOriginalByTask(taskID);
		if (originalFileList.size() > 0) {
			for (int i = 0; i < originalFileList.size(); i++) {
				String path = originalFileList.get(i).getPath();
				String originalName = new String();
				String newPath = new String();
				String[] arr = path.split(File.separator.replaceAll("\\\\",
						"\\\\\\\\"));
				originalName = arr[arr.length - 1];
				newPath = path.substring(path.indexOf("store"));
				originalFileList.get(i).setNote(originalName);
				originalFileList.get(i).setPath(newPath);
			}
			model.addAttribute("taskID", taskID);
			model.addAttribute("taskName", taskName);
			model.addAttribute("resultFileList", resultFileList);
			model.addAttribute("originalFileList", originalFileList);
		} else {
			model.addAttribute("taskName", taskName);
			model.addAttribute("message", "未查询到结果!");
		}
		return "taskmessage";
	}

	@RequestMapping("delettask.do")
	public String deletTask(HttpServletRequest request,
			HttpServletResponse response, String taskID, Model model)
			throws IOException {
		OriginalFileDAO ofDao = new OriginalFileDAO();
		ResultFileDAO resultFileDao = new ResultFileDAO();
		User user = (User)request.getSession().getAttribute("user");
		String userid = user.getUserID();
		if (!checkRole(userid)) {
			return "task_list";
		}
		resultFileDao.deletByTaskID(taskID);
		ofDao.deletAllTaskID(taskID);
		taskDao.delet(taskID);
		return "task_list";
	}
	
	@RequestMapping("videomark.do")
	public String videomark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String originalFileId = request.getParameter("originalFileID");
		response.setCharacterEncoding("utf-8");
		String isCheck = "mp4";
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		CacheTaskDAO ctD = new CacheTaskDAO(); 
		OriginalFile originalFile = new OriginalFile();
		originalFile = originalFileDao.findByOriginalFileID(originalFileId);
		String file = originalFile.getPath();
		String file_test = file.substring(file.indexOf("store"));
		file = file_test.replaceAll("\\\\", "/");
		isCheck = file.substring(file.lastIndexOf(".") + 1);
		String taskId = originalFile.getTaskID();
		List<CacheTask> ctList = ctD.findCacheTaskByOriginalFileID(originalFileId);
		String json = "[]";
		for(CacheTask ct : ctList) {
			JSONObject taskJson = JSONObject.fromObject(ct.getJson());
			String string = taskJson.getString("videolist");
			if(string.length() == 2) {
				string = taskJson.getString("photolist");
			}
			JSONArray jsonArr = JSONArray.fromObject(string);
			JSONObject jsonObject = (JSONObject)jsonArr.get(0);
			String originalFileIdJson = jsonObject.getString("original_file_id");
			if(originalFileId.equals(originalFileIdJson)) {
				json = taskJson.toString();
				break;
			}
		}
		System.out.println(json);
		request.setAttribute("video_info_json", json);
		request.setAttribute("original_file_id", originalFileId);
		request.setAttribute("file", file);
		request.setAttribute("taskId", taskId);
		if(isCheck.equals("mp4") || isCheck.equals("MP4")) {
			return "video_mark";
		}else {
			return "image_mark";
		}
	}

	@RequestMapping("gettaskoriginal.do")
	public void gettaskoriginal(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		String taskId = request.getParameter("task_id");
		List<OriginalFile> originalFileList = new ArrayList<OriginalFile>();
		originalFileList = originalFileDao.findOriginalByTask(taskId);

		JSONArray array = new JSONArray();
		for (int i = 0; i < originalFileList.size(); i++) {
			String id = originalFileList.get(i).getOriginalFileID();
			String path = originalFileList.get(i).getPath();
			path = path.substring(path.lastIndexOf(File.separator) + 1);
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			obj.put("path", path);
			array.add(obj);
		}
		PrintWriter out = response.getWriter();
		out.write(array.toString());
		out.flush();
	}

	@RequestMapping("updatestatus.do")
	public void updateTaskStatus(HttpServletRequest request,
			HttpServletResponse response, String taskID, Model model)
			throws IOException {
		this.checkTaskStatus(taskID);
		response.sendRedirect("task_list.jsp");
	}

	public String checkTaskStatus(String taskID) {
		String taskStatus = "coming";
		OriginalFileDAO originalFileDao = new OriginalFileDAO();
		ResultFileDAO resultFileDao = new ResultFileDAO();
		TaskDAO taskDao = new TaskDAO();
		List<ResultFile> resultFileList = resultFileDao.findByTaskID(taskID);
		List<OriginalFile> originalFileList = originalFileDao
				.findOriginalByTask(taskID);
		if (resultFileList.size() > 0) {
			int isCheck = 0;
			for (int i = 0; i < originalFileList.size(); i++) {
				isCheck = 0;
				for (int j = 0; j < resultFileList.size(); j++) {
					String originalFileID = new String();
					String note = resultFileList.get(j).getNote();
					note = note.split(Const.RESULT_FILE_NOTE_SEP)[0];
					originalFileID = note.split(":")[1];
					if (originalFileID.equals(originalFileList.get(i)
							.getOriginalFileID())) {
						isCheck = 1;
						break;
					}
					if ((j == (resultFileList.size() - 1)) && (isCheck != 1)) {
						isCheck = 0;
					}
				}
				if (isCheck == 0) {
					break;
				}
			}
			if (isCheck == 0) {
				taskStatus = "running";
			} else {
				taskStatus = "finished";
			}
		}

		Task task = taskDao.findTask(taskID).get(0);
		String statusID = tsDao.findByStatusNname(taskStatus).get(0)
				.getStatusID();
		task.setStatusID(statusID);
		taskDao.updateTask(taskID, task);
		return taskStatus;
	}

	public boolean checkRole(String userID) {
		boolean isCheck = false;
		String roleName = roleDao.findRolebyUserID(userID).getRolename();

		if (roleName == "administrator" || "administrator".equals(roleName)) {
			isCheck = true;
		}
		return isCheck;
	}

	@RequestMapping("searchtask.do")
	public void searchTask(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("user_name");
		String taskName = request.getParameter("task_name");
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		String status = request.getParameter("status");
		List<Task> task = new ArrayList<Task>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user = (User)request.getSession().getAttribute("user");
		String userID = user.getUserID();
		if("A".equals(user.getRoleID())) {
			task = taskDao.findTask("");
		}else {
			task = taskDao.findByUserID(userID);
		}
		
		String userId = new String();
		String statusId = new String();
		if(!"".equals(userName)) {
			userId = userDao.findUserIdByRealName(userName);
		}
		if(!"".equals(status)) {
			if("已完成".equals(status)){
				statusId = tsDao.findByStatusNname("finished").get(0).getStatusID();
			}else if("未执行".equals(status)) {
				statusId = tsDao.findByStatusNname("coming").get(0).getStatusID();
			}else if("进行中".equals(status)) {
				statusId = tsDao.findByStatusNname("running").get(0).getStatusID();
			}else {
				statusId = "";
			}
		}
		if(task.size() > 0) {
			int num = task.size();
			if(!"".equals(userName)) {
				for(int i = 0; i < num; i++) {
					if(!userId.equals(task.get(i).getUserID())){
						task.remove(i);
						i--;
						num--;
					}
				}
			}
			
			num = task.size();
			if(!"".equals(taskName)) {
				for(int i = 0; i < num; i++) {
					if(!taskName.equals(task.get(i).getTaskName())) {
						task.remove(i);
						i--;
						num--;
					}
				}
			}
			
			num = task.size();
			if(!"".equals(status)) {
				for(int i = 0; i < num; i++) {
					if(!statusId.equals(task.get(i).getStatusID())) {
						task.remove(i);
						i--;
						num--;
					}
				}
			}
			
			num = task.size();
			if(!"".equals(stime)&&!"".equals(etime)){
				stime += " 00:00:00";
				etime += " 23:59:59";
				Date date = new Date();
				date = format.parse(stime);
				long sTime = date.getTime();
				date = format.parse(etime);
				long eTime = date.getTime();
				for(int i = 0; i < num; i++) {
					if(task.get(i).getCtime() < sTime || task.get(i).getCtime() > eTime) {
						task.remove(i);
						i--;
						num--;
					}
				}
				
			} 
		}
		
		String json = "[";
		for (int i = 0; i < task.size(); i++) {
			json += "{ \"taskID\"" + " : " + "\"" + task.get(i).getTaskID()
					+ "\",";
			json += "\"taskname\"" + " : " + "\""
					+ task.get(i).getTaskName() + "\",";
			json += "\"ctime\"" + " : " + "\""
					+ format.format(task.get(i).getCtime()) + "\",";
			json += "\"utime\"" + " : " + "\""
					+ format.format(task.get(i).getUtime()) + "\",";
			json += "\"userName\"" + " : " + "\""
					+ forGetUserName(task.get(i).getUserID()) + "\",";
			json += "\"statusName\"" + " : " + "\""
					+ forGetStatus(task.get(i).getStatusID()) + "\"";
			if (i == task.size() - 1) {
				json += "}";
			} else {
				json += "},";
			}
		}
		json += "]";
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
	}

	// / 目录树
	//
	//
	//
	//
	//
	//
	//
	//
	@RequestMapping(value = "getTaskTree.do", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getTree(String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("contentType", "text/plain; charset=utf-8");
		String alertTaskId = request.getParameter("alertTaskId");
		OriginalFileDAO dao = new OriginalFileDAO();
		if (id == null || "".equals(id)) {
			OriginalFile o = new OriginalFile();
			o.setCtime(System.currentTimeMillis());
			o.setFlag(1);
			o.setName("source");
			o.setNote("");
			o.setOriginalFileID("0");
			o.setParent(true);
			o.setPath(Const.SOURCE_FILE_DIR);
			o.setPid("");
			o.setTaskID("");
			o.setUtime(System.currentTimeMillis());
			if(!dao.isExistPath(Const.SOURCE_FILE_DIR)){
				dao.insert(o);	
			}
			
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> map3 = new HashMap<>();
				map3.put("id", o.getOriginalFileID());
				map3.put("name", o.getName());
				map3.put("path",o.getPath());
				map3.put("pid", o.getPid());
				map3.put("isParent", o.isParent());// 是否是父节点
				list1.add(map3);
			return JSONArray.fromObject(list1).toString();
		}else{
			String path = dao.findPathById(id);
			File source = new File(path.replace("\\\\", "\\\\\\\\"));
			File[] files = source.listFiles();
			
			for(int i=0;i<files.length;i++){
				String sonpath = files[i].getPath();
				if(dao.isExistPath(sonpath)){
					break;
				}
				OriginalFile of = new OriginalFile();
				of.setCtime(System.currentTimeMillis());
				of.setFlag(1);
				of.setName(sonpath.substring(sonpath.lastIndexOf(File.separator)+1));
				of.setNote("");
				of.setOriginalFileID(UUID.randomUUID().toString());
				if(files[i].isDirectory()){
					of.setParent(true);
				}
				else if(files[i].isFile()){
					of.setParent(false);
				}
				of.setPath(sonpath);
				of.setPid(id);
				of.setTaskID("");
				of.setUtime(System.currentTimeMillis());
				dao.insert(of);
			}
			
			Map<String, Object> map1 = null;
			List<OriginalFile> list0 = dao.findTreeById(id,"");
			List<OriginalFile> list= new ArrayList<OriginalFile>();
			if(alertTaskId!=null){
				list = dao.findTreeById(id,alertTaskId);
			}
			
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			for (OriginalFile m1 : list) {
				map1 = new HashMap<String, Object>();
				map1.put("id", m1.getOriginalFileID());
				map1.put("name", m1.getName());
				map1.put("path",m1.getPath());
				map1.put("pid", m1.getPid());
				map1.put("isParent", m1.isParent());
				list1.add(map1);
			}
			for (OriginalFile m1 : list0) {
				map1 = new HashMap<String, Object>();
				map1.put("id", m1.getOriginalFileID());
				map1.put("name", m1.getName());
				map1.put("path",m1.getPath());
				map1.put("pid", m1.getPid());
				map1.put("isParent", m1.isParent());
				list1.add(map1);
			}
		return JSONArray.fromObject(list1).toString();	
		}
				
	}

	@RequestMapping(value = "getTaskSrc.do")
	public void getSrc(String id, boolean isParent, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("contentType", "application/json; charset=utf-8");
		String taskId = request.getParameter("taskId");
		if(taskId == null) {
			taskId = "";
		}
		OriginalFileDAO dao = new OriginalFileDAO();
		if (isParent) {
			String path1 = null;
			PrintWriter out1 = response.getWriter();
			List<OriginalFile> list = dao.findTreeById(id,taskId);
			List<Map<String, String>> json = new Vector<Map<String, String>>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> user = new Hashtable<String, String>();
					user.put("sortindex", i + "");
					user.put("name", list.get(i).getName());
					user.put("id", list.get(i).getOriginalFileID());
					String name = list.get(i).getName();
					String path = list.get(i).getPath().replaceAll("\\\\", "\\\\\\\\");
					String type = name.substring(name.lastIndexOf(".")+1,name.length()).toLowerCase();
					
					//截取第一帧图片
					System.load(Const.APP_CONTEXT_PATH+File.separator+"opencv_java330.dll");
					if ("mp4".equals(type)) {
						run(path.replaceAll("\\\\", "\\\\\\\\"), list.get(i).getOriginalFileID());
					}
					
					if (list.get(i).getPath() != null && list.get(i).getPath().length() > 0) {
						path1 = list.get(i).getPath().replaceAll("\\\\", "\\\\\\\\");
					}
					path1 = path1.substring(path1.indexOf("store")).replace("\\\\", "/");
					user.put("path", path1);
					json.add(user);
				}
			}
			StringBuffer sbuf = new StringBuffer();
			sbuf.append("[");

			for (Iterator<Map<String, String>> iter = json.iterator(); iter
					.hasNext();) {
				Map<String, String> user = iter.next();
				sbuf.append("{");
				for (Iterator<Map.Entry<String, String>> uIter = user
						.entrySet().iterator(); uIter.hasNext();) {
					Map.Entry<String, String> entry = uIter.next();
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
			out1.write(sbuf.toString());
			out1.flush();
		} else {
			String path = dao.finePathByID(id);
			String name = dao.fineNameByID(id);
			String path1 = null;
			if (path != null && path.length() > 0) {
				path1 = path.replaceAll("\\\\", "\\\\\\\\");
			}
			path1 = path1.substring(path1.indexOf("store")).replace("\\\\", "/");
			PrintWriter out = response.getWriter();
			out.write("[{\"path\":\"" + path1 + "\", \"name\":\"" + name
					+ "\",\"id\":\"" + id + "\"}]");
			out.flush();
			String type = name.substring(name.lastIndexOf(".")+1,name.length()).toLowerCase();
			//截取第一帧图片
			//System.load("D:\\open330\\opencv\\build\\java\\x64\\opencv_java330.dll");
			System.load(Const.APP_CONTEXT_PATH+File.separator+"opencv_java330.dll");
			if(type.equals("mp4")){
				run(path.replaceAll("\\\\", "\\\\\\\\"),id);
			}
		}
	}
	
	public void run(String source,String id){
		VideoCapture v = new VideoCapture();
		v.open(source);
			Mat mat = new Mat();
			//读取对应时间显示的图片
				v.set(0, 0*1000);
				if(v.read(mat)){
					Imgcodecs.imwrite(Const.POSTER_FILE_DIR+File.separator + id + ".jpg", mat);						
				}								
	}

}
