package gvo.hr.portal.util;

import weaver.conn.RecordSet;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Map;

/**公共方法类 hr门户
 * createby jianyong.tang
 * createTime 2020/4/21 13:33
 * version v1
 * desc
 */
public class HrUtil {
    public Map<String,String> getPersonBaseinfo(String userid){
        Map<String,String> map = new HashMap<String ,String>();
        RecordSet rs = new RecordSet();
        String workcode="";
        String belongto = "";
        String recordid="";
        String sql = "select workcode,nvl(belongto,-1) as belongto from hrmresource where id="+userid;
        rs.execute(sql);
        if(rs.next()){
            workcode = Util.null2String(rs.getString("workcode"));
            belongto = Util.null2String(rs.getString("belongto"));
            if("-1".equals(belongto)){
                recordid="0";
            }else{
                recordid = workcode.substring(workcode.length()-1);
                workcode = workcode.substring(0,workcode.length()-1);
            }

        }
        map.put("workcode",workcode);
        map.put("recordid",recordid);
        return map;
    }

    /**
     * 获取当前操作人可查看的负责的组织范围 主岗看全部
     * @param workcode
     * @param recordid
     * @return
     */
    public String getOrgcodes(String workcode,String recordid){
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String otherOrgcodes = "";
        if("0".equals(recordid)){
            otherOrgcodes = getOtherOrgcode(workcode);
        }else{
            otherOrgcodes = "-1";
        }
        String sql = "select wm_concat(orgcode) as orgcodes from table(getckbmids('"+workcode+"','"+recordid+"','"+otherOrgcodes+"'))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));

        }
        if("".equals(orgcodes)){
            orgcodes="-1";
        }
        orgcodes = "'"+orgcodes.replace(",","','")+"'";
        return orgcodes;
    }

    /**
     * 获取负责人code
     * @param workcode
     * @param orgcodes
     * @return
     */
    public String getFzrghs(String workcode,String orgcodes){
        RecordSet rs = new RecordSet();
        String fzrcodes = "";
        String sql = "select wm_concat(fzrcode) as fzrcodes from uf_hr_orgdata where orgcode in( " +
                "select currdeptcode from uf_hr_persondata where workcode='"+workcode+"' " +
                "union all " +
                "select currdeptcode from uf_hr_persondata_dt1 where workcode='"+workcode+"' and status='有效') " +
                "and fzrcode is not null and fzrcode <> '"+workcode+"'  and orgcode in("+orgcodes+")";
        rs.execute(sql);
        if(rs.next()){
            fzrcodes = Util.null2String(rs.getString("fzrcodes"));
        }
        if("".equals(fzrcodes)){
            fzrcodes="-1";
        }
        fzrcodes = "'"+fzrcodes.replace(",","','")+"'";
        return fzrcodes;
    }

    /**
     * 获取当前操作人可查看的负责的组织范围 主岗看全部
     * @param workcode
     * @param recordid
     * @return
     */
    public String getOrgcodesps(String workcode,String recordid){
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String otherOrgcodes = "";
        if("0".equals(recordid)){
            otherOrgcodes = getOtherOrgcode(workcode);
        }else{
            otherOrgcodes = "-1";
        }
        String sql = "select wm_concat(orgcode) as orgcodes from table(getckbmids('"+workcode+"','"+recordid+"','"+otherOrgcodes+"'))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));

        }
        if("".equals(orgcodes)){
            orgcodes="-1";
        }
        return orgcodes;
    }


    /**
     * 获取当前操作人可查看的负责的组织范围 主岗看自己的
     * @param workcode
     * @param recordid
     * @return
     */
    public String getOrgcodes2(String workcode,String recordid){
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String sql = "select wm_concat(orgcode) as orgcodes from table(getckbmids2('"+workcode+"','"+recordid+"'))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));

        }
        if("".equals(orgcodes)){
            orgcodes="-1";
        }
        orgcodes = "'"+orgcodes.replace(",","','")+"'";
        return orgcodes;
    }

    /**
     * 根据组织名称获取组织及下级组织
     * @return
     */
    public String getOrgcodes3(String orgname){
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String sql = "select wm_concat(orgcode) as orgcodes from table(getckbmids3('"+orgname+"'))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));

        }
        if("".equals(orgcodes)){
            orgcodes="-1";
        }
        orgcodes = "'"+orgcodes.replace(",","','")+"'";
        return orgcodes;
    }

    /**
     * 获取当前人员所在部门
     * @param workcode
     * @param recordid
     * @return
     */
    public String getCurrentdeptcode(String workcode,String recordid){
        RecordSet rs = new RecordSet();
        String orgcode = "";
        String sql = "select currdeptcode from uf_hr_persondata where workcode='"+workcode+"' and recordid='"+recordid+"'";
        rs.execute(sql);
        if(rs.next()){
            orgcode = Util.null2String(rs.getString("currdeptcode"));
        }
        if("".equals(orgcode)){
            orgcode = "";
            sql = "select currdeptcode from uf_hr_persondata_dt1 where workcode='"+workcode+"' and recordid='"+recordid+"'";
            rs.execute(sql);
            if(rs.next()){
                orgcode = Util.null2String(rs.getString("currdeptcode"));
            }
        }
        return orgcode;
    }

    /**
     * 获取自定义维度的部门编码
     * @param workcode
     */
    public String getOtherOrgcode(String workcode){
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String bmids="";
        String flag = "";
        String sql = "select a.fzbm from uf_hr_bmfzr_mt a,hrmresource b where a.ry=b.id and b.workcode='"+workcode+"'";
        rs.execute(sql);
        while(rs.next()){
            String fzbm = Util.null2String(rs.getString("fzbm"));
            if(!"".equals(fzbm)){
                bmids = bmids + flag + fzbm;
                flag=",";
            }
        }
        flag = "";
        if(!"".equals(bmids)){
            sql = "select departmentcode from hrmdepartment where id in("+bmids+")";
            rs.execute(sql);
            while(rs.next()){
                String departmentcode = Util.null2String(rs.getString("departmentcode"));
                if(!"".equals(departmentcode)){
                    orgcodes = orgcodes + flag + departmentcode;
                    flag=",";
                }
            }

        }
        if("".equals(orgcodes)){
            orgcodes="-1";
        }
        //orgcodes = "'"+orgcodes.replace(",","','")+"'";
        return orgcodes;
    }
}
