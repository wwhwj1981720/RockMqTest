/**
 *
 */
package unicom.comm.model;

/**
 *账单（BILL_USER）   用户账单表 
 *
 * @author 
 *2015年5月21日
 *账单对象
 */
public class Bill {
    private long userId;
    private int detailItemCode; //账目
    private long initFee; //原始费用
    private long discountFee; //优惠费用
    private long fee; //最终费用
    private int version;

    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getDetailItemCode() {
        return detailItemCode;
    }

    public void setDetailItemCode(int detailItemCode) {
        this.detailItemCode = detailItemCode;
    }

    public long getInitFee() {
        return initFee;
    }

    public void setInitFee(long initFee) {
        this.initFee = initFee;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }


}
