package SudokuV2;

import javafx.application.Application;
//import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
//import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
//import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Main extends Application {

	TextField[][] textField = new TextField[9][9];				 // 텍스트 입 출력 창
	boolean tok;
	boolean token= true;
	
	@Override
	public void start(Stage primaryStage) {
		Sudoku sdoku = Sudoku.getInstance();						// 문제를 만드는 객체
		GridPane board = new GridPane();							// grid 타입으로 전체 레이아웃을 구성	
		MenuBar menuBar = new MenuBar();						// 메뉴 바를 만든다. 

		Scene scene = new Scene(new VBox(), 400, 400);			// Scene을 만들 때 내부 를 수성할 Vbox를 생성
		
		primaryStage.setTitle("SUDOKU made by VICDev");			// 타이틀을 생성한다.

		
		sdoku.inputNum();	// 문제를 만듬

		VBox vbox = new VBox(0);				// 내부를 구성할 vbox를 생성
		vbox.setAlignment(Pos.CENTER);	 		// vbox내부를 중앙정렬 한다.

		Menu menuFile = new Menu("File");		// file 메뉴를 생성
		Menu menuLevel = new Menu("Level");		// level 메뉴를 생성

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[j][i] = new TextField();					// 문제 및 답을 입력 할 텍스트 필드를 생성
				textField[j][i].setPrefWidth(48);					// 텍스트 가로 크기를 48px로 설정
				textField[j][i].setPrefHeight(48);					// 텍스트 세로 크기를 48px로 설정
				if (sdoku.getsArr(i, j) != 0) {						// 문제를 생성한다. true : 숫자를 넣음, false : 빈 공간으로
					textField[j][i].setText(sdoku.getsArr(i, j) + "");	// 빈곳이 아니면 숫자를 넣는다.
					textField[j][i].setEditable(false);				// 편집이 불가능하게 한다 ( 문제이기 떄문에)
					textField[j][i].setId("Qes");					// ID 설정 (색을 바꾼다.)
				} else {											// 빈곳이면
					textField[j][i].setText("");					// 값을 비우고
					textField[j][i].setId("Ans");					// ID 설정
					textField[j][i].setEditable(true);				// 편집을 가능하게 한다
				}	
			}
		}

//		Background bg = new Background(new BackgroundFill(Color.rgb(234, 234, 234),new CornerRadii(0) , new Insets(0.f)));
		for (int col = 0; col< 9; col++) {			// 열을 다루는 반복문
			for (int row = 0; row< 9; row++) {			// 행을 다루는 반복문
				StackPane cell = new StackPane();			// 레이아웃을 다룰 패널
				
				cell.setPadding(new Insets(5));					// Stackpane을 사용하는 이유는 
				cell.getStyleClass().add("cell");				// css적용을 위한 
				if(col == 2 || col == 5){						// 세로 줄 긋기
					cell.setId("right");					// 중앙으로 정렬되기 떄문에
				}
				if(row == 2 || row == 5){						// 가로 줄 긋기
					cell.setId("bottom");
				}
				if((row == 2||row==5) && (col == 2|| col == 5)){		// 중복되는 부분에 마무리 하기
					cell.setId("rc22");
				}
				cell.getChildren().add(textField[col][row]);			// stackpane에 텍스트를 추가한다.
				board.add(cell, col, row);							// stackpane을 추가
			}
		}

																// file에 들어갈 메뉴를 설정
		MenuItem add = new MenuItem("New Game");			 	// new game 새로운 게임을 출력한다.
		add.setOnAction(new EventHandler<ActionEvent>() {		// 이벤트 

			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		MenuItem Reset = new MenuItem("Reset");					// 지금 진행 중인 게임을 처음부터 다시 시작한다.
		Reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				token=true;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (textField[j][i].getId().equals("Dif")|| textField[j][i].getId().equals("Che") ) {
							textField[j][i].setId("Ans");
							textField[j][i].setText("");
						}
					}
				}
			}
		});

																// Game Level을 설정 한다. (공란의 개수로 Level 설정)
		MenuItem low = new MenuItem("쉬움");								// 쉬운 Level
		low.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(45);									// 빈곳의 개수를 40으로 설정
				sdk.inputNum();									// 문제를 새로 만든다.
				newGame();								
			}
		});
		MenuItem middle = new MenuItem("중간");
		middle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(60);
				sdk.inputNum();
				newGame();
			}
		});
		MenuItem high = new MenuItem("높음");
		high.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(70);
				sdk.inputNum();
				newGame();
			}
		});

		MenuItem Anser = new MenuItem("답 출력");
		Anser.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				token=false;;
				Sudoku sdoku = Sudoku.getInstance();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if (textField[j][i].getId().equals("Dif") || textField[j][i].getId().equals("Ans")) {
							textField[j][i].setId("Che");
							textField[j][i].setText("" + sdoku.getArr(i, j));
						}
					}
				}
			}
		});


		menuFile.getItems().addAll(add,Reset,Anser);
		menuLevel.getItems().addAll(low,middle,high);
		menuBar.getMenus().addAll(menuFile,menuLevel);



		// 키이벤트를 찾는 반복문
		for(int inx=0;inx < textField.length; inx++){
			for(int jnx = 0; jnx< textField[inx].length; jnx++ ){
				textField[jnx][inx].setOnKeyReleased(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(token){
							check();
						}// 답 검사 메서드
					}
				});

			}
		}

		((VBox)scene.getRoot()).getChildren().addAll(menuBar, vbox,board);
		scene.getStylesheets().add(getClass().getResource("sudoku.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void newGame() {
		Sudoku sdoku = Sudoku.getInstance();
		sdoku.inputNum();
		token=true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sdoku.getsArr(i, j) != 0) {
					textField[j][i].setText("" + sdoku.getsArr(i, j));
					textField[j][i].setEditable(false);
					textField[j][i].setId("Qes");
				} 
				else {
					textField[j][i].setText("");
					textField[j][i].setEditable(true);
					textField[j][i].setId("Ans");
				}
			}
		}
	}

	private void check(){
		Sudoku sdoku = Sudoku.getInstance();
		tok = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if(textField[j][i].getText().equals("")|| textField[j][i].getId().equals("Dif")){
					tok = false;
				}
				try{
					int tmp = Integer.parseInt(textField[j][i].getText());			//입력받은 문자가 숫자인지 확인
					if(!(tmp >= 1 && tmp <= 9)){								// 숫자이면 1~9 범위 인지 확인						
						textField[j][i].setText("");							// 범위 밖이면 비움
					}
				}catch(NumberFormatException nfe) {
					textField[j][i].setText("");								// 숫자가 아니면 비움
				}
				if (textField[j][i].getId().equals("Dif") || textField[j][i].getId().equals("Che")) {
					textField[j][i].setId("Ans");
				}
				if (textField[j][i].getId().equals("Ans")) {

					if (!textField[j][i].getText().equals(Integer.toString(sdoku.getArr(i, j)))) {
						if (!textField[j][i].getText().equals("")){
							textField[j][i].setId("Dif");
							tok = false;
						}
					} 
					else {
						textField[j][i].setId("Che");
					}
				}
			}
		}
		if(tok && token){
//			Stage popup = new Stage();
//			GridPane grid = new GridPane();
//			Label label = new Label();
//			label.setText("test Success");
//			grid.getChildren().add(label);
//			Scene sen = new Scene(grid,200,200);
//			popup.setScene(sen);
//			popup.showAndWait();
			
			
			
//			Alert alert1 = new Alert(AlertType.INFORMATION);
//			alert1.setTitle("완료");
//			alert1.setHeaderText(null);
//			alert1.setContentText("축하합니다. \n게임을 완료하셨습니다.");
//			token = false;
//			alert1.showAndWait();
			
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
