package gbb.engine.fna.cghttz.cmd;

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

import java.util.HashMap;
import java.util.Map;

public class WeaTableCmd20200326 extends AbstractCommonCommand<Map<String,Object>> {
    public WeaTableCmd20200326(User user, Map<String,Object> params) {
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

            String pageID = "0e1e3809-27fa-4d74-86ae-2a7336e00c05";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";

            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " id,htmc,htbh,cgsqname,cgsq,htxdf,htje,spzje,yfkje,wfkje  ";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件 合同名称
            String htmc =  Util.null2String(params.get("htmc"));
            if (StringUtils.isNotBlank(htmc)) {
                sqlwhere += " and htmc like '%" + htmc + "%' ";
            }

            //合同编号
            String htbh =  Util.null2String(params.get("htbh"));
            if (StringUtils.isNotBlank(htbh)) {
                sqlwhere += " and htbh like '%" + htbh + "%' ";
            }

            //合同相对方
            String htxdf =  Util.null2String(params.get("htxdf"));
            if (StringUtils.isNotBlank(htxdf)) {
                sqlwhere += " and htxdf like '%" + htxdf + "%' ";
            }





            table.setSqlform(" (select id,htmc,htbh,(select requestname from workflow_requestbase where requestid=a.cgsq ) as cgsqname,cgsq,htxdf,cast(htje as numeric(10,2)) as htje,cast((select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(1,2)and t.htmc=a.cghtrqid ) as numeric(10,2)) as spzje,cast((select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(3)and t.htmc=a.cghtrqid ) as numeric(10,2)) as yfkje,cast((htje-(select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(1,2,3)and t.htmc=a.cghtrqid )) as numeric(10,2)) as wfkje from uf_cghttz a) a ");
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("id");
            table.setSqlprimarykey("id");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("requestid").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("10%", "合同名称", "htmc","htmc"));
            table.getColumns().add(new WeaTableColumn("10%", "合同编号", "htbh","htbh"));
            table.getColumns().add(new WeaTableColumn("20%", "采购申请", "cgsqname","cgsqname","gbb.engine.fna.cghttz.cmd.WeaTableTransMethod.getRequestUrl","column:cgsq"));
            table.getColumns().add(new WeaTableColumn("20%", "合同相对方", "htxdf","htxdf"));
            table.getColumns().add(new WeaTableColumn("10%", "合同金额", "htje","htje"));
            table.getColumns().add(new WeaTableColumn("10%", "审批中金额", "spzje","spzje"));
            table.getColumns().add(new WeaTableColumn("10%", "已付款金额", "yfkje","yfkje"));
            table.getColumns().add(new WeaTableColumn("10%", "未付款金额", "wfkje","wfkje"));

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
