
<%@ page import="weaver.general.Util"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />

		<%
		User user1 = HrmUserVarify.getUser (request , response) ;
		int ryid = user1.getUID();
		String sqlry = "";
		String comid = "";
		if(ryid != 1){
			sqlry = "select subcompanyid1 from hrmresource where id="+ryid;
			rs.execute(sqlry);
			if(rs.next()){
				comid = Util.null2String(rs.getString("subcompanyid1"));
			}
		}
		if("301".equals(comid) || "302".equals(comid)){
			request.getRequestDispatcher("/wui/index.html#/main").forward(request, response);
		}else if("301".equals(comid)){
			request.getRequestDispatcher("/gvo/gateway/mainM4.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/gvo/gateway/main.jsp").forward(request, response);
		}

%>
