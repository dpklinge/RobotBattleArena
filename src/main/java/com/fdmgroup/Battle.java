package com.fdmgroup;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.fdmgroup.robot.BattleRobot;
import com.fdmgroup.weapon.EffectType;
import com.fdmgroup.weapon.RobotWeapon;
import com.fdmgroup.weapon.WeaponEffect;

@Component
public class Battle {
	private static int turnspeed = 100;

	public static int getMaxSpeed() {
		return turnspeed;
	}

	private Random random = new Random();
	private BattleRobot player;

	public BattleResults doBattle(BattleRobot playerRobot, BattleRobot enemyRobot) {
		player = playerRobot;
		BattleResults results = new BattleResults(playerRobot, enemyRobot);
		results.setWinnings(enemyRobot.getValue());
		results.addToLog("Battle between " + playerRobot.getName() + " and " + enemyRobot.getName() + "");
		int playerSpeedCount = 0;
		int enemySpeedCount = 0;
		System.out.println("Entering combat loop");
		int counter = 0;
		while (playerRobot.getCurrentHealth() > 0 && enemyRobot.getCurrentHealth() > 0 && counter < 10000) {
			System.out.println("Combat loop");
			playerSpeedCount += playerRobot.getSpeed();
			enemySpeedCount += enemyRobot.getSpeed();
			while ((int)(playerSpeedCount / turnspeed) > 0 && (int)(enemySpeedCount / turnspeed) > 0
					&& playerRobot.getCurrentHealth() > 0 && enemyRobot.getCurrentHealth() > 0) {
				if (playerSpeedCount / turnspeed > 0 && playerSpeedCount>=enemySpeedCount) {
					attemptStrike(playerRobot, enemyRobot, results);
					playerSpeedCount -= turnspeed;
				}
				if (enemySpeedCount / turnspeed > 0 && enemyRobot.getCurrentHealth() > 0 && enemySpeedCount>playerSpeedCount ) {
					attemptStrike(enemyRobot, playerRobot, results);
					enemySpeedCount -= turnspeed;
				}
				counter++;
			}
		}
		if (counter > 10000) {
			results.addToLog("Battle resulted in a draw.");
		}
		results.setWin((playerRobot.getCurrentHealth() > 0));
		return results;
	}

	private void attemptStrike(BattleRobot attacker, BattleRobot defender, BattleResults results) {
		if (attacker.isParalyzed()) {
			results.addToLog(attacker.getName() + " was paralyzed by " + defender.getName() + "'s "
					+ defender.getWeapon().getDescription() + " and could not attack!");
			attacker.setParalyzed(false);
		} else if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.INNACURATE)) {
			double hitRoll = random.nextDouble();
			if (hitRoll < (attacker.getWeapon().getWeaponEffects().get(EffectType.INNACURATE).getEffectStrength()
					/ 2)) {
				results.addToLog(attacker.getName() + " missed!");
			} else {
				strike(attacker, defender, results);
			}
		} else {
			strike(attacker, defender, results);

		}

	}

	private void strike(BattleRobot attacker, BattleRobot defender, BattleResults results) {

		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.DOUBLE_STRIKE)) {
			double hitRoll = random.nextDouble();
			if (hitRoll < (attacker.getWeapon().getWeaponEffects().get(EffectType.DOUBLE_STRIKE).getEffectStrength()
					/ 2)) {
				doDamage(attacker, defender, results);
				results.addToLog(attacker.getName() + "'s double-strike weapon hits again!");
			}
		}
		doDamage(attacker, defender, results);
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.CHAOTIC)) {
			double hitRoll = random.nextDouble();
			if (hitRoll < .02) {
				results.addToLog(
						attacker.getName() + "'s chaotic weapon chain strikes, doubling the number of attacks!");
				strike(attacker, defender, results);
			}
		}

	}

	private void applyParalysis(BattleRobot defender, BattleRobot attacker, WeaponEffect weaponEffect,
			BattleResults results) {

		if (random.nextDouble() < (weaponEffect.getEffectStrength() * 0.5)) {
			results.addToLog(
					attacker.getName() + "'s weapon paralyzes " + defender.getName() + "! They cannot attack!");
			defender.setParalyzed(true);
		}
	}

	public void doDamage(BattleRobot attacker, BattleRobot defender, BattleResults results) {
		RobotWeapon weapon = attacker.getWeapon();

		int damage = (int) ((random.nextDouble() + 0.5) * attacker.getStrength() * weapon.getDamageMultiplier());

		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.GOLD_USING)) {
			damage = applyGoldUsing(damage, attacker.getWeapon().getWeaponEffects().get(EffectType.GOLD_USING),
					defender, results);
		}
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.CRITICAL_HITS)) {
			damage = applyCriticals(damage, attacker.getWeapon().getWeaponEffects().get(EffectType.CRITICAL_HITS),
					defender, results);
		}
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.ARMOR_PIERCE)) {
			damage = applyArmorPierce(damage, attacker.getWeapon().getWeaponEffects().get(EffectType.ARMOR_PIERCE),
					defender, results);
		}
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.CHAOTIC)) {
			damage = applyChaoticEffects(attacker, damage,
					attacker.getWeapon().getWeaponEffects().get(EffectType.CHAOTIC), defender, results);
		}
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.PARALYSIS)) {
			applyParalysis(defender, attacker, attacker.getWeapon().getWeaponEffects().get(EffectType.PARALYSIS),
					results);
		}
		damage = applyArmorDamageReduction(defender, attacker, damage);

		defender.setCurrentHealth(defender.getCurrentHealth() - damage);
		results.addToLog(defender.getName() + " was struck for " + damage + " damage!");

		applyRecoil(damage, attacker, defender, results);
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.GOLD_GAINING)) {
			applyGoldLeech(attacker, damage, attacker.getWeapon().getWeaponEffects().get(EffectType.GOLD_GAINING),
					defender, results);
		}
		applyVampirism(damage, attacker, defender, results);

	}

	private int applyArmorDamageReduction(BattleRobot defender, BattleRobot attacker, int damage) {

		if (damage <= defender.getArmor()) {
			damage = 1;
		} else {
			damage -= defender.getArmor();
		}
		return damage;
	}

	private int applyChaoticEffects(BattleRobot attacker, int damage, WeaponEffect weaponEffect, BattleRobot defender,
			BattleResults results) {
		double rand = random.nextDouble();
		if (rand < 0.15) {
			results.addToLog("Chaotic weapon dealt reduced damage!");
			damage -= damage * weaponEffect.getEffectStrength();
		} else if (rand < 0.25) {
			results.addToLog("Chaotic weapon dealt increased damage!");
			damage += damage * weaponEffect.getEffectStrength();
		} else if (rand < 0.35) {
			results.addToLog("Chaotic weapon attempted to paralyze!");
			applyParalysis(defender, attacker, weaponEffect, results);
		} else if (rand < 0.45 && attacker == player) {
			results.addToLog("Chaotic weapon converts money to damage!");
			damage = applyGoldUsing(damage, weaponEffect, defender, results);
		} else if (rand < 0.55) {
			results.addToLog("Chaotic weapon punctures the enemy armor!");
			damage = applyArmorPierce(damage, weaponEffect, defender, results);
		} else if (rand < 0.65) {
			results.addToLog("Chaotic weapon vampirically leaches the enemy!");
			applyVampirism(damage, attacker, defender, results);
		} else if (rand < 0.75) {
			results.addToLog("Chaotic weapon deals a critical hit!");
			damage = applyCriticals(damage, weaponEffect, defender, results);
		} else if (rand < 0.85) {
			if (attacker == results.getAttacker()) {
				results.addToLog("Chaotic weapon leaches funds!");
				applyGoldLeech(attacker, damage, weaponEffect, defender, results);
			}
		} else if (rand < 0.995) {
			results.addToLog("Chaotic damage fluctuates wildly!");
			damage = (int) (random.nextDouble() * 3 * damage);
		} else {
			results.addToLog("******JACKPOT!******");
			results.addToLog("Gold overflows from your chaotic weapon!");
			results.setWinnings(results.getWinnings() + 10000);
		}
		return damage;
	}

	private void applyVampirism(int damage, BattleRobot attacker, BattleRobot defender, BattleResults results) {
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.VAMPIRISM)) {
			int healing = (int) (damage
					* attacker.getWeapon().getWeaponEffects().get(EffectType.VAMPIRISM).getEffectStrength());
			if (healing > 0) {
				int newHealth = (int) (attacker.getCurrentHealth() + healing);
				if (newHealth > attacker.getMaxHealth()) {
					attacker.setCurrentHealth(attacker.getMaxHealth());
					results.addToLog(attacker.getName() + " vampirically healed to full health! ");
				} else {
					attacker.setCurrentHealth(attacker.getCurrentHealth() + healing);
					results.addToLog(attacker.getName() + " vampirically healed " + healing + "! ");
				}
			}
		}

	}

	private void applyGoldLeech(BattleRobot attacker, int damage, WeaponEffect weaponEffect, BattleRobot defender,
			BattleResults results) {
		if (attacker == results.getAttacker()) {
			System.out.println("Money before gold leech: " + results.getWinnings());
			int moneyGain = (int) (damage * weaponEffect.getEffectStrength());
			System.out.println("Money leeched by gold leech: " + moneyGain);
			if (moneyGain > 0) {
				results.addToLog("Thrifty weapon extracts " + moneyGain + "g from foe.");
				results.setWinnings(results.getWinnings() + moneyGain);
				System.out.println("Money after gold leech: " + results.getWinnings());
			}
		}

	}

	private void applyRecoil(int damage, BattleRobot attacker, BattleRobot defender, BattleResults results) {
		if (attacker.getWeapon().getWeaponEffects().containsKey(EffectType.RECOIL)) {
			int recoil = ((int) (attacker.getWeapon().getWeaponEffects().get(EffectType.RECOIL).getEffectStrength()
					* damage / 2));
			if (recoil < 1) {
				recoil = 1;
			}
			if (recoil > attacker.getCurrentHealth()) {
				results.addToLog(
						attacker.getName() + " suffers " + (attacker.getCurrentHealth() - 1) + " damage in recoil.");
				attacker.setCurrentHealth(1);
			} else {
				results.addToLog(attacker.getName() + " suffers " + recoil + " damage in recoil.");
				attacker.setCurrentHealth(attacker.getCurrentHealth() - recoil);
			}
		}

	}

	private int applyArmorPierce(int damage, WeaponEffect weaponEffect, BattleRobot defender, BattleResults results) {
		int pierce = (int) (weaponEffect.getEffectStrength() * 0.75 * defender.getArmor());
		results.addToLog(pierce + " armor negated!");
		damage += pierce;
		return damage;

	}

	private int applyCriticals(int damage, WeaponEffect effect, BattleRobot defender, BattleResults results) {

		if (random.nextDouble() < (effect.getEffectStrength())) {
			results.addToLog("Critical hit! ");
			damage *= 1.5;
		}

		return damage;
	}

	private int applyGoldUsing(int damage, WeaponEffect effect, BattleRobot defender, BattleResults results) {

		double percentValueDamage = effect.getEffectStrength() * 0.05;
		int bonusDamage = (int) (defender.getValue() * percentValueDamage);
		damage += bonusDamage;
		results.setWinnings(results.getWinnings() - bonusDamage);
		results.addToLog("Expensive weapon converts " + bonusDamage + " gold to boost damage!");
		return damage;

	}

}
