package rrd.supplier.service;

import rrd.supplier.dao.SupplierInInfoDao;
import rrd.supplier.util.DESCoder;
import rrd.supplier.util.InsertUtil;
import rrd.supplier.util.TransUtil;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 9:57
 * version v1
 * desc
 */
public class SupplierInfoServiceImpl implements SupplierInfoService{

    @Override
    public String getSupplierTemporaryDataId(String requestid) {
        RecordSet rs = new RecordSet();
        TransUtil tu = new TransUtil();
        InsertUtil iu = new InsertUtil();
        String zctbname = "uf_gysxxsj_zc";
        String tablename = tu.getTableNameByRequestid(requestid);
        Map<String,String> dataMap = new HashMap<String,String>();
        String billid = "";
        String sql= "select id from uf_gysxxsj_zc where rqid='"+requestid+"'";
        rs.execute(sql);
        if(rs.next()){
            billid = Util.null2String(rs.getString("id"));
        }
        if(!"".equals(billid)){
            return billid;
        }
        sql = "select sqr,sqrq,djbh,szgs,sqbm from "+tablename+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            String szgs = Util.null2String(rs.getString("szgs"));
            String sqbm = Util.null2String(rs.getString("sqbm"));
            if("".equals(szgs)) szgs="0";
            if("".equals(sqbm)) sqbm="0";
            dataMap.put("rqid",requestid);//相关流程id
            dataMap.put("sqr",Util.null2String(rs.getString("sqr")));//申请人
            dataMap.put("sqrq",Util.null2String(rs.getString("sqrq")));//申请日期
            dataMap.put("djbh",Util.null2String(rs.getString("djbh")));//单据编号
            dataMap.put("sqbm",sqbm);//申请部门
            dataMap.put("szgs",szgs);//所属公司
            //dataMap.put("ygbh",Util.null2String(rs.getString("sqrbh")));//员工编号
            dataMap.put("sfyyyzzjggz","0");//是否有营业相关附件
            dataMap.put("sfyyhkhxxjggz","0");//是否有银行相关附件
            dataMap.put("sfysyddhjggz","0");//是否有商业相关附件
            dataMap.put("sfycwxgfj","0");//是否有银行相关附件
            dataMap.put("sfygsxgfj","0");//是否有商业相关附件
            dataMap.put("bz","0");//币种
            dataMap.put("status","0");//状态

        }
        iu.insert(dataMap,zctbname);
        sql = "select id from "+zctbname+" where rqid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            billid = rs.getString("id");
        }
        return billid;
    }

    @Override
    public SupplierInInfoDao getSupplierTemporaryData(String id) {
        SupplierInInfoDao sid = new SupplierInInfoDao();
        RecordSet rs = new RecordSet();
        String sql = "select * from uf_gysxxsj_zc where id="+id;
        rs.execute(sql);
        if(rs.next()){
            sid.setId(Util.null2String(rs.getString("id")));
            sid.setRqid(Util.null2String(rs.getString("rqid")));//相关流程id
            sid.setGsxz(Util.null2String(rs.getString("gsxz")));//公司性质
            sid.setSsqy(Util.null2String(rs.getString("ssqy")));//所属区域
            sid.setGysmc(Util.null2String(rs.getString("gysmc")));//供应商名称
            sid.setSzhy(Util.null2String(rs.getString("szhy")));//所属行业
            sid.setJyzx(Util.null2String(rs.getString("jyzx")));//经营属性
            sid.setZzs(Util.null2String(rs.getString("zzs")));//制造商
            sid.setFxs(Util.null2String(rs.getString("fxs")));//分销商
            sid.setFws(Util.null2String(rs.getString("fws")));//服务商
            sid.setYwfwgjyyzz(Util.null2String(rs.getString("ywfwgjyyzz")));//业务范围（根据营业执照）
            sid.setClsj(Util.null2String(rs.getString("clsj")));//成立时间
            sid.setGsdh(Util.null2String(rs.getString("gsdh")));//公司电话
            sid.setGyszcdz(Util.null2String(rs.getString("gyszcdz")));//供应商注册地址
            sid.setZczbw(Util.null2String(rs.getString("zczbw")));//注册资本（万）
            sid.setNxsew(Util.null2String(rs.getString("nxsew")));//年销售额（万）
            sid.setGysjydz(Util.null2String(rs.getString("gysjydz")));//供应商经营地址
            sid.setFddbr(Util.null2String(rs.getString("fddbr")));//法定代表人
            sid.setGyswz(Util.null2String(rs.getString("gyswz")));//供应商网址
            sid.setGlzlxr(Util.null2String(rs.getString("glzlxr")));//管理者联系人
            sid.setYddh(Util.null2String(rs.getString("yddh")));//移动电话
            sid.setYxdz(Util.null2String(rs.getString("yxdz")));//邮箱地址
            sid.setRcswlxr(Util.null2String(rs.getString("rcswlxr")));//日常事务联系人
            sid.setYddh1(Util.null2String(rs.getString("yddh1")));//移动电话
            sid.setYxdz1(Util.null2String(rs.getString("yxdz1")));//邮箱地址
            sid.setTyshxydm(Util.null2String(rs.getString("tyshxydm")));//统一社会信用代码
            sid.setKhh(Util.null2String(rs.getString("khh")));//开户行
            sid.setYhzh(Util.null2String(rs.getString("yhzh")));//银行账号
            sid.setWb(Util.null2String(rs.getString("wb")));//外币
            sid.setYhzhwb(Util.null2String(rs.getString("yhzhwb")));//银行账号（外币)
            sid.setSwiftcode(Util.null2String(rs.getString("swiftcode")));//SWIFT CODE
            sid.setGlgs(Util.null2String(rs.getString("glgs")));//关联公司
            sid.setYgzsr(Util.null2String(rs.getString("ygzsr")));//员工总数（人）
            sid.setBgcsmjm2(Util.null2String(rs.getString("bgcsmjm2")));//办公场所面积（m2）
            sid.setSccsmjm2(Util.null2String(rs.getString("sccsmjm2")));//生产场所面积（M2
            sid.setGlryzsr(Util.null2String(rs.getString("glryzsr")));//管理人员总数（人）
            sid.setCkmjm2(Util.null2String(rs.getString("ckmjm2")));//仓库面积（M2）
            sid.setSbhcpnlms(Util.null2String(rs.getString("sbhcpnlms")));//设备和产品能力描述
            sid.setSfysjkfnl(Util.null2String(rs.getString("sfysjkfnl")));//是否有设计开发能力？
            sid.setSjryslr(Util.null2String(rs.getString("sjryslr")));//设计人员数量（人）
            sid.setKha(Util.null2String(rs.getString("kha")));//客户A
            sid.setHya(Util.null2String(rs.getString("hya")));//行业A
            sid.setZba(Util.null2String(rs.getString("zba")));//占比A
            sid.setKhb(Util.null2String(rs.getString("khb")));//客户B
            sid.setHyb(Util.null2String(rs.getString("hyb")));//行业B
            sid.setZbb(Util.null2String(rs.getString("zbb")));//占比B
            sid.setKhc(Util.null2String(rs.getString("khc")));//客户C
            sid.setHyc(Util.null2String(rs.getString("hyc")));//行业C
            sid.setZbc(Util.null2String(rs.getString("zbc")));//占比C
            sid.setYyzzjggz(Util.null2String(rs.getString("yyzzjggz")));//营业执照（加盖公章）
            sid.setYhkhxxjggz(Util.null2String(rs.getString("yhkhxxjggz")));//银行开户信息（加盖公章）
            sid.setSyddhjggz(Util.null2String(rs.getString("syddhjggz")));//商业道德函（加盖公章）
            sid.setCwgjxx(Util.null2String(rs.getString("cwgjxx")));//财务关键信息
            sid.setZzsq(Util.null2String(rs.getString("zzsq")));//资质授权
            sid.setSbqd(Util.null2String(rs.getString("sbqd")));//设备清单
            sid.setRbcxxkytjbx(Util.null2String(rs.getString("rbcxxkytjbx")));//如补充信息可以添加表下
            sid.setGsjbxxjpyq(Util.null2String(rs.getString("gsjbxxjpyq")));//公司基本信息截屏要求
            sid.setYyzzlysm(Util.null2String(rs.getString("yyzzlysm")));//营业执照理由说明
            sid.setYhkhxxlysm(Util.null2String(rs.getString("yhkhxxlysm")));//银行开户信息理由说明
            sid.setSyddhlysm(Util.null2String(rs.getString("syddhlysm")));//商业道德函理由说明
            sid.setSl(Util.null2String(rs.getString("sl")));//税率
            sid.setSqr(Util.null2String(rs.getString("sqr")));//申请人
            sid.setSqrq(Util.null2String(rs.getString("sqrq")));//申请日期
            sid.setDjbh(Util.null2String(rs.getString("djbh")));//单据编号
            sid.setSqbm(Util.null2String(rs.getString("sqbm")));//申请部门
            sid.setSzgs(Util.null2String(rs.getString("szgs")));//所属公司
            //sid.setYgbh(Util.null2String(rs.getString("ygbh")));//员工编号
            sid.setStatus(Util.null2String(rs.getString("status")));//状态

            sid.setBz(Util.null2String(rs.getString("bz")));//交易货币
            sid.setSfyyyzzjggz(Util.null2String(rs.getString("sfyyyzzjggz")));//是否有营业相关附件
            sid.setSfyyhkhxxjggz(Util.null2String(rs.getString("sfyyhkhxxjggz")));//是否有银行相关附件
            sid.setSfysyddhjggz(Util.null2String(rs.getString("sfysyddhjggz")));//是否有商业相关附件
            sid.setCwxxmb(Util.null2String(rs.getString("cwxxmb")));//财务信息模板
            sid.setGsjbxxjtmb(Util.null2String(rs.getString("gsjbxxjtmb")));//公司基本信息截图模板
            sid.setSfycwxgfj(Util.null2String(rs.getString("sfycwxgfj")));//是否有财务相关附件
            sid.setCwrwqsmly(Util.null2String(rs.getString("cwrwqsmly")));//财务如无请说明理由
            sid.setSfygsxgfj(Util.null2String(rs.getString("sfygsxgfj")));//是否有公司相关附件
            sid.setGsrwqsmly(Util.null2String(rs.getString("gsrwqsmly")));//公司如无请说明理由
        }
        return sid;
    }

    @Override
    public void updateSupplierTemporaryData(Map<String, Object> params, String id) {

    }

    @Override
    public void updateSupplierTemporaryData(Map<String, Object> params) {

    }

    @Override
    public String getFieldHtml(String fieldName, String fieldValue, String fieldType,String isMust,String otherInfo,String otherInfo2) {
        TransUtil tu = new TransUtil();
        String fieldHtml = "";
        if("select".equals(fieldType)){
            fieldHtml = tu.getSelectHtml(fieldName,fieldValue,isMust,otherInfo);
        }else if("checkbox".equals(fieldType)){
            fieldHtml = tu.getCheckBoxHtml(fieldName,fieldValue,otherInfo);
        }else if("text".equals(fieldType)){
            fieldHtml = tu.getTextHtml(fieldName,fieldValue,isMust,otherInfo,otherInfo2);
        }else if("float".equals(fieldType)){
            fieldHtml = tu.getNumberHtml(fieldName,fieldValue,isMust,otherInfo);
        }else if("int".equals(fieldType)){
            fieldHtml = tu.getIntHtml(fieldName,fieldValue,isMust);
        }

        return fieldHtml;
    }

    @Override
    public String getRqid(String urlKey) {
        if("".equals(urlKey)){
            return "";
        }
        String requestid = "";
        String key = "ddpYENbNURw=QED";
        RecordSet rs = new RecordSet();
        String result = "";
        try {
            byte[] inputData = new BASE64Decoder().decodeBuffer(urlKey);
            byte[] outputData = DESCoder.decrypt(inputData, key);
            result = new String(outputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jmattr[] = result.split(",");
        if(jmattr.length==2){
            String rqid=jmattr[0];
            int count =0;
            String sql = "select count(1) as count from workflow_requestbase where requestid='"+rqid+"'";
            rs.execute(sql);
            if(rs.next()){
                count = rs.getInt("count");
            }

            if(count>0){
                requestid = rqid;
            }

        }
        return requestid;
    }

    @Override
    public String saveData(Map<String, String> map,String submittype,String rqid,String zcid) {
        new BaseBean().writeLog("SupplierInfoServiceImpl","rqid:"+rqid);
        String result = "";
        RecordSet rs = new RecordSet();
        int count =0;
        InsertUtil iu = new InsertUtil();
        TransUtil tu = new TransUtil();
        String sql = "select count(1) as count from uf_gysxxsj_zc where id="+zcid+" and rqid='"+rqid+"'";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count == 0){
            return "error";
        }
        if("zc".equals(submittype)){//暂存保存

            map.put("status","1");
            iu.updateGen(map,"uf_gysxxsj_zc","id",zcid);
        }else if("submit".equals(submittype)){//提交
            count =0;
            sql = "select count(1) as count from uf_gysxxsj where rqid='"+rqid+"'";
            rs.execute(sql);
            if(rs.next()){
                count = rs.getInt("count");
            }
            if(count > 0){
                return "请勿重复提交";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            String nowDate = sdf.format(new Date());
            String nowTime = sdf1.format(new Date());
            String modeid = tu.getModeId("uf_gysxxsj");
            map.put("status","2");
            map.put("rqid",rqid);
            sql = "select * from uf_gysxxsj_zc where id="+zcid;
            rs.execute(sql);
            if(rs.next()){
                String sqr = Util.null2String(rs.getString("sqr"));
                String accsec = Util.null2o(weaver.file.Prop.getPropValue("rrdsupplierzr", "secid"));
                map.put("sqr",sqr);
                map.put("sqrq",Util.null2String(rs.getString("sqrq")));
                map.put("djbh",Util.null2String(rs.getString("djbh")));
                map.put("sqbm",Util.null2String(rs.getString("sqbm")));
                map.put("szgs",Util.null2String(rs.getString("szgs")));
               // map.put("ygbh",Util.null2String(rs.getString("ygbh")));
                map.put("sfyxcshbg","0");
                map.put("sfysbqd","0");

                map.put("modedatacreatedate",nowDate);
                map.put("modedatacreatetime",nowTime);
                map.put("modedatacreater",sqr);
                map.put("modedatacreatertype","0");
                map.put("formmodeid",modeid);
                //文档复制
                String yyzzjggz = tu.copyFile(map.get("yyzzjggz"),Integer.valueOf(accsec),1);//营业执照（加盖公章）
                String yhkhxxjggz = tu.copyFile(map.get("yhkhxxjggz"),Integer.valueOf(accsec),1);//银行开户信息（加盖公章）
                String syddhjggz = tu.copyFile(map.get("syddhjggz"),Integer.valueOf(accsec),1);//商业道德函（加盖公章）
                String cwgjxx = tu.copyFile(map.get("cwgjxx"),Integer.valueOf(accsec),1);//财务关键信息
                String zzsq = tu.copyFile(map.get("zzsq"),Integer.valueOf(accsec),1);//资质授权
                String sbqd = tu.copyFile(map.get("sbqd"),Integer.valueOf(accsec),1);//设备清单
                String rbcxxkytjbx = tu.copyFile(map.get("rbcxxkytjbx"),Integer.valueOf(accsec),1);//如补充信息可以添加表下
                String gsjbxxjpyq = tu.copyFile(map.get("gsjbxxjpyq"),Integer.valueOf(accsec),1);//公司基本信息截屏要求
                map.put("yyzzjggz",yyzzjggz);
                map.put("yhkhxxjggz",yhkhxxjggz);
                map.put("syddhjggz",syddhjggz);
                map.put("cwgjxx",cwgjxx);
                map.put("zzsq",zzsq);
                map.put("sbqd",sbqd);
                map.put("rbcxxkytjbx",rbcxxkytjbx);
                map.put("gsjbxxjpyq",gsjbxxjpyq);
                iu.insert(map,"uf_gysxxsj");
                String billid = "";
                sql = "select max(id) as billid from uf_gysxxsj where rqid='" + rqid + "'";
                rs.execute(sql);
                if (rs.next()) {
                    billid = Util.null2String(rs.getString("billid"));
                }
                if (!"".equals(billid)) {
                    ModeRightInfo ModeRightInfo = new ModeRightInfo();
                    ModeRightInfo.setNewRight(true);
                    ModeRightInfo.editModeDataShare(
                            Integer.valueOf(sqr),
                            Util.getIntValue(modeid),
                            Integer.valueOf(billid));
                    ModeRightInfo.addDocShare( Integer.valueOf(sqr),
                            Util.getIntValue(modeid),
                            Integer.valueOf(billid));
                }
                rs.execute("update uf_gysxxsj_zc set status='2' where id="+zcid);
            }


        }
        result = "success";
        return result;
    }





}
