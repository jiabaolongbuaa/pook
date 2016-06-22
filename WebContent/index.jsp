<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Hello Xapp!</title>
</head>
<body>
<h1>Hello Xapp!</h1>
<h1>Greetings from Simon Jia</h1>
<%

File directory = new File("");

%>
<h2><%=directory.getAbsolutePath() %></h2>
<center>
  <form name="loginForm" method="post" action="judgeUser.jsp">
    <table>
      <tr>
        <td>用户名:<input type="text" name="userName" id="userName"></td>
      </tr>
      <tr>
        <td>密&nbsp;&nbsp;&nbsp;码:<input type="password" name="password" id="password"></td>
      </tr>
      <tr>
        <td><input type="submit" value="登录">  <input type="reset" value="重置" ></td>     
      </tr>
    </table>
  </form>
</center>


</body>
</html>