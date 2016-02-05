/**
 * 
 */
package unicom.billing.judgemethod;

import unicom.billing.domain.Condition;
import unicom.billing.exception.FeePolicyNotFoundException;
import unicom.cacheinit.domain.FeePlolicy;
import unicom.comm.CacheInterface;
import unicom.comm.api.billing.DbOper;
import unicom.comm.model.Accumulator;
import unicom.comm.model.Record;

/**
 * @author qinan.qn@taobao.com
 *2015年5月22日
 */
public class JudgeMethod {
	public static boolean isCalc(Condition c,Record r,DbOper dbOper,CacheInterface cache){
		if(c.getJudgeMethod().equals("-1")){
			return true;
		}else if(c.getJudgeMethod().equals(">")){
			return getJudgeValue(c, r,dbOper,cache) > c.getJudgeValue();
		}else if(c.getJudgeMethod().equals("<")){
			return getJudgeValue(c, r,dbOper,cache) < c.getJudgeValue();
		}else{
			return getJudgeValue(c, r,dbOper,cache) == c.getJudgeValue();
		}
	}
	private static long getJudgeValue(Condition c,Record r,DbOper dbOper,CacheInterface cache){
		 FeePlolicy feepolicy = cache.getFeePolicy(r.getUserId());
		 if(feepolicy == null){
			 throw new FeePolicyNotFoundException();
		 }
		Accumulator acc = dbOper.findAccumulator(r.getUserId(), String.valueOf(c.getJudgeObject()),String.valueOf(feepolicy.getPolicyInstanceId()));
		if(acc != null){
			return acc.getValue();
		}
		return r.getDuration();
	}
}
