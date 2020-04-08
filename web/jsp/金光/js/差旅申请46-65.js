
var ctripHotel = "field51538";//携程酒店 ckeck 框
var ctripHotelRow = "#ctripHotel";//携程酒店  明细表
var ticket = "field51539";//携程机票 ckeck 框
var ticketRow = "#ticket";//携程机票 明细表
var projectType = "field51458";//项目类型
var AProjectRow = ".AProject";//a+ 项目行
var projectRow = ".project";//项目明细行


jQuery(document).ready(function(){
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        WfForm.showConfirm("提交后将无法再修改差旅信息，请再次确认是否提交？", function(){
            callback();
        },function(){
            return;
        });
    })
    //携程预订酒店 ckeck框
    WfForm.bindFieldChangeEvent(ctripHotel, function(obj,id,value){
        setctripHotelLayout();
        WfForm.delDetailRow("detail_2","all");
    });

    //携程预订机票 ckeck框
    WfForm.bindFieldChangeEvent(ticket, function(obj,id,value){
        setctripTicketLayout();
        WfForm.delDetailRow("detail_3","all");
    });


    setTimeout('setctripHotelLayout()',500);
    setTimeout("setctripTicketLayout()",500);

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




































































