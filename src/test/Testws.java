package test;

import org.apache.axis2.AxisFault;
import test.service.WorkflowServiceStub;

/**
 * createby jianyong.tang
 * createTime 2020/3/17 10:19
 * version v1
 * desc
 */
public class Testws {
    public static void main(String[] args) throws Exception {
        System.out.println(dows());
    }

    public static String dows() throws Exception {
        String result = "";
        WorkflowServiceStub wss = new WorkflowServiceStub();
        WorkflowServiceStub.WorkflowRequestInfo workflowRequestInfo = new WorkflowServiceStub.WorkflowRequestInfo();
        int userid = 76;//用户ID-赵晓燕
//		workflowRequestInfo.setRequestId(String.valueOf(1918557));//流程请求ID-创建流程时自动产生
        workflowRequestInfo.setCanView(true);//显示
        workflowRequestInfo.setCanEdit(true);//可编辑
        workflowRequestInfo.setRequestName("测试流程");//请求标题
        workflowRequestInfo.setRequestLevel("0");//请求重要级别
        workflowRequestInfo.setCreatorId("76");

        WorkflowServiceStub.WorkflowBaseInfo workflowBaseInfo = new WorkflowServiceStub.WorkflowBaseInfo();//工作流信息
        workflowBaseInfo.setWorkflowId("20");//流程ID
        workflowBaseInfo.setWorkflowName("测试流程");//流程名称
//		workflowBaseInfo.setWorkflowTypeId("1951");//流程类型id
        workflowBaseInfo.setWorkflowTypeName("测试流程");//流程类型名称
        workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);//工作流信息



        /****************main table start*************/
        WorkflowServiceStub.WorkflowMainTableInfo workflowMainTableInfo = new WorkflowServiceStub.WorkflowMainTableInfo();//主表
        WorkflowServiceStub.WorkflowRequestTableRecord[] workflowRequestTableRecord = new WorkflowServiceStub.WorkflowRequestTableRecord[1];//主表字段只有一条记录
        WorkflowServiceStub.WorkflowRequestTableField[] WorkflowRequestTableField = new  WorkflowServiceStub.WorkflowRequestTableField[5];//主的4个字段
        WorkflowServiceStub.ArrayOfWorkflowRequestTableField WorkflowRequestTableFieldarr = new WorkflowServiceStub.ArrayOfWorkflowRequestTableField();

        WorkflowRequestTableField[0] = new WorkflowServiceStub.WorkflowRequestTableField();
        WorkflowRequestTableField[0].setFieldName("gd");//姓名
        WorkflowRequestTableField[0].setFieldValue("test");//被留言人字段的值，111为被留言人id
        WorkflowRequestTableField[0].setView(true);//字段是否可见
        WorkflowRequestTableField[0].setEdit(true);//字段是否可编辑

        WorkflowRequestTableField[1] = new WorkflowServiceStub.WorkflowRequestTableField();
        WorkflowRequestTableField[1].setFieldName("ccpwlh");//部门
        WorkflowRequestTableField[1].setFieldValue("123");
        WorkflowRequestTableField[1].setView(true);
        WorkflowRequestTableField[1].setEdit(true);

        WorkflowRequestTableField[2] = new WorkflowServiceStub.WorkflowRequestTableField();
        WorkflowRequestTableField[2].setFieldName("sj");//部门
        WorkflowRequestTableField[2].setFieldValue("1234");
        WorkflowRequestTableField[2].setView(true);
        WorkflowRequestTableField[2].setEdit(true);

        WorkflowRequestTableField[3] = new WorkflowServiceStub.WorkflowRequestTableField();
        WorkflowRequestTableField[3].setFieldName("gx");//文档
        WorkflowRequestTableField[3].setFieldValue("1111111");
        WorkflowRequestTableField[3].setView(true);
        WorkflowRequestTableField[3].setEdit(true);

        WorkflowRequestTableField[4] = new WorkflowServiceStub.WorkflowRequestTableField();
        WorkflowRequestTableField[4].setFieldName("xw");//备注
        WorkflowRequestTableField[4].setFieldValue("11111");
        WorkflowRequestTableField[4].setView(true);
        WorkflowRequestTableField[4].setEdit(true);



        workflowRequestTableRecord[0] = new WorkflowServiceStub.WorkflowRequestTableRecord();
        WorkflowRequestTableFieldarr.setWorkflowRequestTableField(WorkflowRequestTableField);
        workflowRequestTableRecord[0].setWorkflowRequestTableFields(WorkflowRequestTableFieldarr);
        WorkflowServiceStub.ArrayOfWorkflowRequestTableRecord  ArrayOfWorkflowRequestTableRecord = new WorkflowServiceStub.ArrayOfWorkflowRequestTableRecord();
        ArrayOfWorkflowRequestTableRecord.setWorkflowRequestTableRecord(workflowRequestTableRecord);
        workflowMainTableInfo.setRequestRecords(ArrayOfWorkflowRequestTableRecord);

        workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
        /****************main table end*************/


        /****************detail table start*************/
        WorkflowServiceStub.WorkflowDetailTableInfo[] workflowDetailTableInfo = new WorkflowServiceStub.WorkflowDetailTableInfo[0];//两个明细表

        WorkflowServiceStub.ArrayOfWorkflowDetailTableInfo ArrayOfWorkflowDetailTableInfo = new WorkflowServiceStub.ArrayOfWorkflowDetailTableInfo();
        ArrayOfWorkflowDetailTableInfo.setWorkflowDetailTableInfo(workflowDetailTableInfo);
        workflowRequestInfo.setWorkflowDetailTableInfos(ArrayOfWorkflowDetailTableInfo);
        WorkflowServiceStub.DoCreateWorkflowRequest dr = new WorkflowServiceStub.DoCreateWorkflowRequest();
        dr.setIn0(workflowRequestInfo);
        dr.setIn1(76);
        WorkflowServiceStub.DoCreateWorkflowRequestResponse ds= wss.doCreateWorkflowRequest(dr);
        result=ds.getOut();
        return result;
    }
}
