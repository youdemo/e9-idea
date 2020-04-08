
<script>
/**
 * Created by fmj on 2018/9/7.
 */
var ctrip_requestid = jQuery("input[name='requestid']").val();
var ctrip_workflowid = jQuery("input[name='workflowid']").val();
var ctrip_nodeid = jQuery("input[name='nodeid']").val();
var ctrip_dpjd = "";     //订票节点
var ctrip_splx = "";     //0先审批后预定  1先预定后审批
var ctrip_cbzx1 = "";    //成本中心1
var ctrip_cbzx2 = "";    //成本中心2
var ctrip_cbzx3 = "";    //成本中心3
var ctrip_cbzx4 = "";    //成本中心4
var ctrip_cbzx5 = "";    //成本中心5
var ctrip_cbzx6 = "";    //成本中心6
var ctrip_JourneyReason = ""; //出行目的
var ctrip_Project = "";//项目号
var ctrip_zdyzd = "";    //自定义字段
var ctrip_showorder = "";   //是否显示订票按钮
var ctrip_sfxyxch = "";     //单点登录是否需要行程号，1不需要
var ctrip_showpush = "";    //是否需要显示推送到携程的按钮，1，显示
var ctrip_orderBtnName = "";    //订票按钮的名称
var ctrip_queryBtnName = "";    //查询订单详情按钮的名称

var ctrip_showssologin = "";//是否显示单点登录按钮
var ctrip_ssologin_btnname = "";//单点登录按钮名称
var ctrip_backurl = "";//携程目标位置

var ctripHotel = "field51538";//携程酒店 ckeck 框
var ctripHotelRow = "#ctripHotel";//携程酒店  明细表
var ticket = "field51539";//携程机票 ckeck 框
var ticketRow = "#ticket";//携程机票 明细表

function ctrip_order(obj,ssologintype) {
    var requestid = jQuery("input[name='requestid']").val();
    var workflowid = jQuery("input[name='workflowid']").val();
    var url = "/interface/ctrip/CtripEntrance.jsp?requestid=" + ctrip_requestid;
    var cbzx1 = ctrip_getCbzxValue(ctrip_cbzx1);
    var cbzx2 = ctrip_getCbzxValue(ctrip_cbzx2);
    var cbzx3 = ctrip_getCbzxValue(ctrip_cbzx3);
    var cbzx4 = ctrip_getCbzxValue(ctrip_cbzx4);
    var cbzx5 = ctrip_getCbzxValue(ctrip_cbzx5);
    var cbzx6 = ctrip_getCbzxValue(ctrip_cbzx6);
    var JourneyReason = ctrip_getCbzxValue(ctrip_JourneyReason);
    var Project = ctrip_getCbzxValue(ctrip_Project);
    var zdyzd = ctrip_getCbzxValue(ctrip_zdyzd);
    url += "&splx=" + ctrip_splx;//先审批后预定不需要成本中心

    url += "&dpjd=" + ctrip_dpjd + "&cost1=" + cbzx1 + "&cost2=" + cbzx2 + "&cost3=" + cbzx3+ "&cost4=" + cbzx4+ "&cost5=" + cbzx5+ "&cost6=" + cbzx6 + "&DefineFlag=" + zdyzd + "&sfxyxch=" + ctrip_sfxyxch;
    url += "&JourneyReason=" + JourneyReason+"&Project=" + Project;
    if(ssologintype == '1'){
        url += "&Backurl=" + ctrip_backurl;
    }

    jQuery.ajax({
        url: "/interface/ctrip/CtripServerOperation.jsp",
        data: {"workflowid": ctrip_workflowid, "cmd": "check"},
        type: "post",
        dataType: "json",
        success: function (data) {
            var issucess = data.issucess;
            if (issucess == "1") {  //验证通过
                window.open(url);
            } else {   //验证不通过
                window.top.Dialog.alert("该时间段不可订票");
            }
        }, error: function () {
        }
    });
}

function ctrip_getFieldValue(fieldhtmltype, type, values, fielddbtype) {
    var requestid = jQuery("input[name='requestid']").val();
    var workflowid = jQuery("input[name='workflowid']").val();
    var showcn = "";
    if (!fielddbtype) {
        fielddbtype = "";
    }
    jQuery.ajax({
        url: '/interface/ctrip/GetCtripCbzxValueAjax.jsp',
        data: {"fieldhtmltype": fieldhtmltype, "type": type, "values": values, fielddbtype: fielddbtype},
        type: 'post',
        async: false,
        dataType: 'json',
        success: function (data) {
            showcn = data.value;
        },
        error: function () {

        }
    });
    return showcn;
}

function ctrip_getCbzxValue(data) {
    var returnvalue = "";
    if (typeof(data) != "undefined") {
        var id = data.id;
        var fieldhtmltype = data.fieldhtmltype;
        var type = data.type;
        var fielddbtype = data.fielddbtype;
        if (fieldhtmltype == "1") {
            returnvalue = $("#field" + id).val();
        } else if (fieldhtmltype == "2") {
            //不会作为成本中心
        } else if (fieldhtmltype == "3") {
            returnvalue = ctrip_getFieldValue(fieldhtmltype, type, $("#field" + id).val(), fielddbtype);
        } else if (fieldhtmltype == "4") {
            //不会作为成本中心
        } else if (fieldhtmltype == "5") {
            returnvalue = ctrip_getFieldValue(fieldhtmltype, type, $("#field" + id).val());
        } else {

        }
    }
    return returnvalue;
}

function ctrip_showOrder() {
    //if (jQuery("#ticketIframe").length > 0) {
    var requestid = jQuery("input[name='requestid']").val();
    var url = "/interface/ctrip/CtripOrderList.jsp?requestid=" + requestid;
    //alert(url);
    var title = "订单信息";
    diag_vote = new window.top.Dialog();
    diag_vote.currentWindow = window;
    diag_vote.Width = 1024;
    diag_vote.Height = 600;
    diag_vote.maxiumnable = true;
    diag_vote.Modal = true;
    diag_vote.Title = title;
    diag_vote.URL = url;
    diag_vote.show();
    //    jQuery("#ticketIframe").height(0).attr("src", ticketIframe);
    //}
}

/**
 * 发送数据到携程
 * @param obj
 */
function ctrip_pushToCtrip(obj) {
    obj.disabled = "disabled";
    jQuery(obj).val("数据发送中，请稍候...");
    jQuery(obj).attr("title", "数据发送中，请稍候...");
    jQuery(obj).css({"max-width": "250px"});
    jQuery.ajax({
        url: "/interface/ctrip/CtripServerOperation.jsp",
        data: {"requestid": ctrip_requestid, "cmd": "send"},
        type: "post",
        dataType: "json",
        success: function (data) {
            var issucess = data.issucess;
            if (issucess == "1") {  //发送成功
                window.top.Dialog.alert("发送数据成功");
            } else {   //发送失败
                var msg = data.msg;
                if (msg != "") {
                    window.top.Dialog.alert(msg);
                } else {
                    window.top.Dialog.alert("发送数据失败");
                }
            }
            obj.disabled = false;
            jQuery(obj).val("推送携程");
            jQuery(obj).attr("title", "推送携程");
            jQuery(obj).css({"max-width": "120px"});
        }, error: function () {
            window.top.Dialog.alert("发送数据异常");
            obj.disabled = false;
            jQuery(obj).val("推送携程");
            jQuery(obj).attr("title", "推送携程");
            jQuery(obj).css({"max-width": "120px"});
        }
    });
}

jQuery(document).ready(function () {
    setTimeout('setctripHotelLayout()',500);
    setTimeout("setctripTicketLayout()",500);
    ctrip_requestid = jQuery("input[name='requestid']").val();
    ctrip_workflowid = jQuery("input[name='workflowid']").val();
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        WfForm.showConfirm("提交后将无法再进行酒店和机票的预订，请再次确认是否提交？", function(){
            callback();
        },function(){
            return;
        });
    })

    jQuery.ajax({
        url: '/interface/ctrip/GetCtripSetJsonAjax.jsp?workflowid=' + ctrip_workflowid + "&requestid=" + ctrip_requestid + "&nodeid=" + ctrip_nodeid,
        data: {},
        type: 'post',
        async: false,
        dataType: 'json',
        success: function (data) {
            ctrip_dpjd = data.dpjd;//订票节点
            ctrip_splx = data.splx;//0先审批后预定  1先预定后审批
            ctrip_cbzx1 = data.cbzx1;//成本中心1
            ctrip_cbzx2 = data.cbzx2;//成本中心2
            ctrip_cbzx3 = data.cbzx3;//成本中心3
            ctrip_cbzx4 = data.cbzx4;//成本中心4
            ctrip_cbzx5 = data.cbzx5;//成本中心5
            ctrip_cbzx6 = data.cbzx6;//成本中心6
            ctrip_JourneyReason = data.JourneyReason; //出行目的
            ctrip_Project = data.Project;//项目号
            ctrip_zdyzd = data.zdyzd;//自定义字段

            ctrip_showorder = data.showorder;//是否显示订票按钮
            ctrip_sfxyxch = data.sfxyxch;//单点登录是否需要行程号，1不需要

            ctrip_showpush = data.showpush;

            ctrip_orderBtnName = data.orderBtnName || "携程订票";
            ctrip_queryBtnName = data.queryBtnName || "查看订单";

            ctrip_showssologin = data.showssologin;
            ctrip_ssologin_btnname = data.ssologin_btnname||"我的携程";
            ctrip_backurl = data.backurl;
        },
        error: function () {

        }
    });

    if (ctrip_showorder == "1") {
        jQuery("#orderTicket").html("<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 100px;' class='dp_btn' title='" + ctrip_orderBtnName + "' value='" + ctrip_orderBtnName + "' type='button' onclick='ctrip_order(this)' _disabled='true' >");
        jQuery("#showTicketInfo").html("<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 100px;' class='dp_btn2' title='" + ctrip_queryBtnName + "' value='" + ctrip_queryBtnName + "' type='button' onclick='ctrip_showOrder(this)' _disabled='true' >");
    } else {
        jQuery("#orderTicket").html("");
        jQuery("#showTicketInfo").html("");
    }

    if (ctrip_showssologin == "1") {
        jQuery("#ssologin_btn").html("<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 300px;' class='e8_btn_top_first' title='" + ctrip_ssologin_btnname + "' value='" + ctrip_ssologin_btnname + "' type='button' onclick='ctrip_order(this,1)' _disabled='true' >");
    } else {
        jQuery("#ssologin_btn").html("");
    }

    //ctrip_showOrder();

    var btnHtml = "";
    if (ctrip_showpush == "1") {
        btnHtml = "<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 200px;' class='e8_btn_top_first' title='推送携程' value='推送携程' type='button' onclick='ctrip_pushToCtrip(this)' _disabled='true' >";
    }
    jQuery("#pushToCtrip").html(btnHtml);


    //已办中放入订票按钮
    try {
        var currentHref = window.location.href;
        if (currentHref.indexOf("/workflow/request/ViewRequestIframe.jsp?requestid=") != -1) {
            jQuery("#orderTicket_0").html("<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 300px;' class='e8_btn_top_first' title='" + ctrip_orderBtnName + "' value='" + ctrip_orderBtnName + "' type='button' onclick='ctrip_order(this)' _disabled='true' >");
        } else {
            jQuery("#orderTicket_0").html("");
        }
    } catch (e) {
    }
});
//机票明细表
function setctripTicketLayout(){
    var ticketVal = WfForm.getFieldValue(ticket);
    if(ticketVal == 1){
        jQuery(ticketRow).show();
    }else{
        jQuery(ticketRow).hide();
    }
}
//酒店明细表
function setctripHotelLayout(){
    var ctripHotelVal = WfForm.getFieldValue(ctripHotel);
    if(ctripHotelVal == 1){
        jQuery(ctripHotelRow).show();
    }else{
        jQuery(ctripHotelRow).hide();
    }
}
</script>































<style>
.dp_btn{
    position: fixed;
    bottom: 220px;
    right: 100px;
    z-index: 9999;
    -moz-box-shadow:1px 1px 18px #ABABAB; -webkit-box-shadow:1px 1px 5px #ABABAB; box-shadow:1px 1px 18px #ABABAB;
    width: 60px;
    height: 60px;
    text-align: center;
    color: #ffffff!important;
    font-size: 12px!important;
    font-family: Microsoft YaHei!important;
    line-height:30px;
    border-radius: 30px;
    float:left;
    background-color: #03a996;
    cursor:pointer;
}
.dp_btn:hover{
    border: 1px solid #03a996;
    background-color: #03a996;
}
.dp_btn2:hover{
    border: 1px solid #e8961a;
    background-color: #e8961a;
}
.dp_btn2{
    cursor:pointer;
    position: fixed;
    bottom: 150px;
    right: 100px;
    z-index: 9999;
    -moz-box-shadow:1px 1px 18px #ABABAB; -webkit-box-shadow:1px 1px 5px #ABABAB; box-shadow:1px 1px 18px #ABABAB;
    width: 60px;
    height: 60px;
    text-align: center;
    color: #ffffff!important;
    font-size: 12px!important;
    font-family: Microsoft YaHei!important;
    line-height:60px;
    border-radius: 30px;
    margin-left: -20px;
    float:left;
    background-color: #ef9b5f;


}
</style>

