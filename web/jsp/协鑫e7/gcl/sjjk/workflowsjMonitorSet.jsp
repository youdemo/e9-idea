<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.general.GCONST" %>
<%@ page import="gcl.sjjk.SjUtil" %>
<%@ include file="/systeminfo/init.jsp" %>
<jsp:useBean id="WorkflowComInfo" class="weaver.workflow.workflow.WorkflowComInfo" scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page"/>
<jsp:useBean id="WorkTypeComInfo" class="gcl.sjjk.WorkTypeComInfo" scope="page" />

<jsp:useBean id="compInfo" class="weaver.hrm.company.CompanyComInfo" scope="page" />
<jsp:useBean id="subCompInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />


<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />

<HTML><HEAD>
<LINK href="/css/Weaver.css" type="text/css" rel="STYLESHEET">
<link type="text/css" rel="stylesheet" href="/js/xloadtree/xtree.css">
<style>
TABLE.Shadow A {
	COLOR: #333; TEXT-DECORATION: none
}
TABLE.Shadow A:hover {
	COLOR: #333; TEXT-DECORATION: none
}

TABLE.Shadow A:link {
	COLOR: #333; TEXT-DECORATION: none
}
TABLE.Shadow A:visited {
	TEXT-DECORATION: none
}
</style>
<SCRIPT language="javascript" src="/js/weaver.js"></SCRIPT>
<script type="text/javascript" src="/gcl/sjjk/xtree4workflowMonitorSet.js?<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="/js/xloadtree/xloadtree4workflow.js"></script>
<script type="text/javascript" src="/js/xmlextras.js"></script>
</HEAD>

<%


String wfHomeIcon = "/images/treeimages/Home.gif";
boolean wfintervenor= false;
%>




<%
int userid=user.getUID();
String pzid=Util.null2String(request.getParameter("pzid"));
String actionKey=Util.null2String(request.getParameter("actionKey"));
String sjry="";
String sjdx="";
String sjdxspan = "";
String ksrq="";
String jsrq="";
String imagefilename = "/images/hdMaintenance.gif";
String titlename = SystemEnv.getHtmlLabelName(16758,user.getLanguage())+SystemEnv.getHtmlLabelName(68,user.getLanguage());
SjUtil sjUtil = new SjUtil();
String tablename = sjUtil.getBillTableName("LCSJSZ");
    String sjjsid = sjUtil.getWfid("LCSJSZJS");
    int count = 0;
    if(userid != 1){
        String sqlqx = "select count(1) as count from hrmrolemembers where roleid="+sjjsid+" and resourceid="+userid;
        rs.executeSql(sqlqx);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count<=0){
            response.sendRedirect("/notice/noright.jsp");
            return;
        }

    }
String needhelp ="";

int monitor=userid;
String oldtypeid = Util.null2String(request.getParameter("oldtypeid"));
String typeid = Util.null2String(request.getParameter("typeid"));
typeid = (Util.getIntValue(typeid,0)<=0)?"":typeid;
if(!"".equals(pzid))
{
	rs.executeSql("select * from "+tablename+" where id = "+pzid);
	if(rs.next())
	{
        sjry = Util.null2String(rs.getString("sjry"));
        sjdx = Util.null2String(rs.getString("sjdx"));
        ksrq = Util.null2String(rs.getString("ksrq"));
        jsrq = Util.null2String(rs.getString("jsrq"));
	}
	String flag = "";
	String sjdxarr[] = sjdx.split(",");
	for(String jsid:sjdxarr){
	    rs.executeSql("select rolesname from hrmroles where id="+jsid);
	    if(rs.next()){
	        sjdxspan = sjdxspan +flag+Util.null2String(rs.getString("rolesname"));
            flag="  ";
        }
    }


}
if(Util.getIntValue(oldtypeid,0)<=0&&Util.getIntValue(typeid,0)>0)
{
	oldtypeid = typeid;
}
String subcompanyid = "";
int detachable=1;

%>

<body>
<%@ include file="/systeminfo/TopTitle.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent.jsp" %>

<%
RCMenu += "{"+SystemEnv.getHtmlLabelName(86,user.getLanguage())+",javascript:doSave(this),_self} " ;
RCMenuHeight += RCMenuHeightStep ;
RCMenu += "{"+SystemEnv.getHtmlLabelName(16216,user.getLanguage())+",javascript:goexpandall(),_self} " ;
RCMenuHeight += RCMenuHeightStep ;
RCMenu += "{"+SystemEnv.getHtmlLabelName(18466,user.getLanguage())+",javascript:docollapseall(),_self} " ;
RCMenuHeight += RCMenuHeightStep ;
//RCMenu += "{"+SystemEnv.getHtmlLabelName(1290,user.getLanguage())+",javascript:goBack(),_self} " ;
//RCMenuHeight += RCMenuHeightStep ;
%>

<%@ include file="/systeminfo/RightClickMenu.jsp" %>

<FORM name="frmmain" action="/gcl/sjjk/systemMonitorOperation.jsp" method="post">
<input type=hidden name="actionKey" id ="actionKey" value="<%=actionKey%>">
<INPUT type="hidden" name="oldmonitortypeid"  id ="oldmonitortypeid" value=<%=typeid%>>
    <INPUT type="hidden" name="pzid"  id ="pzid" value="<%=pzid%>">
  <TABLE class="ViewForm">
  <COLGROUP>
  <COL width="20%">
  <COL width="80%">
  <TBODY>   

  <TR style="height:1px;"><TD class="Line" colSpan="2"></TD></TR>

    <!--代理人-->
  <TR>
      <TD >审计人员</TD>
      <td>
          <button class=Browser type="button" onClick="onShowResource()"></button>
          <span id=sjryspan><%=Util.toScreen(ResourceComInfo.getLastnames(sjry), user.getLanguage())%></span>
          <input class=inputstyle id=sjry type=hidden name=sjry value="<%=sjry%>">
      </td>
  </TR>
  <TR style="height:1px;"><TD class="Line" colSpan="2"></TD></TR>
  <TR>
      <TD >审计对象</TD>
      <td>
          <button class=Browser type="button" onClick="onShowjs()"></button>
          <span id=sjdxspan><%=sjdxspan%></span>
          <input class=inputstyle id=sjdx type=hidden name=sjdx value="<%=sjdx%>">
      </td>
  </TR>
  <TR style="height:1px;"><TD class="Line" colSpan="2"></TD></TR>
  <TR>
      <TD>审计周期</TD>
      <TD>
          <BUTTON type="button" class=calendar id=SelectDate1
                  onclick="gettheDate(ksrq,ksrqspan)"></BUTTON>
          <SPAN id=ksrqspan><%=ksrq%></SPAN>
          <input type="hidden" name="ksrq" value="<%=ksrq%>">
          -&nbsp;&nbsp;
          <BUTTON type="button" class=calendar id=SelectDate onclick="gettheDate(jsrq,jsrqspan)"></BUTTON>
          <SPAN id=jsrqspan><%=jsrq%></SPAN>
          <input type="hidden" name="jsrq" value="<%=jsrq%>">
      </TD>
  </TR>

  </TBODY>
  </TABLE>
  
<SPAN id="wfspan">
<TABLE class=ListStyle cellspacing=1 width="100%">
<TR class=Header>
	<TH width="50%"><%=SystemEnv.getHtmlLabelName(2118,user.getLanguage())%></TH>
	<th width="25%"><INPUT type=checkbox name="checkall" id="checkall" value="1" onclick="onCheckAll(this);"><%=SystemEnv.getHtmlLabelName(17989,user.getLanguage())%></th>
    <th width="25%"></th>
</TR>
<TR class=Line style="height:1px;">
	<TD colSpan=3></TD>
</TR>
<tr>
	<td colspan="3" id="treeTD">
	<script type="text/javascript">
	isOpenWorkflowStopOrCancel="0"; //是否开启流程暂停或撤销设置
	webFXTreeConfig.blankIcon		= "/images/xp2/blank.png";
	webFXTreeConfig.lMinusIcon		= "/images/xp2/Lminus.png";
	webFXTreeConfig.lPlusIcon		= "/images/xp2/Lplus.png";
	webFXTreeConfig.tMinusIcon		= "/images/xp2/Tminus.png";
	webFXTreeConfig.tPlusIcon		= "/images/xp2/Tplus.png";
	webFXTreeConfig.iIcon			= "/images/xp2/I.png";
	webFXTreeConfig.lIcon			= "/images/xp2/L.png";
	webFXTreeConfig.tIcon			= "/images/xp2/T.png";
    webFXTreeConfig.usePersistence=false;
    webFXTreeConfig.wfintervenor=<%=wfintervenor%>;

    var rti;
	var tree = new WebFXTree('<%=SystemEnv.getHtmlLabelName(2118,user.getLanguage())%>','','','<%=wfHomeIcon%>','<%=wfHomeIcon%>');
	
	<%out.println(WorkTypeComInfo.getWFTypeInfo(pzid,""+userid,Util.getIntValue(oldtypeid,0),Util.getIntValue(subcompanyid,0),detachable));%>
	document.write(tree);
	//rti.expand();
	</script>
	</td>
</tr>
</table>
</span>
<BR>
<BR>

</FORM>
</body>

<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker.js"></script>
<script type="text/javascript">
    function onShowResource() {
        data = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/hrm/resource/MutiResourceBrowser.jsp?selectedids=" + jQuery("#sjry").val());
        if (data != null) {
            if (data.id != "") {
                ids = data.id.split(",");
                names = data.name.split(",");
                sHtml = "";
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != "") {
                        sHtml = sHtml + names[i] + "&nbsp;&nbsp;";
                    }
                }
                jQuery("#sjryspan").html(sHtml);
                jQuery("input[name=sjry]").val(data.id.substr(1));
            } else {
                jQuery("#sjryspan").html("");
                jQuery("input[name=sjry]").val("");
            }
        }
    }
    function onShowjs() {
        data = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/hrm/roles/MutiRolesBrowser.jsp?resourceids=" + jQuery("#sjdx").val());
        if (data != null) {
            if (data.id != "") {
                ids = data.id.split(",");
                names = data.name.split(",");
                sHtml = "";
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != "") {
                        sHtml = sHtml + names[i] + "&nbsp;&nbsp;";
                    }
                }
                jQuery("#sjdxspan").html(sHtml);
                jQuery("input[name=sjdx]").val(data.id.substr(1));
            } else {
                jQuery("#sjdxspan").html("");
                jQuery("input[name=sjdx]").val("");
            }
        }
    }

var expandall=false;
function doSave(obj){
	var isCheck = false;
	var len = document.frmmain.elements.length;
    var i=0;
    for( i=0; i<len; i++) {
        //if (document.frmmain.elements[i].name.indexOf('w')==0) {
    		if(document.frmmain.elements[i].checked==true) {
        		//alert(document.frmmain.elements[i].name);
        		isCheck = true;
        		break;
    		}
        //}
    }
   if(!isCheck) {
	   alert('<%=SystemEnv.getHtmlLabelName(22442,user.getLanguage())%>!');
	   return;
   }
   var sjry_val = $("#sjry").val();
   var sjdx_val =  $("#sjdx").val();
   var ksrq_val =  $("#ksrq").val();
   var jsrq_val =  $("#jsrq").val();
   if(sjry_val == ""||sjdx_val == ""||ksrq_val == ""||jsrq_val == ""){
       alert("审计人员，审计对象，审计周期为必填项");
       return;
   }else{
       window.document.frmmain.submit();
       obj.disabled = true;
   }

}




function checkMain(id) {
	/*
    len = document.frmmain.elements.length;
    var mainchecked=document.all("t"+id).checked ;
    if(!mainchecked){
        document.all("vt"+id).checked=mainchecked;
        document.all("it"+id).checked=mainchecked;
        document.all("dt"+id).checked=mainchecked;
        document.all("bt"+id).checked=mainchecked;
        document.all("ot"+id).checked=mainchecked;
        document.all("st"+id).checked=mainchecked;
        document.all("checkall").checked=mainchecked;
        document.all("viewcheckall").checked=mainchecked;
        document.all("intervenorcheckall").checked=mainchecked;
        document.all("delcheckall").checked=mainchecked;
        document.all("fbcheckall").checked=mainchecked;
        document.all("focheckall").checked=mainchecked;
        document.all("socheckall").checked=mainchecked;
    }
    var i=0;
    for( i=0; i<len; i++) {
        if (document.frmmain.elements[i].name=='w'+id || (!mainchecked && (document.frmmain.elements[i].name=='vw'+id || document.frmmain.elements[i].name=='iw'+id || document.frmmain.elements[i].name=='dw'+id || document.frmmain.elements[i].name=='bw'+id || document.frmmain.elements[i].name=='ow'+id|| document.frmmain.elements[i].name=='sw'+id))) {
            document.frmmain.elements[i].checked= mainchecked ;
        } 
    }
    */
    
    var mainchecked=jQuery("input[name='t" + id + "']")[0].checked;
    // if(!mainchecked){
    //     jQuery("input[name='vt"+id + "']")[0].checked=false;
    //     jQuery("input[name='it"+id + "']")[0].checked=false;
    //     jQuery("input[name='dt"+id + "']")[0].checked=false;
    //     jQuery("input[name='bt"+id + "']")[0].checked=false;
    //     jQuery("input[name='ot"+id + "']")[0].checked=false;
    //     jQuery("input[name='st"+id + "']")[0].checked=false;
    //     jQuery("input[name='checkall" + "']")[0].checked=false;
    //     jQuery("input[name='viewcheckall" + "']")[0].checked=false;
    //     jQuery("input[name='intervenorcheckall" + "']")[0].checked=false;
    //     jQuery("input[name='delcheckall" + "']")[0].checked=false;
    //     jQuery("input[name='fbcheckall" + "']")[0].checked=false;
    //     jQuery("input[name='focheckall" + "']")[0].checked=false;
    //     jQuery("input[name='socheckall" + "']")[0].checked=false;
    // }
	try{
    	jQuery("input[name='w" + id + "']")
	        .each(function(){
	        	this.checked = mainchecked;
	        });

	}catch(e){}
    
}

function checkSub(obj,wfid,id) {
	/*
    len = document.frmmain.elements.length;
    var i=0;
    if(!obj.checked){
    document.all("checkall").checked=obj.checked;
    document.all("viewcheckall").checked=obj.checked;
    document.all("intervenorcheckall").checked=obj.checked;
    document.all("delcheckall").checked=obj.checked;
    document.all("fbcheckall").checked=obj.checked;
    document.all("focheckall").checked=obj.checked;
    document.all("socheckall").checked=obj.checked;
    for( i=0; i<len; i++) {
        if (document.frmmain.elements[i].value=="VW"+wfid||document.frmmain.elements[i].value=="IW"+wfid||document.frmmain.elements[i].value=="DW"+wfid||document.frmmain.elements[i].value=="BW"+wfid||document.frmmain.elements[i].value=="OW"+wfid||document.frmmain.elements[i].value=="SW"+wfid) {
            document.frmmain.elements[i].checked=false;
        }
    }
    }
    for( i=0; i<len; i++) {
        if (document.frmmain.elements[i].name=='w'+id) {
            if(document.frmmain.elements[i].checked){
                document.all("t"+id).checked=true;
                return;
            }
        }
    }
    document.all("t"+id).checked=false;
    document.all("vt"+id).checked=false;
    document.all("it"+id).checked=false;
    document.all("dt"+id).checked=false;
    document.all("bt"+id).checked=false;
    document.all("ot"+id).checked=false;
    document.all("st"+id).checked=false;
    */
    
    if(!obj.checked){
	    document.all("checkall").checked=obj.checked;


    }
    
    var wObjects = jQuery("input[name='w" + id + "']");
    for(var i = 0; i < wObjects.length; i++){
        
        if(wObjects[i].checked){
            //该类型的可查看流程内容checkbox
    		document.all("t"+id).checked=true;
            return;
        }
    }
    document.all("t"+id).checked=false;

}

function onCheckAll(obj){
	/*
    len = document.frmmain.elements.length;
    var i=0;
    if(!expandall){
	    for( i=0; i<len; i++) {
	        if (document.frmmain.elements[i].name.indexOf('t')==0) {
	    		document.frmmain.elements[i].checked=!obj.checked;
	            document.frmmain.elements[i].click();
	        }
	    }
    	expandall=true;
    }
    for( i=0; i<len; i++) {
        if (document.frmmain.elements[i].name.indexOf('t')==0 || document.frmmain.elements[i].name.indexOf('w')==0 || (!obj.checked && (
        document.frmmain.elements[i].name.indexOf('vt')==0 || document.frmmain.elements[i].name.indexOf('vw')==0||
        document.frmmain.elements[i].name.indexOf('it')==0 || document.frmmain.elements[i].name.indexOf('iw')==0||
        document.frmmain.elements[i].name.indexOf('dt')==0 || document.frmmain.elements[i].name.indexOf('dw')==0||
        document.frmmain.elements[i].name.indexOf('bt')==0 || document.frmmain.elements[i].name.indexOf('bw')==0||
        document.frmmain.elements[i].name.indexOf('ot')==0 || document.frmmain.elements[i].name.indexOf('ow')==0||
        document.frmmain.elements[i].name.indexOf('st')==0 || document.frmmain.elements[i].name.indexOf('sw')==0))) {
    		document.frmmain.elements[i].checked=obj.checked;
    	}
    }
    if(!obj.checked){
        document.all("viewcheckall").checked=obj.checked;
        document.all("intervenorcheckall").checked=obj.checked;
        document.all("delcheckall").checked=obj.checked;
        document.all("fbcheckall").checked=obj.checked;
        document.all("focheckall").checked=obj.checked;
        document.all("socheckall").checked=obj.checked;
        
    }
    */
    if(!expandall){
	    jQuery("input[name^='t']").each(function(){
    		this.checked=!obj.checked;
            this.click();
        });
    	expandall=true;
    }
    jQuery("input[name^='t']").add(jQuery("input[name^='w']")).each(function(){
    	this.checked = obj.checked;
    });
    if(!obj.checked){
        jQuery("input[name^='t'][checked='true']").add(jQuery("input[name^='w'][checked='true']"))

        .each(function(){
        	this.checked = false;
        });
    }

}


function goexpandall(){
tree.expandAll();
expandall=true;
}
function docollapseall(){
tree.collapseAll();
}
</script>
</html>
