package dama;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    String toDoString[] = ToDoList.getToDoList();
	
	public FrameTest2(String str) {
		
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

		//다마고치
        Label tamaTitle = new Label("I'm Tomadotchi.", Label.CENTER);
        tamaTitle.setLocation(blankWidth, 10);
		tamaTitle.setSize(statusWidth, ToDoListHeight);
		this.add(tamaTitle);
        for(int i = 0; i<statuses.length; i++) setStatus(statuses[i],i); //캐릭터 스테이터스 표시 라벨 배치
        
        /* 캐릭터이미지출력(이거 인터넷에 있는 코드 그대로 긁어다 붙인 거라 수정 필요)
        JLabel imageLabel = new JLabel();
        ImageIcon characterImage = new ImageIcon(FrameTest.class.getResource("img/Tomadotchi.png"));
        imageLabel.setIcon(characterImage);
        imageLabel.setBounds(30, 30, 122, 130);
        imageLabel.setHorizontalAlignment(Label.CENTER);
        contentPane.add(imageLabel);
        */
        
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
        
		buttons[5].setEnabled(false);
		buttons[6].setEnabled(false);
	}

	public void updateToDoList() { //투두리스트 라벨을 새로 쏴주는 메소드
		toDoString = ToDoList.getToDoList(); //getToDoList 하면 파일에서 잘 열어서 string 배열로 넘겨주세요
		Label toDo[] = new Label[toDoString.length];
        for(int i = 0; i<toDoString.length; i++){
        	toDo[i] = new Label(toDoString[i]);
        	setToDoList(toDo[i],i);
        }
	};
	
	public void updateCheckBox() { //체크박스를 새로 배치
	    JCheckBox checkBoxes[] = new JCheckBox[toDoString.length];
        for(int i = 0; i<toDoString.length; i++){
            checkBoxes[i] = new JCheckBox("");
            setCheckBox(checkBoxes[i],i);
            checkBoxes[i].addActionListener(event -> {
            	buttons[5].setEnabled(true);
            	buttons[6].setEnabled(true);
            	});
        }
	};
	
	public void addToDoList(String string) { //addButtonDialog에서 받은 텍스트를 넣어주면 ToDoList의 addToDoList로 쏴줘서 저장하게 하고, 화면의 투두리스트 업데이트
		ToDoList.addToDoList(string);
		updateToDoList();
		updateCheckBox();
	};
	
	public void deleteToDoList(int num) {//deleteButtonDialog에서 받은 번호를 넣어주면 ToDoList의 deleteToDoList로 쏴줘서 삭제하게 하고, 화면의 투두리스트 업데이트
		ToDoList.deleteToDoList(num);
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
       
		String information[] = {"Feed TOmaDOtchi.", "Exercise TOmaDOtchi.", "Button3."};
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
					//밥먹이기
				} else if(num==1){
					//운동시키기
				} else if(num==2){
					//버튼3
				}
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
				//리셋
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
				//저장
				buttons[5].setEnabled(false);
				buttons[6].setEnabled(false);
				d.dispose();
			}
        });
		d.add(okButton);
	}

	public static void main(String args[]) {
	new FrameTest2("start TOmaDOtchi sample 0.0.0");
	}
}
