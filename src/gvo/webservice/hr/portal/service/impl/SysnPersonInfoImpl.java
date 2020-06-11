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
 *@Description: 同步人员信息
* @version: 
* @author: jianyong.tang
* @date: 2020年4月13日 上午10:40:21
 */
public class SysnPersonInfoImpl {
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
		String tablename = "uf_hr_persondata";
		String sql = "";
		String nowtime = "";
		JSONObject jsonStr = new JSONObject(json);
		//处理人员主数据
		JSONArray maininfo = jsonStr.getJSONArray("maininfo");
		boolean result = true;
		for(int i=0;i<maininfo.length();i++) {
			JSONObject jo = maininfo.getJSONObject(i);
			String workcode = jo.getString("workcode");
			String recordid = jo.getString("recordid");

			nowtime = sf.format(new Date());
			Map<String,String> map = new HashMap<String,String>();
			map.put("workcode",workcode);//工号
			map.put("recordid",recordid);//员工记录号
			map.put("name",jo.getString("name"));//姓名
			map.put("status",jo.getString("status"));//状态
			map.put("companyname",jo.getString("companyname"));//公司
			map.put("companycode",jo.getString("companycode"));//公司编码
			map.put("centername",jo.getString("centername"));//中心
			map.put("centercode",jo.getString("centercode"));//中心编码
			map.put("deptname",jo.getString("deptname"));//部门
			map.put("deptcode",jo.getString("deptcode"));//部门编码
			map.put("groupname",jo.getString("groupname"));//组
			map.put("groupcode",jo.getString("groupcode"));//组编码
			map.put("jobtitlename",jo.getString("jobtitlename"));//岗位
			map.put("jobtitlecode",jo.getString("jobtitlecode"));//岗位代码
			map.put("channel",jo.getString("channel"));//通道
			map.put("sequence",jo.getString("sequence"));//序列
			map.put("joblevel",jo.getString("joblevel"));//职等
			map.put("identitytype",jo.getString("identitytype"));//身份类别
			map.put("employeetype",jo.getString("employeetype"));//员工类别
			map.put("worktype",jo.getString("worktype"));//工时类别
			map.put("rjtrq",jo.getString("rjtrq"));//入集团日期
			map.put("syqjzrq",jo.getString("syqjzrq"));//试用期截止日期
			map.put("jtgl",jo.getString("jtgl"));//集团工龄
			map.put("shgl",jo.getString("shgl"));//社会工龄
			map.put("birthday",jo.getString("birthday"));//出生日期
			map.put("age",jo.getString("age"));//年龄
			map.put("sex",jo.getString("sex"));//性别
			map.put("nativeplace",jo.getString("nativeplace"));//籍贯省
			map.put("educationlevel",jo.getString("educationlevel"));//学历
			map.put("school",jo.getString("school"));//学校
			map.put("major",jo.getString("major"));//专业
			map.put("bysj",jo.getString("bysj"));//毕业时间
			map.put("contact",jo.getString("contact"));//联系方式
			map.put("rsxxk",jo.getString("rsxxk"));//人事信息卡
			map.put("joindate",jo.getString("joindate"));//入职日期
			map.put("leavedate",jo.getString("leavedate"));//离职日期
			map.put("currdeptcode",jo.getString("currdeptcode"));//当前部门编码
			map.put("gxsj",nowtime);//更新时间
			String billid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"' and recordid='"+recordid+"'";
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
				log.writeLog("SysnPersonInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"同步人员信息主数据失败 当前失败人员工号："+workcode+"\"}";
			}
			
		}
		//处理兼岗信息
		JSONArray jobinfo = jsonStr.getJSONArray("jobinfo");
		for(int i=0;i<jobinfo.length();i++) {
			JSONObject jo = jobinfo.getJSONObject(i);
			String workcode = jo.getString("workcode");
			String recordid = jo.getString("recordid");

			nowtime = sf.format(new Date());
			Map<String, String> map = new HashMap<String, String>();
			map.put("workcode", workcode);//工号
			map.put("recordid", recordid);//员工记录号
			map.put("status",jo.getString("status"));//状态
			map.put("companyname",jo.getString("companyname"));//公司
			map.put("companycode",jo.getString("companycode"));//公司编码
			map.put("centername",jo.getString("centername"));//中心
			map.put("centercode",jo.getString("centercode"));//中心编码
			map.put("deptname",jo.getString("deptname"));//部门
			map.put("deptcode",jo.getString("deptcode"));//部门编码
			map.put("groupname",jo.getString("groupname"));//组
			map.put("groupcode",jo.getString("groupcode"));//组编码
			map.put("jobtitlename",jo.getString("jobtitlename"));//岗位
			map.put("jobtitlecode",jo.getString("jobtitlecode"));//岗位代码
			map.put("channel",jo.getString("channel"));//通道
			map.put("sequence",jo.getString("sequence"));//序列
			map.put("joblevel",jo.getString("joblevel"));//职等
			map.put("currdeptcode",jo.getString("currdeptcode"));//当前部门编码
			map.put("gxsj",nowtime);//更新时间
			String billid = "";
			String mainid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"'";
			rs.execute(sql);
			if(rs.next()){
				mainid = Util.null2String(rs.getString("id"));
			}
			if("".equals(mainid)){
				log.writeLog("SysnPersonInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"同步人员兼岗信息失败 请先同步主账号信息："+workcode+"\"}";

			}
			map.put("mainid",mainid);//
			sql = "select id from "+tablename+"_dt1 where mainid="+mainid+" and workcode='"+workcode+"' and recordid='"+recordid+"'";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			if("".equals(billid)){
				result=iu.insert(map,tablename+"_dt1");
			}else{
				result=iu.updateGen(map,tablename+"_dt1","id",billid);
			}
			if(!result){
				log.writeLog("SysnPersonInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"同步人员兼岗信息失败 当前失败人员工号："+workcode+" 记录号："+recordid+"\"}";
			}


		}
		//处理异动信息
		JSONArray changeinfo = jsonStr.getJSONArray("changeinfo");
		for(int i=0;i<changeinfo.length();i++) {
			JSONObject jo = changeinfo.getJSONObject(i);
			String workcode = jo.getString("workcode");
			String recordid = jo.getString("recordid");

			nowtime = sf.format(new Date());
			Map<String, String> map = new HashMap<String, String>();
			map.put("workcode", workcode);//工号
			map.put("recordid", recordid);//员工记录号
			map.put("seqno", jo.getString("seqno"));//序号
			map.put("changedate",jo.getString("changedate"));//调动日期
			map.put("changetype",jo.getString("changetype"));//异动类型
			map.put("beforedept",jo.getString("beforedept"));//调动前部门
			map.put("afterdept",jo.getString("afterdept"));//调动后部门
			map.put("gxsj",nowtime);//更新时间
			String mainid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"'";
			rs.execute(sql);
			if(rs.next()){
				mainid = Util.null2String(rs.getString("id"));
			}
			if("".equals(mainid)){
				log.writeLog("SysnPersonInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"同步人员异动信息失败 请先同步主账号信息："+workcode+"\"}";

			}
			map.put("mainid",mainid);//

			result=iu.insert(map,tablename+"_dt2");

			if(!result){
				log.writeLog("SysnPersonInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"同步人员异动信息失败 当前失败人员工号："+workcode+" 记录号："+recordid+"\"}";
			}


		}
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
