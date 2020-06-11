<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.*" %>
<%@ page import="rrd.supplier.dao.SupplierInInfoDao" %>
<%@ page import="rrd.supplier.util.TransUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="weaver.systeminfo.*" %>
<%@ page import="java.net.URLEncoder" %>
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
    boolean flag =tu.checkIfOvertime(urlKey);
    if(flag){
        out.print("连接已超时失效，如还未填写，请联系RRD对应的资源或采购人员发送新网页连接");
        return;
    }
    String zcid = sisi.getSupplierTemporaryDataId(rqid);
    if("".equals(zcid)){
        out.print("系统错误，请联系管理员");
        return;
    }
    SupplierInInfoDao sid = sisi.getSupplierTemporaryData(zcid);
    if("2".equals(sid.getStatus())){
        out.print("供应商信息已提交，无法重复提交");
        return;
    }
    String accsize ="20";
    String accsec= Util.null2o(weaver.file.Prop.getPropValue("rrdsupplierzr", "zcsecid"));
    String sql = "";
    String chkFields = "gsxz,ssqy,gysmc,szhy,jyzx,ywfwgjyyzz,clsj,gsdh,gyszcdz,zczbw,nxsew,gysjydz,fddbr,glzlxr," +
            "yddh,yxdz,rcswlxr,yddh1,yxdz1,tyshxydm,sfysjkfnl,sjryslr,kha,hya,zba,khb,hyb,zbb," +
            "khc,hyc,zbc,yyzzlysm,yhkhxxlysm,syddhlysm,sl," +
            "sfyyyzzjggz,sfyyhkhxxjggz,sfysyddhjggz,sfycwxgfj,cwrwqsmly,bz1,khh1,yhzh1";
%>
<head>
    <title>供应商信息收集</title>
    <LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
    <!--<link href="/css/rp_wev8.css" rel="STYLESHEET" type="text/css">-->
    <link href="/workflow/exceldesign/css/excelHtml_wev8.css" rel="STYLESHEET" type="text/css">
    <link type="text/css" rel="stylesheet" href="/wui/theme/ecology8/skins/default/wui_wev8.css">
    <script type="text/javascript" src="/wui/common/jquery/jquery.min_wev8.js"></script>
    <!--<script language="javascript" type="text/javascript" src="/js/init_wev8.js"></script>-->
    <script language=javascript src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>
    <link rel="stylesheet" href="/css/ecology8/request/requestTopMenu_wev8.css" type="text/css" />
    <link rel="stylesheet" href="/wui/theme/ecology8/jquery/js/zDialog_e8_wev8.css" type="text/css" />
    <link href="/js/swfupload/default_wev8.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/ecology8/lang/weaver_lang_7_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/swfupload_wev8.js?v2"></script>
    <script type="text/javascript" src="/js/swfupload/swfupload.queue_wev8.js"></script>

    <!-- <script type="text/javascript" src="/js/swfupload/fileprogressBywf_wev8.js"></script>
   <script type="text/javascript" src="/js/swfupload/workflowswfupload_wev8.js"></script>-->

  <script type="text/javascript" src="/js/swfupload/fileprogress_wev8.js"></script>
    <script type="text/javascript" src="/js/swfupload/handlers_wev8.js"></script>
    <script type="text/javascript" src="/js/weaver_wev8.js"></script>
    <link href="/rrd/supplier/supplierzr.css?<%=System.currentTimeMillis()%>" rel="stylesheet" type="text/css" />
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
        <div class="contentbody" id="divinner"style="width: 100%;overflow: auto;background-color: #cccccc73;height: 873px;overflow-x: hidden;overflow-y: auto;position: relative;z-index: 0;">
            <div style="height: 100%;overflow:hidden;display:block;margin:0;padding:0;">
                <div style="height: 100%;overflow-x: hidden;overflow-y: auto;position: relative;">
                   <div style="margin: 0 auto;">
                    <div  style="margin: 30px 50px;padding:0;">
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
                        <input type="hidden" name="urlKey" id="urlKey" value="<%=URLEncoder.encode(Util.null2String(request.getParameter("urlKey")),"UTF-8")%>">
                        <input type="hidden" name="submittype" id="submittype" value="">
                        <input type="hidden" name="chkFields" id="chkFields" value="<%=chkFields%>">
                        <input type="hidden" name="gysmccf" id="gysmccf" value="">
                        <input type="hidden" name="tyshxydmcf" id="tyshxydmcf" value="">
                        <div style="margin: 0px auto 20px; background-color: #fff;-webkit-box-shadow: 0 0 5px #ddd;box-shadow: 0 0 5px #ddd;">
                            <div style="margin:0;padding: 0">
                            <table class="excelMainTable" style="margin: 0px auto; width: 100%;border:0;padding:0;">
                                <colgroup><col style="width: 5%;"><col style="width: 14%;"><col style="width: 17%;"><col style="width: 14%;"><col style="width: 5%;"><col style="width: 11%;"><col style="width: 1%;"><col style="width: 12%;"><col style="width: 13%;"><col style="width: 1%;"></colgroup>
                                <tbody>

                                <tr style="height: 51px; display: table-row;">
                                    <td valign="middle" colspan="10" class="" style="text-align: left; vertical-align: middle; font-size: 20pt;  padding-left: 36px; line-height: 1;">
                                        <div style="width: 100%;">
                                            <div style="font-size: 20pt; font-family:'Microsoft YaHei'; word-break: break-all; overflow-wrap: break-word;text-align: center;">供应商信息收集</div>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="height: 21px; display: table-row;">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>

                                </tr>

                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103936.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" class="td_title2"colspan="2" ><div style="width: 100%;"><div class="div_fontstyle2" >基本信息</div></div></td>
                                    <td></td><td></td><td></td>
                                    <td valign="middle" colspan="4" class="" style="border-top: 2px solid rgb(153, 153, 153); background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; font-weight: bold; font-size: 12pt; color: black;  padding-left: 8px; line-height: 1;"><div style="width: 100%;"><div class="div_fontstyle2" >供应商关键信息</div></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">公司性质：</div></td>

                                    <td class="td_fieldvalue"><div width="100%"><%=tu.getSelectHtmlTable2("gsxz",sid.getGsxz(),"1","uf_gsxz","gsxz","id","id","asc","180")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">所属行业：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable2("szhy",sid.getSzhy(),"1","uf_khsshy","drdl01","id","DRKY","asc","180")%></div></td>
                                    <td class="td_grey" ></td>
                                    <td valign="middle" colspan="2" class="td_bordtop" ><div style="width: 100%;"><div  class="div_fieldname" >供应商名称</div></div></td>
                                    <td class="td_grey" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">经营属性：</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("zzs",sid.getZzs(),"checkbox","1","制造商","")%>
                                        <%=sisi.getFieldHtml("fxs",sid.getFxs(),"checkbox","1","分销商","")%>
                                        <%=sisi.getFieldHtml("fws",sid.getFws(),"checkbox","1","服务商","")%>
                                    </div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">法定代表人：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("fddbr",sid.getFddbr(),"text","1","100","26")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="2" class="td_bordbottom"><div width="100%"><%=sisi.getFieldHtml("gysmc",sid.getGysmc(),"text","1","150","35")%></div></td>
                                    <td class="td_grey"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">成立时间：</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <button type="button" class=Calendar id="selectclsj" onclick="onshowPlanDate1('clsj','selectclsjSpan','1')"></BUTTON>
                                        <SPAN id="selectclsjSpan" >
                                                <%if("".equals(sid.getClsj())){%>
                                                <IMG src='/images/BacoError_wev9.png' align=absMiddle>
                                                <%}%>
                                                <%=sid.getClsj()%></SPAN>
                                        <INPUT type="hidden" name="clsj" id="clsj" value="<%=sid.getClsj()%>">
                                    </div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">公司电话：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("gsdh",sid.getGsdh(),"text","1","50","26")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="2" class="td_bordtop"><div style="width: 100%;"><div  class="div_fieldname" >统一社会信用代码</div></div></td>
                                    <td class="td_grey"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">注册资本（万）：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("zczbw",sid.getZczbw(),"float","1","2","")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">年销售额（万）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("nxsew",sid.getNxsew(),"float","1","2","")%></div></td>
                                    <td class="td_grey"></td>
                                    <td valign="middle" colspan="2" class="td_bordbottom"><div width="100%"><%=sisi.getFieldHtml("tyshxydm",sid.getTyshxydm(),"text","1","20","35")%></div></td>
                                    <td class="td_grey"></td>
                                </tr>

                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商网址：</div></td>
                                    <td class="td_fieldvalue" colspan="4"><div width="100%"><%=sisi.getFieldHtml("gyswz",sid.getGyswz(),"text","0","100","70")%></div></td>
                                    <td class=""  colspan="4" style="background-color: rgb(239, 239, 239); text-align: left; vertical-align: middle; color: black; line-height: 1;"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商注册地址：</div></td>
                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("gyszcdz",sid.getGyszcdz(),"text","1","160","120")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">供应商经营地址：</div></td>
                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("gysjydz",sid.getGysjydz(),"text","1","160","120")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">管理者联系人：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("glzlxr",sid.getGlzlxr(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">移动电话：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yddh",sid.getYddh(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">邮箱地址：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yxdz",sid.getYxdz(),"text","1","100","22")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">日常事务联系人：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("rcswlxr",sid.getRcswlxr(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">移动电话：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yddh1",sid.getYddh1(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">邮箱地址：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("yxdz1",sid.getYxdz1(),"text","1","100","22")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">业务范围（根据营业执照）：</div></td>
                                    <td class="td_fieldvalue" colspan="8"><div width="100%">
                                        <textarea class="Inputstyle" temptype="1" viewtype="1" temptitle="业务范围（根据营业执照）" id="ywfwgjyyzz" name="ywfwgjyyzz" rows="4" onchange="checkmustinput('ywfwgjyyzz','ywfwgjyyzzspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word"><%=sid.getYwfwgjyyzz()%></textarea>
                                        <span id="ywfwgjyyzzspan">
                                                  <%if("".equals(sid.getYwfwgjyyzz())){%>
                                                <IMG src='/images/BacoError_wev9.png' align=absMiddle>
                                                <%}%>

                                            </span>
                                    </div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="3" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103954.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" class="td_title2" colspan="2"><div style="width: 100%;"><div class="div_fontstyle2" >财务信息</div></div></td>
                                    <td colspan="7"></td>
                                </tr>
                                <tr class="tr_normal">

                                    <td class="td_fieldnameright"><div class="div_fieldname">税率：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable("sl",sid.getSl(),"1","uf_sl","tatxa","id","tatxa1","asc")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">关联公司：</div></td>
                                    <td class="td_fieldvalue" colspan="4"><div width="100%"><%=sisi.getFieldHtml("glgs",sid.getGlgs(),"text","0","100","26")%></div></td>

                                </tr>
                                <tr class="tr_normal">

                                    <td class="td_fieldnamecenter" colspan="1"><div width="100%">币种（第1行为默认交易币种）</div></td>
                                    <td class="td_fieldnamecenter" colspan="2"><div width="100%">开户行</div></td>
                                    <td class="td_fieldnamecenter" colspan="4"><div width="100%">银行账号</div></td>
                                    <td class="td_fieldnamecenter" colspan="2"><div width="100%">Swift Code</div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldcenter" colspan="1" s><div width="100%"><%=tu.getSelectHtmlTable2("bz1",sid.getBz1(),"1","uf_jyhb","cvcrcd","id","cvcrcd","desc","120")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("khh1",sid.getKhh1(),"text","1","100","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="4"><div width="100%"><%=sisi.getFieldHtml("yhzh1",sid.getYhzh1(),"text","1","50","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("swiftcode1",sid.getSwiftcode1(),"text","0","200","20")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldcenter" colspan="1"><div width="100%"><%=tu.getSelectHtmlTable2("bz2",sid.getBz2(),"0","uf_jyhb","cvcrcd","id","cvcrcd","desc","120")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("khh2",sid.getKhh2(),"text","0","100","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="4"><div width="100%"><%=sisi.getFieldHtml("yhzh2",sid.getYhzh2(),"text","0","50","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("swiftcode2",sid.getSwiftcode2(),"text","0","200","20")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldcenter" colspan="1"><div width="100%"><%=tu.getSelectHtmlTable2("bz3",sid.getBz3(),"0","uf_jyhb","cvcrcd","id","cvcrcd","desc","120")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("khh3",sid.getKhh3(),"text","0","100","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="4"><div width="100%"><%=sisi.getFieldHtml("yhzh3",sid.getYhzh3(),"text","0","50","50")%></div></td>
                                    <td class="td_fieldcenter" colspan="2"><div width="100%"><%=sisi.getFieldHtml("swiftcode3",sid.getSwiftcode3(),"text","0","200","20")%></div></td>
                                </tr>

                                <tr class="tr_normal">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal" id="zcs_1">
                                    <td rowspan="2" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103936.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="3" class="" style="border-top: 2px solid rgb(153, 153, 153); text-align: left; vertical-align: middle; font-weight: bold; font-size: 12pt;  padding-left: 24px; line-height: 1;"><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >生产和业务能力（制造商）</div></div></div></td>
                                    <td colspan="6" ></td>

                                </tr>
                                <tr class="tr_normal" id="zcs_2">
                                    <td class="td_fieldnameright" ><div class="div_fieldname">员工总数（人）：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("ygzsr",sid.getYgzsr(),"int","0","","")%></div></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">办公场所面积（㎡）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("bgcsmjm2",sid.getBgcsmjm2(),"float","0","2","")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">生产场所面积（㎡）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("sccsmjm2",sid.getSccsmjm2(),"float","0","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal" id="zcs_3">
                                    <td></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">管理人员总数（人）：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("glryzsr",sid.getGlryzsr(),"int","0","","")%></div></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">仓库面积（㎡）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("ckmjm2",sid.getCkmjm2(),"float","0","2","")%></div></td>
                                    <td  colspan="2"></td>
                                    <td  colspan="2"></td>
                                </tr>
                                <tr class="tr_normal" id="zcs_4">
                                    <td></td>
                                    <td class="td_fieldnameright" ><div class="div_fieldname">设备和产品能力描述：</div></td>
                                    <td class="td_fieldvalue" colspan="8"><div width="100%">
                                        <textarea class="Inputstyle" temptype="1" viewtype="0" temptitle="设备和产品能力描述" id="sbhcpnlms" name="sbhcpnlms" rows="4" onchange="checkmustinput('sbhcpnlms','sbhcpnlmsspan',this.getAttribute('viewtype'));" cols="40"  style="width:80%;word-break:break-all;word-wrap:break-word" value=""><%=sid.getSbhcpnlms()%></textarea>
                                        <span id="sbhcpnlmsspan">

                                            </span>
                                    </div></td>
                                </tr>


                                <tr class="tr_normal" id="zcs_5">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103954.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="4" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >设计&开发能力</div></div></div></td>
                                    <td colspan="5"></td>

                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">是否有设计开发能力：</div></td>
                                    <td class="td_fieldvalue"><div width="100%"><%=sisi.getFieldHtml("sfysjkfnl",sid.getSfysjkfnl(),"select","1","180","")%></div></td>
                                    <td class="td_fieldnameright" id="sjrysl_1"><div class="div_fieldname">设计人员数量（人）：</div></td>
                                    <td class="td_fieldvalue" colspan="2" id="sjrysl_3"><div width="100%"><%=sisi.getFieldHtml("sjryslr",sid.getSjryslr(),"int","1","","")%></div></td>
                                    <td colspan="4"></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103936.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="3" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >主要客户情况</div></div></div></td>
                                    <td colspan="6"></td>

                                </tr>
                                <tr class="tr_normal">
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户A：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("kha",sid.getKha(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable2("hya",sid.getHya(),"1","uf_khsshy","drdl01","id","DRKY","asc","180")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比（%）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zba",sid.getZba(),"float","1","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户B：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("khb",sid.getKhb(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable2("hyb",sid.getHyb(),"1","uf_khsshy","drdl01","id","DRKY","asc","180")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比（%）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zbb",sid.getZbb(),"float","1","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">客户C：</div></td>
                                    <td class="td_fieldvalue" ><div width="100%"><%=sisi.getFieldHtml("khc",sid.getKhc(),"text","1","100","26")%></div></td>
                                    <td class="td_fieldnameright"><div class="div_fieldname">行业：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=tu.getSelectHtmlTable2("hyc",sid.getHyc(),"1","uf_khsshy","drdl01","id","DRKY","asc","180")%></div></td>
                                    <td class="td_fieldnameright" colspan="2"><div class="div_fieldname">占比（%）：</div></td>
                                    <td class="td_fieldvalue" colspan="2"><div width="100%"><%=sisi.getFieldHtml("zbc",sid.getZbc(),"float","1","2","")%></div></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td valign="middle" colspan="10" class="td_xh" style="text-align: left; vertical-align: middle; line-height: 1;" ></td>
                                </tr>
                                <tr class="tr_normal">
                                    <td rowspan="2" class="td_image" style="background-image: url(/filesystem/exceldesign/uploadimg/uploadImg_2020225103954.jpg) !important;"><div style="width: 100%;"></div></td>
                                    <td valign="middle" colspan="3" class="td_title2" ><div style="width: 100%;"><div style="width: 100%;"><div class="div_fontstyle2" >支持文件上传</div></div></div></td>
                                    <td colspan="6" ></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >营业执照(加盖公章)</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfyyyzzjggz",sid.getSfyyyzzjggz(),"select","1","100","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
                                        <div width="100%" id="yyzzfjyc">
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
                                     <div style="height: 32px;vertical-align:middle;">
                                          <span id="uploadspan" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                          <span id="yyzzjggzspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                              必填
                                          </span>
                                     </div>
                                     <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_yyzzjggz"></span><!--选取多个文件-->
                                            </span>
                                        &nbsp;&nbsp;
                                         <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_yyzzjggz.cancelQueue();" id="btnCancel1_yyzzjggz">
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
                                    <td class="td_fieldnameright" colspan="5"></td>

                                </tr>
                                <tr class="tr_normal1" id="yyzzxzyc">
                                    <td></td>
                                    <td class="td_fieldnamemid"  colspan="1" style="text-align: left;padding-left: 40px;"><div class="div_fieldname" style="color:#a4c2f4;">如无请说明理由：</div></td>

                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("yyzzlysm",sid.getYyzzlysm(),"text","1","200","120")%></div></td>


                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >银行开户信息(加盖公章)</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfyyhkhxxjggz",sid.getSfyyhkhxxjggz(),"select","1","100","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
                                        <div width="100%" id="yhkhfjyc">
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
                                            <div style="height: 32px;vertical-align:middle;">
                                                <span id="uploadspan1" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                <span id="yhkhxxjggzspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                              必填
                                          </span>
                                            </div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_yhkhxxjggz"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_yhkhxxjggz.cancelQueue();" id="btnCancel1_yhkhxxjggz">
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
                                    <td class="td_fieldnameright" colspan="5"></td>
                                </tr>
                                <tr class="tr_normal1" id="yhkhxzyc">
                                    <td></td>
                                    <td class="td_fieldnamemid"  colspan="1" style="text-align: left;padding-left:40px;"><div class="div_fieldname" style="color:#a4c2f4;">如无请说明理由：</div></td>

                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("yhkhxxlysm",sid.getYhkhxxlysm(),"text","1","200","120")%></div></td>


                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >商业道德函(加盖公章)</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfysyddhjggz",sid.getSfysyddhjggz(),"select","1","100","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
                                        <div width="100%" id="syddfjyc">
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
                                            <div style="height: 32px;vertical-align:middle;">
                                                <span id="uploadspan2" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                <span id="syddhjggzspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                              必填
                                          </span>
                                            </div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_syddhjggz"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_syddhjggz.cancelQueue();" id="btnCancel1_syddhjggz">
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
                                    <td class="td_fieldnameright" colspan="1"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="4"><div width="100%">
                                        &nbsp;&nbsp;&nbsp;&nbsp;<a href="/rrd/supplier/SupplierDownload?type=2&urlKey=<%=URLEncoder.encode(Util.null2String(request.getParameter("urlKey")),"UTF-8")%>" target="_blank">致供应商的一封信</a>
                                    </div></td>
                                </tr>
                                <tr class="tr_normal1" id="syddxzyc">
                                    <td></td>
                                    <td class="td_fieldnamemid"  colspan="1" style="text-align: left;padding-left: 40px;"><div class="div_fieldname" style="color:#a4c2f4;">如无请说明理由：</div></td>

                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("syddhlysm",sid.getSyddhlysm(),"text","1","200","120")%></div></td>


                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >财务关键信息</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                        <%=sisi.getFieldHtml("sfycwxgfj",sid.getSfycwxgfj(),"select","1","100","")%>
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
                                        <div width="100%" id="cwxxfjyc">
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
                                            <div style="height: 32px;vertical-align:middle;">
                                                <span id="uploadspan3" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                <span id="cwgjxxspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                              必填
                                          </span>
                                            </div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_cwgjxx"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_cwgjxx.cancelQueue();" id="btnCancel1_cwgjxx">
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
                                    <td class="td_fieldnameright" colspan="1"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="4"><div width="100%">
                                        &nbsp;&nbsp;&nbsp;&nbsp;<a href="/rrd/supplier/SupplierDownload?type=0&urlKey=<%=URLEncoder.encode(Util.null2String(request.getParameter("urlKey")),"UTF-8")%>" target="_blank">供应商财务关键信息清单_中文</a>
                                    </div></td>

                                </tr>
                                <tr class="tr_normal1" id="cwxxxzyc">
                                    <td></td>
                                    <td class="td_fieldnamemid"  colspan="1" style="text-align: left;padding-left: 40px;"><div class="div_fieldname" style="color:#a4c2f4;">如无请说明理由：</div></td>

                                    <td class="td_fieldvalue" colspan="8"><div width="100%"><%=sisi.getFieldHtml("cwrwqsmly",sid.getCwrwqsmly(),"text","1","200","120")%></div></td>


                                </tr>

                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid" ><div class="div_fieldname" >公司基本信息截屏要求</div>
                                    </td>
                                    <td></td>
                                    <td class="td_fieldvalue" colspan="2">
                                        <div width="100%" id="gsjbxxfjyc">
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
                                                <div style="height: 32px;vertical-align:middle;">
                                                    <span id="uploadspan5" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                    <span id="gsjbxxjpyqspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                              必填
                                          </span>
                                                </div>
                                                    <div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_gsjbxxjpyq"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_gsjbxxjpyq.cancelQueue();" id="btnCancel1_gsjbxxjpyq">
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
                                    <td class="td_fieldnameright" colspan="1"><div class="div_fieldname"></div></td>
                                    <td class="td_fieldvalue" colspan="4"><div width="100%"stye>
                                        &nbsp;&nbsp;&nbsp;&nbsp;<a href="/rrd/supplier/SupplierDownload?type=1&urlKey=<%=URLEncoder.encode(Util.null2String(request.getParameter("urlKey")),"UTF-8")%>" target="_blank">公司基本信息截屏要求（境内企业）</a>

                                    </div></td>

                                </tr>

                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >资质授权</div></td>

                                    <td class="td_fieldvalue"><div width="100%">

                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
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
                                            <div style="height: 32px;vertical-align:middle;">
                                                <span id="uploadspan4" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                <span id="zzsqspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                          </span>
                                            </div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_zzsq"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_zzsq.cancelQueue();" id="btnCancel1_zzsq">
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
                                    <td colspan="5"></td>

                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid" colspan="1"><div class="div_fieldname" >补充信息</div></td>
                                    <td></td>
                                    <td class="td_fieldvalue" colspan="2">
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
                                                <div style="height: 32px;vertical-align:middle;">
                                                    <span id="uploadspan6" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                    <span id="rbcxxkytjbxspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                          </span>
                                                </div>
                                            <div>
                                        <span> 
                                            <span id="spanButtonPlaceHolder_rbcxxkytjbx"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_rbcxxkytjbx.cancelQueue();" id="btnCancel1_rbcxxkytjbx">
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
                                    <td colspan="5"></td>


                                </tr>
                                <tr class="tr_normal1">
                                    <td></td>
                                    <td class="td_fieldnamemid"><div class="div_fieldname" >设备清单</div></td>
                                    <td class="td_fieldvalue"><div width="100%">
                                    </div></td>
                                    <td class="td_fieldvalue" colspan="2">
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
                                                <div style="height: 32px;vertical-align:middle;">
                                                    <span id="uploadspan7" style="display:inline-block;line-height: 32px;">最大20M/个</span>
                                                    <span id="sbqdspan" style="display:inline-block;line-height: 32px;color:red !important;font-weight:normal;">
                                          </span>
                                                </div>
                                            <div>
                                        <span>
                                            <span id="spanButtonPlaceHolder_sbqd"></span><!--选取多个文件-->
                                            </span>
                                                &nbsp;&nbsp;
                                                <span style="color:#262626;cursor:hand;TEXT-DECORATION:none" disabled onclick="oUpload_sbqd.cancelQueue();" id="btnCancel1_sbqd">
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
                                    <td colspan="5"></td>

                                </tr>



                                </tbody>
                            </table>

                        </div>
                        </div>
                    </FORM>
                </div>
                   </div>
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
                cancelButtonId : "btnCancel1_yyzzjggz",
                uploadspan : "yyzzjggzspan",
                uploadfiedid:"yyzzjggz"
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
            queue_complete_handler : queueComplete,	// Queue plugin event
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
                cancelButtonId : "btnCancel",
                uploadspan : "yhkhxxjggzspan",
                uploadfiedid:"yhkhxxjggz"
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
                cancelButtonId : "btnCancel",
                uploadspan : "syddhjggzspan",
                uploadfiedid:"syddhjggz"
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
                cancelButtonId : "btnCancel",
                uploadspan : "cwgjxxspan",
                uploadfiedid:"cwgjxx"
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
                cancelButtonId : "btnCancel",
                uploadspan : "zzsqspan",
                uploadfiedid:"zzsq"
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
                cancelButtonId : "btnCancel",
                uploadspan : "sbqdspan",
                uploadfiedid:"sbqd"
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
                cancelButtonId : "btnCancel",
                uploadspan : "rbcxxkytjbxspan",
                uploadfiedid:"rbcxxkytjbx"
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
            post_params: {"method" : "uploadPrjAcc","secid":"<%=accsec%>"},
            file_size_limit : "<%=accsize %> MB",
            file_types : "*.*",
            file_types_description : "All Files",
            file_upload_limit : 100,
            file_queue_limit : 0,
            custom_settings : {
                progressTarget : "fsUploadProgress_gsjbxxjpyq",
                cancelButtonId : "btnCancel",
                uploadspan : "gsjbxxjpyqspan",
                uploadfiedid:"gsjbxxjpyq"
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
        if(fieldname=="zzs"){
            showHidezzs();
        }
    }
    function  showHidezzs(){
        var zzs_val = jQuery("#zzs").val();
        if(zzs_val=="1"){
            jQuery("#zcs_1").show();
            jQuery("#zcs_2").show();
            jQuery("#zcs_3").show();
            jQuery("#zcs_4").show();
            jQuery("#zcs_5").show();
            addcheck("ygzsr","0");
            addcheck("bgcsmjm2","0");
            addcheck("sccsmjm2","0");
            addcheck("glryzsr","0");
            addcheck("ckmjm2","0");
            addcheck("sbhcpnlms","0");
        }else{
            jQuery("#zcs_1").hide();
            jQuery("#zcs_2").hide();
            jQuery("#zcs_3").hide();
            jQuery("#zcs_4").hide();
            jQuery("#zcs_5").hide();
            removecheck("ygzsr","0");
            removecheck("bgcsmjm2","0");
            removecheck("sccsmjm2","0");
            removecheck("glryzsr","0");
            removecheck("ckmjm2","0");
            removecheck("sbhcpnlms","0");
            jQuery("#ygzsr").val("");
            jQuery("#bgcsmjm2").val("");
            jQuery("#sccsmjm2").val("");
            jQuery("#glryzsr").val("");
            jQuery("#ckmjm2").val("");
            jQuery("#sbhcpnlms").val("");
        }
    }
    function bindfjbt(fjid){
        var fj_val = jQuery("#"+fjid).val();
        if(fj_val !=""){
            jQuery("#"+fjid+"span").html("");
        }
        jQuery("#fsUploadProgress_"+fjid).bind('DOMNodeInserted', function(e) {
            jQuery("#"+fjid+"span").html("");
        });
    }
    jQuery(document).ready(function () {
        bindfjbt("yyzzjggz");
        bindfjbt("yhkhxxjggz");
        bindfjbt("syddhjggz");
        bindfjbt("cwgjxx");
        //bindfjbt("zzsq");
        //bindfjbt("sbqd");
       // bindfjbt("rbcxxkytjbx");
        bindfjbt("gsjbxxjpyq");


        jQuery("#sfyyyzzjggz").bind("change",function(){
            changemustfield("1");
        })
        jQuery("#sfyyhkhxxjggz").bind("change",function(){
            changemustfield("2");
        })
        jQuery("#sfysyddhjggz").bind("change",function(){
            changemustfield("3");
        })
        jQuery("#sfysjkfnl").bind("change",function(){
            changemustfield("4");
        })

        jQuery("#sfycwxgfj").bind("change",function(){
            changemustfield("6");
        })
        jQuery("#gsxz").bind("change",function(){
            changemustfield("7");
        })
        jQuery("#bz1").bind("change",function(){
            changemustfield("8");
        })
        jQuery("#bz2").bind("change",function(){
            changemustfield("9");
        })
        jQuery("#bz3").bind("change",function(){
            changemustfield("10");
        })

        jQuery("#gysmc").bind("change",function(){
            checknameandcode("0");
            var gysmccf_val = jQuery("#gysmccf").val();
            if(gysmccf_val == "1"){
                alert("供应商名称重复，请确认后填写");
            }else{
            }
        })
        jQuery("#tyshxydm").bind("change",function(){
            checknameandcode("1");
            var tyshxydmcf_val = jQuery("#tyshxydmcf").val();
            if(tyshxydmcf_val == "1"){
                alert("统一社会信用代码重复，请确认后填写");
            }else{
            }
        })
        changemustfield("0");
        showHidezzs();
    })

    function checknameandcode(type){
        var gysmc_val = jQuery("#gysmc").val();
        var tyshxydm_val = jQuery("#tyshxydm").val();
        var checkresult = "";
        if(type == 0){
            tyshxydm_val = "";
        }else if(type ==1){
            gysmc_val = "";
        }
        jQuery.ajax({
            type: "POST",
            url: "/rrd/supplier/mode/checknameandcode.jsp",
            data: {"gysmc":gysmc_val,"tyshxydm":tyshxydm_val,"billid":"-1"},
            dataType: "text",
            async:false,//同步 true异步
            success: function(data){
                data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
                checkresult = data;

            }

        });
        // if(checkresult == "0"){
        //     alert("供应商信息不重复，请填写其他信息");
        // }else if(checkresult == "1"){
        //     alert("供应商有重复信息，请确认后填写");
        // }
        if(type == 0){
            jQuery("#gysmccf").val(checkresult);

        }else if(type ==1){
            jQuery("#tyshxydmcf").val(checkresult);
        }
    }

    /**
     *
     * @param xzvalue 选择value
     * @param smid 说明id
     * @param fjid 附件id
     * @param smycid 说明隐藏
     * @param fjycid 附件隐藏
     * @param oUpload 附件上传对象
     */
    function addremovefjcheck(xzvalue,smid,fjid,smycid,fjycid,oUpload){
        if (xzvalue == "1") {
            addcheck(smid, "0");
            removecheck(fjid, "1");
            jQuery("#"+smycid).show();
            jQuery("#"+fjycid).hide();
            try{
                oUpload.cancelQueue();
            }catch (err) {
                return;
            }

        }else{
            addcheck(fjid, "1");
            removecheck(smid, "0");
            jQuery("#"+smycid).hide();
            jQuery("#"+fjycid).show();
            jQuery("#"+smid).val("");
            if(jQuery("#"+fjid).val()==""){
                jQuery("#"+fjid+"span").html("必填");
            }else{
                jQuery("#"+fjid+"span").html("");
            }
        }
    }
    function changemustfield(type){
        var sfyyyxgfj_val = jQuery("#sfyyyzzjggz").val();//是否有营业相关附件
        var sfyyhxgfj_val = jQuery("#sfyyhkhxxjggz").val();//是否有银行相关附件
        var sfysyxgfj_val = jQuery("#sfysyddhjggz").val();//是否有商业相关附件
        var sfysjkfnl_val = jQuery("#sfysjkfnl").val();//是否涉及开发能力
        var sfycwxgfj_val = jQuery("#sfycwxgfj").val();//是否有财务相关附件
        var gsxz_val = jQuery("#gsxz").val();//公司性质
        var bz1_val = jQuery("#bz1 option:selected").text();//币种1
        var bz2_val = jQuery("#bz2 option:selected").text();//币种1
        var bz3_val = jQuery("#bz3 option:selected").text();//币种1

        if(type == "0" || type=="1") {
            addremovefjcheck(sfyyyxgfj_val,"yyzzlysm","yyzzjggz","yyzzxzyc","yyzzfjyc",oUpload_yyzzjggz);
        }
        if(type == "0" || type=="2") {
            addremovefjcheck(sfyyhxgfj_val,"yhkhxxlysm","yhkhxxjggz","yhkhxzyc","yhkhfjyc",oUpload_yhkhxxjggz);

        }
        if(type == "0" || type=="3") {
            addremovefjcheck(sfysyxgfj_val,"syddhlysm","syddhjggz","syddxzyc","syddfjyc",oUpload_syddhjggz);
        }
        if(type == "0" || type=="6") {
            addremovefjcheck(sfycwxgfj_val,"cwrwqsmly","cwgjxx","cwxxxzyc","cwxxfjyc",oUpload_cwgjxx);
        }
        if(type == "0" || type=="7") {

            if (gsxz_val == "36" || gsxz_val == "32") {
                removecheck("gsjbxxjpyq", "1");


            }else{
                addcheck("gsjbxxjpyq", "1");
                if(jQuery("#gsjbxxjpyq").val()==""){
                    jQuery("#gsjbxxjpyqspan").html("必填");
                }else{
                    jQuery("#gsjbxxjpyqspan").html("");
                }

            }
        }
        if(type == "0" || type=="4"){
            if(sfysjkfnl_val == "1" || sfysjkfnl_val == ""){
                removecheck("sjryslr","0")
                jQuery("#sjryslr").val("");
                jQuery("#sjrysl_1").hide();
                jQuery("#sjrysl_2").hide();
                jQuery("#sjrysl_3").hide();
            }else{
                addcheck("sjryslr","0");
                jQuery("#sjrysl_1").show();
                jQuery("#sjrysl_2").show();
                jQuery("#sjrysl_3").show();
            }
        }
        if(type == "0" || type=="8"){
            if(bz1_val=="RMB"){
                removecheck("swiftcode1","0")
                jQuery("#swiftcode1").val("");
                jQuery("#swiftcode1").attr('disabled',true);

            }else if(bz1_val==""){
                removecheck("swiftcode1","0")
                jQuery("#swiftcode1").attr('disabled',false);
            }else{
                jQuery("#swiftcode1").attr('disabled',false);
                addcheck("swiftcode1","0")
            }
        }
        if(type == "0" || type=="9"){
            if(bz2_val=="RMB"){
                addcheck("khh2","0");
                addcheck("yhzh2","0");
                removecheck("swiftcode2","0")
                jQuery("#swiftcode2").val("");
                jQuery("#swiftcode2").attr('disabled',true);

            }else if(bz2_val==""){
                removecheck("khh2","0");
                removecheck("yhzh2","0");
                removecheck("swiftcode2","0")
                jQuery("#swiftcode2").attr('disabled',false);
            }else{
                addcheck("khh2","0");
                addcheck("yhzh2","0");
                jQuery("#swiftcode2").attr('disabled',false);
                addcheck("swiftcode2","0")
            }
        }
        if(type == "0" || type=="10"){
            if(bz3_val=="RMB"){
                addcheck("khh3","0");
                addcheck("yhzh3","0");
                removecheck("swiftcode3","0")
                jQuery("#swiftcode3").val("");
                jQuery("#swiftcode3").attr('disabled',true);

            }else if(bz3_val==""){
                removecheck("khh3","0");
                removecheck("yhzh3","0");
                removecheck("swiftcode3","0")
                jQuery("#swiftcode3").attr('disabled',false);
            }else{
                addcheck("khh3","0");
                addcheck("yhzh3","0");
                jQuery("#swiftcode3").attr('disabled',false);
                addcheck("swiftcode3","0")
            }
        }


    }
    function addcheck(btid,flag){
        var btid_val = jQuery("#"+btid).val();
        var btid_check=btid;
        if(btid_val==''){
            if(flag=='0'){
                jQuery("#"+btid+"span").html("<img align='absmiddle' src='/images/BacoError_wev9.png'>");

            }else{
                jQuery("#"+btid+"spang").html("必填");
            }

        }
        if(flag=='0'){
            jQuery("#"+btid).attr("viewtype","1");
            var chkFields = jQuery("#chkFields").val();
            if(chkFields.indexOf(","+btid_check)<0){
                if(chkFields !='') chkFields+=",";
                chkFields+=btid_check;
            }
            jQuery("#chkFields").val(chkFields);
        }

    }

    function removecheck(btid,flag){
        if(flag=='0'){
            jQuery("#"+btid+"span").html("");
        }else{
            jQuery("#"+btid+"span").html("");
        }
        if(flag=='0'){
            var chkFields = jQuery("#chkFields").val();
            chkFields = ","+chkFields+",";
            chkFields=chkFields.replace(","+btid+",",",");
            if(chkFields.length>1){
                chkFields=chkFields.substring(1,chkFields.length-1);
            }
            jQuery("#chkFields").val(chkFields);
        }

    }

    var flag = "0"
    function save(type){
        var wjge=",.doc,.docx,.xls,.xlsx,.jpg,.jpeg,.jepg,.jif,.txt,.htm,.html,.ppt,.pptx,.pdf,.png,";
        jQuery("#submittype").val(type);
        var flag_yyzzjggz="1";
        var flag_yhkhxxjggz="1";
        var flag_syddhjggz ="1";
        var flag_cwgjxx = "1";
        var flag_zzsq="1";
        var flag_sbqd ="1";
        var flag_rbcxxkytjbx = "1";
        var flag_gsjbxxjpyq ="1";
        //sunmit 点击事件
        var info = jQuery("#info").val();
        if (flag != "0") {
            alert("请勿重复提交");
            return;
        } else {
            //flag = "1";
        }//report.submit();
        var chkFields = jQuery("#chkFields").val();
        if(type =="submit"){
            var bz1_val = jQuery("#bz1").val();
            var bz2_val = jQuery("#bz2").val();
            var bz3_val = jQuery("#bz3").val();
            if(bz1_val != "" && bz2_val != "" && bz1_val == bz2_val){
                alert("币种选择有重复，请检查后再提交");
                return;
            }
            if(bz1_val != "" && bz3_val != "" && bz1_val == bz3_val){
                alert("币种选择有重复，请检查后再提交");
                return;
            }
            if(bz2_val != "" && bz3_val != "" && bz2_val == bz3_val){
                alert("币种选择有重复，请检查后再提交");
                return;
            }
            checknameandcode("0");
            checknameandcode("1");
            var gysmccf_val= jQuery("#gysmccf").val();
            var tyshxydmcf_val= jQuery("#tyshxydmcf").val();
            if(gysmccf_val == "1" && tyshxydmcf_val=="1"){
                alert("供应商名称和统一社会信用代码重复，请检查");
                return;
            }
            if(gysmccf_val == "1"){
                alert("供应商名称重复，请检查");
                return;
            }
            if(tyshxydmcf_val == "1"){
                alert("统一社会信用代码重复，请检查");
                return;
            }
            var zzs_val = jQuery("#zzs").val();//制造商
            var fxs_val = jQuery("#fxs").val();//分销商
            var fws_val = jQuery("#fws").val();//服务商
            if(zzs_val != "1" && fxs_val!="1" && fws_val!="1"){
                alert("经营属性必须填写");
                return;
            }
            if(!check_form(report,chkFields)) return false;
        }
        var flaggs = "0";
        jQuery(".progressWrapper").each(function () {
            if(typeof(jQuery(this).attr("style")) == "undefined"){
                jQuery(this).find(".progressName").each(function () {
                    var hz=jQuery(this).text();
                    hz=","+hz.substring(hz.lastIndexOf("."))+",";
                    if(wjge.indexOf(hz)<0){
                        alert("所传附件中有非常用格式，请删除");
                        flaggs = "1";
                        return false;
                    }
                });
                if(flaggs == "1"){
                    return false;
                }

            }

        });
        if(flaggs == "1"){
            return;
        }

        var sfyyyxgfj_val = jQuery("#sfyyyzzjggz").val();//是否有营业相关附件
        var sfyyhxgfj_val = jQuery("#sfyyhkhxxjggz").val();//是否有银行相关附件
        var sfysyxgfj_val = jQuery("#sfysyddhjggz").val();//是否有商业相关附件
        var sfycwxgfj_val = jQuery("#sfycwxgfj").val();//是否有财务相关附件
        var gsxz_val = jQuery("#gsxz").val();//公司性质
        if(type =="submit"){
            if(sfyyyxgfj_val == "1"){
                jQuery("#yyzzjggz").val("");
            }
            if(sfyyhxgfj_val == "1"){
                jQuery("#yhkhxxjggz").val("");
            }
            if(sfysyxgfj_val == "1"){
                jQuery("#syddhjggz").val("");
            }
            if(sfycwxgfj_val == "1"){
                jQuery("#cwgjxx").val("");
            }

        }
        var yyzzjggz_val = jQuery("#yyzzjggz").val();
        var yhkhxxjggz_val = jQuery("#yhkhxxjggz").val();
        var syddhjggz_val = jQuery("#syddhjggz").val();
        var cwgjxx_val = jQuery("#cwgjxx").val();
        var zzsq_val = jQuery("#zzsq").val();
        var sbqd_val = jQuery("#sbqd").val();
        var rbcxxkytjbx_val = jQuery("#rbcxxkytjbx").val();
        var gsjbxxjpyq_val = jQuery("#gsjbxxjpyq").val();
        //yyzzjggz,yhkhxxjggz,syddhjggz,cwgjxx,zzsq,sbqd,rbcxxkytjbx,gsjbxxjpyq
        if((!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0)
            && (!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0)
            && (!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0)
            && (!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0)
            && (!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0)
            && (!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0)
            && (!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0)
            && (!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0)){
            if(type =="submit") {
                if ((yyzzjggz_val == "" && sfyyyxgfj_val == "0") || (yhkhxxjggz_val == "" && sfyyhxgfj_val == "0") || (syddhjggz_val == "" && sfysyxgfj_val == "0")|| (cwgjxx_val == "" && sfycwxgfj_val == "0")|| (gsjbxxjpyq_val == "" && gsxz_val !="36" && gsxz_val!="32")) {
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }
                // if ( zzsq_val == "" || sbqd_val == "" || rbcxxkytjbx_val == "" ) {
                //     alert("有附件信息没上传，请上传相关附件");
                //     return;
                // }
            }
            report.submit();

        }else{
            if(!oUpload_yyzzjggz || oUpload_yyzzjggz.getStats().files_queued === 0){
                count = count+1;
                flag_yyzzjggz = "0";

                if(type =="submit" && yyzzjggz_val == "" && sfyyyxgfj_val == "0"){
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }

            }

            if(!oUpload_yhkhxxjggz || oUpload_yhkhxxjggz.getStats().files_queued === 0){
                count = count+1;
                flag_yhkhxxjggz="0";
                if(type =="submit" && yhkhxxjggz_val == "" && yhkhxxjggz_val == "0"){
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }
            }

            if(!oUpload_syddhjggz || oUpload_syddhjggz.getStats().files_queued === 0){
                count = count+1;
                flag_syddhjggz = "0";
                if(type =="submit" && syddhjggz_val == "" && syddhjggz_val == "0"){
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }
            }

            if(!oUpload_cwgjxx || oUpload_cwgjxx.getStats().files_queued === 0){
                count = count+1;
                flag_cwgjxx = "0";
                if(type =="submit" &&cwgjxx_val == "" && sfycwxgfj_val == "0"){
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }
            }

            if(!oUpload_zzsq || oUpload_zzsq.getStats().files_queued === 0){
                count = count+1;
                flag_zzsq = "0";
                // if(type =="submit" &&zzsq_val == ""){
                //     alert("有附件信息没上传，请上传相关附件");
                //     return;
                // }
            }

            if(!oUpload_sbqd || oUpload_sbqd.getStats().files_queued === 0){
                count = count+1;
                flag_sbqd="0";
                // if(type =="submit" &&sbqd_val == ""){
                //     alert("有附件信息没上传，请上传相关附件");
                //     return;
                // }
            }

            if(!oUpload_rbcxxkytjbx || oUpload_rbcxxkytjbx.getStats().files_queued === 0){
                count = count+1;
                flag_rbcxxkytjbx = "0";
                // if(type =="submit" && rbcxxkytjbx_val == ""){
                //     alert("有附件信息没上传，请上传相关附件");
                //     return;
                // }
            }

            if(!oUpload_gsjbxxjpyq || oUpload_gsjbxxjpyq.getStats().files_queued === 0){
                count = count+1;
                flag_gsjbxxjpyq = "0";
                if(type =="submit" && gsjbxxjpyq_val == ""&& gsxz_val !="36" && gsxz_val!="32"){
                    alert("有附件信息没上传，请上传相关附件");
                    return;
                }
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
        attachids = jQuery("#"+type).val();
        var sta
        if(type =="yyzzjggz"){
            sta = oUpload_yyzzjggz.getStats();
        }else if(type =="yhkhxxjggz"){
            sta = oUpload_yhkhxxjggz.getStats();
        }else if(type =="syddhjggz"){
            sta = oUpload_syddhjggz.getStats();
        }else if(type =="cwgjxx"){
            sta = oUpload_cwgjxx.getStats();
        }else if(type =="zzsq"){
            sta = oUpload_zzsq.getStats();
            attachids="-1";
        }else if(type =="sbqd"){
            sta = oUpload_sbqd.getStats();
            attachids="-1";
        }else if(type =="rbcxxkytjbx"){
            sta = oUpload_rbcxxkytjbx.getStats();
            attachids="-1";
        }else if(type =="gsjbxxjpyq"){
            sta = oUpload_gsjbxxjpyq.getStats();
        }
        if (sta.files_queued == 0 && attachids=="") {
            jQuery("#"+type+"span").html("必填");
        }else{
            jQuery("#"+type+"span").html("");
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
    function check_form(thiswins,items){
        var checkarr = items.split(',');
        for(var i=0;i<checkarr.length;i++){
            var fieldid = checkarr[i];
            if(fieldid !=""){
                if(jQuery("#"+fieldid).val() ==""){
                    alert("必要信息不完整，红色星号为必填项！");
                    return false;
                }
            }
        }
        return true;
    }
    // function check_form(thiswins,items)
    // {
    //
    //
    //     thiswin = thiswins
    //     items = ","+items + ",";
    //
    //     var tempfieldvlaue1 = "";
    //     try{
    //         tempfieldvlaue1 = document.getElementById("htmlfieldids").value;
    //     }catch (e) {
    //     }
    //
    //     for(i=1;i<=thiswin.length;i++){
    //         tmpname = thiswin.elements[i-1].name;
    //         tmpvalue = thiswin.elements[i-1].value;
    //         if(tmpvalue==null){
    //             continue;
    //         }
    //
    //         if(tmpname!="" && items.indexOf(","+tmpname+",")!=-1){
    //             var __fieldhtmltype = jQuery("input[name=" + tmpname + "]").attr("__fieldhtmltype");
    //             if (__fieldhtmltype == '9') {
    //                 continue;
    //             }
    //
    //             var href = location.href;
    //             if(href && href.indexOf("Ext.jsp")!=-1){
    //                 window.__oriAlert__ = true;
    //             }
    //             if(tempfieldvlaue1.indexOf(tmpname+";") == -1){
    //                 while(tmpvalue.indexOf(" ") >= 0){
    //                     tmpvalue = tmpvalue.replace(" ", "");
    //                 }
    //                 while(tmpvalue.indexOf("\r\n") >= 0){
    //                     tmpvalue = tmpvalue.replace("\r\n", "");
    //                 }
    //
    //                 if(tmpvalue == ""){
    //                     if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
    //                         if(window.__oriAlert__){
    //                             window.top.Dialog.alert("\""+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
    //                         }else{
    //                             var tempElement = thiswin.elements[i-1];
    //                             //ueditor必填验证
    //                             if (checkueditorContent(tempElement)) {
    //                                 continue;
    //                             }
    //
    //                             window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
    //                                 formElementFocus(tempElement);
    //                             });
    //                         }
    //                         return false;
    //                     }else{
    //                         if(window.__oriAlert__){
    //                             try{
    //                                 window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                             }catch(e){
    //                                 Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                             }
    //                         }else{
    //                             try{
    //                                 window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                             }catch(e){
    //                                 Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                             }
    //                         }
    //                         return false;
    //                     }
    //                 }
    //             } else {
    //                 var divttt=document.createElement("div");
    //                 divttt.innerHTML = tmpvalue;
    //                 var tmpvaluettt = jQuery.trim(jQuery(divttt).text());
    //                 if(tmpvaluettt == ""){
    //                     if(thiswin.elements[i-1].getAttribute("temptitle")!=null && thiswin.elements[i-1].getAttribute("temptitle")!=""){
    //                         if(window.__oriAlert__){
    //                             window.top.Dialog.alert("\";"+thiswin.elements[i-1].getAttribute("temptitle")+"\""+"未填写");
    //                         }else{
    //                             var tempElement = thiswin.elements[i-1];
    //
    //                             //ueditor必填验证
    //                             if (checkueditorContent(tempElement)) {
    //                                 continue;
    //                             }
    //
    //                             window.top.Dialog.alert("&quot;"+thiswin.elements[i-1].getAttribute("temptitle")+"&quot;"+"未填写", function () {
    //                                 formElementFocus(tempElement);
    //                             });
    //
    //                         }
    //                         return false;
    //                     }else{
    //                         if(window.__oriAlert__){
    //                             window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                         }else{
    //                             window.top.Dialog.alert("必要信息不完整，红色星号为必填项！");
    //                         }
    //                         return false;
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     return true;
    // }
    function readCookie(type){
        return "7";
    }

    function showmustinput(uploadobj) {
        if (document.getElementById(uploadobj.customSettings.uploadfiedid)) {
            var fieldvalue = document.getElementById(uploadobj.customSettings.uploadfiedid).value;
            if (fieldvalue == "" || fieldvalue == "NULL") {
                var ismand = 1
                if (ismand == 1) {
                    var sta = uploadobj.getStats();

                    if (sta.files_queued == 0&&uploadobj !=oUpload_zzsq &&uploadobj !=oUpload_sbqd &&uploadobj !=oUpload_rbcxxkytjbx) {
                        document.getElementById(uploadobj.customSettings.uploadspan).innerHTML = "必填";
                        //document.getElementById(uploadobj.customSettings.uploadfiedid).value = "";
                    } else {
                        document.getElementById(uploadobj.customSettings.uploadspan).innerHTML = "";
                        //document.getElementById(uploadobj.customSettings.uploadfiedid).value = "NULL";
                    }
                }
            }
        }
    }
</script>
</body>
</html>
