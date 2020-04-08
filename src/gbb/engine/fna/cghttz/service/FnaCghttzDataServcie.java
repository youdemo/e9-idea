package gbb.engine.fna.cghttz.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

public interface FnaCghttzDataServcie {
    /**
     * 获取form表单
     * @param params
     * @return
     */
    Map<String, Object> weatableDemo(Map<String, Object> params);

    /**
     * 获取高级搜索条件
     * @param params
     * @return
     */
    Map<String,Object> weatableConditonDemo(Map<String, Object> params);
    /**
     * 导出excel
     *
     */
    InputStream WeaReportOutExcel(HttpServletRequest request, HttpServletResponse response);
}
