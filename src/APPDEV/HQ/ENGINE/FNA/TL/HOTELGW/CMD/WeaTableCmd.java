package APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD;

import APPDEV.HQ.Contract.ConCommonClass;
import com.cloudstore.eccom.pc.table.WeaTable;
import com.cloudstore.eccom.pc.table.WeaTableCheckboxpopedom;
import com.cloudstore.eccom.pc.table.WeaTableColumn;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import org.apache.commons.lang.StringUtils;
import weaver.conn.RecordSet;
import weaver.general.PageIdConst;
import weaver.general.Util;
import weaver.hrm.User;

import java.text.SimpleDateFormat;
import java.util.*;

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
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
            date = calendar.getTime();
            String lastmonth = format.format(date);
            String pageID = "c5f3678a-52bb-45cc-b874-ee5b4c155ab3";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");
            String operator = user.getUID()+"";
            String corpcode = "";
            RecordSet rs = new RecordSet();
            String sql = "select corpcode from uf_fna_monrecouth where operator='"+operator+"'";
            rs.execute(sql);
            if(rs.next()){
                corpcode = Util.null2String(rs.getString("corpcode"));
            }
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);


            String year =  Util.null2String(params.get("year"));
            String month =  Util.null2String(params.get("month"));
            if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
                lastmonth = year+month;
            }
            String fileds = " requestid,xcydsy,rzgsbm,costcenter,xcjpzje,sfbx,BtrPER,BTRBEGDA,BTRENDDA,WFStatus,tjcs,hzje,'"+lastmonth+"' as lastmonth,grzfje ";
            table.setBackfields(fileds);
            //搜索条件,这里可以放高级搜索的的条件 差率单号
            String requestid =  Util.null2String(params.get("requestid"));
            if (StringUtils.isNotBlank(requestid)) {
                sqlwhere += " and requestid like '%" + requestid + "%' ";
            }

            //入账公司编码
            String rzgsbm =  Util.null2String(params.get("rzgsbm"));
            if (StringUtils.isNotBlank(rzgsbm)) {
                sqlwhere += " and rzgsbm in('"+rzgsbm+"') ";
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

            String ccr =  Util.null2String(params.get("ccr"));
            if (StringUtils.isNotBlank(ccr) ) {
                sqlwhere += " and ccr = '" + ccr + "' ";
            }

            String wfstatus =  Util.null2String(params.get("WFStatus"));
            if (StringUtils.isNotBlank(wfstatus) ) {
                sqlwhere += " and clzt = '" + wfstatus + "' ";
            }

            String sqlform = " (select a.requestid,a.xcydsy,a.rzgsbm,a.costcenter,a.xcjpzje,a.sfbx,(select sum(nvl(amount,0)) from CTRIP_SETTLEMENHOTELORDERT where DEFINETITLECONTENT=a.requestid and IsDomestic='false' and paytype='Y') as hzje,(select count(1) from uf_fna_sapfkts where travelno=a.requestid and year='"+lastmonth.substring(0,4)+"' and month='"+lastmonth.substring(4)+"' and type='3') as tjcs,(select lastname from hrmresource where id=b.BtrPER)as BtrPER,b.BtrPER as ccr,b.wfstatus as clzt,BTRBEGDA,BTRENDDA,(select description from uf_wfstatus where code=b.wfstatus and status='1') as WFStatus,(select nvl(sum(nvl(t1.HOCOSTRMB,0)),0) as grzfje from "+tablebx+" t,"+tablebx+"_dt5 t1 where t.id=t1.mainid and t.traappli=a.requestid) as grzfje from ((select DEFINETITLECONTENT as requestid ,max(JourneyReason) as xcydsy,max(COSTCENTER) as rzgsbm,max(COSTCENTER2) as costcenter,sum(nvl(amount,0)) as xcjpzje,case when (select count(1) from "+tablebx+" t,workflow_requestbase t1 where t.REQUESTID=t1.REQUESTID and t1.CURRENTNODETYPE>=1 and t.traappli=a.DEFINETITLECONTENT) > 0 then '是' else '否' end as sfbx from CTRIP_SETTLEMENHOTELORDERT a where DEFINETITLECONTENT is not null and IsDomestic='false' and paytype='Y' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' ";
            if(!"1".equals(operator)){
                if(!"".equals(corpcode)){
                    sqlform += " and COSTCENTER in ("+corpcode+") ";
                }else{
                    sqlform += " and 1=2 ";
                }
            }
            sqlform +="group by DEFINETITLECONTENT) a left join "+tablesq+" b on a.requestid=b.requestid)) a";

            table.setSqlform(sqlform);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            String otherparam="column:requestid+column:lastmonth+column:WFStatus";
           // table.getColumns().add(new WeaTableColumn("requestid").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("10%", "差旅单号", "requestid","requestid","APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD.WeaTableTransMethod.getRequestUrl","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "出差人", "BtrPER","BtrPER","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "差旅开始", "BTRBEGDA","BTRBEGDA","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "差旅结束", "BTRENDDA","BTRENDDA","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "差旅状态", "WFStatus","WFStatus","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "是否报销", "sfbx","sfbx","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            //table.getColumns().add(new WeaTableColumn("17%", "携程预定事由", "xcydsy","xcydsy"));
//            table.getColumns().add(new WeaTableColumn("10%", "入账公司", "rzgsbm","rzgsbm","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "成本中心", "costcenter","costcenter","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "酒店总金额", "xcjpzje","xcjpzje","APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD.WeaTableTransMethod.getjpctUrl",otherparam));
            table.getColumns().add(new WeaTableColumn("10%", "累计金额", "hzje","hzje","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));
            table.getColumns().add(new WeaTableColumn("10%", "个人支付", "grzfje","grzfje","APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD.WeaTableTransMethod.setcolor","column:WFStatus"));

            //设置左侧check默认不存在
            table.setCheckboxList(null);
            table.setCheckboxpopedom(null);
            table.setSumColumns("xcjpzje");
            table.setDecimalFormat("%,.2f");
            List<WeaTableCheckboxpopedom> checkboxpopedomList = new ArrayList<>();
            WeaTableCheckboxpopedom checkboxpopedom = new WeaTableCheckboxpopedom();
            checkboxpopedom.setPopedompara("column:tjcs");
            checkboxpopedom.setShowmethod("APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD.WeaTableTransMethod.canCheck");
            checkboxpopedom.setId("checkbox");
            checkboxpopedomList.add(checkboxpopedom);
            table.getTableType().setName("checkbox");
            table.setCheckboxList(checkboxpopedomList);

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
