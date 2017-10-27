package vm.web.handlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vm.db.dao.CacheTaskDAO;
import vm.db.dao.MiddleTaskTargetTypeDAO;
import vm.db.dao.OriginalFileDAO;
import vm.db.dao.ResultFileDAO;
import vm.db.dao.RoleDAO;
import vm.db.dao.TargetTypeDAO;
import vm.db.dao.TaskDAO;
import vm.db.dao.TaskStatusDAO;
import vm.db.dao.UserDAO;
import vm.db.po.CacheTask;
import vm.db.po.MiddleTaskTargetType;
import vm.db.po.OriginalFile;
import vm.db.po.ResultFile;
import vm.db.po.Role;
import vm.db.po.TargetType;
import vm.db.po.Task;
import vm.db.po.TaskStatus;
import vm.db.po.User;
import vm.util.Const;


@Controller
public class MarkHandler {
	
	private UserDAO userDao = new UserDAO();
	
	private RoleDAO roleDao = new RoleDAO();
	
	private TaskDAO taskDao = new TaskDAO();
	
	private MiddleTaskTargetTypeDAO middleDao = new MiddleTaskTargetTypeDAO();
	
	private TargetTypeDAO targetTypeDAO = new TargetTypeDAO();
	
	private ResultFileDAO resultFileDao = new ResultFileDAO();
	
	private String formdata;
	@RequestMapping(value = "video.do")
	public String videoMark(HttpServletRequest request, HttpServletResponse response) {
		//接受前台传过来的json字符串并解析
		System.out.println(request.getParameter("subdata"));
		String taskId;
		//System.out.println(taskId);
		String sub = request.getParameter("subdata");
		String photoList ="";
		String videoList = "";
		JSONObject subjson = JSONObject.fromObject(sub);
		taskId = subjson.getString("taskid");
			photoList = subjson.getString("photolist");
			videoList = subjson.getString("videolist");
			/*if(photoList==null||videoList==null) {
				try {
					PrintWriter out = response.getWriter();
					out.write("{\"backurl\":\"" + "taskmessage.do?taskID=" + taskId + "\"}");
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		String originalFileId = "";
		String mark = "";
		String source = "";
		String file_name = "";
		String videoID = "";
		StringBuffer wholesb = new StringBuffer();
		wholesb.append("{");
		wholesb.append("\"taskid\":"+ "\""+ taskId+"\",");
		//解析photolist
		if(videoList.length()==2){
			wholesb.append("\"videolist\":"+videoList+",");
			wholesb.append("\"photolist\":"+photoList);
			wholesb.append("}");
			//获得其中的originalFileId
			JSONArray photoArr = JSONArray.fromObject(photoList);
			for(int i=0;i<photoArr.size();i++){
				JSONObject photojson = (JSONObject)photoArr.get(i);
				originalFileId = photojson.getString("original_file_id");
			}	
		}else{
			wholesb.append("\"photolist\":"+photoList+",");
			wholesb.append("\"videolist\":[{");
		}
		//解析videolist数组
		//wholesb.append("\"videolist\":[{");			
		JSONArray videoArr = JSONArray.fromObject(videoList);
		for(int i=0;i<videoArr.size();i++){
			JSONObject videojson = (JSONObject)videoArr.get(i);
			Iterator<?> it = videojson.keys();
			while(it.hasNext()){
				String key = it.next().toString();
				String value = videojson.get(key).toString();
				if(key.equals("url")){
					source = videojson.get(key).toString();
					wholesb.append("\"url\":"+"\""+ source +"\",");
				}
				//解析url
				if(key.equals("original_file_id")){
					originalFileId = videojson.get(key).toString();
					wholesb.append("\"original_file_id\":"+"\""+ originalFileId +"\",");
				}
				if(key.equals("videoID")){
					videoID = videojson.get(key).toString();
					wholesb.append("\"videoID\":"+"\""+ videoID +"\",");
				}
				if(key.equals("mark")){
					mark = videojson.get(key).toString();
					wholesb.append("\"mark\":"+"\""+ mark +"\",");
				}
				//解析formdata
				if(key.equals("formdata")){
					StringBuffer formbuf = new StringBuffer();
					HashMap<String,String> formdatacache = new HashMap<String,String>();
					JSONObject formdatajob = JSONObject.fromObject(videojson.get(key).toString());
					Iterator<?> itformdata = formdatajob.keys();
					while(itformdata.hasNext()){
						String next = itformdata.next().toString();
						formdatacache.put(next, formdatajob.get(next).toString());
					}
					file_name = formdatacache.get("file_name").toString();
					String videoNote = formdatacache.get("video_note").toString();
					String specialCase = formdatacache.get("special_case").toString();
					String weather = formdatacache.get("weather");
					String road_type = formdatacache.get("road_type");
					String road_condition = formdatacache.get("road_condition");
					String light = formdatacache.get("light");
					String video_details = formdatacache.get("video_details");
					String target = formdatacache.get("target");
					 //生成formdata json字符串
					 //wholesb.append("\"formdata\":"+formdata+",");
						formbuf.append("\"formdata\":{");						
						if(weather != null){
							formbuf.append("\"weather\":[\"" + (weather == null ? "" : weather)  + "\"],");
							middleDao.insert(this.createMiddle(taskId, "weather"));
							JSONArray jsonArr = this.StringtoJson(weather);
							middleDao.insert(this.createMiddle(taskId, jsonArr.getString(0)));
						}

						
						if(road_type!=null){
							formbuf.append("\"road_type\":[\"" + (road_type == null ? "" : road_type)  + "\"],");
							middleDao.insert(this.createMiddle(taskId, "road_type"));
							JSONArray jsonArr = this.StringtoJson(road_type);
							middleDao.insert(this.createMiddle(taskId, jsonArr.getString(0)));
						}
						
						
						if(road_condition!=null){
							JSONArray jsonArr = this.StringtoJson(road_condition);
							formbuf.append("\"road_condition\":" + (road_condition == null ? "" : jsonArr.toString())  + ",");
							for (int j = 0; j < jsonArr.size(); j++) {
								String road_conditionEle = jsonArr.getString(j);
								middleDao.insert(this.createMiddle(taskId, road_conditionEle));
							}
						}
						
						
						if(light!=null){
							JSONArray jsonArr = this.StringtoJson(light);
							formbuf.append("\"light\":" + (light == null ? "" : jsonArr.toString())  + ",");
							for (int j = 0; j < jsonArr.size(); j++) {
								String lightEle = jsonArr.getString(j);
								middleDao.insert(this.createMiddle(taskId, lightEle));
							}
						}
						
						
						if(video_details!=null){			
							middleDao.insert(this.createMiddle(taskId, "video_details"));
							JSONArray jsonArr = this.StringtoJson(video_details);
							formbuf.append("\"video_details\":" + (video_details == null ? "" : jsonArr.toString())  + ",");
							for (int j = 0; j < jsonArr.size(); j++) {
								String video_detailsEle = jsonArr.getString(j);
								middleDao.insert(this.createMiddle(taskId, video_detailsEle));
							}
						}
						
						
						if(target!=null){			
							middleDao.insert(this.createMiddle(taskId, "target"));
							JSONArray jsonArr = this.StringtoJson(target);
							formbuf.append("\"target\":" + (target == null ? "" : jsonArr.toString())  + ",");
							for (int j = 0; j < jsonArr.size(); j++) {
								String targetEle = jsonArr.getString(j);
								middleDao.insert(this.createMiddle(taskId, targetEle));
							}
						}
						if(!videoNote.equals("")){
							
							JSONArray jsonArr = this.StringtoJson(videoNote);
							formbuf.append("\"video_note\":" + (videoNote == null ? "" : jsonArr) + ",");
						}
						
						//if(!specialCase.equals("")){
							JSONArray jsonArr = this.StringtoJson(specialCase);
							formbuf.append("\"special_case\":" + (specialCase == null ? "" : jsonArr));
						//}
						

						formbuf.append("}");
						/*System.out.println(this.createResultFile(formbuf.toString(), taskId, originalFileId));
						if (this.createResultFile(formbuf.toString(), taskId, originalFileId)) {
							try {
								PrintWriter out = response.getWriter();
								out.write("{\"backurl\":\"" + "taskmessage.do?taskID=" + taskId + "\"}");
								out.flush();
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}*/formdata = formbuf.toString();
						wholesb.append(formbuf.toString()+",");	
				}
				
				//解析photos数组
				if(key.equals("photos")){
					JSONArray photoArr = JSONArray.fromObject(value);
					wholesb.append("\"photos\":[");
					for(int m=0;m<photoArr.size();m++){
						JSONObject photojson = (JSONObject)photoArr.get(m);
						String picPath = Const.POSTER_FILE_DIR+File.separator + originalFileId + "_" + photojson.get("timestamp").toString() + ".jpg";
						createOriginalFile(originalFileId, picPath);
						photojson.element("url", picPath);
	
						Iterator<?> itphoto = photojson.keys();
						while(itphoto.hasNext()){
							String photokey = itphoto.next().toString();
							if(photokey.equals("timestamp")){
								getPic(Const.APP_CONTEXT_PATH+File.separator+file_name,photojson.get(photokey).toString(),originalFileId);	
							}
						}
						if(m!=photoArr.size()-1){
							wholesb.append(photojson);
							wholesb.append(",");
						}else{
							wholesb.append(photojson);
						}
						
					}				
			}
			
		}
		}
		wholesb.append("]}]}");
		CacheTask ct = new CacheTask();
		ct.setJson(wholesb.toString());
		ct.setOriginalFileId(originalFileId);
		ct.setTaskID(taskId);
		CacheTaskDAO cd = new CacheTaskDAO();
		/*
		 * 更新
		 * */
		String oldJson = "";
		boolean isCheck = false;
		List<CacheTask> ctList = cd.findCacheTaskByOriginalFileID(originalFileId);
		if(videoList.length()!=2){
			for(CacheTask cache : ctList) {
				JSONObject taskJson = JSONObject.fromObject(cache.getJson());
				String videoString = taskJson.getString("videolist");
				JSONArray videoArray = JSONArray.fromObject(videoString);
				JSONObject videoJson = (JSONObject)videoArray.get(0);
				String originalFileIdJson = videoJson.getString("original_file_id");
				if(originalFileId.equals(originalFileIdJson)) {
					isCheck = true;
					oldJson = taskJson.toString();
					break;
				}
			}
		}
		else if(videoList.length()==2){
			for(CacheTask cache : ctList) {
				JSONObject taskJson = JSONObject.fromObject(cache.getJson());
				String photoString = taskJson.getString("photolist");
				JSONArray photoArray = JSONArray.fromObject(photoString);
				JSONObject photoJson = (JSONObject)photoArray.get(0);
				String originalFileIdJson = photoJson.getString("original_file_id");
				if(originalFileId.equals(originalFileIdJson)) {
					isCheck = true;
					oldJson = taskJson.toString();
					break;
				}
			}
		}
		
		if(isCheck) {
			CacheTask cacheTask = new CacheTask();
			cacheTask.setTaskID(taskId);
			cacheTask.setJson(oldJson);
			cacheTask.setOriginalFileId(originalFileId);
			cd.updateCacheTaskById(cacheTask, ct);
		}else {
			cd.insert(ct);
		}
		
				
		//这个formdata中还得加入该视频所对应的所有截图以及框*******
		if (this.createResultFile(wholesb.toString(), taskId, originalFileId)) {
			try {
				PrintWriter out = response.getWriter();
				out.write("{\"backurl\":\"" + "taskmessage.do?taskID=" + taskId + "\"}");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		return null;
	}
	//创建一个将字符串转化为json格式的方法
	private JSONArray StringtoJson(String s){
		JSONArray jsonArr = null;
		try{
			jsonArr = JSONArray.fromObject(s);
		}catch(Exception e){
			jsonArr = new JSONArray();
			jsonArr.add(s);
		}
		return jsonArr;
	}
	//创建一个PrimaryFile对象，将截图插入PrimaryFile中
	private void createOriginalFile(String originalFileId,String path){
		OriginalFile p = new OriginalFile();
		p.setOriginalFileID(UUID.randomUUID().toString());
		p.setCtime(System.currentTimeMillis());
		p.setUtime(System.currentTimeMillis());
		p.setNote(originalFileId);
		p.setPath(path);
		p.setFlag(1);
		p.setName(path.substring(path.lastIndexOf("\\"),path.length()-4));
		//先写死
		p.setParent(false);
		p.setPid("pic");
		p.setTaskID("");
		OriginalFileDAO dao = new OriginalFileDAO();
		dao.insert(p);
		
	}
	
	//创建一个MiddleTaskTargetType对象，如果创建失败则返回null
	private MiddleTaskTargetType createMiddle(String taskId, String targetTypeName) {
		MiddleTaskTargetType middle = new MiddleTaskTargetType();

		middle.setTaskTargetTypeID(UUID.randomUUID().toString());
		long curTime = System.currentTimeMillis();
		middle.setUtime(curTime);
		middle.setCtime(curTime);
		middle.setNote("");
		middle.setFlag(1);
		middle.setTaskID(taskId);
		if (this.getTargetTypeIdByName(targetTypeName) != null) {
			middle.setTargetTypeID(this.getTargetTypeIdByName(targetTypeName));
			return middle;
		}
		return null;
	}
	/*
	 * 根据targetTypeName值获取TargetType对象的ID
	 */
	private String getTargetTypeIdByName(String targetTypeName) {
		List<TargetType> targetTypes = this.targetTypeDAO.findByTargetTypeName(targetTypeName);
		if (targetTypes.size() == 1) {
			return targetTypes.get(0).getTargetTypeID();
		}
		return null;
	}
	
	/*
	 * 1、创建视频标注结果文件（json文本）并写入磁盘中
	 * 2、在标注结果文件表中插入对应的记录
	 */
	private boolean createResultFile(String videoMarkInfo, String taskID, String originalFileId/*,Boolean endflag*/) {
		String path = Const.RESULT_FILE_DIR + File.separator + originalFileId + ".json";
		File file = new File(path);
		List<String> lines = new ArrayList<String>();
		lines.add(videoMarkInfo);
		try {
			FileUtils.writeLines(file, "UTF-8", lines);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		ResultFileDAO resultFileDAO = new ResultFileDAO();
		String origianlFileIdString = "original_file_id:" + originalFileId;
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		resultFileList = resultFileDAO.findByOriginalFileId(origianlFileIdString);
		ResultFile resultFile = new ResultFile();
		long currentTimeMillis = System.currentTimeMillis();
		resultFile.setCtime(currentTimeMillis);
		resultFile.setUtime(currentTimeMillis);
		resultFile.setNote(origianlFileIdString+Const.ORIGINALFILE_NOTE_SEP+formdata);
		resultFile.setFlag(1);
		resultFile.setPath(path);
		resultFile.setTaskID(taskID);
		TaskStatusDAO taskStatusDAO = new TaskStatusDAO();
		List<TaskStatus> taskStatusList = taskStatusDAO.findByStatusNname("running");
		TaskStatus taskStatus = taskStatusList.get(0);
		resultFile.setStatusID(taskStatus.getStatusID());
		if(resultFileList.size() > 0) {
			resultFile.setResultFileID(resultFileList.get(0).getResultFileID());
			if(resultFileDAO.updateByResultFileID(resultFile.getResultFileID(), resultFile)) {
				return true;
			}
		}else {
			resultFile.setResultFileID(UUID.randomUUID().toString());
			if (resultFileDAO.insert(resultFile)) {
				return true;
			}
		}
	
		return false;
	}
	
/*	public boolean insertImgIntoDatabase(String taskId,String path){
		
		OriginalFile o = new OriginalFile();
		o.setOriginalFileID(UUID.randomUUID().toString());
		o.setCtime(System.currentTimeMillis());
		o.setUtime(System.currentTimeMillis());
		o.setFlag(1);
		//磁盘存储地址
		o.setPath(path);
		o.setNote("");
		o.setTaskID(taskId);
		OriginalFileDAO od = new OriginalFileDAO();
		
		od.insert(o);
		return true;
	}*/
	
	@RequestMapping(value="user_role.do")
	public void getUserRole(
			@RequestParam(value="username", required=false) String username,
			HttpServletResponse response) {
		String ret = "invaild_role";
		User user = null;
		List<User> users = this.userDao.findByUserName(username);
		if (users.size() == 1) {
			user = users.get(0);
		}
		if (user != null) {
			Role role = this.roleDao.findRolebyUserID(user.getUserID());
			if (role != null) {
				ret = role.getRolename();
			}
		}
		try {
			PrintWriter out = response.getWriter();
			out.write(ret);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//存图片
	/*public String videoshot(String imglist, String taskId, String fileName) {
		FileImageOutputStream output = null;
		String imgpath = "";
		// 存储磁盘路径
		String path = Const.SOURCE_FILE_DIR + File.separator + "screenshot-"
				+ fileName.split("\\.")[0].substring(13)+"-";
		byte[] buffer;
		if (imglist == null)
			return "";
		else {
			String[] imgs = imglist.split("data:image/png;");
			for (int i = 1; i < imgs.length; i++) {
				try {
					imgpath = path + UUID.randomUUID().toString() + ".jpg";
					this.insertImgIntoDatabase(taskId, imgpath);
					output = new FileImageOutputStream(
							new File(imgpath));
					buffer = Base64Decoder.decode(imgs[i].substring(7).getBytes(), 0,
							imgs[i].substring(7).getBytes().length);
					output.write(buffer);
					output.flush();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "";
	}*/
	
	private boolean writeToImageResultFile(String dotslist, String taskId, String pic, String originalFileId) {
		String fileName = pic.substring(pic.lastIndexOf("/") + 1, pic.lastIndexOf("."));
		File resultFile = new File(Const.RESULT_FILE_DIR + File.separator + fileName + ".json");
		JSONArray jsonArray = JSONArray.fromObject(dotslist);
		JSONArray objects = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			objects.add(object.get("bndbox"));
		}
		JSONObject image = new JSONObject();
		image.put("filename", fileName);
		image.put("objects", objects);
		try {
			FileUtils.writeStringToFile(resultFile, image.toString(), "UTF-8");
			return insertImageResultFile(taskId, resultFile.getAbsolutePath(), originalFileId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean insertImageResultFile(String taskId, String path, String originalFileId) {
		ResultFile result = new ResultFile();
		result.setResultFileID(UUID.randomUUID().toString());
		long time = System.currentTimeMillis();
		result.setCtime(time);
		result.setUtime(time);
		result.setNote("original_file_id:" + originalFileId);
		result.setFlag(1);
		result.setPath(path);
		result.setTaskID(taskId);
		List<Task> tasks = this.taskDao.findTask(taskId);
		Task task = null;
		if (tasks != null && tasks.size() >= 1) {
			task = tasks.get(0);
		}
		result.setStatusID(task.getStatusID());
		return this.resultFileDao.insert(result);
	}
	
	
	
	@RequestMapping(value="image.do")
	public String imageMark(HttpServletRequest request, HttpServletResponse response) {
		String dotslist = request.getParameter("dotslist");
		String taskId = request.getParameter("taskId");
		String pic = request.getParameter("pic");
		String originalFileId = request.getParameter("original_file_id");
		response.setCharacterEncoding("UTF-8");
		this.writeToImageResultFile(dotslist, taskId, pic, originalFileId);
		try {
			PrintWriter out = response.getWriter();
			out.write("{\"backurl\":\"" + "taskmessage.do?taskID=" + taskId + "\"}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 
	 * 接受前台传来的时间点并截图
	 * 
	 * */
	public void getPic(String source,String time,String originalFileId){
		System.load(Const.APP_CONTEXT_PATH+File.separator+"opencv_java330.dll");
		String path = "";
		VideoCapture v = new VideoCapture();
		v.open(source);
		Mat mat = new Mat();
		//读取对应时间显示的图片
			Double i = Double.parseDouble(time);
			//System.out.println(i);
			v.set(0, i*1000);
			if(v.read(mat)){
				//System.out.println("保存Ing");
				path = Const.POSTER_FILE_DIR+File.separator + originalFileId + "_" + time + ".jpg";
				//System.out.println(path);
				Imgcodecs.imwrite(path, mat);
				
				//change(now,i+"");生成压缩图方法
			}
	}
}
