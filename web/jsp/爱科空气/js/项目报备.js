var xmmc = WfForm.convertFieldNameToId("xmmc");	//项目名称
jQuery(document).ready(function() {
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        var xmmc_val = WfForm.getFieldValue(xmmc);

        if(xmmc_val != ""){
           var result = checkxmmc();
           if(result == "1"){
               alert("该项目已被报备，如有问题请咨询商务。");
               return;
           }
        }
        callback();


    })
});

function checkxmmc(){
    var xmmc_val =  WfForm.getFieldValue(xmmc);
    var checkresult = "";
    jQuery.ajax({
        type: "POST",
        url: "/akkq/xm/checkxmname.jsp",
        data: {"xmmc":xmmc_val},
        dataType: "text",
        async:false,//同步 true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            checkresult = data;

        }

    });
    return checkresult;
}