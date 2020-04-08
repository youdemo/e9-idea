
    var travelStartDate =  "#field46387";//差旅开始日期
    var travelStartTime =  "#field46388";//差旅开始时间
    var travelEndDate =  "#field46389";//差旅结束日期
    var travelEndTime =  "#field46390";//差旅结束时间
    var beginDate_dt1 =  "#field46415_";//开始日期 明细一
    var endDate_dt1 =  "#field46416_";//结束的日期 明细一
    var endLoan_dt1 =  "#field46491_";//目的地 明细一
    var hotelArea_dt2 =  "#field46493_";//住宿地点 明细二
    var inDate_dt2 =  "#field46421_";//入住日期 明细 二
    var outDate_dt2 =  "#field46422_";//退房日期 明细 二
    var ticketData_dt3 =  "#field46517_";//机票预定时间 明细 二
    var traveller =  "#field46382";//出差人
    var travelOld =  "#field46520";//原出差流程
    var applicantType =  "#field46379";//出差类型
    ///////////////////////////////
    var treavelperson = "field46382";//出差人
    var startdate = "field46387";//差旅开始日期
    var teavelJobTitle = "field46383";//出差人岗位
    var companycode = "field46386";//公司代码
    var clz = "field46489";//差旅组
    var gncldj = "field46412";//国内差旅等级
    var gwcldj = "field46413";//国外差旅等级
    var cbzx = "field46414";//成本中心
    var jjzx = "";//基金中心
    jQuery(document).ready(function(){
        setTimeout("getTravelPersonDate()",1000);
        WfForm.bindFieldChangeEvent(startdate, function(obj,id,value){
            getTravelPersonDate();
        });
        WfForm.bindFieldChangeEvent(treavelperson, function(obj,id,value){
            setTimeout("getTravelPersonDate()",1000);
        });
        //流程提交校验
        WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
            var flag = "1";

            //时间 格式
            if(checkDate() == '1'){
                window.Dialog.alert("差旅结束时间格式不对。")
                flag = "0"
            }

            if(checkDateDT1() == "1"){
                window.Dialog.alert("明细 开始/结束日期 范围应在差旅开始/结束日期范围内。")
                flag = "0"
            }

            if(checkDateDT1() == "2"){
                window.Dialog.alert("出差日期存在重叠日期。");
                flag = "0"
            }

            if(checkDateDT1() == "3"){
                window.Dialog.alert("出差结束日期不能早于开始日期。");
                flag = "0"
            }

            if(checkDateDT1() == "4"){
                window.Dialog.alert("出差中开始日期和结束日期不能有间隔。");
                flag = "0"
            }

            if(hotelCheck() == "0"){
                window.Dialog.alert("退房日期不能早于入住日期。");
                flag = "0"
            }

            if(hotelCheck() == "1"){
                window.Dialog.alert("住宿日期存在重叠日期");
                flag = "0"
            }

            if(hotelCheck() == "2"){
                window.Dialog.alert("住宿日期的开始/结束日期范围应在差旅开始/结束日期范围内。");
                flag = "0"
            }

            if(hotelCheck() == "3"){
                window.Dialog.alert("住宿地点必须在出差目的地中。");
                flag = "0"
            }

            if(aircraftCheck() == "0"){
                window.Dialog.alert("机票预订应该在出差范围内。");
                flag = "0"
            }

            if(Number(checkTravelTime()) > 0 ){
                window.Dialog.alert("您的差旅申请时间和历史申请流程时间存在重复。");
                flag = "0"
            }

            //实时校验原出差流程的状态
            if(jQuery(applicantType).val() === '1' || jQuery(applicantType).val() === '2' ){

                if(Number(chekRequestStatus()) != 0 ){
                    window.Dialog.alert("您的原差旅流程已经在审批中，请核对。");
                    flag = "0"
                }
            }


            if(flag == "1"){
                callback();
            }
        });


    });

    function getTravelPersonDate(){
        var treavelperson_val = WfForm.getFieldValue(treavelperson);//出差人
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
                    var json = JSON.parse(data);
                    alert(json);
                }

            });

        }else{

            WfForm.changeFieldValue(clz, {
                value: "",
                specialobj:[
                    {id:"",name:""}
                ]
            });
            WfForm.changeFieldValue(gncldj, {value:""});
            WfForm.changeFieldValue(gwcldj, {value:""});
            WfForm.changeFieldValue(cbzx, {value:""});
           // WfForm.changeFieldValue(jjzx, {value:""});
        }
    }
    //结束时间 不能是00：00
    function checkDate(){
        var travelStartDate_val = jQuery(travelStartDate).val();
        var travelStartTime_val = jQuery(travelStartTime).val();
        var travelEndDate_val =  jQuery(travelEndDate).val();
        var travelEndTime_val =  jQuery(travelEndTime).val();

        if(travelEndTime_val  == '00:00'){
            return 1;
        }
    }

    // 明细 开始 结束日期  1 日期范围  2 日期重叠 3 结束日期 4 日期间断
    function checkDateDT1(){
        var travelStartDate_val = jQuery(travelStartDate).val();
        var travelEndDate_val =  jQuery(travelEndDate).val();
        var beginDate = "";//开始日期
        var endDate = "";//结束日期
        var dateArray = "";

        for(var i = 0;i<jQuery("#indexnum0").val();i++){
            if(jQuery(beginDate_dt1+i).length > 0 && jQuery(beginDate_dt1+i).val()  != ''){

                if (beginDate == ''){
                    beginDate = jQuery(beginDate_dt1+i).val();
                }else{
                    if(comparisonDate(jQuery(beginDate_dt1+i).val(),beginDate) == '0'){
                        beginDate = jQuery(beginDate_dt1+i).val()
                    }
                }

                if (endDate == ''){
                    endDate = jQuery(endDate_dt1+i).val();
                }else{
                    if(comparisonDate(jQuery(endDate_dt1+i).val(),beginDate) == '1'){
                        endDate = jQuery(endDate_dt1+i).val()
                    }
                }

                if(comparisonDate(jQuery(beginDate_dt1+i).val(),jQuery(endDate_dt1+i).val()) == '1'){
                    return 3;
                }

                //拼接数组 明细开始 结束日期
                dateArray += getDateStr(jQuery(beginDate_dt1+i).val(),jQuery(endDate_dt1+i).val(),0)+",";
            }
        }

        if (beginDate != '' && travelStartDate_val != '' && comparisonDate(travelStartDate_val,beginDate) != '0'){
            return 1;
        }

        if (endDate != '' && travelEndDate_val != '' && comparisonDate(endDate,travelEndDate_val) == '1'){
            return 1;
        }

        if(isRept(dateArray) == 1){
            return 2;
        }

        if(isUninterrupted(beginDate,endDate,dateArray) == 1){
            return 4;
        }



    }

    function isRept(dateArray){
        var arrayList=dateArray.split(",");
        for(var i=0;i<arrayList.length-1;i++){
            for(var j=i+1;j<arrayList.length-1;j++){
                if(arrayList[i] == arrayList[j]){
                    return 1;
                    break;
                }
            }
        }
    }

    //判断日期是否不间断
    function isUninterrupted(startDate,endDate,dateArray){
        var aDate = jQuery(travelStartDate).val();
        var bDate = jQuery(travelEndDate).val();
        var bzDateList=dateArray.split(",").length - 1;
        var csDateList=getDateStr(aDate,bDate,0).split(",").length;


        if(bzDateList != csDateList){
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

        for(var i = 0;i<jQuery("#indexnum2").val();i++){
            if(jQuery(ticketData_dt3+i).length > 0 && jQuery(ticketData_dt3+i).val()  != ''){

                if(comparisonDate(jQuery(travelStartDate).val(),jQuery(ticketData_dt3+i).val()) == '1' ){
                    return 0;
                }
                if(comparisonDate(jQuery(ticketData_dt3+i).val(),jQuery(travelEndDate).val()) != '0'){
                    return 0;
                }
            }
        }
    }

    //住宿明细表校验
    function hotelCheck(){
        var dateArray = "";
        var travelStartDate_val = jQuery(travelStartDate).val();
        var travelEndDate_val =  jQuery(travelEndDate).val();
        var maxDate = "";
        var minDate = "";

        for(var i = 0;i<jQuery("#indexnum1").val();i++){
            if(jQuery(inDate_dt2+i).length > 0){
                if(jQuery(inDate_dt2+i).val() != '' && jQuery(outDate_dt2+i).val() != ''){
                    if(comparisonDate(jQuery(inDate_dt2+i).val(),jQuery(outDate_dt2+i).val()) == '1'){
                        return 0;
                        break;
                    }
                }

                if (minDate == ''){
                    minDate = jQuery(inDate_dt2+i).val();
                }else{
                    if(comparisonDate(jQuery(inDate_dt2+i).val(),minDate) == '0'){
                        minDate = jQuery(inDate_dt2+i).val()
                    }
                }

                if (maxDate == ''){
                    maxDate = jQuery(outDate_dt2+i).val();
                }else{
                    if(comparisonDate(jQuery(outDate_dt2+i).val(),maxDate) == '1'){
                        maxDate = jQuery(outDate_dt2+i).val()
                    }
                }


                //地点校验
                for(var j = 0;j<jQuery("#indexnum0").val();j++){
                    if(jQuery(endLoan_dt1+j).length > 0){
                        if(jQuery(hotelArea_dt2+i).val() != jQuery(endLoan_dt1+j).val()){
                            return 3;
                            break;
                        }
                    }
                }

                //拼接数组 明细开始 结束日期
                dateArray += getDateStr(jQuery(inDate_dt2+i).val(),jQuery(outDate_dt2+i).val(),0)+",";

                if (inDate_dt2 != '' && travelStartDate_val != '' && comparisonDate(travelStartDate_val,minDate) != '0'){
                    return 2;
                    break;
                }

                if (outDate_dt2 != '' && travelEndDate_val != '' && comparisonDate(maxDate,travelEndDate_val) == '1'){
                    return 2;
                    break;
                }

            }
        }

        if(isRept(dateArray) == 1){
            return 1;
        }
    }

    //差旅时间重复校验
    function checkTravelTime(){

        var beginDateVal = jQuery(travelStartDate).val(); //开始日期
        var beginTimeVal = jQuery(travelStartTime).val(); //开始时间
        var endDateVal = jQuery(travelEndDate).val();//结束日期
        var endTimeVal = jQuery(travelEndTime).val();//结束时间
        var travellerVal = jQuery(traveller).val();//出差人
        var requestidVal = jQuery("#requestid").val();//流程编号

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

    //实时校验原出差流程状态
    function chekRequestStatus(){

        var travelOldVal = jQuery(travelOld).val();//原出差流程

        if(travelOldVal == ''){
            return '0';
        }

        $.ajax({
            async: false,
            type : "POST",
            url :'/appdevjsp/HQ/FNA/checkTime.jsp?requestid='+travelOldVal+'&type=1',
            dataType : "text",
            success : function(data){
                paramter= data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            }
        });

        return paramter;
    }















