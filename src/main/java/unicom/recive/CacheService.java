package unicom.recive;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import unicom.cacheinit.domain.FeePlolicy;
import unicom.cacheinit.domain.User;
import unicom.comm.CacheInterface;
import unicom.hiber.FeePolicyObj;
import unicom.hiber.FeePolicyObjDAO;
/*
 * 
 * 缓存业务的数据库操作封装 
 * **/
public class CacheService {
	CacheDao dao=new CacheDao();
	public void setUserCache(CacheInterface cacheInterface)
	{
		Connection conn=CacheDao.getCon(); 
		List<User> userlist=null;
		try {
			userlist=dao.findAllUser(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(User user:userlist)
		{
			cacheInterface.putUser(user.getPhoneNum(), String.format("%d", user.getUserId()));
		}
		CacheDao.relaseConnection(conn);
	}
	public void setAllFeePolicy(CacheInterface cacheInterface)
	{
		FeePolicyObjDAO dao=new FeePolicyObjDAO();
		List<FeePolicyObj>feepolicylist=dao.findAll();
		FeePlolicy policy=null;
		for(FeePolicyObj feepolicy:feepolicylist)
		{
			policy=new FeePlolicy ();
			policy.setPolicyId(feepolicy.getFeepolicyId());
			policy.setPolicyInstanceId(feepolicy.getFeepolicyInsId());
			policy.setUserId(feepolicy.getId());
			cacheInterface.putFeePolicy(String.format("%d", feepolicy.getId()),policy);
		}
	}

}
