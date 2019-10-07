package com.fdmgroup.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fdmgroup.robot.BattleRobot;
import com.fdmgroup.robot.DifficultyClass;
import com.fdmgroup.robot.RobotFactory;

@Controller
public class GeneratorsController {
	@Autowired
	private RobotFactory roboFactory;
	
	@GetMapping("generateCurrentLevelEnemy")
	@ResponseBody
	public String getCurrentLevelRobot(HttpServletRequest req, HttpSession session) {
		DifficultyClass level = (DifficultyClass) session.getAttribute("enemyLevel");
		if (level == null) {
			level = DifficultyClass.WEAK;
		}
		switch (level) {
		case WEAK:
			return getEasyRobot(req, session);
		case AVERAGE:
			return getMediumRobot(req, session);
		case STRONG:
			return getHardRobot(req, session);
		case LEGENDARY:
			return getLegendaryRobot(req, session);
		case GODLIKE:
			return getGodRobot(req, session);
		default:
			return "????";
		}

	}


	@GetMapping("generateEasyEnemy")
	@ResponseBody
	public String getEasyRobot(HttpServletRequest req, HttpSession session) {
		session.setAttribute("enemyLevel", DifficultyClass.WEAK);
		BattleRobot robot = roboFactory.generateEasyEnemy();
		session.setAttribute("enemyRobot", robot);
		return robot.toString();

	}

	@GetMapping("generateHardEnemy")
	@ResponseBody
	public String getHardRobot(HttpServletRequest req, HttpSession session) {
		session.setAttribute("enemyLevel", DifficultyClass.STRONG);
		BattleRobot robot = roboFactory.generateHardEnemy();
		session.setAttribute("enemyRobot", robot);
		return robot.toString();

	}

	@GetMapping("generateMediumEnemy")
	@ResponseBody
	public String getMediumRobot(HttpServletRequest req, HttpSession session) {
		session.setAttribute("enemyLevel", DifficultyClass.AVERAGE);
		BattleRobot robot = roboFactory.generateMediumEnemy();
		session.setAttribute("enemyRobot", robot);
		return robot.toString();

	}
	
	@GetMapping("generateLegendaryEnemy")
	@ResponseBody
	public String getLegendaryRobot(HttpServletRequest req, HttpSession session) {
		session.setAttribute("enemyLevel", DifficultyClass.LEGENDARY);
		BattleRobot robot = roboFactory.generateLegendaryEnemy();
		session.setAttribute("enemyRobot", robot);
		return robot.toString();

	}

	@GetMapping("generateGodEnemy")
	@ResponseBody
	public String getGodRobot(HttpServletRequest req, HttpSession session) {
		session.setAttribute("enemyLevel", DifficultyClass.GODLIKE);
		BattleRobot robot = roboFactory.generateGodlikeEnemy();
		session.setAttribute("enemyRobot", robot);
		return robot.toString();

	}
}
