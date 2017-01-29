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

	// �ؽ�Ʈ �� ��� â
	TextField[][] textField = new TextField[9][9];				 
	boolean tok;
	boolean token= true;
	
	@Override
	public void start(Stage primaryStage) {
		// ������ ����� ��ü(SingleToen�� ���)
		Sudoku sdoku = Sudoku.getInstance();						
		// GridPane Ÿ������ ��ü ���̾ƿ��� ����
		GridPane board = new GridPane();						
		// �޴� �ٸ� �����.
		MenuBar menuBar = new MenuBar();						 
		// Scene�� ���� �� ���� �� ������ Vbox�� ����
		Scene scene = new Scene(new VBox(), 400, 400);			
		// Ÿ��Ʋ�� �����Ѵ�.
		primaryStage.setTitle("SUDOKU made by VICDev");			

		// ������ ����
		sdoku.inputNum();	
		// ���θ� ������ vbox�� ����
		VBox vbox = new VBox(0);				
		// vbox���θ� �߾����� �Ѵ�.
		vbox.setAlignment(Pos.CENTER);	 		
		// file �޴��� ����
		Menu menuFile = new Menu("File");		
		// level �޴��� ����
		Menu menuLevel = new Menu("Level");		

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// ���� �� ���� �Է� �� �ؽ�Ʈ �ʵ带 ����
				textField[i][j] = new TextField();					
				// �ؽ�Ʈ ���� ũ�⸦ 48px�� ����
				textField[i][j].setPrefWidth(48);					
				// �ؽ�Ʈ ���� ũ�⸦ 48px�� ����
				textField[i][j].setPrefHeight(48);					
				// ������ �����Ѵ�. true : ���ڸ� ����, false : �� ��������
				if (sdoku.getsArr(i, j) != 0) {						
					// ����� �ƴϸ� ���ڸ� �ִ´�.
					textField[i][j].setText(sdoku.getsArr(i, j) + "");
					// ������ �Ұ����ϰ� �Ѵ� ( �����̱� ������)
					textField[i][j].setEditable(false);				
					// ID ���� (���� �ٲ۴�.)
					textField[i][j].setId("Qes");					
					// ����̸�
				} else {											
					// �ؽ�Ʈ â�� ����
					textField[i][j].setText("");					
					// ID ����(���� ����)
					textField[i][j].setId("Ans");					
					// ������ ����
					textField[i][j].setEditable(true);				
				}	
			}
		}

		// ���� �ٷ�� �ݺ���
		for (int i = 0; i< 9; i++) {			
			// ���� �ٷ�� �ݺ���
			for (int j = 0; j< 9; j++) {			
				// ���̾ƿ��� �ٷ� �г�
				// Stackpane�� ����ϴ� ������
				// �߾����� ���ĵǱ� ������
				StackPane cell = new StackPane();			
				
				// css������ ����
				cell.getStyleClass().add("cell");				
				// ���� �� �߱�
				if(i == 2 || i == 5){					
					cell.setId("bottom");					
				}
				// ���� �� �߱�
				if(j == 2 || j == 5){						
					cell.setId("right");
				}
				// �ߺ��Ǵ� �κп� ������ �ٱ߱�
				if((j == 2||j==5) && (i == 2|| i == 5)){		
					cell.setId("rc22");
				}
				// stackpane�� �ؽ�Ʈ�� �߰��Ѵ�.
				cell.getChildren().add(textField[i][j]);		
				// stackpane�� �߰�
				board.add(cell, j, i);							
			}
		}

		// file�� �� �޴��� ����
		// new game ���ο� ������ ����Ѵ�.
		MenuItem add = new MenuItem("New Game");			 	
		// �̺�Ʈ(�޴� ����)
		add.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		// ���� ���� ���� ������ ó������ �ٽ� �����Ѵ�.
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

		
		// Game Level�� ���� �Ѵ�. (������ ������ Level ����)						
		// ���� Level
		MenuItem low = new MenuItem("����");								
		low.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				// ����� ������ 40���� ����
				sdk.setNum(40);									
				// ������ ���� �����.
				sdk.inputNum();									
				newGame();								
			}
		});
		MenuItem middle = new MenuItem("�߰�");
		middle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(60);
				sdk.inputNum();
				newGame();
			}
		});
		MenuItem high = new MenuItem("����");
		high.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(70);
				sdk.inputNum();
				newGame();
			}
		});

		// ���� ����ϴ� �޴�
		MenuItem anser = new MenuItem("�� ���");
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

		// Ű�̺�Ʈ�� ã�� �ݺ���
		for(int inx=0;inx < textField.length; inx++){
			for(int jnx = 0; jnx< textField[inx].length; jnx++ ){
				textField[inx][jnx].setOnKeyReleased(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(token){
							// �� �˻� �޼���
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

	// ���ο� ������ ����� �޼ҵ�
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
					//�Է¹��� ���ڰ� �������� Ȯ��
					int tmp = Integer.parseInt(textField[i][j].getText());		
					// �����̸� 1~9 ���� ���� Ȯ��
					if(!(tmp >= 1 && tmp <= 9)){								
						// ���� ���̸� ���
						textField[i][j].setText("");							
					}
				}catch(NumberFormatException nfe) {
					// ���ڰ� �ƴϸ� ���
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
			str.setText("�����մϴ�. \n������ �Ϸ��ϼ̽��ϴ�.");
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
