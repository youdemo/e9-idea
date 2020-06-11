package APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.SERVICE.IMPL;

import APPDEV.HQ.Contract.ConCommonClass;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELFEEREPORT.SERVICE.TravelFeeReportDataServcie;
import com.engine.common.util.ParamUtil;
import com.engine.core.impl.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;


public class TravelFeeReportServcieImpl extends Service implements TravelFeeReportDataServcie {

    @Override
    public Map<String, Object> weatableDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeaTableCmd(user,params));
    }

    @Override
    public Map<String, Object> weatableConditonDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeatableConditonCmd(user,params));
    }

    @Override
    public InputStream WeaReportOutExcel(HttpServletRequest request, HttpServletResponse response) {
        String userid = user.getUID()+"";
        Map<String, Object> params= ParamUtil.request2Map(request);
        RecordSet rs = new RecordSet();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setColor(HSSFFont.COLOR_NORMAL);
        HSSFCellStyle sheetStyle = wb.createCellStyle();
        sheetStyle.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        sheetStyle.setFont(font);
        sheetStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheetStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());

        //sheetStyle.setWrapText(true);
        sheetStyle.setBorderBottom(BorderStyle.THIN);
        sheetStyle.setBorderRight(BorderStyle.THIN);
        sheetStyle.setBorderTop(BorderStyle.THIN);
        sheetStyle.setBorderLeft(BorderStyle.THIN);
        HSSFCellStyle sheetStyle2 = wb.createCellStyle();
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        sheetStyle2.setFont(font);

        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        sheetStyle2.setBorderTop(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);

        try{
            //创建标题
            //因为这里是前端固定的表头,所以后台手动添加
            HSSFRow row0=sheet.createRow((short)0);
            cell=row0.createCell(0);
            cell.setCellValue("公司代码");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("报销凭证号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("财年");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("期间");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("记账日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("出差人工号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("出差人姓名");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("差旅申请号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("差旅费用号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("专项费用");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("差旅国内/外");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("出差动因");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("出差片区");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("出差事由及安排");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellValue("差旅开始日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(15);
            cell.setCellValue("差旅结束日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(16);
            cell.setCellValue("差旅天数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(17);
            cell.setCellValue("成本中心");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(18);
            cell.setCellValue("基金中心");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(19);
            cell.setCellValue("膳杂费");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(20);
            cell.setCellValue("住宿费");
            cell.setCellStyle(sheetStyle);


            cell=row0.createCell(21);
            cell.setCellValue("交通费");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(22);
            cell.setCellValue("机票费");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(23);
            cell.setCellValue("其他费用");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(24);
            cell.setCellValue("会计调整膳杂");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(25);
            cell.setCellValue("会计调整住宿");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(26);
            cell.setCellValue("会计调整交通");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(27);
            cell.setCellValue("会计调整其他");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(28);
            cell.setCellValue("超标金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(29);
            cell.setCellValue("超标说明");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(30);
            cell.setCellValue("总成本");
            cell.setCellStyle(sheetStyle);


            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");
            String kjjs = com.getformodeid("FNA_TravleSQ", "ROLE_ID_KJ");
            String canSeeAll = "";
            int countsl = 0;
            String sql = "select count(1) as count from hrmrolemembers where roleid="+kjjs+" and RESOURCEID="+userid;
            rs.execute(sql);
            if(rs.next()){
                countsl = rs.getInt("count");
            }
            if(countsl>0){
                canSeeAll ="1";
            }
            String ckbm = "";
            sql = "select BM from uf_fna_BtrRight where HANPER='"+userid+"'";
            rs.execute(sql);
            while(rs.next()){
                String bm = Util.null2String(rs.getString("BM"));
                if(!"".equals(bm)){
                    if(!"".equals(ckbm)){
                        ckbm = ckbm+","+bm;
                    }else{
                        ckbm = bm;
                    }
                }
            }
            sql = "select requestid,BtrBukrs,BELNR,GJAHR,qj,BLDAT,BTRWorkCode,(select lastname from hrmresource where id=a.BtrPER) as BtrPER,TRAAPPLI,bxdh,SPEPRO,BTRCon,BTRReasonsDet,BTRRegion,ASSIGNMENT,BTRBEGDA,BTRENDDA,BTRDAYS,COSTCENTER,FundCenterCODE,MEEXP,HoAmo,OthTraCost,AirAmo,OthCost,AccMEEXPB,AccHoAmoB,AccOthTraCostB,AccOthCostB,ExcAmo,ECEDES,TolCos,(select description from uf_wfstatus where code=a.WFStatus) as WFStatus";
            String sqlfrom = " from (select b.requestid,b.BtrBukrs,b.BELNR,b.GJAHR,to_number(substr(b.BLDAT,6,2)) as qj,b.BLDAT,BTRWorkCode,BtrPER,TRAAPPLI,b.rqid as bxdh,SPEPRO,case when BTRCon='0' then '国内' when BTRCon='1' then '国外' else '' end as BTRCon,(select TraMotivaDet from uf_fna_TraMotivaDet where id=b.BTRReasonsDet) as BTRReasonsDet,(select value from uf_Paper_REGION where code=b.BTRRegion) as BTRRegion,ASSIGNMENT,BTRBEGDA,BTRENDDA,BTRDAYS,COSTCENTER,FundCenterCODE" +
                    ",nvl(MEEXP,0)-nvl(AccMEEXPB,0) as MEEXP,nvl(HoAmo,0)+nvl(TolAmoOfCH,0)-nvl(AccHoAmoB,0) as HoAmo,nvl(OthTraCost,0)-nvl(AccOthTraCostB,0) as OthTraCost,nvl(AirAmo,0)+nvl(TolAmoOfCA,0) as AirAmo,nvl(OthCost,0)-nvl(AccOthCostB,0) as OthCost" +
                    ",AccMEEXPB,AccHoAmoB,AccOthTraCostB,AccOthCostB,nvl(ExcAmo,0) as ExcAmo,ECEDES,TolCos,b.WFStatus" +
                    " from WORKFLOW_REQUESTBASE a,"+tablebx+" b where a.REQUESTID=b.REQUESTID and a.CURRENTNODETYPE>0 ";
            String cxry =  Util.null2String(params.get("cxry"));
            String iscsh =  Util.null2String(params.get("iscsh"));
            if("1".equals(iscsh)){
                sqlfrom +=" and (b.BtrPER ='" + userid + "' or b.HanPER='" + userid + "') ";
            }else {
                if (!"1".equals(userid) && !"1".equals(canSeeAll)) {
                    sqlfrom += " and (b.BtrPER ='" + userid + "' or b.HanPER='" + userid + "' or b.BtrPER in(select id from table(getchilds(" + userid + "))) or b.HanPER in(select id from table(getchilds(" + userid + ")))";
                    if(!"".equals(ckbm)){
                        sqlfrom +=" or b.BtrDEP in("+ckbm+")";
                    }
                    sqlfrom +=")";
                }
            }

            if (StringUtils.isNotBlank(cxry)) {
                sqlfrom += " and b.BtrPER in("+cxry+") ";
            }
            String WFStatus =  Util.null2String(params.get("WFStatus"));
            if (StringUtils.isNotBlank(WFStatus)) {
                sqlfrom += " and b.WFStatus ='"+WFStatus+"'";
            }
            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate) ) {
                sqlfrom += " and b.BTRBEGDA >='"+fromdate+"'";
            }
            if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                sqlfrom += " and b.BTRBEGDA <='"+lenddate+"'";
            }

            String fromdate1 =  Util.null2String(params.get("fromdate1"));
            String lenddate1 =  Util.null2String(params.get("lenddate1"));
            if (StringUtils.isNotBlank(fromdate1) && !"null".equals(fromdate1) ) {
                sqlfrom += " and b.BTRENDDA >='"+fromdate1+"'";
            }
            if (StringUtils.isNotBlank(lenddate1) && !"null".equals(lenddate1)) {
                sqlfrom += " and b.BTRENDDA <='"+lenddate1+"'";
            }
            sqlfrom +=") a";

            sql = sql+sqlfrom+" where 1=1 ";
            sql =sql+" order by requestid desc";
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("BtrBukrs")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("BELNR")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("GJAHR")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("qj")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("BLDAT")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("BTRWorkCode")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("BtrPER")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("TRAAPPLI")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("bxdh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("SPEPRO")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("BTRCon")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("BTRReasonsDet")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("BTRRegion")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("ASSIGNMENT")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("BTRBEGDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(15);
                cell.setCellValue(Util.null2String(rs.getString("BTRENDDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(16);
                cell.setCellValue(Util.null2String(rs.getString("BTRDAYS")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(17);
                cell.setCellValue(Util.null2String(rs.getString("COSTCENTER")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(18);
                cell.setCellValue(Util.null2String(rs.getString("FundCenterCODE")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(19);
                cell.setCellValue(Util.null2String(rs.getString("MEEXP")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(20);
                cell.setCellValue(Util.null2String(rs.getString("HoAmo")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(21);
                cell.setCellValue(Util.null2String(rs.getString("OthTraCost")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(22);
                cell.setCellValue(Util.null2String(rs.getString("AirAmo")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(23);
                cell.setCellValue(Util.null2String(rs.getString("OthCost")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(24);
                cell.setCellValue(Util.null2String(rs.getString("AccMEEXPB")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(25);
                cell.setCellValue(Util.null2String(rs.getString("AccHoAmoB")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(26);
                cell.setCellValue(Util.null2String(rs.getString("AccOthTraCostB")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(27);
                cell.setCellValue(Util.null2String(rs.getString("AccOthCostB")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(28);
                cell.setCellValue(Util.null2String(rs.getString("ExcAmo")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(29);
                cell.setCellValue(Util.null2String(rs.getString("ECEDES")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(30);
                cell.setCellValue(Util.null2String(rs.getString("TolCos")));
                cell.setCellStyle(sheetStyle2);


                indexrow++;
            }



            //将workbook转换为流的形式
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;

        }catch (Exception e){
            new BaseBean().writeLog("导出excel报错,错误信息为:"+e.getMessage());
        }


        return null;
    }
}
