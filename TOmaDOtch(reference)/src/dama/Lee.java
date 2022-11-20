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
 
	public void eat() // 밥먹기
	{
		energy += 15; // 에너지 15씩 증가
		cnt++; // cnt는 1씩 증가
		aging(); // aging()매소드를 호출
	}
	
	public void sleep() // 자기
	{
		energy += 5;
		cnt++;
		aging();
	}
	
	public void exercise() // 운동
	{
		energy -= 10;
		power += 10;
	}
	
	public void aging() // 나이먹기
	{
		if(cnt > 3)
		{
			age++;
			cnt = 0;
		}
	}
 
	public void attack() // 공격
	{
		energy -= 15;
		power += 20;
	}
	
	public void print() // 마찬가지로 상태출력
	{
		System.out.println("나이 : " +age);
		System.out.println("파워 : " +power);
		System.out.println("에너지 : " +energy);
	}	
}