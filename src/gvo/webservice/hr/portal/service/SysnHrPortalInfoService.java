package gvo.webservice.hr.portal.service;

import gvo.webservice.hr.portal.service.impl.*;
import weaver.general.BaseBean;

/**
 *@Description: hr管理者自助数据同步webservice
* @version: 
* @author: jianyong.tang
* @date: 2020年4月8日 下午3:28:44
 */
public class SysnHrPortalInfoService {
	/**
	 * 同步组织数据
	 * @param json
	 * @return
	 */
	public String sysnOrgInfo(String json) {
		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始组织数据同步");
		log.writeLog("SysnHrPortalInfoService","json:"+json);
		String result = "";
		SysnOrgInfoImpl sii = new SysnOrgInfoImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","组织数据同步结束 result："+result);
		return result;
	}
	
	/**
	 * 同步人员信息数据
	 * @param json
	 * @return
	 */
	public String sysnPersonInfo(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始人员信息数据同步");
		String result = "";
		SysnPersonInfoImpl sii = new SysnPersonInfoImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","人员信息数据同步结束 result："+result);
		return result;
	}
	
	/**
	 * 同步人员照片数据
	 * @param json
	 * @return
	 */
	public String sysnPersonPhoto(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始人员照片同步");
		String result = "";
		SysnPersonPhotoImpl sii = new SysnPersonPhotoImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","人员照片同步结束 result："+result);
		return result;
	}
	
	/**
	 * 同步人员上岗证数据
	 * @param json
	 * @return
	 */
	public String sysnPersonCertificates(String json) {

        BaseBean log = new BaseBean();
        log.writeLog("SysnHrPortalInfoService","开始人员上岗证数据同步");
        String result = "";
        SysnPersonCertificatesImpl sii = new SysnPersonCertificatesImpl();
        try {
            result = sii.sysnInfo(json);
        } catch (Exception e) {
            log.writeLog(e);
            result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
        }
        log.writeLog("SysnHrPortalInfoService","人员上岗证数据同步结束 result："+result);
        return result;
	}
	
	/**
	 * 同步人员排班数据
	 * @param json
	 * @return
	 */
	public String sysnPersonScheduling(String json) {

        BaseBean log = new BaseBean();
        log.writeLog("SysnHrPortalInfoService","开始排班数据同步");
        String result = "";
        SysnPersonSchedulingImpl sii = new SysnPersonSchedulingImpl();
        try {
            result = sii.sysnInfo(json);
        } catch (Exception e) {
            log.writeLog(e);
            result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
        }
        log.writeLog("SysnHrPortalInfoService","排班数据同步结束 result："+result);
        return result;
	}
	
	/**
	 * 同步人员请休假数据
	 * @param json
	 * @return
	 */
	public String sysnPersonLeaveInfo(String json) {

        BaseBean log = new BaseBean();
        log.writeLog("SysnHrPortalInfoService","开始请休假数据同步");
        String result = "";
        SysnPersonLeaveInfoImpl sii = new SysnPersonLeaveInfoImpl();
        try {
            result = sii.sysnInfo(json);
        } catch (Exception e) {
            log.writeLog(e);
            result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
        }
        log.writeLog("SysnHrPortalInfoService","请休假数据同步结束 result："+result);
        return result;
	}
	
	/**
	 * 同步人员考勤异常数据
	 * @param json
	 * @return
	 */
	public String sysnPersonAttendExpInfo(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始人员考勤异常数据同步");
		String result = "";
		SysnPersonAttendExpInfoImpl sii = new SysnPersonAttendExpInfoImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","人员考勤异常数据同步结束 result："+result);
		return result;
	}
	
	/**
	 * 同步团队绩效
	 * @param json
	 * @return
	 */
	public String sysnTeamPerformanceInfo(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始团队绩效数据同步");
		String result = "";
		SysnTeamPerformanceInfoImpl sii = new SysnTeamPerformanceInfoImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","团队绩效数据同步结束 result："+result);
		return result;
	}
	
	/**
	 * 同步团队奖惩信息
	 * @param json
	 * @return
	 */
	public String sysnRewardsAndPunishments(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始团队奖惩信息同步");
		String result = "";
		SysnRewardsAndPunishmentsImpl sii = new SysnRewardsAndPunishmentsImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","团队奖惩信息同步结束 result："+result);
		return result;
	}

	/**
	 * 同步B类员工加班时数
	 * @param json
	 * @return
	 */
	public String sysnPersonJbDataForB(String json) {

		BaseBean log = new BaseBean();
		log.writeLog("SysnHrPortalInfoService","开始B类员工加班时数同步");
		String result = "";
		SysnPersonJbDataBImpl sii = new SysnPersonJbDataBImpl();
		try {
			result = sii.sysnInfo(json);
		} catch (Exception e) {
			log.writeLog(e);
			result = "{\"result\":\"E\",\"msg\":\"JSON格式异常\"}";
		}
		log.writeLog("SysnHrPortalInfoService","B类员工加班时数同步结束 result："+result);
		return result;
	}
}
