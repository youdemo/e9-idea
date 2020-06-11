var zzs = WfForm.convertFieldNameToId("zzs");	//制造商
//生产和业务能力（制造商）
var ygzsr = WfForm.convertFieldNameToId("ygzsr");	//员工总数（人）
var bgcsmjm2 = WfForm.convertFieldNameToId("bgcsmjm2");	//办公场所面积（M2）
var sccsmjm2 = WfForm.convertFieldNameToId("sccsmjm2");	//生产场所面积（M2）
var glryzsr = WfForm.convertFieldNameToId("glryzsr");	//管理人员总数（人）
var ckmjm2 = WfForm.convertFieldNameToId("ckmjm2");	//仓库面积（M2）
var sbhcpnlms = WfForm.convertFieldNameToId("sbhcpnlms");	//设备和产品能力描述

var pkg = WfForm.convertFieldNameToId("pkg");	//PKG
var pub = WfForm.convertFieldNameToId("pub");	//pub
var label = WfForm.convertFieldNameToId("label");	//label
var ms = WfForm.convertFieldNameToId("ms");	//ms
var hr = WfForm.convertFieldNameToId("hr");	//hr
var legal = WfForm.convertFieldNameToId("legal");	//legal
var finance = WfForm.convertFieldNameToId("finance");	//finance

var khzd = WfForm.convertFieldNameToId("khzd");	//客户指定
var khzdgysxs = WfForm.convertFieldNameToId("khzdgysxs");	//客户指定供应商形式
var khzdgyssldbjyyly = WfForm.convertFieldNameToId("khzdgyssldbjyyly");	//客户指定供应商事例的背景/原因/理由
var fcczldkhrydydzwtx = WfForm.convertFieldNameToId("fcczldkhrydydzwtx");	//发出此指令的客户人员对应的职位/头衔
var xsfzr = WfForm.convertFieldNameToId("xsfzr");	//销售负责人
//zrk
var gysmc = WfForm.convertFieldNameToId("gysmc");	//供应商名称
var tyshxydm = WfForm.convertFieldNameToId("tyshxydm");	//社会代码
var gysmczfbs = WfForm.convertFieldNameToId("gysmczfbs");	//供应商名称重复标识
var tyshxydmzfbs = WfForm.convertFieldNameToId("tyshxydmzfbs");	//统一社会信用代码重复标识
var lcrid = WfForm.convertFieldNameToId("lcrid");	//流程rid
// var zzs = WfForm.convertFieldNameToId("zzs");	//
var fxs = WfForm.convertFieldNameToId("fxs");	//分销商
var fws = WfForm.convertFieldNameToId("fws");	//服务商
var cnzj = WfForm.convertFieldNameToId("cnzj");	//产能增加
var yrgyjzldzy = WfForm.convertFieldNameToId("yrgyjzldzy");	//引入更有竞争力的资源
var khzd = WfForm.convertFieldNameToId("khzd");	//客户指定
var xywdr = WfForm.convertFieldNameToId("xywdr");	//新业务导入
var xqykf = WfForm.convertFieldNameToId("xqykf");	//新区域开发

var bz1 = WfForm.convertFieldNameToId("bz1");	//币种1
var bz2 = WfForm.convertFieldNameToId("bz2");	//币种2
var bz3 = WfForm.convertFieldNameToId("bz3");	//币种3
var khh1 = WfForm.convertFieldNameToId("khh1");	//开户行1
var khh2 = WfForm.convertFieldNameToId("khh2");	//开户行2
var khh3 = WfForm.convertFieldNameToId("khh3");	//开户行3
var yhzh1 = WfForm.convertFieldNameToId("yhzh1");	//银行账号1
var yhzh2 = WfForm.convertFieldNameToId("yhzh2");	//银行账号2
var yhzh3 = WfForm.convertFieldNameToId("yhzh3");	//银行账号3
var swiftcode1 = WfForm.convertFieldNameToId("swiftcode1");	//swiftcode1
var swiftcode2 = WfForm.convertFieldNameToId("swiftcode2");	//swiftcode1
var swiftcode3 = WfForm.convertFieldNameToId("swiftcode3");	//swiftcode1
jQuery(document).ready(function () {

    //绑定制造商
    WfForm.bindFieldChangeEvent(zzs, function (obj, id, value) {
        showHideZZS();
    })

    var changefilebz = bz1 + "," + bz2 + "," + bz3;
    WfForm.bindFieldChangeEvent(changefilebz, function (obj, id, value) {
        changebzattr();
    })
    changebzattr();

    var checkboxfield1 = hr + "," + legal + "," + finance;
    WfForm.bindFieldChangeEvent(checkboxfield1, function (obj, id, value) {
        if (value == "1") {
            disableotherCheck(id);
        } else {
            showallcheck();
        }
    })
    var checkboxfield2 = pkg + "," + pub + "," + label + "," + ms;
    WfForm.bindFieldChangeEvent(checkboxfield2, function (obj, id, value) {
        var pkg_val = WfForm.getFieldValue(pkg);
        var pub_val = WfForm.getFieldValue(pub);
        var label_val = WfForm.getFieldValue(label);
        var ms_val = WfForm.getFieldValue(ms);
        if (pkg_val == "1" || pub_val == "1" || label_val == "1" || ms_val == "1") {
            disableotherCheck(id);
        } else {
            showallcheck();
        }
    })

    //客户指定绑定
    WfForm.bindFieldChangeEvent(khzd, function (obj, id, value) {
        showhidekhzdxz();
    })
    //客户指定供应商形式绑定
    WfForm.bindFieldChangeEvent(khzdgysxs, function (obj, id, value) {
        showhidekhzdother();
    })
    showHideZZS();
    showOrdisablechek();
    showhidekhzdxz();
    showhidekhzdother();
    //zrk
    //名称重复判断
    WfForm.bindFieldChangeEvent(gysmc, function (obj, id, value) {
        if (value != "") {
            var retnam = checknameandcode("0");
            if (retnam == "1") {
                Dialog.alert("供应商信息有重复，请确认后填写");
            }
        }
    })
    //统一社会信用代码重复判断重复判断
    // WfForm.bindFieldChangeEvent(tyshxydm, function (obj, id, value) {
    // if (value != "") {
    // checknameandcode("1");
    // }
    // })
    WfForm.registerCheckEvent( WfForm.OPER_SUBMIT, function (callback) {
        var bz1_val = WfForm.getFieldValue(bz1);
        var bz2_val = WfForm.getFieldValue(bz2);
        var bz3_val = WfForm.getFieldValue(bz3);
        if(bz1_val != "" && bz2_val != "" && bz1_val == bz2_val){
            Dialog.alert("币种选择有重复，请检查后再提交");
            return;
        }
        if(bz1_val != "" && bz3_val != "" && bz1_val == bz3_val){
            Dialog.alert("币种选择有重复，请检查后再提交");
            return;
        }
        if(bz2_val != "" && bz3_val != "" && bz2_val == bz3_val){
            Dialog.alert("币种选择有重复，请检查后再提交");
            return;
        }
        var cont = true;
        checknameandcode("0");
        var gysmccfbs_val = WfForm.getFieldValue(gysmczfbs);
        var tyshxydmzfbs_val = WfForm.getFieldValue(tyshxydmzfbs);
        var res_jysx = checkJYSX();
        if (res_jysx == '1') {
            Dialog.alert("经营属性 至少选择一个，请检查！");
            return;
        }
        var kfmd_rt = checkKFMD();
        if (kfmd_rt == '1') {
            Dialog.alert("开发目的 至少选择一个，请检查！");
            return;
        }
        var syb = checkSYBSX();
        if (syb == '1') {
            Dialog.alert("供应商事业部属性 至少选择一个，请检查！");
            return;
        }
        var retcod = checkcode();
        if(retcod == '1'){
            Dialog.alert("在途流程中已存在 相同的供应商统一信用代码，请确认！");
            return ;
        }
        if (gysmccfbs_val == "1" && cont) {
            Dialog.alert("供应商名称重复,请确认。");
            return ;
        }
        callback();
    });
})

function changebzattr(){
    var bz1_val = WfForm.getBrowserShowName(bz1);
    var bz2_val = WfForm.getBrowserShowName(bz2);
    var bz3_val = WfForm.getBrowserShowName(bz3);
    if(bz1_val=="RMB"){
        WfForm.changeFieldAttr(khh1,3);
        WfForm.changeFieldAttr(yhzh1,3);
        WfForm.changeFieldAttr(swiftcode1,1);
        WfForm.changeFieldValue(swiftcode1,{value:""})
    }else if(bz1_val=="undefined" || bz1_val==""){
        WfForm.changeFieldAttr(khh1,3);
        WfForm.changeFieldAttr(yhzh1,3);
        WfForm.changeFieldAttr(swiftcode1,2);
    }else{
        WfForm.changeFieldAttr(khh1,3);
        WfForm.changeFieldAttr(yhzh1,3);
        WfForm.changeFieldAttr(swiftcode1,3);
    }
    if(bz2_val=="RMB"){
        WfForm.changeFieldAttr(khh2,3);
        WfForm.changeFieldAttr(yhzh2,3);
        WfForm.changeFieldAttr(swiftcode2,1);
        WfForm.changeFieldValue(swiftcode2,{value:""})
    }else if(bz2_val=="undefined" || bz2_val==""){
        WfForm.changeFieldAttr(khh2,2);
        WfForm.changeFieldAttr(yhzh2,2);
        WfForm.changeFieldAttr(swiftcode2,2);
    }else{
        WfForm.changeFieldAttr(khh2,3);
        WfForm.changeFieldAttr(yhzh2,3);
        WfForm.changeFieldAttr(swiftcode2,3);
    }
    if(bz3_val=="RMB"){
        WfForm.changeFieldAttr(khh3,3);
        WfForm.changeFieldAttr(yhzh3,3);
        WfForm.changeFieldAttr(swiftcode3,1);
        WfForm.changeFieldValue(swiftcode3,{value:""})
    }else if(bz3_val=="undefined" || bz3_val==""){
        WfForm.changeFieldAttr(khh3,2);
        WfForm.changeFieldAttr(yhzh3,2);
        WfForm.changeFieldAttr(swiftcode3,2);
    }else{
        WfForm.changeFieldAttr(khh3,3);
        WfForm.changeFieldAttr(yhzh3,3);
        WfForm.changeFieldAttr(swiftcode3,3);
    }
}

//显示隐藏 客户指定其他
function showhidekhzdother() {
    var khzdgysxs_val = WfForm.getFieldValue(khzdgysxs);
    if (khzdgysxs_val == "0") {
        jQuery("#khzd_1").show();
        jQuery("#khzd_2").show();
        WfForm.changeFieldAttr(fcczldkhrydydzwtx, 3);
        WfForm.changeFieldAttr(xsfzr, 3);
        WfForm.changeFieldAttr(khzdgyssldbjyyly, 3);

    } else {
        jQuery("#khzd_2").hide();
        jQuery("#khzd_1").hide();
        WfForm.changeFieldAttr(fcczldkhrydydzwtx, 2);
        WfForm.changeFieldAttr(xsfzr, 2);
        WfForm.changeFieldAttr(khzdgyssldbjyyly, 2);
        WfForm.changeFieldValue(fcczldkhrydydzwtx, {value: ""});
        WfForm.changeFieldValue(xsfzr, {
            value: "",
            specialobj: [
                {id: "", name: ""}
            ]
        });
        WfForm.changeFieldValue(khzdgyssldbjyyly, {value: ""});
    }
}
//显示隐藏客户指定供应商形式
function showhidekhzdxz() {
    var khzd_val = WfForm.getFieldValue(khzd);
    if (khzd_val == "1") {
        jQuery("#mh_1").show();
        WfForm.changeFieldAttr(khzdgysxs, 3);
    } else {
        WfForm.changeFieldAttr(khzdgysxs, 4);
        WfForm.changeFieldValue(khzdgysxs, {value: ""});
        jQuery("#mh_1").hide();

    }
}
function showOrdisablechek() {
    var pkg_val = WfForm.getFieldValue(pkg);
    var pub_val = WfForm.getFieldValue(pub);
    var label_val = WfForm.getFieldValue(label);
    var ms_val = WfForm.getFieldValue(ms);
    if (pkg_val == "1" || pub_val == "1" || label_val == "1" || ms_val == "1") {
        disableotherCheck(pkg);
    } else {
        var hr_val = WfForm.getFieldValue(hr);
        var legal_val = WfForm.getFieldValue(legal);
        var finance_val = WfForm.getFieldValue(finance);
        if (hr_val == "1") {
            disableotherCheck(hr);
        } else if (legal_val == "1") {
            disableotherCheck(legal);
        } else if (finance_val == "1") {
            disableotherCheck(finance);
        }
    }
}

//将其他按钮置灰
function disableotherCheck(fieldid) {
    if (fieldid != pkg && fieldid != pub && fieldid != label && fieldid != ms) {
        WfForm.changeFieldAttr(pkg, 1);
        WfForm.changeFieldAttr(pub, 1);
        WfForm.changeFieldAttr(label, 1);
        WfForm.changeFieldAttr(ms, 1);
    }
    if (fieldid != hr) {
        WfForm.changeFieldAttr(hr, 1);
    }
    if (fieldid != legal) {
        WfForm.changeFieldAttr(legal, 1);
    }
    if (fieldid != finance) {
        WfForm.changeFieldAttr(finance, 1);
    }
}
//7个check框都能选择
function showallcheck() {
    WfForm.changeFieldAttr(pkg, 2);
    WfForm.changeFieldAttr(pub, 2);
    WfForm.changeFieldAttr(label, 2);
    WfForm.changeFieldAttr(ms, 2);
    WfForm.changeFieldAttr(hr, 2);
    WfForm.changeFieldAttr(legal, 2);
    WfForm.changeFieldAttr(finance, 2);
}

//隐藏显示生产和业务能力（制造商）
function showHideZZS(zzs_val) {
    var zzs_val = WfForm.getFieldValue(zzs);
    if (zzs_val == "1") {
        jQuery("#schywnl_1").show();
        jQuery("#schywnl_2").show();
        jQuery("#schywnl_3").show();
        jQuery("#schywnl_4").show();
        jQuery("#schywnl_5").show();
        WfForm.changeFieldAttr(ygzsr, 3);
        WfForm.changeFieldAttr(bgcsmjm2, 3);
        WfForm.changeFieldAttr(sccsmjm2, 3);
        WfForm.changeFieldAttr(glryzsr, 3);
        WfForm.changeFieldAttr(ckmjm2, 3);
        WfForm.changeFieldAttr(sbhcpnlms, 3);
    } else {
        jQuery("#schywnl_1").hide();
        jQuery("#schywnl_2").hide();
        jQuery("#schywnl_3").hide();
        jQuery("#schywnl_4").hide();
        jQuery("#schywnl_5").hide();
        WfForm.changeFieldAttr(ygzsr, 2);
        WfForm.changeFieldAttr(bgcsmjm2, 2);
        WfForm.changeFieldAttr(sccsmjm2, 2);
        WfForm.changeFieldAttr(glryzsr, 2);
        WfForm.changeFieldAttr(ckmjm2, 2);
        WfForm.changeFieldAttr(sbhcpnlms, 2);
        WfForm.changeFieldValue(ygzsr, {value: ""});
        WfForm.changeFieldValue(bgcsmjm2, {value: ""});
        WfForm.changeFieldValue(sccsmjm2, {value: ""});
        WfForm.changeFieldValue(glryzsr, {value: ""});
        WfForm.changeFieldValue(ckmjm2, {value: ""});
        WfForm.changeFieldValue(sbhcpnlms, {value: ""});
    }
}
//zrk
function checknameandcode(type) {
    var gysmc_val = WfForm.getFieldValue(gysmc);
    var tyshxydm_val = WfForm.getFieldValue(tyshxydm);
    var lcrid_val = WfForm.getFieldValue(lcrid);
    var checkresult = "0";
    jQuery.ajax({
        type: "POST",
        url: "/rrd/supplier/checkChangeNameAndCode.jsp",
        data: {"gysmc": gysmc_val, "tyshxydm": tyshxydm_val, "rid": lcrid_val},
        dataType: "text",
        async: false,//同步 true异步
        success: function (data) {
            data = data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            checkresult = data;

        }

    });
    // if (checkresult == "1") {
    // Dialog.alert("供应商信息有重复，请确认后填写");
    // }
    if (type == 0) {
        // WfForm.changeFieldValue(gysmczfbs, {value: checkresult});
        WfForm.changeFieldValue("field9681", {value: checkresult});

    } else if (type == 1) {
        WfForm.changeFieldValue("field9682", {value: checkresult});
    }
    return checkresult;
}

function checkJYSX() {
    var res = "0";
    var zzss_val = WfForm.getFieldValue(zzs);
    var fxs_val = WfForm.getFieldValue(fxs);
    var fws_val = WfForm.getFieldValue(fws);
    if (zzss_val.length < 1) {
        zzss_val = "0";
    }
    if (fxs_val.length < 1) {
        fxs_val = "0";
    }
    if (fws_val.length < 1) {
        fws_val = "0";
    }
    if (zzss_val == "0" && fxs_val == '0' && fws_val == '0') {
        res = "1";
    }
    return res;
}
function checkSYBSX() {
    var res = "0";
    var pkg_val = WfForm.getFieldValue(pkg);
    var pub_val = WfForm.getFieldValue(pub);
    var label_val = WfForm.getFieldValue(label);
    var ms_val = WfForm.getFieldValue(ms);
    var hr_val = WfForm.getFieldValue(hr);
    var legal_val = WfForm.getFieldValue(legal);
    var finance_val = WfForm.getFieldValue(finance);
    if (pkg_val.length < 1) {
        pkg_val = "0";
    }
    if (pub_val.length < 1) {
        pub_val = "0";
    }
    if (label_val.length < 1) {
        label_val = "0";
    }
    if (ms_val.length < 1) {
        ms_val = "0";
    }
    if (hr_val.length < 1) {
        hr_val = "0";
    }
    if (legal_val.length < 1) {
        legal_val = "0";
    }
    if (finance_val.length < 1) {
        finance_val = "0";
    }
    if (pkg_val == "0" && pub_val == '0' && label_val == '0' && ms_val == '0' && hr_val == '0' && legal_val == '0' && finance_val == '0') {
        res = "1";
    }
    return res;
}
function checkKFMD(){
    var res = "0";
    var cnzj_val = WfForm.getFieldValue(cnzj);
    var yrgyjzldzy_val = WfForm.getFieldValue(yrgyjzldzy);
    var khzd_val = WfForm.getFieldValue(khzd);
    var xywdr_val = WfForm.getFieldValue(xywdr);
    var xqykf_val = WfForm.getFieldValue(xqykf);
    if (cnzj_val.length < 1) {
        cnzj_val = "0";
    }
    if (yrgyjzldzy_val.length < 1) {
        yrgyjzldzy_val = "0";
    }
    if (khzd_val.length < 1) {
        khzd_val = "0";
    }
    if (xywdr_val.length < 1) {
        xywdr_val = "0";
    }
    if (xqykf_val.length < 1) {
        xqykf_val = "0";
    }
    if (cnzj_val == "0" && yrgyjzldzy_val == '0' && khzd_val == '0' && xywdr_val == '0' && xqykf_val == '0') {
        res = "1";
    }
    return res;

}
function checkcode() {
    var gysmc_val = WfForm.getFieldValue(gysmc);
    var tyshxydm_val = WfForm.getFieldValue(tyshxydm);
    var lcrid_val = WfForm.getFieldValue(lcrid);
    var checkresult = "0";
    jQuery.ajax({
        type: "POST",
        url: "/rrd/supplier/checkChangeCode.jsp",
        data: {"gysmc": gysmc_val, "tyshxydm": tyshxydm_val, "rid": lcrid_val},
        dataType: "text",
        async: false,//同步 true异步
        success: function (data) {
            data = data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
            checkresult = data;

        }

    });
    return checkresult;
    // if (checkresult == "1") {
    // Dialog.alert("供应商信息有重复，请确认后填写");
    // }
}