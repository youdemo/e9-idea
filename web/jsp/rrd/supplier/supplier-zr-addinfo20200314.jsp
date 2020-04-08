<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.*" %>
<%@ page import="rrd.supplier.dao.SupplierInInfoDao" %>
<%@ page import="rrd.supplier.util.TransUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="weaver.systeminfo.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>

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
    if("".equals(rqid)){
        out.print("请打开正确的连接地址");
        return;
    }
    String zcid = sisi.getSupplierTemporaryDataId(rqid);
    SupplierInInfoDao sid = sisi.getSupplierTemporaryData(zcid);
    if("".equals(sid.getStatus())){
        out.print("供应商信息已提交，无法重复提交");
        return;
    }
    String accsize ="10";
    String accsec= Util.null2o(weaver.file.Prop.getPropValue("rrdsupplierzr", "zcsecid"));
    String sql = "";
    String chkFields = "gsxz,ssqy,gysmc,szhy,jyzx,zzs,fxs,fws,ywfwgjyyzz,clsj,gsdh,gyszcdz,zczbw,nxsew,gysjydz,fddbr,glzlxr," +
            "yddh,yxdz,rcswlxr,yddh1,yxdz1,tyshxydm,khh,yhzh,wb,yhzhwb,swiftcode,glgs,sfysjkfnl,sjryslr,kha,hya,zba,khb,hyb,zbb," +
            "khc,hyc,zbc,yyzzjggz,yhkhxxjggz,syddhjggz,cwgjxx,zzsq,sbqd,rbcxxkytjbx,gsjbxxjpyq,yyzzlysm,yhkhxxlysm,syddhlysm,sl," +
            "bz,sfyyyxgfj,sfyyhxgfj,sfysyxgfj";
%>
<head>
    <title>供应商信息收集</title>
    <LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
    <!--<link href="/css/rp_wev8.css" rel="STYLESHEET" type="text/css">-->
    <link href="/workflow/exceldesign/css/excelHtml_wev8.css" rel="STYLESHEET" type="text/css">
    <link type="text/css" rel="stylesheet" href="/wui/theme/ecology8/skins/default/wui_wev8.css">
    <script type="text/javascript" src="/wui/common/jquery/jquery.min_wev8.js"></script>
    <script language="javascript" type="text/javascript" src="/js/init_wev8.js"></script>
    <script language=javascript src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>
    <link rel="stylesheet" href="/css/ecology8/request/requestTopMenu_wev8.css" type="text/css" />
    <link rel="stylesheet" href="/wui/theme/ecology8/jquery/js/zDialog_e8_wev8.css" type="text/css" />
    <link href="/js/swfupload/default_wev8.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/ecology8/lang/weaver_lang_7_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/swfupload_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/swfupload.queue_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/fileprogress_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/handlers_wev8.js"></script>
    <script type="text/javascript" src="/js/weaver_wev8.js"></script>
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
            height: 42px; display: table-row;
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
        .submit{
            position:absolute;
            top:0px;
            right:0px;
            margin-left: 50px;
            width:40px;
            text-align:center;
            height:25px;
            line-height: 25px;
            background-color:rgb(5,80,246);
            color:rgb(255,255,255);
            cursor: pointer;
        }
        .ant-btn {
            display: inline-block;
            margin-bottom: 0;
            font-weight: 500;
            text-align: center;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            background-image: none;
            border: 1px solid transparent;
            white-space: nowrap;
            line-height: 1.5;
            padding: 5px 15px;
            font-size: 12px;
            border-radius: 6px;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            -webkit-transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
            -o-transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
            transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
            position: relative;
            color: #000;
            background-color: #f7f7f7;
            border-color: #d9d9d9;
            color: #333;
            background: #fff;
            border-radius: 3px;
        }
        .ant-btn-primary:focus {
            color: #ffffff;
            background-color: #57c5f7;
            border-color: #57c5f7;
        }
        .ant-btn-primary {
            color: #fff;
            background-color: #2db7f5;
            border-color: #2db7f5;
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
                <div>
                    <div style="line-height: 35px;white-space: nowrap;text-overflow: ellipsis;position: relative;margin-left: 0;margin-right: 0;height: auto;zoom: 1;display: block; width: 94%;margin-bottom: 5px;">

                        <div  style="text-align: right; margin-top: -5px;">
                            <div>
                                <span style="display: inline-block; line-height: 28px; vertical-align: middle; margin-left: 10px;">
                                <button type="button" class="ant-btn ant-btn-primary" onclick="save('zc')"><span>暂存</span> </button>
                                </span>
                                <span style="display: inline-block; line-height: 28px; vertical-align: middle; margin-left: 10px;">
                                    <button type="button" class="ant-btn ant-btn-primary" onclick="save('submit')"><span>提交</span></button></span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="contentbody" id="divinner"style="width: 100%;overflow: auto;background-color: #f1f1f1;height: 873px;overflow-x: hidden;overflow-y: auto;position: relative;z-index: 0;">
            <div style="height: auto;">
                <div  style="min-height: 1204px; padding: 30px 40px;">
                    <FORM id=report name=report action="/rrd/supplier/supplier-zr-save.jsp" method=post>
                        <input type="hidden" name="yyzzjggz" id="yyzzjggz" value="<%=sid.getYyzzjggz() %>">
                        <input type="hidden" name="yhkhxxjggz" id="yhkhxxjggz" value="<%=sid.getYhkhxxjggz() %>">
                        <input type="hidden" name="syddhjggz" id="syddhjggz" value="<%=sid.getSyddhjggz() %>">
                        <input type="hidden" name="cwgjxx" id="cwgjxx" value="<%=sid.getCwgjxx() %>">
                        <input type="hidden" name="zzsq" id="zzsq" value="<%=sid.getZzsq() %>">
                        <input type="hidden" name="sbqd" id="sbqd" value="<%=sid.getSbqd() %>">
                        <input type="hidden" name="rbcxxkytjbx" id="rbcxxkytjbx" value="<%=sid.getRbcxxkytjbx() %>">
                        <input type="hidden" name="gsjbxxjpyq" id="gsjbxxjpyq" value="<%=sid.getGsjbxxjpyq() %>">
                        <input type="hidden" name="rqid" id="rqid" value="<%=rqid%>">
                        <input type="hidden" name="zcid" id="zcid" value="<%=zcid%>">
                        <input type="hidden" name="urlKey" id="urlKey" value="<%=Util.null2String(request.getParameter("urlKey"))%>">
                        <input type="hidden" name="submittype" id="submittype" value="">
                        <div style="overflow-x: auto; min-height: 1140px; padding-bottom: 20px;background-color: #fff;">
                            <table class="excelMainTable" style="margin: 0px auto; width: 1189px;" _haspercent="true">
                                <colgroup><col width="60px"><col width="104px"><col width="16px"><col width="233px"><col width="115px"><col width="15px"><col width="254"><col width="20"><col width="33px"><col width="94px"><col width="15px"><col width="200px"><col width="30px"></colgroup>
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
                                        <%=sisi.getFieldHtml("fxs",sid.getFxs(),"checkbox","1","分销商","")%>
                                        <%=sisi.getFieldHtml("fws",sid.getFws(),"checkbox","1","服务商","")%>
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
                                        <textarea class="Inputstyle" temptype="1" viewtype="1" temptitle="业务范围（根据营业执照）" id="ywfwgjyyzz" name="ywfwgjyyzz" rows="4" onchange="checkmustinput('ywfwgjyyzz','ywfwgjyyzzspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word"><%=sid.getYwfwgjyyzz()%></textarea>
                                        <span id="ywfwgjyyzzspan">
                                                  <%if("".equals(sid.getYwfwgjyyzz())){%>
                                                <IMG src='/images/BacoError_wev9.png' align=absMiddle>
                                                <%}%>

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
                                        <textarea class="Inputstyle" temptype="1" viewtype="0" temptitle="设备和产品能力描述" id="sbhcpnlms" name="sbhcpnlms" rows="4" onchange="checkmustinput('sbhcpnlms','sbhcpnlmsspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word" value=""><%=sid.getSbhcpnlms()%></textarea>
                                        <span id="sbhcpnlmsspan">

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
                                <tr class="tr_normal1">
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >营业执照</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfyyyxgfj",sid.getSfyyyxgfj(),"select","1","","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getYyzzjggz())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getYyzzjggz()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','yyzzjggz')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                     <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_yyzzjggz"></span><!--选取多个文件-->
                                            </span>
                                        &nbsp;&nbsp;
                                         <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_yyzzjggz">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                     </div>
                                        <div class="fieldset flash" id="fsUploadProgress_yyzzjggz"></div>
                                        <div id="divStatus_yyzzjggz"></div>

                                        <input class=inputstyle style="display:none;" type=file name="accessory1_yyzzjggz" onchange='accesoryChanage(this)' style="width:100%">
                                        <span id="shfj_span_yyzzjggz"></span>
                                        <button type="button" class=AddDoc style="display:none;" name="addacc_yyzzjggz" onclick="addannexRow()">添加</button>
                                        <input type=hidden name=accessory_num_yyzzjggz value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname">营业如无请说明理由</div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"><%=sisi.getFieldHtml("yyzzlysm",sid.getYyzzlysm(),"text","1","200","30")%></div></td>

                                </tr>

                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >银行开户信息</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfyyhxgfj",sid.getSfyyhxgfj(),"select","1","","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getYhkhxxjggz())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getYhkhxxjggz()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','yhkhxxjggz')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_yhkhxxjggz"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_yhkhxxjggz">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_yhkhxxjggz"></div>
                                            <div id="divStatus_yhkhxxjggz"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_yhkhxxjggz" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_yhkhxxjggz"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_yhkhxxjggz" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_yhkhxxjggz value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname">银行如无请说明理由</div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"><%=sisi.getFieldHtml("yhkhxxlysm",sid.getYhkhxxlysm(),"text","1","200","30")%></div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >商业道德函</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfysyxgfj",sid.getSfysyxgfj(),"select","1","","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getSyddhjggz())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getSyddhjggz()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','syddhjggz')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_syddhjggz"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_syddhjggz">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_syddhjggz"></div>
                                            <div id="divStatus_syddhjggz"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_syddhjggz" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_syddhjggz"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_syddhjggz" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_syddhjggz value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname">商业如无请说明理由</div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"><%=sisi.getFieldHtml("syddhlysm",sid.getSyddhlysm(),"text","1","200","30")%></div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >财务关键信息</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">

                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getCwgjxx())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getCwgjxx()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','cwgjxx')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_cwgjxx"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_cwgjxx">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_cwgjxx"></div>
                                            <div id="divStatus_cwgjxx"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_cwgjxx" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_cwgjxx"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_cwgjxx" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_cwgjxx value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname">财务信息模板</div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%">
                                    </div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >资质授权</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">

                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getZzsq())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getZzsq()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','zzsq')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_zzsq"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_zzsq">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_zzsq"></div>
                                            <div id="divStatus_zzsq"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_zzsq" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_zzsq"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_zzsq" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_zzsq value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"></div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >公司基本信息截屏要求</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getGsjbxxjpyq())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getGsjbxxjpyq()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','gsjbxxjpyq')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_gsjbxxjpyq"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_gsjbxxjpyq">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_gsjbxxjpyq"></div>
                                            <div id="divStatus_gsjbxxjpyq"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_gsjbxxjpyq" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_gsjbxxjpyq"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_gsjbxxjpyq" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_gsjbxxjpyq value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname">公司基本信息截图模板</div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"></div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >如补充信息可以添加表下</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getRbcxxkytjbx())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getRbcxxkytjbx()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','rbcxxkytjbx')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_rbcxxkytjbx"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_rbcxxkytjbx">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_rbcxxkytjbx"></div>
                                            <div id="divStatus_rbcxxkytjbx"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_rbcxxkytjbx" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_rbcxxkytjbx"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_rbcxxkytjbx" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_rbcxxkytjbx value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%">
                                    </div></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >设备清单</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="3">
                                        <div width="100%">
                                            <%
                                                if(!"".equals(sid.getSbqd())){
                                                    String docid="";
                                                    String fileName="";
                                                    String fileId="";
                                                    sql = "select a.docid,b.imagefileid,b.imagefilename from docimagefile a,imagefile b where a.imagefileid=b.imagefileid and a.docid in ("+sid.getSbqd()+")";
                                                    rs.execute(sql);
                                                    while (rs.next()) {
                                                        docid = Util.null2String(rs.getString("docid"));
                                                        fileName = Util.null2String(rs.getString("imagefilename"));
                                                        fileId = Util.null2String(rs.getString("imagefileid"));
                                            %>
                                            <input type=hidden id="field_del_<%=docid%>" value="0" />
                                            <span><%=fileName%></span>&nbsp&nbsp
                                            <input type=hidden id="field_id_<%=docid%>" value=<%=docid%>>
                                            <button type="button" class=btnFlow accessKey=1 onclick="onChangeSharetype('<%=docid%>','sbqd')">
                                                <%=SystemEnv.getHtmlLabelName(91,7)%></button>
                                            <span id="span_id_<%=docid%>" name="span_id_<%=docid%>" style="visibility:hidden">
									<B><FONT COLOR="#FF0033">√</FONT></B>
								</span></br>
                                            <%
                                                    }
                                                }
                                            %>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_sbqd"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload.cancelQueue();" id="btnCancel1_sbqd">
										<span><img src="/js/swfupload/delete_wev8.gif"  border="0"></span>
										<span style="height:19px"><font style="margin:0 0 0 -1"><%=SystemEnv.getHtmlLabelName(21407,7)%></font><!--清除所有选择--></span>
									</span>
                                            </div>
                                            <div class="fieldset flash" id="fsUploadProgress_sbqd"></div>
                                            <div id="divStatus_sbqd"></div>

                                            <input class=inputstyle style="display:none;" type=file name="accessory1_sbqd" onchange='accesoryChanage(this)' style="width:100%">
                                            <span id="shfj_span_sbqd"></span>
                                            <button type="button" class=AddDoc style="display:none;" name="addacc_sbqd" onclick="addannexRow()">添加</button>
                                            <input type=hidden name=accessory_num_sbqd value="1">
                                        </div>
                                    </td>
                                    <td class="td_fieldnameright" colspan="3"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="3"><div width="100%"></div></td>

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
    var oUpload_yhkhxxjggz;//银行开户信息
    var oUpload_syddhjggz;//商业道德函
    var oUpload_cwgjxx;//财务关键信息

    var oUpload_zzsq;//资质授权
    var oUpload_sbqd;//设备清单
    var oUpload_rbcxxkytjbx;//如补充信息可以添加表下
    var oUpload_gsjbxxjpyq;//公司基本信息截屏要求
    var count =0;

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

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
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
        var settings_yhkhxxjggz = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_yhkhxxjggz",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_yhkhxxjggz",

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
            file_dialog_complete_handler : fileDialogComplete_yhkhxxjggz,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_yhkhxxjggz,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_syddhjggz = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_syddhjggz",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_syddhjggz",

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
            file_dialog_complete_handler : fileDialogComplete_syddhjggz,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_syddhjggz,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_cwgjxx = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_cwgjxx",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_cwgjxx",

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
            file_dialog_complete_handler : fileDialogComplete_cwgjxx,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_cwgjxx,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_zzsq = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_zzsq",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_zzsq",

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
            file_dialog_complete_handler : fileDialogComplete_zzsq,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_zzsq,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_sbqd = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_sbqd",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_sbqd",

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
            file_dialog_complete_handler : fileDialogComplete_sbqd,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_sbqd,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_rbcxxkytjbx = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_rbcxxkytjbx",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_rbcxxkytjbx",

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
            file_dialog_complete_handler : fileDialogComplete_rbcxxkytjbx,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_rbcxxkytjbx,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };

        var settings_gsjbxxjpyq = {
            flash_url : "/js/swfupload/swfupload.swf",
            upload_url: "/rrd/supplier/uploadSupplierAcc.jsp",	// Relative to the SWF file
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec %>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_gsjbxxjpyq",
                cancelButtonId : "btnCancel"
            },
            debug: false,


            // Button settings

            button_image_url : "/js/swfupload/add_wev8.png",	// Relative to the SWF file
            button_placeholder_id : "spanButtonPlaceHolder_gsjbxxjpyq",

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
            file_dialog_complete_handler : fileDialogComplete_gsjbxxjpyq,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess_gsjbxxjpyq,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete	// Queue plugin event
        };




        try{
            oUpload_yyzzjggz = new SWFUpload(settings_yyzzjggz);
            oUpload_yhkhxxjggz = new SWFUpload(settings_yhkhxxjggz);
            oUpload_syddhjggz = new SWFUpload(settings_syddhjggz);
            oUpload_cwgjxx = new SWFUpload(settings_cwgjxx);
            oUpload_zzsq = new SWFUpload(settings_zzsq);
            oUpload_sbqd = new SWFUpload(settings_sbqd);
            oUpload_rbcxxkytjbx = new SWFUpload(settings_rbcxxkytjbx);
            oUpload_gsjbxxjpyq = new SWFUpload(settings_gsjbxxjpyq);

        } catch(e){alert(e)}
    }


    function fileDialogComplete_yyzzjggz(){
        document.getElementById("btnCancel1_yyzzjggz").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_yhkhxxjggz(){
        document.getElementById("btnCancel1_yhkhxxjggz").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_syddhjggz(){
        document.getElementById("btnCancel1_syddhjggz").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_cwgjxx(){
        document.getElementById("btnCancel1_cwgjxx").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_zzsq(){
        document.getElementById("btnCancel1_zzsq").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_sbqd(){
        document.getElementById("btnCancel1_sbqd").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_rbcxxkytjbx(){
        document.getElementById("btnCancel1_rbcxxkytjbx").disabled = false;
        fileDialogComplete
    }
    function fileDialogComplete_gsjbxxjpyq(){
        document.getElementById("btnCancel1_gsjbxxjpyq").disabled = false;
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

    function uploadSuccess_yhkhxxjggz(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#yhkhxxjggz").val() == ""){

                    jQuery("#yhkhxxjggz").val(a);
                }else{

                    jQuery("#yhkhxxjggz").val(jQuery("#yhkhxxjggz").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_syddhjggz(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#syddhjggz").val() == ""){

                    jQuery("#syddhjggz").val(a);
                }else{

                    jQuery("#syddhjggz").val(jQuery("#syddhjggz").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_cwgjxx(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#cwgjxx").val() == ""){

                    jQuery("#cwgjxx").val(a);
                }else{

                    jQuery("#cwgjxx").val(jQuery("#cwgjxx").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_zzsq(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#zzsq").val() == ""){

                    jQuery("#zzsq").val(a);
                }else{

                    jQuery("#zzsq").val(jQuery("#zzsq").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_sbqd(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#sbqd").val() == ""){

                    jQuery("#sbqd").val(a);
                }else{

                    jQuery("#sbqd").val(jQuery("#sbqd").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_rbcxxkytjbx(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#rbcxxkytjbx").val() == ""){

                    jQuery("#rbcxxkytjbx").val(a);
                }else{

                    jQuery("#rbcxxkytjbx").val(jQuery("#rbcxxkytjbx").val()+","+a);

                }
            }
        }
    }

    function uploadSuccess_gsjbxxjpyq(fileObj,serverdata){
        var data=eval(serverdata);
        //alert(data);
        if(data){
            var a=data;
            if(a>0){
                if(jQuery("#gsjbxxjpyq").val() == ""){

                    jQuery("#gsjbxxjpyq").val(a);
                }else{

                    jQuery("#gsjbxxjpyq").val(jQuery("#gsjbxxjpyq").val()+","+a);

                }
            }
        }
    }

    function uploadComplete(fileObj) {
        try {

            /*  I want the next upload to continue automatically so I'll call startUpload here */
            if (this.getStats().files_queued === 0) {
                count = count+1;
                //;
                if(count == 8){

                    report.submit();
                }
                //report.submit();
                document.getElementById(this.customSettings.cancelButtonId).disabled = true;
            } else {
                this.startUpload();
            }
        } catch (ex) { this.debug(ex); }

    }
    function changecheck(fieldname){
        var value = jQuery("#"+fieldname).val();
        if(value == '1'){
            value = '0';
        }else{
            value ='1';
        }
        jQuery("#"+fieldname).val(value);
    }
    var flag = "0"
    function save(type){
        jQuery("#submittype").val(type);
        var flag_yyzzjggz="1";
        var flag_yhkhxxjggz="1";
        var flag_syddhjggz ="1";
        var flag_cwgjxx = "1";
        var flag_zzsq="1";
        var flag_sbqd ="1";
        var flag_rbcxxkytjbx = "1";
        var flag_gsjbxxjpyq ="1";
        alert("123");
        //sunmit 点击事件
            var info = jQuery("#info").val();
            if (flag != "0") {
                alert("请勿重复提交");
                return;
            } else {
                //flag = "1";
            }//report.submit();
        var chkFields = '<%=chkFields%>';
        if(!check_form(report,chkFields)) return false;
        if((!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0)
            && (!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0)
            && (!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0)
            && (!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0)
            && (!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0)
            && (!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0)
            && (!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0)
            && (!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0)){
            report.submit();
            alert("222");
        }else{
            alert("333");
            if(!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0){
                count = count+1;
                flag_yyzzjggz = "0";
            }
            
            if(!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0){
                count = count+1;
                flag_yhkhxxjggz="0";
            }
            
            if(!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0){
                count = count+1;
                flag_syddhjggz = "0";
            }
            
            if(!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0){
                count = count+1;
                flag_cwgjxx = "0";
            }
            
            if(!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0){
                count = count+1;
                flag_zzsq = "0";
            }
            
            if(!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0){
                count = count+1;
                flag_sbqd="0";
            }
            
            if(!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0){
                count = count+1;
                flag_rbcxxkytjbx = "0";
            }
            
            if(!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0){
                count = count+1;
                flag_gsjbxxjpyq = "0";
            }
            
            if(flag_yyzzjggz == "1"){
                oUpload_yyzzjggz.startUpload();
            }
            
            if(flag_yhkhxxjggz == "1"){
                oUpload_yhkhxxjggz.startUpload();
            }
            
            if(flag_syddhjggz == "1"){
                oUpload_syddhjggz.startUpload();
            }
            
            if(flag_cwgjxx == "1"){
                oUpload_cwgjxx.startUpload();
            }
            
            if(flag_zzsq == "1"){
                oUpload_zzsq.startUpload();
            }
            
            if(flag_sbqd == "1"){
                oUpload_sbqd.startUpload();
            }
            
            if(flag_rbcxxkytjbx == "1"){
                oUpload_rbcxxkytjbx.startUpload();
            }
            
            if(flag_gsjbxxjpyq == "1"){
                oUpload_gsjbxxjpyq.startUpload();
            }
        }

    }

    function onChangeSharetype(docid,type){
        var delspan="span_id_"+docid;
        var field_id="#field_id_"+docid;
        var field_del="#field_del_"+docid;

        var attachids=jQuery("#"+type).val();
        if(document.all(delspan).style.visibility=='visible'){
            document.all(delspan).style.visibility='hidden';
            jQuery(field_del).val("0");
            if(attachids==""){
                jQuery("#"+type).val(docid);
            }else{
                attachids=attachids+","+docid;
                jQuery("#"+type).val(attachids);
            }
        }else{

            document.all(delspan).style.visibility='visible';
            jQuery(field_del).val("1");
            var attachArr=attachids.split(",");
            var flag="";
            var newids = "";
            for(var i=0;i<attachArr.length;i++){
                if(attachArr[i] !=docid){
                    newids = newids+flag+attachArr[i];
                    flag=",";
                }
            }
            attachids = newids;
            jQuery("#"+type).val(attachids);
        }
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
            if(isMustInput=="1"){
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
    function check_form(thiswins,items)
    {


        thiswin = thiswins
        items = ","+items + ",";

        var tempfieldvlaue1 = "";
        try{
            tempfieldvlaue1 = document.getElementById("htmlfieldids").value;
        }catch (e) {
        }

        for(i=1;i<=thiswin.length;i++){
            tmpname = thiswin.elements[i-1].name;
            tmpvalue = thiswin.elements[i-1].value;
            if(tmpvalue==null){
                continue;
            }

            if(tmpname!="" && items.indexOf(","+tmpname+",")!=-1){
                var __fieldhtmltype = jQuery("input[name=" + tmpname + "]").attr("__fieldhtmltype");
                if (__fieldhtmltype == '9') {
                    continue;
                }

                var href = location.href;
                if(href && href.indexOf("Ext.jsp")!=-1){
                    window.__oriAlert__ = true;
                }
                if(tempfieldvlaue1.indexOf(tmpname+";") == -1){
                    while(tmpvalue.indexOf(" ") >= 0){
                        tmpvalue = tmpvalue.replace(" ", "");
                    }
                    while(tmpvalue.indexOf("\r\n") >= 0){
                        tmpvalue = tmpvalue.replace("\r\n", "");
                    }

                    if(tmpvalue == ""){
                        if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
                            if(window.__oriAlert__){
                                window.top.Dialog.alert("\""+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
                            }else{
                                var tempElement = thiswin.elements[i-1];
                                //ueditor必填验证
                                if (checkueditorContent(tempElement)) {
                                    continue;
                                }

                                window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
                                    formElementFocus(tempElement);
                                });
                            }
                            return false;
                        }else{
                            if(window.__oriAlert__){
                                try{
                                    window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                                }catch(e){
                                    Dialog.alert("必要信息不完整，红色星号为必填项！");
                                }
                            }else{
                                try{
                                    window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                                }catch(e){
                                    Dialog.alert("必要信息不完整，红色星号为必填项！");
                                }
                            }
                            return false;
                        }
                    }
                } else {
                    var divttt=document.createElement("div");
                    divttt.innerHTML = tmpvalue;
                    var tmpvaluettt = jQuery.trim(jQuery(divttt).text());
                    if(tmpvaluettt == ""){
                        if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
                            if(window.__oriAlert__){
                                window.top.Dialog.alert("\";"+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
                            }else{
                                var tempElement = thiswin.elements[i-1];

                                //ueditor必填验证
                                if (checkueditorContent(tempElement)) {
                                    continue;
                                }

                                window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
                                    formElementFocus(tempElement);
                                });

                            }
                            return false;
                        }else{
                            if(window.__oriAlert__){
                                window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }else{
                                window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
                            }
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
</script>
</body>
</html>
