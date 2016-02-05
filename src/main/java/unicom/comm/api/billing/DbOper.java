/**
 * 
 */
package unicom.comm.api.billing;

import java.util.Iterator;

import unicom.billing.domain.Calculation;
import unicom.billing.domain.Condition;
import unicom.billing.domain.Price;
import unicom.cacheinit.domain.FeePlolicy;
import unicom.cacheinit.domain.PayRelationShip;
import unicom.cacheinit.domain.User;
import unicom.comm.model.Accumulator;
import unicom.comm.model.Bill;
import unicom.comm.model.BillAccount;
import unicom.comm.model.ShortMessageInventory;
import unicom.comm.model.VoiceInventory;
import unicom.comm.model.VolumeInventory;

/**
 *2015年5月21日
 *数据库操作
 *TODO 需要更具需要补全参数
 */
public interface DbOper {
	
	public Accumulator findAccumulator(String userId,String itemId,String topBJId);
	
	/**
	 * 保存累计量
	 * @param acc
	 */
	public void saveAccumulator(Accumulator acc) throws VersionConflictException;

	
	/**
	 * 保存语音清单
	 * @param inventory
	 */
	public void saveVoiceInventory(VoiceInventory inventory);
	/**
	 * 保存短信清单
	 * @param inventory
	 */
	public void saveShortMessageInventory(ShortMessageInventory inventory);
	/**
	 * 保存流量清单
	 * @param inventory
	 */
	public void saveVolumeInventory(VolumeInventory inventory);

	/**
	 * 保存用户账单
	 * @param bill
	 */
	public void saveBill(Bill bill) throws VersionConflictException;
	
	public Bill findBill(String userId,int itemId);
	/**
	 * 保存账户账单,如果有就覆盖，没有就插入
	 * @param acount
	 */
	public void saveBillAccount(BillAccount acount);
	
	/**
	 * 查询用户
	 * @param removeTag
	 * @return
	 */
	public Iterator<User> findUser(char removeTag);
	/**
	 * 查询用户订购资费实例
	 * @return
	 */
	public Iterator<FeePlolicy> findFeePolicy();
	/**
	 * 查询付费关系
	 * @return
	 */
	public Iterator<PayRelationShip> findPayRelationShip();

    /**
     * 获得所有的计算表记录
     *
     * @return
     */
    public Iterator<Calculation> getAllCalculations();

    /**
     * 获取所有的条件表的记录
     *
     * @return
     */
    public Iterator<Condition> getAllConditions();

    /**
     * 获得所有的自费表的记录
     *
     * @return
     */
    public Iterator<Price> getAllPrices();
	
}
