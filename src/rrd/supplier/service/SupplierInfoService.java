package rrd.supplier.service;

import rrd.supplier.dao.SupplierInInfoDao;

import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 9:56
 * version v1
 * desc 供应商准入信息动作接口
 */
public interface SupplierInfoService {
    /**
     * 获取暂存表id
     * @param requestid 流程id
     */
    public String getSupplierTemporaryDataId(String requestid);


    /**
     * 获取暂存表信息
     * @param id 暂存表id
     * @return
     */
    public SupplierInInfoDao getSupplierTemporaryData(String id);

    /**
     * 更新暂存表
     * @param params 字段数据
     * @param id 暂存表id
     */
    public void updateSupplierTemporaryData(Map<String, Object> params,String id);

    /**
     * 提交保存供应商表
     * @param params 字段数据
     */
    public void updateSupplierTemporaryData(Map<String, Object> params);

    /**
     *
     * @param fieldName 字段名称
     * @param fieldValue 字段值
     * @param fieldType 字段类型
     * @param isMust 是否必填
     * @param otherInfo 其他信息
     * @return
     */
    public String getFieldHtml(String fieldName,String fieldValue,String fieldType,String isMust,String otherInfo,String otherInfo2);

    /**
     * 获取requestid
     * @param urlKey
     * @return
     */
    public String getRqid(String urlKey);

    /**
     * 页面保存的对象
     * @param map
     * @return
     */
    public String saveData(Map<String,String> map,String submittype,String rqid,String zcid);
}
