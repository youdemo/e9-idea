package xhgz.report.fgpcljamx.CMD;

import com.cloudstore.eccom.pc.table.WeaTable;
import com.cloudstore.eccom.pc.table.WeaTableColumn;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import org.apache.commons.lang.StringUtils;
import weaver.conn.RecordSet;
import weaver.general.PageIdConst;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.User;

import java.util.HashMap;
import java.util.Map;

public class WeaTableCmd extends AbstractCommonCommand<Map<String,Object>> {
    public WeaTableCmd(User user, Map<String,Object> params) {
        this.user = user;
        this.params = params;
    }


    @Override
    public BizLogContext getLogContext() {
        return null;
    }

    @Override
    public Map<String, Object> execute(CommandContext commandContext) {
        Map<String, Object> apidatas = new HashMap<String, Object>();
        //if(!HrmUserVarify.checkUserRight("LogView:View", user)){
        //    apidatas.put("hasRight", false);
        //    return apidatas;
       // }
        try {
            //返回消息结构体
            WeaResultMsg result = new WeaResultMsg(false);
            String pageID = "43607bdc-f2ee-4bff-95bb-ccefab309324";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablenamefgp = "formtable_main_41";//返工品处理但
            String tablenameyppc = "formtable_main_23";//样品品尝及送样记录表
            String tablenametzd = "formtable_main_44";//通知单
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);
            RecordSet rs = new RecordSet();
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='FGPCLZZDYFZY'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            String fileds = " requestId,djbh,ycpmcdx,erplh,sl,(select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenamefgp+"' and a.fieldname='ycpmc' and c.selectvalue=t.ycpmc) as ycpmc,ycms,zpyfry,qtjyyf,jarq, pcrq,ffbh";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select  a.requestId,a.djbh,a.ycpmcdx,a.erplh,a.sl,a.ycpmc,a.ycms,(select lastname from hrmresource where id=a.zpyfry) as zpyfry,qtjyyf,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as jarq,(select lastoperatedate from workflow_requestbase where requestid=c.requestId and currentnodetype=3) as pcrq,d.ffbh from "+tablenamefgp+" a join workflow_requestbase b on a.requestid=b.requestid left join "+tablenameyppc+" c on a.ycpmcdx=c.ypmc  left join "+tablenametzd+" d on  a.ycpmcdx=d.pm where b.currentnodetype=3) t";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            if("1".equals(startDate)){
                sqlwhere += " and t.jarq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sqlwhere += " and t.jarq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sqlwhere += " and t.jarq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sqlwhere += " and t.jarq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sqlwhere += " and t.jarq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sqlwhere += " and t.jarq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sqlwhere += " and t.jarq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sqlwhere += " and substring(t.jarq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sqlwhere += " and t.jarq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sqlwhere += " and t.jarq <='" + lenddate + "'";
                }
            }
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid desc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("10%", "单据编号", "djbh","djbh"));
            table.getColumns().add(new WeaTableColumn("10%", "异常品名称", "ycpmcdx","ycpmcdx"));
            table.getColumns().add(new WeaTableColumn("8%", "ERP料号", "erplh","erplh"));
            table.getColumns().add(new WeaTableColumn("8%", "数量", "sl","sl"));
            table.getColumns().add(new WeaTableColumn("8%", "异常品分类", "ycpmc","ycpmc"));
            table.getColumns().add(new WeaTableColumn("12%", "异常描述", "ycms","ycms"));
            table.getColumns().add(new WeaTableColumn("10%", "指派研发专员", "zpyfry","zpyfry"));
            table.getColumns().add(new WeaTableColumn("10%", "其他建议（研发）", "qtjyyf","qtjyyf"));
            table.getColumns().add(new WeaTableColumn("8%", "接案日期", "jarq","jarq"));
            table.getColumns().add(new WeaTableColumn("8%", "品尝日期", "pcrq","pcrq"));
            table.getColumns().add(new WeaTableColumn("8%", "通知单号", "ffbh","ffbh"));


            //设置左侧check默认不存在
            table.setCheckboxList(null);
            table.setCheckboxpopedom(null);
            //table.setSumColumns("xcjpzje,AirAmo");
            //table.setDecimalFormat("%,.2f|%,.2f");
            //List<WeaTableCheckboxpopedom> checkboxpopedomList = new ArrayList<>();
            //WeaTableCheckboxpopedom checkboxpopedom = new WeaTableCheckboxpopedom();
            //checkboxpopedom.setPopedompara("column:id");
            //checkboxpopedom.setShowmethod("com.engine.demo.test.cmd.WeaTableTransMethod.showmethod");
            //checkboxpopedom.setId("checkbox");
            //checkboxpopedomList.add(checkboxpopedom);
            //table.getTableType().setName("checkbox");
            //table.setCheckboxList(checkboxpopedomList);

            result.putAll(table.makeDataResult());
            result.put("hasRight", true);
            result.success();
            apidatas = result.getResultMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return apidatas;
    }
}
