package kstjj.doc.send;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.SOAPElement;

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



/**
 * 昆山项目公文交换适配类
 * <p>
 * 项目名称：Fe-Pt5.5DOV
 * 类名称：KunShanDTAdapter
 * 类描述：
 * 创建人：Eddie
 * 创建时间：Jul 16, 2012 3:49:26 PM
 * 修改人：Eddie
 * 修改时间：Jul 16, 2012 3:49:26 PM
 * 修改备注：
 */
public class KunShanDTAdapter2 {
    private static final String RES_XML_STRING = "response xml string";
    private static final String RES_MESSAGE = "response message";
    private static final String ATTA_CHARSET = "UnicodeLittleUnmarked";
    private static final String NAMESPACE = "http://tempuri.org/";
    static RSA4DotNet rsa; // 支持.net的RSA加密类

    private static String username;   // 昆山公文交换WSE验证用户名
    private static String password;   // 昆山公文交换WSE验证密码
    private static String serviceUrl="http://oa.ks.gov.cn/oaws/oaws.asmx"; // Web服务URL
    private static String sendUnitCode; // 发文单位编码
    private static String sendUnitName; // 发文单位名称
    private static String receviceUserCode;//接收用户账号""
    private static String sendUnitGUID;//接收建议提案部门GUID

    private static Logger log = Logger.getLogger(KunShanDTAdapter.class.getName());

    private static String mediaCode;
    // 记录发送日志
    private static List<String> msgl = new ArrayList<String>();
    private static File[] docFiles = new File[1]; // 正文
    private static File[] attFiles = new File[1]; // 附件

    private static Document resDoc;

    public KunShanDTAdapter2() throws NoSuchAlgorithmException {
        rsa = new RSA4DotNet();
    }
    public static void getSelectOrg() {
    	 
		try {
			AttachmentPart pubKeyAttachment = createPublicKeyAttachment();
			List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
			attachments.add(pubKeyAttachment);
			Map<String, Object> rspMap = callWebService("SelectOrg",attachments,null);
			 String rspxml = (String) rspMap.get(RES_XML_STRING);
	         System.out.println(rspxml);
	         String sta = getReturnInfo(rspxml);
	 		if(!"Success".equals(sta)) {
	 			System.err.println("接收公文列表失败！" + sta);
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
	 					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
	 					pContent = new String(newbyte, ATTA_CHARSET);
	 				}
	 			}
	 			System.out.println(pContent);
	 			if(pContent != null) {
	 				resDoc = DocumentHelper.parseText(pContent);
	 				List nodes = resDoc.selectNodes("//PantheonData/PantheonDataBody/DATA/UserArea/DeptGuid");

	 				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
	 					Node node = (Node) iterator.next();
	 					String DeptGuidText = node.getText();
	 					System.out.println("DeptGuidText:"+DeptGuidText);
	 					//if(saveArchive(strRecordId)) {
	 					//	incept(strRecordId);
	 					//}
	 					//saveArchive(strRecordId);
	 				}
	 			}
	 		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //System.out.println("rspMap:" + rspMap);
        
    }
    public static void getReceiveDocument() throws Exception {
		log.debug("******** 执行接收公文开始 ********");
		AttachmentPart pubKeyAttachment = createPublicKeyAttachment();
		List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
		attachments.add(pubKeyAttachment);
		Map<String, Object> rspMap = callWebService("Unreceipted", attachments, null);
		String rspxml = (String) rspMap.get(RES_XML_STRING);
		System.out.println(rspxml);
		String sta = getReturnInfo(rspxml);
		if(!"Success".equals(sta)) {
			System.err.println("接收公文列表失败！" + sta);
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
					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
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
				log.debug("待下载公文 " + unreceiptedNum + " 个");
				for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					String strRecordId = node.getText();
					System.out.println("strRecordId:"+strRecordId);
					//if(saveArchive(strRecordId)) {
					//	incept(strRecordId);
					//}
					saveArchive(strRecordId);
				}
			}
		}
		log.debug("******** 执行接收公文结束 ********");
	}
    private static boolean saveArchive(String pRecordId) throws Exception {
		boolean blResult = false;
		//mediaCode = BaseFunc.getMediaUniqueNumber();//多媒体号
		try {
			AttachmentPart pubKeyAttachment = createPublicKeyAttachment(pRecordId);
			List<AttachmentPart> attachments = new ArrayList<AttachmentPart>();
			attachments.add(pubKeyAttachment);
			Map<String, Object> rspMap = callWebService("GetArchives", attachments,  null);
			String rspxml = (String) rspMap.get(RES_XML_STRING);
			String sta = getReturnInfo(rspxml);
			if(!"Success".equals(sta)) {
				log.error("接收公文列表失败！" + sta);
				return false;
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
					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
					System.out.println(pContent);
					resDoc = DocumentHelper.parseText(pContent);
					String docSecretName = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/公文分类").getText();
					System.out.println(docSecretName);
					//************ 存入公文内容到数据库 ***************
					boolean docExist = docIntoDataBase(pContent, mediaCode);
					if(!docExist) {blResult = false; break;}// 如果存在公文就不用往下执行了
				} else if("Affix.xml".equals(atid)) {
					dh.getDataSource().getInputStream().read(byteKey, 0, size);
					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
					pContent = new String(newbyte, ATTA_CHARSET);
					resDoc = DocumentHelper.parseText(pContent);
					mTitle = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/FileName").getText();
					System.out.println(mTitle);
					String mType = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ContentType").getText();
					String mAttType = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/AttType").getText();
					System.out.println("mType："+mType);
					arrTitle = mTitle.split(";");
					//arrType = mType.split(";");
					//arrAttType = mAttType.split(";");
				} else { // 保存附件到多媒体目录
//					dh.getDataSource().getInputStream().read(byteKey, 0, size);
//					File file = gmu.createMediaFile(mediaCode, arrTitle[k]);
//					byte[] newbyte = AES4DotNet.Decrypt(byteKey, pArrKey, pArrIV);
//					FileUtil.saveFileContext(file, newbyte);
//					k++;
				}
			}
			blResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("下载RecordID为 " + pRecordId + " 的公文执行过程出错：" + ex.getMessage());
			return false;
		}
		return blResult;
	}
 // 把公文存到数据库
    private static boolean docIntoDataBase(String pContent, String mediaCode) throws Exception {
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
		String email = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/email").getText();
		System.out.println("docTitle:"+docTitle);
		// 检查公文是否已经存在
//		if(exchangeDao.documentIsExist(docId)) {
//			log.debug("已存在公文编号： " + docId);
//			return false;
//		}
//		
//		DocumentVO docVO = new DocumentVO();
//		docVO.setTitle(docTitle);
//		docVO.setCode(docCode);
//		docVO.setSubjectWords(docSubject);
//		docVO.setDraftDate(docDispatchTime);
//		docVO.setSecret(docSecretName);
//		docVO.setUrgent(docUrgent);
//		docVO.setDraftDeptName(docSendDeptName);
//		docVO.setMainSend(docMainSend);
//		docVO.setCopySend(docCopySend);
//		docVO.setType(docTypeName);
//		docVO.setAttachmentId(mediaCode);
//		docVO.setId(docId);
//		docVO.setIsHaveForm(isHaveForm);
//		docVO.setDocFormType(docFormType);
//		docVO.setFilerecordid(filerecordid);
//		docVO.setLimitDate(limitDate);
//		docVO.setRevertDate(revertDate);
//		docVO.setCenterOADispatchlistID(centerOADispatchlistID);
//		docVO.setEmail(email);
//		
//		exchangeDao.saveDocumentRecord(docVO);
		return true;
	}
    /**
     * 反馈给公文服务已接收公文的RecordId,确定已接收公文
     *
     * @param pRecordId 服务器上公文的记录ID
     * @return
     */
    public boolean incept(String pRecordId) {
        boolean b = false;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("RecordID", pRecordId);
            Map<String, Object> rspMap = callWebService("InceptArchive", paramMap);
            String rspxml = (String) rspMap.get(RES_XML_STRING);
            String sta = getReturnInfo(rspxml);
            if ("Success".equals(sta)) {
                b = true;
                log.debug("确认收文成功");
            } else {
                b = false;
                log.debug("确认收文出错：" + sta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
            log.error("确认收文执行过程出错：" + e.getMessage());
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
    public static boolean SendDocumentOne(String queueId) {
        boolean boo = true;
        try {
            byte[] pArrKey = AES4DotNet.getKeyBytes();
            byte[] pArrIV = AES4DotNet.getIV();
            // 下载平台公钥
            String downPublicKey = downPublicKey();
            // DocumentVO document = exchangeDao.getDocumentInfo(docId);
            // 是用平台公钥把AES的key和iv进行加密
            byte[] parrEncryKey = rsa.Encrypt(pArrKey, downPublicKey);
            byte[] parrEncryIV = rsa.Encrypt(pArrIV, downPublicKey);
            // 把公文组装成XML
            ECDocument document = new ECDocument();
            document.setCode("test123");
            document.setCopySend("");
            document.setDraftDate("2019-11-26 09:00:00");
            document.setMainSend("");
            document.setSecret("");
            document.setSendUnitName("昆山统计局");
            document.setSubjectWords("");
            document.setTitle("测试公文6");
            document.setType("1");
            document.setUnitCode("c477bfb6-595c-41cd-96f7-8f98a6488fdb");
            document.setUnitName("昆山统计局");
            document.setUrgent("");
            String original = getSendRequestString(document);

            byte[] parrEncryArchive = original.getBytes(ATTA_CHARSET);
            byte[] encryptArchive = AES4DotNet.Encrypt(parrEncryArchive);

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

            // 处理正文和附件信息XML
            String affixstring = affixString();
            byte[] parrAffixstring = affixstring.getBytes(ATTA_CHARSET);
            byte[] encryptAffix = AES4DotNet.Encrypt(parrAffixstring);
            DataSource affixDs = new ByteArrayDataSource(encryptAffix, "text/xml");
            DataHandler affixDh = new DataHandler(affixDs);
            AttachmentPart affixAtt = new AttachmentPart(affixDh);
            affixAtt.setContentId("Affix.xml");
            attachments.add(affixAtt);

            // 处理正文
            for (int i = 0; i < docFiles.length; i++) {
                File file = new File("D:\\test\\正文.doc");
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
    		    byte[] buffer = new byte[1024]; 
    		    int count = 0; 
    		    while((count = fis.read(buffer)) >= 0){ 
    		        baos.write(buffer, 0, count); 
    	        } 
                byte[] fileEncrypt = AES4DotNet.Encrypt(baos.toByteArray());
                DataSource fileDs = new ByteArrayDataSource(fileEncrypt, "text/xml");
                DataHandler fileDh = new DataHandler(fileDs);
                AttachmentPart fileAtt = new AttachmentPart(fileDh);
                fileAtt.setContentId(file.getName());
                attachments.add(fileAtt);
            }
//            // 处理附件流
            if (attFiles != null) {
                for (int i = 0; i < attFiles.length; i++) {
                	 File file = new File("D:\\test\\测试附件.docx");
                     FileInputStream fis = new FileInputStream(file);
                     ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
         		    byte[] buffer = new byte[1024]; 
         		    int count = 0; 
         		    while((count = fis.read(buffer)) >= 0){ 
         		        baos.write(buffer, 0, count); 
         	        } 
                    byte[] fileEncrypt = AES4DotNet.Encrypt(baos.toByteArray());
     
                    DataSource fileDs = new ByteArrayDataSource(fileEncrypt, "text/xml");
                    DataHandler fileDh = new DataHandler(fileDs);
                    AttachmentPart fileAtt = new AttachmentPart(fileDh);
                    fileAtt.setContentId(file.getName());
                    attachments.add(fileAtt);             
                }
                for (int i = 0; i < attFiles.length; i++) {
               	 File file = new File("D:\\test\\testpdf2.pdf");
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        		    byte[] buffer = new byte[1024]; 
        		    int count = 0; 
        		    while((count = fis.read(buffer)) >= 0){ 
        		        baos.write(buffer, 0, count); 
        	        } 
                   byte[] fileEncrypt = AES4DotNet.Encrypt(baos.toByteArray());
    
                   DataSource fileDs = new ByteArrayDataSource(fileEncrypt, "text/xml");
                   DataHandler fileDh = new DataHandler(fileDs);
                   AttachmentPart fileAtt = new AttachmentPart(fileDh);
                   fileAtt.setContentId(file.getName());
                   attachments.add(fileAtt);             
               }
                for (int i = 0; i < attFiles.length; i++) {
               	 File file = new File("D:\\test\\测试.doc");
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        		    byte[] buffer = new byte[1024]; 
        		    int count = 0; 
        		    while((count = fis.read(buffer)) >= 0){ 
        		        baos.write(buffer, 0, count); 
        	        } 
                   byte[] fileEncrypt = AES4DotNet.Encrypt(baos.toByteArray());
    
                   DataSource fileDs = new ByteArrayDataSource(fileEncrypt, "text/xml");
                   DataHandler fileDh = new DataHandler(fileDs);
                   AttachmentPart fileAtt = new AttachmentPart(fileDh);
                   fileAtt.setContentId(file.getName());
                   attachments.add(fileAtt);             
               }
            }

            Map<String, Object> rspMap = callWebService("SendArchives", attachments, null);
            //String rinfo = getReturnInfo((String) rspMap.get(RES_XML_STRING));
            resDoc = DocumentHelper.parseText((String) rspMap.get(RES_XML_STRING));
            System.out.println(resDoc.asXML());
            Node status = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/State");
            Node desc = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/Description");
            Node fRecordId = resDoc.selectSingleNode("//PantheonData/PantheonDataBody/DATA/UserArea/ReturnInfo/FileRecordID");
            // updateDocStatus(queueId, status.getText(), desc.getText(), fRecordId.getText());

        } catch (Exception e) {
            boo = false;
            e.printStackTrace();
            msgl.add("执行发送公文过程出错：" + e.getMessage());
            log.error("执行发送公文过程出错：" + e.getMessage());
        }
        return boo;
    }


    // 把公文组装成发送内容XML
    private static String getSendRequestString(ECDocument document) {
        StringBuffer sbf = new StringBuffer("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
        sbf.append("<PantheonData><PantheonDataHeader></PantheonDataHeader><PantheonDataBody><DATA><UserArea>");
        sbf.append("<公文标题>").append(document.getTitle()).append("</公文标题>");
        sbf.append("<发文文号>").append(document.getCode()).append("</发文文号>");
        sbf.append("<主题词>").append(document.getSubjectWords()).append("</主题词>");
        sbf.append("<发文部门内码>").append(sendUnitCode).append("</发文部门内码>");
        sbf.append("<发文部门>").append(document.getSendUnitName()).append("</发文部门>");
        sbf.append("<发文时间>").append(document.getDraftDate()).append("</发文时间>");
        sbf.append("<公文紧急程度>").append(document.getUrgent()).append("</公文紧急程度>");
        sbf.append("<主送信息>").append(document.getMainSend()).append("</主送信息>");
        sbf.append("<收文部门>").append(document.getUnitCode()).append(";</收文部门>");
        sbf.append("<收文部门名称>").append(document.getUnitName()).append("</收文部门名称>");
        sbf.append("<公文类别信息>").append(document.getType()).append("</公文类别信息>");
        sbf.append("<抄送信息>").append(document.getCopySend()).append("</抄送信息>");
        sbf.append("<公文密级>").append(document.getSecret()).append("</公文密级>");
        sbf.append("<公文分类>").append("1").append("</公文分类>"); //1 普通文件 2 会议通知 3 信息简报
        sbf.append("</UserArea></DATA></PantheonDataBody></PantheonData>");
        System.out.println(sbf.toString());
        return sbf.toString();
    }

    // 从数据库中取出 公文和附件 的文件名和文件类型信息,并组织成XML
    private static String affixString() {
        String filename = "正文.doc;测试附件.docx;testpdf2.pdf;测试.doc";
        String attType = "false;true;true;true";
        String contentType = "application/msword;application/vnd.openxmlformats-officedocument.word;application/pdf;application/msword";
//        // 正文
//        for (int i = 0; i < docFiles.length; i++) {
//            if (!"".equals(filename)) {
//                filename += ";";
//                attType += ";";
//                contentType += ";";
//            }
//            File file = docFiles[i];
//            String ct = "";
//            filename += file.getName();
//            attType += "false";
//            contentType += ct;
//        }
//        // 附件
//        if (attFiles != null) {
//            for (int i = 0; i < attFiles.length; i++) {
//                if (!"".equals(filename)) {
//                    filename += ";";
//                    attType += ";";
//                    contentType += ";";
//                }
//                String filesName = "";
//                String ct = "";
//                filename += filesName;
//                attType += "true";
//                contentType += ct;
//            }
//        }
        String sXMLArchive = "<?xml version=\"1.0\" encoding=\"gb2312\"?><PantheonData><PantheonDataHeader></PantheonDataHeader>" +
                "<PantheonDataBody><DATA><UserArea>" +
                "<FileName>" + filename + "</FileName>" +
                "<AttType>" + attType + "</AttType>" +
                "<ContentType>" + contentType + "</ContentType>" +
                "</UserArea></DATA></PantheonDataBody></PantheonData>";
        return sXMLArchive;
    }
   
    public static void main(String[] args) throws Exception {
    	 rsa = new RSA4DotNet();
    	setUsername("yjqx_a_bsfy");
    	setSendUnitCode("c477bfb6-595c-41cd-96f7-8f98a6488fdb");
    	//setPassword("9EAEFB8079221E4E0C8C6F0248DA94FC");
    	setPassword("yjq000331");
    	//boolean result1=SendDocumentOne("123");
    	getReceiveDocument();
//    	System.out.println("aaa"+result1);
//    	System.out.println("aaa"+result1);
    	//getSelectOrg();
    	 // Map<String, Object> rspMap = callWebService("SendArchives", null, null);
           //String rinfo = getReturnInfo((String) rspMap.get(RES_XML_STRING));
           //resDoc = DocumentHelper.parseText((String) rspMap.get(RES_XML_STRING));
          // System.out.println(resDoc.asXML());
	}
    // 下载平台公钥
    public static String downPublicKey() {
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
            e.printStackTrace();
            msgl.add("下载平台公钥失败：" + e.getMessage());
            log.error("下载平台公钥失败：" + e.getMessage());
        }
        return publicKey;
    }

    // 调用web服务，不带附件和参数
    private static Map<String, Object> callWebService(String operation) throws Exception {
        return callWebService(operation, null);
    }

    // 调用web服务，带参数
    private static Map<String, Object> callWebService(String operation, Map<String, Object> params) throws Exception {
        return callWebService(operation, null, params);
    }

    // 调用web服务，带附件和参数
    private static Map<String, Object> callWebService(String operation, List<AttachmentPart> attachments, Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        Call call = null;
        try {
            if (username == null || password == null) {
                System.out.println("WSE验证配置有误，用户名或密码不能为空。");
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
				System.out.println("123");
				System.out.println(message.getSOAPPartAsString());
                map.put(RES_XML_STRING, rsp);
                map.put(RES_MESSAGE, message);
            }
        } catch (AxisFault e) {
      
        	e.printStackTrace();
            if (e.getFaultCode().toString().equals("{http://schemas.xmlsoap.org/ws/2002/07/secext}FailedAuthentication"))
                System.err.println("The usernameToken and password aren't right! ");
            else {
                System.err.println(e.getFaultCode().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    
    private static AttachmentPart createPublicKeyAttachment() throws UnsupportedEncodingException {
        return createPublicKeyAttachment(null);
    }

    // 创建公钥附件Archives.xml
    private static AttachmentPart createPublicKeyAttachment(String pRecordId)
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


    private static String getReturnInfo(String returnXml) throws DocumentException {
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


    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        // 对应.net服务，密码用MD5加密
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public static void setUsername(String username1) {
        username = username1;
    }

    public static void setPassword(String password1) {
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

    public static void setSendUnitCode(String sendUnitCode1) {
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
