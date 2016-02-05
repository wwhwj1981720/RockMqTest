/**
 * 
 */
package unicom.sorting;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import unicom.comm.CacheInterface;
import unicom.comm.LogCode;
import unicom.comm.MessageEvent;
import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.load.AsyncSendCallBack;
import unicom.log.LoggerApi;
import unicom.log.sorting.SortingCacheCountStatistic;
import unicom.log.sorting.SortingMsgReceiveCountStatistic;
import unicom.log.sorting.SortingMsgSendCountStatistic;

import javax.annotation.PostConstruct;

/**
 * 分拣逻辑
 */
public class SortingMessageEvent implements MessageEvent
{
    private Logger log = Logger.getLogger(SortingMessageEvent.class);
    
    private LoggerApi sortingLog;

    private CacheInterface cacheInterface;

    private Sender sender;

    private SortingMsgSendCountStatistic sortingMsgSendCountStatistic;
    private SortingMsgReceiveCountStatistic sortingMsgReceiveCountStatistic;
    private SortingCacheCountStatistic sortingCacheCountStatistic;
    
    private AtomicBoolean started = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        sortingCacheCountStatistic.start();
        sortingMsgReceiveCountStatistic.start();
        sortingCacheCountStatistic.start();
    }

    private AsyncSendCallBack callback = new AsyncSendCallBack() {
		@Override
		public boolean isDone() {
			return false;
		}
		
		@Override
		public Long get() {
			return null;
		}
		
		@Override
		public void addSendCount() {
			sortingMsgSendCountStatistic.addStatistic();
		}
	};

    @Override
    public void onMessageReceive(Record record)
    {

        if(!started.getAndSet(true)) {
            long begin = System.currentTimeMillis();
            log.info(sortingLog.sortingStart(LogCode.SORTING_START, begin));
        }

      sortingMsgReceiveCountStatistic.addStatistic();
        if (cacheInterface == null || sender == null)
        {
            // 异常处理
            throw new IllegalArgumentException(
                    "catch interface or sender are required ");
        }
        String srcPhone = record.getSrcPhone();
        if (null == srcPhone || "".equals(srcPhone))
        {
            // 异常处理
            log.error(sortingLog.sortingSrcPhoneError(LogCode.SORTING_SRCPHONE_ERROR, record));
            return;
        }

        sortingCacheCountStatistic.addStatistic();
        String userId = cacheInterface.getUser(srcPhone);
        if (null == userId || "".equals(userId))
        {
            // 异常处理
            log.error(sortingLog.sortingUserIDNotFoundLog(LogCode.SORTING_USER_ID_NOT_FOUND, record.getSrcPhone()));
            return;
        }

        record.setUserId(userId);

        
        sender.sendRecord(record,callback);

    }

    public CacheInterface getCacheInterface()
    {
        return cacheInterface;
    }

    public void setCacheInterface(CacheInterface cacheInterface)
    {
        this.cacheInterface = cacheInterface;
    }

    public Sender getSender()
    {
        return sender;
    }

    public void setSender(Sender sender)
    {
        this.sender = sender;
    }

    public LoggerApi getSortingLog()
    {
        return sortingLog;
    }

    public void setSortingLog(LoggerApi sortingLog)
    {
        this.sortingLog = sortingLog;
    }

	public void setSortingMsgSendCountStatistic(SortingMsgSendCountStatistic sortingMsgSendCountStatistic) {
		this.sortingMsgSendCountStatistic = sortingMsgSendCountStatistic;
	}

	public void setSortingMsgReceiveCountStatistic(SortingMsgReceiveCountStatistic sortingMsgReceiveCountStatistic) {
		this.sortingMsgReceiveCountStatistic = sortingMsgReceiveCountStatistic;
	}

	public void setSortingCacheCountStatistic(SortingCacheCountStatistic sortingCacheCountStatistic) {
		this.sortingCacheCountStatistic = sortingCacheCountStatistic;
	}
    
}
