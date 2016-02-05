package unicom.log.sorting;

import unicom.comm.LogCode;
import unicom.log.CountStatistic;
import unicom.log.LoggerApi;

/**
 */
public class SortingCacheCountStatistic extends CountStatistic {
    @Override
    public String getLogString(LoggerApi loggerApi) {
        return loggerApi.sortingCacheLog(LogCode.SORTING_CACHE, getProceedNum().get());
    }
}
