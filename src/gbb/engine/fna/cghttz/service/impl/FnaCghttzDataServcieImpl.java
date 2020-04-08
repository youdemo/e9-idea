package gbb.engine.fna.cghttz.service.impl;

import com.engine.common.util.ParamUtil;
import com.engine.core.impl.Service;
import gbb.engine.fna.cghttz.cmd.WeaTableCmd;
import gbb.engine.fna.cghttz.cmd.WeatableConditonCmd;
import gbb.engine.fna.cghttz.service.FnaCghttzDataServcie;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class FnaCghttzDataServcieImpl extends Service implements FnaCghttzDataServcie {

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
        Map<String, Object> params=ParamUtil.request2Map(request);
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
            cell.setCellValue("合同名称");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("合同编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("采购申请");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("合同相对方");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("合同金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("审批中金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("已付款金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("未付款金额");
            cell.setCellStyle(sheetStyle);
            String sql = "select id,htmc,htbh,cgsqname,cgsq,htxdf,htje,spzje,yfkje,wfkje from (select id,htmc,htbh,(select requestname from workflow_requestbase where requestid=a.cgsq ) as cgsqname,cgsq,htxdf,cast(htje as numeric(10,2)) as htje,cast((select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(1,2)and t.htmc=a.cghtrqid ) as numeric(10,2)) as spzje,cast((select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(3)and t.htmc=a.cghtrqid ) as numeric(10,2)) as yfkje,cast((htje-(select isnull(sum(isnull(t.bcfkje,0)),0) from formtable_main_52 t,workflow_requestbase t1 where t.requestid=t1.requestid and t1.currentnodetype in(1,2,3)and t.htmc=a.cghtrqid )) as numeric(10,2)) as wfkje from uf_cghttz a) a where 1=1 ";
            String htmc =  Util.null2String(params.get("htmc"));
            if (StringUtils.isNotBlank(htmc)) {
                sql += " and htmc like '%" + htmc + "%' ";
            }

            //合同编号
            String htbh =  Util.null2String(params.get("htbh"));
            if (StringUtils.isNotBlank(htbh)) {
                sql += " and htbh like '%" + htbh + "%' ";
            }

            //合同相对方
            String htxdf =  Util.null2String(params.get("htxdf"));
            if (StringUtils.isNotBlank(htxdf)) {
                sql += " and htxdf like '%" + htxdf + "%' ";
            }
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("htmc")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("htbh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("cgsqname")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("htxdf")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("htje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("spzje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("yfkje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("wfkje")));
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
