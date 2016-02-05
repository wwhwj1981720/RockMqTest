package unicom.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.impl.producer.DefaultMQProducerImpl;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.RPCHook;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.load.AsyncSendCallBack;
/*
 * 
 * 收到的每条消息都要都是相同的topic,可以尝试将每个手机号码设置成一个 topic 
 * **/
public class UnicomProducer extends DefaultMQProducer implements Sender {
	
	public UnicomProducer(String string) {
		// TODO Auto-generated constructor stub
		super(string);
		setConfig();
	}
	public void setConfig()
	{
		setNamesrvAddr("mqnameserver1:9876;mqnameserver2:9876");
	    setInstanceName("UnicomProducer");
	    try {
			this.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendRecord(Record record, AsyncSendCallBack asyncSendCallBack) {
		
		// TODO Auto-generated method stub
		Message msg=new Message("topic", record.toString().getBytes());
		try {
			super.send(msg);
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
