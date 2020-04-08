<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page import="APPDEV.HQ.FNA.UTIL.TransUtil" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="weaver.general.BaseBean" %>
<%
    BaseBean log = new BaseBean();
    String rqid = Util.null2String(request.getParameter("rqid"));
    RecordSet rs = new RecordSet();
    RecordSet rs_dt = new RecordSet();
    String sql_dt = "";
    String workflowid = "";
    String sql = "select workflowid from workflow_requestbase where requestid="+rqid;
    rs.execute(sql);
    if(rs.next()){
        workflowid = Util.null2String(rs.getString("workflowid"));
    }
    TransUtil tu = new TransUtil();
    String tableName = tu.getTableName(workflowid);
    String beginDate = "";
    String beginTime = "";
    String endDate = "";
    String endTime = "";
    String mainid = "";
    String MOREI = "";//差旅组
    //String TavelType = "";//差旅方案
    String ERGRU = "";//国内差旅等级
    String ERKLA = "";//国外差旅等级
    String BTRCon = "";//国内外
    String SENDCAR = "";//是否全程派车
    sql = "select * from "+tableName+" where requestid="+rqid;
    rs.execute(sql);
    if(rs.next()){
        beginDate = Util.null2String(rs.getString("BTRBEGDA"));
        beginTime = Util.null2String(rs.getString("BTRBEGTIME"));
        endDate = Util.null2String(rs.getString("BTRENDDA"));
        endTime = Util.null2String(rs.getString("BTRENDTIME"));
        mainid = Util.null2String(rs.getString("id"));
        MOREI = Util.null2String(rs.getString("MOREI"));
        //TavelType = Util.null2String(rs.getString("TavelType"));
        ERGRU = Util.null2String(rs.getString("ERGRU"));
        ERKLA = Util.null2String(rs.getString("ERKLA"));
        BTRCon = Util.null2String(rs.getString("BTRCon"));
        SENDCAR = Util.null2String(rs.getString("SENDCAR"));

    }
    String cldj = "";
    if("0".equals(BTRCon)){
        cldj = ERGRU;
    }else if("1".equals(BTRCon)){
        cldj = ERKLA;
    }

    JSONArray ja = new JSONArray();
    String DAY_ID = "";
    String TavelDestCity = "";
    int countnum=0;
    sql = "select count(1) as count from (SELECT  TO_CHAR(TO_DATE('"+beginDate+"', 'YYYY-MM-DD') + ROWNUM-1 , 'YYYY-MM-DD') DAY_ID  FROM DUAL   CONNECT BY ROWNUM <= TO_DATE('"+endDate+"', 'YYYY-MM-DD') - TO_DATE('"+beginDate+"', 'YYYY-MM-DD') + 1) a";
    rs.execute(sql);
    if(rs.next()){
        countnum = rs.getInt("count");
    }
    int count = 1;
    sql = "SELECT  TO_CHAR(TO_DATE('"+beginDate+"', 'YYYY-MM-DD') + ROWNUM-1 , 'YYYY-MM-DD') DAY_ID  FROM DUAL   CONNECT BY ROWNUM <= TO_DATE('"+endDate+"', 'YYYY-MM-DD') - TO_DATE('"+beginDate+"', 'YYYY-MM-DD') + 1";
    rs.execute(sql);
    while(rs.next()){
        TavelDestCity = "";
        JSONObject jo = new JSONObject();
        DAY_ID = Util.null2String(rs.getString("DAY_ID"));
        jo.put("Date",DAY_ID);

        if(count == 1){
            jo.put("beginTime",beginTime);
            jo.put("endTime","23:59");
            if(beginTime.compareTo("12:00")>0){
                jo.put("zccheck","0");
                jo.put("wuccheck","0");
            }else{
                jo.put("zccheck","1");
                jo.put("wuccheck","1");
            }
            jo.put("wanccheck","1");
            jo.put("zfcheck","1");
        }else if(count == countnum){
            jo.put("beginTime","00:00");
            jo.put("endTime",endTime);
            if(endTime.compareTo("12:00")<=0){
                jo.put("wanccheck","0");
                jo.put("wuccheck","0");
            }else{
                jo.put("wanccheck","1");
                jo.put("wuccheck","1");
            }
            jo.put("zccheck","1");
            jo.put("zfcheck","1");
        }else{
            jo.put("beginTime","00:00");
            jo.put("endTime","23:59");
            jo.put("zccheck","1");
            jo.put("wuccheck","1");
            jo.put("wanccheck","1");
            jo.put("zfcheck","1");
        }
        if("1".equals(SENDCAR)){
            jo.put("jtcheck","0");
        }else{
            jo.put("jtcheck","1");
        }
        //目的地
        String cityname = "";
        String TavelType = "";
        String ACTICITYNAME = "";//差旅方案名称
        sql_dt = "select TavelType,(select ACTICITYNAME from uf_fna_TavelType where ACTICITY=a.TavelType) as ACTICITYNAME,TavelDestCity,(select CITYNAME from ctrip_hotelcity where citystate = '1' and citykey=a.TavelDestCity) as cityname from "+tableName+"_dt1 a where mainid="+mainid+" and TavelBEGDA<'"+DAY_ID+"' and TavelENDDA>='"+DAY_ID+"'";
        rs_dt.execute(sql_dt);
        if(rs_dt.next()){
            TavelDestCity = Util.null2String(rs_dt.getString("TavelDestCity"));
            cityname = Util.null2String(rs_dt.getString("cityname"));
            TavelType = Util.null2String(rs_dt.getString("TavelType"));
            ACTICITYNAME = Util.null2String(rs_dt.getString("ACTICITYNAME"));

        }
        if("".equals(TavelDestCity)){
            sql_dt = "select TavelType,(select ACTICITYNAME from uf_fna_TavelType where ACTICITY=a.TavelType) as ACTICITYNAME,TavelDestCity,(select CITYNAME from ctrip_hotelcity where citystate = '1' and citykey=a.TavelDestCity) as cityname from "+tableName+"_dt1 a where mainid="+mainid+" and TavelBEGDA<='"+DAY_ID+"' and TavelENDDA>='"+DAY_ID+"'";
            rs_dt.execute(sql_dt);
            if(rs_dt.next()){
                TavelDestCity = Util.null2String(rs_dt.getString("TavelDestCity"));
                cityname = Util.null2String(rs_dt.getString("cityname"));
                TavelType = Util.null2String(rs_dt.getString("TavelType"));
                ACTICITYNAME = Util.null2String(rs_dt.getString("ACTICITYNAME"));

            }
        }

        jo.put("mdd",TavelDestCity);
        jo.put("cityname",cityname);
        jo.put("TavelType",TavelType);
        jo.put("ACTICITYNAME",ACTICITYNAME);
        //公出
        if("30".equals(TavelType)){
            jo.put("zccheck","0");
            jo.put("wuccheck","0");
            jo.put("wanccheck","0");
            jo.put("zfcheck","0");
            jo.put("jtcheck","0");
        }else if("20".equals(TavelType)){
            jo.put("zccheck","0");
            jo.put("wuccheck","0");
            jo.put("wanccheck","0");
        }
        int countzc = 0;
        sql_dt = "select count(1) as count from "+tableName+"_dt4  where mainid="+mainid+" and CtripBEGDA<'"+DAY_ID+"' and CtripENDDA>='"+DAY_ID+"' and nvl(Ctripgra,0)>0";
        rs_dt.execute(sql_dt);
        if(rs_dt.next()){
            countzc = rs_dt.getInt("count");
        }
        if(countzc>0){
            jo.put("zccheck","0");
            jo.put("haszbs","1");
        }else{
            jo.put("haszbs","0");
        }
        //国家
        String COUNTRYNAME = "";
        sql_dt = "select COUNTRYNAME from ctrip_hotelcity where citystate = '1' and citykey="+TavelDestCity;
        rs_dt.execute(sql_dt);
        if(rs_dt.next()){
            COUNTRYNAME = Util.null2String(rs_dt.getString("COUNTRYNAME"));
        }
        //城市别
        String rgion = "";
        sql_dt = "select rgion  from uf_fna_LOC where city="+TavelDestCity;
        rs_dt.execute(sql_dt);
        if(rs_dt.next()){
            rgion = Util.null2String(rs_dt.getString("rgion"));
        }
        String zc = "";
        String wuc = "";
        String wac = "";
        String zf = "";
        String jtf = "";
        String curr = "";//币别
        String hl = "";//汇率
        String currdesc = "";
        String szfid = "";
        sql_dt = "select nvl(F,0) as zc,nvl(M,0) as wuc ,nvl(A,0) as wac,nvl(BETFZ,0) as zf,curr,(select rate from uf_fna_Curr where curr=a.curr) as hl,(select currdesc from uf_fna_Curr where curr=a.curr) as currdesc,nvl(Trasubsidies,0) as jtf from uf_fna_BETFZ a " +
                "  where morei='"+MOREI+"' and country='"+COUNTRYNAME+"' and ACTICITY='"+TavelType+"' and RGION='"+rgion+"'" +
                "  and ergru='"+cldj+"' and begda<='"+DAY_ID+"' and endda>='"+DAY_ID+"'";
        rs_dt.execute(sql_dt);
        if(rs_dt.next()){
            zc = Util.null2String(rs_dt.getString("zc"));//早餐
            wuc = Util.null2String(rs_dt.getString("wuc"));//午餐
            wac = Util.null2String(rs_dt.getString("wac"));//晚餐
            zf = Util.null2String(rs_dt.getString("zf"));//杂费
            curr = Util.null2String(rs_dt.getString("curr"));//币别
            currdesc = Util.null2String(rs_dt.getString("currdesc"));//币别
            hl = Util.null2String(rs_dt.getString("hl"));
            jtf = Util.null2String(rs_dt.getString("jtf"));//交通费
        }
        jo.put("zc",zc);
        jo.put("wuc",wuc);
        jo.put("wac",wac);
        jo.put("zf",zf);
        jo.put("curr",curr);
        jo.put("currdesc",curr);
        jo.put("hl",hl);
        if(count>15){
            jo.put("cfzk","0.7");
        }else{
            jo.put("cfzk","1");
        }
        jo.put("jtf",jtf);
        ja.put(jo);
        count++;

    }

    out.print(ja.toString());






%>