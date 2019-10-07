package com.fdmgroup.weapon;

public enum EffectType {
	VAMPIRISM("Vampiric"), CRITICAL_HITS("Critical"), 
	PARALYSIS("Paralyzing"), CHAOTIC("Chaotic"),
	RECOIL("Recoiling"), INNACURATE("Erratic"), 
	GOLD_GAINING("Thrifty"), GOLD_USING("Expensive"),
	ARMOR_PIERCE("Piercing"), DOUBLE_STRIKE("Double-strike");
	
	private String descriptor;

	private EffectType(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getDescriptor() {
		return descriptor;
	}
	
	
}
