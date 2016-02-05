
package unicom.recive;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * tomcat��ݿ����ӳع�����<br>
 * ʹ��Ϊtomcat���𻷾�<br>
 * ��Ҫ����·����׼����ݿ����������ļ�dbcp.properties
 * 
 * @author ����ǿ
 * @mail songxinqiang123@gmail.com
 * 
 * @time 2013-12-27
 * 
 */
public class DBManager {
 private static final Log log = LogFactory.getLog(DBManager.class);
 private static final String configFile = "DBPool.properties";

 private static BasicDataSource  dataSource;

 static {
  Properties properties = new Properties();
  try {
   properties.load(DBManager.class.getClassLoader()
     .getResourceAsStream(configFile));
   //dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
   dataSource = new BasicDataSource();
   dataSource.setDriverClassName(properties.getProperty("db_driver"));
   dataSource.setUrl(properties.getProperty("db_url"));
   dataSource.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
   dataSource.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
   dataSource.setMaxWait(Integer.parseInt(properties.getProperty("maxWait")));
   dataSource.setUsername(properties.getProperty("db_user"));
   dataSource.setPassword(properties.getProperty("db_pwd"));

   Connection conn = getConn();
   DatabaseMetaData mdm = conn.getMetaData();
   log.info("Connected to " + mdm.getDatabaseProductName() + " "
     + mdm.getDatabaseProductVersion());
   if (conn != null) {
    conn.close();
   }
  } catch (Exception e) {
   log.error("��ʼ�����ӳ�ʧ�ܣ�" + e);
  }
 }

 private DBManager() {
 }

 /**
  * ��ȡ���ӣ������ǵùر�
  * 
  * @see {@link DBManager#closeConn(Connection)}
  * @return
  */
 public static final Connection getConn() {
  Connection conn = null;
  try {
   conn = dataSource.getConnection();
  } catch (SQLException e) {
   log.error("��ȡ��ݿ�����ʧ�ܣ�" + e);
  }
  return conn;
 }

 /**
  * �ر�����
  * 
  * @param conn
  *            ��Ҫ�رյ�����
  */
 public static void closeConn(Connection conn) {
  try {
   if (conn != null && !conn.isClosed()) {
    conn.setAutoCommit(true);
    conn.close();
   }
  } catch (SQLException e) {
   log.error("�ر���ݿ�����ʧ�ܣ�" + e);
  }
 }

}

