package APPDEV.HQ.FNA.TL;

import com.ctrip.ecology.XCAppUtil;
import com.ctrip.v2.util.CtripUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FNA_TL_TP_NodeGetCtripDataAction implements Action{
	
	@Override
	public String execute(RequestInfo info) {
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		new BaseBean().writeLog("FNA_TL_TP_NodeGetCtripDataAction requestid:"+requestid);
		RecordSet rs = new RecordSet();
		String tableName = "";
		String clsqid = "";//差旅申请流程id
		String sql = " Select tablename From Workflow_bill Where id in ( "
				+"	Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		String mainid = "";
		sql = "select id from " + tableName + " where requestid = " + requestid;
		rs.executeQuery(sql);
		if(rs.next()){
			mainid = Util.null2String(rs.getString("id"));
			clsqid = Util.null2String(rs.getString("clsqid"));
		}
		if("".equals(clsqid)){
			new BaseBean().writeLog("FNA_TL_TP_NodeGetCtripDataAction 差旅申请为空clsqid:"+clsqid);
			return SUCCESS;
		}
		int jdcount = 0;//酒店明细数量
		int jpcount = 0;//机票明细数量
		sql = "select count(1) as jdcount from "+tableName+"_dt2 where mainid="+mainid;
		rs.execute(sql);
		if(rs.next()){
			jdcount = rs.getInt("jdcount");
		}
		sql = "select count(1) as jpcount from "+tableName+"_dt3 where mainid="+mainid;
		rs.execute(sql);
		if(rs.next()){
			jpcount = rs.getInt("jpcount");
		}
		String journeyNos="";
		String flag = "";
		//if(jdcount==1 || jpcount==1){
			journeyNos = clsqid;
			flag = ",";
		//}
		if(jpcount>1){
			journeyNos = journeyNos+flag+clsqid+"-1";
			flag = ",";
		}
		if(jdcount>1){
			for(int i=1;i<=jdcount;i++){
				if(journeyNos.indexOf(clsqid+"-"+i)<0){
					journeyNos = journeyNos+flag+clsqid+"-"+i;
					flag = ",";
				}
			}
		}
		new BaseBean().writeLog("FNA_TL_TP_NodeGetCtripDataAction journeyNos:"+journeyNos);
//        sql = "delete from "+tableName+"_dt4 where mainid="+mainid;
//        rs.execute(sql);
		sql = "delete from "+tableName+"_dt5 where mainid="+mainid;
		rs.execute(sql);
		operGba(requestid,mainid,tableName,journeyNos,clsqid);
		
		
		
		return SUCCESS;
	}
	
	public boolean operGba(String requestid,String mainid,String tableName,String journeyNos,String clsqid){
		BaseBean log = new BaseBean();
		XCAppUtil xcau = new XCAppUtil();
		RecordSet rs = new RecordSet();
		String EmployeeID = xcau.getEmployeeIDForRequestid(clsqid);
		CtripUtil ctripUtil = CtripUtil.getUtilByRequestid(clsqid);
        String journeyNoArr[] = journeyNos.split(",");
        for(String journeyid:journeyNoArr) {
			if("".equals(journeyid)){
				continue;
			}
			new BaseBean().writeLog("FNA_TL_TP_NodeGetCtripDataAction journeyid:"+journeyid);
			String result = ctripUtil.getOrderInfo("", journeyid, EmployeeID, clsqid);


			// 获取机票信息
			try {
				JSONObject arry = new JSONObject(result);
				JSONObject jsonval = (JSONObject) arry.get("Status");
				String success = Util.null2String(jsonval.get("Success").toString());
				if ("true".equals(success)) {
					JSONArray itineraryList = arry.getJSONArray("ItineraryList");
					for (int orderIndex = 0; orderIndex < itineraryList.length(); orderIndex++) {
						JSONObject Itinerary = (JSONObject) itineraryList.get(orderIndex);
						//	String JourneyNO = Util.null2String(Itinerary.get("JourneyNO"));
						if (Itinerary.length() > 0) {
							//机票信息
							JSONArray flightOrderInfoList = null;
							if (!Itinerary.get("FlightOrderInfoList").toString().equals("null")) {
								flightOrderInfoList = Itinerary.getJSONArray("FlightOrderInfoList");
								if (flightOrderInfoList != null && flightOrderInfoList.length() > 0) {
									//		int flightindex = 0;//机票序号
									for (int findex = 0; findex < flightOrderInfoList.length(); findex++) {
										JSONObject flightOrderInfo = flightOrderInfoList.getJSONObject(findex);
										JSONObject basicInfo = (JSONObject) flightOrderInfo.get("BasicInfo");
										String OrderStatus = basicInfo.getString("OrderStatus");
										String CorpPayType = basicInfo.getString("CorpPayType");
										String PrepayType = basicInfo.getString("PrepayType");
										if("已取消".equals(OrderStatus)|| "全部退票".equals(OrderStatus)||!"因公".equals(CorpPayType)||!"ACCNT".equals(PrepayType)){
											continue;
										}
										//		String flight_journey_no = Util.null2String(basicInfo.getString("JourneyID"));

										//保险总价
										double InsuranceFee = basicInfo.getDouble("InsuranceFee");//保险的总价
										//String FlightWay = basicInfo.getString("FlightWay");//单程、往返

										double InsuranceFee_Avg = 0;//保险单价

										//航班信息
										JSONArray flights = flightOrderInfo.getJSONArray("FlightInfo");
										//乘客信息
										JSONArray passengers = flightOrderInfo.getJSONArray("PassengerInfo");
										if (flights.length() > 0 && passengers.length() > 0) {//机票总数
											InsuranceFee_Avg = InsuranceFee / (flights.length() * passengers.length());//保险总价除以机票数量

											for (int i = 0; i < flights.length(); i++) {
												//	flightindex++;
												JSONObject flight = flights.getJSONObject(i);
												//出发
												String takeoffTime = flight.get("TakeoffTime").toString();
												//到达
												String arrivalTime = flight.get("ArrivalTime").toString();

												String airBDate = takeoffTime.substring(0, 10);
												String airBTime = takeoffTime.substring(11, 16);
												String airEDate = arrivalTime.substring(0, 10);
												String airETime = arrivalTime.substring(11, 16);

												double ServerFee = flight.getDouble("ServerFee");//服务费

												double Amount = flight.getDouble("Amount");//机票价，机票价格+燃油费+税费
												double totalAmount = Amount + ServerFee + InsuranceFee_Avg;//机票价格+服务费+保险费

												for (int j = 0; j < passengers.length(); j++) {
													//    JSONObject passenger = passengers.getJSONObject(j);
													//    JSONObject passengerInfo = passenger.getJSONObject("PassengerBasic");
													//    String passengerName = Util.null2String(passengerInfo.getString("PassengerName"));

													String airDepCityName = flight.get("DCityName").toString(); // 出发城市
													String airDepCity = getCityKey(airDepCityName);
													String airDestCityName = flight.get("ACityName").toString(); // 到达城市
													String airDestCity = getCityKey(airDestCityName);

													String airBEGDA = airBDate;// 出发日期
													String airBEGTI = airBTime;// 出发时间
													String airENDDA = airEDate;// 到达日期
													String airENDTI = airETime;// 到达时间
													String airLine = flight.get("AirLineName").toString(); // 航空公司
													String airNum = flight.get("Flight").toString(); // 航班号
													String airCOST = String.format("%.2f", totalAmount); // 总金额
													//	    String airCURR = flight.get("Currency").toString(); // 币种

													// 插入表中
													String sql = "insert into " + tableName + "_dt5(mainid,airDepCity,airDestCity,airBEGDA,airBEGTI,airENDDA,airENDTI,airLine,airNum,airCOST)"
															+ " values(" + mainid + ",'" + airDepCity + "','" + airDestCity + "','" + airBEGDA + "','" + airBEGTI + "','" + airENDDA + "','"
															+ airENDTI + "','" + airLine + "','" + airNum + "','" + airCOST + "')";
													rs.execute(sql);
												}
											}

											for (int j = 0; j < passengers.length(); j++) {
												JSONObject passenger = passengers.getJSONObject(j);
												//JSONObject passengerInfo = passenger.getJSONObject("PassengerBasic");
												//	String passengerName = Util.null2String(passengerInfo.getString("PassengerName"));
												if (passenger.has("SequenceInfo") && !"null".equals(passenger.getString("SequenceInfo"))) {
													JSONArray sequenceInfo = passenger.getJSONArray("SequenceInfo");
													if (sequenceInfo == null) {
														continue;
													}
													for (int k = 0; k < sequenceInfo.length(); k++) {
														JSONObject sequenceInfoEntity = sequenceInfo.getJSONObject(k);
														if (sequenceInfoEntity.has("ChangeInfo") && !"null".equals(sequenceInfoEntity.getString("ChangeInfo"))) {
															JSONArray changeInfo = sequenceInfoEntity.getJSONArray("ChangeInfo");
															if (changeInfo == null) {
																continue;
															}
															for (int m = 0; m < changeInfo.length(); m++) {
																JSONObject changeInfoEntity = changeInfo.getJSONObject(m);
																//		flightindex++;
																double cCFee = changeInfoEntity.getDouble("CFee");
																double cRebookServiceFee = changeInfoEntity.getDouble("RebookServiceFee");
																double cSendTicketFee = changeInfoEntity.getDouble("SendTicketFee");
																double ctotalAmount = cCFee + cRebookServiceFee + cSendTicketFee;

																String airDepCityName = changeInfoEntity.get("CDCityName").toString(); // 出发城市
																String airDepCity = getCityKey(airDepCityName);
																String airDestCityName = changeInfoEntity.get("CACityName").toString(); // 到达城市
																String airDestCity = getCityKey(airDestCityName);

																String cTakeOffTime = changeInfoEntity.get("CTakeOffTime").toString();
																String CArrivalTime = changeInfoEntity.get("CArrivalTime").toString();

																String airBDate = cTakeOffTime.substring(0, 10);
																String airBTime = cTakeOffTime.substring(11, 16);
																String airEDate = CArrivalTime.substring(0, 10);
																String airETime = CArrivalTime.substring(11, 16);

																String airBEGDA = airBDate;// 出发日期
																String airBEGTI = airBTime;// 出发时间
																String airENDDA = airEDate;// 到达日期
																String airENDTI = airETime;// 到达时间
																String airLine = changeInfoEntity.get("CAirlineName").toString(); // 航空公司
																String airNum = changeInfoEntity.get("CFlight").toString(); // 航班号
																String airCOST = String.format("%.2f", ctotalAmount); // 总金额
																//	    String airCURR = changeInfoEntity.get("Currency").toString(); // 币种

																// 插入表中
																String sql = "insert into " + tableName + "_dt5(mainid,airDepCity,airDestCity,airBEGDA,airBEGTI,airENDDA,airENDTI,airLine,airNum,airCOST)"
																		+ " values(" + mainid + ",'" + airDepCity + "','" + airDestCity + "','" + airBEGDA + "','" + airBEGTI + "','" + airENDDA + "','"
																		+ airENDTI + "','" + airLine + "','" + airNum + "','" + airCOST + "')";
																rs.execute(sql);

															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.writeLog(clsqid + " -> 读取订单信息出错! ");
			}

			
		}
		
		return false;
	}
	
	private String getCityKey(String cityName){
		String ctripCity = "";
		RecordSet rs = new RecordSet();
		rs.execute("select cityname,citykey from CTRIP_HOTELCITY where cityname = '" + cityName + "'");
		if(rs.next()){
			ctripCity = Util.null2String(rs.getString("citykey"));
		}
		return ctripCity;
	}
	
}
