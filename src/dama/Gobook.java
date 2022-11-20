package dama;

public class Gobook extends Poket {
	public int cnt;
	public Gobook (String name)
	{
		this.name = name;
		race = "Gobook";
		age = 0;
		power = 50;
		energy = 150;
		cnt = 0;
		System.out.println("꼬북이가 만들어졌습니다.");
	}
}