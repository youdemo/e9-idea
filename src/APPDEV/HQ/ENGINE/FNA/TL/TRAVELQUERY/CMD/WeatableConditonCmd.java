package APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD;

import com.api.browser.bean.SearchConditionGroup;
import com.api.browser.bean.SearchConditionItem;
import com.api.browser.bean.SearchConditionOption;
import com.api.browser.util.BrowserBaseUtil;
import com.api.browser.util.ConditionFactory;
import com.api.browser.util.ConditionType;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;
import weaver.systeminfo.SystemEnv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeatableConditonCmd extends AbstractCommonCommand<Map<String,Object>> {

    public WeatableConditonCmd(User user, Map<String,Object> params) {
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
//        if(!HrmUserVarify.checkUserRight("LogView:View", user)){
//            apidatas.put("hasRight", false);
//            return apidatas;
//        }

        apidatas.put("hasRight", true);

        ConditionFactory conditionFactory = new ConditionFactory(user);

        //条件组
        List<SearchConditionGroup> addGroups = new ArrayList<SearchConditionGroup>();

        List<SearchConditionItem> conditionItems = new ArrayList<SearchConditionItem>();

        SearchConditionItem cxry = conditionFactory.createCondition(ConditionType.BROWSER,502327, "cxry","17");
        cxry.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        cxry.setFieldcol(16); //条件输入框所占宽度，默认值18
        cxry.setLabelcol(8);
        cxry.setViewAttr(2); // 编辑权限 1：只读，2：可编辑， 3：必填 默认2
        cxry.setLabel("查询人员"); //设置文本值 这个将覆盖多语言标签的值
        conditionItems.add(cxry);

        SearchConditionItem workcode = conditionFactory.createCondition(ConditionType.INPUT,502327, "workcode");
        workcode.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        workcode.setFieldcol(16); //条件输入框所占宽度，默认值18
        workcode.setLabelcol(8);
        workcode.setViewAttr(2); // 编辑权限 1：只读，2：可编辑， 3：必填 默认2
        workcode.setLabel("工号"); //设置文本值 这个将覆盖多语言标签的值
        conditionItems.add(workcode);



        SearchConditionItem startDate = conditionFactory.createCondition(ConditionType.DATE, 722, new String[]{"startDate", "fromdate", "lenddate"});
        startDate.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        startDate.setFieldcol(16);    //条件输入框所占宽度，默认值18
        startDate.setLabelcol(8);
        startDate.setViewAttr(2);  //  编辑权限  1：只读，2：可编辑， 3：必填   默认2
        startDate.setLabel("日期范围"); //设置文本值  这个将覆盖多语言标签的值
        List<SearchConditionOption> selectOptions = new ArrayList <>(); //设置选项值
        selectOptions.add(new SearchConditionOption("6", "日期范围选择",true));
        startDate.setOptions(selectOptions);
        conditionItems.add(startDate);

        SearchConditionItem wfstatus = conditionFactory.createCondition(ConditionType.BROWSER, "502327", "WFStatus", "161",1,"WF_STATUS");
        wfstatus.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        wfstatus.setFieldcol(16);	//条件输入框所占宽度，默认值18
        wfstatus.setLabelcol(8);
        wfstatus.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        wfstatus.setLabel("流程状态"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(wfstatus);




        addGroups.add(new SearchConditionGroup("",true,conditionItems));

        apidatas.put("condition",addGroups);

        return apidatas;

    }
}
