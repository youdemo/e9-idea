package APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD;

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

public class WeaTableCmd20200509 extends AbstractCommonCommand<Map<String,Object>> {
    public WeaTableCmd20200509(User user, Map<String,Object> params) {
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
            String pageID = "13377be9-f66b-424e-8785-fec3bfbb66fb";
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

            String fileds = " requestid,sqdh,BTRWorkCode,(select lastname from hrmresource where id=a.BtrPER) as BtrPER,(select DEPARTMENTNAME from HRMDEPARTMENT where id=a.BtrDEP) as BtrDEP,BTRBEGDA,BTRENDDA,BTRBEGDA||' '||BTRBEGTIME as kssj,BTRENDDA||' '||BTRENDTIME as jssj,TavelDestCityALL,sqspzt,bxspzt,poststatus,bxdh,(select description from uf_wfstatus where code=a.WFStatus) as WFStatus ";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 差率单号
//            String requestid =  Util.null2String(params.get("requestid"));
//            if (StringUtils.isNotBlank(requestid)) {
//                sqlwhere += " and requestid like '%" + requestid + "%' ";
//            }

            String sqlfrom = " (select b.requestid,b.rqid as sqdh,b.BTRWorkCode,b.BtrPER,b.BtrDEP,b.BTRBEGDA,b.BTRENDDA,b.BTRBEGTIME,b.BTRENDTIME,b.TavelDestCityALL,case when a.CURRENTNODETYPE=3 then '已审核' else '已提交' end sqspzt," +
                    " case (select CURRENTNODETYPE from WORKFLOW_REQUESTBASE where REQUESTID=c.rqid) when '0' then '未提交' when '1' then '已提交' when '2' then '已提交' when '3' then '已审核' else '' end as bxspzt,c.poststatus,c.rqid as bxdh,b.WFStatus" +
                    " from WORKFLOW_REQUESTBASE a join "+tablesq+" b on a.REQUESTID=b.REQUESTID and a.CURRENTNODETYPE>0 left join "+tablebx+" c on b.REQUESTID=c.TRAAPPLI where 1=1 ";
            String workcode =  Util.null2String(params.get("workcode"));
            String cxry =  Util.null2String(params.get("cxry"));
            if("".equals(workcode)&&"".equals(cxry)){
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

            if (StringUtils.isNotBlank(workcode)) {
                sqlfrom += " and b.BTRWorkCode = '"+workcode+"'";
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
                sqlfrom += " and b.BTRENDDA <='"+lenddate+"'";
            }
            sqlfrom +=") a";
            //new BaseBean().writeLog("TRAVELQUERYaaa","select "+fileds+" from "+sqlfrom);
            table.setSqlform(sqlfrom);
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("requestid desc");
            table.setSqlprimarykey("requestid");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("requestid").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("7%", "申请单", "sqdh","sqdh","APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD.WeaTableTransMethod.getRequestUrl","column:sqdh"));
            table.getColumns().add(new WeaTableColumn("7%", "员工编号", "BTRWorkCode","BTRWorkCode"));
            table.getColumns().add(new WeaTableColumn("7%", "姓名", "BtrPER","BtrPER"));
            table.getColumns().add(new WeaTableColumn("7%", "部门", "BtrDEP","BtrDEP"));
            table.getColumns().add(new WeaTableColumn("10%", "开始时间", "kssj","kssj"));
            table.getColumns().add(new WeaTableColumn("10%", "结束时间", "jssj","jssj"));
            table.getColumns().add(new WeaTableColumn("9%", "第一目的地", "TavelDestCityALL","TavelDestCityALL"));
            table.getColumns().add(new WeaTableColumn("9%", "申请审批状态", "sqspzt","sqspzt"));
            table.getColumns().add(new WeaTableColumn("9%", "报销审批状态", "bxspzt","bxspzt"));
            table.getColumns().add(new WeaTableColumn("9%", "会计过账状态", "poststatus","poststatus"));
            table.getColumns().add(new WeaTableColumn("9%", "费用报销单号", "bxdh","bxdh","APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD.WeaTableTransMethod.getRequestUrl","column:bxdh"));
            table.getColumns().add(new WeaTableColumn("8%", "流程状态", "WFStatus","WFStatus"));
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
