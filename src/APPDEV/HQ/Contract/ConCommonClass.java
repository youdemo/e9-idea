package APPDEV.HQ.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import APPDEV.HQ.SOP.InsertUtil;

public class ConCommonClass {

	public String getcreater(String requestid) {
		String creater = "";
		RecordSet rs = new RecordSet();
		String sql = " select creater from workflow_requestbase where requestid = '"
				+ requestid + "'";
		rs.executeSql(sql);
		if (rs.next()) {
			creater = Util.null2String(rs.getString("creater"));
		}
		return creater;
	}

	public String gettablename(String workflowID) {
		String tableName = "";
		RecordSet rs = new RecordSet();
		String sql = " Select tablename From Workflow_bill Where id in ("
				+ " Select formid From workflow_base Where id= " + workflowID
				+ ")";
		rs.executeSql(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		return tableName;
	}

	public String getformodeid(String PROJECT, String DATATYPE) {
		String formodeid = "";
		String sql = "";
		RecordSet rs = new RecordSet();
		sql = "select formodeid from uf_formodeid where PROJECT='" + PROJECT
				+ "' and DATATYPE='" + DATATYPE + "'";
		rs.executeSql(sql);
		if (rs.next()) {
			formodeid = Util.null2String(rs.getString("formodeid"));
		}
		return formodeid;
	}

	public String insertHistory(String billid, String table_name,
			String uqField, String uqVal) {

		// BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		String createrid = "";

		// 存放 表的字段
		List<String> list = new ArrayList<String>();
		String sql = "select fieldname from workflow_billfield where billid="
				+ billid + " order by dsporder";
		// log.writeLog("insertNow(1) = " + sql);
		rs.executeSql(sql);
		while (rs.next()) {
			String tmp_1 = Util.null2String(rs.getString("fieldname"));

			// 关联父类排除
			if (!"".equals(tmp_1) && !"superid".equalsIgnoreCase(tmp_1)) {
				list.add(tmp_1);
			}
		}
		if (!"".equals(table_name)) {

			Map<String, String> mapStr = new HashMap<String, String>();

			sql = "";
			sql = "select * from " + table_name + " where " + uqField + "='"
					+ uqVal + "'";
			rs.execute(sql);
			if (rs.next()) {
				// 循环获取 不为空值的组合成sql
				for (String field : list) {
					String tmp_x = Util.null2String(rs.getString(field));
					if (tmp_x.length() > 0)
						mapStr.put(field, tmp_x);
				}
			}
			createrid = rs.getString("modedatacreater");
			// 最后需要补充关联父id
			if (mapStr.size() > 0) {
				mapStr.put("superid", Util.null2String(rs.getString("ID")));
				// 增加请求的id
				// mapStr.put("requestid",
				// Util.null2String(rs.getString("requestid")));
				mapStr.put("modedatacreater", Util.null2String(rs
						.getString("modedatacreater")));
				mapStr.put("modedatacreatertype", Util.null2String(rs
						.getString("modedatacreatertype")));
				mapStr.put("formmodeid", Util.null2String(rs
						.getString("formmodeid")));
				iu.insert(mapStr, table_name);
			}
		}

		return createrid;
	}
}
