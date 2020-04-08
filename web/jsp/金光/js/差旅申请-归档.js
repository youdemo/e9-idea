
<script type="text/javascript">
var ctripHotel = "field51538";//携程酒店 ckeck 框
var ctripHotelRow = "#ctripHotel";//携程酒店  明细表
var ticket = "field51539";//携程机票 ckeck 框
var ticketRow = "#ticket";//携程机票 明细表

jQuery(document).ready(function(){
    setTimeout('setctripHotelLayout()',500);
    setTimeout("setctripTicketLayout()",500);
    setTimeout("showhidexc()",500);
    jQuery("#openbx").html("<input style='overflow: hidden; white-space: nowrap; text-overflow: ellipsis; max-width: 100px;' class='dp_btn' title='创建报销' value='创建报销' type='button' onclick='openbxlc()' _disabled='true' >");
});

function openbxlc(){
    window.open("/workflow/request/CreateRequestForward.jsp?workflowid=5606","_blank")
}
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
//携程
function showhidexc(){
    var count_dt4 = WfForm.getDetailRowCount("detail_4");
    var count_dt5 = WfForm.getDetailRowCount("detail_5");
    if(count_dt4 == 0 && count_dt5 == 0){
        jQuery("#xcyc_1").hide();
        jQuery("#xcyc_2").hide();
    }else{
        jQuery("#xcyc_1").show();
        jQuery("#xcyc_2").show();
    }
}

</script>
<style>
.dp_btn{
    position: fixed;
    bottom: 300px;
    right: 80px;
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
    background-color: #0079de;
    cursor:pointer;
}
.dp_btn:hover{
    border: 1px solid #0079de;
    background-color: #0079de;
}
</style>

















