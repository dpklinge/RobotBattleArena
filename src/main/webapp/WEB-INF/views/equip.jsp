<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Weapon Purchasing</title>
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>
	<div class="centered flex">
		<div class="left">
		<p>${ sellError }</p>
		<p>Equipped: ${ robot.weapon.description }</p>
			<c:set var="invIndex" value="0"></c:set>
			<c:forEach var="item" items="${ inventory }">
				<form action="equipWeapon" method="POST">
					<p>${ item.description }</p>
					<p>${ item.damageMultiplier }Multiplier</p>
					<fmt:formatNumber value="${item.value * 0.4 - ( item.value * 0.4 % 1 ) }" var="valueAsInt" maxFractionDigits="0"/>
					<p>${ valueAsInt }Sell value</p>
					<input class="hidden" name="weaponIndex" type="text"
						value="${ invIndex }" />
					<p></p>
					<input type="submit" value="Equip" />
				</form>
				<form action="sellWeapon" method="POST">
					<input class="hidden" name="weaponIndex" type="text"
						value="${ invIndex }" />
					<input type="submit" value="Sell" />
				</form>
				<c:set var="invIndex" value="${ invIndex + 1 }" />
			</c:forEach>
		</div>
		<div class="right">
			<p>Your funds : ${ money }g</p>
			<p>${ error }</p>
			<c:set var="index" value="0"></c:set>
			<c:forEach var="weapon" items="${ weaponsForSale }">
				<form action="buyWeapon" method="POST">
					<p>${ weapon.description }</p>
					<p>${ weapon.damageMultiplier }Multiplier</p>
					<p>${ weapon.value }g</p>
					<input class="hidden" name="weaponIndex" type="text"
						value="${ index }" />
					<p></p>
					<input type="submit" value="Buy" />
					<c:set var="index" value="${ index + 1 }" />
				</form>
			</c:forEach>
			<p>Store updates at: ${ timeToUpdate } </p>
		</div>

	</div>
	<form action="home">
		<input type="submit" value="Return to home" />
	</form>
</body>
</html>