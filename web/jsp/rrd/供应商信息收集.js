var zzs = ModeForm.convertFieldNameToId("zzs");	//制造商
var gysmc = ModeForm.convertFieldNameToId("gysmc");	//供应商名称
var tyshxydm = ModeForm.convertFieldNameToId("tyshxydm");	//统一社会信用代码
var gysmccfbs = ModeForm.convertFieldNameToId("gysmccfbs");	//供应商名称重复标识
var tyshxydmzf = ModeForm.convertFieldNameToId("tyshxydmzf");	//统一社会信用代码重复

//生产和业务能力（制造商）
var ygzsr = ModeForm.convertFieldNameToId("ygzsr");	//员工总数（人）
var bgcsmjm2 = ModeForm.convertFieldNameToId("bgcsmjm2");	//办公场所面积（M2）
var sccsmjm2 = ModeForm.convertFieldNameToId("sccsmjm2");	//生产场所面积（M2）
var glryzsr = ModeForm.convertFieldNameToId("glryzsr");	//管理人员总数（人）
var ckmjm2 = ModeForm.convertFieldNameToId("ckmjm2");	//仓库面积（M2）
var sbhcpnlms = ModeForm.convertFieldNameToId("sbhcpnlms");	//设备和产品能力描述
jQuery(document).ready(function() {

    //绑定制造商
    ModeForm.bindFieldChangeEvent(zzs, function (obj, id, value) {
        showHideZZS();
    })
    //名称重复判断
    ModeForm.bindFieldChangeEvent(gysmc, function (obj, id, value) {
        if(value != "") {
            checknameandcode("0");
        }
    })
    //统一社会信用代码重复判断重复判断
    ModeForm.bindFieldChangeEvent(tyshxydm, function (obj, id, value) {
        if(value != ""){
            checknameandcode("1");
        }
    })
    showHideZZS();
    checkCustomize= function () {
        var gysmccfbs_val = ModeForm.getFieldValue(gysmccfbs);
        var tyshxydmzf_val = ModeForm.getFieldValue(tyshxydmzf);
        if(gysmccfbs_val == "1" || tyshxydmzf_val == "1"){
            Dialog.alert("供应商名称或统一社会信用代码重复无法保存，请检查");
            return false;
        }
        return true;
    }

})
function checknameandcode(type){
    var  urlinfo=ModeForm.getCardUrlInfo();
    var billid = urlinfo.billid;
    var gysmc_val = ModeForm.getFieldValue(gysmc);
    var tyshxydm_val = ModeForm.getFieldValue(tyshxydm);
    var checkresult = "";
    if(type == 0){
        tyshxydm_val = "";
    }else if(type ==1){
        gysmc_val = "";
    }
    jQuery.ajax({
        type: "POST",
        url: "/rrd/supplier/mode/checknameandcode.jsp",
        data: {"gysmc":gysmc_val,"tyshxydm":tyshxydm_val,"billid":billid},
        dataType: "text",
        async:false,//同步 true异步
        success: function(data){
            data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            checkresult = data;

        }

    });
    if(checkresult == "0"){
        Dialog.alert("供应商信息不重复，请填写其他信息");
    }else if(checkresult == "1"){
        Dialog.alert("供应商有重复信息，请确认后填写");
    }
    if(type == 0){
        ModeForm.changeFieldValue(gysmccfbs, {value:checkresult});

    }else if(type ==1){
        ModeForm.changeFieldValue(tyshxydmzf, {value:checkresult});
    }
}
//隐藏显示生产和业务能力（制造商）
function showHideZZS(zzs_val){
    var zzs_val = ModeForm.getFieldValue(zzs);
    if(zzs_val == "1"){
        jQuery("#schywnl_1").show();
        jQuery("#schywnl_2").show();
        jQuery("#schywnl_3").show();
        jQuery("#schywnl_4").show();
        jQuery("#schywnl_5").show();
        ModeForm.changeFieldAttr(ygzsr, 3);
        ModeForm.changeFieldAttr(bgcsmjm2, 3);
        ModeForm.changeFieldAttr(sccsmjm2, 3);
        ModeForm.changeFieldAttr(glryzsr, 3);
        ModeForm.changeFieldAttr(ckmjm2, 3);
        ModeForm.changeFieldAttr(sbhcpnlms, 3);
    }else{
        jQuery("#schywnl_1").hide();
        jQuery("#schywnl_2").hide();
        jQuery("#schywnl_3").hide();
        jQuery("#schywnl_4").hide();
        jQuery("#schywnl_5").hide();
        ModeForm.changeFieldAttr(ygzsr, 2);
        ModeForm.changeFieldAttr(bgcsmjm2, 2);
        ModeForm.changeFieldAttr(sccsmjm2, 2);
        ModeForm.changeFieldAttr(glryzsr, 2);
        ModeForm.changeFieldAttr(ckmjm2, 2);
        ModeForm.changeFieldAttr(sbhcpnlms, 2);
        ModeForm.changeFieldValue(ygzsr, {value:""});
        ModeForm.changeFieldValue(bgcsmjm2, {value:""});
        ModeForm.changeFieldValue(sccsmjm2, {value:""});
        ModeForm.changeFieldValue(glryzsr, {value:""});
        ModeForm.changeFieldValue(ckmjm2, {value:""});
        ModeForm.changeFieldValue(sbhcpnlms, {value:""});
    }
}