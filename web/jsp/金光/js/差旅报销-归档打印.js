
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记
var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明

jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    showhidecbz();
    showhidexc();

})


function showhidexc(){
    var count_dt4 = WfForm.getDetailRowCount("detail_4");
    var count_dt2 = WfForm.getDetailRowCount("detail_2");
    var count_dt5 = WfForm.getDetailRowCount("detail_5");
    var count_dt6 = WfForm.getDetailRowCount("detail_6");
    var count_dt3 = WfForm.getDetailRowCount("detail_3");

    if(count_dt4 == 0){
        jQuery("#szf_1").hide();
        jQuery("#szf_2").hide();
        jQuery("#szf_3").hide();
    }else{
        jQuery("#szf_1").show();
        jQuery("#szf_2").show();
        jQuery("#szf_3").show();
    }

    if(count_dt2 == 0){
        jQuery("#jtf_1").hide();
        jQuery("#jtf_2").hide();
        jQuery("#jtf_3").hide();
    }else{
        jQuery("#jtf_1").show();
        jQuery("#jtf_2").show();
        jQuery("#jtf_3").show();
    }
    if(count_dt5 == 0){
        jQuery("#zsf_1").hide();
        jQuery("#zsf_2").hide();
        jQuery("#zsf_3").hide();
    }else{
        jQuery("#zsf_1").show();
        jQuery("#zsf_2").show();
        jQuery("#zsf_3").show();
    }

    if(count_dt6 == 0){
        jQuery("#jpf_1").hide();
        jQuery("#jpf_2").hide();
        jQuery("#jpf_3").hide();
    }else{
        jQuery("#jpf_1").show();
        jQuery("#jpf_2").show();
        jQuery("#jpf_3").show();
    }

    if(count_dt3 == 0){
        jQuery("#qtf_1").hide();
        jQuery("#qtf_2").hide();
        jQuery("#qtf_3").hide();
    }else{
        jQuery("#qtf_1").show();
        jQuery("#qtf_2").show();
        jQuery("#qtf_3").show();
    }
}
//显示隐藏超标字段行
function showhidecbz(){

    var HoExcStaMark_val =  WfForm.getFieldValue(HoExcStaMark);

    if(HoExcStaMark_val == "0"){
        WfForm.changeFieldAttr(HoExcStaMark, 2);
    }else{
        WfForm.changeFieldAttr(HoExcStaMark, 4);
    }
    if(HoExcStaMark_val == "0"){
        WfForm.changeFieldAttr(ECEDES, 3);
        jQuery("#cbbj").show();
        jQuery("#cbsm").show();
    }else{
        WfForm.changeFieldAttr(ECEDES, 2);
        jQuery("#cbbj").hide();
        jQuery("#cbsm").hide();
    }

}