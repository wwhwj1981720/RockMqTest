package unicom.recive;

import java.awt.TrayIcon.MessageType;


import unicom.Sender.MyUserSender;
import unicom.Sender.MyphoneSender;
import unicom.billing.BillingAction;
import unicom.billing.BillingMessageEvent;
import unicom.billing.RuleRepository;
import unicom.comm.CacheInterface;
import unicom.comm.MessageEvent;
import unicom.comm.Sender;
import unicom.comm.api.billing.DbOper;
import unicom.comm.model.Record;
import unicom.log.LoggerApi;
import unicom.log.billing.BillingDBReadCountStatistic;
import unicom.log.billing.BillingDBWriteCountStatistic;
import unicom.log.billing.BillingMsgCountStatistic;
import unicom.sorting.MyLog;

public class Billing {
	public BillingMessageEvent billingevent=new BillingMessageEvent();
	private BillingMsgCountStatistic msgCountStat=new BillingMsgCountStatistic() ;
	private BillingDBReadCountStatistic dbReadCountStatistic=new BillingDBReadCountStatistic();
	private BillingDBWriteCountStatistic dbWriteCountStatistic=new BillingDBWriteCountStatistic();
	private LoggerApi billinglog=new MyLog("wwh");
	BillingAction billingAction=new BillingAction();
	RuleRepository ruleRepository=new RuleRepository();
	CacheInterface cacheInterface=new MyCache();
	CacheService service=new CacheService();
	
	DbOper dbOper=new BillingService();
	public void init()
	{
		billingevent.setDbReadCountStatistic(dbReadCountStatistic);
		billingevent.setDbWriteCountStatistic(dbWriteCountStatistic);
		billingevent.setMsgCountStat(msgCountStat);
		billingevent.setLoggerApi(billinglog);
		billingevent.setDbOper(dbOper);
		billingAction.setRuleRepository(ruleRepository);
		billingevent.setBillingAction(billingAction);
		service.setUserCache(cacheInterface);
		service.setAllFeePolicy(cacheInterface);
		billingevent.setCache(cacheInterface);
	}
	public void pushRecord(MessageEvent event)
	{
		
		Record record=MyphoneSender.recordlist.poll();
		while(record!=null)
		{
			event.onMessageReceive(record);
			
			billingevent.onMessageReceive(record);
			record=MyphoneSender.recordlist.poll();
		}
	}
	public DbOper getDbOper() {
		return dbOper;
	}
	public void setDbOper(DbOper dbOper) {
		this.dbOper = dbOper;
	}
	
	
}
