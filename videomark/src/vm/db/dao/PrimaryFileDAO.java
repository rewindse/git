package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vm.db.common.DBConn;
import vm.db.po.PrimaryFile;

public class PrimaryFileDAO {
		private DBConn dbConn = null;

		public PrimaryFileDAO() {
			dbConn = new DBConn();
		}

		
		public boolean insert(PrimaryFile originalfile) {
			boolean executeResultFlag = false;
			String sql = "insert into primaryfile values(?, ?, ?, ?, ?, ?, ?,?,?,?);";
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
		public String isExistFileByPath(String filepath) {
			String sql ="select count(*) as flag from primaryfile where path=? ";
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
			String sql ="select originalFileID from primaryfile where path = ? and flag = 1 ";
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



		public List<PrimaryFile> findTreeById(String id) {
			
			List<PrimaryFile> list = new ArrayList<PrimaryFile>();  
			String sql = "select * from primaryfile where flag <> 0 and pid = '"+id+"';";
			try {
				PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				   while (rs.next()) { 
					PrimaryFile orifile = new PrimaryFile();
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
			String sql ="select path from primaryfile where originalFileID=? ";
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
	}


