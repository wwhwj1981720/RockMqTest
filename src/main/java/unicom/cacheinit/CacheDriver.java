package unicom.cacheinit;


import unicom.comm.LogCode;
import unicom.log.LoggerApi;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

/**
 * Cache加载的入口
 *
 * Created by  on 15-5-21.
 */
public class CacheDriver {
	private final static Logger logger = Logger.getLogger(CacheDriver.class);
	
	private ExecutorService executorService;
    private int taskNum;
    private CacheLoader cacheLoader;
    private LoggerApi loggerApi;
    

    public LoggerApi getLoggerApi() {
		return loggerApi;
	}

	public void setLoggerApi(LoggerApi loggerApi) {
		this.loggerApi = loggerApi;
	}

	public void start() {
        final CacheLoader cacheLoader1 = cacheLoader;
       
        final TPSStatistic TPSStatistic = new TPSStatistic(loggerApi);
        cacheLoader1.setTpsStaticstic(TPSStatistic);
        TPSStatistic.start();

        for(int i=0; i<taskNum; ++i) {
            final int index = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        cacheLoader1.loadFromDb() ;
                    } catch (Exception e) {
                    	logger.error(loggerApi.initCacheLog(LogCode.INITCACHE_ERROR, e));
                    }
                }
            });
        }

        TPSStatistic.start();
    }

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public CacheLoader getCacheLoader() {
		return cacheLoader;
	}

	public void setCacheLoader(CacheLoader cacheLoader) {
		this.cacheLoader = cacheLoader;
	}
    
}
