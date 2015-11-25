package org.nbme.wbti.document.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nbme.jms.service.impl.DefaultRequestServiceImpl;

import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * This class represents the logic in the CVRT system
 */

// public class MyRequestServiceImpl implements  ConsumeProcessService {
public class MyRequestServiceImpl extends  DefaultRequestServiceImpl{

	
		public MyRequestServiceImpl() {
			_log.info("MyRequestServiceImpl Construct  REQREQREQ");
		}
		
		public String processMessage(String msg) throws Exception {
			_log.info("String processMessage(String msg): " + msg);		
			return check_validate(msg);
		}
		

		public Message processMessage(Message msg) throws Exception {
			_log.info("Message processMessage(Message msg): " + msg);
			TextMessage tMsg = (TextMessage)msg;
			// tMsg.setText(tMsg.getText() + "Back to Response Queue");
			
			return check_validate(msg);
		}
		
		public String processMessage(String msg, Map<String, String> msgHeaders) throws Exception {
			_log.info("String processMessage (String msg, Map<String, String> msgHeaders): " + msg);
			for (Map.Entry<String, String> entry : msgHeaders.entrySet()) 
				_log.info("MsgHeader Key : " + entry.getKey() + " Value : " + entry.getValue());
			return check_validate(msg);
		}
		
		public Message processMessage(Message msg, Map<String, String> msgHeaders) throws Exception {
			_log.info("Message processMessage (Message msg, Map<String, String> msgHeaders): " + msg);
			for (Map.Entry<String, String> entry : msgHeaders.entrySet()) 
				_log.info("MsgHeader Key : " + entry.getKey() + " Value : " + entry.getValue());
			return check_validate(msg);
		}
		
		/*
		public ExchangeData processMessage(ExchangeData msg) throws Exception {
			Random rand = new Random();
			
			_log.info("Process Request Message: " + msg.toString());
	        msg.setExchangeStatur("Changed");
			_log.info("Update Request Message: " + msg.toString());
			return msg;
		} */
		
		private String check_validate(String msg) throws Exception {
			if(StringUtils.startsWithIgnoreCase(msg, "Error"))
				throw (new Exception("Rollback"));
			return msg;
		}
		
		private Message check_validate(Message msg) throws Exception {
			if(StringUtils.startsWithIgnoreCase(((TextMessage)msg).getText(), "Error"))
				throw (new Exception("Rollback"));
			return msg;
		}
		
		private static final Logger _log = Logger.getLogger(MyRequestServiceImpl.class);

}
