package edu.yu.intro;

public class BasketballPlayer {

	private int seasonPoints;
	private int seasonFouls;
	private long yearlySalary;

	public BasketballPlayer (int points, int fouls, long salary) {
		this.seasonPoints = points;
		this.seasonFouls = fouls;
		this.yearlySalary = salary;
	}
	public int getPoints() {
		return seasonPoints;
	}
	public int getFouls() {
		return seasonFouls;
	}
	public long getSalary() {
		return yearlySalary;
	}
	
	public void mvpChant (int mostPoints) {	
		if (seasonPoints == mostPoints) {
			System.out.println("MVP! MVP! MVP!");
		}
		else {
			System.out.println("Trade Him!");
		}
	}
	
	public long foulsDeduction () {
		if (seasonFouls > 200) {
			yearlySalary -= 1000; 
		}
		return yearlySalary;
	}
}	