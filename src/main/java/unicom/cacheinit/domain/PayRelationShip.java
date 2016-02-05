package unicom.cacheinit.domain;

/**
 * 资费信息表的实体
 *
 * 表: TF_F_PAYRELATION
 *
 * Created by  on 15-5-21.
 */
public class PayRelationShip {
    private long userId;
    private long accountId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
