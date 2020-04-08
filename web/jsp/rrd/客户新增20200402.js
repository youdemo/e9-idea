
var fplx = WfForm.convertFieldNameToId("fplx");	//发票类型
var khmczfxy = WfForm.convertFieldNameToId("khmczfxy");	//客户名称重复校验
var nsrsbhzfxy = WfForm.convertFieldNameToId("nsrsbhzfxy");	//纳税人识别号重复校验
var khqz = WfForm.convertFieldNameToId("khqz");	//客户群组
var xjkhqznickname = WfForm.convertFieldNameToId("xjkhqznickname");	//新建客户群组（nickname）
var xjkhqzrealname = WfForm.convertFieldNameToId("xjkhqzrealname");	//新建客户群组（realname）
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
        WfForm.showMessage("客户名称已存在，请确认!");
        return;
    }
    if(v_fplx=="0"&&Number(v_nsrsbhzfxy)>0){
        WfForm.showMessage("纳税人识别号已存在，请确认!");
        return;
    }
    callback();
});
jQuery(document).ready(function() {
    setTimeout("showhide()",300);
    WfForm.bindFieldChangeEvent(khqz, function(obj,id,value) {
        showhide();
    })
})
    function showhide(){
      var khqz_val = WfForm.getFieldValue(khqz);
      if(khqz_val == ""){
          WfForm.changeFieldAttr(xjkhqznickname, 3);
          WfForm.changeFieldAttr(xjkhqzrealname, 3);
      }else{
          WfForm.changeFieldAttr(xjkhqznickname, 2);
          WfForm.changeFieldAttr(xjkhqzrealname, 2);
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

