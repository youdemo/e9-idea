<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.hrm.*" %>
<%@ page import="java.util.Enumeration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%

	   //获取session
     //HttpSession session = request.getSession();
	 //session = request.getSession(true);
   // 泛型
  // Enumeration<Object> et = session.getAttributeNames();
    //	while(et.hasMoreElements()){
    //    out.println(et.nextElement()+"<br>");
     // }
	 // 在session放置对象
	// 如何放置对象？  放置什么对象
 // User user = new User();
	//user.setUid(-10);


	//request.getSession(true).setAttribute("weaver_user@bean",user);
  User user1 = new User();
		//user1 = User.getUser(1, 0);
    user1.setUid(-10);
    user1.setLoginid("test111");
    user1.setFirstname("test111");
    user1.setLastname("test111");
    user1.setSeclevel("10");
    user1.setLogintype("1");
		user1.setLanguage(7);
    user1.setIsAdmin(false);
		request.getSession(true).setMaxInactiveInterval(60 * 60 * 24);
		request.getSession(true).setAttribute("curloginid","test111");
		request.getSession(true).setAttribute("SESSION_CURRENT_SKIN","default");
		request.getSession(true).setAttribute("SESSION_CURRENT_THEME","ecology8");
		request.getSession(true).setAttribute("SESSION_TEMP_CURRENT_THEME","ecology8"); 
		request.getSession(true).setAttribute("SESSION_CURRENT_SKIN","default");
		request.getSession(true).setAttribute("weaver_user@bean", user1);


 // 跳转到  目标界面
 // response.sendRedirect("/gvo/visitor/visitorInfo.jsp");
	response.sendRedirect("/gvo/redirect.jsp");
  //request.getRequestDispatcher("/gvo/redirect.jsp").forward(request, response);
%>