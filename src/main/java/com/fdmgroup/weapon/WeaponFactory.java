package com.fdmgroup.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.fdmgroup.robot.DifficultyClass;

@Component
public class WeaponFactory {
	private Random random = new Random();
	private List<String> weaponNames = new ArrayList<>();

	{
		weaponNames.add("Torcher");
		weaponNames.add("Slicer");
		weaponNames.add("Basher");
		weaponNames.add("Clobberer");
		weaponNames.add("Tickler");
		weaponNames.add("Stabber");
		weaponNames.add("Frier");
		weaponNames.add("Masher");
		weaponNames.add("Smasher");
		weaponNames.add("Crusher");
	}

	private List<EffectType> npcEffects = new ArrayList<>();

	{
		npcEffects.add(EffectType.ARMOR_PIERCE);
		npcEffects.add(EffectType.CRITICAL_HITS);
		npcEffects.add(EffectType.INNACURATE);
		npcEffects.add(EffectType.VAMPIRISM);
		npcEffects.add(EffectType.RECOIL);
		npcEffects.add(EffectType.CHAOTIC);
		npcEffects.add(EffectType.PARALYSIS);
		npcEffects.add(EffectType.DOUBLE_STRIKE);
	}

	private List<EffectType> pcEffects = new ArrayList<>();

	{
		for (EffectType type : EffectType.values()) {
			pcEffects.add(type);
		}
	}

	public RobotWeapon getWeapon(int minEffectNumber, int randomExtraEffectMax, double minEffectStrength,
			double effectStrengthMax, double damageMultiplierMin, double randomExtraDamageMultiplier,
			boolean isPc) {
		int effectNumber = random.nextInt(randomExtraEffectMax + 1) + minEffectNumber;
		RobotWeapon robotWeapon = new RobotWeapon();
		double effectPercent = random.nextDouble() * effectStrengthMax + minEffectStrength;
		robotWeapon.setDamageMultiplier(damageMultiplierMin + (random.nextDouble() * randomExtraDamageMultiplier));
		for (int i = 0; i < effectNumber; i++) {
			WeaponEffect effect;
			if (isPc) {
				effect = generateEffect(effectPercent, robotWeapon, pcEffects);
			} else {
				effect = generateEffect(effectPercent, robotWeapon, npcEffects);
			}
			robotWeapon.addWeaponEffect(effect);
		}
		robotWeapon.setDescription(generateDescription(robotWeapon));
		return robotWeapon;
	}

	public RobotWeapon getWeakWeapon(boolean isPc) {
		return getWeapon(0, 1, 0, 0.2, 0.9, 0.2, isPc);
	}

	public RobotWeapon getMediumWeapon(boolean isPc) {
		return getWeapon(0, 2, 0.2, 0.2, 1, .25, isPc);
	}

	public RobotWeapon getStrongWeapon(boolean isPc) {
		return getWeapon(1, 1, 0.3, 0.3, 1.1, .5, isPc);
	}

	public RobotWeapon getLegendaryWeapon(boolean isPc) {
		return getWeapon(1, 2, 0.4, 0.4, 1.25, .75, isPc);
	}

	public RobotWeapon getGodlikeWeapon(boolean isPc) {
		return getWeapon(2, 1, .5, .5, 1.5, 1, isPc);
	}

	public RobotWeapon getRandomWeapon(DifficultyClass maxLevel, boolean isPc) {
		System.out.println("Level of random generation: "+maxLevel.getDifficultyLevel());
		int level = random.nextInt(maxLevel.getDifficultyLevel()) + 1;
		switch (level) {
		case 1:
			return getWeakWeapon(isPc);
		case 2:
			return getMediumWeapon(isPc);
		case 3:
			return getStrongWeapon(isPc);
		case 4:
			return getLegendaryWeapon(isPc);
		case 5:
			return getGodlikeWeapon(isPc);
		default:
			return null;
		}

	}

	private WeaponEffect generateEffect(double effectPercent, RobotWeapon robotWeapon, List<EffectType> effects) {
		EffectType type;
		boolean notDuplicate;
		do {
			notDuplicate = true;
			type = effects.get(random.nextInt(effects.size()));
			if (robotWeapon.getWeaponEffects().containsKey(type)) {
				notDuplicate = false;
			}

		} while (!notDuplicate);
		WeaponEffect effect = new WeaponEffect(type);
		effect.setEffectStrength(effectPercent);
		switch (type) {

		case CRITICAL_HITS:
			effect.setMultiplier(1 - (0.10 * effectPercent));
			effect.setEffectGoldValue(10000 * effectPercent);
			break;
		case CHAOTIC:
			effect.setMultiplier(1 + (0.25 * effectPercent));
			effect.setEffectGoldValue(100);
			break;
		case GOLD_GAINING:
			effect.setMultiplier(1 - (0.25 * effectPercent));
			effect.setEffectGoldValue(250);
			break;
		case GOLD_USING:
			effect.setMultiplier(1);
			effect.setEffectGoldValue(20000 * effectPercent);
			break;
		case INNACURATE:
			effect.setMultiplier(1 + (1.25 * effectPercent));
			effect.setEffectGoldValue(100);
			break;
		case PARALYSIS:
			effect.setMultiplier(1 - (0.5 * effectPercent));
			effect.setEffectGoldValue(30000 * effectPercent);
			break;
		case ARMOR_PIERCE:
			effect.setMultiplier(1 + (0.2 * effectPercent));
			effect.setEffectGoldValue(15000 * effectPercent);
			break;
		case RECOIL:
			effect.setMultiplier(1 + (1 * effectPercent));
			effect.setEffectGoldValue(2500 * effectPercent);
			break;
		case VAMPIRISM:
			effect.setMultiplier(1 - (0.3 * effectPercent));
			effect.setEffectGoldValue(20000 * effectPercent);
			break;
		case DOUBLE_STRIKE:
			effect.setMultiplier(1);
			effect.setEffectGoldValue(30000 * effectPercent);
			break;

		default:
			System.err.println("??????????");
		}

		return effect;
	}

	public String generateDescription(RobotWeapon weapon) {
		String description = "";
		for (WeaponEffect effect : weapon.getWeaponEffects().values()) {
			description += effect.getType().getDescriptor() + " ";
		}
		description += strengthAppelate(weapon.getDamageMultiplier()) + " ";
		description += generateName();
		return description;

	}

	private String strengthAppelate(double damageMultiplier) {
		if (damageMultiplier < .5) {
			return "Ineffectual";
		} else if (damageMultiplier < .9) {
			return "Weak";
		} else if (damageMultiplier < 1.1) {
			return "Average";
		} else if (damageMultiplier < 1.5) {
			return "Strong";
		} else if (damageMultiplier < 2) {
			return "Powerful";
		} else {
			return "Devastating";
		}
	}

	private String generateName() {
		int index = random.nextInt(weaponNames.size());
		return weaponNames.get(index);
	}

	public RobotWeapon getWeakBossWeapon(boolean isNpc) {
		RobotWeapon weapon = getWeapon(1, 0, .25, 0, 1.25, 0, isNpc);
		bossNameReplace(weapon);
		weapon.setValue(weapon.getValue()*3);
		return weapon;
	}
	
	private void bossNameReplace(RobotWeapon weapon) {
		weapon.setDescription(weapon.getDescription().replace("Ineffectual", "Boss").replace("Weak", "Boss").replace("Average", "Boss").replace("Strong", "Boss").replace("Powerful", "Boss").replace("Devastating", "Boss"));
		
	}

	public RobotWeapon getMediumBossWeapon(boolean isPc) {
		RobotWeapon weapon = getWeapon(1, 1, 0.5, 0, 1.35, 0, isPc);
		bossNameReplace(weapon);
		weapon.setValue(weapon.getValue()*3);
		return weapon;
	}

	public RobotWeapon getStrongBossWeapon(boolean isPc) {
		RobotWeapon weapon = getWeapon(2, 0, .6, 0, 1.7, 0, isPc);
		bossNameReplace(weapon);
		weapon.setValue(weapon.getValue()*3);
		return weapon;
	}

	public RobotWeapon getLegendaryBossWeapon(boolean isPc) {
		RobotWeapon weapon = getWeapon(2, 1, .8, 0 , 2, .5, isPc);
		bossNameReplace(weapon);
		weapon.setValue(weapon.getValue()*3);
		return weapon;
	}

	public RobotWeapon getGodlikeBossWeapon(boolean isPc) {
		RobotWeapon weapon = getWeapon(3, 0, 1, 0, 2.5, 1, isPc);
		bossNameReplace(weapon);
		weapon.setValue(weapon.getValue()*3);
		return weapon;
	}

	public RobotWeapon generateBossWeapon(DifficultyClass difficultyClass, boolean isPc) {
		switch (difficultyClass) {
		case WEAK:
			return getWeakBossWeapon(isPc);
		case AVERAGE:
			return getMediumBossWeapon(isPc);
		case STRONG:
			return getStrongBossWeapon(isPc);
		case LEGENDARY:
			return getLegendaryBossWeapon(isPc);
		case GODLIKE:
			return getGodlikeBossWeapon(isPc);
		default:
			return null;
		}
		
	}

}
