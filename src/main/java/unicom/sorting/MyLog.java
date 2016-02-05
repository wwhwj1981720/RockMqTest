package unicom.sorting;

import org.apache.log4j.Logger;

import unicom.comm.model.Record;
import unicom.load.FileLoaderImp;
import unicom.log.LoggerApi;

public class MyLog extends Logger implements LoggerApi{
	public static final Logger log = Logger.getLogger(MyLog.class);
	public MyLog(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String initCacheLog(int code, long writeTps) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initCacheLog(int code, Throwable e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingLog(int code, Throwable e, Record r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingMsgLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingDBReadLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingDBWriteLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingCacheLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String billingStart(int code, long begin) {
		// TODO Auto-generated method stub
		
		return code+""+begin;
	}

	@Override
	public String sortingMsgSendLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortingMsgReceiveLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortingCacheLog(int code, long num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortingSrcPhoneError(int code, Record record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortingUserIDNotFoundLog(int code, String srcPhone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sortingStart(int code, long begin) {
		// TODO Auto-generated method stub
		log.warn(code+" "+begin);
		return null;
	}

	@Override
	public String preloadingLog(int code, long fileCount, long errorFileCount,
			long sendMsgCnt, long errorMsgCnt) {
		// TODO Auto-generated method stub
		return null;
	}

}
