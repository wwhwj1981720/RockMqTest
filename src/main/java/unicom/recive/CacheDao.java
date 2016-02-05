package unicom.recive;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import unicom.cacheinit.domain.User;



public class CacheDao {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * 
	 * 修改为 数据连接池的模式
	 * **/
	public static Connection getCon() {
//		Connection conn = null;
//		ResultSet rs = null;
//		String url = "jdbc:oracle:thin:@192.168.100.3:1521:ylrx";
//		String username = "billing";
//		String password = "billing";
//		Properties prop = null;
//		try {
//
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection(url, username, password);
//			conn.setAutoCommit(false);
//		} catch (ClassNotFoundException cnfex) {
//
//			System.err.println("装载 JDBC/ODBC 驱动程序失败。");
//			cnfex.printStackTrace();
//			System.exit(1);
//		} catch (SQLException sqlex) {
//			System.err.println("无法连接数据库");
//			sqlex.printStackTrace();
//			System.exit(1);
//		}
		Connection conn = null;
		conn=DBManager.getConn();

		return conn;
	}

	
	 public static void relaseConnection(Connection conn) 
	 { 
		 if (conn != null) 
		 {
//			 try { conn.close(); 
//			 } catch (SQLException e) 
//			 { // TODO Auto-generated
//				 e.printStackTrace(); 
//			}
			 DBManager.closeConn(conn);
			 conn = null; 
		} 
	}
	 

	
	public static void delete(Connection conn, String SMSId)
			throws Exception {
		Statement stmt = null;
		// int int_SMSId = 0;
		try {
			// int_SMSId = Integer.parseInt(SMSId);
		} catch (Exception e) {
			throw new Exception("日程记录ID错误");

		}

		try {
			stmt = conn.createStatement();

			String sql = "delete " + "from SMS " + "where "
					+ "SMSId  = " + SMSId;

			stmt.executeUpdate(sql);
			try

			{
				if (stmt != null) {

					stmt.close();
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("删除日程提醒出错" + ex.getMessage());
		}
	}

	// find
	/*
	 public static void find(SMS SMS, Connection conn, String date)
			throws Exception, SQLException {

		Statement stmt = null;
		ResultSet resultSet = null;
		// int int_Remindeid = 0;

		try {
			stmt = conn.createStatement();
			String sql = null;
			sql = "select " + "re.SMSId,    " + "re.empNum,     "
					+ "re.title,      " + "re.SMSType,  "
					+ "re.SMSDate," + "re.content   " + "from "
					+ "SMS re " + "where " + "re.SMSDate = " + date;

			System.out.println(sql);
			resultSet = stmt.executeQuery(sql);

			if (resultSet.next()) {
				

				SMS.setSMSId(resultSet.getString("SMSId"));
				SMS.setEmpNum(resultSet.getString("empNum"));
				SMS.setTitle(resultSet.getString("title"));
				SMS.setSMSType(resultSet.getString("SMSType"));
				SMS.setSMSDate(resultSet.getString("SMSDate"));
				SMS.setContent(resultSet.getString("content"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查找出错" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}
		}
	}
	*/
	public static List<User> findAllUser(Connection conn) throws Exception, SQLException {
		List listUser = new ArrayList();
		User user = null;
		Statement stmt = null;
		//ConnectionPoolManager connMgr = null;
		// Connection conn = null;
		// PreparedStatement st = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			// stmt = conn.createStatement();
			// stmt=conn.prepareStatement(sql);
			String sql = null;
			sql = "select " + "re.SERIAL_NUMBER,    " + "re.USER_ID     "
					 +"from  TF_F_USER re"
			;

			System.out.println(sql);
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				//User 
				user = new User();
				user.setPhoneNum(resultSet.getString("SERIAL_NUMBER"));
				user.setUserId(resultSet.getLong("user_id"));
			
				// re.setParentID(rs.getInt("ParentID"));
				listUser.add(user);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("查找出错" + ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}
		}
		return listUser;

	}

	public static List<User> findUserId(String phoneNum,
			Connection conn) throws Exception, SQLException {
		List listUser = new ArrayList();
		User user = null;
		Statement stmt = null;
		//ConnectionPoolManager connMgr = null;
		// Connection conn = null;
		// PreparedStatement st = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			// stmt = conn.createStatement();
			// stmt=conn.prepareStatement(sql);
			String sql = null;
			sql = "select " + "re.SERIAL_NUMBER,    " + "re.USER_ID     "
					 +"from  TF_F_USER re"
					+ " where " + " re.SERIAL_NUMBER = '" + phoneNum + "'";

			System.out.println(sql);
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				//User 
				user = new User();
				user.setPhoneNum(phoneNum);
				user.setUserId(resultSet.getLong("user_id"));
			
				// re.setParentID(rs.getInt("ParentID"));
				listUser.add(user);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("查找出错" + ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}
		}
		return listUser;

	}
	
	
/*
	public static List<SMS> findAllSMS(String pnum, Connection conn)
			throws Exception, SQLException {
		List<SMS> listSMS = new ArrayList<SMS>();
		SMS SMS = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			stmt = conn.createStatement();
			String sql = null;
			sql = "select " + "re.oc,    " + "re.dc,     "
					+ "re.cdate,      " + "re.pnum,  "
					+ "re.dnum," + "re.content   " + "from "
					+ "SMS re " + " where " + " re.pnum=" + pnum;

			System.out.println(sql);
			resultSet = (ResultSet) stmt.executeQuery(sql);

			if (resultSet.next()) {
				SMS = new SMS();
				
				SMS.setSMSId(resultSet.getString("SMSId"));
				SMS.setEmpNum(resultSet.getString("empNum"));
				SMS.setTitle(resultSet.getString("title"));
				SMS.setSMSType(resultSet.getString("SMSType"));
				SMS.setSMSDate(resultSet.getString("SMSDate"));
				SMS.setContent(resultSet.getString("content"));
				listSMS.add(SMS);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查找出错" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}

		}
		return listSMS;
	}
	*/
/*
	public static ArrayList<SMS> findMonthSMS(String empNum,
			String month, String year, Connection conn) throws Exception,
			SQLException {
		ArrayList listSMS = new ArrayList();
		SMS SMS = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {

			stmt = conn.createStatement();
			String sql = null;

			String sql2 = " select " + " SMSDate" + " from " + "SMS "
					+ " where " + " MONTH(SMSDate) = '" + month + "' and "
					+ " YEAR(SMSDate)=" + year + "' and " + " empNum="
					+ empNum;

			System.out.println(sql2);
			resultSet = (ResultSet) stmt.executeQuery(sql2);

			while (resultSet.next()) {
				SMS re = new SMS();
				re.setSMSId(resultSet.getString("SMSDate"));

				listSMS.add(re);
			}

			stmt.close();
			stmt = null;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查找出错" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}

		}
		return listSMS;
	}

	public static ArrayList<SMS> findMonthAllSMS(String empNum,
			String month, String year, Connection conn) throws Exception,
			SQLException {
		ArrayList listSMS = new ArrayList();
		SMS SMS = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		// int int_Remindeid = 0;

		try {

			stmt = conn.createStatement();
			String sql = null;

			String sql2 = " select " + "empNum," + "SMSId,"
					+ " SMSDate," + "title," + "SMSType," + "content"
					+ " from " + "SMS " + " where "
					+ " MONTH(SMSDate) = '" + month + "' and "
					+ " YEAR(SMSDate)='" + year + "' and " + " empNum="
					+ empNum + " order by SMSDate desc";

			System.out.println(sql2);
			resultSet = (ResultSet) stmt.executeQuery(sql2);

			while (resultSet.next()) {
				SMS re = new SMS();
				re.setSMSDate(resultSet.getString("SMSDate"));

				re.setContent(resultSet.getString("content"));
				re.setEmpNum(resultSet.getString("empNum"));
				re.setSMSId(resultSet.getString("SMSId"));
				re.setTitle(resultSet.getString("title"));
				re.setSMSType(resultSet.getString("SMSType"));
				listSMS.add(re);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查找出错" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}

		}
		return listSMS;
	}
	*/
/*
	public static boolean update(SMS SMS, Connection conn)
			throws Exception {
		Statement stmt = null;

		try {
			SMS.getSMSId();
			System.out.println("SMSDao");
		} catch (Exception e) {
			throw new Exception("日程记录ID错误");
		}

		try {
			System.out.println("33333333333333333333333333333333333333333333");
			stmt = conn.createStatement();
			String sql = null;
			sql = "update " + "SMS t " + "set " + "title= '"
					+ PublicTools.toSql(SMS.getTitle()) + "', "
					+ "SMSType='"
					+ PublicTools.toSql(SMS.getSMSType()) + "', "
					+ "SMSDate= '" + SMS.getSMSDate() + "',"
					+ "content='" + PublicTools.toSql(SMS.getContent())
					+ "'" +

					"where " + "SMSId  = " + SMS.getSMSId();
			System.out.println(sql);
			stmt.executeUpdate(sql);
			if (stmt != null)

			{
				stmt.close();

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("修改日程提醒出错" + ex.getMessage());
		}
		return true;
	}

	public static SMS findOne(String SMSId, Connection conn)
			throws Exception, SQLException {

		Statement stmt = null;
		ResultSet resultSet = null;
		// int int_Remindeid = 0;
		SMS SMS = new SMS();
		try {
			stmt = conn.createStatement();
			String sql = null;
			sql = "select " + "re.SMSId,    " + "re.empNum,     "
					+ "re.title,      " + "re.SMSType,  "
					+ "re.SMSDate," + "re.content   " + "from "
					+ "SMS re " + "where " + "re.SMSId = "
					+ SMSId;

			System.out.println(sql);
			resultSet = stmt.executeQuery(sql);
			System.out
					.println("33333333333333333333333333333333333333333333333333");

			
			while (resultSet.next()) {
				SMS.setSMSId(resultSet.getString("SMSId"));
				SMS.setEmpNum(resultSet.getString("empNum"));
				SMS.setTitle(resultSet.getString("title"));
				SMS.setSMSType(resultSet.getString("SMSType"));
				SMS.setSMSDate(resultSet.getString("SMSDate"));
				SMS.setContent(resultSet.getString("content"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查找出错" + e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException ex) {
				throw ex;
			}
		}
		return SMS;
	}
	
*/
	public static void main(String args[]) throws Exception, SQLException {

		User sms=new User();
		Connection conn=CacheDao.getCon();

		CacheDao dao =new CacheDao();
		//SMSDao.create(sms, SMSDao.getCon());
		dao.findUserId("13127298444", conn);

	}

}
