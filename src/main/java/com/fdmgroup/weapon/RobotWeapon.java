package com.fdmgroup.weapon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class RobotWeapon {
	private Map<EffectType, WeaponEffect> weaponEffects = new HashMap<>();
	private double damageMultiplier=1;
	private int value = 100;
	private String description;
	
	public void addWeaponEffect(WeaponEffect effect){
		
		damageMultiplier += (effect.getMultiplier() - 1);
		BigDecimal bd = new BigDecimal(damageMultiplier);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		damageMultiplier = bd.doubleValue();
		value+=effect.getEffectGoldValue();
		weaponEffects.put(effect.getType(), effect);
	}

	
	

	public double getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(double damageMultiplier) {
		BigDecimal bd = new BigDecimal(damageMultiplier);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		damageMultiplier = bd.doubleValue();
		this.damageMultiplier = damageMultiplier;
	}

	public int getValue() {
		return value;
	}

	public Map<EffectType, WeaponEffect> getWeaponEffects() {
		return weaponEffects;
	}

	public void setValue(int value) {
		this.value = value;
	}


	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	@Override
	public String toString() {
		return "RobotWeapon [weaponEffects=" + weaponEffects + ", damageMultiplier=" + damageMultiplier + ", value="
				+ value + "]";
	}
	
	
	
	
	
	


}
