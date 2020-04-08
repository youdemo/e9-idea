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

/** 
* @author 作者  张瑞坤
* @date 创建时间：2020年3月2日 下午5:26:53 
* @version 1.0  费用报销单
*/
public class ExpenseReimbursement20200327 implements Action{

	@Override
	public String execute(RequestInfo info) {
		// TODO Auto-generated method stub
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		String requestid = info.getRequestid();
		String tablename = info.getRequestManager().getBillTableName();
		String mid = "";//
		String bdbh = "";//单据流水号     --- 主表
		String sqr = "";//申请人     --- 主表
		String bm = "";//申请部门     --- 主表
		String szgs = "";//所属公司     --- 主表
		String rq = "";//申请日期:     --- 主表
		String fygzbmfzr = "";//费用归属部门负责人     --- 主表
		String fycdbm = "";//费用承担部门     --- 主表
		String khyx = "";//开户银行     --- 主表
		String yxzh = "";//银行账号     --- 主表
		String skdw = "";//收款单位     --- 主表
		String skr = "";//收款单位开户行     --- 主表
		String skdwyxzh = "";//收款单位银行帐号     --- 主表
		String bxlx = "";//报销类型     --- 主表
		String fkje = "";//付款总金额     --- 主表
		String jedx = "";//金额大写     --- 主表
		String glsqspd = "";//费用超标申请关联     --- 主表
		String gljkd = "";//关联借款单     --- 主表
		String szqy = "";//研发项目     --- 主表
		String sy = "";//事由     --- 主表
		String dksj = "";//打款时间:     --- 主表
		String skren = "";//收款人     --- 主表
		String rq_mx = "";//日期     --- 明细表1
		String sy_mx = "";//摘要     --- 明细表1
		String je = "";//报销金额     --- 明细表1
		String dzfph = "";//电子发票号     --- 明细表1
		String fyxmu = "";//费用项目     --- 明细表1
		String se = "";//税额     --- 明细表1
		String bhsje = "";//不含税金额     --- 明细表1
		String djzs = "";//单据张数     --- 明细表1
		String fyxmjm = "";//项目     --- 明细表1
		String bz = "";//备注     --- 明细表1
		String fyxm = "";//项目1     --- 明细表1
		String zzszyhm = "";//增值税专用号码     --- 明细表1
		String fplx = "";//发票类型     --- 明细表1
		String fpsl = "";//发票税率%     --- 明细表1
		String fkje_mx = "";//付款金额     --- 明细表1
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
		StringBuffer sb = new StringBuffer();
		GetUtil gu = new GetUtil();
		String K3CloudURL = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL"));
		String dbId = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "dbId"));
		String uid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "uid"));
		String pwd = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "pwd"));
		log.writeLog("K3CloudURL---"+K3CloudURL+"---dbId----"+dbId+"---uid---"+uid+"---pwd-"+pwd);
		String sql = "select * from  "+tablename+" where requestid = '"+requestid+"'";
		rs.executeSql(sql);
		if(rs.next()) {
			bdbh = Util.null2String(rs.getString("bdbh"));//单据流水号
			//sqr = Util.null2String(rs.getString("sqr"));//申请人
			sqrgh = Util.null2String(rs.getString("sqrgh"));//申请人
			//bm = Util.null2String(rs.getString("bm"));//申请部门
			k3sqbmid = Util.null2String(rs.getString("sqbmbm"));//申请部门
			k3fycdbm = Util.null2String(rs.getString("fycdbmbm"));//
			k3szgs = Util.null2String(rs.getString("szgsbm"));//
			//szgs = Util.null2String(rs.getString("szgs"));//所属公司
			rq = Util.null2String(rs.getString("rq"));//申请日期:
			//fygzbmfzr = Util.null2String(rs.getString("fygzbmfzr"));//费用归属部门负责人
			//fycdbm = Util.null2String(rs.getString("fycdbm"));//费用承担部门
			//khyx = Util.null2String(rs.getString("khyx"));//开户银行
			//yxzh = Util.null2String(rs.getString("yxzh"));//银行账号
//			skdw = Util.null2String(rs.getString("skdw"));//收款单位
			skdw = Util.null2String(rs.getString("skdwbm"));//收款单位
			skr = Util.null2String(rs.getString("skr"));//收款单位开户行
			skdwyxzh = Util.null2String(rs.getString("skdwyxzh"));//收款单位银行帐号
			bxlx = Util.null2String(rs.getString("bxlx"));//报销类型
			fkje = Util.null2String(rs.getString("fkje"));//付款总金额
			//jedx = Util.null2String(rs.getString("jedx"));//金额大写
			//glsqspd = Util.null2String(rs.getString("glsqspd"));//费用超标申请关联
			//gljkd = Util.null2String(rs.getString("gljkd"));//关联借款单
			szqy = Util.null2String(rs.getString("yfxm"));//研发项目
			sy = Util.null2String(rs.getString("sy"));//事由
			//dksj = Util.null2String(rs.getString("dksj"));//打款时间:
			skren = Util.null2String(rs.getString("skrgh"));//收款人
			hl = Util.null2String(rs.getString("hl"));//汇率
			mid = rs.getString("id");
			jsfs = Util.null2String(rs.getString("jsfs"));//  FPaySettlleTypeID
			bb = Util.null2String(rs.getString("bb"));//uf_bb   FCurrencyID
			tbxm = Util.null2String(rs.getString("tbxm"));//投标项目
		}
		String nblx = "";
		String wldw = "";
		if("0".equals(bxlx)) {
			nblx ="BD_Empinfo";
			wldw = skren;
		}else {
			nblx = "FIN_OTHERS";
			wldw = skdw;
		}
		if(hl.length()<1) {
			hl = "1";
		}
		//gu.getFieldVal("hrmdepartment", "departmentcode", "id", bm)  gu.getFieldVal("hrmsubcompany", "subcompanycode", "id", szgs)
		sb.append("{\"Creator\":\""+sqrgh+"\",\"NeedUpDateFields\":[],\"NeedReturnFields\":[],\"IsDeleteEntry\":\"true\",\"SubSystemId\":\"\",\"IsVerifyBaseDataField\":\"false\",\"IsEntryBatchFill\":\"true\",\"ValidateFlag\":\"true\",\"NumberSearch\":\"true\",\"InterationFlags\":\"\",\"IsAutoSubmitAndAudit\":\"false\",");
		sb.append("\"Model\": {\"FID\": 0,\"FBillNo\": \""+bdbh+"\", \"FDate\": \""+rq+"\",\"FCurrencyID\": {\"FNUMBER\": \""+gu.getFieldVal("uf_bb", "bm", "id", bb)+"\"  }, \"FOrgID\": {\"FNumber\": \""+k3szgs+"\" },");
		sb.append("\"FCausa\": \""+sy+"\", \"FProposerID\": {  \"FSTAFFNUMBER\": \""+sqrgh+"\"  }, \"FRequestDeptID\": {\"FNUMBER\": \""+k3sqbmid+"\"}, \"FContactPhoneNo\": \"\", \"FBillTypeID\": { \"FNUMBER\": \"FYBXD001_SYS\" },");
		sb.append(" \"FExpenseOrgId\": {\"FNumber\": \""+k3szgs+"\"},\"FExpenseDeptID\": { \"FNUMBER\": \""+k3fycdbm
		+"\"},\"FOUTCONTACTUNITTYPE\": \"\", \"FCONTACTUNITTYPE\": \""+nblx+"\"," + " \"FOUTCONTACTUNIT\": {\"FNumber\": \"\" }, \"FCONTACTUNIT\": { \"FNumber\": \""+wldw+"\"},");
		sb.append("\"FPayOrgId\": {\"FNumber\": \""+k3szgs+"\"},\"FPaySettlleTypeID\": {\"FNUMBER\": \""+gu.getFieldVal("uf_jsfs", "bm", "id", jsfs)+"\"},\"FRefundBankAccount\": {\"FNUMBER\": \""
		+skdwyxzh+"\" },\"FBankBranchT\": \""+skr+"\",\"FBankAccountNameT\": \"\",\"FBankAccountT\": \""+skdwyxzh+"\",\"FBankTypeRecT\": {\"FNUMBER\": \"\"},\"FProvinceT\": {\"FNAME\": \"\"},\"FCityT\": {\"FNAME\": \"\"},");
		sb.append("\"FDistrictT\": { \"FNUMBER\": \"\" }, \"FLocCurrencyID\": { \"FNUMBER\": \"PRE001\" }, \"FExchangeTypeID\": { \"FNUMBER\": \"HLTX01_SYS\" },  \"FExchangeRate\": "+hl+", \"FSplitEntry\": \"false\", \"FCombinedPay\": \"false\", \"FLocExpAmountSum\": 0, \"FLocReqAmountSum\": 0, \"FExpAmountSum\": 0, \"FReqAmountSum\": 0, \"FCreatorId\": { \"FUserID\": \"\" }, \"FCreateDate\": \""+gu.getDateNow()+"\",  \"FModifierId\": { \"FUserID\": \"\" },");
		sb.append("\"FModifyDate\": \"\", \"FAPPROVEDATE\": \"\", \"FAPPROVERID\": { \"FUserID\": \"\" }, \"FRequestType\": \"\", \"FReqReimbAmountSum\": "+fkje+", \"FReqPayReFoundAmountSum\": 0, \"FBankAddress\": \"\", \"FBANKCNAPS\": \"\", \"FRealPay\": \"false\", \"FBankDetail\": {  \"FNUMBER\": \"\" }, \"FScanPoint\": {  \"FNUMBER\": \"\" },");
		sb.append("\"FCountry\": \"\",\"FNProvince\": \"\",\"FNCity\": \"\",\"FNDistrict\": \"\",\"F_ORA_RSCPROJECT\": { \"FNumber\": \""+gu.getFieldVal("uf_yfxm", "bm", "id", szqy)+"\"}, \"F_ORA_ENGPROJECT\": { \"FNumber\": \"\"},\"F_ORA_RECRUITPROJECT\": {\"FNUMBER\": \""+tbxm+"\"},");
		sb.append(" \"FEntity\": [");
		
		
		sql = "select * from "+tablename+"_dt1 where mainid = '"+mid+"' order by id ";
		rs.executeSql(sql);
		int a = 0;
		while(rs.next()) {
			//rq_mx = Util.null2String(rs.getString("rq"));//日期
			//sy_mx = Util.null2String(rs.getString("sy"));//摘要
			je = Util.null2String(rs.getString("je"));//报销金额
			dzfph = Util.null2String(rs.getString("dzfph"));//电子发票号
			fyxmu = Util.null2String(rs.getString("fyxmu"));//费用项目
			se = Util.null2String(rs.getString("se"));//税额
			bhsje = Util.null2String(rs.getString("bhsje"));//不含税金额  --bhsje
			//djzs = Util.null2String(rs.getString("djzs"));//单据张数
			//fyxmjm = Util.null2String(rs.getString("fyxmjm"));//项目
			bz = Util.null2String(rs.getString("bz"));//备注
			//fyxm = Util.null2String(rs.getString("fyxm"));//项目1
			//zzszyhm = Util.null2String(rs.getString("zzszyhm"));//增值税专用号码
			fplx = Util.null2String(rs.getString("fplx"));//发票类型
			fpsl = Util.null2String(rs.getString("fpsl"));//发票税率%
			fkje_mx = Util.null2String(rs.getString("fkje"));//
			if(fpsl.length()<1) {
				fpsl = "1";
			}
			if(bhsje.length()<1) {
				bhsje = "0";
			}
			if(se.length()<1) {
				se = "0";
			}
			
			if(fkje_mx.length()<1) {
				fkje_mx = "0";
			}
			String fp = gu.getFieldVal("uf_fplxsl", "fplx", "id", fplx);
			if("增值税发票".equals(fp)) {
				fp = "1";
			}else if("火车票".equals(fp)) {
				fp = "R";
			}else if("机票".equals(fp)) {
				fp = "P";
			}else {
				fp = "0";
			}
			if(a>0) {
				sb.append(",");
			}
			sb.append(" {\"FEntryID\": 0, \"FExpID\": {\"FNUMBER\": \""+fyxmu+"\"},\"FLocExpSubmitAmount\": 0,\"FLocReqSubmitAmount\": 0,\"FLOCTAXAMOUNT\": 0,\"FLOCNOTAXAMOUNT\": "+bhsje+",\"FInvoiceType\": \""+fp
					+"\",\"FOriginalCurrencyId\": {\"FNUMBER\": \"\"},\"FOriginalAmount\": 0,\"FOriginalExRate\": 0,");
			
			///费用金额 + 税额 = 申请报销金额        bhsje +se = 
			
			sb.append(" \"FTaxSubmitAmt\": "+bhsje+", \"FTaxRate\": "+fpsl+", \"FTaxAmt\": "+se+", \"FExpenseAmount\": "+je+", \"FProductID\": { \"FNUMBER\": \"\" }, \"FExpenseDeptEntryID\": {  \"FNUMBER\": \""+k3fycdbm+"\" }, \"FRequestAmount\": "+fkje_mx+", \"FExpSubmitAmount\": "+je+", \"FReqSubmitAmount\": 0, \"FBankType\": {   \"FNUMBER\": \"\"  },  \"FSettlleTypeID\": {  \"FNUMBER\": \"\" },");
			sb.append(" \"FRemark\": \""+bz+"\", \"FProvince\": { \"FNUMBER\": \"\"  },  \"FCity\": { \"FNUMBER\": \"\" }, \"FDistrict\": {  \"FNUMBER\": \"\" }, \"FOnlineBank\": \"false\", \"FBankAccountName\": \"\", \"FBankAccount\": \"\", \"FBankBranch\": \"\", \"FPushRecAmount\": 0, \"FRELRQAMOUNT\": 0, \"F_ORA_INVOICENO\": \""+dzfph+"\" }");
			
			a++;
		}
		sb.append("  ] } }");
		log.writeLog("传递数据0-----"+sb.toString());
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		int lang = Util.getIntValue(Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL")),2052);
		StringBuffer error_sb = new StringBuffer();
		try {
			Boolean resultLogin = client.login(dbId, uid, pwd, lang);
			
			 if (resultLogin) {
				 String content = sb.toString();
				 String result = client.save("ER_ExpReimbursement", content);
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
