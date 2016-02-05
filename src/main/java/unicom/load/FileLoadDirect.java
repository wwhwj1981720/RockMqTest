package unicom.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import unicom.comm.LogCode;
import unicom.comm.MessageEvent;
import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.log.LoggerApi;



public class FileLoadDirect extends FileLoaderImp {
	private static final Logger log = Logger.getLogger(FileLoaderImp.class);
	// 非法消息数
	private AtomicLong errorMsgCount = new AtomicLong();

	// 话单文件数
	private AtomicLong fileCount = new AtomicLong();

	// 非法话单文件数
	private AtomicLong errorFileCount = new AtomicLong();

	private LoggerApi loggerApi;

	// 消息发送的异步回调
	private AsyncSendCallBack asyncSendCallBack = new AsyncSendCallBack();
	public void readFile(String path, Sender sender) {
		// 先读数据
		long beginTime = System.currentTimeMillis();
		log.warn(String.format("开始本机文件处理 文件路径 %s . 开始时间 %s", path, String.valueOf(new Date())));
		nonRecursiveScanDir(path, sender);
		// readFileByConstantDir(path, sender);

		log.warn(String.format("本机文件处理结束 文件路径 %s . 结束时间 %s", path, String.valueOf(new Date())));
		log.warn(String.format("耗时 %s ms", System.currentTimeMillis() - beginTime));
		log.warn("filetotal = " + fileCount.get() + ", errorFileCount = " + errorFileCount.get());
		log.warn("sendMessageCount = " + asyncSendCallBack.get() + ", errorMsgCount = " + errorMsgCount.get());

		/*&log.info(loggerApi.preloadingLog(LogCode.PRELOADING, fileCount.get(), errorFileCount.get(), asyncSendCallBack.get(),
				errorMsgCount.get()));
				*/
	}
	
	public void processFile(String path, Sender sender)
	{
		fileCount.addAndGet(1);
		log.info("process file : " + path);
		File afile = new File(path);

		try {
			// 确定数据类型，根据文件名判断，语音1 数据2 短信3
			String srcFileName = afile.getCanonicalPath();
			int type = 0;
			if (srcFileName.indexOf("yuyin") > 0) {
				type = 1;
			} else if (srcFileName.indexOf("shuju") > 0) {
				type = 2;
			} else if (srcFileName.indexOf("duanxin") > 0) {
				type = 3;
			} else {
				errorFileCount.addAndGet(1);
				log.error("file is not valid. " + srcFileName + " 不是语音、短信或数据");
				return;
			}

			// 循环读取文件
			BufferedReader br = new BufferedReader(new FileReader(afile));
			String aline = "";
			while ((aline = br.readLine()) != null) {
				boolean success = true;
				Record record = new Record();

				record.setType(type);
				record.setSrcFileName(srcFileName);
				record.setOriData(aline);

				// 解析单条话单，将其赋给record
				int result = parserLine(aline, record);
				if (result == -1) {
					errorMsgCount.addAndGet(1);
					log.error("话单不合法: " + aline.substring(0, Math.min(128, aline.length())));
					continue;
				}

				try {
					if (sender != null) {
						asyncSendCallBack.beforeSend();
						sender.sendRecord(record, asyncSendCallBack);
					}
				
				} catch (Exception e) {
					log.error("", e);
					success = false;
				}
			}

			br.close();
		} catch (Exception e) {
			log.error("读取文件失败 !", e);
		}
	}

}
