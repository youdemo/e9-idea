package APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.CMD;

import com.cloudstore.eccom.constant.WeaBoolAttr;
import com.cloudstore.eccom.pc.table.WeaTable;
import com.cloudstore.eccom.pc.table.WeaTableColumn;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.ctrip.ecology.system.SettlementHotelOrderCron;
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

            String pageID = "f97199c3-49cf-47b5-9973-91afb45fb02c";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";

            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = "id,DEFINETITLECONTENT,OrderID,ClientName as clients,costcenter2 as costcenter, OrderDate,StartTime as ETA,EndTime as ETD," +
                    "CityName as city,HotelName,Quantity,Price,Amount,ExtraCharge as addedfees,Servicefee," +
                    "case when PayType='X' then '员工现付' when PayType='Y' then '公司月结' else '' end as Corp_PayType," +
                    "case when IsDomestic='true' then '国内' when IsDomestic='false' then '海外酒店' else '' end as HtlClass";
            table.setBackfields(fileds);
            String HOTELRELATEDJOURNEYNO =  Util.null2String(params.get("rqid"));
            sqlwhere += " and DEFINETITLECONTENT='"+HOTELRELATEDJOURNEYNO+"' ";



            table.setSqlform("CTRIP_SETTLEMENHOTELORDERT ");
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("id");
            table.setSqlprimarykey("id");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("id").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("100px", "关联行程号", "DEFINETITLECONTENT","DEFINETITLECONTENT"));
            table.getColumns().add(new WeaTableColumn("100px", "订单号", "OrderID","OrderID"));
            //table.getColumns().add(new WeaTableColumn("100px", "订单状态", "status","status"));
            table.getColumns().add(new WeaTableColumn("100px", "入住人", "clients","clients"));
            table.getColumns().add(new WeaTableColumn("100px", "成本中心", "costcenter","costcenter"));
            table.getColumns().add(new WeaTableColumn("100px", "预定日期", "OrderDate","OrderDate"));
            table.getColumns().add(new WeaTableColumn("100px", "入住日期", "ETA","ETA"));
            table.getColumns().add(new WeaTableColumn("100px", "离店日期", "ETD","ETD"));
            table.getColumns().add(new WeaTableColumn("100px", "酒店城市", "city","city"));
            table.getColumns().add(new WeaTableColumn("100px", "酒店名称", "HotelName","HotelName"));
            //table.getColumns().add(new WeaTableColumn("100px", "房型", "roomname","roomname"));
            //table.getColumns().add(new WeaTableColumn("100px", "房间数", "roomcount","roomcount"));
            //table.getColumns().add(new WeaTableColumn("100px", "星级", "star","star"));
            table.getColumns().add(new WeaTableColumn("100px", "间夜", "Quantity","Quantity"));
            table.getColumns().add(new WeaTableColumn("100px", "单价", "Price","Price"));
            table.getColumns().add(new WeaTableColumn("100px", "金额", "Amount","Amount"));
            table.getColumns().add(new WeaTableColumn("100px", "手续费", "addedfees","addedfees"));
            table.getColumns().add(new WeaTableColumn("100px", "服务费", "Servicefee","Servicefee"));
            //table.getColumns().add(new WeaTableColumn("100px", "实收实付", "PaidAmount","PaidAmount"));
            //table.getColumns().add(new WeaTableColumn("100px", "补充说明", "remarks","remarks"));
            table.getColumns().add(new WeaTableColumn("100px", "公费/自费", "Corp_PayType","Corp_PayType"));
            table.getColumns().add(new WeaTableColumn("100px", "酒店类型", "HtlClass","HtlClass"));
            //table.getColumns().add(new WeaTableColumn("100px", "备注", "EmptyColumn","EmptyColumn"));
            //table.getColumns().add(new WeaTableColumn("100px", "预订来源", "IsOnline","IsOnline"));



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
