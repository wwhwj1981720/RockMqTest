/**
 * 
 */
package unicom.billing.method;

/**
 * @author qinan.qn@taobao.com 2015年5月22日
 */
public class OneMethod extends AbstractMethod{

	@Override
	public int getMethodId() {
		return 1;
	}

	/**
	 * (#-A)*B/C+D
	 */
	@Override
	public long calc(long input, double[] args) {
		if (args.length < 4) {
			throw new RuntimeException("args's num is less than 4");
		}
		double a = args[0];
		double b = args[1];
		double c = args[2];
		double d = args[3];
		return Math.round((input - a) * b / c + d);
	}
}
