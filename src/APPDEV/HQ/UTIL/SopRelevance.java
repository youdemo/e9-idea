package APPDEV.HQ.UTIL;

import weaver.conn.RecordSet;
import weaver.general.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SopRelevance {

	private SopResult from;
	private SopResult to;
	public SopResult getFrom() {
		return from;
	}
	public void setFrom(SopResult from) {
		this.from = from;
	}
	public SopResult getTo() {
		return to;
	}
	public void setTo(SopResult to) {
		this.to = to;
	}
	public boolean isPeer(){
		if(from == null ) return false;
		if(from == to ) return false;
		return from.getTlevel() == to.getTlevel();
	}

	public static Map<String,String> getAllMap() {
		
		// ��ȡ���нڵ�   �ֱ��ŵ�һ�������ļ�
		List<SopResult> list_0 = new ArrayList<SopResult>();
		List<SopResult> list_1 = new ArrayList<SopResult>();
		List<SopResult> list_2 = new ArrayList<SopResult>();
		List<SopResult> list_3 = new ArrayList<SopResult>();
		
		RecordSet rs = new RecordSet();
		String sql = "select id,code,name,tlevel,relation from uf_sop_relation ";
		rs.executeSql(sql);
		
		while(rs.next()){
			int tlevel = rs.getInt("tlevel");
			SopResult sr  = new SopResult();
			sr.setId(Util.null2String(rs.getString("id")));
			sr.setName(Util.null2String(rs.getString("name")));
			sr.setCode(Util.null2String(rs.getString("code")));
			sr.setRelation(Util.null2String(rs.getString("relation")));
			sr.setTlevel(tlevel);
			if(tlevel == 0){
				list_0.add(sr);
			}else if(tlevel == 1){
				list_1.add(sr);
			}else if(tlevel == 2){
				list_2.add(sr);
			}else if(tlevel == 3){
				list_3.add(sr);
			}

		}

		// ��װ��һ��͵ڶ����ϵ
		List<SopRelevance> sop_0_1 = new ArrayList<SopRelevance>();
		for(int index=0;index<list_1.size();index++){
			SopResult sr = list_1.get(index);
			String[] ids = sr.getRelation().split(",");
			for(int jj=0;jj<ids.length;jj++){
				SopResult sr_x = sr.getSopResult(list_0, ids[jj]);
				if(sr_x != null){
					SopRelevance ssr = new SopRelevance();
					ssr.setFrom(sr);
					ssr.setTo(sr_x);
					sop_0_1.add(ssr);
				}
			}	
		}
		List<SopRelevance> sop_1_2 = new ArrayList<SopRelevance>();
		// ��װ�ڶ���͵������ϵ
		for(int index=0;index<list_2.size();index++){
			SopResult sr = list_2.get(index);
			String[] ids = sr.getRelation().split(",");
			for(int jj=0;jj<ids.length;jj++){
				SopResult sr_x = sr.getSopResult(list_1, ids[jj]);
				if(sr_x != null){
					SopRelevance ssr = new SopRelevance();
					ssr.setFrom(sr);
					ssr.setTo(sr_x);
					
					sop_1_2.add(ssr);
				}
			}	
		}
		List<SopRelevance> sop_2_3 = new ArrayList<SopRelevance>();
		// ��װ������͵��Ĳ��ϵ
		for(int index=0;index<list_3.size();index++){
			SopResult sr = list_3.get(index);
			String[] ids = sr.getRelation().split(",");
			for(int jj=0;jj<ids.length;jj++){
				SopResult sr_x = sr.getSopResult(list_2, ids[jj]);
				if(sr_x != null){
					SopRelevance ssr = new SopRelevance();
					ssr.setFrom(sr);
					ssr.setTo(sr_x);
					
					sop_2_3.add(ssr);
				}
			}
		}
		
		
		Map<String,String> mapStr = new HashMap<String,String>();
		String index_no_0 = "0";
		String str_0 = "";
		String flag_0 = "";
		// ��װ��һ��
		for(int index=0;index<list_0.size();index++){
			
			SopResult sr = list_0.get(index);
			String id_0 = sr.getId();
			str_0 = str_0 + flag_0 + sr.getName();
			flag_0 = ",";
			// �ڵ�һ�㵽�ڶ���Ĺ�ϵ�в�ѯ
			String index_no_1 = index_no_0 + "_" + index;
			String str_1 = "";
			String flag_1 = "";
			int index_x_1 = 0;
			for(SopRelevance ssr_0_1 : sop_0_1){
				// �¼���ϵ
				if(ssr_0_1.getTo().getId().equals(id_0)){
					SopResult sr_1 = ssr_0_1.getFrom();
					str_1 = str_1 + flag_1 + sr_1.getName();
					flag_1 = ",";
					String id_1 = sr_1.getId();
					// �ڵڶ��㵽������Ĺ�ϵ�в�ѯ
					String index_no_2 = index_no_1 + "_" + index_x_1;
					index_x_1++;
					String str_2 = "";
					String flag_2 = "";
					int index_x_2 = 0;
					for(SopRelevance ssr_1_2 : sop_1_2){
						// �¼���ϵ
						if(ssr_1_2.getTo().getId().equals(id_1)){
							SopResult sr_2 = ssr_1_2.getFrom();
							str_2 = str_2 + flag_2 + sr_2.getName();
							flag_2 = ",";
							
							String id_2 = sr_2.getId();
							// �ڵ����㵽���Ĳ�Ĺ�ϵ�в�ѯ
							String index_no_3 = index_no_2 + "_" + index_x_2;
							index_x_2++;
							String str_3 = "";
							String flag_3 = "";
							for(SopRelevance ssr_2_3 : sop_2_3){
								// �¼���ϵ
								if(ssr_2_3.getTo().getId().equals(id_2)){
									SopResult sr_3 = ssr_2_3.getFrom();
									str_3 = str_3 + flag_3 + sr_3.getName();
									flag_3 = ",";
									
									
								}
							}
							if(str_3.length() > 0)
								mapStr.put(index_no_3, str_3);
						}
					}
					if(str_2.length() > 0)
						mapStr.put(index_no_2, str_2);
				}
			}
			if(str_1.length() > 0 )
				mapStr.put(index_no_1, str_1);
		}
		mapStr.put(index_no_0, str_0);
		
		return mapStr;
		
	}
	
}
