package unicom.load;

import unicom.Sender.MyUserSender;
import unicom.comm.CacheInterface;
import unicom.comm.Sender;
import unicom.log.LoggerApi;
import unicom.log.sorting.SortingCacheCountStatistic;
import unicom.log.sorting.SortingMsgReceiveCountStatistic;
import unicom.log.sorting.SortingMsgSendCountStatistic;
import unicom.recive.CacheService;
import unicom.recive.MyCache;
import unicom.sorting.MyLog;
import unicom.sorting.SortingMessageEvent;

public class Sort {
	SortingMessageEvent event=new SortingMessageEvent();
	SortingMsgSendCountStatistic sortingMsgSendCountStatistic=new SortingMsgSendCountStatistic();
	SortingMsgReceiveCountStatistic sortingMsgReceiveCountStatistic=new SortingMsgReceiveCountStatistic();
	SortingCacheCountStatistic sortingCacheCountStatistic=new SortingCacheCountStatistic();
	LoggerApi sortlog=new MyLog("wwh");
	CacheInterface cacheInterface=new MyCache();
	CacheService service=new CacheService();
	Sender senderuser;
	public Sort(Sender senderuser) {
		super();
		this.senderuser = senderuser;
	}
	public Sort() {
		super();
		this.senderuser = new MyUserSender();
	}
	
	public void init()
	{
		event.setSortingLog(sortlog);
		event.setSortingMsgSendCountStatistic(sortingMsgSendCountStatistic);
		event.setSortingMsgReceiveCountStatistic(sortingMsgReceiveCountStatistic);
		event.setSortingCacheCountStatistic(sortingCacheCountStatistic);
		
		//MyCache 
		
		service.setUserCache(cacheInterface);
		service.setAllFeePolicy(cacheInterface);
		event.setCacheInterface(cacheInterface);
		event.setSender(senderuser);//
	}

}
