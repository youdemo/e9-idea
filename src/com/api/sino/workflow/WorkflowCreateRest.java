package com.api.sino.workflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import sino.util.TransUtil;
import sino.workflow.CreatePurchaseOrder;
import sino.workflow.CreatePurchaseOrderModify;
import sino.workflow.MaterialPayment;
import sino.workflow.MaterialPurchaseWFCreate;

@Path("/sino")
public class WorkflowCreateRest {
	
	@POST
	@Path("/purchaseRequest/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String PDALoginVerify(@Context HttpServletRequest paramHttpServletRequest, @Context HttpServletResponse paramHttpServletResponse) throws JSONException{
		MaterialPurchaseWFCreate mtc = new MaterialPurchaseWFCreate();
		String info = paramHttpServletRequest.getParameter("info");
		String result = "";
		try {
			result = mtc.doCreate(info);
		} catch (Exception e) {
			new TransUtil().writeLog("WorkflowCreateRest", e);
			return "{\"MSG_TYPE\":\"E\",\"MSG_CONTENT\":\"参数解析异常\",\"OA_ID\":\"0\"}";
		}
		return result;
	}
	
	/**
	 * 采购订单
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/purchaseOrder/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createPR(@Context HttpServletRequest paramHttpServletRequest, @Context HttpServletResponse paramHttpServletResponse) throws JSONException{
		CreatePurchaseOrder cpo = new CreatePurchaseOrder();
		String info = paramHttpServletRequest.getParameter("info");
		String result = "";
		try {
			result = cpo.createPR(info);
		} catch (Exception e) {
			new TransUtil().writeLog("WorkflowCreateRest", e);
			return "{\"MSG_TYPE\":\"E\",\"MSG_CONTENT\":\"参数解析异常\",\"OA_ID\":\"0\"}";
		}
		return result;
	}

	/**
	 * ST-生产物料付款
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/materialPayment/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createSCWL(@Context HttpServletRequest paramHttpServletRequest, @Context HttpServletResponse paramHttpServletResponse) throws JSONException{
		MaterialPayment cpo = new MaterialPayment();
		String info = paramHttpServletRequest.getParameter("info");
		String result = "";
		try {
			result = cpo.createSCWL(info);
		} catch (Exception e) {
			new TransUtil().writeLog("WorkflowCreateRest", e);
			return "{\"MSG_TYPE\":\"E\",\"MSG_CONTENT\":\"参数解析异常\",\"OA_ID\":\"0\"}";
		}
		return result;
	}

	/**
	 * 采购订单变更
	 * @param paramHttpServletRequest
	 * @param paramHttpServletResponse
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/purchaseOrderModify/create")
	@Produces(MediaType.APPLICATION_JSON)
	public String createPRModify(@Context HttpServletRequest paramHttpServletRequest, @Context HttpServletResponse paramHttpServletResponse) throws JSONException{
		CreatePurchaseOrderModify cpo = new CreatePurchaseOrderModify();
		String info = paramHttpServletRequest.getParameter("info");
		String result = "";
		try {
			result = cpo.createPRModify(info);
		} catch (Exception e) {
			new TransUtil().writeLog("WorkflowCreateRest", e);
			return "{\"MSG_TYPE\":\"E\",\"MSG_CONTENT\":\"参数解析异常\",\"OA_ID\":\"0\"}";
		}
		return result;
	}
}
