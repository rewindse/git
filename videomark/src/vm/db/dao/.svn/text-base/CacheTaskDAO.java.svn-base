package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.CacheTask;

public class CacheTaskDAO {

	DBConn dbConn = null;
	
	public CacheTaskDAO() {
		this.dbConn = new DBConn();
	}
	
	public boolean insert(CacheTask cacheTask) {
		boolean isInsert = false;
		String sql = "insert into cachetask values(?,?, ?);";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, cacheTask.getTaskID());
			ps.setString(2, cacheTask.getJson());
			ps.setString(3, cacheTask.getOriginalFileId());
			isInsert = ps.executeUpdate()  != 1 ? false : true;
			this.dbConn.release();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isInsert;
	}
	
	public List<CacheTask> findCacheTaskByTaskID(String taskId) {
		List<CacheTask> cacheTaskList = new ArrayList<CacheTask>();
		String sql = "select * from cachetask where taskid = ?;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, taskId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CacheTask cacheTask = new CacheTask();
				cacheTask.setTaskID(rs.getString("taskid"));
				cacheTask.setJson(rs.getString("json"));
				cacheTaskList.add(cacheTask);
			}
			this.dbConn.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cacheTaskList;
	}
	
	public boolean updateCacheTaskById(CacheTask oldCahceTask, CacheTask cacheTask) {
		boolean isUpdate = false;
		String sql = "update cachetask set json = ? where originalFileId = ?;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			
			ps.setString(1, cacheTask.getJson());
			ps.setString(2, cacheTask.getOriginalFileId());
			isUpdate = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdate;
	}
	public List<CacheTask> findCacheTaskByOriginalFileID(String originalFileId) {
		List<CacheTask> cacheTaskList = new ArrayList<CacheTask>();
		String sql = "select * from cachetask where originalFileId = ?;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, originalFileId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CacheTask cacheTask = new CacheTask();
				cacheTask.setTaskID(rs.getString("taskid"));
				cacheTask.setJson(rs.getString("json"));
				cacheTaskList.add(cacheTask);
			}
			this.dbConn.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cacheTaskList;
	}
}
