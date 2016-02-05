package unicom.recive;

import java.sql.Connection;

public class ConnectionSource {
 public static void main(String[] args){
  long begin=System.currentTimeMillis();
  for(int i=0;i<10;i++){
	   Connection conn=DBManager.getConn();
	   System.out.print(i+"   ");
	   DBManager.closeConn(conn);
  }
  long end=System.currentTimeMillis();
  System.out.println("��ʱ��"+(end-begin));
 }
}
