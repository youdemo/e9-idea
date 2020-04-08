package gvo.portal;

import com.engine.workflow.service.DimensionCustomService;

/** 
* @author 作者  张瑞坤
* @date 创建时间：2020年2月12日 下午4:32:47 
* @version 1.0 测试维度
*/
public class TestVD implements DimensionCustomService {
	/**
	 * 异构系统流程自定义条件
	 */
	
	@Override
	public String getOsSqlwhere() {
		// TODO Auto-generated method stub
		return " (isremark = 0) ";
	}
	/**
	 * 门户自定义条件
	 */
	@Override
	public String getPortalSqlwhere() {
		// TODO Auto-generated method stub
		return "  ";
	}
	/**
	 * 流程模块自定义条件
	 */
	
	@Override
	public String getToDoSqlwhere() {
		// TODO Auto-generated method stub
		return "  (t2.workflowid>1 and t2.workflowid not in (125262))  ";
	}

}
