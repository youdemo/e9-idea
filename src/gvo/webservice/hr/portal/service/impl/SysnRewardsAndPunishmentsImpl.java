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
 *@Description: 同步团队奖惩绩效信息
* @version: 
* @author: jianyong.tang
* @date: 2020年4月15日 上午09：50
 */
public class SysnRewardsAndPunishmentsImpl {
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
		String tablename = "uf_hr_tdjcxx";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		String seqno = "";//序号
		String status = "";//状态
		JSONArray data = new JSONArray(json);
		boolean result = true;
		new BaseBean().writeLog("jtcbcount:"+data.length()+" times:"+System.currentTimeMillis());
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			seqno = jo.getString("seqno");
			status = jo.getString("status");
			nowtime = sf.format(new Date());
			Map<String,String> map = new HashMap<String,String>();
			map.put("seqno",seqno);
			map.put("status",status);//状态
			map.put("workcode",workcode);//工号
			map.put("year",jo.getString("year"));//奖惩年度
			map.put("jcrq",jo.getString("date"));//奖惩日期
			map.put("jcjb",jo.getString("level"));//奖惩级别
			map.put("type",jo.getString("type"));//奖惩类型
			map.put("description",jo.getString("desc"));//事件描述
			map.put("gxsj",nowtime);//更新时间
			String billid = "";
			if("A".equals(status)){//新增
				result=iu.insert(map,tablename);
			}else if("C".equals(status)||"D".equals(status)){
				sql = "select id from "+tablename+" where workcode='"+workcode+"' and seqno='"+seqno+"' and status in('A','C')";
				rs.execute(sql);
				if(rs.next()){
					billid = Util.null2String(rs.getString("id"));
				}
				if(!"".equals(billid)){
					result=iu.updateGen(map,tablename,"id",billid);
				}else{
					log.writeLog("SysnRewardsAndPunishmentsImpl",jo.toString());
					return "{\"result\":\"E\",\"msg\":\"数据更新失败 没有原始数据 当前同步工号："+workcode+"\"}";
				}
			}

			if(!result){
				log.writeLog("SysnRewardsAndPunishmentsImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前同步工号："+workcode+"\"}";
			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
