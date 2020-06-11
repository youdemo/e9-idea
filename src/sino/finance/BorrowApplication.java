package sino.finance;

import org.json.JSONArray;
import org.json.JSONObject;
import sino.k3cloud.webapi.K3CloudApiClient;
import sino.util.GetUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @author
* @date
* @version 1.0  借款申请单
*/
public class BorrowApplication implements Action{

	@Override
	public String execute(RequestInfo info) {
		// TODO Auto-generated method stub
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		String requestid = info.getRequestid();
		String tablename = info.getRequestManager().getBillTableName();
		int currentid = info.getRequestManager().getUserId();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sf.format(new Date());

		String mid = "";//
		String bdbh = "";//单据流水号     --- 主表
		String rq = "";//申请日期:     --- 主表
		String skdwbm = "";//收款单位     --- 主表
		String k3khyx = "";//收款人开户行
		String k3yxzh = "";//收款人银行账号
		String skdwkhh = "";//收款单位开户行     --- 主表
		String skdwyxzh = "";//收款单位银行帐号     --- 主表
		String bxlx = "";//报销类型     --- 主表
		String fkje = "";//付款总金额     --- 主表
		String szqy = "";//研发项目     --- 主表
		String sy = "";//事由     --- 主表
		String skren = "";//收款人     --- 主表
		///
		String k3fycdbm = "";//k3费用承担部门编码
		String k3szgs = "";//k3所属公司编码
		String k3sqbmid = "";//k3申请部门编码
		///
		String hl = "";//汇率
		String bb = "";//币别
		String jsfs = "";//结算方式
		String tbxm = "";//tbxm  投标项目
		String sqrgh = "";//
		String yhzhmc = "";
		String dwzhmc = "";
		StringBuffer sb = new StringBuffer();
		GetUtil gu = new GetUtil();
		String K3CloudURL = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL"));
		String dbId = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "dbId"));
		String uid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "uid"));
		String pwd = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "pwd"));
		log.writeLog("K3CloudURL---"+K3CloudURL+"---dbId----"+dbId+"---uid---"+uid+"---pwd-"+pwd);
		String sql = "select * from  "+tablename+" where requestid = '"+requestid+"'";
		rs.execute(sql);
		if(rs.next()) {
			bdbh = Util.null2String(rs.getString("bdbh"));//单据流水号
			sqrgh = Util.null2String(rs.getString("sqrgh"));//申请人
			k3sqbmid = Util.null2String(rs.getString("sqbmbm"));//申请部门
			k3fycdbm = Util.null2String(rs.getString("jkcdbmbm"));//
			k3szgs = Util.null2String(rs.getString("szgsbm"));//
			rq = Util.null2String(rs.getString("rq"));//申请日期:
			skdwbm = Util.null2String(rs.getString("skdwbm"));//收款单位
			skdwkhh = Util.null2String(rs.getString("skdwkhx"));//收款单位开户行     --- 主表
			skdwyxzh = Util.null2String(rs.getString("skdwyxzh"));//收款单位银行帐号     --- 主表

			skren = Util.null2String(rs.getString("skrgh"));//收款人
			k3khyx = Util.null2String(rs.getString("khyx"));//收款单位开户行
			k3yxzh = Util.null2String(rs.getString("yxzh"));//收款单位银行帐号

			bxlx = Util.null2String(rs.getString("jklx"));//报销类型
			fkje = Util.null2String(rs.getString("fkje"));//付款总金额
			sy = Util.null2String(rs.getString("sy"));//事由
			hl = Util.null2String(rs.getString("hl"));//汇率
			mid = rs.getString("id");
			jsfs = Util.null2String(rs.getString("jsfs"));//  FPaySettlleTypeID
			bb = Util.null2String(rs.getString("bbie"));//uf_bb   FCurrencyID
			tbxm = Util.null2String(rs.getString("tbxm"));//投标项目

			yhzhmc = Util.null2String(rs.getString("zhmc"));
			dwzhmc = Util.null2String(rs.getString("skdwzhmc"));
		}
		String zgjdid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "jksqzgjd"));
		String cwshjdid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "jksqcwjd"));
		String zjljdid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "jksqzjl"));
		String zgsh = "";//主管审核
		String zgshsj = "";
		String cwsh = "";//财务审核
		String cwshsj = "";
		String zjlsh = "";//总经理审核
		String zjlshsj="";
		String cnsh = "";//出纳审核
		String cnshsj = nowDate;//出纳审核
		sql = "select (select lastname from hrmresource where id=a.operator)as spr,operatedate as spsj from workflow_requestlog a where logtype in('0','2') and nodeid="+zgjdid+" and requestid="+requestid+" order by logid desc";
		rs.execute(sql);
		if(rs.next()){
			zgsh = Util.null2String(rs.getString("spr"));
			zgshsj = Util.null2String(rs.getString("spsj"));
		}
		sql = "select (select lastname from hrmresource where id=a.operator)as spr,operatedate as spsj from workflow_requestlog a where logtype in('0','2') and nodeid="+cwshjdid+" and requestid="+requestid+" order by logid desc";
		rs.execute(sql);
		if(rs.next()){
			cwsh = Util.null2String(rs.getString("spr"));
			cwshsj = Util.null2String(rs.getString("spsj"));
		}
		sql = "select (select lastname from hrmresource where id=a.operator)as spr,operatedate as spsj from workflow_requestlog a where logtype in('0','2') and nodeid="+zjljdid+" and requestid="+requestid+" order by logid desc";
		rs.execute(sql);
		if(rs.next()){
			zjlsh = Util.null2String(rs.getString("spr"));
			zjlshsj = Util.null2String(rs.getString("spsj"));
		}
		sql = "select lastname from hrmresource where id="+currentid;
		rs.execute(sql);
		if(rs.next()){
			cnsh = Util.null2String(rs.getString("lastname"));
		}

		String zhmc = "";
		String nblx = "";
		String wldw = "";
		String khh = "";
		String yhzh = "";
		if("0".equals(bxlx)) {
			nblx ="BD_Empinfo";
			wldw = skren;
			zhmc = yhzhmc;
			khh =k3khyx;
			yhzh =k3yxzh;
		}else {
			nblx = "FIN_OTHERS";
			wldw = skdwbm;
			zhmc = dwzhmc;
			khh =skdwkhh;
			yhzh =skdwyxzh;
		}
		if(hl.length()<1) {
			hl = "1";
		}
		//gu.getFieldVal("hrmdepartment", "departmentcode", "id", bm)  gu.getFieldVal("hrmsubcompany", "subcompanycode", "id", szgs)
		sb.append("{\"Creator\":\""+sqrgh+"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"true\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"true\",\"ValidateFlag\":\"true\",\"NumberSearch\":\"true\",\"InterationFlags\":\"\",\"IsAutoSubmitAndAudit\":\"false\",");
		sb.append("\"Model\": {\"FID\": 0,\"FBillNo\": \""+bdbh+"\", \"FDate\": \""+rq+"\",\"FCurrencyID\": {\"FNUMBER\": \""+bb+"\"  }, \"FOrgID\": {\"FNumber\": \""+k3szgs+"\" },");
		sb.append("\"FReason\": \""+sy+"\", \"FStaffID\": {  \"FSTAFFNUMBER\": \""+sqrgh+"\"  }, \"FDeptID\": {\"FNUMBER\": \""+k3sqbmid+"\"}, \"FPhoneNumber\": \"\", \"FBillTypeID\": { \"FNUMBER\": \"FYSQ001_SYS\" },");
		sb.append(" \"FCostOrgID\": {\"FNumber\": \""+k3szgs+"\"},\"FCostDeptID\": { \"FNUMBER\": \""+k3fycdbm+"\"},\"FIsBorrow\": \"true\",\"FTOCONTACTUNITTYPE\": \""+nblx+"\", \"FTOCONTACTUNIT\": { \"FNumber\": \""+wldw+"\"},");
		sb.append("\"FPayOrgId\": {\"FNumber\": \""+k3szgs+"\"},\"FSettleTypeID\": {\"FNUMBER\": \""+gu.getFieldVal("uf_jsfs", "bm", "id", jsfs)+"\"},"
		+"\"FBankName\": \""+khh+"\",\"FBankAcctName\": \""+zhmc+"\",\"FBankAccount\": \""+yhzh+"\", \"FIsOnlineBankPay\": \"false\", \"FCostProductID\": {\"FNUMBER\": \"\"},");
		sb.append("\"FNDistrict\": { \"FNUMBER\": \"\" }, \"FLocCurrencyID\": { \"FNUMBER\": \"PRE001\" }, \"FExchangeTypeID\": { \"FNUMBER\": \"HLTX01_SYS\" },  \"FExchangeRate\": "+hl+", \"FOrgAmountSum\": "+fkje+", \"FCheckedOrgAmountSum\": "+fkje+", \"FLocAmountSum\": "+fkje+", \"FCheckedLocAmountSum\": "+fkje+", \"FCreatorId\": { \"FUserID\": \"\" }, \"FCreateDate\": \""+gu.getDateNow()+"\",  \"FModifierId\": { \"FUserID\": \"\" },");
		sb.append("\"FModifyDate\": \"\", \"FAPPROVEDATE\": \"\", \"FAPPROVERID\": { \"FUserID\": \"\" }, \"FRefundDate\": \""+rq+"\", \"FBankAddress\": \"\", \"FBANKCNAPS\": \"\", \"FBankDetail\": {  \"FNUMBER\": \"\" },");
		sb.append("\"FCountry\": \"\",\"FNProvince\": \"\",\"FNCity\": \"\",\"FNDistrict\": \"\",\"F_ORA_RECRUITPROJECT\": {\"FNUMBER\": \""+tbxm+"\"},\"F_ORA_LEADERCHECK\": \""+zgsh+"\", \"F_ORA_GMCHECK\": \""+zjlsh+"\",\"F_ORA_FICHECK\": \""+cwsh+"\",\"F_ORA_ACCTCHECK\": \""+cnsh+"\",\"F_ORA_LEADERDATE\": \""+zgshsj+"\",\"F_ORA_GMDATE\": \""+zjlshsj+"\", \"F_ORA_FIDATE\": \""+cwshsj+"\",\"F_ORA_ACCTDATE\": \""+cnshsj+"\",");

		sb.append(" \"FEntity\": [");
		
		
		sql = "select * from "+tablename+"_dt1 where mainid = '"+mid+"' order by id ";
		rs.execute(sql);
		int a = 0;
		while(rs.next()) {
			String fkje_dt = Util.null2String(rs.getString("je"));//报销金额
			String sy_dt = Util.null2String(rs.getString("zy"));//摘要
			String fyxmu_dt = Util.null2String(rs.getString("fyxm2"));//费用项目
			if("".equals(fkje_dt)){
				fkje_dt = "0";
			}
			if(a>0) {
				sb.append(",");
			}
			sb.append(" {\"FEntryID\": 0, \"FSourceBillNo\": \"\",\"FSourceRowID\": 0,\"FSourceRowID\": 0, \"FLocAmount\": "+fkje_dt+",\"FCheckedLocAmount\": "+fkje_dt+",\"FExpenseItemID\": {\"FNUMBER\": \""+fyxmu_dt+"\"},\"FOrgAmount\": "+fkje_dt+",\"FCheckedOrgAmount\":"+fkje_dt+",\"FEntryCostDeptID\": {\"FNUMBER\": \""+k3fycdbm+"\"},\"FRemark\": \""+sy_dt+"\"}");
			
			a++;
		}
		sb.append("  ] } }");
		log.writeLog("BorrowApplication 传递数据0-----"+sb.toString());
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		int lang = Util.getIntValue(Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL")),2052);
		StringBuffer error_sb = new StringBuffer();
		try {
			Boolean resultLogin = client.login(dbId, uid, pwd, lang);
			
			 if (resultLogin) {
				 String content = sb.toString();
				 String result = client.save("ER_ExpenseRequest", content);
				 log.writeLog("requestid：---------"+requestid+"result--------"+result);
				 JSONObject jo = new JSONObject(result);
				 String IsSUCCESS = jo.getJSONObject("Result").getJSONObject("ResponseStatus").getString("IsSuccess");
				 String Errors  = jo.getJSONObject("Result").getJSONObject("ResponseStatus").getString("Errors");
				 JSONArray item = new JSONArray(Errors);
				 String flag = "";
				 for(int i = 0 ;i<item.length();i++) {
					 JSONObject err = item.getJSONObject(i);
					 String  Message =  err.getString("Message");
					 error_sb.append(flag+Message);
					 flag =",";
				 }
//				 String message = jo.getJSONObject("Result").getJSONObject("ResponseStatus").getString("IsSuccess");
				 if("false".equals(IsSUCCESS)) {
			    		info.getRequestManager().setMessagecontent("推送cloud接口成功，cloud处理失败，请联系系统管理员:"+error_sb.toString());        
				    	info.getRequestManager().setMessageid("ERRORMSG:");
				    	return FAILURE_AND_CONTINUE;
			    	}			 
				 
			 }else {
			    	info.getRequestManager().setMessagecontent("推送cloud登录验证接口失败，请联系系统管理员");        
			    	info.getRequestManager().setMessageid("ERRORMSG:");
			    	return FAILURE_AND_CONTINUE;
			    }
			
		} catch (Exception e) {
			e.printStackTrace();
			log.writeLog("requestid：-----"+requestid+"异常----"+e.getMessage());
			info.getRequestManager().setMessagecontent("推送cloud接口失败，请联系系统管理员:"+e.getMessage());        
	    	info.getRequestManager().setMessageid("ERRORMSG:");
	    	return FAILURE_AND_CONTINUE;
			
		}
		return SUCCESS;
	}

}
