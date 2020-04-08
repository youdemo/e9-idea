var AirAmo = WfForm.convertFieldNameToId("AirAmo");//机票费
var HoAmo = WfForm.convertFieldNameToId("HoAmo");//住宿费
var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记
//明细10
var AccCostType_dt10 = WfForm.convertFieldNameToId("AccCostType", "detail_10");//费用类型
var AccCostTypeSAP_dt10 = WfForm.convertFieldNameToId("AccCostTypeSAP", "detail_10");//费用类型SAP
var AccMEEXPB = WfForm.convertFieldNameToId("AccMEEXPB");//会计调整膳杂费
var AccHoAmoB = WfForm.convertFieldNameToId("AccHoAmoB");//会计调整膳杂费
var AccOthTraCostB = WfForm.convertFieldNameToId("AccOthTraCostB");//会计调整膳杂费
var AccOthCostB = WfForm.convertFieldNameToId("AccOthCostB");//会计调整其他费

var AccCOST_dt10 = WfForm.convertFieldNameToId("AccCOST", "detail_10");//总金额(RMB)
var AccCOSTOri_dt10 = WfForm.convertFieldNameToId("AccCOSTOri", "detail_10");//总金额
var AccTaxRate_dt10 = WfForm.convertFieldNameToId("AccTaxRate", "detail_10");//税率
var AccTaxM_dt10= WfForm.convertFieldNameToId("AccTaxM", "detail_10");//税额

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
    setTimeout("dealDt10()",1000);
    var changesedt10field = AccCOSTOri_dt10+","+AccTaxRate_dt10;
    WfForm.bindDetailFieldChangeEvent(changesedt10field,function(id,rowIndex,value){
        var AccCOSTOri_dt10_val = WfForm.getFieldValue(AccCOSTOri_dt10+"_"+rowIndex);
        var AccTaxRate_dt10_val = WfForm.getFieldValue(AccTaxRate_dt10+"_"+rowIndex);
        var AccTaxM_dt10_val = 0;
        if(AccCOSTOri_dt10_val != "" && AccTaxRate_dt10_val !=""){
            AccTaxM_dt10_val = getse(AccCOSTOri_dt10_val,AccTaxRate_dt10_val);
        }
        WfForm.changeFieldValue(AccTaxM_dt10+"_"+rowIndex,{value:AccTaxM_dt10_val});
    })

})
function getse(zje,sl){
    return accMul(accDiv(zje,accAdd("1",sl)),sl).toFixed(2);
}
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
        WfForm.changeFieldAttr(ECEDES, 1);
        WfForm.changeFieldAttr(HoExcStaMark, 1);
    }else{
        WfForm.changeFieldAttr(ECEDES, 5);
        WfForm.changeFieldAttr(HoExcStaMark, 5);
    }
}

//校验各明细的发票号码  index开始的行下标
function accAdd(arg1,arg2){
    var r1,r2,m; try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}

function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""))
        r2=Number(arg2.toString().replace(".",""))
        return (r1/r2)*pow(10,t2-t1);
    }
}