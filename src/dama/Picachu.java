package dama;

public class Picachu extends Poket {
	public int cnt;
	public Picachu(String name)
	{
		this.name = name;
		race = "Picachu";
		age = 0;
		power = 100;
		energy = 300;
		cnt = 0;
		System.out.println("피카츄가 만들어졌습니다.");
	}
}