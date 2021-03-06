
var traappli = WfForm.convertFieldNameToId("traappli");//关联差旅申请
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记

var AirAmo = WfForm.convertFieldNameToId("AirAmo");//机票费
var HoAmo = WfForm.convertFieldNameToId("HoAmo");//住宿费
var OtherAmo = WfForm.convertFieldNameToId("OtherAmo");//其他差旅费

var OriAirAmo = WfForm.convertFieldNameToId("OriAirAmo");//申请机票费
var OriHoAmo = WfForm.convertFieldNameToId("OriHoAmo");//申请住宿费
var OriOtherAmo = WfForm.convertFieldNameToId("OriOtherAmo");//申请其他差旅费
var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明
////明细4
var SZDate_dt4 = WfForm.convertFieldNameToId("SZDate", "detail_4");//日期
var SZDestCity_dt4 = WfForm.convertFieldNameToId("SZDestCity", "detail_4");//目的地
var TavelType_dt4 = WfForm.convertFieldNameToId("TavelType", "detail_4");//目的地
var SZBEGTIME_dt4 = WfForm.convertFieldNameToId("SZBEGTIME", "detail_4");//开始时间
var SZENDTIME_dt4 = WfForm.convertFieldNameToId("SZENDTIME", "detail_4");//结束时间
var SZBCheck_dt4 = WfForm.convertFieldNameToId("SZBCheck", "detail_4");//早餐check
var SZLCheck_dt4 = WfForm.convertFieldNameToId("SZLCheck", "detail_4");//午餐check
var SZDCheck_dt4 = WfForm.convertFieldNameToId("SZDCheck", "detail_4");//晚餐check
var BETFZCheck_dt4 = WfForm.convertFieldNameToId("BETFZCheck", "detail_4");//杂费check
var SZB_dt4 = WfForm.convertFieldNameToId("SZB", "detail_4");//早餐
var SZL_dt4 = WfForm.convertFieldNameToId("SZL", "detail_4");//午餐
var SZD_dt4 = WfForm.convertFieldNameToId("SZD", "detail_4");//晚餐
var SZBETFZ_dt4 = WfForm.convertFieldNameToId("SZBETFZ", "detail_4");//杂费
var SZCURR_dt4 = WfForm.convertFieldNameToId("SZCURR", "detail_4");//币别
var SZDis_dt4 = WfForm.convertFieldNameToId("SZDis", "detail_4");//杂费折扣
var SZCOST_dt4 = WfForm.convertFieldNameToId("SZCOST", "detail_4");//总金额
var SZCOSTRMB_dt4 = WfForm.convertFieldNameToId("SZCOSTRMB", "detail_4");//总金额（RMB）
var SZRate_dt4 = WfForm.convertFieldNameToId("SZRate", "detail_4");//汇率
//明细2
var TraINVNUM_dt2 = WfForm.convertFieldNameToId("TraINVNUM", "detail_2");//发票号码
var fpsfcf_dt2 = WfForm.convertFieldNameToId("fpsfcf", "detail_2");//发票是否重复
//明细3
var OTHINVNUM_dt3 = WfForm.convertFieldNameToId("OTHINVNUM", "detail_3");//发票号码
var fpsfcf_dt3 = WfForm.convertFieldNameToId("fpsfcf", "detail_3");//发票是否重复
//明细5
var HOCOST_dt5 = WfForm.convertFieldNameToId("HOCOST", "detail_5");//总金额
var HOCOSTRMB_dt5 = WfForm.convertFieldNameToId("HOCOSTRMB", "detail_5");//总金额RMB
var TotStaAmo_dt5 = WfForm.convertFieldNameToId("TotStaAmo", "detail_5");//总住宿标准金额
var isover_dt5 = WfForm.convertFieldNameToId("isover", "detail_5");//是否超标
var HOINVNUM_dt5 = WfForm.convertFieldNameToId("HOINVNUM", "detail_5");//发票号码
var fpsfcf_dt5 = WfForm.convertFieldNameToId("fpsfcf", "detail_5");//发票是否重复
//明细10
var AccCostType_dt10 = WfForm.convertFieldNameToId("AccCostType", "detail_10");//费用类型
var AccCOST_dt10 = WfForm.convertFieldNameToId("AccCOST", "detail_10");//总金额

var AccHoAmo = WfForm.convertFieldNameToId("AccHoAmo");//会计调整后报销住宿费
var AccMEEXP = WfForm.convertFieldNameToId("AccMEEXP");//会计调整后报销膳杂费
var AccOthTraCost = WfForm.convertFieldNameToId("AccOthTraCost");//会计调整后其他交通费汇总
var AccOthCost = WfForm.convertFieldNameToId("AccOthCost");//会计调整后其他费用汇总

jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    showhidecbz();
    showhidexc();
    WfForm.bindFieldChangeEvent(traappli, function(obj,id,value){
        //带出明细4膳杂费
        getMealData(value);
        setTimeout('showhidexc()','1000');
    });

    //住宿费明细超标准校验
    var changedt4field = HOCOST_dt5+","+TotStaAmo_dt5;
    WfForm.bindDetailFieldChangeEvent(changedt4field,function(id,rowIndex,value){
        var HOCOST_dt5_val = WfForm.getFieldValue(HOCOST_dt5+"_"+rowIndex);
        var TotStaAmo_dt5_val = WfForm.getFieldValue(TotStaAmo_dt5+"_"+rowIndex);
        if(HOCOST_dt5_val==""){
            HOCOST_dt5_val = "0";
        }
        if(TotStaAmo_dt5_val==""){
            TotStaAmo_dt5_val = "0";
        }
        if(Number(HOCOST_dt5_val)>Number(TotStaAmo_dt5_val)){
            WfForm.changeFieldValue(isover_dt5+"_"+rowIndex, {value: "1"});
        }else{
            WfForm.changeFieldValue(isover_dt5+"_"+rowIndex, {value: "0"});
        }
        checkdt5zsfcb();
        showhidecbz();
    });
    //明细10会计调整
    var changedt10field =  AccCostType_dt10+","+AccCOST_dt10;
    WfForm.bindDetailFieldChangeEvent(changedt10field,function(id,rowIndex,value){
        var AccHoAmo_val = "0";//会计调整后报销住宿费
        var AccMEEXP_val = "0";//会计调整后报销膳杂费
        var AccOthTraCost_val = "0";//会计调整后其他交通费汇总
        var AccOthCost_val = "0";//会计调整后其他费用汇总
        var index_dt10 = WfForm.getDetailAllRowIndexStr("detail_10");
        var isover ="0";
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
        WfForm.changeFieldValue(AccHoAmo, {value: AccHoAmo_val});
        WfForm.changeFieldValue(AccOthTraCost, {value: AccOthTraCost_val});
        WfForm.changeFieldValue(AccMEEXP, {value: AccMEEXP_val});
        WfForm.changeFieldValue(AccOthCost, {value: AccOthCost_val});

    });

    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){

        var index_dt2 = WfForm.getDetailAllRowIndexStr("detail_2");
        if(index_dt2 != ""){
            var arr_dt2 = index_dt2.split(',');
            if (arr_dt2.length > 0) {
                for (var i = 0; i < arr_dt2.length; i++) {

                    var TraINVNUM_dt2_val = WfForm.getFieldValue(TraINVNUM_dt2+"_"+arr_dt2[i]);
                    var fpsfcf_dt2_val = WfForm.getFieldValue(fpsfcf_dt2+"_"+arr_dt2[i]);
                    if(fpsfcf_dt2_val !="" && Number(fpsfcf_dt2_val)>Number("0")){
                        Dialog.alert("发票号码("+TraINVNUM_dt2_val+") 已被其他流程使用，请检查");
                        return;
                    }
                    if(TraINVNUM_dt2_val != ""){
                        var result=checkfphm(i+1,"detail_2",TraINVNUM_dt2_val);
                        if(result == "0"){
                            result=checkfphm(0,"detail_3",TraINVNUM_dt2_val);
                        }
                        if(result == "0"){
                            result=checkfphm(0,"detail_5",TraINVNUM_dt2_val);
                        }
                        if(result == "1"){
                            Dialog.alert("发票号码("+TraINVNUM_dt2_val+") 在当前单据存在重复，请检查");
                            return;
                        }
                    }
                }
            }
        }
        var index_dt3 = WfForm.getDetailAllRowIndexStr("detail_3");
        if(index_dt3 != ""){
            var arr_dt3 = index_dt3.split(',');
            if (arr_dt3.length > 0) {
                for (var i = 0; i < arr_dt3.length; i++) {

                    var OTHINVNUM_dt3_val = WfForm.getFieldValue(OTHINVNUM_dt3+"_"+arr_dt3[i]);
                    var fpsfcf_dt3_val = WfForm.getFieldValue(fpsfcf_dt3+"_"+arr_dt3[i]);
                    if(fpsfcf_dt3_val !="" && Number(fpsfcf_dt3_val)>Number("0")){
                        Dialog.alert("发票号码("+OTHINVNUM_dt3_val+") 已被其他流程使用，请检查");
                        return;
                    }
                    if(OTHINVNUM_dt3_val != ""){
                        var result=checkfphm(i+1,"detail_3",OTHINVNUM_dt3_val);
                        if(result == "0"){
                            result=checkfphm(0,"detail_5",OTHINVNUM_dt3_val);
                        }

                        if(result == "1"){
                            Dialog.alert("发票号码("+OTHINVNUM_dt3_val+") 在当前单据存在重复，请检查");
                            return;
                        }
                    }
                }
            }
        }
        var index_dt5 = WfForm.getDetailAllRowIndexStr("detail_5");
        if(index_dt5 != ""){
            var arr_dt5 = index_dt5.split(',');
            if (arr_dt5.length > 0) {
                for (var i = 0; i < arr_dt5.length; i++) {
                    var HOINVNUM_dt5_val = WfForm.getFieldValue(HOINVNUM_dt5+"_"+arr_dt5[i]);
                    var fpsfcf_dt5_val = WfForm.getFieldValue(fpsfcf_dt5+"_"+arr_dt5[i]);
                    if(fpsfcf_dt5_val !="" && Number(fpsfcf_dt5_val)>Number("0")){
                        Dialog.alert("发票号码("+HOINVNUM_dt5_val+") 已被其他流程使用，请检查");
                        return;
                    }
                    if(HOINVNUM_dt5_val != ""){
                        var result=checkfphm(i+1,"detail_5",HOINVNUM_dt5_val);

                        if(result == "1"){
                            Dialog.alert("发票号码("+HOINVNUM_dt5_val+") 在当前单据存在重复，请检查");
                            return;
                        }
                    }
                }
            }
        }



        callback();
    })

})
//检验明细5住宿费整体是否超标
function checkdt5zsfcb(){
    var index_dt5 = WfForm.getDetailAllRowIndexStr("detail_5");
    var isover ="0";
    if(index_dt5 != ""){
        var arr_dt5 = index_dt5.split(',');
        if (arr_dt5.length > 0) {
            for (var i = 0; i < arr_dt5.length; i++) {
                var HOCOST_dt5_val = WfForm.getFieldValue(HOCOST_dt5+"_"+arr_dt5[i]);
                var TotStaAmo_dt5_val = WfForm.getFieldValue(TotStaAmo_dt5+"_"+arr_dt5[i]);
                if(HOCOST_dt5_val != "" && TotStaAmo_dt5_val != ""){
                    if(Number(HOCOST_dt5_val) > Number(TotStaAmo_dt5_val)){
                        isover = "1";
                        break;
                    }
                }
            }
        }
    }
    if(isover == "1"){
        WfForm.changeFieldValue(HoExcStaMark, {value: "0"});
    }else{
        WfForm.changeFieldValue(HoExcStaMark, {value: ""});
    }
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
    if(HoExcStaMark_val == "0" ){
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
function checkfphm(index,detailtb,fphm){
    var checkfield = "";
    if(detailtb == "detail_2"){
        checkfield = TraINVNUM_dt2;
    }else if(detailtb == "detail_3"){
        checkfield = OTHINVNUM_dt3;
    }else if(detailtb == "detail_5"){
        checkfield = HOINVNUM_dt5;
    }

    var indexall = WfForm.getDetailAllRowIndexStr(detailtb);
    if(indexall != ""){
        var arr_dt = indexall.split(',');
        if (arr_dt.length > 0) {
            for (var i = index; i < arr_dt.length; i++) {
                var fphmcheck = WfForm.getFieldValue(checkfield+"_"+arr_dt[i]);
                if(fphmcheck !="" && fphm == fphmcheck){
                    return "1";
                }

            }
        }
    }

    return "0";
}
function getMealData(){
    var traappli_val = WfForm.getFieldValue(traappli);//差旅申请
    WfForm.delDetailRow("detail_4", "all");
    if(traappli_val != ""  ){

        jQuery.ajax({
            type: "POST",
            url: "/appdevjsp/HQ/FNA/TL/fna_tl_getMealDataForReim.jsp",
            data: {"rqid":traappli_val},
            dataType: "text",
            async:false,//同步 true异步
            success: function(data){
                data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
                //alert(data);
                var json = JSON.parse(data);
                for(var index=0;index<json.length;index++){
                    var obj = json[index];
                    var addObj = {};
                    addObj[SZDate_dt4] = {value:obj.Date};
                    addObj[SZDestCity_dt4] = {
                        value: obj.mdd,
                        specialobj:[
                            {id:obj.mdd,name:obj.cityname}
                        ]
                    };
                    addObj[TavelType_dt4] = {
                        value: obj.TavelType,
                        specialobj:[
                            {id:obj.TavelType,name:obj.ACTICITYNAME}
                        ]
                    };
                    addObj[SZBEGTIME_dt4] = {value:obj.beginTime};
                    addObj[SZENDTIME_dt4] = {value:obj.endTime};
                    addObj[SZBCheck_dt4] = {value:obj.zccheck};
                    addObj[SZLCheck_dt4] = {value:obj.wuccheck};
                    addObj[SZDCheck_dt4] = {value:obj.wanccheck};
                    addObj[BETFZCheck_dt4] = {value:obj.zfcheck};
                    addObj[SZB_dt4] = {value:obj.zc};
                    addObj[SZL_dt4] = {value:obj.wuc};
                    addObj[SZD_dt4] = {value:obj.wac};
                    addObj[SZBETFZ_dt4] = {value:obj.zf};
                    addObj[SZCURR_dt4] = {
                        value: obj.curr,
                        specialobj:[
                            {id:obj.curr,name:obj.currdesc}
                        ]
                    };
                    addObj[SZDis_dt4] = {value:obj.cfzk};
                    addObj[SZRate_dt4] = {value:obj.hl};

                    WfForm.addDetailRow("detail_4", addObj);

                }

            }

        });

    }
}
function accAdd(arg1,arg2){
    var r1,r2,m; try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}



