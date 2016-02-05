package unicom.log.billing;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 * 批价数据库读取统计
 */
public class BillingDBReadCountStatistic extends CountStatistic {

    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.billingDBReadLog(LogCode.BILLING_DB_READ, getProceedNum().get());
    }
}
