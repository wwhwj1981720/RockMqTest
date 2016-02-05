/**
 * 
 */
package unicom.billing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import unicom.billing.method.Method;
import unicom.billing.method.MethodManager;
import unicom.comm.CacheInterface;
import unicom.comm.LogCode;
import unicom.comm.MessageEvent;
import unicom.comm.api.billing.DbOper;
import unicom.comm.api.billing.VersionConflictException;
import unicom.comm.model.Accumulator;
import unicom.comm.model.Bill;
import unicom.comm.model.BillAccount;
import unicom.comm.model.Record;
import unicom.comm.model.ShortMessageInventory;
import unicom.comm.model.VoiceInventory;
import unicom.comm.model.VolumeInventory;
import unicom.log.LoggerApi;
import unicom.log.billing.BillingDBReadCountStatistic;
import unicom.log.billing.BillingDBWriteCountStatistic;
import unicom.log.billing.BillingMsgCountStatistic;

import javax.annotation.PostConstruct;

/**
 * 2015年5月21日
 *收到 消息开始 计算  价格 
 * 
 */
public class BillingMessageEvent implements MessageEvent {
	public void setLoggerApi(LoggerApi loggerApi) {
		this.loggerApi = loggerApi;
	}

	private final Logger logger = Logger.getLogger(BillingMessageEvent.class);

	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");

	private BillingAction billingAction;
	private DbOper dbOper;
	private CacheInterface cache;

	private BillingMsgCountStatistic msgCountStat;
	private BillingDBReadCountStatistic dbReadCountStatistic;
	private BillingDBWriteCountStatistic dbWriteCountStatistic;

	private AtomicBoolean started = new AtomicBoolean(false);

	private LoggerApi loggerApi;

	@PostConstruct
	public void postConstruct() {
		msgCountStat.start();
		dbReadCountStatistic.start();
		dbWriteCountStatistic.start();
	}

	@Override
	public void onMessageReceive(Record record) {

		if (!started.getAndSet(true)) {
			long begin = System.currentTimeMillis();
			logger.info(loggerApi.billingStart(LogCode.BILLING_START, begin));
		}

		msgCountStat.addStatistic();
		// TODO 批价逻辑
		try {
			// 一批

			long oneFee = billingAction.calculateSimple(record, record.getDuration(), 1);
			
			//saveAccumulator(record) 返回的是二批的费用
			long twoFee = saveAccumulator(record);
			
			

			saveInventory(record, oneFee, Math.min(oneFee, twoFee));

			dbReadCountStatistic.addStatistic();
			// 判断是否存入月租
			Bill bill = dbOper.findBill(record.getUserId(), 20000);
			if (bill == null) {
				try {
					bill = new Bill();
					bill.setUserId(Long.parseLong(record.getUserId()));
					bill.setDetailItemCode(20000);
					bill.setInitFee(0);
					//long threeFee = billingAction.calculate(record, bill.getInitFee(), 3);
					//long threeFee = billingAction.calculateSimple(record,bill.getInitFee(),2);
					long threeFee=296000;

					bill.setDiscountFee(bill.getInitFee() - threeFee);
					bill.setFee(threeFee);

					dbWriteCountStatistic.addStatistic();
					dbOper.saveBill(bill);
				} catch (VersionConflictException e) {
				}
				String accountId = getAccountId(record);
				if (accountId == null || accountId.equals("")) {
					logger.info(String.format("[error][%s] not found account", record.getUserId()));
				} else {
					BillAccount billaccount = new BillAccount();
					billaccount.setAccountId(Long.parseLong(accountId));
					billaccount.setDetailItemCode(20000);
					billaccount.setDiscountFee(bill.getDiscountFee());
					billaccount.setFee(bill.getFee());
					billaccount.setInitFee(bill.getInitFee());
					billaccount.setUserId(Long.parseLong(record.getUserId()));

					dbWriteCountStatistic.addStatistic();
					dbOper.saveBillAccount(billaccount);
				}
			}

			// 保存用户账单
			// 判断是否存biz_type对应的
			if (Math.min(oneFee, twoFee) != 0) {
				bill = null;
				int accountItemId = 20000 + record.getType();
				while (true) {
					try {
						

						dbReadCountStatistic.addStatistic();
						bill = dbOper.findBill(record.getUserId(), accountItemId);
						if (bill == null) {
							bill = new Bill();
						}
						bill.setUserId(Long.parseLong(record.getUserId()));
						bill.setDetailItemCode(accountItemId);
						bill.setInitFee(Math.min(oneFee, twoFee) + bill.getInitFee());
						bill.setDiscountFee(0);
						bill.setFee(bill.getInitFee());

						dbReadCountStatistic.addStatistic();
						dbOper.saveBill(bill);
						break;
					} catch (VersionConflictException e) {
					}
				}/*
				String accountId = getAccountId(record);
				if (accountId == null || accountId.equals("")) {
					logger.info(String.format("[error][%s] not found account", record.getUserId()));
				} else {
					BillAccount billaccount = new BillAccount();
					billaccount.setAccountId(Long.parseLong(accountId));
					billaccount.setDetailItemCode(accountItemId);
					billaccount.setDiscountFee(bill.getDiscountFee());
					billaccount.setFee(bill.getFee());
					billaccount.setInitFee(bill.getInitFee());
					billaccount.setUserId(Long.parseLong(record.getUserId()));

					dbWriteCountStatistic.addStatistic();
					dbOper.saveBillAccount(billaccount);
					
				}*/
			}

		} catch (Throwable e) {
			// 需要记录日志
			String result = loggerApi.billingLog(LogCode.BILLING_ERROR, e, record);
			logger.error(result, e);
		}
	}
	//手机号查账户id
	private String getAccountId(Record r) {
		long phone = Long.parseLong(r.getSrcPhone());
		if (phone % 1000 == 444) {
			return "9999999999999999";
		} else {
			//从缓存中查询
			return cache.getAccoutId(r.getUserId());
		}
	}
	//保存累积量，同时返回本条话单的费用
	private long saveAccumulator(Record r) {
		while (true) {
			try {
				long feePolicyInsId = cache.getFeePolicy(r.getUserId()).getPolicyInstanceId();
				//dbReadCountStatistic.addStatistic();
				String str="4096,1024,60000,200,300";
				//long value=r.
				String []strs=str.split(",");
				
				Accumulator acc = dbOper.findAccumulator(String.valueOf(r.getUserId()), String.valueOf(getItemId(r.getType())),String.valueOf(0));

				if (acc == null) {
					acc = new Accumulator();
				}
				long org =  billingAction.calculateSimple(r, acc.getValue(), 2);
//				Method method = MethodManager.getMethod(2);
//				long org= method.calc( acc.getValue(), strs);
				
				acc.setBillId(getItemId(r.getType()));
				acc.setTopBJId(feePolicyInsId);
				
				acc.setUserId(Long.parseLong(r.getUserId()));
				acc.setValue(acc.getValue() + r.getDuration());//新增的累积量

				dbWriteCountStatistic.addStatistic();

				dbOper.saveAccumulator(acc);
				//billingAction.calculate(r, acc.getValue(), 2) 是加上了本条信息的r.getDuration
				//return billingAction.calculate(r, acc.getValue(), 2) -  org;
				return  billingAction.calculateSimple(r,acc.getValue(),2)-org;
			} catch (VersionConflictException e) {
			}
		}
	}
	//保存到清单表
	private void saveInventory(Record r, long orgCfee, long cfee/* （一批二批最小）费用 */) {
		Date now = new Date();
		switch (r.getType()) {
		case 1:
			VoiceInventory inventory = new VoiceInventory();
			inventory.setBillItem(String.valueOf(getItemId(r.getType())));
			inventory.setBizType(r.getType());
			inventory.setCfee(cfee);
			inventory.setDealTime(getTime(now));
			inventory.setDuration(r.getDuration());
//			inventory.setEndDate(getDate(r.getStart()));
//			inventory.setEndTime(getTime(r.getStart()));
			inventory.setErrorCode(0);
			inventory.setInDBTime(getTime(now));
			inventory.setOrgCfee(orgCfee);
			inventory.setSrcFileName(r.getSrcFileName());
//			inventory.setStartDate(getDate(r.getStart()));
//			inventory.setStartTime(getTime(r.getStart()));
			inventory.setTargetPhone(r.getTargetPhone());
			inventory.setSrcPhone(r.getSrcPhone());
			inventory.setUserId(r.getUserId());

			dbWriteCountStatistic.addStatistic();

			dbOper.saveVoiceInventory(inventory);
			break;
		case 2:
			VolumeInventory vi = new VolumeInventory();
			vi.setBizType(r.getType());
			vi.setCfee(cfee);
			vi.setDataUp(r.getDuration());
			vi.setErrorCode(0);
			vi.setInDBTime(getTime(now));
			vi.setOrgCfee(orgCfee);
			vi.setSrcFileName(r.getSrcFileName());
			vi.setSrcPhone(r.getSrcPhone());
			vi.setUserId(r.getUserId());
			
			dbWriteCountStatistic.addStatistic();
			
			dbOper.saveVolumeInventory(vi);
			break;
		default:
			ShortMessageInventory smi = new ShortMessageInventory();
//			smi.setApplyDate(getDate(r.getStart()));
//			smi.setApplyTime(getTime(r.getStart()));
			smi.setErrorCode(0);
			smi.setIfee(cfee);
			smi.setMfee(orgCfee);
			smi.setSrcFileName(r.getSrcFileName());
			smi.setSrcPhone(r.getSrcPhone());
			smi.setUserId(r.getUserId());
			smi.setInDBTime(getTime(now));
			
			dbWriteCountStatistic.addStatistic();
			
			dbOper.saveShortMessageInventory(smi);
			break;
		}
	}

	private int getItemId(int type) {
		switch (type) {
		case 1 /* voice */:
			return 10001;
		case 2 /* volumn */:
			return 10002;
		case 3 /* sm */:
			return 10003;
		default:
			return 10004;
		}
	}

	private String getDate(Date d) {
		return DATE_FORMAT.format(d);
	}

	private String getTime(Date d) {
		return TIME_FORMAT.format(d);
	}

	public BillingAction getBillingAction() {
		return billingAction;
	}

	public void setBillingAction(BillingAction billingAction) {
		this.billingAction = billingAction;
	}

	public DbOper getDbOper() {
		return dbOper;
	}

	public void setDbOper(DbOper dbOper) {
		this.dbOper = dbOper;
	}

	public CacheInterface getCache() {
		return cache;
	}

	public void setCache(CacheInterface cache) {
		this.cache = cache;
	}

	public void setMsgCountStat(BillingMsgCountStatistic msgCountStat) {
		this.msgCountStat = msgCountStat;
	}

	public void setDbWriteCountStatistic(BillingDBWriteCountStatistic dbWriteCountStatistic) {
		this.dbWriteCountStatistic = dbWriteCountStatistic;
	}

	public void setDbReadCountStatistic(BillingDBReadCountStatistic dbReadCountStatistic) {
		this.dbReadCountStatistic = dbReadCountStatistic;
	}
}
