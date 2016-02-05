package unicom.recive;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import unicom.cacheinit.domain.User;
import unicom.comm.model.Accumulator;
import unicom.comm.model.Bill;
import unicom.comm.model.VolumeInventory;



public class BillingDao {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


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
		//conn.setAutoCommit(false);
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
	 

	
	public  void create(VolumeInventory inventory)
			throws Exception {
		
		Connection conn=BillingDao.getCon();
		String sql = null;
		Statement stmt = null;
		conn.setAutoCommit(false);
		PreparedStatement pst = null;
		
				
		try {
			
			sql = "insert " + "into " + "TG_CDR01_GS ( " + "SRCPHONE, " + "BIZTYPE, "
					+ "USERID, " + "ERRORCODE, " + "SRCFILENAME, "+"ORGCFEE, "+"CFEE,"+"INDBTIME,"+"DATAUP,"+"DATADOWN"+") "+
							" values (?,?,?,?,?,?,?,?,?,?)";
			System.out.println(sql);
			pst = conn.prepareStatement(sql);
			pst.setString(1,inventory.getSrcPhone());
			pst.setInt(2,inventory.getBizType());
			pst.setString(3,inventory.getUserId());
			pst.setInt(4,inventory.getErrorCode());
			pst.setString(5,inventory.getSrcFileName());
			pst.setLong(6, inventory.getOrgCfee());
			pst.setLong(7, inventory.getCfee());
			pst.setString(8, inventory.getInDBTime());
			pst.setLong(9, inventory.getDataUp());
			pst.setLong(10, inventory.getDataDown());
			
			pst.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);

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
			throw new Exception("新增日程提醒出错\n" + ex.getMessage());
		
		}
		BillingDao.relaseConnection(conn);
	}
	/**
	 * 
	 * 更新或者插入累积量累积量
	 * **/
	public  void insertOrupdateAcc(Accumulator acc)
			  {
		
		Connection conn=BillingDao.getCon();
		String sql = null;
		Statement stmt = null;
		
		PreparedStatement pst = null;
		sql = "update BILL_USER_SUM1_XX set VALUE= ? where USERID=? and TOPBJID=? and ITEMID=?";
		try {
			conn.setAutoCommit(false);
			pst=conn.prepareStatement(sql);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			pst.setLong(1, acc.getValue());
			pst.setLong(2, acc.getUserId());
			pst.setLong(3, acc.getTopBJId());
			pst.setInt(4,acc.getBillId());
			//pst.addBatch();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		int row=0;
		try {
			row = pst.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * 
		 * 更新的数目为 0,说明没有记录，需要插入
		 * */
		if (row == 0) {
			try {

				sql = "insert " + "into " + "BILL_USER_SUM1_XX ( " + "ITEMID, "
						+ "TOPBJID, " + "USERID, " + "VALUE, " + "VERSION) "
						+ " values (?,?,?,?,?)";
				System.out.println(sql);
				conn.setAutoCommit(false);
				pst=conn.prepareStatement(sql);
				pst.setInt(1, acc.getBillId());
				pst.setLong(2, acc.getTopBJId());
				pst.setLong(3, acc.getUserId());
				pst.setLong(4, acc.getValue());
				pst.setInt(5, acc.getVersion());
				//pst.addBatch();
				pst.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
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
				try {
					throw new Exception("新增日程提醒出错\n" + ex.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		BillingDao.relaseConnection(conn);
		
	}
	/**
	 * 更新账单
	 * 
	 * **/
	public  void insertOrupdateBill(Bill bill) {
		
		Connection conn=BillingDao.getCon();
		String sql = null;
		Statement stmt = null;
		PreparedStatement pst = null;
		sql = "update bill set fee= ? ,INITFEE= ? where USERID=? and DETAILITEMCODE=?";
		try {
			conn.setAutoCommit(false);//手动控制事物
			pst = conn.prepareStatement(sql);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			pst.setLong(1, bill.getFee());
			pst.setLong(2, bill.getInitFee());
			pst.setLong(3, bill.getUserId());
			pst.setInt(4, bill.getDetailItemCode());
			// pst.addBatch();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		int row = 0;
		try {
			row = pst.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (row == 0) {
			try {

				sql = "insert " + "into " + "bill ( " + "USERID, "
						+ "DETAILITEMCODE, " + "INITFEE, " + "DISCOUNTFEE, " + "FEE, "+"VERSION) "
						+ " values (?,?,?,?,?,?)";
				System.out.println(sql);
				conn.setAutoCommit(false);
				pst = conn.prepareStatement(sql);
				pst.setLong(1, bill.getUserId());
				pst.setLong(2, bill.getDetailItemCode());
				pst.setLong(3, bill.getInitFee());
				pst.setLong(4, bill.getDiscountFee());
				pst.setLong(5, bill.getFee());
				pst.setInt(6, bill.getVersion());
				// pst.addBatch();
				pst.executeUpdate();
				conn.commit();
				conn.setAutoCommit(true);
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
				try {
					throw new Exception("新增日程提醒出错\n" + ex.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
}
		BillingDao.relaseConnection(conn);
}

	// delete
	public  void delete(Connection conn, String SMSId)
			throws Exception {
		Statement stmt = null;
		// int int_SMSId = 0;
		try {
			// int_SMSId = Integer.parseInt(SMSId);
		} catch (Exception e) {
			throw new Exception("日程记录ID错误");

		}

		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			String sql = "delete " + "from SMS " + "where "
					+ "SMSId  = " + SMSId;

			stmt.executeUpdate(sql);
			conn.commit();
			conn.setAutoCommit(true);
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
		
	public  Bill findBillProc(String userId, int itemId,Connection conn)
	{
		Bill bill=null;
		CallableStatement proc = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			
			String sql = null;
			sql="{call FindBill (?,?,?,?,?,?,?,?) }";
			proc = conn.prepareCall(sql);
			long id=Long.parseLong(userId);
			proc.setLong(1,id);
			proc.setLong(2, itemId);
			proc.registerOutParameter(3, Types.BIGINT);
			proc.registerOutParameter(4, Types.TINYINT);
			proc.registerOutParameter(5, Types.BIGINT);
			proc.registerOutParameter(6, Types.BIGINT);
			proc.registerOutParameter(7, Types.BIGINT);
			proc.registerOutParameter(8, Types.TINYINT);
			//if(proc.execute())
			proc.executeQuery();
			{

			
				bill = new Bill();
				bill.setUserId(proc.getLong(3));
				bill.setDetailItemCode(proc.getInt(4));
				bill.setInitFee(proc.getLong(5));
				bill.setDiscountFee(proc.getLong(6));
				bill.setFee(proc.getLong(7));
				bill.setVersion(proc.getInt(8));
				
				// re.setParentID(rs.getInt("ParentID"));
				//listUser.add(user);
			}
			

		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				throw new Exception("查找出错" + ex.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (proc != null) {
					proc.close();
				}
			} catch (SQLException ex) {
				try {
					throw ex;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return bill;
	}
	/*
	 * 使用 select *　from  for update 
	 * */
	public  Bill findBill(String userId, int itemId) throws Exception, SQLException {
		
		Connection conn=BillingDao.getCon();
		Bill bill=null;
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
			sql = "select " + "re.USERID,    " + "re.DETAILITEMCODE , "+
			"re.INITFEE, "+" re.DISCOUNTFEE,"+" re.FEE,"+" re.VERSION "
					 +"from  bill re where re.USERID= '"+Long.parseLong(userId)+"' and re.DETAILITEMCODE="+itemId ;
			

			System.out.println(sql);
			//conn.setAutoCommit(false);
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				//User 
				bill = new Bill();
				bill.setUserId(resultSet.getLong(1));
				bill.setDetailItemCode(resultSet.getInt(2));
				bill.setInitFee(resultSet.getLong(3));
				bill.setDiscountFee(resultSet.getLong(4));
				bill.setFee(resultSet.getLong(5));
				bill.setVersion(resultSet.getInt(6));
				// re.setParentID(rs.getInt("ParentID"));
				//listUser.add(user);
			}
//			if(itemId==20000&&(!resultSet.next()))
//			{
//				
//			}

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
		BillingDao.relaseConnection(conn);
		return bill;

	}
	public static List<User> findAllUser() throws Exception, SQLException {
		Connection conn=BillingDao.getCon();
		
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
			//conn.setAutoCommit(false);
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
		BillingDao.relaseConnection(conn);
		return listUser;

	}
	/*
	* 使用  select *　from  for update
	*
	**/
	public static Accumulator findAccumulator(String user_id,String itemid) throws Exception, SQLException 
	{
		
		Connection conn=BillingDao.getCon();
		Accumulator acc=null;
		Statement stmt = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			
			String sql = null;
			sql = "select " + "re.ITEMID,    " + "re.TOPBJID,     "+"re.USERID, "+"re.VALUE, "+"re.VERSION "
					 +"from  BILL_USER_SUM1_XX re"
					+ " where " + " re.USERID = '" + user_id + "' "+"and re.ITEMID="+"'"+itemid+"'";

			System.out.println(sql);
			//conn.setAutoCommit(false);
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				//User 
				acc = new Accumulator();
				acc.setBillId(resultSet.getInt("ITEMID"));
				acc.setUserId(resultSet.getLong("USERID"));
				acc.setTopBJId(resultSet.getLong("TOPBJID"));
				acc.setValue(resultSet.getLong("value"));
				acc.setVersion(resultSet.getInt("version"));
				// re.setParentID(rs.getInt("ParentID"));
				//listUser.add(user);
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
		BillingDao.relaseConnection(conn);
		return acc;
		
	}
	public static List<User> findUserId(String phoneNum,
			Connection conn) throws Exception, SQLException {
		List listUser = new ArrayList();
		User user = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		int int_Remindeid = 0;

		try {
			
			String sql = null;
			sql = "select " + "re.SERIAL_NUMBER,    " + "re.USER_ID     "
					 +"from  TF_F_USER re"
					+ " where " + " re.SERIAL_NUMBER = '" + phoneNum + "'";

			System.out.println(sql);
			//conn.setAutoCommit(false);
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
	
	

	public static void main(String args[]) throws Exception, SQLException {

		User sms=new User();
		Connection conn=BillingDao.getCon();

		BillingDao dao =new BillingDao();
		//SMSDao.create(sms, SMSDao.getCon());
		dao.findUserId("13127298444", conn);

	}

}
