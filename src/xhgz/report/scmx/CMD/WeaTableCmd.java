package xhgz.report.scmx.CMD;

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
            String pageID = "209a72f1-9a28-4db1-a910-ad6489d017c2";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablenamesjkf = "formtable_main_2";//设计开发变更申请
            String tablenamebom = "formtable_main_27";//BOM
            String tablenametscqr = "formtable_main_35";//试车确认
            String tablenametlc = "formtable_main_33";//试车量产
            //新建一个weatable
            RecordSet rs = new RecordSet();
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='SJKFBGYFZY'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            String cwid="";
            sql = "select jdid from uf_report_mt where bs='BOMCW'";
            rs.execute(sql);
            if(rs.next()){
                cwid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(cwid)){
                cwid = "0";
            }

            String yfzgid="";
            sql = "select jdid from uf_report_mt where bs='BOMYFZG'";
            rs.execute(sql);
            if(rs.next()){
                yfzgid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(yfzgid)){
                yfzgid = "0";
            }
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " requestid,bh,sjkfzt,(select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenamesjkf+"' and a.fieldname='a' and c.selectvalue=t.sjkflb) as sjkflb,mbkh,(select lastname from hrmresource where id=t.zpyfzy) as zpyfzy,jarq,yjlh,jjlh,scrq,scjg,clkg,scdd";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select a.requestid,a.bh,a.sjkfzt,a.a as sjkflb,a.mbkh,a.zpyfzy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid)  as jarq,(select kzlh from "+tablenamebom+"_dt1 where id=(select min(t1.id) from "+tablenamebom+"_dt1 t1 where e.id=t1.mainid )) as yjlh,(select kzlh from "+tablenamebom+"_dt1 where id=(select min(t1.id) from "+tablenamebom+"_dt3 t1 where e.id=t1.mainid )) as jjlh,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+cwid+") and requestid=c.requestid)  as scrq,c.requestid as scjg,d.clkg,d.scdd from "+tablenamesjkf+" a join workflow_requestbase b on a.requestId=b.requestid left join "+tablenamebom+" e on a.requestid=e.sjkfbgsq left join "+tablenametlc+" d on e.requestid=d.lcid left join "+tablenametscqr+" c on d.requestid=c.lcid  where b.currentnodetype=3) t";


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

            table.getColumns().add(new WeaTableColumn("8%", "编号", "bh","bh"));
            table.getColumns().add(new WeaTableColumn("9%", "设计开发主题", "sjkfzt","sjkfzt"));
            table.getColumns().add(new WeaTableColumn("9%", "设计开发类别", "sjkflb","sjkflb"));
            table.getColumns().add(new WeaTableColumn("8%", "目標客戶", "mbkh","mbkh"));
            table.getColumns().add(new WeaTableColumn("9%", "指派研发专员", "zpyfzy","zpyfzy"));
            table.getColumns().add(new WeaTableColumn("8%", "接案日期", "jarq","jarq"));
            table.getColumns().add(new WeaTableColumn("8%", "1阶料号", "yjlh","yjlh"));
            table.getColumns().add(new WeaTableColumn("8%", "9阶料号", "jjlh","jjlh"));
            table.getColumns().add(new WeaTableColumn("8%", "试车日期", "scrq","scrq"));
            table.getColumns().add(new WeaTableColumn("8%", "试车结果", "scjg","scjg","xhgz.report.scmx.CMD.WeaTableTransMethod.getScjg"));
            table.getColumns().add(new WeaTableColumn("9%", "试车重量（KG）", "clkg","clkg"));
            table.getColumns().add(new WeaTableColumn("8%", "试车地点", "scdd","scdd"));


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
