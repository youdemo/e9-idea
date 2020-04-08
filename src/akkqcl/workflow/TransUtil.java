package akkqcl.workflow;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class TransUtil {


	/**
	 * 根据选择框显示值 获取现在框value
	 * 
	 * @param tableName
	 * @param filedname
	 * @param selectname
	 * @return
	 */
	public String getSelectValue(String tableName, String filedname, String selectname) {
		RecordSet rs = new RecordSet();
		String value = "";
		String sql = "select c.selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"
				+ tableName + "' and a.fieldname='" + filedname + "' and c.selectname='" + selectname + "'";
		// writeLog("sql:"+sql);
		rs.execute(sql);
		if (rs.next()) {
			value = Util.null2String(rs.getString("selectvalue"));
		}
		return value;
	}

	/**
	 * 根据标识获取流程id
	 * 
	 * @param bs
	 * @return
	 */
	public String getWfid(String bs) {
		String wfid = "";
		RecordSet rs = new RecordSet();
		String sql = "select wfid from uf_K3_cf_mt where bs='" + bs + "'";
		rs.execute(sql);
		if (rs.next()) {
			wfid = Util.null2String(rs.getString("wfid"));
		}
		return wfid;

	}

	/**
	 * 根据标识获取表名
	 * 
	 * @param bs
	 * @return
	 */
	public String getTbName(String bs) {
		String bm = "";
		RecordSet rs = new RecordSet();
		String sql = "select bm from uf_K3_cf_mt where bs='" + bs + "'";
		rs.execute(sql);
		if (rs.next()) {
			bm = Util.null2String(rs.getString("bm"));
		}
		return bm;

	}

	public String getTableName(String wfid) {
		RecordSet rs = new RecordSet();
		String tableName = "";
		rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base where id=" + wfid
				+ ")");
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		return tableName;
	}

	public String getResourceId(String workcode) {
		RecordSet rs = new RecordSet();
		if ("".equals(workcode))
			return "";
		String ryid = "";
		String sql = "select id from hrmresource where workcode='" + workcode + "'";
		rs.execute(sql);
		if (rs.next()) {
			ryid = Util.null2String(rs.getString("id"));
		}
		return ryid;
	}

	public String getDepartmentId(String code) {
		RecordSet rs = new RecordSet();
		if ("".equals(code))
			return "";
		String dpid = "";
		String sql = "select id from hrmdepartment where departmentcode='" + code + "'";
		rs.execute(sql);
		if (rs.next()) {
			dpid = Util.null2String(rs.getString("id"));
		}
		return dpid;
	}
	public String getWorkcode(String ryid) {
		RecordSet rs = new RecordSet();
		if ("".equals(ryid))
			return "";
		String workcode = "";
		String sql = "select workcode from hrmresource where id='" + ryid + "'";
		rs.execute(sql);
		if (rs.next()) {
			workcode = Util.null2String(rs.getString("workcode"));
		}
		return workcode;
	}
	public String getSubcompanyId(String code) {
		RecordSet rs = new RecordSet();
		if ("".equals(code))
			return "";
		String subid = "";
		String sql = "select id from hrmsubcompany where subcompanycode='" + code + "'";
		rs.execute(sql);
		if (rs.next()) {
			subid = Util.null2String(rs.getString("id"));
		}
		return subid;
	}

	public void writeLog(Object obj) {
		if (true) {
			new BaseBean().writeLog(this.getClass().getName(), obj);
		}
	}

	public void writeLog(String className, Object obj) {
		if (true) {
			new BaseBean().writeLog(className, obj);
		}
	}

}
