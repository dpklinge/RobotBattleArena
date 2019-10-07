<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arena menu</title>
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
	<div class="centered">
		<h1>Welcome to the Robot Arena!</h1>
		<p>${ newGame }</p>
	</div>
	<br />
	<div class="centered">
		<p>${ noRobotError }</p>
		<form action="build">
			<input type="submit" value="Build a robot!" /> <br />
		</form>
		<br/>
		<form action="fight">
			<input type="submit" value="Fight with your robot!" /> <br />
		</form>
	</div>
	<div class="centered">
		<br />
		<form action="equip">
			<input type="submit" value="Purchase/equip weapons!" />
		</form>
	</div>
	<div class="centered">
		<br />
		<form action="newGame">
			<input type="submit" value="Start a new game!" />
		</form>
	</div>

</body>
</html>