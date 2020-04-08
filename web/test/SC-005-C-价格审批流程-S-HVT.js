
   var lclx="#field35091";//流程类型
   var ybwl = "#field54149";//是否一般物料
   var lclx_old="";	
   var mxb1="#mxb1";//明细1
   var mxb11="#mxb11";//明细1空白行
   var mxb2="#mxb2";//明细2
   var mxb22="#mxb22";//明细2空白行
   var mxb3="#mxb3";//明细3
   var mxb33="#mxb33";//明细3空白行
   var mxb4="#mxb4";//明细4
   var mxb44="#mxb44";//明细4空白行
   var butt1="#butt1";//明细1button 
   var butt2="#butt2";//明细2button 
   var butt3="#butt3";//明细3button
   var butt4="#butt4";//明细4button 
   var xxjlbh_dt1="field35142_";//明细1-信息记录编号
   var gc_dt1 = "field35096_";//明细1-工厂
   var cgzz_dt1 = "field35141_";//明细1-采购组织
    var xxjlxx_dt1 = "field35098_";//明细1-信息记录类型
    var gysbh_dt1 = "field35099_";//明细1-供应商编号
    var wlbh_dt1 = "field35106_";//明细1-物料编号
    var jj_dt1 = "field35116_";//明细1-净价
    var hh_dt1 = "field35094_";//明细1-行号
    var cgz_dt1 = "field35206_";//明细1-采购组 
    var oaid_dt1 =   "field35167_";//明细1-oaid
    var bb_dt1 = "field35117_";//明细1-币别
    var jgjs_dt1 = "field35118_";//明细1-价格基数
    var dw_dt1= "field35157_";//明细1-单位
    var jbdw_dt1= "field36286_";//明细1-基本单位
    var dwfz_dt1= "field35109_";//明细1-单位分子
    var dwfm_dt1= "field35110_";//明细1-单位分母
       
    var gjmytk_dt1= "field36304_";//明细1-国际贸易条款
    var gjmytj_dt1= "field36305_";//明细1-国际贸易条件
    var sfjtj_dt1 = "field36287_";//明细1-是否阶梯价
    var jtyjsl_dt1 = "field36288_";//明细1-阶梯一级数量
    var yjdj_dt1 = "field36289_";//明细1-一级单价
    var jtejsl_dt1 = "field36290_";//明细1-阶梯二级数量
    var ejdj_dt1 = "field36291_";//明细1-二级单价
    	
     var xxjlbh_dt2="field35143_";//明细2-信息记录编号
    var mc_dt2 = "field35128_";//明细2-名称
    var oaid_dt2 = "field35168_";//明细2-oaid
     var bb_dt2 = "field35130_";//明细2-币别
    var jgjs_dt2= "field35131_";//明细2-价格基数
    var dw_dt2= "field35132_";//明细2-单位  
    	 
    var sfjtj_dt2 = "field35134_";//明细2-是否阶梯价
    var jtyjsl_dt2 = "field36296_";//明细2-阶梯一级数量
    var yjdj_dt2 = "field36297_";//明细2-一级单价
    var jtejsl_dt2 = "field36298_";//明细2-阶梯二级数量
    var ejdj_dt2= "field36299_";//明细2-二级单价
    	 
    var wzxxjl_dt4 = "field35154_";//明细4-完整信息记录
    var cgzzsj_dt4 = "field35155_";//明细4-完整信息记录
   var oaid_dt4 = "field35181_";//明细4-完整信息记录
    	
    var xxhlbhall = "#field35306";//信息记录编号all
    var num_main="#field35453";//
   jQuery(document).ready(function(){
	   jQuery("[name=copybutton0]").css('display','none');
	   jQuery("[name=copybutton1]").css('display','none');
	   jQuery("[name=copybutton3]").css('display','none');
         Dialog.alert("如明细信息中的报价单及成本分析附件未显示，请勿点击保存按钮！");
        setTimeout("setInit();",1000);
        var indexnum0 = jQuery("#indexnum0").val();
 if ("0" == jQuery(ybwl).val()) {//如果是一般物料		
	for ( var index = 0; index < indexnum0; index++) {
		if (jQuery("#" + jgjs_dt1+ index).length > 0) {
			//setMust(jgjs_dt1 + index);//价格基数设置必输
			//setMust(dwfz_dt1 + index);//单位分子设置必输
                        //setMust(dwfm_dt1 + index);//单位分母设置必输
		}
	}
  }
///////////////////////////////////////////////

  	 var lclx_val = jQuery(lclx).val();
  	 if(lclx_val !=''){
  	     lclx_old = lclx_val;
	     jQuery(lclx+"_browserbtn").attr('disabled',true);
	 }
	 showhideButton();
	 dodetail0();
	 dodetail1();
	 dodetail3();
	  jQuery(lclx).bindPropertyChange(function () {
	     jQuery(lclx+"_browserbtn").attr('disabled',true);
	     	 var lclx_val = WfForm.getFieldValue(lclx.slice(1));
	     	  if(lclx_old==''){
                	lclx_old = lclx_val; 
              }else{
               jQuery(lclx).val(lclx_old);
                jQuery(lclx+"span").text(lclx_old);	  
            }
            showhideButton();
         })		
       
        jQuery("[name=addbutton1]").live("click", function () {
	  	   	dodetail1();
	 });	
         jQuery("[name=addbutton0]").live("click", function () {
 	   var indexnum00 = jQuery("#indexnum0").val();
 	   	 var num_main_val = jQuery(num_main).val();
 	     var lclx_val = jQuery(lclx).val();
 	     if(num_main_val==""){
 	       num_main_val=1;	 
 	     }else{
 	     	 num_main_val=Number(num_main_val)+1;
 	    }
 	      var index00= indexnum00-1;
 	      jQuery("#"+oaid_dt1+index00).val(num_main_val);
 	     jQuery(num_main).val(num_main_val);
 	     	dodetail0();
 });
        jQuery("[name=addbutton3]").live("click", function () {
 	   var indexnum33 = jQuery("#indexnum3").val();
 	     var lclx_val = jQuery(lclx).val();
 	     	 var num_main_val = jQuery(num_main).val();
 	     	  if(num_main_val==""){
 	       num_main_val=1;	 
 	     }else{
 	     	 num_main_val=Number(num_main_val)+1;
 	    }
 	      if(lclx_val == '3'){
 	      	 var index33= indexnum33-1;
 	      	 jQuery("#"+oaid_dt4+index33).val(num_main_val);
 	      	   jQuery(num_main).val(num_main_val);
 	      }
 	      
 });
        checkCustomize = function () {
 
        	var indexnum1 = jQuery("#indexnum1").val();
        	 for(var index=0;index<indexnum1;index++){
        	   	 if (jQuery("#"+oaid_dt2 + index).length > 0) {
        	   	 	 var oaid_dt2_val= jQuery("#"+oaid_dt2 + index).val();
        	   	 	var result= checkexist(oaid_dt2_val);
        	   	 	if(result == "0"){
        	   	 		 alert("明细2中oaid:"+oaid_dt2_val+" 在明细1中不存在请检查");	
        	   	 		 return false;
        	   	      }
        	       }
        	 }
        	 return true;
        }
   }) 
function _customAddFun0(addIndexStr){     
    dodetail0();
}
function _customAddFun1(addIndexStr){     
    setTimeout('dodetail1()',2000);
}
function _customAddFun3(addIndexStr){     
    dodetail3();
}
   	   
    function  checkexist(oaid_dt2_val){
    		var indexnum0 = jQuery("#indexnum0").val();
        	 for(var index=0;index<indexnum0;index++){
        	   	 if (jQuery("#"+oaid_dt1 + index).length > 0) {
        	   	 	 var oaid_dt1_val= jQuery("#"+oaid_dt1 + index).val();
        	   	 	 if(oaid_dt1_val == oaid_dt2_val){
        	   	 	   return "1";	 
        	   	       }
        	       }
        	 }
    	return "0";
    }
    function dodetail0(){
       var indexnum0 =jQuery("#indexnum0").val();
       var lclx_val = jQuery(lclx).val();
       //var num_dt1=1;
        for( var index=0;index<indexnum0;index++){
            if( jQuery("#"+xxjlbh_dt1+index).length>0){
            	 if(lclx_val == '1'){
            	       jQuery("#"+xxjlbh_dt1+index+"_browserbtn").attr('disabled',true);
					   WfForm.changeFieldAttr(gc_dt1+index,3);
					   WfForm.changeFieldAttr(cgzz_dt1+index,3);
					   WfForm.changeFieldAttr(xxjlxx_dt1+index,3);
					   WfForm.changeFieldAttr(gysbh_dt1+index,3);
					   WfForm.changeFieldAttr(wlbh_dt1+index,3);
					   WfForm.changeFieldAttr(jj_dt1+index,3);
            	       jQuery("#"+oaid_dt1+index).attr("readonly", "readonly");
            	        //jQuery("#"+oaid_dt1+index).val(num_dt1);
            	        //num_dt1=num_dt1+1;
            	        jQuery("#"+jbdw_dt1+index).attr("readonly", "readonly");
            	       bindchangedw(index);
            	       bindchangefzfm(index);
            	 }
            	  if(lclx_val == '2'){
					  WfForm.changeFieldAttr(xxjlbh_dt1+index,3);
            	  	  jQuery("#"+gc_dt1+index+"_browserbtn").attr('disabled',true);
            	  	  jQuery("#"+cgzz_dt1+index+"_browserbtn").attr('disabled',true);
            	  	  jQuery("#"+xxjlxx_dt1+index+"_browserbtn").attr('disabled',true);
            	  	  jQuery("#"+gysbh_dt1+index+"_browserbtn").attr('disabled',true);
            	  	  jQuery("#"+wlbh_dt1+index+"_browserbtn").attr('disabled',true);
            	  	 //jQuery("#"+cgz_dt1+index+"_browserbtn").attr('disabled',true);
            	  	 //jQuery("#"+jj_dt1+index).attr("readonly", "readonly");
            	  	  jQuery("#"+oaid_dt1+index).attr("readonly", "readonly");
            	  	  bindchange(index);
            	  	  bindchangefzfm(index);
            	  	  
            	  }
            	  
            }
            
        }
   }
   function bindchangefzfm(index){
   	      jQuery("#"+dwfz_dt1+index).bindPropertyChange(function () {
   	    	var dwfz_dt1_val =WfForm.getFieldValue(dwfz_dt1+index);
   	    	 if(dwfz_dt1_val == '' || dwfz_dt1_val =='0'){
   	    	 	 jQuery("#"+dwfz_dt1+index).val('1');
   	    	}
   	    })
   	    jQuery("#"+dwfm_dt1+index).bindPropertyChange(function () {
   	    	var dwfm_dt1_val = WfForm.getFieldValue(dwfm_dt1+index);
   	    	 if(dwfm_dt1_val == '' || dwfm_dt1_val =='0'){
   	    	 	 jQuery("#"+dwfm_dt1+index).val('1');
   	    	}
   	    })
   	   jQuery("#"+gjmytk_dt1+index).bindPropertyChange(function () {
   	    	var gjmytk_dt1_val = WfForm.getFieldValue(gjmytk_dt1+index);
   	    	 if(gjmytk_dt1_val != '' ){
				 WfForm.changeFieldAttr(gjmytj_dt1+index,3);
   	    	}
   	    })
   	  

   	     jQuery("#"+sfjtj_dt1+index).bindPropertyChange(function () {
   	    	var sfjtj_dt1_val = WfForm.getFieldValue(sfjtj_dt1+index);
   	    	 if(sfjtj_dt1_val != '' ){
				WfForm.changeFieldAttr(jtyjsl_dt1+index,3);
				WfForm.changeFieldAttr(yjdj_dt1+index,3);
				WfForm.changeFieldAttr(jtejsl_dt1+index,3);
				WfForm.changeFieldAttr(ejdj_dt1+index,3);
   	    	}else{
			 WfForm.changeFieldAttr(jtyjsl_dt1+index,2);
			 WfForm.changeFieldAttr(yjdj_dt1+index,2);
			 WfForm.changeFieldAttr(jtejsl_dt1+index,2);
			 WfForm.changeFieldAttr(ejdj_dt1+index,2);
   	       }
   	    }) 
   }
   function bindchangedw(index){
   	   jQuery("#"+jbdw_dt1+index).bindPropertyChange(function () {
   	         var jbdw_dt1_val= WfForm.getFieldValue(jbdw_dt1+index);
   	         var dw_dt1_val= jQuery("#"+dw_dt1+index).val();
   	             if(dw_dt1_val == ''){
   	         jQuery("#"+dw_dt1+index).val(jbdw_dt1_val);
   	          jQuery("#"+dw_dt1+index+"span").text(jbdw_dt1_val);
   	 }
   	          if(jQuery("#"+dw_dt1+index).val()!= ''){
   	          	   jQuery("#"+dw_dt1+index+"spanimg").html("");
   	         }
   	    })
   	    
   }
   function bindchange(index){
   	    jQuery("#"+xxjlbh_dt1+index).bindPropertyChange(function () {
   	    	setTimeout('setxxjlbhall()',1000);
   	    })
  }
  function setxxjlbhall(){
  	  var flag1="(";
  	  var flag2="";
  	  var xxhlbhall_val="";
  	   jQuery(xxhlbhall).val("");
  	   var indexnum0 =jQuery("#indexnum0").val();
  	    for( var index=0;index<indexnum0;index++){
  	       if( jQuery("#"+xxjlbh_dt1+index).length>0){
  	            var context1="";
  	            var xxjlbh_dt1_val= jQuery("#"+xxjlbh_dt1+index).val();
  	            var gc_dt1_val = jQuery("#"+gc_dt1+index).val();
  	            var cgzz_dt1_val= jQuery("#"+cgzz_dt1+index).val();
  	            var xxjlxx_dt1_val = jQuery("#"+xxjlxx_dt1+index).val();
  	             var gysbh_dt1_val = jQuery("#"+gysbh_dt1+index).val();
  	              var wlbh_dt1_val = jQuery("#"+wlbh_dt1+index).val();
  	             if(xxjlbh_dt1_val !=""){
  	             	 context1 = xxjlbh_dt1_val+"&"+gc_dt1_val+"&"+cgzz_dt1_val+"&"+xxjlxx_dt1_val+"&"+gysbh_dt1_val+"&"+wlbh_dt1_val;
  	            }
  	            if(context1 !=""){
  	               xxhlbhall_val=	xxhlbhall_val+flag1+flag2+"#"+context1+"#";
  	               flag1="";
  	               flag2=",";
  	            }
  	       }
  	    }
  	    if(xxhlbhall_val !=""){
  	    	xxhlbhall_val = xxhlbhall_val + ")";
  	    }
  	    jQuery(xxhlbhall).val(xxhlbhall_val);
  }
    function dodetail1(){
       var indexnum1=jQuery("#indexnum1").val();
       var lclx_val = jQuery(lclx).val();
        for( var index=0;index<indexnum1;index++){
            if( jQuery("#"+xxjlbh_dt2+index).length>0){
            	 if(lclx_val == '1'){
            	       jQuery("#"+xxjlbh_dt2+index+"_browserbtn").attr('disabled',true);
            	        jQuery("#"+mc_dt2+index).attr("readonly", "readonly");
						WfForm.changeFieldAttr(oaid_dt2+index,3);
            	        bindchange2(index);
            	        bindchangejt2(index);
            	 }
            	  if(lclx_val == '2'){
            	  	  jQuery("#"+xxjlbh_dt2+index+"_browserbtn").attr('disabled',true);
            	  	   jQuery("#"+mc_dt2+index).attr("readonly", "readonly");
            	  	 
            	  	   var xxjlbh_dt2_val=jQuery("#"+xxjlbh_dt2+index).val();
            	  	   if(xxjlbh_dt2_val !=""){
            	  	   jQuery("#"+oaid_dt2+index).attr("readonly", "readonly");
            	  	   var oaid1_val=getoaid1(xxjlbh_dt2_val);
            	  	   jQuery("#"+oaid_dt2+index).val(oaid1_val);
            	         }else{
            	         bindchange2(index);
						 WfForm.changeFieldAttr(oaid_dt2+index,3);
            	        }
            	    	var sfjtj_dt2_val = jQuery("#"+sfjtj_dt2+index).val();
   	    	 if(sfjtj_dt2_val != '' ){
				WfForm.changeFieldAttr(jtyjsl_dt2+index,3);
				WfForm.changeFieldAttr(yjdj_dt2+index,3);
				WfForm.changeFieldAttr(jtejsl_dt2+index,3);
				WfForm.changeFieldAttr(ejdj_dt2+index,3);
   	    	}
            	         bindchangejt2(index);
            	  }
            }
        }
   }   
   function bindchangejt2(index){
	jQuery("#"+sfjtj_dt2+index).bindPropertyChange(function () {
   	    	var sfjtj_dt2_val = WfForm.getFieldValue(sfjtj_dt2+index);
   	    	 if(sfjtj_dt2_val != '' ){
				WfForm.changeFieldAttr(jtyjsl_dt2+index,3);
				WfForm.changeFieldAttr(yjdj_dt2+index,3);
				WfForm.changeFieldAttr(jtejsl_dt2+index,3);
				WfForm.changeFieldAttr(ejdj_dt2+index,3);
   	    	}else{
			 WfForm.changeFieldAttr(jtyjsl_dt2+index,2);
			 WfForm.changeFieldAttr(yjdj_dt2+index,2);
			 WfForm.changeFieldAttr(jtejsl_dt2+index,2);
			 WfForm.changeFieldAttr(ejdj_dt2+index,2);
   	       }
   	    }) 
   }
   function bindchange2(index1){
   	     jQuery("#"+oaid_dt2+index1).bind("change",function () {
   	         var oaid_dt2_val=jQuery("#"+oaid_dt2+index1).val();
   	             var bb_dt2_val = "";
                   var jgjs_dt2_val= "";
                    var dw_dt2_val= "";
                     var indexnum0=jQuery("#indexnum0").val();
                     for( var index=0;index<indexnum0;index++){
                     	  if( jQuery("#"+oaid_dt1+index).length>0){
                     	  	  var oaid_dt1_val =  jQuery("#"+oaid_dt1+index).val();
                     	  	  if(oaid_dt1_val==oaid_dt2_val){
                     	  	  	  bb_dt2_val= jQuery("#"+bb_dt1+index).val();
                     	  	  	  jgjs_dt2_val= jQuery("#"+jgjs_dt1+index).val();
                     	  	  	  dw_dt2_val= jQuery("#"+dw_dt1+index).val();
                     	  	  	  break;
                     	         }
                     	  }
                     }
                     jQuery("#"+bb_dt2+index1).val(bb_dt2_val);
                      jQuery("#"+bb_dt2+index1+"span").text(bb_dt2_val);
                      jQuery("#"+jgjs_dt2+index1).val(jgjs_dt2_val);
                       jQuery("#"+dw_dt2+index1).val(dw_dt2_val);
                       jQuery("#"+dw_dt2+index1+"span").text(dw_dt2_val);
   	    })
    }
   function getoaid1(xxjlbh_dt2_val){
   	    var indexnum0=jQuery("#indexnum0").val();
   	      for( var index=0;index<indexnum0;index++){
   	        if( jQuery("#"+xxjlbh_dt1+index).length>0){
   	           var xxjlbh_dt1_val =  jQuery("#"+xxjlbh_dt1+index).val();
   	           if(xxjlbh_dt2_val==xxjlbh_dt1_val){
   	           	   var oaid_dt1_val =  jQuery("#"+oaid_dt1+index).val();
   	           	   return oaid_dt1_val;
   	           }
   	         }  
   	      }
   }
   function dodetail3(){
       var indexnum3=jQuery("#indexnum3").val();
       var lclx_val = jQuery(lclx).val();
        for( var index=0;index<indexnum3;index++){
            if( jQuery("#"+oaid_dt4+index).length>0){
            	 if(lclx_val == '3'){          	     
            	  	   jQuery("#"+oaid_dt4+index).attr("readonly", "readonly");
            	  }
            }
        }
   }    		 		
      
   function showhideButton(){
    	var lclx_val = jQuery(lclx).val();
    	if(lclx_val == ''){
        	jQuery(butt1).hide();
 	      jQuery(butt2).hide();
 	      jQuery(butt4).hide();		
       }
    	if(lclx_val == '1' || lclx_val=='2'){
    		jQuery(mxb4).hide();
    		jQuery(mxb44).hide();
    		jQuery(butt1).show();
 	 	jQuery(butt2).show();	
      }
      if(lclx_val == '3' ){
    		jQuery(mxb1).hide();
    		jQuery(mxb11).hide();
    		jQuery(mxb2).hide();
    		jQuery(mxb22).hide();
    		jQuery(butt4).show();	
      }
      if(lclx_val == '1' ){
      	 jQuery("[name=sapmulbutton1]").css('display','none');
      }
       //if(lclx_val == '2' ){
      //	 jQuery("button[name=addbutton1]").css('display','none');
//}
    }
    



function setInit(){
    var indexnum0 = jQuery("#indexnum0").val();
    for(var i=0;i<indexnum0;i++){
        var tempid = "#field498725_"+i;
        if(jQuery(tempid).length>0){
            var temp_Val=jQuery(tempid).val();
            if(temp_Val == 'QUOTE'){
                jQuery(tempid).parent().parent().hide();
            }
        }
    }
}





