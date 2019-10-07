package com.fdmgroup;

import com.fdmgroup.robot.BattleRobot;

public class BattleResults {
	private int winnings;
	private boolean win;
	private String battleLog="";
	private BattleRobot attacker;
	private BattleRobot defender;
	

	public BattleResults(BattleRobot attacker, BattleRobot defender) {
		super();
		this.attacker = attacker;
		this.defender = defender;
	}

	public BattleRobot getAttacker() {
		return attacker;
	}

	public void setAttacker(BattleRobot attacker) {
		this.attacker = attacker;
	}

	public BattleRobot getDefender() {
		return defender;
	}

	public void setDefender(BattleRobot defender) {
		this.defender = defender;
	}

	public int getWinnings() {
		return winnings;
	}

	public void setWinnings(int winnings) {
		this.winnings = winnings;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public String getBattleLog() {
		return battleLog;
	}

	public void setBattleLog(String battleLog) {
		this.battleLog = battleLog;
	}

	public void addToLog(String addition) {
		battleLog += addition+"<br/>";
	}

}
