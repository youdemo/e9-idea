var cgjelx = WfForm.convertFieldNameToId("cgjelx");//采购金额类型
var bcfkje = WfForm.convertFieldNameToId("bcfkje");//本次付款金额
var htwfkje = WfForm.convertFieldNameToId("htwfkje");//合同未付款金额
var jjcgsqwfkje = WfForm.convertFieldNameToId("jjcgsqwfkje");//间接采购未付款金额
var fplx = WfForm.convertFieldNameToId("fplx");	// 0  1 2
var fpjezh = WfForm.convertFieldNameToId("fpjezh");	//
var hsjezh = WfForm.convertFieldNameToId("hsjezh");	//
var jjcgsq = WfForm.convertFieldNameToId("jjcgsq");	//
var jjcgsqzs = WfForm.convertFieldNameToId("jjcgsqzs");	// 间接采购申请总额
var cghtmc = WfForm.convertFieldNameToId("cghtmc");	// 采购合同名称
var sqlx = WfForm.convertFieldNameToId("sqlx");	// 申请类型
jQuery(document).ready(function(){
    WfForm.registerCheckEvent(WfForm.OPER_SAVE+","+WfForm.OPER_SUBMIT, function(callback){
        var cgjelx_val =  WfForm.getFieldValue(cgjelx);
        var bcfkje_val =  WfForm.getFieldValue(bcfkje);
        var htwfkje_val = WfForm.getFieldValue(htwfkje);
        var jjcgsqwfkje_val = WfForm.getFieldValue(jjcgsqwfkje);
        var jjcgsq_val = WfForm.getFieldValue(jjcgsq);
        var sqlx_val = WfForm.getFieldValue(sqlx);
        if(bcfkje_val == ""){
            bcfkje_val = "0";
        }
        if(htwfkje_val == ""){
            htwfkje_val = "0";
        }
        if(jjcgsqwfkje_val == ""){
            jjcgsqwfkje_val = "0";
        }
        var v_fplx = WfForm.getFieldValue(fplx);
        if(jjcgsq_val.length>0 && jjcgsq_val != ''){

            var resu = checkRep();
            if(!resu){
                WfForm.showMessage("明细中存在  科目和原科目信息不一样的地方，请确认!");
                return;
            }
        }
        if(v_fplx == '0' || v_fplx == '1'){
            var num = checkFPNUM();
            if(Number(num)< 1 ){
                WfForm.showMessage("请填写发票信息!");
                return;
            }
            var v_fpjezh = WfForm.getFieldValue(fpjezh);
            var v_hsjezh = WfForm.getFieldValue(hsjezh);
            if(v_fpjezh == ""){
                v_fpjezh = "0";
            }
            if(v_hsjezh == ""){
                v_hsjezh = "0";
            }
            if(Number(v_fpjezh)!= Number(v_hsjezh)){
                WfForm.showMessage("费用明细总金额不等于发票明细总金额，请确认!");
                return;
            }
        }

        if(sqlx_val != "0"){
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
            }else if(cgjelx_val ==""){
                if(Number(bcfkje_val)>Number(jjcgsqwfkje_val)){
                    WfForm.showMessage("本次付款金额不能大于采购未付款金额，请检查", 2, 3);
                    return;
                }
            }
        }
        callback();
    })
    WfForm.bindFieldChangeEvent(jjcgsqzs, function(obj,id,value){
        if(value == '' || value.length < 1){
            value = '0';
        }
        if(Number(value)>=10000){
            WfForm.changeFieldAttr(cghtmc, 3);
        }else{
            WfForm.changeFieldAttr(cghtmc, 2);
        }
    });
})
function checkFPNUM(){
    var inde = jQuery("#indexnum1").val();
    var a = 0
    var ze = "#field7133_";
    for(var i = 0;i<inde;i++){
        if(jQuery(ze+i).length>0){
            a++;
        }
    }
    return a;
}
function checkRep(){
    var res = true;
    var inde = jQuery("#indexnum0").val();
    var km = WfForm.convertFieldNameToId("km", "detail_1"); // 正式机的id
    var ykmxx = WfForm.convertFieldNameToId("ykmxx", "detail_1");// 正式机的id

    for(var i = 0;i<inde;i++){
        if(jQuery("#"+km+"_"+i).length>0){
            var km1_v = WfForm.getFieldValue(km+"_"+i);
            var km2_v = WfForm.getFieldValue(ykmxx+"_"+i);
            if(km1_v != km2_v){
                res = false;
                break;
            }
        }
    }

    return res;
}