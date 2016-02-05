package unicom.cacheinit;

import unicom.cacheinit.domain.FeePlolicy;
import unicom.cacheinit.domain.PayRelationShip;
import unicom.cacheinit.domain.User;
import unicom.comm.CacheInterface;
import unicom.comm.api.billing.DbOper;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/**
 * 从数据库加载内容到缓存
 * 
 * Created by on 15-5-21.
 */
public class CacheLoader {

	private ExecutorService sendExecotorService;
	private CacheInterface cacheInterface;
	private DbOper dbOper;
    //配置的discountId，替换加载到的数据库中的
    private Long discountId = 10000001L;
    private TPSStatistic tpsStaticstic;
    
    
	public void setTpsStaticstic(TPSStatistic tpsStaticstic) {
		this.tpsStaticstic = tpsStaticstic;
	}

	public void loadFromDb() throws Exception {
		char removeTag = '0';

		boolean isUserContinue = true;
		boolean isPayContinue = true;
		boolean isFeeContinue = true;
		while (isUserContinue || isPayContinue || isFeeContinue) {
			if (isUserContinue) {
				Iterator<User> userIterator = dbOper.findUser(removeTag);
				if (userIterator != null) {
					while (userIterator.hasNext()) {
						final User user = userIterator.next();
						putToCache(user);
					}
				} else {
					isUserContinue = false;
				}
			}

			if (isPayContinue) {
				Iterator<PayRelationShip> payRelationShipIterator = dbOper.findPayRelationShip();
				if (payRelationShipIterator != null) {
					while (payRelationShipIterator.hasNext()) {
						final PayRelationShip payRelationShip = payRelationShipIterator.next();
						putToCache(payRelationShip);
					}
				} else {
					isPayContinue = false;
				}
			}
			if (isFeeContinue) {
				Iterator<FeePlolicy> feePlolicyIterator = dbOper.findFeePolicy();
				if (feePlolicyIterator != null) {
					while (feePlolicyIterator.hasNext()) {
						final FeePlolicy feePlolicy = feePlolicyIterator.next();
						putToCache(feePlolicy);

					}
				} else {
					isFeeContinue = false;
				}
			}
		}
		
	}
	
	private void putToCache(final PayRelationShip payRelationShip) {
		sendExecotorService.submit(new Runnable() {
			@Override
			public void run() {
				cacheInterface.putAccountId(Long.toString(payRelationShip.getUserId()), Long.toString(payRelationShip.getAccountId()));
				tpsStaticstic.addStatistic();
			}
		});
	}

	private void putToCache(final FeePlolicy feePlolicy) {

        //替换discountId
        feePlolicy.setPolicyId(discountId);

		sendExecotorService.submit(new Runnable() {
			@Override
			public void run() {
				cacheInterface.putFeePolicy(Long.toString(feePlolicy.getUserId()), feePlolicy);
				tpsStaticstic.addStatistic();
			}
		});
	}

	private void putToCache(final User user) {
		sendExecotorService.submit(new Runnable() {
			@Override
			public void run() {
				cacheInterface.putUser(user.getPhoneNum(), Long.toString(user.getUserId()));
                tpsStaticstic.addStatistic();
			}
		});
	}

	public ExecutorService getSendExecotorService() {
		return sendExecotorService;
	}

	public void setSendExecotorService(ExecutorService sendExecotorService) {
		this.sendExecotorService = sendExecotorService;
	}

	public CacheInterface getCacheInterface() {
		return cacheInterface;
	}

	public void setCacheInterface(CacheInterface cacheInterface) {
		this.cacheInterface = cacheInterface;
	}

	public DbOper getDbOper() {
		return dbOper;
	}

	public void setDbOper(DbOper dbOper) {
		this.dbOper = dbOper;
	}

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}
