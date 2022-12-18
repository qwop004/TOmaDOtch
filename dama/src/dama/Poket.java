package dama;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Poket {

	public static String filePath = "Tamagotchi.txt"; //사용할 파일명 정의
	public String fileData[] = getFileData();
	
	public String race = fileData[0]; // 종족
	public String name = fileData[1]; // 캐릭터 이름
	
	public int age = Integer.parseInt(fileData[2]); // 나이
	public int power = Integer.parseInt(fileData[3]); // 힘
	public int energy = Integer.parseInt(fileData[4]); // 체력

	public int cnt = Integer.parseInt(fileData[5]); //점수
	public int point = Integer.parseInt(fileData[6]); //포인트

	//캐틱터 정보 반환
	public int getAge() {return age;}
	public int getPower() {return power;}
	public int getEnergy() {return energy;}
	public int getCnt() {return cnt;}
	public int getPoint() {return point;}
	
	//포인트 설정
	public void setPoint(int point) {this.point = point;}
	
	public static String[] getFileData() { //캐릭터 정보 읽어오기
		ArrayList<String> packet = new ArrayList<>();		
		String[] input_arr = new String[packet.size()];		
		BufferedReader br = null;				
		try {
			br = new BufferedReader(new FileReader(new File(filePath))); 
			String s; 
			while ((s = br.readLine()) != null) {
				if(!s.isBlank())
				packet.add(s);
			}
			input_arr = packet.toArray(input_arr);		
			br.close();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		finally {
			if(br != null) {
				try {br.close();}
				catch (IOException e) {e.printStackTrace();}
			}
		}
		return input_arr;
	}
		
	public void setFileData() {//GUI 닫을 때 캐릭터 관련 변경사항을 파일에 저장
		try (FileOutputStream fos = new FileOutputStream(filePath, false)) {} //false 옵션으로 파일을 열면 내용이 사라짐.
		catch(IOException e) {e.printStackTrace();}
		try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true));
            fw.write(race);
            fw.newLine();
            fw.write(name);
            fw.newLine();
            fw.write(Integer.toString(age));
            fw.newLine();
            fw.write(Integer.toString(power));
            fw.newLine();
            fw.write(Integer.toString(energy));
            fw.newLine();
            fw.write(Integer.toString(cnt));
            fw.newLine();
            fw.write(Integer.toString(point));
            fw.close();
        }
        catch(Exception e) {e.printStackTrace();}
	}
	
	public void pointStatus(int num) {
		int prePoint = getPoint();
		setPoint(prePoint+num);
	}
	
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

