var traappli = WfForm.convertFieldNameToId("traappli");//关联差旅申请
var cygbjzsf = WfForm.convertFieldNameToId("cygbjzsf");//超预估标记(住宿费)

var AirExcMark = WfForm.convertFieldNameToId("AirExcMark");//机票费用超标标记
var HoExcMark = WfForm.convertFieldNameToId("HoExcMark");//住宿费用超标标记
var OthExcMark = WfForm.convertFieldNameToId("OthExcMark");//其他差旅费用超标标记
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记

var AirAmo = WfForm.convertFieldNameToId("AirAmo");//机票费
var HoAmo = WfForm.convertFieldNameToId("HoAmo");//住宿费
var OtherAmo = WfForm.convertFieldNameToId("OtherAmo");//其他差旅费
var MEEXP = WfForm.convertFieldNameToId("MEEXP");//报销膳杂费

var OriAirAmo = WfForm.convertFieldNameToId("OriAirAmo");//申请机票费
var OriHoAmo = WfForm.convertFieldNameToId("OriHoAmo");//申请住宿费
var OriOtherAmo = WfForm.convertFieldNameToId("OriOtherAmo");//申请其他差旅费
var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明

//明细10
var AccCostType_dt10 = WfForm.convertFieldNameToId("AccCostType", "detail_10");//费用类型
var AccCOST_dt10 = WfForm.convertFieldNameToId("AccCOST", "detail_10");//总金额

var AccHoAmo = WfForm.convertFieldNameToId("AccHoAmo");//会计调整后报销住宿费
var AccMEEXP = WfForm.convertFieldNameToId("AccMEEXP");//会计调整后报销膳杂费
var AccOthTraCost = WfForm.convertFieldNameToId("AccOthTraCost");//会计调整后其他交通费汇总
var AccOthCost = WfForm.convertFieldNameToId("AccOthCost");//会计调整后其他费用汇总
var AccOthTraExpCost = WfForm.convertFieldNameToId("AccOthTraExpCost");//会计调整后其他差旅费汇总

jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    showhidecbz();
    showhidexc();

})


function showhidexc(){
    var count_dt7 = WfForm.getDetailRowCount("detail_7");
    var count_dt8 = WfForm.getDetailRowCount("detail_8");
    if(count_dt7 == 0 && count_dt8 == 0){
        jQuery("#xcyc_1").hide();
        jQuery("#xcyc_2").hide();
    }else{
        jQuery("#xcyc_1").show();
        jQuery("#xcyc_2").show();
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
//校验各明细的发票号码  index开始的行下标

