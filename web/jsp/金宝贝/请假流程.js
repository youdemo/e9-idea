var kqnjts = "field6692";//可请年假天数
var kqdxbjts = "field6693";//可请带薪假天数
var qjts = "field6690";//请假天数
var qjlx = "field6717";//请假类型
jQuery(document).ready(function(){
    WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
        var flag = "0";
        var kqnjts_val = WfForm.getFieldValue(kqnjts);
        var kqdxbjts_val = WfForm.getFieldValue(kqdxbjts);
        var qjts_val = WfForm.getFieldValue(qjts);
        var qjlx_val = WfForm.getFieldValue(qjlx);
        if(kqnjts_val == ""){
            kqnjts_val == "0";
        }
        if(qjts_val ==""){
            qjts_val = "0";
        }
        if(kqdxbjts_val == ""){
            kqdxbjts_val = "0";
        }
        if(qjlx_val == "6" ){
            if(Number(kqnjts_val)>Number("0")){
                alert("当前可请年假数不为0，请先使用年假");
                flag = "1";
            }
        }
        if(qjlx_val == 2 && flag == "0"){
            if(Number(qjts_val)>Number(kqnjts_val)){
                alert("请假天数不能大于当前可请假天数,请检查");
                flag = "1";
            }
        }else  if(qjlx_val == 3 && flag == "0"){
            if(Number(qjts_val)>Number(kqdxbjts_val)){
                alert("请假天数不能大于当前可请假天数,请检查");
                flag = "1";
            }
        }
        if(flag == "0"){
            callback();
        }
    });
})