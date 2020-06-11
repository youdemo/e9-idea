package gvo.hr.portal.engine.glz.kq;


import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author
 * @date 2020/4/24 0024 - 下午 4:46
 */
public class Person {
    private String workcode;
    private String name;
    private String companyname;
    private String centername;
    private String deptname;
    private String groupname;
    private String identitytype;
    private String joblevel;
    private String channel;
    private String year;
    private String month;
    private List<Map<String,String>> map;
    private List<String> list;

    public Person() {
    }


    public Person(String workcode, String name, String companyname, String centername, String deptname, String groupname, String identitytype, String joblevel, String channel, String year, String month, List<Map<String, String>> map, List<String> list) {
        this.workcode = workcode;
        this.name = name;
        this.companyname = companyname;
        this.centername = centername;
        this.deptname = deptname;
        this.groupname = groupname;
        this.identitytype = identitytype;
        this.joblevel = joblevel;
        this.channel = channel;
        this.year = year;
        this.month = month;
        this.map = map;
        this.list = list;
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

    public String getIdentitytype() {
        return identitytype;
    }

    public void setIdentitytype(String identitytype) {
        this.identitytype = identitytype;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(String joblevel) {
        this.joblevel = joblevel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<Map<String, String>> getMap() {
        return map;
    }

    public void setMap(List<Map<String, String>> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(workcode, person.workcode) &&
                Objects.equals(name, person.name) &&
                Objects.equals(companyname, person.companyname) &&
                Objects.equals(centername, person.centername) &&
                Objects.equals(deptname, person.deptname) &&
                Objects.equals(groupname, person.groupname) &&
                Objects.equals(identitytype, person.identitytype) &&
                Objects.equals(joblevel, person.joblevel) &&
                Objects.equals(channel, person.channel) &&
                Objects.equals(year, person.year) &&
                Objects.equals(month, person.month) &&
                Objects.equals(map, person.map) &&
                Objects.equals(list, person.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workcode, name, companyname, centername, deptname, groupname, identitytype, joblevel, channel, year, month, map, list);
    }

    @Override
    public String toString() {
        return "Person{" +
                "workcode='" + workcode + '\'' +
                ", name='" + name + '\'' +
                ", companyname='" + companyname + '\'' +
                ", centername='" + centername + '\'' +
                ", deptname='" + deptname + '\'' +
                ", groupname='" + groupname + '\'' +
                ", identitytype='" + identitytype + '\'' +
                ", joblevel='" + joblevel + '\'' +
                ", channel='" + channel + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", map=" + map +
                ", list=" + list +
                '}';
    }
}
