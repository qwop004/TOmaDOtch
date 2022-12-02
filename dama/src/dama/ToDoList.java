package dama;


import java.io.*;
import java.util.ArrayList;


public class ToDoList {
	
	public static String filePath = "data.txt";
	
	public static String[] getToDoList() { //투두리스트 반환	
		ArrayList<String> packet = new ArrayList<>();		
		String[] input_arr = new String[packet.size()];		
		BufferedReader br = null;				
		try {
			br = new BufferedReader(new FileReader(new File(filePath))); 
			String s; 
			while ((s = br.readLine()) != null) {
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
	
	public static void addToDoList(String newToDo){ //투두리스트 적용
		try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(filePath,true));
            fw.newLine();
            String lineToAppend = newToDo;    
            fw.write(lineToAppend);
            fw.close();
        }
        catch(Exception e) {e.printStackTrace();}
     }
}

	