package gvo.webservice.hr.portal.service.impl;

import gvo.webservice.hr.portal.util.InsertUtil;
import org.apache.axis.encoding.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *@Description: 同步人员照片
* @version: 
* @author: jianyong.tang
* @date: 2020年4月13日 下午15:40:21
 */
public class SysnPersonPhotoImpl {
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
		String tablename = "uf_hr_personphoto";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		String photo = "";//分管领导工号
		String photoname = "";//组织负责人工号
		String secid= Util.null2o(weaver.file.Prop.getPropValue("hrmhconfig", "zpsecid"));
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			photo = jo.getString("photo");
			photoname = jo.getString("photoname");
			if("".equals(photo)){
				log.writeLog("SysnPersonPhotoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败人员工号："+workcode+"\"}";
			}
			try{
				String docid=getDocId(photoname,photo,"1",secid);
				photo = docid;
			}catch(Exception e){
				log.writeLog("SysnPersonPhotoImpl",e);
				log.writeLog("SysnPersonPhotoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败人员工号："+workcode+"\"}";
			}


			nowtime = sf.format(new Date());
			Map<String,String> map = new HashMap<String,String>();
			map.put("workcode",workcode);
			map.put("photo",photo);
			map.put("photoname",photoname);

			map.put("gxsj",nowtime);
			String billid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"'";
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
				log.writeLog("SysnPersonPhotoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败人员工号："+workcode+"\"}";
			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}

	/**
	 * 创建文档
	 * @param name 文档名称
	 * @param value base64位加密的字符串
	 * @param createrid 文档创建人
	 * @param seccategory 文档存放目录
	 * @return
	 * @throws Exception
	 */
	private String getDocId(String name, String value,String createrid,String seccategory) throws Exception {
		String docId = "";
		DocInfo di= new DocInfo();
		di.setMaincategory(0);
		di.setSubcategory(0);
		di.setSeccategory(Integer.valueOf(seccategory));
		di.setDocSubject(name.substring(0, name.lastIndexOf(".")));
		//di.setDoccontent(arg0);
		DocAttachment doca = new DocAttachment();
		doca.setFilename(name);
		byte[] buffer = new BASE64Decoder().decodeBuffer(value);
		String encode= Base64.encode(buffer);
		doca.setFilecontent(encode);
		DocAttachment[] docs= new DocAttachment[1];
		docs[0]=doca;
		di.setAttachments(docs);
		String departmentId="-1";
		String sql="select departmentid from hrmresource where id="+createrid;
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		User user = new User();
		if(rs.next()){
			departmentId = Util.null2String(rs.getString("departmentid"));
		}
		user.setUid(Integer.parseInt(createrid));
		user.setUserDepartment(Integer.parseInt(departmentId));
		user.setLanguage(7);
		user.setLogintype("1");
		user.setLoginip("127.0.0.1");
		DocServiceImpl ds = new DocServiceImpl();
		try {
			docId=String.valueOf(ds.createDocByUser(di, user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return docId;
	}

}
