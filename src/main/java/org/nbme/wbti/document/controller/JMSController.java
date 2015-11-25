package org.nbme.wbti.document.controller;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nbme.jms.service.ExchangeRequestPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
// @ContextConfiguration("classpath:applicationContext-JMS.xml")
@RequestMapping("/jmstest")
@ComponentScan("org.nbme.jms.common.JMSCommonService..*")
public class JMSController {

    
    @Autowired
    private ExchangeRequestPublish requestPublish;
     
	@RequestMapping(method = RequestMethod.GET)
	public String getMessage(HttpServletRequest request, Model model) {
		// By WZ
		_log.info("get Message: Path=" + request.getRequestURI());
		return "message";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String sendMessage(HttpServletRequest request, Model model) {
		// By WZ
		_log.info("sendMessage=" + request.getRequestURI());
		model.addAttribute("invalidateSecurity", "NSP Login Failed. Try again");
		String msg = request.getParameter("message");
		String loop = request.getParameter("loop");
		String destination = request.getParameter("destination");
		String reply = request.getParameter("reply");
		String message_type = request.getParameter("message_type");

		
		boolean isReply = true;
		if(StringUtils.equalsIgnoreCase(reply, "no"))
			isReply = false;
		int numb_message = Integer.parseInt(loop, 10);
		if(numb_message < 0)
			numb_message = 10;
		
		_log.info("Publish Message: " + loop + " / " + destination + " / " + isReply +"\n" + msg);

		Map<String, String> msgHeaders = new HashMap<String, String>();
		msgHeaders.put("RequestReponseType1", "AccommodationRequestResponse");
		msgHeaders.put("RequestReponseType2", "EligibilityRequestResponse");
		
		
		for(int i=0; i<numb_message; i++) {
			try {
				if(StringUtils.equalsIgnoreCase(message_type, "Text")) {
					if(StringUtils.isBlank(destination)) {
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, isReply);
						// requestPublish.sendMessage_WithHeader(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, isReply);
					}
					else {
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, destination, isReply);
						// requestPublish.sendMessage_WithHeader(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, destination, isReply);
					}
				}
				if(StringUtils.equalsIgnoreCase(message_type, "HashMap")) {
					if(StringUtils.isBlank(destination)) {
						// requestPublish.sendMapMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, isReply);
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, isReply);
					}
					else {
						// requestPublish.sendMapMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, destination, isReply);
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, destination, isReply);
					}
				}	
				if(StringUtils.equalsIgnoreCase(message_type, "Message")) {
					if(StringUtils.isBlank(destination)) {
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, isReply);

					}
					else {
						requestPublish.sendMessage(msg + " " + i+i+i+i+i+i+i+i+i+i+i, msgHeaders, destination, isReply);
					}
				}
				Thread.sleep(2000);
			}
			catch (Exception e) {
				_log.error("Cannot Send Message: " + e.getMessage());
			}

		}
		
		return "message";
	}
	

	
	private static final Logger _log = Logger.getLogger(JMSController.class);
	
}