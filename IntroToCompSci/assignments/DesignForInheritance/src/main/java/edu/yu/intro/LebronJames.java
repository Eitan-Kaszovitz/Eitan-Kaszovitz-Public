package edu.yu.intro;


public class LebronJames extends BasketballPlayer {
	private long endorsementMoney;
	public LebronJames (int points, int fouls, long salary, long endorsementMoney) {
		super(points, fouls, salary);
		this.endorsementMoney = endorsementMoney;
	}
	
	public long getEndorsements() {
		return endorsementMoney;
	}
	
	@Override
	public long foulsDeduction () {
		long lebronSalary = getSalary();
		if (getFouls() > 200) {
			lebronSalary -= 20; ////// Lebron's too good to penalize so much for fouls
		}
		return lebronSalary;	
	}
	
	public long endorsementBonus() {
		if (getPoints() > 2000) {
			endorsementMoney += 10000000;
		}
		return endorsementMoney;
	}
	
	public static void main (String[] args) {
		BasketballPlayer player = new BasketballPlayer (1000, 210, 7000000);
		System.out.print("Player Chant: ");
		player.mvpChant(900);
		System.out.println("Original Salary: " + player.getSalary() + ". Salary after getting accounting for fouls in this season: " + player.foulsDeduction());
		
		LebronJames lebron2018 = new LebronJames(2200, 235, 25000000, 50000000);
		System.out.println("Original Salary: " + lebron2018.getSalary() + ". Salary after getting accounting for fouls in this season: " + lebron2018.foulsDeduction());
		System.out.println("Original Endorsement Pay: " + lebron2018.getEndorsements() + ". Endorsement Pay after accounting for points this season: " + lebron2018.endorsementBonus());
	
	}
} 