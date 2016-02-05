/**
 * 
 */
package unicom.comm;

import unicom.cacheinit.domain.FeePlolicy;

/**
 * @author 
 *2015年5月21日
 */
public interface CacheInterface {
	public void putUser(String phone,String userId);
	public String getUser(String phone);
	
	public void putFeePolicy(String userId,FeePlolicy policy);
	public FeePlolicy getFeePolicy(String userId);
	
	public void putAccountId(String userId,String accountId);
	public String getAccoutId(String userId);
}
