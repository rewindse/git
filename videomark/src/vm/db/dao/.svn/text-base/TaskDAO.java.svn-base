package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.TargetType;
import vm.db.po.Task;
import vm.db.po.TaskStatus;
import vm.db.po.User;

public class TaskDAO {
	
	private DBConn dbConn = null;
	
	public TaskDAO() {
		dbConn = new DBConn();
	}
	
	/*
	 * insert the task
	 * The value of the input is the task
	 * The value of the return is the boolean type value
	 * if the values is the false, This operation failed
	 * if the values is the true, This operation is successful
	 * */
	public boolean insert(Task task) {
		boolean executeResultFlag = false;
		String sql = "insert into task values(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, task.getTaskID());
			ps.setString(2, task.getTaskName());
			ps.setLong(3, task.getCtime());
			ps.setLong(4, task.getUtime());
			ps.setString(5, task.getNote());
			ps.setInt(6, task.getFlag());
			ps.setString(7, task.getUserID());
			ps.setString(8, task.getStatusID());
			ps.setString(9, task.getResultTypeID());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}
	
	public boolean delet(String taskID) {
		List<Task> taskList = this.findTask(taskID);
		if(taskList.size() != 1) {
			return false;
		}
		
		Task task  = taskList.get(0);
		return this.updateByTaskID(taskID, task);
	}
	
	public boolean updateByTaskID(String taskID, Task newTask) {
		String sql = "update task set flag = 0 where taskID = '"+taskID+"' ";
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
	
	public List<Task> findTask(String taskID) {
		List<Task> taskList = new ArrayList<Task>();
		String sql = new String();
		ResultSet rs;
		
		if(taskID.equals("")){
			sql = "select * from task where flag <> 0;";
		}else{
			sql = "select * from task where taskID = '"+taskID+"' and flag <> 0; ";
		}
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				String tsID = rs.getString("taskID");
				task.setTaskID(tsID == null ? "" : tsID);
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return taskList;
	}
	
	public boolean updateTask(String taskID, Task newTask) {
		String sql = "update task set taskname = ?, ctime = ?, utime = ?, note = ?,"
				+ " flag = ?, userID = ?, statusID = ?, resultTypeID = ?"
				+ " where taskID = '"+taskID+"' and flag <> 0;";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, newTask.getTaskName());
			ps.setLong(2, newTask.getCtime());
			ps.setLong(3, System.currentTimeMillis());
			ps.setString(4, newTask.getNote());
			ps.setInt(5, newTask.getFlag());
			ps.setString(6, newTask.getUserID());
			ps.setString(7, newTask.getStatusID());
			ps.setString(8, newTask.getResultTypeID());
			isUpdated = ps.executeUpdate() == 1 ? true : false;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	public List<Task> findByUserID(String userID) {
		List<Task> taskList = new ArrayList<Task>();
		String sql = "select * from task where userID = '"+userID+"' and flag <> 0";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	public List<Task> findByUserName(String userName){
		List<Task> taskList =new ArrayList<Task>();
		List<User> userList =null;
		UserDAO userdao= new UserDAO();
		
		userList=userdao.findByUserName(userName);
		if(userList.size()!=1){
			return taskList;
		}
		String sql = "select * from task where userID = '"+userList.get(0).getUserID()+"' and flag <> 0 order by ctime";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;	
	}
	
	public List<Task> findByStatusID(String statusID) {
		List<Task> taskList = new ArrayList<Task>();
		String sql = "select * from task where statusID = '"+statusID+"' and flag <> 0";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	public List<Task> findByStatusName(String statusName){
		List<Task> taskList = new ArrayList<Task>();
		List<TaskStatus> statusList = new ArrayList<TaskStatus>();
		statusList=new TaskStatusDAO().findByStatusNname(statusName);
		if(statusList.size()!=1){
			return taskList;
		}
		String sql = "select * from task where statusID = '"+statusList.get(0).getStatusID()+"' and flag <> 0 order by ctime";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;	
	}
	
	public List<Task> findByCreateTime(String startTime,String endTime){
		List<Task> taskList = new ArrayList<Task>();

		String sql = "select * from task where ctime > '"+startTime+"' and ctime < '"+endTime+"' and flag <> 0 order by ctime";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;	
	}
	
	public List<Task> findByTargetType(String targerTypeName){
		List<Task> taskList = new ArrayList<Task>();
		List<TargetType> targetList = new ArrayList<TargetType>();
		TargetTypeDAO typeDAO = new TargetTypeDAO();
		
		targetList=typeDAO.findByTargetTypeName(targerTypeName);
		if(targetList.size()!=1){
			return taskList;
		}
		String middle_sql = "select * from middletasktargettype where targetTypeID = '"+targetList.get(0).getTargetTypeID()+"' and flag <> 0";
		String task_id="";
		String task_sql="";
		ResultSet task_rs=null;
		try {
			PreparedStatement ps =dbConn.getConn().prepareStatement(middle_sql);
			ResultSet rs = ps.executeQuery(middle_sql);
			while(rs.next()) {
				task_id=rs.getString("taskID");
				task_sql="select * from task where taskID = '"+task_id+"' and flag <> 0 order by ctime";
				ps= dbConn.getConn().prepareStatement(task_sql);
				task_rs=ps.executeQuery();
				if(task_rs.next()){
					Task task = new Task();
					task.setTaskID(task_rs.getString("taskID"));
					task.setTaskName(task_rs.getString("taskname"));
					task.setCtime(task_rs.getLong("ctime"));
					task.setUtime(task_rs.getLong("utime"));
					task.setNote(task_rs.getString("note"));
					task.setFlag(task_rs.getInt("flag"));
					task.setUserID(task_rs.getString("userID"));
					task.setStatusID(task_rs.getString("statusID"));
					task.setResultTypeID(task_rs.getString("resulttypeID"));
					taskList.add(task);
				}	
			}
			rs.close();
			dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;	
	}
	
	public List<Task> findByResultTypeID(String resultTypeID){
		List<Task> taskList = new ArrayList<Task>();
		String sql = "select * from task where resultTypeID = '"+resultTypeID+"' and flag <> 0";
		
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				Task task = new Task();
				task.setTaskID(rs.getString("taskID"));
				task.setTaskName(rs.getString("taskname"));
				task.setCtime(rs.getLong("ctime"));
				task.setUtime(rs.getLong("utime"));
				task.setNote(rs.getString("note"));
				task.setFlag(rs.getInt("flag"));
				task.setUserID(rs.getString("userID"));
				task.setStatusID(rs.getString("statusID"));
				task.setResultTypeID(rs.getString("resulttypeID"));
				taskList.add(task);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;	
	}
	
	public static void main(String[] args) {
		
	}	
}
