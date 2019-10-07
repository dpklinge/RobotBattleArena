package com.fdmgroup.robot;

import com.fdmgroup.weapon.RobotWeapon;

public class BattleRobot {
	private int currentHealth = 100;
	private int maxHealth = 100;
	private int speed = 5;
	private int strength = 5;
	private int armor = 5;
	private int value = 0;
	private boolean paralyzed = false;
	private String name ="Generic Robot";
	private RobotWeapon weapon;
	

	public boolean isParalyzed() {
		return paralyzed;
	}
	public void setParalyzed(boolean paralyzed) {
		this.paralyzed = paralyzed;
	}
	public RobotWeapon getWeapon() {
		return weapon;
	}
	public void setWeapon(RobotWeapon weapon) {
		this.weapon = weapon;
	}
	public void updateValue(){
		value=(25*speed)+(20*armor)+(15*strength)+(maxHealth);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		
		this.value = value;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getArmor() {
		
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	@Override
	public String toString() {
		return name+"<br/>Health: "+currentHealth+"/"+maxHealth+"<br/>speed: " + speed
				+ "<br/>strength: " + strength + "<br/> armor: " + armor+"<br/>Weapon:"+weapon.getDescription()+"<br/><br/>Gold for beating: "+value;
	}
	
	

}
