package hywl.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class TransUtil {
	/**
	 * 根据选择框显示值 获取现在框value
	 * @param tableName
	 * @param filedname
	 * @param selectname
	 * @return
	 */
	public String getSelectValue(String tableName, String filedname,
			String selectname) {
		RecordSet rs = new RecordSet();
		String value = "";
		String sql = "select c.selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"
				+ tableName
				+ "' and a.fieldname='"
				+ filedname
				+ "' and c.selectname='"
				+ selectname
				+ "'";
		//writeLog("sql:"+sql);
		rs.execute(sql);
		if (rs.next()) {
			value = Util.null2String(rs.getString("selectvalue"));
		}
		return value;
	}
	
	
	public String getTableName(String wfid) {
		RecordSet rs = new RecordSet();
		String tableName = "";
		rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base where id=" + wfid + ")");
        if (rs.next()) {
        	tableName = Util.null2String(rs.getString("tablename"));
        }
        return tableName;
	}
	
	public String getResourceIdByLoginid(String loginid ) {
		RecordSet rs = new RecordSet();
		String ryid = "";
		String sql = "select id from hrmresource where loginid='"+loginid+"'";
		rs.execute(sql);
		if(rs.next()) {
			ryid = Util.null2String(rs.getString("id"));
		}
		return ryid;
	}
	
	public String getDepartmentId(String code ) {
		RecordSet rs = new RecordSet();
		String dpid = "";
		String sql = "select id from hrmdepartment where departmentcode='"+code+"'";
		rs.execute(sql);
		if(rs.next()) {
			dpid = Util.null2String(rs.getString("id"));
		}
		return dpid;
	}
	 public void writeLog(String className,Object obj) {
	        if (true) {
	            new BaseBean().writeLog(className, obj);
	        }
	    }

}
