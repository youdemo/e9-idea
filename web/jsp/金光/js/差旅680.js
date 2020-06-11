var projectType = WfForm.convertFieldNameToId("IncPro");//项目类型
var APLUS = WfForm.convertFieldNameToId("APLUS");//系统属性

showhidesm680();

function showhidesm680(){
    var projectType_val = WfForm.getFieldValue(projectType);
    var APLUS_val = WfForm.getFieldValue(APLUS);
    if(projectType_val == "1"){
        if(APLUS_val == "1"){

            jQuery("#prj666").hide();

        }

    }
}