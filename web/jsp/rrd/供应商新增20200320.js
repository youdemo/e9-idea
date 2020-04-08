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
jQuery(document).ready(function() {

    //绑定制造商
    WfForm.bindFieldChangeEvent(zzs, function(obj,id,value){
        showHideZZS();
    })

    var checkboxfield1=hr+","+legal+","+finance;
    WfForm.bindFieldChangeEvent(checkboxfield1, function(obj,id,value){
        if(value == "1"){
            disableotherCheck(id);
        }else{
            showallcheck();
        }
    })
    var checkboxfield2=pkg+","+pub+","+label+","+ms;
    WfForm.bindFieldChangeEvent(checkboxfield2, function(obj,id,value){
        var pkg_val = WfForm.getFieldValue(pkg);
        var pub_val = WfForm.getFieldValue(pub);
        var label_val = WfForm.getFieldValue(label);
        var ms_val = WfForm.getFieldValue(ms);
        if(pkg_val == "1" || pub_val == "1" || label_val == "1" || ms_val == "1"){
            disableotherCheck(id);
        }else{
            showallcheck();
        }
    })

    //客户指定绑定
    WfForm.bindFieldChangeEvent(khzd, function(obj,id,value){
        showhidekhzdxz();
    })
    //客户指定供应商形式绑定
    WfForm.bindFieldChangeEvent(khzdgysxs, function(obj,id,value){
        showhidekhzdother();
    })
    showHideZZS();
    showOrdisablechek();
    showhidekhzdxz();
    showhidekhzdother();
})

//显示隐藏 客户指定其他
function showhidekhzdother(){
    var khzdgysxs_val =  WfForm.getFieldValue(khzdgysxs);
    if(khzdgysxs_val == "1"){
        jQuery("#khzd_1").show();
        jQuery("#khzd_2").show();
        WfForm.changeFieldAttr(fcczldkhrydydzwtx, 3);
        WfForm.changeFieldAttr(xsfzr, 3);
        WfForm.changeFieldAttr(khzdgyssldbjyyly, 3);

    }else{
        jQuery("#khzd_2").hide();
        jQuery("#khzd_1").hide();
        WfForm.changeFieldAttr(fcczldkhrydydzwtx, 2);
        WfForm.changeFieldAttr(xsfzr, 2);
        WfForm.changeFieldAttr(khzdgyssldbjyyly, 2);
        WfForm.changeFieldValue(fcczldkhrydydzwtx, {value:""});
        WfForm.changeFieldValue(xsfzr, {
            value: "",
            specialobj:[
                {id:"",name:""}
            ]
        });
        WfForm.changeFieldValue(khzdgyssldbjyyly, {value:""});
    }
}


//显示隐藏客户指定供应商形式
function showhidekhzdxz(){
    var khzd_val =  WfForm.getFieldValue(khzd);
    if(khzd_val == "1"){
        jQuery("#mh_1").show();
        WfForm.changeFieldAttr(khzdgysxs, 3);
    }else{
        WfForm.changeFieldAttr(khzdgysxs, 4);
        WfForm.changeFieldValue(khzdgysxs, {value:""});
        jQuery("#mh_1").hide();

    }
}
function showOrdisablechek(){
    var pkg_val = WfForm.getFieldValue(pkg);
    var pub_val = WfForm.getFieldValue(pub);
    var label_val = WfForm.getFieldValue(label);
    var ms_val = WfForm.getFieldValue(ms);
    if(pkg_val == "1" || pub_val == "1" || label_val == "1" || ms_val == "1"){
        disableotherCheck(pkg);
    }else{
        var hr_val = WfForm.getFieldValue(hr);
        var legal_val = WfForm.getFieldValue(legal);
        var finance_val = WfForm.getFieldValue(finance);
        if(hr_val == "1"){
            disableotherCheck(hr);
        }else if(legal_val == "1"){
            disableotherCheck(legal);
        }else if(finance_val == "1"){
            disableotherCheck(finance);
        }
    }
}

//将其他按钮置灰
function disableotherCheck(fieldid){
    if(fieldid != pkg && fieldid != pub && fieldid != label && fieldid != ms){
        WfForm.changeFieldAttr(pkg, 1);
        WfForm.changeFieldAttr(pub, 1);
        WfForm.changeFieldAttr(label, 1);
        WfForm.changeFieldAttr(ms, 1);
    }
    if(fieldid != hr){
        WfForm.changeFieldAttr(hr, 1);
    }
    if(fieldid != legal){
        WfForm.changeFieldAttr(legal, 1);
    }
    if(fieldid != finance){
        WfForm.changeFieldAttr(finance,1);
    }
}
//7个check框都能选择
function showallcheck(){
    WfForm.changeFieldAttr(pkg, 2);
    WfForm.changeFieldAttr(pub, 2);
    WfForm.changeFieldAttr(label, 2);
    WfForm.changeFieldAttr(ms, 2);
    WfForm.changeFieldAttr(hr, 2);
    WfForm.changeFieldAttr(legal, 2);
    WfForm.changeFieldAttr(finance, 2);
}

//隐藏显示生产和业务能力（制造商）
function showHideZZS(zzs_val){
    var zzs_val = WfForm.getFieldValue(zzs);
    if(zzs_val == "1"){
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
    }else{
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
        WfForm.changeFieldValue(ygzsr, {value:""});
        WfForm.changeFieldValue(bgcsmjm2, {value:""});
        WfForm.changeFieldValue(sccsmjm2, {value:""});
        WfForm.changeFieldValue(glryzsr, {value:""});
        WfForm.changeFieldValue(ckmjm2, {value:""});
        WfForm.changeFieldValue(sbhcpnlms, {value:""});
    }
}