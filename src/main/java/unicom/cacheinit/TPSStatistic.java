/**
 * 
 */
package unicom.cacheinit;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import unicom.comm.LogCode;
import unicom.log.LoggerApi;

/**
 * @author qinan.qn@taobao.com
 *2015年5月27日
 */
public class TPSStatistic {
	
	private final static Logger logger = Logger.getLogger(TPSStatistic.class);
	private final AtomicLong proceedNum = new AtomicLong(0);
	private LoggerApi loggerApi;

	private volatile long lastProceededTime;
	
	private long lastLogTime = System.currentTimeMillis();
	private volatile long lastProceedNum = 0;

    private int DURATION = 1000;
    private int flushCount = 10000;
	
	
	/**
	 * @param loggerApi
	 */
	public TPSStatistic(LoggerApi loggerApi) {
		super();
		this.loggerApi = loggerApi;
	}
	
	public synchronized void log(boolean isForce){
		long now = System.currentTimeMillis();
		long proceedNumCache = proceedNum.get();
		double duration = now - lastLogTime;
		if(duration < DURATION && !isForce){
			return;//小于5秒则放弃记录
		}
		
		if(duration == 0 ){
			duration = 0.0001;
		}
		long tps = (long)((proceedNumCache - lastProceedNum) *1000/ duration);
		String logString = loggerApi.initCacheLog(LogCode.INIT_CACHE_TPS, tps);
		logger.info(logString);
		lastProceedNum = proceedNumCache;
		lastLogTime = now;
	}

    public void addStatistic(){
        long num = proceedNum.incrementAndGet();
        if(num % flushCount == 0){
            log(true);
        }
    }
	
	public long getLastProceededTime() {
		return lastProceededTime;
	}
	public void setLastProceededTime(long lastProceededTime) {
		this.lastProceededTime = lastProceededTime;
	}

    public void start() {
        Thread t = new Thread(){
            public void run(){
                while(true){
                    long now = System.currentTimeMillis();
                    if(now - getLastProceededTime() > DURATION){
                        log(false);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        t.start();
    }
}
