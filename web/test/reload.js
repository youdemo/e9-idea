
var bxje = "#field53603";//报销金额
var qxje = "#field53606";//付款金额
var cjzje = "#field53580";//冲预付款金额
var fphm_dt1 = "#field53935_";//明细1 发票号码
var sfcf_dt1 = "#field53632_";//明细1 是否重复
var fkjeyb = "#field53601";//付款金额原币
var htwfje = "#field53588";//合同未付金额
var cgddh = "#field53611";//采购订单号
var cgddlc = "#field53612";//采购订单流程

var sfhtkz = "#field53590";  //是否合同控制
var bcfue = "#field53601";  //付款金额原币
var htwfe = "#field53588";   //合同未付金额
var cgddh_dt1 = "#field63241_";//采购订单号-明细1
var fpyzh = "#field154221";//发票预制号
var bcfue_dt1 = "#field53627_";  //付款金额原币-明细1
var htwfe_dt1 = "#field62868_";   //合同未付金额-明细1
var sfhtkz_dt1 = "#field62861_";  //是否合同控制-明细

var gysdm = "#field53576";        //供应商代码
var vendor1 = "#field53594" ;   // 供应商


jQuery(document).ready(function () {

   // var gysdm_val = jQuery(gysdm ).val();
   //showJc(gysdm_val );

   jQuery(vendor1 ).bindPropertyChange(function() {

      var gysdm_val = jQuery(gysdm ).val();
      alert("======="+gysdm_val );
      //if (jQuery(gysdm ).length > 0) {
      showJc(gysdm_val );
      //	}

   });


   jQuery("[name=addbutton0]").css('display', 'none');
   jQuery("[name=delbutton0]").css('display', 'none');
   jQuery("[name=copybutton0]").css('display', 'none');
   jQuery("[name=copybutton3]").css('display', 'none');
   // jQuery(cgddh).bindPropertyChange(function (){
   WfForm.bindFieldChangeEvent("field53611", function (obj, id, value) {
      var cgddh_val = value;
      var nodesnum0 = jQuery("#nodesnum0").val();
      if (nodesnum0 > 0) {
         // jQuery("[name = check_node_0]:checkbox").attr("checked", true);
         // adddelid(0,fphm_dt1)
         WfForm.delDetailRow("detail_1", "all");
         // removeRow0(0);
      }
      cgddh_val = cgddh_val.replace(/\|\|/g, ',');
      if (cgddh_val != "") {
         dodetail(cgddh_val);
         var rqids = "";
         var rqnames = "";
         var flag = "";
         var result = getRQID(cgddh_val, 'PO02');
         // var result = "test###2607069";
         var text_arr = result.split("@@@");
         var strv = "";//拼接字符串
         if (text_arr.length > 1) {
            var length_dt = Number(text_arr.length);
            for (var i = 0; i < length_dt - 1; i++) {
               var tmp_arr = text_arr[i].split("###");
               rqids = rqids + flag + tmp_arr[1];
               strv = strv + flag + "{id:\"" + tmp_arr[1] + "\",name:\"" + tmp_arr[0] + "\"}";
               flag = ",";
            }

         }
      }
      var aaa = "{value: \"" + rqids + "\",specialobj:[" + strv + "]}";
      var stt = eval('(' + aaa + ')');
      WfForm.changeFieldValue("field53612", stt);
      addDetail1Row(rqids);
   })

   checkCustomize = function () {
      alert("laile");
      var gysdm_val = jQuery(gysdm ).val();
      // var zzgys_jc_val = jQuery(zzgys_jc).val();
//alert(gysdm_val +"=="+zzgys_jc_val );

      if(gysdm_val !="V3200"){

         alert("集采供应商必填");
         return false;

      }
      //订单中间表金额管控
      var cgddh = "field63241_";//采购订单号
      var ddzje = "field488721_";//订单总金额
      var dddjje = "field488722_";//订单冻结金额
      var ddyfje = "field488723_";//订单已付金额
      var bcfkje = "field53627_";//本次付款金额
      var indexnum0 = jQuery("#indexnum0").val();//获取明细总行数，包含删除数
      for (var i = 0; i < indexnum0; i++) {
         if (jQuery("#" + cgddh + i).length > 0) {//跳过被删除的行
            var ddzje_val = jQuery("#" + ddzje + i).val();
            var dddjje_val = jQuery("#" + dddjje + i).val();
            var ddyfje_val = jQuery("#" + ddyfje + i).val();
            var bcfkje_val = jQuery("#" + bcfkje + i).val();
            if (ddzje_val != "" && ddzje_val > 0) {//订单中间表中存在该订单数据
               if (Number(ddzje_val) < (Number(dddjje_val) + Number(ddyfje_val) + Number(bcfkje_val))) {//订单总金额 < (订单冻结金额+订单已付金额+本次付款金额)
                  Dialog.alert("明细表中本次付款金额>订单可付款金额，请检查！");
                  return false;
               }
            }
         }
      }

      // add by shaw 2018-08-23 start
      // 校验供应商是否一致
      var vendor = '#field53594' // 供应商
      var contactVendor = '#field53582' // 合同供应商
      var vendorVal = jQuery(vendor).val() // 浏览按钮取span的text
      var contactVendorVal = jQuery(contactVendor).val() // 单行文本取value
      //alert(vendorVal+"  --  "+contactVendorVal );
      // Dialog.alert('ids=' + ids)
      var fpyzh_val = jQuery(fpyzh).val();
      if (fpyzh_val != '1') {
         Dialog.alert('发票预制号' + fpyzh_val + '已报销过，请勿重复报销！');
      }
      if (vendorVal !== '' && contactVendorVal !== '') {
         if (vendorVal !== contactVendorVal) {
            Dialog.alert('选择的供应商与合同供应商不一致，请检查');
            return false;
         }
      }
      // add by shaw 2018-08-23 end
      var bxje_val = jQuery(bxje).val();
      var qxje_val = jQuery(qxje).val();
      var cjzje_val = jQuery(cjzje).val();
      var fkjeyb_val = jQuery(fkjeyb).val();
      var htwfje_val = jQuery(htwfje).val();
      var bxrid = "";
      if (bxje_val == "") {
         bxje_val = "0";
      }
      if (qxje_val == "") {
         qxje_val = "0";
      }
      if (cjzje_val == "") {
         cjzje_val = "0";
      }
      if (fkjeyb_val == "") {
         fkjeyb_val = "0";
      }
      if (htwfje_val == "") {
         htwfje_val = "0";
      }
      if (jQuery(sfhtkz).val() == "0") {
         if (Number(fkjeyb_val) > Number(htwfje_val)) {
            window.top.Dialog.alert("本次付款额（原币）不得超过合同未付金额,请检查");
            return false;
         }
      }
      //财务张世权，祝经理提出由于质保款的存在，取消此管控
      /*if(accAdd(Number(qxje_val),Number(cjzje_val))!=Number(bxje_val)){
       window.top.Dialog.alert("报销金额不等于付款金额加冲预付款金额,请检查");
       return false;
       }*/
      var indexnum0 = jQuery("#indexnum0").val();
      var countnum = 0;
      var countnum1 = 0;
      for (var index = 0; index < indexnum0; index++) {
         if (jQuery(fphm_dt1 + index).length > 0) {
            countnum = countnum + 1;
            var fphm_dt1_val = jQuery(fphm_dt1 + index).val();
            var sfcf_dt1_val = jQuery(sfcf_dt1 + index).val();
            if (fphm_dt1_val != "" && sfcf_dt1_val != "") {
               Dialog.alert("第" + countnum + "行明细的发票号码（" + fphm_dt1_val + "）已在流程编号为（" + sfcf_dt1_val + "）的流程中使用，请检查");
               return false;
            }
         }
         if (jQuery(sfhtkz_dt1 + index).length > 0) {
            countnum1 = countnum1 + 1;
            var sfhtkz_dt1_val = jQuery(sfhtkz_dt1 + index).val();
            if (sfhtkz_dt1_val == "0") {
               var htwfe_dt1_val = jQuery(htwfe_dt1 + index).val();
               var bcfue_dt1_val = jQuery(bcfue_dt1 + index).val();
               if (Number(htwfe_dt1_val) < Number(bcfue_dt1_val)) {
                  Dialog.alert("第" + countnum1 + "行明细的本次付款额原币大于合同未付金额！");
                  return false;
               }
            }
         }
      }
      return true;
   }

})
function getRQID(cgddhs, type) {
   var result = "";
   $.ajax({
      type: "POST",
      url: "/gvo/costcontrol/getrqinfo.jsp",
      data: {'cgddhs': cgddhs, 'type': type},
      dataType: "text",
      async: false,//同步   true异步
      success: function (data) {
         data = data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
         result = data;
      }
   });
   return result;
}
function accAdd(arg1, arg2) {
   var r1, r2, m;
   try {
      r1 = arg1.toString().split(".")[1].length
   } catch (e) {
      r1 = 0
   }
   try {
      r2 = arg2.toString().split(".")[1].length
   } catch (e) {
      r2 = 0
   }
   m = Math.pow(10, Math.max(r1, r2))
   return (arg1 * m + arg2 * m) / m
}

function dodetail(cgddh_val) {
   var indexnum1 = jQuery("#indexnum0").val();
   var cgddh_array = cgddh_val.split(",");
   var length1 = Number(cgddh_array.length) + Number(indexnum1);
   for (var i = indexnum1; i < length1; i++) {
      WfForm.addDetailRow("detail_1", {field63241: {value: cgddh_array[i - indexnum1]}});
      // addRow0(0);
      // jQuery(cgddh_dt1+i).val();
      jQuery(cgddh_dt1 + i).attr("readonly", "readonly");
   }
}

function showJc(code){
   if(code!="V3200"){
      jQuery("#flag1").show();
      WfForm.changeFieldAttr("field818431", 3);
   }else{
      jQuery("#flag1").hide();
      WfForm.changeFieldAttr("field818431", 2);
   }
}



















































