package gcl.xuzhou.util;

import org.apache.axis.encoding.Base64;
import sun.misc.BASE64Decoder;
import weaver.conn.RecordSet;
import weaver.docs.webservices.DocAttachment;
import weaver.docs.webservices.DocInfo;
import weaver.docs.webservices.DocServiceImpl;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

import java.io.*;
import java.util.zip.ZipInputStream;

/**
 * @Description: 合同文档创建方法合集
 * @Author: adore
 * @DateTime: 2018-10-31 11:49 AM
 * @Version: v1.0
 */

public class CreateFileUtil {

    /**
     * 创建系统文档
     *
     * @param inputStream 文件流
     * @param docCategory 文档目录id
     * @param filename    文档名称
     * @param createrid   创建人id
     * @return 文档id
     */
    public String createDoc(InputStream inputStream, String docCategory, String filename, String createrid) {
        String docid = "";
        try {
            String uploadBuffer = "";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }

            uploadBuffer = Base64.encode(baos.toByteArray());
            // log.writeLog("uploadBuffer:"+uploadBuffer.length());
            docid = getDocId(filename, uploadBuffer, createrid, docCategory);
        } catch (Exception e) {
            this.writeLog(e);
        }
        return docid;
    }

    /**
     * 获取文档id
     *
     * @param name        文档名称
     * @param value       文档内容
     * @param createrid   创建人id
     * @param seccategory 目录
     * @return docId 文档id，-1失败
     */
    public String getDocId(String name, String value, String createrid, String seccategory) {
        String docId = "";
        try {
        DocInfo di = new DocInfo();
        di.setMaincategory(0);
        di.setSubcategory(0);
        di.setSeccategory(Util.getIntValue(seccategory));
        di.setDocSubject(name.substring(0, name.lastIndexOf(".")));
        DocAttachment doca = new DocAttachment();
        doca.setFilename(name);
        byte[] buffer = new BASE64Decoder().decodeBuffer(value);
        String encode = Base64.encode(buffer);
        doca.setFilecontent(encode);
        DocAttachment[] docs = new DocAttachment[1];
        docs[0] = doca;
        di.setAttachments(docs);
        String departmentId = "-1";
        String sql = "select departmentid from hrmresource where id=" + createrid;
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        User user = new User();
        if (rs.next()) {
            departmentId = Util.null2String(rs.getString("departmentid"));
        }
        user.setUid(Util.getIntValue(createrid));
        user.setUserDepartment(Util.getIntValue(departmentId));
        user.setLanguage(7);
        user.setLogintype("1");
        user.setLoginip("127.0.0.1");
        DocServiceImpl ds = new DocServiceImpl();

            docId = String.valueOf(ds.createDocByUser(di, user));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return docId;
    }

    /**
     * 根据系统文档存放路径 获取文档流
     *
     * @param filerealpath 文档存放路径
     * @param iszip        是否压缩包
     * @return InputStream
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

    /**
     * InputStream --> File
     *
     * @param ins  InputStream
     * @param file File
     */
    public String inputStream2File(InputStream ins, File file) {
        String path = "";
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            path = file.getPath();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }


    /**
     * 开发技巧，一键开关日志，if(true)开启；if(false)关闭
     *
     * @param obj 日志内容
     */
    private void writeLog(Object obj) {
        if (false) {
            new BaseBean().writeLog(this.getClass().getName(), obj);
        }
    }
}
