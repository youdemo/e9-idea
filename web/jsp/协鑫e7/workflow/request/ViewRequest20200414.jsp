<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="weaver.general.Util,java.util.*,weaver.conn.*" %>
<%@ page import="weaver.general.AttachFileUtil" %>
<%@ page import="weaver.rtx.RTXConfig" %>
<%@ page import="weaver.file.Prop,weaver.general.GCONST" %>
<%@ include file="/systeminfo/init.jsp" %>
<%@ page import="weaver.worktask.worktask.*" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="RecordSet2" class="weaver.conn.RecordSet" scope="page" /> <%-- xwj for td2104 on 20050802--%>
<jsp:useBean id="RecordSet4" class="weaver.conn.RecordSet" scope="page" /> <%-- xwj for td2104 on 20050802--%>
<jsp:useBean id="RecordSet5" class="weaver.conn.RecordSet" scope="page" /> <%-- xwj for td3665 on 20060227--%>
<jsp:useBean id="FieldComInfo" class="weaver.workflow.field.FieldComInfo" scope="page" />
<jsp:useBean id="RequestTriDiffWfManager" class="weaver.workflow.request.RequestTriDiffWfManager" scope="page" /> 
<jsp:useBean id="WorkflowComInfo" class="weaver.workflow.workflow.WorkflowComInfo" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<jsp:useBean id="BrowserComInfo" class="weaver.workflow.field.BrowserComInfo" scope="page"/>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="CustomerInfoComInfo" class="weaver.crm.Maint.CustomerInfoComInfo" scope="page" />
<jsp:useBean id="DocImageManager" class="weaver.docs.docs.DocImageManager" scope="page" />
<jsp:useBean id="CoworkDAO" class="weaver.cowork.CoworkDAO" scope="page"/>
<jsp:useBean id="PoppupRemindInfoUtil" class="weaver.workflow.msg.PoppupRemindInfoUtil" scope="page"/><!--xwj for td3450 20060112-->
<jsp:useBean id="WfFunctionManageUtil" class="weaver.workflow.workflow.WfFunctionManageUtil" scope="page"/><!--xwj for td3665 20060224-->
<jsp:useBean id="WfForceOver" class="weaver.workflow.workflow.WfForceOver" scope="page" /><!--xwj for td3665 20060224-->
<jsp:useBean id="WfForceDrawBack" class="weaver.workflow.workflow.WfForceDrawBack" scope="page" /><!--xwj for td3665 20060224-->
<jsp:useBean id="flowDoc" class="weaver.workflow.request.RequestDoc" scope="page"/>
<jsp:useBean id="WFUrgerManager" class="weaver.workflow.request.WFUrgerManager" scope="page" />
<jsp:useBean id="WFManager" class="weaver.workflow.workflow.WFManager" scope="page"/>
<jsp:useBean id="WFLinkInfo" class="weaver.workflow.request.WFLinkInfo" scope="page"/>
<jsp:useBean id="RequestCheckUser" class="weaver.workflow.request.RequestCheckUser" scope="page"/>
<jsp:useBean id="SysWFLMonitor" class="weaver.system.SysWFLMonitor" scope="page"/>
<jsp:useBean id="sysInfo" class="weaver.system.SystemComInfo" scope="page"/>
<jsp:useBean id="WFForwardManager" class="weaver.workflow.request.WFForwardManager" scope="page" />
<jsp:useBean id="WFCoadjutantManager" class="weaver.workflow.request.WFCoadjutantManager" scope="page" />
<jsp:useBean id="WechatPropConfig" class="weaver.wechat.util.WechatPropConfig" scope="page"/>
<jsp:useBean id="CrmContractTypeUtil" class="weaver.crm.CrmContractTypeUtil" scope="page"/>
<%
boolean isfromtab =  Util.null2String(request.getParameter("isfromtab")).equals("true")?true:false;
boolean isUseOldWfMode = sysInfo.isUseOldWfMode();
String isworkflowhtmldoc=Util.null2String(request.getParameter("isworkflowhtmldoc"));
%>
<%----xwj for td3665 20060301 begin---%>
<%
String info = (String)request.getParameter("infoKey");
int isovertime= Util.getIntValue(request.getParameter("isovertime"),0) ;
int isonlyview = Util.getIntValue(request.getParameter("isonlyview"), 0);
%>
<script language="JavaScript">
<%if(isovertime==1){%>
        window.opener.location.href=window.opener.location.href;
<%}%>
<%if(info!=null && !"".equals(info)){

  if("ovfail".equals(info)){%>
 alert("<%=SystemEnv.getHtmlLabelName(18566,user.getLanguage())%>")
 <%}
 else if("rbfail".equals(info)){%>
 alert("<%=SystemEnv.getHtmlLabelName(18567,user.getLanguage())%>")
 <%}
 else{
 
 }
 }%>
</script>
<%----xwj for td3665 20060301 end---%>

<%
String isworkflowdoc = "0";//�Ƿ�Ϊ����
int seeflowdoc = Util.getIntValue(request.getParameter("seeflowdoc"),0);
boolean isnotprintmode =Util.null2String(request.getParameter("isnotprintmode")).equals("1")?true:false;
String reEdit=""+Util.getIntValue(request.getParameter("reEdit"),1);//�Ƿ�Ϊ�༭
int requestid=Util.getIntValue(request.getParameter("requestid"),0);
String wfdoc = Util.null2String((String)session.getAttribute(requestid+"_wfdoc"));
String isrequest = Util.null2String(request.getParameter("isrequest")); //
String nodetypedoc=Util.null2String(request.getParameter("nodetypedoc"));
int desrequestid=0;
int wflinkno=Util.getIntValue(request.getParameter("wflinkno"));
boolean isprint = Util.null2String(request.getParameter("isprint")).equals("1")?true:false;
String fromoperation=Util.null2String(request.getParameter("fromoperation"));

String fromPDA = Util.null2String((String)session.getAttribute("loginPAD"));   //��PDA��¼
//TD4262 ������ʾ��Ϣ  ��ʼ
String src=Util.null2String(request.getParameter("src"));//����ý���ǰ�Ĳ�����"submit"���ύ��"reject"���˻ء�
String isShowPrompt=Util.null2String(request.getParameter("isShowPrompt"));//�Ƿ���ʾ��ʾ  ȡֵ"true"����"false"
//TD4262 ������ʾ��Ϣ  ����

String requestname="";      //��������
String requestlevel="";     //������Ҫ���� 0:���� 1:��Ҫ 2:����
String requestmark = "" ;   //������
String isbill="0";          //�Ƿ񵥾� 0:�� 1:��
int creater=0;              //����Ĵ�����
int creatertype = 0;        //���������� 0: �ڲ��û� 1: �ⲿ�û�
int deleted=0;              //�����Ƿ�ɾ��  1:�� 0�������� ��
int billid=0 ;              //����ǵ���,��Ӧ�ĵ��ݱ��id
String isModifyLog = "";		//�Ƿ��¼����־ by cyril on 2008-07-09 for TD:8835
int workflowid=0;           //������id
String workflowtype = "" ;  //����������
int formid=0;               //�����ߵ��ݵ�id
int helpdocid = 0;          //�����ĵ� id
String workflowname = "" ;         //����������
String status = ""; //��ǰ�Ĳ�������
String docCategory="";//������Ŀ¼

int lastOperator=0; //��������id
String lastOperateDate="";//����������
String lastOperateTime="";//������ʱ��

int userid=user.getUID();                   //��ǰ�û�id
String logintype = user.getLogintype();     //��ǰ�û�����  1: ����û�  2:�ⲿ�û�
int usertype = 0;
if(logintype.equals("1")) usertype = 0;
if(logintype.equals("2")) usertype = 1;
int nodeid=WFLinkInfo.getCurrentNodeid(requestid,userid,Util.getIntValue(logintype,1));               //�ڵ�id
String nodetype=WFLinkInfo.getNodeType(nodeid);         //�ڵ�����  0:���� 1:���� 2:ʵ�� 3:�鵵

//��ǰ���������ڵ�id�����ͣ������ж��Ƿ���ǿ�ƹ鵵Ȩ��TD9023
String currentnodetype = "";
int currentnodeid = 0;

boolean canview = false ;               // �Ƿ���Բ鿴
boolean canactive = false ;             // �Ƿ���Զ�ɾ���Ĺ���������
boolean isurger=false;                  //�����˿ɲ鿴
boolean wfmonitor=false;                //���̼����
boolean haveBackright=false;            //ǿ���ջ�Ȩ��
boolean haveStopright = false;			//��ͣȨ��
boolean haveCancelright = false;		//����Ȩ��
boolean haveRestartright = false;		//����Ȩ��
boolean islog=true;            			//�Ƿ��¼�鿴��־
//boolean haveOverright=false;            //ǿ�ƹ鵵Ȩ��
int wfcurrrid=0;

String sql = "" ;
char flag = Util.getSeparator() ;
String  fromFlowDoc=Util.null2String(request.getParameter("fromFlowDoc"));  //�Ƿ�����̴����ĵ�����
String isSignMustInputOfThisJsp="0";
String isFormSignatureOfThisJsp="0";
rs.executeSql("select requestid from workflow_currentoperator where userid="+userid+" and usertype="+usertype+" and requestid="+requestid);
if(rs.next()){
	canview=true;
	isrequest = "0";
}
if(isrequest.equals("1")){      // ����ع���������,�в鿴Ȩ��
    requestid=Util.getIntValue(String.valueOf(session.getAttribute("resrequestid"+wflinkno)),0);
    String realateRequest=Util.null2String(String.valueOf(session.getAttribute("relaterequest")));
	if (requestid==0)  requestid=Util.getIntValue(request.getParameter("requestid"),0);
    int tempnum_ = Util.getIntValue(String.valueOf(session.getAttribute("slinkwfnum")));
	for(int cx_=0; cx_<=tempnum_; cx_++){
		int resrequestid_ = Util.getIntValue(String.valueOf(session.getAttribute("resrequestid"+cx_)));
		if(resrequestid_ == requestid){
			desrequestid = Util.getIntValue(String.valueOf(session.getAttribute("desrequestid")),0);//��������ID
			rs.executeSql("select count(*) from workflow_currentoperator where userid="+userid+" and usertype="+usertype+" and requestid="+desrequestid);
		    if(rs.next()){
		        int counts=rs.getInt(1);
		        if(counts>0){
		           canview=true;
		           break;
		        }
		    }
		}
	}
    session.setAttribute(requestid+"wflinkno",wflinkno+"");//���������̣��������̲�������Ȩ�޴�ӡ������    
}
int currentstatus = -1;//��ǰ����״̬(��Ӧ������ͣ����������)
// ��ѯ�������ع�����������Ϣ
RecordSet.executeProc("workflow_Requestbase_SByID",requestid+"");
if(RecordSet.next()){
    status  = Util.null2String(RecordSet.getString("status")) ;
    requestname= Util.null2String(RecordSet.getString("requestname")) ;
	requestlevel = Util.null2String(RecordSet.getString("requestlevel"));
    requestmark = Util.null2String(RecordSet.getString("requestmark")) ;
    creater = Util.getIntValue(RecordSet.getString("creater"),0);
	creatertype = Util.getIntValue(RecordSet.getString("creatertype"),0);
    deleted = Util.getIntValue(RecordSet.getString("deleted"),0);
	workflowid = Util.getIntValue(RecordSet.getString("workflowid"),0);
	currentnodeid = Util.getIntValue(RecordSet.getString("currentnodeid"),0);
    if(nodeid<1) nodeid = currentnodeid;
	currentnodetype = Util.null2String(RecordSet.getString("currentnodetype"));
    if(nodetype.equals("")) nodetype = currentnodetype;
    docCategory=Util.null2String(RecordSet.getString("docCategory"));
    workflowname = WorkflowComInfo.getWorkflowname(workflowid+"");
    workflowtype = WorkflowComInfo.getWorkflowtype(workflowid+"");

    lastOperator = Util.getIntValue(RecordSet.getString("lastOperator"),0);
    lastOperateDate=Util.null2String(RecordSet.getString("lastOperateDate"));
    lastOperateTime=Util.null2String(RecordSet.getString("lastOperateTime"));
    currentstatus = Util.getIntValue(RecordSet.getString("currentstatus"),-1);
}else{
    response.sendRedirect("/notice/Deleted.jsp?showtype=wf");
    return ;
}
//�Ӽƻ�����ҳ��������в鿴Ȩ�� Start
int isworktask = Util.getIntValue(request.getParameter("isworktask"), 0);
if(isworktask == 1){
	int haslinkworktask = Util.getIntValue((String)session.getAttribute("haslinkworktask"), 0);
	if(haslinkworktask == 1){
		int tlinkwfnum = Util.getIntValue((String)session.getAttribute("tlinkwfnum"), 0);
		int i_tmp = 0;
		for(i_tmp=0; i_tmp<tlinkwfnum; i_tmp++){
			int retrequestid = Util.getIntValue((String)session.getAttribute("retrequestid"+i_tmp), 0);
			if(retrequestid != requestid){
				session.removeAttribute("retrequestid"+i_tmp);
				session.removeAttribute("deswtrequestid"+i_tmp);
				continue;
			}
			int deswtrequestid = Util.getIntValue((String)session.getAttribute("deswtrequestid"+i_tmp), 0);
			rs.execute("select * from worktask_requestbase where requestid="+deswtrequestid);
			if(rs.next()){
				int wt_id = Util.getIntValue(rs.getString("taskid"), 0);
				int wt_status = Util.getIntValue(rs.getString("status"), 1);
				int wt_creater = Util.getIntValue(rs.getString("creater"), 0);
				int wt_needcheck = Util.getIntValue(rs.getString("needcheck"), 0);
				int wt_checkor = Util.getIntValue(rs.getString("checkor"), 0);
				int wt_approverequest = Util.getIntValue(rs.getString("approverequest"), 0);
				if(wt_needcheck == 0){
					wt_checkor = 0;
				}
				WTRequestManager wtRequestManager = new WTRequestManager(wt_id);
				wtRequestManager.setLanguageID(user.getLanguage());
				wtRequestManager.setUserID(user.getUID());
				Hashtable checkRight_hs = wtRequestManager.checkRight(deswtrequestid, wt_status, 0, wt_creater, wt_checkor, wt_approverequest);
				boolean canView_tmp = false;
				canView_tmp = (Util.null2String((String)checkRight_hs.get("canView"))).equalsIgnoreCase("true")?true:false;
				if(canView_tmp==false){
					checkRight_hs = wtRequestManager.checkTemplateRight(deswtrequestid, wt_status, 0, wt_creater, wt_checkor, wt_approverequest);
					canView_tmp = (Util.null2String((String)checkRight_hs.get("canView"))).equalsIgnoreCase("true")?true:false;
				}
				if(canView_tmp == true){
					canview = canView_tmp;
				}
			}
			session.removeAttribute("retrequestid"+i_tmp);
			session.removeAttribute("deswtrequestid"+i_tmp);
			session.setAttribute("haslinkworktask", "0");
			session.setAttribute("tlinkwfnum", "0");
			continue;
		}
	}

}
//�Ӽƻ�����ҳ��������в鿴Ȩ�� End
session.removeAttribute(userid+"_"+requestid+"isremark");
ArrayList canviewwff = (ArrayList)session.getAttribute("canviewwf");
if(canviewwff!=null)
   if(canviewwff.indexOf(requestid+"")>-1)
         canview = true;

if(creater == userid && creatertype == usertype){   // �����߱����в鿴Ȩ��
	canview=true;
	canactive=true;
}

// ����û��鿴Ȩ��
// ����û��Ƿ���Բ鿴�ͼ���ù����� (����Ƕ�ɾ���Ĺ�����,��ɾ��״̬��Ϊɾ��ǰ��״̬)
// canview = HrmUserVarify.checkUserRight("ViewRequest:View", user);   //��ViewRequest:ViewȨ�޵��˿��Բ鿴ȫ��������
// canactive = HrmUserVarify.checkUserRight("ViewRequest:Active", user);   //��ViewRequest:ActiveȨ�޵��˿��Բ鿴ȫ��������
   

// ��ǰ�û����и������Ӧ����Ϣ isremarkΪ0Ϊ��ǰ������, isremarkΪ1Ϊ��ǰ��ת����,isremarkΪ2Ϊ�ɸ��ٲ鿴��,isremark=5Ϊ��Ԥ��
//RecordSet.executeProc("workflow_currentoperator_SByUs",userid+""+flag+usertype+flag+requestid+"");
int preisremark=-1;//��������̲����ˣ���ֵ�ᱻ������ȷ��ֵ���ڳ�ʼ��ʱ����Ϊ����ֵ���Խ�������̲����˲鿴������ʱȨ���ж����⡣TD10126
String isremarkForRM = "";
int groupdetailid=0;
RecordSet.executeSql("select isremark,isreminded,preisremark,id,groupdetailid,nodeid,(CASE WHEN isremark=9 THEN '7.5' ELSE isremark END) orderisremark from workflow_currentoperator where requestid="+requestid+" and userid="+userid+" and usertype="+usertype+" order by orderisremark,id ");
new weaver.general.BaseBean().writeLog("--261---sql---====="+"select isremark,isreminded,preisremark,id,groupdetailid,nodeid from workflow_currentoperator where requestid="+requestid+" and userid="+userid+" and usertype="+usertype+" order by isremark,id");
boolean istoManagePage=false;   //add by xhheng @20041217 for TD 1438
while(RecordSet.next())	{
    String isremark = Util.null2String(RecordSet.getString("isremark")) ;
	isremarkForRM = isremark;
    preisremark=Util.getIntValue(RecordSet.getString("preisremark"),0) ;
    wfcurrrid=Util.getIntValue(RecordSet.getString("id"));
    groupdetailid=Util.getIntValue(RecordSet.getString("groupdetailid"),0);
    int tmpnodeid=Util.getIntValue(RecordSet.getString("nodeid"));
    //modify by mackjoe at 2005-09-29 td1772 ת�����⴦��ת����Ϣ����δ����һֱ��Ҫ����ʹ�����ѹ鵵
    if( isremark.equals("1")||isremark.equals("5") || isremark.equals("7")|| isremark.equals("9") ||(isremark.equals("0")  && !nodetype.equals("3")) ) {
      //modify by xhheng @20041217 for TD 1438
      istoManagePage=true;
      canview=true;
      nodeid=tmpnodeid;
      nodetype=WFLinkInfo.getNodeType(nodeid);  
      break;
    }
    if(isremark.equals("8")){
        canview=true;
        break;
    }
    canview=true;
} 

String IsFromWFRemark = "";
if(currentnodetype.equals("3")){
	IsFromWFRemark="2";
}else{
	if("1".equals(isremarkForRM) || "0".equals(isremarkForRM) || "7".equals(isremarkForRM) || "8".equals(isremarkForRM) || "9".equals(isremarkForRM)){
		IsFromWFRemark="0";
	}else if("2".equals(isremarkForRM)){
	    IsFromWFRemark="1";
	}
}
session.setAttribute(userid+"_"+requestid+"IsFromWFRemark",""+IsFromWFRemark);

//add by mackjoe at 2008-10-15 td9423
String isintervenor=Util.null2String(request.getParameter("isintervenor"));
int intervenorright=0;
if(isintervenor.equals("1")){
    intervenorright=SysWFLMonitor.getWFInterventorRightBymonitor(userid,requestid);
}  
if(intervenorright>0){
    istoManagePage=false;
    canview=true;
    nodeid=currentnodeid;
    nodetype=currentnodetype;
}
//add by mackjoe at 2006-04-24 td3994
int urger=Util.getIntValue(request.getParameter("urger"),0);
session.setAttribute(userid+"_"+requestid+"urger",""+urger);
if(urger==1){
    canview=false;
    intervenorright=0;
}
if(!canview) isurger=WFUrgerManager.UrgerHaveWorkflowViewRight(requestid,userid,Util.getIntValue(logintype,1));
int ismonitor=Util.getIntValue(request.getParameter("ismonitor"),0);
session.setAttribute(userid+"_"+requestid+"ismonitor",""+ismonitor);    
if(ismonitor==1){
    canview=false;
    intervenorright=0;
    isurger=false;
}
if(!canview&&!isurger) wfmonitor=WFUrgerManager.getMonitorViewRight(requestid,userid);
session.setAttribute(userid+"_"+requestid+"isintervenor",""+isintervenor);
session.setAttribute(userid+"_"+requestid+"intervenorright",""+intervenorright);
PoppupRemindInfoUtil.updatePoppupRemindInfo(userid,10,(logintype).equals("1") ? "0" : "1",requestid);
PoppupRemindInfoUtil.updatePoppupRemindInfo(userid,14,(logintype).equals("1") ? "0" : "1",requestid);
session.removeAttribute(userid+"_"+requestid+"currentusercanview");
if(!canview && !isurger && !wfmonitor && !CoworkDAO.haveRightToViewWorkflow(Integer.toString(userid),Integer.toString(requestid)) && !CrmContractTypeUtil.haveRightToViewWorkflow(Integer.toString(userid),Integer.toString(requestid))) {
    if(!WFUrgerManager.UrgerHaveWorkflowViewRight(desrequestid,userid,Util.getIntValue(logintype,1)) && !WFUrgerManager.getMonitorViewRight(desrequestid,userid)){//�������̺ͼ�����̵���������в鿴Ȩ��
    	session.setAttribute(userid+"_"+requestid+"currentusercanview","true");    	
        response.sendRedirect("/notice/noright.jsp?isovertime="+isovertime);
        return ;
    }
}
if(urger==1 && isurger==true){//���̶������ڣ����Ҿ��ж���鿴���̱���Ȩ��
	nodeid = currentnodeid;
	nodetype = currentnodetype;
}
String isaffirmance=WorkflowComInfo.getNeedaffirmance(""+workflowid);//�Ƿ���Ҫ�ύȷ��
//TD8715 ��ȡ��������Ϣ���Ƿ���ʾ����ͼ
WFManager.setWfid(workflowid);
WFManager.getWfInfo();
String isShowChart = Util.null2String(WFManager.getIsShowChart());
//System.out.println("isShowChart = " + isShowChart);
RecordSet.executeProc("workflow_Workflowbase_SByID",workflowid+"");

//��session�洢��SESSION�У����������ã��ﵽ��ͬ�����̿���ʹ��ͬһ����򣬲�ͬ������
session.setAttribute("workflowidbybrowser",workflowid+"");

if(RecordSet.next()){
	isModifyLog = Util.null2String(RecordSet.getString("isModifyLog"));//by cyril on 2008-07-09 for TD:8835
	formid = Util.getIntValue(RecordSet.getString("formid"),0);
	isbill = ""+Util.getIntValue(RecordSet.getString("isbill"),0);
	helpdocid = Util.getIntValue(RecordSet.getString("helpdocid"),0);
}
RecordSet.executeSql("select issignmustinput from workflow_flownode where workflowid="+workflowid+" and nodeid="+nodeid);
if(RecordSet.next()){
	isSignMustInputOfThisJsp = ""+Util.getIntValue(RecordSet.getString("issignmustinput"), 0);
}
boolean isOrgBeforeCoadSubmit = false;
if(isremarkForRM.equals("0")){//Э�����Ѿ��ύ�����ڹ�ѡ"δ�鿴һֱͣ���ڴ���"�����������˴򿪴������̣�ֱ�Ӿͱ���Ѱ�
	RecordSet.execute("select c1.id from workflow_currentoperator c1 where c1.isremark='2' and c1.preisremark='7' and c1.requestid="+requestid+" and exists(select 1 from workflow_currentoperator c2 where c2.id="+wfcurrrid+" and c1.receivedate=c2.receivedate and c1.receivetime=c2.receivetime and c1.nodeid=c2.nodeid and c1.groupdetailid=c2.groupdetailid ) and exists(select id from workflow_groupdetail g where g.id=c1.groupdetailid and g.signtype='0')");
	if(RecordSet.next()){
		int c1id_t = Util.getIntValue(RecordSet.getString("id"));
		if(c1id_t > 0){
			isOrgBeforeCoadSubmit = true;
			isremarkForRM="2";
		    istoManagePage=false;
		}
	}
}
session.setAttribute(userid+"_"+requestid+"canview",canview+"");
session.setAttribute(userid+"_"+requestid+"isurger",isurger+"");
session.setAttribute(userid+"_"+requestid+"wfmonitor",wfmonitor+"");
session.setAttribute(userid+"_"+requestid+"isrequest",isrequest);
session.setAttribute(userid+"_"+requestid+"isremarkForRM",isremarkForRM+"");
session.setAttribute(userid+"_"+requestid+"preisremark",preisremark+"");
session.setAttribute(userid+"_"+requestid+"wfcurrrid",wfcurrrid+"");
session.setAttribute(userid+"_"+requestid+"groupdetailid",groupdetailid+"");
session.setAttribute(userid+"_"+requestid+"isSignMustInputOfThisJsp",""+isSignMustInputOfThisJsp);
session.setAttribute(userid+"_"+requestid+"helpdocid",""+helpdocid);
session.setAttribute(userid+"_"+requestid+"isModifyLog",""+isModifyLog);
session.setAttribute(userid+"_"+requestid+"currentnodeid",""+currentnodeid);
session.setAttribute(userid+"_"+requestid+"currentnodetype",currentnodetype);
session.setAttribute(userid+"_"+requestid+"isaffirmance",isaffirmance);
session.setAttribute(userid+"_"+requestid+"reEdit",reEdit);
session.setAttribute(userid+"_"+requestid+"workflowname",workflowname);
request.setAttribute(userid+"_"+workflowid+"workflowname",workflowname);
session.setAttribute(""+userid+"_"+requestid+"currentstatus",""+currentstatus);
//�ж��Ƿ������̴����ĵ��������ڸýڵ����������ֶ�
boolean docFlag=flowDoc.haveDocFiled(""+workflowid,""+nodeid);
String  docFlagss=docFlag?"1":"0";
//��������̴��ĵ���������û��TABҳ
if("1".equals(isworkflowhtmldoc)) docFlagss="0";
session.setAttribute("requestAdd"+requestid,docFlagss);
if (!fromFlowDoc.equals("1"))
{
if (docFlag)
{ if (fromoperation.equals("1"))
	{

	if (!nodetypedoc.equals("0")) {
	%>
	<script>
		<%if("1".equals(isShowChart)){%>
	 //setTimeout("window.close()",1);
     //window.opener._table.reLoad();
try{	
	window.opener.btnWfCenterReload.onclick();
}catch(e){}

try{
    parent.window.taskCallBack(2,<%=preisremark%>); 
}catch(e){}

try{
	<%if(isUseOldWfMode){%>
	window.opener._table.reLoad();
	<%}else{%>
	window.opener.reLoad();
	<%}%>
}catch(e){}
     window.location.href="/workflow/request/WorkflowDirection.jsp?requestid=<%=requestid%>&workflowid=<%=workflowid%>&isbill=<%=isbill%>&formid=<%=formid%>";
	 <%}else{
		 islog=false;
	 %>
	 //setTimeout("window.close()",1);
     //window.opener._table.reLoad();
try{
	window.opener.btnWfCenterReload.onclick();
}catch(e){}

try{
	<%if(isUseOldWfMode){%>
	window.opener._table.reLoad();
	<%}else{%>
	window.opener.reLoad();
	<%}%>
}catch(e){}

		 <%}%>
    </script>
    <%}
	else
	{%>
			<script>
		try{
	window.opener.btnWfCenterReload.onclick();
}catch(e){}

try{
	<%if(isUseOldWfMode){%>
	window.opener._table.reLoad();
	<%}else{%>
	window.opener.reLoad();
	<%}%>
}catch(e){}

try{
    parent.window.taskCallBack(2,<%=preisremark%>); 
}catch(e){}
</script>
<%
		if("1".equals(isShowChart)){
			if(!docFlag){
			response.sendRedirect("/workflow/request/WorkflowDirection.jsp?requestid="+requestid+"&workflowid="+workflowid+"&isbill="+isbill+"&formid="+formid+"&isfromtab="+isfromtab);
			return;
			}
		}else{
		 //response.sendRedirect("RequestView.jsp");
		}
     //return;
	}
    }
session.setAttribute(userid+"_"+requestid+"requestname",requestname);
session.setAttribute(userid+"_"+requestid+"requestmark",requestmark);
session.setAttribute(userid+"_"+requestid+"status",status);
//response.sendRedirect("ViewRequestDoc.jsp?requestid="+requestid+"&isrequest="+isrequest+"&isovertime="+isovertime+"&isaffirmance="+isaffirmance+"&reEdit="+reEdit+"&wflinkno="+wflinkno);
//return;
isworkflowdoc = "1";
//fromFlowDoc = "1";
}
}
if(fromoperation.equals("1")&&(src.equals("submit")||src.equals("reject"))){//fromoperation=1��ʾ��������������,�����ύ���˻ز���ʱ���ص�����ͼҳ�档
%>
<script>
try{
	window.opener.btnWfCenterReload.onclick();
}catch(e){}
try{
	<%if(isUseOldWfMode){%>
	window.opener._table.reLoad();
	<%}else{%>
	window.opener.reLoad();
	<%}%>
}catch(e){}

try{
    parent.window.taskCallBack(2,<%=preisremark%>); 
}catch(e){}

<%if("1".equals(isShowChart)){%>
window.location.href="/workflow/request/WorkflowDirection.jsp?requestid=<%=requestid%>&workflowid=<%=workflowid%>&isbill=<%=isbill%>&formid=<%=formid%>";
</script>
<%  return;
	}else{%>
</script>
    <%}

}
WFForwardManager.init();
WFForwardManager.setWorkflowid(workflowid);
WFForwardManager.setNodeid(nodeid);
WFForwardManager.setIsremark(isremarkForRM);
WFForwardManager.setRequestid(requestid);
WFForwardManager.setBeForwardid(wfcurrrid);
WFForwardManager.getWFNodeInfo();
String IsPendingForward=WFForwardManager.getIsPendingForward();
String IsBeForward=WFForwardManager.getIsBeForward();
String IsSubmitedOpinion=WFForwardManager.getIsSubmitedOpinion();
String IsSubmitForward=WFForwardManager.getIsSubmitForward();
String IsWaitForwardOpinion=WFForwardManager.getIsWaitForwardOpinion();
String IsBeForwardSubmit=WFForwardManager.getIsBeForwardSubmit();
String IsBeForwardModify=WFForwardManager.getIsBeForwardModify();
String IsBeForwardPending=WFForwardManager.getIsBeForwardPending();
String IsBeForwardTodo = WFForwardManager.getIsBeForwardTodo();
String IsBeForwardSubmitAlready = WFForwardManager.getIsBeForwardSubmitAlready();
String IsBeForwardSubmitNotaries = WFForwardManager.getIsBeForwardSubmitNotaries();
String IsFromWFRemark_T = WFForwardManager.getIsFromWFRemark();
String IsBeForwardAlready = WFForwardManager.getIsBeForwardAlready();
boolean IsFreeWorkflow=WFForwardManager.getIsFreeWorkflow(requestid,nodeid,Util.getIntValue(isremarkForRM));
String IsFreeNode=WFForwardManager.getIsFreeNode(nodeid);
session.setAttribute(userid+"_"+requestid+"wfcurrrid",""+wfcurrrid);
boolean IsCanSubmit=WFForwardManager.getCanSubmit();
boolean IsBeForwardCanSubmitOpinion=WFForwardManager.getBeForwardCanSubmitOpinion();
boolean IsCanModify=WFForwardManager.getCanModify();
WFCoadjutantManager.getCoadjutantRights(groupdetailid);
String coadsigntype=WFCoadjutantManager.getSigntype();
String coadissubmitdesc=WFCoadjutantManager.getIssubmitdesc();
String coadisforward=WFCoadjutantManager.getIsforward();
String coadismodify=WFCoadjutantManager.getIsmodify();
String coadispending=WFCoadjutantManager.getIspending();
if(!IsCanModify&&coadismodify.equals("1")) IsCanModify=true;
if(nodeid!=currentnodeid && coadismodify.equals("1") && isremarkForRM.equals("7")) IsCanModify = false;
boolean coadCanSubmit=WFCoadjutantManager.getCoadjutantCanSubmit(requestid,wfcurrrid,isremarkForRM,coadsigntype);
boolean isMainSubmitted =      WFCoadjutantManager.isMainSubmitted();
session.setAttribute(userid+"_"+requestid+"isMainSubmitted",""+isMainSubmitted);
session.setAttribute(userid+"_"+requestid+"coadsigntype",coadsigntype);
session.setAttribute(userid+"_"+requestid+"coadissubmitdesc",coadissubmitdesc);
session.setAttribute(userid+"_"+requestid+"coadisforward",coadisforward);
session.setAttribute(userid+"_"+requestid+"coadismodify",coadismodify);
session.setAttribute(userid+"_"+requestid+"coadispending",coadispending);
session.setAttribute(userid+"_"+requestid+"coadCanSubmit",""+coadCanSubmit);
session.setAttribute(userid+"_"+requestid+"IsPendingForward",IsPendingForward);
session.setAttribute(userid+"_"+requestid+"IsBeForward",IsBeForward);
session.setAttribute(userid+"_"+requestid+"IsBeForwardTodo",IsBeForwardTodo);
session.setAttribute(userid+"_"+requestid+"IsBeForwardSubmitAlready",IsBeForwardSubmitAlready);
session.setAttribute(userid+"_"+requestid+"IsBeForwardSubmitNotaries",IsBeForwardSubmitNotaries);
session.setAttribute(userid+"_"+requestid+"IsFromWFRemark_T",IsFromWFRemark_T);
session.setAttribute(userid+"_"+requestid+"IsBeForwardAlready",IsBeForwardAlready);
session.setAttribute(userid+"_"+requestid+"IsSubmitedOpinion",IsSubmitedOpinion);
session.setAttribute(userid+"_"+requestid+"IsSubmitForward",IsSubmitForward);
session.setAttribute(userid+"_"+requestid+"IsWaitForwardOpinion",IsWaitForwardOpinion);
session.setAttribute(userid+"_"+requestid+"IsBeForwardSubmit",IsBeForwardSubmit);
session.setAttribute(userid+"_"+requestid+"IsBeForwardModify",IsBeForwardModify);
session.setAttribute(userid+"_"+requestid+"IsBeForwardPending",IsBeForwardPending);
session.setAttribute(userid+"_"+requestid+"IsCanSubmit",""+IsCanSubmit);
session.setAttribute(userid+"_"+requestid+"IsBeForwardCanSubmitOpinion",""+IsBeForwardCanSubmitOpinion);
session.setAttribute(userid+"_"+requestid+"IsCanModify",""+IsCanModify);
session.setAttribute(userid+"_"+requestid+"IsFreeWorkflow",""+IsFreeWorkflow);
session.setAttribute(userid+"_"+requestid+"IsFreeNode",""+IsFreeNode);

boolean canForwd = false;
if(("0".equals(IsFromWFRemark_T) && "1".equals(IsBeForwardTodo))||("1".equals(IsFromWFRemark_T) && "1".equals(IsBeForwardAlready))||("2".equals(IsFromWFRemark_T) && "1".equals(IsBeForward))) {
	canForwd = true;
	}
	new weaver.general.BaseBean().writeLog("---vr-581-IsFromWFRemark_T---===="+IsFromWFRemark_T+"--IsBeForwardAlready--==="+IsBeForwardAlready+"----IsBeForward---==="+IsBeForward+"---canForwd---====="+canForwd+"---IsBeForwardTodo---="+IsBeForwardTodo);

Calendar today = Calendar.getInstance();
String currentdate = Util.add0(today.get(Calendar.YEAR), 4) + "-" +
                     Util.add0(today.get(Calendar.MONTH) + 1, 2) + "-" +
                     Util.add0(today.get(Calendar.DAY_OF_MONTH), 2) ;

String currenttime = Util.add0(today.get(Calendar.HOUR_OF_DAY), 2) + ":" +
                     Util.add0(today.get(Calendar.MINUTE), 2) + ":" +
                     Util.add0(today.get(Calendar.SECOND), 2) ;
boolean isPendingRemark = false;
String tempIsremark = isremarkForRM;
if("0".equals(isremarkForRM))isPendingRemark =true;
if(isremarkForRM.equals("8")||(isremarkForRM.equals("1")&&!IsCanSubmit&&!IsSubmitedOpinion.equals("1"))||("7".equals(isremarkForRM)&&!coadCanSubmit) || isOrgBeforeCoadSubmit){
	if(isremarkForRM.equals("1") && WFForwardManager.hasChildCanSubmit(requestid+"",userid+"")){
		RecordSet.executeProc("workflow_CurrentOperator_Copy", requestid + "" + flag + userid + flag + usertype + "");
	}else if(isremarkForRM.equals("8") || ("7".equals(isremarkForRM)&&!coadCanSubmit) || isOrgBeforeCoadSubmit){
		RecordSet.executeProc("workflow_CurrentOperator_Copy", requestid + "" + flag + userid + flag + usertype + "");
	}

    if (currentnodetype.equals("3")) {
        RecordSet.executeSql("update workflow_currentoperator set iscomplete=1 where requestid=" + requestid + " and userid=" + userid + " and usertype=" + usertype);
    }
	isPendingRemark = true;
	if (currentnodetype.equals("3")) {
		RecordSet.executeSql("select isremark from workflow_currentoperator  where requestid=" + requestid + " and nodeid="+nodeid+" and userid=" + userid + " and usertype=" + usertype);
		if(RecordSet.next()){
			isremarkForRM=Util.null2String(RecordSet.getString("isremark"));
		}else{
			isremarkForRM="2";
		}
	}else{
		isremarkForRM="2";
	}
	session.setAttribute(userid+"_"+requestid+"isremarkForRM",isremarkForRM+"");
    istoManagePage=false;

%>
<script>
     //window.opener._table.reLoad();
try{	
	window.opener.btnWfCenterReload.onclick();
}catch(e){}
try{
	<%if(isUseOldWfMode){%>
	window.opener._table.reLoad();
	<%}else{%>
	window.opener.reLoad();
	<%}%>

}catch(e){}

try{
    parent.window.taskCallBack(2,<%=preisremark%>); 
}catch(e){}

</script>
<%
}
session.setAttribute(userid+"_"+requestid+"isPendingRemark",""+isPendingRemark);
if( isbill.equals("1") ) {
    RecordSet.executeProc("workflow_form_SByRequestid",requestid+"");
    if(RecordSet.next()){
    formid = Util.getIntValue(RecordSet.getString("billformid"),0);
    billid= Util.getIntValue(RecordSet.getString("billid"));
    }
    if(formid == 207){//�ƻ�������������������
    	//���⴦��ֱ����ת���ƻ�������棬������������
    	int approverequest = Util.getIntValue(request.getParameter("requestid"), 0);
    	int wt_requestid = 0;
    	rs.execute("select * from worktask_requestbase where approverequest="+approverequest);
    	if(rs.next()){
    		wt_requestid = Util.getIntValue(rs.getString("requestid"), 0);
    	}
    	response.sendRedirect("/worktask/request/ViewWorktask.jsp?requestid="+wt_requestid);
		return;
    }

}

//xwj for td3450 20060112
if(istoManagePage && !isprint){
PoppupRemindInfoUtil.updatePoppupRemindInfo(userid,0,(logintype).equals("1") ? "0" : "1",requestid);
}
else{
String updatePoppupFlag = Util.null2String(request.getParameter("updatePoppupFlag"));
if( !"1".equals(updatePoppupFlag)){
PoppupRemindInfoUtil.updatePoppupRemindInfo(userid,1,(logintype).equals("1") ? "0" : "1",requestid);
}
}
String message = Util.null2String(request.getParameter("message"));       // ���صĴ�����Ϣ

session.setAttribute(userid+"_"+requestid+"requestname",requestname);
session.setAttribute(userid+"_"+requestid+"workflowid",""+workflowid);
session.setAttribute(userid+"_"+requestid+"nodeid",""+nodeid);
session.setAttribute(userid+"_"+requestid+"preisremark",""+preisremark);
if(((isremarkForRM.equals("0")||isremarkForRM.equals("1"))&&!IsCanSubmit)||(isremarkForRM.equals("7")&&!coadCanSubmit)) istoManagePage=false;
session.setAttribute(userid+"_"+requestid+"formid",""+formid);
    session.setAttribute(userid+"_"+requestid+"billid",""+billid);
    session.setAttribute(userid+"_"+requestid+"isbill",isbill);
    session.setAttribute(userid+"_"+requestid+"nodetype",nodetype);
    session.setAttribute(userid+"_"+requestid+"creater",""+creater);
    session.setAttribute(userid+"_"+requestid+"creatertype",""+creatertype);
    session.setAttribute(userid+"_"+requestid+"requestlevel",requestlevel);
//add by xhheng @20041217 for TD 1438 start
if(istoManagePage && !isprint && !wfmonitor && isonlyview!=1 && !"1".equals(isworkflowhtmldoc)){
    String topage = URLEncoder.encode(Util.null2String(request.getParameter("topage"))) ;        //���ص�ҳ��
    String docfileid = Util.null2String(request.getParameter("docfileid"));   // �½��ĵ��Ĺ������ֶ�
    String newdocid = Util.null2String(request.getParameter("docid"));        // �½����ĵ�
    String actionPage = "ManageRequestNoForm.jsp?fromFlowDoc="+fromFlowDoc ;

    session.setAttribute(userid+"_"+requestid+"status",status);
    session.setAttribute(userid+"_"+requestid+"requestmark",requestmark);
    session.setAttribute(userid+"_"+requestid+"deleted",""+deleted);
    session.setAttribute(userid+"_"+requestid+"currentnodeid",""+currentnodeid);
    session.setAttribute(userid+"_"+requestid+"currentnodetype",currentnodetype);
    session.setAttribute(userid+"_"+requestid+"workflowtype",workflowtype);
    session.setAttribute(userid+"_"+requestid+"helpdocid",""+helpdocid);
    session.setAttribute(userid+"_"+requestid+"docCategory",docCategory);
    session.setAttribute(userid+"_"+requestid+"newdocid",newdocid);
    session.setAttribute(userid+"_"+requestid+"wfmonitor",""+wfmonitor);

    session.setAttribute(userid+"_"+requestid+"lastOperator",""+lastOperator);
    session.setAttribute(userid+"_"+requestid+"lastOperateDate",lastOperateDate);
    session.setAttribute(userid+"_"+requestid+"lastOperateTime",lastOperateTime);

    //RecordSet������һ���ͻ�������ִ��֮�䣬��ѯ������Ա�������һ�β�ѯ(��RecordSet�� Description 4)
    //�����ڶ���sqlִ��ʧ��ʱ��û�����RecordSet�����Ա�����һ��sqlִ�н��
    //�ɴˣ�Ϊ��ֹͼ�λ�����ؽű�û��ִ�ж�����sqlִ��ʧ�������µ��߼������������µ�RecordSet����mrs
    //�����ϵ�ͼ�λ���ʽ��Ĭ��Ϊ��ͨ��ʽ mackjoe at 2006-06-12
    /*RecordSet mrs=new RecordSet();
    mrs.executeSql("select count(a.formid) from workflow_formprop a, workflow_base b, workflow_Requestbase c where a.formid = b.formid and b.id = c.workflowid and b.isbill='0' and c.requestid = "+requestid);

    if(mrs.next() && mrs.getInt(1) > 0 ){
      actionPage = "ManageRequestForm.jsp" ;
    }else{
      actionPage = "ManageRequestNoForm.jsp" ;
    }
    */
    //System.out.println("actionPage="+actionPage);

    //�����˵����̲鿴״̬��Ϊ�Ѳ鿴2,���ύ���Ĳ�Ҫ�ڸ��²���ʱ��
    if (RecordSet.getDBType().equals("oracle"))
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh begin
	{
        //RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate= to_char(sysdate,'yyyy-mm-dd'),operatetime=to_char(sysdate,'hh24:mi:ss'),orderdate=receivedate,ordertime=receivetime where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
        RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=( case isremark when '2' then operatedate else to_char(sysdate,'yyyy-mm-dd') end  ) ,operatetime=( case isremark when '2' then operatetime else to_char(sysdate,'hh24:mi:ss') end  ) where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2 ");
	}
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh end
    else if (RecordSet.getDBType().equals("db2"))
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh begin
	{
        //RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=to_char(current date,'yyyy-mm-dd'),operatetime=to_char(current time,'hh24:mi:ss'),orderdate=receivedate,ordertime=receivetime where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
        RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=( case isremark when '2' then operatedate else to_char(current date,'yyyy-mm-dd') end ),operatetime=( case isremark when '2' then operatetime else to_char(current time,'hh24:mi:ss') end ) where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
	}
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh end
    else
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh begin
	{
        //RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=convert(char(10),getdate(),20),operatetime=convert(char(8),getdate(),108),orderdate=receivedate,ordertime=receivetime where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
        //RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=to_char(current date,'yyyy-mm-dd'),operatetime=to_char(current time,'hh24:mi:ss')  where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");//update by fanggsh 20060510
        RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,operatedate=( case isremark when '2' then operatedate else convert(char(10),getdate(),20) end ),operatetime=( case isremark when '2' then operatetime else convert(char(8),getdate(),108) end ) where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");//update by fanggsh 20060510

	}
		//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh end
    //��¼��һ�β鿴ʱ��
    //RecordSet.executeProc("workflow_CurOpe_UpdatebyView",""+requestid+ flag + userid + flag + usertype);

    actionPage+="&requestid="+requestid+"&isrequest="+isrequest+"&isovertime="+isovertime+"&isaffirmance="+isaffirmance+"&reEdit="+reEdit+"&seeflowdoc="+seeflowdoc+"&isworkflowdoc="+isworkflowdoc+"&isfromtab="+isfromtab;
    if(!message.equals("")) actionPage+="&message="+message;
    if(!topage.equals("")) actionPage+="&topage="+topage;
    if(!docfileid.equals("")) actionPage+="&docfileid="+docfileid;
    if(!newdocid.equals("")) actionPage+="&newdocid="+newdocid;
    response.sendRedirect(actionPage);

    //��ǰ�����߻��߱�ת����,���ҵ�ǰ�ڵ㲻Ϊ�����ڵ�,ֱ�ӽ������ҳ��
    return ;
}
//add by xhheng @20041217 for TD 1438 end

// ��¼�鿴��־
String clientip = Util.getIpAddr(request);

// modify by xhheng @20050304 for TD 1691

/*--  xwj for td2104 on 20050802 begin  --*/
boolean isOldWf = false;
if(isprint==false){
RecordSet4.executeSql("select nodeid from workflow_currentoperator where requestid = " + requestid);
while(RecordSet4.next()){
	if(RecordSet4.getString("nodeid") == null || "".equals(RecordSet4.getString("nodeid")) || "-1".equals(RecordSet4.getString("nodeid"))){
			isOldWf = true;
	}
}

/*--  xwj for td2104 on 20050802 end  --*/


//�����˵����̲鿴״̬��Ϊ�Ѳ鿴2
	//TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh begin
//RecordSet.executeSql("update workflow_currentoperator set viewtype=-2,orderdate=receivedate,ordertime=receivetime where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
RecordSet.executeSql("update workflow_currentoperator set viewtype=-2 where requestid = " + requestid + "  and userid ="+userid+" and usertype = "+usertype+" and viewtype<>-2");
    //TD4294  ɾ��workflow_currentoperator����orderdate��ordertime�� fanggsh end
//��¼��һ�β鿴ʱ��
RecordSet.executeProc("workflow_CurOpe_UpdatebyView",""+requestid+ flag + userid + flag + usertype);

if(! currentnodetype.equals("3") )
    RecordSet.executeProc("SysRemindInfo_DeleteHasnewwf",""+userid+flag+usertype+flag+requestid);
else
    RecordSet.executeProc("SysRemindInfo_DeleteHasendwf",""+userid+flag+usertype+flag+requestid);
}

String imagefilename = "/images/hdReport.gif";
String titlename =  SystemEnv.getHtmlLabelName(648,user.getLanguage())+":"
	                +SystemEnv.getHtmlLabelName(553,user.getLanguage())+" - "+Util.toScreen(workflowname,user.getLanguage()) + " - " +  status + " "+requestmark ;//Modify by ����� 2004-10-26 For TD1231
//if(helpdocid !=0 ) {titlename=titlename + "<img src=/images/help.gif style=\"CURSOR:hand\" width=12 onclick=\"location.href='/docs/docs/DocDsp.jsp?id="+helpdocid+"'\">";}
String needfav ="1";
String needhelp ="";
//add by mackjoe at 2005-12-20 ����ģ��Ӧ��
String ismode="";
int modeid=0;
int isform=0;
int showdes=0;
int printdes=0;
int toexcel=0;
RecordSet.executeSql("select ismode,showdes,printdes,toexcel from workflow_flownode where workflowid="+workflowid+" and nodeid="+nodeid);
if(RecordSet.next()){
    ismode=Util.null2String(RecordSet.getString("ismode"));
    showdes=Util.getIntValue(Util.null2String(RecordSet.getString("showdes")),0);
    printdes=Util.getIntValue(Util.null2String(RecordSet.getString("printdes")),0);
    toexcel=Util.getIntValue(Util.null2String(RecordSet.getString("toexcel")),0);
}

if(ismode.equals("1") && showdes!=1){
    RecordSet.executeSql("select id from workflow_nodemode where isprint='0' and workflowid="+workflowid+" and nodeid="+nodeid);
    if(RecordSet.next()){
        modeid=RecordSet.getInt("id");
    }else{
        RecordSet.executeSql("select id from workflow_formmode where isprint='0' and formid="+formid+" and isbill='"+isbill+"'");
        if(RecordSet.next()){
            modeid=RecordSet.getInt("id");
            isform=1;
        }
    }
}else if("2".equals(ismode)){
	RecordSet.executeSql("select id from workflow_nodehtmllayout where type=0 and workflowid="+workflowid+" and nodeid="+nodeid);
    if(RecordSet.next()){
        modeid=RecordSet.getInt("id");
    }
}

//---------------------------------------------------------------------------------
//����������-��ǰ������Ƿ�IE����Ϊ���ݣ�������δ�޸ĵĵ��ݣ�����ת������ҳ�� START
//---------------------------------------------------------------------------------
//ģ��ģʽ-����û�ʹ�õ��Ƿ�IE���Զ�ʹ��һ��ģʽ����ʾ���� START 2011-11-23 CC
//if (!isIE.equalsIgnoreCase("true") && ismode.equals("1")) {
if (!isIE.equalsIgnoreCase("true") && ismode.equals("1") && modeid != 0) {
	String messageLableId = "";
	if (ismode.equals("1")) {
		messageLableId = "18017";
	} else {
		messageLableId = "23682";
	}
	ismode = "0";	
	//response.sendRedirect("/wui/common/page/sysRemind.jsp?labelid=" + messageLableId);
	%>

	<script type="text/javascript">
	
	window.parent.location.href = "/wui/common/page/sysRemind.jsp?labelid=<%=messageLableId %>";
	
	</script>

<%
	return;
}
//ģ��ģʽ-����û�ʹ�õ��Ƿ�IE���Զ�ʹ��һ��ģʽ����ʾ���� END
//---------------------------------------------------------------------------------
//����������-��ǰ������Ƿ�IE����Ϊ���ݣ�������δ�޸ĵĵ��ݣ�����ת������ҳ�� END
//---------------------------------------------------------------------------------


if(fromPDA.equals("1") && ismode.equals("1")){
	modeid=0;
}
if(ismode.equals("1") && !isnotprintmode && isprint && printdes!=1&&!fromPDA.equals("1")){
    response.sendRedirect("PrintMode.jsp?requestid="+requestid+"&isbill="+isbill+"&workflowid="+workflowid+"&formid="+formid+"&nodeid="+nodeid+"&billid="+billid+"&isfromtab="+isfromtab);
    return;
}
%>

<HTML><HEAD>
<LINK href="/css/Weaver.css" type=text/css rel=STYLESHEET>
<script language=javascript src="/js/weaver.js"></script>
<LINK href="/css/rp.css" rel="STYLESHEET" type="text/css">

<style>
.wordSpan{font-family:MS Shell Dlg,Arial;CURSOR: hand;font-weight:bold;FONT-SIZE: 10pt}
</style>
<title><%=requestname%></title>
</head>
<script language=javascript>
var showTableDiv;
var oIframe = document.createElement('iframe');
function showPrompt(content){
	showTableDiv  = document.getElementById('_xTable');
     var message_table_Div = document.createElement("div")
     message_table_Div.id="message_table_Div";
     message_table_Div.className="xTable_message";
     showTableDiv.appendChild(message_table_Div);
     var message_table_Div  = document.getElementById("message_table_Div");
     message_table_Div.style.display="inline";
     message_table_Div.innerHTML=content;
     var pTop= document.body.offsetHeight/2+document.body.scrollTop-50;
     var pLeft= document.body.offsetWidth/2-50;
     message_table_Div.style.position="absolute"
     message_table_Div.style.top=pTop;
     message_table_Div.style.left=pLeft;

     message_table_Div.style.zIndex=1002;
     //oIframe = document.createElement('iframe');
     oIframe.id = 'HelpFrame';
     showTableDiv.appendChild(oIframe);
     oIframe.frameborder = 0;
     oIframe.style.position = 'absolute';
     oIframe.style.top = pTop;
     oIframe.style.left = pLeft;
     oIframe.style.zIndex = message_table_Div.style.zIndex - 1;
     oIframe.style.width = parseInt(message_table_Div.offsetWidth);
     oIframe.style.height = parseInt(message_table_Div.offsetHeight);
     oIframe.style.display = 'block';

}
function hiddenPop(){
 try{
    <%
	if(modeid>0 && "1".equals(ismode)){
%>
    oPopup.hide();
<%
    }else{
%>
    showTableDiv.style.display='none';
    oIframe.style.display='none';
<%
    }
%>
}catch(e){}
}
var fromoperation="<%=fromoperation%>";
var overtime="<%=isovertime%>";
function windowOnload()
{
    <%if(modeid>0 && "1".equals(ismode)){%>
        //init();
    <%}else{%>
    setwtableheight();
    <%}
        if( message.equals("4") ) {//�Ѿ���ת����һ�ڵ㣬���������ύ��
%>

		  var content="<%=SystemEnv.getHtmlLabelName(21266,user.getLanguage())%>";
		  showPrompt(content);
          window.setTimeout("message_table_Div.style.display='none';document.all.HelpFrame.style.display='none'", 2000);
<%
	} else if( message.equals("5") ) {//������ת��ʱ�������ԡ�
%>

		  var content="<%=SystemEnv.getHtmlLabelName(21270,user.getLanguage())%>";
		  showPrompt(content);
          window.setTimeout("message_table_Div.style.display='none';document.all.HelpFrame.style.display='none'", 2000);

<%
	} else if( message.equals("6") ) {//ת��ʧ�ܣ������ԣ�
%>

		  var content="<%=SystemEnv.getHtmlLabelName(21766,user.getLanguage())%>";
		  showPrompt(content);
          window.setTimeout("message_table_Div.style.display='none';document.all.HelpFrame.style.display='none'", 2000);

<%
	} else if( message.equals("7") ) {//�Ѿ����������ظ�����
%>

		  var content="<%=SystemEnv.getHtmlLabelName(22751,user.getLanguage())%>";
		  showPrompt(content);
          window.setTimeout("message_table_Div.style.display='none';document.all.HelpFrame.style.display='none'", 2000);

<%
	} else if( message.equals("8") ) {//���������Ѹ��ģ���˶Ժ��ٴ���
%>

		  var content="<%=SystemEnv.getHtmlLabelName(24676,user.getLanguage())%>";
		  showPrompt(content);
          window.setTimeout("message_table_Div.style.display='none';document.all.HelpFrame.style.display='none'", 2000);

<%
	}
%>
    if (fromoperation=="1") {
<%
         //TD4262 ������ʾ��Ϣ  ��ʼ
		 if(isShowPrompt.equals("true"))
		{
			 if(src.equals("submit")){
				 if(modeid>0 && "1".equals(ismode)){
%>
	                 contentBox = document.getElementById("divFavContent18982");
                     showObjectPopup(contentBox);
<%
				 }else{
%>
		             var content="<%=SystemEnv.getHtmlLabelName(18982,user.getLanguage())%>";
		             showPrompt(content);
<%
				 }
			 }else if(src.equals("reject")){
				 if(modeid>0 && "1".equals(ismode)){
%>
	                 contentBox = document.getElementById("divFavContent18983");
                     showObjectPopup(contentBox);
<%
				 }else {
%>
		             var content="<%=SystemEnv.getHtmlLabelName(18983,user.getLanguage())%>";
		             showPrompt(content);
<%
				 }
			}
		}
%>
		if( overtime!="1"){
			try{
				window.opener.btnWfCenterReload.onclick();
			}catch(e){}
		}
		
		try{
	    	parent.window.taskCallBack(2,<%=preisremark%>); 
		}catch(e){}
		
		<%if("1".equals(isShowChart)){%>
       		try{
   				<%if(isUseOldWfMode){%>
   				window.opener._table.reLoad();
   				<%}else{%>
   				window.opener.reLoad();
   				<%}%>
			}catch(e){}
			window.location.href="/workflow/request/WorkflowDirection.jsp?requestid=<%=requestid%>&workflowid=<%=workflowid%>&isbill=<%=isbill%>&formid=<%=formid%>";
		<%}else{%>
			try{
			<%if(isUseOldWfMode){%>
			window.opener._table.reLoad();
			<%}else{%>
			window.opener.reLoad();
			<%}%>
			}catch(e){}
			<%
				if("1".equals(fromoperation)){
					islog = false;
				}
			%>
			setTimeout("window.close()",1);
			<%}%>
    }
}
<%if(modeid<1 || "2".equals(ismode)){%>
function setwtableheight(){
    /*
    var totalheight=5;
    var bodyheight=document.body.clientHeight;
    if(document.getElementById("divTopTitle")!=null){
        totalheight+=document.getElementById("divTopTitle").clientHeight;
    }
    <%if (fromFlowDoc.equals("1")){%>
        totalheight+=100;
        bodyheight=parent.document.body.clientHeight;
    <%}%>
    document.getElementById("w_table").height=bodyheight-totalheight;
    */
}
window.onresize = function (){
    setwtableheight();
}
<%}%>
</script>
<%
if(islog){
if(isOldWf){//������ , ��� td2104 ��ǰ
	RecordSet.executeProc("workflow_RequestViewLog_Insert",requestid+"" + flag + userid+"" + flag + currentdate +flag + currenttime + flag + clientip + flag + usertype +flag + nodeid + flag + "9" + flag + -1);
	//������û���κεط�ʹ����ordertype='-1'�����������Դ˴�ֱ�Ӱ�-1�ĳ�9 by ben 2006-05-24 for TD439
	}
	else{
	int showorder = 10000;
	String orderType = "";
	RecordSet.executeSql("select agentorbyagentid, agenttype, showorder from workflow_currentoperator where userid = " + userid +
	" and nodeid = " + nodeid + " and requestid = " + requestid + " and isremark in ('0','1','4','5','8','9','7') and usertype = " + usertype);

	if(RecordSet.next()){
	  orderType = "1"; // ��ǰ�ڵ������
	  showorder  = RecordSet.getInt("showorder");
	}
	else{

	orderType = "2";// �ǵ�ǰ�ڵ������
	RecordSet2.executeSql("select max(showorder) from workflow_requestviewlog where id = " + requestid + "  and ordertype = '2' and currentnodeid = " + nodeid);
	RecordSet2.next();
	if(RecordSet2.getInt(1) != -1){
	showorder = RecordSet2.getInt(1) + 1;
	}
	}
	if(wfmonitor){
	    orderType ="3";//���̼���˲鿴
	}
	if(isurger){
	    orderType ="4";//���̶����˲鿴
	}
	RecordSet.executeProc("workflow_RequestViewLog_Insert",requestid+"" + flag + userid+"" + flag + currentdate +flag + currenttime + flag + clientip + flag + usertype +flag + nodeid + flag + orderType + flag + showorder);

	}
}
%>


<script language="javascript">
var isfirst = 0 ;

function displaydiv()
{
    if(oDivAll.style.display == ""){
		oDivAll.style.display = "none";
		oDivInner.style.display = "none";
        oDiv.style.display = "none";
        <%if(modeid>0 && "1".equals(ismode)){%> oDivSign.style.display = "none";<%}%>
        spanimage.innerHTML = "<img src='/images/ArrowDownRed.gif' border=0>" ;
    }
    else{
        if(isfirst == 0) {
			document.getElementById("picInnerFrame").src="/workflow/request/WorkflowRequestPictureInner.jsp?isview=1&fromFlowDoc=<%=fromFlowDoc%>&modeid=<%=modeid%>&requestid=<%=requestid%>&workflowid=<%=workflowid%>&nodeid=<%=nodeid%>&isbill=<%=isbill%>&formid=<%=formid%>&randpara=<%=System.currentTimeMillis()%>";				
			document.getElementById("picframe").src="/workflow/request/WorkflowRequestPicture.jsp?requestid=<%=requestid%>&desrequestid=<%=desrequestid%>&workflowid=<%=workflowid%>&nodeid=<%=nodeid%>&isbill=<%=isbill%>&formid=<%=formid%>&isurger=<%=isurger%>&randpara=<%=System.currentTimeMillis()%>";
            <%if(modeid>0 && "1".equals(ismode)){%> document.getElementById("picSignFrame").src="/workflow/request/WorkflowViewSignMode.jsp?isprint=true&languageid=<%=user.getLanguage()%>&userid=<%=userid%>&requestid=<%=requestid%>&workflowid=<%=workflowid%>&nodeid=<%=nodeid%>&isOldWf=<%=isOldWf%>&desrequestid=<%=desrequestid%>&ismonitor=<%=ismonitor%>&logintype=<%=logintype%>&randpara=<%=System.currentTimeMillis()%>";<%}%>
            isfirst ++ ;
        }

        spanimage.innerHTML = "<img src='/images/ArrowUpGreen.gif' border=0>" ;
		oDivAll.style.display = "";
		oDivInner.style.display = "";
        oDiv.style.display = "";
        workflowStatusLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(19678,user.getLanguage())%></font>";
        workflowChartLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(19676,user.getLanguage())%></font>";
        <%if(modeid>0 && "1".equals(ismode)){%>
        oDivSign.style.display = "";
        workflowSignLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(21200,user.getLanguage())%></font>";
        <%}%>
    }
}


function displaydivOuter()
{
    if(oDiv.style.display == ""){
        oDiv.style.display = "none";
        workflowStatusLabelSpan.innerHTML="<font color=red><%=SystemEnv.getHtmlLabelName(19677,user.getLanguage())%></font>";
		if(oDiv.style.display == "none"&&oDivInner.style.display == "none"<%if(modeid>0 && "1".equals(ismode)){%> &&oDivSign.style.display == "none"<%}%>){
		    oDivAll.style.display = "none";
            spanimage.innerHTML = "<img src='/images/ArrowDownRed.gif' border=0>" ;
		}
    }
    else{
        oDiv.style.display = "";
        workflowStatusLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(19678,user.getLanguage())%></font>";
    }
}

function displaydivInner()
{
    if(oDivInner.style.display == ""){
        oDivInner.style.display = "none";
        workflowChartLabelSpan.innerHTML="<font color=red><%=SystemEnv.getHtmlLabelName(19675,user.getLanguage())%></font>";
		if(oDiv.style.display == "none"&&oDivInner.style.display == "none"<%if(modeid>0 && "1".equals(ismode)){%> &&oDivSign.style.display == "none"<%}%>){
		    oDivAll.style.display = "none";
            spanimage.innerHTML = "<img src='/images/ArrowDownRed.gif' border=0>" ;
		}
    }
    else{
        oDivInner.style.display = "";
        workflowChartLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(19676,user.getLanguage())%></font>";
    }
}


</SCRIPT>
<body onload="windowOnload()">

<%@ include file="/systeminfo/TopTitle.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent.jsp" %>
<iframe id="triSubwfIframe" frameborder=0 scrolling=no src=""  style="display:none"></iframe>
<%
Prop prop = Prop.getInstance();
String ifchangstatus = Util.null2String(prop.getPropValue(GCONST.getConfigFile() , "ecology.changestatus"));
String sqlselectName = "select * from workflow_nodecustomrcmenu where wfid="+workflowid+" and nodeid="+nodeid;
 
if(!"0".equals(isremarkForRM)){
	RecordSet.executeSql("select nodeid from workflow_currentoperator c where c.requestid="+requestid+" and c.userid="+userid+" and c.usertype="+usertype+" and c.isremark='"+isremarkForRM+"' ");
	int tmpnodeid = 0;
	if(RecordSet.next()){
		tmpnodeid=Util.getIntValue(RecordSet.getString("nodeid"), 0);
	}
	if (tmpnodeid==0) tmpnodeid = nodeid;
	sqlselectName = "select * from workflow_nodecustomrcmenu where wfid="+workflowid+" and nodeid="+tmpnodeid;
}

RecordSet.executeSql(sqlselectName);
String forwardName = "";
String newWFName = "";//�½����̰�ť
String newSMSName = "";//�½����Ű�ť
String newCHATSName = "";//�½����Ű�ť
String ccsubnobackName = "";//������ע���跴��
String haswfrm = "";//�Ƿ�ʹ���½����̰�ť
String hassmsrm = "";//�Ƿ�ʹ���½����Ű�ť
String haschats = "";//�Ƿ�ʹ���½�΢�Ű�ť
String hasccnoback = "";//ʹ�ó�����ע���跴����ť
int t_workflowid = 0;//�½����̵�ID
if(RecordSet.next()){
	if(user.getLanguage() == 7){
		forwardName = Util.null2String(RecordSet.getString("forwardName7"));
		newWFName = Util.null2String(RecordSet.getString("newWFName7"));
		newSMSName = Util.null2String(RecordSet.getString("newSMSName7"));
		newCHATSName = Util.null2String(RecordSet.getString("newCHATSName7"));
		ccsubnobackName = Util.null2String(RecordSet.getString("ccsubnobackName7"));
	}
	else if(user.getLanguage() == 9){
		forwardName = Util.null2String(RecordSet.getString("forwardName9"));
		newWFName = Util.null2String(RecordSet.getString("newWFName9"));
		newSMSName = Util.null2String(RecordSet.getString("newSMSName9"));
		newCHATSName = Util.null2String(RecordSet.getString("newCHATSName9"));
		ccsubnobackName = Util.null2String(RecordSet.getString("ccsubnobackName9"));
	}
	else{
		forwardName = Util.null2String(RecordSet.getString("forwardName8"));
		newWFName = Util.null2String(RecordSet.getString("newWFName8"));
		newSMSName = Util.null2String(RecordSet.getString("newSMSName8"));
		newCHATSName = Util.null2String(RecordSet.getString("newCHATSName8"));
		ccsubnobackName = Util.null2String(RecordSet.getString("ccsubnobackName8"));
	} 
	haschats = Util.null2String(RecordSet.getString("haschats")); 
	haswfrm = Util.null2String(RecordSet.getString("haswfrm"));
	hassmsrm = Util.null2String(RecordSet.getString("hassmsrm"));
	hasccnoback = Util.null2String(RecordSet.getString("hasccnoback"));
	t_workflowid = Util.getIntValue(RecordSet.getString("workflowid"), 0);
}
if("".equals(forwardName)){
	forwardName = SystemEnv.getHtmlLabelName(6011,user.getLanguage());
}
if("".equals(ccsubnobackName)){
	ccsubnobackName = SystemEnv.getHtmlLabelName(615,user.getLanguage())+"��"+SystemEnv.getHtmlLabelName(21762,user.getLanguage())+"��";
}

%>
<%--xwj for td3665 20060224 begin--%>
<%
String strBar = "[";
//HashMap map = WfFunctionManageUtil.wfFunctionManageByNodeid(workflowid,nodeid);
//String ov = (String)map.get("ov");//�ܷ�ǿ�ƹ鵵���ݲ鿴�����ڽڵ��Ƿ���Ȩ��
HashMap map = WfFunctionManageUtil.wfFunctionManageByNodeid(workflowid,currentnodeid);
String rb = (String)map.get("rb");
//if(!"0".equals(rb)){//ǿ���ջ�:������̵�ǰ�ڵ�����"�鿴ǰ�ջ�"��"�鿴���ջ�"������һ�ڵ�Ĳ�������Ȩ���ջء� myq�޸� TD9348
//	RecordSet.executeSql("select * from workflow_NodeLink where nodeid="+nodeid+" and destnodeid="+currentnodeid+" and workflowid="+workflowid);
//	if(!RecordSet.next()) rb = "0";//������������ڽڵ㲻��ǿ���ջؽڵ����һ���ڵ㣬��û��ǿ���ջص�Ȩ�ޡ�
//}
//String ifremark=Util.null2String(WorkflowComInfo.getIsremark(workflowid+""));
//haveOverright=preisremark!=1 && preisremark!=5 && preisremark!=8 && preisremark!=9 && "1".equals(ov) && !"3".equals(currentnodetype) && WfForceOver.isNodeOperator(requestid,currentnodeid,userid);		//���Ӷ����̵�ǰ�����ڵ����͵��ж�TD9023
haveStopright = WfFunctionManageUtil.haveStopright(currentstatus,creater,user,currentnodetype,requestid,false);//���̲�Ϊ��ͣ���߳���״̬����ǰ�û�Ϊ���̷����˻���ϵͳ����Ա����������״̬��Ϊ�����͹鵵
haveCancelright = WfFunctionManageUtil.haveCancelright(currentstatus,creater,user,currentnodetype,requestid,false);//���̲�Ϊ����״̬����ǰ�û�Ϊ���̷����ˣ���������״̬��Ϊ�����͹鵵
haveRestartright = WfFunctionManageUtil.haveRestartright(currentstatus,creater,user,currentnodetype,requestid,false);//����Ϊ��ͣ���߳���״̬����ǰ�û�Ϊϵͳ����Ա����������״̬��Ϊ�����͹鵵
if(currentstatus>-1&&!haveCancelright&&!haveRestartright)
{
	String tips = "";
	if(currentstatus==0)
	{
		tips = SystemEnv.getHtmlLabelName(26154,user.getLanguage());//��������ͣ,�������̷����˻�ϵͳ����Ա��ϵ!
	}
	else
	{
		tips = SystemEnv.getHtmlLabelName(26155,user.getLanguage());//�����ѳ���,����ϵͳ����Ա��ϵ!
	}
%>
	<script language="JavaScript">
		var tips = '<%=tips%>';
		if(tips!="")
		{
			alert(tips);
		}
		//window.location.href="/notice/noright.jsp?isovertime=<%=isovertime%>";
		try
		{
			setTimeout('top.window.close()',1);
		}
		catch(e)
		{
			window.location.href="/notice/noright.jsp?isovertime=<%=isovertime%>";
		}
	</script>
<%
    return ;
}
haveBackright=(preisremark!=1 && preisremark!=5 && preisremark!=8 && preisremark!=9  || (preisremark==7 && !"2".equals(coadsigntype))) && !"0".equals(rb) && WfForceDrawBack.isHavePurview(requestid,userid,Integer.parseInt(logintype),-1,-1);
if(intervenorright>0){
//RCMenu += "{"+SystemEnv.getHtmlLabelName(615,user.getLanguage())+",javascript:doIntervenor(this),_self}" ;//Modified by xwj for td3247 20051201
//RCMenuHeight += RCMenuHeightStep ;
strBar += "{text: '"+SystemEnv.getHtmlLabelName(615,user.getLanguage())+"',iconCls:'btn_doIntervenor',handler: function(){bodyiframe.doIntervenor(this);}},";
}else{
if((preisremark!=8 || (preisremark==8 && isremarkForRM.equals("2"))) && !wfmonitor){
if (isurger)
{
//RCMenu += "{"+SystemEnv.getHtmlLabelName(21223,user.getLanguage())+",javascript:doSupervise(this),_self}" ;//Modified by xwj for td3247 20051201
//RCMenuHeight += RCMenuHeightStep ;
strBar += "{text: '"+SystemEnv.getHtmlLabelName(21223,user.getLanguage())+"',iconCls:'btn_Supervise',handler: function(){bodyiframe.doSupervise(this);}},";
}else{
	//�����˵�preisremark=2�����
	String agenttypetmp = "0";
	RecordSet.executeSql("SELECT * FROM workflow_currentoperator where id=" + wfcurrrid);
	if(RecordSet.next()) agenttypetmp = RecordSet.getString("AGENTTYPE");
if (WFForwardManager.getFMRight(haveRestartright,canview,IsCanSubmit,isrequest,IsSubmitForward,IsBeForward,preisremark,tempIsremark,agenttypetmp,currentnodetype,isPendingRemark))
{
new weaver.general.BaseBean().writeLog("---VR-1296-IsFromWFRemark_T---===="+IsFromWFRemark_T+"--IsBeForwardAlready--==="+IsBeForwardAlready+"----IsBeForward---==="+IsBeForward+"---canForwd---====="+canForwd+"----preisremark---=="+preisremark+"---IsPendingForward======="+IsPendingForward);
    if((preisremark==1&&canForwd)||(preisremark==9&&IsPendingForward.equals("1"))||(preisremark==0&&IsPendingForward.equals("1"))){
//RCMenu += "{"+forwardName+",javascript:doReview(),_self}" ;//Modified by xwj for td3247 20051201
//RCMenuHeight += RCMenuHeightStep ;	
strBar += "{text: '"+forwardName+"',iconCls:'btn_forward',handler: function(){bodyiframe.doReview();}},";
}
}
//if(haveOverright){
//RCMenu += "{"+SystemEnv.getHtmlLabelName(18360,user.getLanguage())+",javascript:doDrawBack(this),_self}" ;//xwj for td3665 20060224
//RCMenuHeight += RCMenuHeightStep ;
//strBar += "{text: '"+SystemEnv.getHtmlLabelName(18360,user.getLanguage())+"',iconCls:'btn_doDrawBack',handler: function(){bodyiframe.doDrawBack(this);}},";
//}
String isTriDiffWorkflow=null;
RecordSet.executeSql("select isTriDiffWorkflow from workflow_base where id="+workflowid);
if(RecordSet.next()){
	isTriDiffWorkflow=Util.null2String(RecordSet.getString("isTriDiffWorkflow"));
}

if(!"1".equals(isTriDiffWorkflow)){
	isTriDiffWorkflow="0";
}


                StringBuffer sb=new StringBuffer();
				if("1".equals(isTriDiffWorkflow)){
					sb.append("  select  ab.id as subwfSetId,c.id as buttonNameId,c.triSubwfName7,c.triSubwfName8 from ")
					  .append(" ( ")
					  .append("  select a.triggerType,b.nodeType,b.nodeId,a.triggerTime,a.fieldId ,a.id ")
					  .append("    from Workflow_TriDiffWfDiffField a,workflow_flownode b ")
					  .append("   where a.triggerNodeId=b.nodeId ")
					  .append("     and a.mainWorkflowId=b.workflowId ")
					  .append("     and a.mainWorkflowId=").append(workflowid)
					  .append("     and a.triggerNodeId=").append(nodeid)
					  .append("     and a.triggerType='2' ")
					  .append(" )ab left join  ")
					  .append(" ( ")
					  .append("   select *  ")
					  .append("     from Workflow_TriSubwfButtonName ")
					  .append("    where workflowId=").append(workflowid)
					  .append("      and nodeId=").append(nodeid)
					  .append("      and subwfSetTableName='Workflow_TriDiffWfDiffField' ")
					  .append(" )c on ab.id=c.subwfSetId ")
					  .append(" order by ab.triggerType asc,ab.nodeType asc,ab.nodeId asc,ab.triggerTime asc,ab.fieldId asc ,ab.id asc ")
					  ;
				}else{
					sb.append("  select  ab.id as subwfSetId,c.id as buttonNameId,c.triSubwfName7,c.triSubwfName8 from ")
					  .append(" ( ")
					  .append("  select a.triggerType,b.nodeType,b.nodeId,a.triggerTime,a.subWorkflowId ,a.id ")
					  .append("    from Workflow_SubwfSet a,workflow_flownode b ")
					  .append("   where a.triggerNodeId=b.nodeId ")
					  .append("     and a.mainWorkflowId=b.workflowId ")
					  .append("     and a.mainWorkflowId=").append(workflowid)
					  .append("     and a.triggerNodeId=").append(nodeid)
					  .append("     and a.triggerType='2' ")
					  .append(" )ab left join  ")
					  .append(" ( ")
					  .append("   select *  ")
					  .append("     from Workflow_TriSubwfButtonName ")
					  .append("    where workflowId=").append(workflowid)
					  .append("      and nodeId=").append(nodeid)
					  .append("      and subwfSetTableName='Workflow_SubwfSet' ")
					  .append(" )c on ab.id=c.subwfSetId ")
					  .append(" order by ab.triggerType asc,ab.nodeType asc,ab.nodeId asc,ab.triggerTime asc,ab.subWorkflowId asc,ab.id asc ")
					  ;
				}
				int subwfSetId=0;
				int buttonNameId=0;
				String triSubwfName7=null;
				String triSubwfName8=null;
				String triSubwfName=null;
				String trClass="datalight";
				int indexId=0;
				RecordSet.executeSql(sb.toString());
				while(RecordSet.next()){
					subwfSetId=Util.getIntValue(RecordSet.getString("subwfSetId"),0);
					buttonNameId=Util.getIntValue(RecordSet.getString("buttonNameId"),0);
					triSubwfName7=Util.null2String(RecordSet.getString("triSubwfName7"));
					triSubwfName8=Util.null2String(RecordSet.getString("triSubwfName8"));
					indexId++;
					triSubwfName="";
					if(user.getLanguage()==8){
						triSubwfName=triSubwfName8;
					}else{
						triSubwfName=triSubwfName7;
					}
					if(triSubwfName.equals("")){
						triSubwfName=SystemEnv.getHtmlLabelName(22064,user.getLanguage())+indexId;
					}
					//lihaibo start
					String finalsubworkflownameS="";
					if("1".equals(isTriDiffWorkflow)){
			        	finalsubworkflownameS = RequestTriDiffWfManager.getWorkFlowNameByisTriDiffWorkflow(requestid,subwfSetId,workflowid,nodeid);
					}else{
						finalsubworkflownameS = RequestTriDiffWfManager.getWorkFlowNameByDiffWorkflow(subwfSetId,workflowid);
					}
					strBar += "{text: '"+triSubwfName+"',iconCls:'btn_relateCwork',handler: function(){bodyiframe.triSubwf2("+subwfSetId+",\\\""+finalsubworkflownameS+"\\\");}},";
				}
%>
<%if(haveBackright){
//RCMenu += "{"+SystemEnv.getHtmlLabelName(18359,user.getLanguage())+",javascript:doRetract(this),_self}" ;//xwj for td3665 20060224
//RCMenuHeight += RCMenuHeightStep ;
strBar += "{text: '"+SystemEnv.getHtmlLabelName(18359,user.getLanguage())+"',iconCls:'btn_doRetract',handler: function(){bodyiframe.doRetract(this);}},";
}
}
}else if(preisremark==8 && !wfmonitor){//���Ͳ���Ҫ�ύҲ��ת����ťTD9144
	//if(!"".equals(ifchangstatus) && "1".equals(hasccnoback)){
		//RCMenu += "{"+ccsubnobackName+",javascript:doRemark_nNoBack(this),_self}";
		//RCMenuHeight += RCMenuHeightStep;
    //    strBar += "{text: '"+ccsubnobackName+"',iconCls:'btn_ccsubnobackName',handler: function(){bodyiframe.doRemark_nNoBack(this);}},";
	//}
	
	if (!haveRestartright&&((IsSubmitForward.equals("1")&&(preisremark==0||preisremark==8))||(IsBeForward.equals("1")&&preisremark==1))&&canview&&!isrequest.equals("1")&&isurger==false){
		//RCMenu += "{"+forwardName+",javascript:doReview(),_self}" ;//Modified by xwj for td3247 20051201
		//RCMenuHeight += RCMenuHeightStep ;	
		strBar += "{text: '"+forwardName+"',iconCls:'btn_forward',handler: function(){bodyiframe.doReview();}},";
	}
}
/* added by cyril on 2008-07-09 for TD:8835 **/
if(isModifyLog.equals("1") && preisremark>-1&&!isurger) {//TD10126
	//RCMenu += "{"+SystemEnv.getHtmlLabelName(21625,user.getLanguage())+",javascript:doViewModifyLog(),_self}" ;
	//RCMenuHeight += RCMenuHeightStep ;
	strBar += "{text: '"+SystemEnv.getHtmlLabelName(21625,user.getLanguage())+"',iconCls:'btn_doViewModifyLog',handler: function(){bodyiframe.doViewModifyLog();}},";
}
/* end by cyril on 2008-07-09 for TD:8835 **/
if(!isurger&&!wfmonitor){
/*TD9145 START*/
if("1".equals(haswfrm)){
	if("".equals(newWFName)){
		newWFName = SystemEnv.getHtmlLabelName(1239,user.getLanguage());
	}
	RequestCheckUser.setUserid(userid);
	RequestCheckUser.setWorkflowid(t_workflowid);
	RequestCheckUser.setLogintype(logintype);
	RequestCheckUser.checkUser();
	int  t_hasright=RequestCheckUser.getHasright();
	if(t_hasright == 1){
		//RCMenu += "{"+newWFName+",javascript:onNewRequest("+t_workflowid+", "+requestid+",0),_top} " ;
		//RCMenuHeight += RCMenuHeightStep ;
		strBar += "{text: '"+newWFName+"',iconCls:'btn_newWFName',handler: function(){bodyiframe.onNewRequest("+t_workflowid+", "+requestid+",0);}},";
	}
}
RTXConfig rtxconfig = new RTXConfig();
String temV = rtxconfig.getPorp(rtxconfig.CUR_SMS_SERVER_IS_VALID);
boolean valid = false;
if (temV != null && temV.equalsIgnoreCase("true")) {
	valid = true;
} else {
	valid = false;
}
if(valid == true && "1".equals(hassmsrm) && HrmUserVarify.checkUserRight("CreateSMS:View", user)){
	if("".equals(newSMSName)){
		newSMSName = SystemEnv.getHtmlLabelName(16444,user.getLanguage());
	}
	//RCMenu += "{"+newSMSName+",javascript:onNewSms("+workflowid+", "+nodeid+", "+requestid+"),_top} " ;
	//RCMenuHeight += RCMenuHeightStep ;
	strBar += "{text: '"+newSMSName+"',iconCls:'btn_newSMSName',handler: function(){bodyiframe.onNewSms("+workflowid+", "+nodeid+", "+requestid+");}},";
} 
if("1".equals(haschats) && WechatPropConfig.isUseWechat()){
	if("".equals(newCHATSName)){
		newCHATSName = SystemEnv.getHtmlLabelName(32818,user.getLanguage());
	} 	 
	strBar += "{text: '"+newCHATSName+"',iconCls:'btn_newSMSName',handler: function(){bodyiframe.onNewChats("+workflowid+", "+nodeid+", "+requestid+");}},";
}
/*TD9145 END*/
}
}
if(haveStopright)
{
	strBar += "{text: '"+SystemEnv.getHtmlLabelName(20387,user.getLanguage())+"',iconCls:'btn_end',handler: function(){bodyiframe.doStop(this);}},";
}
if(haveCancelright)
{
	strBar += "{text: '"+SystemEnv.getHtmlLabelName(16210,user.getLanguage())+"',iconCls:'btn_backSubscrible',handler: function(){bodyiframe.doCancel(this);}},";
}
if(haveRestartright)
{
	strBar += "{text: '"+SystemEnv.getHtmlLabelName(18095,user.getLanguage())+"',iconCls:'btn_next',handler: function(){bodyiframe.doRestart(this);}},";
}
if("1".equals(ismode) && modeid>0 && toexcel==1){
strBar += "{text: '"+SystemEnv.getHtmlLabelName(17416,user.getLanguage())+" Excel',iconCls:'btn_excel',handler: function(){bodyiframe.ToExcel();}},";
}
strBar += "{text: '"+SystemEnv.getHtmlLabelName(257,user.getLanguage())+"',iconCls:'btn_print',handler: function(){bodyiframe.openSignPrint();}},";
strBar = strBar.substring(0,strBar.lastIndexOf(","));
strBar+="]";
if(isonlyview == 1){
	strBar = "[]";
}
%>
<%--xwj for td3665 20060224 end--%>

<%@ include file="/systeminfo/RightClickMenu.jsp" %>
<%@ include file="/workflow/request/RequestShowHelpDoc.jsp" %>
        <input type=hidden name=seeflowdoc value="<%=seeflowdoc%>">
		<input type=hidden name=isworkflowdoc value="<%=isworkflowdoc%>">
        <input type=hidden name=wfdoc value="<%=wfdoc%>">
        <input type=hidden name=picInnerFrameurl value="/workflow/request/WorkflowRequestPictureInner.jsp?isview=1&fromFlowDoc=<%=fromFlowDoc%>&modeid=<%=modeid%>&requestid=<%=requestid%>&workflowid=<%=workflowid%>&nodeid=<%=nodeid%>&isbill=<%=isbill%>&formid=<%=formid%>&randpara=<%=System.currentTimeMillis()%>">
		<input type=hidden name=statInnerFrameurl value="WorkflowRequestPicture.jsp?hasExt=true&requestid=<%=requestid%>&workflowid=<%=workflowid%>&nodeid=<%=nodeid%>&isbill=<%=isbill%>&formid=<%=formid%>&desrequestid=<%=desrequestid %>&isurger=<%=isurger%>&randpara=<%=System.currentTimeMillis()%>">

		<%@ include file="/workflow/request/NewRequestFrame.jsp" %>
		<TABLE width=100%>
			<tr>
				<td valign="top">
		        <%if( message.equals("4") ) {%>
		        <font color=red><%=SystemEnv.getHtmlLabelName(21266,user.getLanguage())%></font>
				<%} else if( message.equals("5") ) {%>
		        <font color=red><%=SystemEnv.getHtmlLabelName(21270,user.getLanguage())%></font>
				<%} else if( message.equals("6") ) {%>
                <font color=red><%=SystemEnv.getHtmlLabelName(21766,user.getLanguage())%></font>
                <%} else if( message.equals("7") ) {%>
                <font color=red><%=SystemEnv.getHtmlLabelName(22751,user.getLanguage())%></font>
                <%} else if( message.equals("8") ) {%>
                <font color=red><%=SystemEnv.getHtmlLabelName(24676,user.getLanguage())%></font>
                <%}%>
		
				<!--TD4262 ������ʾ��Ϣ  ��ʼ-->
				<%
				    if(modeid>0 && "1".equals(ismode)){
				%>
				<div id="divFavContent18982" style="display:none">  <!--�����ύ�ɹ���-->
					<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%">
						<tr height="22">
							<td style="font-size:9pt"><%=SystemEnv.getHtmlLabelName(18982,user.getLanguage())%>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="divFavContent18983" style="display:none"> <!--�����˻سɹ���-->
					<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%">
						<tr height="22">
							<td style="font-size:9pt"><%=SystemEnv.getHtmlLabelName(18983,user.getLanguage())%>
							</td>
						</tr>
					</table>
				</div>
				<div id="divFavContent19205" style="display:none"> <!--���ڻ�ȡ���ݡ�-->
					<table cellspacing="0" cellpadding="0" border="0" width="100%" height="100%">
						<tr height="22">
							<td style="font-size:9pt"><%=SystemEnv.getHtmlLabelName(19205,user.getLanguage())%>
							</td>
						</tr>
					</table>
				</div>
				<%
				    }else{
				%>
				<div id='_xTable' style='background:#FFFFFF;padding:3px;width:100%' valign='top'>
				</div>
				<%
					}
				%>
				<!--TD4262 ������ʾ��Ϣ  ����-->
				
				</td>
			</tr>
		</table>
</body>
</html>

<script language="JavaScript">

//TD4262 ������ʾ��Ϣ  ��ʼ
//��ʾ����
<%
  if(modeid>0 && "1".equals(ismode)){
%>
var oPopup;
try{
    oPopup = window.createPopup();
}catch(e){}
function showObjectPopup(contentBox){
  try{ 
  var iX=document.body.offsetWidth/2-50;
	var iY=document.body.offsetHeight/2+document.body.scrollTop-50;

	var oPopBody = oPopup.document.body;
  oPopBody.style.border = "1px solid #8888AA";
  oPopBody.style.backgroundColor = "white";
  oPopBody.style.position = "absolute";
  oPopBody.style.padding = "0px";
  oPopBody.style.zindex = 150;

  oPopBody.innerHTML = contentBox.innerHTML;

  oPopup.show(iX, iY, 180, 22, document.body);
 }catch(e){} 

}
function displaydivSign()
{
  if(oDivSign.style.display == ""){
      oDivSign.style.display = "none";
      workflowSignLabelSpan.innerHTML="<font color=red><%=SystemEnv.getHtmlLabelName(21199,user.getLanguage())%></font>";
		if(oDiv.style.display == "none"&&oDivInner.style.display == "none"&&oDivSign.style.display == "none"){
		    oDivAll.style.display = "none";
          spanimage.innerHTML = "<img src='/images/ArrowDownRed.gif' border=0>" ;
		}
  }
  else{
      oDivSign.style.display = "";
      workflowSignLabelSpan.innerHTML="<font color=green><%=SystemEnv.getHtmlLabelName(21200,user.getLanguage())%></font>";
  }
}
function ajaxinit(){
  var ajax=false;
  try {
      ajax = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e) {
      try {
          ajax = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (E) {
          ajax = false;
      }
  }
  if (!ajax && typeof XMLHttpRequest!='undefined') {
  ajax = new XMLHttpRequest();
  }
  return ajax;
}
function showallreceived(requestid,viewLogIds,operator,operatedate,operatetime,returntdid,logtype,destnodeid){
  <%
  if(modeid>0 && "1".equals(ismode)){
  %>
  showObjectPopup(document.getElementById("divFavContent19205"));
  <%}else{%>
  showPrompt("<%=SystemEnv.getHtmlLabelName(19205,user.getLanguage())%>");
  <%}%>
  var ajax=ajaxinit();
  ajax.open("POST", "/workflow/request/WorkflowReceiviedPersons.jsp", true);
  ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
  ajax.send("requestid="+requestid+"&viewnodeIds="+viewLogIds+"&operator="+operator+"&operatedate="+operatedate+"&operatetime="+operatetime+"&returntdid="+returntdid+"&logtype="+logtype+"&destnodeid="+destnodeid);
  //��ȡִ��״̬
  ajax.onreadystatechange = function() {
      //���ִ��״̬�ɹ�����ô�Ͱѷ�����Ϣд��ָ���Ĳ���
      if (ajax.readyState == 4 && ajax.status == 200) {
          try{
          document.getElementById(returntdid).innerHTML = ajax.responseText;
          }catch(e){}
          hiddenPop();
      }
  }
}
function displaydiv_1()
{
  if(WorkFlowDiv.style.display == ""){
      WorkFlowDiv.style.display = "none";
      //WorkFlowspan.innerHTML = "<a href='javascript:void(0);' onClick=displaydiv_1() target=_self><%=SystemEnv.getHtmlLabelName(332,user.getLanguage())%></a>";
		WorkFlowspan.innerHTML = "<span style='cursor:hand;color: blue; text-decoration: underline' onClick='displaydiv_1()' ><%=SystemEnv.getHtmlLabelName(332,user.getLanguage())%></span>";
  }
  else{
      WorkFlowDiv.style.display = "";
      //WorkFlowspan.innerHTML = "<a href='javascript:void(0);' onClick=displaydiv_1() target=_self><%=SystemEnv.getHtmlLabelName(15154,user.getLanguage())%></a>";
		WorkFlowspan.innerHTML = "<span style='cursor:hand;color: blue; text-decoration: underline' onClick='displaydiv_1()' ><%=SystemEnv.getHtmlLabelName(15154,user.getLanguage())%></span>";
  }
}
function openSignPrint() {
var redirectUrl = "PrintRequest.jsp?requestid=<%=requestid%>&isprint=1&fromFlowDoc=1&urger=<%=urger%>&ismonitor=<%=ismonitor%>" ;
<%//���������̴�ӡȨ������  
if(wflinkno>=0){
%>
redirectUrl = "PrintRequest.jsp?requestid=<%=requestid%>&isprint=1&fromFlowDoc=1&isrequest=1&wflinkno=<%=wflinkno%>&urger=<%=urger%>&ismonitor=<%=ismonitor%>";
<%}%>  
var width = screen.width ;
var height = screen.height ;
if (height == 768 ) height -= 75 ;
if (height == 600 ) height -= 60 ;
var szFeatures = "top=0," ;
szFeatures +="left=0," ;
szFeatures +="width="+width+"," ;
szFeatures +="height="+height+"," ;
szFeatures +="directories=no," ;
szFeatures +="status=yes," ;
szFeatures +="menubar=no," ;
szFeatures +="toolbar=yes," ;
szFeatures +="scrollbars=yes," ;

szFeatures +="resizable=yes" ; //channelmode
window.open(redirectUrl,"",szFeatures) ;
}        
<%
  }
%>
//TD4262 ������ʾ��Ϣ  ����

	var bodyiframeurl = location.href.substring(location.href.indexOf("ViewRequest.jsp?")+16);
	function setbodyiframe(){
		document.getElementById("bodyiframe").src="ViewRequestIframe.jsp?"+bodyiframeurl;
		initNewRequestFrame();
		eventPush(document.getElementById('bodyiframe'),'load',loadcomplete);		
	}
	//window.attachEvent("onload", setbodyiframe);
	if (window.addEventListener){
	    window.addEventListener("load", setbodyiframe, false);
	}else if (window.attachEvent){
	    window.attachEvent("onload", setbodyiframe);
	}else{
	    window.onload=setbodyiframe;
	}

    var wftitle="<%=titlename%>";
	var isfromtab=<%=isfromtab%>;	
	var bar=eval("<%=strBar%>");
	if("<%=seeflowdoc%>"=="1"){
		bar = eval("[]");
		if(document.getElementById("rightMenu")!=null){
			document.getElementById("rightMenu").style.display="none";
		}
	}
</script>