package com.vic.sudokuv2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SudokuMain extends Application {

	// 텍스트 입 출력 창
	TextField[][] textField = new TextField[9][9];				 
	boolean tok;
	boolean token= true;
	
	@Override
	public void start(Stage primaryStage) {
		// 문제를 만드는 객체(SingleToen을 사용)
		Sudoku sdoku = Sudoku.getInstance();						
		// GridPane 타입으로 전체 레이아웃을 구성
		GridPane board = new GridPane();						
		// 메뉴 바를 만든다.
		MenuBar menuBar = new MenuBar();						 
		// Scene을 만들 때 내부 를 구성할 Vbox를 생성
		Scene scene = new Scene(new VBox(), 400, 400);			
		// 타이틀을 생성한다.
		primaryStage.setTitle("SUDOKU made by VICDev");			

		// 문제를 만듬
		sdoku.inputNum();	
		// 내부를 구성할 vbox를 생성
		VBox vbox = new VBox(0);				
		// vbox내부를 중앙정렬 한다.
		vbox.setAlignment(Pos.CENTER);	 		
		// file 메뉴를 생성
		Menu menuFile = new Menu("File");		
		// level 메뉴를 생성
		Menu menuLevel = new Menu("Level");		

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// 문제 및 답을 입력 할 텍스트 필드를 생성
				textField[i][j] = new TextField();					
				// 텍스트 가로 크기를 48px로 설정
				textField[i][j].setPrefWidth(48);					
				// 텍스트 세로 크기를 48px로 설정
				textField[i][j].setPrefHeight(48);					
				// 문제를 생성한다. true : 숫자를 넣음, false : 빈 공간으로
				if (sdoku.getsArr(i, j) != 0) {						
					// 빈곳이 아니면 숫자를 넣는다.
					textField[i][j].setText(sdoku.getsArr(i, j) + "");
					// 편집이 불가능하게 한다 ( 문제이기 떄문에)
					textField[i][j].setEditable(false);				
					// ID 설정 (색을 바꾼다.)
					textField[i][j].setId("Qes");					
					// 빈곳이면
				} else {											
					// 텍스트 창을 비우고
					textField[i][j].setText("");					
					// ID 설정(색을 변경)
					textField[i][j].setId("Ans");					
					// 편집이 가능
					textField[i][j].setEditable(true);				
				}	
			}
		}

		// 열을 다루는 반복문
		for (int i = 0; i< 9; i++) {			
			// 행을 다루는 반복문
			for (int j = 0; j< 9; j++) {			
				// 레이아웃을 다룰 패널
				// Stackpane을 사용하는 이유는
				// 중앙으로 정렬되기 떄문에
				StackPane cell = new StackPane();			
				
				// css적용을 위한
				cell.getStyleClass().add("cell");				
				// 세로 줄 긋기
				if(i == 2 || i == 5){					
					cell.setId("bottom");					
				}
				// 가로 줄 긋기
				if(j == 2 || j == 5){						
					cell.setId("right");
				}
				// 중복되는 부분에 마무리 줄긋기
				if((j == 2||j==5) && (i == 2|| i == 5)){		
					cell.setId("rc22");
				}
				// stackpane에 텍스트를 추가한다.
				cell.getChildren().add(textField[i][j]);		
				// stackpane을 추가
				board.add(cell, j, i);							
			}
		}

		// file에 들어갈 메뉴를 설정
		// new game 새로운 게임을 출력한다.
		MenuItem add = new MenuItem("New Game");			 	
		// 이벤트(메뉴 선택)
		add.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		// 지금 진행 중인 게임을 처음부터 다시 시작한다.
		MenuItem reset = new MenuItem("Reset");					
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				token=true;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if ("Dif".equals(textField[i][j].getId())|| "Che".equals(textField[i][j].getId()) ) {
							textField[i][j].setId("Ans");
							textField[i][j].setText("");
						}
					}
				}
			}
		});

		
		// Game Level을 설정 한다. (공란의 개수로 Level 설정)						
		// 쉬운 Level
		MenuItem low = new MenuItem("쉬움");								
		low.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				// 빈곳의 개수를 40으로 설정
				sdk.setNum(40);									
				// 문제를 새로 만든다.
				sdk.inputNum();									
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

		// 답을 출력하는 메뉴
		MenuItem anser = new MenuItem("답 출력");
		anser.setOnAction(new EventHandler<ActionEvent>() {		
			@Override
			public void handle(ActionEvent arg0) {
				token=false;
				Sudoku sdoku = Sudoku.getInstance();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						if ("Dif".equals(textField[i][j].getId()) || "Ans".equals(textField[i][j].getId())) {
							textField[i][j].setId("Che");
							textField[i][j].setText("" + sdoku.getArr(i, j));
						}
					}
				}
			}
		});


		menuFile.getItems().addAll(add,reset,anser);
		menuLevel.getItems().addAll(low,middle,high);
		menuBar.getMenus().addAll(menuFile,menuLevel);

		// 키이벤트를 찾는 반복문
		for(int inx=0;inx < textField.length; inx++){
			for(int jnx = 0; jnx< textField[inx].length; jnx++ ){
				textField[inx][jnx].setOnKeyReleased(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(token){
							// 답 검사 메서드
							checkGame();
						}
					}
				});

			}
		}

		((VBox)scene.getRoot()).getChildren().addAll(menuBar, vbox,board);
		scene.getStylesheets().add(getClass().getResource("sudoku.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// 새로운 게임을 만드는 메소드
	private void newGame() {
		Sudoku sdoku = Sudoku.getInstance();
		sdoku.inputNum();
		token=true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sdoku.getsArr(i, j) != 0) {
					textField[i][j].setText("" + sdoku.getsArr(i, j));
					textField[i][j].setEditable(false);
					textField[i][j].setId("Qes");
				} else {
					textField[i][j].setText("");
					textField[i][j].setEditable(true);
					textField[i][j].setId("Ans");
				}
			}
		}
	}

	private void checkGame(){
		Sudoku sdoku = Sudoku.getInstance();
		tok = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if("".equals(textField[i][j].getText())|| "Dif".equals(textField[j][i].getId())){
					tok = false;
				}
				try{
					//입력받은 문자가 숫자인지 확인
					int tmp = Integer.parseInt(textField[i][j].getText());		
					// 숫자이면 1~9 범위 인지 확인
					if(!(tmp >= 1 && tmp <= 9)){								
						// 범위 밖이면 비움
						textField[i][j].setText("");							
					}
				}catch(NumberFormatException nfe) {
					// 숫자가 아니면 비움
					textField[i][j].setText("");								
				}
				if ("Dif".equals(textField[i][j].getId()) || "Che".equals(textField[i][j].getId())) {
					textField[i][j].setId("Ans");
				}
				if ("Ans".equals(textField[i][j].getId())) {
					String sudoku_num =  Integer.toString(sdoku.getArr(i, j));
					if (!textField[i][j].getText().equals(sudoku_num)) {
						if (!"".equals(textField[i][j].getText())){
							textField[i][j].setId("Dif");
							tok = false;
						}
					} else {
						textField[i][j].setId("Che");
					}
				}
			}
		}
		if(tok && token){
			Stage pop = new Stage();
			GridPane rootAlert = new GridPane();
			Button ok = new Button();
			ok.setText("OK");
			final Label str = new Label();
			str.setText("축하합니다. \n게임을 완료하셨습니다.");
			ok.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Stage sScene = (Stage) str.getScene().getWindow();
					sScene.close();
				}
			});
			ok.setId("ok");
			rootAlert.add(str, 2, 0,1,2);
			rootAlert.add(ok, 2, 4, 2, 1);
			rootAlert.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(rootAlert,200,100);
			
			pop.setScene(scene);
			
			pop.show();
			token = false;
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
