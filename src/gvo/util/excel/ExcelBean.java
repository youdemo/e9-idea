package gvo.util.excel;

import java.util.List;
import java.util.Map;

public class ExcelBean {
	private String fileName;// sheet的名称
    private String[] handers;// sheet里的标题
    private List<Map<String,String>> dataset;// sheet里的数据集

    public ExcelBean(String fileName, String[] handers, List<Map<String,String>> dataset) {
        this.fileName = fileName;
        this.handers = handers;
        this.dataset = dataset;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getHanders() {
		return handers;
	}

	public void setHanders(String[] handers) {
		this.handers = handers;
	}

	public List<Map<String,String>> getDataset() {
		return dataset;
	}

	public void setDataset(List<Map<String,String>> dataset) {
		this.dataset = dataset;
	}
	
	
}
