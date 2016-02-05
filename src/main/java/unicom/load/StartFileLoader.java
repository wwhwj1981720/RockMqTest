package unicom.load;

import unicom.Sender.MyUserSender;
import unicom.Sender.MyphoneSender;
import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.producer.UnicomProducer;
import unicom.recive.Billing;

public class StartFileLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileLoaderImp loader = new FileLoadDirect();

		
		String path="E:\\cbss选型\\cbss性能测试文档\\批价验证比对_王伟华\\简单批价程序\\shuju";
		
		
		Sender phonesender=new MyphoneSender();
		
		
		loader.readFile(path, phonesender);//第一次发送消息 到 MyphoneSender.recordlist
		
		
		Sender sortsender=new UnicomProducer("string");
		
		Sort sort=new Sort(sortsender);//使用消息模式
		sort.init();
		
		
	
		startSortConSumerRecord(sort);
		
		

	}
	public static void startSortConSumerRecord(Sort sort)
	{
		
		Record record=MyphoneSender.recordlist.poll();//模拟消费
		while(record!=null)
		{
			sort.event.onMessageReceive(record);
			record=MyphoneSender.recordlist.poll();
		}
		
	}
	public static void startConSumerRecord(Sort sort,Billing billing)
	{
		
		Record record=MyphoneSender.recordlist.poll();//模拟消费
		while(record!=null)
		{
			sort.event.onMessageReceive(record);
			record=MyphoneSender.recordlist.poll();
		}
		Record userrecord=MyUserSender.recordlist.poll();
		while(userrecord!=null)
		{
			
			billing.billingevent.onMessageReceive(userrecord);
			userrecord=MyUserSender.recordlist.poll();
		}
	}

	

}
