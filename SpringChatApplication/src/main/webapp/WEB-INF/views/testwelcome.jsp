<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test welcome page</title>


<link rel="stylesheet" type="text/css"
	href="/chetanspringmvctest/resources/css/test.css" />
</head>

<body>
	<div>
		<form:form method="POST" action="/chetanspringmvctest/testsubmit"
			commandName="testmodel">
			<p class="test">Welcome to Chetan's Spring MVC Test Project!!</p>
			<table>
				<tr>
					<td><form:label path="testName">TestName:</form:label></td>
					<td><form:input path="testName"></form:input></td>
				</tr>
				<tr>
				<tr>
					<td><input type="submit" class="submitbtn" value="Submit"></td>
				</tr>
			</table>

		</form:form>
	</div>
</body>
</html>