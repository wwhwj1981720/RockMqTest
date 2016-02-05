package unicom.recive;

import java.util.HashMap;
import java.util.Map;

import unicom.cacheinit.domain.FeePlolicy;
import unicom.comm.CacheInterface;

public class MyCache implements CacheInterface {
	Map<String,String> tf_f_usermap=new HashMap();
	Map<String,FeePlolicy> tf_f_feepolicymap=new HashMap();
	Map<String,String> tf_f_payrelationmap=new HashMap();
	
	

	@Override
	public void putUser(String phone, String userId) {
		// TODO Auto-generated method stub
		tf_f_usermap.put(phone, userId);

	}

	@Override
	public String getUser(String phone) {
		// TODO Auto-generated method stub
		return tf_f_usermap.get(phone);
		//return null;
	}

	@Override
	public void putFeePolicy(String userId, FeePlolicy policy) {
		// TODO Auto-generated method stub
		tf_f_feepolicymap.put(userId, policy);
		

	}

	@Override
	public FeePlolicy getFeePolicy(String userId) {
		// TODO Auto-generated method stub
		return tf_f_feepolicymap.get(userId);
	}

	@Override
	public void putAccountId(String userId, String accountId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAccoutId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
