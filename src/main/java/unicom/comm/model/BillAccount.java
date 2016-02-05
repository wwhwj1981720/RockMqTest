package unicom.comm.model;

/**
 * 账户账单（BILL_ACCOUNT_X）， 优惠之后使用       账户账单表 
 *
 * Created by  on 15-5-21.
 */
public class BillAccount {
    private long accountId;
    private long userId;
    private int detailItemCode; //账目
    private long initFee; //原始费用
    private long discountFee; //优惠费用
    private long fee; //最终费用

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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
