package com.fdmgroup.weapon;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdmgroup.robot.DifficultyClass;

@Component
public class WeaponStore {
	private List<RobotWeapon> inventory = new ArrayList<>();
	private DifficultyClass storeLevel;
	private LocalDateTime time;
	private Set<DifficultyClass> bossesBeaten = new HashSet<>();
	private Random random = new Random();
	private double bossWeaponChance = 0.1;
	private int listSize = 6;

	@Autowired
	private WeaponFactory weaponFactory;

	public List<RobotWeapon> forceUpdate(DifficultyClass level) {

		if (level == null) {
			level = DifficultyClass.WEAK;
		}
		storeLevel = level;
		inventory = new ArrayList<>();
		for (int i = 0; i < listSize; i++) {
			inventory.add(weaponFactory.getRandomWeapon(storeLevel, true));
		}
		time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

		return inventory;
	}

	public List<RobotWeapon> checkForNewInventory(DifficultyClass achievedLevel) {
		if(achievedLevel==null){
			achievedLevel=DifficultyClass.WEAK;
		}

			if (LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).compareTo(time.plus(2L, ChronoUnit.MINUTES)) >= 0||storeLevel != achievedLevel) {
			if (storeLevel != achievedLevel) {
				storeLevel=achievedLevel;
			}

				inventory = new ArrayList<>();
				for (int i = 0; i < listSize; i++) {
					if(random.nextDouble()<bossWeaponChance && bossesBeaten.size()>0){
						DifficultyClass[] beatenArray = new DifficultyClass[bossesBeaten.size()];
						inventory.add(weaponFactory.generateBossWeapon(bossesBeaten.toArray(beatenArray)[random.nextInt(bossesBeaten.size())], true));
					}
					inventory.add(weaponFactory.getRandomWeapon(storeLevel, true));
				}
				time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

				return inventory;
			
		}
		return inventory;
	}

	public List<RobotWeapon> getInventory() {
		return inventory;
	}

	public void setInventory(List<RobotWeapon> inventory) {
		this.inventory = inventory;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public void addBossLevel(DifficultyClass enemyLevel) {
		bossesBeaten.add(enemyLevel);
		
	}

}
