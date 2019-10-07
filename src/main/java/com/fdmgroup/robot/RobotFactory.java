package com.fdmgroup.robot;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdmgroup.Battle;
import com.fdmgroup.weapon.WeaponFactory;

@Component
public class RobotFactory {
	@Autowired
	private WeaponFactory factory;
	private Random random = new Random();
	private double goldenRobotChance = 0.08;
	private double bossChance = 0.04;

	public String generateRobotName(BattleRobot robot) {

		String name = RandomStringUtils.randomAlphanumeric(3, random.nextInt(5) + 3);
		name += " the";
		if (robot.getMaxHealth() > 2000) {
			name += " Robust";
		} else if (robot.getMaxHealth() > 1000) {
			name += " Sturdy";
		} else if (robot.getMaxHealth() < 200) {
			name += " Frail";
		}
		if (robot.getArmor() > 50) {
			name += " Tanky";
		} else if (robot.getArmor() < 20) {
			name += " Fragile";
		}
		if (robot.getSpeed() > 50) {
			name += " Swift";
		} else if (robot.getSpeed() < 20) {
			name += " Slow";
		}
		if (robot.getStrength() > 50) {
			name += " Crusher";
		} else if (robot.getStrength() < 20) {
			name += " Weak";
		}
		name += " BattleBot";
		return name;
	}

	public BattleRobot generateEnemy(int minSpeed, int maxRandomAddSpeed, int minStrength, int maxRandomAddStrength,
			int minArmor, int maxRandomAddArmor, int minHealth, int maxRandomHealth, double valueMultiplier,
			String nullableOverrideName, DifficultyClass clazz) {
		BattleRobot robot = new BattleRobot();
		int speed = 0;
		if (maxRandomAddSpeed > 0) {
			speed = random.nextInt(maxRandomAddSpeed) + minSpeed;
		} else {
			speed = minSpeed;
		}

		int strength = 0;
		if (maxRandomAddSpeed > 0) {
			strength = random.nextInt(maxRandomAddStrength) + minStrength;
		} else {
			strength = minStrength;
		}

		int armor = 0;
		if (maxRandomAddArmor > 0) {
			armor = random.nextInt(maxRandomAddArmor) + minArmor;
		} else {
			armor = minArmor;
		}

		int maxHealth = 0;
		if (maxRandomAddArmor > 0) {
			maxHealth = random.nextInt(maxRandomHealth) + minHealth;
		} else {
			maxHealth = minHealth;
		}

		robot.setArmor(armor);
		robot.setCurrentHealth(maxHealth);
		robot.setMaxHealth(maxHealth);
		robot.setSpeed(speed);
		robot.setStrength(strength);
		if (nullableOverrideName != null) {
			robot.setName(nullableOverrideName);
		} else {
			robot.setName(generateRobotName(robot));
		}
		switch (clazz) {
		case WEAK:
			robot.setWeapon(factory.getWeakWeapon(false));
			break;
		case AVERAGE:
			robot.setWeapon(factory.getMediumWeapon(false));
			break;
		case STRONG:
			robot.setWeapon(factory.getStrongWeapon(false));
			break;
		case LEGENDARY:
			robot.setWeapon(factory.getLegendaryWeapon(false));
			break;
		case GODLIKE:
			robot.setWeapon(factory.getGodlikeWeapon(false));
			break;
		case PC:
			System.out.println("?????");

		}
		robot.updateValue();
		robot.setValue((int) (robot.getValue() * valueMultiplier));
		double upgradeChance = random.nextDouble();
		if (upgradeChance < bossChance) {
			robot.setName("Boss " + robot.getName());
			robot.setArmor((int) (robot.getArmor() * 2.5));
			robot.setCurrentHealth((int) (robot.getCurrentHealth() * 2.5));
			robot.setMaxHealth((int) (robot.getMaxHealth() * 2.5));
			robot.setSpeed((int) (robot.getSpeed() * 2.5));
			robot.setStrength((int) (robot.getStrength() * 2.5));
			robot.setWeapon(factory.generateBossWeapon(clazz, false));
			robot.updateValue();
			robot.setValue(robot.getValue() * 5 + 1000);
		} else if (upgradeChance < goldenRobotChance) {
			robot.setName("Golden " + robot.getName());
			robot.setArmor((int) (robot.getArmor() * 1.3));
			robot.setCurrentHealth((int) (robot.getCurrentHealth() * 1.3));
			robot.setMaxHealth((int) (robot.getMaxHealth() * 1.3));
			robot.setSpeed((int) (robot.getSpeed() * 1.3));
			robot.setStrength((int) (robot.getStrength() * 1.3));
			robot.setValue(robot.getValue() * 2 + 1000);
		}
		return robot;
	}

	public BattleRobot generateEasyEnemy() {
		return generateEnemy(1, 15, 1, 15, 1, 15, 50, 200, 0.5, null, DifficultyClass.WEAK);
	}

	public BattleRobot generateMediumEnemy() {
		return generateEnemy(10, 30, 10, 30, 10, 30, 250, 500, .75, null, DifficultyClass.AVERAGE);
	}

	public BattleRobot generateHardEnemy() {
		return generateEnemy(30, 20, 30, 50, 30, 50, 500, 1000, 1, null, DifficultyClass.STRONG);
	}

	public BattleRobot generateLegendaryEnemy() {
		return generateEnemy(50, 50, 50, 100, 50, 100, 1000, 2000, 1.25, null, DifficultyClass.LEGENDARY);
	}

	public BattleRobot generateGodlikeEnemy() {
		return generateEnemy(Battle.getMaxSpeed(), 0, 250, 0, 250, 0, 10000, 0, 1.5, "God Incarnate",
				DifficultyClass.GODLIKE);
	}
}
