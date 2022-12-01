package dama;

public class Poket {
	public String race; // 종족
	public String name; // 캐릭터 이름
	public int age; // 나이
	public int power; // 파워
	public int energy; // 에너지
	int cnt;
	
	public Poket() // 생성자
	{
		System.out.println("시작");
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
	
	public void aging() // 나이 먹기
	{
		if(cnt > 3) // cnt가 3보다 커지면
		{
			age++; // 나이를 먹는다
			cnt = 0;
		}
	}
 
	public void attack() // 공격하기
	{
		energy -= 15;
		power += 20;
	}
	
	public void print() // 현재 상태출력 하기
	{
		System.out.println("나이 : " +age);
		System.out.println("파워 : " +power);
		System.out.println("에너지 : " +energy);
	}
}

