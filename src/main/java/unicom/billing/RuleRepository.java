package unicom.billing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import unicom.billing.domain.Calculation;
import unicom.billing.domain.Condition;
import unicom.billing.domain.Price;
import unicom.billing.exception.CalculationNotFoundException;
import unicom.billing.exception.ConditionNotFoundException;
import unicom.billing.exception.FeePolicyNotFoundException;
import unicom.cacheinit.domain.FeePlolicy;
import unicom.comm.CacheInterface;
import unicom.comm.api.billing.DbOper;
import unicom.log.billing.BillingCacheCountStatistic;
import unicom.log.billing.BillingDBReadCountStatistic;

/**
 * 组装并查找各种规则
 */
public class RuleRepository {

    private DbOper dbOper;
    private CacheInterface cacheInterface;

    //价格的映射
    //discountId --> { position --> { bizType --> price} }
    private Map<Long, Map<Integer, Map<Integer, Price>>> priceMap = new HashMap<Long, Map<Integer, Map<Integer, Price>>>();
    //条件映射
    //conditionId --> condition
    private Map<Long, Condition> conditionMap = new HashMap<Long, Condition>();
    //计算映射
    //calId --> Calculation
    private Map<Long, Calculation> calculationMap = new HashMap<Long, Calculation>();

    //默认资费的代码，支持注入修改
    private Long defaultDiscountId = 99999999L;

    //缓存读取计数统计
    private BillingCacheCountStatistic cacheCountStatistic;
    private BillingDBReadCountStatistic dbReadCountStatistic;

    @PostConstruct
    public void postContruct() {
        dbReadCountStatistic.addStatistic();
        Iterator<Price> priceIterator = dbOper.getAllPrices();
        while(priceIterator.hasNext()) {
            Price price = priceIterator.next();
            Map<Integer, Map<Integer, Price>> position2PriceMap = priceMap.get(price.getDiscountId());

            //put if absent
            if(position2PriceMap == null) {
                position2PriceMap = new HashMap<Integer, Map<Integer, Price>>();
                priceMap.put(price.getDiscountId(), position2PriceMap);
            }

            Map<Integer, Price> biz2Map = position2PriceMap.get(price.getExePosition());

            //put if absent
            if(biz2Map == null) {
                biz2Map = new HashMap<Integer, Price>();
                position2PriceMap.put(price.getExePosition(), biz2Map);
            }

            biz2Map.put(price.getBizType(), price);
        }

        dbReadCountStatistic.addStatistic();
        Iterator<Condition> conditionIterator = dbOper.getAllConditions();
        while(conditionIterator.hasNext()) {
            Condition condition = conditionIterator.next();
            conditionMap.put(condition.getConditionId(), condition);
        }

        dbReadCountStatistic.addStatistic();
        Iterator<Calculation> calculationIterator = dbOper.getAllCalculations();
        while(calculationIterator.hasNext()) {
            Calculation calculation = calculationIterator.next();
            calculationMap.put(calculation.getCalcId(), calculation);
        }

        cacheCountStatistic.start();
    }
    // position 说明当前是几批
    public Collection<Price> findPrices(String userId, int type, int position) {

        Long discountId = defaultDiscountId;
        //不是一批，采用套餐的discountId
        if(position != 1) {
            cacheCountStatistic.addStatistic();
            FeePlolicy feePlolicy = cacheInterface.getFeePolicy(userId);
            if(feePlolicy ==null ){
            	throw new FeePolicyNotFoundException();
            }
            discountId = feePlolicy.getPolicyId();
        }

        Map<Integer, Map<Integer, Price>> discountId2Map = priceMap.get(discountId);
        if(discountId2Map == null) {
            return Collections.EMPTY_LIST;
        }

        Map<Integer, Price> position2Map = discountId2Map.get(position);
        if(position2Map == null) {
            return Collections.EMPTY_LIST;
        }

        //优惠需要单独计算
        if(position == 3) {
            //0就是空的， 优惠去空的计算
            Price price = position2Map.get(0);
            List<Price> list = new ArrayList<Price>();
            list.add(price);

            return list;
        } else {
            Price price = position2Map.get(type);
            List<Price> list = new ArrayList<Price>();
            list.add(price);

            return list;
        }
    }

    public Condition findCondition(long conditionId) {
    	Condition condition =  conditionMap.get(conditionId);
    	if(condition == null){
    		throw new ConditionNotFoundException();
    	}
    	return condition;
    }

    public Calculation findCalculation(long calcId) {
    	Calculation c = calculationMap.get(calcId);
    	if(c == null){
    		throw new CalculationNotFoundException();
    	}
        return calculationMap.get(calcId);
    }

    public void setCacheInterface(CacheInterface cacheInterface) {
        this.cacheInterface = cacheInterface;
    }

    public void setDbOper(DbOper dbOper) {
        this.dbOper = dbOper;
    }

    public void setDefaultDiscountId(Long defaultDiscountId) {
        this.defaultDiscountId = defaultDiscountId;
    }

    public void setCacheCountStatistic(BillingCacheCountStatistic cacheCountStatistic) {
        this.cacheCountStatistic = cacheCountStatistic;
    }

    public void setDbReadCountStatistic(BillingDBReadCountStatistic dbReadCountStatistic) {
        this.dbReadCountStatistic = dbReadCountStatistic;
    }
}
