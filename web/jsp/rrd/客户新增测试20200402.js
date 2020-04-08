<script src="/plugin/workflowForm/util/jquery-1.11.3.min.js"></script>
    <script src="/plugin/workflowForm/util/dev_drag.js"></script>

    <script>
var fplx = WfForm.convertFieldNameToId("fplx");	//发票类型
var khmczfxy = WfForm.convertFieldNameToId("khmczfxy");	//客户名称重复校验
var nsrsbhzfxy = WfForm.convertFieldNameToId("nsrsbhzfxy");	//纳税人识别号重复校验
var khqz = WfForm.convertFieldNameToId("khqz");	//客户群组
var xjkhqznickname = WfForm.convertFieldNameToId("xjkhqznickname");	//新建客户群组（nickname）
var xjkhqzrealname = WfForm.convertFieldNameToId("xjkhqzrealname");	//
var ywx = "field7045";//业务线
var xs2 = "field6880";//销售2
var xskhjl = "field6881";//销售负责人/客户经理
var aejl = "field6882";//AE经理
var esjl = "field6883";//ES经理
var hyzfl = "field6566";//行业子分类
var szqy = "field6567";//所在区域
var khsq2 = "field7340";//客户授权2
var xs3 = "field6884";//销售3
var xsfzr3 = "field6885";//销售负责人3
var csrjl = "field6886";//CSR经理
var khss3 = "field7341";//客户授权3
var xs1 = "field6877";//销售1
var xskhjl1 = "field6878";//销售负责人/客户经理1
var csrjl1 = "field6879";//CSR经理 1
var khsh1 = "field7339";//客户授权1
var sfsyjt = "field6907";//是否属于OEM/ODM集团
var oemdomjt = "field7088";//OEM/ODM集团
var sfxyzf = "field6564";//是否需要支付
var mgxs1 = "field7061";//美国销售1
var mgxs2 = "field7062";//美国销售2
var mgxs3 = "field7063";//美国销售3
var mgxs4 = "field7064";//美国销售4
var fzpd = "field7842";//辅助判断 realnam   nickname
var sfxykhqz = "field7825";//是否现有客户群组
var xjkhqzr = "field6553";//新建客户群组realname
var xjkhqzn = "field6554";//新建客户群组nickname
var khqz = "field6568";//客户群组
var khqzdm = "field7585";//客户群组代码

jQU
//提交控制
WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
    var v_fplx = WfForm.getFieldValue(fplx);
    var v_khmczfxy = WfForm.getFieldValue(khmczfxy);
    var v_nsrsbhzfxy = WfForm.getFieldValue(nsrsbhzfxy);
    var khqz_val = WfForm.getFieldValue(khqz);
    var xjkhqznickname_val = WfForm.getFieldValue(xjkhqznickname);
    var xjkhqzrealname_val = WfForm.getFieldValue(xjkhqzrealname);
    var fzpdt_v = WfForm.getFieldValue(fzpd);
    if(xjkhqznickname_val !="" || xjkhqzrealname_val !=""){
        if(khqz_val !=""){
            WfForm.showMessage("客户群组和新建客户群组不可以同时存在数据，请修改!",1,2);
            return;
        }
    }
    if(Number(v_khmczfxy)>0){
        WfForm.showMessage("客户名称已存在，请确认!");
        return;
    }
    if(Number(fzpdt_v)>0){
        WfForm.showMessage("新建客户群组（nickname）已存在，请确认!",1,3);
        return;
    }
    if(v_fplx=="0"&&Number(v_nsrsbhzfxy)>0){
        WfForm.showMessage("纳税人识别号已存在，请确认!");
        return;
    }
    callback();
});

//客户名称重复校验绑定
WfForm.bindFieldChangeEvent(khmczfxy, function(obj,id,value) {
    if(Number(value)>0){
        WfForm.showMessage("客户名称已存在，请确认!",1,3);
    }
})
jQuery(document).ready(function() {
    //申请类型
    WfForm.bindFieldChangeEvent(sfxykhqz, function(obj,id,value){
        WfForm.changeFieldValue(xjkhqzr,{value:'',specialobj:[{id:xjkhqzr,name:''}],});
        WfForm.changeFieldValue(xjkhqzn,{value:'',specialobj:[{id:xjkhqzn,name:''}],});
        WfForm.changeFieldValue(khqz,{value:'',specialobj:[{id:khqz,name:''}],});
        WfForm.changeFieldValue(khqzdm,{value:'',specialobj:[{id:khqzdm,name:''}],});
    });

    setTimeout("showhide()",300);
    WfForm.bindFieldChangeEvent(khqz, function(obj,id,value) {
        showhide();
    })

    jQuery("#wftipsoutdiv1").html(
        '<div class="drag_box"><div class="title">小贴士<span class="close">X</span></div>'+
        '<div class="content"><p><b>纳税人识别号:</b><br/>1. 国内客户 - 如未三证合一，可填写税号<br/>2. 国外客户 - Tax Number，或Business License Number等可证明客户唯一的号码  <br/><b>内销客户（含保税区）:</b><br/>信用额度＝对某一客户的估计月交易额*(信用期/30+0.5)*调整比例（参看风险级别）  <br /><b>外销客户:</b><br/>信用额度＝对某一客户的估计月交易额 * (信用期 / 30) * 调整比例（参看风险级别） <br></p></div> </div>'
    );
    $(".drag_box").bg_move({ move: ".title", closed: ".close", size: 6 });

    //业务线
    WfForm.bindFieldChangeEvent(ywx, function(obj,id,value) {
        yelxAction();
    })

    //是否需要支付
    WfForm.bindFieldChangeEvent(sfxyzf, function(obj,id,value) {
        mgxsAction();
    })

    WfForm.bindFieldChangeEvent(xjkhqznickname, function(obj,id,value){
        var fzpd_v = WfForm.getFieldValue(fzpd);
        var real_v = WfForm.getFieldValue(xjkhqzrealname);
        if( value.length>0 ){
            var resu = getkhz(real_v, value);
            if(Number(resu)>0){
                WfForm.showMessage("新建客户群组（nickname）:"+value+" 已存在，请确认!",1,3);
                WfForm.changeFieldValue(xjkhqznickname, {value:""});
            }
        }
    });

})
function getkhz(realname, nickname) {
    var result = 0;

    var xhr = null;
    if(window.ActiveXObject) {//IE浏览器
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }else if (window.XMLHttpRequest){
        xhr =new XMLHttpRequest();
    }
    var st = new Date().getTime();
    if (null!= xhr) {
        xhr.open("GET","/rrd/jsp/getkhz.jsp?realname="+realname+"&nickname="+nickname+"&stm="+st,false);
        xhr.onreadystatechange = function() {
            if(xhr.readyState==4){
                if(xhr.status==200){
                    var text= xhr.responseText;
                    text=text.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g,'');
                    result = text;
                }
            }
        }
        xhr.send();
    }
    return result;
}
function showhide(){
    var khqz_val = WfForm.getFieldValue(khqz);
    if(khqz_val == ""){
        //WfForm.changeFieldAttr(xjkhqznickname, 3);
        //WfForm.changeFieldAttr(xjkhqzrealname, 3);
    }else{
        //WfForm.changeFieldAttr(xjkhqznickname, 2);
        //WfForm.changeFieldAttr(xjkhqzrealname, 2);
    }

}
//业务类型动作
function yelxAction(){
    var ywlxVal = WfForm.getFieldValue(ywx);
    if(ywlxVal === '0' || ywlxVal === '1' ){//Packaging 或者 Labels
        WfForm.changeFieldValue(xs2,{value:"",specialobj:[],});
        WfForm.changeFieldValue(xskhjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(aejl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(esjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(hyzfl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(szqy,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khsq2,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xs3,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xsfzr3,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(csrjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khss3,{ value:'',specialobj:[],});
    }else if(ywlxVal === '2'){ //Publishing?Services
        WfForm.changeFieldValue(xs3,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xsfzr3,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(csrjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khss3,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xs1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xskhjl1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(csrjl1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khsh1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(sfsyjt,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(oemdomjt,{ value:'',specialobj:[],});
    }else if(ywlxVal === '3'){//Marketing?Services
        WfForm.changeFieldValue(xs1,{ value:"",specialobj:[],});
        WfForm.changeFieldValue(xskhjl1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khsh1,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(sfsyjt,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(oemdomjt,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xs2,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(xskhjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(aejl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(esjl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(hyzfl,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(szqy,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(khsq2,{ value:'',specialobj:[],});
        WfForm.changeFieldValue(csrjl1,{ value:'',specialobj:[],});
    }
}
//是否需要支付动作
function mgxsAction(){
    var sfxyzfVal = WfForm.getFieldValue(sfxyzf);
    if(sfxyzfVal === '1'){//是否需要支付等于否
        WfForm.changeFieldValue(mgxs1,{value:"",specialobj:[],});
        WfForm.changeFieldValue(mgxs2,{value:"",specialobj:[],});
        WfForm.changeFieldValue(mgxs3,{value:"",specialobj:[],});
        WfForm.changeFieldValue(mgxs4,{value:"",specialobj:[],});
    }
}
</script>

<style>
/*e8*/

.e8_os{

    min-width:100% !important;
}

.e8_outScroll{
    border:none;
}

select {
    line-height: 22px;
    height: 22px;
    background: none;
    border: none;
    min-width:100%;
}
/* e8 明细表斑马线 */
.excelDetailTable tr:nth-child(2n+3) td {
    background: #f7f7f7!important;
}
.excelDetailTable tr:nth-child(2n+3) td:first-child ,  .excelDetailTable tr:nth-child(2n+3) td:last-child {
    background:transparent !important;
}


.excelDetailTable tr:nth-child(2n+3) td input{
    background: #f7f7f7!important;
}



/* e9样式-明细表斑马线条纹 */
tr.detail_even_row td{
    background: #FAFAFA!important;
}
tr.detail_even_row td:first-child{
    background: transparent !important;
}
tr.detail_even_row td:last-child{
    background: transparent !important;
}
tr.detail_even_row td .wf-input{
    background: #FAFAFA!important;
}
.excelDetailTable tr td:first-child{
    border-top-color: #efefef!important;
    border-left-color: #efefef!important;
}
.excelDetailTable tr td:last-child{
    border-top-color: #efefef!important;
    border-right-color: #efefef!important;
}

.excelDetailTable tr:nth-child(2n+2) td {
    background: transparent !important;
}
.excelDetailTable tr:nth-child(2n+2) td input{
    background: transparent !important;
}


/* 样式-下拉框长度 */
.wea-select{
    width: 95%
}
/*选择框宽度*/
.wea-select ,.ant-select-selection{
    width: 95%;
    border-style: none;
}

/*浏览框去边框，调整宽度*/
.wea-associative-search {
    border-style: none;
    min-width: 100% !important;
}
/* e9样式结束*/

/*通用圆角样式*/
.ysyj{
    height:15px;
    width:100%;
    background:#ffffff!important;
    border: 1px solid #ffffff!important;
    border-top-right-radius:9px;
}
.zxyj{
    height:15px;
    width:100%;
    background:#ffffff!important;
    border: 1px solid #ffffff!important;
    border-bottom-left-radius:9px;
}
.yxyj{
    height:15px;
    width:100%;
    background:#ffffff!important;
    border: 1px solid #ffffff!important;
    border-bottom-right-radius:9px;
}
.zsyj{
    height:15px;
    width:100%;
    background:#ffffff!important;
    border: 1px solid #ffffff!important;
    border-top-left-radius:9px;
}





/*单行文本去背景*/
input.InputStyle,
    input.Inputstyle,
    input.inputStyle,
    input.inputstyle,
.excelMainTable input[type="text"],
.excelMainTable input[type="password"],
.e8_innerShowContent,
.excelMainTable textarea,
.sbHolder {
    /*border: 1px solid #F2F2F2 !important;*/
    border: none;
    background: none;
}



/* 修复 input 框 Chrome 自动填充屎黄色背景 */
input:-webkit-autofill,.excelMainTable input:-webkit-autofill:hover,.excelMainTable input:-webkit-autofill:focus,.excelMainTable input:-webkit-autofill:active {
    -webkit-transition-delay: 99999s;
    -webkit-transition: color 99999s ease-out, background-color 99999s ease-out;
}



</style>
<style>
.drag_box {
    position: fixed;
    top: 50%;
    right: 20px;
    width: 470px;
    min-height: 160px;
    box-shadow: 7px 15px 20px #bcbcbc;
    background: #fde61b;
    z-index: 1000;
}
.drag_box .title {
    height: 35px;
    line-height: 30px;
    font-size: 12px;
    text-align: center;
    color: #000;
    font-weight: bold;
    cursor: move;
}
.drag_box .close {
    border: 1px solid #d08;
    color: #d08;
    width: 20px;
    height: 20px;
    line-height: 19px;
    cursor: pointer;
    position: absolute;
    right: 5px;
    top: 5px;
    font-weight: bold;
    border-radius: 50%;
}
.drag_box .content {
    line-height: 20px;
    padding: 4px 10px;
}
.drag_box .bg_change_size {
    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKTWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjanVN3WJP3Fj7f92UPVkLY8LGXbIEAIiOsCMgQWaIQkgBhhBASQMWFiApWFBURnEhVxILVCkidiOKgKLhnQYqIWotVXDjuH9yntX167+3t+9f7vOec5/zOec8PgBESJpHmomoAOVKFPDrYH49PSMTJvYACFUjgBCAQ5svCZwXFAADwA3l4fnSwP/wBr28AAgBw1S4kEsfh/4O6UCZXACCRAOAiEucLAZBSAMguVMgUAMgYALBTs2QKAJQAAGx5fEIiAKoNAOz0ST4FANipk9wXANiiHKkIAI0BAJkoRyQCQLsAYFWBUiwCwMIAoKxAIi4EwK4BgFm2MkcCgL0FAHaOWJAPQGAAgJlCLMwAIDgCAEMeE80DIEwDoDDSv+CpX3CFuEgBAMDLlc2XS9IzFLiV0Bp38vDg4iHiwmyxQmEXKRBmCeQinJebIxNI5wNMzgwAABr50cH+OD+Q5+bk4eZm52zv9MWi/mvwbyI+IfHf/ryMAgQAEE7P79pf5eXWA3DHAbB1v2upWwDaVgBo3/ldM9sJoFoK0Hr5i3k4/EAenqFQyDwdHAoLC+0lYqG9MOOLPv8z4W/gi372/EAe/tt68ABxmkCZrcCjg/1xYW52rlKO58sEQjFu9+cj/seFf/2OKdHiNLFcLBWK8ViJuFAiTcd5uVKRRCHJleIS6X8y8R+W/QmTdw0ArIZPwE62B7XLbMB+7gECiw5Y0nYAQH7zLYwaC5EAEGc0Mnn3AACTv/mPQCsBAM2XpOMAALzoGFyolBdMxggAAESggSqwQQcMwRSswA6cwR28wBcCYQZEQAwkwDwQQgbkgBwKoRiWQRlUwDrYBLWwAxqgEZrhELTBMTgN5+ASXIHrcBcGYBiewhi8hgkEQcgIE2EhOogRYo7YIs4IF5mOBCJhSDSSgKQg6YgUUSLFyHKkAqlCapFdSCPyLXIUOY1cQPqQ28ggMor8irxHMZSBslED1AJ1QLmoHxqKxqBz0XQ0D12AlqJr0Rq0Hj2AtqKn0UvodXQAfYqOY4DRMQ5mjNlhXIyHRWCJWBomxxZj5Vg1Vo81Yx1YN3YVG8CeYe8IJAKLgBPsCF6EEMJsgpCQR1hMWEOoJewjtBK6CFcJg4Qxwicik6hPtCV6EvnEeGI6sZBYRqwm7iEeIZ4lXicOE1+TSCQOyZLkTgohJZAySQtJa0jbSC2kU6Q+0hBpnEwm65Btyd7kCLKArCCXkbeQD5BPkvvJw+S3FDrFiOJMCaIkUqSUEko1ZT/lBKWfMkKZoKpRzame1AiqiDqfWkltoHZQL1OHqRM0dZolzZsWQ8ukLaPV0JppZ2n3aC/pdLoJ3YMeRZfQl9Jr6Afp5+mD9HcMDYYNg8dIYigZaxl7GacYtxkvmUymBdOXmchUMNcyG5lnmA+Yb1VYKvYqfBWRyhKVOpVWlX6V56pUVXNVP9V5qgtUq1UPq15WfaZGVbNQ46kJ1Bar1akdVbupNq7OUndSj1DPUV+jvl/9gvpjDbKGhUaghkijVGO3xhmNIRbGMmXxWELWclYD6yxrmE1iW7L57Ex2Bfsbdi97TFNDc6pmrGaRZp3mcc0BDsax4PA52ZxKziHODc57LQMtPy2x1mqtZq1+rTfaetq+2mLtcu0W7eva73VwnUCdLJ31Om0693UJuja6UbqFutt1z+o+02PreekJ9cr1Dund0Uf1bfSj9Rfq79bv0R83MDQINpAZbDE4Y/DMkGPoa5hpuNHwhOGoEctoupHEaKPRSaMnuCbuh2fjNXgXPmasbxxirDTeZdxrPGFiaTLbpMSkxeS+Kc2Ua5pmutG003TMzMgs3KzYrMnsjjnVnGueYb7ZvNv8jYWlRZzFSos2i8eW2pZ8ywWWTZb3rJhWPlZ5VvVW16xJ1lzrLOtt1ldsUBtXmwybOpvLtqitm63Edptt3xTiFI8p0in1U27aMez87ArsmuwG7Tn2YfYl9m32zx3MHBId1jt0O3xydHXMdmxwvOuk4TTDqcSpw+lXZxtnoXOd8zUXpkuQyxKXdpcXU22niqdun3rLleUa7rrStdP1o5u7m9yt2W3U3cw9xX2r+00umxvJXcM970H08PdY4nHM452nm6fC85DnL152Xlle+70eT7OcJp7WMG3I28Rb4L3Le2A6Pj1l+s7pAz7GPgKfep+Hvqa+It89viN+1n6Zfgf8nvs7+sv9j/i/4XnyFvFOBWABwQHlAb2BGoGzA2sDHwSZBKUHNQWNBbsGLww+FUIMCQ1ZH3KTb8AX8hv5YzPcZyya0RXKCJ0VWhv6MMwmTB7WEY6GzwjfEH5vpvlM6cy2CIjgR2yIuB9pGZkX+X0UKSoyqi7qUbRTdHF09yzWrORZ+2e9jvGPqYy5O9tqtnJ2Z6xqbFJsY+ybuIC4qriBeIf4RfGXEnQTJAntieTE2MQ9ieNzAudsmjOc5JpUlnRjruXcorkX5unOy553PFk1WZB8OIWYEpeyP+WDIEJQLxhP5aduTR0T8oSbhU9FvqKNolGxt7hKPJLmnVaV9jjdO31D+miGT0Z1xjMJT1IreZEZkrkj801WRNberM/ZcdktOZSclJyjUg1plrQr1zC3KLdPZisrkw3keeZtyhuTh8r35CP5c/PbFWyFTNGjtFKuUA4WTC+oK3hbGFt4uEi9SFrUM99m/ur5IwuCFny9kLBQuLCz2Lh4WfHgIr9FuxYji1MXdy4xXVK6ZHhp8NJ9y2jLspb9UOJYUlXyannc8o5Sg9KlpUMrglc0lamUycturvRauWMVYZVkVe9ql9VbVn8qF5VfrHCsqK74sEa45uJXTl/VfPV5bdra3kq3yu3rSOuk626s91m/r0q9akHV0IbwDa0b8Y3lG19tSt50oXpq9Y7NtM3KzQM1YTXtW8y2rNvyoTaj9nqdf13LVv2tq7e+2Sba1r/dd3vzDoMdFTve75TsvLUreFdrvUV99W7S7oLdjxpiG7q/5n7duEd3T8Wej3ulewf2Re/ranRvbNyvv7+yCW1SNo0eSDpw5ZuAb9qb7Zp3tXBaKg7CQeXBJ9+mfHvjUOihzsPcw83fmX+39QjrSHkr0jq/dawto22gPaG97+iMo50dXh1Hvrf/fu8x42N1xzWPV56gnSg98fnkgpPjp2Snnp1OPz3Umdx590z8mWtdUV29Z0PPnj8XdO5Mt1/3yfPe549d8Lxw9CL3Ytslt0utPa49R35w/eFIr1tv62X3y+1XPK509E3rO9Hv03/6asDVc9f41y5dn3m978bsG7duJt0cuCW69fh29u0XdwruTNxdeo94r/y+2v3qB/oP6n+0/rFlwG3g+GDAYM/DWQ/vDgmHnv6U/9OH4dJHzEfVI0YjjY+dHx8bDRq98mTOk+GnsqcTz8p+Vv9563Or59/94vtLz1j82PAL+YvPv655qfNy76uprzrHI8cfvM55PfGm/K3O233vuO+638e9H5ko/ED+UPPR+mPHp9BP9z7nfP78L/eE8/sl0p8zAAAAIGNIUk0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAAAAjSURBVHjaYmDADupxCf7HJfgfl+B/XIL/cQn+BwAAAP//AwAsNxHsU0OZ6gAAAABJRU5ErkJggg==);
    background-repeat: no-repeat;
}
</style>

