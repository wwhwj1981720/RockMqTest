package unicom.Sender;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.load.AsyncSendCallBack;

public class MyphoneSender implements Sender {
	public static Queue<Record> recordlist=new LinkedList<Record>();// save record

	@Override
	public void sendRecord(Record record, AsyncSendCallBack asyncSendCallBack) {
		// TODO Auto-generated method stub
		recordlist.offer(record);

	}

}
