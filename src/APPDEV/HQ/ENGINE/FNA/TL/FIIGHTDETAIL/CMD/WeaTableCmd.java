package APPDEV.HQ.ENGINE.FNA.TL.FIIGHTDETAIL.CMD;

import com.cloudstore.eccom.constant.WeaBoolAttr;
import com.cloudstore.eccom.pc.table.WeaTable;
import com.cloudstore.eccom.pc.table.WeaTableColumn;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import weaver.general.PageIdConst;
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

            String pageID = "705f03e9-a355-4949-a147-85a1bfb135b7";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";

            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " id,DefineFlag,OrderID,PassengerName,TakeOffTime||' '||TakeOffTime2 as TakeOffTime,case when FlightClass='I' then '国际' when FlightClass='N' then '国内' else '' end as  FlightClass," +
                    "DCityName||'-'||ACityName as OrderDesc,CostCenter3,tClass as Class,PriceRate,price,OilFee,Tax,SendTicketFee,InsuranceFee,ServiceFee,Coupon,RebookQueryFee,ReBookingServiceFee," +
                    "RefundServiceFee,Refund,Amount as RealAmount,ItineraryFee ";
            table.setBackfields(fileds);
            String journeyid =  Util.null2String(params.get("rqid"));
            String FlightClass =  Util.null2String(params.get("FlightClass"));
            String lastmonth =  Util.null2String(params.get("lastmonth"));
            sqlwhere += " and DefineFlag='"+journeyid+"' and FlightClass='"+FlightClass+"' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"'";



            table.setSqlform("ctrip_SettlemenFlightOrdert ");
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("id");
            table.setSqlprimarykey("id");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("id").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("100px", "差旅单号", "DefineFlag","DefineFlag"));
            table.getColumns().add(new WeaTableColumn("100px", "订单号", "OrderID","OrderID"));
            table.getColumns().add(new WeaTableColumn("100px", "乘机人", "PassengerName","PassengerName"));
            table.getColumns().add(new WeaTableColumn("100px", "起飞时间", "TakeOffTime","TakeOffTime"));
            table.getColumns().add(new WeaTableColumn("100px", "国际/国内", "FlightClass","FlightClass"));
            table.getColumns().add(new WeaTableColumn("100px", "航程", "OrderDesc","OrderDesc"));
            //table.getColumns().add(new WeaTableColumn("100px", "里程数", "Tpm","Tpm"));
            table.getColumns().add(new WeaTableColumn("100px", "成本中心3", "CostCenter3","CostCenter3"));
            table.getColumns().add(new WeaTableColumn("100px", "舱等", "Class","Class"));
            table.getColumns().add(new WeaTableColumn("100px", "折扣", "PriceRate","PriceRate"));
            table.getColumns().add(new WeaTableColumn("100px", "成交净价", "price","price"));
            table.getColumns().add(new WeaTableColumn("100px", "燃油税", "OilFee","OilFee"));
            table.getColumns().add(new WeaTableColumn("100px", "民航基金", "Tax","Tax"));
            table.getColumns().add(new WeaTableColumn("100px", "送票费", "SendTicketFee","SendTicketFee"));
            table.getColumns().add(new WeaTableColumn("100px", "配送服务费", "ItineraryFee","ItineraryFee"));
            table.getColumns().add(new WeaTableColumn("100px", "保险费", "InsuranceFee","InsuranceFee"));
            table.getColumns().add(new WeaTableColumn("100px", "服务费", "ServiceFee","ServiceFee"));
            table.getColumns().add(new WeaTableColumn("100px", "优惠券", "Coupon","Coupon"));
            table.getColumns().add(new WeaTableColumn("100px", "改签费", "RebookQueryFee","RebookQueryFee"));
            table.getColumns().add(new WeaTableColumn("100px", "改签服务费", "ReBookingServiceFee","ReBookingServiceFee"));
            table.getColumns().add(new WeaTableColumn("100px", "退票服务费", "RefundServiceFee","RefundServiceFee"));
            table.getColumns().add(new WeaTableColumn("100px", "退票费", "Refund","Refund"));
            table.getColumns().add(new WeaTableColumn("100px", "实收/实付", "RealAmount","RealAmount"));
            //table.getColumns().add(new WeaTableColumn("100px", "ReasonCode", "CodeBrief","CodeBrief"));
            //table.getColumns().add(new WeaTableColumn("100px", "票号", "TicketNo","TicketNo"));
            //table.getColumns().add(new WeaTableColumn("100px", "行程单打印状态", "PrintStatus","PrintStatus"));
            //table.getColumns().add(new WeaTableColumn("100px", "快递单号", "ExpressNo","ExpressNo"));
            //table.getColumns().add(new WeaTableColumn("100px", "改签行程单号", "RebookSegmentNo","RebookSegmentNo"));
            //table.getColumns().add(new WeaTableColumn("100px", "改签快递单号", "RebookExpressNo","RebookExpressNo"));
           // table.getColumns().add(new WeaTableColumn("100px", "改签行程单金额", "RebookSegmentPrintPrice","RebookSegmentPrintPrice"));
            //table.getColumns().add(new WeaTableColumn("100px", "改签后票号状态", "RebookSegStatus","RebookSegStatus"));


            //设置左侧check默认不存在
            table.setCheckboxList(null);
            table.setCheckboxpopedom(null);
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
