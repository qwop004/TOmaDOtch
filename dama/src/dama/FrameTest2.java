package dama;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;

public class FrameTest2 extends JFrame {
	
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
	
	Poket poket = new Poket();
	
	Button[] buttons = { //하단 버튼
    		new Button("Feed"),
    		new Button("Exercise"),
    		new Button("Sleep"),
    		new Button("Add"),
    		new Button("Delete"),
    		new Button("Reset"),
    		new Button("Apply")
    		};
	
    Label[] statuses = { //캐릭터 정보 라벨
    		new Label("Age: " + poket.getAge(), Label.LEFT),
    		new Label("Power: " + poket.getPower(), Label.LEFT),
    		new Label("Energy: " + poket.getEnergy(), Label.LEFT),
    		new Label("Point: " + poket.getPoint(), Label.LEFT)
    		};

    String[] toDoString = {"","","","","","","",""}; //파일에서 읽어들인 투두리스트 저장
    
    Label[] toDo = {
    		new Label(""),new Label(""),new Label(""),new Label(""),
    		new Label(""),new Label(""),new Label(""),new Label("")
    };
    
    Boolean[] isCleared = {false, false, false, false, false, false, false, false}; //파일에서 읽어들인 투두리스트 클리어 여부 저장
    
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
    
	public FrameTest2(String str) {
		
		super(str);

		setTitle("TOmaDOtchi 0.0.0");
        setSize(windowWidth,windowHeight);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	this.addWindowListener(new JFrameWindowClosingEventHandler());
        
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        makeMenu();
        makeGUI();	
	}
	
	public void makeMenu() {
		//만들 메뉴 - 파일 (저장, 불러오기, 새로 시작하기) (없어도 될 듯)
	}
	
	public void makeGUI() {

		//다마고치
        Label tamaTitle = new Label("I'm TomaDotchi.", Label.CENTER);
        tamaTitle.setLocation(blankWidth, 10);
		tamaTitle.setSize(statusWidth, ToDoListHeight);
		this.add(tamaTitle);
        for(int i = 0; i<statuses.length; i++) setStatus(statuses[i],i); //캐릭터 스테이터스 표시 라벨 배치
        
        // 캐릭터이미지출력(오류는 없는데 안 돌아감)
        JLabel imageLabel = new JLabel(new ImageIcon("2.png")); //이미지 추가한거로 적용했어요
        imageLabel.setBounds(20, 80, 100, 100);
        this.add(imageLabel);
        
        //투두리스트
        Label toDoTitle = new Label("To Do List", Label.CENTER);
        toDoTitle.setLocation(blankWidth*2 + statusWidth, 10);
		toDoTitle.setSize(ToDoListWidth, ToDoListHeight);
		this.add(toDoTitle);
        
		updateToDoList();	
		updateCheckBox();
        
		//버튼
		for(int i = 0; i<buttons.length; i++) setButton(buttons[i],i); //하단 버튼 배치
		
        buttons[0].addActionListener(event -> {confirmButtonDialog(buttons[0], 0);});
        buttons[1].addActionListener(event -> {confirmButtonDialog(buttons[1], 1);});
        buttons[2].addActionListener(event -> {confirmButtonDialog(buttons[2], 2);});
        buttons[3].addActionListener(event -> {addButtonDialog();});
        buttons[4].addActionListener(event -> {deleteButtonDialog();});
        buttons[5].addActionListener(event -> {resetButtonDialog();});
        buttons[6].addActionListener(event -> {applyButtonDialog();});
        
        updateButtonStatus();
		buttons[5].setEnabled(false);
		buttons[6].setEnabled(false);
	}

	public void updateButtonStatus() {								//버튼 비활성화 시킬거 늘어나서 그냥 하나로..
        buttons[0].setEnabled(poket.getPoint() == 0?false:true);
		buttons[0].setEnabled(poket.getEnergy() <= 0?false:true); //에너지 0일떄 버튼 비활성화(죽는거) 추가
        buttons[1].setEnabled(poket.getPoint() == 0?false:true);
		buttons[1].setEnabled(poket.getEnergy() <= 0?false:true);
        buttons[2].setEnabled(poket.getPoint() == 0?false:true);
		buttons[2].setEnabled(poket.getEnergy() <= 0?false:true);
        buttons[3].setEnabled(toDoString.length==8?false:true); 	//꽉 차면 add버튼 비홀성화
        buttons[4].setEnabled(toDoString.length==0?false:true);
	}


	public void updateToDoList() { //투두리스트 라벨을 새로 쏴주는 메소드
		toDoString = ToDoList.getToDoList(); //파일을 배열로 반환하여 넘겨받음
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
            checkBoxes[i].addActionListener(event -> {
            	buttons[5].setEnabled(true);
            	buttons[6].setEnabled(true);
            	});
            setCheckBox(checkBoxes[i], i);
        }
	};
	
	public void updateStatus() {
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
	
	public void deleteToDoList(int index) { //deleteButtonDialog에서 받은 번호를 넣어주면 ToDoList의 deleteToDoList로 쏴줘서 삭제하게 하고, 화면의 투두리스트 업데이트
		ToDoList.deleteToDoList(index);
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
	
	public void setCheckBox(JCheckBox checkBox, int i) {
		checkBox.setLocation(blankWidth + statusWidth, 40 + i * ToDoListHeight);
		checkBox.setSize(blankWidth, ToDoListHeight);
		checkBox.isSelected();
		this.add(checkBox);
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
       
		String[] information = {"Feed TOmaDOtchi.", "Exercise TOmaDOtchi.", "Sleep TomaDOtchi."};
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
        okButton.addActionListener(new ActionListener() {
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
				buttons[5].setEnabled(true);
				buttons[6].setEnabled(true);
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
						deleteToDoList(i);
					}
				buttons[5].setEnabled(true);
				buttons[6].setEnabled(true);
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
       
		Label informationLabel = new Label("Reset your changes.", Label.LEFT);
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
				updateCheckBox();
				buttons[5].setEnabled(false);
				buttons[6].setEnabled(false);
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
				//Boolean[] pointArray = pointAward(nowChecked);
				ToDoList.setCheckBoxes(nowChecked);//체크박스 변경사항 저장
				buttons[5].setEnabled(false);
				buttons[6].setEnabled(false);
				d.dispose();
				
				int addPoint = Collections.frequency(Arrays.asList(nowChecked),true); //배열>리스트 변환 nowChecked의 true값 몇개인지 카운트
				poket.pointStatus(addPoint);									  //포인트 추가	
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
	
//	public Boolean[] pointAward(Boolean[] nowChecked) {
//		Boolean[] isPoint = new Boolean[8];
//		for(int i = 0; i < checkBoxes.length; i++) {
//			isPoint[i] = (((isCleared[i] == false) && (nowChecked[i] == true)) ? true : false);
//		}
//		return isPoint;
//	}

	public static void main(String args[]) {
	new FrameTest2("start TOmaDOtchi sample 0.0.0");
	}
}
