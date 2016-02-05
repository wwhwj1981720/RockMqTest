/**
 * 
 */
package unicom.comm;

import unicom.comm.model.Record;

/**
 * @author 
 * 这里需要在接收到一条从load过程传输过来的消息之后，调用onMessageReceive
 * 1.在分拣阶段，onMessageReceive中需要处理分拣流程，使用phone从缓存中查找userId，并将数据转换成Record后，向后续billing流程发送消息
 * 2.在批价阶段，onMessageReceive中需要处理批价的算法逻辑，并进行入库操作
 *2015年5月21日
 */
public interface MessageEvent {
	public void onMessageReceive(Record record);
}
