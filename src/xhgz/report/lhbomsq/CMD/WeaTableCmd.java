package xhgz.report.lhbomsq.CMD;

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
            String pageID = "d0556907-954d-4d50-85a9-7a6521199b4f";
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
            String fileds = " wname,requestId,sjkfbgsq,sqrq,lh,kfbgsqyfzy,cjr,lastoperatedate,xjno1,kzlh,mc,gg,bbl,cbl,bz,bcp";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select a.requestId," +
                    "a.sjkfbgsq," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.kfbgsqyfzy='1' then '系统管理员' else (select lastname from HrmResource where id=a.kfbgsqyfzy) end as  kfbgsqyfzy," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id  and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt1')as xjno1," +
                    "c.kzlh," +
                    "c.mc," +
                    "c.gg," +
                    "c.bbl," +
                    "c.cbl," +
                    "c.bz," +
                    "(select d.workflowname from workflow_base d where d.id=b.workflowid and b.currentnodetype=3 and a.requestId=b.requestid) as wname,"+
                    "(select top 1 d.kzlh from "+tablenamecpjghs+"_dt3 d where a.id=d.mainid order by d.id asc) as bcp" +
                    " from "+tablenamecpjghs+" a,  workflow_requestbase b,"+tablenamecpjghs+"_dt1 c" +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3) t";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            String kzlh = Util.null2String(params.get("kzlh"));
            String lh = Util.null2String(params.get("lh"));
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
            if(!"".equals(lh)){
                sqlwhere+= " and t.lh ='"+lh+"'";
            }
          //  new BaseBean().writeLog("XhgzXjLhServcie  sql:"+sqlfrom);
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestId desc");
            table.setSqlprimarykey("requestId");
            table.setSqlisdistinct("false");
            // String fileds = " requestId,sjkfbgsq,sqrq,lh,kfbgsqyfzy,cjr,lastoperatedate,xjno,kzlh,mc,gg,bbl,cbl,bz,bcp";
            table.getColumns().add(new WeaTableColumn("100px", "列号", "lh","lh"));
            table.getColumns().add(new WeaTableColumn("100px", "新/旧（N/O）", "xjno1","xjno1"));
            table.getColumns().add(new WeaTableColumn("100px", "1阶料号", "kzlh","kzlh"));
            table.getColumns().add(new WeaTableColumn("100px", "名称", "mc","mc"));
            table.getColumns().add(new WeaTableColumn("100px", "9阶料号", "bcp","bcp"));
            table.getColumns().add(new WeaTableColumn("100px", "规格", "gg","gg"));
            table.getColumns().add(new WeaTableColumn("100px", "B比例", "bbl","bbl"));
            table.getColumns().add(new WeaTableColumn("100px", "C比例", "cbl","cbl"));
            table.getColumns().add(new WeaTableColumn("100px", "备注", "bz","bz"));
            table.getColumns().add(new WeaTableColumn("100px", "申请人", "cjr","cjr"));
            table.getColumns().add(new WeaTableColumn("100px", "申请日期", "sqrq","sqrq"));
            table.getColumns().add(new WeaTableColumn("100px", "完成日期", "lastoperatedate","lastoperatedate"));
         //   table.getColumns().add(new WeaTableColumn("100px", "设计开发/变更申请", "wname","sjkfbgsq","xhgz.report.lhbomsq.CMD.WeaTableTransMethod.getRequestUrl","column:sjkfbgsq"));
            table.getColumns().add(new WeaTableColumn("100px", "研发专员", "kfbgsqyfzy","kfbgsqyfzy"));
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
