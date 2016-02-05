/**
 * 
 */
package unicom.billing.method;

import java.util.HashMap;

/**
 * @author qinan.qn@taobao.com
 *2015年5月22日
 */
public class MethodManager {
	private final static HashMap<Integer, Method> methodMap = new HashMap<Integer, Method>();
	static{
		methodMap.put(1, new OneMethod());
		methodMap.put(2, new TwoMethod());
	}
	public static Method getMethod(Integer methodId){
		return methodMap.get(methodId);
	}
}
