/**
 * 
 */
package unicom.comm;

import unicom.comm.model.Record;
import unicom.load.AsyncSendCallBack;

/**
 * @author 
 *2015年5月20日
 *向批账系统传递消息
 */
public interface Sender {
	public void sendRecord(Record record, AsyncSendCallBack asyncSendCallBack);
}
