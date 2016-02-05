/**
 * 
 */
package unicom.log;

import unicom.comm.model.Record;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @author qinan.qn@taobao.com
 *2015年5月27日
 */
public interface LoggerApi {
	public String initCacheLog(int code,long writeTps);
	public String initCacheLog(int code,Throwable e);

	public String billingLog(int code,Throwable e,Record r);
    //批价消息接收统计log
    public String billingMsgLog(int code, long num);
    //批价数据库读取统计log
    public String billingDBReadLog(int code, long num);
    //批价数据库写入统计log
    public String billingDBWriteLog(int code, long num);
    //批价数据库读取log统计
    public String billingCacheLog(int code, long num);
    //billing启动时间的log
    public String billingStart(int code, long begin);

	
    public String sortingMsgSendLog(int code, long num);
    public String sortingMsgReceiveLog(int code, long num);
    public String sortingCacheLog(int code, long num);
    public String sortingSrcPhoneError(int code, Record record);
    public String sortingUserIDNotFoundLog(int code, String srcPhone);
    public String sortingStart(int code, long begin);

    /**
     * 文件加载日志
     * @param code 日志代码
     * @param fileCount 读取文件数
     * @param errorFileCount 错误文件数
     * @param sendMsgCnt 发送消息数
     * @param errorMsgCnt 错误消息数
     */
    public String preloadingLog(int code, long fileCount, long errorFileCount, long sendMsgCnt, long errorMsgCnt);

}
