<%--
  Created by IntelliJ IDEA.
  User: tangj
  Date: 2020/2/20
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>供应商信息收集</title>
    <LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
    <link href="/css/rp_wev8.css" rel="STYLESHEET" type="text/css">
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

        .fiedtitle {
            background: #e7f3fc!important;
            vertical-align: middle;
            word-break: break-all;
            word-wrap: break-word;
            text-align: left;
            padding-left: 8.0px;
            border-top-width: 1px;
            border-top-style: solid;
            border-top-color: #90badd;
            border-left-width: 1px;
            border-left-style: solid;
            border-left-color: #90badd;
            border-right-width: 1px;
            border-right-style: solid;
            border-right-color: #90badd;
            border-bottom-width: 1px;
            border-bottom-style: solid;
            border-bottom-color: #90badd;
            height: 30px;
        }
        .fieldvalue {
            vertical-align: middle;
            word-break: break-all;
            word-wrap: break-word;
            text-align: left;
            padding-left: 8.0px;
            border-top-width: 1px;
            border-top-style: solid;
            border-top-color: #90badd;
            border-left-width: 1px;
            border-left-style: solid;
            border-left-color: #90badd;
            border-right-width: 1px;
            border-right-style: solid;
            border-right-color: #90badd;
            border-bottom-width: 1px;
            border-bottom-style: solid;
            border-bottom-color: #90badd;
            height: 30px;
        }

        .formtitle {
            text-align: center;
            vertical-align: middle;
            word-break: break-all;
            word-wrap: break-word;
            height: 30px;
            font-size: 12pt;
        }
    </style>
</head>
<body>
<FORM id=report1 name=report1 action="/alarm/refresh.jsp" method=post>
    <div >
        <table class="excelOuterTable">
            <tbody>
            <tr>
                <td align="center">
                    <table class="excelMainTable" style=" width:95%; " _haspercent="true">
                        <colgroup>  <col width="15%"></col><col width="18.3%"><col width="15%"></col><col width="18.3%"><col width="15%"></col><col width="18.3%"></colgroup>
                        <tbody>
                        <tr style="height:30px;">
                            <td class="formtitle" colspan="6"><div><span>供应商信息收集</span></div></td>
                        </tr>
                        <tr>
                            <td class="fiedtitle">公司性质 status of ownership</td>
                            <td class="fieldvalue">
                                <select class="e8_btn_top middle" id="gsxz" name="gsxz" viewtype="1" onblur="checkinput2('gsxz','gsxzspan',this.getAttribute('viewtype'));">
                                    <option value='' selected ></option>
                                    <option value='1'  >公司性质1</option>
                                    <option value='2' >公司性质2</option>
                                </select>
                                <span id="gsxzspan" style="word-break:break-all;word-wrap:break-word"><img src="/images/BacoError_wev8.gif" align="absMiddle"></span>
                            </td>
                            <td class="fiedtitle">所属区域 location</td>
                            <td class="fieldvalue">
                                <select class="e8_btn_top middle" id="ssqy" name="ssqy" viewtype="1" onblur="checkinput2('ssqy','ssqyspan',this.getAttribute('viewtype'));">
                                    <option value='' selected ></option>
                                    <option value='1'  >所属区域1</option>
                                    <option value='2' >所属区域2</option>
                                </select>
                                <span id="ssqyspan" style="word-break:break-all;word-wrap:break-word"><img src="/images/BacoError_wev8.gif" align="absMiddle"></span>
                            </td>
                            <td class="fiedtitle">供应商名称 Companyname</td>
                            <td class="fieldvalue">
                                <input class="inputstyle" maxlength="100" size="20" id="gysmc" name="gysmc" onblur="checkLength()" onchange="checkinput('gysmc','gysmcspan')" value="">
                                <span id="gysmcspan"><img src="/images/BacoError_wev8.gif" align="absMiddle"></span>
                                <span id="remind1" style="cursor:hand" title="文本长度不能超过100(1个中文字符等于3个长度)"><img src="/images/remind_wev8.png" align="absmiddle"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="fiedtitle">所属行业 Industry</td>
                            <td class="fieldvalue">
                                <select class="e8_btn_top middle" id="sshy" name="sshy" viewtype="1" onblur="checkinput2('sshy','sshyspan',this.getAttribute('viewtype'));">
                                    <option value='' selected ></option>
                                    <option value='1'  >公司性质1</option>
                                    <option value='2' >公司性质2</option>
                                </select>
                                <span id="sshyspan" style="word-break:break-all;word-wrap:break-word"><img src="/images/BacoError_wev8.gif" align="absMiddle"></span>
                            </td>
                            <td class="fiedtitle">经营属性（可多选） Business attribute</td>
                            <td class="fieldvalue">
                                <span >
                                    <input type="checkbox" class="Inputstyle " viewtype="0" temptitle="属性1" value="1" id="jysx_ck1" name="jysx_ck1" checked="true">属性1</span>
                                    <input type="checkbox" class="Inputstyle " viewtype="0" temptitle="属性2" value="1" id="jysx_ck2" name="jysx_ck2">属性2</span>

                                </span>
                            </td>
                            <td class="fiedtitle">业务范围（根据经营执照） Business scope</td>
                            <td class="fieldvalue">
                                <input class="inputstyle" maxlength="200" size="20" id="ywfw" name="ywfw" onblur="checkLength()" onchange="checkinput('ywfw','ywfwspan')" value="">
                                <span id="ywfwspan"><img src="/images/BacoError_wev8.gif" align="absMiddle"></span>
                                <span id="remind2" style="cursor:hand" title="文本长度不能超过100(1个中文字符等于3个长度)"><img src="/images/remind_wev8.png" align="absmiddle"></span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>

        </table>
    </div>

</FORM>
</body>
</html>
