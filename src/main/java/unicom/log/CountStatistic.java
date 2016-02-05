package unicom.log;

import org.apache.log4j.Logger;
import unicom.comm.LogCode;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数统计器,覆盖getLogString()获得不同的log格式内容
 */
public abstract class CountStatistic {
	private final static Logger logger = Logger.getLogger(CountStatistic.class);
	private final AtomicLong proceedNum = new AtomicLong(0);
	private LoggerApi loggerApi;

	private volatile long lastProceededTime;

	private long lastLogTime = System.currentTimeMillis();
	private volatile long lastProceedNum = 0;

	private int durationCount = 1000;
	private int flushCount = 10000;

	public abstract String getLogString(LoggerApi loggerApi);

	public CountStatistic() {
	}

	public CountStatistic(LoggerApi loggerApi) {
		this.loggerApi = loggerApi;
	}

	public void setLoggerApi(LoggerApi loggerApi) {
		this.loggerApi = loggerApi;
	}

	/**
	 * 
	 * @param loggerApi
	 *            注入loggerAPI i的impl
	 * @param durationCount
	 *            间隔多长时间打印一次， 单位为毫秒
	 * @param flushCount
	 *            累计多少数量打印一次日志
	 */
	public CountStatistic(LoggerApi loggerApi, int durationCount, int flushCount) {
		super();
		this.durationCount = durationCount;
		this.flushCount = flushCount;
	}

	public synchronized void log(boolean isForce) {
		long now = System.currentTimeMillis();
		long proceedNumCache = proceedNum.get();
		double duration = now - lastLogTime;
		if (duration < durationCount && !isForce) {
			return;// 小于5秒则放弃记录
		}

		if (duration == 0) {
			duration = 0.0001;
		}
		String logString = getLogString(loggerApi);
		logger.info(logString);
		lastProceedNum = proceedNumCache;
		lastLogTime = now;
	}

	public void addStatistic() {
		long num = proceedNum.incrementAndGet();
		if (num % flushCount == 0) {
			log(true);
		}
	}

	public long getLastProceededTime() {
		return lastProceededTime;
	}

	public void setLastProceededTime(long lastProceededTime) {
		this.lastProceededTime = lastProceededTime;
	}

	private boolean isStart = false;

	public synchronized void start() {
		if (!isStart) {
			Thread t = new Thread() {
				public void run() {
					while (true) {
						long now = System.currentTimeMillis();
						if (now - getLastProceededTime() > durationCount) {
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
			isStart = true;
		}
	}

	public AtomicLong getProceedNum() {
		return proceedNum;
	}
}
