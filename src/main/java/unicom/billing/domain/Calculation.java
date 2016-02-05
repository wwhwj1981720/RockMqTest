package unicom.billing.domain;

/**
 * 计算表
 *
 */
public class Calculation {
    private long calcId;
    private long itemId;//账目   语音20001 流量 20002 短信费 20003 
    private int calMethod;
    private String calcValue;
    private String calcDesc;

    public long getCalcId() {
        return calcId;
    }

    public void setCalcId(long calcId) {
        this.calcId = calcId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getCalMethod() {
        return calMethod;
    }

    public void setCalMethod(int calMethod) {
        this.calMethod = calMethod;
    }

    public String getCalcValue() {
        return calcValue;
    }

    public void setCalcValue(String calcValue) {
        this.calcValue = calcValue;
    }

    public String getCalcDesc() {
        return calcDesc;
    }

    public void setCalcDesc(String calcDesc) {
        this.calcDesc = calcDesc;
    }
}
