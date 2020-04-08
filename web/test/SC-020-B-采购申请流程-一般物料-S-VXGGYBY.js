
<!-- script代码，如果需要引用js文件，请使用与HTML中相同的方式。 -->
<script type="text/javascript">
var  cgsqlx="field34226";//采购申请类型
var  glbmjl="field34229";//管理部门经理
var gc="field34764";//工厂
var  fycdfb  ="#field34790";//费用承担分部

var kmflm_dt1="field34667_";//明细1-科目分类码
var gdzclx_dt1="field34668_";//明细1-固定资产类型
var wlbh_dt1 ="field149721_";//明细1-物料编号V3
var wlms_dt1 = "field34670_";//明细1-物料描述
// var cgdw_dt1 = "field34700_";//明细1-采购单位
var cgdw_dt1 = "field170721_";//明细1-采购单位V3
var cbzx_dt1 ="field34730_";//明细1-成本中心
var fykm_dt1 ="field34732_";//明细1-费用科目
var wlz_dt1 ="field34748_";//明细1-物料组
//   var kcdd_dt1="field34750_";//明细1-库存地点
var kcdd_dt1="field170724_";//明细1-库存地点v3
var bgr_dt1="field34758_";//明细1-保管人
var bgbm_dt1="field34760_";//明细1-保管部门
var nbdd_dt1="field34728_";//明细1-研发项目订单号
var cgsqlx_old="";
var ysje="field36589_";//明细1-预算金额
var yyje="field36590_";//明细1-已用金额
var kyje="field36591_";//明细1-可用金额
var ndkyje="field39488_";//明细1-年度可用金额
var gc_dt1="field36608_";//明细1-工厂
var zcmc_dt1 ="field36666_";//明细1-资产名称
var xmbh_dt1 = "field42007_";//明细1-项目编号
var je_dt1="field34727_";//明细1-金额
var cbzxspzje_dt1="field37087_";//明细1-成本中心审批中金额
var zcspzje_dt1 = "field37088_";//明细1-资产审批中金额
var chrq_dt1= "field34806_";//明细1-出货日期
var cgggs_dt1= "#field51938_";//明细1-采购规格书
var glggs_dt1= "field51939_";//明细1-关联规格书
var glfw_dt1 = "#field42351_";//明细1-管理范围
var gyd_dt1 = "#field51937_";//明细1-工艺段
var wlgkfw ="field54851";//物料管控范围
var gyd = "field54852";//工艺段
var cgsl_dt1="#field34699_";//数量采购数量
jQuery(document).ready(function(){
	alert("123");
	alert(je_dt1.replace("_",""));
	jQuery(".excelDetailContent").find("#uploadFile").html("<input type=button class=\"ant-btn ant-btn-primary\" style=\"height:100%\"  value=\"明细批量导入\" onclick=\"showUpPage()\;\" >");
	jQuery("[name=copybutton0]").css('display','none');
	checkrule();
	var cgsqlx_val= jQuery("#"+cgsqlx).val();
	if(cgsqlx_val !=''){
		cgsqlx_old = cgsqlx_val;
		WfForm.changeFieldAttr(cgsqlx, 1);
	}else{
		jQuery("[name=addbutton0]").css('display','none');
	}
	showhide();
	dodetail(cgsqlx_val);
	jQuery("#"+cgsqlx).bindPropertyChange(function () {
		showhide();
		addremovecheck();
		WfForm.changeFieldAttr(cgsqlx, 1);
		//jQuery("#"+cgsqlx+"_browserbtn").attr('disabled',true);
		jQuery("[name=addbutton0]").css('display','');
		var cgsqlx_val=WfForm.getFieldValue(cgsqlx);
		if(cgsqlx_old==''){
			cgsqlx_old = cgsqlx_val;
		}else{

			jQuery("#"+cgsqlx).val(cgsqlx_old);
			jQuery("#"+cgsqlx+"span").text(cgsqlx_old);
		}
	})
	WfForm.registerAction(WfForm.ACTION_ADDROW+"1", function(index){
		var cgsqlx_val= jQuery("#"+cgsqlx).val();
		if(cgsqlx_val =='PR01' ){
			$(".gdzclx_dt").hide();
			$(".kmflm_dt").hide();
			$(".cbzx_dt").hide();
			$(".fykm_dt").hide();
			$(".bg_dt").hide();
			$(".zcmc_dt").hide();
			$(".xmbh_dt").hide();
			$(".ys_dt").hide();
			$(".ndys_dt").hide();
			$(".nb_dt").hide();


		}
		if(cgsqlx_val =='PR02' ){
			$(".wlbh_dt").hide();
			$(".wlmsfj_dt").hide();
			$(".kcall_dt").hide();
			//$(".cbzx_dt").hide();
			$(".fykm_dt").hide();
			$(".bg_dt").hide();
			$(".ndys_dt").hide();
			$(".nb_dt").hide();
		}
		if(cgsqlx_val =='PR06' ){
			$(".gdzclx_dt").hide();
			$(".kcall_dt").hide();
			$(".zcmc_dt").hide();
			$(".xmbh_dt").hide();
			$(".ys_dt").hide();
			$(".ndys_dt").hide();
		}

		if(cgsqlx_val =='PR04' ){
			$(".gdzclx_dt").hide();
			$(".kcall_dt").hide();
			$(".zcmc_dt").hide();
			$(".xmbh_dt").hide();
			$(".nb_dt").hide();
		}


		dodetail(cgsqlx_val);
	});

	jQuery("[name=addbutton0]").live("click", function () {
		var cgsqlx_val= jQuery("#"+cgsqlx).val();
		dodetail(cgsqlx_val);
		var indexnum0_val = jQuery("#indexnum0").val();
		for(var index1 = 0;index1 < indexnum0_val;index1 ++){
			var cgggsdt1 = cgggs_dt1+index1;
			var glggsdt1 = glggs_dt1+index1;
			jQuery(cgggsdt1 ).bindPropertyChange(function () {
				var  cgggs_dt1_val = WfForm.getFieldValue(cgggsdt1.slice(1));
				if(cgggs_dt1_val == "是" ){
					WfForm.changeFieldAttr(glggsdt1, 3);


				}else{
					WfForm.changeFieldAttr(glggsdt1, 2);
				}
			});
			var glfwdt1 = glfw_dt1+index1;
			var gyddt1 =gyd_dt1 +index1;
			jQuery(glfwdt1).bindPropertyChange(function () {
				var glfwdt1_val =  WfForm.getFieldValue(glfwdt1.slice(1));
				if(glfwdt1_val =='集团化'){
					WfForm.changeFieldValue(wlgkfw, {value:"0"});

				}else if(glfwdt1_val =='现地化'){
					var flag1=0;
					for(var index2 = 0;index2 < indexnum0_val;index2 ++){
						var glfwdt1_val1 = jQuery(glfw_dt1+index2).val();
						if(glfwdt1_val1=='集团化'){
							flag1=1;
						}
					}
					if(flag1 ==0){
						WfForm.changeFieldValue(wlgkfw, {value:"1"});
					}
				}else{//$(wlgkfw).get(0).selectedIndex='';
				}
			});
			jQuery(gyddt1).bindPropertyChange(function () {
				var tempi ='';
				for(var ii = indexnum0_val-1;ii>= 0;ii--){
					tempi = jQuery(gyd_dt1 +ii).val();
					if(tempi !=''){
						break;
					}
				}
				if(tempi =='模组'){
					WfForm.changeFieldValue(gyd, {value:"0"});
				}else if(tempi =='屏体'){
					WfForm.changeFieldValue(gyd, {value:"1"});
				}else{
					WfForm.changeFieldValue(gyd, {value:""});
				}
			});
		}
	});

	jQuery("[name=delbutton0]").live("click", function () {
		checkrule();
	});

	checkCustomize = function () {

		var fycdfb_val=jQuery(fycdfb).val();
		if(fycdfb_val == "24" || fycdfb_val =="101" || fycdfb_val =="43"){
			window.top.Dialog.alert("费用承担分部不能为维信诺昆山公司或显示中心！");
			return false;
		}
		//增加采购数量为0禁止提交;
		var indexnum00 = jQuery("#indexnum0").val();
		var countnumm=0;
		for(var index=0;index<indexnum00;index++){

			if( jQuery(cgsl_dt1+index).val()=="0" ||  jQuery(cgsl_dt1+index).val()==''  ||  jQuery(cgsl_dt1+index).val()=="0.000"){
				countnumm=countnumm+1;
				alert("第"+(countnumm)+"行采购数量为0，请重新输入");
				return false;
			}
		}
		var cgsqlx_val= jQuery("#"+cgsqlx).val();
		var gc2_val= jQuery("#"+gc).val();
		if((cgsqlx_val=='PR02'||cgsqlx_val=='PR04') && (gc2_val!=2201)){
			var indexnum0 = jQuery("#indexnum0").val();
			var countnum=0;
			for(var index=0;index<indexnum0;index++){
				if (jQuery("#"+je_dt1 + index).length > 0) {
					countnum=countnum+1;
					var je_dt1_val=jQuery("#"+je_dt1 + index).val();
					if(je_dt1_val==''){
						je_dt1_val="0";
					}
					var kyje_val=jQuery("#"+kyje + index).val();
					if(kyje_val==''){
						kyje_val="0";
					}
					var cbzxspzje_dt1_val=jQuery("#"+cbzxspzje_dt1 + index).val();
					var zcspzje_dt1_val=jQuery("#"+zcspzje_dt1 + index).val();
					var sykyje="";
					if(cgsqlx_val=='PR02'){
						sykyje=accSub(kyje_val,zcspzje_dt1_val);
					}else{
						sykyje=accSub(kyje_val,cbzxspzje_dt1_val);
					}

					var cbzx_dt1_val = jQuery("#"+cbzx_dt1+ index).val();
					var fykm_dt1_val = jQuery("#"+fykm_dt1 + index).val();
					var chrq_dt1_val = jQuery("#"+chrq_dt1 + index).val().substring(0,7);
					var zcmc_dt1_val = jQuery("#"+zcmc_dt1+ index).val();
					var context="";
					if(cgsqlx_val=='PR02'){
						context=checkcfdata2(zcmc_dt1_val,index+1,je_dt1_val,sykyje);
						if(context=="1"){
							alert("第"+countnum+"行明细采购金额大于预算可用金额");
							return false;

						}
					}else{
						context=checkcfdata1(cbzx_dt1_val,fykm_dt1_val,chrq_dt1_val,index+1,je_dt1_val,sykyje);
						if(context=="1"){
							if(confirm("第"+countnum+"行明细采购金额大于预算可用金额")){
							}else{
								return false;
							}
						}
					}

				}
			}
			if(cgsqlx_val=='PR04'){
				countnum=0;
				for(var index=0;index<indexnum0;index++){
					if (jQuery("#"+je_dt1 + index).length > 0) {
						countnum=countnum+1;
						var je_dt1_val=jQuery("#"+je_dt1 + index).val();
						if(je_dt1_val==''){
							je_dt1_val="0";
						}
						var kyje_val=jQuery("#"+ndkyje + index).val();
						if(kyje_val==''){
							kyje_val="0";
						}
						var cbzxspzje_dt1_val=jQuery("#"+cbzxspzje_dt1 + index).val();
						var zcspzje_dt1_val=jQuery("#"+zcspzje_dt1 + index).val();
						var sykyje="";
						if(cgsqlx_val=='PR02'){
							sykyje=accSub(kyje_val,zcspzje_dt1_val);
						}else{
							sykyje=accSub(kyje_val,cbzxspzje_dt1_val);
						}

						var cbzx_dt1_val = jQuery("#"+cbzx_dt1+ index).val();
						var fykm_dt1_val = jQuery("#"+fykm_dt1 + index).val();
						var chrq_dt1_val = jQuery("#"+chrq_dt1 + index).val().substring(0,7);
						var zcmc_dt1_val = jQuery("#"+zcmc_dt1+ index).val();
						var context="";
						if(cgsqlx_val=='PR02'){
							context=checkcfdata2(zcmc_dt1_val,index+1,je_dt1_val,sykyje);
						}else{
							context=checkcfdata1(cbzx_dt1_val,fykm_dt1_val,chrq_dt1_val,index+1,je_dt1_val,sykyje);
						}
						if(context=="1"){
							alert("第"+countnum+"行明细采购金额大于年度可用金额,请查看")
							return false;

						}
					}
				}
			}
		}
		return true;
	}
})
function checkcfdata1(cbzx,fykm,chrq,startindex,checkval,ckeckallval){
	var indexnum0 = jQuery("#indexnum0").val();
	var values=checkval;
	for (var index2= startindex; index2 < indexnum0; index2++) {
		if (jQuery("#"+je_dt1 + index2).length > 0) {
			var je_dt1_val = jQuery("#"+je_dt1 + index2).val();
			var cbzx_dt1_val = jQuery("#"+cbzx_dt1+ index2).val();
			var fykm_dt1_val = jQuery("#"+fykm_dt1 + index2).val();
			var chrq_dt1_val = jQuery("#"+chrq_dt1 + index2).val().substring(0,7);
			if(je_dt1_val == ''){
				je_dt1_val = "0";
			}
			if(cbzx_dt1_val==cbzx&&fykm_dt1_val==fykm&&chrq_dt1_val==chrq){
				values=accAdd(values,je_dt1_val);

			}
		}
	}
	if(Number(values)>Number(ckeckallval)){
		return "1";
	}
	return "0";
}

function checkcfdata2(zcmc,startindex,checkval,ckeckallval){
	var indexnum0 = jQuery("#indexnum0").val();
	var values=checkval;
	for (var index2= startindex; index2 < indexnum0; index2++) {
		if (jQuery("#"+je_dt1 + index2).length > 0) {
			var je_dt1_val = jQuery("#"+je_dt1 + index2).val();
			var zcmc_dt1_val = jQuery("#"+zcmc_dt1+ index2).val();
			if(je_dt1_val == ''){
				je_dt1_val = "0";
			}
			if(zcmc_dt1_val==zcmc){
				values=accAdd(values,je_dt1_val);

			}
		}
	}
	if(Number(values)>Number(ckeckallval)){
		return "1";
	}
	return "0";
}
function dodetail(cgsqlx_val){
	var indexnum0 =jQuery("#indexnum0").val();
	var gc_val=jQuery("#"+gc).val();
	for( var index=0;index<indexnum0;index++){
		if(     jQuery("#"+kmflm_dt1+index).length>0){
			var gc_dt1_val=jQuery("#"+gc_dt1+index).val();
			if(gc_dt1_val==""){
				jQuery("#"+gc_dt1+index).val(gc_val);
				jQuery("#"+gc_dt1+index+"span").text(gc_val);
			}
			jQuery("#"+ysje+index).attr("readonly", "readonly");
			jQuery("#"+yyje+index).attr("readonly", "readonly");
			jQuery("#"+kyje+index).attr("readonly", "readonly");
			jQuery("#"+ndkyje+index).attr("readonly", "readonly");
			jQuery("#"+xmbh_dt1+index).attr("readonly", "readonly");
			if(cgsqlx_val =='PR02' ){
				jQuery("#"+kmflm_dt1+index).val("A")
				jQuery("#"+kmflm_dt1+index+"span").text("A");
				jQuery("#"+wlz_dt1+index).val("Z002")
				jQuery("#"+wlz_dt1+index+"span").text("Z002");
				addcheck(gdzclx_dt1+index,'0');
				addcheck(zcmc_dt1+index,'1');
				addcheck(cbzx_dt1+index,'1');
			}else if(cgsqlx_val =='PR04' ){
				jQuery("#"+kmflm_dt1+index).val("K")
				jQuery("#"+kmflm_dt1+index+"span").text("K");
				addcheck(cbzx_dt1+index,'1');
				addcheck(fykm_dt1+index,'1');
				addcheck(bgr_dt1+index,'1');
				addcheck(bgbm_dt1+index,'1');
				jQuery("#"+wlz_dt1+index).val("Z001")
				jQuery("#"+wlz_dt1+index+"span").text("Z001");
			}else if(cgsqlx_val =='PR01' ){
				addcheck(wlbh_dt1+index,'1');
				addcheck(kcdd_dt1+index,'1');
				WfForm.changeFieldAttr(wlms_dt1+index, 1);
				WfForm.changeFieldAttr(cgdw_dt1+index, 1);
				//jQuery("#"+wlms_dt1+index).attr("readonly", "readonly");
				//jQuery("#"+cgdw_dt1+index+"_browserbtn").attr('disabled',true);
			}else if(cgsqlx_val =='PR06' ){
				jQuery("#"+kmflm_dt1+index).val("F")
				jQuery("#"+kmflm_dt1+index+"span").text("F");
				addcheck(cbzx_dt1+index,'1');
				addcheck(fykm_dt1+index,'1');
				addcheck(nbdd_dt1+index,'1');
				jQuery("#"+wlz_dt1+index).val("Z006")
				jQuery("#"+wlz_dt1+index+"span").text("Z006");
				addcheck(bgr_dt1+index,'1');
				addcheck(bgbm_dt1+index,'1');
			}
		}
	}
}
function showhide(){
	var cgsqlx_val= jQuery("#"+cgsqlx).val();
	if(cgsqlx_val !='PR02' ){
		jQuery("#gdzcxs0").hide();
		jQuery("#gdzcxs1").hide();
		jQuery("#gdzcxs5").hide();
		jQuery("#pgwj0").show();
	}else{
		jQuery("#gdzcxs0").show();
		jQuery("#gdzcxs1").show();
		jQuery("#gdzcxs5").show();
		jQuery("#pgwj0").hide();
	}
	if(cgsqlx_val !='PR01' ){
		jQuery("#ybwlxs0").hide();
	}else{
		jQuery("#ybwlxs0").show();
	}
	if(cgsqlx_val !='PR04' ){
		jQuery("#fylxs0").hide();
		jQuery("#fylxs1").hide();
		jQuery("#fylxs2").hide();
	}else{
		jQuery("#fylxs0").show();
		jQuery("#fylxs1").show();
		jQuery("#fylxs2").show();
	}
	if(cgsqlx_val !='PR06' ){
		jQuery("#xmlxs0").hide();
	}else{
		jQuery("#xmlxs0").show();
	}
	if(cgsqlx_val =='PR01' ){
		$(".gdzclx_dt").hide();
		$(".kmflm_dt").hide();
		$(".cbzx_dt").hide();
		$(".fykm_dt").hide();
		$(".bg_dt").hide();
		$(".zcmc_dt").hide();
		$(".xmbh_dt").hide();
		$(".ys_dt").hide();
		$(".ndys_dt").hide();
	}

	if(cgsqlx_val =='PR02' ){
		$(".wlbh_dt").hide();
		$(".wlmsfj_dt").hide();
		$(".kcall_dt").hide();
		//$(".cbzx_dt").hide();
		$(".fykm_dt").hide();
		$(".bg_dt").hide();
		$(".ndys_dt").hide();
	}
	if(cgsqlx_val =='PR06' ){
		$(".gdzclx_dt").hide();
		$(".kcall_dt").hide();
		$(".zcmc_dt").hide();
		$(".xmbh_dt").hide();
		$(".ys_dt").hide();
		$(".ndys_dt").hide();
	}
	if(cgsqlx_val =='PR04' ){
		$(".gdzclx_dt").hide();
		$(".kcall_dt").hide();
		$(".zcmc_dt").hide();
		$(".xmbh_dt").hide();
	}
}

function addremovecheck(){
	var cgsqlx_val= jQuery("#"+cgsqlx).val();
	if(cgsqlx_val == 'PR02'){
		addcheck(glbmjl,'1');
	}else{
		removecheck(glbmjl,'1');
	}
}

function addcheck(btid,flag){
	var btid_val = jQuery("#"+btid).val();
	var btid_check=btid;
	WfForm.changeFieldAttr(btid_check, 3);
}

function removecheck(btid,flag){
	var btid_check=btid;
	WfForm.changeFieldAttr(btid_check, 2);
}
function accAdd(arg1,arg2){
	var r1,r2,m; try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2))
	return (arg1*m+arg2*m)/m
}
function accSub(arg1,arg2){
	var r1,r2,m,n;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2));
	//动态控制精度长度
	n=(r1>=r2)?r1:r2;
	return ((arg1*m-arg2*m)/m).toFixed(n);
}

function checkrule(){
	var indexnum0_val = jQuery("#indexnum0").val();
	for(var index1 = 0;index1 < indexnum0_val;index1 ++){

		var glfwdt1 = glfw_dt1+index1;
		var gyddt1 =gyd_dt1 +index1;
		var temp1 ='';
		var temp2 ='';
		for(var ii = indexnum0_val-1;ii>= 0;ii--){
			var tempv= jQuery(glfw_dt1+ii).val();
			if(tempv=='集团化'){
				temp1=tempv ;
			}
			if(tempv=='现地化'){
				temp2 =tempv;
			}
		}
		if(temp1=='集团化'){
			WfForm.changeFieldValue(wlgkfw, {value:"0"});
		}else if(temp2 =='现地化'){
			WfForm.changeFieldValue(wlgkfw, {value:"1"});
		}else{
			WfForm.changeFieldValue(wlgkfw, {value:""});
		}
		var tempi ='';
		for(var ii = indexnum0_val-1;ii>= 0;ii--){
			tempi = jQuery(gyd_dt1 +ii).val();
			if(tempi !='' && tempi !=undefined){
				break;
			}
		}
		if(tempi =='模组'){
			WfForm.changeFieldValue(gyd, {value:"0"});
		}else if(tempi =='屏体'){
			WfForm.changeFieldValue(gyd, {value:"1"});
		}else{
			WfForm.changeFieldValue(gyd, {value:""});
		}
	}

	for(var index1 = 0;index1 < indexnum0_val;index1 ++){

		var glfwdt1 = glfw_dt1+index1;
		var gyddt1 =gyd_dt1 +index1;
		jQuery(glfwdt1).bindPropertyChange(function () {
			var temp1 ='';
			var temp2 ='';
			for(var ii = indexnum0_val-1;ii>= 0;ii--){
				var tempv= jQuery(glfw_dt1+ii).val();
				if(tempv=='集团化'){
					temp1=tempv ;
				}
				if(tempv=='现地化'){
					temp2 =tempv;
				}
			}
			if(temp1=='集团化'){
				WfForm.changeFieldValue(wlgkfw, {value:"0"});
			}else if(temp2 =='现地化'){
				WfForm.changeFieldValue(wlgkfw, {value:"1"});
			}else{
				WfForm.changeFieldValue(wlgkfw, {value:""});
			}

		});
		jQuery(gyddt1).bindPropertyChange(function () {
			var tempi ='';
			for(var ii = indexnum0_val-1;ii>= 0;ii--){
				tempi = jQuery(gyd_dt1 +ii).val();
				if(tempi !='' && tempi !=undefined){
					break;
				}
			}
			if(tempi =='模组'){
				WfForm.changeFieldValue(gyd, {value:"0"});
			}else if(tempi =='屏体'){
				WfForm.changeFieldValue(gyd, {value:"1"});
			}else{
				WfForm.changeFieldValue(gyd, {value:""});
			}
		});
	}
}


function showUpPage(){
	dialog = new window.top.Dialog();
	dialog.currentWindow = window;
	var url = "/gvo/materialUpload/materialUpload.jsp";
	dialog.Title = "明细批量导入";
	dialog.Width = 500;
	dialog.Height =300;
	dialog.Drag = true;
	dialog.Model = true;
	dialog.maxiumnable = true;
	dialog.URL = url;
	dialog.show();
}
function doinputdata(index0){
	var indexnum0 =jQuery("#indexnum0").val();
	for(var index=index0;index<indexnum0;index++){
		WfForm.triggerFieldAllLinkage("field34754_"+index);
		WfForm.triggerFieldAllLinkage("field34755_"+index;
	}
}
function showDetailInfo(resulto){
	var indexnum1 =jQuery("#indexnum0").val();
	if(indexnum1 == '' || indexnum1 == null){
		indexnum1 ="0";
	}
	var nodesnum0 = jQuery("#nodesnum0").val();
	if(nodesnum0 >0){
		WfForm.delDetailRow("detail_1", "all");
	}
	var res =resulto;
	for(var i in res){
		if(i =='remove'){
			continue;
		}
		var data = res[i];

		addRow0(0);
		var addObj = {};
		var price_val = "";
		var purchasenum_val = "";
		for(var j in data){
			var indexId = Number(i)+Number(indexnum1);
			var fieldid = data[j].fieldid.replace("_","");
			var fieldvalue = data[j].fieldvalue;
			var fieldspanvalue = data[j].fieldvalue2;
			if(fieldid  =='field34705' ){
				price_val = fieldvalue;
			}else if(fieldid  =='field34699' ){
				price_val = fieldvalue;
			}
			if(fieldid=='field34667'||fieldid=='field149721'||fieldid=='field170721'||fieldid=='field34754'||fieldid=='field34755'||fieldid=='field36608'||fieldid=='field170724'||fieldid=='field51941'||fieldid=='field51939'){
				addObj[fieldid] = {
					value: fieldvalue,
					specialobj:[
						{id:fieldvalue,name:fieldspanvalue}
					]
				};
			}else{

				addObj[fieldid] = {value:fieldvalue};
			}

		}
		if(price_val ==""){
			price_val ="0";
		}
		if(purchasenum_val ==""){
			purchasenum_val ="0";
		}
		var totalJe =accMul(je1,je2);
		var field_je_dt1=je_dt1.replace("_","");
		addObj[field_je_dt1] = {value:totalJe};
		WfForm.addDetailRow("detail_1", addObj);
	}

	setTimeout("doinputdata('"+indexnum1 +"');", 1000);
	Dialog.alert("数据导入成功");
	/*  var newindexnum =jQuery("#indexnum0").val();
      for(var x=0;x<newindexnum ;x++){
          var ygdj_dt1_id = "#field34705_"+x;
          var fycdr_dt1_id = "field34754_"+x;
          var fycdbm_dt1_id = "field34755_"+x;
          if(jQuery(ygdj_dt1_id).length>0){
              jQuery(ygdj_dt1_id).focus();
             datainputd(fycdr_dt1_id);
             datainputd(fycdbm_dt1_id);
          }
      }*/
}

function accMul(arg1,arg2){
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){}
	try{m+=s2.split(".")[1].length}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
