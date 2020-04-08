var traappli = WfForm.convertFieldNameToId("traappli");//关联差旅申请
var cygbjzsf = WfForm.convertFieldNameToId("cygbjzsf");//超预估标记(住宿费)

var AirExcMark = WfForm.convertFieldNameToId("AirExcMark");//机票费用超标标记
var HoExcMark = WfForm.convertFieldNameToId("HoExcMark");//住宿费用超标标记
var OthExcMark = WfForm.convertFieldNameToId("OthExcMark");//其他差旅费用超标标记
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记

var AirAmo = WfForm.convertFieldNameToId("AirAmo");//机票费

var OtherAmo = WfForm.convertFieldNameToId("OtherAmo");//其他差旅费(删除)


var OthTraCost = WfForm.convertFieldNameToId("OthTraCost");//交通费
var HoAmo = WfForm.convertFieldNameToId("HoAmo");//住宿费
var OthCost =  WfForm.convertFieldNameToId("OthCost");//其他费
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

var AccMEEXPB = WfForm.convertFieldNameToId("AccMEEXPB");//会计调整膳杂费
var AccHoAmoB = WfForm.convertFieldNameToId("AccHoAmoB");//会计调整膳杂费
var AccOthTraCostB = WfForm.convertFieldNameToId("AccOthTraCostB");//会计调整膳杂费
var AccOthCostB = WfForm.convertFieldNameToId("AccOthCostB");//会计调整其他费

jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    showhidecbz();
    showhidexc();
    //明细10会计调整
    var changedt10field =  AccCostType_dt10+","+AccCOST_dt10;
    WfForm.bindDetailFieldChangeEvent(changedt10field,function(id,rowIndex,value){
        dealDt10();

    });
    dealDt10();


})

function dealDt10(){
    var AccHoAmo_val = "0";//会计调整后报销住宿费
    var AccMEEXP_val = "0";//会计调整后报销膳杂费
    var AccOthTraCost_val = "0";//会计调整后其他交通费汇总
    var AccOthCost_val = "0";//会计调整后其他费用汇总
    var index_dt10 = WfForm.getDetailAllRowIndexStr("detail_10");
    if(index_dt10 != ""){
        var arr_dt10 = index_dt10.split(',');
        if (arr_dt10.length > 0) {
            for (var i = 0; i < arr_dt10.length; i++) {
                var AccCostType_dt10_val = WfForm.getFieldValue(AccCostType_dt10+"_"+arr_dt10[i]);
                var AccCOST_dt10_val = WfForm.getFieldValue(AccCOST_dt10+"_"+arr_dt10[i]);
                if(AccCostType_dt10_val != "" && AccCOST_dt10_val != ""){
                    if(AccCostType_dt10_val=="ZFKB"){//住宿
                        AccHoAmo_val = accAdd(AccHoAmo_val,AccCOST_dt10_val);
                    }else if (AccCostType_dt10_val=="ZFKC"){//交通
                        AccOthTraCost_val = accAdd(AccOthTraCost_val,AccCOST_dt10_val);
                    }else if (AccCostType_dt10_val=="ZFKD"){//其他
                        AccOthCost_val = accAdd(AccOthCost_val,AccCOST_dt10_val);
                    }else if (AccCostType_dt10_val=="ZFKA"){//膳杂
                        AccMEEXP_val = accAdd(AccMEEXP_val,AccCOST_dt10_val);
                    }
                }
            }
        }
    }
   
    WfForm.changeFieldValue(AccMEEXPB, {value: AccMEEXP_val});
    WfForm.changeFieldValue(AccHoAmoB, {value: AccHoAmo_val});
    WfForm.changeFieldValue(AccOthTraCostB, {value: AccOthTraCost_val});
    WfForm.changeFieldValue(AccOthCostB, {value: AccOthCost_val});
    
    // var MEEXP_val =  WfForm.getFieldValue(MEEXP);
    // var HoAmo_val =  WfForm.getFieldValue(HoAmo);
    // var OthCost_val =  WfForm.getFieldValue(OthCost);
    // var OthTraCost_val =  WfForm.getFieldValue(OthTraCost);
    // if(MEEXP_val == ""){
    //     MEEXP_val ="0";
    // }
    // if(HoAmo_val == ""){
    //     HoAmo_val ="0";
    // }
    // if(OthCost_val == ""){
    //     OthCost_val ="0";
    // }
    // if(OthTraCost_val == ""){
    //     OthTraCost_val ="0";
    // }
    // AccHoAmo_val = accAdd(HoAmo_val,AccHoAmo_val);
    // AccMEEXP_val = accAdd(MEEXP_val,AccMEEXP_val);
    // AccOthTraCost_val = accAdd(OthTraCost_val,AccOthTraCost_val);
    // AccOthCost_val = accAdd(OthCost_val,AccOthCost_val);
    //
    // WfForm.changeFieldValue(AccHoAmo, {value: AccHoAmo_val});
    // WfForm.changeFieldValue(AccOthTraCost, {value: AccOthTraCost_val});
    // WfForm.changeFieldValue(AccMEEXP, {value: AccMEEXP_val});
    // WfForm.changeFieldValue(AccOthCost, {value: AccOthCost_val});
    //WfForm.changeFieldValue(AccOthTraExpCost, {value: AccOthTraExpCost_val});
}
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

function accAdd(arg1,arg2){
    var r1,r2,m; try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}
