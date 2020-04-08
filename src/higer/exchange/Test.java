package higer.exchange;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import com.engine.workflow.cmd.requestForm.RequestSubmitCmd;
import org.json.JSONArray;
import org.json.JSONObject;

import microsoft.exchange.webservices.data.Appointment;
import microsoft.exchange.webservices.data.ConflictResolutionMode;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.TimeZoneDefinition;
import microsoft.exchange.webservices.data.WebCredentials;

public class Test {

	public static void main(String[] args) throws Exception {
		sendRc();
	}
	
	public static String getZw(String str) {
		int zwindex = str.indexOf("~`~`7");
		if(zwindex<0) {
			return str;
		}
		int ywindex = str.indexOf("`~`8");
		String zw= str.substring(zwindex+5,ywindex);
		if(str.indexOf("~`~`7", ywindex)<0) {
			return zw+str.substring(str.indexOf("`~`~:")+4,str.length());
		}else {
			return zw+str.substring(str.indexOf("`~`~:")+4,str.indexOf("~`~`7", ywindex))+getZw(str.substring(str.indexOf("~`~`7", ywindex),str.length()));
		}
	}
	public static String getMinlength(int length1, int length2, int length3, int length4) {
		String result = "";
		int length = length1;
		result = "1";
		if (length > length2) {
			result = "2";
			length = length2;
		}
		if (length > length3) {
			result = "3";
			length = length3;
		}
		if (length > length4) {
			result = "4";
			length = length4;
		}

		return result;

	}

	public static void sendRc() throws Exception {
		SimpleDateFormat aa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1, TimeZone.getTimeZone("Asia/Chongqing"));
		ExchangeCredentials credentials = new WebCredentials("oa.schedule", "oa@higer");
		//TimeZoneInfo.FindSystemTimeZoneById("China Standard Time")
		service.setCredentials(credentials);
		service.setUrl(new URI("https://mail.higer.com/ews/Exchange.asmx"));
		service.setCredentials(credentials);
		service.setTraceEnabled(true);
		Appointment appointment = new Appointment(service);
		// Set the properties on the appointment object to create the appointment.
		appointment.setSubject("test1243");
		appointment.setBody(MessageBody.getMessageBodyFromText("test"));
		
		appointment.setLocation("123");
		appointment.setLocation("会议位置");
		Collection<TimeZoneDefinition> t = service.getServerTimeZones();
		TimeZoneDefinition tf = null;
		for (TimeZoneDefinition timeZoneDefinition : t) {
			tf = timeZoneDefinition;
			System.out.println(timeZoneDefinition.getId());
			if("Asia/Chongqing".equalsIgnoreCase(timeZoneDefinition.getId())) {
				System.out.println(tf.getId());
				break;
			}
		}
		appointment.setStartTimeZone(tf);
		appointment.setStart(local2Utc(aa.parse("2020-01-07 15:20:12")));
		//appointment.setEndTimeZone(tf);
		//appointment.setEndTimeZone(tf);
		appointment.setEnd(local2Utc(aa.parse("2020-01-08 17:20:12")));
		// appointment.getResources().add("会议资源账号，如：meetingroom@company.com");

		appointment.getRequiredAttendees().add("wb_zhuky@higer.com");
		// appointment.getOptionalAttendees().add("可选参加的员工的账号");
		appointment.save();
		System.out.println(appointment.getId().toString());
		//appointment.update(ConflictResolutionMode.AutoResolve);

	}
	public static Date local2Utc(Date date) {
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour=c.get(Calendar.HOUR);
		c.set(Calendar.HOUR,hour-8);
		return c.getTime();
	}

}
