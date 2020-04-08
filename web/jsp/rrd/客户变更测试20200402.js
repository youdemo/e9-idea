<script>
var fplx = WfForm.convertFieldNameToId("fplx");	//发票类型
var khmczfxy = WfForm.convertFieldNameToId("khmczfxy");	//客户名称重复校验
var nsrsbhzfxy = WfForm.convertFieldNameToId("nsrsbhzfxy");	//纳税人识别号重复校验
var khqz = WfForm.convertFieldNameToId("khqz");	//客户群组
var xjkhqznickname = WfForm.convertFieldNameToId("xjkhqznickname");	//新建客户群组（nickname）
var xjkhqzrealname = WfForm.convertFieldNameToId("xjkhqzrealname");	//新建客户群组（realname）
var ywx = "field7527";//业务线
var xs2 = "field7517";//销售2
var xskhjl = "field7518";//销售负责人/客户经理
var aejl = "field7519";//AE经理
var esjl = "field7520";//ES经理
var hyzfl = "field7498";//行业子分类
var szqy = "field7499";//所在区域
var khsq2 = "field7571";//客户授权2
var xs3 = "field7521";//销售3
var xsfzr3 = "field7522";//销售负责人3
var csrjl = "field7523";//CSR经理
var khss3 = "field7572";//客户授权3
var xs1 = "field7514";//销售1
var xskhjl1 = "field7515";//销售负责人/客户经理1
var csrjl1 = "field7516";//CSR经理 1
var khsh1 = "field7570";//客户授权1
var sfsyjt = "field7524";//是否属于OEM/ODM集团
var oemdomjt = "field7553";//OEM/ODM集团
var sfxyzf = "field7496";//是否需要支付
var mgxs1 = "field7543";//美国销售1
var mgxs2 = "field7544";//美国销售2
var mgxs3 = "field7545";//美国销售3
var mgxs4 = "field7546";//美国销售4
var fzpd = "field7842";//辅助判断 realnam   nickname
var sfxykhqz = "field7832";//是否现有客户群组
var xjkhqzr = "field7485";//新建客户群组realname
var xjkhqzn = "field7486";//新建客户群组nickname
var khqz = "field7500";//客户群组
var khqzdm = "field7849";//客户群组代码
//提交控制
WfForm.registerCheckEvent(WfForm.OPER_SUBMIT, function(callback){
    var v_fplx = WfForm.getFieldValue(fplx);
    var v_khmczfxy = WfForm.getFieldValue(khmczfxy);
    var v_nsrsbhzfxy = WfForm.getFieldValue(nsrsbhzfxy);
    var khqz_val = WfForm.getFieldValue(khqz);
    var xjkhqznickname_val = WfForm.getFieldValue(xjkhqznickname);
    var xjkhqzrealname_val = WfForm.getFieldValue(xjkhqzrealname);
    if(xjkhqznickname_val !="" || xjkhqzrealname_val !=""){
        if(khqz_val !=""){
            WfForm.showMessage("客户群组和新建客户群组不能同时有值!",1,2);
            return;
        }
    }
    if(Number(v_khmczfxy)>0){
        //WfForm.showMessage("客户名称已存在，请确认!");
        //return;
    }
    if(v_fplx=="0"&&Number(v_nsrsbhzfxy)>0){
        //WfForm.showMessage("纳税人识别号已存在，请确认!");
        //return;
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

