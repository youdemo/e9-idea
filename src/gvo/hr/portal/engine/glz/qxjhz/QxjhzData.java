package gvo.hr.portal.engine.glz.qxjhz;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author
 * @date 2020/5/12 0012 - 上午 10:45
 */
public class QxjhzData {
    private String workcode;
    private String name;
    private String companyname;
    private String centername;
    private String deptname;
    private String groupname;
    private String jobtitlename;
    private String sanqi;
    private String yxjhj;
    private String dnxjzss;
    private String nf;
    private Map<String, List<String>> map;//key:月份 value:请假类型-请假时长

    public QxjhzData() {
    }

    public QxjhzData(String workcode, String name, String companyname, String centername, String deptname, String groupname, String jobtitlename, String sanqi, String yxjhj, String dnxjzss, String nf, Map<String, List<String>> map) {
        this.workcode = workcode;
        this.name = name;
        this.companyname = companyname;
        this.centername = centername;
        this.deptname = deptname;
        this.groupname = groupname;
        this.jobtitlename = jobtitlename;
        this.sanqi = sanqi;
        this.yxjhj = yxjhj;
        this.dnxjzss = dnxjzss;
        this.nf = nf;
        this.map = map;
    }

    public String getWorkcode() {
        return workcode;
    }

    public void setWorkcode(String workcode) {
        this.workcode = workcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCentername() {
        return centername;
    }

    public void setCentername(String centername) {
        this.centername = centername;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getJobtitlename() {
        return jobtitlename;
    }

    public void setJobtitlename(String jobtitlename) {
        this.jobtitlename = jobtitlename;
    }

    public String getSanqi() {
        return sanqi;
    }

    public void setSanqi(String sanqi) {
        this.sanqi = sanqi;
    }

    public String getYxjhj() {
        return yxjhj;
    }

    public void setYxjhj(String yxjhj) {
        this.yxjhj = yxjhj;
    }

    public String getDnxjzss() {
        return dnxjzss;
    }

    public void setDnxjzss(String dnxjzss) {
        this.dnxjzss = dnxjzss;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<String>> map) {
        this.map = map;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QxjhzData qxjhzData = (QxjhzData) o;
        return Objects.equals(workcode, qxjhzData.workcode) &&
                Objects.equals(name, qxjhzData.name) &&
                Objects.equals(companyname, qxjhzData.companyname) &&
                Objects.equals(centername, qxjhzData.centername) &&
                Objects.equals(deptname, qxjhzData.deptname) &&
                Objects.equals(groupname, qxjhzData.groupname) &&
                Objects.equals(jobtitlename, qxjhzData.jobtitlename) &&
                Objects.equals(sanqi, qxjhzData.sanqi) &&
                Objects.equals(yxjhj, qxjhzData.yxjhj) &&
                Objects.equals(dnxjzss, qxjhzData.dnxjzss) &&
                Objects.equals(nf, qxjhzData.nf) &&
                Objects.equals(map, qxjhzData.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workcode, name, companyname, centername, deptname, groupname, jobtitlename, sanqi, yxjhj, dnxjzss, nf, map);
    }

    @Override
    public String toString() {
        String q="QxjhzData{" +
                "workcode='" + workcode + '\'' +
                ", name='" + name + '\'' +
                ", companyname='" + companyname + '\'' +
                ", centername='" + centername + '\'' +
                ", deptname='" + deptname + '\'' +
                ", groupname='" + groupname + '\'' +
                ", jobtitlename='" + jobtitlename + '\'' +
                ", sanqi='" + sanqi + '\'' +
                ", yxjhj='" + yxjhj + '\'' +
                ", dnxjzss='" + dnxjzss + '\'';
        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for(Map.Entry<String, List<String>> m:entries){
            q+=", map key="+m.getKey();
            q+=", map value list={";
            List<String> value = m.getValue();
            for(String s:value){
                q+=" s, ";
            }
        }
        q+="}";
        return  q;
    }
}
