package unicom.cacheinit.domain;

/**
 * 资费实例表的实体
 *
 * 表: TABLE TF_F_FEEPOLICY
 * Created by  on 15-5-21.
 */
public class FeePlolicy {
    private long userId;
    private long policyId;
    private long policyInstanceId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    public long getPolicyInstanceId() {
        return policyInstanceId;
    }

    public void setPolicyInstanceId(long policyInstanceId) {
        this.policyInstanceId = policyInstanceId;
    }
}
