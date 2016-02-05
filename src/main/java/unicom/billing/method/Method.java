/**
 * 
 */
package unicom.billing.method;

/**
 * @author qinan.qn@taobao.com
 *2015年5月22日
 */
public interface Method {
	public int getMethodId();
	public long calc(long input,String[] args);
	public long calc(long input,double[] args);
}
