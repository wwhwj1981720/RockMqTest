/**
 *
 */
package unicom.comm.model;

/**
 * 累计表（BILL_USER_SUM1_XX）
 *
 * @author 
 *2015年5月21日
 *累积量对象
 */
public class Accumulator {
    private int billId; //itemId
    private long topBJId;//fee_policy_ins_id
    private long userId;
    private long value;
    private int version;

    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getTopBJId() {
        return topBJId;
    }

    public void setTopBJId(long topBJId) {
        this.topBJId = topBJId;
    }
}
