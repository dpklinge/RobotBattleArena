package com.fdmgroup.weapon;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeaponEffect {
	private String appellate;
	private EffectType type;
	private double multiplier = 1;
	private double criticalChance = 0.1;
	private double effectGoldValue;
	private double effectStrength;
	
	
	public WeaponEffect(EffectType type) {
		this.type=type;
	}


	public String getAppellate() {
		return appellate;
	}


	public void setAppellate(String appellate) {
		this.appellate = appellate;
	}


	public EffectType getType() {
		return type;
	}


	public void setType(EffectType type) {
		this.type = type;
	}


	public double getMultiplier() {
		return multiplier;
	}


	public void setMultiplier(double multiplier) {
		BigDecimal bd = new BigDecimal(multiplier);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		multiplier = bd.doubleValue();
		this.multiplier = multiplier;
	}


	public double getCriticalChance() {
		return criticalChance;
	}


	public void setCriticalChance(double criticalChance) {
		this.criticalChance = criticalChance;
	}


	public double getEffectGoldValue() {
		return effectGoldValue;
	}


	public void setEffectGoldValue(double effectGoldValue) {
		this.effectGoldValue = effectGoldValue;
	}


	public double getEffectStrength() {
		return effectStrength;
	}


	public void setEffectStrength(double effectStrength) {
		BigDecimal bd = new BigDecimal(effectStrength);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		effectStrength = bd.doubleValue();
		this.effectStrength = effectStrength;
	}


	@Override
	public String toString() {
		return "WeaponEffect [appellate=" + appellate + ", type=" + type + ", multiplier=" + multiplier
				+ ", criticalChance=" + criticalChance + ", effectGoldValue=" + effectGoldValue + ", effectStrength="
				+ effectStrength + "]";
	}



	
	
}
