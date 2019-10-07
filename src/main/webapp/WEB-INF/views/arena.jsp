<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Arena</title>
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
	function newBot(urlInput){
		$.ajax({
			type : 'GET',
			url : urlInput,
		})
		.done(function(response) {
			if(response.includes("Golden")){
				$('#enemyDiv').css('box-shadow', '0px 0px 10px 3px rgb(230, 230, 80)');
			}else if(response.includes("Boss")){
				$('#enemyDiv').css('box-shadow', '0px 0px 15px 5px rgb(144, 0, 0)');
			}else{
				$('#enemyDiv').css('box-shadow','0px 0px 2px 2px darkgrey');
			}
			$('#enemyRobot').html(response);
		})
	}
	function normalBorder(){
		$('#enemyDiv').css('border-style', 'solid');
		$('#enemyDiv').css('border-radius', '4px');
		$('#enemyDiv').css('border-color', 'black');
		$('#enemyDiv').css('border-width', '2.4px');
		
	}
	function toughBorder(){
		$('#enemyDiv').css('border-style', 'solid');
		$('#enemyDiv').css('border-radius', '4px');
		$('#enemyDiv').css('border-color', 'black');
		$('#enemyDiv').css('border-width', '4px');
		
	}
	function legendBorder(){
		$('#enemyDiv').css('border-style', 'double');
		$('#enemyDiv').css('border-radius', '6px');
		$('#enemyDiv').css('border-color', 'red');
		$('#enemyDiv').css('border-width', '4px');
		
	}
	function godlikeBorder(){
		$('#enemyDiv').css('border-style', 'ridge');
		$('#enemyDiv').css('border-radius', '8px');
		$('#enemyDiv').css('border-color', 'rgb(148, 0, 0)');
		$('#enemyDiv').css('border-width', '5px');
		
	}
	</script>
</head>
<body onload="newBot('generateCurrentLevelEnemy')">
	<div class="centered">
		<h1>Welcome to the Arena!</h1>
	</div>
	<div class="flex">
		<div class="flex arena">
			<div class="left robot">
				<p>${ robot }</p>
			</div>
			<div id="enemyDiv" class="right robot">
				<p id="enemyRobot"></p>		
			</div>
		</div>
	</div>
	<form class="floatLeft" action="battle">
		<input type="submit" value="Fight" />
	</form>
	<form class="floatRight" action="home">
		<input type="submit" value="Flee" />
	</form>
	<div class="centered">
		<button onclick="newBot('generateEasyEnemy');normalBorder()">Get new easy opponent</button>
	</div>
	<div class="centered">
		<button onclick="newBot('generateMediumEnemy');normalBorder()">Get new medium opponent</button>
	</div>
	<div class="centered">
		<button onclick="newBot('generateHardEnemy');toughBorder()">Get new tough opponent</button>
	</div>
	<div class="centered">
		<button onclick="newBot('generateLegendaryEnemy');legendBorder()">Get new legendary opponent</button>
	</div>
	<div class="centered">
		<button onclick="newBot('generateGodEnemy');godlikeBorder()">Get new god-like opponent</button>
	</div>
	

</body>
</html>