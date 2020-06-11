package xhgz.report.cbhsmx.CMD;

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
            String pageID = "ea0e8a53-62ed-4f09-a180-ec2bbd49e143";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablenamecpjghs = "formtable_main_25";//产品价格核算单
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);
            RecordSet rs = new RecordSet();
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='CPJGHSDYFZY'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            String zgjdid="";
            sql = "select jdid from uf_report_mt where bs='CPJGHSDYFZG'";
            rs.execute(sql);
            if(rs.next()){
                zgjdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(zgjdid)){
                zgjdid = "0";
            }
            String fileds = " requestid,pm,bzgg,khmc,yfzy,yfzytjrq,yfzgtjrq";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = "(select a.requestid,b.pm,b.bzgg,a.khmc,(select lastname from hrmresource where id=a.yfzy) as yfzy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as yfzytjrq,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+zgjdid+") and requestid=a.requestid) as yfzgtjrq from "+tablenamecpjghs+" a,"+tablenamecpjghs+"_dt1 b,workflow_requestbase c where a.id=b.mainid and a.requestId=c.requestid and c.currentnodetype=3) t";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            if("1".equals(startDate)){
                sqlwhere += " and t.yfzytjrq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sqlwhere += " and t.yfzytjrq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sqlwhere += " and t.yfzytjrq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sqlwhere += " and t.yfzytjrq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sqlwhere += " and t.yfzytjrq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sqlwhere += " and t.yfzytjrq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sqlwhere += " and t.yfzytjrq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sqlwhere += " and substring(t.yfzytjrq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sqlwhere += " and t.yfzytjrq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sqlwhere += " and t.yfzytjrq <='" + lenddate + "'";
                }
            }
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid desc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("15%", "品名", "pm","pm"));
            table.getColumns().add(new WeaTableColumn("15%", "包装(规格)", "bzgg","bzgg"));
            table.getColumns().add(new WeaTableColumn("15%", "客户名称", "khmc","khmc"));
            table.getColumns().add(new WeaTableColumn("15%", "研发专员", "yfzy","yfzy"));
            table.getColumns().add(new WeaTableColumn("20%", "研发专员1 接收日期", "yfzytjrq","yfzytjrq"));
            table.getColumns().add(new WeaTableColumn("20%", "研发主管* 提交日期", "yfzgtjrq","yfzgtjrq"));


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
