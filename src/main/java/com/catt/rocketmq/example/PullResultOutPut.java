package com.catt.rocketmq.example;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.common.message.MessageExt;

public class PullResultOutPut {
	PullResult pullResult;

	public PullResult getPullResult() {
		return pullResult;
	}

	public void setPullResult(PullResult pullResult) {
		this.pullResult = pullResult;
	}
	public void printResult()
	{
		List<MessageExt> msglist=this.pullResult.getMsgFoundList();
		if(msglist!=null)
		{
			System.out.println(msglist.size());
			for(MessageExt mes:msglist)
			{
				try {
					System.out.println(mes.getMsgId()+mes.getTags()+mes.getKeys()+mes.getTopic()+new String(mes.getBody(),"utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
