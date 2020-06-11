package xhgz.report.cbhsmx.CMD;

import com.api.browser.bean.SearchConditionGroup;
import com.api.browser.bean.SearchConditionItem;
import com.api.browser.bean.SearchConditionOption;
import com.api.browser.util.ConditionFactory;
import com.api.browser.util.ConditionType;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
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





        SearchConditionItem startDate = conditionFactory.createCondition(ConditionType.DATE, 722, new String[]{"startDate", "fromdate", "lenddate"});
        startDate.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        startDate.setFieldcol(16);    //条件输入框所占宽度，默认值18
        startDate.setLabelcol(8);
        startDate.setViewAttr(2);  //  编辑权限  1：只读，2：可编辑， 3：必填   默认2
        startDate.setLabel("接案日期"); //设置文本值  这个将覆盖多语言标签的值
        List<SearchConditionOption> selectOptions = new ArrayList <>(); //设置选项值
        selectOptions.add(new SearchConditionOption("0", SystemEnv.getHtmlLabelName(332, this.user.getLanguage()), true));
        selectOptions.add(new SearchConditionOption("1", SystemEnv.getHtmlLabelName(15537, this.user.getLanguage())));
        selectOptions.add(new SearchConditionOption("2", SystemEnv.getHtmlLabelName(15539, this.user.getLanguage())));
        selectOptions.add(new SearchConditionOption("3", SystemEnv.getHtmlLabelName(15541, this.user.getLanguage())));
        selectOptions.add(new SearchConditionOption("4", SystemEnv.getHtmlLabelName(21904, this.user.getLanguage())));
        selectOptions.add(new SearchConditionOption("5", SystemEnv.getHtmlLabelName(15384, this.user.getLanguage())));
        selectOptions.add(new SearchConditionOption("6", SystemEnv.getHtmlLabelName(32530, this.user.getLanguage())));

        startDate.setOptions(selectOptions);
        conditionItems.add(startDate);






        addGroups.add(new SearchConditionGroup("",true,conditionItems));

        apidatas.put("condition",addGroups);

        return apidatas;

    }
}
