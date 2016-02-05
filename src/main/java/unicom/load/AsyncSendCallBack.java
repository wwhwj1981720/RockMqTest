package unicom.load;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 异步消息发送的回调
 */
public class AsyncSendCallBack {

    private AtomicLong count = new AtomicLong(0L);
    private AtomicLong countStatus = new AtomicLong(0L);

    public void beforeSend() {
        countStatus.incrementAndGet();
    }
    /**
     * 发送消息的时候增加计数
     */
    public void addSendCount() {
        count.incrementAndGet();
        countStatus.decrementAndGet();
    }

    /**
     * @return 返回消息统计的最后结果
     */
    public Long get() {
        return count.get();
    }

    /**
     * 判断整体消息发送是否结束
     * @return true 消息发送完成
     *         false 还在消息发送中
     */
    public boolean isDone() {
        return countStatus.get() == 0L;
    }
}
