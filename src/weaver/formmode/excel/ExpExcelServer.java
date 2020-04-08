/*
 * ExpExcelServer.java Created on 2017年1月5日 16:14:48 by cdy
 *
 * Copyright (c) 2001-2017 泛微软件, 版权所有.
 */
package weaver.formmode.excel;

import com.alibaba.fastjson.JSONObject;
import com.api.cube.bean.TabPane;
import com.api.cube.service.CubeSearchService;
import com.cloudstore.dev.api.bean.SplitPageBean;
import com.engine.cube.cmd.app.GetExportSetInfo;
import com.weaver.cssRenderHandler.CssRenderDeal;
import com.weaver.formmodel.util.StringHelper;
import sun.misc.BASE64Decoder;
import weaver.conn.ConnStatement;
import weaver.conn.ConnectionPool;
import weaver.conn.RecordSet;
import weaver.dateformat.TimeZoneVar;
import weaver.file.ExcelFile;
import weaver.file.ExcelRow;
import weaver.file.ExcelSheet;
import weaver.file.ExcelStyle;
import weaver.formmode.service.CommonConstant;
import weaver.formmode.virtualform.UUIDPKVFormDataSave;
import weaver.formmode.virtualform.VirtualFormHandler;
import weaver.general.*;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;
import weaver.interfaces.datasource.DataSource;
import weaver.servicefiles.DataSourceXML;
import weaver.systeminfo.SystemEnv;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Description: CreateExcelServer.java
 * 
 * @author dongping
 * @version 1.0 2005-4-21
 */
public class ExpExcelServer extends HttpServlet {
    private SplitPageParaBean spp = new SplitPageParaBean();
    private SplitPageUtil spu = new SplitPageUtil() ;
    private ExcelParaBean epb;

    private ArrayList headNameList = new ArrayList();
    private ArrayList columnList = new ArrayList();
    private ArrayList trasforMethodList = new ArrayList();
    private ArrayList otherparaList = new ArrayList();
    private List<Integer> colWidthList = new ArrayList<Integer>();
    private static final Object lock = new Object();
    private String modeCustomid;


	private static ConcurrentHashMap<String, String> userMap = new ConcurrentHashMap<String, String>();//管理用户时间
    private HashMap<String,HashMap<String, String>> browserMap = new HashMap<String, HashMap<String,String>>();

    /**
     *  
     */
    public ExpExcelServer() {
        super();
    }
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	ExecutorService executorService = ExpExcelUtil.getInstance().getExppool();
    	User user = HrmUserVarify.getUser (request , response) ;
    	try {
    	browserMap = new HashMap<String, HashMap<String,String>>();
        if(user == null)  return ;
        String showOrder = request.getParameter("showOrder");
        String iscustomsearch = Util.null2String(request.getParameter("iscustomsearch"));
        String from = request.getParameter("from");//tagtag
        modeCustomid = Util.null2String(request.getParameter("modeCustomid"));
        String dataKey = Util.null2String(request.getParameter("dataKey"));
        ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
        if("frommode".equals(Util.null2String(request.getParameter("comefrom")))){
        	epb = (ExcelParaBean) request.getSession(true).getAttribute("ExcelParaBeanForServlet_formmode_"+modeCustomid);
        	//导出前先加载下缓存
    		ModeCacheManager.getInstance().loadCacheNow("", modeCustomid, "");
        }else{
        	epb = (ExcelParaBean) request.getSession(true).getAttribute("ExcelParaBeanForServlet");
        }
        ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
		boolean isCustomExportField = isCustomExportField(modeCustomid);
		boolean isgroupexport=isgroupexport(modeCustomid);//是否分组导出
        if (epb == null&&!StringHelper.isEmpty(dataKey)){
        	try {
				SplitPageBean bean = new SplitPageBean(request, dataKey, "RootMap", "head", "sql");
				String pageUId = StringHelper.null2String(bean.getRootMap().getString("pageUid"));
				if(StringHelper.isEmpty(modeCustomid)&&!StringHelper.isEmpty(pageUId)){
					modeCustomid = pageUId.replace("mode_customsearch:", "");
				}
				//重新初始化epb
				epb = new ExcelParaBean();
				String copyWhere = "";
				JSONObject json = bean.getSql();
				String backfields = StringHelper.null2String(json.get("backfields"));
				String sqlorderby = StringHelper.null2String(json.get("sqlorderby"));
				String sqlprimarykey = StringHelper.null2String(json.get("sqlprimarykey"));
				String sqlwhere = StringHelper.null2String(json.get("sqlwhere"));
				if(isgroupexport){
					//当点击分组，去除最后一个分组带来的sql过滤。
					String[] sqlSplit = sqlwhere.split("and");
					String sqlFlag =sqlSplit[sqlSplit.length-1];
					if(!sqlFlag.toLowerCase().trim().equals("t1.id = t2.sourceid") && sqlFlag.indexOf("(") < 0){
						for(int i = 0; i < sqlSplit.length-1;i++){
							String sqlWhere  = sqlSplit[i];
							copyWhere += sqlWhere + " and ";
						}
						if(!copyWhere.equals("")){
							sqlwhere = copyWhere.substring(0,copyWhere.length()-4);
						}
					}
				}

				String sqlform = StringHelper.null2String(json.get("sqlform"));

				String sum = StringHelper.null2String(json.getString("sumColumns"));

				String poolname = "";
				if(json.containsKey("poolname")){
					poolname = StringHelper.null2String(json.get("poolname"));
				}
				ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
				Map tempMap = getExportFieldsHead(modeCustomid,user,isCustomExportField);
				List<Map<String,String>> resultList = (List<Map<String, String>>) tempMap.get("resultList");
				ArrayList headParaList = new ArrayList();
				for (int i = 0; i < resultList.size(); i++) {   
					Map<String,String> mapTemp  = resultList.get(i);
					String[] headParas = new String[5];
				    headParas[0] = mapTemp.get("text");
				    headParas[1] = mapTemp.get("column").toLowerCase();
				    headParas[2] = mapTemp.get("transmethod");
				    headParas[3] = mapTemp.get("otherpara");
				    headParas[4] = mapTemp.get("id");
				    headParaList.add(headParas);
				}
				ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
				if(sqlprimarykey.equals("t1.id,d_id")){
					sqlprimarykey = "t1.id";
				}
				
				epb.setHeadParaList(headParaList);
				epb.setPageIndex(1);
				epb.setPageSize(10);
				epb.setDataSource("");
				epb.setPoolname(poolname);
				epb.setParams("");
				epb.setCountColumns(sum);
				epb.setPageBySelf("");
				//epb.setCountColumnsDbType(new HashMap());
				//epb.setDecimalCV(new HashMap());
				//epb.setCountCV(new HashMap());
				
				epb.setBackFields(backfields);
				epb.setDistinct(false);
				epb.setPrimaryKey(sqlprimarykey);
				epb.setSqlFrom(sqlform);
				epb.setSqlWhere(sqlwhere);
				epb.setSqlOrderBy(sqlorderby);
				epb.setSqlGroupBy("");
				epb.setSortWay(1);
				/*修改导出Excel名称*/
				RecordSet rs = new RecordSet();
				rs.executeSql("select customname from mode_customsearch  where id=" + modeCustomid);
				String name ="";
				if(rs.next()){
				 	name =rs.getString("customname");
					if(name.indexOf("~`~`7")!=-1){
						name = Util.formatMultiLang(name,user.getLanguage()+"");
					}
				}
			    String  Lastname = user.getLastname();
			    Date date =new Date(); 
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			    String dateNowStr = sdf.format(date); 
				/*结束位置*/
				epb.setExcelFileName(name+"-"+Lastname+"-"+dateNowStr);
			} catch (Exception e) {
				throw new ServletException(SystemEnv.getHtmlLabelName(503398,user.getLanguage()));//初始化ExcelParaBean出现错误
			}
        }
        if (epb == null){
        	throw new ServletException("ExcelParaBeanForServlet Bean does not exist in session");//ExcelParaBeanForServlet Bean没有在seesion在存在
        }
        
        ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
        ArrayList headParaList = epb.getHeadParaList();//7
        int pageIndex = epb.getPageIndex();//1
        int pageSize = epb.getPageSize();//10
        String dataSource = epb.getDataSource();//""
        String poolname = epb.getPoolname();//""
        String params = epb.getParams();//""
        String countColumns = epb.getCountColumns();//""
        String pageBySelf = epb.getPageBySelf();//""
       // Map<String, Integer> countColumnsDbType = epb.getCountColumnsDbType();//{}
		Map<String,Integer>	countColumnsDbType = this.getCountColumnsDbType(countColumns,epb.getSqlFrom());
        //Map<String, String> decimalCV = epb.getDecimalCV();//{}
        //Map<String,String> countCV = epb.getCountCV();//{}

		String backfields = getExportFieldsHead(modeCustomid,isCustomExportField);
        spp.setBackFields(backfields);// t1.id,t1.formmodeid,t1.modedatacreater,t1.modedatacreatertype,t1.modedatacreatedate,t1.modedatacreatetime ,t1.bt,t1.bm,t1.cl,t1.ls
        spp.setDistinct(epb.getIsDistinct());//false
        spp.setPrimaryKey(epb.getPrimaryKey());//t1.id
        spp.setSqlFrom(epb.getSqlFrom());// from uf_llkcs t1 , (SELECT  sourceid,MAX(sharelevel) AS sharelevel from (  ((SELECT  sourceid,MAX(sharelevel) AS sharelevel from modeDataShare_686 where((type=1 and content=1) or  (type=4 and content in (12,22,32,52,62,72,82,92,102,112,122,132,142,152,162,172,182,192,202,212,222,392,340,440,42) and seclevel<=30 and 30<=  isnull(showlevel2,9999) and sharesetid is null) or  (type=4 and content=1 and sharesetid is not null) or (type=6 and content=-1 and seclevel<=30 and 30<=  isnull(showlevel2,9999) and ((joblevel=2) or (joblevel=0 and ','+jobleveltext+',' like '%,0,%') or (joblevel=1 and ','+jobleveltext+',' like '%,0,%'))) or (type=7 and content=1 ) or (type=5 and content=1 and seclevel<=30 and 30<=  isnull(showlevel2,9999))) GROUP BY sourceid ) union (SELECT  sourceid,MAX(sharelevel) AS sharelevel from modeDataShare_686 where((type=1 and content=1) or  (type=4 and content in (12,22,32,52,62,72,82,92,102,112,122,132,142,152,162,172,182,192,202,212,222,392,340,440,42) and seclevel<=30 and 30<=  isnull(showlevel2,9999) and sharesetid is null) or  (type=4 and content=1 and sharesetid is not null) or (type=6 and content=-1 and seclevel<=30 and 30<=  isnull(showlevel2,9999) and ((joblevel=2) or (joblevel=0 and ','+jobleveltext+',' like '%,-6,%') or (joblevel=1 and ','+jobleveltext+',' like '%,-9,%'))) or (type=7 and content=1 ) or (type=5 and content=1 and seclevel<=30 and 30<=  isnull(showlevel2,9999))) GROUP BY sourceid ) union (SELECT  sourceid,MAX(sharelevel) AS sharelevel from modeDataShare_686 where((type=1 and content=1) or  (type=4 and content in (12,22,32,52,62,72,82,92,102,112,122,132,142,152,162,172,182,192,202,212,222,392,340,440,42) and seclevel<=30 and 30<=  isnull(showlevel2,9999) and sharesetid is null) or  (type=4 and content=1 and sharesetid is not null) or (type=6 and content=-1 and seclevel<=30 and 30<=  isnull(showlevel2,9999) and ((joblevel=2) or (joblevel=0 and ','+jobleveltext+',' like '%,-8,%') or (joblevel=1 and ','+jobleveltext+',' like '%,-10,%'))) or (type=7 and content=1 ) or (type=5 and content=1 and seclevel<=30 and 30<=  isnull(showlevel2,9999))) GROUP BY sourceid ) )  ) temptable group by temptable.sourceid)  t2 
        spp.setSqlWhere(epb.getSqlWhere());// where t1.formmodeid = 686  and t1.id = t2.sourceid
		String ids = Util.null2String(request.getParameter("ids"));//选中的id
		BASE64Decoder decoder = new BASE64Decoder();
		ids = new String(decoder.decodeBuffer(ids),"UTF-8");
		if(ids.length()>0){//勾选导出
			ids = ids.replaceAll(",", "','");
			String sqlwhere = epb.getSqlWhere()+" and "+spp.getPrimaryKey()+" in ('"+ids+"')";
			spp.setSqlWhere(sqlwhere);
		}
        spp.setSqlOrderBy(epb.getSqlOrderBy());//t1.id desc
        spp.setSqlGroupBy(epb.getSqlGroupBy());//""
		spp.setCountColumns(countColumns);	//统计项
        spp.setSortWay(epb.getSortWay());//1
        spp.setPoolname(epb.getPoolname());//""
		spp.setCountColumnsDbType(countColumnsDbType);
        spu.setSpp(spp);
        headNameList.clear();
        columnList.clear();
        trasforMethodList.clear();
        otherparaList.clear();
        colWidthList.clear();
        ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
		Map<String,String> columnMap = new HashMap<>();
		Map<String,String> headNameMap = new HashMap<>();
		Map<String,String> trasforMethodMap = new HashMap<>();
		Map<String,String> otherparaMap = new HashMap<>();
        for (int i = 0; i < headParaList.size(); i++) {
        	String headName = ((String[]) headParaList.get(i))[0];
			if(headName.indexOf("~`~`7")!=-1){
        		headName = Util.formatMultiLang(headName,user.getLanguage()+"");
        	}
        	String headParaName = ((String[]) headParaList.get(i))[1];
        	if(!"idskey_forused".equals(headName)){
				String[] temp = (String[]) headParaList.get(i);
				if(!isCustomExportField){//不是自定义导出字段
					columnList.add(headParaName);
					headNameList.add(headName);
					trasforMethodList.add(temp[2]);
					otherparaList.add(temp[3]);
				}else{
					columnMap.put(temp[4],headParaName);
					headNameMap.put(temp[4],headName);
					trasforMethodMap.put(temp[4],temp[2]);
					otherparaMap.put(temp[4],temp[3]);
				}
        	}
        }
        if(isCustomExportField){//自定义导出字段:按照导出字段设置进行排序
			List<String> list = getExportOrder(modeCustomid,user);
			Map<String,Integer> colWidth = getColWidth(modeCustomid);
			for(int i=0;i<list.size();i++){
				String fieldid = list.get(i);
				columnList.add(columnMap.get(fieldid));
				headNameList.add(headNameMap.get(fieldid));
				trasforMethodList.add(trasforMethodMap.get(fieldid));
				otherparaList.add(otherparaMap.get(fieldid));
				if(colWidth.containsKey(fieldid)){
					colWidthList.add(colWidth.get(fieldid));
				}else{
					colWidthList.add(9);
				}
			}
		}
        ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
        //create Excel
        ExcelFile ef = new ExcelFile();
        boolean customStyle = isCustomStyle(modeCustomid);//是否自定义样式
		String primaryKeyFieldName = epb.getPrimaryKey();
		if(primaryKeyFieldName.contains(".")){
			primaryKeyFieldName = primaryKeyFieldName.substring(primaryKeyFieldName.indexOf(".")+1);
		}
        //分组导出
        Map<String,String> sheets = getSheets(modeCustomid,epb.getExcelFileName(),user);
        String sqlWhere = spp.getSqlWhere();
		for(Map.Entry<String,String> entry: sheets.entrySet()){
			String value = entry.getValue();
			ef.setFilename(epb.getExcelFileName());
			ExcelSheet es = new ExcelSheet();
			ef.addSheet(value, es);

			String key = entry.getKey();
			String sqlWhere2 = getExportSqlWhere(modeCustomid,key,sqlWhere);
			spp.setSqlWhere(sqlWhere2);
			epb.setSqlWhere(sqlWhere2);

			ExcelStyle est = ef.newExcelStyle("Header") ;
			est.setGroundcolor(ExcelStyle.WeaverHeaderGroundcolor1) ;
			est.setFontcolor(ExcelStyle.WeaverHeaderFontcolor) ;
			est.setFontbold(ExcelStyle.WeaverHeaderFontbold) ;
			est.setAlign(ExcelStyle.WeaverHeaderAlign) ;

			ExcelStyle estDark = ef.newExcelStyle("dark") ;
			estDark.setGroundcolor(ExcelStyle.WeaverDarkGroundcolor) ;
			estDark.setFontcolor(ExcelStyle.WeaverHeaderFontcolor) ;
			estDark.setAlign(ExcelStyle.WeaverHeaderAlign) ;

			ExcelStyle estLight = ef.newExcelStyle("light") ;
			estLight.setGroundcolor(ExcelStyle.WeaverLightGroundcolor) ;
			estLight.setFontcolor(ExcelStyle.WeaverHeaderFontcolor) ;
			estLight.setAlign(ExcelStyle.WeaverHeaderAlign) ;
			String styleName ="";
			ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
			int rowindex=1;
			Map<String, String> decimalCV = this.getDecimalCV(countColumns,epb.getSqlFrom());
			Map<String,String> countCV = this.getCountCV(countColumns);
			List<Map<String,String>> datas = this.getData(request, response, user, dataSource, poolname, pageIndex, pageSize, params, countColumns, pageBySelf, showOrder);
			ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
			datas.add(new HashMap<String,String>());
			int rows =datas.size();
			
			if(!"".equals(countColumns)&& rows>0){
				rows = rows+1;
			}
			es.initRowList(rows+1);
			ExcelRow headfield = es.newExcelRow(0);
			headfield.addStringValue("测试啊啊啊");
			ExcelRow erHead = es.newExcelRow(1);
			//统计字段是否第一列
			boolean flag = false;
			String[] countColumnArr = countColumns.toLowerCase().split(",");
			if(columnList.size()>0&&countColumnArr[0].equals((String) columnList.get(0))){
				flag = true;
			}
			if(flag){
				erHead.addStringValue("","Header");
			}
			ArrayList<Map<String,Object>> styleList = new ArrayList<>();
			Map<String,Object> headerStyle = getHeaderStyle(modeCustomid);
			for (int i = 0; i < headNameList.size(); i++) {
				Map<String,Object> map = new HashMap<>();
				map.putAll(headerStyle);
				styleList.add(map);
				erHead.addStringValue((String) headNameList.get(i),"Header");
				if(isCustomExportField){
					es.addColumnwidth(colWidthList.get(i));
				}
			}
			erHead.setStyleList(styleList);
			es.addExcelRow(erHead);
			ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态

			Map<String,String> qfwsMap0 = new ConcurrentHashMap<String,String>();
			Map<String,String> qfwsMap1 = new ConcurrentHashMap<String,String>();
			Map<String,String> selectMap = new ConcurrentHashMap<String,String>();
			Map<String,Map<String,String>> barcodeMap=new ConcurrentHashMap<String,Map<String,String>>();;
			CountDownLatch doneSignal = null;
			if(datas.size()<100){//小于100条时一律使用1个线程
				doneSignal = new CountDownLatch(1);//使用计数栅栏
				executorService.submit(new ExpExcelWriter(doneSignal, 1, datas.size(),datas,countColumns,ef,es,erHead,flag,headNameList,columnList,trasforMethodList,otherparaList,showOrder,iscustomsearch,from,modeCustomid,user,countColumnArr,countColumnsDbType,decimalCV,countCV,browserMap,qfwsMap0,qfwsMap1,selectMap,barcodeMap,SystemEnv.getHtmlLabelName(17416,user.getLanguage())+" ALL",customStyle,primaryKeyFieldName,TimeZoneVar.getTimeZone()));//导出ALL
			}else{
				int threadNum = 2;//最多使用2个线程导出同一个excel
				doneSignal = new CountDownLatch(threadNum);//使用计数栅栏
				int d = (datas.size()-datas.size()%threadNum)/threadNum;
				for(int i=0;i<threadNum;i++){
					int start = i*d;
					int end  = (i+1)*d;
					if(i==threadNum-1){
						end = datas.size();
					}
					executorService.submit(new ExpExcelWriter(doneSignal, start+1, end,datas,countColumns,ef,es,erHead,flag,headNameList,columnList,trasforMethodList,otherparaList,showOrder,iscustomsearch,from,modeCustomid,user,countColumnArr,countColumnsDbType,decimalCV,countCV,browserMap,qfwsMap0,qfwsMap1,selectMap,barcodeMap,SystemEnv.getHtmlLabelName(17416,user.getLanguage())+i,customStyle,primaryKeyFieldName,TimeZoneVar.getTimeZone()));//导出
				}
			}
			ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
			/**
			 * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
			 */
			doneSignal.await();
			//由于是静态线程池，所以不能关闭线程池
//			executorService.shutdown();
			ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
		}
		request.getSession(true).setAttribute("ExcelFile", ef);
		UUIDPKVFormDataSave UUIDPKVFormDataSave = new UUIDPKVFormDataSave();
		String key = StringHelper.null2String(UUIDPKVFormDataSave.generateID(null)).replace("-", "");
		ExpExcelUtil.refreshExpUserState(user.getUID());//刷新状态
		if("frommode".equals(Util.null2String(request.getParameter("comefrom")))){
			ExpExcelUtil expExcelUtil = ExpExcelUtil.getInstance();
			expExcelUtil.setExcelFile(key, ef);
			response.sendRedirect("/weaver/weaver.file.ExcelOutForFormMode?k="+key+"&customid="+modeCustomid+"&isCustomStyle="+customStyle);
		}else{
			response.sendRedirect("/weaver/weaver.file.ExcelOut");
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			ExpExcelUtil.removeExpUserState(user.getUID());//导出完毕，移除用户状态
		}
    }

	/**
	 * qc451484
	 * 获取统计字段的统计值
	 * @param countColumns
	 * @return
	 */
    private Map<String,String> getCountCV(String countColumns){
    	Map<String,String> countCV = new HashMap<String,String>();
		RecordSet rs = new RecordSet();
    	if(!"".equals(countColumns)){
			rs = spu.getAllRs(true);
			while(rs.next()){
				countCV=CssRenderDeal.recordSet2Map(rs);
			}
		}
		return countCV;
	}

	/**
	 * 得到表头样式
	 * @param customId
	 * @return
	 */
	private Map<String,Object> getHeaderStyle(String customId){
    	Map<String,Object> styleMap = new HashMap<>();
    	RecordSet rs = new RecordSet();
		//查询列表自定义导出样式
		String sql = "select headerbg,headerfontcolor,headerfont,headerfontsize,headertextalign,gridlinewidth,gridlinecolor from mode_exportcustomstyle where customid=?";
		rs.executeQuery(sql,customId);
		if(rs.next()){
			String headerbg = Util.null2s(rs.getString("headerbg"),"#808080");;
			styleMap.put("headerbg",ExpExcelUtil.getNearestColour(headerbg));
			String headerfontcolor = Util.null2s(rs.getString("headerfontcolor"),"#000000");
			styleMap.put("headerfontcolor",ExpExcelUtil.getNearestColour(headerfontcolor));
			String headerfont =  Util.null2s(rs.getString("headerfont"),"Arial");
			styleMap.put("headerfont",headerfont);
			String headerfontsize = Util.null2s(rs.getString("headerfontsize"),"10");
			styleMap.put("headerfontsize",headerfontsize);
			String headertextalign = Util.null2s(rs.getString("headertextalign"),"2");
			styleMap.put("headertextalign",headertextalign);

			String gridlinewidth = Util.null2s(rs.getString("gridlinewidth"),"1");
			styleMap.put("gridlinewidth",gridlinewidth);
			String gridlinecolor = Util.null2s(rs.getString("gridlinecolor"),"#000000");
			styleMap.put("gridlinecolor",ExpExcelUtil.getNearestColour(gridlinecolor));
		}
    	return styleMap;
	}


	/**
	 * qc451484
	 * 获取统计字段类型，若为千分位则为“1”
	 * @param countColumns
	 * @param sqlFrom
	 * @return
	 */
	private Map<String,Integer> getCountColumnsDbType(String countColumns,String sqlFrom){
		Map<String,Integer> countColumnsDbType = new HashMap<String,Integer>();
    	String[] fromSql = sqlFrom.split(" ");
		RecordSet rs = new RecordSet();
		//System.out.println(fromSql);
		String tabelname = fromSql[1];
		String tableId = "";
		rs.executeSql("SELECT id FROM workflow_bill WHERE tablename='"+tabelname+"'");
		while (rs.next()){
			tableId = rs.getString("id");
		}
		if("".equals(tableId)){
			rs.executeSql("SELECT formid FROM mode_customsearch WHERE id="+modeCustomid);
			while(rs.next()){
				tableId = rs.getString("formid");
			}
		}
		String[] countColumns1 = countColumns.split(",");
		for(String s : countColumns1){
			String s1= s;
			if(s.contains("d_")){
				s =s.substring(2);
			}
			rs.executeSql("SELECT fieldhtmltype,type FROM workflow_billfield WHERE fieldname='"+s+"' AND billid="+tableId);
			while (rs.next()){
				if(rs.getString("fieldhtmltype").equals("1")){
					if(rs.getString("type").equals("5")){
						countColumnsDbType.put(s1.toLowerCase(),1);
					}else{
						countColumnsDbType.put(s1.toLowerCase(),0);
					}
				}
			}
		}
		return countColumnsDbType;
	}

	/**
	 * qc451484
	 * 获取统计字段的小数位数
	 * @param countColumns
	 * @param sqlFrom
	 * @return
	 */
	private Map<String,String> getDecimalCV(String countColumns,String sqlFrom){
		Map<String,String> decimalCV = new HashMap<String,String>();

		String[] fromSql = sqlFrom.split(" ");
		RecordSet rs = new RecordSet();
		//System.out.println(fromSql);
		String tabelname = fromSql[1];
		String tableId = "";
		rs.executeSql("SELECT id FROM workflow_bill WHERE tablename='"+tabelname+"'");
		while (rs.next()){
			tableId = rs.getString("id");
		}
		if("".equals(tableId)){
			rs.executeSql("SELECT formid FROM mode_customsearch WHERE id="+modeCustomid);
			while(rs.next()){
				tableId = rs.getString("x");
			}
		}
		String detailTableName = "";
		if(fromSql.length >= 5) detailTableName = fromSql[5];
		String[] countColumns1 = countColumns.split(",");
		for(String s : countColumns1) {
			String qfws = "";
			String sql = "SELECT qfws,fieldhtmltype,type FROM workflow_billfield WHERE fieldname='" + s + "' AND billid=" + tableId;
			rs.executeSql(sql);
			boolean flag = rs.next();
			if(!flag){
				String fieldname = "";
				if(s.indexOf("d_") > -1){
					fieldname = s.substring(2,s.length());
				}
				if("".equals(detailTableName)){
					sql = "SELECT qfws,fieldhtmltype,type FROM workflow_billfield WHERE fieldname='" + fieldname + "' AND billid=" + tableId;
				}else{
					sql = "SELECT qfws,fieldhtmltype,type FROM workflow_billfield WHERE fieldname='" + fieldname + "' AND billid=" + tableId+" and detailtable = '"+detailTableName+"'";
				}
				rs.executeSql(sql);
				flag = rs.next();
			}
			if (flag){
				if(rs.getString("fieldhtmltype").equals("1")){
					if(rs.getString("type").equals("4")){
						decimalCV.put(s.toLowerCase(),"%.2f");
					}else{
						if(rs.getString("qfws") != null && !rs.getString("qfws").equals("")){
							qfws = "%."+rs.getString("qfws")+"f";
							decimalCV.put(s.toLowerCase(),qfws);
						}
					}
				}
			}
		}
		return decimalCV;
	}
	/**
	 * 得到excel列宽
	 * @param customId
	 * @return
	 */
	public Map<String,Integer> getColWidth(String customId){
		Map<String,Integer> colWidth = new HashMap<String,Integer>();
		RecordSet rs = new RecordSet();
		String sql = "select wbf.id,mef.colwidth from mode_exportexcelfield mef,mode_exportexcelset me,workflow_billfield wbf where mef.customid=me.customid and me.isexportfield=1 and mef.isexport=1 and wbf.id=mef.fieldid and mef.customid=? " +
				" union select mef.fieldid,mef.colwidth from mode_exportexcelfield mef,mode_exportexcelset me where  mef.customid=me.customid and me.isexportfield=1 and mef.isexport=1 and mef.customid=? and mef.fieldid in(-1,-2,-3,-4,-5)";
		rs.executeQuery(sql,customId,customId);
		while(rs.next()){
			String fieldname = rs.getString("id");
			int colwidth = Util.getIntValue(rs.getString("colwidth"),9);
			colWidth.put(fieldname,colwidth);
		}
		return colWidth;
	}
    /**
     * 获取查询数据
     * @param request
     * @param response
     * @param user
     * @param dataSource
     * @param poolname
     * @param pageIndex
     * @param pageSize
     * @param params
     * @param countColumns
     * @param pageBySelf
     * @return
     */
    private List<Map<String,String>> getData(HttpServletRequest request, HttpServletResponse response,User user,String dataSource,String poolname,int pageIndex,int pageSize,String params,String countColumns,String pageBySelf,String showOrder)
    {
    	RecordSet rs = new RecordSet();
    	List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    	if((dataSource.equals("")||dataSource.equalsIgnoreCase("null")) && (poolname.equals("")||poolname.equalsIgnoreCase("null"))){
            if ("all".equalsIgnoreCase(showOrder)) {
                //rs = spu.getAllRs();
                String sql = spu.getAllPage(false);
                ConnStatement connStatement=new ConnStatement();
                try {
					connStatement.setStatementSql(sql);
					connStatement.executeQuery();
					while(connStatement.next()){
						data.add(ExpExcelServer.ConnStatement2Map(connStatement));
	            	}
				} catch (Exception e) {
					rs.writeLog("建模查询列表导出，查询数据异常");
					rs.writeLog(e);
				} finally{
					connStatement.close();
				}
            } else {
            	rs = spu.getCurrentPageRsNew(pageIndex, pageSize);
            }
        	while(rs.next()){
        		data.add(CssRenderDeal.recordSet2Map(rs));
        	}
        }else if(!dataSource.equals("") && !dataSource.equalsIgnoreCase("null")){//非数据库数据源分页处理
        	//反射调用具体类得到数据
        	String className = dataSource.substring(0,dataSource.lastIndexOf("."));
        	String methodName = dataSource.substring(dataSource.lastIndexOf(".")+1);
        	
        	try{
        		Class<?> cls = Class.forName(className);
        		Object bds = cls.newInstance();
        		Map<String,String> paramsMap = new HashMap<String,String>();
        		if(params!=null && !params.trim().equals("") && !params.trim().equalsIgnoreCase("null")){
        			String[] paramsArr = params.split("\\+");
        			for(int i=0;i<paramsArr.length;i++){
        				String[] kv = paramsArr[i].split(":");
        				String para1 = kv[0];
        				String para2 = "";
        				if(kv.length>=2){
        					para2 = kv[1];
        				}
        				paramsMap.put(Util.null2String(para1), Util.null2String(para2));
        			}
        		}
        		paramsMap.put("pageSize", ""+pageSize);
        		paramsMap.put("pageIndex", ""+pageIndex);
        		paramsMap.put("countColumns", countColumns);
        		paramsMap.put("showOrder", showOrder);
        		Method method = bds.getClass().getDeclaredMethod(methodName, User.class,Map.class,HttpServletRequest.class,HttpServletResponse.class);
        		if(pageBySelf.equalsIgnoreCase("true")){//自行控制分页
        			Map<String,Object> dataAll = (Map<String,Object>)method.invoke(bds, user,paramsMap,request,response);
        			if(dataAll==null){
    	            	return null;
            		}
        			data = (List<Map<String,String>>)dataAll.get("dataAll");
        		}else{
        			List<Map<String,String>> dataAll = (List<Map<String,String>>)method.invoke(bds, user,paramsMap,request,response);
        			if(dataAll==null){
            			return null;
            		}
            		int recordCount = dataAll.size();
            		if ("all".equalsIgnoreCase(showOrder)) {
            			data = dataAll;
                    } else {
                    	for(int i=(pageIndex-1)*pageSize;i<pageIndex*pageSize&&i<recordCount;i++){
                			data.add(dataAll.get(i));
                		}
                    }
            		
        		}
        		
        	}catch(Exception e){
        		return null;
        	}
        }else{//非数据库数据源分页处理
        	//反射调用具体类得到数据
        	//由于外部数据源-修改为配置页面配置处理--此处需修改业务
        	///----------------------------

    		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
    		if(DataSourceXML.SYS_LOCAL_POOLNAME.equals(poolname)){
    			ConnectionPool cp = ConnectionPool.getInstance();
    			List list = this.getSplitPageXmlByConn(cp.getConnection(), spu, rs.getDBType(), pageIndex,pageSize,showOrder);
    			int count = 0;
		        List<Map<String,String>> dataAll = new ArrayList<Map<String,String>>();
		        if(list.size()>0){
		        	count = Util.getIntValue(list.get(0)+"");
		        	dataAll = (List<Map<String,String>>)list.get(1);
		        }
		        // modify by huguomin 20170823	虚拟表单无数据导出无数据时,返回null
		        if(count==0){
	           	 return data = new ArrayList<Map<String,String>>();
	            	//return null;
        		}
        		data = dataAll;
    		}else{
    			//RecordSetDataSource rsds = new RecordSetDataSource(poolname);
    			
    			//Connection connection = baseDataSource.getConnection();
    			DataSource datasource = (DataSource) StaticObj.getServiceByFullname("datasource."+poolname, DataSource.class);
    			Connection connection = datasource.getConnection();
    			String dbtype = datasource.getType();
		        List list = this.getSplitPageXmlByConn(connection, spu, dbtype, pageIndex,pageSize,showOrder);
		        int count = 0;
		        List<Map<String,String>> dataAll = new ArrayList<Map<String,String>>();
		        if(list.size()>0){
		        	count = Util.getIntValue(list.get(0)+"");
		        	dataAll = (List<Map<String,String>>)list.get(1);
		        }
		        if(count==0){
		        	return data = new ArrayList<Map<String,String>>();
        		}
        		data = dataAll;
    		}
        }
    	return data;
    }
    public List getSplitPageXmlByConn(Connection connection,SplitPageUtil spu,String dbtype,int pageIndex,int pageSize,String showOrder){
    	List list = new ArrayList();
    	java.sql.PreparedStatement psm = null;
        ResultSet rs1 = null;
        try {
        	String countSql = spu.getCountSql();
        	psm= connection.prepareStatement(countSql);
        	rs1 = psm.executeQuery();
        	int count = 0;
        	if(rs1.next()){
        		count = rs1.getInt(1);
        	}
        	if(count>0){
        		String dbtypeStr = "";
        		if(dbtype.toLowerCase().indexOf("sqlserver")!=-1){
        			dbtypeStr = "sqlserver";
        		}else if(dbtype.toLowerCase().indexOf("db2")!=-1){
        			dbtypeStr = "db2";
        		}else if(dbtype.toLowerCase().indexOf("mysql")!=-1){
        			dbtypeStr = "mysql";
        		}else{
        			dbtypeStr = "oracle";
        		}
        		spu.setRecordCount(count);
        		String sql = "";
        		if ("all".equalsIgnoreCase(showOrder)) {
        			sql = spu.getAllPage(false);
                } else {
                	sql = spu.getPageSqlByDBType(pageSize, pageIndex, dbtypeStr);
                }
        		//System.out.println(sql);
        		psm= connection.prepareStatement(sql);
        		rs1 = psm.executeQuery();
        		List<Map<String,String>> dataAll = new ArrayList<Map<String,String>>();
        		if(rs1!=null){
        			ResultSetMetaData md = rs1.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等
        			int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数
        			Map rowData = new HashMap();   
        			while (rs1.next()) {   
        				rowData = new HashMap(columnCount);   
        				for (int j = 1; j <= columnCount; j++) {   
        					if(dbtypeStr.equals("oracle")){
        						if(md.getColumnType(j)==java.sql.Types.CLOB){
            						String clobString=null; 
            						Clob clob=rs1.getClob(j);        
            						if(clob!=null&&(int)clob.length()>0){
            							clobString=clob.getSubString((long)1,(int)clob.length());                   
            						}
            						rowData.put(md.getColumnName(j).toLowerCase(), clobString);  
            					}else{
            						rowData.put(md.getColumnName(j).toLowerCase(), rs1.getObject(j));   
            					}
        					}else{
        						rowData.put(md.getColumnName(j).toLowerCase(), rs1.getObject(j));  
        					}  
        				}   
        				dataAll.add(rowData);   
        			}   
        		}
        		list.add(count);
        		list.add(dataAll);
        	}
        	
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if(rs1!=null){
					rs1.close();
				}
				if(psm!=null){
					psm.close();
				}
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        if(list.size()==0){
        	list.add(0);
        	list.add(null);
        }
    	return list;
    	
    }

    public Map<String,String> getSheets(String customId,String defaultSheetName,User user){
		Map<String,String> sheets = new LinkedHashMap<>();
		String sql = "select mcf.fieldid,wbf.fieldname,wbf.viewtype from mode_exportexcelset mes,mode_customdspfield mcf,workflow_billfield wbf where mes.customid=mcf.customid and wbf.id=mcf.fieldid and mes.isgroupexport=1 and mcf.isgroup=1 and mes.customid=?";
		RecordSet rs = new RecordSet();
		rs.executeQuery(sql,customId);
		String groupFieldId = "";//分组字段id
		String fieldname = "";//
		if(rs.next()){
			groupFieldId = rs.getString("fieldid");
			fieldname = rs.getString("fieldname");
			String viewtype = rs.getString("viewtype");
			if(viewtype.equals("1")){//明细表
				fieldname = "d1."+fieldname;
			}else{
				fieldname = "t1."+fieldname;
			}
		}
		if("".equals(groupFieldId)) {//没有开启分组导出，或者没有分组字段
			if(defaultSheetName.indexOf("~`~`7")!=-1){
				defaultSheetName = Util.formatMultiLang(defaultSheetName,user.getLanguage()+"");
			}
			sheets.put("all",defaultSheetName);//全部
		}else{
			//查询是否设置分组顺序
			CubeSearchService cubeSearchService = new CubeSearchService();
			List<TabPane> tabs = cubeSearchService.getSearchGroupTabs(customId,user);
			for(int i=0;i<tabs.size();i++){
				TabPane tab = tabs.get(i);
				String sheetName = tab.getTitle();
				if(sheetName.indexOf("~`~`7")!=-1){
					sheetName = Util.formatMultiLang(sheetName,user.getLanguage()+"");
				}
				sheets.put(tab.getKey(),sheetName);
			}
			sql = "select count(1) "+epb.getSqlFrom()+" "+epb.getSqlWhere()+" and "+fieldname+" is null";
			rs.executeQuery(sql);
			if(rs.next()){
				int count = rs.getInt(1);
				if(count<=0){
					sheets.remove("empty");
				}
			}
		}
    	return sheets;
	}
	/**
	 * 得到SQLwhere
	 * @param customId 查询列表id
	 * @param selectValue 选择框的值
	 * @param sqlWhere
	 * @return 重组后的sqlwhere
	 */
	public String getExportSqlWhere(String customId,String selectValue,String sqlWhere){
		String groupSqlWhere = "";
		RecordSet rs = new RecordSet();
		String sql = "select wb.fieldname,wb.viewtype from mode_exportexcelset mes,mode_customdspfield mcf,workflow_billfield wb where wb.id=mcf.fieldid and mes.customid=mcf.customid and mes.isgroupexport=1 and mcf.isgroup=1 and mes.customid=?";
		rs.executeQuery(sql,customId);
		if(rs.next()){
			String fieldname = rs.getString("fieldname");
			String viewtype = rs.getString("viewtype");
			String tablename = "t1";
			if("1".equals(viewtype)){//明细表
				tablename = "d1";
			}
			if(selectValue.equalsIgnoreCase("empty")){//未分组
				if(selectValue.length()>0){
					groupSqlWhere = "("+tablename+"."+fieldname+"  is null)";
				}else{
					groupSqlWhere = "";//分组下拉框的值为空
				}
			}else if(selectValue.equals("all")){//全部
				groupSqlWhere = " 1=1 ";
			}else{
				groupSqlWhere = tablename+"."+fieldname+"="+selectValue;
			}
			if(!Util.null2String(sqlWhere).trim().endsWith("and")){
				sqlWhere+=" and ";
			}
		}
		sqlWhere += groupSqlWhere;
		return sqlWhere;
	}

	/**
	 * 得到导出字段
	 * @param customId
	 * @return
	 */
	public String getExportFieldsHead(String customId,boolean isCustomExportField){
		String backfields = null;
		if(isCustomExportField){//自定义导出字段
			StringBuffer fields = new StringBuffer();
			fields.append(epb.getPrimaryKey());
			RecordSet recordSet = new RecordSet();//得到所有的字段
			String sql = "select wbf.fieldname,wbf.viewtype from mode_exportexcelfield me,workflow_billfield wbf where me.fieldid=wbf.id and me.customid="+customId;
			recordSet.execute(sql);
			while(recordSet.next()){
				String fieldname = recordSet.getString("fieldname");
				String viewtype = recordSet.getString("viewtype");
				if("1".equals(viewtype)){//明细表字段
					fields.append(",d1."+fieldname+" as d_"+fieldname);
				}else{//主表字段
					fields.append(",t1."+fieldname);
				}
			}
			GetExportSetInfo gesi = new GetExportSetInfo(null,null);
			int customid = Integer.parseInt(customId);
			if(gesi.hasField(customid,"modedatacreatedate")){
				fields.append(",t1.modedatacreatedate");
			}
			if(gesi.hasField(customid,"modedatacreater")){
				fields.append(",t1.modedatacreater");
			}
			if(gesi.isOpenLabel(customid)){
				fields.append(",t1.modelableid");
			}
			if(gesi.hasField(customid,"modedatastatus")){
				fields.append(",t1.modedatastatus");
			}
			backfields = fields.toString();
		}else{
			backfields = epb.getBackFields();
		}
		return backfields;
	}


	/**
	 * 得到导出字段
	 * @param customId
	 * @param user
	 * @return
	 */
    public Map getExportFieldsHead(String customId,User user,boolean isCustomExportField){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		RecordSet rs = new RecordSet();
		if(!isCustomExportField){//没有开启自定义导出字段开关
			return getHeadParam(customId,user);
		}else{
			//自定义导出字段
			String sql = "select a.*,b.tablename,b.detailkeyfield from mode_customsearch a left join workflow_bill b on a.formid=b.id where a.id=?";
			rs.executeQuery(sql,customId);
			boolean isVirtualForm = false;
			if(rs.next()) {
				String isbill = "1";
				String viewtype = "0";
				String opentype = "0";
				String enabled = "0";
				String istitle = "0";
				String showmethod = "0";
				String ismaplocation = "0";
				String vdatasource = "";
				String vprimarykey = "";
				String detailfieldAlias="d_";
				String formID = Util.null2String(rs.getString("formid"));
				String modeId = "" + Util.getIntValue(rs.getString("modeid"), 0);
				isVirtualForm = VirtualFormHandler.isVirtualForm(formID);    //是否是虚拟表单
				Map<String, Object> vFormInfo = new HashMap<String, Object>();
				if(isVirtualForm){
					vFormInfo = VirtualFormHandler.getVFormInfo(formID);
					vdatasource = Util.null2String(vFormInfo.get("vdatasource"));	//虚拟表单数据源
					vprimarykey = Util.null2String(vFormInfo.get("vprimarykey"));	//虚拟表单主键列名称
				}
				RecordSet rs2 = new RecordSet();
				sql = " select t.id,t.fieldname name,CASE WHEN "+ CommonConstant.DB_ISNULL_FUN+"(mcdf.shownamelabel,0)=0 THEN t.fieldlabel ELSE mcdf.shownamelabel END AS label,t.fielddbtype dbtype,t.fieldhtmltype httype,t.type,me.exportorder,t.viewtype,t.detailtable  from mode_exportexcelfield me " +
						" left join mode_CustomDspField mcdf on mcdf.fieldid=me.fieldid and mcdf.customid=me.customid " +
						" ,(select wb.id,wb.fieldname,wb.fieldlabel,wb.fielddbtype,wb.fieldhtmltype,wb.type,wb.viewtype,wb.detailtable from workflow_billfield wb,mode_customsearch mcs where wb.billid=mcs.formid and mcs.id=?)t  where me.customid=? and t.id=me.fieldid and me.isexport=1 " +
						" union select me.fieldid as id,'1' as name,2 as label,'3' as dbtype, '4' as httype,5 as type,me.exportorder,0 as viewtype,'' as detailtable from mode_exportexcelfield me,Mode_CustomSearch mc where me.customid=mc.id  and mc.id=? and me.fieldid<0 and me.isexport=1 " +
						" order by exportorder,id asc";
				rs2.executeQuery(sql,customId,customId,customId);
				while(rs2.next()){
					String text = "";
					String column = "";
					String otherpara = "";
					String transmethod = "";
					String fieldid = Util.null2String(rs2.getString("id"));
					Map<String,String> map = new HashMap<String,String>();
					String id = rs2.getString("id");
					if(id.equals("-1")){
						String para3="column:modedatacreatetime+"+customId+"+"+showmethod+"+column:"+(isVirtualForm?vprimarykey:"id")+"+"+formID;
						text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(722,user.getLanguage()));
						column = "modedatacreatedate";
						otherpara = para3;
						transmethod = "weaver.formmode.search.FormModeTransMethod.getSearchResultCreateTime";
					}else if(id.equals("-2")){
						text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(882,user.getLanguage()));
						column = "modedatacreater";
						otherpara = "column:modedatacreatertype";
						transmethod = "weaver.formmode.search.FormModeTransMethod.getSearchResultName";
					}else if(id.equals("-3")){
						text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(81287,user.getLanguage()));
						column = "id";
						otherpara = "column:dataid";
						transmethod = "weaver.formmode.search.FormModeTransMethod.getDataId";
					}else if(id.equals("-4")){
						text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(176,user.getLanguage()));
						column = "modelableid";
						otherpara = modeId;
						transmethod = "weaver.formmode.search.FormModeTransMethod.getTabLabel";
					}else if(id.equals("-5")){
						text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(385992,user.getLanguage()));
						column = "modedatastatus";
						otherpara = user.getLanguage()+"";
						transmethod = "com.api.cube.util.CubeSearchTransMethodProxy.getModedatastatus";
					}else{
						String name = rs2.getString("name");
						String label = rs2.getString("label");
						String htmltype = rs2.getString("httype");
						String type = rs2.getString("type");
						String dbtype=rs2.getString("dbtype");
						int field_viewtype=rs2.getInt("viewtype");
						String fieldAlias=name;
						if(field_viewtype==1){
							fieldAlias=detailfieldAlias+name;
						}
						if(isVirtualForm){
							if(modeId.equals("0")){
								modeId = "virtual";
							}
						}
						String para3="column:"+(isVirtualForm?vprimarykey:"id")+"+"+id+"+"+htmltype+"+"+type+"+"+user.getLanguage()+"+"+isbill+"+"+dbtype+"+"+istitle+"+"+modeId+"+"+formID+"+"+viewtype+"+"+ismaplocation+"+"+opentype+"+"+customId+"+fromsearchlist"+"+"+showmethod+"+"+user.getUID()+"+"+enabled;
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label),user.getLanguage());
						text = Util.toHtmlForSplitPage(label);
						column = fieldAlias;
						otherpara = para3;
						transmethod = "weaver.formmode.search.FormModeTransMethod.getOthers";
					}
					map.put("id", fieldid);
					map.put("text", text);
					map.put("column", column);
					map.put("otherpara", otherpara);
					map.put("transmethod", transmethod);
					map.put("vdatasource", vdatasource);
					list.add(map);
				}
			}
			Map map = new HashMap();
			map.put("resultList", list);
			return map;
		}
	}

    public Map getHeadParam(String customid,User user) {
    	RecordSet RecordSet = new RecordSet();
    	RecordSet rs = new RecordSet();
		String detailfieldAlias="d_";
		String formID = "0";
		String modeid = "0";
		
		String isbill = "1";
		String viewtype = "0";
		String opentype = "0";
		String enabled = "0";
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		rs.execute("select a.*,b.tablename,b.detailkeyfield from mode_customsearch a left join workflow_bill b on a.formid=b.id where a.id="+customid);
		String vdatasource = "";
		String vprimarykey = "";
		boolean isVirtualForm = false;
		if(rs.next()){
		    formID=Util.null2String(rs.getString("formid"));
		    modeid=""+Util.getIntValue(rs.getString("modeid"),0);
			isVirtualForm = VirtualFormHandler.isVirtualForm(formID);	//是否是虚拟表单
			Map<String, Object> vFormInfo = new HashMap<String, Object>();
			if(isVirtualForm){
				vFormInfo = VirtualFormHandler.getVFormInfo(formID);
				vdatasource = Util.null2String(vFormInfo.get("vdatasource"));	//虚拟表单数据源
				vprimarykey = Util.null2String(vFormInfo.get("vprimarykey"));	//虚拟表单主键列名称
			}
			
			String sqlwhere = " and Mode_CustomDspField.isshow='1' ";
			String sql = "select isorder,ColWidth,workflow_billfield.id as id,workflow_billfield. fieldlabel AS fieldlabel,workflow_billfield.fieldname as name,Mode_CustomDspField.shownamelabel as label"+
			",workflow_billfield.fielddbtype as dbtype ,workflow_billfield.fieldhtmltype as httype, workflow_billfield.type as type"+
			",Mode_CustomDspField.showorder,Mode_CustomDspField.istitle,Mode_CustomDspField.isstat,Mode_CustomDspField.showmethod"+
			",viewtype,workflow_billfield.detailtable,Mode_CustomDspField.ismaplocation,Mode_CustomDspField.editable" +
			" from workflow_billfield,Mode_CustomDspField,Mode_CustomSearch "+
			" where Mode_CustomDspField.customid=Mode_CustomSearch.id and Mode_CustomSearch.id="+customid+
			"  "+sqlwhere+" and workflow_billfield.billid="+formID+"  and   workflow_billfield.id=Mode_CustomDspField.fieldid" +
			" union select isorder,ColWidth,Mode_CustomDspField.fieldid as id,2 as fieldlabel,'1' as name,shownamelabel as label,'3' as dbtype, '4' as httype,5 as type "+
			",Mode_CustomDspField.showorder,Mode_CustomDspField.istitle,Mode_CustomDspField.isstat,Mode_CustomDspField.showmethod" +
			",0 as viewtype,'' as detailtable,Mode_CustomDspField.ismaplocation,Mode_CustomDspField.editable"+
			" from Mode_CustomDspField ,Mode_CustomSearch"+
			" where Mode_CustomDspField.customid=Mode_CustomSearch.id and Mode_CustomSearch.id="+customid+
			"  "+sqlwhere+"  and Mode_CustomDspField.fieldid<0" +
			" order by showorder,id asc";
			RecordSet.execute(sql);
			RecordSet.beforFirst();
			
			while (RecordSet.next()) {	
				String text = "";
				String column = "";
				String otherpara = "";
				String transmethod = "";
				String fieldid = Util.null2String(RecordSet.getString("id"));
				Map<String,String> map = new HashMap<String,String>();
				if(RecordSet.getString("id").equals("-1")){
					String showmethod = Util.null2o(RecordSet.getString("showmethod"));
					String para3="column:modedatacreatetime+"+customid+"+"+showmethod+"+column:"+(isVirtualForm?vprimarykey:"id")+"+"+formID;
					text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(722,user.getLanguage()));
					//509829创建时间的解析
					String label = RecordSet.getString("label");
					if(!label.equals("")) {
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label), user.getLanguage());
						String text1 = Util.toHtmlForSplitPage(Util.null2String(label));
						if (!text.equals(text1)) {
							text = text1;
						}
					}
					column = "modedatacreatedate";
					otherpara = para3;
					transmethod = "weaver.formmode.search.FormModeTransMethod.getSearchResultCreateTime";
				}else if(RecordSet.getString("id").equals("-2")){
					text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(882,user.getLanguage()));
					//509829创建人的解析
					String label = RecordSet.getString("label");
					if(!label.equals("")) {
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label), user.getLanguage());
						String text1 = Util.toHtmlForSplitPage(Util.null2String(label));
						if (!text.equals(text1)) {
							text = text1;
						}
					}
					column = "modedatacreater";
					otherpara = "column:modedatacreatertype";
					transmethod = "weaver.formmode.search.FormModeTransMethod.getSearchResultName";
				}else if(RecordSet.getString("id").equals("-3")){
					text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(81287,user.getLanguage()));
					//509829数据id的解析
					String label = RecordSet.getString("label");
					if(!label.equals("")) {
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label), user.getLanguage());
						String text1 = Util.toHtmlForSplitPage(Util.null2String(label));
						if (!text.equals(text1)) {
							text = text1;
						}
					}
					column = "id";
					otherpara = "column:dataid";
					transmethod = "weaver.formmode.search.FormModeTransMethod.getDataId";
				}else if(RecordSet.getString("id").equals("-4")){
					String istagsetsql = "select count(*) from  modeinfo t where id="+modeid+" and istagset=1";
					rs.execute(istagsetsql);
					int counttag = 0;
					if(rs.next()){
						counttag = rs.getInt(1);
					}
					if(counttag == 0){
						continue;
					}
					text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(176,user.getLanguage()));
					//509829标签的解析
					String label = RecordSet.getString("label");
					if(!label.equals("")) {
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label), user.getLanguage());
						String text1 = Util.toHtmlForSplitPage(Util.null2String(label));
						if (!text.equals(text1)) {
							text = text1;
						}
					}
					column = "modelableid";
					otherpara = modeid;
					transmethod = "weaver.formmode.search.FormModeTransMethod.getTabLabel";
				}else if(RecordSet.getString("id").equals("-5")){
					text = Util.toHtmlForSplitPage(SystemEnv.getHtmlLabelName(385992,user.getLanguage()));
					//509829创建状态的解析
					String label = RecordSet.getString("label");
					if(!label.equals("")) {
						label = SystemEnv.getHtmlLabelName(Util.getIntValue(label), user.getLanguage());
						String text1 = Util.toHtmlForSplitPage(Util.null2String(label));
						if (!text.equals(text1)) {
							text = text1;
						}
					}
					column = "modedatastatus";
					otherpara = user.getLanguage()+"";
					transmethod = "com.api.cube.util.CubeSearchTransMethodProxy.getModedatastatus";
				}else{
					String name = RecordSet.getString("name");
					String label = RecordSet.getString("label");
					String fieldlabel = RecordSet.getString("fieldlabel");
					String htmltype = RecordSet.getString("httype");
					String type = RecordSet.getString("type");
					String id = RecordSet.getString("id");
					String dbtype=RecordSet.getString("dbtype");
					String istitle = RecordSet.getString("istitle");
					String showmethod = Util.null2o(RecordSet.getString("showmethod"));
					String ismaplocation = Util.getIntValue(RecordSet.getString("ismaplocation"),0)+"";
					int field_viewtype=RecordSet.getInt("viewtype");
					String fieldAlias=name;
					if(field_viewtype==1){
						fieldAlias=detailfieldAlias+name;
					}
					if(isVirtualForm){
					    if(modeid.equals("0")){
					    	modeid = "virtual";
					    }
					}
					if ("".equals(label)) {
						label=fieldlabel;
					}
					String para3="column:"+(isVirtualForm?vprimarykey:"id")+"+"+id+"+"+htmltype+"+"+type+"+"+user.getLanguage()+"+"+isbill+"+"+dbtype+"+"+istitle+"+"+modeid+"+"+formID+"+"+viewtype+"+"+ismaplocation+"+"+opentype+"+"+customid+"+fromsearchlist"+"+"+showmethod+"+"+user.getUID()+"+"+enabled;
					label = SystemEnv.getHtmlLabelName(Util.getIntValue(label),user.getLanguage());
					text = Util.toHtmlForSplitPage(Util.null2String(label));
					column = fieldAlias;
					otherpara = para3;
					transmethod = "weaver.formmode.search.FormModeTransMethod.getOthers";
				}
				
				map.put("id", fieldid);
				map.put("text", text);
				map.put("column", column);
				map.put("otherpara", otherpara);
				map.put("transmethod", transmethod);
				map.put("vdatasource", vdatasource);
				list.add(map);
			}
		}
    	Map map = new HashMap();
    	map.put("resultList", list);
		return map;
	}

	//导出时是否自定义样式
	public boolean isCustomStyle(String customId){
		boolean result = false;
		String sql = "select count(1) from mode_exportexcelset mes,mode_exportcustomstyle mecs where mes.customid=mecs.customid and mes.iscustomstyle=1 and mes.customid=?";
		RecordSet rs = new RecordSet();
		rs.executeQuery(sql,customId);
		if(rs.next()){
			result = rs.getInt(1)==1;
		}
		return result;
	}

	/**
	 * 是否自定义导出字段
	 * @param customId
	 * @return
	 */
	public boolean isCustomExportField(String customId){
		boolean result = false;
		String sql = "select count(1) from mode_exportexcelset where customid=? and isexportfield=1";
		RecordSet rs = new RecordSet();
		rs.executeQuery(sql,customId);
		if(rs.next()){
			result = rs.getInt(1)==1;
		}
		return result;
	}

	/**
	 * 得到导出顺序
	 * @param customId
	 * @return
	 */
	public List<String> getExportOrder(String customId,User user){
		GetExportSetInfo getExportSetInfo = new GetExportSetInfo(null,user);
		List<Map<String,String>> list = getExportSetInfo.getExportFields(Integer.parseInt(customId));
		List<String> result = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			String isexport = map.get("isexport");
			if("1".equals(isexport)){
				String fieldId = map.get("fieldid");
				if("-4".equals(fieldId)&&!getExportSetInfo.isOpenLabel(Integer.parseInt(customId))){
					continue;
				}
				result.add(fieldId);
			}
		}
		return result;
	}
	
	/**
	 * 将查询的结果集转化为MAP
	 * @param cs
	 * @return
	 * @throws Exception
	 */
	private static Map<String,String> ConnStatement2Map(ConnStatement cs) throws Exception{
		Map<String,String> valueMap=new HashMap<String,String>();
		int columns = cs.getColumnCount();
		if(columns==0) return valueMap;
		for(int i=0;i<columns;i++){
			valueMap.put(cs.getColumnName(i+1).toLowerCase(), cs.getString(i+1));
		}
		return valueMap;
	}

	/**
	 * 是否是分组导出
	 * @param customId
	 * @return
	 */
	public boolean isgroupexport(String customId){
		boolean result = false;
		String sql = "select count(1) from mode_exportexcelset where customid=? and isgroupexport=1";
		RecordSet rs = new RecordSet();
		rs.executeQuery(sql,customId);
		if(rs.next()){
			result = rs.getInt(1)==1;
		}
		return result;
	}


}
