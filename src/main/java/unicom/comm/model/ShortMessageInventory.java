/**
 *
 */
package unicom.comm.model;

import java.util.Date;

/**
 * 短信
 *
 * @author 
 *2015年5月21日
 */
public class ShortMessageInventory {
    private String srcPhone; //源号码, MSISDN

    private String applyDate; //发送日期
    private String applyTime; //发送时间
    private String userId;
    private int errorCode; //0代表正确
    private String srcFileName;
    private long mfee;//一批费用
    private long ifee; //最终费用， ifee， 以厘为单位
    private String inDBTime;



	public String getInDBTime() {
		return inDBTime;
	}

	public void setInDBTime(String inDBTime) {
		this.inDBTime = inDBTime;
	}

	public String getSrcPhone() {
        return srcPhone;
    }

    public void setSrcPhone(String srcPhone) {
        this.srcPhone = srcPhone;
    }

    public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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

    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public long getMfee() {
        return mfee;
    }

    public void setMfee(long mfee) {
        this.mfee = mfee;
    }

    public long getIfee() {
        return ifee;
    }

    public void setIfee(long ifee) {
        this.ifee = ifee;
    }

}
