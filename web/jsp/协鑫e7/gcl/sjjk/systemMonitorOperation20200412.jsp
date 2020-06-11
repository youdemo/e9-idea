<%@ page language="java" contentType="text/html; charset=GBK" %>
 <%@ include file="/systeminfo/init.jsp" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.general.TimeUtil"%>
<%@ page import="weaver.systeminfo.SystemEnv" %>
<%@ page import="gcl.sjjk.SjUtil" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="WorkflowComInfo" class="weaver.workflow.workflow.WorkflowComInfo" scope="page"/>
<jsp:useBean id="CheckSubCompanyRight" class="weaver.systeminfo.systemright.CheckSubCompanyRight" scope="page" />

<%
int userid=user.getUID();
SjUtil sjUtil = new SjUtil();
String tablename_sz = sjUtil.getBillTableName("LCSJSZ");
String tablename_jl = sjUtil.getBillTableName("SJJKJLB");
int wfid = 0;
boolean flag = true;
String currentDate=TimeUtil.getCurrentDateString();
String currentTime=(TimeUtil.getCurrentTimeString()).substring(11,19);
String[] value;
String[] value1;
char separ = Util.getSeparator();
String Procpara = "";

    String actionKey = Util.null2String(request.getParameter("actionKey"));
    String pzid = Util.null2String(request.getParameter("pzid"));
    String sjry = Util.null2String(request.getParameter("sjry"));
    String sjdx=Util.null2String(request.getParameter("sjdx"));
    String ksrq=Util.null2String(request.getParameter("ksrq"));
    String jsrq=Util.null2String(request.getParameter("jsrq"));
    int ischeckall = Util.getIntValue(request.getParameter("checkall"),0);
    int detachable = Util.getIntValue(request.getParameter("detachable"),0);
    int isview=0;
    int isintervenor=0;
if("add".equals(actionKey)){
    try{
	String typeid="-1";
	String typeidtemp="-1";
	String wfids="-1";
	String sql = "insert into "+tablename_sz+"(sjry,sjdx,ksrq,jsrq) values('"+sjry+"','"+sjdx+"','"+ksrq+"','"+jsrq+"')";
	rs.executeSql(sql);
	sql = "select max(id) as pzid from "+tablename_sz+" where to_char(sjry)='"+sjry+"' and sjdx='"+sjdx+"' and ksrq='"+ksrq+"' and jsrq='"+jsrq+"'";
	rs.executeSql(sql);
	if(rs.next()){
        pzid = Util.null2String(rs.getString("pzid"));
    }
      for(Enumeration En=request.getParameterNames();En.hasMoreElements();){
			//out.print((String) En.nextElement()+"<br>");
            value=request.getParameterValues((String) En.nextElement());
             for(int i=0;i<value.length;i++){
              value[i]=Util.null2String(value[i]);
              if(value[i].indexOf("W")==0){
                 wfid = Integer.parseInt(value[i].substring(1,value[i].length()));

                 
                 rs.executeSql("insert into "+tablename_jl+"(monitorhrmid,workflowid,operatordate,operatortime,isview,isintervenor,isdelete,isForceDrawBack,isForceOver,issooperator,operator,monitortype,subcompanyid,sjszid) values ('','"+wfid+"','"+currentDate+"','"+currentTime+"','1','0','0','0','0','0',"+userid+",'','','"+pzid+"')");
               }
             }
           }


    //rs.executeSql("update workflow_monitor_bound set monitortype="+monitortypeid+",subcompanyid=(select subcompanyid from workflow_base where id = workflow_monitor_bound.workflowid) where monitorhrmid="+monitorhrmid);
   }
   catch(Exception e){
    flag = false;
	//out.print(e.toString());
   }
    if(flag){
     response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
     return;
    }
    else{
    response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
     return;
    }

}
    if("add".equals(actionKey)){
        try{
            String typeid="-1";
            String typeidtemp="-1";
            String wfids="-1";
            String sql = "insert into "+tablename_sz+"(sjry,sjdx,ksrq,jsrq) values('"+sjry+"','"+sjdx+"','"+ksrq+"','"+jsrq+"')";
            rs.executeSql(sql);
            sql = "select max(id) as pzid from "+tablename_sz+" where to_char(sjry)='"+sjry+"' and sjdx='"+sjdx+"' and ksrq='"+ksrq+"' and jsrq='"+jsrq+"'";
            rs.executeSql(sql);
            if(rs.next()){
                pzid = Util.null2String(rs.getString("pzid"));
            }
            for(Enumeration En=request.getParameterNames();En.hasMoreElements();){
                //out.print((String) En.nextElement()+"<br>");
                value=request.getParameterValues((String) En.nextElement());
                for(int i=0;i<value.length;i++){
                    value[i]=Util.null2String(value[i]);
                    if(value[i].indexOf("W")==0){
                        wfid = Integer.parseInt(value[i].substring(1,value[i].length()));


                        rs.executeSql("insert into "+tablename_jl+"(monitorhrmid,workflowid,operatordate,operatortime,isview,isintervenor,isdelete,isForceDrawBack,isForceOver,issooperator,operator,monitortype,subcompanyid,sjszid) values ('','"+wfid+"','"+currentDate+"','"+currentTime+"','1','0','0','0','0','0',"+userid+",'','','"+pzid+"')");
                    }
                }
            }


            //rs.executeSql("update workflow_monitor_bound set monitortype="+monitortypeid+",subcompanyid=(select subcompanyid from workflow_base where id = workflow_monitor_bound.workflowid) where monitorhrmid="+monitorhrmid);
        }
        catch(Exception e){
            flag = false;
            //out.print(e.toString());
        }
        if(flag){
            response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
            return;
        }
        else{
            response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
            return;
        }

    }else if("edit".equals(actionKey)){
        try{
            if(!"".equals(pzid)) {
                rs.executeSql("update "+tablename_sz+" set sjry='" + sjry + "',sjdx='" + sjdx + "',ksrq='" + ksrq + "',jsrq='" + jsrq + "' where id=" + pzid);


                rs.executeSql("delete from "+tablename_jl+" where sjszid='" + pzid + "'");

                for (Enumeration En = request.getParameterNames(); En.hasMoreElements(); ) {
                    //out.print((String) En.nextElement()+"<br>");
                    value = request.getParameterValues((String) En.nextElement());
                    for (int i = 0; i < value.length; i++) {
                        value[i] = Util.null2String(value[i]);
                        if (value[i].indexOf("W") == 0) {
                            wfid = Integer.parseInt(value[i].substring(1, value[i].length()));


                            rs.executeSql("insert into "+tablename_jl+"(monitorhrmid,workflowid,operatordate,operatortime,isview,isintervenor,isdelete,isForceDrawBack,isForceOver,issooperator,operator,monitortype,subcompanyid,sjszid) values ('','" + wfid + "','" + currentDate + "','" + currentTime + "','1','0','0','0','0','0'," + userid + ",'','','" + pzid + "')");
                        }
                    }
                }
            }


            //rs.executeSql("update workflow_monitor_bound set monitortype="+monitortypeid+",subcompanyid=(select subcompanyid from workflow_base where id = workflow_monitor_bound.workflowid) where monitorhrmid="+monitorhrmid);
        }
        catch(Exception e){
            flag = false;
            //out.print(e.toString());
        }
        if(flag){
            response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
            return;
        }
        else{
            response.sendRedirect("/gcl/sjjk/workflowsjMonitorSet.jsp?pzid="+pzid+"&actionKey=edit");
            return;
        }

    }else if("del".equals(actionKey)){
        rs.executeSql("delete from "+tablename_sz+" where id='" + pzid + "'");
        rs.executeSql("delete from "+tablename_jl+" where sjszid='" + pzid + "'");
        out.print("S");
        return;
    }

%>