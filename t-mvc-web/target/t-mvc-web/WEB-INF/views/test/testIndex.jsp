<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面（测试Spring MVC是否配置成功）</title>
</head>
<body>
	测试页面（测试Spring MVC是否配置成功）
	
	<a href="${pageContext.request.contextPath}/test/testAdd">新增测试</a>
	<hr/>
	提交的信息如下：numbers-->>${test.numbers}; name-->>${test.name}
</body>
</html>