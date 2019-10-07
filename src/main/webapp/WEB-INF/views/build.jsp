<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Robot builder</title>
<link rel="stylesheet" href="<c:url value="./css/main.css"/>">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	var speedMax = 250;
	var armorMax = 500;
	var strengthMax = 500;
	var healthMax = 100000;
	var healthCost = 100;
	var speedCost = 250;
	var armorCost = 200;
	var strengthCost = 150;
	var healthBaseCost = 100;
	var speedBaseCost = 250;
	var armorBaseCost = 200;
	var strengthBaseCost = 150;
	var healCost = 5;
	function heal() {
		var currentHealth = $('#currentHealth').html();
		var maxHealth = $("#maxHealth").html();
		var difference = maxHealth - currentHealth;
		var money = $("#money").html();
		if ((difference * healCost) > money) {
			var availableHealing = Math.round(money / healCost);
			$("#currentHealth").html((currentHealth - (-availableHealing)));
			$("#money").html(0);
		} else {
			$("#currentHealth").html(maxHealth);
			$("#money").html(money - (difference * healCost));
		}
		maxCheck();
	}
	function addHealth(num) {

		var currentHealth = $("#currentHealth").html();
		var maxHealth = $("#maxHealth").html();
		var money = $("#money").html();
		if (money >= (healthCost * num)) {
			$("#money").html(money - num * healthCost);
			$("#currentHealth").html(currentHealth - (-5 * num));
			$("#maxHealth").html(maxHealth - (-5 * num));
		}
		maxCheck();
	}
	function addSpeed(num) {
		console.log('Speed adding num: ' + num)
		var speed = $("#speed").html();
		var money = $("#money").html();
		if (money >= (speedCost * num)) {

			if ((speed - (-num)) <= speedMax) {
				$("#money").html(money - (speedCost * num));
				$("#speed").html(speed - (-num));
			} else {
				var speedChange = speedMax - speed;
				console.log('Speed adding - speedChange' + speedChange)
				$("#money").html(money - (speedCost * speedChange));
				$("#speed").html(speed - (-speedChange));
			}
		}
		maxCheck();
	}
	function addStrength(num) {
		var strength = $("#strength").html();
		var money = $("#money").html();
		if (money >= (strengthCost * num)) {
			if (strength - (-num) <= strengthMax) {
				$("#money").html(money - (strengthCost * num));
				$("#strength").html(strength - (-1 * num));
			} else {
				var strengthChange = strengthMax - strength;
				$("#money").html(money - (strengthCost * strengthChange));
				$("#strength").html(strength - (-1 * strengthChange));
			}
		}
		maxCheck();
	}
	function addArmor(num) {
		var armor = $("#armor").html();
		var money = $("#money").html();
		if (money >= (armorCost * num)) {
			if (armor - (-num) <= armorMax) {
				$("#money").html(money - armorCost * num);
				$("#armor").html(armor - (-1 * num));
			} else {
				var armorChange = armorMax - armor;
				$("#money").html(money - armorCost * armorChange);
				$("#armor").html(armor - (-1 * armorChange));
			}
		}
		maxCheck();
	}
	function submit() {
		var currentHealth = $("#currentHealth").html();
		var maxHealth = $("#maxHealth").html();
		var armor = $("#armor").html();
		var speed = $("#speed").html();
		var strength = $("#strength").html();
		var money = $("#money").html();
		var name = $('#name').val();
		$('#nameSubmit').val(name);
		$("#currentHealthSubmit").val(currentHealth);
		$("#maxHealthSubmit").val(maxHealth);
		$("#armorSubmit").val(armor);
		$("#speedSubmit").val(speed);
		$("#strengthSubmit").val(strength);
		$("#moneySubmit").val(money);

		var form = $('#submitForm');
		$(form).submit(function(event) {
			event.preventDefault();
			var formData = $(form).serialize();
			$.ajax({
				type : 'POST',
				url : $(form).attr('action'),
				data : formData
			}).done(function(responseText) {
				var response = JSON.parse(responseText);
				console.log(response.currentHealth+' '+response.maxHealth+' '+response.speed+' '+response.strength+' '+response.armor);
				$('#currHealth').html(response.currentHealth);
				$('#currMaxHealth').html(response.maxHealth);
				$('#currSpeed').html(response.speed);
				$('#currStrength').html(response.strength);
				$('#currArmor').html(response.armor);
				$('#currName').html(response.name);
				
			})
		});
		form.submit();
		updateCost();
	}

	function maxCheck() {
		if ($('#currentHealth').text() == $('#maxHealth').text()
				|| $('#money').text() < 5) {
			$("#heal").prop("disabled", true);
		} else {
			$("#heal").prop("disabled", false);
		}
		if ($("#money").html() < healthCost) {
			$("#addHealth").prop("disabled", true);
		} else {
			$("#addHealth").prop("disabled", false);
		}
		if ($("#money").html() < (healthCost * 5)) {
			$("#addHealth5").prop("disabled", true);
		} else {
			$("#addHealth5").prop("disabled", false);
		}
		if ($("#money").html() < (healthCost * 25)) {
			$("#addHealth25").prop("disabled", true);
		} else {
			$("#addHealth25").prop("disabled", false);
		}
		
		if ($("#money").html() < armorCost || $('#armor').html() >= armorMax) {
			$("#addArmor").prop("disabled", true);
		} else {
			$("#addArmor").prop("disabled", false);
		}
		if ($("#money").html() < (5 * armorCost)
				|| $('#armor').html() >= armorMax) {
			$("#addArmor5").prop("disabled", true);
		} else {
			$("#addArmor5").prop("disabled", false);
		}
		if ($("#money").html() < (25 * armorCost)
				|| $('#armor').html() >= armorMax) {
			$("#addArmor25").prop("disabled", true);
		} else {
			$("#addArmor25").prop("disabled", false);
		}
		if ($("#speed").html() >= speedMax || $("#money").html() < speedCost) {
			$("#addSpeed").prop("disabled", true);
		} else {
			$("#addSpeed").prop("disabled", false);
		}
		if ($("#speed").html() >= speedMax
				|| $("#money").html() < (5 * speedCost)) {
			$("#addSpeed5").prop("disabled", true);
		} else {
			$("#addSpeed5").prop("disabled", false);
		}
		if ($("#speed").html() >= speedMax
				|| $("#money").html() < (25 * speedCost)) {
			$("#addSpeed25").prop("disabled", true);
		} else {
			$("#addSpeed25").prop("disabled", false);
		}
		if ($("#money").html() < strengthCost
				|| $('#strength').html() >= strengthMax) {
			$("#addStrength").prop("disabled", true);
		} else {
			$("#addStrength").prop("disabled", false);
		}
		if ($("#money").html() < (strengthCost * 5)
				|| $('#strength').html() >= strengthMax) {
			$("#addStrength5").prop("disabled", true);
		} else {
			$("#addStrength5").prop("disabled", false);
		}
		if ($("#money").html() < (strengthCost * 25)
				|| $('#strength').html() >= strengthMax) {
			$("#addStrength25").prop("disabled", true);
		} else {
			$("#addStrength25").prop("disabled", false);
		}
		updateCost();
		
	}
	function updateCost(){
		healthCost = Math.round(healthBaseCost + healthBaseCost * (Math.pow($('#maxHealth').html(), 3)/Math.pow($('#maxHealth').html(), 2))/250000);
		speedCost = Math.round(speedBaseCost-13 + speedBaseCost * (Math.pow($('#speed').html(), 3)/Math.pow($('#speed').html(), 2))/speedMax);
		strengthCost = Math.round(strengthBaseCost-2 + strengthBaseCost * (Math.pow($('#strength').html(), 3)/Math.pow($('#strength').html(), 2))/strengthMax);
		armorCost = Math.round(armorBaseCost-2 + armorBaseCost * (Math.pow($('#armor').html(), 3)/Math.pow($('#armor').html(), 2))/strengthMax);
		
		$('#healthCost').html(healthCost+'g');
		$('#speedCost').html(speedCost+'g');
		$('#strengthCost').html(strengthCost+'g');
		$('#armorCost').html(armorCost+'g');
	}
</script>
</head>
<p>${ noRobotError }</p>
<body onload="submit(); maxCheck(); updateCost();">
	<div class="flex">
		<div class="left">
			<p></p>
			<p>Current robot:</p>
			<p id="currName">${ robot.name }</p>
			<p><span id="currMaxHealth">${ robot.maxHealth }</span>/<span id="currHealth">${ robot.currentHealth }</span> health</p>
			<p ><span id="currSpeed">${ robot.speed }</span> speed</p>
			<p ><span id="currStrength">${ robot.strength }</span> strength</p>
			<p ><span id="currArmor">${ robot.armor }</span> armor</p>
			
			
			
		</div>
		<div class="right">
			<p>
				Current money: <span id="money">${ money }</span>g
			</p>
			<p>Upgrade robot:</p>
			<p>
				Name: <input type="text" id="name" value="${ robot.name }" />
			</p>
			<p>
				Health: <span id="currentHealth">${ robot.currentHealth }</span>/<span
					id="maxHealth">${ robot.maxHealth }</span>
			</p>
			<button id="heal" onclick="heal()">Heal (5g/health)</button>
			<button id="addHealth" onclick="addHealth(1)">Add max health
				(<span id="healthCost">100g</span>/5 health)</button>
			<button id="addHealth5" onclick="addHealth(5)">Add 5</button>
			<button id="addHealth25" onclick="addHealth(25)">Add 25</button>
			<p>
				Speed: <span id="speed">${ robot.speed }</span>
			</p>
			<button id="addSpeed" onclick="addSpeed(1)">Add speed
				(<span id="speedCost">250g</span>/1 speed)</button>
			<button id="addSpeed5" onclick="addSpeed(5)">Add 5</button>
			<button id="addSpeed25" onclick="addSpeed(25)">Add 25</button>
			<p>
				Strength: <span id="strength">${ robot.strength }</span>
			</p>
			<button id="addStrength" onclick="addStrength(1)">Add
				strength (<span id="strengthCost">150g</span>/1 strength)</button>
			<button id="addStrength5" onclick="addStrength(5)">Add 5</button>
			<button id="addStrength25" onclick="addStrength(25)">Add 25</button>
			<p>
				Armor: <span id="armor">${ robot.armor }</span>
			</p>
			<button id="addArmor" onclick="addArmor(1)">Add max armor
				(<span id="armorCost">200g</span>/1 armor)</button>
			<button id="addArmor5" onclick="addArmor(5)">Add 5</button>
			<button id="addArmor25" onclick="addArmor(25)">Add 25</button>
			<br /> <br />
			<button onclick="submit()">Make upgrades</button>
		</div>
	</div>
	<form action="home">
		<input type="submit" value="Return to home" />
	</form>
	<form id="submitForm" action="build" method="POST" class="hidden">
		<input type="text" id="nameSubmit" name="name" value="" /> <input
			type="text" id="currentHealthSubmit" name="currentHealth" value="" />
		<input type="text" id="maxHealthSubmit" name="maxHealth" value="" />
		<input type="text" id="speedSubmit" name="speed" value="" /> <input
			type="text" id="strengthSubmit" name="strength" value="" /> <input
			type="text" id="armorSubmit" name="armor" value="" /> <input
			type="text" id="moneySubmit" name="money" value="" /> <input
			type="submit" id="submit" value="submit" />
	</form>
</body>
</html>