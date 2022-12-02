package dama;

import java.io.*;
import java.util.ArrayList;

public class ToDoList {
	
	public static String filePath = "ToDoList.txt"; //사용할 파일명 정의
	
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
		String[] beforeArray = getToDoList();
		String[] afterArray = new String[beforeArray.length - 1];
		int j = 0;
		for (int  i = 0; i<beforeArray.length; i++) {
			if (i != num) afterArray[j++] = beforeArray[i];
		}
		
		try (FileOutputStream fos = new FileOutputStream(filePath, false)) {} //false 옵션으로 파일을 열면 내용이 사라짐.
		catch(IOException e) {e.printStackTrace();} 
		
		for(int i=0; i<afterArray.length; i++) {
			if(!afterArray[i].isBlank()) {
				addToDoList(afterArray[i]);}
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

	