/**
 * 
 */
package unicom.billing.method;

/**
 * @author qinan.qn@taobao.com
 *2015年5月22日
 */
public class TwoMethod extends AbstractMethod {

	@Override
	public int getMethodId() {
		return 2;
	}

	/*
	 * 
	 * X1=#-A
	 *X2=X1/B*C
	 *X3=X1%B
	 *X4=X3<D?X3*E:C
	 *X=X2+X4
	 */
	@Override
	public long calc(long input, double[] args) {
		if(args.length < 5){
			throw new RuntimeException("args's num is less than 5");
		}
		double a = args[0];
		double b = args[1];
		double c = args[2];
		double d = args[3];
		double e = args[4];
		
		double x1 = input - a;
		if(x1<=0) return 0;//wwh add
		double x2 = Math.floor(x1 / b)*c;
		double x3 = x1 % b;
		double x4 = x3 < d ? x3 *e:c;
		double x = x2 + x4;
		
		return Math.round(x);
	}

	
}
