package sino.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.Util;

/** 
* @author 作者  张瑞坤
* @date 创建时间：2020年3月5日 上午4:15:33 
* @version 1.0 
*/
public class GetUtil {
	
	public String getFieldVal(String tableName,String fieldName,String wherekey,String strparm){
		String sql = "select * from "+ tableName + " where  "+wherekey+" = '" + strparm + "'";
		RecordSet rs = new RecordSet();
		String result = "";
		rs.executeSql(sql);
	    if(rs.next()){
	    	result = Util.null2String(rs.getString(fieldName));
	    }
	    return result;
	}
	public String getDateNow () {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
		
	}

}
