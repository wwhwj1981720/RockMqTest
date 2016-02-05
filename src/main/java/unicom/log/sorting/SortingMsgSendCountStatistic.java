package unicom.log.sorting;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 */
public class SortingMsgSendCountStatistic extends CountStatistic {
    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.sortingMsgSendLog(LogCode.SORTING_MSG_SEND, getProceedNum().get());
    }
}
