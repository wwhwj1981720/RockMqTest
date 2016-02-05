package unicom.billing.domain;

/**
 * 资费表
 *
 */
public class Price {
    private long discountId;
    private long conditionId;
    private int bizType;//语音, 流量, 短信
    private String conditionDesc;
    private int exePosition; //1, 一批, 2. 二批, 3. 优惠

    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
        this.discountId = discountId;
    }

    public long getConditionId() {
        return conditionId;
    }

    public void setConditionId(long conditionId) {
        this.conditionId = conditionId;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public int getExePosition() {
        return exePosition;
    }

    public void setExePosition(int exePosition) {
        this.exePosition = exePosition;
    }
}
