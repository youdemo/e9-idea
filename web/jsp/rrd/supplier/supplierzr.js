
var oUpload_yyzzjggz;//营业执照
var oUpload_yhkhxxjggz;//银行开户信息
var oUpload_syddhjggz;//商业道德函
var oUpload_cwgjxx;//财务关键信息

var oUpload_zzsq;//资质授权
var oUpload_sbqd;//设备清单
var oUpload_rbcxxkytjbx;//如补充信息可以添加表下
var oUpload_gsjbxxjpyq;//公司基本信息截屏要求
var count =0;

window.onload = function() {
    var clienthei = window.innerHeight
    var clientwid= window.innerWidth
    var height1 = Number(clienthei) - 71;
    if(clientwid <1200){
        clientwid =1200;
    }
    height1 = height1 + 'px';
    document.getElementById('divinner').style.height = height1;
    document.getElementsByTagName("body")[0].style.width  = clientwid+"px";

    var settings_yyzzjggz = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_yyzzjggz",
            cancelButtonId : "btnCancel1_yyzzjggz",
            uploadspan : "yyzzjggzspan"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_yyzzjggz",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_yyzzjggz,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_yyzzjggz,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };
    var settings_yhkhxxjggz = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_yhkhxxjggz",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_yhkhxxjggz",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_yhkhxxjggz,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_yhkhxxjggz,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };

    var settings_syddhjggz = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_syddhjggz",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_syddhjggz",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_syddhjggz,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_syddhjggz,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };

    var settings_cwgjxx = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_cwgjxx",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_cwgjxx",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_cwgjxx,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_cwgjxx,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };

    var settings_zzsq = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_zzsq",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_zzsq",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_zzsq,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_zzsq,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };

    var settings_sbqd = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_sbqd",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_sbqd",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_sbqd,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_sbqd,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };

    var settings_rbcxxkytjbx = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_rbcxxkytjbx",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_rbcxxkytjbx",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_rbcxxkytjbx,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_rbcxxkytjbx,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };
var accsec="<%=accsec%>";
alert(accsec);
    var settings_gsjbxxjpyq = {
        flash_url : "/js/swfupload/swfupload.swf",
        upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
        post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec%>"},
        file_size_limit : "<%=accsize %> MB",
        file_types : "*.*",
        file_types_description : "All Files",
        file_upload_limit : 100,
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fsUploadProgress_gsjbxxjpyq",
            cancelButtonId : "btnCancel"
        },
        debug: false,


        // Button settings

        button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
        button_placeholder_id : "spanButtonPlaceHolder_gsjbxxjpyq",

        button_width: 100,
        button_height: 30,
        button_text : '<span class="button"></span>',
        button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
        button_text_top_padding: 0,
        button_text_left_padding: 2,

        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
        // The event handler functions are defined in handlers.js
        file_queued_handler : fileQueued,
        file_queue_error_handler : fileQueueError,
        file_dialog_complete_handler : fileDialogComplete_gsjbxxjpyq,
        upload_start_handler : uploadStart,
        upload_progress_handler : uploadProgress,
        upload_error_handler : uploadError,
        upload_success_handler : uploadSuccess_gsjbxxjpyq,
        upload_complete_handler : uploadComplete,
        queue_complete_handler : queueComplete	// Queue plugin event
    };




    try{
        oUpload_yyzzjggz = new SWFUpload(settings_yyzzjggz);
        oUpload_yhkhxxjggz = new SWFUpload(settings_yhkhxxjggz);
        oUpload_syddhjggz = new SWFUpload(settings_syddhjggz);
        oUpload_cwgjxx = new SWFUpload(settings_cwgjxx);
        oUpload_zzsq = new SWFUpload(settings_zzsq);
        oUpload_sbqd = new SWFUpload(settings_sbqd);
        oUpload_rbcxxkytjbx = new SWFUpload(settings_rbcxxkytjbx);
        oUpload_gsjbxxjpyq = new SWFUpload(settings_gsjbxxjpyq);

    } catch(e){alert(e)}
}


function fileDialogComplete_yyzzjggz(){
    document.getElementById("btnCancel1_yyzzjggz").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_yhkhxxjggz(){
    document.getElementById("btnCancel1_yhkhxxjggz").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_syddhjggz(){
    document.getElementById("btnCancel1_syddhjggz").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_cwgjxx(){
    document.getElementById("btnCancel1_cwgjxx").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_zzsq(){
    document.getElementById("btnCancel1_zzsq").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_sbqd(){
    document.getElementById("btnCancel1_sbqd").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_rbcxxkytjbx(){
    document.getElementById("btnCancel1_rbcxxkytjbx").disabled = false;
    fileDialogComplete
}
function fileDialogComplete_gsjbxxjpyq(){
    document.getElementById("btnCancel1_gsjbxxjpyq").disabled = false;
    fileDialogComplete
}

function uploadSuccess_yyzzjggz(fileObj,serverdata){
    alert("123")
    var data=eval(serverdata);
    alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#yyzzjggz").val() == ""){

                jQuery("#yyzzjggz").val(a);
            }else{

                jQuery("#yyzzjggz").val(jQuery("#yyzzjggz").val()+","+a);

            }
        }
    }
}

function uploadSuccess_yhkhxxjggz(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#yhkhxxjggz").val() == ""){

                jQuery("#yhkhxxjggz").val(a);
            }else{

                jQuery("#yhkhxxjggz").val(jQuery("#yhkhxxjggz").val()+","+a);

            }
        }
    }
}

function uploadSuccess_syddhjggz(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#syddhjggz").val() == ""){

                jQuery("#syddhjggz").val(a);
            }else{

                jQuery("#syddhjggz").val(jQuery("#syddhjggz").val()+","+a);

            }
        }
    }
}

function uploadSuccess_cwgjxx(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#cwgjxx").val() == ""){

                jQuery("#cwgjxx").val(a);
            }else{

                jQuery("#cwgjxx").val(jQuery("#cwgjxx").val()+","+a);

            }
        }
    }
}

function uploadSuccess_zzsq(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#zzsq").val() == ""){

                jQuery("#zzsq").val(a);
            }else{

                jQuery("#zzsq").val(jQuery("#zzsq").val()+","+a);

            }
        }
    }
}

function uploadSuccess_sbqd(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#sbqd").val() == ""){

                jQuery("#sbqd").val(a);
            }else{

                jQuery("#sbqd").val(jQuery("#sbqd").val()+","+a);

            }
        }
    }
}

function uploadSuccess_rbcxxkytjbx(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#rbcxxkytjbx").val() == ""){

                jQuery("#rbcxxkytjbx").val(a);
            }else{

                jQuery("#rbcxxkytjbx").val(jQuery("#rbcxxkytjbx").val()+","+a);

            }
        }
    }
}

function uploadSuccess_gsjbxxjpyq(fileObj,serverdata){
    var data=eval(serverdata);
    //alert(data);
    if(data){
        var a=data;
        if(a>0){
            if(jQuery("#gsjbxxjpyq").val() == ""){

                jQuery("#gsjbxxjpyq").val(a);
            }else{

                jQuery("#gsjbxxjpyq").val(jQuery("#gsjbxxjpyq").val()+","+a);

            }
        }
    }
}

function uploadComplete(fileObj) {
    try {

        /*  I want the next upload to continue automatically so I'll call startUpload here */
        if (this.getStats().files_queued === 0) {
            count = count+1;
            //;
            if(count == 8){
                alert(jQuery("#yyzzjggz").val())
                report.submit();
            }
            //report.submit();
            document.getElementById(this.customSettings.cancelButtonId).disabled = true;
        } else {
            this.startUpload();
        }
    } catch (ex) { this.debug(ex); }

}
function changecheck(fieldname){
    var value = jQuery("#"+fieldname).val();
    if(value == '1'){
        value = '0';
    }else{
        value ='1';
    }
    jQuery("#"+fieldname).val(value);
    if(fieldname=="zzs"){
        showHidezzs();
    }
}
function  showHidezzs(){
    var zzs_val = jQuery("#zzs").val();
    if(zzs_val=="1"){
        jQuery("#zcs_1").show();
        jQuery("#zcs_2").show();
        jQuery("#zcs_3").show();
        jQuery("#zcs_4").show();
        jQuery("#zcs_5").show();
        addcheck("ygzsr","0");
        addcheck("bgcsmjm2","0");
        addcheck("sccsmjm2","0");
        addcheck("glryzsr","0");
        addcheck("ckmjm2","0");
        addcheck("sbhcpnlms","0");
    }else{
        jQuery("#zcs_1").hide();
        jQuery("#zcs_2").hide();
        jQuery("#zcs_3").hide();
        jQuery("#zcs_4").hide();
        jQuery("#zcs_5").hide();
        removecheck("ygzsr","0");
        removecheck("bgcsmjm2","0");
        removecheck("sccsmjm2","0");
        removecheck("glryzsr","0");
        removecheck("ckmjm2","0");
        removecheck("sbhcpnlms","0");
        jQuery("#ygzsr").val("");
        jQuery("#bgcsmjm2").val("");
        jQuery("#sccsmjm2").val("");
        jQuery("#glryzsr").val("");
        jQuery("#ckmjm2").val("");
        jQuery("#sbhcpnlms").val("");
    }
}
jQuery(document).ready(function () {

    jQuery("#sfyyyzzjggz").bind("change",function(){
        changemustfield();
    })
    jQuery("#sfyyhkhxxjggz").bind("change",function(){
        changemustfield();
    })
    jQuery("#sfysyddhjggz").bind("change",function(){
        changemustfield();
    })
    changemustfield();
    showHidezzs();
})

function changemustfield(){
    var sfyyyxgfj_val = jQuery("#sfyyyzzjggz").val();//是否有营业相关附件
    var sfyyhxgfj_val = jQuery("#sfyyhkhxxjggz").val();//是否有银行相关附件
    var sfysyxgfj_val = jQuery("#sfysyddhjggz").val();//是否有商业相关附件
    if(sfyyyxgfj_val == "1"){
        addcheck("yyzzlysm","0");
        removecheck("yyzzjggz","1")
    }else{
        addcheck("yyzzjggz","1");
        removecheck("yyzzlysm","0")
    }
    if(sfyyhxgfj_val == "1"){
        addcheck("yhkhxxlysm","0");
        removecheck("yhkhxxjggz","1")
    }else{
        addcheck("yhkhxxjggz","1");
        removecheck("yhkhxxlysm","0")
    }
    if(sfysyxgfj_val == "1"){
        addcheck("syddhlysm","0");
        removecheck("syddhjggz","1")
    }else{
        addcheck("syddhjggz","1");
        removecheck("syddhlysm","0")
    }
}
function addcheck(btid,flag){
    var btid_val = jQuery("#"+btid).val();
    var btid_check=btid;
    if(btid_val==''){
        if(flag=='0'){
            jQuery("#"+btid+"span").html("<img align='absmiddle' src='/images/BacoError_wev9.png'>");

        }else{
            jQuery("#"+btid+"spang").html("必填");
        }

    }
    if(flag=='0'){
        jQuery("#"+btid).attr("viewtype","1");
        var chkFields = jQuery("#chkFields").val();
        if(chkFields.indexOf(","+btid_check)<0){
            if(chkFields !='') chkFields+=",";
            chkFields+=btid_check;
        }
        jQuery("#chkFields").val(chkFields);
    }

}

function removecheck(btid,flag){
    if(flag=='0'){
        jQuery("#"+btid+"span").html("");
    }else{
        jQuery("#"+btid+"span").html("");
    }
    if(flag=='0'){
        var chkFields = jQuery("#chkFields").val();
        chkFields = ","+chkFields+",";

        chkFields=chkFields.replace(","+btid+",","");
        if(chkFields.length>1){
            chkFields.substring(1,chkFields.length-1);
        }
        jQuery("#chkFields").val(chkFields);
    }

}

var flag = "0"
function save(type){
    jQuery("#submittype").val(type);
    var flag_yyzzjggz="1";
    var flag_yhkhxxjggz="1";
    var flag_syddhjggz ="1";
    var flag_cwgjxx = "1";
    var flag_zzsq="1";
    var flag_sbqd ="1";
    var flag_rbcxxkytjbx = "1";
    var flag_gsjbxxjpyq ="1";
    //sunmit 点击事件
    var info = jQuery("#info").val();
    if (flag != "0") {
        alert("请勿重复提交");
        return;
    } else {
        //flag = "1";
    }//report.submit();
    var chkFields = jQuery("#chkFields").val();
    if(type =="submit"){
        if(!check_form(report,chkFields)) return false;
    }
    var yyzzjggz_val = jQuery("#yyzzjggz").val();
    var yhkhxxjggz_val = jQuery("#yhkhxxjggz").val();
    var syddhjggz_val = jQuery("#syddhjggz").val();
    var cwgjxx_val = jQuery("#cwgjxx").val();
    var zzsq_val = jQuery("#zzsq").val();
    var sbqd_val = jQuery("#sbqd").val();
    var rbcxxkytjbx_val = jQuery("#rbcxxkytjbx").val();
    var gsjbxxjpyq_val = jQuery("#gsjbxxjpyq").val();
    var sfyyyxgfj_val = jQuery("#sfyyyzzjggz").val();//是否有营业相关附件
    var sfyyhxgfj_val = jQuery("#sfyyhkhxxjggz").val();//是否有银行相关附件
    var sfysyxgfj_val = jQuery("#sfysyddhjggz").val();//是否有商业相关附件
    //yyzzjggz,yhkhxxjggz,syddhjggz,cwgjxx,zzsq,sbqd,rbcxxkytjbx,gsjbxxjpyq
    if((!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0)
        && (!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0)
        && (!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0)
        && (!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0)
        && (!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0)
        && (!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0)
        && (!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0)
        && (!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0)){
        if(type =="submit") {
            if ((yyzzjggz_val == "" && sfyyyxgfj_val == "0") || (yhkhxxjggz_val == "" && sfyyhxgfj_val == "0") || (syddhjggz_val == "" && sfysyxgfj_val == "0")) {
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
            if (cwgjxx_val == "" || zzsq_val == "" || sbqd_val == "" || rbcxxkytjbx_val == "" || gsjbxxjpyq_val == "") {
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }
        report.submit();

    }else{
        if(!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0){
            count = count+1;
            flag_yyzzjggz = "0";

            if(type =="submit" && yyzzjggz_val == "" && sfyyyxgfj_val == "0"){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }

        }

        if(!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0){
            count = count+1;
            flag_yhkhxxjggz="0";
            if(type =="submit" && yhkhxxjggz_val == "" && yhkhxxjggz_val == "0"){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0){
            count = count+1;
            flag_syddhjggz = "0";
            if(type =="submit" && syddhjggz_val == "" && syddhjggz_val == "0"){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0){
            count = count+1;
            flag_cwgjxx = "0";
            if(type =="submit" &&cwgjxx_val == ""){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0){
            count = count+1;
            flag_zzsq = "0";
            if(type =="submit" &&zzsq_val == ""){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0){
            count = count+1;
            flag_sbqd="0";
            if(type =="submit" &&sbqd_val == ""){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0){
            count = count+1;
            flag_rbcxxkytjbx = "0";
            if(type =="submit" && rbcxxkytjbx_val == ""){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0){
            count = count+1;
            flag_gsjbxxjpyq = "0";
            if(type =="submit" && gsjbxxjpyq_val == ""){
                alert("有附件信息没上传，请上传相关附件");
                return;
            }
        }

        if(flag_yyzzjggz == "1"){
            oUpload_yyzzjggz.startUpload();
        }

        if(flag_yhkhxxjggz == "1"){
            oUpload_yhkhxxjggz.startUpload();
        }

        if(flag_syddhjggz == "1"){
            oUpload_syddhjggz.startUpload();
        }

        if(flag_cwgjxx == "1"){
            oUpload_cwgjxx.startUpload();
        }

        if(flag_zzsq == "1"){
            oUpload_zzsq.startUpload();
        }

        if(flag_sbqd == "1"){
            oUpload_sbqd.startUpload();
        }

        if(flag_rbcxxkytjbx == "1"){
            oUpload_rbcxxkytjbx.startUpload();
        }

        if(flag_gsjbxxjpyq == "1"){
            oUpload_gsjbxxjpyq.startUpload();
        }
    }

}

function onChangeSharetype(docid,type){
    var delspan="span_id_"+docid;
    var field_id="#field_id_"+docid;
    var field_del="#field_del_"+docid;

    var attachids=jQuery("#"+type).val();
    if(document.all(delspan).style.visibility=='visible'){
        document.all(delspan).style.visibility='hidden';
        jQuery(field_del).val("0");
        if(attachids==""){
            jQuery("#"+type).val(docid);
        }else{
            attachids=attachids+","+docid;
            jQuery("#"+type).val(attachids);
        }
    }else{

        document.all(delspan).style.visibility='visible';
        jQuery(field_del).val("1");
        var attachArr=attachids.split(",");
        var flag="";
        var newids = "";
        for(var i=0;i<attachArr.length;i++){
            if(attachArr[i] !=docid){
                newids = newids+flag+attachArr[i];
                flag=",";
            }
        }
        attachids = newids;
        jQuery("#"+type).val(attachids);
    }
}
window.onresize=function(){
    var clienthei = window.innerHeight
    var clientwid= window.innerWidth
    var height1 = Number(clienthei) - 71;
    if(clientwid <1200){
        clientwid =1200;
    }
    height1 = height1 + 'px';
    document.getElementById('divinner').style.height = height1;
    document.getElementsByTagName("body")[0].style.width  = clientwid+"px";

};

function onshowPlanDate1(inputname,spanname,isMustInput){
    var returnvalue;
    var oncleaingFun = function(){
        if(isMustInput== "1"){
            $ele4p(spanname).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
        }else{
            $ele4p(spanname).innerHTML = "";
        }
        $ele4p(inputname).value = '';
    }
    WdatePicker({lang:languageStr,el:spanname,onpicked:function(dp){
            returnvalue = dp.cal.getDateStr();
            $dp.$(spanname).innerHTML = returnvalue;
            $dp.$(inputname).value = returnvalue;},oncleared:oncleaingFun});

    var hidename = $ele4p(inputname).value;
    if(hidename != ""){
        $ele4p(inputname).value = hidename;
        $ele4p(spanname).innerHTML = hidename;
    }else{
        if(isMustInput=="1"){
            $ele4p(spanname).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
        }else{
            $ele4p(spanname).innerHTML = "";
        }
    }
}
function checkmustinput(elementname,spanid,viewtype){
    if (viewtype == "") {
        viewtype = $G(elementname).getAttribute("viewtype");
    }
    if(viewtype==1){
        var tmpvalue = "";
        try{
            tmpvalue = $GetEle(elementname).value;
        }catch(e){
            tmpvalue = $GetEle(elementname).value;
        }
        while(tmpvalue.indexOf(" ") >= 0){
            tmpvalue = tmpvalue.replace(" ", "");
        }
        while(tmpvalue.indexOf("\r\n") >= 0){
            tmpvalue = tmpvalue.replace("\r\n", "");
        }
        if(tmpvalue!=""){
            $GetEle(spanid).innerHTML = "";
        }else{
            $GetEle(spanid).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
            $GetEle(elementname).value = "";
        }
    }else{
        $GetEle(spanid).innerHTML = "";
    }
}
function check_form(thiswins,items)
{


    thiswin = thiswins
    items = ","+items + ",";

    var tempfieldvlaue1 = "";
    try{
        tempfieldvlaue1 = document.getElementById("htmlfieldids").value;
    }catch (e) {
    }

    for(i=1;i<=thiswin.length;i++){
        tmpname = thiswin.elements[i-1].name;
        tmpvalue = thiswin.elements[i-1].value;
        if(tmpvalue==null){
            continue;
        }

        if(tmpname!="" && items.indexOf(","+tmpname+",")!=-1){
            var __fieldhtmltype = jQuery("input[name=" + tmpname + "]").attr("__fieldhtmltype");
            if (__fieldhtmltype == '9') {
                continue;
            }

            var href = location.href;
            if(href && href.indexOf("Ext.jsp")!=-1){
                window.__oriAlert__ = true;
            }
            if(tempfieldvlaue1.indexOf(tmpname+";") == -1){
                while(tmpvalue.indexOf(" ") >= 0){
                    tmpvalue = tmpvalue.replace(" ", "");
                }
                while(tmpvalue.indexOf("\r\n") >= 0){
                    tmpvalue = tmpvalue.replace("\r\n", "");
                }

                if(tmpvalue == ""){
                    if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
                        if(window.__oriAlert__){
                            window.top.Dialog.alert("\""+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
                        }else{
                            var tempElement = thiswin.elements[i-1];
                            //ueditor必填验证
                            if (checkueditorContent(tempElement)) {
                                continue;
                            }

                            window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
                                formElementFocus(tempElement);
                            });
                        }
                        return false;
                    }else{
                        if(window.__oriAlert__){
                            try{
                                window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }catch(e){
                                Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }
                        }else{
                            try{
                                window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }catch(e){
                                Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }
                        }
                        return false;
                    }
                }
            } else {
                var divttt=document.createElement("div");
                divttt.innerHTML = tmpvalue;
                var tmpvaluettt = jQuery.trim(jQuery(divttt).text());
                if(tmpvaluettt == ""){
                    if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
                        if(window.__oriAlert__){
                            window.top.Dialog.alert("\";"+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
                        }else{
                            var tempElement = thiswin.elements[i-1];

                            //ueditor必填验证
                            if (checkueditorContent(tempElement)) {
                                continue;
                            }

                            window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
                                formElementFocus(tempElement);
                            });

                        }
                        return false;
                    }else{
                        if(window.__oriAlert__){
                            window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                        }else{
                            window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                        }
                        return false;
                    }
                }
            }
        }
    }
    return true;
}