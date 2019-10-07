package com.fdmgroup.controllers;

import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.robot.BattleRobot;
import com.fdmgroup.robot.DifficultyClass;
import com.fdmgroup.weapon.RobotWeapon;
import com.fdmgroup.weapon.WeaponStore;

@Controller
public class ShopController {
	
	@Autowired
	private WeaponStore store;
	private boolean firstVisit =true;

	@GetMapping("/equip")
	public String doEquip(Model model, HttpSession session) {
		if (session.getAttribute("robot") == null) {
			model.addAttribute("noRobotError", "Go build a robot before you try to equip it!");
			return "redirect:/home";
		}
		DifficultyClass achievedLevel = (DifficultyClass)session.getAttribute("victoryLevel");
		List<RobotWeapon> weaponList=null;
		if(firstVisit){
			firstVisit=false;
			weaponList = store.forceUpdate(achievedLevel);
		}else{
			weaponList = store.checkForNewInventory(achievedLevel);
			
		}
		session.setAttribute("timeToUpdate", store.getTime().plus(2L, ChronoUnit.MINUTES).toLocalTime());
		session.setAttribute("weaponsForSale", weaponList);
		return "equip";
	}

	@PostMapping("buyWeapon")
	public String buyWeapon(Model model, HttpSession session, HttpServletRequest req) {
		int index = Integer.parseInt((String) req.getParameter("weaponIndex"));
		List<RobotWeapon> weapons = (List<RobotWeapon>) session.getAttribute("weaponsForSale");
		List<RobotWeapon> inventory = (List<RobotWeapon>) session.getAttribute("inventory");
		Integer money = ((Integer) session.getAttribute("money"));
		if(weapons.get(index)!=store.getInventory().get(index)){
			model.addAttribute("error", "Too slow! Weapons updated!");
		}else if (money > weapons.get(index).getValue()) {
			inventory.add(0, weapons.get(index));
			BattleRobot robot = (BattleRobot) session.getAttribute("robot");
			robot.setWeapon(weapons.get(index));
			money -= weapons.get(index).getValue();
			weapons.remove(index);
			session.setAttribute("inventory", inventory);
			session.setAttribute("money", money);
		} else {
			model.addAttribute("error", "Not enough money to purchase!");
		}

		return "equip";

	}

	@PostMapping("equipWeapon")
	public String equipWeapon(@RequestParam String weaponIndex , HttpSession session) {
		int index = Integer.parseInt(weaponIndex);
		List<RobotWeapon> inventory = (List<RobotWeapon>) session.getAttribute("inventory");
		BattleRobot robot = (BattleRobot) session.getAttribute("robot");
		robot.setWeapon(inventory.get(index));
		inventory.remove(index);
		inventory.add(0, robot.getWeapon());
		session.setAttribute("robot", robot);
		session.setAttribute("inventory", inventory);
		return "redirect:/equip";

	}
	
	@PostMapping("sellWeapon")
	public String equipWeapon(@RequestParam String weaponIndex,Model model, HttpSession session) {
		int index = Integer.valueOf(weaponIndex);
		List<RobotWeapon> inventory = (List<RobotWeapon>) session.getAttribute("inventory");
		BattleRobot robot = (BattleRobot) session.getAttribute("robot");
		if(inventory.get(index)==robot.getWeapon()){
			model.addAttribute("sellError", "You cannot sell your equipped weapon!");
			return "equip";
		}
		Integer money = (Integer) session.getAttribute("money");
		session.setAttribute("money", Integer.valueOf((Double.valueOf(money + (money)*0.4).intValue())));
		inventory.remove(index);
		session.setAttribute("robot", robot);
		session.setAttribute("inventory", inventory);
		return "redirect:/equip";

	}
}
