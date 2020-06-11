package gvo.webservice.hr.portal.service.impl;

import gvo.webservice.hr.portal.util.InsertUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *@Description: 同步组织信息
* @version: 
* @author: jianyong.tang
* @date: 2020年4月10日 下午3:28:21
 */
public class SysnOrgInfoImpl {
	/**
	 * 同步处理数据
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public String sysnInfo(String json) throws Exception {
		BaseBean log = new BaseBean();
		if("".equals(json)) {
			return "{\"result\":\"E\",\"msg\":\"请传递正确的json格式\"}";
		}
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tablename = "uf_hr_orgdata";
		String sql = "";
		String nowtime = "";
		String orgtype = "";//组织类型
		String fgldcode = "";//分管领导工号
		String fzrcode = "";//组织负责人工号
		String fgldrcd = "";//分管领导员工记录号
		String orgcode = "";//组织编码
		String orglevel = "";//组织层级
		String suporgcode = "";//上级组织编码
		String fzrrcd = "";//组织负责人员工记录号
		String status = "";//组织状态   0是有效 1是无效
		String orgname = "";//组织名称
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			orgtype = jo.getString("orgtype");
			fgldcode = jo.getString("fgldcode");
			fzrcode = jo.getString("fzrcode");
			fgldrcd = jo.getString("fgldrcd");
			orgcode = jo.getString("orgcode");
			orglevel = jo.getString("orglevel");
			suporgcode = jo.getString("suporgcode");
			fzrrcd = jo.getString("fzrrcd");
			status = jo.getString("status");
			orgname = jo.getString("orgname");
			nowtime = sf.format(new Date());
			Map<String,String> map = new HashMap<String,String>();
			map.put("orgtype",orgtype);
			map.put("fgldcode",fgldcode);
			map.put("fzrcode",fzrcode);
			map.put("fgldrcd",fgldrcd);
			map.put("orgcode",orgcode);
			map.put("orglevel",orglevel);
			map.put("suporgcode",suporgcode);
			map.put("fzrrcd",fzrrcd);
			map.put("status",status);
			map.put("orgname",orgname);
			map.put("gxsj",nowtime);
			String billid = "";
			sql = "select id from "+tablename+" where orgcode='"+orgcode+"'";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			if("".equals(billid)){
				result=iu.insert(map,tablename);
			}else{
				result=iu.updateGen(map,tablename,"id",billid);
			}
			if(!result){
				log.writeLog("SysnOrgInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败组织编码："+orgcode+"\"}";
			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
