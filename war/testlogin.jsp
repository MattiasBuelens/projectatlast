<%@page import="projectatlast.student.AuthController"%>
<%@page import="projectatlast.data.Registry"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="projectatlast.data.DAO" %>

<html>
  <body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>!

<%
	AuthController auth = new AuthController();
	if(Registry.studentFinder().getStudent(auth.getCurrentUser())!=null){
		out.println("does exist");
	}else {
		if(auth.register()){
			out.println("registered");
		}else{
			out.println("bah");
		}
		
	}

%>

 (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with greetings you post.</p>
<%
    }
%>

  </body>
</html>