package gcl.xuzhou.workflow;

import gcl.xuzhou.util.CreateFileUtil;
import gcl.xuzhou.util.ReadXmlUtil;
import gcl.xuzhou.util.XuzhouWorkflowUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.*;

/**
 * E9触发流程xml实现类
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-09-12 11:34
 * <p>
 * update by shaw 2019-10-17 23:07:18
 * 新增附件传递
 */

public class FlowAutoRequestServiceImpl extends BaseBean implements FlowAutoRequestService {
    @Override
    public String createFlow(String param) {
        CreateFileUtil fileUtil = new CreateFileUtil();
        XuzhouWorkflowUtil workflowUtil = new XuzhouWorkflowUtil();
        ReadXmlUtil xmlUtil = new ReadXmlUtil();
        Map<String, Object> map = xmlUtil.readStringXml(param);
        this.writeLog("map:" + map);

        boolean hasAttach = false; // 判断是否含有附件
        for (String name : map.keySet()) {
            if ("attachment".equals(name)) {
                hasAttach = true;
            }
        }
        String rqid = "";

        String workCode = Util.null2String(map.get("Createrid"));
        int workflowid = Util.getIntValue(Util.null2String(map.get("workflowCode")));
        String isNext = Util.null2String(map.get("isNext"));
        String creator = workflowUtil.getCreator(workCode);
        Map<String, String> header = (Map<String, String>) map.get("HEADER");
        Map<String, List<Map<String, String>>> dtMap = (Map<String, List<Map<String, String>>>) map.get("DETAILS");
        this.writeLog("dtMap:" + dtMap);
        String title = workflowUtil.getWFTitle(workflowid, creator);
        rqid = workflowUtil.createRequestDt(workflowid, Util.getIntValue(creator), title, isNext, header, dtMap);
        if (hasAttach && Util.getIntValue(rqid) > 0) {
            RecordSet rs = new RecordSet();
            String tabName = workflowUtil.getTableName(rqid);
            String docCategory = "";
            String sql = " select DOCCATEGORY from WORKFLOW_BASE where ID=" + workflowid;
            rs.execute(sql);
            if (rs.next()) {
                String docCategories = Util.null2String(rs.getString("DOCCATEGORY"));
                String[] dcg = docCategories.split(",");
                docCategory = dcg[dcg.length - 1];
            }

            List<Map<String, String>> attachList = (List<Map<String, String>>) map.get("attachment");
            if (attachList.size() > 0) {
                String flag = "";
                String docIds = "";
                String fieldName = "";
                for (Map<String, String> attach : attachList) {
                    String name = attach.get("name");
                    String type = attach.get("type");
                    String content = attach.get("content");
                    fieldName = attach.get("fieldName");
                    String docId = fileUtil.getDocId(name + "." + type, content, creator, docCategory);
                    docIds += flag + docId;
                    flag = ",";
                    this.writeLog("docId:" + name + "|id=" + docId);
                }
                sql = " Update " + tabName + " set " + fieldName + " = '" + docIds + "' where requestid= " + rqid;
                this.writeLog("attach sql :" + sql);
                rs.execute(sql);
            }
        }

        return rqid;
    }
}
