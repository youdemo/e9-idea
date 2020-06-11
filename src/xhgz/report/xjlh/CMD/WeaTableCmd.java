package xhgz.report.xjlh.CMD;

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
            String pageID = "b6d062fc-76c7-4eeb-8946-d0b6d6028067";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablenamecpjghs = "formtable_main_27";//料号及BOM申请联络单
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);
            RecordSet rs = new RecordSet();
//            String jdid="";
//            String sql = "select jdid from uf_report_mt where bs='CPJGHSDYFZY'";
//            rs.execute(sql);
//            if(rs.next()){
//                jdid = Util.null2String(rs.getString("jdid"));
//            }
//            if("".equals(jdid)){
//                jdid = "0";
//            }
//            String zgjdid="";
//            sql = "select jdid from uf_report_mt where bs='CPJGHSDYFZG'";
//            rs.execute(sql);
//            if(rs.next()){
//                zgjdid = Util.null2String(rs.getString("jdid"));
//            }
//            if("".equals(zgjdid)){
//                zgjdid = "0";
//            }
            String fileds = " rn,requestId,sqrq,lh,cjr,lastoperatedate,xjno1,kzlh,mc ";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select  row_number() over(order by tempcolumn1) as rn,p.* from " +
                    "(select  tempcolumn1=0,requestId,sqrq,lh,cjr,lastoperatedate,xjno1,kzlh,mc from " +
                    "((((select a.requestId," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt1')as xjno1," +
                    "c.kzlh," +
                    "c.mc" +
                    " from formtable_main_27 a,  workflow_requestbase b,formtable_main_27_dt1 c " +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3) union " +
                    "(select a.requestId," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt2')as xjno1," +
                    "c.kzlh," +
                    "c.lpm as mc " +
                    " from formtable_main_27 a,  workflow_requestbase b,formtable_main_27_dt2 c " +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3)) union " +
                    "(select a.requestId," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt3')as xjno1," +
                    "c.kzlh," +
                    "c.lpmjcpbh as mc " +
                    " from formtable_main_27 a,  workflow_requestbase b,formtable_main_27_dt3 c " +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3)) union " +
                    "(select a.requestId," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt4')as xjno1," +
                    "c.kzlh," +
                    " c.lpm as mc " +
                    " from formtable_main_27 a,  workflow_requestbase b,formtable_main_27_dt4 c " +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3)) y) p) t";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            String kzlh = Util.null2String(params.get("kzlh"));
            if("1".equals(startDate)){
                sqlwhere += " and t.sqrq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sqlwhere += " and t.sqrq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sqlwhere += " and t.sqrq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sqlwhere += " and t.sqrq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sqlwhere += " and t.sqrq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sqlwhere += " and t.sqrq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sqlwhere += " and t.sqrq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sqlwhere += " and substring(t.sqrq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sqlwhere += " and t.sqrq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sqlwhere += " and t.sqrq <='" + lenddate + "'";
                }
            }
            if(!"".equals(kzlh)){
                sqlwhere+= " and  t.kzlh ='"+kzlh+"'";
            }
          //  new BaseBean().writeLog("XhgzXjLhServcie  sql:"+sqlfrom);
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestId desc");
            table.setSqlprimarykey("requestId");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("15%", "列号", "lh","lh"));
            table.getColumns().add(new WeaTableColumn("10%", "新/旧（N/O）", "xjno1","xjno1"));
            table.getColumns().add(new WeaTableColumn("15%", "料号", "kzlh","kzlh"));
            table.getColumns().add(new WeaTableColumn("15%", "名称", "mc","mc"));
            table.getColumns().add(new WeaTableColumn("15%", "申请人", "cjr","cjr"));
            table.getColumns().add(new WeaTableColumn("15%", "申请日期", "sqrq","sqrq"));
            table.getColumns().add(new WeaTableColumn("15%", "完成日期", "lastoperatedate","lastoperatedate"));
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
