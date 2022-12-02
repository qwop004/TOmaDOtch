package dama;

public class Gobook extends Poket {
	public int cnt;
	public Gobook (String name)
	{
		this.name = name;
		race = "Gobook";
		Poket poket = new Poket();
		poket.setAge(0); // 나는 이러면 될줄알았찌..
		//age = 0;
		poket.setPower(50);
		poket.setEnergy(150);
		poket.setCnt(0);
		System.out.println("꼬북이가 만들어졌습니다.");
	}
}