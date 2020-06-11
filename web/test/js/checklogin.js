
var checklogin = "";
jQuery.ajax({
    type: "GET",
    url: "/api/gvo/hr/portal/checklogin",
    data: {},
    dataType: "text",
    async:false,//同步   true异步
    success: function(data){
        data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
        var json = JSON.parse(data);
        checklogin = json.result;
    }
});
if(checklogin !="success"){
    window.location.href="/wui/index.html#/?logintype=1";
}

