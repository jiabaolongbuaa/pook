<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>




<form action="upjsp.jsp" enctype="MULTIPART/FORM-DATA" method=post> 

<br /> 

公司: <input type="text" name="company" /> 

<br /> 

选择要上载的文件 <input type="file" name="filename" /> 

<br /> 

<input type="submit" value="上载" /> 

</form> 




</body>
</html>