package gvo.util.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
 * @author dingji
 * @date 2019-07-18
 */
public class ReadExcelUtil {
    /**ReadExcelBean对象数据*/
	private ReadExcelBean reb;
    /**mapfield数据 excel标题与对应存储数据表字段对应关系*/
	private Map<String,String> mapfield;
	BaseBean log = new BaseBean();
	
	public ReadExcelUtil(String docid,Map<String,String> map) {
		this.reb = getExcel(docid);
		this.mapfield = map;
	}
	
	/**
	 * @return Map<String,String> map 数据  result：E/S； message：错误原因/JSONArray数据
	 * @throws IOException 
	 */
	public Map<String,String> readExcel() throws IOException {
        String excelname = reb.getExcelname();
        String[] split = excelname.split("\\."); 
        Map<String,String> map_fieldname = new HashMap<String,String>();
        Map<String,String> map_return = new HashMap<String,String>();
        if("xls".equals(split[1])) {
        	HSSFWorkbook hssfWorkbook  = new HSSFWorkbook(reb.getIn());
        	HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			if (hssfSheet == null) {
				map_return.put("result", "E");
				map_return.put("message", "excel的第一个sheet不存在");
	        	return map_return;
			}else {
				if(hssfSheet.getLastRowNum()==0) {
					map_return.put("result", "E");
					map_return.put("message", "excel的只有一行标题数据，请确认");
		        	return map_return;
				}
				JSONArray arr = new JSONArray();
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						if(rowNum==0) {
							for(int cellNum = 0 ; cellNum < hssfRow.getLastCellNum() ; cellNum ++) {
								if(!mapfield.containsKey(getValue(hssfRow.getCell((short) cellNum)))) {
									map_return.put("result", "E");
									map_return.put("message", "excel中的第"+(cellNum+1)+"列字段标题与模板不符，字段："+getValue(hssfRow.getCell((short) cellNum)));
						        	return map_return;
								}else{
									String value = mapfield.get(getValue(hssfRow.getCell((short) cellNum))).trim();
									map_fieldname.put(Integer.toString(cellNum), value);
								}
							}
						}else {
							JSONObject json = new JSONObject();
							for(int cellNum = 0 ; cellNum < hssfRow.getLastCellNum() ; cellNum ++) {
								String field_name = map_fieldname.get(Integer.toString(cellNum));
								String field_value = getValue(hssfRow.getCell((short) cellNum)).trim();
								try {
									json.put(field_name, field_value);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							arr.put(json);
						}
					}
				}
				map_return.put("result", "S");
				map_return.put("message", arr.toString());
			}     	
        }else{
        	map_return.put("result", "E");
        	map_return.put("message", "请上传xls格式的附件");
        	return map_return;
        }	
    	return map_return;
	}

	/**
	 * @param docid OA附件ID
	 * @return ReadExcelBean
	 */
	private ReadExcelBean getExcel(String docid) {
		ReadExcelBean reb1 = new ReadExcelBean();
		RecordSet rs = new RecordSet();
		String sql = " select * from docimagefile where docid= "+docid;
		String IMAGEFILEID = "";
		String IMAGEFILENAME = "";
		String FILEREALPATH = "";
		String ISZIP = "";
		rs.execute(sql);
		if(rs.next()) {
			IMAGEFILEID = Util.null2String(rs.getString("IMAGEFILEID"));
		}
		sql = " select * from imagefile where imagefileid="+IMAGEFILEID;
		rs.execute(sql);
		if(rs.next()) {
			IMAGEFILENAME = Util.null2String(rs.getString("IMAGEFILENAME"));
			FILEREALPATH = Util.null2String(rs.getString("FILEREALPATH"));
			ISZIP = Util.null2String(rs.getString("ISZIP"));
		}
		reb1.setExcelname(IMAGEFILENAME);
		InputStream in = null;
		try {
			in = getFile(FILEREALPATH,ISZIP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reb1.setIn(in);
		return reb1;
	}
	
	/**
	 * @param filerealpath OA附件地址
	 * @param iszip 是否解压缩
	 * @return InputStream 输入流
	 */
	private InputStream getFile(String filerealpath, String iszip) {
		ZipInputStream zin = null;
		InputStream in = null;
		File thefile = new File(filerealpath);
		try {
			if (iszip.equals("1")) {
				zin = new ZipInputStream(new FileInputStream(thefile));
				if (zin.getNextEntry() != null)
					in = new BufferedInputStream(zin);
			} else {
				in = new BufferedInputStream(new FileInputStream(thefile));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return in;
	}
	
	/**
	 * @param hssfCell excel单元格
	 * @return String 字符串
	 */
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == CellType.NUMERIC) {
			DecimalFormat df = new DecimalFormat("0");
			return String.valueOf(df.format(hssfCell.getNumericCellValue()));
		} else if(hssfCell.getCellType() == CellType.FORMULA){
			return String.valueOf(hssfCell.getCellFormula() + "");
		} else if(hssfCell.getCellType() == CellType.STRING){
			return String.valueOf(hssfCell.getStringCellValue());
		} else {
			return "";
		}
	}
}
