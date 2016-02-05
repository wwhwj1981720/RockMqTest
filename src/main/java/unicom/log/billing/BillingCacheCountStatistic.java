package unicom.log.billing;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 * 批价缓存读取统计
 */
public class BillingCacheCountStatistic extends CountStatistic {
    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.billingCacheLog(LogCode.BILLING_CACHE_READ, getProceedNum().get());
    }
}
