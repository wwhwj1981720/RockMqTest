package unicom.log.billing;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 * 批价数据库写入统计
 */
public class BillingDBWriteCountStatistic extends CountStatistic {

    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.billingDBWriteLog(LogCode.BILLING_DB_WRITE, getProceedNum().get());
    }
}
