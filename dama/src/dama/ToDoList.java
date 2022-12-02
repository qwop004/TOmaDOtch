package dama;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToDoList {
	
	public static String filePath = "data.txt"; //사용할 파일명 정의
	
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
		String[] Array = getToDoList();
		List<String> newList = new ArrayList<>(Arrays.asList(Array));
		newList.remove(num);
		
		Array = newList.toArray(new String[0]);
		
		 try (FileOutputStream fos = new FileOutputStream(filePath, false)) { //false 옵션으로 파일을 열면 내용이 사라짐.

		 } catch(IOException e) {e.printStackTrace();} 
		
		for(int i=0; i<=Array.length; i++) {
			
			if(!Array[i].isBlank()) {
				System.out.println("Start");
				System.out.println(Array[i]); //정상 반환되는지 볼려고 콘솔창에 출력
				addToDoList(Array[i]);}
		}
//		
//		Array = newList.toArray(new String[0]);
//		
//		try (FileOutputStream fos = new FileOutputStream(filePath, false)) {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//		
//		addToDoList(Array);
		
	}


	

}

	