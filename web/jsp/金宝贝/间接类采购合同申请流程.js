var htje = WfForm.convertFieldNameToId("htje");//合同金额
var cgsqje = WfForm.convertFieldNameToId("cgsqje");//采购申请金额
jQuery(document).ready(function(){
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        var htje_val =  WfForm.getFieldValue(htje);
        var cgsqje_val = WfForm.getFieldValue(cgsqje);
        if(htje_val == ""){
            htje_val = "0";
        }
        if(cgsqje_val == ""){
            cgsqje_val = "0";
        }
        if(Number(htje_val)>Number(cgsqje_val)){
            WfForm.showMessage("合同金额不能大于采购申请金额，请检查", 2, 3);
            return;
        }
        callback();
    })
})