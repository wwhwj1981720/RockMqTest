package unicom.billing;

import unicom.billing.domain.Calculation;
import unicom.billing.domain.Condition;
import unicom.billing.domain.Price;
import unicom.billing.judgemethod.JudgeMethod;
import unicom.billing.method.Method;
import unicom.billing.method.MethodManager;
import unicom.comm.CacheInterface;
import unicom.comm.api.billing.DbOper;
import unicom.comm.model.Bill;
import unicom.comm.model.Record;

import java.util.Collection;

/**
 * 批帐处理
 */
public class BillingAction {

    private RuleRepository ruleRepository;
    private DbOper dbOper;
    private CacheInterface cache;


    /**
     * 一批
     *
     * @param record 记录
     * @param position　一批，二批，还是优惠
     */
    public long calculate(Record record,long value, int position) {
        Collection<Price> prices = ruleRepository.findPrices(record.getUserId(), record.getType(), position);
        long result =value;
        for(Price price : prices) {
            Condition condition = ruleRepository.findCondition(price.getConditionId());
            result = calculate(record, condition, result);
        }

        return result;
    }

    private long calculate(Record record, Condition condition, long value) {
        if(JudgeMethod.isCalc(condition, record,dbOper,cache)) {
            Calculation calculation = ruleRepository.findCalculation(condition.getCalcId());
            Method method = MethodManager.getMethod(calculation.getCalMethod());

            String [] strs = calculation.getCalcValue().split(",");
            long v = method.calc(value, strs);
            
            return v;
        } else {
            return 0;
        }
    }
    public long calculateSimple(Record record,long value,int postion) {
        
            if(value==0) return 0;
    		Method method = MethodManager.getMethod(postion);
            String str=null;
            String contion2="4096,1024,60000,200,300";
            String contion1="0, 300,1,0";
            if(postion==1)
            {
            	str=contion1;
            }
            else
            {
            	str=contion2;
            }
            String [] strs =str.split(",");
            long v =0;
            v=method.calc(value, strs);
            return v;
            
       
    }
    public long findItemId(Record record){
    	 Collection<Price> prices = ruleRepository.findPrices(record.getUserId(), record.getType(), 3);
         for(Price price : prices) {
             Condition condition = ruleRepository.findCondition(price.getConditionId());
             Calculation calculation = ruleRepository.findCalculation(condition.getCalcId());
             return calculation.getItemId();
         }
         return 0;
    }

    public void setDbOper(DbOper dbOper) {
        this.dbOper = dbOper;
    }

    public void setRuleRepository(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

	public CacheInterface getCache() {
		return cache;
	}

	public void setCache(CacheInterface cache) {
		this.cache = cache;
	}
    
}
