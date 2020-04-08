<%@page import="weaver.general.TimeUtil"%>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="gvo.cowork.PortalTransUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs_dt" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="CoworkService" class="weaver.cowork.CoworkService" scope="page" />
<jsp:useBean id="DocSearchManage" class="weaver.docs.search.DocSearchManage" scope="page" />
<jsp:useBean id="sharemanager" class="weaver.share.ShareManager" scope="page" />
<html>
<%
int userid=user.getUID();
PortalTransUtil ptu = new PortalTransUtil();
String sql = "select name from nickname where userid = "+userid;
String sql_dt = "";
rs.executeQuery(sql);
boolean hasnickname = false;
if(rs.getCounts()>0){
 hasnickname = true;
}

if(!hasnickname) {
  response.sendRedirect("/cowork/nickname/coworknicknameview.jsp?loca=99") ;
   return ;
}
String accsize ="8";
String accsec="14024";//目录测14024 正5801
String attach="";



%>
<head>
	<title>联系我们</title>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="/js/swfupload/default_wev8.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/swfupload/swfupload_wev8.js"></script>
<script type="text/javascript" src="/js/swfupload/swfupload.queue_wev8.js"></script>
<script type="text/javascript" src="/js/swfupload/fileprogress_wev8.js"></script>
<script type="text/javascript" src="/js/swfupload/handlers_wev8.js"></script>
</head>
<style type="text/css">
	*{
		padding:0px;
		margin:0px;
		font-family: "微软雅黑";
	}
	html,body{
		width:100%;
		height:100%;
	}
	.contact{
		border:1px solid #ffffff; 
	}
	.head{
		height:160px;
		line-height: 70px;
		font-size: 20px;
		border-bottom: 1px solid #ebedf0;
		padding-left: 120px;
		padding-right: 120px;
	}
	.contact_info{
		padding-bottom: 10px;
		margin-top:0px;
		padding-bottom: 20px;
		padding-left: 120px;
		padding-right: 120px;
	}
	.contact_info div{
		margin-top:5px;
		font-size: 12px;
	}
	.contact_text_tips{
		font-size: 18px;
		margin-top:0px;
	}
	.operation{
		position:relative;
		margin-top:0px;
	}
	.submit{
		position:absolute;
		top:0px;
		right:0px;
		margin-left: 50px;
		width:40px;
		text-align:center; 
		height:25px;
		line-height: 25px;
		background-color:rgb(5,80,246);
		color:rgb(255,255,255);
		cursor: pointer;
	}
	.type{
		position: absolute;
		top:0px;
		left:50px;
		margin-right: 50px;
	}
	.info{
		margin-top:20px;
		height:200px;
	}
	.info textarea{
		width:100%;
		height:100%;
		text-indent: 2px;
	}
	#info{
		resize:none
	}
	.head_img{
		margin-top:10px;
		height:140px;
	}
	.head_img img{
		width:100%;
		height:100%;
	}
	.content{
		background:url(./img/background_img.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}
	a{
		text-decoration: none;
	}
	.contact_title{
		position: relative;
		height:20px;
	}
	.contact_title img{
		display:inline-block;
		width:16px;
		height:17px;
	}
	.contact_title span{
		margin:0px;
		padding: 0px;
		float: left;
		margin-right: 5px;
		font-weight:bold;	
	}
	.contact_title_span{
		height:20px;
		font-size: 14px;
	}
	.contact_title_img{
		width:20px;
		height:20px;
		display:block;
		background: url(./img/tel.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}
	.contactus{
		color:#0000EE;
	}
	.it{
		padding-left: 64px;
	}
	.contact_text{
		padding-left: 120px;
		padding-right: 120px;
	}
	.contactus_info{
		margin-right: 10px;
		margin-left: 15px;
		color:#808080;
	}
	.hyly{
		background: url(./img/lxwm.png);
		background-repeat: no-repeat;
		background-size: 100% 100%;
	}
</style>
<script type="text/javascript">
			var oUpload;
				
				window.onload = function() {
				  var settings = {
						flash_url : "/js/swfupload/swfupload.swf",
						upload_url: "/proj/data/uploadPrjAcc.jsp",	// Relative to the SWF file
						post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
						file_size_limit : "<%=accsize %> MB",
						file_types : "*.*",
						file_types_description : "All Files",
						file_upload_limit : 100,
						file_queue_limit : 0,
						custom_settings : {
							progressTarget : "fsUploadProgress",
							cancelButtonId : "btnCancel"
						},
						debug: false,
						 

						// Button settings
						
						button_image_url : "",	// Relative to the SWF file
						button_placeholder_id : "spanButtonPlaceHolder",
		
						button_width: 100,
						button_height: 18,
						button_text : '<span class="button" style="background:url(\"/gvo/cowork/img/sc.png\")"></span>',
						button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
						button_text_top_padding: 0,
						button_text_left_padding: 18,
							
						button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
						button_cursor: SWFUpload.CURSOR.HAND,
						
						// The event handler functions are defined in handlers.js
						file_queued_handler : fileQueued,
						file_queue_error_handler : fileQueueError,
						file_dialog_complete_handler : fileDialogComplete_1,
						upload_start_handler : uploadStart,
						upload_progress_handler : uploadProgress,
						upload_error_handler : uploadError,
						upload_success_handler : uploadSuccess,
						upload_complete_handler : uploadComplete,
						queue_complete_handler : queueComplete	// Queue plugin event
					};

					
					
					try{
						oUpload = new SWFUpload(settings);
						jQuery("#spanButtonPlaceHolder").html("<span><img src=\"/gvo/cowork/img/sc.png\"  border=\"0\"></span>");
						jQuery("#spanButtonPlaceHolder").css({"background":"","bottom":"","padding-left":"","cursor":"","position":"","z-index":""})
						//jQuery("#spanButtonPlaceHolder").css({"background-color":"rgb(5,80,246)","padding-left":""})
					} catch(e){alert(e)}
				}
		
				function fileDialogComplete_1(){
					document.getElementById("btnCancel1").disabled = false;
					fileDialogComplete
				}
				function uploadSuccess(fileObj,serverdata){
					var data=eval(serverdata);
					//alert(data);
					if(data){
						var a=data;
						if(a>0){
							if(jQuery("#attach").val() == ""){

								jQuery("#attach").val(a);
							}else{
							
								jQuery("#attach").val(jQuery("#attach").val()+","+a);
								
							}
						}
					}
				}
		
				function uploadComplete(fileObj) {
					try {
						/*  I want the next upload to continue automatically so I'll call startUpload here */
						if (this.getStats().files_queued === 0) {
							
							report.submit();
							document.getElementById(this.customSettings.cancelButtonId).disabled = true;
						} else {	
							this.startUpload();
						}
					} catch (ex) { this.debug(ex); }
		
				}
			</script>
<body>
	<div class= "contact">
		<div class = "head">
			<div class = "head_img">
				<img src = "img/head_img.png"/>
			</div>
		</div>
		<div class = "content">
			<div class = "contact_info">
				<div class = "contact_title">
					<span class = "contact_title_span contact_title_img"></span>
					<span class = "contact_title_span">联系方式</span>
				</div>
				<div>如有任何问题,您可以通过以下方式联系我们</div>
				<div>
					电子邮件：
					<%
						String address = "";
						String ms = "";
						sql = "select address,ms from uf_co_contact_mt where type=0 order by xh asc";
						rs.execute(sql);
						if(rs.next()){
							address = Util.null2String(rs.getString("address"));
							ms = Util.null2String(rs.getString("ms"));
					%>
						<a href = "mailto:<%=address%>" class = "contactus"><%=address%></a>
						<span class = "contactus_info"><%=ms%></span>
					<%
						}
					%>
					
				</div>
				<div >
					联系方式：
						<%
						String mtid = "";
						sql = "select id,address,ms from uf_co_contact_mt where type=1 order by xh asc";
						rs.execute(sql);
						if(rs.next()){
							mtid = Util.null2String(rs.getString("id"));
							address = Util.null2String(rs.getString("address"));
							ms = Util.null2String(rs.getString("ms"));
					%>
						<span class = "contactus"><%=address%></span> 
						<span class = "contactus_info"><%=ms%></span>
					<%
						}
					%>
						
				</div>
				<%
						sql = "select id,address,ms from uf_co_contact_mt where type=1 and id not in("+mtid+")order by xh asc";
						rs.execute(sql);
						while(rs.next()){
							address = Util.null2String(rs.getString("address"));
							ms = Util.null2String(rs.getString("ms"));
					%>
						<div class = "contactus it">
							<span class = "contactus"><%=address%></span>
							<span class= "contactus_info"><%=ms%></span>
						</div>
					<%
						}
					%>
				
			</div>
			<div class = "contact_text">
				<FORM id=report name=report action="/gvo/cowork/submit-contact.jsp" method=post enctype="multipart/form-data">
	<input type="hidden" name="attach" id="attach" value="<%=attach %>">
					<div class = "operation">
						<div class = "contact_text_tips contact_title">
							<span class = "contact_title_span contact_title_img hyly"></span>
							<span class = "contact_title_span">欢迎留言</span>
						</div>
						<div class = "submit">提交</div>	
					</div>	
					<div style="clear: both"></div>
					<div class = "info">
						<textarea placeholder="请输入留言内容" name="info" id = "info"></textarea>
					</div>
					<div style="margin-left: 50px;margin-top: 20px">
									<span> 
										<span id="spanButtonPlaceHolder">
										
										</span><!--选取多个文件-->
									</span>
									&nbsp;&nbsp;
									<span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1">
										<span><img src="/gvo/cowork/img/qc.png"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"></font><!--清除所有选择--></span>
									</span>
								</div>
								<div class="fieldset flash" id="fsUploadProgress"></div>
								<div id="divStatus"></div>
								
								<input class=inputstyle style="display:none;" type=file name="accessory1" onchange='accesoryChanage(this)' style="width:100%">
								<span id="shfj_span"></span>
					
								<button type="button" class=AddDoc style="display:none;" name="addacc" onclick="addannexRow()"><%=SystemEnv.getHtmlLabelName(611,user.getLanguage())%></button>
								<input type=hidden name=accessory_num value="1">
					
					</div>
				</form> 
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	jQuery(document).ready(function(){
		
		var flag ="0"
		//sunmit 点击事件
		jQuery(".submit").click(function(){
			var info = jQuery("#info").val();
			if(flag !="0"){
				alert("请勿重复提交");
				return;
			}else{
				flag = "1";
			}
			if(!oUpload){
					report.submit();
			}else{
				try {
					if(oUpload.getStats().files_queued === 0){
								report.submit();
					}else {
						oUpload.startUpload();
					}
				} catch (e) {
				
				}
			}
		
		})
		
	});
	jQuery(".content").css('height',Number(jQuery(window).height())-185+"px");
	
</script>
</html>
