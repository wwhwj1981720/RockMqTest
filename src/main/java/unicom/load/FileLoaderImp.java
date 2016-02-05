package unicom.load;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;


import unicom.Sender.MyUserSender;
import unicom.Sender.MyphoneSender;
import unicom.billing.BillingMessageEvent;
import unicom.comm.CacheInterface;
import unicom.comm.LogCode;
import unicom.comm.MessageEvent;
import unicom.comm.Sender;
import unicom.comm.model.Record;
import unicom.log.LoggerApi;
import unicom.log.sorting.SortingCacheCountStatistic;
import unicom.log.sorting.SortingMsgReceiveCountStatistic;
import unicom.log.sorting.SortingMsgSendCountStatistic;
import unicom.recive.Billing;
import unicom.recive.CacheService;
import unicom.recive.MyCache;

import unicom.sorting.MyLog;
import unicom.sorting.SortingMessageEvent;

public class FileLoaderImp implements FileLoader {

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

	@Override
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

		log.info(loggerApi.preloadingLog(LogCode.PRELOADING, fileCount.get(), errorFileCount.get(), asyncSendCallBack.get(),
				errorMsgCount.get()));
				
	}

	// 按两级固定目录来处理话单
	/*
	 * private void readFileByConstantDir(String path, Sender sender){ try{ File
	 * tmpLevel0 = new File(path); if(!tmpLevel0.isDirectory()){
	 * log.error("should not be here , path " + path +" is not a directory!"); }
	 * File[] files = tmpLevel0.listFiles(); for(File tmpLevel1 : files){
	 * if(!tmpLevel1.isDirectory()){
	 * log.error("level1 folder is not a directory . " +
	 * tmpLevel1.getAbsolutePath() + " ."); continue; }
	 * 
	 * for(File afile : tmpLevel1.listFiles()) { BufferedReader br = new
	 * BufferedReader(new FileReader(afile)); try { String aline = ""; String
	 * srcFileName = afile.getCanonicalPath(); // 确定数据类型，根据文件名判断，语音1 数据2 短信3 int
	 * type = 0; if(srcFileName.indexOf("GJYY") > 0){ type = 1; }else
	 * if(srcFileName.indexOf("GJSJ") > 0){ type = 2; }else
	 * if(srcFileName.indexOf("GJDX") > 0){ type = 3; }else{
	 * log.error("file is not valid. " + srcFileName + " 不是语音、短信或数据"); continue;
	 * }
	 * 
	 * while((aline = br.readLine()) != null) { boolean success = true; Record
	 * record = new Record();
	 * 
	 * record.setType(type); record.setSrcFileName(srcFileName);
	 * record.setOriData(aline);
	 * 
	 * // 解析话单 parserLine(aline, record);
	 * 
	 * try { sender.sendRecord(record);
	 * 
	 * } catch (Exception e) { log.error("",e); success = false; }
	 * 
	 * if(!success) { log.error("Call Detail Record :"+ aline+" fail ."); } } }
	 * catch (Exception e) { log.error("",e); }finally{ br.close(); } } } }catch
	 * (Exception e){ log.error("",e); } }
	 */

	// 非递归遍历文件夹，遍历文件夹下所有文件，处理话单，提高对文件夹结构的兼容性
	public void nonRecursiveScanDir(String path, Sender sender) {

		// 栈，用来保存未处理的文件夹
		Stack<String> find_file = new Stack<String>();

		// 检查传入的路径是否为文件夹，如果不是，直接返回
		File tmpLevel0 = new File(path);
		if (!tmpLevel0.isDirectory()) {
			log.error("should not be here , path " + path + " is not a directory!");
			return;
		}

		// 遍历第一级文件夹，文件直接处理，文件夹放到 find_file 中，然后再逐一弹出处理
		scanSingleDir(path, find_file, sender);

		while (!find_file.empty()) {
			String pathString = find_file.pop();
			scanSingleDir(pathString, find_file, sender);
		}

		while (!asyncSendCallBack.isDone()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("interrpted", e);
			}
		}
	}

	// 扫描文件夹，如果含有子文件夹，就加入 find_path, 如果是文件就直接处理
	private void scanSingleDir(String path, Stack<String> find_path, Sender sender) {
		File dir = new File(path);

		File[] files = dir.listFiles();
		for (File tmp : files) {

			String tmp_path = tmp.getAbsolutePath();
			if (tmp.isDirectory()) {
				find_path.push(tmp_path);
				log.warn("find dir : " + tmp_path);
			} else {
				processFile(tmp_path, sender);
			}
		}
	}

	// 处理单个文件
	public void processFile(String path, Sender sender) {
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
					//wwh add
					if(sender==null)
					{
						
					}
					//wwwh end
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

	// 分析单条话单
	public int parserLine(String line, Record record) {

		try {
			String str[] = splitStr(line, ',');
			// 将解析出来的值，赋给 recode 结构...
			if (record.getType() == 1) { // 语音
				if (str.length != 74) {
					return -1;
				}

				record.setSrcPhone(str[6]);
				record.setTargetPhone(str[7]);
				record.setStart(StrToDate(str[8] + str[9]));
				record.setEnd(StrToDate(str[10] + str[11]));

				// 通话时长处理为分钟，不足一分钟按一分钟计算
				long second = Long.parseLong(str[12].trim());
				int min = (int) Math.ceil(second / 60.0);
				record.setDuration(min);

			} else if (record.getType() == 3) { // 短信
				if (str.length != 47) {
					return -1;
				}
				record.setSrcPhone(str[9]);
				record.setTargetPhone(str[11]);
				record.setStart(StrToDate(str[32] + str[33]));
				record.setDuration(1);
			} else if (record.getType() == 2) { // 流量
				if (str.length != 53) {
					return -1;
				}

				record.setSrcPhone(str[6]);// 计费号码
				if (str[30].length() == 8 && str[31].length() == 6) {
					record.setStart(StrToDate((str[30] + str[31]).trim()));
				}

				// 流量按 m 计算，上行下行都算, 不足 1m 算 1m
				record.setDataUp(str[36].trim().equals("") ? 0 : Long.parseLong(str[36].trim()));// 上行流量1
																									// 有时流量为空
				record.setDataDown(str[37].trim().equals("") ? 0 : Long.parseLong(str[37].trim()));// 下行流量1
																									// 有时流量为空
				int dataMB = (int) Math.ceil((record.getDataDown() + record.getDataUp()) / 1048576.0);
				record.setDuration(dataMB);
			}
			return 0;
		} catch (Exception e) {
			// 解析出现异常，认为话单错误
			return -1;
		}
	}

	private Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Date date = format.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] splitStr(String str, char c) {
		str += c;
		int n = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c)
				n++;
		}

		String out[] = new String[n];

		for (int i = 0; i < n; i++) {
			int index = str.indexOf(c);
			out[i] = str.substring(0, index);
			str = str.substring(index + 1, str.length());
		}
		return out;
	}

	public void setLoggerApi(LoggerApi loggerApi) {
		this.loggerApi = loggerApi;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileLoaderImp loader = new FileLoadDirect();

		// loader.nonRecursiveScanDir("d://cbsstest", null);
		String path="E:\\cbss选型\\cbss性能测试文档\\批价验证比对_王伟华\\shuju";
		
		
		Sender phonesender=new MyphoneSender();
		
		
		loader.readFile(path, phonesender);//第一次发送消息 到 MyphoneSender.recordlist
		
		Sort sort=new Sort();
		sort.init();
		
		Billing billing=new Billing();
		billing.init();
		startConSumerRecord(sort,billing);
		
		

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