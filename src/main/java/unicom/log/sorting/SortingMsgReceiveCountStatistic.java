package unicom.log.sorting;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 */
public class SortingMsgReceiveCountStatistic extends CountStatistic{
    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.sortingMsgReceiveLog(LogCode.SORTING_MSG_RECEIVE, getProceedNum().get());
    }
}
