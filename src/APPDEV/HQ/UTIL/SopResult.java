package APPDEV.HQ.UTIL;

import java.util.List;

public class SopResult {

	private String id;
	private String code;
	private String name;
	private int tlevel;
	private String relation;

	public SopResult(){
		
	}
	
	public SopResult(String id,String code,String name,int tlevel,String relation){
		this.id = id;
		this.code = code;
		this.name = name;
		this.relation = relation;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTlevel() {
		return tlevel;
	}
	public void setTlevel(int tlevel) {
		this.tlevel = tlevel;
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public boolean isSame(SopResult sr){
		String tmp_id = sr.getId();
		if(tmp_id == null) return false;
		if(this.id == null) return false;
		return tmp_id.equals(this.id);
	}
	public SopResult getSopResult(List<SopResult> list, String id){
		if(id == null || "".equals(id)) return null;
		for(SopResult sr : list){
			if(id.equals(sr.getId())){
				return sr;
			}
		}
		return null;
	}

}
