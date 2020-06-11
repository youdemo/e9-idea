package xhgz.report.ypdhmx.CMD;

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
            String pageID = "ceaf4a81-5a25-40d2-956a-b3f018efef69";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablename = "formtable_main_128";//样品订货单
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);
            RecordSet rs = new RecordSet();
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='YPDHDYFJDR'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }

            String fileds = " requestid,bdbh,khmc, (select  c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablename+"' and a.fieldname='lb' and c.selectvalue=t.lb) as lb,ywy,yfjsrq,bh,pm,slkg,yfjdr,yqchsj,sjchsj ";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select a.requestid,a.bh as bdbh,khmc,lb,(select lastname from hrmresource where id=a.ywy) as ywy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as yfjsrq,b.bh,b.pm,b.slkg,(select lastname from hrmresource where id=a.yfjdr) as yfjdr,yqchsj,sjchsj  from "+tablename+" a,"+tablename+"_dt1 b,workflow_requestbase c where a.id=b.mainid and a.requestid = c.requestid and c.currentnodetype=3) t";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            if("1".equals(startDate)){
                sqlwhere += " and t.yfjsrq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sqlwhere += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sqlwhere += " and t.yfjsrq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sqlwhere += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sqlwhere += " and t.yfjsrq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sqlwhere += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sqlwhere += " and t.yfjsrq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sqlwhere += " and substring(t.yfjsrq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sqlwhere += " and t.yfjsrq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sqlwhere += " and t.yfjsrq <='" + lenddate + "'";
                }
            }
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid desc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("11%", "表单编号", "bdbh","bdbh"));
            table.getColumns().add(new WeaTableColumn("10%", "客户名称", "khmc","khmc"));
            table.getColumns().add(new WeaTableColumn("7%", "类别", "lb","lb"));
            table.getColumns().add(new WeaTableColumn("7%", "业务员", "ywy","ywy"));
            table.getColumns().add(new WeaTableColumn("10%", "研发接收日期", "yfjsrq","yfjsrq"));
            table.getColumns().add(new WeaTableColumn("10%", "编号", "bh","bh"));
            table.getColumns().add(new WeaTableColumn("8%", "品名", "pm","pm"));
            table.getColumns().add(new WeaTableColumn("9%", "数量（KG）", "slkg","slkg"));
            table.getColumns().add(new WeaTableColumn("8%", "研发专员", "yfjdr","yfjdr"));
            table.getColumns().add(new WeaTableColumn("10%", "要求出货时间", "yqchsj","yqchsj"));
            table.getColumns().add(new WeaTableColumn("10%", "确认出货时间", "sjchsj","sjchsj"));


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
