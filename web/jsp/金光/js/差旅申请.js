
var travelStartDate =  "field51452";//差旅开始日期
var travelStartTime =  "field51453";//差旅开始时间
var travelEndDate =  "field51454";//差旅结束日期
var travelEndTime =  "field51455";//差旅结束时间
var beginDate_dt1 =  "field51474";//开始日期 明细一
var endDate_dt1 =  "field51475";//结束的日期 明细一
var endLoan_dt1 =  "field51492";//目的地 明细一
var hotelArea_dt2 =  "field51494";//住宿地点 明细二
var inDate_dt2 =  "field51480";//入住日期 明细 二
var outDate_dt2 =  "field51481";//退房日期 明细 二
var ticketData_dt3 =  "field51501";//机票预定时间 明细 三
var ticketBackDate_dt3 =  "field51507";//机票返回日期 明细 三
var traveller =  "field51447";//出差人
var treavelpersoncode = "field51508";//出差人工号
var startdate = "field51452";//差旅开始日期
var teavelJobTitle = "field51448";//出差人岗位
var companycode = "field51451";//公司代码
var clz = "field51490";//差旅组
var gncldj = "field51471";//国内差旅等级
var gwcldj = "field51472";//国外差旅等级
var ERGRUTEXT = WfForm.convertFieldNameToId("ERGRUTEXT");//国内差旅等级
var ERKLATEXT = WfForm.convertFieldNameToId("ERKLATEXT");//国外差旅等级
var SAPComment = WfForm.convertFieldNameToId("SAPComment");//SAP获取人员信息注释

var cbzx = "field51473";//成本中心
var cbzxmc = "field70895";//成本中心名称
var FICTR = "field78887";//基金中心
var project = "field51487";//项目
var department = "field51459";//部门
var HoAmo = "field51464";//住宿费
var HoAmoHide = "field59156";//住宿费隐藏
var ISCtrip = "field66385";//携程预定信息 ckeck 框
var ctripRow = "#ctrip";//携程预定信息行
var projectType = "field51458";//项目类型
var AGroupType = "field51486";//A+组别
var airm = "field51514";//目的
var fundCenter = "field51516";//基金中心Key
var fundCenterName = "field51748";//基金中心
var Hotelstandard = "field51483";//酒店标准
var AProjectRow = ".AProject";//a+ 项目行
var projectRow = ".project";//项目明细行
var hrmScope = "field51489";//人事范围
var mobile = "field51450";//联系方式
// var companycode = "field51451";//公司代码
var SAPComment = WfForm.convertFieldNameToId("SAPComment");//SAP获取人员信息注释
var APLUS = WfForm.convertFieldNameToId("APLUS");//系统属性
var PROJECT680 = WfForm.convertFieldNameToId("PROJECT680");//项目680
var wbsstr = WfForm.convertFieldNameToId("PROJECT680");//项目描述
var wbs = WfForm.convertFieldNameToId("wbs");//项目编号
jQuery(document).ready(function(){
    WfForm.bindFieldChangeEvent(startdate, function(obj,id,value){
        getTravelPersonDate();
    });

    WfForm.bindFieldChangeEvent(treavelpersoncode, function(obj,id,value){
        setTimeout("getTravelPersonDate()",500);
    });
    WfForm.bindFieldChangeEvent(project, function(obj,id,value){
        WfForm.changeFieldValue(department, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
    })
    showhidesm680();
    WfForm.bindFieldChangeEvent(projectType, function(obj,id,value){
        WfForm.changeFieldValue(AGroupType, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(airm, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(project, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(department, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(PROJECT680, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(wbsstr, {value: ""});
        WfForm.changeFieldValue(wbs, {value: ""});
        showhidesm680();

    });
    WfForm.bindFieldChangeEvent(APLUS, function(obj,id,value){
        showhidesm680();
    })
    //携程预订ckeck框
    WfForm.bindFieldChangeEvent(ISCtrip, function(obj,id,value){
        setctripLayout();
        WfForm.delDetailRow("detail_2","all");
        WfForm.delDetailRow("detail_3","all");
    });

    //WfForm.bindFieldChangeEvent(treavelpersoncode, function(obj,id,value){
    //setTimeout("getTravelPersonDate()",500);
    //});
    WfForm.bindFieldChangeEvent(HoAmoHide, function(obj,id,value){
        checkHoAmoMoney();
    });
    //差旅开始日期
    WfForm.bindFieldChangeEvent(travelStartDate, function(obj,id,value){
        var datavalue = value;
        var indexall = WfForm.getDetailAllRowIndexStr("detail_1");
        if(indexall != ""){
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                var fieldksrq = beginDate_dt1+"_"+arr_dt[0];
                WfForm.changeFieldValue(fieldksrq, {
                    value: datavalue,
                    specialobj:[
                        {id:datavalue,name:datavalue}
                    ]
                });

            }
        }
        indexall = WfForm.getDetailAllRowIndexStr("detail_2");
        if(indexall != ""){
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                var fieldksrq = inDate_dt2+"_"+arr_dt[0];
                WfForm.changeFieldValue(fieldksrq, {
                    value: datavalue,
                    specialobj:[
                        {id:datavalue,name:datavalue}
                    ]
                });

            }
        }
        indexall = WfForm.getDetailAllRowIndexStr("detail_3");
        if(indexall != ""){
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                var fieldksrq = ticketData_dt3+"_"+arr_dt[0];
                WfForm.changeFieldValue(fieldksrq, {
                    value: datavalue,
                    specialobj:[
                        {id:datavalue,name:datavalue}
                    ]
                });

            }
        }
    });
    //新增明细1
    WfForm.registerAction(WfForm.ACTION_ADDROW+"1", function(index){
        var indexall = WfForm.getDetailAllRowIndexStr("detail_1");
        var lastindex =-1;
        if(indexall != "") {
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                for (var i = 0; i < arr_dt.length; i++) {
                    if(arr_dt[i]==index){
                        if(lastindex != -1){
                            var endDate_dt1_val = WfForm.getFieldValue(endDate_dt1+"_"+lastindex);
                            //endDate_dt1_val = addDate(endDate_dt1_val,1);
                            var fieldksrq = beginDate_dt1+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: endDate_dt1_val,
                                specialobj:[
                                    {id:endDate_dt1_val,name:endDate_dt1_val}
                                ]
                            });
                        }else{
                            var datavalue =  WfForm.getFieldValue(travelStartDate);
                            var fieldksrq = beginDate_dt1+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: datavalue,
                                specialobj:[
                                    {id:datavalue,name:datavalue}
                                ]
                            });
                        }
                    }else{
                        lastindex=arr_dt[i];
                    }
                }
            }
        }
    });
    //明细2新增
    WfForm.registerAction(WfForm.ACTION_ADDROW+"2", function(index){
        var indexall = WfForm.getDetailAllRowIndexStr("detail_2");
        var lastindex =-1;
        if(indexall != "") {
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                for (var i = 0; i < arr_dt.length; i++) {
                    if(arr_dt[i]==index){
                        if(lastindex != -1){
                            var endDate_dt2_val = WfForm.getFieldValue(outDate_dt2+"_"+lastindex);
                            //endDate_dt2_val = addDate(endDate_dt2_val,1);
                            var fieldksrq = inDate_dt2+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: endDate_dt2_val,
                                specialobj:[
                                    {id:endDate_dt2_val,name:endDate_dt2_val}
                                ]
                            });
                        }else{
                            var datavalue =  WfForm.getFieldValue(travelStartDate);
                            var fieldksrq = inDate_dt2+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: datavalue,
                                specialobj:[
                                    {id:datavalue,name:datavalue}
                                ]
                            });
                        }
                    }else{
                        lastindex=arr_dt[i];
                    }
                }
            }
        }
    });
    //明细3新增
    WfForm.registerAction(WfForm.ACTION_ADDROW+"3", function(index){
        var indexall = WfForm.getDetailAllRowIndexStr("detail_3");
        var lastindex =-1;
        if(indexall != "") {
            var arr_dt = indexall.split(',');
            if (arr_dt.length > 0) {
                for (var i = 0; i < arr_dt.length; i++) {
                    if(arr_dt[i]==index){
                        if(lastindex != -1){
                            var endDate_dt3_val = WfForm.getFieldValue(ticketBackDate_dt3+"_"+lastindex);
                            endDate_dt3_val = addDate(endDate_dt3_val,1);
                            var fieldksrq = ticketData_dt3+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: endDate_dt3_val,
                                specialobj:[
                                    {id:endDate_dt3_val,name:endDate_dt3_val}
                                ]
                            });
                        }else{
                            var datavalue =  WfForm.getFieldValue(travelStartDate);
                            var fieldksrq = ticketData_dt3+"_"+index;
                            WfForm.changeFieldValue(fieldksrq, {
                                value: datavalue,
                                specialobj:[
                                    {id:datavalue,name:datavalue}
                                ]
                            });
                        }
                    }else{
                        lastindex=arr_dt[i];
                    }
                }
            }
        }
    });

    setTimeout('setctripLayout()',500);
    //流程提交校验
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        var flag = "1";
        var checkDate_x = checkDate() ;
        var checkDate_1 = checkDateDT1();
        var hotelCheck_x = hotelCheck();
        var aircraftCheck_x = aircraftCheck();
        //时间 格式
        if(flag == '1' && checkDate_x == '1' ){
            alert("差旅结束时间格式不对！");
            flag = "0"
        }
        if(flag == '1' && checkDate_1 == "1"){
            alert("明细出差开始/结束日期 范围应在差旅开始/结束日期范围内！");
            flag = "0"
        }
        //if(flag == '1' && checkDate_1 == "2"){
        // alert("出差日期存在重叠日期！");
        //flag = "0"
        //}
        //if(flag == '1' && checkDate_1 == "3"){
        //    alert("出差结束日期不能早于开始日期！");
        //   flag = "0"
        //}
        //if(flag == '1' && checkDate_1 == "4" ){
        //   alert("出差日期不能间断！");
        //    flag = "0"
        // }
        //明细1 下一行的出差开始日期等于上一行的结束日期  出差结束日期不能相同
        var index_dt1= WfForm.getDetailAllRowIndexStr("detail_1");
        var lastenddate = "";
        var enddatelist = "";
        var flag1 = "";
        if(index_dt1 != "") {
            var arr_dt1 = index_dt1.split(',');
            if (arr_dt1.length > 0) {
                for (var i = 0; i < arr_dt1.length; i++) {
                    var beginDate_dt1_val =   WfForm.getFieldValue(beginDate_dt1+"_"+arr_dt1[i]);
                    var endDate_dt1_val =  WfForm.getFieldValue(endDate_dt1+"_"+arr_dt1[i]);
                    if(beginDate_dt1_val !="" && endDate_dt1_val !="" &&comparisonDate(beginDate_dt1_val,endDate_dt1_val) == '1'){
                        alert("明细出差结束日期不能早于开始日期！");
                        return;
                    }
                    if(i != 0 ){
                        if(beginDate_dt1_val !="" && lastenddate !="" && beginDate_dt1_val != lastenddate){
                            alert("明细出差开始日期必须等于上一行的结束日期");
                            return;
                        }
                    }
                    if(endDate_dt1_val != "" && enddatelist != "" && enddatelist.indexOf(endDate_dt1_val)>=0){
                        alert("明细出差结束日期不能重复,请检查");
                        return;
                    }
                    lastenddate = endDate_dt1_val
                    if(endDate_dt1_val != ""){
                        enddatelist = enddatelist+flag1+endDate_dt1_val;
                        flag1 = ",";
                    }

                }
            }
        }

        if(flag == '1' && hotelCheck_x == "0"){
            alert("退房日期不能早于入住日期！");
            flag = "0"
        }
        //if(flag == '1' && hotelCheck_x == "1"){
        //   alert("住宿日期存在重叠日期！");
        //   flag = "0"
        //}
        if(flag == '1' && hotelCheck_x == "2"){
            alert("住宿日期的开始/结束日期范围不在差旅开始/结束日期范围内！");
            flag = "0"
        }
        if(flag == '1' && hotelCheck_x == "3" ){
            alert("住宿地点必须在出差目的地中！");
            flag = "0"
        }
        if(flag == '1' && aircraftCheck_x == "0"  ){
            alert("机票预订出发日期应该在出差范围内！");
            flag = "0"
        }
        var pastDiff  = checkTravelTime();
        if( flag == '1' && pastDiff != '0'){
            alert("您的差旅申请时间和历史申请流程时间存在重复（重复流程ID为：" + pastDiff + "）！");
            flag = "0"
        }

        //住宿费的校验
        if(WfForm.getFieldValue(HoAmo) != '' || WfForm.getFieldValue(HoAmoHide) === '2' ){
            if((Number(WfForm.getFieldValue(HoAmo)) > Number(WfForm.getFieldValue(HoAmoHide))) && flag == '1'){
                alert("住宿费金额不能大于住宿标准金额！");
                flag = "0"
            }
        }

        // 携程酒店/机票 明细表 校验必填
        if(flag == '1' && checkctrip() == '1' ){
            alert("您勾选了携程预定信息，请填写后再提交！");
            flag = "0"
        }

        if(flag == '1' && hotelCheck_x == '4'){
            alert("单日住宿标准必须大于0！");
            flag = "0"
        }

        if(flag == "1"){
            callback();
        }
    });
});

function showhidesm680(){

    var projectType_val = WfForm.getFieldValue(projectType);
    var APLUS_val = WfForm.getFieldValue(APLUS);
    if(projectType_val == "1"){
        if(APLUS_val == "0"){
            jQuery("#prj680").hide();
            jQuery("#prj666").show();
            WfForm.changeFieldAttr(project, 3);
            WfForm.changeFieldAttr(department, 3);
            WfForm.changeFieldAttr(PROJECT680, 2);
            WfForm.changeFieldAttr(wbs, 2);
            WfForm.changeFieldValue(PROJECT680, {
                value: "",
                specialobj:[
                    {id:"",name:""}
                ]
            });
            WfForm.changeFieldValue(wbsstr, {value: ""});
            WfForm.changeFieldValue(wbs, {value: ""});
        }else if(APLUS_val == "1"){
            jQuery("#prj680").show();
            jQuery("#prj666").hide();
            WfForm.changeFieldAttr(project, 2);
            WfForm.changeFieldAttr(department, 3);
            WfForm.changeFieldAttr(PROJECT680, 3);
            WfForm.changeFieldAttr(wbs, 3);
            WfForm.changeFieldValue(project, {
                value: "",
                specialobj:[
                    {id:"",name:""}
                ]
            });
            WfForm.changeFieldValue(department, {
                value: "",
                specialobj:[
                    {id:"",name:""}
                ]
            });
        }

    }else{
        WfForm.changeFieldAttr(project, 2);
        WfForm.changeFieldAttr(department, 2);
        WfForm.changeFieldAttr(PROJECT680, 2);
        WfForm.changeFieldAttr(wbs, 2);
    }
}

//日期增加函数
function addDate(date,days){
    var d=new Date(date);
    d.setDate(d.getDate()+days);
    var m=d.getMonth()+1;
    return d.getFullYear()+'-'+m+'-'+d.getDate();
}

function getTravelPersonDate(){
    var treavelperson_val = WfForm.getFieldValue(treavelpersoncode);//出差人工号
    var startdate_val = WfForm.getFieldValue(startdate);//差旅开始日期
    var teavelJobTitle_val = WfForm.getFieldValue(teavelJobTitle);//出差人岗位
    var companycode_val = WfForm.getFieldValue(companycode);//公司代码
    if(treavelperson_val != "" && startdate_val != "" && teavelJobTitle_val != "" && companycode_val != "" ){
        jQuery.ajax({
            type: "POST",
            url: "/appdevjsp/HQ/FNA/TL/fna_tl_getTravelPersonBaseData.jsp",
            data: {"ryid":treavelperson_val,"gsdm":companycode_val,"startdate":startdate_val,"gw":teavelJobTitle_val},
            dataType: "text",
            async:false,//同步 true异步
            success: function(data){
                data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
//alert(data);
                var json = JSON.parse(data);
                var EV_STATU = json.EV_STATU;

                if(EV_STATU = "1"){
                    WfForm.changeFieldValue(clz, {
                        value: json.EV_MOREI,
                        specialobj:[
                            {id:json.EV_MOREI,name:json.EV_MOREI}
                        ]
                    });
                    WfForm.changeFieldValue(gncldj, {value:json.EV_ERGRU});
                    WfForm.changeFieldValue(gwcldj, {value:json.EV_ERKLA});
                    WfForm.changeFieldValue(ERGRUTEXT, {value:json.EV_ERGRU_TEXT});
                    WfForm.changeFieldValue(ERKLATEXT, {value:json.EV_ERKLA_TEXT});
                    WfForm.changeFieldValue(cbzx, {value:json.EV_KOSTL});
                    WfForm.changeFieldValue(FICTR, {value:json.EV_FICTR});
                    WfForm.changeFieldValue(cbzxmc, {value:json.EV_KTEXT});
                    WfForm.changeFieldValue(fundCenter, {value:json.EV_FICTR});
                    WfForm.changeFieldValue(fundCenterName, {
                        value: json.EV_BEZEICH,
                        specialobj:[
                            {id:json.EV_BEZEICH,name:json.EV_BEZEICH}
                        ]
                    });
                    WfForm.changeFieldValue(SAPComment, {value:json.EV_MESSAGE});
                    WfForm.changeFieldValue(APLUS, {value:json.EV_APLUS});

                }else{
                    WfForm.changeFieldValue(clz, {
                        value: "",
                        specialobj:[
                            {id:"",name:""}
                        ]
                    });
                    WfForm.changeFieldValue(fundCenterName, {
                        value: "",
                        specialobj:[

                        ]
                    });
                    WfForm.changeFieldValue(fundCenter, {value:""});
                    WfForm.changeFieldValue(gncldj, {value:""});
                    WfForm.changeFieldValue(gwcldj, {value:""});
                    WfForm.changeFieldValue(ERGRUTEXT, {value:""});
                    WfForm.changeFieldValue(ERKLATEXT, {value:""});
                    WfForm.changeFieldValue(cbzx, {value:""});
                    WfForm.changeFieldValue(cbzxmc, {value:""});
                    WfForm.changeFieldValue(SAPComment, {value:""});
                    WfForm.changeFieldValue(APLUS, {value:""});
                    alert("获取差旅人员基本信息失败，请联系系统管理员");
                }
            }

        });

    }else{

        WfForm.changeFieldValue(clz, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(fundCenterName, {
            value: "",
            specialobj:[

            ]
        });
        WfForm.changeFieldValue(fundCenter, {value:""});
        WfForm.changeFieldValue(gncldj, {value:""});
        WfForm.changeFieldValue(gwcldj, {value:""});
        WfForm.changeFieldValue(ERGRUTEXT, {value:""});
        WfForm.changeFieldValue(ERKLATEXT, {value:""});
        WfForm.changeFieldValue(cbzx, {value:""});
        WfForm.changeFieldValue(cbzxmc, {value:""});
        WfForm.changeFieldValue(SAPComment, {value:""});
    }
}

function checkDate(){
    var travelEndTime_val = WfForm.getFieldValue(travelEndTime);
    if(travelEndTime_val  == '00:00'){
        return 1;
    }
}

// 明细 开始 结束日期  1 日期范围  2 日期重叠 3 结束日期 4 日期间断
function checkDateDT1(){
    var travelStartDate_val = WfForm.getFieldValue(travelStartDate);
    var travelEndDate_val = WfForm.getFieldValue(travelEndDate);
    var beginDate = "";//开始日期
    var endDate = "";//结束日期
    var dateArray = "";
    var beginDate_dt1Val = getDetailVals("detail_1",beginDate_dt1).split(',');
    var endDate_dt1Val = getDetailVals("detail_1",endDate_dt1).split(',');
    if(beginDate_dt1Val != ''){
        for( var i =0;i<beginDate_dt1Val.length;i++){
            if(beginDate_dt1Val[i] != ''){

                if (beginDate == ''){
                    beginDate = beginDate_dt1Val[i];
                }else{
                    if(comparisonDate(beginDate_dt1Val[i],beginDate) == '0'){
                        beginDate = beginDate_dt1Val[i];
                    }
                }

                if (endDate == ''){
                    endDate = endDate_dt1Val[i];
                }else{
                    if(comparisonDate(endDate_dt1Val[i],endDate) == '1'){
                        endDate = endDate_dt1Val[i];
                    }
                }

                //if(comparisonDate(beginDate_dt1Val[i],endDate_dt1Val[i]) == '1'){
                //   return 3;
                //}
                dateArray += getDateStr(beginDate_dt1Val[i],endDate_dt1Val[i],0)+",";
            }
        }
    }
    //  if (beginDate != '' && travelStartDate_val != '' && comparisonDate(travelStartDate_val,beginDate) != '0'){
//        return 1;
//    }

//    if (endDate != '' && travelEndDate_val != '' && comparisonDate(endDate,travelEndDate_val) == '1'){
    //       return 1;
//   }

    if (beginDate != '' && travelStartDate_val != '' && travelStartDate_val != beginDate ){
        return 1;
    }
    if (endDate != '' && travelEndDate_val != ''&& travelEndDate_val != endDate ){
        return 1;
    }

    //if(isRept(dateArray) == 1){
    //   return 2;
    // }

    //if(isUninterrupted(beginDate,endDate,dateArray) == 1){
    //   return 4;
    //}
}

function isRept(dateArray){
    var arrayList=dateArray.split(",");
    if(arrayList != ''){
        for(var i=0;i<arrayList.length-1;i++){
            for(var j=i+1;j<arrayList.length-1;j++){
                if((arrayList[i] == arrayList[j]) &&  arrayList[i] != ''){
                    return 1;
                    break;
                }
            }
        }
    }
}

//判断日期是否不间断
function isUninterrupted(startDate,endDate,dateArray){
    var aDate = WfForm.getFieldValue(travelStartDate);
    var bDate = WfForm.getFieldValue(travelEndDate);
    var bzDateList=dateArray.split(",").length - 1;
    // var csDateList=getDateStr(aDate,bDate,0).split(",").length;
    var csDateList=getDateStr(startDate,endDate,0).split(",").length;
    if((bzDateList != csDateList) &&  csDateList != '' && bzDateList != ''){
        return 1;
    }else{
        return 0
    }
}

//判断日期的大小
function comparisonDate(date1,date2){
    var oDate1 = new Date(date1.replace(/-/g, "/"));
    var oDate2 = new Date(date2.replace(/-/g, "/"));
    if(oDate1.getTime() <= oDate2.getTime()){
        return "0";
    }else{
        return "1";
    }
}

//迭代循环两个日期
function getDateStr(startDate, endDate, dayLength) {
    var str = startDate;
    for (var i = 0 ;; i++) {
        var getDate = getTargetDate(startDate, dayLength);
        startDate = getDate;
        if (getDate <= endDate) {
            str += ','+getDate;
        } else {
            break;
        }
    }
    return str;
}
function getTargetDate(date,dayLength) {
    dayLength = dayLength + 1;
    var tempDate = new Date(date);
    tempDate.setDate(tempDate.getDate() + dayLength);
    var year = tempDate.getFullYear();
    var month = tempDate.getMonth() + 1 < 10 ? "0" + (tempDate.getMonth() + 1) : tempDate.getMonth() + 1;
    var day = tempDate.getDate() < 10 ? "0" + tempDate.getDate() : tempDate.getDate();
    return year + "-" + month + "-" + day;
}

//机票明细表校验
function aircraftCheck(){

    var ticketData_dt3Array = getDetailVals("detail_3",ticketData_dt3).split(',');
    var travelStartDateVal = WfForm.getFieldValue(travelStartDate);
    var travelEndDateVal = WfForm.getFieldValue(travelEndDate);

    if(ticketData_dt3Array != ''){
        for(var i = 0;i<ticketData_dt3Array.length;i++){
            if(comparisonDate(travelStartDateVal,ticketData_dt3Array[i]) == '1' ){
                return 0;
            }

            if(comparisonDate(ticketData_dt3Array[i],travelEndDateVal) != '0'){
                return 0;
            }
        }
    }
}
//住宿明细表校验
function hotelCheck(){
    var dateArray = "";
    var travelStartDateVal = WfForm.getFieldValue(travelStartDate);
    var travelEndDateVal = WfForm.getFieldValue(travelEndDate);
    var maxDate = "";
    var minDate = "";
    var inDate_dt2Array = getDetailVals("detail_2",inDate_dt2).split(',');
    var outDate_dt2Array = getDetailVals("detail_2",outDate_dt2).split(',');
    var hotelArea_dt2Array = getDetailVals("detail_2",hotelArea_dt2).split(',');
    var endLoan_dt1Array = getDetailVals("detail_1",endLoan_dt1).split(',');
    var HotelstandardArray = getDetailVals("detail_2",Hotelstandard).split(',');

    for(var i = 0;i<inDate_dt2Array.length;i++){
        if((comparisonDate(inDate_dt2Array[i],outDate_dt2Array[i]) == '1') && inDate_dt2Array[i] !='' && outDate_dt2Array[i] != ''){
            return 0;
            break;
        }

        if (minDate == ''){
            minDate = outDate_dt2Array[i];
        }else{
            if(comparisonDate(inDate_dt2Array[i],minDate) == '0'){
                minDate = inDate_dt2Array[i];
            }
        }

        if (maxDate == ''){
            maxDate = outDate_dt2Array[i];
        }else{
            if(comparisonDate(outDate_dt2Array[i],maxDate) == '1'){
                maxDate = outDate_dt2Array[i];
            }
        }

        if(Number(HotelstandardArray[i].replace(/,/g,',')) <= 0 && HotelstandardArray[i].length > 0 ){
            return 4;
        }

        //地点校验
        var ideneit = 1;
        for(var j = 0;j<endLoan_dt1Array.length;j++){
            if((hotelArea_dt2Array[i] == endLoan_dt1Array[j]) && ideneit != 2 && endLoan_dt1Array[j].length > 0 && hotelArea_dt2Array[i].length >0 ){
                ideneit = 2;
            }
            if(endLoan_dt1Array[j].length == 0 || hotelArea_dt2Array[i].length == 0){
                ideneit = 2;
            }

        }

        if(ideneit == 1){
            return 3;
        }

        //拼接数组 明细开始 结束日期
        //alert(inDate_dt2Array[i]+";;"+outDate_dt2Array[i]);
        dateArray += getDateStr(inDate_dt2Array[i],outDate_dt2Array[i],0)+",";

        if (inDate_dt2Array[i] != '' && travelStartDateVal != '' && comparisonDate(travelStartDateVal,minDate) != '0'){
            return 2;
            break;
        }

        if (outDate_dt2Array[i] != '' && travelEndDateVal != '' && comparisonDate(maxDate,travelEndDateVal) == '1'){
            return 2;
            break;
        }

    }

    //if(isRept(dateArray) == 1){
    //    return 1;
    //}
}

//差旅时间重复校验
function checkTravelTime(){

    var beginDateVal =  WfForm.getFieldValue(travelStartDate) //开始日期
    var beginTimeVal =  WfForm.getFieldValue(travelStartTime)//开始时间
    var endDateVal =  WfForm.getFieldValue(travelEndDate)//结束日期
    var endTimeVal =  WfForm.getFieldValue(travelEndTime)//结束时间
    var travellerVal = WfForm.getFieldValue(traveller)//出差人
    var baseinfo=WfForm.getBaseInfo()
    var requestidVal = baseinfo.requestid;//流程编号

    if(requestidVal == ''){
        requestidVal = 0;
    }
    jQuery.ajax({
        async: false,
        type : "POST",
        url :'/appdevjsp/HQ/FNA/checkTime.jsp?ksrq='+beginDateVal+'&kssj='+beginTimeVal+'&jsrq='+endDateVal+'&jssj='+endTimeVal+'&ccr='+travellerVal+'&requestid='+requestidVal+'&type=0',
        dataType : "text",
        success : function(data){
            paramter= data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
        }
    });
    return paramter;
}
//住宿费校验
function checkHoAmoMoney(){
    var HoAmoHideVal = WfForm.getFieldValue(HoAmoHide);
    WfForm.changeFieldValue(HoAmo,{value:HoAmoHideVal});
}

//获取明细下标
function getDetailVals(detail, fieldid) {
    var index = WfForm.getDetailAllRowIndexStr(detail)
    var arr = []
    if (index !== '') {
        arr = index.split(',')
    }
    var value = '';

    if (arr.length > 0) {
        for (var i = 0; i < arr.length; i++) {
            value += WfForm.getFieldValue(fieldid + '_' + arr[i]) + ','
        }
    }
    value=(value.substring(value.length-1)==',')?value.substring(0,value.length-1):value;
    return value
}

//携程预定信息行隐藏显示
function setctripLayout(){
    var ctripVal = WfForm.getFieldValue(ISCtrip);
    if(ctripVal === '1'){
        jQuery(ctripRow).show();
    }else{
        jQuery(ctripRow).hide();
    }
}

//携程 酒店 机票 明细表校验
function checkctrip(){
    var ctripVal = WfForm.getFieldValue(ISCtrip);
    if(ctripVal === '1'){
        var index1 = WfForm.getDetailAllRowIndexStr("detail_2").length;
        var index2 = WfForm.getDetailAllRowIndexStr("detail_3").length;
        if(index1 === 0 && index2 === 0){
            return 1;
        }
    }

}