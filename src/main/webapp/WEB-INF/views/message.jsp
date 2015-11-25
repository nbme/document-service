<HTML>
<HEAD><TITLE>Messaging Center</TITLE></HEAD>

<BODY>

<CENTER>
<FONT SIZE="6" COLOR="navy">Welcome To The Messaging Center</FONT>
</CENTER>

<FORM NAME="QueueMessage" ACTION="./jmstest" METHOD="POST">
<TABLE WIDTH="85%" ALIGN="center" BGCOLOR="yellow">
	<TR><TD WIDTH="50%" ALIGN="right">Message</TD>
		<TD WIDTH="50%"><INPUT TYPE="text" NAME="message" VALUE="Enter A Message For the Queue" SIZE="50"></TD>
	</TR>
	<TR><TD WIDTH="50%" ALIGN="right">Number of Message to send</TD>
		<TD WIDTH="50%"><INPUT TYPE="text" NAME="loop" VALUE="1" SIZE="50"></TD>
	</TR>
	<TR><TD WIDTH="50%" ALIGN="right">Select Queue</TD>
		<TD WIDTH="50%">
		<select name="destination">
		<option />
		<option>jms/BNEDataRequestNotificationQueueTest</option>
		<option>jms/CBTEligibilityRequestQueueTest</option>
		<option>jms/AccommdationRequestQueueTest</option>	
		<!-- <option>jms/BNEDataRequestNotificationQueue</option>
		<option>jms/CBTEligibilityRequestQueue</option>
		<option>jms/AccommdationRequestQueue</option> -->
		</select>  
		</TD>
	</TR>
	<TR><TD WIDTH="50%" ALIGN="right">Reply?</TD>
		<TD WIDTH="50%">
		<select name="reply">
		<option>yes</option>
		<option>no</option>
		</select>  
		</TD>
	</TR>
	
	<TR><TD WIDTH="50%" ALIGN="right">Select Message Type</TD>
		<TD WIDTH="50%">
		<select name="message_type">
		<option value="Text">Text Message Only</option>
		<option value="HashMap">HashMap Message with header</option>
		<option value="Message">Message with Header</option>
		<option value="Redelivery">Re delivery</option>
		
		</select>  
		</TD>
	</TR>	
	<TR><TD COLSPAN="2" ALIGN="center">
			<INPUT TYPE="submit" NAME="btnSubmit" VALUE="Post Message to the Queue">
		</TD>
	</TR>
</TABLE>
</FORM>



</BODY>
</HTML>
