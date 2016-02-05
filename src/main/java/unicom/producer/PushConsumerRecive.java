package unicom.producer;
import java.util.List;

import unicom.Sender.MyUserSender;
import unicom.Sender.MyphoneSender;
import unicom.comm.CacheInterface;
import unicom.comm.MessageEvent;
import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.recive.Billing;
import unicom.recive.MsgToRecord;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
/*
 * 
 * 测试时使用的消费者
 * **/
public class PushConsumerRecive extends Billing {

  /**
   * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
   * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
   */
	MsgToRecord msg2record=new MsgToRecord();
	StringBuffer msgsb=new StringBuffer();
	public void reciveRecord(MessageExt msg)
	{
		
		//Record record=MyphoneSender.recordlist.poll();
		Record record=new Record();
		if(msg!=null)
		{
			//event.onMessageReceive(record);
			//record=msg.getBody();
			msgsb.delete(0, msgsb.length());
			msgsb.append(new String(msg.getBody()));
			System.out.println(msgsb);
			msg2record.setMsg(msgsb);
			try {
				msg2record.transferMsgToRecord(record);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(new String(msg.getBody());
			billingevent.onMessageReceive(record);
			//record=MyphoneSender.recordlist.poll();
		}
	}
  public static void main(String[] args) throws InterruptedException,
      MQClientException {
    /**
     * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
     * 注意：ConsumerGroupName需要由应用来保证唯一
     */
	  final PushConsumerRecive  reciver=new PushConsumerRecive();
	  reciver.init();
	 
		//reciver.pushRecord(event);//record 填入 user_id
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
        "ConsumerGroupName");
    //consumer.setNamesrvAddr("192.168.138.129:9876");
    consumer.setNamesrvAddr("192.168.138.129:9876");
    consumer.setInstanceName("Consumber");

    /**
     * 订阅指定topic下tags分别等于TagA或TagC或TagD
     */
   // consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
    /**
     * 订阅指定topic下所有消息<br>
     * 注意：一个consumer对象可以订阅多个topic
     */
    consumer.subscribe("topic", "*");
    consumer.registerMessageListener(new MessageListenerConcurrently() {

		public ConsumeConcurrentlyStatus consumeMessage(
				 List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
	        System.out.println(Thread.currentThread().getName()
	            + " Receive New Messages: " + msgs.size());

	        MessageExt msg = msgs.get(0);
	        if (msg.getTopic().equals("topic")) {
	        	synchronized (reciver.getDbOper()) 
	        	{
					reciver.reciveRecord(msg);
	        	}
	        	
	        }

	        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
    
      
    });

    /**
     * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
     */
    consumer.start();

    System.out.println("Consumer Started.");
  }
}
