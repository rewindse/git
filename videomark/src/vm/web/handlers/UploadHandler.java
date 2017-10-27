package vm.web.handlers;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import vm.db.dao.OriginalFileDAO;
import vm.db.po.OriginalFile;
import vm.util.Const;


@Component 
@Controller
public class UploadHandler{

	@RequestMapping(value = "/upload.do", method = RequestMethod.POST)
	public ModelAndView upload(@RequestParam("files") MultipartFile[] files) {
		if (files != null) {
			for (MultipartFile file : files) {
				try {
					String tmpname = file.getOriginalFilename();
					String type = tmpname.substring(tmpname.lastIndexOf(".")).toLowerCase();
					String filename = tmpname.substring(0, tmpname.lastIndexOf(".")) + type;
					String path = Const.SOURCE_FILE_DIR + File.separator + filename;
					System.out.println(path);
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
					this.InsertOrifile(path, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ModelAndView model = new ModelAndView("fileupload2");
		return model;
	}
   
   
	@RequestMapping(value = "getJson.do", produces = "application/json;charset=utf-8")
	public void getJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("contentType", "application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		OriginalFileDAO dao = new OriginalFileDAO();
		List<OriginalFile> list = dao.selectAll();
		List<Map<String, String>> json = new Vector<Map<String, String>>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> user = new Hashtable<String, String>();
				String[] path = list.get(i).getPath().split(File.separator.replaceAll("\\\\", "\\\\\\\\"));
				String filename = path[path.length - 1];
				user.put("sortindex", i + "");
				user.put("filename", filename);
				user.put("id", list.get(i).getOriginalFileID());
				json.add(user);
			}
		}
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("[");

		for (Iterator<Map<String, String>> iter = json.iterator(); iter.hasNext();) {
			Map<String, String> user = iter.next();
			sbuf.append("{");
			for (Iterator<Map.Entry<String, String>> uIter = user.entrySet().iterator(); uIter.hasNext();) {
				Map.Entry<String, String> entry = uIter.next();
				sbuf.append("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
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
		out.write(sbuf.toString());
		out.flush();

	}
    
	@RequestMapping(value = "getTree.do")
	@ResponseBody
	public String getTree(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
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
			if (!dao.isExistPath(Const.SOURCE_FILE_DIR)) {
				dao.insert(o);
			}

			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> map3 = new HashMap<>();
			map3.put("id", o.getOriginalFileID());
			map3.put("name", o.getName());
			map3.put("path", o.getPath());
			map3.put("pid", o.getPid());
			map3.put("isParent", o.isParent());// 是否是父节点
			list1.add(map3);
			return JSONArray.fromObject(list1).toString();
		}
		String path = dao.findPathById(id);
		File source = new File(path.replace("\\\\", "\\\\\\\\"));
		File[] files = source.listFiles();

		for (int i = 0; i < files.length; i++) {
			String sonpath = files[i].getPath();
			if (dao.isExistPath(sonpath)) {
				continue;
			}
			OriginalFile of = new OriginalFile();
			of.setCtime(System.currentTimeMillis());
			of.setFlag(1);
			of.setName(sonpath.substring(sonpath.lastIndexOf(File.separator) + 1));
			of.setNote("");
			of.setOriginalFileID(UUID.randomUUID().toString());
			if (files[i].isDirectory()) {
				of.setParent(true);
			} else if (files[i].isFile()) {
				of.setParent(false);
			}
			of.setPath(sonpath);
			of.setPid(id);
			of.setTaskID("");
			of.setUtime(System.currentTimeMillis());
			dao.insert(of);
		}
		Map<String, Object> map1 = null;
		List<OriginalFile> list = dao.findTreeByAll(id);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (OriginalFile m1 : list) {
			map1 = new HashMap<String, Object>();
			map1.put("id", m1.getOriginalFileID());
			map1.put("name", m1.getName());
			map1.put("path", m1.getPath());
			map1.put("pid", m1.getPid());
			map1.put("isParent", m1.isParent());
			list1.add(map1);
		}
		return JSONArray.fromObject(list1).toString();
	}
        
	@RequestMapping(value = "getSrc.do")
	public void getSrc(String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("contentType", "application/json; charset=utf-8");
		OriginalFileDAO dao = new OriginalFileDAO();
		String path = dao.finePathByID(id);
		String path1 = null;
		if (path != null && path.length() > 0) {
			path1 = path.replaceAll("\\\\", "\\\\\\\\");
		}
		PrintWriter out = response.getWriter();
		out.write("[{\"path\":\"" + path1 + "\"}]");
		out.flush();

	}

	public void InsertOrifile(String path, String json_note) {
		OriginalFileDAO dao = new OriginalFileDAO();
		String a = dao.isExistFileByPath(path);
		if ("0".equals(a)) {
			OriginalFile orifile = new OriginalFile();
			long time = System.currentTimeMillis();
			String name = path.substring(path.lastIndexOf(File.separator) + 1);
			orifile.setOriginalFileID(UUID.randomUUID().toString());
			orifile.setPath(path);
			orifile.setCtime(time);
			orifile.setNote(json_note);
			orifile.setUtime(time);
			orifile.setFlag(1);
			orifile.setPid("0");
			orifile.setTaskID("");
			orifile.setParent(false);
			orifile.setName(name);
			dao.insert(orifile);
		}
	}
}