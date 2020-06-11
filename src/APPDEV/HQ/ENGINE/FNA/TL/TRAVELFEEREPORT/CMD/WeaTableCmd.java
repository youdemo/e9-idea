package APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD;

import APPDEV.HQ.Contract.ConCommonClass;
import com.cloudstore.eccom.constant.WeaBoolAttr;
import com.cloudstore.eccom.pc.table.WeaTable;
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
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");
            String kjjs = com.getformodeid("FNA_TravleSQ", "ROLE_ID_KJ");
            String canSeeAll = "";
            RecordSet rs = new RecordSet();
            int countsl = 0;
            String sql = "select count(1) as count from hrmrolemembers where roleid="+kjjs+" and RESOURCEID="+userid;
            rs.execute(sql);
            if(rs.next()){
                countsl = rs.getInt("count");
            }
            if(countsl>0){
                canSeeAll ="1";
            }
            String ckbm = "";
            sql = "select BM from uf_fna_BtrRight where HANPER='"+userid+"'";
            rs.execute(sql);
            while(rs.next()){
                String bm = Util.null2String(rs.getString("BM"));
                if(!"".equals(bm)){
                    if(!"".equals(ckbm)){
                        ckbm = ckbm+","+bm;
                    }else{
                        ckbm = bm;
                    }
                }
            }
            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " requestid,BtrBukrs,BELNR,GJAHR,qj,BLDAT,BTRWorkCode,(select lastname from hrmresource where id=a.BtrPER) as BtrPER,TRAAPPLI,bxdh,SPEPRO,BTRCon,BTRReasonsDet,BTRRegion,ASSIGNMENT,BTRBEGDA,BTRENDDA,BTRDAYS,COSTCENTER,FundCenterCODE,MEEXP,HoAmo,OthTraCost,AirAmo,OthCost,AccMEEXPB,AccHoAmoB,AccOthTraCostB,AccOthCostB,ExcAmo,ECEDES,TolCos,(select description from uf_wfstatus where code=a.WFStatus) as WFStatus";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = " (select b.requestid,b.BtrBukrs,b.BELNR,b.GJAHR,to_number(substr(b.BLDAT,6,2)) as qj,b.BLDAT,BTRWorkCode,BtrPER,TRAAPPLI,b.rqid as bxdh,SPEPRO,case when BTRCon='0' then '国内' when BTRCon='1' then '国外' else '' end as BTRCon,(select TraMotivaDet from uf_fna_TraMotivaDet where id=b.BTRReasonsDet) as BTRReasonsDet,(select value from uf_Paper_REGION where code=b.BTRRegion) as BTRRegion,ASSIGNMENT,BTRBEGDA,BTRENDDA,BTRDAYS,COSTCENTER,FundCenterCODE" +
                    ",nvl(MEEXP,0)-nvl(AccMEEXPB,0) as MEEXP,nvl(HoAmo,0)+nvl(TolAmoOfCH,0)-nvl(AccHoAmoB,0) as HoAmo,nvl(OthTraCost,0)-nvl(AccOthTraCostB,0) as OthTraCost,nvl(AirAmo,0)+nvl(TolAmoOfCA,0) as AirAmo,nvl(OthCost,0)-nvl(AccOthCostB,0) as OthCost" +
                    ",AccMEEXPB,AccHoAmoB,AccOthTraCostB,AccOthCostB,nvl(ExcAmo,0) as ExcAmo,ECEDES,TolCos,b.WFStatus" +
                    " from WORKFLOW_REQUESTBASE a,"+tablebx+" b where a.REQUESTID=b.REQUESTID and a.CURRENTNODETYPE>0 ";
            String cxry =  Util.null2String(params.get("cxry"));
            String iscsh =  Util.null2String(params.get("iscsh"));
            if("1".equals(iscsh)){
                sqlfrom +=" and (b.BtrPER ='" + userid + "' or b.HanPER='" + userid + "') ";
            }else {
                if (!"1".equals(userid) && !"1".equals(canSeeAll)) {
                    sqlfrom += " and (b.BtrPER ='" + userid + "' or b.HanPER='" + userid + "' or b.BtrPER in(select id from table(getchilds(" + userid + "))) or b.HanPER in(select id from table(getchilds(" + userid + ")))";
                    if(!"".equals(ckbm)){
                        sqlfrom +=" or b.BtrDEP in("+ckbm+")";
                    }
                    sqlfrom +=")";
                }
            }

            if (StringUtils.isNotBlank(cxry)) {
                sqlfrom += " and b.BtrPER in("+cxry+") ";
            }
            String WFStatus =  Util.null2String(params.get("WFStatus"));
            if (StringUtils.isNotBlank(WFStatus)) {
                sqlfrom += " and b.WFStatus ='"+WFStatus+"'";
            }

            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate) ) {
                sqlfrom += " and b.BTRBEGDA >='"+fromdate+"'";
            }
            if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                sqlfrom += " and b.BTRBEGDA <='"+lenddate+"'";
            }

            String fromdate1 =  Util.null2String(params.get("fromdate1"));
            String lenddate1 =  Util.null2String(params.get("lenddate1"));
            if (StringUtils.isNotBlank(fromdate1) && !"null".equals(fromdate1) ) {
                sqlfrom += " and b.BTRENDDA >='"+fromdate1+"'";
            }
            if (StringUtils.isNotBlank(lenddate1) && !"null".equals(lenddate1)) {
                sqlfrom += " and b.BTRENDDA <='"+lenddate1+"'";
            }
            sqlfrom +=") a";
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid desc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("requestid").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("100px", "公司代码", "BtrBukrs","BtrBukrs"));
            table.getColumns().add(new WeaTableColumn("100px", "报销凭证号", "BELNR","BELNR"));
            table.getColumns().add(new WeaTableColumn("100px", "财年", "GJAHR","GJAHR"));
            table.getColumns().add(new WeaTableColumn("100px", "期间", "qj","qj"));
            table.getColumns().add(new WeaTableColumn("100px", "记账日期", "BLDAT","BLDAT"));
            table.getColumns().add(new WeaTableColumn("100px", "出差人工号", "BTRWorkCode","BTRWorkCode"));
            table.getColumns().add(new WeaTableColumn("100px", "出差人姓名", "BtrPER","BtrPER"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅申请号", "TRAAPPLI","TRAAPPLI","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.getRequestUrl","column:sqdh"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅费用号", "bxdh","bxdh","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.getRequestUrl","column:sqdh"));
            table.getColumns().add(new WeaTableColumn("100px", "专项费用", "SPEPRO","SPEPRO"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅国内/外", "BTRCon","BTRCon"));
            table.getColumns().add(new WeaTableColumn("100px", "出差动因", "BTRReasonsDet","BTRReasonsDet"));
            table.getColumns().add(new WeaTableColumn("100px", "出差片区", "BTRRegion","BTRRegion"));
            table.getColumns().add(new WeaTableColumn("100px", "出差事由及安排", "ASSIGNMENT","ASSIGNMENT"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅开始日期", "BTRBEGDA","BTRBEGDA"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅结束日期", "BTRENDDA","BTRENDDA"));
            table.getColumns().add(new WeaTableColumn("100px", "差旅天数", "BTRDAYS","BTRDAYS"));
            table.getColumns().add(new WeaTableColumn("100px", "成本中心", "COSTCENTER","COSTCENTER"));
            table.getColumns().add(new WeaTableColumn("100px", "基金中心", "FundCenterCODE","FundCenterCODE"));
            table.getColumns().add(new WeaTableColumn("100px", "膳杂费", "MEEXP","MEEXP","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "住宿费", "HoAmo","HoAmo","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "交通费", "OthTraCost","OthTraCost","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "机票费", "AirAmo","AirAmo","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "其他费用", "OthCost","OthCost","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "会计调整膳杂", "AccMEEXPB","AccMEEXPB","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "会计调整住宿", "AccHoAmoB","AccHoAmoB","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "会计调整交通", "AccOthTraCostB","AccOthTraCostB","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "会计调整其他", "AccOthCostB","AccOthCostB","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "超标金额", "ExcAmo","ExcAmo","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("200px", "超标说明", "ECEDES","ECEDES"));
            table.getColumns().add(new WeaTableColumn("100px", "总成本", "TolCos","TolCos","APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableTransMethod.fundFormat"));
            table.getColumns().add(new WeaTableColumn("100px", "流程状态", "WFStatus","WFStatus"));


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
