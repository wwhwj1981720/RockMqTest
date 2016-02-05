package unicom.billing.domain;

/**
 * 条件表中的一条记录
 */
public class Condition {
    private long conditionId;
    private long judgeObject;
    private String judgeMethod;
    private long judgeValue;
    private long calcId;
    private String desc;

    public long getConditionId() {
        return conditionId;
    }

    public void setConditionId(long conditionId) {
        this.conditionId = conditionId;
    }

    public long getJudgeObject() {
        return judgeObject;
    }

    public void setJudgeObject(long judgeObject) {
        this.judgeObject = judgeObject;
    }

    public String getJudgeMethod() {
        return judgeMethod;
    }

    public void setJudgeMethod(String judgeMethod) {
        this.judgeMethod = judgeMethod;
    }

    public long getJudgeValue() {
        return judgeValue;
    }

    public void setJudgeValue(long judgeValue) {
        this.judgeValue = judgeValue;
    }

    public long getCalcId() {
        return calcId;
    }

    public void setCalcId(long calcId) {
        this.calcId = calcId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
