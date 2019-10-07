package com.fdmgroup.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.robot.BattleRobot;
import com.fdmgroup.robot.DifficultyClass;
import com.fdmgroup.robot.RobotFactory;
import com.fdmgroup.weapon.RobotWeapon;
import com.fdmgroup.weapon.WeaponFactory;
import com.fdmgroup.weapon.WeaponStore;

@Controller
public class MainController {
	@Autowired
	private RobotFactory roboFactory;
	@Autowired
	private WeaponFactory weaponFactory;
	@Autowired 
	private WeaponStore store;
	private ObjectMapper mapper = new ObjectMapper();
	private Random random = new Random();

	@GetMapping("/build")
	public String robotBuilderGet() {
		return "build";
	}

	@PostMapping("/build")
	@ResponseBody
	protected String robotBuilderPost(@RequestParam String money,@RequestParam String currentHealth,@RequestParam String maxHealth,@RequestParam String armor,@RequestParam String speed,@RequestParam String strength,@RequestParam String name, HttpSession session, HttpServletRequest req)
			throws ServletException, IOException {
		BattleRobot robot = (BattleRobot) session.getAttribute("robot");

		robot.setCurrentHealth(Integer.valueOf(currentHealth));
		robot.setMaxHealth(Integer.valueOf(maxHealth));
		robot.setArmor(Integer.valueOf(armor));
		robot.setSpeed(Integer.valueOf(speed));
		robot.setStrength(Integer.valueOf(strength));
		robot.setName(name);
		robot.updateValue();
		session.setAttribute("robot", robot);
		session.setAttribute("money", Integer.valueOf(money));
		return mapper.writeValueAsString(robot);
	}

	@GetMapping({ "/home", "/" })
	public String goHome(HttpSession session,Model model) {
		BattleRobot robot = null;
		if (session.getAttribute("robot") == null) {
			model.addAttribute("noRobotError", "You have been given a starter robot!");
			robot = new BattleRobot();
			robot.setName(RandomStringUtils.randomAlphanumeric(3, random.nextInt(5) + 3)+" Arena Contender");	
			if (session.getAttribute("inventory") != null) {
				robot.setWeapon(((List<RobotWeapon>) session.getAttribute("inventory")).get(0));
			} else {
				robot.setWeapon(weaponFactory.getWeakWeapon(true));
				List<RobotWeapon> weapons = new ArrayList<>();
				weapons.add(robot.getWeapon());
				session.setAttribute("inventory", weapons);
			}
			robot.updateValue();
			session.setAttribute("robot", robot);
		}
		if (session.getAttribute("money") == null) {
			session.setAttribute("money", Integer.valueOf(1000));
		}
		return "index";
	}

	@GetMapping("newGame")
	public String newGame(HttpSession session, HttpServletRequest req) {
		session.invalidate();
		store.forceUpdate(DifficultyClass.WEAK);
		req.setAttribute("newGame", "Your game has been restarted!");
		return "redirect:/home";
	}	

}
