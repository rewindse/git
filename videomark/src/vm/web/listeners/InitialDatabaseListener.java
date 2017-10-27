/**
 * 
 */
package vm.web.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import vm.db.common.Configurations;
import vm.db.common.DBConn;

/**
 * @author Administrator
 *
 */
public class InitialDatabaseListener implements ServletContextListener {
	
	private DBConn db = new DBConn();

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("InitialDataBaseListener.contextDestroyed()");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("InitialDataBaseListener.contextInitialized()");
		String dbAutoInitial = Configurations.getInstance().getProperty("db.auto_initial");
		if (!Boolean.parseBoolean(dbAutoInitial)) {
			return ;
		}
		this.truncateDatabase();
		this.initialDatabase();
	}
	
	private void truncateDatabase() {
		System.out.println("InitialDataBaseListener.truncateDatabase()");
		Connection conn;
		ResultSet rs;
		String alltablename_sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'videomark';";
		String truncate_sql = "truncate table ";
		try {
			conn = this.db.getConn();
			rs = conn.prepareStatement(alltablename_sql).executeQuery();
			List<String> names = new Vector<String>();
			
			while (rs.next()) {
				names.add(rs.getString(1));
			}
			for (String name : names) {
				PreparedStatement ps = conn.prepareStatement(truncate_sql + name + ";");
				ps.execute();
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialDatabase() {
		System.out.println("InitialDataBaseListener.initialDatabase()");
		Connection conn;
		long cur = System.currentTimeMillis();
		String Role_sql1 = "insert into role values('A','" + cur + "','" + cur + "','','" + 1 + "','" + "administrator" + "');";
		String Role_sql2 = "insert into role values('B','" + cur + "','" + cur + "','','" + 1 + "','" + "marker" + "');";

		
		cur = System.currentTimeMillis();
		String resultType_sql1 = "insert into resulttype values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" + "image" + "');";
		String resultType_sql2 = "insert into resulttype values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" + "video" + "');";

		cur = System.currentTimeMillis();
		String status_sql1 = "insert into status values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" + "coming" + "');";
		String status_sql2 = "insert into status values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" + "running" + "');";
		String status_sql3 = "insert into status values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" + "finished" + "');";
	
		cur = System.currentTimeMillis();
		String user_sql1 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1 + "','" 
								+ "lifang" + "','" + "123" + "','" + "李放" + "','" + "723384439@qq.com" + "','" + "138****6778" + "','" + "西安交大" + "','A');";
		String user_sql2 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "wang" + "','" + "123" + "','" + "王萌" + "','" + "756602283@qq.com" + "','" + "134****6798" + "','" + "西安交大" + "','B');";
		String user_sql3 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "zhang" + "','" + "123" + "','" + "张涛" + "','" + "121426097@qq.com" + "','" + "138****9078" + "','" + "西安交大" + "','B');";
		String user_sql4 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "bai" + "','" + "123" + "','" + "白琪" + "','" + "1214398998@qq.com" + "','" + "134****1298" + "','" + "西安交大" + "','B');";
		String user_sql5 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "dong" + "','" + "123" + "','" + "董家河" + "','" + "734678878@qq.com" + "','" + "138****2238" + "','" + "西安交大" + "','B');";
		String user_sql6 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "jiang" + "','" + "123" + "','" + "姜媛" + "','" + "121678798@qq.com" + "','" + "134****2348" + "','" + "西安交大" + "','B');";
		String user_sql7 = "insert into user values('" + UUID.randomUUID().toString() + "','" + cur + "','" + cur + "','','" + 1
								+ "','" + "zhao" + "','" + "123" + "','" + "赵坤" + "','" + "726237768@qq.com" + "','" + "138****0123" + "','" + "西安交大" + "','B');";

		try {
			conn = this.db.getConn();
			PreparedStatement ps = conn.prepareStatement(Role_sql1);
			ps.execute();
			ps = conn.prepareStatement(Role_sql2);
			ps.execute();

			ps = conn.prepareStatement(resultType_sql1);
			ps.execute();
			ps = conn.prepareStatement(resultType_sql2);
			ps.execute();

			Statement stat = conn.createStatement();
			stat.addBatch("insert into targettype values('2d45a5dc-cb04-4711-940b-f552f9e343b2', 1501130719830, 1501130719830, '天气', 1, 'weather', '-1')");
			stat.addBatch("insert into targettype values('0578cc93-2607-4f63-89ae-b37dd94b3e2f', 1501130719938, 1501130719938, '晴', 1, 'sunshine', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('411f343d-0752-4b0d-b351-efd91503f44f', 1501130719938, 1501130719938, '阴', 1, 'overcast', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('72a594fa-c5bf-49ee-ad28-96628ddfbb31', 1501130719939, 1501130719939, '多云', 1, 'cloudy', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('0c6889c4-9fbb-4a82-b221-26ac9e34ca9f', 1501130719939, 1501130719939, '小雨', 1, 'light_rain', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('92ece30a-82dd-4ba6-9646-06d3237ecf94', 1501130719939, 1501130719939, '大雨', 1, 'heavy_rain', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('0807c90f-28a1-4007-b239-e1f257b77989', 1501130719939, 1501130719939, '雪', 1, 'snow', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('d0dee0b1-3558-4820-90b6-7090b2366eba', 1501130719939, 1501130719939, '雾', 1, 'fog', '2d45a5dc-cb04-4711-940b-f552f9e343b2')");
			stat.addBatch("insert into targettype values('03fe4041-62b1-4227-bce1-b6872b451c25', 1501130719939, 1501130719939, '道路类型', 1, 'road_type', '-1')");
			stat.addBatch("insert into targettype values('64b2ae23-76e1-4b4e-81b7-6b43b0e32c93', 1501130719939, 1501130719939, '城市道路', 1, 'urban_road', '03fe4041-62b1-4227-bce1-b6872b451c25')");
			stat.addBatch("insert into targettype values('14714714-7184-4f6a-918e-81a2a8cd27da', 1501130719939, 1501130719939, '高速公路', 1, 'expressway', '03fe4041-62b1-4227-bce1-b6872b451c25')");
			stat.addBatch("insert into targettype values('41081f65-2bef-471a-800b-b9019757c696', 1501130719939, 1501130719939, '乡村公路', 1, 'rural_road', '03fe4041-62b1-4227-bce1-b6872b451c25')");
			stat.addBatch("insert into targettype values('ef841695-4b59-43ba-8f2e-81a03acc54c7', 1501130719939, 1501130719939, '路况', 1, 'road_condition', '-1')");
			stat.addBatch("insert into targettype values('c782635a-bbbb-4a78-9d43-2a8867d62e3b', 1501130719939, 1501130719939, '正常路况', 1, 'road_condition_normal', 'ef841695-4b59-43ba-8f2e-81a03acc54c7')");
			stat.addBatch("insert into targettype values('729449f2-8917-4392-9e28-84c6a44107b3', 1501130719940, 1501130719940, '隧道', 1, 'tunnel', 'ef841695-4b59-43ba-8f2e-81a03acc54c7')");
			stat.addBatch("insert into targettype values('43cc1aa8-5c27-489c-ae50-5085b7380053', 1501130719940, 1501130719940, '路口', 1, 'crossing', 'ef841695-4b59-43ba-8f2e-81a03acc54c7')");
			stat.addBatch("insert into targettype values('a2ad39e9-8dc4-4bcb-9ebe-dca8cf70466a', 1501130719940, 1501130719940, '弯道', 1, 'bend', 'ef841695-4b59-43ba-8f2e-81a03acc54c7')");
			stat.addBatch("insert into targettype values('ba868cb7-0a5f-4bdf-921d-c5b2ecceb8fb', 1501130719940, 1501130719940, '坡道', 1, 'rampway', 'ef841695-4b59-43ba-8f2e-81a03acc54c7')");
			stat.addBatch("insert into targettype values('c8a15525-0fb0-4063-88dc-7d3ff406adb7', 1501130719940, 1501130719940, '光线', 1, 'light', '-1')");
			stat.addBatch("insert into targettype values('66b0854d-38a6-4ebe-a9fc-a1be9a0db257', 1501130719940, 1501130719940, '正常光线', 1, 'light_normal', 'c8a15525-0fb0-4063-88dc-7d3ff406adb7')");
			stat.addBatch("insert into targettype values('58c74ca4-618b-4a87-af06-cff7f98dca07', 1501130719940, 1501130719940, '顺光', 1, 'frontlight', 'c8a15525-0fb0-4063-88dc-7d3ff406adb7')");
			stat.addBatch("insert into targettype values('3dc0f8da-386d-47b7-9519-9daead01cf78', 1501130719940, 1501130719940, '侧光', 1, 'sidelight', 'c8a15525-0fb0-4063-88dc-7d3ff406adb7')");
			stat.addBatch("insert into targettype values('eec6c78c-4386-4a3a-8e48-f2fe735e4e5a', 1501130719940, 1501130719940, '逆光', 1, 'backlight', 'c8a15525-0fb0-4063-88dc-7d3ff406adb7')");
			stat.addBatch("insert into targettype values('a0ef5e74-27c1-442e-8dc6-886b9084d979', 1501130719940, 1501130719940, '红外补光', 1, 'infrared', 'c8a15525-0fb0-4063-88dc-7d3ff406adb7')");
			stat.addBatch("insert into targettype values('f7d96842-b82f-44af-9f84-f1c2a12fdf1f', 1501130719941, 1501130719941, '视频细节', 1, 'video_details', '-1')");
			stat.addBatch("insert into targettype values('77e696a7-811a-4af5-ad1b-38e7b5391b7b', 1501130719941, 1501130719941, 'FCWS', 1, 'FCWS', 'f7d96842-b82f-44af-9f84-f1c2a12fdf1f')");
			stat.addBatch("insert into targettype values('f236da65-68b3-45b6-8654-7b56cdd0df96', 1501130719941, 1501130719941, 'PCWS', 1, 'PCWS', 'f7d96842-b82f-44af-9f84-f1c2a12fdf1f')");
			stat.addBatch("insert into targettype values('db01bb79-f9f4-423b-b039-f3c19406c82e', 1501130719941, 1501130719941, 'LDWS', 1, 'LDWS', 'f7d96842-b82f-44af-9f84-f1c2a12fdf1f')");
			stat.addBatch("insert into targettype values('7e9b35c9-3555-406f-a1e7-8e1511be021d', 1501130719941, 1501130719941, 'TSR', 1, 'TSR', 'f7d96842-b82f-44af-9f84-f1c2a12fdf1f')");
			stat.addBatch("insert into targettype values('d8a05a85-462b-4138-a6e5-83a946814840', 1501130719941, 1501130719941, 'LAMP', 1, 'LAMP', 'f7d96842-b82f-44af-9f84-f1c2a12fdf1f')");
			stat.addBatch("insert into targettype values('b5ca70d0-f4f1-45ac-bfe0-b082f19da83e', 1501130719941, 1501130719941, '标注信息', 1, 'target', '-1')");
			stat.addBatch("insert into targettype values('9f193543-8660-4fe9-8cd6-8dc60885d0b6', 1501130719941, 1501130719941, '人', 1, 'persion', 'b5ca70d0-f4f1-45ac-bfe0-b082f19da83e')");
			stat.addBatch("insert into targettype values('82c2fb79-8bdc-495e-bfa7-6fe18d148dab', 1501130719941, 1501130719941, '车', 1, 'vehicle', 'b5ca70d0-f4f1-45ac-bfe0-b082f19da83e')");
			stat.addBatch("insert into targettype values('0bf77ed5-a58f-4755-afec-f4c7705ecc25', 1501130719941, 1501130719941, '骑行', 1, 'ride', 'b5ca70d0-f4f1-45ac-bfe0-b082f19da83e')");
			stat.addBatch("insert into targettype values('b4db9146-1294-43e0-937a-64edd811586f', 1501130719941, 1501130719941, '交通标志', 1, 'traffic_sign', 'b5ca70d0-f4f1-45ac-bfe0-b082f19da83e')");
			stat.addBatch("insert into targettype values('63cc1dd7-f9dc-4061-8c58-33baa4de1efb', 1501130719942, 1501130719942, '视频备注', 1, 'video_note', '0')");
			stat.addBatch("insert into targettype values('9f62ee05-2558-4bb2-ac8b-3f65685a3e8c', 1501130719942, 1501130719942, '特殊场景', 1, 'special_case', '0')");
			stat.executeBatch();

			ps = conn.prepareStatement(status_sql1);
			ps.execute();
			ps = conn.prepareStatement(status_sql2);
			ps.execute();
			ps = conn.prepareStatement(status_sql3);
			ps.execute();

			ps = conn.prepareStatement(user_sql1);
			ps.execute();
			ps = conn.prepareStatement(user_sql2);
			ps.execute();
			ps = conn.prepareStatement(user_sql3);
			ps.execute();
			ps = conn.prepareStatement(user_sql4);
			ps.execute();
			ps = conn.prepareStatement(user_sql5);
			ps.execute();
			ps = conn.prepareStatement(user_sql6);
			ps.execute();
			ps = conn.prepareStatement(user_sql7);
			ps.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
