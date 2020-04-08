package gvo.portal;

import com.engine.workflow.service.DimensionCustomService;

/**
 * createby jianyong.tang
 * createTime 2020/2/13 17:01
 * version v1
 * desc
 */
public class GetTydbFlowListDimension implements DimensionCustomService {
    @Override
    public String getPortalSqlwhere() {
        return " (1=1) ";
    }

    @Override
    public String getToDoSqlwhere() {
        //return " (t1.workflowid not in(1,125262)) ";
        return " (1=1) ";
    }

    @Override
    public String getOsSqlwhere() {
        return " (isremark=0) ";
    }
}
