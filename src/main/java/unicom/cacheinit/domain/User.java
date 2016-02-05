package unicom.cacheinit.domain;

/**
 * 用户表的相关用户信息
 * 对应的表: TF_F_USER
 *
 * Created by  on 15-5-21.
 */
public class User {
    private long userId; //CUST_ID
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
    
}
