package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vm.db.common.DBConn;
import vm.db.po.MiddleTaskTargetType;

public class MiddleTaskTargetTypeDAO {
	private DBConn dbConn = null;

	public MiddleTaskTargetTypeDAO() {
		dbConn = new DBConn();
	}

	public boolean insert(MiddleTaskTargetType middleTaskTargetType) {
		boolean executeResultFlag = false;
		String sql = "insert into middletasktargettype values(?, ?, ?, ?, 1, ?, ?);";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, middleTaskTargetType.getTaskTargetTypeID());
			ps.setLong(2, middleTaskTargetType.getCtime());
			ps.setLong(3, middleTaskTargetType.getUtime());
			ps.setString(4, middleTaskTargetType.getNote());
			ps.setString(5, middleTaskTargetType.getTaskID());
			ps.setString(6, middleTaskTargetType.getTargetTypeID());
			executeResultFlag = ps.executeUpdate() != 1 ? false : true;
			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return executeResultFlag;
	}

	public boolean deleteByUserID(String middleTaskTargetTypeID) {
		if (middleTaskTargetTypeID == null || "".equals(middleTaskTargetTypeID)) {
			return false;
		}
		MiddleTaskTargetType middleTaskTargetType = this.findByMiddleTaskTargetTypeID(middleTaskTargetTypeID);
		middleTaskTargetType.setFlag(0); // logical delete
		return this.updateByMiddleTaskTargetTypeID(middleTaskTargetTypeID, middleTaskTargetType);
	}

	// update methods
	public boolean updateByMiddleTaskTargetTypeID(String middleTaskTargetTypeID,
			MiddleTaskTargetType middleTaskTargetType) {
		String sql = "update user set ctime = ?, utime = ?, note = ?, "
				+ "flag = ?, taskID = ?, targetTypeID = ? where taskTargetTypeID = ? and flag <> 0;";
		boolean isUpdated = false;
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setLong(1, middleTaskTargetType.getCtime());
			ps.setLong(2, System.currentTimeMillis());
			ps.setString(3, middleTaskTargetType.getNote());
			ps.setInt(4, middleTaskTargetType.getFlag());
			ps.setString(5, middleTaskTargetType.getTaskID());
			ps.setString(6, middleTaskTargetType.getTargetTypeID());
			ps.setString(7, middleTaskTargetTypeID);
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
	public MiddleTaskTargetType findByMiddleTaskTargetTypeID(String middleTaskTargetTypeID) {
		if (middleTaskTargetTypeID == null || "".equals(middleTaskTargetTypeID)) {
			return null;
		}
		String sql = "select * from middletasktargettype where taskTargetTypeID = ? and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, middleTaskTargetTypeID);
			ResultSet rs = ps.executeQuery();
			MiddleTaskTargetType middleTaskTargetType = new MiddleTaskTargetType();
			if (rs.next()) { 
				middleTaskTargetType.setTaskTargetTypeID(rs.getString("taskTargetTypeID"));
				middleTaskTargetType.setCtime(rs.getLong("ctime"));
				middleTaskTargetType.setUtime(rs.getLong("utime"));
				middleTaskTargetType.setNote(rs.getString("note"));
				middleTaskTargetType.setFlag(rs.getInt("flag"));
				middleTaskTargetType.setTaskID(rs.getString("taskID"));
				middleTaskTargetType.setTargetTypeID(rs.getString("targetTypeID"));
			}
			this.dbConn.release();
			return middleTaskTargetType;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
