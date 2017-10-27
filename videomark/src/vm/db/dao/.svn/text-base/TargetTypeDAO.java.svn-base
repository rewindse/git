package vm.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import vm.db.common.DBConn;
import vm.db.po.TargetType;

public class TargetTypeDAO {
	/**
	 * 标注对象类型分为若干个大类，每个大类下又有若干个小类。 这个表不需要单独提供DAO接口。只在需要的时候往数据库中插入记录即可。
	 * main方法打印insert sql语句
	 */
	public static void main(String[] args) {
		String jsonString = "[\n"
				+ "        {\n"
				+ "                \"key\": \"weather\",\n"
				+ "                \"china_key\": \"天气\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"sunshine\", \"china_key\": \"晴\" }, { \"key\": \"overcast\", \"china_key\": \"阴\" }, { \"key\": \"cloudy\", \"china_key\": \"多云\" },\n"
				+ "                        { \"key\": \"light_rain\", \"china_key\": \"小雨\" }, { \"key\": \"heavy_rain\", \"china_key\": \"大雨\" },\n"
				+ "                        { \"key\": \"snow\", \"china_key\": \"雪\" }, { \"key\": \"fog\", \"china_key\": \"雾\" }\n"
				+ "                ]\n"
				+ "        }, {\n"
				+ "                \"key\": \"road_type\",\n"
				+ "                \"china_key\": \"道路类型\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"urban_road\", \"china_key\": \"城市道路\" }, { \"key\": \"expressway\", \"china_key\": \"高速公路\" },\n"
				+ "                        { \"key\": \"rural_road\", \"china_key\": \"乡村公路\" }\n"
				+ "                ]\n"
				+ "        }, {\n"
				+ "                \"key\": \"road_condition\",\n"
				+ "                \"china_key\": \"路况\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"road_condition_normal\", \"china_key\": \"正常路况\" }, { \"key\": \"tunnel\", \"china_key\": \"隧道\" },\n"
				+ "                        { \"key\": \"crossing\", \"china_key\": \"路口\" }, { \"key\": \"bend\", \"china_key\": \"弯道\" },\n"
				+ "                        { \"key\": \"rampway\", \"china_key\": \"坡道\" }\n"
				+ "                ]\n"
				+ "        }, {\n"
				+ "                \"key\": \"light\",\n"
				+ "                \"china_key\": \"光线\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"light_normal\", \"china_key\": \"正常光线\" }, { \"key\": \"frontlight\", \"china_key\": \"顺光\" },\n"
				+ "                        { \"key\": \"sidelight\", \"china_key\": \"侧光\" }, { \"key\": \"backlight\", \"china_key\": \"逆光\" },\n"
				+ "                        { \"key\": \"infrared\", \"china_key\": \"红外补光\" }\n"
				+ "                ]\n"
				+ "        }, {\n"
				+ "                \"key\": \"video_details\",\n"
				+ "                \"china_key\": \"视频细节\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"FCWS\", \"china_key\": \"FCWS\" }, { \"key\": \"PCWS\", \"china_key\": \"PCWS\" }, { \"key\": \"LDWS\", \"china_key\": \"LDWS\" },\n"
				+ "                        { \"key\": \"TSR\", \"china_key\": \"TSR\" }, { \"key\": \"LAMP\", \"china_key\": \"LAMP\" }\n"
				+ "                ]\n"
				+ "        }, {\n"
				+ "                \"key\": \"target\",\n"
				+ "                \"china_key\": \"标注信息\",\n"
				+ "                \"sub_key\" : [\n"
				+ "                        { \"key\": \"persion\", \"china_key\": \"人\" }, { \"key\": \"vehicle\", \"china_key\": \"车\" }, { \"key\": \"ride\", \"china_key\": \"骑行\" },\n"
				+ "                        { \"key\": \"traffic_sign\", \"china_key\": \"交通标志\" }\n"
				+ "                ]\n" + "        }, {\n"
				+ "                \"key\": \"video_note\",\n"
				+ "                \"china_key\": \"视频备注\",\n"
				+ "                \"sub_key\": []\n" + "        }, {\n"
				+ "                \"key\": \"special_case\",\n"
				+ "                \"china_key\": \"特殊场景\",\n"
				+ "                \"sub_key\": []\n" + "        }\n" + "]\n"
				+ "";
		String sql = "insert into targettype values(''{0}'', {1}, {1}, ''{2}'', 1, ''{3}'', ''{4}'');";
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			String key = jsonObject.getString("key");
			String chinaKey = jsonObject.getString("china_key");
			long cur = System.currentTimeMillis();
			String id = UUID.randomUUID().toString();
			System.out.println(MessageFormat.format(sql, id,
					Long.toString(cur), chinaKey, key, "-1"));
			JSONArray subKeys = jsonObject.getJSONArray("sub_key");
			for (Object subKey : subKeys) {
				JSONObject subJsonObject = (JSONObject) subKey;
				key = subJsonObject.getString("key");
				chinaKey = subJsonObject.getString("china_key");
				cur = System.currentTimeMillis();
				System.out.println(MessageFormat.format(sql, UUID.randomUUID()
						.toString(), Long.toString(cur), chinaKey, key, id));
			}
		}
	}

	private DBConn dbConn = null;

	public TargetTypeDAO() {
		dbConn = new DBConn();
	}

	public List<TargetType> findByParentID(String parentID) {
		List<TargetType> targetTypeList = new ArrayList<TargetType>();

		if (parentID == null || "".equals(parentID)) {
			return null;
		}
		String sql = "select * from targettype where parentID = ? and flag <> 0;";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, parentID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TargetType targetType = new TargetType();
				String ttID = rs.getString("targettypeID");
				targetType.setTargetTypeID(ttID == null ? " " : ttID);
				targetType.setCtime(rs.getLong("ctime"));
				targetType.setUtime(rs.getLong("utime"));
				targetType.setNote(rs.getString("note"));
				targetType.setFlag(rs.getInt("flag"));
				String ttName = rs.getString("targetname");
				targetType.setTargetName(ttName == null ? " " : ttName);
				String pID = rs.getString("parentID");
				targetType.setParentID(pID == null ? " " : pID);
				targetTypeList.add(targetType);
			}
			this.dbConn.release();
			rs.close();
			return targetTypeList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TargetType findByTargetTypeID(String targetTypeID) {
		TargetType targetType = new TargetType();

		if (targetTypeID == null || "".equals(targetTypeID)) {
			return null;
		}
		String sql = "select * from targettype where targetTypeID = ? and flag <> 0";

		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, targetTypeID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String ttID = rs.getString("targetTypeID");
				targetType.setTargetTypeID(ttID == null ? " " : ttID);
				targetType.setCtime(rs.getLong("ctime"));
				targetType.setUtime(rs.getLong("utime"));
				targetType.setNote(rs.getString("note"));
				targetType.setFlag(rs.getInt("flag"));
				String ttName = rs.getString("targetname");
				targetType.setTargetName(ttName == null ? " " : ttName);
				String pID = rs.getString("parentID");
				targetType.setParentID(pID == null ? " " : pID);
			}
			rs.close();
			this.dbConn.release();
			return targetType;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<TargetType> findByTargetTypeName(String targetName) {

		if (targetName == null || "".equals(targetName)) {
			return null;
		}
		String sql = "select * from targettype where targetName = ? and flag <> 0";
		List<TargetType> targetTypes = new ArrayList<TargetType>();
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, targetName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TargetType targetType = new TargetType();
				String ttID = rs.getString("targetTypeID");
				targetType.setTargetTypeID(ttID == null ? " " : ttID);
				targetType.setCtime(rs.getLong("ctime"));
				targetType.setUtime(rs.getLong("utime"));
				targetType.setNote(rs.getString("note"));
				targetType.setFlag(rs.getInt("flag"));
				String ttName = rs.getString("targetname");
				targetType.setTargetName(ttName == null ? " " : ttName);
				String pID = rs.getString("parentID");
				targetType.setParentID(pID == null ? " " : pID);
				targetTypes.add(targetType);
			}
			rs.close();
			this.dbConn.release();
			return targetTypes;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findIDByTargetTypeName(String targetName) {
		String targetTypeID = null;
		if (targetName == null || " ".equals(targetName)) {
			return null;
		}
		String sql = "select * from targettype where targetName = ? and flag <> 0";
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ps.setString(1, targetName);
			ResultSet rs = ps.executeQuery();
			TargetType targetType = new TargetType();
			while (rs.next()) {
				String ttID = rs.getString("targetTypeID");
				targetType.setTargetTypeID(ttID == null ? " " : ttID);
				targetType.setCtime(rs.getLong("ctime"));
				targetType.setUtime(rs.getLong("utime"));
				targetType.setNote(rs.getString("note"));
				targetType.setFlag(rs.getInt("flag"));
				String ttName = rs.getString("targetname");
				targetType.setTargetName(ttName == null ? " " : ttName);
				String pID = rs.getString("parentID");
				targetType.setParentID(pID == null ? " " : pID);
			}
			targetTypeID = targetType.getTargetTypeID();
			rs.close();

			this.dbConn.release();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return targetTypeID;
	}

	public List<TargetType> findAll() {
		String sql = "select * from targettype where flag <> 0";
		List<TargetType> targetTypes = new ArrayList<TargetType>();
		try {
			PreparedStatement ps = this.dbConn.getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TargetType targetType = new TargetType();
				String ttID = rs.getString("targetTypeID");
				targetType.setTargetTypeID(ttID == null ? " " : ttID);
				targetType.setCtime(rs.getLong("ctime"));
				targetType.setUtime(rs.getLong("utime"));
				targetType.setNote(rs.getString("note"));
				targetType.setFlag(rs.getInt("flag"));
				String ttName = rs.getString("targetname");
				targetType.setTargetName(ttName == null ? " " : ttName);
				String pID = rs.getString("parentID");
				targetType.setParentID(pID == null ? " " : pID);
				targetTypes.add(targetType);
			}
			rs.close();
			this.dbConn.release();
			return targetTypes;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
