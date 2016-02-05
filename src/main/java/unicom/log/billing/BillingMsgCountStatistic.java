package unicom.log.billing;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 * 批价消息统计
 *
 */
public class BillingMsgCountStatistic extends CountStatistic {
    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.billingMsgLog(LogCode.BILLING_MSG_READ, getProceedNum().get());
    }
}
