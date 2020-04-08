var cgjelx = WfForm.convertFieldNameToId("cgjelx");//采购金额类型
var bcfkje = WfForm.convertFieldNameToId("bcfkje");//本次付款金额
var htwfkje = WfForm.convertFieldNameToId("htwfkje");//合同未付款金额
var jjcgsqwfkje = WfForm.convertFieldNameToId("jjcgsqwfkje");//间接采购未付款金额
jQuery(document).ready(function(){
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        var cgjelx_val =  WfForm.getFieldValue(cgjelx);
        var bcfkje_val =  WfForm.getFieldValue(bcfkje);
        var htwfkje_val = WfForm.getFieldValue(htwfkje);
        var jjcgsqwfkje_val = WfForm.getFieldValue(htwfkje);
        if(bcfkje_val == ""){
            bcfkje_val = "0";
        }
        if(htwfkje_val == ""){
            htwfkje_val = "0";
        }
        if(jjcgsqwfkje_val == ""){
            jjcgsqwfkje_val = "0";
        }
        if(cgjelx_val == '0'){
            if(Number(bcfkje_val)>Number(htwfkje_val)){
                WfForm.showMessage("本次付款金额不能大于合同未付款金额，请检查", 2, 3);
                return;
            }
        }else if(cgjelx_val == '1'){
            if(Number(bcfkje_val)>Number(jjcgsqwfkje_val)){
                WfForm.showMessage("本次付款金额不能大于采购未付款金额，请检查", 2, 3);
                return;
            }
        }
        callback();
    })
})



