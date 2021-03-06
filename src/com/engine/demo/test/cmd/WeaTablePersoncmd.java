package com.engine.demo.test.cmd;

import com.cloudstore.eccom.constant.WeaBoolAttr;
import com.cloudstore.eccom.pc.table.*;
import com.cloudstore.eccom.result.WeaResultMsg;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import org.apache.commons.lang.StringUtils;
import weaver.general.BaseBean;
import weaver.general.PageIdConst;
import weaver.general.Util;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaTablePersoncmd extends AbstractCommonCommand<Map<String,Object>> {
    public WeaTablePersoncmd(User user, Map<String,Object> params) {
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

            String pageID = "0d4f39a5-804d-4578-8a73-aad48490f611";
            String pageUid = pageID + "_" + user.getUID();
            String pageSize = PageIdConst.getPageSize(pageID, user.getUID());
            String sqlwhere = " 1=1 ";

            //新建一个weatable
            WeaTable table = new WeaTable();
            table.setPageUID(pageUid);
            table.setPageID(pageID);
            table.setPagesize(pageSize);

            String fileds = " id,lastname,sex,workcode,departmentid,subcompanyid1,(select subcompanyname from hrmsubcompany where id=a.subcompanyid1)  as subcompanyname";
            table.setBackfields(fileds);

            //搜索条件,这里可以放高级搜索的的条件
            String workcode =  Util.null2String(params.get("workcode"));
            if (StringUtils.isNotBlank(workcode)) {
                sqlwhere += " and workcode like '%" + workcode + "%' ";
            }

            //创建人
            String ry =  Util.null2String(params.get("ry"));
            if (StringUtils.isNotBlank(ry)) {
                sqlwhere += " and id='"+ry+"' ";
            }

            //性别
            String sex =  Util.null2String(params.get("sex"));
            if (StringUtils.isNotBlank(sex)) {
                sqlwhere += " and sex = '" + sex + "' ";
            }

            //类型
            String bm =  Util.null2String(params.get("bm"));
            if (StringUtils.isNotBlank(bm) ) {
                sqlwhere += " and departmentid = '" + bm + "' ";
            }

            String fb =  Util.null2String(params.get("fb"));
            if(StringUtils.isNotBlank(fb)){
                sqlwhere += " and subcompanyid1 = '" + fb + "' ";
            }

            table.setSqlform(" hrmresource a ");
            table.setSqlwhere(sqlwhere);
            table.setSqlorderby("id");
            table.setSqlprimarykey("id");
            table.setSqlisdistinct("false");
            table.getColumns().add(new WeaTableColumn("id").setDisplay(WeaBoolAttr.FALSE));   //设置为不显示
            table.getColumns().add(new WeaTableColumn("20%", "姓名", "lastname","lastment"));
            table.getColumns().add(new WeaTableColumn("20%", "性别", "sex","sex","com.engine.demo.test.cmd.WeaTableTransMethod.getSex"));
            table.getColumns().add(new WeaTableColumn("20%", "工号", "workcode","workcode"));
            table.getColumns().add(new WeaTableColumn("20%", "部门", "departmentid","departmentid","weaver.hrm.company.DepartmentComInfo.getDepartmentname"));
            table.getColumns().add(new WeaTableColumn("20%", "分部", "subcompanyname","subcompanyname"));
            new BaseBean().writeLog("aaaaa:姓名");
            //设置左侧check默认不存在
            //table.setCheckboxList(null);
            //table.setCheckboxpopedom(null);
            List<WeaTableCheckboxpopedom> checkboxpopedomList = new ArrayList<>();
            //WeaTableCheckboxpopedom checkboxpopedom = new WeaTableCheckboxpopedom();
            //checkboxpopedom.setPopedompara("column:id");
            //checkboxpopedom.setShowmethod("com.engine.demo.test.cmd.WeaTableTransMethod.showmethod");
            //checkboxpopedom.setId("checkbox");
            //checkboxpopedomList.add(checkboxpopedom);
            table.getTableType().setName("checkbox");
            table.setCheckboxList(checkboxpopedomList);


            //增加右侧操作选项
//            WeaTableOperates weaTableOperates = new WeaTableOperates();
//            List<WeaTableOperate> operateList = new ArrayList<>();
//
//            WeaTableOperate edit = new WeaTableOperate("编辑","","0");
//            WeaTableOperate delete = new WeaTableOperate("删除","","1");
//            operateList.add(edit);
//            operateList.add(delete);
//            weaTableOperates.setOperate(operateList);
//            table.setOperates(weaTableOperates);

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
