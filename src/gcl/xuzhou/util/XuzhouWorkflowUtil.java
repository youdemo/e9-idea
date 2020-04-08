package gcl.xuzhou.util;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;
import weaver.soa.workflow.request.*;

import java.util.*;

/**
 * 流程创建接口方法
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-09-12 11:36
 */

public class XuzhouWorkflowUtil extends BaseBean {
    /**
     * 获取流程默认标题
     *
     * @param workflowid 流程id
     * @param creator    创建人
     * @return title 流程默认标题
     */
    public String getWFTitle(int workflowid, String creator) {
        String title;
        try {
            RecordSet rs = new RecordSet();
            ResourceComInfo rci = new ResourceComInfo();
            String nowDate = TimeUtil.getCurrentDateString(); // 获取当前日期2018-07-13
            String workflowname = "";
            String sql = " select id,workflowname from workflow_base where id= " + workflowid;
            rs.execute(sql);
            if (rs.next()) {
                workflowname = Util.null2String(rs.getString("workflowname"));
            }
            workflowname = Util.processBody(workflowname, "7"); // 多语言处理
            title = workflowname + "-" + rci.getResourcename(creator) + "-" + nowDate;
        } catch (Exception e) {
            title = "";
        }
        return title;
    }

    /**
     * 通过工号获取人员id
     *
     * @param workCode 工号
     * @return 人员id
     */
    public String getCreator(String workCode) {
        String creator = "";
        RecordSet rs = new RecordSet();
        String sql = "select id from HRMRESOURCE where WORKCODE='" + workCode + "' ";
        rs.execute(sql);
        if (rs.next()) {
            creator = Util.null2String(rs.getString("id"));
        }
        return creator;
    }

    /**
     * 方法描述 : 创建流程方法(主表)
     * 项目名称 : WEAVER_KS
     * 创建者 : ADORE
     * 创建时间 : 2018-07-12 14:44:07
     *
     * @param workflowid 流程类型id
     * @param userid     创建人id
     * @param title      流程标题
     * @param IsNextFlow 是否默认流转到下一节点，0不流转，其他流转
     * @param map        数据map
     * @return String 流程requestid
     */
    public String createRequest(int workflowid, int userid, String title, String IsNextFlow, Map<String, String> map) {
        String newrequestid = "";
        RequestInfo req = new RequestInfo();
        req.setCreatorid(userid + "");  //创建人
        req.setWorkflowid(workflowid + "");//流程id
        req.setDescription(title);  //流程标题
        req.setIsNextFlow(IsNextFlow); //是否默认流转到下一节点
        MainTableInfo maintable = new MainTableInfo();
        // 去除为空的字段
        getMap(map);
        Set<String> keySet = map.keySet();
        Property[] property = new Property[keySet.size()];
        int i = 0;
        Property p;
        for (String key : keySet) {
            p = new Property();
            p.setName(key);
            p.setValue(map.get(key));
            property[i] = p;
            i++;
        }

        maintable.setProperty(property);

        req.setMainTableInfo(maintable);
        req.setRequestlevel("0");
        req.setRemindtype("0");
        RequestService service = new RequestService();
        try {
            newrequestid = service.createRequest(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newrequestid;
    }

    private static void getMap(Map<String, String> map) {
        // 判断数据是否为空，如果为空就不传，防止字段为数字型时，Update语句出错，导致流程看不到
        Set<Map.Entry<String, String>> mainKeySet = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = mainKeySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            // String name = entry.getKey();
            String value = entry.getValue();
            if ("".equals(value)) {
                //特别注意：不能使用map.remove(name)  否则会报同样的错误
                iterator.remove();
            }
        }
    }

    /**
     * 方法描述 : 创建流程方法(含明细表)
     * 项目名称 : WEAVER_KS
     * 创建者 : ADORE
     * 创建时间 : 2018-07-23 13:48:19
     * 修改时间 ：2019-01-22 14:33:41
     *
     * @param workflowid 流程类型id
     * @param userid     创建人id
     * @param title      流程标题
     * @param IsNextFlow 是否默认流转到下一节点，0不流转，其他流转
     * @param map        主表数据map
     * @param dtMap      明细表数据map
     * @return newrequestid >0 则创建成功
     */
    public String createRequestDt(int workflowid, int userid, String title, String IsNextFlow,
                                  Map<String, String> map, Map<String, List<Map<String, String>>> dtMap) {
        String newrequestid = "";
        RequestInfo req = new RequestInfo();

        req.setCreatorid(userid + "");  //创建人
        req.setWorkflowid(workflowid + "");//流程id
        req.setDescription(title);  //流程标题
        req.setIsNextFlow(IsNextFlow); //是否默认流转到下一节点
        MainTableInfo maintable = new MainTableInfo();
        // 去除为空的字段
        getMap(map);

        Set<String> keySet = map.keySet();
        Property[] property = new Property[keySet.size()];
        int i = 0;
        Property p;
        for (String key : keySet) {
            p = new Property();
            p.setName(key);
            p.setValue(map.get(key));
            property[i] = p;
            i++;
        }

        maintable.setProperty(property);
        DetailTableInfo detailTableInfo = new DetailTableInfo();

        // 有明细时生成明细表
        if (dtMap.size() > 0) {
            detailTableInfo = getDetailTableInfo(dtMap);
        }

        req.setMainTableInfo(maintable);
        req.setDetailTableInfo(detailTableInfo);
        req.setRequestlevel("0");
        req.setRemindtype("0");

        RequestService service = new RequestService();
        try {
            newrequestid = service.createRequest(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newrequestid;
    }

    /**
     * 整理流程明细表数据
     *
     * @param dtMap 流程明细表map
     * @return DetailTableInfo
     */
    private static DetailTableInfo getDetailTableInfo(Map<String, List<Map<String, String>>> dtMap) {
        DetailTableInfo details = new DetailTableInfo();
        List<DetailTable> list_detail = new ArrayList<DetailTable>();

        Set<String> dtKeySet = dtMap.keySet();
        for (String dtKey : dtKeySet) {
            List<Row> list_row = new ArrayList<>();
            DetailTable dt = new DetailTable();
            List<Map<String, String>> dataMapList = dtMap.get(dtKey);
            for (int i = 0; i < dataMapList.size(); i++) {
                Map<String, String> dataMap = dataMapList.get(i);
                getMap(dataMap); // 清除空数据
                Row row = new Row();
                List<Cell> list_cell = new ArrayList<Cell>();
                Set<String> jo = dataMap.keySet();
                for (String dataKey : jo) {
                    String value = dataMap.get(dataKey);
                    Cell cel = new Cell();
                    cel.setName(dataKey);
                    if (Util.null2String(value).length() > 0) {
                        cel.setValue(value);
                        list_cell.add(cel);
                    }
                }

                int size = list_cell.size();
                Cell cells[] = new Cell[size];
                for (int index = 0; index < list_cell.size(); index++) {
                    cells[index] = list_cell.get(index);
                }
                row.setCell(cells);
                row.setId("" + i);
                list_row.add(row);
            }
            int size = list_row.size();
            // if(size == 0) break;
            Row rows[] = new Row[size];
            for (int index = 0; index < list_row.size(); index++) {
                rows[index] = list_row.get(index);
            }
            dt.setRow(rows);
            dt.setId(dtKey);
            list_detail.add(dt);
        }
        int size = list_detail.size();
        DetailTable detailtables[] = new DetailTable[size];
        for (int index = 0; index < list_detail.size(); index++) {
            detailtables[index] = list_detail.get(index);
        }
        details.setDetailTable(detailtables);
        return details;
    }

    /**
     * 获取表名
     *
     * @param requestid 流程请求id
     * @return tableName
     */
    public String getTableName(String requestid) {
        String tableName = "";
        RecordSet rs = new RecordSet();
        String sql = " select TABLENAME from WORKFLOW_BILL where ID in (select FORMID " +
                " from WORKFLOW_BASE where ID in(select WORKFLOWID " +
                " from WORKFLOW_REQUESTBASE where REQUESTID =" + requestid + ")) ";
        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("TABLENAME"));
        }
        return tableName;
    }

    /**
     * 获取表名
     *
     * @param requestid 流程请求id
     * @return tableName
     */
    public String getWorkflowid(String requestid) {
        String tableName = "";
        RecordSet rs = new RecordSet();
        String sql = " select WORKFLOWID from WORKFLOW_REQUESTBASE where REQUESTID =" + requestid;
        rs.execute(sql);
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("WORKFLOWID"));
        }
        return tableName;
    }
}
