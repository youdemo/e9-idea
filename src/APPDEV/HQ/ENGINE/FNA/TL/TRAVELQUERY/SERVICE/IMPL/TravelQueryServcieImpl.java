package APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.SERVICE.IMPL;

import APPDEV.HQ.Contract.ConCommonClass;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.TRAVELQUERY.SERVICE.TravelQueryDataServcie;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class TravelQueryServcieImpl extends Service implements TravelQueryDataServcie {

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
            cell.setCellValue("申请单");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("员工编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("姓名");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("部门");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("开始日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("结束日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("第一目的地");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("申请审批状态");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("报销审批状态");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("会计过账状态");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("费用报销单号");
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
            sql = "select requestid,sqdh,BTRWorkCode,(select lastname from hrmresource where id=a.BtrPER) as BtrPER,(select DEPARTMENTNAME from HRMDEPARTMENT where id=a.BtrDEP) as BtrDEP,BTRBEGDA,BTRENDDA,TavelDestCityALL,sqspzt,bxspzt,poststatus,bxdh,(select description from uf_wfstatus where code=a.WFStatus) as WFStatus ";
            String sqlfrom = " from (select b.requestid,b.rqid as sqdh,b.BTRWorkCode,b.BtrPER,b.BtrDEP,b.BTRBEGDA,b.BTRENDDA,b.TavelDestCityALL,case when a.CURRENTNODETYPE=3 then '已审核' else '已提交' end sqspzt," +
                    " case (select CURRENTNODETYPE from WORKFLOW_REQUESTBASE where REQUESTID=c.rqid) when '0' then '未提交' when '1' then '已提交' when '2' then '已提交' when '3' then '已审核' else '' end as bxspzt,c.poststatus,c.rqid as bxdh,b.WFStatus" +
                    " from WORKFLOW_REQUESTBASE a join "+tablesq+" b on a.REQUESTID=b.REQUESTID and a.CURRENTNODETYPE>0 left join "+tablebx+" c on b.REQUESTID=c.TRAAPPLI where 1=1 ";

            String workcode =  Util.null2String(params.get("workcode"));
            String cxry =  Util.null2String(params.get("cxry"));
            if("".equals(workcode)&&"".equals(cxry)){
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

            if (StringUtils.isNotBlank(workcode)) {
                sqlfrom += " and b.BTRWorkCode = '"+workcode+"'";
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
                sqlfrom += " and b.BTRENDDA <='"+lenddate+"'";
            }
            sqlfrom +=") a";
            sql = sql+sqlfrom+" where 1=1 ";
            sql =sql+" order by requestid desc";
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("sqdh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("BTRWorkCode")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("BtrPER")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("BtrDEP")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("BTRBEGDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("BTRENDDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("TavelDestCityALL")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("sqspzt")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("bxspzt")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("poststatus")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("bxdh")));
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
