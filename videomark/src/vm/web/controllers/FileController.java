/**
 * 
 */
package vm.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import vm.services.FileService;

@Controller
@RequestMapping("/file")
public class FileController {
	private FileService fileService = new FileService();

	@RequestMapping(value="/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String oriID = request.getParameter("oriID");
		fileService.delete(oriID);
 	    PrintWriter out = response.getWriter(); 
		out.write("[{\"oriID\":\""+oriID+"\"}]");
		out.flush();
	}
	
	@RequestMapping(value="/get_tree")
	@ResponseBody
	public String getTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "text/plain; charset=utf-8");
		String id = request.getParameter("id");
	    List<Map<String,Object>> list1 = fileService.getTree(id);  		
	    return JSONArray.fromObject(list1).toString();
	}
	
	@RequestMapping(value = "/get_src")
	@ResponseBody
	public String getSrc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 response.setCharacterEncoding("utf-8");
         response.setHeader("contentType", "application/json; charset=utf-8");
         String id = request.getParameter("id");
         Boolean isParent = Boolean.parseBoolean(request.getParameter("isParent"));
         List<Map<String,Object>> json= fileService.getSrc(id,isParent);
         return JSONArray.fromObject(json).toString();
	}
	
	@RequestMapping(value = "/rename")
	@ResponseBody
	public void rename(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newname = request.getParameter("newname");
        String oriID = request.getParameter("oriID");
        response.setCharacterEncoding("utf-8");
        boolean flag = fileService.rename(newname,oriID);
 	    PrintWriter out = response.getWriter(); 
		out.write("[{\"flag\":\""+flag+"\"}]");
		out.flush();
	}	
	
	@RequestMapping(value="/mkdir")
	@ResponseBody
	public void mkdir(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String dirName = request.getParameter("dirName");
        String orgId = request.getParameter("orgId");
        response.setCharacterEncoding("utf-8");
        Boolean a = fileService.mkdir(id,dirName,orgId);
		PrintWriter out = response.getWriter(); 
		out.write("[{\"a\":\""+a+"\"}]");
		out.flush();
	}
	
	@RequestMapping(value="/upload")
	@ResponseBody
	public void upload(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="files", required=false) MultipartFile files[]) throws IOException {
	     String id = request.getParameter("id");
	     fileService.saveFile(files,id);
		 PrintWriter out = response.getWriter(); 
		 out.write("[{\"id\":\""+id+"\"}]");
		 out.flush();
	}
	@RequestMapping(value="/get_alltree")
	@ResponseBody
	public String getAllTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
        response.setHeader("contentType", "application/json; charset=utf-8");
		List<Map<String,Object>> json = fileService.selectAll();
        return JSONArray.fromObject(json).toString();
	}
}
