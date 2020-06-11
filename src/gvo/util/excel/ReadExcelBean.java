package gvo.util.excel;

import java.io.InputStream;

public class ReadExcelBean {
	/**Excel文件名*/
	private String excelname;
	/**输入流*/
	private InputStream in;
	
	public String getExcelname() {
		return excelname;
	}
	public void setExcelname(String excelname) {
		this.excelname = excelname;
	}
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	
}
