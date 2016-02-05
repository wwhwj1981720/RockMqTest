/**
 * 
 */
package unicom.comm.model;

import java.util.Date;
import java.util.Map;

/**
 * @author 
 *2015年5月20日
 */
public class Record {
	private int type; //记录类型
	private String srcPhone; //源号码, 作为缓存的key
	private String targetPhone; //目标号码
	private int duration; //时长 or 条数 or  流量
	
	private boolean isUp;//true是上行流量，false是下行流量
	
	private Date start;//开始时间
	private Date end; //结束时间
	
	private String userId;
	private String srcFileName;
	
	private long dataDown; // 下行流量
	private long dataUp;   // 上行流量
	private String oriData; // 原始话单
	
	public boolean isUp() {
		return isUp;
	}
	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
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
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSrcFileName() {
		return srcFileName;
	}
	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}
	public long getDataDown() {
		return dataDown;
	}
	public void setDataDown(long dataDown) {
		this.dataDown = dataDown;
	}
	public long getDataUp() {
		return dataUp;
	}
	public void setDataUp(long dataUp) {
		this.dataUp = dataUp;
	}
	public String getOriData() {
		return oriData;
	}
	public void setOriData(String oriData) {
		this.oriData = oriData;
	}
	
	@Override
	public String toString() {
		return "Record [type=" + type + ", srcPhone=" + srcPhone
				+ ", targetPhone=" + targetPhone + ", duration=" + duration
				+ ", isUp=" + isUp + ", start=" + start + ", end=" + end
				+ ", userId=" + userId + ", srcFileName=" + srcFileName
				+ ", dataDown=" + dataDown + ", dataUp=" + dataUp + "]";
	}
	
	
	
}
