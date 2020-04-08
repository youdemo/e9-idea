package kstjj.doc.send;

public interface IAdapter {

	/**
	 * 发送公文
	 * @param docId 发文ID
	 * @throws Exception
	 */
	public void sendDocuments(String docId) throws Exception;

	/**
	 * 回复办文单
	 * @param docId 回复公文ID
	 * @throws Exception
	 */
	public void sendReplyForDoc(String docId) throws Exception;

	/**
	 * 从外部获取发文信息
	 * @param account
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public void getReceiveDocument() throws Exception;

	/**
	 * 接收部门信息
	 * @throws Exception
	 */
	public void getReceiveUnit() throws Exception;

	/**
	 * 反馈给公文服务已接收公文的RecordId,确定已接收公文
	 * @param pRecordId 服务器上公文的记录ID
	 * @return
	 */
	public boolean incept(String pRecordId);

	/**
	 * 通过发文id删除已经发到服务器上并且未签收的文
	 * @param docId
	 * @throws Exception
	 */
	public void deletePublishDocById(String docId) throws Exception;

	/**
	 * 平台修改办理类公文后重新接受办文单
	 * @throws Exception
	 */
	public void againGetReceiveDocument() throws Exception;

	/**
	 *  重新回复
	 * @throws Exception
	 */
	public void renewReplyList() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120913
	 * 描述：接收政府OA的重点工作
	 * @throws Exception
	 */
	public void ZDGZDownLoadIds() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120921
	 * 描述：接收政府OA的重点工作类别
	 * @throws Exception
	 */
	public void ZDGZGetWORKTYPE() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120913
	 * 描述：根据政府OA删除已删除的重点工作
	 * @throws Exception
	 */
	public void ZDGZDownLoadDelIds() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：从市政府OA下载建议提案信息
	 * @throws Exception
	 */
	public void JYTADownLoadEnrolInfo() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：从市政府OA下载届次信息
	 * @throws Exception
	 */
	public void JYTADownLoadJIECIInfo() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：从市政府OA下载会议信息
	 * @throws Exception
	 */
	public void JYTADownLoadMEETINGInfo() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：从市政府OA下载界别信息
	 * @throws Exception
	 */
	public void JYTADownLoadJIEBIEInfo() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：从市政府OA下载分类信息
	 * @throws Exception
	 */
	public void JYTADownLoadKINDInfo() throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20120915
	 * 描述：回复办理信息到政府OA平台
	 * @throws Exception
	 */
	public void JYTASendEnrolListToOA(String docId) throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20121012
	 * 描述：回复接收信息到政府OA平台
	 * @throws Exception
	 */
	public void JYTASendEnrolListReceiveToOA(String docId) throws Exception;

	/**
	 * 创建人：蔡日胜
	 * 创建时间：20121012
	 * 描述：接收登记表子表信息
	 * @throws Exception
	 */
	public void JYTAEnrolList(int intEnrolID) throws Exception;
}
