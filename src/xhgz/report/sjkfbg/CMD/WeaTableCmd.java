package xhgz.report.sjkfbg.CMD;

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
            String pageID = "67bc7f39-ab9e-40d7-b058-858a529ed7af";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String userid = user.getUID()+"";
            String sqlwhere = " 1=1 ";
            String tablenamesjkfbg = "formtable_main_2";//设计开发/变更申请单
            String tablenameyppc = "formtable_main_23";//样品品尝及送样记录表
            String tablenameypfk = "formtable_main_24";//样品反馈信息表
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);
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

            String fileds = " syjlid,requestid,bh,sjkfzt,(select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenamesjkfbg+"' and a.fieldname='a' and c.selectvalue=t.sjkflb) as sjkflb,mbkh,(select lastname from hrmresource where id=t.zpyfzy)zpyfzy,jarq,xqwcrq,yjwcrq,syrq,sycs,sfwc,yjfkrq,ypbh,sjfkrq, (select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenameypfk+"' and a.fieldname='fkjg' and c.selectvalue=t.fkjg) as fkjg ";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = " (select c.requestid as syjlid,a.requestid,a.bh,a.sjkfzt,a.a as sjkflb,a.mbkh,a.zpyfzy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as jarq,a.xqwcrq,a.yjwcrq,(select lastoperatedate from workflow_requestbase where requestid=c.requestId and currentnodetype=3) as syrq,c.sycs,(select case when currentnodetype=3 then 'Y' else 'N' end from workflow_requestbase where requestid=c.requestId) as sfwc,c.yjfkrq,c.ypbh,(select createdate from workflow_requestbase where requestid=d.requestid) as sjfkrq, fkjg from "+tablenamesjkfbg+" a join workflow_requestbase b on a.requestId=b.requestid left join "+tablenameyppc+" c on a.sjkfzt = c.ypmc left join "+tablenameypfk+" d on c.requestid = d.lcid where b.currentnodetype=3) t";


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
            table.setSqlorderby("requestid desc,syjlid asc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("100px", "编号", "bh","bh"));
            table.getColumns().add(new WeaTableColumn("100px", "设计开发主题", "sjkfzt","sjkfzt"));
            table.getColumns().add(new WeaTableColumn("100px", "设计开发类别", "sjkflb","sjkflb"));
            table.getColumns().add(new WeaTableColumn("100px", "目標客戶", "mbkh","mbkh"));
            table.getColumns().add(new WeaTableColumn("100px", "指派研发专员", "zpyfzy","zpyfzy"));
            table.getColumns().add(new WeaTableColumn("100px", "接案日期", "jarq","jarq"));
            table.getColumns().add(new WeaTableColumn("100px", "需求完成日期", "xqwcrq","xqwcrq"));
            table.getColumns().add(new WeaTableColumn("100px", "预估完成日期", "yjwcrq","yjwcrq"));
            table.getColumns().add(new WeaTableColumn("100px", "送样日期", "syrq","syrq"));
            table.getColumns().add(new WeaTableColumn("100px", "送样次数", "sycs","sycs"));
            table.getColumns().add(new WeaTableColumn("100px", "是否完成（Y/N）", "sfwc","sfwc"));
            table.getColumns().add(new WeaTableColumn("100px", "预计反馈日期", "yjfkrq","yjfkrq"));
            table.getColumns().add(new WeaTableColumn("100px", "样品编号", "ypbh","ypbh"));
            table.getColumns().add(new WeaTableColumn("100px", "实际反馈日期", "sjfkrq","sjfkrq"));
            table.getColumns().add(new WeaTableColumn("100px", "反馈结果", "fkjg","fkjg"));



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
