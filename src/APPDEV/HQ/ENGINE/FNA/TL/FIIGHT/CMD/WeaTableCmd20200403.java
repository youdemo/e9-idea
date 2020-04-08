package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD;

import APPDEV.HQ.Contract.ConCommonClass;
import com.cloudstore.eccom.constant.WeaBoolAttr;
import com.cloudstore.eccom.pc.table.WeaTable;
import com.cloudstore.eccom.pc.table.WeaTableColumn;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import org.apache.commons.lang.StringUtils;
import weaver.general.PageIdConst;
import weaver.general.Util;
import weaver.hrm.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeaTableCmd20200403 extends AbstractCommonCommand<Map<String,Object>> {
    public WeaTableCmd20200403(User user, Map<String,Object> params) {
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
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
            date = calendar.getTime();
            String lastmonth = format.format(date);
            String pageID = "0d4f39a5-804d-4578-8a73-aad48490f611";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");

            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " requestid,requestname,xcydsy,rzgsbm,costcenter,xcjpzje,AirAmo,sfbx ";
            table.setBackfields(fileds);
            String year =  Util.null2String(params.get("year"));
            String month =  Util.null2String(params.get("month"));
            if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
                lastmonth = year+month;
            }
            //搜索条件,这里可以放高级搜索的的条件 差率单号
            String requestid =  Util.null2String(params.get("requestid"));
            if (StringUtils.isNotBlank(requestid)) {
                sqlwhere += " and requestid like '%" + requestid + "%' ";
            }

            //入账公司编码
            String rzgsbm =  Util.null2String(params.get("rzgsbm"));
            if (StringUtils.isNotBlank(rzgsbm)) {
                sqlwhere += " and rzgsbm like '%" + rzgsbm + "%' ";
            }

            //成本中心
            String costcenter =  Util.null2String(params.get("costcenter"));
            if (StringUtils.isNotBlank(costcenter)) {
                sqlwhere += " and costcenter like '%" + costcenter + "%' ";
            }

            //是否报销
            String sfbx =  Util.null2String(params.get("sfbx"));
            if (StringUtils.isNotBlank(sfbx) ) {
                sqlwhere += " and sfbx = '" + sfbx + "' ";
            }



            table.setSqlform(" (select journeyid as requestid,(select requestname from WORKFLOW_REQUESTBASE where requestid=a.journeyid) as  requestname ,max(JourneyReason) as xcydsy,max(COSTCENTER) as rzgsbm,max(COSTCENTER2) as costcenter,sum(nvl(amount,0)) as xcjpzje,(select nvl(AirAmo,0.00) as AirAmo from "+tablesq+" where requestid=a.JOURNEYID) as AirAmo,case when (select count(1) from "+tablebx+" t,workflow_requestbase t1 where t.REQUESTID=t1.REQUESTID and t1.CURRENTNODETYPE>=1 and t.traappli=a.JOURNEYID) > 0 then '是' else '否' end as sfbx from ctrip_SettlemenFlightOrdert a where substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' group by journeyid) a");
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("requestid").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("10%", "差旅单号", "requestid","requestid"));
            table.getColumns().add(new WeaTableColumn("20%", "差旅申请", "requestname","requestname","APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeaTableTransMethod.getRequestUrl","column:requestid"));
            table.getColumns().add(new WeaTableColumn("20%", "携程预定事由", "xcydsy","xcydsy"));
            table.getColumns().add(new WeaTableColumn("10%", "入账公司", "rzgsbm","rzgsbm"));
            table.getColumns().add(new WeaTableColumn("10%", "成本中心", "costcenter","costcenter"));
            table.getColumns().add(new WeaTableColumn("10%", "机票总金额", "xcjpzje","xcjpzje","APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeaTableTransMethod.getjpctUrl","column:requestid"));
            table.getColumns().add(new WeaTableColumn("10%", "申请预估金额", "AirAmo","AirAmo"));
            table.getColumns().add(new WeaTableColumn("10%", "是否报销", "sfbx","sfbx"));

            //设置左侧check默认不存在
            table.setCheckboxList(null);
            table.setCheckboxpopedom(null);
            table.setSumColumns("xcjpzje,AirAmo");
            table.setDecimalFormat("%,.2f|%,.2f");
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
