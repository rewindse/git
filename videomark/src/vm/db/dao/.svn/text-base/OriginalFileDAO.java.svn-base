package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vm.db.common.DBConn;
import vm.db.po.OriginalFile;



public class OriginalFileDAO {
	private DBConn dbConn = null;

	public OriginalFileDAO() {
		dbConn = new DBConn();
	}

	public boolean insert(OriginalFile originalfile) {
		boolean executeResultFlag = false;
		String sql = "insert into originalfile values(?, ?, ?, ?, ?, ?, ?,?,?,?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, originalfile.getOriginalFileID());
			ps.setLong(2, originalfile.getCtime());
			ps.setLong(3, originalfile.getUtime());
			ps.setString(4, originalfile.getNote());
			ps.setInt(5, originalfile.getFlag());
			ps.setString(6, originalfile.getPath());
			ps.setString(7, originalfile.getTaskID());
			ps.setString(8, originalfile.getName());
			ps.setString(9, originalfile.getPid());
			ps.setBoolean(10, originalfile.isParent());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}

	public boolean deleteByOriginalFileId(String originalFileID) {
		if (originalFileID == null || "".equals(originalFileID)) {
			return false;
		}
		OriginalFile originalfile = this.findByOriginalFileID(originalFileID);
		originalfile.setFlag(0); // update the field value of flag is 0
		return this.updateByOriginalFileId(originalFileID, originalfile);
	}

	// update methods
	public boolean updateByOriginalFileId(String originalFileID,OriginalFile originalfile) {
		String sql = "update originalfile set ctime = ?, utime = ?, note = ?, "
				+ " flag = ?, path = ?, taskID = ? , name =? , pid = ? , isParent = ? where originalFileID = ? and flag <> 0";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setLong(1, originalfile.getCtime());
			ps.setLong(2, originalfile.getUtime());
			ps.setString(3, originalfile.getNote());
			ps.setInt(4, originalfile.getFlag());
			ps.setString(5, originalfile.getPath());
			ps.setString(6, originalfile.getTaskID());
			ps.setString(7, originalfile.getName());
			ps.setString(8, originalfile.getPid());
			ps.setBoolean(9, originalfile.isParent());
			ps.setString(10, originalfile.getOriginalFileID());
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
	public OriginalFile findByOriginalFileID(String originalFileID) {
		if (originalFileID == null || "".equals(originalFileID)) {
			return null;
		}
		String sql = "select * from originalfile where originalFileID = ? and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, originalFileID);
			ResultSet rs = ps.executeQuery();
			OriginalFile originalfile=new OriginalFile();
			if (rs.next()) {
				String ofid = rs.getString("originalFileID");
				originalfile.setOriginalFileID(ofid == null ? "" : ofid);
				originalfile.setCtime(rs.getLong("ctime"));
				originalfile.setUtime(rs.getLong("utime"));
				originalfile.setNote(rs.getString("note"));
				originalfile.setFlag(rs.getInt("flag"));
				originalfile.setName(rs.getString("name"));
				originalfile.setParent(rs.getBoolean("isParent"));
				originalfile.setPid(rs.getString("pid"));
				String path = rs.getString("path");
				originalfile.setPath(path == null ? "" : path);
				String taskid = rs.getString("taskid");
				originalfile.setTaskID(taskid == null ? "" : taskid);
				
			}
			rs.close();
			this.dbConn.release();
			return originalfile;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<OriginalFile> selectAll() {
		List<OriginalFile> list = new ArrayList<OriginalFile>();  
		String sql = "select * from originalfile where flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			   while (rs.next()) { 
				OriginalFile orifile=new OriginalFile();
				String orifileid = rs.getString("originalFileID");
				orifile.setOriginalFileID(orifileid == null ? "" : orifileid);
				orifile.setCtime(Long.parseLong(rs.getString(2)));
				orifile.setUtime(Long.parseLong(rs.getString(3)));
				orifile.setNote(rs.getString(4));
				orifile.setFlag(Integer.parseInt(rs.getString(5)));
				orifile.setPath(rs.getString(6));
				orifile.setTaskID(rs.getString(7));
				orifile.setName(rs.getString(8));
				orifile.setPid(rs.getString(9));
				orifile.setParent(rs.getBoolean(10));
			    list.add(orifile);
			   } 
			   rs.close();
			this.dbConn.release();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findPathById(String oID) {
		if (oID == null || "".equals(oID)) {
			return null;
		}
		String sql = "select path from originalfile where originalFileID = ? ";
		String path=null;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, oID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				path = rs.getString("path");
			}			
			OriginalFile originalfile=new OriginalFile();
			originalfile.setPath(path == null ? "" : path);
			rs.close();
			this.dbConn.release();
			return path;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean UpdatePathById(String oID ,String delPath) {
		String sql = "update originalfile set path = ? where originalFileID = ? ";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, delPath);
			ps.setString(2, oID);
		    isUpdated = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	public List<OriginalFile> findOriginalByTask(String taskID) {
		List<OriginalFile> originalFileList = new ArrayList<OriginalFile>();
		String sql ="select * from originalfile where taskID = '"+taskID+"' and flag <> 0";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OriginalFile of = new OriginalFile();
				of.setOriginalFileID(rs.getString("originalFileID"));
				of.setCtime(rs.getLong("ctime"));
				of.setUtime(rs.getLong("utime"));
				of.setFlag(rs.getInt("flag"));
				of.setNote(rs.getString("note"));
				of.setPath(rs.getString("path"));
				of.setTaskID(rs.getString("taskID"));
				of.setName(rs.getString("name"));
				of.setPid(rs.getString("pid"));
				of.setParent(rs.getBoolean("isParent"));
				originalFileList.add(of);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return originalFileList;
	}


	public List<Map<String,Object>> findNameById(String id) {
		String sql ="select id,name,path,isParent from tree_data where id = '"+id+"' and flag='1'";
		PreparedStatement ps;
		Map<String,Object> map = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				map = new HashMap<String,Object>();
				map.put("id", rs.getString("id"));
				map.put("name", rs.getString("name"));
				map.put("path", rs.getString("path"));
				map.put("isParent", rs.getString("isParent"));
				list.add(map);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Boolean saveTreeData(Map<String, String> map1) {
		boolean executeResultFlag = false;
		String sql = "insert into tree_data values(?,?,?,?,?);";
			try {
				PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, map1.get("id"));
				ps.setString(2, map1.get("name"));
				ps.setString(3, map1.get("path"));
				ps.setString(4, map1.get("isParent"));
				ps.setString(5, map1.get("flag"));
				executeResultFlag = ps.executeUpdate() != 1 ? false : true;
				this.dbConn.release();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return executeResultFlag;
	}

	public String isExistData(Map<String, String> map1) {
		String sql ="select count(*) as flag from tree_data where id = '"+map1.get("id")+"' and name='"+map1.get("name")+"' and flag='1' ";
		PreparedStatement ps;
		String a = null;
			try {
				ps = this.dbConn.getConn().prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					a = rs.getString("flag");
				}
				rs.close();
				this.dbConn.release();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return a;
	}

	
	public boolean deletAllTaskID (String taskID) {
		boolean isCheck = false;
		String sql = "update originalfile set taskID = ? where taskID = ? ";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, taskID);
		    isCheck = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCheck;
	}

	public boolean updateFlagByName(String name) {
		String sql = "update tree_data set flag = ? where name = ? ";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, "0");
			ps.setString(2, name);
		    isUpdated = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public String isExistFileByPath(String filepath) {
		String sql ="select count(*) as flag from originalfile where path=? and flag='1'";
		PreparedStatement ps;
		String a = null;
			try {
				ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, filepath);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					a = rs.getString("flag");
				}
				rs.close();
				this.dbConn.release();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return a;
	}
	public String finePidByPath(String path) {
		String sql ="select originalFileID from originalfile where path = ? and flag = 1 ";
		PreparedStatement ps;
		String id = null;
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, path);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString("originalFileID");
			}
			rs.close();	
			this.dbConn.release();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public List<OriginalFile> findTreeById(String id,String taskId) {
		
		List<OriginalFile> list = new ArrayList<OriginalFile>();  
		String sql = "select * from originalfile where flag <> 0 and pid = '"+id+"' and taskID='"+taskId+"';";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			   while (rs.next()) { 
				OriginalFile orifile = new OriginalFile();
				orifile.setOriginalFileID(rs.getString(1));
				orifile.setCtime(rs.getLong(2));
				orifile.setUtime(rs.getLong(3));
				orifile.setNote(rs.getString(4));
				orifile.setFlag(rs.getInt(5));
                orifile.setPath(rs.getString(6));
                orifile.setTaskID(rs.getString(7));
                orifile.setName(rs.getString(8));
                orifile.setPid(rs.getString(9));
				int i = Integer.parseInt(rs.getString(10));
				orifile.setParent(i == 1 ? true : false);
			    list.add(orifile);
			   }  	
			rs.close();
			this.dbConn.release();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String finePathByID(String id) {
		String sql ="select path from originalfile where originalFileID=? ";
		PreparedStatement ps;
		String path = null;
			try {
				ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					path = rs.getString("path");
				}
				rs.close();
				this.dbConn.release();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return path;
	}
	public String fineNameByID(String id) {
		String sql ="select name from originalfile where originalFileID=? ";
		PreparedStatement ps;
		String name = null;
			try {
				ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					name = rs.getString("name");
				}
				rs.close();
				this.dbConn.release();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return name;
	}

	public boolean isExistPath(String path) {
		String sql = "select * from originalfile where path = ?";
		boolean isExist = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, path);
			ResultSet rs = ps.executeQuery();
			isExist = rs.next();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	public static void main(String[] args) {
		OriginalFileDAO of = new OriginalFileDAO();
		
		of.isExistPath("dasd");
	}
	//
public List<OriginalFile> findTreeByAll(String id) {
		
		List<OriginalFile> list = new ArrayList<OriginalFile>();  
		String sql = "select * from originalfile where flag <> 0 and pid = '"+id+"';";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			   while (rs.next()) { 
				OriginalFile orifile = new OriginalFile();
				orifile.setOriginalFileID(rs.getString(1));
				orifile.setCtime(rs.getLong(2));
				orifile.setUtime(rs.getLong(3));
				orifile.setNote(rs.getString(4));
				orifile.setFlag(rs.getInt(5));
                orifile.setPath(rs.getString(6));
                orifile.setTaskID(rs.getString(7));
                orifile.setName(rs.getString(8));
                orifile.setPid(rs.getString(9));
				int i = Integer.parseInt(rs.getString(10));
				orifile.setParent(i == 1 ? true : false);
			    list.add(orifile);
			   }  	
			rs.close();
			this.dbConn.release();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<String> findPathsByNote(String OriginalFileId){
		List<String> list = new ArrayList<String>();
		String sql = "select path from originalfile where note=?";
		PreparedStatement ps;
		String path = "";
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, OriginalFileId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				path = rs.getString("path");
				list.add(path);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	public Boolean updateOrifileByID(String newname, String newpath,String oriID) {	
			String sql = "update originalfile set path = ?, name =? where originalFileID = ? and flag <> 0";
			boolean isUpdated = false;
			try {
				PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, newpath);
				ps.setString(2, newname);
				ps.setString(3, oriID);
			    isUpdated = ps.executeUpdate() == 1 ? true : false;
				this.dbConn.release();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return isUpdated;
		}
}



