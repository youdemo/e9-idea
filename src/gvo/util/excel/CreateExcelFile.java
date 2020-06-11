package gvo.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import weaver.conn.RecordSet;
import weaver.general.Util;

public class CreateExcelFile {
    private static HSSFWorkbook workbook = null;  
    
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
     * 判断文件的sheet是否存在. 
     * @param fileDir   文件路径 
     * @param sheetName  表格索引名 
     * @return 
     */  
    public boolean sheetExist(String fileDir,String sheetName) throws Exception{  
         boolean flag = false;  
         File file = new File(fileDir);  
         if(file.exists()){    //文件存在  
            //创建workbook  
             try {  
                workbook = new HSSFWorkbook(new FileInputStream(file));  
                //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {  
                throw e;
            }   
              
         }else{    //文件不存在  
             flag = false;  
         }  
         return flag;  
    }  
    /** 
     * 创建新excel. 
     * @param fileDir  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     */  
    public void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
            //添加表头  
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                //row.getCell(i).setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(titleRow[i]);  
            }  
            out = new FileOutputStream(fileDir);  
            workbook.write(out);  
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {    
                out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
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
    /** 
     * 往excel中写入(已存在的数据无法写入). 
     * @param fileDir    文件路径 
     * @param sheetName  表格索引 
     * @param object 
     * @throws Exception 
     */  
    public void writeToExcel(String fileDir,String sheetName,List<Map> mapList) throws Exception{  
        //创建workbook  
        File file = new File(fileDir);  
        try {  
            workbook = new HSSFWorkbook(new FileInputStream(file));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //流  
        FileOutputStream out = null;  
        HSSFSheet sheet = workbook.getSheet(sheetName);  
        // 获取表格的总行数  
        //int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
        // 获取表头的列数  
        int columnCount = sheet.getRow(0).getLastCellNum(); 
        try {  
            // 获得表头行对象  
            HSSFRow titleRow = sheet.getRow(0);
            if(titleRow!=null){ 
                for(int rowId=0;rowId<mapList.size();rowId++){
                    Map map = mapList.get(rowId);
                    HSSFRow newRow=sheet.createRow(rowId+1);
                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
                        String mapKey = getStringCellValue(titleRow.getCell(columnIndex)).toString().trim(); 
                        HSSFCell cell = newRow.createCell(columnIndex);  
                        //newRow.getCell(columnIndex).setEncoding(HSSFCell.ENCODING_UTF_16);
                        cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());
                    } 
                }
            }  
  
            out = new FileOutputStream(fileDir);  
            workbook.write(out); 
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {    
                out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
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
        case STRING:
            strCell = cell.getStringCellValue();
            break;
        case NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case BLANK:
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
     * 获取sql的columnName
     * 
     * @param sql sql字符串
     * @return List<String> 
     */
	public List<String> getColumnName(String sql){
		ArrayList<String> list = new ArrayList<String>();
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		for(int i = 1;i<=rs.getColCounts();i++) {
			list.add(rs.getColumnName(i));
		}
		return list;		
	}
    /**
     * 获取sql的List<Map>数据
     * 
     * @param sql sql字符串
     * @param list columnName
     * @return List<Map> 
     */
	public List<Map> getExcelList(String sql,List<String> list){
		List<Map> listmap=new ArrayList<Map>();
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		while(rs.next()) {
			Map<String,String> map=new HashMap<String,String>();
			for(int i=0;i<list.size();i++) {
				map.put(list.get(i), Util.null2String(rs.getString(list.get(i))));
			}
			listmap.add(map);
		}
		return listmap;		
	}
    /**
     * 获取excel的名称，默认时间戳
     * 
     *
     * @return String 
     */
	public String setExcelName() {
		String excelname = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		excelname = sdf.format(date);	
		return excelname;
	}
      
    /*public static void main(String[] args) {  
        //判断文件是否存在  
    	CreateExcelFile cef = new CreateExcelFile();
        //创建文件  
        String title[] = {"客户","姓名","密码"};  
        try {
			cef.createExcel("E:/test.xls","sheet1",title);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
        List<Map> list=new ArrayList<Map>();
        Map<String,String> map=new HashMap<String,String>();
        map.put("客户", "111");
        map.put("姓名", "张三");
        map.put("密码", "111！@#");
        
        Map<String,String> map2=new HashMap<String,String>();
        map2.put("客户", "222");
        map2.put("姓名", "李四");
        map2.put("密码", "222！@#");
        list.add(map);
        list.add(map2);
        //System.out.println(list.toString());
        try {
			cef.writeToExcel("E:/test.xls","sheet1",list);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
    }  */
}