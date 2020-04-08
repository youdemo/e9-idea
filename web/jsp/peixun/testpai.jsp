<%--
  Created by IntelliJ IDEA.
  User: tangj
  Date: 2020/3/30
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript" src="/wui/common/jquery/jquery.min_wev8.js"></script>
<head>
    <title>Title</title>
</head>
<body>
<div><a href="javascript:testapi()" >测试</a> </div>

</body>
<script type="text/javascript">
    function testapi(){
        jQuery.ajax({
            type: "POST",//GET POST
            url: "/api/peixun/ryxx/getlastname2",
            data: {'workcode':'test123'},
            dataType: "text",
            async:false,//同步 true异步
            success: function(data){
                data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
                alert(data);
            }

        });

    }
</script>
</html>
