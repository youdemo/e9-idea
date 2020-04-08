
var  absence_top="#field398721_" ;//事假上限
var  work_code = "#field47078_";//人员工号
var message="#field50199"; //消息
var yjqjksrq = "#field47080_";//请假开始日期
var yjqjjsrq = "#field47082_";//请假结束日期
var yjqjkssj = "#field47081_";//请假开始时间
var yjqjjssj = "#field47083_";//结束开始时间
var 	hours= "#field47084_";//休假小时数
var  absence_type= "#field48948_";// 请假类型
var remain_hours="#field47086_" //额度
var fjsc = "field47076";//有效证明文件
var fjsc1 = "field_47076";//有效证明文件
var hrsp = "#field48622";//是否考勤审批
var director_sp="#field48621" ;// 是否上级审批
var   place="#field50212_";//出差地点
var   error_message="#field53007_" ;
var  on_hours="#field47087_" ;//在途时数
var  al_hours="#field47085_" ;//已修时数
var cjcs_dt1="#field122721_";//产检次数
var syzsh_dt1="#field175738_";//剩余年假/调休假/额外调休假总时数
var kgcs="#field228222_" ;  //旷工次数
var allkgcs="#field228223_"; //累计
var swork_code="#field47071" ;//申请人工号
var sfsqdsfllc="field590721_";//是否申请大事福利流程
var sfsqdsfllc1="field590721";//是否申请大事福利流程
var sfsqdsfllc_wb="field590722_";//是否申请大事福利流程文本


var btid="346230";
var yc="346231";
var db="346232";
var btid2="#field346233";
var yc2="#field346234";
var db2="#field346235";
var name1="#field47077_";
var flag;
alert("A");

jQuery("[name=addbutton0]").live("click", function () {
    var indexnum = jQuery("#indexnum0").val();
    //     gethistoryinfo(indexnum-1);
    bindchange(indexnum-1);
});

jQuery(document).ready(function(){
    alert("0");
    jQuery("[name=copybutton0]").css('display','none');
    var message_val= jQuery(message).val();
    if ( message_val  !="" ||  message_val  !="null" ) {
        $("#k1").show();
    }

    else {
        $("#k1").hide();
    }

    var indexnum0 = jQuery("#indexnum0").val();
    for (var index1= 0; index1 < indexnum0; index1++) {
        // bindchange(index1);
        if (jQuery(absence_type+ index1).length > 0) {
            //	jQuery(yjsjxss+index1).attr("readonly",true);
            bindchange(index1);

        }

    }
    checkdata();
    checkdata1();
    setSFCF(indexnum0);
    checkdata33();
//是否触发大事福利流程文本
    /*jQuery("select[id^='field590721_']").bindPropertyChange(function(obj){alert("s");
        setSFCF(indexnum0);
     });*/
    WfForm.bindDetailFieldChangeEvent("field590721",function(id,rowIndex,value){
        setSFCF(indexnum0);
    });
    checkCustomize = function(){
        var fieldAttr= WfForm.getFieldCurViewAttr(fjsc);alert(fieldAttr);
        if(fieldAttr == '3'){
            var fjsc_val= jQuery("#"+fjsc).val();
            if(fjsc_val =="" || fjsc_val =="null"){
                jQuery("#"+fjsc).val("");
                jQuery(".progressBarStatus").each(function(){
                    if($(this).html().indexOf("Pending")>=0){
                        jQuery("#"+fjsc).val("null");
                    }
                })
            }
        }
        var indexnum1 = jQuery("#indexnum0").val();
        for (var index2= 0; index2 < indexnum1; index2++) {
            if (jQuery(work_code + index2).length > 0) {
                var work_code_val = jQuery(work_code+index2).val();
                var 	hours_val = jQuery(hours+index2).val();
                var   absence_type_val= jQuery(absence_type+index2).val();
                var  remain_hours_var= jQuery(remain_hours+index2).val();
                var   on_hours_val= jQuery( on_hours+index2).val();
                var   al_hours_val= jQuery( al_hours+index2).val();
                var place_val= jQuery( place+index2).val();
                var cjcs_dt1_val= jQuery( cjcs_dt1+index2).val();
                var syzsh_dt1_val= jQuery( syzsh_dt1+index2).val();
                var result = true;
                var  error_message_val= jQuery(error_message+index2).val();
                var kgcs_val= jQuery( kgcs+index2).val();
                var    allkgcs_val= jQuery( allkgcs+index2).val();
                var    yjqjksrq_val= jQuery(yjqjksrq+index2).val();
                var    yjqjjsrq_val= jQuery(yjqjjsrq+index2).val();
                var     yjqjjssj_val= jQuery( yjqjjssj+index2).val();
                var  swork_code_val =jQuery(swork_code).val();    //2019-06-13
                var    absence_top_val=jQuery(absence_top+index2).val();

                if     (error_message_val !="OK")
                {
                    if(absence_type_val =='03' && error_message_val.indexOf("不可休事假") != -1){
                        var sytotal=checkSYZSS(indexnum1,work_code_val);
                        if(Number(syzsh_dt1_val)!=Number(sytotal)){
                            window.top.Dialog.alert("年假、调休假、额外调休假仍有剩余，烦请休完后提交事假，可于同一支流程中提交，谢谢！");
                            result = false;
                            break;
                        }
                    }else{
                        window.top.Dialog.alert("信息未校验成功，不可提交！");
                        result = false;
                        break;
                    }
                }


                if  (absence_type_val =="08" &&  place_val=="")
                {
                    window.top.Dialog.alert("集团内出差，出差地点不可为空！");
                    result = false;
                    break;
                }


                if(absence_type_val== "11" || absence_type_val== "10" || absence_type_val== "22"  ){                          //2019-06-13

                    if(swork_code_val !=work_code_val ){
                        window.top.Dialog.alert("产假、多胞胎假，产检假申请人需和明细一致，且不可申请多笔！");
                        result = false;
                        break;
                    }

                }


                if  (absence_type_val =="011" && yjqjksrq_val.substr(5,2)!=yjqjjsrq_val.substr(5,2)    && yjqjjssj_val.substr(0,2)!='05'   )
                {
                    window.top.Dialog.alert("调休假（当月）不可跨月提交！");

                    result = false;
                    break;
                }


                if(absence_type_val== "011"){

                    if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var)){
                        window.top.Dialog.alert("当前请假小时数大于剩余调休假（当月）");
                        result = false;
                        break;

                    }
                }


                if (Number(hours_val )<0.5) {
                    window.top.Dialog.alert("实际请假小时数要大于等于0.5！");
                    result = false;
                    break;
                }
                if(absence_type_val== "0" || absence_type_val== "01" || absence_type_val== "02"
                    || absence_type_val== "03" || absence_type_val== "09"
                    || absence_type_val== "12" || absence_type_val== "15" || absence_type_val== "10"|| absence_type_val=="14"  || absence_type_val=="23" ||  absence_type_val=="18" )
                {


                    if(absence_type_val== "18"){

                        if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var))  {
                            window.top.Dialog.alert("当前请假小时数大于探亲假额度");
                            result = false;
                            break;
                        }

                    }


                    if(absence_type_val== "14"){

                        if   ( Number(hours_val )<8  &&  Number(remain_hours_var) >=8 ){

                            window.top.Dialog.alert("哺乳假B最小额度为8小时");
                            result = false;
                            break;
                        }

                        else if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var)){
                            window.top.Dialog.alert("当前请假小时数大于哺乳假B额度");
                            result = false;
                            break;
                        }
                    }


                    if(absence_type_val== "10"){

                        if   ( Number(hours_val ) >8  ){

                            window.top.Dialog.alert("产检假最大额度为8小时");
                            result = false;
                            break;
                        }
                        var cjcs = getCJCS(indexnum1,absence_type_val,work_code_val);
                        if((Number(cjcs)+Number(cjcs_dt1_val))>18){
                            window.top.Dialog.alert("员工"+work_code_val+"产检假次数已经超出18次，流程不可提交！");
                            result = false;
                            break;
                        }
                    }
                    if(absence_type_val== "01"){
                        if(Number(hours_val )+Number(on_hours_val)> Number(remain_hours_var) ){
                            window.top.Dialog.alert("当前请假小时数大于剩余调休假");
                            result = false;
                            break;
                        }else if(Number(hours_val )<0.5){
                            window.top.Dialog.alert("当前调休假请假小时数小于0.5小时");
                            result = false;
                            break;
                        }
                    }
                    if(absence_type_val== "0"){

                        if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var)){
                            window.top.Dialog.alert("当前请假小时数大于剩余年假");
                            result = false;
                            break;
                        }else if(Number(hours_val )<0.5){
                            window.top.Dialog.alert("当前请假小时数小于0.5小时");
                            result = false;
                            break;
                        }

                    }

                    if(absence_type_val== "23"){

                        if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var)){
                            window.top.Dialog.alert("当前请假小时数大于剩余福利年假");
                            result = false;
                            break;
                        }else if(Number(hours_val)<0.5){
                            window.top.Dialog.alert("当前请假小时数小于0.5小时");
                            result = false;
                            break;
                        }
                    }


                    if(absence_type_val== "06")
                    {

                        var kgcs2  = getKGCS(indexnum1,(index2+1),work_code_val,absence_type_val);
                        var total_kgcs = Number(kgcs2) + Number(allkgcs_val)  + Number(kgcs_val);
                        if(Number(kgcs_val)>3){
                            window.top.Dialog.alert("连续旷工次数不可大于4次");
                            result = false;
                            break;
                        }

                        if(Number(total_kgcs )>5){
                            window.top.Dialog.alert("累计旷工次数年度不可大于5次");
                            result = false;
                            break;
                        }
                    }


                    if(absence_type_val== "02"){

                        if(Number(hours_val )+Number(on_hours_val)>Number(remain_hours_var)){
                            window.top.Dialog.alert("当前请假小时数大于剩余额外调休假");
                            result = false;
                            break;
                        }else if(Number(hours_val )<0.5){
                            window.top.Dialog.alert("当前请假小时数小于0.5小时");
                            result = false;
                            break;
                        }
                    }


                    // 事假
                    if (absence_type_val== "03") {
                        var sjss_val = getSJSS(indexnum1,(index2+1),work_code_val,absence_type_val);
                        var total_hours = Number(sjss_val) + Number(hours_val)+Number(al_hours_val)+Number(on_hours_val)
                        if (Number(hours_val) > 24) {
                            //alert("当前请假小时数超出规定！");
                            jQuery(director_sp).val("是");
                            result = true;

                        }else{
                            jQuery(director_sp).val("否");
                            result = true;
                        }
                        if (Number(total_hours) > Number(absence_top_val) ) {
                            window.top.Dialog.alert("工号"+work_code_val+"事假请假小时数已超出15天，流程无法提交！");
                            result = false;
                            break;
                        }
                        if(Number(hours_val)<0.5){
                            window.top.Dialog.alert("当前事假请假小时数小于0.5小时")			;
                            result = false;
                            break;
                        }
                    }
                }

            }
        }
        return result;
    }

})
//是否触发大事福利流程
function setSFCF(indexnum){
    for(var j=0; j < indexnum;j++){
        var sfsqdsfllc_v=  "#"+sfsqdsfllc+j;
        if(jQuery(sfsqdsfllc_v).length>0){
            var sfsqdsfllc_val = WfForm.getFieldValue(sfsqdsfllc_v);
            //var sfsqdsfllc_val= jQuery(sfsqdsfllc_v).val();
            if(sfsqdsfllc_val=='0'){
                //jQuery("#"+sfsqdsfllc_wb+j).val('0');
                WfForm.changeFieldValue(sfsqdsfllc_wb+j, {value:"0"});
            }else{
                //jQuery("#"+sfsqdsfllc_wb+j).val('1');
                WfForm.changeFieldValue(sfsqdsfllc_wb+j, {value:"1"});
            }
        }
    }
}
function setFiled(fieldid,val){
    WfForm.controlSelectOption(fieldid, val);
}
function checkdata3(){
    var indexnum1 = jQuery("#indexnum0").val();
    for (var index2= 0; index2 < indexnum1; index2++) {
        if (jQuery(work_code + index2).length > 0) {
            var absence_type_val = jQuery(absence_type+index2).val();
            //判断是否触发大事福利流程
            if(absence_type_val== "11" || absence_type_val== "12" || absence_type_val== "09"  || absence_type_val== "15"){
                WfForm.changeFieldValue(sfsqdsfllc+index2, {value:"0"});
                //jQuery("#"+sfsqdsfllc+index2).val("0");
            }else{
                setFiled(sfsqdsfllc+index2,'1');
                // jQuery("#"+sfsqdsfllc+index2).val("1");
                WfForm.changeFieldValue(sfsqdsfllc+index2, {value:"1"});
            }
        }
    }

}
function checkdata33(){
    var indexnum1 = jQuery("#indexnum0").val();
    for (var index2= 0; index2 < indexnum1; index2++) {
        if (jQuery(work_code + index2).length > 0) {
            var absence_type_val = jQuery(absence_type+index2).val();
            //判断是否触发大事福利流程
            if(absence_type_val== "11" || absence_type_val== "12" || absence_type_val== "09"  || absence_type_val== "15"){
            }else{
                setFiled(sfsqdsfllc+index2,'1');
                //jQuery("#"+sfsqdsfllc+index2).val("1");
                WfForm.changeFieldValue(sfsqdsfllc+index2, {value:"1"});
            }
        }
    }

}
function bindchange(index){
    jQuery(hours+index).bindPropertyChange(function(){

        checkdata();
    });
    jQuery(absence_type+index).bindPropertyChange(function(){
        checkdata3();
    });
    jQuery(absence_type+index).bindPropertyChange(function(){
        checkdata();
    });
    jQuery(hours+index).bindPropertyChange(function(){
        checkdata1();
    });

    jQuery(absence_type+index).bindPropertyChange(function(){
        checkdata1();
    });
}

function checkdata()
{
    var indexnum1 = jQuery("#indexnum0").val();
    for (var index2= 0; index2 < indexnum1; index2++) {
        if (jQuery(work_code + index2).length > 0) {
            var absence_type_val = jQuery(absence_type+index2).val();//病假 L_004
            var hours_val = jQuery(hours+index2).val();
            //var needcheck = document.all("needcheck");



            //2019-06-21
            var  swork_code_val =jQuery(swork_code).val();
            var work_code_val = jQuery(work_code+index2).val();
            if (absence_type_val =='11' ||absence_type_val =='10' || absence_type_val =='22'){
                if  ( swork_code_val  !=work_code_val ) {
                    flag='A';
                    if (jQuery(name1+ index2).length > 0) {
                        jQuery(name1+index2+'span').html("");
                        jQuery(name1+index2).val("");
                        jQuery(absence_type+index2+'span').html("");
                        jQuery(absence_type+index2).val("");
                        alert("产假、产检假、多胞胎假期 申请人需和明细保持一致")
                    }
                }
                else {
                    flag='B';
                }


            }
            //2019-06-21
            if  (absence_type_val  != "08")
            {

                jQuery(place+index2).hide();

            }
            else {
                jQuery(place+index2).show();

            }

            if((absence_type_val == "04" && Number(hours_val ) >= 8) || absence_type_val == "09" || absence_type_val == "17" || absence_type_val == "12" || absence_type_val == "10" || absence_type_val == "11" || absence_type_val == "20" || absence_type_val == "21" || absence_type_val == "22"  || absence_type_val == "24"   )
            {
                if(jQuery("#"+fjsc).length>0)
                {
                    var fjsc_val= jQuery("#"+fjsc).val();
                    WfForm.changeFieldAttr(fjsc,3);
                    return;
                }
            }
            else{
                WfForm.changeFieldAttr(fjsc,2);
            }
        }
    }
}

function checkdata1()
{
    var indexnum1 = jQuery("#indexnum0").val();
    for (var index2= 0; index2 < indexnum1; index2++) {
        if (jQuery(work_code+ index2).length > 0) {
            var absence_type_val = jQuery(absence_type+index2).val();
            var hours_val = jQuery(hours+index2).val();
            //var needcheck = document.all("needcheck");

            if(  (absence_type_val == "04" && Number(hours_val ) >= 8) || absence_type_val == "09" || absence_type_val == "17" ||  absence_type_val == "12" || absence_type_val == "10" || absence_type_val == "11" ||  absence_type_val == "16" || absence_type_val == "20" || absence_type_val == "21" || absence_type_val == "22"  || absence_type_val == "24"  )
            {
                jQuery(hrsp).val("是");

                index2=indexnum1;
            }
            else{
                jQuery(hrsp).val("否");

            }


            if  (absence_type_val == "04" && Number(hours_val ) > 24)
            {
                jQuery(director_sp).val("是");

                index2=indexnum1;
            }
            else{
                jQuery(director_sp).val("否");

            }


            var btid2_val = jQuery(btid2).val();
            var yc2_val = jQuery(yc2).val();
            var db2_val = jQuery(db2).val();

            if  (absence_type_val == "11" &&  btid2_val ==""  && flag=="B")
            {
                removecheck(yc);
                removecheck(db);
                $("#p1").show();
                $("#r1").hide();
                var btid_val = jQuery("#field"+btid).val();
                var btid_check = "field" + btid;
                if(btid_val==""  || btid_val=="null")
                {
                    WfForm.changeFieldAttr(btid_check ,3);
                    index2=indexnum1;
                }

                else if (absence_type_val == "11" &&  btid2_val !=""   && flag=="B" )
                {
                    removecheck(yc);
                    removecheck(db);
                    $("#p1").hide();
                    $("#s1").show();
                    index2=indexnum1;
                }
                else
                {
                    $("#p1").hide();
                    $("#s1").hide();
                    if(jQuery("#field"+btid+"span").length>0)
                    {
                        jQuery("#field"+btid+"span").html("");
                        jQuery("#field"+btid).val("");
                    }
                }


                if  (absence_type_val == "10" && yc2_val ==""   && flag=="B"  )
                {
                    removecheck(btid);
                    removecheck(db);
                    $("#p2").show();
                    $("#s2").hide();
                    var  yc_val = jQuery( "#field" + yc).val();
                    var yc_check = "field" + yc;
                    if(yc_val==""  || yc_val=="null")
                    {
                        jQuery("#field"+yc+"span").html("<img align='absmiddle' src='/images/BacoError_wev8.gif'>");
                    }
                    WfForm.changeFieldAttr(yc_check ,3);
                    index2=indexnum1;
                }

                else if (absence_type_val == "10" && yc2_val !=""   && flag=="B" )
                {
                    removecheck(btid);
                    removecheck(db);
                    $("#p2").hide();
                    $("#s2").show();
                    index2=indexnum1;

                }
                else{
                    $("#p2").hide();
                    $("#s2").hide();
                    if(jQuery("#field"+yc+"span").length>0){
                        jQuery("#field"+yc+"span").html("");
                        jQuery("#field"+yc).val("");
                    }
                }

                if  (absence_type_val == "22" && db2_val ==""  && flag=="B" )
                {
                    removecheck(btid);
                    removecheck(yc);
                    $("#p3").show();
                    $("#s3").hide();
                    var db_val = jQuery("#field" +db).val();
                    var db_check = "field" + db;
                    if(db_val==""  || db_val=="null"){
                        jQuery("#field"+db+"span").html("<img align='absmiddle' src='/images/BacoError_wev8.gif'>");
                    }
                    WfForm.changeFieldAttr(db_check,3);
                    index2=indexnum1;
                }

                else  if  (absence_type_val == "22" && db2_val !=""  && flag=="B")
                {           removecheck(btid);
                    removecheck(yc);
                    $("#p3").hide();
                    $("#s3").show();
                    index2=indexnum1;
                }
                else{
                    $("#p3").hide();
                    $("#p3").hide();
                    if(jQuery("#field"+db+"span").length>0){
                        jQuery("#field"+db+"span").html("");
                        jQuery("#field"+db).val("");
                    }
                }

            }
        }
    }

    function getSJSS(total,index,qjr,absence_type_val){
        var res ='0';
        for (var i=index;i<total;i++){
            var qjr_id = work_code +i;
            var hours_id = hours +i;
            var absence_type_id = absence_type +i;
            if(jQuery(qjr_id).length>0){
                var qjr_val = jQuery(qjr_id).val();
                var absence_type_id_val =  jQuery(absence_type_id ).val();
                if(qjr_val == qjr && absence_type_id_val == absence_type_val){
                    var hour_val =  jQuery(hours_id).val();
                    res = Number(res) +Number(hour_val);
                }
            }
        }
        return res;
    }


//
    function getKGCS(total,index,qjr,absence_type_val){
        var res ='0';
        for (var i=index;i<total;i++){
            var qjr_id = work_code +i;
            var kgcs_id = kgcs+i;
            var absence_type_id = absence_type +i;
            if(jQuery(qjr_id).length>0){
                var qjr_val = jQuery(qjr_id).val();
                var absence_type_id_val =  jQuery(absence_type_id ).val();
                if(qjr_val == qjr && absence_type_id_val == absence_type_val){
                    var kgcs_val =  jQuery(kgcs_id).val();
                    res =Number(res)+Number(kgcs_val);
                }
            }
        }
        return res;
    }
//


    function getCJCS(total,absence_type_val,work_code_val){
        var res =0;
        var judge = "";
        for(var i=0;i<total;i++){
            var sqrq_id1= yjqjksrq+i;
            var absence_type_id1= absence_type+i;
            var work_code_id1= work_code +i;
            if(jQuery(sqrq_id1).length<=0 || jQuery(absence_type_id1).length<=0 || jQuery(work_code_id1).length<=0){
                continue;
            }
            var absence_type_id1_val = jQuery(absence_type_id1).val();
            var work_code_id1_val = jQuery(work_code_id1).val();
            var sqrq_id1_val = jQuery(sqrq_id1).val();
            if(judge.indexOf(sqrq_id1_val) == -1 && absence_type_id1_val == absence_type_val && work_code_val==work_code_id1_val ){
                res = res +1;
                judge = judge +sqrq_id1_val+",";
            }
        }
        return res;
    }

    function checkSYZSS(total,qjr){
        var res =0;
        for (var i=0;i<total;i++){
            var qjr_id = work_code +i;
            var hours_id = hours +i;
            var absence_type_id = absence_type +i;
            if(jQuery(qjr_id).length>0){
                var qjr_val = jQuery(qjr_id).val();
                var absence_type_id_val =  jQuery(absence_type_id ).val();
                if(qjr_val == qjr && (absence_type_id_val =='0' || absence_type_id_val =='01' || absence_type_id_val =='02') ){
                    var hour_val =  jQuery(hours_id).val();
                    res = Number(res) +Number(hour_val);
                }
            }
        }
        return res;
    }

    function removecheck(btid){

        var btid_check="field"+btid;

        WfForm.changeFieldAttr(btid_check,2);

    }










