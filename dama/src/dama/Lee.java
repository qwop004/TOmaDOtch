package dama;

public class Lee extends Poket { // Poket 클래스로 부터 상속한다.
	public int cnt;
	public Lee(String name)
	{
		// Poket에서 가져왔기 때문에 따로 형태를 지정해주지 않아도된다.
		this.name = name; // 이름으로 초기화
		race = "Leesanghae";  // 종족은 이상해로 초기화
		age = 0; // 나이는 0으로 초기화
		power = 150; // 파워는 150으로 초기화
		energy = 200; // 에너지는 200으로 초기화
		cnt = 0; // cnt는 0
		System.out.println("이상해씨가 만들어졌습니다.");
	}
}