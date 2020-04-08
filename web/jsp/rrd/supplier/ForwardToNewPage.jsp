<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="rrd.util.GetUtil" %>
<%@ page import="sun.misc.BASE64Decoder" %>
<%@ include file="/systeminfo/init_wev8.jsp" %> 
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<link rel="stylesheet" href="/css/Weaver_wev8.css" type="text/css">
<LINK href="/js/jquery/jquery_dialog_wev8.css" type=text/css rel=STYLESHEET>
<script type="text/javascript" language="javascript" src="/js/jquery/jquery_dialog_wev8.js"></script>
<style id="popupmanager">
#codediv{
    text-align: center;
    background-color: #fff;
    border-radius: 20px;
    width: 800px;
    height: 350px;
    position: absolute;
    left: 50%;
    top: 30%;
    transform: translate(-50%,-50%);
}
#code{
    outline-style: none ;
    border: 1px solid #ccc; 
    border-radius: 3px;
    padding: 13px 14px;
    width: 620px;
    font-size: 14px;
    font-weight: 700;
    font-family: "Microsoft soft";
}

#code:focus{
    border-color: #66afe9;
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
}
#btt {
   background: #33bdef;
  background-image: -webkit-linear-gradient(top, #33bdef, #019ad2);
  background-image: -moz-linear-gradient(top, #33bdef, #019ad2);
  background-image: -ms-linear-gradient(top, #33bdef, #019ad2);
  background-image: -o-linear-gradient(top, #33bdef, #019ad2);
  background-image: linear-gradient(to bottom, #33bdef, #019ad2);
  -webkit-border-radius: 6;
  -moz-border-radius: 6;
  border-radius: 6px;
  text-shadow: 0px -1px 0px #5b6178;
  -webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
  -moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
  box-shadow: 0px 1px 0px 0px #f0f7fa;
  font-family: Arial;
  color: #ffffff;
  font-size: 16px;
  padding: 12px 30px 12px 30px;
  border: solid #057fd0 1px;
  text-decoration: none;
}

#btt:hover {
  background: #2980b9;
  background-image: -webkit-linear-gradient(top, #2980b9, #3498db);
  background-image: -moz-linear-gradient(top, #2980b9, #3498db);
  background-image: -ms-linear-gradient(top, #2980b9, #3498db);
  background-image: -o-linear-gradient(top, #2980b9, #3498db);
  background-image: linear-gradient(to bottom, #2980b9, #3498db);
  color: #ffffff;
  text-decoration: none;
}
</style>

<%  
    String keys = Util.null2String(request.getParameter("key"));//
    String result = "";
    GetUtil gu = new GetUtil();
    String  mw = gu.getEncryptBASE64JM(keys);
    String rid = mw.split(",")[0];
    String code  = "";
    String urlkey = URLEncoder.encode(keys,"utf-8");
    String sql = "select yzm  from uf_prescription where lcrid = '"+rid+"' and zt = 0 ";
    rs.executeSql(sql);
    if(rs.next()){
        code = rs.getString("yzm");
    }
    if(code.length()<1){
        result = "该链接无效，没找到对应的流程数据，请确认！";
        %>
   <script type="text/javascript" language="javascript">
    var tex = '<%=result%>'; 
    Dialog.alert(tex);
    </script>
        <%
        return;
    }
    boolean flag = gu.checkIfOvertime(mw);
    if(flag){
        sql = "update uf_prescription set zt= 1 where   lcrid = '"+rid+"' and zt = 0 ";
        rs.executeSql(sql);
        result = "链接已失效，请确认!";
%>   
<script type="text/javascript" language="javascript">
var tex = '<%=result%>'; 
Dialog.alert(tex);
</script>
<%
        return ;
        // out.print("链接已失效")
    }else{
%>
       
      <Div  id = 'codediv'>
      
     <input id="code" name= "code" type="text" placeholder="请输入验证码"> <button type="button" id = "btt" onclick = "getCode()">确认</button>
      </Div>
       
       <% 
         // response.sendRedirect("www.baidu.com");
    } 
    
%>
<script type="text/javascript" language="javascript">

function getCode(){
    var code = jQuery("#code").val();
    var codeCont = '<%=code%>';
    var urlkeys = '<%=urlkey%>';
    if(code == ''){
        Dialog.alert("请输入验证码 ！");
        return;
    }
    if(code != codeCont){
        Dialog.alert("验证码不对，请确认！");
    }else{
       window.location.replace("https://www.runoob.com");//外部页面访问
        // window.location.href="www.baidu.com";//内部页面
    }
    
}




</script>



