package rrd.supplier.util;

import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.docs.docs.DocComInfo;
import weaver.docs.docs.DocManager;
import weaver.docs.docs.ShareManageDocOperation;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 10:10
 * version v1
 * desc
 */
public class TransUtil {

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
    public String getSelectHtml(String fieldName,String fieldValue,String isMust,String length){
        RecordSet rs = new RecordSet();
        StringBuffer transResult = new StringBuffer();
        String viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        if("".equals(length)){
            length = "210";
        }
        transResult.append("<select class=\"e8_btn_top middle\" style=\"width:"+length+"px\" id=\""+fieldName+"\" name=\""+fieldName+"\" " +
                "viewtype=\""+viewtype+"\" onblur=\"checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'));\">");
        transResult.append(" <option value=\"\"");
        if("".equals(fieldValue)){
            transResult.append("selected");
        }
        transResult.append("></option>");
        String sql = "select selectname,selectvalue from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='uf_gysxxsj' and a.fieldname='"+fieldName+"'order by c.listorder asc";
        rs.execute(sql);
        while(rs.next()){

            String selectname = Util.null2String(rs.getString("selectname"));


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



    /**
     *
     * @param fieldName
     * @param fieldValue
     * @param isMust
     * @param tablename
     * @param showfield
     * @param keyfield
     * @param orderfield 排序字段
     * @param ordertype  排序类型
     * @return
     */
    public String getSelectHtmlTable(String fieldName,String fieldValue,String isMust,String tablename,String showfield,String keyfield,String orderfield,String ordertype){
        RecordSet rs = new RecordSet();
        StringBuffer transResult = new StringBuffer();
        String  viewtype = "0";
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
        String sql = "select "+keyfield+" as selectvalue,"+showfield+" as selectname from "+tablename+"";
        if("uf_khsshy".equals(tablename)){
            sql = sql +" where DRSY='01' and DRRT='29'  and DRHRDC = 'N' ";
        }else if("uf_sl".equals(tablename)){
            sql = sql +" where  TATXA1 like '%OV%'  or TATXA1 like '%PV%' ";
        }
        if(!"".equals(orderfield)){
            sql += " order by "+orderfield+" "+ordertype;
        }
        rs.execute(sql);
        while(rs.next()){

            String selectname = Util.null2String(rs.getString("selectname"));


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
        return  transResult.toString();

    }

    /**
     *可调节宽度
     * @param fieldName
     * @param fieldValue
     * @param isMust
     * @param tablename
     * @param showfield
     * @param keyfield
     * @param orderfield 排序字段
     * @param ordertype  排序类型
     * @return
     */
    public String getSelectHtmlTable2(String fieldName,String fieldValue,String isMust,String tablename,String showfield,String keyfield,String orderfield,String ordertype,String width){
        RecordSet rs = new RecordSet();
        StringBuffer transResult = new StringBuffer();
        String  viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        transResult.append("<select class=\"e8_btn_top middle\" style=\"width:"+width+"px\" id=\""+fieldName+"\" name=\""+fieldName+"\" " +
                "viewtype=\""+viewtype+"\" onblur=\"checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'));\">");
        transResult.append(" <option value=\"\"");
        if("".equals(fieldValue)){
            transResult.append("selected");
        }
        transResult.append("></option>");
        String sql = "select "+keyfield+" as selectvalue,"+showfield+" as selectname from "+tablename+"";
        if("uf_khsshy".equals(tablename)){
            sql = sql +" where DRSY='01' and DRRT='29'  and DRHRDC = 'N' ";
        }else if("uf_sl".equals(tablename)){
            sql = sql +" where  TATXA1 like '%OV%'  or TATXA1 like '%PV%' ";
        }
        if(!"".equals(orderfield)){
            sql += " order by "+orderfield+" "+ordertype;
        }
        rs.execute(sql);
        while(rs.next()){

            String selectname = Util.null2String(rs.getString("selectname"));


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
        return  transResult.toString();

    }

    public String getCheckBoxHtml(String fieldName,String fieldValue,String showtext){
        String result = "";
        if("1".equals(fieldValue)){
            result = " <input type=\"checkbox\" class=\"Inputstyle \" style=\"display: inline-block;\" viewtype=\"0\" temptitle=\""+showtext+"\" value=\""+fieldValue+"\" id=\""+fieldName+"\" name=\""+fieldName+"\"  checked onclick=\"changecheck('"+fieldName+"')\" >"+showtext+"</span>";
        }else{
            result = " <input type=\"checkbox\" class=\"Inputstyle \" style=\"display: inline-block;\" viewtype=\"0\" temptitle=\""+showtext+"\" value=\""+fieldValue+"\" id=\""+fieldName+"\" name=\""+fieldName+"\"  onclick=\"changecheck('"+fieldName+"')\">"+showtext+"</span>";
        }
        return result;
    }

    public String getTextHtml(String fieldName,String fieldValue,String isMust, String length,String showlength){
        StringBuffer transResult = new StringBuffer();
        String  viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        if("1".equals(isMust)){
            transResult.append("<input type=\"text\"  viewtype=\""+viewtype+"\" maxlength=\""+length+"\" size=\""+showlength+"\" id=\""+fieldName+"\" name=\""+fieldName+"\" onblur=\"\" onchange=\"checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'));checkLength(&quot;"+fieldName+"&quot;,'"+length+"','字段','文本长度不能超过','1个中文字符等于3个长度');\" value=\""+fieldValue+"\"> "+
                    "<span id=\""+fieldName+"span\">");
            if("".equals(fieldValue)){
                transResult.append("<img src=\"/images/BacoError_wev9.png\" align=\"absMiddle\">");
            }
            transResult.append("</span>");
        }else{
            transResult.append("<input type=\"text\"  viewtype=\""+viewtype+"\" maxlength=\""+length+"\" size=\""+showlength+"\" id=\""+fieldName+"\" name=\""+fieldName+"\" onblur=\"\" onchange=\"checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'));checkLength(&quot;"+fieldName+"&quot;,'"+length+"','字段','文本长度不能超过','1个中文字符等于3个长度');\" value=\""+fieldValue+"\"> "+
                    "<span id=\""+fieldName+"span\">");
        }
        return transResult.toString();

    }

    public String getNumberHtml(String fieldName,String fieldValue,String isMust, String floatdigit){
        StringBuffer transResult = new StringBuffer();
        String viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        transResult.append("<input datalength=\""+floatdigit+"\" datatype=\"float\" style=\"ime-mode:disabled\" onafterpaste=\"if(isNaN(value))execCommand('undo')\" viewtype=\""+viewtype+"\" type=\"text\"  id=\""+fieldName+"\" name=\""+fieldName+"\" onkeypress=\"ItemDecimal_KeyPress('"+fieldName+"',15,"+floatdigit+")\" onblur=\"checkFloat(this);checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'))\" value=\""+fieldValue+"\" onchange=\"\" onpropertychange=\"\" _listener=\"\">");
        transResult.append("<span id=\""+fieldName+"span\" style=\"word-break:break-all;word-wrap:break-word\">");
        if("".equals(fieldValue)&&"1".equals(viewtype)){
            transResult.append("<img src=\"/images/BacoError_wev9.png\" align=\"absMiddle\">") ;
        }
        transResult.append("</span>");
        return transResult.toString();

    }

    public String getIntHtml(String fieldName,String fieldValue,String isMust){
        StringBuffer transResult = new StringBuffer();
        String viewtype = "0";
        if("1".equals(isMust)){
            viewtype = "1";
        }
        transResult.append("<input datatype=\"int\" onafterpaste=\"if(isNaN(value))execCommand('undo')\" style=\"ime-mode:disabled\" viewtype=\""+viewtype+"\" type=\"text\" class=\"InputStyle\" id=\""+fieldName+"\" name=\""+fieldName+"\" onkeypress=\"ItemCount_KeyPress()\" onblur=\"checkcount1(this);checkItemScale(this,'整数位数长度不能超过9位，请重新输入！',-999999999,999999999);checkmustinput('"+fieldName+"','"+fieldName+"span',this.getAttribute('viewtype'))\" value=\""+fieldValue+"\" onchange=\"\" onpropertychange=\"\" _listener=\"\">");
        transResult.append("<span id=\""+fieldName+"span\" style=\"word-break:break-all;word-wrap:break-word\">");
        if("".equals(fieldValue)&&"1".equals(viewtype)){
            transResult.append("<img src=\"/images/BacoError_wev9.png\" align=\"absMiddle\">") ;
        }
        transResult.append("</span>");
        return transResult.toString();

    }


    public String getModeId(String tableName){
        RecordSet rs = new RecordSet();
        String formid = "";
        String modeid = "";
        String sql = "select id from workflow_bill where tablename='"+tableName+"'";
        rs.execute(sql);
        if(rs.next()){
            formid = Util.null2String(rs.getString("id"));
        }
        sql="select id from modeinfo where  formid="+formid;
        rs.execute(sql);
        if(rs.next()){
            modeid = Util.null2String(rs.getString("id"));
        }
        return modeid;
    }

    /**\
     * 文档复制
     * @param docStrs 文档id
     * @param seccategory 对方目录
     * @param userid 创建人员
     * @return
     * @throws Exception
     */
    public String copyFile(String docStrs, int seccategory,int userid)  {
        if ("".equals(Util.null2String(docStrs))) {
            return "";
        }
        String newdocids = "";
        try {
            DocManager docManager = new DocManager();
            DocComInfo dci = new DocComInfo();
            //MultiAclManager am = new MultiAclManager();
            ShareManageDocOperation manager = new ShareManageDocOperation();
            String docids[] = Util.TokenizerString2(docStrs, ",");

            String flag = "";
            for (int i = 0; i < docids.length; i++) {
                int docid = 0;
                String subject = "";
                docid = Util.getIntValue(docids[i], 0);
                subject = Util.null2String(dci.getDocname(docids[i]));
                // 如果目标子目录允许文件名重复
                //DocUtil docUtil = new DocUtil();
                docManager.setId(docid);
                docManager.setUserid(userid);
                docManager.setUsertype("1");
                docManager.setDocsubject(subject);
                docManager.setClientAddress("127.0.0.1");
                docManager.setMaincategory(0);
                docManager.setSubcategory(0);
                docManager.setSeccategory(seccategory);
                //docManager.setCustomDataIdMapping(selectedPropertyMapping);
                docManager.copyDoc();
                manager.copyMoveDocShareBySec(seccategory, docManager.getId());// DocManager.getId()获取复制的新文档的id
                newdocids = newdocids + flag + String.valueOf(docManager.getId());
                if (!"".equals(newdocids)) {
                    flag = ",";
                }
            }
        }catch (Exception e){
            newdocids = docStrs;
            writeLog("rrd.supplier.util.TransUtil",e);
        }
        return newdocids;
    }

    public void writeLog(String className, Object obj) {
        if (true) {
            new BaseBean().writeLog(className, obj);
        }
    }

    /**
     * 判断是否超时
     * @param urlKey   加密字符串
     * @return
     */

    public boolean checkIfOvertime(String urlKey) {
        if("".equals(urlKey)){
            return true;
        }
        String key = "ddpYENbNURw=QED";
        String result = "";
        try {
            byte[] inputData = new BASE64Decoder().decodeBuffer(urlKey);
            byte[] outputData = DESCoder.decrypt(inputData, key);
            result = new String(outputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean  flag = false;
        String sj = result.split(",")[1];
//    	String sj = result.substring(result.length()-14);
        String sxsc = "";//时效时长
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String sql = "select  sxsc from uf_PrescriptionToCo where zt = 0 order by id desc";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        if(rs.next()){
            sxsc = rs.getString("sxsc");
        }
        try {
            Date date1 = format.parse(sj);
            String ss = format.format(new Date());
            Date date2 = format.parse(ss);
            long diff = (date2.getTime() - date1.getTime());//这样得到的差值是毫秒级别
            long aa = (long) (Double.valueOf(sxsc)*60*60*1000);
            if(aa<diff) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return flag;

    }




}
