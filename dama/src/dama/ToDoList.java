package dama;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToDoList {
	
	public static String filePath = "ToDoList.txt"; //사용할 파일명 정의
	public static String temporary = "temp.txt"; //임시파일... 쓸까 말까 고민 중인데 일단 걍 만든 편. 뭐 아무래도 체크박스 표시 여부 저장할 파일은 필요하잖아요?
	
	public static String[] getToDoList() { //투두리스트 반환	
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
	
	public static void addToDoList(String newToDo){ //투두리스트 추가
		try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true));
            fw.newLine();
            String lineToAppend = newToDo;    
            fw.write(lineToAppend);
            fw.close();
        }
        catch(Exception e) {e.printStackTrace();}
     }

	
	public static void deleteToDoList(int num) { //투두리스트 삭제
		String[] afterArray = getToDoList();
		List<String> newList = new ArrayList<>(Arrays.asList(afterArray));
		newList.remove(num);
		afterArray = newList.toArray(new String[0]);
		try (FileOutputStream fos = new FileOutputStream(filePath, false)) {} //false 옵션으로 파일을 열면 내용이 사라짐.
		catch(IOException e) {e.printStackTrace();} 
		
		for(int i=0; i<afterArray.length; i++) {
			addToDoList(afterArray[i]);
		}
	}
	
	public static Boolean[] getIsCleared() {
		ArrayList<Boolean> packet = new ArrayList<>();		
		Boolean[] IsCleared = new Boolean[8];		
		BufferedReader br = null;				
		try {
			br = new BufferedReader(new FileReader(new File(temporary))); 
			String s;
			while ((s = br.readLine()) != null) {
				if(!s.isBlank())
				packet.add(Boolean.parseBoolean(s));
			}
			IsCleared = packet.toArray(IsCleared);		
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
		return IsCleared;
	}
	
	public static void setCheckBoxes(Boolean[] nowChecked){ //체크박스 상태정보
		
		try (FileOutputStream fos = new FileOutputStream(temporary, false)) { //false 옵션으로 파일을 열면 내용이 사라짐.
			BufferedWriter fw = new BufferedWriter(new FileWriter(temporary,true));
			for(int i=0; i<8; i++) {
				fw.newLine();
	            fw.write(nowChecked[i].toString());
			}
            fw.close();
		}
		catch(IOException e) {e.printStackTrace();} 
	}	
}