
        //var pc = "field530856_"; //批次
    var pcgl = "field530863_"; //批次管理
    var xmh = "field530854_"; //项目id
    //var wlbhmx = "#field545221_"; //明细的物料编号field530866_  -- field545221_
    //var wlbhid = "field530866_";
    var kcdd_dt = "#field545222_";//库存地点-明细
    var kwdd = "#field530929";//库位地点

    jQuery(document).ready(function() {
        jQuery("#field530868_browserbtn").hide();
		jQuery("[name=copybutton0]").css('display','none');
        jQuery(".icon-workflow-form-sap.detailBtn").hide();
        //changedt1();批次改为由SAP带出，顾不需要此控制
        checkKCDD();
        jQuery("[name=delbutton0]").live("click",
        function() {
            var nodesnum = jQuery("#nodesnum0").val();
              checkKCDD();
        });

       jQuery("[name=addbutton0]").live("click",
        function() {
                 fu();
        	dodetail0();
           checkKCDD();
        }); 
    });

    function changedt1() {
        var indexnum0 = jQuery("#indexnum0").val();
        for (var index = 0; index < indexnum0; index++) {
            var pcgl_val = jQuery("#" + pcgl + index).val();
            var pc1 = jQuery("#" + pc + index).val();
            var pcid = pc + index;
            if (pcgl_val == "X") {
				WfForm.changeFieldAttr(pcid, 3);        
            } else {
                WfForm.changeFieldAttr(pcid, 2);
            }
        }
        setTimeout("changedt1()", 500);
    }
	

	function dodetail0(){
		var indexnum0 =jQuery("#indexnum0").val();
		var ywlx = "#field530846"; //业务类型
		var ywlx_val=jQuery(ywlx).val();
		if(ywlx_val==1){
			for( var index=0;index<indexnum0;index++){
				bindchangesqsl(index);	
			}			
		}		
	}	


	function bindchangesqsl	(index){
		var  sqsl="#field530855_";//申请数量
		var  ycsj="#field530895_";//数据预存
		jQuery(sqsl+index).bind('change',function() {	
			 var  sqsl_val=jQuery(sqsl+index).val();
			 var ycsj_val=jQuery(ycsj+index).val();
			 if(sqsl_val>ycsj_val){
				 alert("修改申请数量不能大于原来的申请的数量！");
				 jQuery(sqsl+index).val(ycsj_val);
			 }			 
		 });		
	}

function  fu(){
		
		var  index0 =jQuery("#indexnum0").val();
		var  ycsj="#field530895_";//数据预存
		var  sqsl="#field530855_";//申请数量		
		for(var i=0;i<index0;i++){
		var  sqsl_val=jQuery(sqsl+i).val();
			//alert("fu---"+sqsl_val);
			jQuery(ycsj+i).val(sqsl_val);					
		}			
	}
function checkKCDD(){
     var indexnum0_val = jQuery("#indexnum0").val();
     var bz = "0";
     for(var j=0;j<indexnum0_val;j++){
       (function(){
       var kcdd_dt_1= kcdd_dt+j;
       if(jQuery(kcdd_dt_1).length>0){
             bz="1";
       }
       jQuery(kcdd_dt_1).bindPropertyChange(function () {
             var  kcdd_dt_1_val=jQuery(kcdd_dt_1).val();
             var flag = "0";
             for(var i=0;i<indexnum0_val;i++){
                  if(jQuery(kcdd_dt+i).length<=0){
                         continue;
                  }
                  var  kcdd_dt_val=jQuery(kcdd_dt+i).val();
                  if(kcdd_dt_val !='' && kcdd_dt_1_val !='' && kcdd_dt_val != kcdd_dt_1_val){
                        Dialog.alert("本次选择的库存地点与已存在的不同，请重新选择!");
                        jQuery(kcdd_dt_1).val("");
                        jQuery(kcdd_dt_1+"span").html("");
                        return;
                  }else if(kcdd_dt_1_val != ""){
                       jQuery(kwdd).val(kcdd_dt_1_val);
                 }
                if(kcdd_dt_val !=''){
                    flag ="1";
                }
             }
             if(flag =="0"){
                  jQuery(kwdd).val("");
             }
       });
      })();
     }
     if(bz=="0"){
          jQuery(kwdd).val("");
     }
}

	var zzkmbh = "field530880_";//总账科目编号
	var wlbh = "field545221_";//物料编号
	var wlms = "field530861_";//物料描述
	var jldw = "field530862_";//计量单位
	var pcgl = "field530863_";//批次管理
	var yldj = "field530858_";//预留单价
       //var cbzxid = "#field530927";//成本中心ID
       var cbzx = "#field542791";//成本中心field530872--  field542791
       var cbzxbm = "#field530937";//成本中心编码
       var fycd = "#field530926";//费用承担
       var ndsyys = "#field530923_";//年度剩余预算
       var yskm = "#field530921_";//预算科目
       var ysssqj = "#field530845";//申请日期
       var zje = "#field530924_";//总金额
       var ndkyje = "#field530923_";//年度可用金额
       var gc_v = "#field530864";//工厂
       var reqnum = "#field530855_";   //申请数量
       var avablenum = "#field530889_";  //批次可用数量
       var wlbhmx = "#field545221_"; //明细的物料编号

jQuery(document).ready(function() {
	setTimeout('getbind()', 1000);

	jQuery("button[name=addbutton0]").live("click",function(){
        var i=jQuery("#indexnum0").val()-1;
		//Dialog.alert(i);
		  jQuery("#"+zzkmbh+i).attr("readonly", "readonly");
                  jQuery(ndsyys+i).attr("readonly", "readonly");
                 jQuery("#"+wlbh+i).bindPropertyChange(function(){			
			bindchange(i);
                 });	
	});	
	
	checkCustomize = function() {
		var indexnum = jQuery("#indexnum0").val();
                var cbzxbm_val =jQuery(cbzxbm).val();
                var cbzx_val =jQuery(cbzx).val();
                var fycd_val = jQuery(fycd).val();
                var gc_v_val = jQuery(gc_v).val();
    		 var countnum=0;
                if(cbzxbm_val !=cbzx_val ){
                     Dialog.alert("费用承担与成本中心不符合，请核实！");
                     return false;
                }
                if(gc_v_val =='G001' || gc_v_val =='G099' ||gc_v_val =='2201' ){
                     Dialog.alert("国显和显示中心的部门领料无法走此流程，请知悉！");
                     return false;
                 }

//////////////////////////////////////////////
var index = 0;
var indexnum0_val = jQuery("#indexnum0").val();
if (Number(indexnum0_val) > 0) {
	for ( var i = 0; i < indexnum0_val; i++) {
		var wlbhmxi = wlbhmx + i;  //物料编号
                var reqnumi = reqnum + i; //申请数量
                var avablenumi = avablenum + i;//批次可用数量
                
		if (jQuery(wlbhmxi).length > 0) {// 明细行不能为空
			index++;
			var reqnumi_val = jQuery(reqnumi).val();// 申请数量值
                        var avablenumi_val = jQuery(avablenumi).val();// 批次可用数量值
                        
			if (reqnumi_val.trim().length > 0) {// 申请数量值不能为空
                        //alert("22222222222::" + reqnumi_val + ":::"+ avablenumi_val);
				if (parseFloat(reqnumi_val) > parseFloat(avablenumi_val) ) {// 如果申请数量大于批次可用数量
                                        //alert("3333344::" + reqnumi_val + ":::"+ avablenumi_val);
					Dialog.alert("明细第" + index + "“申请数量”大于“批次可用数量”：" + avablenumi_val);
					return false;
				}
			} 
		}

	}
}
/////////////////////////////////////////////

    		 for(var index=0; index<indexnum;index++){
    		 	 	 countnum=countnum+1;
    		 	 	 var ysssqj_dt1_val=jQuery(ysssqj).val().substring(0,4);
  		 	 	 var cbzx_dt1_val=jQuery(fycd).val();
    		 	 	 var km_dt1_val=jQuery(yskm+index).val();
    		 	 	 var sqje_dt1_val=jQuery(zje+index).val();
    		 	 	  var ndkyje_dt1_val=jQuery(ndkyje+index).val();
    		 	 	  if(sqje_dt1_val == ''){
    		 	 	      sqje_dt1_val='0';	  
    		 	 	  }
    		 	 	  if(ndkyje_dt1_val == ''){
    		 	 	      ndkyje_dt1_val='0';	  
    		 	 	  }
    		 	 	  if(ysssqj_dt1_val!='' && cbzx_dt1_val !='' && sqje_dt1_val !='') {
    		 	 	  var checkresult=checkyearmoney(cbzx_dt1_val,km_dt1_val,ysssqj_dt1_val,index+1,sqje_dt1_val,ndkyje_dt1_val);
    		 	 	  if(checkresult == '1'){
    		 	 	  	  Dialog.alert("【年度强控】第"+countnum+"行明细当前申请金额大于年度可用预算，请检查");
                                          return false;
    		 	             }
    		 	    	 }
               }
		for (var j=0;j<indexnum;j++){
			var zzkmbh_v = jQuery("#"+zzkmbh+j).val().indexOf("异常");
            var wlbh_v = jQuery("#"+wlbh+j).val();
            if(Number(zzkmbh_v)>= 0){
				Dialog.alert("物料编号:"+wlbh_v+" 的总账科目编号异常，请联系财务!");
				j=indexnum;
				return false;
			}				
		}

		return true;
	}
});
	function bindchange(i){
		var zzkmbh_val = jQuery("#"+zzkmbh+i).val().indexOf("异常");
        var wlbh_val = jQuery("#"+wlbh+i).val();
		//Dialog.alert(zzkmbh_val);
	    if (Number(zzkmbh_val)>= 0){
			Dialog.alert("物料编号:"+wlbh_val+" 的总账科目编号异常，请联系财务!");
			jQuery("#"+wlbh+i).val("");
			document.all(wlbh+i+"span").innerHTML = "";
			
			jQuery("#"+zzkmbh+i).val("");
			document.all(zzkmbh+i+"span").innerHTML = "";
			
			jQuery("#"+wlms+i).val("");
			document.all(wlms+i+"span").innerHTML = "";
			
			jQuery("#"+jldw+i).val("");
			document.all(jldw+i+"span").innerHTML = "";
			
			jQuery("#"+pcgl+i).val("");
			document.all(pcgl+i+"span").innerHTML = "";
			
			jQuery("#"+yldj+i).val("");
			document.all(yldj+i+"span").innerHTML = "";
		}
	}
        function getbind(){
	       var indexnum = jQuery("#indexnum0").val();
	       if(Number(indexnum)>0){
		     for(var i=0;i<indexnum;i++){
			  (function(){
				 var a=i;
                                  jQuery("#"+zzkmbh+a).attr("readonly", "readonly");
                                  jQuery(ndsyys+i).attr("readonly", "readonly");
				   jQuery("#"+wlbh+i).bindPropertyChange(function(){
					bindchange(a);
				});	
			})();
		    }
	       }
         }
 function checkyearmoney(cbzx,fykm,year,startindex,checkval,ckeckallval){
   	   var indexnum0 = jQuery("#indexnum0").val();
     	  var values=checkval;
     	  for (var index2= startindex; index2 < indexnum0; index2++) {   
     	  	    if (jQuery(zje+ index2).length > 0) {
     	  	    	         var ysssqj_dt1_val=jQuery(ysssqj).val().substring(0,4);
    		 	 	 var cbzx_dt1_val=jQuery(fycd).val();
    		 	 	 var km_dt1_val=jQuery(yskm+index2).val();
    		 	 	 var sqje_dt1_val=jQuery(zje+index2).val();
    		 	 	  if(sqje_dt1_val == ''){
    		 	 	      sqje_dt1_val='0';	  
    		 	 	  }
     			if(cbzx_dt1_val==cbzx && km_dt1_val==fykm && ysssqj_dt1_val==year){
     					values=accAdd(values,sqje_dt1_val);
     					
     		      }
     	  	   }
     	  }
     	  if(Number(values)>Number(ckeckallval)){
 	 	 return "1";	
 	  }
 	  return "0";
   }

 function accAdd(arg1,arg2){ 
    var r1,r2,m; try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
    m=Math.pow(10,Math.max(r1,r2)) 
    return (arg1*m+arg2*m)/m 
}






















