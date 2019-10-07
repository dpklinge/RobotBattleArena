package com.fdmgroup.robot;

public enum DifficultyClass {
	PC(0), WEAK(1), AVERAGE(2), STRONG(3), LEGENDARY(4), GODLIKE(5);
	
	private int difficultyLevel;
	
	private DifficultyClass(int num){
		difficultyLevel=num;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}
	
	
}
