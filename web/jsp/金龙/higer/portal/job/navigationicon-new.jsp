<%@page import="weaver.general.TimeUtil"%>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="java.lang.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<html>
<%
int userid = user.getUID();

 %>
<head>
	<title></title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	*{
		margin:0px;
		padding:0px;
	}
	.navifation{
		width:300px;
		height: 300px;
        margin-left:12%;

	}
	.navifation ul{
		width: 100%;
		height: 100%;
		list-style: none;
		background-color:#ffffff;
	}
	.navifation ul .list{
		width: 29.5%;
		height: 29%;
		float: left;
		margin-top: 4%;
		margin-left: 2%;
		border-radius: 10px;
		background-color: #d9d6c3;
		margin-bottom: -2%;
	}
	.list_logo{
		width:100%;
		height:70%;
	}
	.list_logo img{
		width: 100%;
		height: 100%;
		border-radius: 5px;
		z-index: 0;
		margin-top:5px;
		margin-left: 3px;
	}
	.title{
		width: 100%;
		height: 28%;
		color: #666;
		font-size: 12px;
		text-align: center;
		color: #ffffff;
		font-weight: bold;
	}
	.imgbox{
		width: 55%;
		height: 80%;
		margin:0 auto;
		margin-top: 7px;
		border-radius: 5px;
		font-size: 18px;
		text-align: center;
		position:relative;
		color: #ffffff;
	}
	.number{
		width: 100%;
		text-align: center;
		font-size: 20px;
		margin-top: 5px;
		color:#ffffff;
	}
	.countinfo{
		font-size: 30px;
		z-index: 1;
		width: 60px;
		height: 60px;
		border-radius: 50%;
		color: #ffffff;
		line-height: 60px;
	}
	#todo{
		background-color: rgb(255,155,100);
	}
	#mymission{
		background-color: rgb(255,204,103);
	}
	#mymissionW{
		background-color: rgb(102,194,204);
	}
	#mymissionP{ 
		background-color: rgb(1,102,255);
	}
	#supervisor{ 
		background-color: rgb(152,103,254);
	}
	#mettting{ 
		background-color: rgb(206,103,255);
	} 
	#read{ 
		background-color: rgb(0,153,255);
	}
	#byyb{
		background-color: rgb(153,153,153); 
	}
	#byyy{
		background-color: rgb(52,204,254); 
	}
	.list{
		background-image: linear-gradient(rgb(24,170,125),rgb(1,144,238));
	}  	
</style>

<body>
	<div class = "navifation">
		<ul >
			<li class = "list " id = "todo">
            	<a href="/wui/index.html#/main/workflow/listDoing" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="toDoCount">0</span>
					</div>
				</div>
				<!-- <div class = "title">我的待办</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99121,user.getLanguage())%></div>
                </a>
			</li>
			<li class = "list" id = "mymission">
            <a href="/spa/prj/index.html#/main/prj/queryTaskResult?searchfrom=portal" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="oajobcount">0</span>
					</div>
				</div>
				<!-- <div class = "title">OA任务</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99122,user.getLanguage())%></div>
                </a>
			</li>
			<li class = "list" id = "mymissionW">
            <a href="/higer/portal/job/myjob-w.jsp" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="wjobcount">0</span>
					</div>
				</div>
				<!-- <div class = "title" style="font-size:11px;">Windchill任务</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99129,user.getLanguage())%></div>
                </a>
			</li>
			<li class = "list" id = "mymissionP">
            <a href="/higer/portal/job/myjob-p-more-Url.jsp" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="pjobcount">0</span>

					</div>
				</div>
				<!-- <div class = "title">项目任务</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99123,user.getLanguage())%></div>
                </a>
			</li>
			<li class = "list"  id= "supervisor">
            	<a href="/spa/govern/static/index.html#/main/govern/projAccount" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="myGovernCount">0</span>
					</div>
				</div>
				<!-- <div class = "title">我的督办</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99124,user.getLanguage())%></div>
                	</a>
			</li>
			<li class = "list" id = "mettting">
            <a href="/wui/index.html#/main/meeting/calView" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="myMeetCount">0</span>
					</div>
				</div>
				<!-- <div class = "title">我的会议</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99125,user.getLanguage())%></div>
                	</a>
			</li>
			<li class = "list" id = "read">
            <a href="/wui/index.html#/main/workflow/listDoing" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="toReadCount">0</span>
					</div>
				</div>
				<!-- <div class = "title">我的待阅</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99126,user.getLanguage())%></div>
                </a>
			</li>

			<li class = "list" id = "read">
            <a href="/wui/index.html#/main/workflow/listDoing" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="havaToDoCount">0</span>
					</div>
				</div>
				<!-- <div class = "title">本月已办</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99127,user.getLanguage())%></div>
                </a>
			</li>

			<li class = "list" id = "read">
            <a href="/wui/index.html#/main/workflow/listDoing" target="_blank">
				<div class = "list_logo">
					<div class = "imgbox">
						<span class = "countinfo" id="havaToDoCountyear">0</span>
					</div>
				</div>
				<!-- <div class = "title">本年已办</div> -->
				<div class = "title"><%=SystemEnv.getHtmlLabelName(-99128,user.getLanguage())%></div>
                </a>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
		var ryid = "<%=userid%>";
		jQuery(document).ready(function(){
			  $.ajax({
             type: "GET",
             url: "/api/higer/portal/getPortalCount",
             data: {'type':'1','ryid':ryid,'timess':new Date().getTime()},
             dataType: "text",
             async:true,//同步   true异步
             success: function(data){
            	data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
				var countjson = eval('(' + data + ')');
						jQuery("#toDoCount").text(countjson.toDoCount);
						jQuery("#oajobcount").text(countjson.oajobcount);
						jQuery("#wjobcount").text(countjson.wjobcount);
						jQuery("#pjobcount").text(countjson.pjobcount);
						jQuery("#myGovernCount").text(countjson.myGovernCount);
						jQuery("#myMeetCount").text(countjson.myMeetCount);
						jQuery("#toReadCount").text(countjson.toReadCount);
						jQuery("#havaToDoCount").text(countjson.havaToDoCount);
						jQuery("#havaToDoCountyear").text(countjson.havaToDoCountyear);
                      }
         	});
			jQuery(".navifation").css("margin-left",Number(Number(jQuery(document.body).width())-296)/2+"px");
		})
	</script>
</body>
</html>