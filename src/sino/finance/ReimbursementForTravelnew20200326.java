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
* @date 创建时间：2020年3月2日 下午5:26:34 
* @version 1.0 差旅费报销单
*/
public class ReimbursementForTravelnew20200326 implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		String requestid = info.getRequestid();
		GetUtil gu = new GetUtil();
		String tablename = info.getRequestManager().getBillTableName();
//		String spr = gu.getFieldVal("hrmresource", "workcode", "id", info.getRequestManager().getUserId()+"");
		String mid = "";//
		String djbh = "";//单据流水号 :  主表---文本
		String sqrq = "";//申请日期 :  主表---浏览按钮
//		String bxr = "";//申请人 :  主表---浏览按钮
//		String bm = "";//申请部门  主表---浏览按钮
//		String fycdbm = "";//费用承担部门  主表---浏览按钮
//		String szgs = "";//费用所属公司  主表---浏览按钮
		String sy = "";//事由  主表---单行文本框
		String bxzje = "";//付款总金额  主表---单行文本框
//		String skr = "";//收款人  主表---浏览按钮
		String khyx = "";//开户银行  主表---单行文本框
		String yxzh = "";//银行帐号  主表---单行文本框
//		String glcczjbg = "";//差旅报告关联  主表---浏览按钮
//		String gljksq = "";//借款申请关联  主表---浏览按钮
//		String glccsq = "";//费用超标申请关联  主表---浏览按钮
//		String dx = "";//金额大写  主表---单行文本框
//		String fkrq = "";//付款日期  主表---浏览按钮
//		String ccbz = "";//民航发展基金  主表---单行文本框
//		String whxje = "";//未核销金额  主表---单行文本框
		String sqrgh = "";//申请人工号  主表---单行文本框
		String fyszgsbm = "";//费用所属公司编码  主表---单行文本框
		String sqbmbm = "";//申请部门编码  主表---单行文本框
		String fycdbmbm = "";//费用承担部门编码  主表---单行文本框
		String skrgh = "";//收款人工号  主表---单行文本框
		String bb = "";//币别  主表---浏览按钮
		String hl = "";//汇率  主表---单行文本框
		String jsfs = "";//结算方式  主表---浏览按钮
//		String zsf = "";//付款金额合计  主表---单行文本框
//		String bxzje = "";//付款总金额
		String yfxm = "";//研发项目  主表---浏览按钮
		String ksrq = "";//开始日期  主表---浏览按钮
		String jsrq = "";//结束日期  主表---浏览按钮
		String cfd = "";//出发地  主表---单行文本框
		String mdd = "";//目的地  主表---单行文本框
//		String sj = "";//日期  明细表1---浏览按钮
		String symx = "";//摘要  明细表1---单行文本框  sy
		String fyxmu = "";//费用项目  明细表1---浏览按钮
//		String clflx = "";//差旅费类型  明细表1---单行文本框
//		String fplx = "";//发票类型  明细表1---单行文本框
		String pmje = "";//报销金额  明细表1---单行文本框
		String snjqt = "";//民航发展基金  明细表1---单行文本框
		String dzfph = "";//电子发票号  明细表1---单行文本框
		String fpsl = "";//发票税率%  明细表1---单行文本框
		String ryf = "";//税额  明细表1---单行文本框
//		String glsl = "";//不含税金额  明细表1---单行文本框
//		String djzs = "";//单据张数  明细表1---单行文本框
//		String zs = "";//住宿费  明细表1---单行文本框
//		String zdf = "";//招待费  明细表1---单行文本框
//		String ccbzmx = "";//出差补助  明细表1---单行文本框  ccbz
//		String fkje = "";//付款金额  明细表1---单行文本框
		String zs = "";//税额本位币
		String zdf = "";//不含税金额本位币
		String ccbz = "";//核定金额本位币
		StringBuffer sb = new StringBuffer();
		String K3CloudURL = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL"));
		String dbId = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "dbId"));
		String uid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "uid"));
		String pwd = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "pwd"));
		log.writeLog("K3CloudURL---"+K3CloudURL+"---dbId----"+dbId+"---uid---"+uid+"---pwd-"+pwd);
		String sql = "select * from  "+tablename+" where requestid = '"+requestid+"'";
		rs.executeSql(sql);
		if(rs.next()) {
			djbh = Util.null2String(rs.getString("djbh"));// 单据流水号 : 主表----文本
			sqrq = Util.null2String(rs.getString("sqrq"));// 申请日期 : 主表----浏览按钮
//			bxr = Util.null2String(rs.getString("bxr"));// 申请人 : 主表----浏览按钮
//			bm = Util.null2String(rs.getString("bm"));// 申请部门 主表----浏览按钮
//			fycdbm = Util.null2String(rs.getString("fycdbm"));// 费用承担部门 主表----浏览按钮
//			szgs = Util.null2String(rs.getString("szgs"));// 费用所属公司 主表----浏览按钮
			sy = Util.null2String(rs.getString("sy"));// 事由 主表----单行文本框
			bxzje = Util.null2String(rs.getString("bxzje"));// 付款总金额 主表----单行文本框
//			skr = Util.null2String(rs.getString("skr"));// 收款人 主表----浏览按钮
			khyx = Util.null2String(rs.getString("khyx"));// 开户银行 主表----单行文本框
			yxzh = Util.null2String(rs.getString("yxzh"));// 银行帐号 主表----单行文本框
//			glcczjbg = Util.null2String(rs.getString("glcczjbg"));// 差旅报告关联 主表----浏览按钮
//			gljksq = Util.null2String(rs.getString("gljksq"));// 借款申请关联 主表----浏览按钮
//			glccsq = Util.null2String(rs.getString("glccsq"));// 费用超标申请关联 主表----浏览按钮
//			dx = Util.null2String(rs.getString("dx"));// 金额大写 主表----单行文本框
//			fkrq = Util.null2String(rs.getString("fkrq"));// 付款日期 主表----浏览按钮
//			ccbz = Util.null2String(rs.getString("ccbz"));// 民航发展基金 主表----单行文本框
//			whxje = Util.null2String(rs.getString("whxje"));// 未核销金额 主表----单行文本框
			sqrgh = Util.null2String(rs.getString("sqrgh"));// 申请人工号 主表----单行文本框
			fyszgsbm = Util.null2String(rs.getString("fyszgsbm"));// 费用所属公司编码 主表----单行文本框
			sqbmbm = Util.null2String(rs.getString("sqbmbm"));// 申请部门编码 主表----单行文本框
			fycdbmbm = Util.null2String(rs.getString("fycdbmbm"));// 费用承担部门编码 主表----单行文本框
			skrgh = Util.null2String(rs.getString("skrgh"));// 收款人工号 主表----单行文本框
			bb = Util.null2String(rs.getString("bb"));// 币别 主表----浏览按钮
			hl = Util.null2String(rs.getString("hl"));// 汇率 主表----单行文本框
			jsfs = Util.null2String(rs.getString("jsfs"));// 结算方式 主表----浏览按钮
//			zsf = Util.null2String(rs.getString("zsf"));// 付款金额合计 主表----单行文本框
			yfxm = Util.null2String(rs.getString("yfxm"));// 研发项目 主表----浏览按钮
			ksrq = Util.null2String(rs.getString("ksrq"));// 开始日期 主表----浏览按钮
			jsrq = Util.null2String(rs.getString("jsrq"));// 结束日期 主表----浏览按钮
			cfd = Util.null2String(rs.getString("cfd"));// 出发地 主表----单行文本框
			mdd = Util.null2String(rs.getString("mdd"));// 目的地 主表----单行文本框
			mid = rs.getString("id");
		}
		if(hl.length()<1) {
			hl = "1";
		}
		if(bxzje.length()<1) {
			bxzje = "0";
		}
		
		sb.append("{\"Creator\":\""+sqrgh+"\", \"NeedUpDateFields\": [], \"NeedReturnFields\": [], \"IsDeleteEntry\": \"true\", \"SubSystemId\": \"\", \"IsVerifyBaseDataField\": \"false\", \"IsEntryBatchFill\": \"true\", \"ValidateFlag\": \"true\", \"NumberSearch\": \"true\", \"InterationFlags\": \"\", \"IsAutoSubmitAndAudit\": \"false\", \"Model\": {");
		
		sb.append("\"FID\": 0, \"FBillNo\": \""+djbh+"\", \"FDate\": \""+sqrq+"\",\"FBUSINESSTYPE\": \"2\", \"FCurrencyID\": { \"FNUMBER\": \""+bb+"\" }, \"FOrgID\": { \"FNumber\": \""+fyszgsbm+"\" }, \"FCausa\": \""+sy+"\", \"FProposerID\": { \"FSTAFFNUMBER\": \""+sqrgh+"\" }, \"FRequestDeptID\": { \"FNUMBER\": \""+sqbmbm+"\" }, \"FContactPhoneNo\": \"\", \"FBillTypeID\": { \"FNUMBER\": \"CLFBX002_SYS\" }, \"FExpenseOrgId\": { \"FNumber\": \""+fyszgsbm+"\" }, \"FExpenseDeptID\": { \"FNUMBER\": \""+fycdbmbm+"\" },");
		
		sb.append("\"FOUTCONTACTUNITTYPE\": \"\", \"FCONTACTUNITTYPE\": \"BD_Empinfo\", \"FOUTCONTACTUNIT\": { \"FNumber\": \"\" }, \"FCONTACTUNIT\": { \"FNumber\": \""+skrgh+"\" }, \"FPayOrgId\": { \"FNumber\": \"\"  }, \"FPaySettlleTypeID\": {  \"FNUMBER\": \""+gu.getFieldVal("uf_jsfs", "bm", "id", jsfs)+"\"  }, \"FRefundBankAccount\": { \"FNUMBER\": \"\" }, \"FBankBranchT\": \""+khyx+"\", \"FBankAccountNameT\": \"\",");
		
		
		sb.append("\"FBankAccountT\": \""+yxzh+"\", \"FBankTypeRecT\": { \"FNUMBER\": \"\" }, \"FProvinceT\": { \"FNAME\": \"\"  }, \"FCityT\": { \"FNAME\": \"\" }, \"FDistrictT\": {  \"FNUMBER\": \"\" }, \"FLocCurrencyID\": { \"FNUMBER\": \"PRE001\" }, \"FPeerPerson\": \"\", \"FExchangeRate\": "+hl+", \"FExchangeTypeID\": { \"FNUMBER\": \"HLTX01_SYS\" }, \"FSplitEntry\": \"false\", \"FCombinedPay\": \"false\", \"FLocExpAmountSum\": 0, \"FLocReqAmountSum\": 0, \"FExpAmountSum\": 0, \"FReqAmountSum\": "+bxzje+",");
		
		sb.append("\"FCreatorId\": { \"FUserID\": \"\" }, \"FCreateDate\": \""+gu.getDateNow()+"\", \"FModifierId\": { \"FUserID\": \"\" }, \"FModifyDate\": \"\", \"FAPPROVEDATE\": \"\", \"FAPPROVERID\": { \"FUserID\": \"\" }, \"FRequestType\": \"\", \"FReqReimbAmountSum\": "+bxzje+", \"FReqPayReFoundAmountSum\": 0, \"FBankAddress\": \"\", \"FBANKCNAPS\": \"\", \"FRealPay\": \"false\", \"FBankDetail\": { \"FNUMBER\": \"\" }, \"FScanPoint\": { \"FNUMBER\": \"\" },");
		sb.append("\"FCountry\": \"\",  \"FNProvince\": \"\", \"FNCity\": \"\", \"FNDistrict\": \"\",  \"F_ORA_RSCPROJECT\": { \"FNumber\": \""+gu.getFieldVal("uf_yfxm", "bm", "id", yfxm)+"\" }, \"F_ORA_ENGPROJECT\": { \"FNumber\": \"\" }, \"FEntity\": [");
		sql = "select * from "+tablename+"_dt1 where mainid = '"+mid+"'";
		rs.executeSql(sql);
		int a = 0;
		while(rs.next()){
//			sj = Util.null2String(rs.getString("sj"));// 日期 明细表1----浏览按钮
			symx = Util.null2String(rs.getString("sy"));// 摘要 明细表1----单行文本框
			fyxmu = Util.null2String(rs.getString("fyxmu"));// 费用项目 明细表1----浏览按钮
//			clflx = Util.null2String(rs.getString("clflx"));// 差旅费类型 明细表1----单行文本框
//			fplx = Util.null2String(rs.getString("fplx"));// 发票类型 明细表1----单行文本框
			pmje = Util.null2String(rs.getString("pmje"));// 报销金额 明细表1----单行文本框
			snjqt = Util.null2String(rs.getString("snjqt"));// 民航发展基金 明细表1----单行文本框
			dzfph = Util.null2String(rs.getString("dzfph"));// 电子发票号 明细表1----单行文本框
			fpsl = Util.null2String(rs.getString("fpsl"));// 发票税率% 明细表1----单行文本框
			ryf = Util.null2String(rs.getString("ryf"));// 税额 明细表1----单行文本框
			String glsl = Util.null2String(rs.getString("glsl"));// 不含税金额 明细表1----单行文本框
			zs = Util.null2String(rs.getString("zs"));
			zdf = Util.null2String(rs.getString("zdf"));
			ccbz = Util.null2String(rs.getString("ccbz"));
//			djzs = Util.null2String(rs.getString("djzs"));// 单据张数 明细表1----单行文本框
//			zs = Util.null2String(rs.getString("zs"));// 住宿费 明细表1----单行文本框
//			zdf = Util.null2String(rs.getString("zdf"));// 招待费 明细表1----单行文本框
//			ccbzmx = Util.null2String(rs.getString("ccbz"));// 出差补助 明细表1----单行文本框
//			fkje = Util.null2String(rs.getString("fkje"));// 付款金额 明细表1----单行文本框

			if(a>0) {
				sb.append(",");
			}
			if(pmje.length()<1) {
				pmje = "0";
			}
			if(snjqt.length()<1) {
				snjqt = "0";
			}
//			if(glsl.length()<1) {
//				glsl = "0";
//			}
			if(fpsl.length()<1) {
				fpsl = "1";
			}
//			if(glsl.length()<1) {
//				glsl = "0";//
//			}
			if(zs.length()<1) {
				zs = "0";//
			}
			if(zdf.length()<1) {
				zdf = "0";//
			}
			if(ccbz.length()<1) {
				ccbz = "0";//
			}
			
			sb.append("{\"FEntryID\": 0,\"FExpID\": {\"FNUMBER\": \""+gu.getFieldVal("uf_clbxd", "fyxmbm", "id", fyxmu)+"\"}, \"FTravelStartDate\": \""+ksrq+"\", \"FTravelEndDate\": \""+
			jsrq+"\",\"FTravelStartSite\": \""+cfd+"\",\"FTravelEndSite\": \""+mdd+"\",\"FDays\": 0,\"FTravelType\": {\"FNUMBER\": \""+gu.getFieldVal("uf_clbxd", "clflxbm", "id", fyxmu)+"\"},\"FOriginalExRate\": 0,\"FOriginalCurrencyId\": {\"FNUMBER\": \"\" },\"FTravelAmountFor\": "+pmje+",\"FTravelAmount\": "+pmje+",");
			sb.append("\"FCivilAviationFund\": "+snjqt+", \"FIsRealName\": \"\", \"FOriginalAmount\": 0, \"FFlyAmount\": 0, \"FFlyAmountFor\": 0, \"FCityTrafficFee\": 0, \"FCityTrafficFeeFor\": 0, \"FOtherTraAmount\": 0,  \"FOtherTraAmountFor\": 0, \"FLodgingFee\": 0, \"FLodgingFeeFor\": 0, \"FTravelSubsidy\": 0, \"FTravelSubsidyFor\": 0, \"FOtherExpense\": 0, \"FOtherExpenseFor\": 0,");
			sb.append("\"FInvoiceType\": \""+gu.getFieldVal("uf_clbxd", "bm", "id", fyxmu)+"\", \"FTaxRate\": "+fpsl+", \"FTaxAmt\": "+ryf+", \"FExpenseAmount\": "+pmje+", \"FProductID\": { \"FNUMBER\": \"\" }, \"FExpenseDeptEntryID\": {  \"FNUMBER\": \""+fycdbmbm+"\"  }, \"FRequestAmount\": "+pmje+",  \"FExpSubmitAmount\":"+pmje+",  \"FReqSubmitAmount\": "+pmje+", \"FTaxSubmitAmt\": "+glsl+", \"FLOCTAXAMOUNT\": "+zs+", \"FLOCNOTAXAMOUNT\": "+zdf+", \"FLocReqSubmitAmount\": "+ccbz+", \"FLocExpSubmitAmount\": "+ccbz+", \"F_ORA_INVOICENO\": \""+dzfph+"\", \"FBankType\": {  \"FNUMBER\": \"\" },");
			sb.append("\"FPushRecAmount\": 0, \"FSettlleTypeID\": { \"FNUMBER\": \"\" }, \"FRELRQAMOUNT\": 0, \"FProvince\": {  \"FNUMBER\": \"\" }, \"FCity\": { \"FNUMBER\": \"\" }, \"FDistrict\": { \"FNUMBER\": \"\" }, \"FOnlineBank\": \"false\", \"FBankAccountName\": \"\", \"FBankBranch\": \"\", \"FBankAccount\": \"\", \"FRemark\": \""+symx+"\",  \"FTravelSeq\": 0");
			sb.append("}");
			a++;
		}
		sb.append("]} }");
		log.writeLog("差旅费报销单传递数据0-----"+sb.toString());
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		int lang = Util.getIntValue(Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL")),2052);
		StringBuffer error_sb = new StringBuffer();
		try {
			Boolean resultLogin = client.login(dbId, uid, pwd, lang);
			 if (resultLogin) {
				 String content = sb.toString();
				 String result = client.save("ER_ExpReimbursement_Travel", content);
				 log.writeLog("差旅费报销单传递数据------requestid：---------"+requestid+"result--------"+result);
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
			    		info.getRequestManager().setMessagecontent("差旅费报销单传递数据------推送cloud接口成功，cloud处理失败，请联系系统管理员:"+error_sb.toString());        
				    	info.getRequestManager().setMessageid("ERRORMSG:");
				    	return FAILURE_AND_CONTINUE;
			    	}		 
				 
			 }else {
			    	info.getRequestManager().setMessagecontent("差旅费报销单传递数据-----------推送cloud登录验证接口失败，请联系系统管理员");        
			    	info.getRequestManager().setMessageid("ERRORMSG:");
			    	return FAILURE_AND_CONTINUE;
			    }
			
		} catch (Exception e) {
			e.printStackTrace();
			log.writeLog("差旅费报销单传递数据------requestid：-----"+requestid+"异常----"+e.getMessage());
			info.getRequestManager().setMessagecontent("差旅费报销单传递数据---------推送cloud接口失败，请联系系统管理员:"+e.getMessage());        
	    	info.getRequestManager().setMessageid("ERRORMSG:");
	    	return FAILURE_AND_CONTINUE;
			
		}
		return SUCCESS;
	}

}
