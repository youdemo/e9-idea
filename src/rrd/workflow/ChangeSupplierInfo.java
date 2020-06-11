package rrd.workflow;


import rrd.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChangeSupplierInfo implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        RecordSet rs = new RecordSet();
        String workflowID = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowID);
        Map<String,String> baseMap = new HashMap<String,String>();
        Map<String,String> khflMap = new HashMap<String,String>();
        Map<String,String> buMap = new HashMap<String,String>();
        Map<String,String> xyMap = new HashMap<String,String>();
        Map<String,String> baseMapMode = new HashMap<String,String>();
        Map<String,String> khflMapMode = new HashMap<String,String>();
        Map<String,String> buMapMode = new HashMap<String,String>();
        Map<String,String> xyMapMode = new HashMap<String,String>();
        String jcsjbg = "1";// 0 是 1 否  其他信息变更
        String khflxxbg = "1";//0 是 1 否 付款条件变更
        String buxxbg = "1";//0 是 1 否
        String xyxgxxbg = "1";//0 是 1 否 联系人相关信息
        String sql = "select * from "+tableName+" where requestid="+requestid;
        String gysbh = "";
        rs.execute(sql);
        if(rs.next()){
            gysbh = Util.null2String(rs.getString("gysbh"));
            baseMap.put("gsxz", Util.null2String(rs.getString("gsxz")));//公司性质
            baseMap.put("gysmc", Util.null2String(rs.getString("gysmc")));//供应商名称
            baseMap.put("szhy", Util.null2String(rs.getString("szhy")));//所属行业
            baseMap.put("zzs", Util.null2String(rs.getString("zzs")));//制造商
            baseMap.put("fxs", Util.null2String(rs.getString("fxs")));//分销商
            baseMap.put("fws", Util.null2String(rs.getString("fws")));//服务商
            baseMap.put("ywfwgjyyzz", Util.null2String(rs.getString("ywfwgjyyzz")));//业务范围（根据营业执照）
            baseMap.put("clsj", Util.null2String(rs.getString("clsj")));//成立时间
            baseMap.put("gsdh", Util.null2String(rs.getString("gsdh")));//公司电话
            baseMap.put("gyszcdz", Util.null2String(rs.getString("gyszcdz")));//供应商注册地址
            baseMap.put("zczbw", Util.null2String(rs.getString("zczbw")));//注册资本（万）
            baseMap.put("nxsew", Util.null2String(rs.getString("nxsew")));//年销售额（万）
            baseMap.put("gysjydz", Util.null2String(rs.getString("gysjydz")));//供应商经营地址
            baseMap.put("fddbr", Util.null2String(rs.getString("fddbr")));//法定代表人
            baseMap.put("gyswz", Util.null2String(rs.getString("gyswz")));//供应商网址
            baseMap.put("khh", Util.null2String(rs.getString("khh")));//开户行
            baseMap.put("yhzh", Util.null2String(rs.getString("yhzh")));//银行账号
            baseMap.put("wb", Util.null2String(rs.getString("wb")));//外币
            baseMap.put("yhzhwb", Util.null2String(rs.getString("yhzhwb")));//银行账号（外币）
            baseMap.put("swiftcode", Util.null2String(rs.getString("swiftcode")));//SWIFT CODE
            baseMap.put("glgs", Util.null2String(rs.getString("glgs")));//关联公司
            baseMap.put("ygzsr", Util.null2String(rs.getString("ygzsr")));//员工总数（人）
            baseMap.put("bgcsmjm2", Util.null2String(rs.getString("bgcsmjm2")));//办公场所面积（㎡）
            baseMap.put("sccsmjm2", Util.null2String(rs.getString("sccsmjm2")));//生产场所面积（㎡）
            baseMap.put("glryzsr", Util.null2String(rs.getString("glryzsr")));//管理人员总数（人）
            baseMap.put("ckmjm2", Util.null2String(rs.getString("ckmjm2")));//仓库面积（㎡）
            baseMap.put("sbhcpnlms", Util.null2String(rs.getString("sbhcpnlms")));//设备和产品能力描述
            baseMap.put("sfysjkfnl", Util.null2String(rs.getString("sfysjkfnl")));//是否有设计开发能力
            baseMap.put("sjryslr", Util.null2String(rs.getString("sjryslr")));//设计人员数量（人）
            baseMap.put("kha", Util.null2String(rs.getString("kha")));//客户A
            baseMap.put("hya", Util.null2String(rs.getString("hya")));//行业A
            baseMap.put("khb", Util.null2String(rs.getString("khb")));//客户B
            baseMap.put("hyb", Util.null2String(rs.getString("hyb")));//行业B
            baseMap.put("khc", Util.null2String(rs.getString("khc")));//客户C
            baseMap.put("hyc", Util.null2String(rs.getString("hyc")));//行业C
            baseMap.put("yyzzlysm", Util.null2String(rs.getString("yyzzlysm")));//营业如无请说明理由
            baseMap.put("yhkhxxlysm", Util.null2String(rs.getString("yhkhxxlysm")));//银行如无请说明理由
            baseMap.put("syddhlysm", Util.null2String(rs.getString("syddhlysm")));//商业如无请说明理由
            baseMap.put("sl", Util.null2String(rs.getString("sl")));//税率
            baseMap.put("gysjlrq", Util.null2String(rs.getString("gysjlrq")));//供应商建立日期
            baseMap.put("sfktkhsf", Util.null2String(rs.getString("sfktkhsf")));//是否开通客户身份
            baseMap.put("xyfzr", Util.null2String(rs.getString("xyfzr")));//寻源负责人
            baseMap.put("sfzytxqdsqjy", Util.null2String(rs.getString("sfzytxqdsqjy")));//属于特许/渠道授权经营？
            baseMap.put("gyshzdw", Util.null2String(rs.getString("gyshzdw")));//供应商合作定位
            baseMap.put("kfmdkydx", Util.null2String(rs.getString("kfmdkydx")));//开发目的
            baseMap.put("cnzj", Util.null2String(rs.getString("cnzj")));//产能增加
            baseMap.put("yrgyjzldzy", Util.null2String(rs.getString("yrgyjzldzy")));//引入更有竞争力的资源
            baseMap.put("khzd", Util.null2String(rs.getString("khzd")));//客户指定
			baseMap.put("khzdgysxs", Util.null2String(rs.getString("khzdgysxs")));//客户指定供应商形式
			baseMap.put("khzdgyssldbjyyly", Util.null2String(rs.getString("khzdgyssldbjyyly")));//客户指定供应商事例的背景/原因/理由
			baseMap.put("fcczldkhrydydzwtx", Util.null2String(rs.getString("fcczldkhrydydzwtx")));//发出此指令的客户人员对应的职位/头衔
			baseMap.put("xsfzr", Util.null2String(rs.getString("xsfzr")));//销售负责人
			baseMap.put("yjndhzcgjew", Util.null2String(rs.getString("yjndhzcgjew")));//预计年度合作采购金额（万）
			baseMap.put("sfcgbmzdkf", Util.null2String(rs.getString("sfcgbmzdkf")));//是否采购部门主导开发
			baseMap.put("xqbmzdspfzr", Util.null2String(rs.getString("xqbmzdspfzr")));//需求部门指定审批负责人
			baseMap.put("gyszr", Util.null2String(rs.getString("gyszr")));//供应商新增
			baseMap.put("gyssybzxbu", Util.null2String(rs.getString("gyssybzxbu")));//供应商事业部属性
			baseMap.put("glgsgysdm", Util.null2String(rs.getString("glgsgysdm")));//关联公司供应商代码
			baseMap.put("sqrcsyj", Util.null2String(rs.getString("sqrcsyj")));//申请人初审意见
			baseMap.put("pkg", Util.null2String(rs.getString("pkg")));//Packaging
			baseMap.put("pub", Util.null2String(rs.getString("pub")));//Pubishing Services
			baseMap.put("label", Util.null2String(rs.getString("label")));//Labels
			baseMap.put("ms", Util.null2String(rs.getString("ms")));//Marketing Services
			baseMap.put("hr", Util.null2String(rs.getString("hr")));//HR
			baseMap.put("legal", Util.null2String(rs.getString("legal")));//Legal
			baseMap.put("finance", Util.null2String(rs.getString("finance")));//Finance
			baseMap.put("kfgysjtlyms", Util.null2String(rs.getString("kfgysjtlyms")));//开发供应商具体理由描述
			baseMap.put("bz", Util.null2String(rs.getString("bz")));//交易货币
			baseMap.put("gysszgjdq", Util.null2String(rs.getString("gysszgjdq")));//供应商所属国家/地区
			baseMap.put("gysszsf", Util.null2String(rs.getString("gysszsf")));//供应商所属省份
			baseMap.put("fxxxjl", Util.null2String(rs.getString("fxxxjl")));//风险信息记录
			baseMap.put("khrwqsmly", Util.null2String(rs.getString("khrwqsmly")));//现场如无请说明理由
			baseMap.put("sbrwqsmly", Util.null2String(rs.getString("sbrwqsmly")));//设备如无请说明理由
			baseMap.put("sfyxcshbg", Util.null2String(rs.getString("sfyxcshbg")));//是否有现场相关附件
			baseMap.put("sfyyyzzjggz", Util.null2String(rs.getString("sfyyyzzjggz")));//是否有营业相关附件
			baseMap.put("sfyyhkhxxjggz", Util.null2String(rs.getString("sfyyhkhxxjggz")));//是否有银行相关附件
			baseMap.put("sfysyddhjggz", Util.null2String(rs.getString("sfysyddhjggz")));//是否有商业相关附件
			baseMap.put("sfysbqd", Util.null2String(rs.getString("sfysbqd")));//是否有设备相关附件
			baseMap.put("yysffhbz", Util.null2String(rs.getString("yysffhbz")));//营业是否符合标准
			baseMap.put("yhsffhbz", Util.null2String(rs.getString("yhsffhbz")));//银行是否符合标准
			baseMap.put("cwsffhbz", Util.null2String(rs.getString("cwsffhbz")));//财务是否符合标准
			baseMap.put("sysffhbz", Util.null2String(rs.getString("sysffhbz")));//商业是否符合标准
			baseMap.put("gssffhbz", Util.null2String(rs.getString("gssffhbz")));//公司是否符合标准
			baseMap.put("gysplzx1j", Util.null2String(rs.getString("gysplzx1j")));//供应商品类属性（1级）
			baseMap.put("gysplzx2j", Util.null2String(rs.getString("gysplzx2j")));//供应商品类属性（2级）
			baseMap.put("gysplzx3j", Util.null2String(rs.getString("gysplzx3j")));//供应商品类属性（3级）
			baseMap.put("gysdm", Util.null2String(rs.getString("gysdm")));//供应商代码
			baseMap.put("glgsgysmc", Util.null2String(rs.getString("glgsgysmc")));//关联公司供应商名称
			baseMap.put("zba", Util.null2String(rs.getString("zba")));//占比A（%）
			baseMap.put("zbb", Util.null2String(rs.getString("zbb")));//占比B（%）
			baseMap.put("zbc", Util.null2String(rs.getString("zbc")));//占比C（%）
			baseMap.put("hzdwdm", Util.null2String(rs.getString("hzdwdm")));//合作定位代码
			baseMap.put("fkfs", Util.null2String(rs.getString("fkfs")));//付款方式
			baseMap.put("sfycwxgfj", Util.null2String(rs.getString("sfycwxgfj")));//是否有财务相关附件
			baseMap.put("cwrwqsmly", Util.null2String(rs.getString("cwrwqsmly")));//财务如无请说明理由
			baseMap.put("sfygsxgfj", Util.null2String(rs.getString("sfygsxgfj")));//是否有公司相关附件
			baseMap.put("gsrwqsmly", Util.null2String(rs.getString("gsrwqsmly")));//公司如无请说明理由
			baseMap.put("xywdr", Util.null2String(rs.getString("xywdr")));//新业务导入
			baseMap.put("xqykf", Util.null2String(rs.getString("xqykf")));//新区域开发
			
			

            khflMap.put("fktj", Util.null2String(rs.getString("fktj")));//付款条件
            

            /*buMap.put("xsfzrkhjl1", Util.null2String(rs.getString("xsfzrkhjl1")));//销售负责人/客户经理1
            buMap.put("xs1", Util.null2String(rs.getString("xs1")));//销售1
			buMap.put("csrjl", Util.null2String(rs.getString("csrjl")));//CSR经理
			buMap.put("khsqry1", Util.null2String(rs.getString("khsqry1")));//客户授权人员1
            buMap.put("xsfzrkhjl2", Util.null2String(rs.getString("xsfzrkhjl2")));//销售负责人/客户经理2
            buMap.put("xs2", Util.null2String(rs.getString("xs2")));//销售2
            buMap.put("aejl", Util.null2String(rs.getString("aejl")));//AE经理
            buMap.put("esjl", Util.null2String(rs.getString("esjl")));//ES经理
			buMap.put("khsqry2", Util.null2String(rs.getString("khsqry2")));//客户授权人员2
			buMap.put("hyzfl", Util.null2String(rs.getString("hyzfl")));//行业子分类
            buMap.put("szqy", Util.null2String(rs.getString("szqy")));//所在区域
            buMap.put("xsfzrkhjl3", Util.null2String(rs.getString("xsfzrkhjl3")));//销售负责人/客户经理3
            buMap.put("xs3", Util.null2String(rs.getString("xs3")));//销售3
            buMap.put("csrjl2", Util.null2String(rs.getString("csrjl2")));//CSR经理2
			buMap.put("khsqry3", Util.null2String(rs.getString("khsqry3")));//客户授权人员3
			buMap.put("oemodmjt", Util.null2String(rs.getString("oemodmjt")));//OEM/ODM集团
			buMap.put("sfczmyjt", Util.null2String(rs.getString("sfczmyjt")));//是否从属某一集团*/

            xyMap.put("glzlxr", Util.null2String(rs.getString("glzlxr")));//管理者联系人
			xyMap.put("yddh", Util.null2String(rs.getString("yddh")));//移动电话
			xyMap.put("yxdz", Util.null2String(rs.getString("yxdz")));//邮箱地址
			xyMap.put("rcswlxr", Util.null2String(rs.getString("rcswlxr")));//日常事务联系人
			xyMap.put("yddh1", Util.null2String(rs.getString("yddh1")));//移动电话
            xyMap.put("yxdz1", Util.null2String(rs.getString("yxdz1")));//邮箱地址
        }

        sql = "select * from uf_gyslb where gysbh= '"+gysbh+"'";
        rs.execute(sql);
        if(rs.next()){
			baseMapMode.put("gsxz", Util.null2String(rs.getString("gsxz")));//公司性质
            baseMapMode.put("gysmc", Util.null2String(rs.getString("gysmc")));//供应商名称
            baseMapMode.put("szhy", Util.null2String(rs.getString("szhy")));//所属行业
            baseMapMode.put("zzs", Util.null2String(rs.getString("zzs")));//制造商
            baseMapMode.put("fxs", Util.null2String(rs.getString("fxs")));//分销商
            baseMapMode.put("fws", Util.null2String(rs.getString("fws")));//服务商
            baseMapMode.put("ywfwgjyyzz", Util.null2String(rs.getString("ywfwgjyyzz")));//业务范围（根据营业执照）
            baseMapMode.put("clsj", Util.null2String(rs.getString("clsj")));//成立时间
            baseMapMode.put("gsdh", Util.null2String(rs.getString("gsdh")));//公司电话
            baseMapMode.put("gyszcdz", Util.null2String(rs.getString("gyszcdz")));//供应商注册地址
            baseMapMode.put("zczbw", Util.null2String(rs.getString("zczbw")));//注册资本（万）
            baseMapMode.put("nxsew", Util.null2String(rs.getString("nxsew")));//年销售额（万）
            baseMapMode.put("gysjydz", Util.null2String(rs.getString("gysjydz")));//供应商经营地址
            baseMapMode.put("fddbr", Util.null2String(rs.getString("fddbr")));//法定代表人
            baseMapMode.put("gyswz", Util.null2String(rs.getString("gyswz")));//供应商网址
            baseMapMode.put("khh", Util.null2String(rs.getString("khh")));//开户行
            baseMapMode.put("yhzh", Util.null2String(rs.getString("yhzh")));//银行账号
            baseMapMode.put("wb", Util.null2String(rs.getString("wb")));//外币
            baseMapMode.put("yhzhwb", Util.null2String(rs.getString("yhzhwb")));//银行账号（外币）
            baseMapMode.put("swiftcode", Util.null2String(rs.getString("swiftcode")));//SWIFT CODE
            baseMapMode.put("glgs", Util.null2String(rs.getString("glgs")));//关联公司
            baseMapMode.put("ygzsr", Util.null2String(rs.getString("ygzsr")));//员工总数（人）
            baseMapMode.put("bgcsmjm2", Util.null2String(rs.getString("bgcsmjm2")));//办公场所面积（㎡）
            baseMapMode.put("sccsmjm2", Util.null2String(rs.getString("sccsmjm2")));//生产场所面积（㎡）
            baseMapMode.put("glryzsr", Util.null2String(rs.getString("glryzsr")));//管理人员总数（人）
            baseMapMode.put("ckmjm2", Util.null2String(rs.getString("ckmjm2")));//仓库面积（㎡）
            baseMapMode.put("sbhcpnlms", Util.null2String(rs.getString("sbhcpnlms")));//设备和产品能力描述
            baseMapMode.put("sfysjkfnl", Util.null2String(rs.getString("sfysjkfnl")));//是否有设计开发能力
            baseMapMode.put("sjryslr", Util.null2String(rs.getString("sjryslr")));//设计人员数量（人）
            baseMapMode.put("kha", Util.null2String(rs.getString("kha")));//客户A
            baseMapMode.put("hya", Util.null2String(rs.getString("hya")));//行业A
            baseMapMode.put("khb", Util.null2String(rs.getString("khb")));//客户B
            baseMapMode.put("hyb", Util.null2String(rs.getString("hyb")));//行业B
            baseMapMode.put("khc", Util.null2String(rs.getString("khc")));//客户C
            baseMapMode.put("hyc", Util.null2String(rs.getString("hyc")));//行业C
            baseMapMode.put("yyzzlysm", Util.null2String(rs.getString("yyzzlysm")));//营业如无请说明理由
            baseMapMode.put("yhkhxxlysm", Util.null2String(rs.getString("yhkhxxlysm")));//银行如无请说明理由
            baseMapMode.put("syddhlysm", Util.null2String(rs.getString("syddhlysm")));//商业如无请说明理由
            baseMapMode.put("sl", Util.null2String(rs.getString("sl")));//税率
            baseMapMode.put("gysjlrq", Util.null2String(rs.getString("gysjlrq")));//供应商建立日期
            baseMapMode.put("sfktkhsf", Util.null2String(rs.getString("sfktkhsf")));//是否开通客户身份
            baseMapMode.put("xyfzr", Util.null2String(rs.getString("xyfzr")));//寻源负责人
            baseMapMode.put("sfzytxqdsqjy", Util.null2String(rs.getString("sfzytxqdsqjy")));//属于特许/渠道授权经营？
            baseMapMode.put("gyshzdw", Util.null2String(rs.getString("gyshzdw")));//供应商合作定位
            baseMapMode.put("kfmdkydx", Util.null2String(rs.getString("kfmdkydx")));//开发目的
            baseMapMode.put("cnzj", Util.null2String(rs.getString("cnzj")));//产能增加
            baseMapMode.put("yrgyjzldzy", Util.null2String(rs.getString("yrgyjzldzy")));//引入更有竞争力的资源
            baseMapMode.put("khzd", Util.null2String(rs.getString("khzd")));//客户指定
			baseMapMode.put("khzdgysxs", Util.null2String(rs.getString("khzdgysxs")));//客户指定供应商形式
			baseMapMode.put("khzdgyssldbjyyly", Util.null2String(rs.getString("khzdgyssldbjyyly")));//客户指定供应商事例的背景/原因/理由
			baseMapMode.put("fcczldkhrydydzwtx", Util.null2String(rs.getString("fcczldkhrydydzwtx")));//发出此指令的客户人员对应的职位/头衔
			baseMapMode.put("xsfzr", Util.null2String(rs.getString("xsfzr")));//销售负责人
			baseMapMode.put("yjndhzcgjew", Util.null2String(rs.getString("yjndhzcgjew")));//预计年度合作采购金额（万）
			baseMapMode.put("sfcgbmzdkf", Util.null2String(rs.getString("sfcgbmzdkf")));//是否采购部门主导开发
			baseMapMode.put("xqbmzdspfzr", Util.null2String(rs.getString("xqbmzdspfzr")));//需求部门指定审批负责人
			baseMapMode.put("gyszr", Util.null2String(rs.getString("gyszr")));//供应商新增
			baseMapMode.put("gyssybzxbu", Util.null2String(rs.getString("gyssybzxbu")));//供应商事业部属性
			baseMapMode.put("glgsgysdm", Util.null2String(rs.getString("glgsgysdm")));//关联公司供应商代码
			baseMapMode.put("sqrcsyj", Util.null2String(rs.getString("sqrcsyj")));//申请人初审意见
			baseMapMode.put("pkg", Util.null2String(rs.getString("pkg")));//Packaging
			baseMapMode.put("pub", Util.null2String(rs.getString("pub")));//Pubishing Services
			baseMapMode.put("label", Util.null2String(rs.getString("label")));//Labels
			baseMapMode.put("ms", Util.null2String(rs.getString("ms")));//Marketing Services
			baseMapMode.put("hr", Util.null2String(rs.getString("hr")));//HR
			baseMapMode.put("legal", Util.null2String(rs.getString("legal")));//Legal
			baseMapMode.put("finance", Util.null2String(rs.getString("finance")));//Finance
			baseMapMode.put("kfgysjtlyms", Util.null2String(rs.getString("kfgysjtlyms")));//开发供应商具体理由描述
			baseMapMode.put("bz", Util.null2String(rs.getString("bz")));//交易货币
			baseMapMode.put("gysszgjdq", Util.null2String(rs.getString("gysszgjdq")));//供应商所属国家/地区
			baseMapMode.put("gysszsf", Util.null2String(rs.getString("gysszsf")));//供应商所属省份
			baseMapMode.put("fxxxjl", Util.null2String(rs.getString("fxxxjl")));//风险信息记录
			baseMapMode.put("khrwqsmly", Util.null2String(rs.getString("khrwqsmly")));//现场如无请说明理由
			baseMapMode.put("sbrwqsmly", Util.null2String(rs.getString("sbrwqsmly")));//设备如无请说明理由
			baseMapMode.put("sfyxcshbg", Util.null2String(rs.getString("sfyxcshbg")));//是否有现场相关附件
			baseMapMode.put("sfyyyzzjggz", Util.null2String(rs.getString("sfyyyzzjggz")));//是否有营业相关附件
			baseMapMode.put("sfyyhkhxxjggz", Util.null2String(rs.getString("sfyyhkhxxjggz")));//是否有银行相关附件
			baseMapMode.put("sfysyddhjggz", Util.null2String(rs.getString("sfysyddhjggz")));//是否有商业相关附件
			baseMapMode.put("sfysbqd", Util.null2String(rs.getString("sfysbqd")));//是否有设备相关附件
			baseMapMode.put("yysffhbz", Util.null2String(rs.getString("yysffhbz")));//营业是否符合标准
			baseMapMode.put("yhsffhbz", Util.null2String(rs.getString("yhsffhbz")));//银行是否符合标准
			baseMapMode.put("cwsffhbz", Util.null2String(rs.getString("cwsffhbz")));//财务是否符合标准
			baseMapMode.put("sysffhbz", Util.null2String(rs.getString("sysffhbz")));//商业是否符合标准
			baseMapMode.put("gssffhbz", Util.null2String(rs.getString("gssffhbz")));//公司是否符合标准
			baseMapMode.put("gysplzx1j", Util.null2String(rs.getString("gysplzx1j")));//供应商品类属性（1级）
			baseMapMode.put("gysplzx2j", Util.null2String(rs.getString("gysplzx2j")));//供应商品类属性（2级）
			baseMapMode.put("gysplzx3j", Util.null2String(rs.getString("gysplzx3j")));//供应商品类属性（3级）
			baseMapMode.put("gysdm", Util.null2String(rs.getString("gysdm")));//供应商代码
			baseMapMode.put("glgsgysmc", Util.null2String(rs.getString("glgsgysmc")));//关联公司供应商名称
			baseMapMode.put("zba", Util.null2String(rs.getString("zba")));//占比A（%）
			baseMapMode.put("zbb", Util.null2String(rs.getString("zbb")));//占比B（%）
			baseMapMode.put("zbc", Util.null2String(rs.getString("zbc")));//占比C（%）
			baseMapMode.put("hzdwdm", Util.null2String(rs.getString("hzdwdm")));//合作定位代码
			baseMapMode.put("fkfs", Util.null2String(rs.getString("fkfs")));//付款方式
			baseMapMode.put("sfycwxgfj", Util.null2String(rs.getString("sfycwxgfj")));//是否有财务相关附件
			baseMapMode.put("cwrwqsmly", Util.null2String(rs.getString("cwrwqsmly")));//财务如无请说明理由
			baseMapMode.put("sfygsxgfj", Util.null2String(rs.getString("sfygsxgfj")));//是否有公司相关附件
			baseMapMode.put("gsrwqsmly", Util.null2String(rs.getString("gsrwqsmly")));//公司如无请说明理由
			baseMapMode.put("xywdr", Util.null2String(rs.getString("xywdr")));//新业务导入
			baseMapMode.put("xqykf", Util.null2String(rs.getString("xqykf")));//新区域开发
			
			

            khflMapMode.put("fktj", Util.null2String(rs.getString("fktj")));//付款条件
            

            /*buMap.put("xsfzrkhjl1", Util.null2String(rs.getString("xsfzrkhjl1")));//销售负责人/客户经理1
            buMap.put("xs1", Util.null2String(rs.getString("xs1")));//销售1
			buMap.put("csrjl", Util.null2String(rs.getString("csrjl")));//CSR经理
			buMap.put("khsqry1", Util.null2String(rs.getString("khsqry1")));//客户授权人员1
            buMap.put("xsfzrkhjl2", Util.null2String(rs.getString("xsfzrkhjl2")));//销售负责人/客户经理2
            buMap.put("xs2", Util.null2String(rs.getString("xs2")));//销售2
            buMap.put("aejl", Util.null2String(rs.getString("aejl")));//AE经理
            buMap.put("esjl", Util.null2String(rs.getString("esjl")));//ES经理
			buMap.put("khsqry2", Util.null2String(rs.getString("khsqry2")));//客户授权人员2
			buMap.put("hyzfl", Util.null2String(rs.getString("hyzfl")));//行业子分类
            buMap.put("szqy", Util.null2String(rs.getString("szqy")));//所在区域
            buMap.put("xsfzrkhjl3", Util.null2String(rs.getString("xsfzrkhjl3")));//销售负责人/客户经理3
            buMap.put("xs3", Util.null2String(rs.getString("xs3")));//销售3
            buMap.put("csrjl2", Util.null2String(rs.getString("csrjl2")));//CSR经理2
			buMap.put("khsqry3", Util.null2String(rs.getString("khsqry3")));//客户授权人员3
			buMap.put("oemodmjt", Util.null2String(rs.getString("oemodmjt")));//OEM/ODM集团
			buMap.put("sfczmyjt", Util.null2String(rs.getString("sfczmyjt")));//是否从属某一集团*/

            xyMapMode.put("glzlxr", Util.null2String(rs.getString("glzlxr")));//管理者联系人
			xyMapMode.put("yddh", Util.null2String(rs.getString("yddh")));//移动电话
			xyMapMode.put("yxdz", Util.null2String(rs.getString("yxdz")));//邮箱地址
			xyMapMode.put("rcswlxr", Util.null2String(rs.getString("rcswlxr")));//日常事务联系人
			xyMapMode.put("yddh1", Util.null2String(rs.getString("yddh1")));//移动电话
            xyMapMode.put("yxdz1", Util.null2String(rs.getString("yxdz1")));//邮箱地址
			
			
            
        }

        Iterator itbase = baseMapMode.keySet().iterator();
        while(itbase.hasNext()){
            String filename = (String) itbase.next();
            String modevalue = baseMapMode.get(filename);
            String flowvalue = baseMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeSupplierInfo baseMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                jcsjbg = "0";
                break;
            }
        }

        Iterator itkh = khflMapMode.keySet().iterator();
        while(itkh.hasNext()){
            String filename = (String) itkh.next();
            String modevalue = khflMapMode.get(filename);
            String flowvalue = khflMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeSupplierInfo khflMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                khflxxbg = "0";
                break;
            }
        }

        /*Iterator  itbu = buMapMode.keySet().iterator();
        while(itbu.hasNext()){
            String filename = (String) itbu.next();
            String modevalue = buMapMode.get(filename);
            String flowvalue = buMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeSupplierInfo buMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                buxxbg = "0";
                break;
            }
        }*/

        Iterator  itxy = xyMapMode.keySet().iterator();
        while(itxy.hasNext()){
            String filename = (String) itxy.next();
            String modevalue = xyMapMode.get(filename);
            String flowvalue = xyMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeSupplierInfo xyMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                xyxgxxbg = "0";
                break;
            }
        }
        sql = "update "+tableName+" set jcsjbg='"+jcsjbg+"',khflxxbg='"+khflxxbg+"',buxxbg='"+buxxbg+"',xyxgxxbg='"+xyxgxxbg+"' where requestid="+requestid;
        new BaseBean().writeLog("ChangeSupplierInfo",sql);
        rs.execute(sql);
        return SUCCESS;
    }
}
