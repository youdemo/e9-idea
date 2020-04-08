<!DOCTYPE html>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@ page import="weaver.hrm.User"%>
<html style="height:100%">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title></title>
  <link rel="stylesheet" href="">
  <style type="text/css">
  	*{
  		padding:0px;
  		margin:0px;

  	}
  	#meeting .labelMenu {

  	}
  	#meeting .labelMenu li{
  		float:left;
  		width:135px; 
  		list-style-type: none;
  		line-height: 30px;
  		text-align: center;
  		font-size: 12px;
  		border-radius: 5px;
  		margin-left: 5px;
  		overflow: hidden;
  	}
  	a{
  		text-decoration: none;
  	}
  	#meeting .mainContect{
  		height:calc(100% - 30px);
  	}
  	#meeting .labelMenu .clickLi{
  		background-color:rgb(79,167,255);
  		color:rgb(255,255,255);

  	}
  	.mettingglt{
  		border:1px solid red;

  	}
    .leftMenu:hover,.rightMenu:hover{
      background-color: rgb(56,63,72);
    }
   
  </style>
  <%
    User user = HrmUserVarify.getUser(request, response); 

  %>
  <script src="/js/jquery/jquery_wev8.js" type="text/javascript"></script>
  <script type="text/javascript" src="/js/dragBox/parentShowcol_wev8.js"></script>
  <script language="javascript" type="text/javascript" src="/js/init_wev8.js"></script>
  <script language=javascript src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>
  <script type="text/javascript" src="/js/ecology8/lang/weaver_lang_<%=user.getLanguage()%>_wev8.js"></script>

</head>
<body style="height:100%">
	<div  id = "meeting" style="height:100%">
		<div class = "meetingleft" style = "float:left;width:190px;height:100%;background-color:rgb(42,46,52);position: relative;overflow-x:hidden; "  >
			<iframe src = "/gvo/gateway/jsp/getProcess.jsp" style = "height:99%;width: 108%;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" id = "scroll"></iframe>
      <div class = "leftMenu"style = "position:absolute;width:18px;height:30px;z-index: 99;bottom: 5px;right:1px;background-image:url(/wui/theme/ecology8/page/images/leftHideDefault_wev8.png);background-color: rgb(114, 115, 116);border-radius: 3px;background-position-y: center;"></div>  
		</div>
		<div class = "meetingright" style = "float:right;width:calc(100% - 190px);height:100%;">
			<iframe src = "/gvo/ty/gvo_tydb_Url.jsp" name = "mainFrame" style = "height:99%;width: 100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
       
       <div class = "rightMenu" style = "position:absolute;width:18px;height:30px;z-index: 99;bottom: 40px;left:5px;background-image:url(/wui/theme/ecology8/page/images/leftShowDefault_wev8.png);display:none;background-color: rgb(114, 115, 116);border-radius: 3px;background-position-y: center;"></div>
		</div>
	</div>
	<script type="text/javascript">

    //页面全部加载完毕才显示页面
    document.onreadystatechange = function () {
            if (document.readyState == "complete") {
                document.body.style.display = "block";
            } else {
                document.body.style.display = "none";
            };
        };


	    window.onload = function(){
			var width=window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
			var height=window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			document.getElementById("meeting").style.width = (width-5)+"px";
			document.getElementById("meeting").style.height = height+"px";


      




       //隐藏左边的菜单
        jQuery(".leftMenu").click(function(){
          setLeftMenu();
          jQuery(".meetingright").width("100%");
          jQuery(".rightMenu").css('display','block');
        });

        //展现左边菜单
        jQuery(".rightMenu").click(function(){
          setRightMenu();
          jQuery(".meetingright").width("calc(100% - 190px)");
          jQuery(".rightMenu").css('display','none');
        });

}
   
  function setLeftMenu (){
      jQuery(".meetingleft").hide();
    }
    function setRightMenu(){
      jQuery(".meetingleft").show();
    }
	</script>
</body>
</html>