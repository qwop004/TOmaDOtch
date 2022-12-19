package dama;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame {
	
	//디자인적인 수치는 여기에서 final로 정의하여 수정이 용이하게끔 한다
	public static final int windowWidth = 500;
	public static final int windowHeight = 300;
	
	public static final int buttonWidth = 50;
	public static final int buttonHeight = 20;
	
	public static final int statusWidth = 200;
	public static final int statusHeight = 20;
	
	public static final int ToDoListWidth = 250;
	public static final int ToDoListHeight = 20;
	
	public static final int blankWidth = 20;
	
	public static final int toDoLength = 8;
	
	//Poket 객체 생성
	Poket poket = new Poket();
	
	//하단 버튼
	Button[] buttons = {
    		new Button("Feed"),
    		new Button("Exercise"),
    		new Button("Sleep"),
    		new Button("Add"),
    		new Button("Delete"),
    		new Button("Reset"),
    		new Button("Apply")
    		};
	
	//캐릭터 정보 라벨
    Label[] statuses = {
    		new Label("Age: " + poket.getAge(), Label.LEFT),
    		new Label("Power: " + poket.getPower(), Label.LEFT),
    		new Label("Energy: " + poket.getEnergy(), Label.LEFT),
    		new Label("Point: " + poket.getPoint(), Label.LEFT)
    		};
    
    String[] tomaDochiImagePath = {"images\\level-0.gif", "images\\level-1.gif", "images\\level-2.gif", "images\\level-3.gif",
    							   "images\\level-4.gif", "images\\level-5.gif", "images\\level-6.gif", "images\\level-7.gif",
								   "images\\level-8.gif", "images\\level-9.gif", "images\\level-10.gif","images\\level-11.gif"};
    
    JLabel tomaDochiImage = new JLabel("", JLabel.CENTER);
    
    //파일에서 읽어들인 투두리스트 저장하는 배열
    String[] toDoString = {"","","","","","","",""};
    
    //투두리스트 표시하는 라벨
    Label[] toDo = {
    		new Label(""),new Label(""),new Label(""),new Label(""),
    		new Label(""),new Label(""),new Label(""),new Label("")
    };
    
    //파일에서 읽어들인 투두리스트 클리어 여부 저장
    Boolean[] isCleared = {false, false, false, false, false, false, false, false};
    
    //완료 여부 표시용 체크박스
    JCheckBox[] checkBoxes = {
    		new JCheckBox(""),new JCheckBox(""),new JCheckBox(""),new JCheckBox(""),
    		new JCheckBox(""),new JCheckBox(""),new JCheckBox(""),new JCheckBox(""),
    };
    
    class JFrameWindowClosingEventHandler extends WindowAdapter {
    	public void windowClosing(WindowEvent e) {
    		poket.setFileData();
    		JFrame frame = (JFrame)e.getWindow();
    		frame.dispose();
    	}
    }
    
    //메인 메소드
	public GUI(String str) {
		
		super(str);

		setTitle("TOmaDOtchi 0.0.0");
        setSize(windowWidth,windowHeight);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	this.addWindowListener(new JFrameWindowClosingEventHandler());
        
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        makeGUI();	
	}
	
	public void makeGUI() {

		//다마고치
        Label tamaTitle = new Label("I'm TOmaDOtchi.", Label.CENTER);
        tamaTitle.setLocation(blankWidth, 10);
		tamaTitle.setSize(statusWidth, ToDoListHeight);
		this.add(tamaTitle);
        for(int i = 0; i<statuses.length; i++) setStatus(statuses[i],i); //캐릭터 스테이터스 표시 라벨 배치

        
        updateTomaDochi();
        
        //투두리스트 배치
        Label toDoTitle = new Label("To Do List", Label.CENTER);
        toDoTitle.setLocation(blankWidth*2 + statusWidth, 10);
		toDoTitle.setSize(ToDoListWidth, ToDoListHeight);
		this.add(toDoTitle);
		updateToDoList();
		
		//체크박스 배치
		updateCheckBox();
		
		//이걸 추가해야 그림, 체크박스가 한번에 제대로 출력
		repaint();
        
		//하단 버튼 배치
		for(int i = 0; i<buttons.length; i++) setButton(buttons[i],i);
		
		//버튼 이벤트. 자세한 내용은 메소드로 구현했기 때문에 람다를 이용하여 단순화함
        buttons[0].addActionListener(event -> {confirmButtonDialog(buttons[0], 0);});
        buttons[1].addActionListener(event -> {confirmButtonDialog(buttons[1], 1);});
        buttons[2].addActionListener(event -> {confirmButtonDialog(buttons[2], 2);});
        buttons[3].addActionListener(event -> {addButtonDialog();});
        buttons[4].addActionListener(event -> {deleteButtonDialog();});
        buttons[5].addActionListener(event -> {resetButtonDialog();});
        buttons[6].addActionListener(event -> {applyButtonDialog();});
        
        //버튼 정보 업데이트
        updateButtonStatus();
	}
	
	public String getTomaDochiImagePath() {
		int i = poket.getAge();
		if(i<=0) {return tomaDochiImagePath[0];}
		else if(i>=11) {return tomaDochiImagePath[11];}
		else {return tomaDochiImagePath[i];}
	}
	
	public void updateTomaDochi() {
		BufferedImage bufImg;
		try {
			bufImg = ImageIO.read(new File(getTomaDochiImagePath()));
			JLabel tomaDochiImage = new JLabel(new ImageIcon(bufImg), JLabel.CENTER);
	        tomaDochiImage.setLocation(blankWidth, 120);
	        tomaDochiImage.setSize(statusWidth, ToDoListHeight*5);
	        this.add(tomaDochiImage);
		} catch (IOException e) { e.printStackTrace();}
        
	}
	

	public void updateButtonStatus() {	//버튼 비활성화
		
		boolean isEnergyZero = ( poket.getEnergy() <= 0 ? true : false );	//체력이 0인지?
		boolean isPointZero = ( poket.getPoint() == 0 ? true : false );		//포인트가 0인지?
		boolean isEnabled = (isEnergyZero || isPointZero ? false : true );	//둘 중 하나라도 0인지?
		
		Boolean[] nowChecked = getNowChecked(); //체크된 체크박스 수 세기
		int checkedCount = 0;
		for(int i=0;i<toDoString.length;i++) if (nowChecked[i] == true) checkedCount+=1;

		buttons[0].setEnabled(isEnabled); 	//Feed 버튼 비활성화
        buttons[1].setEnabled(isEnabled);	//Exercise 버튼 비활성화
        buttons[2].setEnabled(isEnabled);	//Add 버튼 비활성화
        
        buttons[3].setEnabled(toDoString.length==8?false:true);	//ToDoList 꽉 차면 Add 버튼 비활성화
        buttons[4].setEnabled(toDoString.length==0?false:true);	//ToDoList 없으면 Delete 버튼 비활성화
        buttons[5].setEnabled(toDoString.length==0?false:true);	//ToDoList 없으면 Reset 버튼 비활성화
        
		buttons[6].setEnabled(checkedCount==0?false:true); //체크된 체크박스가 없으면 버튼 비활성화
		
	}

	public void updateToDoList() {	//투두리스트 라벨을 새로 쏴주는 메소드
		toDoString = ToDoList.getToDoList(); //파일에 적힌 값을 배열로 반환하여 넘겨받음
        for(int i = 0; i<toDoString.length; i++){
        	toDo[i].setVisible(true);
			toDo[i].setEnabled(true);
        	toDo[i].setText(toDoString[i]);
        	setToDoList(toDo[i],i);
        }
        updateButtonStatus();
	};
	
	public void updateCheckBox() { //체크박스를 새로 배치
		isCleared = ToDoList.getIsCleared();
        for(int i = 0; i<toDoString.length; i++){
        	checkBoxes[i].setVisible(true);
			checkBoxes[i].setEnabled(true);
			checkBoxes[i].setSelected(isCleared[i]);
            checkBoxes[i].addActionListener(event -> { updateButtonStatus(); });
            setCheckBox(checkBoxes[i], i);
        }
	};
	
	public void updateStatus() {	//캐릭터 정보 업데이트
		statuses[0].setText("Age: " + poket.getAge());
		statuses[1].setText("Power: " + poket.getPower());
		statuses[2].setText("Energy: " + poket.getEnergy());
		statuses[3].setText("Point: " + poket.getPoint());
		updateButtonStatus();
	};
	
	public void addToDoList(String string) { //addButtonDialog에서 받은 텍스트를 넣어주면 ToDoList의 addToDoList로 쏴줘서 저장하게 하고, 화면의 투두리스트 업데이트
		ToDoList.addToDoList(string);
		updateToDoList(); 				
		updateCheckBox();
	};
	
	public void deleteToDoList(int[] indexes) { //deleteButtonDialog에서 받은 번호를 넣어주면 ToDoList의 deleteToDoList로 쏴줘서 삭제하게 하고, 화면의 투두리스트 업데이트
		Boolean[] checkBoxData = getNowChecked();
		List<Boolean> newList = new ArrayList<>(Arrays.asList(checkBoxData));
		for (int i = 0; i<indexes.length; i++) {
			newList.remove(indexes[i]-i);
			newList.add(false);
			ToDoList.deleteToDoList(indexes[i]-i);
		}	
		ToDoList.setCheckBoxes(newList.toArray(new Boolean[0]));
		for(int i = 0; i<toDoLength; i++) {
			toDo[i].setVisible(false);
			toDo[i].setEnabled(false);
			checkBoxes[i].setVisible(false);
			checkBoxes[i].setEnabled(false);
		}
		updateToDoList();
		updateCheckBox();
	};

	
	public void setButton(Button button, int i){ //버튼 배치
		button.setLocation(blankWidth + i * (buttonWidth + 15), 225);
        button.setSize(buttonWidth, buttonHeight);
        this.add(button);
	}
	
	public void setStatus(Label status, int i){ //캐릭터 정보 배치
		status.setLocation(blankWidth, 40 + i * statusHeight);
        status.setSize(statusWidth, statusHeight);
        this.add(status);
	}
	
	public void setCheckBox(JCheckBox checkBox, int i) { //체크박스 배치
		checkBox.setLocation(blankWidth + statusWidth, 40 + i * ToDoListHeight);
		checkBox.setSize(blankWidth, ToDoListHeight);
		checkBox.isSelected();
		this.add(checkBox);
		repaint();
	}
	
	public void setToDoList(Label ToDoList, int i){ //투두리스트 배치
		ToDoList.setLocation(blankWidth*2 + statusWidth, 40 + i * ToDoListHeight);
		ToDoList.setSize(ToDoListWidth, ToDoListHeight);
		this.add(ToDoList);
	}
	
	public void confirmButtonDialog(Button button, int num) { //버튼 누르고 실행하기 전에 확인받는 다이얼로그를 생성(0,1,2번 버튼)
		Dialog d= new Dialog(this);
		d.setTitle("Confirm");
        d.setSize(150,120);
        d.setVisible(true);
        d.setLayout(null);
        d.setModal(true);
       
		String[] information = {"Feed TOmaDOtchi.", "Exercise TOmaDOtchi.", "Sleep TomaDOtchi."}; //다이얼로그에서 출력할 글씨
		Label informationLabel = new Label(information[num], Label.LEFT);
		informationLabel.setLocation(20,50);
		informationLabel.setSize(150, 20);
        d.add(informationLabel);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20, 80);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75, 80);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() { //정석적인 액션 리스너 구현 방법
			@Override
			public void actionPerformed(ActionEvent e) {
				if(num==0){
					poket.eat();
				} else if(num==1){
					poket.exercise();
				} else if(num==2){
					poket.sleep();
				}
				poket.pointStatus(-1);
				updateStatus();
				d.dispose();
			}
        });
		d.add(okButton);
		updateTomaDochi();
	}
	
	public void addButtonDialog() { //투 두 리스트 추가하는 버튼 누르면 나오는 다이얼로그
		Dialog d= new Dialog(this);
		d.setTitle("Add");
        d.setSize(200,120);
        d.setVisible(true);
        d.setLayout(null);
        d.setModal(true);
       
		Label informationLabel = new Label("Please enter a new ----", Label.LEFT);
		informationLabel.setLocation(20,40);
		informationLabel.setSize(150, 20);
        d.add(informationLabel);
        
        TextField textField = new TextField("", 20);
        textField.setLocation(20,60);
        textField.setSize(150, 20);
        d.add(textField);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20,80);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75,80);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateButtonStatus();
				addToDoList(textField.getText());
				d.dispose();
			}
        });
		d.add(okButton);
	}
	
	public void deleteButtonDialog() { //삭제할 투 두 리스트를 고르는 다이얼로그
		
		Dialog d= new Dialog(this);
		d.setTitle("Add");
        d.setSize(300,120 + 20*toDoString.length);
        d.setVisible(true);
        d.setLayout(null);
        
        Label informationLabel = new Label("Please select the item to be deleted.", Label.LEFT);
		informationLabel.setLocation(20,40);
		informationLabel.setSize(260, 20);
        d.add(informationLabel);
        
        JRadioButton[] radio = new JRadioButton[toDoString.length];
        ButtonGroup group = new ButtonGroup();
        
        for(int i=0; i<radio.length; i++){
            radio[i] = new JRadioButton(toDoString[i]);
            radio[i].setLocation(40, 70 + i * ToDoListHeight);
    		radio[i].setSize(ToDoListWidth, ToDoListHeight);
            group.add(radio[i]);
            d.add(radio[i]);
        }
        
        radio[toDoString.length-1].setSelected(true);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20,80 + 20*toDoString.length);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75,80 + 20*toDoString.length);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<toDoString.length;i++) {
					if (radio[i].isSelected() == true) {
						int[] index = {i};
						deleteToDoList(index);
					}
					updateButtonStatus();
				d.dispose();
				}
			}
		});
		d.add(okButton);
	}
	
	public void resetButtonDialog() { //버튼 누르고 실행하기 전에 확인받는 다이얼로그를 생성(0,1,2번 버튼)
		Dialog d= new Dialog(this);
		d.setTitle("Confirm");
        d.setSize(150,120);
        d.setVisible(true);
        d.setLayout(null);
        d.setModal(true);
       
		Label informationLabel = new Label("Delete all of to-do list.", Label.LEFT);
		informationLabel.setLocation(20,50);
		informationLabel.setSize(150, 20);
        d.add(informationLabel);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20, 80);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75, 80);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//모든 투두리스트 내용 삭제
				int[] indexAll = {0, 1, 2, 3, 4, 5, 6, 7};
				deleteToDoList(Arrays.copyOfRange(indexAll, 0, toDoString.length));//모두 삭제
				updateButtonStatus();
				d.dispose();
			}
        });
		d.add(okButton);
	}
	
	public void applyButtonDialog() { //버튼 누르고 실행하기 전에 확인받는 다이얼로그를 생성(0,1,2번 버튼)
		Dialog d= new Dialog(this);
		d.setTitle("Confirm");
        d.setSize(150,120);
        d.setVisible(true);
        d.setLayout(null);
        d.setModal(true);
       
		Label informationLabel = new Label("Apply your changes.", Label.LEFT);
		informationLabel.setLocation(20,50);
		informationLabel.setSize(150, 20);
        d.add(informationLabel);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20, 80);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75, 80);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean[] nowChecked = getNowChecked();
				ToDoList.setCheckBoxes(nowChecked);//체크박스 변경사항 저장
				
				//체크된 체크박스 인덱스 번호 저장
				int[] checkedNumbers = new int[8];
				int j = 0;
				for(int i=0;i<toDoString.length;i++) {	
					if (nowChecked[i] == true) {
						checkedNumbers[j++] = i;
						}
				}
				deleteToDoList(Arrays.copyOfRange(checkedNumbers, 0, j));//체크된 만큼 삭제
				
				updateButtonStatus();
				d.dispose();
				
				int addPoint = Collections.frequency(Arrays.asList(nowChecked),true); //배열>리스트 변환 nowChecked의 true값 몇개인지 카운트
				poket.pointStatus(addPoint);									      //포인트 추가	
				updateStatus();														  //변경된 포인트 값의 반영을 위한 업데이트
			}
        });
		d.add(okButton);
	}
	
	public Boolean[] getNowChecked() {
		Boolean[] nowChecked = new Boolean[8];
		for(int i = 0; i < checkBoxes.length; i++) {
			nowChecked[i] = checkBoxes[i].isSelected();
		}
		return nowChecked;
	}

	public static void main(String args[]) {
	new GUI("start TOmaDOtchi sample 0.0.0");
	}
}
