package vm.web.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import vm.db.dao.OriginalFileDAO;
import vm.db.dao.ResultFileDAO;
import vm.db.dao.RoleDAO;
import vm.db.dao.TaskDAO;
import vm.db.dao.TaskStatusDAO;
import vm.db.po.OriginalFile;
import vm.db.po.ResultFile;
import vm.db.po.Task;
import vm.db.po.User;
import vm.util.Const;

@Controller
public class ResultFileHandlers {
	static ResultFileDAO resultFileDao = new ResultFileDAO();
	static TaskDAO taskDao = new TaskDAO();
	RoleDAO roleDao = new RoleDAO();
	static TaskStatusDAO taskStatusDao = new TaskStatusDAO();
	String massage = null;

	@RequestMapping("findallresultfile.do")
	public String selectResultFile(String userID, String taskID, Model model) {
		if (userID == null || "".equals(userID)) {
			return "error";
		}

		if (taskID == null || "".equals(taskID)) {
			return this.selectResultFileByNullTaskID(userID, model);
		}

		return this.selectResultFileByTaskID(taskID, model);
	}

	public String selectResultFileByTaskID(String taskID, Model model) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		resultFileList = resultFileDao.findByTaskID(taskID);

		if (resultFileList.size() > 0) {
			model.addAttribute("resultFileList", resultFileList);
		} else {
			model.addAttribute("还没有结果文件，请联系管理员分配任务!", massage);
		}
		return "result_file_lis";
	}

	public String selectResultFileByNullTaskID(String userID, Model model) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		List<Task> taskList = new ArrayList<Task>();
		taskList = taskDao.findByUserID(userID);

		if (this.checkRole(userID)) {
			resultFileList = resultFileDao.findByResultFileID("");
		} else {
			for (Task task : taskList) {
				for (ResultFile resultFile : resultFileDao.findByTaskID(task
						.getTaskID())) {
					resultFileList.add(resultFile);
				}
			}
		}

		if (resultFileList.size() > 0) {
			model.addAttribute("resultFileList", resultFileList);
		} else {
			model.addAttribute("还没有结果文件，请联系管理员分配任务!", massage);
		}

		return "result_file_list";
	}

	@RequestMapping("changeResultFile")
	public String changeResultFile(String resultFileID, Model model) {
		ResultFile resultFile = resultFileDao.findByResultFileID(resultFileID)
				.get(0);

		if (resultFile.getResultFileID() == null
				|| "".equals(resultFile.getResultFileID())) {
			return "error";
		}

		model.addAttribute("resultFile", resultFile);
		return "resultfile";
	}

	@RequestMapping("deletResultFile.do")
	public String deleteResultFile(HttpServletRequest request, String userID,
			String taskID, String resultFileID, Model model) {
		if (resultFileID == "" || "".equals(resultFileID)) {
			return "error";
		}

		if (resultFileDao.deletByResultFileID(resultFileID)) {
			return this.selectResultFile(userID, taskID, model);
		}
		return "error";
	}

	@RequestMapping("updateResultFile.do")
	public String updateResultFile(HttpServletRequest request, String userID,
			String taskID, String resultFileID, Model model) {
		ResultFile resultFile = new ResultFile();
		if (resultFileID == "" || "".equals(resultFileID)) {
			return "error";
		}
		resultFile = resultFileDao.findByResultFileID(resultFileID).get(0);

		if (resultFileDao.updateByResultFileID(resultFileID, resultFile)) {
			return this.selectResultFile(userID, taskID, model);
		}

		return "error";
	}

	public boolean checkRole(String userID) {
		boolean isCheck = false;
		String roleName = roleDao.findRolebyUserID(userID).getRolename();

		if (roleName == "管理员" || "管理员".equals(roleName)) {
			isCheck = true;
		}
		return isCheck;
	}

	@RequestMapping("fileresult.do")
	public void fileResult(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");

		String sub = request.getParameter("sub_class");

		List<Map<Object, Object>> json = new Vector<Map<Object, Object>>();
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		String b = (sub == null) ? "" : sub;
		resultFileList = resultFileDao.findByTargetType(b);
		for (int i = 0; i < resultFileList.size(); i++) {

			Map<Object, Object> resultFile = new Hashtable<Object, Object>();
			resultFile.put("resultFileID", resultFileList.get(i)
					.getResultFileID());
			long a = resultFileList.get(i).getCtime();
			Date nowTime = new Date(a);

			SimpleDateFormat sdFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			String retStrFormatNowDate = sdFormatter.format(nowTime);
			resultFile.put("ctime", retStrFormatNowDate);
			String taskID = resultFileList.get(i).getTaskID();
			List<Task> task = taskDao.findTask(taskID);
			String taskName = task.get(0).getTaskName();
			resultFile.put("taskName", taskName);
			resultFile.put("path", resultFileList.get(i).getPath());
			String resultType = new String(); 
			String note = resultFileList.get(i).getNote();
			note = note.split(Const.RESULT_FILE_NOTE_SEP)[0];
			note = note.split(":")[1];
			OriginalFileDAO originalFileDao = new OriginalFileDAO();
			OriginalFile originalFile = originalFileDao.findByOriginalFileID(note);
			String originalFilePath = originalFile.getPath(); 
			String type = originalFilePath.substring(originalFilePath.lastIndexOf(".")+1);
			System.out.println(type);
			if("mp4".equals(type)) {
				resultType = "视频";
			}else {
				resultType = "图片";
			}
			resultFile.put("resultType", resultType);
			json.add(resultFile);
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("[");

		for (Iterator<Map<Object, Object>> iter = json.iterator(); iter
				.hasNext();) {
			Map<Object, Object> user = iter.next();
			sbuf.append("{");
			for (Iterator<Entry<Object, Object>> uIter = user.entrySet()
					.iterator(); uIter.hasNext();) {
				Entry<Object, Object> entry = uIter.next();
				sbuf.append("\"" + entry.getKey() + "\":\"" + entry.getValue()
						+ "\"");
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
		System.out.println(sbuf.toString());
		try {
			PrintWriter out = response.getWriter();
			out.write(sbuf.toString().replaceAll("\\\\", "\\\\\\\\"));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("ResultFileRequest.do")
	public void GetMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		List<Map<Object, Object>> json = new Vector<Map<Object, Object>>();
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		
		HttpSession session = request.getSession(false);
		User currentUser = (User) session.getAttribute("user");
		if ("A".equals(currentUser.getRoleID())) {
			currentUser = null;
		}
		if (currentUser != null) {
			String currentUserId = currentUser.getUserID();
			List<Task> tasks = this.taskDao.findByUserID(currentUserId);
			for (Task task : tasks) {
				String taskId = task.getTaskID();
				List<ResultFile> resultFiles = this.resultFileDao.findByTaskID(taskId);
				for (ResultFile resultFile : resultFiles) {
					resultFileList.add(resultFile);
				}
			}
		} else {
			resultFileList = resultFileDao.findByResultFileID("");
		}

		for (int i = 0; i < resultFileList.size(); i++) {
			Map<Object, Object> resultFile = new Hashtable<Object, Object>();
			resultFile.put("resultFileID", resultFileList.get(i)
					.getResultFileID());
			long a = resultFileList.get(i).getCtime();
			Date nowTime = new Date(a);

			SimpleDateFormat sdFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			String retStrFormatNowDate = sdFormatter.format(nowTime);
			resultFile.put("ctime", retStrFormatNowDate);
			String taskID = resultFileList.get(i).getTaskID();
			List<Task> task = taskDao.findTask(taskID);
			String taskName = task.get(0).getTaskName();
			resultFile.put("taskName", taskName);
			String abspath = resultFileList.get(i).getPath();
			String compath = abspath.substring(abspath.indexOf(request.getContextPath().substring(1)));
			resultFile.put("path", compath);
			String resultType = new String(); 
			String note = resultFileList.get(i).getNote();
			note = note.split(Const.RESULT_FILE_NOTE_SEP)[0];
			//////要加一个判断，如果是图片进来的话  没有originalFileId
			if(note.split(":").length>1){
				note = note.split(":")[1];
			}else{
				break;
			}
			
			if(note==null||note==""){
				break;
			}else{
				OriginalFileDAO originalFileDao = new OriginalFileDAO();
				OriginalFile originalFile = originalFileDao.findByOriginalFileID(note);
				String originalFilePath = originalFile.getPath(); 
				String type = originalFilePath.substring(originalFilePath.lastIndexOf(".")+1);
				if("mp4".equals(type)) {
					resultType = "视频";
				}else {
					resultType = "图片";
				}
				resultFile.put("resultType", resultType);
				
				json.add(resultFile);
			}
				
		}

		StringBuffer sbuf = new StringBuffer();
		sbuf.append("[");

		for (Iterator<Map<Object, Object>> iter = json.iterator(); iter
				.hasNext();) {
			Map<Object, Object> user = iter.next();
			sbuf.append("{");
			for (Iterator<Entry<Object, Object>> uIter = user.entrySet()
					.iterator(); uIter.hasNext();) {
				Entry<Object, Object> entry = uIter.next();
				sbuf.append("\"" + entry.getKey() + "\":\"" + entry.getValue()
						+ "\"");
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

		out.write(sbuf.toString().replaceAll("\\\\", "\\\\\\\\"));
		out.flush();

	}

	// 导出文件

	/**
	 * @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
	 * @param resourePath
	 *            源文件/文件夹
	 * @param targetPath
	 *            目的压缩文件保存路径
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping("export.do")
	public void download(String path, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.setContentType("application/octet-stream");
/*		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		InputStream in = new FileInputStream(Const.RESULT_FILE_DIR + File.separator + filename);
		byte[] b = new byte[in.available()];
		in.read(b);
		OutputStream out = response.getOutputStream();
		out.write(b);
		out.flush();
		in.close();*/
		String filename = path.substring(path.lastIndexOf(File.separator)+1);
		ResultFileDAO dao = new ResultFileDAO();
		long a = System.currentTimeMillis();
		Date nowTime = new Date(a);
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String ret = sdFormatter.format(nowTime);
		File file = new File(Const.RESULT_FILE_DIR + File.separator + ret
				+ (UUID.randomUUID()).toString());
		if (!file.exists())
			file.mkdirs();
		File dfile = new File(Const.RESULT_FILE_DIR);
		if (!dfile.exists())
			dfile.mkdirs();
		
		path = Const.APP_CONTEXT_PATH.substring(0,Const.APP_CONTEXT_PATH.lastIndexOf(File.separator))+File.separator+path;
		//根据path取note
		String note = dao.findNoteByPath(path);
		String originalFileId = note.split(Const.ORIGINALFILE_NOTE_SEP)[0].split(":")[1];
		//System.out.println(originalFileId);
		
		//视频文件根据originalFileId查找相应的图片的路径
		OriginalFileDAO odao = new OriginalFileDAO();
		List<String> list = odao.findPathsByNote(originalFileId);
		for (Iterator<File> iter = FileUtils.iterateFiles(dfile, null, true); iter.hasNext();) {
			File eachFile = iter.next();
			String eachName = eachFile.getName();
			if(filename.equals(eachName)){
				FileUtils.copyFile(new File(Const.RESULT_FILE_DIR
						+ File.separator + filename), new File(file.getPath()
						+ File.separator + filename));

			}
		}
		//图片文件根据originalFileId查找对应图片的路径
		String picPath = odao.findPathById(originalFileId);
		String pic_name = picPath.substring(picPath.lastIndexOf(File.separator)+1);
		if(!pic_name.endsWith("mp4")){
			FileUtils.copyFile(new File(picPath), new File(file.getPath()
					+ File.separator + pic_name));
		}
		
		//跟视频相关的图片转存
		for(int i=0;i<list.size();i++){
			String picName = list.get(i).substring(list.get(i).lastIndexOf(File.separator)+1); 
			FileUtils.copyFile(new File(list.get(i)), new File(file.getPath()
					+ File.separator + picName));
		}
		String Path = this.compressExe(file.getPath());
		File file2 = new File(Path);

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/zip");
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ file2.getName());
		try {
			@SuppressWarnings("unused")
			File f = new File(file2.getPath());
			// 以流的形式下载文件。
			@SuppressWarnings("resource")
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file2.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			OutputStream out = response.getOutputStream();
			out.write(buffer);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @desc 生成压缩文件。 如果是文件夹，则使用递归，进行文件遍历、压缩 如果是文件，直接压缩
	 * @param out
	 *            输出流
	 * @param file
	 *            目标文件
	 * @return void
	 * @throws Exception
	 */
	public void createCompressedFile(ZipOutputStream out, File file, String dir)
			throws Exception {
		// 如果当前的是文件夹，则进行进一步处理
		if (file.isDirectory()) {
			// 得到文件列表信息
			File[] files = file.listFiles();
			// 将文件夹添加到下一级打包目录
			out.putNextEntry(new ZipEntry(dir + "/"));

			dir = dir.length() == 0 ? "" : dir + "/";

			// 循环将文件夹中的文件打包
			for (int i = 0; i < files.length; i++) {
				createCompressedFile(out, files[i], dir + files[i].getName()); // 递归处理
			}
		} else { // 当前的是文件，打包处理
			// 文件输入流
			FileInputStream fis = new FileInputStream(file);

			out.putNextEntry(new ZipEntry(dir));
			// 进行写操作
			int j = 0;
			byte[] buffer = new byte[1024];
			while ((j = fis.read(buffer)) > 0) {
				out.write(buffer, 0, j);
			}
			// 关闭输入流
			fis.close();
		}
	}

	/**
	 * 压缩并导出文件
	 * 
	 * @param zipPath
	 *            压缩文件临时路径 路径最后不要有 /
	 * @param zipName
	 *            压缩为文件名 **.zip
	 * @param createFilesPath
	 *            需要压缩的文件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@Autowired
	HttpServletResponse response;

	@RequestMapping("Compressed.do")
	public void downloadZip(HttpServletRequest request,
			HttpServletResponse response) {
		long a = System.currentTimeMillis();
		Date nowTime = new Date(a);
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String ret = sdFormatter.format(nowTime);
		String pathString = request.getParameter("path");
		String[] path = pathString.split(",");
		ResultFileDAO dao = new ResultFileDAO();
		File file = new File(Const.RESULT_FILE_DIR + File.separator + ret
				+ (UUID.randomUUID()).toString());
		if (!file.exists())
			file.mkdirs();
		String resultPath = Const.RESULT_FILE_DIR+File.separator+ret+ (UUID.randomUUID()).toString();
		File resultFile = new File(resultPath);
		if(!resultFile.exists()){
			resultFile.mkdirs();
		}
		String originalFileId = "";
		for (int i = 0; i < path.length; i++) {
			File dfile = new File(Const.RESULT_FILE_DIR);
			if (!dfile.exists())
				dfile.mkdirs();
			path[i] = path[i].replaceAll("\\\\", "/");
			String[] npath = path[i].split("/");
			String pathname = npath[npath.length - 1];
			String tmpPath = Const.RESULT_FILE_DIR + File.separator + pathname;
			//根据path取note
			String note = dao.findNoteByPath(tmpPath);
			originalFileId = note.split(Const.ORIGINALFILE_NOTE_SEP)[0].split(":")[1];
			try {
				File ofile = new File(resultPath+File.separator+originalFileId);
				if(!ofile.exists()){
					ofile.mkdirs();
				}
				FileUtils.copyFile(new File(tmpPath), new File(resultPath+File.separator+originalFileId+File.separator+pathname));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//根据originalFileId查找相应的图片的路径
			OriginalFileDAO odao = new OriginalFileDAO();
			List<String> list = odao.findPathsByNote(originalFileId);
			
			//图片文件根据originalFileId查找对应图片的路径
			String picPath = odao.findPathById(originalFileId);
			String pic_name = picPath.substring(picPath.lastIndexOf(File.separator)+1);
			try {
				if(!pic_name.endsWith("mp4")){
					FileUtils.copyFile(new File(picPath), new File(resultPath+File.separator+originalFileId
							+ File.separator + pic_name));
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//跟视频相关的图片转存
			for(int j=0;j<list.size();j++){
				String picName = list.get(j).substring(list.get(j).lastIndexOf(File.separator)+1); 
				try {
					FileUtils.copyFile(new File(list.get(j)), new File(resultPath+File.separator+originalFileId+File.separator+picName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		String Path = this.compressExe(resultPath);
		File file2 = new File(Path);

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/zip");
		response.setHeader("Content-Type", "application/zip");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ file2.getName());
		try {
			
			File f = new File(file2.getPath());
			// 以流的形式下载文件。
			
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file2.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			OutputStream out = response.getOutputStream();
			out.write(buffer);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 以压缩文件导出
	 * 
	 * @param fileName
	 * @param filePath
	 * @param response
	 */

	public static void downloadFile(String filePath, String fileName,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");

		try {
			File file = new File(filePath);
			System.out.println(file.getPath());
			// 以流的形式下载文件。
			BufferedInputStream fis = new BufferedInputStream(
					new FileInputStream(file.getPath()));

			byte[] buffer = new byte[fis.available()];

			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/zip");
			response.setHeader("Content-Type", "application/zip");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String compressExe(String srcPathName) {
		File zipFile = new File(srcPathName + ".zip");
		if (!zipFile.exists()) {

			zipFile.getParentFile().mkdirs();
		}
		File srcdir = new File(srcPathName);
		if (!srcdir.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		}

		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); //排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
		return zipFile.getAbsolutePath();
	}

	public void compressExe1(String srcPathName) {
		File zipFile = new File("D:\\dl\\dl.zip");
		if (!zipFile.exists()) {

			zipFile.getParentFile().mkdirs();
		}
		File file = new File(srcPathName);
		if (!file.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compressByType(file, out, basedir);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("执行压缩操作时发生异常:"+e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private void compressByType(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			// logger.info("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		}
	}

	/**
	 * 压缩一个目录
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compressByType(files[i], out, basedir + dir.getName() + "/");
		}
	}

	public void downloadFile1(URL theURL, String filePath) throws IOException {
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {// 文件路径不存在时，自动创建目录
			dirFile.mkdir();
		}
		// 从服务器上获取图片并保存
		URLConnection connection = theURL.openConnection();
		InputStream in = connection.getInputStream();
		FileOutputStream os = new FileOutputStream(filePath);
		byte[] buffer = new byte[4 * 1024];
		int read;
		while ((read = in.read(buffer)) > 0) {
			os.write(buffer, 0, read);
		}
		os.close();
		in.close();

	}
}
