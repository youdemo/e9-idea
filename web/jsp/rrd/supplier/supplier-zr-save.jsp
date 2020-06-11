<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="rrd.supplier.util.TransUtil" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="weaver.general.BaseBean" %>
<%
    TransUtil tu = new TransUtil();
    String submittype = Util.null2String(request.getParameter("submittype"));
    String urlKey = Util.null2String(request.getParameter("urlKey"));
    //String urlkeyparam = URLEncoder.encode(urlKey,"UTF-8");
    String urlkeyparam = urlKey;
    boolean flag =tu.checkIfOvertime(URLDecoder.decode(URLDecoder.decode(urlKey,"UTF-8"),"UTF-8"));
    if(flag){
        out.print("连接已超时失效，如还未填写，请联系RRD对应的资源或采购人员发送新网页连接");
        return;
    }
    String rqid = Util.null2String(request.getParameter("rqid"));
    String zcid = Util.null2String(request.getParameter("zcid"));
    Map<String,String> dataMap = new HashMap<String,String>();
    dataMap.put("gsxz",Util.null2String(request.getParameter("gsxz")));//公司性质
    dataMap.put("ssqy",Util.null2String(request.getParameter("ssqy")));//所属区域
    dataMap.put("gysmc",Util.null2String(request.getParameter("gysmc")));//供应商名称
    dataMap.put("szhy",Util.null2String(request.getParameter("szhy")));//所属行业
    dataMap.put("jyzx",Util.null2String(request.getParameter("jyzx")));//经营属性
    dataMap.put("zzs",Util.null2String(request.getParameter("zzs")));//制造商
    dataMap.put("fxs",Util.null2String(request.getParameter("fxs")));//分销商
    dataMap.put("fws",Util.null2String(request.getParameter("fws")));//服务商
    dataMap.put("ywfwgjyyzz",Util.null2String(request.getParameter("ywfwgjyyzz")));//业务范围（根据营业执照）
    dataMap.put("clsj",Util.null2String(request.getParameter("clsj")));//成立时间
    dataMap.put("gsdh",Util.null2String(request.getParameter("gsdh")));//公司电话
    dataMap.put("gyszcdz",Util.null2String(request.getParameter("gyszcdz")));//供应商注册地址
    dataMap.put("zczbw",Util.null2String(request.getParameter("zczbw")));//注册资本（万）
    dataMap.put("nxsew",Util.null2String(request.getParameter("nxsew")));//年销售额（万）
    dataMap.put("gysjydz",Util.null2String(request.getParameter("gysjydz")));//供应商经营地址
    dataMap.put("fddbr",Util.null2String(request.getParameter("fddbr")));//法定代表人
    dataMap.put("gyswz",Util.null2String(request.getParameter("gyswz")));//供应商网址
    dataMap.put("glzlxr",Util.null2String(request.getParameter("glzlxr")));//管理者联系人
    dataMap.put("yddh",Util.null2String(request.getParameter("yddh")));//移动电话
    dataMap.put("yxdz",Util.null2String(request.getParameter("yxdz")));//邮箱地址
    dataMap.put("rcswlxr",Util.null2String(request.getParameter("rcswlxr")));//日常事务联系人
    dataMap.put("yddh1",Util.null2String(request.getParameter("yddh1")));//移动电话
    dataMap.put("yxdz1",Util.null2String(request.getParameter("yxdz1")));//邮箱地址
    dataMap.put("tyshxydm",Util.null2String(request.getParameter("tyshxydm")));//统一社会信用代码

    dataMap.put("glgs",Util.null2String(request.getParameter("glgs")));//关联公司
    dataMap.put("ygzsr",Util.null2String(request.getParameter("ygzsr")));//员工总数（人）
    dataMap.put("bgcsmjm2",Util.null2String(request.getParameter("bgcsmjm2")));//办公场所面积（m2）
    dataMap.put("sccsmjm2",Util.null2String(request.getParameter("sccsmjm2")));//生产场所面积（M2
    dataMap.put("glryzsr",Util.null2String(request.getParameter("glryzsr")));//管理人员总数（人）
    dataMap.put("ckmjm2",Util.null2String(request.getParameter("ckmjm2")));//仓库面积（M2）
    dataMap.put("sbhcpnlms",Util.null2String(request.getParameter("sbhcpnlms")));//设备和产品能力描述
    dataMap.put("sfysjkfnl",Util.null2String(request.getParameter("sfysjkfnl")));//是否有设计开发能力？
    dataMap.put("sjryslr",Util.null2String(request.getParameter("sjryslr")));//设计人员数量（人）
    dataMap.put("kha",Util.null2String(request.getParameter("kha")));//客户A
    dataMap.put("hya",Util.null2String(request.getParameter("hya")));//行业A
    dataMap.put("zba",Util.null2String(request.getParameter("zba")));//占比A
    dataMap.put("khb",Util.null2String(request.getParameter("khb")));//客户B
    dataMap.put("hyb",Util.null2String(request.getParameter("hyb")));//行业B
    dataMap.put("zbb",Util.null2String(request.getParameter("zbb")));//占比B
    dataMap.put("khc",Util.null2String(request.getParameter("khc")));//客户C
    dataMap.put("hyc",Util.null2String(request.getParameter("hyc")));//行业C
    dataMap.put("zbc",Util.null2String(request.getParameter("zbc")));//占比C
    dataMap.put("yyzzjggz",Util.null2String(request.getParameter("yyzzjggz")));//营业执照（加盖公章）
    dataMap.put("yhkhxxjggz",Util.null2String(request.getParameter("yhkhxxjggz")));//银行开户信息（加盖公章）
    dataMap.put("syddhjggz",Util.null2String(request.getParameter("syddhjggz")));//商业道德函（加盖公章）
    dataMap.put("cwgjxx",Util.null2String(request.getParameter("cwgjxx")));//财务关键信息
    dataMap.put("zzsq",Util.null2String(request.getParameter("zzsq")));//资质授权
    dataMap.put("sbqd",Util.null2String(request.getParameter("sbqd")));//设备清单
    dataMap.put("rbcxxkytjbx",Util.null2String(request.getParameter("rbcxxkytjbx")));//如补充信息可以添加表下
    dataMap.put("gsjbxxjpyq",Util.null2String(request.getParameter("gsjbxxjpyq")));//公司基本信息截屏要求
    dataMap.put("yyzzlysm",Util.null2String(request.getParameter("yyzzlysm")));//营业执照理由说明
    dataMap.put("yhkhxxlysm",Util.null2String(request.getParameter("yhkhxxlysm")));//银行开户信息理由说明
    dataMap.put("syddhlysm",Util.null2String(request.getParameter("syddhlysm")));//商业道德函理由说明
    dataMap.put("sl",Util.null2String(request.getParameter("sl")));//税率

    dataMap.put("sfyyyzzjggz",Util.null2String(request.getParameter("sfyyyzzjggz")));//是否有营业相关附件
    dataMap.put("sfyyhkhxxjggz",Util.null2String(request.getParameter("sfyyhkhxxjggz")));//是否有银行相关附件
    dataMap.put("sfysyddhjggz",Util.null2String(request.getParameter("sfysyddhjggz")));//是否有商业相关附件

    dataMap.put("sfycwxgfj",Util.null2String(request.getParameter("sfycwxgfj")));//是否有财务相关附件
    dataMap.put("cwrwqsmly",Util.null2String(request.getParameter("cwrwqsmly")));//财务如无请说明理由
    dataMap.put("sfygsxgfj",Util.null2String(request.getParameter("sfygsxgfj")));//是否有公司相关附件
    dataMap.put("gsrwqsmly",Util.null2String(request.getParameter("gsrwqsmly")));//公司如无请说明理由
    dataMap.put("bz1",Util.null2String(request.getParameter("bz1")));//币种1
    dataMap.put("bz2",Util.null2String(request.getParameter("bz2")));//币种1
    dataMap.put("bz3",Util.null2String(request.getParameter("bz3")));//币种1
    dataMap.put("khh1",Util.null2String(request.getParameter("khh1")));//开户行1
    dataMap.put("khh2",Util.null2String(request.getParameter("khh2")));//开户行2
    dataMap.put("khh3",Util.null2String(request.getParameter("khh3")));//开户行3
    dataMap.put("yhzh1",Util.null2String(request.getParameter("yhzh1")));//银行账号1
    dataMap.put("yhzh2",Util.null2String(request.getParameter("yhzh2")));//银行账号2
    dataMap.put("yhzh3",Util.null2String(request.getParameter("yhzh3")));//银行账号3
    dataMap.put("swiftcode1",Util.null2String(request.getParameter("swiftcode1")));//swiftcode1
    dataMap.put("swiftcode2",Util.null2String(request.getParameter("swiftcode2")));//swiftcode2
    dataMap.put("swiftcode3",Util.null2String(request.getParameter("swiftcode3")));//swiftcode3
    SupplierInfoServiceImpl sis = new SupplierInfoServiceImpl();
    String result = sis.saveData(dataMap,submittype,rqid,zcid);





%>
<html>
<head>
    <title>供应商信息收集</title>
</head>
<body>
</body>
<script type="text/javascript">
    var result = "<%=result%>";
    var urlKey = "<%=urlkeyparam%>";
    var submittype = "<%=submittype%>";
    if(submittype=="zc"){
        if(result == "success"){
            alert("暂存成功");
            window.location.href="/rrd/supplier/supplier-zr-addinfo.jsp?urlKey="+urlKey;

        }else{
            alert("暂存失败");
            window.location.href="/rrd/supplier/supplier-zr-addinfo.jsp?urlKey="+urlKey;
        }
    }else{
        if(result == "success"){
            alert("提交成功");
            window.open("about:blank","_self").close();
        }else{
            alert("提交失败");
            window.location.href="/rrd/supplier/supplier-zr-addinfo.jsp?urlKey="+urlKey;
        }
    }
</script>
</html>