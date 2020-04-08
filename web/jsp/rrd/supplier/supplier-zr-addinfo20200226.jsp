<%--
  Created by IntelliJ IDEA.
  User: tangj
  Date: 2020/2/25
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>供应商信息收集</title>
    <LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
    <!--<link href="/css/rp_wev8.css" rel="STYLESHEET" type="text/css">-->
    <link href="/workflow/exceldesign/css/excelHtml_wev8.css" rel="STYLESHEET" type="text/css">
    <link type="text/css" rel="stylesheet" href="/wui/theme/ecology8/skins/default/wui_wev8.css">
    <script type="text/javascript" src="/wui/common/jquery/jquery.min_wev8.js"></script>
    <SCRIPT language="JavaScript" src="/js/weaver_wev8.js"></SCRIPT>
    <link rel="stylesheet" href="/css/ecology8/request/requestTopMenu_wev8.css" type="text/css" />
    <link rel="stylesheet" href="/wui/theme/ecology8/jquery/js/zDialog_e8_wev8.css" type="text/css" />
    <style>
        * {
            font-family: "Microsoft YaHei"!important;
            font-family:'微软雅黑';
        }
        table{
            FONT-SIZE: 9pt !important;

        }

        .td_xh{

            border-bottom-color: grey;
            border-bottom-width: 2px;
            border-bottom-style: solid;
        }
        .tr_normal{
            height: 30px; display: table-row;
        }
        .contentbody::-webkit-scrollbar {/*滚动条整体样式*/
            width: 7px;
            height: 7px;
            background-color: transparent;
        }
        .contentbody::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
            border-radius: 7px;
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
            background-color: #9d9fa1
        }
        .contentbody::-webkit-scrollbar-track {/*滚动条里面轨道*/
            background-color: transparent
        }
        .td_grey{
            background-color: rgb(239, 239, 239);
            text-align: left;
            vertical-align: middle;
            color: black; line-height: 1;
        }
        .td_bordtop{
            border-top: 1px solid rgb(153, 153, 153);
            border-left: 1px solid rgb(153, 153, 153);
            border-right: 1px solid rgb(153, 153, 153);
            text-align: left;
            vertical-align: middle;
            font-weight: bold;
            font-size: 9pt;
            color: rgb(183, 183, 183);
            padding-left: 8px;
            line-height: 1;
        }
        .td_bordbottom{
            border-left: 1px solid rgb(153, 153, 153); 
            border-right: 1px solid rgb(153, 153, 153); 
            border-bottom: 1px solid rgb(153, 153, 153); 
            text-align: left; 
            vertical-align: middle; 
            font-weight: bold; 
            font-size: 9pt; 
            color: rgb(183, 183, 183);  
            padding-left: 8px; 
            line-height: 1;
        }
        .td_image{
            border-top: 2px solid rgb(153, 153, 153);  
            background-repeat: no-repeat; 
            text-align: left; 
            vertical-align: middle; 
            line-height: 1;
        }
        .td_title1{
            border-top: 2px solid rgb(153, 153, 153);
            text-align: right;
            vertical-align: middle;
            font-weight: bold;
            font-size: 12pt;
            color: black;
            line-height: 1;
        }
        .td_title2{
            border-top: 2px solid rgb(153, 153, 153); 
            text-align: left; 
            vertical-align: middle; 
            font-weight: bold; 
            font-size: 12pt; 
            padding-left: 24px; line-height: 1;
        }
        .td_fieldname{
            font-size: 9pt;
            word-break: break-all;
            overflow-wrap: break-word;
        }
        .div_fontstyle1{
            font-size: 9pt;
            word-break: break-all;
            overflow-wrap: break-word;
        }
        .div_fontstyle2{
            font-size: 12pt;  
            word-break: break-all; 
            overflow-wrap: break-word;
        }
    </style>
</head>
<body >
<div  style="overflow: hidden;height: 100%;">
<div style="width: 100%;height: 100%;padding-top: 71px;position: relative;overflow: hidden;">
    <div style="width: 100%;background-color: #fff;position: absolute;top: 0;left: 0;z-index: 1;margin-left: 0;margin-right: 0;height: auto;zoom: 1;display: block;">
        <div style="padding: 6px 0 0 80px;position: relative;padding-left: 65px;padding-top: 23px;background: #fff;display: block;width:100%;">
            <div style="position: absolute;width: 40px;left: 24px;top: 23px;">

            </div>
            <div style="padding-left: 8px;font-weight: 500;white-space: nowrap;overflow: hidden;-o-text-overflow: ellipsis;text-overflow: ellipsis;line-height: 20px;height: 20px;color: #333;font-size: 14px !important;">
                <div title="供应商信息收集" >供应商信息收集</div>
            </div>
        </div>
    </div>
    <div class="contentbody" id="divinner"style="width: 100%;overflow: auto;background-color: #f1f1f1;height: 873px;overflow-x: hidden;overflow-y: auto;position: relative;z-index: 0;">
    <div style="height: auto;">
        <div  style="min-height: 1204px; padding: 30px 40px;">
            <FORM id=report1 name=report1 action="/alarm/refresh.jsp" method=post>
                <div style="overflow-x: auto; min-height: 1140px; padding-bottom: 20px;background-color: #fff;">
                                <table class="excelMainTable" style="margin: 0px auto; width: 1189px;" _haspercent="true">
                                    <colgroup><col width="60px"><col width="104px"><col width="16px"><col width="233px"><col width="105px"><col width="15px"><col width="30px"><col width="254px"><col width="33px"><col width="94px"><col width="15px"><col width="200px"><col width="30px"></colgroup>
                                    <tbody>
                                    <tr style="height: 30px; display: table-row;">
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <tr style="height: 51px; display: table-row;">
                                        <td ></td>
                                        <td valign="middle" colspan="7" class="" style="text-align: left; vertical-align: middle; font-size: 20pt;  padding-left: 36px; line-height: 1;">
                                            <div style="width: 100%;">
                                                <div style="font-size: 20pt; font-family:'Microsoft YaHei'; word-break: break-all; overflow-wrap: break-word;">供应商信息收集</div>
                                            </div>
                                        </td>
                                        <td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr style="height: 21px; display: table-row;">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492450.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" class="td_title1" ><div style="width: 100%;"><div class="div_fontstyle2" >经办信息</div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr style="height: 3px; display: table-row;">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492458.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" class="td_title1" ><div style="width: 100%;"><div class="div_fontstyle2" >基本信息</div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td valign="middle" colspan="5" class="" style="border-top: 2px solid rgb(153, 153, 153); background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; font-weight: bold; font-size: 12pt; color: black;  padding-left: 8px; line-height: 1;"><div style="width: 100%;"><div class="div_fontstyle2" >供应商关键信息</div></div></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey" ></td>
                                        <td valign="middle" colspan="3" class="td_bordtop" ><div style="width: 100%;"><div  class="div_fontstyle1" >供应商名称</div></div></td>
                                        <td class="td_grey" ></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey"></td>
                                        <td valign="middle" colspan="3" class="td_bordbottom"></td>
                                        <td class="td_grey"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey"></td>
                                        <td valign="middle" colspan="3" class="td_bordtop"><div style="width: 100%;"><div  class="div_fontstyle1" >统一社会信用代码</div></div></td>
                                        <td class="td_grey"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey"></td>
                                        <td valign="middle" colspan="3" class="td_bordbottom"></td>
                                        <td class="td_grey"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey"></td>
                                        <td valign="middle" colspan="3" class="td_bordtop"><div style="width: 100%;"><div  class="div_fontstyle1" >法定代表人</div></div></td>
                                        <td class="td_grey"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class="td_grey"></td>
                                        <td valign="middle" colspan="3" class="td_bordbottom"></td>
                                        <td class="td_grey"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                        <td class=""  colspan="5" style="background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; color: black; line-height: 1;"></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492710.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" colspan="4" class="" style="border-top: 2px solid rgb(153, 153, 153); text-align: left; vertical-align: middle; font-weight: bold; font-size: 12pt;  padding-left: 24px; line-height: 1;"><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >生产和业务能力（制造商）</div></div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492725.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >设计&开发能力R&D capability </div></div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492737.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >主要客户情况 Key Customer Brief</div></div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492750.jpg');"><div style="width: 100%;"></div></td>
                                        <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >相关文件上传 Supporting attachment </div></div></div></td>
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>
                                    <tr class="tr_normal">
                                        <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                    </tr>


                                    </tbody>
                                </table>

                </div>

            </FORM>
        </div>
    </div>
    </div>
</div>
</div>
<script type="text/javascript">
    window.onload = function() {
            var clienthei = window.innerHeight
            var clientwid= window.innerWidth
            var height1 = Number(clienthei) - 71;
            if(clientwid <1200){
                clientwid =1200;
            }
            height1 = height1 + 'px';
            document.getElementById('divinner').style.height = height1;
            document.getElementsByTagName("body")[0].style.width  = clientwid+"px";
        }
    window.onresize=function(){
        var clienthei = window.innerHeight
        var clientwid= window.innerWidth
        var height1 = Number(clienthei) - 71;
        if(clientwid <1200){
            clientwid =1200;
        }
        height1 = height1 + 'px';
        document.getElementById('divinner').style.height = height1;
        document.getElementsByTagName("body")[0].style.width  = clientwid+"px";

    };
 </script>
</body>
</html>
