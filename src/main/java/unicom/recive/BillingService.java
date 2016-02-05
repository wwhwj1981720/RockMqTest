package unicom.recive;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import unicom.billing.domain.Calculation;
import unicom.billing.domain.Condition;
import unicom.billing.domain.Price;
import unicom.cacheinit.domain.FeePlolicy;
import unicom.cacheinit.domain.PayRelationShip;
import unicom.cacheinit.domain.User;
import unicom.comm.CacheInterface;
import unicom.comm.api.billing.DbOper;
import unicom.comm.api.billing.VersionConflictException;
import unicom.comm.model.Accumulator;
import unicom.comm.model.Bill;
import unicom.comm.model.BillAccount;
import unicom.comm.model.ShortMessageInventory;
import unicom.comm.model.VoiceInventory;
import unicom.comm.model.VolumeInventory;
/*
 * 
 * 批价业务的数据库封装
 * **/
public class BillingService implements DbOper {
	BillingDao dao=new BillingDao();
	public void setUserCache(CacheInterface cacheInterface)
	{
		//Connection conn=CacheDao.getCon(); 
		List<User> userlist=null;
		try {
			userlist=dao.findAllUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch blockd
			e.printStackTrace();
		}
		for(User user:userlist)
		{
			cacheInterface.putUser(user.getPhoneNum(), String.format("%d", user.getUserId()));
		}
		//CacheDao.relaseConnection(conn);
	}
	@Override
	public Accumulator findAccumulator(String userId, String itemId,
			String topBJId) {
		// TODO Auto-generated method stub
		Accumulator acc=null;
		
		try {
			acc=dao.findAccumulator(userId, itemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return acc;
	}
	@Override
	public void saveAccumulator(Accumulator acc)
			throws VersionConflictException {
		
		
		try {
			dao.insertOrupdateAcc(acc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveVoiceInventory(VoiceInventory inventory) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void saveShortMessageInventory(ShortMessageInventory inventory) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveVolumeInventory(VolumeInventory inventory) {
		// TODO Auto-generated method stub
		
		try {
			dao.create(inventory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void saveBill(Bill bill) throws VersionConflictException {
		// TODO Auto-generated method stub
		
		try {
			dao.insertOrupdateBill(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public Bill findBill(String userId, int itemId) {
		// TODO Auto-generated method stub
		Bill bill=null;
		
		try {
			bill=dao.findBill(userId, itemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bill;
		
		//return null;
	}
	@Override
	public void saveBillAccount(BillAccount acount) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Iterator<User> findUser(char removeTag) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<FeePlolicy> findFeePolicy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<PayRelationShip> findPayRelationShip() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<Calculation> getAllCalculations() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<Condition> getAllConditions() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<Price> getAllPrices() {
		// TODO Auto-generated method stub
		return null;
	}

}
