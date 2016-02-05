/**
 * 
 */
package unicom.billing.method;

/**
 * @author qinan.qn@taobao.com
 *2015年5月22日
 */
public abstract class AbstractMethod implements Method{
	protected double[] getDoubleArray(String[] args){
		double[] da = new double[args.length];
		for(int i = 0 ; i < args.length ; ++i){
			da[i] = Double.parseDouble(args[i]);
		}
		return da;
	}
	public long calc(long input, String[] args) {
		return calc(input, getDoubleArray(args));
	}
}
