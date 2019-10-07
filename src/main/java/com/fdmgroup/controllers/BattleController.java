package com.fdmgroup.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fdmgroup.Battle;
import com.fdmgroup.BattleResults;
import com.fdmgroup.robot.BattleRobot;
import com.fdmgroup.robot.DifficultyClass;
import com.fdmgroup.weapon.WeaponStore;

@Controller
public class BattleController {
	@Autowired
	private Battle battle;
	@Autowired
	private WeaponStore store;
	
	@GetMapping("battle")
	protected String doGet(HttpSession session, HttpServletRequest req) throws ServletException, IOException {
		BattleRobot playerRobot = (BattleRobot) session.getAttribute("robot");
		BattleRobot enemyRobot = (BattleRobot) session.getAttribute("enemyRobot");
		BattleResults results = battle.doBattle(playerRobot, enemyRobot);

		if (results.isWin()) {
			checkCurrentWinLevel(session, enemyRobot);
			Integer winnings = (Integer) session.getAttribute("money") + results.getWinnings();
			results.addToLog("As the victor, you won " + results.getWinnings() + "!");
			session.setAttribute("robot", playerRobot);
			session.setAttribute("money", winnings);
			req.setAttribute("results", results);

		} else {
			results.addToLog("Your robot was utterly destroyed!<br/>");
			results.addToLog("You salvage "+(int)(playerRobot.getValue()*.7)+" worth of scrap and your weapon from the wreckage.<br/>");
			results.addToLog("Enemy health: " + enemyRobot.getCurrentHealth() + "/" + enemyRobot.getMaxHealth());
			req.setAttribute("robot", playerRobot);
			session.setAttribute("robot", null);
			session.setAttribute("money", ((Integer) session.getAttribute("money"))+(int)(playerRobot.getValue()*10));
			req.setAttribute("results", results);
		}
		return "battleScreen";
	}

	@GetMapping("fight")
	protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("robot") == null) {
			req.setAttribute("noRobotError", "You need a robot before you can fight!");
			return "redirect:/home";
		} else {
			return "arena";
		}

	}
	
	public void checkCurrentWinLevel(HttpSession session, BattleRobot enemyRobot) {
		
		DifficultyClass enemyLevel = (DifficultyClass) session.getAttribute("enemyLevel");
		DifficultyClass currentLevel = (DifficultyClass) session.getAttribute("victoryLevel");
		if(enemyRobot.getName().contains("Boss ")){
			store.addBossLevel(enemyLevel);
		}
		if (currentLevel==null || enemyLevel.getDifficultyLevel()>currentLevel.getDifficultyLevel() ) {
			session.setAttribute("victoryLevel", enemyLevel);
		} 
	}
}
