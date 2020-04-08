<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="rrd.supplier.dao.SupplierInInfoDao" %>
<%@ page import="rrd.supplier.util.TransUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="weaver.systeminfo.SystemEnv" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="SystemEnv" class="weaver.systeminfo.SystemEnv" scope="page"/>
<%--
  Created by IntelliJ IDEA.
  User: tangj
  Date: 2020/2/25
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>

<html>
<%
    //RGZ1Pm1JoLWuBfQqqUJoZ2lVWs0RYEdk
    String urlKey = URLDecoder.decode(Util.null2String(request.getParameter("urlKey")),"UTF-8");
    SupplierInfoServiceImpl sisi = new SupplierInfoServiceImpl();
    TransUtil tu = new TransUtil();
    String rqid = sisi.getRqid(urlKey);
    String zcid = sisi.getSupplierTemporaryDataId(rqid);
    SupplierInInfoDao sid = sisi.getSupplierTemporaryData(zcid);
    //out.print(rqid);
    //out.print(" zcid:"+zcid);
    String accsize ="10";
    String accsec="19024";
%>
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
    <link href="/js/swfupload/default_wev8.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/swfupload/swfupload_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/swfupload.queue_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/fileprogress_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/handlers_wev8.js"></script>
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
        .tr_normal1{
            height: 46px; display: table-row;
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
        .td_fieldnameright{
            text-align: right;
            vertical-align: middle;
            font-size: 9pt;
            font-family: "Microsoft YaHei";
            line-height: 1;
        }
        .td_fieldnamemid{
            text-align: left;
            vertical-align: middle;
            font-size: 11pt;
            font-family: "Microsoft YaHei";
            padding-left: 28px;
            line-height: 1;
        }
        .td_fieldnameleft {
            text-align: left;
            vertical-align: middle;
            font-size: 9pt;
            font-family: "Microsoft YaHei";
            line-height: 1;
        }
        .div_fieldname{
            font-size: 9pt;
            overflow-wrap: break-word;
            word-break: break-word!important;
        }
        .td_fieldvalue {
            text-align: left;
            vertical-align: middle;
            color: black;
            line-height: 1;
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
                        <input type="hidden" name="yyzzjggz" id="yyzzjggz" value="<%=sid.getYyzzjggz() %>">
                        <div style="overflow-x: auto; min-height: 1140px; padding-bottom: 20px;background-color: #fff;">
                            <table class="excelMainTable" style="margin: 0px auto; width: 1189px;" _haspercent="true">
                                <colgroup><col width="60px"><col width="104px"><col width="16px"><col width="233px"><col width="115px"><col width="15px"><col width="20px"><col width="254px"><col width="33px"><col width="94px"><col width="15px"><col width="200px"><col width="30px"></colgroup>
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
                                    <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492458.jpg');"><div style="width: 100%;"></div></td>
                                    <td valign="middle" class="td_title1" ><div style="width: 100%;"><div class="div_fontstyle2" >基本信息</div></div></td>
                                    <td></td><td></td><td></td><td></td><td></td><td></td>
                                    <td valign="middle" colspan="5" class="" style="border-top: 2px solid rgb(153, 153, 153); background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; font-weight: bold; font-size: 12pt; color: black;  padding-left: 8px; line-height: 1;"><div style="width: 100%;"><div class="div_fontstyle2" >供应商关键信息</div></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">公司性质</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("gsxz",sid.getGsxz(),"select","1","","")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">所属行业</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable("szhy",sid.getSzhy(),"1","uf_khsshy","drdl01","id","DRKY","asc")%></div></td>
                                    <td class="td_grey" ></td>
                                    <td valign="middle" colspan="3" class="td_bordtop" ><div style="width: 100%;"><div  class="div_fontstyle1" >供应商名称</div></div></td>
                                    <td class="td_grey" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">经营属性</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("zzs",sid.getZzs(),"checkbox","1","制造商","")%>
                                        <%=sisi.getFieldHtml("fxs",sid.getZzs(),"checkbox","1","分销商","")%>
                                        <%=sisi.getFieldHtml("fws",sid.getZzs(),"checkbox","1","服务商","")%>
                                    </div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">法定代表人</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("fddbr",sid.getFddbr(),"text","1","50","30")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="3" class="td_bordbottom"><div width="100%"><%=sisi.getFieldHtml("gysmc",sid.getGysmc(),"text","1","100","35")%></div></td>
                                    <td class="td_grey"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">成立时间</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <button type="button" class=Calendar id="selectclsj" onclick="onshowPlanDate1('clsj','selectclsjSpan','1')"></BUTTON>
                                        <SPAN id="selectclsjSpan" >
                                                <%if("".equals(sid.getClsj())){%>
                                                <IMG src='/images/BacoError_wev9.png' align=absMiddle>
                                                <%}%>
                                                <%=sid.getClsj()%></SPAN>
                                        <INPUT type="hidden" name="clsj" id="clsj" value="<%=sid.getClsj()%>">
                                    </div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">公司电话</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("gsdh",sid.getGsdh(),"text","1","50","30")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="3" class="td_bordtop"><div style="width: 100%;"><div  class="div_fontstyle1" >统一社会信用代码</div></div></td>
                                    <td class="td_grey"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">注册资本（万）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("zczbw",sid.getZczbw(),"float","1","2","")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">年销售额（万）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("nxsew",sid.getNxsew(),"float","1","2","")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="3" class="td_bordbottom"><div width="100%"><%=sisi.getFieldHtml("tyshxydm",sid.getTyshxydm(),"text","1","50","35")%></div></td>
                                    <td class="td_grey"></td>
                                </tr>

                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商网址</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="5"><div width="100%"><%=sisi.getFieldHtml("gyswz",sid.getGyswz(),"text","0","50","70")%></div></td>
                                    <td class=""  colspan="5" style="background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; color: black; line-height: 1;"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商注册地址</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="10"><div width="100%"><%=sisi.getFieldHtml("gyszcdz",sid.getGyszcdz(),"text","1","200","120")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商经营地址</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="10"><div width="100%"><%=sisi.getFieldHtml("gysjydz",sid.getGysjydz(),"text","1","200","120")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">管理者联系人</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("glzlxr",sid.getGlzlxr(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">移动电话</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yddh",sid.getYddh(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">邮箱地址</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yxdz",sid.getYxdz(),"text","1","50","30")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">日常事务联系人</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("rcswlxr",sid.getRcswlxr(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">移动电话</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yddh1",sid.getYddh1(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">邮箱地址</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yxdz1",sid.getYxdz1(),"text","1","50","30")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">业务范围<br/>（根据营业执照）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="10"><div width="100%">
                                        <textarea class="Inputstyle" temptype="1" viewtype="1" temptitle="业务范围（根据营业执照）" id="ywfwgjyyzz" name="ywfwgjyyzz" rows="4" onchange="checkmustinput('ywfwgjyyzz','ywfwgjyyzzspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word"></textarea>
                                        <span id="ywfwgjyyzzspan">
                                                  <%if("".equals(sid.getClsj())){%>
                                                <IMG src='/images/BacoError_wev9.png' align=absMiddle>
                                                <%}%>
                                                 <%=sid.getClsj()%>
                                            </span>
                                    </div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492458.jpg');"><div style="width: 100%;"></div></td>
                                    <td valign="middle" class="td_title1" ><div style="width: 100%;"><div class="div_fontstyle2" >财务信息</div></div></td>
                                    <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">交易货币</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("bz",sid.getBz(),"select","1","","")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">税率</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable("sl",sid.getSl(),"1","uf_sl","tatxa","id","tatxa1","asc")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">关联公司</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("glgs",sid.getGlgs(),"text","1","100","30")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">银行账号</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("yhzh",sid.getGlgs(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">开户行</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="7"><div width="100%"><%=sisi.getFieldHtml("khh",sid.getKhh(),"text","1","200","60")%></div></td>
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
                                    <td class="td_fieldnameright" ><div class="div_fieldname">员工总数（人）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("ygzsr",sid.getYgzsr(),"int","0","","")%></div></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">办公场所面积（㎡）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("bgcsmjm2",sid.getBgcsmjm2(),"float","0","2","")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">生产场所面积（㎡）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("sccsmjm2",sid.getSccsmjm2(),"float","0","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">管理人员总数（人）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("glryzsr",sid.getGlryzsr(),"int","0","","")%></div></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">仓库面积（㎡）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="7"><div width="100%"><%=sisi.getFieldHtml("ckmjm2",sid.getCkmjm2(),"float","0","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">设备和产品能力描述</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="7"><div width="100%">
                                        <textarea class="Inputstyle" temptype="1" viewtype="0" temptitle="设备和产品能力描述" id="sbhcpnlms" name="sbhcpnlms" rows="4" onchange="checkmustinput('sbhcpnlms','sbhcpnlmsspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word"></textarea>
                                        <span id="sbhcpnlmsspan">
                                                <%=sid.getSbhcpnlms()%>
                                            </span>
                                    </div></td>
                                </tr>


                                <tr class="tr_normal">
                                    <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492725.jpg');"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >设计&开发能力</div></div></div></td>
                                    <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">是否有设计开发能力</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("sfysjkfnl",sid.getSfysjkfnl(),"select","1","","")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">设计人员数量（人）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="7"><div width="100%"><%=sisi.getFieldHtml("sjryslr",sid.getSjryslr(),"int","1","","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td valign="middle" colspan="13" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url('/filesystem/exceldesign/uploadimg/uploadImg_202022492737.jpg');"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >主要客户情况</div></div></div></td>
                                    <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>

                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户A</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("kha",sid.getKha(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业A</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("hya",sid.getHya(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比A（%）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zba",sid.getZba(),"float","1","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户B</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("khb",sid.getKhb(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业B</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("hyb",sid.getHyb(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比B（%）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zbb",sid.getZbb(),"float","1","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户C</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("khc",sid.getKhc(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业C</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("hyc",sid.getHyc(),"text","1","50","30")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比C（%）</div></td>
                                    <td class="td_fieldnameleft"><div class="div_fieldname">：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zbc",sid.getZbc(),"float","1","2","")%></div></td>
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
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >营业执照</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <select class="e8_btn_top middle" style="width:50px" id="sfyyyxgfj" name="sfyyyxgfj" viewtype="1" onblur="checkmustinput('sfyyyxgfj','sfyyyxgfjspan',this.getAttribute('viewtype'));"> <option value="" selected=""></option> <option value="0">有</option> <option value="1">无</option></select>
                                    </div></td>
                                    <td colspan="3">
                                     <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_yyzzjggz"></span><!--选取多个文件-->
                                            </span>
                                        &nbsp;&nbsp;
                                         <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_yyzzjggz">
										<span><img src="/rrd/supplier/img/qc.png"  border="0"></span>
										<span><font style="color:#808080;font-size:10pt">&nbsp;&nbsp;最大10M/个</font></span>
									</span>
                                     </div>
                                        <div class="fieldset flash" id="fsUploadProgress_yyzzjggz"></div>
                                        <div id="divStatus_yyzzjggz"></div>

                                        <input class=inputstyle style="display:none;" type=file name="accessory1_yyzzjggz" onchange='accesoryChanage(this)' style="width:100%">
                                        <span id="shfj_span_yyzzjggz"></span>
                                        <button type="button" class=AddDoc style="display:none;" name="addacc_yyzzjggz" onclick="addannexRow()">添加</button>
                                        <input type=hidden name=accessory_num_yyzzjggz value="1">
                                    </td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">营业如无请说明理由</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue" colspan="5"><div width="100%"><%=sisi.getFieldHtml("yyzzlysm",sid.getYyzzlysm(),"text","1","200","40")%></div></td>

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
<SCRIPT language="javascript" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" src="/js/selectDateTime_wev8.js"></script>
<SCRIPT language="javascript" src="/js/JSDateTime/WdatePicker_wev8.js"></script>

<script type="text/javascript">
    var oUpload_yyzzjggz;//营业执照

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

        var settings_yyzzjggz = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_yyzzjggz",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/rrd/supplier/img/sc3.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_yyzzjggz",

            button_width: 100,
            button_height: 30,
            button_text : '<span class="button"></span>',
            button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
            button_text_top_padding: 0,
            button_text_left_padding: 2,

            button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
            button_cursor: SWFUpload.CURSOR.HAND,
            // The event handler functions are defined in handlers.js
            file_queued_handler : fileQueued,
            file_queue_error_handler : fileQueueError,
            file_dialog_complete_handler : fileDialogComplete_yyzzjggz,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_yyzzjggz,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };



        try{
            oUpload = new SWFUpload(settings_yyzzjggz);
            jQuery("#spanButtonPlaceHolder_yyzzjggz").html("<span><img src=\"/gvo/cowork/img/sc.png\"  border=\"0\"></span>");
            jQuery("#spanButtonPlaceHolder_yyzzjggz").css({"background":"","bottom":"","padding-left":"","cursor":"","position":"","z-index":""})
            //jQuery("#spanButtonPlaceHolder").css({"background-color":"rgb(5,80,246)","padding-left":""})
            //jQuery("#spanButtonPlaceHolder").html("<span><img src=\"/gvo/cowork/img/sc.png\"  border=\"0\"></span>");
            //SWFUpload_0
        } catch(e){alert(e)}
    }

    function fileDialogComplete_yyzzjggz(){
        document.getElementById("btnCancel1_yyzzjggz").disabled = false;
        fileDialogComplete
    }
    function uploadSuccess_yyzzjggz(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#yyzzjggz").val() == ""){

                    jQuery("#yyzzjggz").val(a);
                }else{

                    jQuery("#yyzzjggz").val(jQuery("#yyzzjggz").val()+","+a);

                }
            }
        }
    }

    function uploadComplete(fileObj) {
        try {
            /*  I want the next upload to continue automatically so I'll call startUpload here */
            if (this.getStats().files_queued === 0) {

                report.submit();
                document.getElementById(this.customSettings.cancelButtonId).disabled = true;
            } else {
                this.startUpload();
            }
        } catch (ex) { this.debug(ex); }

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

    function onshowPlanDate1(inputname,spanname,isMustInput){
        var returnvalue;
        var oncleaingFun = function(){
            if(isMustInput== "1"){
                $ele4p(spanname).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
            }else{
                $ele4p(spanname).innerHTML = "";
            }
            $ele4p(inputname).value = '';
        }
        WdatePicker({lang:languageStr,el:spanname,onpicked:function(dp){
                returnvalue = dp.cal.getDateStr();
                $dp.$(spanname).innerHTML = returnvalue;
                $dp.$(inputname).value = returnvalue;},oncleared:oncleaingFun});

        var hidename = $ele4p(inputname).value;
        if(hidename != ""){
            $ele4p(inputname).value = hidename;
            $ele4p(spanname).innerHTML = hidename;
        }else{
            if("1".equals(isMustInput)){
                $ele4p(spanname).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
            }else{
                $ele4p(spanname).innerHTML = "";
            }
        }
    }
    function checkmustinput(elementname,spanid,viewtype){
        if (viewtype == "") {
            viewtype = $G(elementname).getAttribute("viewtype");
        }
        if(viewtype==1){
            var tmpvalue = "";
            try{
                tmpvalue = $GetEle(elementname).value;
            }catch(e){
                tmpvalue = $GetEle(elementname).value;
            }
            while(tmpvalue.indexOf(" ") >= 0){
                tmpvalue = tmpvalue.replace(" ", "");
            }
            while(tmpvalue.indexOf("\r\n") >= 0){
                tmpvalue = tmpvalue.replace("\r\n", "");
            }
            if(tmpvalue!=""){
                $GetEle(spanid).innerHTML = "";
            }else{
                $GetEle(spanid).innerHTML = "<IMG src='/images/BacoError_wev9.png' align=absMiddle>";
                $GetEle(elementname).value = "";
            }
        }else{
            $GetEle(spanid).innerHTML = "";
        }
    }
</script>
</body>
</html>
