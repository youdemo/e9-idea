package xhgz.report.scmx.CMD;


import weaver.conn.RecordSet;
import weaver.general.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeaTableTransMethod {


    public static String fundFormat(String num) {
        if ("".equals(num)) {
            num = "0";
        }
        double number = Util.getDoubleValue(num);
        return String.format("%,.2f", number);
    }

    public String getScjg(String rqid){
        RecordSet rs = new RecordSet();
        String remark = "";
        String yfzgid="";
        String sql = "select jdid from uf_report_mt where bs='BOMYFZG'";
        rs.execute(sql);
        if(rs.next()){
            yfzgid = Util.null2String(rs.getString("jdid"));
        }
        if("".equals(yfzgid)){
            yfzgid = "0";
        }
        if("".equals(rqid)){
            return "";
        }
        sql = "select remark from workflow_requestlog where nodeid="+yfzgid+" and requestid="+rqid+" order by operatedate desc";
        rs.execute(sql);
        if(rs.next()){
            remark = removeHtmlTag(Util.null2String(rs.getString("remark")));
        }
        return remark;
    }

    /**
     * 签字意见html格式化
     * @param content 签字意见remark字段文本
     * @return
     */
    private String removeHtmlTag(String content) {
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = content
                    .replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            content = removeHtmlTag(content);
        }
        return content;
    }


}
