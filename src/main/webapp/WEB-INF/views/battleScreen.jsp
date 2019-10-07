<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Battle Results</title>
<link rel="stylesheet" href="<c:url value="/css/main.css"/>">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script> 
	function sleep(ms) {
	  return new Promise(resolve => setTimeout(resolve, ms));
	}
	async function displayLine(){
		var log = $('#log').html();
		var lineArray = log.split("<br>");
		
		for(var i=0;i<lineArray.length-1;i++){
			var temp = lineArray[i];
			
			doEffect(temp);
			await sleep(5000/lineArray.length);
			
		}
		
		for(var i=lineArray.length-1;i<lineArray.length;i++){
			var temp = lineArray[i];
			displayNoEffect(temp);
		}
		
		var results= document.getElementById('fightResults');
		var elements= document.getElementsByClassName("hiddenStats");
		var display = $('#results')[0];
		for(var i=0;i<elements.length;i++){
			display.appendChild(elements[i]);
		}
	}
	function doEffect(line){
		$("#dynamicResults").append('<p class="shake">'+line+'</p>');
		var node = $("#dynamicResults")[0];
		node.scrollTop = node.scrollHeight;
	}
	function displayNoEffect(line){
		$("#dynamicResults").append('<p>'+line+'</p>');
		var node = $("#dynamicResults")[0];
		node.scrollTop = node.scrollHeight;
	}

</script>

</head>
<body onload="displayLine()">
	<div class="flex">
		<div class="left">
			<div id="dynamicResults"></div>
		</div>
		<div id="results" class="right"></div>
	</div>
	<form action="home">
		<input type="submit" value="Return to Home" />
	</form>
	<br />
	<form action="fight">
		<input type="submit" value="To the Arena" />
	</form>
	<p id="log" class="hidden">${ results.battleLog }</p>
	<div id="fightResults" class="hidden">
		<p class="hiddenStats"></p>
		<p class="hiddenStats">Post fight:</p>
		<p class="hiddenStats">Health: ${ robot.currentHealth }/${ robot.maxHealth }</p>
		<p class="hiddenStats">Speed: ${ robot.speed }</p>
		<p class="hiddenStats">Strength: ${ robot.strength }</p>
		<p class="hiddenStats">Armor: ${ robot.armor }</p>
	</div>
	<p class="hidden" id="speed">${ robot.speed }</p>

</body>
</html>