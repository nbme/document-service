package org.nbme.wbti.document.impl;

import org.apache.log4j.Logger;
import org.nbme.jms.service.impl.DefaultResponseServiceImpl;

import javax.jms.Message;
import java.util.Map;


/**
 * This class represents the logic in the CVRT system
 */

// public class MyResponseServiceImpl implements  ConsumeProcessService {
public class MyResponseServiceImpl extends  DefaultResponseServiceImpl {

		
		public MyResponseServiceImpl() {
			_log.info("RESPONSE MyResponseServiceImpl Construct  RESPONSE");
		}
		
		public String processMessage(String msg) throws Exception {
			_log.info("String processMessage(String msg): " + msg);
			return msg;
		}
		
		public Message processMessage(Message msg) throws Exception {
			_log.info("Message processMessage(Message msg): " + msg);
			return msg;
		}	
		
		public String processMessage(String msg, Map<String, String> msgHeaders) throws Exception {
			_log.info("String processMessage (String msg, Map<String, String> msgHeaders): " + msg);
			for (Map.Entry<String, String> entry : msgHeaders.entrySet()) 
				_log.info("MsgHeader Key : " + entry.getKey() + " Value : " + entry.getValue());
			return msg;
		}
		
		public Message processMessage(Message msg, Map<String, String> msgHeaders) throws Exception {
			_log.info("Message processMessage (Message msg, Map<String, String> msgHeaders): " + msg);
			for (Map.Entry<String, String> entry : msgHeaders.entrySet()) 
				_log.info("MsgHeader Key : " + entry.getKey() + " Value : " + entry.getValue());
			return msg;
		}
		
		/*
		public ExchangeData processMessage(ExchangeData msg) throws Exception {
			_log.info("ExchangeResponseServiceImp: " + msg);
			return msg;
		} */
		
		private static final Logger _log = Logger.getLogger(MyResponseServiceImpl.class);

}
