package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.taskFile;


public class TaskFileDAO {
	private DBConn dbConn = null;

	public TaskFileDAO() {
		dbConn = new DBConn();
	}

	
	public boolean insert(taskFile originalfile) {
		boolean executeResultFlag = false;
		String sql = "insert into taskfile values(?, ?, ?, ?, ?, ?, ?,?,?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, originalfile.getTaskFileID());
			ps.setLong(2, originalfile.getCtime());
			ps.setLong(3, originalfile.getUtime());
			ps.setString(4, originalfile.getNote());
			ps.setInt(5, originalfile.getFlag());
			ps.setString(6, originalfile.getPath());
			ps.setString(7, originalfile.getName());
			ps.setString(8, originalfile.getPid());
			ps.setBoolean(9, originalfile.isParent());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}
	public String isExistFileByPath(String filepath) {
		String sql ="select count(*) as flag from taskfile where path=? ";
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
		String sql ="select taskFileID from taskfile where path = ? and flag = 1 ";
		PreparedStatement ps;
		String id = null;
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, path);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString("taskFileID");
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


	public List<taskFile> findTreeById(String id) {
		
		List<taskFile> list = new ArrayList<taskFile>();  
		String sql = "select * from taskfile where flag <> 0 and pid = '"+id+"';";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			   while (rs.next()) { 
				taskFile orifile = new taskFile();
				orifile.setTaskFileID(rs.getString(1));
				orifile.setCtime(rs.getLong(2));
				orifile.setUtime(rs.getLong(3));
				orifile.setNote(rs.getString(4));
				orifile.setFlag(rs.getInt(5));
                orifile.setPath(rs.getString(6));
                orifile.setName(rs.getString(7));
                orifile.setPid(rs.getString(8));
				int i = Integer.parseInt(rs.getString(9));
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
		String sql ="select path from taskfile where taskFileID=? ";
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
		String sql ="select name from taskfile where taskFileID=? ";
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
}
