package test;

import weaver.workflow.webservices.*;

/**
 * createby jianyong.tang
 * createTime 2020/3/13 9:08
 * version v1
 * desc
 */
public class TestCreateRequest {
    public  String Createtest() throws Exception {

        WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();//工作流程请求信息

        int userid = 1;//用户ID-赵晓燕
//		workflowRequestInfo.setRequestId(String.valueOf(1918557));//流程请求ID-创建流程时自动产生
        workflowRequestInfo.setCanView(true);//显示
        workflowRequestInfo.setCanEdit(true);//可编辑
        workflowRequestInfo.setRequestName("供应商信息收集流程test");//请求标题
        workflowRequestInfo.setRequestLevel("0");//请求重要级别
        workflowRequestInfo.setCreatorId("1");

        WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();//工作流信息
        workflowBaseInfo.setWorkflowId("11");//流程ID
        workflowBaseInfo.setWorkflowName("供应商信息收集流程");//流程名称
//		workflowBaseInfo.setWorkflowTypeId("1951");//流程类型id
        workflowBaseInfo.setWorkflowTypeName("供应商管理");//流程类型名称
        workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);//工作流信息



        /****************main table start*************/
        WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();//主表
        WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowRequestTableRecord[1];//主表字段只有一条记录
        WorkflowRequestTableField[] WorkflowRequestTableField = new WorkflowRequestTableField[6];//主的4个字段

        WorkflowRequestTableField[0] = new WorkflowRequestTableField();
        WorkflowRequestTableField[0].setFieldName("sqr");//姓名
        WorkflowRequestTableField[0].setFieldValue("1");//被留言人字段的值，111为被留言人id
        WorkflowRequestTableField[0].setView(true);//字段是否可见
        WorkflowRequestTableField[0].setEdit(true);//字段是否可编辑

        WorkflowRequestTableField[1] = new WorkflowRequestTableField();
        WorkflowRequestTableField[1].setFieldName("sqrq");//部门
        WorkflowRequestTableField[1].setFieldValue("2020-03-13");
        WorkflowRequestTableField[1].setView(true);
        WorkflowRequestTableField[1].setEdit(true);

        WorkflowRequestTableField[2] = new WorkflowRequestTableField();
        WorkflowRequestTableField[2].setFieldName("gysmc");//部门
        WorkflowRequestTableField[2].setFieldValue("1234");
        WorkflowRequestTableField[2].setView(true);
        WorkflowRequestTableField[2].setEdit(true);

        WorkflowRequestTableField[3] = new WorkflowRequestTableField();
        WorkflowRequestTableField[3].setFieldName("lxryx");//文档
        WorkflowRequestTableField[3].setFieldValue("1111111");
        WorkflowRequestTableField[3].setView(true);
        WorkflowRequestTableField[3].setEdit(true);

        WorkflowRequestTableField[4] = new WorkflowRequestTableField();
        WorkflowRequestTableField[4].setFieldName("bz");//备注
        WorkflowRequestTableField[4].setFieldValue("11111");
        WorkflowRequestTableField[4].setView(true);
        WorkflowRequestTableField[4].setEdit(true);

        WorkflowRequestTableField[5] = new WorkflowRequestTableField();
        WorkflowRequestTableField[5].setFieldName("djbh");//附件
        WorkflowRequestTableField[5].setFieldType("123456");//http:开头代表该字段为附件字段		wrti[5].setFieldValue("http://www.baidu.com/img/baidu_sylogo1.gif");//附件地址
        WorkflowRequestTableField[5].setView(true);
        WorkflowRequestTableField[5].setEdit(true);

        workflowRequestTableRecord[0] = new WorkflowRequestTableRecord();
        workflowRequestTableRecord[0].setWorkflowRequestTableFields(WorkflowRequestTableField);
        workflowMainTableInfo.setRequestRecords(workflowRequestTableRecord);

        workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
        /****************main table end*************/


        /****************detail table start*************/
        WorkflowDetailTableInfo[] workflowDetailTableInfo = new WorkflowDetailTableInfo[0];//两个明细表


        workflowRequestInfo.setWorkflowDetailTableInfos(workflowDetailTableInfo);
        /****************detail table end*************/

//		String response = ClientUtil.getClient().submitWorkflowRequest(workflowRequestInfo, requestid, userid, type, remark);

//		if(!"".equals(response)&&response!=null)
//		System.out.println("返回结果："+response);
//		else
//		System.out.println("返回结果为空");
        String response = new WorkflowServiceImpl().doCreateWorkflowRequest(workflowRequestInfo, userid);
       return response;
    }

}
