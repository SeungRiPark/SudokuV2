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

	TextField[][] textField = new TextField[9][9];				 // �ؽ�Ʈ �� ��� â
	boolean tok;
	boolean token= true;
	
	@Override
	public void start(Stage primaryStage) {
		Sudoku sdoku = Sudoku.getInstance();						// ������ ����� ��ü
		GridPane board = new GridPane();							// grid Ÿ������ ��ü ���̾ƿ��� ����	
		MenuBar menuBar = new MenuBar();						// �޴� �ٸ� �����. 

		Scene scene = new Scene(new VBox(), 400, 400);			// Scene�� ���� �� ���� �� ������ Vbox�� ����
		
		primaryStage.setTitle("SUDOKU made by VICDev");			// Ÿ��Ʋ�� �����Ѵ�.

		
		sdoku.inputNum();	// ������ ����

		VBox vbox = new VBox(0);				// ���θ� ������ vbox�� ����
		vbox.setAlignment(Pos.CENTER);	 		// vbox���θ� �߾����� �Ѵ�.

		Menu menuFile = new Menu("File");		// file �޴��� ����
		Menu menuLevel = new Menu("Level");		// level �޴��� ����

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				textField[j][i] = new TextField();					// ���� �� ���� �Է� �� �ؽ�Ʈ �ʵ带 ����
				textField[j][i].setPrefWidth(48);					// �ؽ�Ʈ ���� ũ�⸦ 48px�� ����
				textField[j][i].setPrefHeight(48);					// �ؽ�Ʈ ���� ũ�⸦ 48px�� ����
				if (sdoku.getsArr(i, j) != 0) {						// ������ �����Ѵ�. true : ���ڸ� ����, false : �� ��������
					textField[j][i].setText(sdoku.getsArr(i, j) + "");	// ����� �ƴϸ� ���ڸ� �ִ´�.
					textField[j][i].setEditable(false);				// ������ �Ұ����ϰ� �Ѵ� ( �����̱� ������)
					textField[j][i].setId("Qes");					// ID ���� (���� �ٲ۴�.)
				} else {											// ����̸�
					textField[j][i].setText("");					// ���� ����
					textField[j][i].setId("Ans");					// ID ����
					textField[j][i].setEditable(true);				// ������ �����ϰ� �Ѵ�
				}	
			}
		}

//		Background bg = new Background(new BackgroundFill(Color.rgb(234, 234, 234),new CornerRadii(0) , new Insets(0.f)));
		for (int col = 0; col< 9; col++) {			// ���� �ٷ�� �ݺ���
			for (int row = 0; row< 9; row++) {			// ���� �ٷ�� �ݺ���
				StackPane cell = new StackPane();			// ���̾ƿ��� �ٷ� �г�
				
				cell.setPadding(new Insets(5));					// Stackpane�� ����ϴ� ������ 
				cell.getStyleClass().add("cell");				// css������ ���� 
				if(col == 2 || col == 5){						// ���� �� �߱�
					cell.setId("right");					// �߾����� ���ĵǱ� ������
				}
				if(row == 2 || row == 5){						// ���� �� �߱�
					cell.setId("bottom");
				}
				if((row == 2||row==5) && (col == 2|| col == 5)){		// �ߺ��Ǵ� �κп� ������ �ϱ�
					cell.setId("rc22");
				}
				cell.getChildren().add(textField[col][row]);			// stackpane�� �ؽ�Ʈ�� �߰��Ѵ�.
				board.add(cell, col, row);							// stackpane�� �߰�
			}
		}

																// file�� �� �޴��� ����
		MenuItem add = new MenuItem("New Game");			 	// new game ���ο� ������ ����Ѵ�.
		add.setOnAction(new EventHandler<ActionEvent>() {		// �̺�Ʈ 

			@Override
			public void handle(ActionEvent event) {
				newGame();
			}
		});

		MenuItem Reset = new MenuItem("Reset");					// ���� ���� ���� ������ ó������ �ٽ� �����Ѵ�.
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

																// Game Level�� ���� �Ѵ�. (������ ������ Level ����)
		MenuItem low = new MenuItem("����");								// ���� Level
		low.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Sudoku sdk = Sudoku.getInstance();
				sdk.setNum(45);									// ����� ������ 40���� ����
				sdk.inputNum();									// ������ ���� �����.
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

		MenuItem Anser = new MenuItem("�� ���");
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



		// Ű�̺�Ʈ�� ã�� �ݺ���
		for(int inx=0;inx < textField.length; inx++){
			for(int jnx = 0; jnx< textField[inx].length; jnx++ ){
				textField[jnx][inx].setOnKeyReleased(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(token){
							check();
						}// �� �˻� �޼���
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
					int tmp = Integer.parseInt(textField[j][i].getText());			//�Է¹��� ���ڰ� �������� Ȯ��
					if(!(tmp >= 1 && tmp <= 9)){								// �����̸� 1~9 ���� ���� Ȯ��						
						textField[j][i].setText("");							// ���� ���̸� ���
					}
				}catch(NumberFormatException nfe) {
					textField[j][i].setText("");								// ���ڰ� �ƴϸ� ���
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
//			alert1.setTitle("�Ϸ�");
//			alert1.setHeaderText(null);
//			alert1.setContentText("�����մϴ�. \n������ �Ϸ��ϼ̽��ϴ�.");
//			token = false;
//			alert1.showAndWait();
			
			
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
