package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD;

import com.api.browser.bean.SearchConditionGroup;
import com.api.browser.bean.SearchConditionItem;
import com.api.browser.bean.SearchConditionOption;
import com.api.browser.util.ConditionFactory;
import com.api.browser.util.ConditionType;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import weaver.hrm.User;

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

        //文本输入框
        SearchConditionItem requestid = conditionFactory.createCondition(ConditionType.INPUT,502327, "requestid");
        requestid.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        requestid.setFieldcol(16);	//条件输入框所占宽度，默认值18
        requestid.setLabelcol(8);
        requestid.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        requestid.setLabel("差旅单号"); //设置文本值  这个将覆盖多语言标签的值
        //workcode.setRules("required");
        // workcode.setRules("required");
        // workcode.setIsQuickSearch(true);
        conditionItems.add(requestid);

        SearchConditionItem ccr = conditionFactory.createCondition(ConditionType.BROWSER,502327, "ccr","17");
        ccr.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        ccr.setFieldcol(16); //条件输入框所占宽度，默认值18
        ccr.setLabelcol(8);
        ccr.setViewAttr(2); // 编辑权限 1：只读，2：可编辑， 3：必填 默认2
        ccr.setLabel("出差人"); //设置文本值 这个将覆盖多语言标签的值
        conditionItems.add(ccr);

        SearchConditionItem costcenter = conditionFactory.createCondition(ConditionType.INPUT,502327, "costcenter");
        costcenter.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        costcenter.setFieldcol(16);	//条件输入框所占宽度，默认值18
        costcenter.setLabelcol(8);
        costcenter.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        costcenter.setLabel("成本中心"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(costcenter);
        //文本输入框
        SearchConditionItem rzgsbm = conditionFactory.createCondition(ConditionType.BROWSER, "502327", "rzgsbm", "162",1,"travel_dzgsdm");
        rzgsbm.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        rzgsbm.setFieldcol(16);	//条件输入框所占宽度，默认值18
        rzgsbm.setLabelcol(8);
        rzgsbm.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        rzgsbm.setLabel("入账公司编码"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(rzgsbm);



        //下拉选择框类
        SearchConditionItem sfbx = conditionFactory.createCondition(ConditionType.SELECT,502327,"sfbx");
        List<SearchConditionOption> selectOptions = new ArrayList <>();  //设置选项值
        selectOptions.add(new SearchConditionOption("","",true));
        selectOptions.add(new SearchConditionOption("是","是"));
        selectOptions.add(new SearchConditionOption("否","否"));
        sfbx.setOptions(selectOptions);
        sfbx.setColSpan(2);
        sfbx.setFieldcol(16);
        sfbx.setLabelcol(8);
        sfbx.setLabel("是否报销");
        conditionItems.add(sfbx);

        SearchConditionItem wfstatus = conditionFactory.createCondition(ConditionType.BROWSER, "502327", "WFStatus", "161",1,"fna_wfstatus");
        wfstatus.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        wfstatus.setFieldcol(16);	//条件输入框所占宽度，默认值18
        wfstatus.setLabelcol(8);
        wfstatus.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        wfstatus.setLabel("差旅状态"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(wfstatus);

        SearchConditionItem year = conditionFactory.createCondition(ConditionType.BROWSER,502327, "year","178");
        year.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        year.setFieldcol(16);	//条件输入框所占宽度，默认值18
        year.setLabelcol(8);
        year.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        year.setLabel("年份"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(year);

        SearchConditionItem month = conditionFactory.createCondition(ConditionType.SELECT,502327,"month");
        List<SearchConditionOption> selectOptionsmonth = new ArrayList <>();  //设置选项值
        selectOptionsmonth.add(new SearchConditionOption("","",true));
        selectOptionsmonth.add(new SearchConditionOption("01","一月"));
        selectOptionsmonth.add(new SearchConditionOption("02","二月"));
        selectOptionsmonth.add(new SearchConditionOption("03","三月"));
        selectOptionsmonth.add(new SearchConditionOption("04","四月"));
        selectOptionsmonth.add(new SearchConditionOption("05","五月"));
        selectOptionsmonth.add(new SearchConditionOption("06","六月"));
        selectOptionsmonth.add(new SearchConditionOption("07","七月"));
        selectOptionsmonth.add(new SearchConditionOption("08","八月"));
        selectOptionsmonth.add(new SearchConditionOption("09","九月"));
        selectOptionsmonth.add(new SearchConditionOption("10","十月"));
        selectOptionsmonth.add(new SearchConditionOption("11","十一月"));
        selectOptionsmonth.add(new SearchConditionOption("12","十二月"));
        month.setOptions(selectOptionsmonth);
        month.setColSpan(2);
        month.setFieldcol(16);
        month.setLabelcol(8);
        month.setLabel("月份");
        conditionItems.add(month);

        SearchConditionItem hasrefund = conditionFactory.createCondition(ConditionType.SELECT,502327,"hasrefund");
        List<SearchConditionOption> selectOptionshasrefund = new ArrayList <>();  //设置选项值
        selectOptionshasrefund.add(new SearchConditionOption("","",true));
        selectOptionshasrefund.add(new SearchConditionOption("0","有退票费"));
        selectOptionshasrefund.add(new SearchConditionOption("1","无退票费"));

        hasrefund.setOptions(selectOptionshasrefund);
        hasrefund.setColSpan(2);
        hasrefund.setFieldcol(16);
        hasrefund.setLabelcol(8);
        hasrefund.setLabel("有无退票费");
        conditionItems.add(hasrefund);

        SearchConditionItem tpStatus = conditionFactory.createCondition(ConditionType.BROWSER, "502327", "tpStatus", "161",1,"fna_wfstatus1");
        tpStatus.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        tpStatus.setFieldcol(16);	//条件输入框所占宽度，默认值18
        tpStatus.setLabelcol(8);
        tpStatus.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        tpStatus.setLabel("退票审批状态"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(tpStatus);




        addGroups.add(new SearchConditionGroup("",true,conditionItems));

        apidatas.put("condition",addGroups);

        return apidatas;

    }
}
