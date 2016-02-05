package unicom.log;

import java.util.concurrent.atomic.AtomicLong;

/**
 */
public class SoringLog {
    private int code;
    private AtomicLong receiveMsgCount;
    private AtomicLong cacheCount;
    private AtomicLong sendMsgCount;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AtomicLong getReceiveMsgCount() {
        return receiveMsgCount;
    }

    public void setReceiveMsgCount(AtomicLong receiveMsgCount) {
        this.receiveMsgCount = receiveMsgCount;
    }

    public AtomicLong getCacheCount() {
        return cacheCount;
    }

    public void setCacheCount(AtomicLong cacheCount) {
        this.cacheCount = cacheCount;
    }

    public AtomicLong getSendMsgCount() {
        return sendMsgCount;
    }

    public void setSendMsgCount(AtomicLong sendMsgCount) {
        this.sendMsgCount = sendMsgCount;
    }
}
