package gvo.hr.portal.engine.glz;

import gvo.util.excel.ExcelBean;
import gvo.util.excel.ExcelImportUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.SendMail;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.system.SystemComInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class EmpOverTimeNotice extends BaseCronJob{
	/**
	 * 人员加班信息推送
	 * */
	String[] detailTitle = {"分部","中心","部门","组别","工号","姓名","员工类型","平时加班","周末加班","国假日加班","加班合计","标准加班受限总时数","调整后加班受限总时数","标准百分比","调整后百分比"};
	String[] colsName = {"company_name","center_name","dept_name","team_name","workcode","lastname","hum_type","PS_HOURS","ZM_HOURS","FD_HOURS","total_hours","st_tops","up_tops","rate","rate1"};
	String[] titles = {"月份","公司","中心","部门","身份类型","标准工时人数","标准加班受限总时数","调整后加班受限总时数","当月申请加班总时数","已加班总时数占比","人均时数","0-30","31-50","51-60","61-80","81-110","110+"};
	String absPath = getCurrentPath();
	BaseBean log = new BaseBean();
	String ifContainNoSubmit;
	String ifTestMode = "";
	String testMail = "";
	public void execute() {
		log.writeLog("EmpOverTimeNotice start....");
		boolean sendFlag = false;
		String mailSubject = "";
		String mailContent = "";
		String now_month = "";
		String now_hour = "";
		String now_day = "";
		String first_day = "";
		RecordSet rs  = new RecordSet();
		RecordSet rs1  = new RecordSet();
		String sql =" select to_char(sysdate,'yyyy\"年\"fmmm\"月\"') as now_month, "
				+ " to_char(sysdate,'yyyy\"年\"fmmm\"月\"fmdd\"日\" fmhh24\"点\"') as now_hour, "
				+ " to_char(sysdate,'yyyy-mm-dd') as now_day, "
				+ " to_char(sysdate,'yyyy-mm')||'-01' as first_day from dual ";
		rs.execute(sql);
		if(rs.next()) {
			now_month =  Util.null2String(rs.getString("now_month"));
			now_hour =  Util.null2String(rs.getString("now_hour"));
			now_day =  Util.null2String(rs.getString("now_day"));
			first_day =  Util.null2String(rs.getString("first_day"));
		}
		sql = " select content from uf_mail_content_mt where typeid='M004' ";
		rs.execute(sql);
		if(rs.next()) {
			mailContent = Util.null2String(rs.getString("content"));
		}
		mailContent = mailContent.replace("time", now_hour);
		mailContent = mailContent.replace("month", now_month);
		mailContent = "<font face=\"微软雅黑\">"+mailContent+"</font>";
		log.writeLog("EmpOverTimeNotice mail content:"+mailContent);
		mailSubject = "【烦请查收】"+now_month+"累计加班时数反馈";
		String bmjl = "";
		String zxzjl = "";
		String hrbp = "";
		String bm = "";
		String bm_name = "";
		String emails = "";
		String hum_sql="";//按照部门/中心获取人员加班信息
		String bm_sql = "";
		bm_sql = getSql("20");
		rs.execute(bm_sql);
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		List<ExcelBean> ebs = new ArrayList<ExcelBean>();
		ExcelImportUtil eiu = new ExcelImportUtil();
		while(rs.next()) {
			String temp_mailContent = mailContent;
			zxzjl = Util.null2String(rs.getString("zxzjl"));
			hrbp = Util.null2String(rs.getString("hrbp"));
			bm = Util.null2String(rs.getString("bm"));
			bm_name = getDeptName(rs1, bm);
			temp_mailContent = temp_mailContent.replace("department", bm_name);
			if("".equals(zxzjl)) {
				continue;
			}else if(!"".equals(hrbp)){
				emails = getMail(rs1, zxzjl+","+hrbp);
			}else {
				emails = getMail(rs1, zxzjl);
			}
			String fileName = bm+"_"+eiu.getExcelName()+".xls";
			String filepath = absPath +"/filepath/"+ fileName;
			try {
				hum_sql = getBmSql(bm, first_day, now_day,"center");//创建查询人员加班信息sql
				dataList = getExcelList(rs1,hum_sql);//通过中心id获取人员加班信息
				if(dataList.size()>0) {
					if(eiu.fileExist(filepath)) {
						eiu.deleteExcel(filepath);
					}
					ExcelBean eb1 = new ExcelBean("detail",detailTitle,dataList);
					dataList = getSummaryList(dataList,now_month,"center");
					ExcelBean eb2 = new ExcelBean("summary",titles,dataList);
					ebs.add(eb2);
					ebs.add(eb1);
					eiu.exportManySheetExcel(filepath, ebs);
					if("Y".equals(ifTestMode)) {
						sendFlag = sendemail(testMail, mailSubject+emails,temp_mailContent,fileName,filepath);
						if(sendFlag) {
							break;
						}
					}else {
						sendFlag = sendemail(emails, mailSubject,temp_mailContent,fileName,filepath);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(eiu.fileExist(filepath)) {
					eiu.deleteExcel(filepath);
				}
				ebs.clear();
			}
		}
		bm_sql = getSql("30");
		rs.execute(bm_sql);
		while(rs.next()) {
			String temp_mailContent = mailContent;
			zxzjl = Util.null2String(rs.getString("zxzjl"));
			hrbp = Util.null2String(rs.getString("hrbp"));
			bmjl = Util.null2String(rs.getString("bmjl"));
			bm = Util.null2String(rs.getString("bm"));
			bm_name = getDeptName(rs1, bm);
			temp_mailContent = temp_mailContent.replace("department", bm_name);
			if("".equals(bmjl)) {
				continue;
			}else if(!"".equals(hrbp)){
				emails = getMail(rs1, bmjl+","+hrbp);
			}else {
				emails = getMail(rs1, bmjl);
			}
			String fileName = bm+"_"+eiu.getExcelName()+".xls";
			String filepath = absPath +"/filepath/"+ fileName;
			try {
				hum_sql = getBmSql(bm, first_day, now_day,"dept");
				dataList = getExcelList(rs1,hum_sql);
				if(dataList.size()>0) {
					if(eiu.fileExist(filepath)) {
						eiu.deleteExcel(filepath);
					}
					ExcelBean eb1 = new ExcelBean("detail",detailTitle,dataList);
					dataList = getSummaryList(dataList,now_month,"dept");
					ExcelBean eb2 = new ExcelBean("summary",titles,dataList);
					ebs.add(eb2);
					ebs.add(eb1);
					eiu.exportManySheetExcel(filepath, ebs);
					if("Y".equals(ifTestMode)) {
						sendFlag = sendemail(testMail, mailSubject+emails,temp_mailContent,fileName,filepath);
						if(sendFlag) {
							break;
						}
					}else {
						sendFlag = sendemail(emails, mailSubject,temp_mailContent,fileName,filepath);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(eiu.fileExist(filepath)) {
					eiu.deleteExcel(filepath);
				}
				ebs.clear();
			}
		}
	}
	
	public String getSql(String type) {
		StringBuffer bm_sql = new StringBuffer();
		bm_sql.append(" select a.*,b.zzlx from ( ");
		bm_sql.append(" select bm,bmjl,zxzjl,hrbp from matrixtable_3 ");
		bm_sql.append(" union ");
		bm_sql.append(" select bm,bmjl,zxzjl,hrbp from matrixtable_4 ");
		bm_sql.append(" union ");
		bm_sql.append(" select bm,bmjl,zxzjl,hrbp from matrixtable_6 ");
		bm_sql.append(" union ");
		bm_sql.append(" select bm,bmjl,zxzjl,hrbp from matrixtable_14 ");
		bm_sql.append(" union ");
		bm_sql.append(" select bm,bmjl,zxzjl,hrbp from matrixtable_15) a  ");
		bm_sql.append(" join hrmdepartmentdefined b on a.bm=b.deptid where b.zzlx='");
		bm_sql.append(type);
		bm_sql.append("' ");
		return bm_sql.toString();
	}
	//获取按部门/中心获取人员加班信息的sql
	public String getBmSql(String bmid,String begDate,String endDate,String type) {
		StringBuffer bm_sql = new StringBuffer();
		bm_sql.append(" select d.*,d.PS_HOURS+d.ZM_HOURS+d.FD_HOURS as total_hours , ");
		bm_sql.append(" round((d.PS_HOURS+d.ZM_HOURS+d.FD_HOURS)/d.st_tops,4)*100||'%' as rate,  ");
		bm_sql.append(" round((d.PS_HOURS+d.ZM_HOURS+d.FD_HOURS)/d.up_tops,4)*100||'%' as rate1  ");
		
		bm_sql.append(" from  ");
		bm_sql.append(" (select a.company_name,a.dept_name,a.team_name,a.center_name,a.lastname,a.workcode, ");
		bm_sql.append(" decode(field25,'0','直接人员','1','间接人员') as hum_type, ");
		bm_sql.append(" gvogroup.F_getovertime('");
		bm_sql.append(endDate);
		bm_sql.append("', a.workcode, 'G') AS up_tops, ");
		if("Y".equals(ifContainNoSubmit)) {
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa  ");
			bm_sql.append(" where   cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and  overtime_type like '%平时%') AS PS_HOURS, ");
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa ");
			bm_sql.append(" where   cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and  overtime_type like '%周末%') AS ZM_HOURS, ");
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa ");
			bm_sql.append(" where   cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and overtime_type like '%法定%') AS FD_HOURS, ");
		}else {
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa  ");
			bm_sql.append(" join formtable_main_744 bb on aa.mainid=bb.id  ");
			bm_sql.append(" join workflow_requestbase cc on bb.requestid=cc.requestid  ");
			bm_sql.append(" where cc.currentnodetype>0 and cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and  overtime_type like '%平时%') AS PS_HOURS, ");
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa ");
			bm_sql.append(" join formtable_main_744 bb on aa.mainid=bb.id  ");
			bm_sql.append(" join workflow_requestbase cc on bb.requestid=cc.requestid  ");
			bm_sql.append(" where cc.currentnodetype>0 and cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and  overtime_type like '%周末%') AS ZM_HOURS, ");
			bm_sql.append(" (SELECT nvl(SUM(nvl(a_hours,0)),0) ");
			bm_sql.append(" from gvogroup.formtable_main_744_dt1 aa ");
			bm_sql.append(" join formtable_main_744 bb on aa.mainid=bb.id  ");
			bm_sql.append(" join workflow_requestbase cc on bb.requestid=cc.requestid  ");
			bm_sql.append(" where cc.currentnodetype>0 and cday  >='");
			bm_sql.append(begDate);
			bm_sql.append("' and   cday <='");
			bm_sql.append(endDate);
			bm_sql.append("'  and aa.work_code=A.WORKCODE ");
			bm_sql.append(" and overtime_type like '%法定%') AS FD_HOURS, ");
		}
		bm_sql.append(" (select nvl(TOP_HOURS,0) from uf_companytop_hours ch where ch.company=a.SUBCOMPANYID1 and PEOPLE_TYPE=a.field25 and ch.BEGINDATE<='");
		bm_sql.append(endDate);
		bm_sql.append("' and ch.ENDDATE>='");
		bm_sql.append(endDate);
		bm_sql.append("' and rownum<=1) as st_tops ");
		bm_sql.append(" FROM ");
		bm_sql.append(" (SELECT b.*,c.subcompanyid1,e.field25 FROM v_ps_dept B join hrmresource c on b.id=c.id  join cus_fielddata e on b.id=e.id ");
		bm_sql.append("where  c.residentplace='A' AND C.STATUS<4 and c.belongto='-1' and e.scope='HrmCustomFieldByInfoType' and e.scopeid='-1' AND e.FIELD42 in('30','10')) A ");
		if("center".equals(type)) {
			bm_sql.append(" WHERE A.CENTER_ID=");
		}else {
			bm_sql.append(" WHERE A.DEPT_ID=");
		}
		bm_sql.append(bmid);
		bm_sql.append("  ) d  ");
		return bm_sql.toString();
	}
	//获取人员邮箱地址
	public String getMail(RecordSet rs,String userid) {
		String email = "";
		String sql = " select to_char(wm_concat(email)) as email from hrmresource where id in("+userid+")";
		rs.execute(sql);
		while(rs.next()) {
			email = Util.null2String(rs.getString("email"));
		}
		return email;
	}
	
	//获取部门名称
	public String getDeptName(RecordSet rs,String dept_id) {
		String res = "";
		String sql = " select DEPARTMENTNAME from hrmdepartment where id="+dept_id;
		rs.execute(sql);
		while(rs.next()) {
			res = Util.null2String(rs.getString("DEPARTMENTNAME"));
		}
		return res;
	}

	//获取人员加班明细信息
	public List<Map<String,String>> getExcelList(RecordSet rs,String sql){
		List<Map<String,String>> listmap=new ArrayList<Map<String,String>>();
		rs.execute(sql);
		while(rs.next()) {
			Map<String,String> map=new HashMap<String,String>();
			for(int i=0;i<detailTitle.length;i++) {
				map.put(detailTitle[i], Util.null2String(rs.getString(colsName[i])));
			}
			listmap.add(map);
		}
		return listmap;		
	}
	
	//获取人员加班汇总信息
	public List<Map<String,String>> getSummaryList(List<Map<String,String>> detailDate,String now_month,String type){
		List<Map<String,String>> listmap=new ArrayList<Map<String,String>>();
		log.writeLog("EmpOverTimeNotice getSummaryList start");
		Object[] zjry = new Object[12];//直接人员加班信息
		Object[] jjry = new Object[12];//间接人员加班信息
		Object[] all = new Object[12];//所有人员加班信息
		for(int i=0;i<12;i++) {
			zjry[i]="0";
			jjry[i]="0";
			all[i]="0";
		}
		Map<String,String> detailInfo=new HashMap<String,String>();
		String humType = "";//人员类型
		double jbsxss = 0;//调整后加班受限时数
		double stzss = 0;//标准加班受限时数
		double jbzss = 0;//加班时数
		String company = "";//公司
		String center = "";//中心
		String departName = "";//部门
		for(int j=0;j<detailDate.size();j++) {
			detailInfo = detailDate.get(j);
			if("".equals(company)) {
				company = detailInfo.get("分部");
			}
			if("".equals(center)) {
				center = detailInfo.get("中心");
			}
			if("".equals(departName)) {
				departName = detailInfo.get("部门");
			}
			humType = detailInfo.get("员工类型");
			String limitHours = detailInfo.get("调整后加班受限总时数");
			String stHours = detailInfo.get("标准加班受限总时数");
			String totalHours = detailInfo.get("加班合计");
			if(!isInteger(limitHours) || !isInteger(totalHours) || !isInteger(stHours)) {
				continue;
			}
			jbsxss = Float.parseFloat("".equals(limitHours)||limitHours==null?"0":limitHours);
			jbzss = Float.parseFloat("".equals(totalHours)||totalHours==null?"0":totalHours);
			stzss = Float.parseFloat("".equals(stHours)||stHours==null?"0":stHours);
			if("直接人员".equals(humType)) {
				zjry[0] = Integer.parseInt(String.valueOf(zjry[0]))+1;
				zjry[1] = Float.parseFloat(String.valueOf(zjry[1]))+stzss;
				zjry[2] = Float.parseFloat(String.valueOf(zjry[2]))+jbsxss;
				zjry[3] = Float.parseFloat(String.valueOf(zjry[3]))+jbzss;
				if(jbzss>=0 && jbzss<=30) {
					zjry[6] = Integer.parseInt(String.valueOf(zjry[6]))+1;
				}else if(jbzss>30 && jbzss<=50){
					zjry[7] = Integer.parseInt(String.valueOf(zjry[7]))+1;
				}else if(jbzss>50 && jbzss<=60){
					zjry[8] = Integer.parseInt(String.valueOf(zjry[8]))+1;
				}else if(jbzss>60 && jbzss<=80){
					zjry[9] = Integer.parseInt(String.valueOf(zjry[9]))+1;
				}else if(jbzss>80 && jbzss<=110){
					zjry[10] = Integer.parseInt(String.valueOf(zjry[10]))+1;
				}else {
					zjry[11] = Integer.parseInt(String.valueOf(zjry[11]))+1;
				}
			}else if("间接人员".equals(humType)){
				jjry[0] = Integer.parseInt(String.valueOf(jjry[0]))+1;
				jjry[1] = Float.parseFloat(String.valueOf(jjry[1]))+stzss;
				jjry[2] = Float.parseFloat(String.valueOf(jjry[2]))+jbsxss;
				jjry[3] = Float.parseFloat(String.valueOf(jjry[3]))+jbzss;
				if(jbzss>=0 && jbzss<=30) {
					jjry[6] = Integer.parseInt(String.valueOf(jjry[6]))+1;
				}else if(jbzss>30 && jbzss<=50){
					jjry[7] = Integer.parseInt(String.valueOf(jjry[7]))+1;
				}else if(jbzss>50 && jbzss<=60){
					jjry[8] = Integer.parseInt(String.valueOf(jjry[8]))+1;
				}else if(jbzss>60 && jbzss<=80){
					jjry[9] = Integer.parseInt(String.valueOf(jjry[9]))+1;
				}else if(jbzss>80 && jbzss<=110){
					jjry[10] = Integer.parseInt(String.valueOf(jjry[10]))+1;
				}else {
					jjry[11] = Integer.parseInt(String.valueOf(jjry[11]))+1;
				}
			}else {
				//do nothing
			}
			all[0] = Integer.parseInt(String.valueOf(all[0]))+1;
			all[1] = Float.parseFloat(String.valueOf(all[1]))+stzss;
			all[2] = Float.parseFloat(String.valueOf(all[2]))+jbsxss;
			all[3] = Float.parseFloat(String.valueOf(all[3]))+jbzss;
			if(jbzss>=0 && jbzss<=30) {
				all[6] = Integer.parseInt(String.valueOf(all[6]))+1;
			}else if(jbzss>30 && jbzss<=50){
				all[7] = Integer.parseInt(String.valueOf(all[7]))+1;
			}else if(jbzss>50 && jbzss<=60){
				all[8] = Integer.parseInt(String.valueOf(all[8]))+1;
			}else if(jbzss>60 && jbzss<=80){
				all[9] = Integer.parseInt(String.valueOf(all[9]))+1;
			}else if(jbzss>80 && jbzss<=110){
				all[10] = Integer.parseInt(String.valueOf(all[10]))+1;
			}else {
				all[11] = Integer.parseInt(String.valueOf(all[11]))+1;
			}
		}
		zjry[4] = getPercent(Float.parseFloat(String.valueOf(zjry[3])),Float.parseFloat(String.valueOf(zjry[1]==null || "0".equals(zjry[1])?"1":zjry[1])));
		zjry[5] = division(Float.parseFloat(String.valueOf(zjry[3])),Integer.parseInt(String.valueOf(zjry[0]==null || "0".equals(zjry[0])?"1":zjry[0])));
		jjry[4] = getPercent(Float.parseFloat(String.valueOf(jjry[3])),Float.parseFloat(String.valueOf(jjry[1]==null || "0".equals(jjry[1])?"1":jjry[1])));
		jjry[5] = division(Float.parseFloat(String.valueOf(jjry[3])),Integer.parseInt(String.valueOf(jjry[0]==null || "0".equals(jjry[0])?"1":jjry[0])));
		all[4] = getPercent(Float.parseFloat(String.valueOf(all[3])),Float.parseFloat(String.valueOf(all[1]==null || "0".equals(all[1])?"1":all[1])));
		all[5] = division(Float.parseFloat(String.valueOf(all[3])),Integer.parseInt(String.valueOf(all[0]==null || "0".equals(all[0])?"1":all[0])));
		listmap.add(getData(company, center, zjry, departName, type, now_month, "直接人员"));
		listmap.add(getData(company, center, jjry, departName, type, now_month, "间接人员"));
		listmap.add(getData(company, center, all, departName, type, now_month, "直接人员+间接人员"));
		return listmap;		
	}
	
	public boolean sendemail(String mailto,String subject,String body,String filename,String filecontent) {
		SystemComInfo sci = new SystemComInfo();
		String mailip = sci.getDefmailserver();//
		String mailuser = sci.getDefmailuser();
		String password = sci.getDefmailpassword();
		String needauth = sci.getDefneedauth();//是否需要发件认证
		String mailfrom = sci.getDefmailfrom();
		String mailcc = "";
		String mailbcc = "";	
		int char_set = 3;
		String priority = "3" ;	
		boolean sendFlag = false;
    	ArrayList<String> filenames = new ArrayList<String>();
    	ArrayList<InputStream> filecontents = new ArrayList<InputStream>();    	
    	filenames.add(filename);   	
    	try {
		    InputStream in = new FileInputStream(filecontent);
		    filecontents.add(in);
	    } catch (FileNotFoundException e) {
		    e.printStackTrace();
	    }
    	
    	//log.writeLog("mailto="+mailto+" subject="+subject+" body="+body+" filename="+filenames.toString()+" filecontents="+filecontents.toString());
   	
		SendMail sm = new SendMail();
		sm.setMailServer(mailip);//邮件服务器IP
		if (needauth.equals("1")) {
			sm.setNeedauthsend(true);
			sm.setUsername(mailuser);//服务器的账号
			sm.setPassword(password);//服务器密码
		} else {
			sm.setNeedauthsend(false);
		}
		sendFlag = sm.sendMiltipartHtml(mailfrom, mailto, mailcc, mailbcc, subject, body, char_set, filenames, filecontents, priority);
		if(sendFlag) {
			log.writeLog("EmpOverTimeNotice run success");
		}else {
			log.writeLog("EmpOverTimeNotice run error");		
		}
		return sendFlag;
	}
	//获取百分数
	public String getPercent(float a,float b) {
		float percent = a / b;
		//获取格式化对象
		NumberFormat nt = NumberFormat.getPercentInstance();
		//设置百分数精确度2即保留两位小数
		nt.setMinimumFractionDigits(0);
		//最后格式化并输出
		return nt.format(percent);
	}
	//保留一位小数
	public String division(float a ,int b){
        String result = "";
        float num =a/b;

        DecimalFormat df = new DecimalFormat("0.0");

        result = df.format(num);

        return result;

    }
	
	public Map<String,String> getData(String company,String center,Object[] num,String departName,String type,String now_month,String humType) {
		String[] data = new String[17];
		Map<String,String> map=new HashMap<String,String>();
		for(int j=0;j<data.length;j++) {
			if(j==0) {
				data[j] = now_month;
			}else if(j==1) {
				data[j] = company;
			}else if(j==2){
				data[j] = center;
			}else if(j==3){
				if("center".equals(type)) {
					data[j] = "";
				}else {
					data[j] = departName;
				}
			}else if(j==4){
				data[j] = humType;
			}else {
				data[j] = String.valueOf(num[j-5]);
			}
			map.put(titles[j], data[j]);
		}
		return map;
	}
	/**
	 * 判断是否为数值
	 * */
	public static boolean isInteger(String str) {  
		Pattern pattern = Pattern.compile("[0-9]*");
	    if(str.indexOf(".")>0){//判断是否有小数点
	        if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
	            return pattern.matcher(str.replace(".","")).matches();
	        }else {
	            return false;
	        }
	    }else {
	        return pattern.matcher(str).matches();
	    }
    }
	
	public String getCurrentPath() {
		File f2 = new File(this.getClass().getResource("").getPath());
        return f2.toString();
	}
	/*public static void main(String[] args) throws Exception {
		EmpOverTimeNotice etn = new EmpOverTimeNotice();
		List<Map> listmap=new ArrayList<Map>();
		Map<String,String> map=new HashMap<String,String>();
		List<ExcelBean> ebs = new ArrayList<ExcelBean>();
		for(int i=0;i<etn.detailTitle.length;i++) {
			map.put(etn.detailTitle[i], "s");
		}
		listmap.add(map);
		ExcelBean eb1 = new ExcelBean("detail",etn.detailTitle,listmap);
		listmap = etn.getExcelList(listmap, "2019-05-16", "center");
		ExcelBean eb2 = new ExcelBean("summary",etn.titles,listmap);
		ebs.add(eb1);
		ebs.add(eb2);
		ExcelImportUtil eiu = new ExcelImportUtil();
		eiu.exportManySheetExcel("d://12345.xls", ebs);
		
	}*/

	public String getIfContainNoSubmit() {
		return ifContainNoSubmit;
	}

	public void setIfContainNoSubmit(String ifContainNoSubmit) {
		this.ifContainNoSubmit = ifContainNoSubmit;
	}

	public String getIfTestMode() {
		return ifTestMode;
	}

	public void setIfTestMode(String ifTestMode) {
		this.ifTestMode = ifTestMode;
	}

	public String getTestMail() {
		return testMail;
	}

	public void setTestMail(String testMail) {
		this.testMail = testMail;
	}
}
