
    jQuery(document).ready(function() {
        var highfxqu="field63401";//高风险(危险作业项目)作业区域
        var firequ="field63403"; //动火作业
        var diaozhqu="field63404"; //吊装作业
        var highqu="field63405"; //高处作业
        var closequ="field63406"; //密闭空间作业
        var dangerqu="field63407"; //危险管路阀门作业
        var xiankaiqu="field63497";//掀开区域
        var jikengqu="field63498";//基坑区域


        var shigquyu = "field63481";//施工区域
        var gaszcquyu ="field63400";//气体侦测系统
        var shishiStart = "#field63352";//施工开始日期
        var shishiEnd = "#field63353";//施工结束日期
        var baitian= "field212721";//baitian
        var yewan= "field212722";//night

        var sgsc="#field144223";//施工时长
        var sgsc_isyewan="#field144224";//判断是否超过17:30;

//=========特殊作业  需上传附件。======================
        var dianhan="field63444";//电焊 选项;
        var yahan="field63445";//氩焊
        var qigehan="field63446";//气切割


        var qizhong1="field63452";
        var qizhong2="field63453";
        var qizhong3="field63454";
        var qizhong4="field63457";

//===============================
        var filedianhan="field63451";//所需动火作业 -附件;
        var filediao="field63479";// 吊装-附件1;
        var filediao2="field63509";// 吊装-附件2;
        var filediao3="field63510";// 吊装-附件3;
        var fileshigong="field63480";//施工密闭空间作业所需-附件;

//==============
        var isdian="field63388";  //是否用电
        var isdianFou="field63389";  //是否用电-否
        var filedian="field63514";  //用电申请表

        setTimeout(checkSGQY,1000);
        setTimeout(checkGFX,1000);
        setTimeout(checkGAS,1000);
        setTimeout(checkFile4GFX,1000);

// //setTimeout('checkFile4GFX();',1000);

//===========附件检查=======================
        jQuery("#"+isdian).bind("click",function(){
            checkFile4GFX();
        });

        jQuery("#"+dianhan).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+yahan).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+qigehan).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+qizhong1).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+qizhong2).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+qizhong3).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+qizhong4).bind("click",function(){
            checkFile4GFX();
        });
        jQuery("#"+closequ).bind("click",function(){
            checkFile4GFX();
        });

        function checkFile4GFX(){
            var isdian_v = WfForm.getFieldValue(isdian);

            if(isdian_v ==1){//filedian
                WfForm.changeFieldAttr(filedian,3);   //设置必填
            }else{
                WfForm.changeFieldAttr(filedian,2);   //设置可编辑
            }

        }


//=========页面加载检查==================
        function checkSGQY(){
            var  shigquyu_val = jQuery("#"+shigquyu).val();
            if(shigquyu_val ==0){
                jQuery("#xxbgq").show();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            /*if(shigquyu_val ==1){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").show();
            jQuery("#xxzl").hide();
            jQuery("#xxoled").hide();
            jQuery("#mozuxx").hide();
            }*/
            if(shigquyu_val ==2){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").show();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            if(shigquyu_val ==3){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").show();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            if(shigquyu_val ==4){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").show();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            if(shigquyu_val ==5){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").show();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==6){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").show();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==7){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").show();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==8){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").show();
            }

            if(shigquyu_val ==9){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

        }


        function checkGAS(){
            var gaszcquy_v =  WfForm.getFieldValue(gaszcquyu);

            if(gaszcquy_v == 1){
                jQuery("#gasoneid").show();
                jQuery("#gastwoid").show();
                jQuery("#gasthreeid").show();
                jQuery("#gascheck1").show();
                jQuery("#gascheck2").show();
                jQuery("#gascheck3").show();
            }
        }


        function checkGFX(){
            var highfx_v =  WfForm.getFieldValue(highfxqu);
            if(highfx_v == 1 ){
                jQuery("#mixid").show();

                var fire_v = WfForm.getFieldValue(firequ);
                var diao_v =  WfForm.getFieldValue(diaozhqu);
                var high_v =  WfForm.getFieldValue(highqu);
                var close_v = WfForm.getFieldValue(closequ);
                var danger_v = WfForm.getFieldValue(dangerqu);

                if(fire_v ==1 ){
                    jQuery("#fireid").show();
                }else{
                    jQuery("#fireid").hide();
                }

                if(  diao_v ==1){
                    jQuery("#diaoid").show();
                }else{
                    jQuery("#diaoid").hide();
                }

                if( high_v ==1){
                    jQuery("#highid").show();
                    jQuery("#hightxtid").show();
                }else{
                    jQuery("#highid").hide();
                    jQuery("#hightxtid").hide();
                }

                if(  close_v ==1){
                    jQuery("#closeid").show();
                    jQuery("#closetxtid").show();
                }else{
                    jQuery("#closeid").hide();
                    jQuery("#closetxtid").hide();
                }


                if( danger_v==1){
                    jQuery("#dangerid").show();
                    jQuery("#dangertxtid").show();
                }else{
                    jQuery("#dangerid").hide();
                    jQuery("#dangertxtid").hide();
                }

            }
        }
//=============================
        function gaszc(){//气体侦测检查
            var gasquyu_v =  WfForm.getFieldValue(gaszcquyu);

            if(gasquyu_v==1){
                jQuery("#gasoneid").show();
                jQuery("#gastwoid").show();
                jQuery("#gasthreeid").show();
                jQuery("#gascheck1").show();
                jQuery("#gascheck2").show();
                jQuery("#gascheck3").show();
            }else{
                jQuery("#gasoneid").hide();
                jQuery("#gastwoid").hide();
                jQuery("#gasthreeid").hide();
                jQuery("#gascheck1").hide();
                jQuery("#gascheck2").hide();
                jQuery("#gascheck3").hide();
            }

        }

//====================================
        function checkgfx(){ //高风险作业检查

            var highfx_v = WfForm.getFieldValue(highfxqu);
            var fire_v =  WfForm.getFieldValue(firequ);
            var diao_v = WfForm.getFieldValue(diaozhqu);
            var high_v =  WfForm.getFieldValue(highqu);
            var close_v = WfForm.getFieldValue(closequ);
            var danger_v = WfForm.getFieldValue(dangerqu);
            var xiankai_v= WfForm.getFieldValue(xiankaiqu);
            var jikeng_v= WfForm.getFieldValue(jikengqu);

            if(xiankai_v==1){
                jQuery("#xiankaiid").show();
            }else{
                jQuery("#xiankaiid").hide();
            }

            if(jikeng_v==1){
                jQuery("#jikengid").show();
            }else{
                jQuery("#jikengid").hide();
            }

            if(highfx_v ==1){
                jQuery("#mixid").show();
            }else{
                jQuery("#mixid").hide();
            }

            if(fire_v ==1){
                jQuery("#fireid").show();
            }else{
                jQuery("#fireid").hide();
            }

            if(diao_v ==1){
                jQuery("#diaoid").show();
            }else{
                jQuery("#diaoid").hide();
            }

            if(high_v ==1){
                jQuery("#highid").show();
                jQuery("#hightxtid").show();
            }else{
                jQuery("#highid").hide();
                jQuery("#hightxtid").hide();
            }

            if(close_v ==1){
                jQuery("#closeid").show();
                jQuery("#closetxtid").show();
            }else{
                jQuery("#closeid").hide();
                jQuery("#closetxtid").hide();
            }

            if(danger_v ==1){
                jQuery("#dangerid").show();
                jQuery("#dangertxtid").show();
            }else{
                jQuery("#dangerid").hide();
                jQuery("#dangertxtid").hide();
            }
        }

//=======气体侦测控制=================
        jQuery("#"+gaszcquyu).bind("click",function(){
            jQuery("#gasoneid").show();
            jQuery("#gastwoid").show();
            jQuery("#gasthreeid").show();
            jQuery("#gascheck1").show();
            jQuery("#gascheck2").show();
            jQuery("#gascheck3").show();
            gaszc();
        });

//====施工区域的控制=======================
        jQuery("#"+shigquyu).bindPropertyChange(function(){
            var  shigquyu_val = jQuery("#"+shigquyu).val();
            if(shigquyu_val ==0){
                jQuery("#xxbgq").show();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

            /*if(shigquyu_val ==1){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").show();
            jQuery("#xxzl").hide();
            jQuery("#xxoled").hide();
            jQuery("#mozuxx").hide();
            }*/
            if(shigquyu_val ==2){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").show();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            if(shigquyu_val ==3){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").show();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }
            if(shigquyu_val ==4){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").show();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }


            if(shigquyu_val ==5){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").show();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==6){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").show();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==7){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").show();
                jQuery("#xxit").hide();
            }

            if(shigquyu_val ==8){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").show();
            }

            if(shigquyu_val ==9){
                jQuery("#xxbgq").hide();
                jQuery("#xxww").hide();
                jQuery("#xxzl").hide();
                jQuery("#xxoled").hide();
                jQuery("#mozuxx").hide();
                jQuery("#xxdlkt").hide();
                jQuery("#xxscjh").hide();
                jQuery("#xxqhsw").hide();
                jQuery("#xxit").hide();
            }


        });


//====高风险区域的控制=======================
        jQuery("#"+xiankaiqu).bind("click",function(){
            jQuery("#xiankaiid").show();
            checkgfx();
        });
        jQuery("#"+jikengqu).bind("click",function(){
            jQuery("#jikengid").show();
            checkgfx();
        });
        jQuery("#"+highfxqu).bind("click",function(){
            jQuery("#mixid").show();
            checkgfx();
        });
        jQuery("#"+firequ).bind("click",function(){
            jQuery("#fireid").show();
            checkgfx();
        });
        jQuery("#"+diaozhqu).bind("click",function(){
            jQuery("#diaoid").show();
            checkgfx();
        });
        jQuery("#"+highqu).bind("click",function(){
            jQuery("#highid").show();
            jQuery("#hightxtid").show();
            checkgfx();
        });
        jQuery("#"+closequ).bind("click",function(){
            jQuery("#closeid").show();
            jQuery("#closetxtid").show();
            checkgfx();
        });
        jQuery("#"+dangerqu).bind("click",function(){
            jQuery("#dangerid").show();
            jQuery("#dangertxtid").show();
            checkgfx();
        });

//=============施工日期（最长跨度7天的控制）=============================
        checkCustomize = function() {
            var sqtime_val = jQuery(shishiStart).val();
            var dqtime_val = jQuery(shishiEnd).val();
            var gfxchoose_val = WfForm.getFieldValue(highfxqu); //高风险区域施工选中
            var baitian_v=WfForm.getFieldValue(baitian);//白天
            var yewan_v= WfForm.getFieldValue(yewan);//夜晚
            var isdian_v1 = WfForm.getFieldValue(isdian); //用电勾选后上传附件
            var isdianFou_v1 = WfForm.getFieldValue(isdianFou); //用电勾选后上传附件
            var dianCheck1="field63495";//电的明细数据1
            var dianCheck2="field63496";//电的明细数据2
            var dianCheck1Value=jQuery("#"+dianCheck1).val();
            var dianCheck2Value=jQuery("#"+dianCheck2).val();

            var sfzNum="field63443_";//身份证号码 （明细）
            var xuHaonum="field213221_";//工号序号（明细）
            var indexnum = jQuery("#indexnum0").val();//(明细表行数)

            var zhongduanNeed="field63392";//中断需求-是
            var zhongduanNeedFou="field63391";//中断需求-Fou
            var shuiwater1="field63393";//水
            var shuiwater2="field63394";//空调
            var shuiwater3="field63395";//系统
            var shuiwater4="field63396";
            var shuiwater5="field63397";
            var shuiwater6="field63398";
            var shuiwater7="field63399";
            var shuiwater8="field63400";

            var zhongduanNeed_val= WfForm.getFieldValue(zhongduanNeed);
            var zhongduanNeedFou_val=WfForm.getFieldValue(zhongduanNeedFou);
            var shuiwater1_val= WfForm.getFieldValue(shuiwater1);
            var shuiwater2_val= WfForm.getFieldValue(shuiwater2);
            var shuiwater3_val=WfForm.getFieldValue(shuiwater3);
            var shuiwater4_val= WfForm.getFieldValue(shuiwater4);
            var shuiwater5_val= WfForm.getFieldValue(shuiwater5);
            var shuiwater6_val= WfForm.getFieldValue(shuiwater6);
            var shuiwater7_val= WfForm.getFieldValue(shuiwater7);
            var shuiwater8_val= WfForm.getFieldValue(shuiwater8);

//==================================
            var firequ_v= WfForm.getFieldValue(firequ);//动火作业
            var diaozhqu_v= WfForm.getFieldValue(diaozhqu);//吊装
            var highqu_v= WfForm.getFieldValue(highqu);//高处
            var closequ_v= WfForm.getFieldValue(closequ);//密闭空间
            var dangerqu_v= WfForm.getFieldValue(dangerqu);//危险管路阀门
            var xiankaiqu_v= WfForm.getFieldValue(xiankaiqu);//掀开
            var jikengqu_v= WfForm.getFieldValue(jikengqu);//基坑
//=======涉及危险作业================================
            if(gfxchoose_val == 1){
                if (firequ_v==1 || diaozhqu_v==1 || highqu_v==1 || closequ_v==1|| dangerqu_v==1 || xiankaiqu_v==1|| jikengqu_v==1){
                }else{
                    Dialog.alert("勾选了：危险作业项目-有，下面具体的作业类型也必须选择，请确认后再提交！");
                    return false;
                }
            }

//======中断需求检查==========
            if(zhongduanNeed_val==1){
                if(shuiwater1_val ==1 || shuiwater2_val ==1|| shuiwater3_val ==1|| shuiwater4_val ==1|| shuiwater5_val ==1|| shuiwater6_val ==1|| shuiwater7_val ==1|| shuiwater8_val ==1) {
                }else{
                    Dialog.alert("您勾选了 中断需求-有，请填写下面明细选项");
                    return false;
                }
            }


//======用电检查==========
            if(isdian_v1 ==1){
                if(dianCheck1Value==""|| dianCheck1Value==null || dianCheck1Value.trim().length<=0){
                    Dialog.alert( "您勾选了 是否用电-是，请填写-施工用电电压！");
                    return false;
                }
                if(dianCheck2Value==""|| dianCheck2Value==null || dianCheck2Value.trim().length<=0){
                    Dialog.alert( "您勾选了 是否用电-是，请填写-施工用电电量！");
                    return false;
                }
            }


//=======循环检查明细中身份证号码检查长度=================
            for (var i=0;i<indexnum;i++)
            {
                if(jQuery("#"+xuHaonum+i).length>0){
                    var numi_v=jQuery("#"+xuHaonum+i).val();
                    if(numi_v.trim().length !=11){
                        Dialog.alert( " 第"+(i+1)+"行"+"-出入证长度不等于11位，请重新输入！");
                        return false;
                    }
                }
            }

//======= 检查：施工时间（白天或夜晚 必选其一）======================
            if(baitian_v==1|| yewan_v==1){

            }else{
                Dialog.alert( "涉及夜晚施工 是、否 必选其一！");
                return false;
            }

            if(isdian_v1==1|| isdianFou_v1 ==1){

            }else{
                Dialog.alert( "是否用电  是、否 必选其一！");
                return false;
            }

            if(zhongduanNeed_val==1|| zhongduanNeedFou_val==1){

            }else{
                Dialog.alert( "中断需求 是、否  必选其一！");
                return false;
            }

            if((baitian_v==1) && ( yewan_v==1) ){
                Dialog.alert( "涉及夜晚施工 是、否  只能选其一！");
                return false;
            }else{

            }

            if((isdian_v1 ==1) && ( isdianFou_v1 ==1) ){
                Dialog.alert( "是否用电 是、否  只能选其一！");
                return false;
            }else{

            }

            if((zhongduanNeedFou_val==1) && ( zhongduanNeed_val==1) ){
                Dialog.alert( "中断需求 是、否  只能选其一！");
                return false;
            }else{

            }


            var sgsc_val = jQuery(sgsc).val();

            if(Number(sgsc_val)>7){
                Dialog.alert("一般施工作业总时长不可超过7天，请确认！");
                return false;
            }

            if((gfxchoose_val ==1) &&  Number(sgsc_val)>1 ){
                Dialog.alert("非一般施工作业总时长不可超过1天（已选择危险作业项目），请确认！");
                return false;
            }




            return true;
        }

        function convertStringToDate(dateString) {
            dateString = dateString.split('-');
            return new Date(dateString[0], dateString[1] - 1, dateString[2]);
        }

    });








