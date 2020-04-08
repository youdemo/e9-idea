<!-- script代码，如果需要引用js文件，请使用与HTML中相同的方式。 -->
<script type="text/javascript">

var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记
var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明

var RQID = WfForm.convertFieldNameToId("RQID");//报销单号
var BLDAT = WfForm.convertFieldNameToId("BLDAT");//记账日期



jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    showhidecbz();
    showhidexc();
    jQuery("#mngz").html("<button onclick=\"domngz()\" style=\"padding: 2px 8px; float: right;\" class=\"ant-btn ant-btn-primary\" type=\"button\"><div class=\"wf-req-top-button\" style=\"color: white;\">模拟过账</div></button>");


})
function domngz(){
    var RQID_val = WfForm.getFieldValue(RQID);//膳杂费
    var BLDAT_val = WfForm.getFieldValue(BLDAT);//记账日期

    var EV_STR = "";
    jQuery.ajax({
        type: "POST",
        url: "/appdevjsp/HQ/FNA/TL/fna_tl_SapMIGZ.jsp",
        data: {'rqid':RQID_val,'IV_BLDAT':BLDAT_val},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            var json = JSON.parse(data);
            EV_STR = json.EV_STR;


        }
    });
    if(EV_STR == ""){
        alert("模拟凭证调用失败，请联系系统管理员");
        return;
    }else{
        window.open(EV_STR,"_blank");
    }
}
function showhidexc(){
    var count_dt7 = WfForm.getDetailRowCount("detail_7");
    var count_dt8 = WfForm.getDetailRowCount("detail_8");
    var count_dt10 = WfForm.getDetailRowCount("detail_10");
    if(count_dt7 == 0 && count_dt8 == 0){
        jQuery("#xcyc_1").hide();
        jQuery("#xcyc_2").hide();
    }else{
        jQuery("#xcyc_1").show();
        jQuery("#xcyc_2").show();
    }
    if(count_dt10 == 0){
        jQuery("#kjyc_1").hide();
        jQuery("#kjyc_2").hide();
        jQuery("#kjyc_3").hide();
    }else{
        jQuery("#kjyc_1").show();
        jQuery("#kjyc_2").show();
        jQuery("#kjyc_3").show();
    }
}

//显示隐藏超标字段行
function showhidecbz(){
    var HoExcStaMark_val =  WfForm.getFieldValue(HoExcStaMark);
    if(HoExcStaMark_val == "0"){
        WfForm.changeFieldAttr(ECEDES, 1);
        WfForm.changeFieldAttr(HoExcStaMark, 1);
    }else{
        WfForm.changeFieldAttr(ECEDES, 5);
        WfForm.changeFieldAttr(HoExcStaMark, 5);
    }
}

</script>
<style>
.excelTempDiv  .wf-form-textarea  .wf-form-textarea-html-show {
    min-height: 25px;
}
</style>

