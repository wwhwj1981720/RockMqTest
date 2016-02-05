package unicom.recive;

import java.lang.reflect.Method;
import java.util.Date;

import unicom.comm.model.Record;

public class MsgToRecord {

	/**
	 * @param args
	 */
	StringBuffer msg;
	StringBuffer unitsb=new StringBuffer();
	enum field
	{
		type,
		srcPhone,
		targetPhone
	}
	
	public StringBuffer getMsg() {
		return msg;
	}

	public void setMsg(StringBuffer msg) {
		this.msg = msg;
	}
	public void transferMsgToRecord(Record record) throws Exception
	{
		
		int start=msg.indexOf("[");
		int end=msg.lastIndexOf("]");
		String submsg=msg.substring(start+1, end);
//		int msgstart=0;
//		int msglen=msg.length();
		int msgend=0;
		while(true)
		{
			
			msgend=recisverStringPos(submsg, 0,",");//分析,分割
			if(msgend==-1) break;
			CharSequence unit=submsg.substring(0, msgend);
					
			unitsb.delete(0, unitsb.length());
			unitsb.append(unit);
			this.setProperty(unitsb,"=",record);
			submsg=submsg.substring(msgend+1).trim();
			
			
		}
	}
	//type=2, srcPhone=18654308830, targetPhone=null
	public CharSequence recisverString(String sb,int s,String flag)
	{
		int end=sb.indexOf(flag);
		return sb.subSequence(s, end);
	}
	//从s 位置开始 返回出现,第一次的位置
	public int recisverStringPos(String sb,int s,String flag)
	{
		int end=sb.indexOf(",");
		return end;
	}
	public void setProperty(StringBuffer unit,String flag,Record record) throws Exception
	{
		int pos=unit.indexOf(flag);
		int len=unit.length();
		CharSequence type=unit.subSequence(0, pos);
		CharSequence value=unit.subSequence(pos+1, len);
		StringBuffer methodname=getPropertyName(type, "set");
		//String parameterTypes=getParameterType(type);
		ClassAndValue classvalue=new ClassAndValue();
		Class parametertypes=getParameterType(type.toString(),value.toString(),classvalue);
		Method method =Class.forName("unicom.comm.model.Record").getDeclaredMethod(methodname.toString(), parametertypes);
		method.invoke(record, classvalue.value);
		
	}
	public StringBuffer getPropertyName(CharSequence value,String pre)
	{
		char head=value.charAt(0);
		int len=value.length();
		char uc=Character.toUpperCase(head);
		StringBuffer property=new StringBuffer();
		if(value.equals("isUp"))
		{
			property.append(pre);
			property.append("Up");
		}
		else
		{
			property.append(pre);
			property.append(uc);
			property.append(value.subSequence(1, len));
		}
		return property;
		
	}
	public Class getParameterType(String fieldname,String value,ClassAndValue classvalue)
	{
		
		
		if(fieldname.equals("srcPhone")||fieldname.equals("targetPhone")||fieldname.equals("userId")||fieldname.equals("srcFileName")||fieldname.equals("oriData"))
		{
			classvalue.value=value;
			return String.class;
		}
		else if(fieldname.equals("start")||fieldname.equals("end"))
		{
			
			if(!value.equals("null"))
			{
				classvalue.value=Date.parse(value);
			}
			else classvalue.value=null;
			return Date.class;
		}
		else if(fieldname.equals("type")||fieldname.equals("duration"))
		{
			classvalue.value=Integer.parseInt(value);
			return int.class;
		}
		else if(fieldname.equals("dataDown")||fieldname.equals("dataUp"))
		{
			classvalue.value=Long.parseLong(value);
			return long.class;
		}
		else 
		{
			classvalue.value=Boolean.parseBoolean(value);
			return boolean.class;
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String msgs="Record [type=2, srcPhone=18654308830, targetPhone=null, duration=100, isUp=false, start=null, end=null, userId=null, srcFileName=E:\\cbss选型\\cbss性能测试文档\\批价验证比对_王伟华\\shuju\\GPRS5.Tdat, dataDown=0, dataUp=104857600]";
		MsgToRecord msg2record=new MsgToRecord();
		StringBuffer msgsb=new StringBuffer();
		msgsb.append(msgs);
		msg2record.setMsg(msgsb);
		Record record=new Record();
		msg2record.transferMsgToRecord(record);

	}

}
