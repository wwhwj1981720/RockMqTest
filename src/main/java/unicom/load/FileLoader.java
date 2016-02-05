/**
 * 
 */
package unicom.load;

import unicom.comm.Sender;

/**
 * @author 
 * 从文件加载话单的主业务流程，需要在readFile中，进行文件读取，并将读取到的行，进行消息发送
 *2015年5月20日
 */
public interface FileLoader {
	public void readFile(String path, Sender sender);
	//
	
}
