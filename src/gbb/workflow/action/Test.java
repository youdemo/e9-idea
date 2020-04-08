package gbb.workflow.action;

import com.cloudstore.dev.api.bean.MessageType;
import gbb.workflow.service.ZWS_FI_GOS_VENDOR_PAYStub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import weaver.general.BaseBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) throws Exception {
        String hrmmembers = "1,2,923";
        hrmmembers = (","+hrmmembers+",").replace(",923,",",");
        if(hrmmembers.length()>1){
            hrmmembers = hrmmembers.substring(1,hrmmembers.length()-1);
        }

    }

    public static void doService() throws Exception {
        new BaseBean().writeLog("bbbbbbbb");
        try {
            DecimalFormat df1 = new DecimalFormat("#.00");
            ZWS_FI_GOS_VENDOR_PAYStub service = new ZWS_FI_GOS_VENDOR_PAYStub();
            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
            auth.setUsername("GYMIF");
            auth.setPassword("Gymif2019");
            service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
            ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAY zgvp = new ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAY();
            ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_VENDORPAY toz = new ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_VENDORPAY();
            ZWS_FI_GOS_VENDOR_PAYStub.ZFIS_VENDORPAY zv = new ZWS_FI_GOS_VENDOR_PAYStub.ZFIS_VENDORPAY();
            ZWS_FI_GOS_VENDOR_PAYStub.Char13 XBLNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char13();
            XBLNR.setChar13("1");
            zv.setXBLNR(XBLNR);//GOS单号
            ZWS_FI_GOS_VENDOR_PAYStub.Char3 XBLNR2 = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
            XBLNR2.setChar3("1");
            zv.setXBLNR2(XBLNR2);//GOS行号
            ZWS_FI_GOS_VENDOR_PAYStub.Char4 BUKRS = new ZWS_FI_GOS_VENDOR_PAYStub.Char4();
            BUKRS.setChar4("1");
            zv.setBUKRS(BUKRS);//公司代码
            ZWS_FI_GOS_VENDOR_PAYStub.Char10 LIFNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
            LIFNR.setChar10("1");
            zv.setLIFNR(LIFNR);//供应商编码
            ZWS_FI_GOS_VENDOR_PAYStub.Char10 RACCT = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
            RACCT.setChar10("1");
            zv.setRACCT(RACCT);//费用科目
            ZWS_FI_GOS_VENDOR_PAYStub.Char10 KOSTL = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
            KOSTL.setChar10("1");
            zv.setKOSTL(KOSTL);//成本中心
            ZWS_FI_GOS_VENDOR_PAYStub.Char8 PS_POSID = new ZWS_FI_GOS_VENDOR_PAYStub.Char8();
            PS_POSID.setChar8("1");
            zv.setPS_POSID(PS_POSID);//WBS编码
            ZWS_FI_GOS_VENDOR_PAYStub.Char10 KUNNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
            KUNNR.setChar10("1");
            zv.setKUNNR(KUNNR);//中心号
            ZWS_FI_GOS_VENDOR_PAYStub.Char10 BANFN = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
            BANFN.setChar10("1");
            zv.setBANFN(BANFN);//采购申请号
            ZWS_FI_GOS_VENDOR_PAYStub.Char3 WAERS = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
            WAERS.setChar3("1");
            zv.setWAERS(WAERS);//币种
            ZWS_FI_GOS_VENDOR_PAYStub.Decimal122 TSL = new ZWS_FI_GOS_VENDOR_PAYStub.Decimal122();
            TSL.setDecimal122(new BigDecimal(df1.format(123.12)));
            zv.setTSL(TSL);//不含税金额
            ZWS_FI_GOS_VENDOR_PAYStub.Decimal122 TSL2 = new ZWS_FI_GOS_VENDOR_PAYStub.Decimal122();
            TSL2.setDecimal122(new BigDecimal(df1.format(11.12)));
            zv.setTSL2(TSL2);//税额
            ZWS_FI_GOS_VENDOR_PAYStub.Char1 TAX = new ZWS_FI_GOS_VENDOR_PAYStub.Char1();
            TAX.setChar1("1");
            zv.setTAX(TAX);//是否抵扣
            ZWS_FI_GOS_VENDOR_PAYStub.Char30 BKTXT = new ZWS_FI_GOS_VENDOR_PAYStub.Char30();
            BKTXT.setChar30("1");
            zv.setBKTXT(BKTXT);//描述
            ZWS_FI_GOS_VENDOR_PAYStub.Date10 BLDAT = new ZWS_FI_GOS_VENDOR_PAYStub.Date10();
            BLDAT.setDate10("2019-10-10");
            zv.setBLDAT(BLDAT);//审核日期
            ZWS_FI_GOS_VENDOR_PAYStub.Char12 USNAM = new ZWS_FI_GOS_VENDOR_PAYStub.Char12();
            USNAM.setChar12("1");
            zv.setUSNAM(USNAM);//审核人
            toz.addItem(zv);
            zgvp.setIT_ITEM(toz);
            ZWS_FI_GOS_VENDOR_PAYStub.Char3 sysid = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
            sysid.setChar3("GOS");
            zgvp.setI_SYSID(sysid);
            ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_RETURN tzr = new ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_RETURN();
            zgvp.setET_RETURN(tzr);
            new BaseBean().writeLog("aaaaaaaa");
            ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAYResponse res = service.Z_GOS_VENDOR_PAY(zgvp);
            new BaseBean().writeLog(res.getET_RETURN().getItem()[0].toString());
            new BaseBean().writeLog("aaaaaaaa:" + res.getET_RETURN().getItem()[0].getMSGTY().toString());
        }catch(Exception e){
            new BaseBean().writeLog(e);
        }

    }
}
