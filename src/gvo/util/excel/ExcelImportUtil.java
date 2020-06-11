package gvo.util.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import km.org.apache.poi.ss.usermodel.IndexedColors;


public class ExcelImportUtil {
	/**
     * 
     * @Title: exportManySheetExcel 
     * @Description: 可生成单个、多个sheet 
     * @param @param file 导出文件路径
     * @param @param mysheets
     * @return void
     * @throws
     */
    public void exportManySheetExcel(String file, List<ExcelBean> mysheets){
        
        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        List<ExcelBean> sheets = mysheets;
        
        //表头样式
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式   5C93B0
        //style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //style.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
        //style.setVerticalAlignment(VerticalAlignment.CENTER);
        //字体样式
        HSSFFont fontStyle = wb.createFont();    
        fontStyle.setFontName("微软雅黑");   
        fontStyle.setBold(true);
        style.setFont(fontStyle);
        //数据样式
        HSSFCellStyle style1 = wb.createCellStyle(); 
        HSSFFont fontStyle1 = wb.createFont();
        fontStyle1.setFontName("微软雅黑"); 
        style1.setFont(fontStyle1);
        int sheetIndex=0;
        for(ExcelBean excel: sheets){
            //新建一个sheet
        	HSSFSheet sheet = wb.createSheet();//获取该sheet名称
            //wb.createSheet(excel.getFileName());//获取该sheet名称
        	wb.setSheetName(sheetIndex, excel.getFileName());
            String[] handers = excel.getHanders();//获取sheet的标题名
            HSSFRow rowFirst = sheet.createRow(0);//第一个sheet的第一行为标题
            //写标题
            for(short i=0;i<handers.length;i++){
                //获取第一行的每个单元格
                HSSFCell cell = rowFirst.createCell(i);
                //往单元格里写数据
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(handers[i]);
                cell.setCellStyle(style); //加样式
                sheet.setColumnWidth(i, (short) 5000); //设置每列的列宽
            }
            
            //写数据集
            List<Map<String,String>> dataset = excel.getDataset();
            int columnCount = sheet.getRow(0).getLastCellNum(); 
            HSSFRow titleRow = sheet.getRow(0);
            for(int i=0;i<dataset.size();i++){
            	Map map = dataset.get(i);
                HSSFRow newRow=sheet.createRow(i+1);
                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
                    String mapKey = getStringCellValue(titleRow.getCell(columnIndex)).toString().trim(); 
                    HSSFCell cell = newRow.createCell(columnIndex);  
                    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());
                    cell.setCellStyle(style1);
                } 
            }
            sheetIndex++;
        }
        
        // 写文件
        try {
            FileOutputStream out = new FileOutputStream(new File(file));
            out.flush();
            wb.write(out);
            out.close(); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: exportExcelToStream 
     * @Description: 可返回ByteArrayOutputStream类型
     * @return ByteArrayOutputStream
     * @throws
     */
    public ByteArrayOutputStream exportExcelToStream(List<ExcelBean> mysheets){
        
        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        List<ExcelBean> sheets = mysheets;
        
        //表头样式
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式   5C93B0
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.index);
        //字体样式
        HSSFFont fontStyle = wb.createFont();    
        fontStyle.setFontName("微软雅黑");   
        fontStyle.setBold(true);
        style.setFont(fontStyle);
        //数据样式
        HSSFCellStyle style1 = wb.createCellStyle(); 
        HSSFFont fontStyle1 = wb.createFont();    
        fontStyle1.setFontName("微软雅黑"); 
        style1.setFont(fontStyle1);
        int sheetIndex=0;
        for(ExcelBean excel: sheets){
            //新建一个sheet
        	HSSFSheet sheet = wb.createSheet();//获取该sheet名称
            //wb.createSheet(excel.getFileName());//获取该sheet名称
        	wb.setSheetName(sheetIndex, excel.getFileName());
            String[] handers = excel.getHanders();//获取sheet的标题名
            HSSFRow rowFirst = sheet.createRow(0);//第一个sheet的第一行为标题
            //写标题
            for(short i=0;i<handers.length;i++){
                //获取第一行的每个单元格
                HSSFCell cell = rowFirst.createCell(i);
                //往单元格里写数据
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(handers[i]);
                cell.setCellStyle(style); //加样式
                sheet.setColumnWidth(i, (short) 5000); //设置每列的列宽
            }
            
            //写数据集
            List<Map<String,String>> dataset = excel.getDataset();
            int columnCount = sheet.getRow(0).getLastCellNum(); 
            HSSFRow titleRow = sheet.getRow(0);
            for(int i=0;i<dataset.size();i++){
            	Map map = dataset.get(i);
                HSSFRow newRow=sheet.createRow(i+1);
                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
                    String mapKey = getStringCellValue(titleRow.getCell(columnIndex)).toString().trim(); 
                    HSSFCell cell = newRow.createCell(columnIndex); 
                    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());
                    cell.setCellStyle(style1);
                } 
            }
            sheetIndex++;
        }
        
        // 写成文件流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            wb.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
    
    /**
     * 
     * @Title: exportExcelToStream 
     * @Description: 可返回HSSFWorkbook类型
     * @return HSSFWorkbook
     * @throws
     */
    public HSSFWorkbook exportExcelToWorkbook(List<ExcelBean> mysheets){
        
        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        List<ExcelBean> sheets = mysheets;
        
        //表头样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式   5C93B0
        style.setFillBackgroundColor(IndexedColors.BLUE_GREY.index);
        //字体样式
        HSSFFont fontStyle = wb.createFont();    
        fontStyle.setFontName("微软雅黑");  
        fontStyle.setBold(true);
        style.setFont(fontStyle);
        //数据样式
        HSSFCellStyle style1 = wb.createCellStyle(); 
        HSSFFont fontStyle1 = wb.createFont();    
        fontStyle1.setFontName("微软雅黑"); 
        style1.setFont(fontStyle1);
        int sheetIndex=0;
        for(ExcelBean excel: sheets){
            //新建一个sheet
        	HSSFSheet sheet = wb.createSheet();//获取该sheet名称
            //wb.createSheet(excel.getFileName());//获取该sheet名称
        	wb.setSheetName(sheetIndex, excel.getFileName());
            String[] handers = excel.getHanders();//获取sheet的标题名
            HSSFRow rowFirst = sheet.createRow(0);//第一个sheet的第一行为标题
            //写标题
            for(short i=0;i<handers.length;i++){
                //获取第一行的每个单元格
                HSSFCell cell = rowFirst.createCell(i);
                //往单元格里写数据
                //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(handers[i]);
                cell.setCellStyle(style); //加样式
                sheet.setColumnWidth(i, (short) 5000); //设置每列的列宽
            }
            
            //写数据集
            List<Map<String,String>> dataset = excel.getDataset();
            int columnCount = sheet.getRow(0).getLastCellNum(); 
            HSSFRow titleRow = sheet.getRow(0);
            for(int i=0;i<dataset.size();i++){
            	Map map = dataset.get(i);
                HSSFRow newRow=sheet.createRow(i+1);
                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
                    String mapKey = getStringCellValue(titleRow.getCell(columnIndex)).toString().trim(); 
                    HSSFCell cell = newRow.createCell(columnIndex);  
                    //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());
                    cell.setCellStyle(style1);
                } 
            }
            sheetIndex++;
        }
        return wb;
    }
    
    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case NUMERIC:
        	DecimalFormat df = new DecimalFormat("0");  
            strCell = String.valueOf(df.format(cell.getNumericCellValue()));
            break;
        case STRING:
            strCell = cell.getStringCellValue();
            break;
        case BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue()+"");
            break;
        case FORMULA:
        	strCell = String.valueOf(cell.getCellFormula() + "");
			break;
        case BLANK:
            strCell = "";
            break;
        case ERROR:
        	strCell = "";
			break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }
    
    /**
     * 获取excel的名称，默认时间戳
     * @return String 
     */
	public String getExcelName() {
		String excelname = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		excelname = sdf.format(date);	
		return excelname;
	}
	
    /** 
     * 判断文件是否存在. 
     * @param fileDir  文件路径 
     * @return 
     */  
    public boolean fileExist(String fileDir){  
         boolean flag = false;  
         File file = new File(fileDir);  
         flag = file.exists();  
         return flag;  
    }
    
    /** 
     * 删除文件. 
     * @param fileDir  文件路径 
     */  
    public boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);  
        // 判断目录或文件是否存在    
        if (!file.exists()) {  // 不存在返回 false    
            return flag;    
        } else {    
            // 判断是否为文件    
            if (file.isFile()) {  // 为文件时调用删除文件方法    
                file.delete();  
                flag = true;  
            }   
        }  
        return flag;  
    }
    
    public static void main(String[] args) {
    	DecimalFormat df = new DecimalFormat("0");  
        String strCell = String.valueOf(df.format(112211220.0));
        System.out.println(strCell);
	}
}
