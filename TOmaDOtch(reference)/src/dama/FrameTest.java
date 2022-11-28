package Gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FrameTest extends JFrame {
	
	public static final int windowWidth = 500;
	public static final int windowHeight = 300;
	
	public static final int buttonWidth = 50;
	public static final int buttonHeight = 20;
	
	public static final int statusWidth = 200;
	public static final int statusHeight = 20;
	
	public static final int ToDoListWidth = 200;
	public static final int ToDoListHeight = 20;
	
	public static final int blankWidth = 20;
	
	Button buttons[] = { //하단 버튼
    		new Button("Feed"),
    		new Button("Exercise"),
    		new Button("Button 3"),
    		new Button("Add"),
    		new Button("Delete"),
    		new Button("Reset"),
    		new Button("Apply")
    		};
	
    Label statuses[] = { //캐릭터 정보 라벨
    		new Label("Status 1", Label.LEFT),
    		new Label("Status 2", Label.LEFT),
    		new Label("Status 3", Label.LEFT)
    		};
	
	public FrameTest(String str) {
		
		super(str);

		setTitle("TOmaDOtchi 0.0.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth,windowHeight);
        setVisible(true);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        makeMenu();
        
        makeGUI();	
	}
	
	public void makeMenu() {
		//만들 메뉴 - 파일 (저장, 불러오기, 새로 시작하기)
	}
	
	public void makeGUI() {

        for(int i = 0; i<buttons.length; i++) setButton(buttons[i],i); //하단 버튼 배치
        for(int i = 0; i<statuses.length; i++) setStatus(statuses[i],i); //캐릭터 스테이터스 표시 라벨 배치
        
        /* 캐릭터이미지출력(이거 인터넷에 있는 코드 그대로 긁어다 붙인 거라 수정 필요)
        JLabel imageLabel = new JLabel();
        ImageIcon characterImage = new ImageIcon(FrameTest.class.getResource("img/Tomadotchi.png"));
        imageLabel.setIcon(characterImage);
        imageLabel.setBounds(30, 30, 122, 130);
        imageLabel.setHorizontalAlignment(Label.CENTER);
        contentPane.add(imageLabel);
        */
        
        Label toDoTitle = new Label("To Do", Label.CENTER);
        toDoTitle.setLocation(blankWidth, ToDoListHeight);
		toDoTitle.setSize(ToDoListWidth, ToDoListHeight);
		this.add(toDoTitle);
        
		updateToDoList();
		/*
		 * 투 두 리스트 띄워주기.
		 * 투 두 리스트는 파일에서 긁어와서 여기로 쏴주면 그걸 배열 형태로 갖고 있다가 띄운다.('배열 형태로 갖고 있다가' 부분이 아직 미구현...)
		 * 나중에 이 옆에 네모난 체크 버튼...?같은 걸 추가해서 완료 여부를 표시하고, Apply 버튼을 누르면 적용되어 토마도치에게 적용 여부를 쏴주는 걸로.
		 * Reset 버튼 누르면 체크 버튼 등등의 반영사항 초기화하고 파일에서 새로 긁어와서 띄움
		 */
        
        buttons[0].addActionListener(event -> {confirmButtonDialog(buttons[0], 0);});
        buttons[1].addActionListener(event -> {confirmButtonDialog(buttons[1], 1);});
        buttons[2].addActionListener(event -> {confirmButtonDialog(buttons[2], 2);});
        buttons[3].addActionListener(event -> {addButtonDialog();});
        buttons[4].addActionListener(event -> {deleteButtonDialog();});
        buttons[5].addActionListener(event -> {});
        buttons[6].addActionListener(event -> {});
        
		buttons[5].setEnabled(false);
		buttons[6].setEnabled(false);
	}

	public void updateToDoList() { //투두리스트 라벨을 새로 쏴주는 메소드
		//String toDoString[] = ToDoList.getToDoList(); //getToDoList 하면 파일에서 잘 열어서 string 배열로 넘겨주세요
		String toDoString[] = {"Test1", "Test2", "Test3"}; //위에 코드 준비 안 돼서 임시로 만든 것
		Label toDo[] = new Label[toDoString.length];
        for(int i = 0; i<toDoString.length; i++){
        	toDo[i] = new Label(toDoString[i]);
        	setToDoList(toDo[i],i);
        }
	};
	
	public void addToDoList(String string) { //addButtonDialog에서 받은 텍스트를 넣어주면 ToDoList의 addToDoList로 쏴줘서 저장하게 하고, 화면의 투두리스트 업데이트
		//ToDoList.addToDoList(string);
		updateToDoList();
	};
	
	public void deleteToDoList(int num) {//deleteButtonDialog에서 받은 번호를 넣어주면 ToDoList의 deleteToDoList로 쏴줘서 삭제하게 하고, 화면의 투두리스트 업데이트
		//ToDoList.deleteTodoList(num);
		updateToDoList();
	};
	
	
	public void setButton(Button button, int i){ //버튼 배치
		button.setLocation(blankWidth + i * (buttonWidth + 15), 225);
        button.setSize(buttonWidth, buttonHeight);
        this.add(button);
	}
	
	public void setStatus(Label status, int i){ //캐릭터 정보 배치
		status.setLocation(blankWidth, blankWidth + i * statusHeight);
        status.setSize(statusWidth, statusHeight);
        this.add(status);
	}
	
	public void setToDoList(Label ToDoList, int i){ //투두리스트 배치
		ToDoList.setLocation(blankWidth + statusWidth, 40 + i * ToDoListHeight);
		ToDoList.setSize(ToDoListWidth, ToDoListHeight);
		this.add(ToDoList);
	}
	
	
	public void confirmButtonDialog(Button button, int num) { //버튼 누르고 실행하기 전에 확인받는 다이얼로그를 생성(0,1,2번 버튼)
		Dialog d= new Dialog(this);
		d.setTitle("Confirm");
        d.setSize(150,120);
        d.setVisible(true);
        d.setLayout(null);
       
		String information[] = {"Feed TOmaDOtchi.", "Exercise TOmaDOtchi.", "Button3."};
		Label informationLabel = new Label(information[num], Label.LEFT);
		informationLabel.setLocation(20,50);
		informationLabel.setSize(150, 20);
        d.add(informationLabel);
        
        addCancelOK1(d, 80);
	}
	
	public void addCancelOK1(Dialog d, int height) { //처음엔 분리한 목적이 있었는데 이제 그냥 위에 있는 메소드랑 합칠 가능성이 큽니다
		
		d.setModal(true);
        
		Button cancelButton = new Button("Cancel");
        cancelButton.setLocation(20,height);
		cancelButton.setSize(buttonWidth,buttonHeight);
		cancelButton.addActionListener(event -> {d.dispose();});
        d.add(cancelButton);
        
        Button okButton = new Button("OK");
        okButton.setLocation(75,height);
        okButton.setSize(buttonWidth,buttonHeight);
        okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//다이얼로그 삭제하기 전에 실행할 메소드 갖다 놓아야 함... 하지만 어떻게?
				//이거 올린 후에 프로그램 구조가 확 바뀔 수 있습니다
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
		//라디오버튼을 넣긴 했는데 값 읽어들이는 법을 몰라서... 대폭 수정할 가능성 있음
        //String toDoString[] = ToDoList.getToDoList();
        String toDoString[] = {"Test1", "Test2", "Test3"}; //위에 코드 준비 안 돼서 임시로 만든 것
		
		Dialog d= new Dialog(this);
		d.setTitle("Add");
        d.setSize(300,120 + 20*toDoString.length);
        d.setVisible(true);
        d.setLayout(null);
        
        
        Label informationLabel = new Label("Please select the item to be deleted.", Label.LEFT);
		informationLabel.setLocation(20,40);
		informationLabel.setSize(260, 20);
        d.add(informationLabel);
        
        
        JRadioButton radio[] = new JRadioButton[toDoString.length];
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
				buttons[5].setEnabled(true);
				buttons[6].setEnabled(true);
				deleteToDoList(0);
				d.dispose();
			}
        });
		d.add(okButton);
	}

	public static void main(String args[]) {
	new FrameTest("start TOmaDOtchi sample 0.0.0");
	}
}
