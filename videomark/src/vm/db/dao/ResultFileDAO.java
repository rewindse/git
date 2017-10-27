package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.ResultFile;
import vm.db.po.Task;
import vm.util.Decode;

public class ResultFileDAO {
	private DBConn dbConn = null;

	public ResultFileDAO() {
		dbConn = new DBConn();
	}

	public boolean insert(ResultFile resultFile) {
		boolean isInsert = false;
		String sql = "insert into resultfile values(?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, resultFile.getResultFileID());
			ps.setLong(2, resultFile.getCtime());
			ps.setLong(3, resultFile.getUtime());
			ps.setString(4, resultFile.getNote());
			ps.setInt(5, resultFile.getFlag());
			ps.setString(6, resultFile.getPath());
			ps.setString(7, resultFile.getTaskID());
			ps.setString(8, resultFile.getStatusID());
			isInsert = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isInsert;
	}

	public boolean deletByResultFileID(String resultFileID) {
		if (resultFileID == null || "".equals(resultFileID)) {
			return false;
		}

		ResultFile rf = this.findByResultFileID(resultFileID).get(0);
		rf.setFlag(0);

		return this.updateByResultFileID(resultFileID, rf);
	}

	public boolean deletByTaskID(String taskID){
		boolean isDelet = false;
		if(taskID == null || "".equals(taskID)){
			return isDelet;
		}
		String sql = "update resultfile set flag = ? where taskID = ? and flag <> 0";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setString(2, taskID);
			isDelet = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDelet;
	}
	
	public boolean updateByResultFileID(String resultFileID,
			ResultFile resultFile) {
		boolean isUpdate = false;
		String sql = "update resultfile set ctime = ?, utime = ?, note = ?,"
				+ " flag = ?, path = ?, taskID = ?, statusID = ? where resultFileID = ? "
				+ "and flag <> 0;";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setLong(1, resultFile.getCtime());
			ps.setLong(2, System.currentTimeMillis());
			ps.setString(3, resultFile.getNote());
			ps.setInt(4, resultFile.getFlag());
			ps.setString(5, resultFile.getPath());
			ps.setString(6, resultFile.getTaskID());
			ps.setString(7, resultFile.getStatusID());
			ps.setString(8, resultFile.getResultFileID());
			isUpdate = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdate;
	}

	public List<ResultFile> findByResultFileID(String resultFileID) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		String sql;
		if (resultFileID == null || "".equals(resultFileID)) {
			sql = "select * from resultfile where flag <> 0;";
		} else {
			sql = "select * from resultfile where resultfileID = '"
					+ resultFileID + "' and flag <> 0;";
		}

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultFile rf = new ResultFile();
				rf.setResultFileID(rs.getString("resultfileID"));
				rf.setCtime(rs.getLong("ctime"));
				rf.setUtime(rs.getLong("utime"));
				rf.setNote(rs.getString("note"));
				rf.setFlag(rs.getInt("flag"));
				rf.setPath(rs.getString("path"));
				rf.setTaskID(rs.getString("taskID"));
				rf.setStatusID(rs.getString("statusID"));
				resultFileList.add(rf);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFileList;
	}

	public List<ResultFile> findByTaskID(String taskID) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		String sql = "select * from resultfile where taskID = '" + taskID
				+ "' and flag <> 0";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultFile rf = new ResultFile();
				rf.setResultFileID(rs.getString("resultfileID"));
				rf.setCtime(rs.getLong("ctime"));
				rf.setUtime(rs.getLong("utime"));
				rf.setNote(rs.getString("note"));
				rf.setFlag(rs.getInt("flag"));
				rf.setPath(rs.getString("path"));
				rf.setTaskID(rs.getString("taskID"));
				rf.setStatusID(rs.getString("statusID"));
				resultFileList.add(rf);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFileList;
	}
	public List<ResultFile> findByOriginalFileId(String originalFileId) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		String sql = "select * from resultfile where note = '" + originalFileId
				+ "' and flag <> 0";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultFile rf = new ResultFile();
				rf.setResultFileID(rs.getString("resultfileID"));
				rf.setCtime(rs.getLong("ctime"));
				rf.setUtime(rs.getLong("utime"));
				rf.setNote(rs.getString("note"));
				rf.setFlag(rs.getInt("flag"));
				rf.setPath(rs.getString("path"));
				rf.setTaskID(rs.getString("taskID"));
				rf.setStatusID(rs.getString("statusID"));
				resultFileList.add(rf);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFileList;
	}

	public List<ResultFile> findByStatusID(String statusID) {
		List<ResultFile> resultFileList = new ArrayList<ResultFile>();
		String sql = "select * from resultfile where statusID = '" + statusID
				+ "' and flag <> 0";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultFile rf = new ResultFile();
				rf.setResultFileID(rs.getString("resultfileID"));
				rf.setCtime(rs.getLong("ctime"));
				rf.setUtime(rs.getLong("utime"));
				rf.setNote(rs.getString("note"));
				rf.setFlag(rs.getInt("flag"));
				rf.setPath(rs.getString("path"));
				rf.setTaskID(rs.getString("taskID"));
				rf.setStatusID(rs.getString("statusID"));
				resultFileList.add(rf);
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFileList;
	}

	public List<ResultFile> findByTargetType(String targetType) {
		Decode decode= new Decode();
		List<ResultFile> fList = new ArrayList<ResultFile>();
		TaskDAO taskDAO = new TaskDAO();
		List<Task> taskList = taskDAO.findByTargetType(targetType);
		if (taskList.size() < 1) {
			return fList;
		}
		String sql = null;
		String temp=null;
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			for (Task t : taskList) {
				sql = "select * from resultfile where taskID = '"
						+ t.getTaskID() + "' and flag <> 0";
				ps = this.dbConn.getConn().prepareStatement(sql);
				rs = ps.executeQuery(sql);
				if (rs.next()) {
					ResultFile file = new ResultFile();
					file.setResultFileID(rs.getString("resultFileID"));
					file.setCtime(rs.getLong("ctime"));
					file.setUtime(rs.getLong("utime"));
					file.setFlag(rs.getInt("flag"));
					file.setNote(rs.getString("note"));
					file.setPath(rs.getString("path"));
					file.setStatusID(rs.getString("statusID"));
					file.setTaskID(rs.getString("taskID"));
					/*temp=decode.readFile(file.getPath());
					if(!"".equals(temp)){
						if(decode.decodefile(temp, targetType)){
							fList.add(file);
						}
					}*/
					fList.add(file);
				}
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fList;

	}

	public List<ResultFile> getPathListbByTargetType(String targetType) {
		Decode decode =new Decode();
		boolean flag=false;
		String targetString="";
		List<ResultFile> fList = new ArrayList<ResultFile>();
		String sql = null;
		PreparedStatement ps;
		ResultSet rs = null;
		
		try {	
			sql = "select * from resultfile where flag <> 0";
			ps = this.dbConn.getConn().prepareStatement(sql);
			rs = ps.executeQuery(sql);
			while(rs.next()) {
				ResultFile file = new ResultFile();
				file.setResultFileID(rs.getString("resultFileID"));
				file.setCtime(rs.getLong("ctime"));
				file.setUtime(rs.getLong("utime"));
				file.setFlag(rs.getInt("flag"));
				file.setNote(rs.getString("note"));
				file.setPath(rs.getString("path"));
				file.setStatusID(rs.getString("statusID"));
				file.setTaskID(rs.getString("taskID"));
				targetString=decode.readFile(file.getPath());
				if("".equals(targetString)){
					continue;
				}
				flag=decode.decodefile(decode.readFile(file.getPath()), targetType);
				if(flag){
					fList.add(file);
				}
			}
			rs.close();
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fList;

	}
	public String findNoteByPath(String path){
		String sql ="select note from resultfile where path=? ";
		PreparedStatement ps;
		String note = null;
			try {
				ps = this.dbConn.getConn().prepareStatement(sql);
				ps.setString(1, path);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					note = rs.getString("note");
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
		return note;
	}
	// unit test main method
	public static void main(String[] args) {

	}
}
