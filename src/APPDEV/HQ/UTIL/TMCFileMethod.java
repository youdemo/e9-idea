package APPDEV.HQ.UTIL;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class TMCFileMethod {

	public String getURLFile(String fileid){
		
		RecordSet rs = new RecordSet();
		
		if(Util.null2String(fileid).length() < 1) fileid="-1";
		
		String fileName  = "" ;
		String sql = "select imagefilename from imagefile where imagefileid="+fileid;
		rs.executeSql(sql); 
		if(rs.next()){
			fileName = Util.null2String(rs.getString("imagefilename"));
		}
		
		String str  = "<a target=\"_blank\" href=\"/weaver/weaver.file.FileDownload?fileid=";
		str = str + fileid +"&amp;coworkid=-1&amp;requestid=0&amp;desrequestid=0\">";
		str = str + fileName +"</a>";

		return str;
		
	}
}
