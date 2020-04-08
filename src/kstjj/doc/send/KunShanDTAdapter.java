package kstjj.doc.send;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.SOAPElement;

import com.api.integration.Base;
import jxl.read.biff.Record;
import kstjj.doc.send.AES4DotNet;
import kstjj.doc.send.ECDocument;
import kstjj.doc.send.RSA4DotNet;
import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.istack.ByteArrayDataSource;

import net.vitale.filippo.axis.handlers.WsseClientHandler;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;


/**

 */
public class KunShanDTAdapter {
    private static final String RES_XML_STRING = "response xml string";
    private static final String RES_MESSAGE = "response message";
    private static final String ATTA_CHARSET = "UnicodeLittleUnmarked";
    private static final String NAMESPACE = "http://tempuri.org/";
     RSA4DotNet rsa; // 支持.net的RSA加密类

    private  String username;   // 昆山公文交换WSE验证用户名
    private  String password;   // 昆山公文交换WSE验证密码
    private static String serviceUrl="http://oa.ks.gov.cn/oaws/oaws.asmx"; // Web服务URL
    private  String sendUnitCode; // 发文单位编码
    private  String sendUnitName; // 发文单位名称
    private  String receviceUserCode;//接收用户账号""
    private  String sendUnitGUID;//接收建议提案部门GUID

    private static Logger log = Logger.getLogger(KunShanDTAdapter.class.getName());

    private  String mediaCode;
    // 记录发送日志
    private  List<String> msgl = new ArrayList<String>();
    private  File[] docFiles = new File[1]; // 正文
    private  File[] attFiles = new File[1]; // 附件

    private  Document resDoc;

    public KunShanDTAdapter() throws NoSuchAlgorithmException {
        rsa = new RSA4DotNet();
    }
    public  List<String> getSelectOrg(String username,String password) {
		setUsername(username);
		setPassword(password);
		List<String> list = new ArrayList<String>();
		try {
			AttachmentPart pubKeyAttachment = createPublicKeyAttachment();
			List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
			attachments.add(pubKeyAttachment);
			Map<String, Object> rspMap = callWebService("SelectOrg",attachments,null);
			 String rspxml = (String) rspMap.get(RES_XML_STRING);
	         String sta = getReturnInfo(rspxml);
	 		if(!"Success".equals(sta)) {
	 			//System.err.println("接收公文列表失败！" + sta);
	 		} else {
	 			Message msg = (Message) rspMap.get(RES_MESSAGE);
	 			Iterator<AttachmentPart> attaIter = msg.getAttachments();
	 			byte[] pArrKey = null;
	 			byte[] pArrIV =  null;
	 			String pContent = null;
	 			String atid = null;
	 			int size = 0;
	 			byte[] byteKey = null;
	 			while(attaIter.hasNext()) {
	 				AttachmentPart attachment = (AttachmentPart) attaIter.next();
	 				DataHandler dh = attachment.getDataHandler();
	 				size = attachment.getSize();
					new BaseBean().writeLog("test：atid"+size);
	 				atid = attachment.getContentId();
	 				byteKey = new byte[size];
	 				if("Key.xml".equals(atid)) {
	 					dh.getDataSource().getInputStream().read(byteKey, 0, size);
	 					pArrKey = rsa.Decrypt(byteKey);
	 				} else if("IV.xml".equals(atid)) {
	 					dh.getDataSource().getInputStream().read(byteKey, 0, size);
	 					pArrIV = rsa.Decrypt(byteKey);
	 				} else if("Archives.xml".equals(atid)) {
	 					dh.getDataSource().getInputStream().read(byteKey, 0, size);
	 					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
	 					pContent = new String(newbyte, ATTA_CHARSET);
	 				}
	 			}
	 			if(pContent != null) {
	 				resDoc = DocumentHelper.parseText(pContent);
	 				List nodes = resDoc.selectNodes("//PantheonData/PantheonDataBody/DATA/UserArea/DeptGuid");

	 				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
	 					Node node = (Node) iterator.next();
	 					String DeptGuidText = node.getText();
						list.add(DeptGuidText);
	 					//if(saveArchive(strRecordId)) {
	 					//	incept(strRecordId);
	 					//}
	 					//saveArchive(strRecordId);
	 				}
	 			}
	 		}
		} catch (Exception e) {
			new BaseBean().writeLog("test：",e);
			e.printStackTrace();
		}
         //System.out.println("rspMap:" + rspMap);
        return list;
    }
    public   List<String> getReceiveDocument(String username,String password) throws Exception {
		//log.debug("******** 执行接收公文开始 ********");
        List<String> resultList = new ArrayList<String>();
        setUsername(username);
        setPassword(password);
		AttachmentPart pubKeyAttachment = createPublicKeyAttachment();
		List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
		attachments.add(pubKeyAttachment);
		Map<String, Object> rspMap = callWebService("Unreceipted", attachments, null);
		String rspxml = (String) rspMap.get(RES_XML_STRING);
		//System.out.println(rspxml);
        new BaseBean().writeLog("getReceiveDocument rspxml:"+rspxml);
		String sta = getReturnInfo(rspxml);
		if(!"Success".equals(sta)) {
			//System.err.println("接收公文列表失败！" + sta);
		} else {
			Message msg = (Message) rspMap.get(RES_MESSAGE);
			Iterator<AttachmentPart> attaIter = msg.getAttachments();
			byte[] pArrKey = null;
			byte[] pArrIV =  null;
			String pContent = null;
			String atid = null;
			int size = 0;
			byte[] byteKey = null;
			while(attaIter.hasNext()) {
				AttachmentPart attachment = (AttachmentPart) attaIter.next();
				DataHandler dh = attachment.getDataHandler();
				size = attachment.getSize();
				atid = attachment.getContentId();
				byteKey = new byte[size];
				if("Key.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					pArrKey = rsa.Decrypt(byteKey);
				} else if("IV.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					pArrIV = rsa.Decrypt(byteKey);
				} else if("Archives.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					byte[] newbyte =AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
                    new BaseBean().writeLog("getReceiveDocument pContent:"+pContent);
				}
			}
			if(pContent != null) {
				resDoc = DocumentHelper.parseText(pContent);
				List nodes = resDoc.selectNodes("//PantheonData/PantheonDataBody/DATA/UserArea/RecordID");
				Node numNode = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/Count");
				int unreceiptedNum = 0;
				try {
					unreceiptedNum = Integer.parseInt(numNode.getText());
				} catch(Exception e) {
					unreceiptedNum = 0;
				}
				//log.debug("待下载公文 " + unreceiptedNum + " 个");
				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					String strRecordId = node.getText();
					//System.out.println("strRecordId:"+strRecordId);
                    resultList.add(strRecordId);
					//if(saveArchive(strRecordId)) {
					//	incept(strRecordId);
					//}
					//saveArchive(strRecordId);
				}
			}
		}
		return resultList;
		//log.debug("******** 执行接收公文结束 ********");
	}
    public  Map<String,String> getDocInfo(String pRecordId) throws Exception {
		boolean blResult = false;
        Map<String,String> resultMap = new HashMap<String,String>();
		//mediaCode = BaseFunc.getMediaUniqueNumber();//多媒体号
		try {
			AttachmentPart pubKeyAttachment = createPublicKeyAttachment(pRecordId);
			List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
			attachments.add(pubKeyAttachment);
			Map<String, Object> rspMap = callWebService("GetArchives", attachments,  null);
			String rspxml = (String) rspMap.get(RES_XML_STRING);
			String sta = getReturnInfo(rspxml);
            new BaseBean().writeLog("getReceiveDocument rspxml:"+rspxml);
			if(!"Success".equals(sta)) {
				//log.error("接收公文列表失败！" + sta);
				return null;
			}
	
			Message msg = (Message) rspMap.get(RES_MESSAGE);
			Iterator<AttachmentPart> attaIter = msg.getAttachments();
			byte[] pArrKey = null;
			byte[] pArrIV =  null;
			int k = 0;
			int n = 0;
			String pContent = null;
			String atid = null;
			int size = 0;
			byte[] byteKey = null;
			String	mTitle = "";
			//String mType = "";
			//String mAttType = "";
			String[] arrTitle = null;
			//String[] arrType = null;
			//String[] arrAttType = null;
            String zw = "";
            String fj = "";
			while(attaIter.hasNext()) {
				AttachmentPart attachment = (AttachmentPart) attaIter.next();
				DataHandler dh = attachment.getDataHandler();
				size = attachment.getSize();
				atid = attachment.getContentId();
				byteKey = new byte[size];
				if("Key.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					pArrKey = rsa.Decrypt(byteKey);
				} else if("IV.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					pArrIV = rsa.Decrypt(byteKey);
				} else if("Archives.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					byte[] newbyte =AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
                    new BaseBean().writeLog("getReceiveDocument pContent:"+pContent);
					//************ 存入公文内容到数据库 ***************
					resultMap = getDocMap(pContent);

				} else if("Affix.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					byte[] newbyte =AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
					resDoc = DocumentHelper.parseText(pContent);
					mTitle = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/FileName").getText();
					//System.out.println(mTitle);
					//mType = doc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ContentType").getText();
					//mAttType = doc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/AttType").getText();
					arrTitle = mTitle.split(";");
					//arrType = mType.split(";");
					//arrAttType = mAttType.split(";");
				} else { // 保存附件到多媒体目录
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);

                    String fileid=getDocId(arrTitle[k],newbyte);
                    if("正文.doc".equals(arrTitle[k])){
                        zw = fileid;
                    }else{
                        if("".equals(fj)){
                            fj = fileid;
                        }else{
                            if(!"".equals(fileid)){
                                fj = fj + ","+fileid;
                            }
                        }
                    }
					k++;
				}
			}
            resultMap.put("zw",zw);
            resultMap.put("fj",fj);
		} catch (Exception e) {
			new BaseBean().writeLog("KunShanDTAdapter 收文异常",e);
            resultMap = null;
		}
		return resultMap;
	}
    private String getDocId(String name, byte[]  value) throws Exception {
        String docId = "";
        String createrid = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "swryid"));
        String seccategory = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "docsec"));
        DocInfo di= new DocInfo();
        di.setMaincategory(0);
        di.setSubcategory(0);
        di.setSeccategory(Integer.valueOf(seccategory));
        di.setDocSubject(name.substring(0, name.lastIndexOf(".")));
        DocAttachment doca = new DocAttachment();
        doca.setFilename(name);
        String encode=Base64.encode(value);
        doca.setFilecontent(encode);
        DocAttachment[] docs= new DocAttachment[1];
        docs[0]=doca;
        di.setAttachments(docs);
        String departmentId="-1";
        String sql="select departmentid from hrmresource where id='"+createrid+"'";
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        User user = new User();
        if(rs.next()){
            departmentId = Util.null2String(rs.getString("departmentid"));
        }
        user.setUid(Integer.parseInt(createrid));
        user.setUserDepartment(Integer.parseInt(departmentId));
        user.setLanguage(7);
        user.setLogintype("1");
        user.setLoginip("127.0.0.1");
        DocServiceImpl ds = new DocServiceImpl();
        try {
            docId=String.valueOf(ds.createDocByUser(di, user));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return docId;
    }
    private  Map<String,String> getDocMap(String pContent) throws Exception {
        Map<String,String> resultMap = new HashMap<String,String>();
		resDoc = DocumentHelper.parseText(pContent);
		String docTitle = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文标题").getText();
		String docCode = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/发文文号").getText();
		String docSubject = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/主题词").getText();
		String docDispatchTime = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/发文时间").getText();
		String docSecretName = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文密级").getText();
		String docUrgent = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文紧急程度").getText();
		String docSendDeptName = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/发文部门").getText();
		String docMainSend = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/主送信息").getText();
		String docCopySend = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/抄送信息").getText();
		String docTypeName = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文类别信息").getText();
		String docId = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文编号").getText();
		String isHaveForm = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/IsHaveForm").getText();//是否是办文单
		String docFormType = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/DocFormType").getText();//办文单类型
		String filerecordid = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/filerecordid").getText();
		String limitDate = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/LimitDate").getText();//期限天数
		String revertDate = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/RevertDate").getText();//限定回复截止时间
		String centerOADispatchlistID = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/CenterOADispatchlistID").getText();//平台Dispatchlist.ID
        String gwfl = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文分类").getText();
        String email = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/email").getText();
        resultMap.put("docTitle",docTitle);
        resultMap.put("docCode",docCode);
        resultMap.put("docSubject",docSubject);
        resultMap.put("docDispatchTime",docDispatchTime);
        resultMap.put("docSecretName",docSecretName);
        resultMap.put("docUrgent",docUrgent);
        resultMap.put("docSendDeptName",docSendDeptName);
        resultMap.put("docMainSend",docMainSend);
        resultMap.put("docCopySend",docCopySend);
        resultMap.put("docTypeName",docTypeName);
        resultMap.put("docId",docId);
        resultMap.put("isHaveForm",isHaveForm);
        resultMap.put("docFormType",docFormType);
        resultMap.put("filerecordid",filerecordid);
        resultMap.put("limitDate",limitDate);
        resultMap.put("revertDate",revertDate);
        resultMap.put("centerOADispatchlistID",centerOADispatchlistID);
        resultMap.put("email",email);
        resultMap.put("gwfl",gwfl);

		return resultMap;
	}
    /**
     * 反馈给公文服务已接收公文的RecordId,确定已接收公文
     *
     * @param pRecordId 服务器上公文的记录ID
     * @return
     */
    public boolean incept(String pRecordId) {
        BaseBean log = new BaseBean();
        boolean b = false;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("RecordID", pRecordId);
            Map<String, Object> rspMap = callWebService("InceptArchive", paramMap);
            String rspxml = (String) rspMap.get(RES_XML_STRING);
            String sta = getReturnInfo(rspxml);
            if ("Success".equals(sta)) {
                b = true;
                log.writeLog("确认收文成功");
            } else {
                b = false;
                log.writeLog("确认收文出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
            log.writeLog(e);
        }
        return b;
    }

    /**
     * 发送公文
     */
    public void sendDocuments(String docId) throws Exception {
        boolean boo = true;
        log.debug("******** 执行发送公文开始 ********");
        msgl.clear();
        log.debug("******** 执行发送公文结束 ********");
    }

    // 发送单个公文
    public  Map<String, String> SendDocumentOne(Map<String,String> map, String username, String password) {
        boolean boo = true;
        Map<String,String> resultMap = new HashMap<String,String>();
        setUsername(username);
        setPassword(password);
        try {
            byte[] pArrKey =AES4DotNet.getKeyBytes();
            byte[] pArrIV =AES4DotNet.getIV();
            // 下载平台公钥
            String downPublicKey = downPublicKey();
            // DocumentVO document = exchangeDao.getDocumentInfo(docId);
            // 是用平台公钥把AES的key和iv进行加密
            byte[] parrEncryKey = rsa.Encrypt(pArrKey, downPublicKey);
            byte[] parrEncryIV = rsa.Encrypt(pArrIV, downPublicKey);
            // 把公文组装成XML

            String original = getSendRequestString(map);

            byte[] parrEncryArchive = original.getBytes(ATTA_CHARSET);
            byte[] encryptArchive =AES4DotNet.Encrypt(parrEncryArchive);

            List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
            DataSource aeskeyDs = new ByteArrayDataSource(parrEncryKey, "text/xml");
            DataHandler aeskeyDh = new DataHandler(aeskeyDs);
            AttachmentPart aeskeyAtt = new AttachmentPart(aeskeyDh);
            aeskeyAtt.setContentId("Key.xml");
            attachments.add(aeskeyAtt);

            DataSource aesivDs = new ByteArrayDataSource(parrEncryIV, "text/xml");
            DataHandler aesivDh = new DataHandler(aesivDs);
            AttachmentPart aesivAtt = new AttachmentPart(aesivDh);
            aesivAtt.setContentId("IV.xml");
            attachments.add(aesivAtt);

            DataSource archivesDs = new ByteArrayDataSource(encryptArchive, "text/xml");
            DataHandler archivesDh = new DataHandler(archivesDs);
            AttachmentPart archivesAtt = new AttachmentPart(archivesDh);
            archivesAtt.setContentId("Archives.xml");
            attachments.add(archivesAtt);
            String zw = map.get("zw");
            String fj = map.get("fj");
            // 处理正文和附件信息XML
            String affixstring = affixString(fj);
            byte[] parrAffixstring = affixstring.getBytes(ATTA_CHARSET);
            byte[] encryptAffix =AES4DotNet.Encrypt(parrAffixstring);
            DataSource affixDs = new ByteArrayDataSource(encryptAffix, "text/xml");
            DataHandler affixDh = new DataHandler(affixDs);
            AttachmentPart affixAtt = new AttachmentPart(affixDh);
            affixAtt.setContentId("Affix.xml");
            attachments.add(affixAtt);

            // 处理正文

            if(!"".equals(zw)){
                AttachmentPart park=getAttachmentPark(zw,"0");
                if(park != null){
                    attachments.add(park);
                }

            }
            // 处理附件流
            String fjids[]=fj.split(",");
            for(String fjid: fjids){
                if(!"".equals(fjid)){
                    AttachmentPart park=getAttachmentPark(fjid,"1");
                    if(park != null){
                        attachments.add(park);
                    }
                }
            }
//
            Map<String, Object> rspMap = callWebService("SendArchives", attachments, null);
            //String rinfo = getReturnInfo((String) rspMap.get(RES_XML_STRING));
            resDoc = DocumentHelper.parseText((String) rspMap.get(RES_XML_STRING));
            Node status = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/State");
            Node desc = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/Description");
            Node fRecordId = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/FileRecordID");
            resultMap.put("status",status.getText());//Success
            resultMap.put("desc",desc.getText());
            resultMap.put("fRecordId",fRecordId.getText());
            // updateDocStatus(queueId, status.getText(), desc.getText(), fRecordId.getText());

        } catch (Exception e) {
            new BaseBean().writeLog("KunShanDTAdapter 推送公文失败："+e);
            resultMap.put("status","E");//Success
            resultMap.put("desc","推送公文失败");
            resultMap.put("fRecordId","111");
        }
        return resultMap;
    }

    public AttachmentPart getAttachmentPark(String docid,String type){
        RecordSet rs = new RecordSet();
        AttachmentPart fileAtt = null;
        String filerealpath = "";
        String iszip = "";
        String imagefilename = "";
        String sql = "select b.filerealpath,b.iszip,b.imagefilename,b.imagefileid from  imagefile b  where b.imagefileid in(select max(imagefileid) from docimagefile where docid in("+docid+") group by docid)";
        rs.execute(sql);
        if(rs.next()){
            filerealpath = Util.null2String(rs.getString("filerealpath"));
            iszip = Util.null2String(rs.getString("iszip"));
            imagefilename = Util.null2String(rs.getString("imagefilename"));

        }
        if(imagefilename.lastIndexOf(".") < 0){
            return null;
        }
        if(filerealpath.length()>0){
            try {
                InputStream fis = getFile(filerealpath, iszip);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = fis.read(buffer)) >= 0) {
                    baos.write(buffer, 0, count);
                }
                byte[] fileEncrypt = AES4DotNet.Encrypt(baos.toByteArray());
                DataSource fileDs = new ByteArrayDataSource(fileEncrypt, "text/xml");
                DataHandler fileDh = new DataHandler(fileDs);
                fileAtt = new AttachmentPart(fileDh);
                if("0".equals(type)){
                    fileAtt.setContentId("正文.doc");
                }else{
                    fileAtt.setContentId(imagefilename);
                }
                fis.close();
                baos.close();

            }catch (Exception e){
                new BaseBean().writeLog("KunShanDTAdapter 获取流失败 docid:"+docid,e);
            }
        }
        return fileAtt;
    }

    /**
     * 获取附件流
     * @param filerealpath
     * @param iszip
     * @return
     * @throws Exception
     */
    private InputStream getFile(String filerealpath, String iszip)
            throws Exception {
        ZipInputStream zin = null;
        InputStream imagefile = null;
        File thefile = new File(filerealpath);
        if (iszip.equals("1")) {
            zin = new ZipInputStream(new FileInputStream(thefile));
            if (zin.getNextEntry() != null)
                imagefile = new BufferedInputStream(zin);
        } else {
            imagefile = new BufferedInputStream(new FileInputStream(thefile));
        }
        return imagefile;
    }
    // 把公文组装成发送内容XML
    private  String getSendRequestString(Map<String,String> map) {
        StringBuffer sbf = new StringBuffer("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
        sbf.append("<PantheonData><PantheonDataHeader></PantheonDataHeader><PantheonDataBody><DATA><UserArea>");
        sbf.append("<公文标题>").append(map.get("title")).append("</公文标题>");
        sbf.append("<发文文号>").append(map.get("code")).append("</发文文号>");
        sbf.append("<主题词>").append("").append("</主题词>");
        sbf.append("<发文部门内码>").append(map.get("sendUnitCode")).append("</发文部门内码>");
        sbf.append("<发文部门>").append(map.get("sendUnitName")).append("</发文部门>");
        sbf.append("<发文时间>").append(map.get("fwsj")).append("</发文时间>");
        sbf.append("<公文紧急程度>").append("").append("</公文紧急程度>");
        sbf.append("<主送信息>").append("").append("</主送信息>");
        sbf.append("<收文部门>").append(map.get("swbm")).append(";</收文部门>");
        sbf.append("<收文部门名称>").append(map.get("swbmmc")).append("</收文部门名称>");
        sbf.append("<公文类别信息>").append(map.get("gwlbxx")).append("</公文类别信息>");
        sbf.append("<抄送信息>").append("").append("</抄送信息>");
        sbf.append("<公文密级>").append("").append("</公文密级>");
        sbf.append("<公文分类>").append(map.get("gwfl")).append("</公文分类>"); //1 普通文件 2 会议通知 3 信息简报
        sbf.append("</UserArea></DATA></PantheonDataBody></PantheonData>");
        new BaseBean().writeLog("KunShanDTAdapter","getSendRequestString:"+sbf.toString());
        return sbf.toString();
    }

    // 从数据库中取出 公文和附件 的文件名和文件类型信息,并组织成XML
    private  String affixString(String fj) {
        RecordSet rs = new RecordSet();
        String filename = "正文.doc";
        String attType = "false";
        String contentType = "application/msword";
        String fjids[] = fj.split(",");
        String sql = "";
        String imagefilename = "";
        for(String fjid:fjids){
            if(!"".equals(fjid)){
                sql = "select b.imagefilename from  imagefile b  where b.imagefileid in(select max(imagefileid) from docimagefile where docid in("+fjid+") group by docid)";
                rs.execute(sql);
                if(rs.next()){
                    imagefilename = Util.null2String(rs.getString("imagefilename"));
                    if(imagefilename.lastIndexOf(".")<0){
                        continue;
                    }
                    filename =filename+";"+imagefilename;
                    attType = attType+";"+"true";
                    contentType = contentType+";"+getContentType(imagefilename);
                }

            }
        }
//
        String sXMLArchive = "<?xml version=\"1.0\" encoding=\"gb2312\"?><PantheonData><PantheonDataHeader></PantheonDataHeader>" +
                "<PantheonDataBody><DATA><UserArea>" +
                "<FileName>" + filename + "</FileName>" +
                "<AttType>" + attType + "</AttType>" +
                "<ContentType>" + contentType + "</ContentType>" +
                "</UserArea></DATA></PantheonDataBody></PantheonData>";
        return sXMLArchive;
    }

     private String getContentType(String docname){
        RecordSet rs = new RecordSet();
        String minetype = "";
        String type = docname.substring(docname.lastIndexOf("."),docname.length());
        String sql = "select minetype from uf_doc_mine where type='"+type+"'";
        rs.execute(sql);
        if(rs.next()){
            minetype = Util.null2String(rs.getString("minetype"));

        }
        if("".equals(minetype)){
            minetype = "application/octet-stream";
        }
        return minetype;

     }
//    public  void main(String[] args) throws Exception {
//    	 rsa = new RSA4DotNet();
//    	setUsername("yjqx_a_bsfy");
//    	setSendUnitCode("c477bfb6-595c-41cd-96f7-8f98a6488fdb");
//    	//setPassword("9EAEFB8079221E4E0C8C6F0248DA94FC");
//    	setPassword("yjq000331");
//    	//boolean result1=SendDocumentOne("123");
//    	//getReceiveDocument();
//    	//System.out.println("aaa"+result1);
//    	getSelectOrg();
//    	 // Map<String, Object> rspMap = callWebService("SendArchives", null, null);
//           //String rinfo = getReturnInfo((String) rspMap.get(RES_XML_STRING));
//           //resDoc = DocumentHelper.parseText((String) rspMap.get(RES_XML_STRING));
//          // System.out.println(resDoc.asXML());
//	}
    // 下载平台公钥
    public  String downPublicKey() {
        String publicKey = null;
        try {
            Map<String, Object> rspMap = callWebService("DownPublicKey");
            //System.out.println("rspMap:" + rspMap);
            String rspxml = (String) rspMap.get(RES_XML_STRING);
            //System.out.println("rspxml:" + rspxml);
            resDoc = DocumentHelper.parseText(rspxml);
            Node node = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/PublicKey");
            if (node != null)
                publicKey = node.asXML();
        } catch (Exception e) {
            new BaseBean().writeLog("downPublicKey 下载平台公钥失败",e);
            e.printStackTrace();
        }
        return publicKey;
    }

    // 调用web服务，不带附件和参数
    private  Map<String, Object> callWebService(String operation) throws Exception {
        return callWebService(operation, null);
    }

    // 调用web服务，带参数
    private  Map<String, Object> callWebService(String operation, Map<String, Object> params) throws Exception {
        return callWebService(operation, null, params);
    }

    // 调用web服务，带附件和参数
    private  Map<String, Object> callWebService(String operation, List<AttachmentPart> attachments, Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        Call call = null;
        try {
            if (username == null || password == null) {
               // System.out.println("WSE验证配置有误，用户名或密码不能为空。");
            } else {
                Service service = new Service();
                call = (Call) service.createCall();

                call.setTargetEndpointAddress(new java.net.URL(serviceUrl));
                call.setOperation(operation);
                call.setOperationName(new QName(NAMESPACE, operation));
                call.setSOAPActionURI(NAMESPACE + operation);
                call.setUseSOAPAction(true);

                ////////// 处理用户名密码 //////////
                call.setUsername(getUsername());
                call.setPassword(getPassword());
               // call.addHeader(setSoapHeader());
                call.addHeader(new SOAPHeaderElement("Authorization","username",getUsername()));
                call.addHeader(new SOAPHeaderElement("Authorization","password",getPassword()));
                call.setProperty(WsseClientHandler.PASSWORD_OPTION, WsseClientHandler.PASSWORD_DIGEST_WITH_NONCE);
                call.setClientHandlers(new WsseClientHandler(), null);
                if (attachments != null && !attachments.isEmpty()) {
                    call.setProperty(call.ATTACHMENT_ENCAPSULATION_FORMAT, call.ATTACHMENT_ENCAPSULATION_FORMAT_DIME); // 是用DIME附件和.net服务端对应
                    for (Iterator iterator = attachments.iterator(); iterator
                            .hasNext(); ) {
                        AttachmentPart attachment = (AttachmentPart) iterator
                                .next();
                        call.addAttachmentPart(attachment);
                    }
                }

                ////////// 处理参数 //////////
                Object[] param = new Object[]{};
                if (params != null) {
                    param = new Object[params.keySet().size()];
                    int i = 0;
                    for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext(); ) {
                        String key = iter.next();
                        Object val = params.get(key);
                        call.addParameter(new QName(NAMESPACE, key), XMLType.XSD_STRING, ParameterMode.IN);
                        param[i] = val;
                        i++;
                    }
                    call.setReturnType(XMLType.XSD_STRING);
                }

                ////////// 调用服务 //////////
                String rsp = (String) call.invoke(param);
//                MessageContext msgContext = call.getMessageContext();
//                Message reqMsg = msgContext.getRequestMessage();
//                System.out.println("reqaaa:"+reqMsg.getSOAPPartAsString());
                //System.out.println("rsp:" + rsp);

                Message message = call.getResponseMessage();

                map.put(RES_XML_STRING, rsp);
                map.put(RES_MESSAGE, message);
            }
        } catch (AxisFault e) {
      
        	new BaseBean().writeLog(e);

        } catch (Exception e) {
            new BaseBean().writeLog(e);

            e.printStackTrace();
        }
        return map;
    }
    
    
    private  AttachmentPart createPublicKeyAttachment() throws UnsupportedEncodingException {
        return createPublicKeyAttachment(null);
    }

    // 创建公钥附件Archives.xml
    private  AttachmentPart createPublicKeyAttachment(String pRecordId)
            throws UnsupportedEncodingException {
        String pubKeyStr = rsa.getPubKeyXMLString(); // 获取.net支持的公钥XML字符串

        // 组装成符合昆山定的规范XML
        StringBuffer sbf = new StringBuffer();
        sbf.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
        sbf.append("<PantheonData><PantheonDataHeader>");
        sbf.append("</PantheonDataHeader><PantheonDataBody>");
        sbf.append("<DATA><UserArea><ReturnInfo>");
        //// pubKeyStr ////
        sbf.append("<PublicKey>");
        sbf.append(pubKeyStr);
        sbf.append("</PublicKey>");
        //// pubKeyStr ////
        if (pRecordId != null) {
            sbf.append("<RecordID>");
            sbf.append(pRecordId);
            sbf.append("</RecordID>");
        }
        sbf.append("</ReturnInfo></UserArea></DATA></PantheonDataBody></PantheonData>");
        //  使用LE顺序，无BOM的Unicode编码，和.net兼容
        byte[] archives = sbf.toString().getBytes(ATTA_CHARSET);
        DataSource ds = new ByteArrayDataSource(archives, "text/xml");
        DataHandler dh = new DataHandler(ds);
        AttachmentPart attachment = new AttachmentPart(dh);
        attachment.setContentId("Archives.xml");
        return attachment;
    }


    private  String getReturnInfo(String returnXml) throws DocumentException {
		resDoc = DocumentHelper.parseText(returnXml);
		Node status = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/State");
		Node desc = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/Description");
		if ("Failing".equals(status.getText())) {
			System.err.println("Failing：" + desc.getText());
			return desc.getText();
		} else {
			return "Success";
		}
	}

    /**
     * 创建人：蔡日胜
     * 创建时间：20120830
     * 描述：从数据库中取出 公文和附件 的文件名和文件类型信息,并组织成XML
     *
     * @param queueId
     * @return sXMLArchive
     */
    private String affixStringSendReplyForDocument(String queueId) {
        String filename = "";
        String attType = "";
        String contentType = "";

        String sXMLArchive = "<?xml version=\"1.0\" encoding=\"gb2312\"?><PantheonData><PantheonDataHeader></PantheonDataHeader>" +
                "<PantheonDataBody><DATA><UserArea>" +
                "<FileName>" + filename + "</FileName>" +
                "<AttType>" + attType + "</AttType>" +
                "<ContentType>" + contentType + "</ContentType>" +
                "</UserArea></DATA></PantheonDataBody></PantheonData>";
        return sXMLArchive;
    }


    public  String getUsername() {
        return username;
    }

    public  String getPassword() {
        // 对应.net服务，密码用MD5加密
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public  void setUsername(String username1) {
        username = username1;
    }

    public  void setPassword(String password1) {
        password = password1;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getSendUnitCode() {
        return sendUnitCode;
    }

    public String getSendUnitName() {
        return sendUnitName;
    }

    public  void setSendUnitCode(String sendUnitCode1) {
        sendUnitCode = sendUnitCode1;
    }

    public void setSendUnitName(String sendUnitName) {
        this.sendUnitName = sendUnitName;
    }

    public String getReceviceUserCode() {
        return receviceUserCode;
    }

    public void setReceviceUserCode(String receviceUserCode) {
        this.receviceUserCode = receviceUserCode;
    }

    public String getSendUnitGUID() {
        return sendUnitGUID;
    }

    public void setSendUnitGUID(String sendUnitGUID) {
        this.sendUnitGUID = sendUnitGUID;
    }
}
