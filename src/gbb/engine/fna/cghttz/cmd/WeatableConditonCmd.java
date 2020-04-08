package gbb.engine.fna.cghttz.cmd;

import com.api.browser.bean.SearchConditionGroup;
import com.api.browser.bean.SearchConditionItem;
import com.api.browser.bean.SearchConditionOption;
import com.api.browser.util.ConditionFactory;
import com.api.browser.util.ConditionType;
import com.engine.common.biz.AbstractCommonCommand;
import com.engine.common.entity.BizLogContext;
import com.engine.core.interceptor.CommandContext;
import weaver.hrm.HrmUserVarify;
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
        if(!HrmUserVarify.checkUserRight("LogView:View", user)){
            apidatas.put("hasRight", false);
            return apidatas;
        }

        apidatas.put("hasRight", true);

        ConditionFactory conditionFactory = new ConditionFactory(user);

        //条件组
        List<SearchConditionGroup> addGroups = new ArrayList<SearchConditionGroup>();

        List<SearchConditionItem> conditionItems = new ArrayList<SearchConditionItem>();

        //文本输入框
        SearchConditionItem htmc = conditionFactory.createCondition(ConditionType.INPUT,502327, "htmc");
        htmc.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        htmc.setFieldcol(16);	//条件输入框所占宽度，默认值18
        htmc.setLabelcol(8);
        htmc.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        htmc.setLabel("合同名称"); //设置文本值  这个将覆盖多语言标签的值
        //workcode.setRules("required");
       // workcode.setRules("required");
       // workcode.setIsQuickSearch(true);
        conditionItems.add(htmc);


        //文本输入框
        SearchConditionItem htbh = conditionFactory.createCondition(ConditionType.INPUT,502327, "htbh");
        htbh.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        htbh.setFieldcol(16);	//条件输入框所占宽度，默认值18
        htbh.setLabelcol(8);
        htbh.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        htbh.setLabel("合同编号"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(htbh);

        SearchConditionItem htxdf = conditionFactory.createCondition(ConditionType.BROWSER, "502327", "htxdf", "161",1,"gyslb");
        htxdf.setColSpan(2);//定义一行显示条件数，默认值为2,当值为1时标识该条件单独占一行
        htxdf.setFieldcol(16);	//条件输入框所占宽度，默认值18
        htxdf.setLabelcol(8);
        htxdf.setViewAttr(2);  //	 编辑权限  1：只读，2：可编辑， 3：必填   默认2
        htxdf.setLabel("合同相对方"); //设置文本值  这个将覆盖多语言标签的值
        conditionItems.add(htxdf);





        addGroups.add(new SearchConditionGroup("",true,conditionItems));

        apidatas.put("condition",addGroups);

        return apidatas;

    }
}
