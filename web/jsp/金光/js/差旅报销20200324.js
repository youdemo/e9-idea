
var traappli = WfForm.convertFieldNameToId("traappli");//关联差旅申请
var HoExcStaMark = WfForm.convertFieldNameToId("HoExcStaMark");//住宿费用超标准标记

var AirAmo = WfForm.convertFieldNameToId("AirAmo");//机票费
var HoAmo = WfForm.convertFieldNameToId("HoAmo");//住宿费

var ECEDES = WfForm.convertFieldNameToId("ECEDES");//超标说明
var BTRBEGDA = WfForm.convertFieldNameToId("BTRBEGDA");//差旅开始日期
var BTRENDDA = WfForm.convertFieldNameToId("BTRENDDA");//差旅结束日期

var BtrPER = WfForm.convertFieldNameToId("BtrPER");//出差人
var TravelNumber = WfForm.convertFieldNameToId("TravelNumber");//出差单号
var BtrBukrs = WfForm.convertFieldNameToId("BtrBukrs");//出差单号
var PayInDoll = WfForm.convertFieldNameToId("PayInDoll");//是否美元支付
var MoreThan90D = WfForm.convertFieldNameToId("MoreThan90D");//报销是否超90天


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
var SZRate_dt4 = WfForm.convertFieldNameToId("SZRate", "detail_4");//汇率
var TRAFCheck_dt4 = WfForm.convertFieldNameToId("TRAFCheck", "detail_4");//交通check
var traffic_dt4 = WfForm.convertFieldNameToId("traffic", "detail_4");//交通
var hasSZB_dt4 = WfForm.convertFieldNameToId("hasSZB", "detail_4");//是否含早餐
var Deductions_dt4 = WfForm.convertFieldNameToId("Deductions", "detail_4");//扣减
//明细2
var TraINVNUM_dt2 = WfForm.convertFieldNameToId("TraINVNUM", "detail_2");//发票号码
var fpsfcf_dt2 = WfForm.convertFieldNameToId("fpsfcf", "detail_2");//发票是否重复
var TraCOSTRMB_dt2 = WfForm.convertFieldNameToId("TraCOSTRMB", "detail_2");//总金额(RMB)
var TraCOST_dt2 = WfForm.convertFieldNameToId("TraCOST", "detail_2");//总金额
var TraTaxRate_dt2 = WfForm.convertFieldNameToId("TraTaxRate", "detail_2");//税率
var TraTaxM_dt2 = WfForm.convertFieldNameToId("TraTaxM", "detail_2");//税额
var City1_dt2 = WfForm.convertFieldNameToId("City1", "detail_2");//城市
var Selinv_dt2 = WfForm.convertFieldNameToId("Selinv", "detail_2");//选择发票
var TraRate_dt2 = WfForm.convertFieldNameToId("TraRate", "detail_2");//汇率
var TraCurr_dt2 = WfForm.convertFieldNameToId("TraCurr", "detail_2");//币别
//明细3
var OTHINVNUM_dt3 = WfForm.convertFieldNameToId("OTHINVNUM", "detail_3");//发票号码
var fpsfcf_dt3 = WfForm.convertFieldNameToId("fpsfcf", "detail_3");//发票是否重复
var OTHCOSTRMB_dt3 = WfForm.convertFieldNameToId("OTHCOSTRMB", "detail_3");//总金额(RMB)
var OTHCOST_dt3 = WfForm.convertFieldNameToId("OTHCOST", "detail_3");//总金额
var OTHTaxRate_dt3 = WfForm.convertFieldNameToId("OTHTaxRate", "detail_3");//税率
var OTHTaxM_dt3= WfForm.convertFieldNameToId("OTHTaxM", "detail_3");//税额
var City1_dt3 = WfForm.convertFieldNameToId("City1", "detail_3");//城市
var selinv_dt3 = WfForm.convertFieldNameToId("selinv", "detail_3");//选择发票
var OTHRate_dt3 = WfForm.convertFieldNameToId("OTHRate", "detail_3");//汇率
var OTHCURR_dt3= WfForm.convertFieldNameToId("OTHCURR", "detail_3");//币别
//明细5
var HOCOST_dt5 = WfForm.convertFieldNameToId("HOCOST", "detail_5");//总金额
var TotStaAmo_dt5 = WfForm.convertFieldNameToId("TotStaAmo", "detail_5");//总住宿标准金额
var isover_dt5 = WfForm.convertFieldNameToId("isover", "detail_5");//是否超标
var HOINVNUM_dt5 = WfForm.convertFieldNameToId("HOINVNUM", "detail_5");//发票号码
var fpsfcf_dt5 = WfForm.convertFieldNameToId("fpsfcf", "detail_5");//发票是否重复
var HOCOSTRMB_dt5 = WfForm.convertFieldNameToId("HOCOSTRMB", "detail_5");//总金额(RMB)
var HOTaxRate_dt5 = WfForm.convertFieldNameToId("HOTaxRate", "detail_5");//税率
var HOTaxM_dt5= WfForm.convertFieldNameToId("HOTaxM", "detail_5");//税额
var HODestCity1_dt5 = WfForm.convertFieldNameToId("HODestCity1", "detail_5");//住宿地点
var HORate_dt5 = WfForm.convertFieldNameToId("HORate", "detail_5");//汇率
var HOCURR_dt5 = WfForm.convertFieldNameToId("HOCURR", "detail_5");//币别

var HOBEGDA_dt5 = WfForm.convertFieldNameToId("HOBEGDA", "detail_5");//入住日期
var HOENDDA_dt5 = WfForm.convertFieldNameToId("HOENDDA", "detail_5");//退房日期

var selinv_dt5 = WfForm.convertFieldNameToId("selinv", "detail_5");//选择发票

//明细6
var PCOSTRMB_dt6 = WfForm.convertFieldNameToId("PCOSTRMB", "detail_6");//总金额(RMB)
var PCOST_dt6 = WfForm.convertFieldNameToId("PCOST", "detail_6");//总金额
var PTaxRate_dt6 = WfForm.convertFieldNameToId("PTaxRate", "detail_6");//税率
var PTaxM_dt6= WfForm.convertFieldNameToId("PTaxM", "detail_6");//税额
var PRate_dt6= WfForm.convertFieldNameToId("PRate", "detail_6");//汇率
var PCURR_dt6= WfForm.convertFieldNameToId("PCURR", "detail_6");//币别

//明细11
var RecDate_dt11= WfForm.convertFieldNameToId("RecDate", "detail_11");//请假调休日期

jQuery(document).ready(function(){
    jQuery("[name=copybutton3]").css('display','none');
    jQuery("[name=addbutton3]").css('display','none');
    jQuery("[name=delbutton3]").css('display','none');
    jQuery("#xzfp_btn").html("<button onclick=\"showfp()\" style=\"margin-left:6px;padding: 2px 8px;\" class=\"ant-btn ant-btn-primary\" type=\"button\"><div class=\"wf-req-top-button\" style=\"color: white;\">选择发票</div></button>");
    showhidecbz();
    showhidexc();
    disableszbcheck();
    showhidesfcts();
    setTimeout("cutmealmoney()","500");
    WfForm.bindFieldChangeEvent(traappli, function(obj,id,value){
        //清空明细城市
        emptyDetailCity();
        //带出明细4膳杂费
        getMealData(value);

    });
    //是否美元支付发生变化 汇率发生变化
    WfForm.bindFieldChangeEvent(PayInDoll, function(obj,id,value){
        changealldthl();

    });
    //是否超90
    WfForm.bindFieldChangeEvent(MoreThan90D, function(obj,id,value){
        showhidesfcts();

    });
    var changefieldbz = TraCurr_dt2+","+HOCURR_dt5+","+PCURR_dt6+","+OTHCURR_dt3;
    WfForm.bindDetailFieldChangeEvent(changefieldbz,function(id,rowIndex,value){
        var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
        var hl_val = getHl(value,PayInDoll_val);
        if(id==TraCurr_dt2){
            WfForm.changeFieldValue(TraRate_dt2+"_"+rowIndex,{value:hl_val});
        }else if(id==HOCURR_dt5){
            WfForm.changeFieldValue(HORate_dt5+"_"+rowIndex,{value:hl_val});
        }else if(id==PCURR_dt6){
            WfForm.changeFieldValue(PRate_dt6+"_"+rowIndex,{value:hl_val});
        }else if(id==OTHCURR_dt3){
            WfForm.changeFieldValue(OTHRate_dt3+"_"+rowIndex,{value:hl_val});
        }
    })
    WfForm.registerAction(WfForm.ACTION_ADDROW+"2", function(index){
        var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
        var TraCurr_dt2_val = WfForm.getFieldValue(TraCurr_dt2+"_"+index);
        var hl_val = getHl(TraCurr_dt2_val,PayInDoll_val);
        WfForm.changeFieldValue(TraRate_dt2+"_"+index,{value:hl_val});
    });
    WfForm.registerAction(WfForm.ACTION_ADDROW+"5", function(index){
        var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
        var HOCURR_dt5_val = WfForm.getFieldValue(HOCURR_dt5+"_"+index);
        var hl_val = getHl(HOCURR_dt5_val,PayInDoll_val);
        WfForm.changeFieldValue(HORate_dt5+"_"+index,{value:hl_val});
    });
    WfForm.registerAction(WfForm.ACTION_ADDROW+"6", function(index){
        var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
        var PCURR_dt6_val = WfForm.getFieldValue(PCURR_dt6+"_"+index);
        var hl_val = getHl(PCURR_dt6_val,PayInDoll_val);
        WfForm.changeFieldValue(PRate_dt6+"_"+index,{value:hl_val});
    });
    WfForm.registerAction(WfForm.ACTION_ADDROW+"3", function(index){
        var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
        var OTHCURR_dt3_val = WfForm.getFieldValue(OTHCURR_dt3+"_"+index);
        var hl_val = getHl(OTHCURR_dt3_val,PayInDoll_val);
        WfForm.changeFieldValue(OTHRate_dt3+"_"+index,{value:hl_val});
    });
    //交通费计算税额
    var changedt2field = TraCOST_dt2+","+TraTaxRate_dt2;
    WfForm.bindDetailFieldChangeEvent(changedt2field,function(id,rowIndex,value){
        var TraCOST_dt2_val = WfForm.getFieldValue(TraCOST_dt2+"_"+rowIndex);
        var TraTaxRate_dt2_val = WfForm.getFieldValue(TraTaxRate_dt2+"_"+rowIndex);
        var TraTaxM_dt2_val = 0;
        if(TraCOST_dt2_val != "" && TraTaxRate_dt2_val !=""){
            TraTaxM_dt2_val = getse(TraCOST_dt2_val,TraTaxRate_dt2_val);
        }
        WfForm.changeFieldValue(TraTaxM_dt2+"_"+rowIndex,{value:TraTaxM_dt2_val});
    })

    //住宿费计算税额
    var changesedt5field = HOCOST_dt5+","+HOTaxRate_dt5;
    WfForm.bindDetailFieldChangeEvent(changesedt5field,function(id,rowIndex,value){
        var HOCOST_dt5_val = WfForm.getFieldValue(HOCOST_dt5+"_"+rowIndex);
        var HOTaxRate_dt5_val = WfForm.getFieldValue(HOTaxRate_dt5+"_"+rowIndex);
        var HOTaxM_dt5_val = 0;
        if(HOCOST_dt5_val != "" && HOTaxRate_dt5_val !=""){
            HOTaxM_dt5_val = getse(HOCOST_dt5_val,HOTaxRate_dt5_val);
        }
        WfForm.changeFieldValue(HOTaxM_dt5+"_"+rowIndex,{value:HOTaxM_dt5_val});
    })
    //机票费计算税额
    var changesedt6field = PCOST_dt6+","+PTaxRate_dt6;
    WfForm.bindDetailFieldChangeEvent(changesedt6field,function(id,rowIndex,value){
        var PCOST_dt6_val = WfForm.getFieldValue(PCOST_dt6+"_"+rowIndex);
        var PTaxRate_dt6_val = WfForm.getFieldValue(PTaxRate_dt6+"_"+rowIndex);
        var PTaxM_dt6_val = 0;
        if(PCOST_dt6_val != "" && PTaxRate_dt6_val !=""){
            PTaxM_dt6_val = getse(PCOST_dt6_val,PTaxRate_dt6_val);
        }
        WfForm.changeFieldValue(PTaxM_dt6+"_"+rowIndex,{value:PTaxM_dt6_val});
    })
    //其他费计算税额
    var changesedt3field = OTHCOST_dt3+","+OTHTaxRate_dt3;
    WfForm.bindDetailFieldChangeEvent(changesedt3field,function(id,rowIndex,value){
        var OTHCOST_dt3_val = WfForm.getFieldValue(OTHCOST_dt3+"_"+rowIndex);
        var OTHTaxRate_dt3_val = WfForm.getFieldValue(OTHTaxRate_dt3+"_"+rowIndex);
        var OTHTaxM_dt3_val = 0;
        if(OTHCOST_dt3_val != "" && OTHTaxRate_dt3_val !=""){
            OTHTaxM_dt3_val = getse(OTHCOST_dt3_val,OTHTaxRate_dt3_val);
        }
        WfForm.changeFieldValue(OTHTaxM_dt3+"_"+rowIndex,{value:OTHTaxM_dt3_val});
    })

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


    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        getSapleaveDay();
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

            var BTRBEGDA_val = WfForm.getFieldValue(BTRBEGDA);//差旅开始日期
            var BTRENDDA_val = WfForm.getFieldValue(BTRENDDA);//差旅结束日期
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
                    //1.入住日期和退房日期必须在出差日期范围内;2.入住日期和退房日期不能相等;
                    var HOBEGDA_dt5_val =  WfForm.getFieldValue(HOBEGDA_dt5+"_"+arr_dt5[i]);//入住日期
                    var HOENDDA_dt5_val =  WfForm.getFieldValue(HOENDDA_dt5+"_"+arr_dt5[i]);//退房日期

                    if(HOBEGDA_dt5_val !=""&& HOENDDA_dt5_val !="" && checkdate(HOBEGDA_dt5_val,HOENDDA_dt5_val)=="1"){
                        Dialog.alert("住宿费明细"+(i+1)+"行退房日期须大于入住日期，请检查");
                        return;
                    }
                    if(HOBEGDA_dt5_val != ""){
                        if(checkdate(HOBEGDA_dt5_val,BTRBEGDA_val)=="0" || checkdate(BTRENDDA_val,HOBEGDA_dt5_val)=="0"){
                            Dialog.alert("住宿费明细行入住日期和退房日期必须在出差日期范围内，请检查");
                            return;
                        }
                    }
                    if(HOENDDA_dt5_val != ""){
                        if(checkdate(HOENDDA_dt5_val,BTRBEGDA_val)=="0" || checkdate(BTRENDDA_val,HOENDDA_dt5_val)=="0"){
                            Dialog.alert("住宿费明细行入住日期和退房日期必须在出差日期范围内，请检查");
                            return;
                        }
                    }

                }
            }
        }



        callback();
    })

})

//变化4个明细汇率
function changealldthl(){
    var PayInDoll_val = WfForm.getFieldValue(PayInDoll);
    var index_dt2 = WfForm.getDetailAllRowIndexStr("detail_2");
    if(index_dt2 != "") {
        var arr_dt2 = index_dt2.split(',');
        if (arr_dt2.length > 0) {
            for (var i = 0; i < arr_dt2.length; i++) {
                var bz_val =  WfForm.getFieldValue(TraCurr_dt2+"_"+arr_dt2[i]);
                var hl_val=getHl(bz_val,PayInDoll_val);
                WfForm.changeFieldValue(TraRate_dt2+"_"+arr_dt2[i], {value: hl_val});

            }
        }
    }
    var index_dt5 = WfForm.getDetailAllRowIndexStr("detail_5");
    if(index_dt5 != "") {
        var arr_dt5 = index_dt5.split(',');
        if (arr_dt5.length > 0) {
            for (var i = 0; i < arr_dt5.length; i++) {
                var bz_val =  WfForm.getFieldValue(HOCURR_dt5+"_"+arr_dt5[i]);
                var hl_val=getHl(bz_val,PayInDoll_val);
                WfForm.changeFieldValue(HORate_dt5+"_"+arr_dt5[i], {value: hl_val});

            }
        }
    }
    var index_dt6 = WfForm.getDetailAllRowIndexStr("detail_6");
    if(index_dt6 != "") {
        var arr_dt6 = index_dt6.split(',');
        if (arr_dt6.length > 0) {
            for (var i = 0; i < arr_dt6.length; i++) {
                var bz_val =  WfForm.getFieldValue(PCURR_dt6+"_"+arr_dt6[i]);
                var hl_val=getHl(bz_val,PayInDoll_val);
                WfForm.changeFieldValue(PRate_dt6+"_"+arr_dt6[i], {value: hl_val});

            }
        }
    }
    var index_dt3 = WfForm.getDetailAllRowIndexStr("detail_3");
    if(index_dt3 != "") {
        var arr_dt3 = index_dt3.split(',');
        if (arr_dt3.length > 0) {
            for (var i = 0; i < arr_dt3.length; i++) {
                var bz_val =  WfForm.getFieldValue(OTHCURR_dt3+"_"+arr_dt3[i]);
                var hl_val=getHl(bz_val,PayInDoll_val);
                WfForm.changeFieldValue(OTHRate_dt3+"_"+arr_dt3[i], {value: hl_val});

            }
        }
    }
}

/**
 * 获取汇率
 * @param bz 币种
 * @param sfmyzf 是否美元支付
 */
function getHl(bz,sfmyzf){
    if(bz==""||sfmyzf==""){
        return "";
    }
    var hl = "";
    jQuery.ajax({
        type: "POST",
        url: "/appdevjsp/HQ/FNA/TL/fna_tl_get_hl.jsp",
        data: {'sfmyzf':sfmyzf,'bz':bz},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            hl= data;
        }
    });
    return  hl;
}
/**
 * 获取sap请假数据
 */
function getSapleaveDay(){
    var traappli_val = WfForm.getFieldValue(traappli);//出差单号
    WfForm.delDetailRow("detail_11", "all");
    var EV_STATU = "";
    jQuery.ajax({
        type: "POST",
        url: "/appdevjsp/HQ/FNA/TL/fna_tl_getLeaveDays.jsp",
        data: {'clh':traappli_val},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            var json = JSON.parse(data);
            EV_STATU = json.EV_STATU;
            var jsonarr = json.dt;
            for(var index=0;index<jsonarr.length;index++){
                var obj = jsonarr[index];
                var addObj = {};
                addObj[RecDate_dt11] = {value:obj.BEGDA};


                WfForm.addDetailRow("detail_11", addObj);


            }
        }
    });
    cutmealmoney();
    //setTimeout("cutmealmoney()","500");
}

/**
 * 有请假日期扣减对应的餐费
 */
function cutmealmoney(){
    var index_dt11 = WfForm.getDetailAllRowIndexStr("detail_11");
    var index_dt4 = WfForm.getDetailAllRowIndexStr("detail_4");
    if(index_dt4 != "") {
        var arr_dt4 = index_dt4.split(',');
        if (arr_dt4.length > 0) {
            for (var i = 0; i < arr_dt4.length; i++) {
                var Deductions_dt4_val = WfForm.getFieldValue(Deductions_dt4 + "_" + arr_dt4[i]);
                if(Deductions_dt4_val == "1"){
                    WfForm.changeFieldValue(Deductions_dt4 + "_" + arr_dt4[i], {value: "0"});
                }
            }
        }
    }
    if(index_dt11 != ""){
        var arr_dt11 = index_dt11.split(',');
        if (arr_dt11.length > 0) {
            for (var i = 0; i < arr_dt11.length; i++) {
                var RecDate_dt11_val = WfForm.getFieldValue(RecDate_dt11+"_"+arr_dt11[i]);
                if(RecDate_dt11_val != ""){
                    if(index_dt4 != ""){
                        var arr_dt4 = index_dt4.split(',');
                        if (arr_dt4.length > 0) {
                            for (var j = 0; j < arr_dt4.length; j++) {
                                var SZDate_dt4_val = WfForm.getFieldValue(SZDate_dt4 + "_" + arr_dt4[j]);
                                if (RecDate_dt11_val == SZDate_dt4_val) {
                                    WfForm.changeFieldValue(SZBCheck_dt4 + "_" + arr_dt4[j], {value: "0"});
                                    WfForm.changeFieldValue(SZLCheck_dt4 + "_" + arr_dt4[j], {value: "0"});
                                    WfForm.changeFieldValue(SZDCheck_dt4 + "_" + arr_dt4[j], {value: "0"});

                                    WfForm.changeFieldAttr(SZBCheck_dt4 + "_" + arr_dt4[j], 1);
                                    WfForm.changeFieldAttr(SZLCheck_dt4 + "_" + arr_dt4[j], 1);
                                    WfForm.changeFieldAttr(SZDCheck_dt4 + "_" + arr_dt4[j], 1);

                                    WfForm.changeFieldValue(Deductions_dt4 + "_" + arr_dt4[j], {value: "1"});

                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
//获取几个明细已经选择的发票
function getfpids(){
    var fpids = "";
    var flag = "";
    var fpids_dt2 = getdetailfpid(Selinv_dt2,"detail_2");//交通
    var fpids_dt5 = getdetailfpid(selinv_dt5,"detail_5");//住宿
    var fpids_dt3 = getdetailfpid(selinv_dt3,"detail_3");//其他
    if(fpids_dt2 != ""){
        fpids = fpids + flag + fpids_dt2;
        flag = ",";
    }
    if(fpids_dt5 != ""){
        fpids = fpids + flag + fpids_dt5;
        flag = ",";
    }
    if(fpids_dt3 != ""){
        fpids = fpids + flag + fpids_dt3;
    }
    return fpids;
}


//获取明细已经选择的发票
function getdetailfpid(fieldid,detail){
    var index = WfForm.getDetailAllRowIndexStr(detail)
    var arr = []
    var fpids = "";
    var flag = "";
    if (index !== '') {
        arr = index.split(',')
        if (arr.length > 0) {
            for (var i = 0; i < arr.length; i++) {
                var fpid = WfForm.getFieldValue(fieldid+"_"+arr[i]);
                if(fpid != ""){
                    fpids = fpids + flag +fpid;
                    flag = ",";
                }
            }
        }
    }
    return fpids;

}
//弹框显示发票选择
function showfp(){
    var yxzfpids=getfpids();
    var BtrPER_val = WfForm.getFieldValue(BtrPER);
    var title = "";
    var url = "/appdevjsp/HQ/FNA/TL/fna_tl_show_fpinfo_url.jsp?yxzfpids="+yxzfpids+"&ryid="+BtrPER_val;
    var diag_vote = new window.top.Dialog();
    diag_vote.currentWindow = window;
    diag_vote.Width = 600;
    diag_vote.Height = 450;
    diag_vote.Modal = true;
    diag_vote.Title = title;
    diag_vote.URL = url;
    diag_vote.isIframe=false;
    diag_vote.CancelEvent=function(){diag_vote.close();
    };
    diag_vote.show();
}
function dodetail(checkids){
    if(checkids == ""){
        return;
    }
    jQuery.ajax({
        type: "POST",
        url: "/appdevjsp/HQ/FNA/TL/fna_tl_get_fp_detailinfo.jsp",
        data: {'checkids':checkids},
        dataType: "text",
        async:false,//同步   true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            var json = JSON.parse(data);
            for(var index=0;index<json.length;index++){
                var obj = json[index];
                var Costtypetype = obj.Costtypetype;
                if(Costtypetype == "交通") {
                    var addObj = {};
                    addObj[Selinv_dt2] = {
                        value: obj.fp,
                        specialobj:[
                            {id:obj.fp,name:obj.INVOICENUMBER}
                        ]
                    };
                    WfForm.addDetailRow("detail_2", addObj);

                }else if(Costtypetype == "住宿") {
                    var addObj = {};
                    addObj[selinv_dt5] = {
                        value: obj.fp,
                        specialobj:[
                            {id:obj.fp,name:obj.INVOICENUMBER}
                        ]
                    };
                    WfForm.addDetailRow("detail_5", addObj);

                }else if(Costtypetype == "其他") {
                    var addObj = {};
                    addObj[selinv_dt3] = {
                        value: obj.fp,
                        specialobj:[
                            {id:obj.fp,name:obj.INVOICENUMBER}
                        ]
                    };
                    WfForm.addDetailRow("detail_3", addObj);
                }
            }
        }
    });
}
//获取弹框选择发票id
function savefphm(checkids){
    setTimeout("dodetail('"+checkids+"')",300);
}
//差旅申请变化的时候清空城市
function emptyDetailCity(){
    var index_dt2 = WfForm.getDetailAllRowIndexStr("detail_2");
    var index_dt3 = WfForm.getDetailAllRowIndexStr("detail_3");
    var index_dt5 = WfForm.getDetailAllRowIndexStr("detail_5");
    if(index_dt2 != ""){
        var arr_dt2 = index_dt2.split(',');
        if (arr_dt2.length > 0) {
            for (var i = 0; i < arr_dt2.length; i++) {
                WfForm.changeFieldValue(City1_dt2+"_"+arr_dt2[i], {
                    value: "",
                    specialobj:[
                        {id:"",name:""}
                    ]
                });
            }
        }
    }
    if(index_dt3 != ""){
        var arr_dt3 = index_dt3.split(',');
        if (arr_dt3.length > 0) {
            for (var i = 0; i < arr_dt3.length; i++) {
                WfForm.changeFieldValue(City1_dt3+"_"+arr_dt3[i], {
                    value: "",
                    specialobj:[
                        {id:"",name:""}
                    ]
                });
            }
        }
    }
    if(index_dt5 != ""){
        var arr_dt5 = index_dt5.split(',');
        if (arr_dt5.length > 0) {
            for (var i = 0; i < arr_dt5.length; i++) {
                WfForm.changeFieldValue(HODestCity1_dt5+"_"+arr_dt5[i], {
                    value: "",
                    specialobj:[
                        {id:"",name:""}
                    ]
                });
            }
        }
    }
}

function getse(zje,sl){
    return accMul(accDiv(zje,accAdd("1",sl)),sl).toFixed(2);
}

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
function showhidesfcts(){
    var MoreThan90D_val =  WfForm.getFieldValue(MoreThan90D);
     if(MoreThan90D_val == "0" ){
         jQuery("#sfcts").hide();
     }else{
         jQuery("#sfcts").show();
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
                    //addObj[SZRate_dt4] = {value:obj.hl};
                    addObj[TRAFCheck_dt4] = {value:obj.jtcheck};
                    addObj[traffic_dt4] = {value:obj.jtf};
                    addObj[hasSZB_dt4] = {value:obj.haszbs};
                    if(obj.haszbs == "1"){
                        addObj[Deductions_dt4] = {value:"2"};
                    }else{
                        addObj[Deductions_dt4] = {value:"0"};
                    }


                    WfForm.addDetailRow("detail_4", addObj);

                }

            }

        });


        disableszbcheck();
        setTimeout("getSapleaveDay()","500");
    }
}
//将早餐置成只读
function disableszbcheck(){
    var indexall = WfForm.getDetailAllRowIndexStr("detail_4");
    if(indexall != ""){
        var arr_dt = indexall.split(',');
        if (arr_dt.length > 0) {
            for (var i = 0; i < arr_dt.length; i++) {
                var hasSZB_dt4_val = WfForm.getFieldValue(hasSZB_dt4+"_"+arr_dt[i]);
                if(hasSZB_dt4_val=="1"){
                    WfForm.changeFieldAttr(SZBCheck_dt4+"_"+arr_dt[i], 1);
                }

            }
        }
    }
}
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

function checkdate(date1,date2){
    var oDate1 = new Date(date1.replace(/-/g, "/"));
    var oDate2 = new Date(date2.replace(/-/g, "/"));
    if(oDate1.getTime() < oDate2.getTime()){
        return "0";
    } else {
        return "1";
    }
}
