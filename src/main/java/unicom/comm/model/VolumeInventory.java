/**
 * 
 */
package unicom.comm.model;

import java.util.Date;

/**
 * 流量 UCR_BMA.TG_CDR01_GS   
 *
 *  流量清单表的模型 
 * @author 
 *2015年5月21日
 */
public class VolumeInventory {
	private String srcPhone; //源号码, MSISDN

	private int bizType; //语音， 短信或者流量
	
	private String userId;
	private int errorCode; //0代表正确
	private String srcFileName;
	private long orgCfee;//一批费用
	private long cfee; //最终费用， cfee， 以厘为单位
	private String inDBTime; //入库时间
	
	private long dataUp; //上行流量 data_up1
	private long dataDown; //下行流量 data_down1
	public String getSrcPhone() {
		return srcPhone;
	}
	public void setSrcPhone(String srcPhone) {
		this.srcPhone = srcPhone;
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
	public long getDataUp() {
		return dataUp;
	}
	public void setDataUp(long dataUp) {
		this.dataUp = dataUp;
	}
	public long getDataDown() {
		return dataDown;
	}
	public void setDataDown(long dataDown) {
		this.dataDown = dataDown;
	}
	
	
}
