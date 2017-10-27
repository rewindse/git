package vm.services;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import vm.db.dao.OriginalFileDAO;
import vm.db.po.OriginalFile;


public class FileService {
	OriginalFileDAO dao=new OriginalFileDAO();
	
	public void delete(String oriID) {
		OriginalFile orifile = dao.findByOriginalFileID(oriID);
		if(!orifile.isParent()){
	    	dao.deleteByOriginalFileId(oriID);
		}else{
			deletePnode(oriID);
		}	
	}
	
	private void deletePnode(String oriID) {
		dao.deleteByOriginalFileId(oriID);
		List<OriginalFile> orifileList = dao.findTreeByAll(oriID);
		if(orifileList!=null&&orifileList.size()>0){
			for(int i=0;i<orifileList.size();i++){
				if(orifileList.get(i).isParent()){
					dao.deleteByOriginalFileId(orifileList.get(i).getOriginalFileID());
					deletePnode(orifileList.get(i).getOriginalFileID());
				}else{
					dao.deleteByOriginalFileId(orifileList.get(i).getOriginalFileID());
				}
			}
		}
	}
	
	public List<Map<String, Object>> getTree(String id) {
		   List<Map<String,Object>> list1 = new Vector<Map<String,Object>>();
		   if(id==null||"".equals(id)){
     		   id="";
     	   }
     	   List<OriginalFile> list = dao.findTreeByAll(id); 
     	   Map<String,Object> map = null;
     	   for(OriginalFile m1 : list){
               map = new HashMap<String, Object>();
        	   map.put("id", m1.getOriginalFileID());
        	   map.put("name", m1.getName());
        	   map.put("pid", m1.getPid());
        	   map.put("path", m1.getPath().substring(m1.getPath().indexOf("store")));
        	   map.put("isParent", m1.isParent());//是否是父节点
        	   map.put("chkDisabled", false);
        	   list1.add(map);   	
	      }
		  return list1;
	}
	
	public List<Map<String, Object>> getSrc(String id, Boolean isParent) {
		List<Map<String, Object>> json = new Vector<Map<String,Object>>();
		 if(isParent){
    	     String path1 = null;     		  
    		 List<OriginalFile> list = dao.findTreeByAll(id);    		 
			 if(list!=null&&list.size()>0){
		         for (int i=0;i<list.size();i++){	
			         Map<String, Object> user = new Hashtable<String, Object>();
					 user.put("sortindex",i+"");
					 user.put("name", list.get(i).getName());
					 user.put("id", list.get(i).getOriginalFileID());
				     if(list.get(i).getPath()!=null&&list.get(i).getPath().length()>0){
				        path1 = list.get(i).getPath().replaceAll("\\\\", "\\\\\\\\");
					 }
				     user.put("path", path1);
					 user.put("isParent", list.get(i).isParent());
				     json.add(user);
				 } 
			 }			  
    	 }else{
    		 OriginalFile orifile = dao.findByOriginalFileID(id);
     		 String path = orifile.getPath();
			 String name = orifile.getName();
			 String path1 = null;
			 if(path!=null&&path.length()>0){
				 path1 = path.replaceAll("\\\\", "\\\\\\\\");
			 }  
	  		 Map<String, Object> user = new Hashtable<String, Object>();
			 user.put("name", name);
			 user.put("id", id);
             user.put("path", path1); 
             user.put("isParent", false);
             json.add(user);
    	 }
		return json;
	}
	
	public Boolean rename(String newname, String oriID) {
		OriginalFile orifile = dao.findByOriginalFileID(oriID);
		String path = orifile.getPath();
		File oldfile = new File(path);
		String path1 = path.substring(0,path.lastIndexOf(File.separator));
        File newfile = new File(path1+File.separator+newname);
	    String a = dao.isExistFileByPath(path1+File.separator+newname);
	    if("0".equals(a)){
		oldfile.renameTo(newfile);
		String newpath = path1+File.separator+newname;
		dao.updateOrifileByID(newname,newpath,oriID);
		return true;
	    }
	    return false;
	}
	
	 public Boolean mkdir(String id, String dirName, String orgId) {
		OriginalFile orifile = new OriginalFile();
		OriginalFile pfile = dao.findByOriginalFileID(id);
	    String a = dao.isExistFileByPath(pfile.getPath()+File.separator+dirName);
	    if("0".equals(a)){
		long time =System.currentTimeMillis();
        orifile.setOriginalFileID(orgId);
        orifile.setPath(pfile.getPath()+File.separator+dirName);
        orifile.setCtime(time);
        orifile.setNote("");
        orifile.setUtime(time);
        orifile.setFlag(1);
        orifile.setPid(id);
        orifile.setTaskID("");
        orifile.setParent(true);
        orifile.setName(dirName);
        dao.insert(orifile);  
		File file = new File(pfile.getPath()+File.separator+dirName);
        file.mkdir();
        return true;
	    }
	    return false;
	}

	public void saveFile(MultipartFile[] files, String id) {
		OriginalFile orifile = dao.findByOriginalFileID(id);
		String path1 = orifile.getPath().substring(0,orifile.getPath().lastIndexOf(File.separator)+orifile.getName().length()+1);
		for(MultipartFile file : files){
			if(file != null){
				String filename = file.getOriginalFilename();
				if(filename.contains(".")){
					String type = filename.substring(filename.lastIndexOf(".")).toLowerCase();
					filename = filename.substring(0,filename.lastIndexOf("."))+type;
				}
				String path = path1+File.separator+filename;
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.InsertOrifile(path, id);
			}
		}
		
	}
	  public void InsertOrifile(String path,String pid){
   	     OriginalFileDAO dao = new OriginalFileDAO(); 
	     String a = dao.isExistFileByPath(path);
	     if("0".equals(a)){
	          OriginalFile orifile = new OriginalFile();
              long time =System.currentTimeMillis();
              String name = path.substring(path.lastIndexOf(File.separator)+1);  
              orifile.setOriginalFileID(UUID.randomUUID().toString());
              orifile.setPath(path);
              orifile.setCtime(time);
              orifile.setNote("");
              orifile.setUtime(time);
              orifile.setFlag(1);
              orifile.setPid(pid);
              orifile.setTaskID("");
              orifile.setParent(false);
              orifile.setName(name);
              dao.insert(orifile);  
	        }
       }

	public List<Map<String, Object>> selectAll() {
		List<OriginalFile> orifile = dao.selectAll();
		List<Map<String,Object>> json = new Vector<Map<String,Object>>();
  	    Map<String,Object> map = null;
  	    for(OriginalFile m1 : orifile){
           map = new HashMap<String, Object>();
     	   map.put("id", m1.getOriginalFileID());
     	   map.put("name", m1.getName());
     	   map.put("path", m1.getPath().substring(m1.getPath().indexOf("store")));
     	   map.put("pid", m1.getPid());
     	   map.put("isParent", m1.isParent());//是否是父节点
     	   map.put("chkDisabled", false);
			if(m1.isParent()){
				map.put("icon", "assets/yangxiao/css/img/2_file.png");
			}else{
		     	String name = m1.getName();
				String type = name.substring(name.lastIndexOf(".")+1);
				if(type.toLowerCase().equals("jpg")){
					map.put("icon","assets/yangxiao/css/img/2.png");
				}else{
					map.put("icon","assets/yangxiao/css/img/4.png");
				}
			}
     	   json.add(map);   	
	      }
		  return json;
	}
}
