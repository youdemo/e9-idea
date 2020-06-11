package rrd.supplier.dao;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 9:10
 * version v1
 * desc 供应商准入信息
 */
public class SupplierInInfoDao20200417 {
    private String id = "";//数据ID
    private String rqid = "";//相关流程id
    private String gsxz = "";//公司性质
    private String ssqy = "";//所属区域
    private String gysmc = "";//供应商名称
    private String szhy = "";//所属行业
    private String jyzx = "";//经营属性
    private String zzs = "";//制造商
    private String fxs = "";//分销商
    private String fws = "";//服务商
    private String ywfwgjyyzz = "";//业务范围（根据营业执照）
    private String clsj = "";//成立时间
    private String gsdh = "";//公司电话
    private String gyszcdz = "";//供应商注册地址
    private String zczbw = "";//注册资本（万）
    private String nxsew = "";//年销售额（万）
    private String gysjydz = "";//供应商经营地址
    private String fddbr = "";//法定代表人
    private String gyswz = "";//供应商网址
    private String glzlxr = "";//管理者联系人
    private String yddh = "";//移动电话
    private String yxdz = "";//邮箱地址
    private String rcswlxr = "";//日常事务联系人
    private String yddh1 = "";//移动电话
    private String yxdz1 = "";//邮箱地址
    private String tyshxydm = "";//统一社会信用代码
    private String khh = "";//开户行
    private String yhzh = "";//银行账号
    private String wb = "";//外币
    private String yhzhwb = "";//银行账号（外币)
    private String swiftcode = "";//SWIFT CODE
    private String glgs = "";//关联公司
    private String ygzsr = "";//员工总数（人）
    private String bgcsmjm2 = "";//办公场所面积（m2）
    private String sccsmjm2 = "";//生产场所面积（M2
    private String glryzsr = "";//管理人员总数（人）
    private String ckmjm2 = "";//仓库面积（M2）
    private String sbhcpnlms = "";//设备和产品能力描述
    private String sfysjkfnl = "";//是否有设计开发能力？
    private String sjryslr = "";//设计人员数量（人）
    private String kha = "";//客户A
    private String hya = "";//行业A
    private String zba = "";//占比A
    private String khb = "";//客户B
    private String hyb = "";//行业B
    private String zbb = "";//占比B
    private String khc = "";//客户C
    private String hyc = "";//行业C
    private String zbc = "";//占比C
    private String yyzzjggz = "";//营业执照（加盖公章）
    private String yhkhxxjggz = "";//银行开户信息（加盖公章）
    private String syddhjggz = "";//商业道德函（加盖公章）
    private String cwgjxx = "";//财务关键信息
    private String zzsq = "";//资质授权
    private String sbqd = "";//设备清单
    private String rbcxxkytjbx = "";//如补充信息可以添加表下
    private String gsjbxxjpyq = "";//公司基本信息截屏要求
    private String yyzzlysm = "";//营业执照理由说明
    private String yhkhxxlysm = "";//银行开户信息理由说明
    private String syddhlysm = "";//商业道德函理由说明
    private String sl = "";//税率
    private String sqr = "";//申请人
    private String sqrq = "";//申请日期
    private String djbh = "";//单据编号
    private String sqbm = "";//申请部门
    private String szgs = "";//所属公司
    //private String ygbh = "";//员工编号
    private String status = "";//状态

    private String bz = "";//交易货币
    private String sfyyyzzjggz = "";//是否有营业相关附件 sfyyyzzjggz sfyyyxgfj
    private String sfyyhkhxxjggz = "";//是否有银行相关附件 sfyyhkhxxjggz sfyyhxgfj
    private String sfysyddhjggz = "";//是否有商业相关附件 sfysyddhjggz sfysyxgfj
    private String cwxxmb = "";//财务信息模板
    private String gsjbxxjtmb = "";//公司基本信息截图模板
    private String sfycwxgfj = "";//是否有财务相关附件
    private String cwrwqsmly = "";//财务如无请说明理由
    private String sfygsxgfj = "";//是否有公司相关附件
    private String gsrwqsmly = "";//公司如无请说明理由

    public String getId() {
        return id;
    }

    public String getSfycwxgfj() {
        return sfycwxgfj;
    }

    public void setSfycwxgfj(String sfycwxgfj) {
        this.sfycwxgfj = sfycwxgfj;
    }

    public String getCwrwqsmly() {
        return cwrwqsmly;
    }

    public void setCwrwqsmly(String cwrwqsmly) {
        this.cwrwqsmly = cwrwqsmly;
    }

    public String getSfygsxgfj() {
        return sfygsxgfj;
    }

    public void setSfygsxgfj(String sfygsxgfj) {
        this.sfygsxgfj = sfygsxgfj;
    }

    public String getGsrwqsmly() {
        return gsrwqsmly;
    }

    public void setGsrwqsmly(String gsrwqsmly) {
        this.gsrwqsmly = gsrwqsmly;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRqid() {
        return rqid;
    }

    public void setRqid(String rqid) {
        this.rqid = rqid;
    }

    public String getGsxz() {
        return gsxz;
    }

    public void setGsxz(String gsxz) {
        this.gsxz = gsxz;
    }

    public String getSsqy() {
        return ssqy;
    }

    public void setSsqy(String ssqy) {
        this.ssqy = ssqy;
    }

    public String getGysmc() {
        return gysmc;
    }

    public void setGysmc(String gysmc) {
        this.gysmc = gysmc;
    }

    public String getSzhy() {
        return szhy;
    }

    public void setSzhy(String szhy) {
        this.szhy = szhy;
    }

    public String getJyzx() {
        return jyzx;
    }

    public void setJyzx(String jyzx) {
        this.jyzx = jyzx;
    }

    public String getZzs() {
        return zzs;
    }

    public void setZzs(String zzs) {
        this.zzs = zzs;
    }

    public String getFxs() {
        return fxs;
    }

    public void setFxs(String fxs) {
        this.fxs = fxs;
    }

    public String getFws() {
        return fws;
    }

    public void setFws(String fws) {
        this.fws = fws;
    }

    public String getYwfwgjyyzz() {
        return ywfwgjyyzz;
    }

    public void setYwfwgjyyzz(String ywfwgjyyzz) {
        this.ywfwgjyyzz = ywfwgjyyzz;
    }

    public String getClsj() {
        return clsj;
    }

    public void setClsj(String clsj) {
        this.clsj = clsj;
    }

    public String getGsdh() {
        return gsdh;
    }

    public void setGsdh(String gsdh) {
        this.gsdh = gsdh;
    }

    public String getGyszcdz() {
        return gyszcdz;
    }

    public void setGyszcdz(String gyszcdz) {
        this.gyszcdz = gyszcdz;
    }

    public String getZczbw() {
        return zczbw;
    }

    public void setZczbw(String zczbw) {
        this.zczbw = zczbw;
    }

    public String getNxsew() {
        return nxsew;
    }

    public void setNxsew(String nxsew) {
        this.nxsew = nxsew;
    }

    public String getGysjydz() {
        return gysjydz;
    }

    public void setGysjydz(String gysjydz) {
        this.gysjydz = gysjydz;
    }

    public String getFddbr() {
        return fddbr;
    }

    public void setFddbr(String fddbr) {
        this.fddbr = fddbr;
    }

    public String getGyswz() {
        return gyswz;
    }

    public void setGyswz(String gyswz) {
        this.gyswz = gyswz;
    }

    public String getGlzlxr() {
        return glzlxr;
    }

    public void setGlzlxr(String glzlxr) {
        this.glzlxr = glzlxr;
    }

    public String getYddh() {
        return yddh;
    }

    public void setYddh(String yddh) {
        this.yddh = yddh;
    }

    public String getYxdz() {
        return yxdz;
    }

    public void setYxdz(String yxdz) {
        this.yxdz = yxdz;
    }

    public String getRcswlxr() {
        return rcswlxr;
    }

    public void setRcswlxr(String rcswlxr) {
        this.rcswlxr = rcswlxr;
    }

    public String getYddh1() {
        return yddh1;
    }

    public void setYddh1(String yddh1) {
        this.yddh1 = yddh1;
    }

    public String getYxdz1() {
        return yxdz1;
    }

    public void setYxdz1(String yxdz1) {
        this.yxdz1 = yxdz1;
    }

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm;
    }

    public String getKhh() {
        return khh;
    }

    public void setKhh(String khh) {
        this.khh = khh;
    }

    public String getYhzh() {
        return yhzh;
    }

    public void setYhzh(String yhzh) {
        this.yhzh = yhzh;
    }

    public String getWb() {
        return wb;
    }

    public void setWb(String wb) {
        this.wb = wb;
    }

    public String getYhzhwb() {
        return yhzhwb;
    }

    public void setYhzhwb(String yhzhwb) {
        this.yhzhwb = yhzhwb;
    }

    public String getSwiftcode() {
        return swiftcode;
    }

    public void setSwiftcode(String swiftcode) {
        this.swiftcode = swiftcode;
    }

    public String getGlgs() {
        return glgs;
    }

    public void setGlgs(String glgs) {
        this.glgs = glgs;
    }

    public String getYgzsr() {
        return ygzsr;
    }

    public void setYgzsr(String ygzsr) {
        this.ygzsr = ygzsr;
    }

    public String getBgcsmjm2() {
        return bgcsmjm2;
    }

    public void setBgcsmjm2(String bgcsmjm2) {
        this.bgcsmjm2 = bgcsmjm2;
    }

    public String getSccsmjm2() {
        return sccsmjm2;
    }

    public void setSccsmjm2(String sccsmjm2) {
        this.sccsmjm2 = sccsmjm2;
    }

    public String getGlryzsr() {
        return glryzsr;
    }

    public void setGlryzsr(String glryzsr) {
        this.glryzsr = glryzsr;
    }

    public String getCkmjm2() {
        return ckmjm2;
    }

    public void setCkmjm2(String ckmjm2) {
        this.ckmjm2 = ckmjm2;
    }

    public String getSbhcpnlms() {
        return sbhcpnlms;
    }

    public void setSbhcpnlms(String sbhcpnlms) {
        this.sbhcpnlms = sbhcpnlms;
    }

    public String getSfysjkfnl() {
        return sfysjkfnl;
    }

    public void setSfysjkfnl(String sfysjkfnl) {
        this.sfysjkfnl = sfysjkfnl;
    }

    public String getSjryslr() {
        return sjryslr;
    }

    public void setSjryslr(String sjryslr) {
        this.sjryslr = sjryslr;
    }

    public String getKha() {
        return kha;
    }

    public void setKha(String kha) {
        this.kha = kha;
    }

    public String getHya() {
        return hya;
    }

    public void setHya(String hya) {
        this.hya = hya;
    }

    public String getZba() {
        return zba;
    }

    public void setZba(String zba) {
        this.zba = zba;
    }

    public String getKhb() {
        return khb;
    }

    public void setKhb(String khb) {
        this.khb = khb;
    }

    public String getHyb() {
        return hyb;
    }

    public void setHyb(String hyb) {
        this.hyb = hyb;
    }

    public String getZbb() {
        return zbb;
    }

    public void setZbb(String zbb) {
        this.zbb = zbb;
    }

    public String getKhc() {
        return khc;
    }

    public void setKhc(String khc) {
        this.khc = khc;
    }

    public String getHyc() {
        return hyc;
    }

    public void setHyc(String hyc) {
        this.hyc = hyc;
    }

    public String getZbc() {
        return zbc;
    }

    public void setZbc(String zbc) {
        this.zbc = zbc;
    }

    public String getYyzzjggz() {
        return yyzzjggz;
    }

    public void setYyzzjggz(String yyzzjggz) {
        this.yyzzjggz = yyzzjggz;
    }

    public String getYhkhxxjggz() {
        return yhkhxxjggz;
    }

    public void setYhkhxxjggz(String yhkhxxjggz) {
        this.yhkhxxjggz = yhkhxxjggz;
    }

    public String getSyddhjggz() {
        return syddhjggz;
    }

    public void setSyddhjggz(String syddhjggz) {
        this.syddhjggz = syddhjggz;
    }

    public String getCwgjxx() {
        return cwgjxx;
    }

    public void setCwgjxx(String cwgjxx) {
        this.cwgjxx = cwgjxx;
    }

    public String getZzsq() {
        return zzsq;
    }

    public void setZzsq(String zzsq) {
        this.zzsq = zzsq;
    }

    public String getSbqd() {
        return sbqd;
    }

    public void setSbqd(String sbqd) {
        this.sbqd = sbqd;
    }

    public String getRbcxxkytjbx() {
        return rbcxxkytjbx;
    }

    public void setRbcxxkytjbx(String rbcxxkytjbx) {
        this.rbcxxkytjbx = rbcxxkytjbx;
    }

    public String getGsjbxxjpyq() {
        return gsjbxxjpyq;
    }

    public void setGsjbxxjpyq(String gsjbxxjpyq) {
        this.gsjbxxjpyq = gsjbxxjpyq;
    }

    public String getYyzzlysm() {
        return yyzzlysm;
    }

    public void setYyzzlysm(String yyzzlysm) {
        this.yyzzlysm = yyzzlysm;
    }

    public String getYhkhxxlysm() {
        return yhkhxxlysm;
    }

    public void setYhkhxxlysm(String yhkhxxlysm) {
        this.yhkhxxlysm = yhkhxxlysm;
    }

    public String getSyddhlysm() {
        return syddhlysm;
    }

    public void setSyddhlysm(String syddhlysm) {
        this.syddhlysm = syddhlysm;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getSqrq() {
        return sqrq;
    }

    public void setSqrq(String sqrq) {
        this.sqrq = sqrq;
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh;
    }

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }

    public String getSzgs() {
        return szgs;
    }

    public void setSzgs(String szgs) {
        this.szgs = szgs;
    }

//    public String getYgbh() {
//        return ygbh;
//    }
//
//    public void setYgbh(String ygbh) {
//        this.ygbh = ygbh;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfyyyzzjggz() {
        return sfyyyzzjggz;
    }

    public void setSfyyyzzjggz(String sfyyyzzjggz) {
        this.sfyyyzzjggz = sfyyyzzjggz;
    }

    public String getSfyyhkhxxjggz() {
        return sfyyhkhxxjggz;
    }

    public void setSfyyhkhxxjggz(String sfyyhkhxxjggz) {
        this.sfyyhkhxxjggz = sfyyhkhxxjggz;
    }

    public String getSfysyddhjggz() {
        return sfysyddhjggz;
    }

    public void setSfysyddhjggz(String sfysyddhjggz) {
        this.sfysyddhjggz = sfysyddhjggz;
    }

    public String getCwxxmb() {
        return cwxxmb;
    }

    public void setCwxxmb(String cwxxmb) {
        this.cwxxmb = cwxxmb;
    }

    public String getGsjbxxjtmb() {
        return gsjbxxjtmb;
    }

    public void setGsjbxxjtmb(String gsjbxxjtmb) {
        this.gsjbxxjtmb = gsjbxxjtmb;
    }
}
