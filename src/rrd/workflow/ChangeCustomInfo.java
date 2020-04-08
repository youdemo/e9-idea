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

public class ChangeCustomInfo implements Action {
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
        String jcsjbg = "1";// 0 是 1 否
        String khflxxbg = "1";//0 是 1 否
        String buxxbg = "1";//0 是 1 否
        String xyxgxxbg = "1";//0 是 1 否 信用相关信息
        String sql = "select * from "+tableName+" where requestid="+requestid;
        String khdm = "";
        rs.execute(sql);
        if(rs.next()){
            khdm = Util.null2String(rs.getString("khdm"));
            baseMap.put("khmc", Util.null2String(rs.getString("khmc")));//客户名称
            baseMap.put("szgj", Util.null2String(rs.getString("szgj")));//客户所属国家
            baseMap.put("szsf", Util.null2String(rs.getString("szsf")));//客户所属省份
            baseMap.put("khdz", Util.null2String(rs.getString("khdz")));//客户地址
            baseMap.put("khlx", Util.null2String(rs.getString("khlx")));//客户类型
            baseMap.put("fplx", Util.null2String(rs.getString("fplx")));//发票类型
            baseMap.put("nsrsbh", Util.null2String(rs.getString("nsrsbh")));//纳税人识别号
            baseMap.put("fptt", Util.null2String(rs.getString("fptt")));//发票抬头
            baseMap.put("fpjsdz", Util.null2String(rs.getString("fpjsdz")));//发票寄送地址
            baseMap.put("khyhmc", Util.null2String(rs.getString("khyhmc")));//开户银行名称
            baseMap.put("khyhzh", Util.null2String(rs.getString("khyhzh")));//开户银行账号
            baseMap.put("kpdh", Util.null2String(rs.getString("kpdh")));//开票电话
            baseMap.put("xslx", Util.null2String(rs.getString("xslx")));//销售类型
            baseMap.put("jyhb", Util.null2String(rs.getString("jyhb")));//交易货币
            baseMap.put("sl", Util.null2String(rs.getString("sl")));//税率
            baseMap.put("sfyj", Util.null2String(rs.getString("sfyj")));//是否月结
            baseMap.put("fkzq", Util.null2String(rs.getString("fkzq")));//付款账期
            baseMap.put("yjdst", Util.null2String(rs.getString("yjdst")));//月结多少天
            baseMap.put("dqr", Util.null2String(rs.getString("dqr")));//到期日
            baseMap.put("fkr", Util.null2String(rs.getString("fkr")));//付款日
            baseMap.put("fkfs", Util.null2String(rs.getString("fkfs")));//付款方式
            baseMap.put("htfktk", Util.null2String(rs.getString("htfktk")));//合同付款条款
            baseMap.put("sfxyzf", Util.null2String(rs.getString("sfxyzf")));//是否需要支付
            baseMap.put("mgxsxm1", Util.null2String(rs.getString("mgxsxm1")));//美国销售姓名1
            baseMap.put("mgxsxm2", Util.null2String(rs.getString("mgxsxm2")));//美国销售姓名2
            baseMap.put("mgxsxm3", Util.null2String(rs.getString("mgxsxm3")));//美国销售姓名3
            baseMap.put("mgxsxm4", Util.null2String(rs.getString("mgxsxm4")));//美国销售姓名4
            baseMap.put("yjnxse", Util.null2String(rs.getString("yjnxse")));//预计年销售额
            baseMap.put("sqxded", Util.null2String(rs.getString("sqxded")));//申请信贷额度
            baseMap.put("xyeddhb", Util.null2String(rs.getString("xyeddhb")));//信用额度的货币
            baseMap.put("hl", Util.null2String(rs.getString("hl")));//汇率
            baseMap.put("sqxdedmj", Util.null2String(rs.getString("sqxdedmj")));//申请信贷额度（美金）
            
            baseMap.put("csjtmc", Util.null2String(rs.getString("csjtmc")));//从事集团名称
            baseMap.put("cwlxrxm", Util.null2String(rs.getString("cwlxrxm")));//财务联系人姓名
            baseMap.put("cwlxrdh", Util.null2String(rs.getString("cwlxrdh")));//财务联系人电话
            baseMap.put("cwlxryj", Util.null2String(rs.getString("cwlxryj")));//财务联系人邮件
            baseMap.put("cwlxrcz", Util.null2String(rs.getString("cwlxrcz")));//财务联系人传真
            baseMap.put("cwlxrdz", Util.null2String(rs.getString("cwlxrdz")));//财务联系人地址
            baseMap.put("xslxrxm", Util.null2String(rs.getString("xslxrxm")));//销售联系人姓名
            baseMap.put("xslxrdh", Util.null2String(rs.getString("xslxrdh")));//销售联系人电话
            baseMap.put("xslxryj", Util.null2String(rs.getString("xslxryj")));//销售联系人邮件
            baseMap.put("xslxrcz", Util.null2String(rs.getString("xslxrcz")));//销售联系人传真
            baseMap.put("xslxrdz", Util.null2String(rs.getString("xslxrdz")));//销售联系人地址
            baseMap.put("yyzz", Util.null2String(rs.getString("yyzz")));//营业执照
            baseMap.put("kpzl", Util.null2String(rs.getString("kpzl")));//开票资料
            baseMap.put("dsfdcbg", Util.null2String(rs.getString("dsfdcbg")));//第三方调查报告
            baseMap.put("commisionxy", Util.null2String(rs.getString("commisionxy")));//Commision协议
            baseMap.put("q", Util.null2String(rs.getString("q")));//其他

            khflMap.put("khszhy", Util.null2String(rs.getString("khszhy")));//客户所属行业
            khflMap.put("khqz", Util.null2String(rs.getString("khqz")));//客户群组
            khflMap.put("ywx", Util.null2String(rs.getString("ywx")));//产品线
            khflMap.put("xjkhqzrealname", Util.null2String(rs.getString("xjkhqzrealname")));//新建客户群组（realname
            khflMap.put("xjkhqznickname", Util.null2String(rs.getString("xjkhqznickname")));//新建客户群组（nickname）

            buMap.put("xsfzrkhjl1", Util.null2String(rs.getString("xsfzrkhjl1")));//销售负责人/客户经理1
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
			buMap.put("sfczmyjt", Util.null2String(rs.getString("sfczmyjt")));//是否从属某一集团

            xyMap.put("xyq", Util.null2String(rs.getString("xyq")));//信用期
            xyMap.put("xyed", Util.null2String(rs.getString("xyed")));//信用额度
        }

        sql = "select * from uf_xzkhsp where khdm= '"+khdm+"'";
        rs.execute(sql);
        if(rs.next()){
            baseMapMode.put("khmc", Util.null2String(rs.getString("khmc")));//客户名称
            baseMapMode.put("szgj", Util.null2String(rs.getString("szgj")));//客户所属国家
            baseMapMode.put("szsf", Util.null2String(rs.getString("szsf")));//客户所属省份
            baseMapMode.put("khdz", Util.null2String(rs.getString("khdz")));//客户地址
            baseMapMode.put("khlx", Util.null2String(rs.getString("khlx")));//客户类型
            baseMapMode.put("fplx", Util.null2String(rs.getString("fplx")));//发票类型
            baseMapMode.put("nsrsbh", Util.null2String(rs.getString("nsrsbh")));//纳税人识别号
            baseMapMode.put("fptt", Util.null2String(rs.getString("fptt")));//发票抬头
            baseMapMode.put("fpjsdz", Util.null2String(rs.getString("fpjsdz")));//发票寄送地址
            baseMapMode.put("khyhmc", Util.null2String(rs.getString("khyhmc")));//开户银行名称
            baseMapMode.put("khyhzh", Util.null2String(rs.getString("khyhzh")));//开户银行账号
            baseMapMode.put("kpdh", Util.null2String(rs.getString("kpdh")));//开票电话
            baseMapMode.put("xslx", Util.null2String(rs.getString("xslx")));//销售类型
            baseMapMode.put("jyhb", Util.null2String(rs.getString("jyhb")));//交易货币
            baseMapMode.put("sl", Util.null2String(rs.getString("sl")));//税率
            baseMapMode.put("sfyj", Util.null2String(rs.getString("sfyj")));//是否月结
            baseMapMode.put("fkzq", Util.null2String(rs.getString("fkzq")));//付款账期
            baseMapMode.put("yjdst", Util.null2String(rs.getString("yjdst")));//月结多少天
            baseMapMode.put("dqr", Util.null2String(rs.getString("dqr")));//到期日
            baseMapMode.put("fkr", Util.null2String(rs.getString("fkr")));//付款日
            baseMapMode.put("fkfs", Util.null2String(rs.getString("fkfs")));//付款方式
            baseMapMode.put("htfktk", Util.null2String(rs.getString("htfktk")));//合同付款条款
            baseMapMode.put("sfxyzf", Util.null2String(rs.getString("sfxyzf")));//是否需要支付
            baseMapMode.put("mgxsxm1", Util.null2String(rs.getString("mgxsxm1")));//美国销售姓名1
            baseMapMode.put("mgxsxm2", Util.null2String(rs.getString("mgxsxm2")));//美国销售姓名2
            baseMapMode.put("mgxsxm3", Util.null2String(rs.getString("mgxsxm3")));//美国销售姓名3
            baseMapMode.put("mgxsxm4", Util.null2String(rs.getString("mgxsxm4")));//美国销售姓名4
            baseMapMode.put("yjnxse", Util.null2String(rs.getString("yjnxse")));//预计年销售额
            baseMapMode.put("sqxded", Util.null2String(rs.getString("sqxded")));//申请信贷额度
            baseMapMode.put("xyeddhb", Util.null2String(rs.getString("xyeddhb")));//信用额度的货币
            baseMapMode.put("hl", Util.null2String(rs.getString("hl")));//汇率
            baseMapMode.put("sqxdedmj", Util.null2String(rs.getString("sqxdedmj")));//申请信贷额度（美金）
            
            baseMapMode.put("csjtmc", Util.null2String(rs.getString("csjtmc")));//从事集团名称
            baseMapMode.put("cwlxrxm", Util.null2String(rs.getString("cwlxrxm")));//财务联系人姓名
            baseMapMode.put("cwlxrdh", Util.null2String(rs.getString("cwlxrdh")));//财务联系人电话
            baseMapMode.put("cwlxryj", Util.null2String(rs.getString("cwlxryj")));//财务联系人邮件
            baseMapMode.put("cwlxrcz", Util.null2String(rs.getString("cwlxrcz")));//财务联系人传真
            baseMapMode.put("cwlxrdz", Util.null2String(rs.getString("cwlxrdz")));//财务联系人地址
            baseMapMode.put("xslxrxm", Util.null2String(rs.getString("xslxrxm")));//销售联系人姓名
            baseMapMode.put("xslxrdh", Util.null2String(rs.getString("xslxrdh")));//销售联系人电话
            baseMapMode.put("xslxryj", Util.null2String(rs.getString("xslxryj")));//销售联系人邮件
            baseMapMode.put("xslxrcz", Util.null2String(rs.getString("xslxrcz")));//销售联系人传真
            baseMapMode.put("xslxrdz", Util.null2String(rs.getString("xslxrdz")));//销售联系人地址
            baseMapMode.put("yyzz", Util.null2String(rs.getString("yyzz")));//营业执照
            baseMapMode.put("kpzl", Util.null2String(rs.getString("kpzl")));//开票资料
            baseMapMode.put("dsfdcbg", Util.null2String(rs.getString("dsfdcbg")));//第三方调查报告
            baseMapMode.put("commisionxy", Util.null2String(rs.getString("commisionxy")));//Commision协议
            baseMapMode.put("q", Util.null2String(rs.getString("q")));//其他

            khflMapMode.put("khszhy", Util.null2String(rs.getString("khszhy")));//客户所属行业            
            khflMapMode.put("khqz", Util.null2String(rs.getString("khqz")));//客户群组            
            khflMapMode.put("ywx", Util.null2String(rs.getString("ywx")));//产品线
            khflMapMode.put("xjkhqzrealname", Util.null2String(rs.getString("xjkhqzrealname")));//新建客户群组（realname
            khflMapMode.put("xjkhqznickname", Util.null2String(rs.getString("xjkhqznickname")));//新建客户群组（nickname）

            buMapMode.put("xsfzrkhjl1", Util.null2String(rs.getString("xsfzrkhjl1")));//销售负责人/客户经理1
            buMapMode.put("xs1", Util.null2String(rs.getString("xs1")));//销售1
			buMapMode.put("csrjl", Util.null2String(rs.getString("csrjl")));//CSR经理
			buMapMode.put("khsqry1", Util.null2String(rs.getString("khsqry1")));//客户授权人员1
            			
            buMapMode.put("esjl", Util.null2String(rs.getString("esjl")));//ES经理
            buMapMode.put("xsfzrkhjl2", Util.null2String(rs.getString("xsfzrkhjl2")));//销售负责人/客户经理2
            buMapMode.put("xs2", Util.null2String(rs.getString("xs2")));//销售2
			buMapMode.put("aejl", Util.null2String(rs.getString("aejl")));//AE经理
			buMapMode.put("khsqry2", Util.null2String(rs.getString("khsqry2")));//客户授权人员2
			buMapMode.put("hyzfl", Util.null2String(rs.getString("hyzfl")));//行业子分类
            buMapMode.put("szqy", Util.null2String(rs.getString("szqy")));//所在区域
            
            buMapMode.put("xsfzrkhjl3", Util.null2String(rs.getString("xsfzrkhjl3")));//销售负责人/客户经理3
            buMapMode.put("xs3", Util.null2String(rs.getString("xs3")));//销售3
            buMapMode.put("csrjl2", Util.null2String(rs.getString("csrjl2")));//CSR经理2
			buMapMode.put("khsqry3", Util.null2String(rs.getString("khsqry2")));//客户授权人员3

			buMapMode.put("sfczmyjt", Util.null2String(rs.getString("sfczmyjt")));//是否从属某一集团
			buMapMode.put("oemodmjt", Util.null2String(rs.getString("oemodmjt")));//OEM/ODM集团

            xyMapMode.put("xyq", Util.null2String(rs.getString("xyq")));//信用期
            xyMapMode.put("xyed", Util.null2String(rs.getString("xyed")));//信用额度
        }

        Iterator itbase = baseMapMode.keySet().iterator();
        while(itbase.hasNext()){
            String filename = (String) itbase.next();
            String modevalue = baseMapMode.get(filename);
            String flowvalue = baseMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeCustomInfo baseMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
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
                new BaseBean().writeLog("ChangeCustomInfo khflMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                khflxxbg = "0";
                break;
            }
        }

        Iterator  itbu = buMapMode.keySet().iterator();
        while(itbu.hasNext()){
            String filename = (String) itbu.next();
            String modevalue = buMapMode.get(filename);
            String flowvalue = buMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeCustomInfo buMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                buxxbg = "0";
                break;
            }
        }

        Iterator  itxy = xyMapMode.keySet().iterator();
        while(itxy.hasNext()){
            String filename = (String) itxy.next();
            String modevalue = xyMapMode.get(filename);
            String flowvalue = xyMap.get(filename);
            if(!modevalue.equals(flowvalue)){
                new BaseBean().writeLog("ChangeCustomInfo xyMapMode","filename:"+filename+" modevalue:"+modevalue+" flowvalue:"+flowvalue);
                xyxgxxbg = "0";
                break;
            }
        }
        sql = "update "+tableName+" set jcsjbg='"+jcsjbg+"',khflxxbg='"+khflxxbg+"',buxxbg='"+buxxbg+"',xyxgxxbg='"+xyxgxxbg+"' where requestid="+requestid;
        new BaseBean().writeLog("ChangeCustomInfo",sql);
        rs.execute(sql);
        return SUCCESS;
    }
}
