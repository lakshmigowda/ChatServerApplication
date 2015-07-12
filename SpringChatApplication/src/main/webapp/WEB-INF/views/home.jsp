<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Chat Application</title>
<link rel="stylesheet"
	href="/SpringChatApplication/resources/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/SpringChatApplication/resources/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="/SpringChatApplication/resources/css/chat.css">
<script src="/SpringChatApplication/resources/js/jquery-2.1.4.js">
	
</script>
<script src="/SpringChatApplication/resources/js/bootstrap.min.js">
	
</script>
<script src="/SpringChatApplication/resources/js/home.js">
	
</script>
</head>
<body>
	<table id="chatTable" border="1" class="table table-striped">
		<thead>
			<tr>
				<th class="text-center" style="width: 100px;">Chat Rooms</th>
				<th colspan="3" id="heading" class="text-center">Welcome to
					Chat Server!</th>
			</tr>
		</thead>
		<tr>
			<td><div id="listchatrooms">
					<ul>
						<c:forEach var="elem" items="${rooms}">
							<li class="listname"><c:out value="${elem.name}" /></li>
						</c:forEach>
					</ul>
				</div></td>
			<td colspan="3">
				<div id="content">"Your chat will appear here. Select a chat
					from the history."</div>
			</td>
		</tr>
		<tr>
			<td><div id="listusersforchatroom"></div></td>
			<td colspan="2"><textarea class="form-control" rows="3"
					cols="50" id="text" placeholder="Type your message here."
					onkeypress="return handleKeyPress(event)"></textarea></td>
			<td><input type="button" class="btn btn-info btn-lg" id="send"
				value="Send" onclick="handleSend()"></input></td>
		</tr>
	</table>
</body>
</html>