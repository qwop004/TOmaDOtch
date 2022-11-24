package dama;
import java.util.*;

public class ExcMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // 너무나도 익숙한 Scanner
		Poket p = null; // Poket 객체 선언 및 null값으로 초기화
		System.out.println("포켓몬을 선택하시오.");
		System.out.println("1. 피카츄\n2. 이상해씨\n3. 꼬부기"); //됐나?
				int x = sc.nextInt(); // 종족값을 입력받음
				System.out.println("이름을 입력하시오.");
				String name = sc.next(); // 이름을 입력받음
				
				if(x==1)
					p = new Picachu(name);
				else if(x==2)
					p = new Lee(name);
				else if(x==3)
					p = new Gobook(name);
				boolean flag = true;
				
				while(flag)
				{
					System.out.println("메뉴");
					System.out.println("1. 밥\n2. 잠\n3. 운동\n4. 공격\n5. 끝");
					x = sc.nextInt();
					switch(x)
					{
					case 1:
						p.eat();
						p.print();
						break;
						
					case 2:
						p.sleep();
						p.print();
						break;
						
					case 3:
						p.exercise();
						p.print();
						if(p.energy <= 0)
						{
							System.out.println("게임 종료@@@@@@@@@@@@@@@@@@@@@@@@@@@");
							flag = false;
						}
						break;
						
					case 4:
						p.attack();
						p.print();
						if(p.energy <= 0)
						{
							System.out.println("게임 종료");
							flag = false;
						}
						break;
						
					case 5:
					flag = false;
					}
				}
	}
}
