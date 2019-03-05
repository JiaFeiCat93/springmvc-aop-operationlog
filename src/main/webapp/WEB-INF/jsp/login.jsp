<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<body>

	<div id="global">
		<p>
			${message }
		</p>
		<%-- <form:form modelAttribute="user" action="login" method="post"> --%>
		<form:form modelAttribute="user" action="updateUser" method="post">
			<p>
				<label for="username">UserName:</label>
				<form:input id="username" path="userName"/>
			</p>	
			<p>
				<label for="password">PassWord:</label>
				<form:input id="password" path="password"/>
			</p>
			<p>
				<input type="reset" id="reset" value="重置"/>
				<input type="submit" id="submit" value="登录"/>
			</p>
		</form:form>
	</div>

</body>
</html>