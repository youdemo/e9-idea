
var jg_dt1 = WfForm.convertFieldNameToId("jg", "detail_1");//jg
var sfsc1_dt1 = WfForm.convertFieldNameToId("sfsc1", "detail_1");//sfsc1
jQuery(document).ready(function(){
    WfForm.bindDetailFieldChangeEvent(jg_dt1,function(id,rowIndex,value){
        WfForm.changeFieldValue(sfsc1_dt1+'_'+rowIndex, {value:""});

    });
});