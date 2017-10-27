package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.TaskStatus;

public class TaskStatusDAO {
	
	private DBConn dbConn = null;
	
	public TaskStatusDAO() {
		dbConn = new DBConn();
	}
	
	/*
	 * insert the task
	 * The value of the input is the status
	 * The value of the return is the boolean type value
	 * if the values is the false, This operation failed
	 * if the values is the true, This operation is successful
	 * */
	
	public boolean insert(TaskStatus ts) {
		boolean executeResultFlag = false;
		String sql = "insert into status values(?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, ts.getStatusID());
			ps.setLong(2, ts.getCtime());
			ps.setLong(3, ts.getUtime());
			ps.setString(4, ts.getNote());
			ps.setInt(5, ts.getFlag());
			ps.setString(6, ts.getStatusName());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}
	
	public boolean delet(String statusID) throws SQLException, Exception {
		List<TaskStatus> taskStatusList = this.findByTaskStatusID(statusID);
		
		if(taskStatusList.size() != 1) {
			return false;
		}
		TaskStatus ts  = taskStatusList.get(0);
		return this.updateByTaskStatusID(statusID, ts);
	}
	
	public boolean updateByTaskStatusID(String statusID, TaskStatus newTaskStatus) {
		long currentTimeMillis = System.currentTimeMillis();
		String sql = "update status set utime = '"+currentTimeMillis+"' , flag = 0 where statusID = '"+statusID+"';";
		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			result = ps.executeUpdate();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result > 0) {
			return true;
		}
		return false;
	}
	
	/*
	 * update the task's status
	 * The value of the input is the statusID and statusName
	 * The value of the return is the boolean type value
	 * if the values is the false, This operation failed
	 * if the values is the true, This operation is successful 
	 * */
	public boolean updateTaskStatusName (String statusID, String statusName) {
		long currentTimeMillis = System.currentTimeMillis();
		String sql = "update status set statusname = '"+statusName+"' , utime = '"+currentTimeMillis+"' where statusID = '"+statusID+"' and flag <> 0;";
		int result = 0;
		PreparedStatement ps;
		
		try {
			ps = this.dbConn.getConn().prepareStatement(sql);
			result = ps.executeUpdate();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result > 0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public List<TaskStatus> findByTaskStatusID(String tsID) {
		List<TaskStatus> taskStatusList = new ArrayList<TaskStatus>();
		String sql = new String();
		ResultSet rs;
		
		if(tsID.equals("")){
			sql = "select * from status where flag <> 0;";
		}else {
			sql = "select * from status where statusID = '"+tsID+"' and flag <> 0;";
		}
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while(rs.next()) {
				TaskStatus ts = new TaskStatus();
				String statusID = rs.getString("statusID");
				ts.setStatusID(statusID == null ? "" : statusID);
				ts.setCtime(Long.parseLong(rs.getString(2)));
				ts.setUtime(Long.parseLong(rs.getString(3)));
				ts.setNote(rs.getString(4));
				ts.setFlag(Integer.parseInt(rs.getString(5)));
				ts.setStatusName(rs.getString(6));
				taskStatusList.add(ts);
			}
			this.dbConn.release();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskStatusList;
	}
	
	public List<TaskStatus> findByStatusNname(String statusName) {
		List<TaskStatus> taskStatusList = new ArrayList<TaskStatus>();
		ResultSet rs;
		String sql = "select * from status where statusname = '"+statusName+"' and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while(rs.next()) {
				TaskStatus ts = new TaskStatus();
				String statusID = rs.getString("statusID");
				ts.setStatusID(statusID == null ? "" : statusID);
				ts.setCtime(Long.parseLong(rs.getString(2)));
				ts.setUtime(Long.parseLong(rs.getString(3)));
				ts.setNote(rs.getString(4));
				ts.setFlag(Integer.parseInt(rs.getString(5)));
				ts.setStatusName(rs.getString(6));
				taskStatusList.add(ts);
			}
			this.dbConn.release();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskStatusList;
	}
	
	// unit test main method
	public static void main(String[] args) {
	
	}
}
