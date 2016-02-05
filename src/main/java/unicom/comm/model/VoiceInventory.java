/**
 * 
 */
package unicom.comm.model;

import java.util.Date;

/**
 * 语音清单 UCR_BMA.TG_CDR01
 * 
 * 语音清单表 
 * @author 
 *2015年5月21日
 *清单对象
 */
public class VoiceInventory {
	private String srcPhone; //源号码, MSISDN
	private String targetPhone; //目标号码 otherParty
	private int duration; //时长 call_duration
    private String dealTime;//处理时间
	private String startDate;//开始日期
	private String startTime; //开始时间
	private String endDate; //结束日期
    private String endTime; //结束时间
	private int bizType; //语音， 短信或者流量
	
	private String userId;
	private int errorCode; //0代表正确
    private String billItem; //BILL_ITEM
	private String srcFileName; //FILE_NO
	private long orgCfee;//一批费用, ORG_CFEE
	private long cfee; //最终费用， cfee， 以厘为单位
	private String inDBTime; //入库时间

    public String getSrcPhone() {
        return srcPhone;
    }

    public void setSrcPhone(String srcPhone) {
        this.srcPhone = srcPhone;
    }

    public String getTargetPhone() {
        return targetPhone;
    }

    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }

    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public long getOrgCfee() {
        return orgCfee;
    }

    public void setOrgCfee(long orgCfee) {
        this.orgCfee = orgCfee;
    }

    public long getCfee() {
        return cfee;
    }

    public void setCfee(long cfee) {
        this.cfee = cfee;
    }

    public String getInDBTime() {
        return inDBTime;
    }

    public void setInDBTime(String inDBTime) {
        this.inDBTime = inDBTime;
    }
}
