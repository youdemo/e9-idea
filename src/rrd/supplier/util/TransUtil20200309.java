package rrd.supplier.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 10:10
 * version v1
 * desc
 */
public class TransUtil20200309 {

    public String getTableNameByRequestid(String requestid) {
        RecordSet rs = new RecordSet();
        String tableName = "";
        rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base a," +
                "workflow_requestbase b where a.id=b.workflowid and b.requestid="+requestid+")");
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }
        return tableName;
    }

    /**
     * 获取选择框html
     * @param fieldName
     * @param fieldValue
     * @param isMust
     * @return
     */
    public String getSelectHtml(String fieldName,String fieldValue,String isMust,String language){
        RecordSet rs = new RecordSet();
        StringBuffer transResult = new StringBuffer();
        String viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        transResult.append("<select class=\"e8_btn_top middle\" style=\"width:210px\" id=\""+fieldName+"\" name=\""+fieldName+"\" " +
                "viewtype=\""+viewtype+"\" onblur=\"checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'));\">");
        transResult.append(" <option value=\"\"");
        if("".equals(fieldValue)){
            transResult.append("selected");
        }
        transResult.append("></option>");
        String sql = "select case when CHARINDEX('~`~`7 ',selectname)>0 then substring(selectname,charindex('~`~`7 ',selectname)+6,charindex('`~`8 ',selectname)-charindex('~`~`7 ',selectname)-6) else selectname end as zw," +
                "case when CHARINDEX('~`~`7 ',selectname)>0 then substring(selectname,charindex('`~`8 ',selectname)+5,charindex('`~`~',selectname)-charindex('`~`8 ',selectname)-5) else selectname end as yw," +
                "selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='uf_gysxxsj' and a.fieldname='"+fieldName+"'order by c.listorder asc";
        rs.execute(sql);
        while(rs.next()){
            String zw = Util.null2String(rs.getString("zw"));
            String yw = Util.null2String(rs.getString("yw"));
            String selectname = "";
            selectname = zw;
            if("8".equals(language)){
                selectname = yw;
            }

            String selectvalue = Util.null2String(rs.getString("selectvalue"));
            transResult.append(" <option value=\""+selectvalue+"\"");
            if(selectvalue.equals(fieldValue)){
                transResult.append("selected");
            }
            transResult.append(">"+selectname+"</option>");
        }
        transResult.append("</select>");
        transResult.append("<span id=\""+fieldName+"span\" style=\"word-break:break-all;word-wrap:break-word;margin-left:4px;\">");
        if("".equals(fieldValue)&&"1".equals(viewtype)){
            transResult.append("<img src=\"/images/BacoError_wev9.png\" align=\"absMiddle\">") ;
        }
        transResult.append("</span>");
        return transResult.toString();

    }

    public void writeLog(String className, Object obj) {
        if (true) {
            new BaseBean().writeLog(className, obj);
        }
    }




}
