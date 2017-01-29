package com.vic.sudokuv2;

class Sudoku {
	
	private  int[][] arr = new int[9][9];
	private  int[][] sArr;
	private int num = 70;
	private static Sudoku sdk = new Sudoku();
	
	public int getArr(int i, int j) {
		return arr[i][j];
	}
	public int getsArr(int i,int j) {
		return sArr[i][j];
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	private Sudoku() {
		
	}
	public static Sudoku getInstance() {
		return sdk;
	}


	// ���ڸ� ���ʴ�� �ִ� �޼���
	public void inputNum() {

		// �Է�üũ���� �ݺ��� ���ϸ� ������ ���� ���� ������ �̵�
		int count = 0;						 

		for (int i = 0; i < arr.length; i++) {
			// �Է��Ҷ� �ߺ� ������ ���� �Է������� ����
			int[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };		 

			for (int j = 0; j < arr[i].length; j++) {

				// �������� ���ڸ� ��� �ε����� ���
				int tmp = ((int) (Math.random() * numArray.length));			
				arr[i][j] = numArray[tmp];

				// �� üũ�� �� üũ�� �ؼ�
				if (colCheck(arr, j) || blockCheck(arr, i, j)) {		  
					
					// �ߺ��� ������ true
					count++;										
					// ���� 9ĭ�� 3�������� ����������
					if (count > 9) {								
						// �ڽ��� �ִ� ������ Ȯ�� �Ѵ�. -1��  for ������ +1�� �Ǳ�
						i = (i / 3) * 3 - 1;						
						// ������ �������.
						first(arr, i + 1);							
						break;
					}
					// ���� ù��°�� �ƴϸ� �ٽ� �ѹ� �ݺ��Ѵ�.
					if (j != 0) {									
						//���� �ڷ� ��ĭ �ű��.
						j--;											
						continue;
						// ���� ù��°�̸� ���� ���� ������ ���� �ٽ� �Ѵ�.
					} else {										 
						// ù��° ���� �ƴϸ�
						if (i != 0) {								
							// ���� ���������� �̵�
							i--;									
							j = arr[i].length - 1;
							// ���� �ٲٴ� ��Ȯ�� �� ���� ���´�.
							break;									
						}
					}
				}
				// ������ �̻��� ������ �ݺ� ī��Ʈ �ʱ�ȭ
				count = 0;												
				// num�� �Էµ�  �� ����
				numArray[tmp] = 0;											
				// num�迭�� 0�� �ƴϸ�
				if (numArray.length > 0){									
					// ������ �迭�� �ٽ� �迭�� �ִ´�.
					// �迭 ������ ���̱�
					numArray = arrChg(numArray);									
				}
			}
		}
		// ���� �����Ǹ� ������ ������
		makeSudoku(arr);												
	}
	
	//�迭���� 0�� �ִ� �ڸ��� �����ϰ� ��ȯ�ϴ� �޼ҵ�
	private int[] arrChg(int[] arr) {
		// �迭�� 1������ ���� �迭�� ����
		int[] cArr = new int[arr.length - 1]; 	
		// index
		int i;									
		// 0�� �ִ� �ڸ��� ã�� ����Ʈ
		int p = 0;								
			
		// 0�� ��� �ִ� �ڸ��� p�� �ִ´�.
		for (i = 0; i < arr.length; i++) {		
			// 0�� ã�´�
			if (arr[i] == 0) {						
				// 0�� ��� �ִ� �ڸ��� ã�´�.
				p = i;								
				// 0�� ã���� ���� ���´�.
				break;								
			}
		}
		// �� ���ڸ� ����
		if (i == 0) {												
			// 0�� �迭�� ù��°�� ������� ��  1���� ������ ����
			System.arraycopy(arr, 1, cArr, 0, arr.length - 1);		
			// ������ �ڸ� ����
		} else if (i == arr.length - 1) {							
			// 0�� �������� ������� �� 0�� ���� arr.length -1���� ����
			System.arraycopy(arr, 0, cArr, 0, arr.length - 1);		
			// p�� �߰��� ���������
		} else {													
			// 0���� p-1 ���� ����
			System.arraycopy(arr, 0, cArr, 0, p);					
			// ������ �κ� ����
			System.arraycopy(arr, p + 1, cArr, p, arr.length - 1 - p);	
		}
		// �迭�� �����Ѵ�.
		return cArr;												
	}


	// ���� ä���� �迭���� �� ������ ����� ������ ����
	private void makeSudoku(int[][] arr) {							
		// �迭�� ũ�� ��ŭ�� �迭�� �����.
		sArr = new int[arr.length][arr[0].length];				
												
		for (int i = 0; i < sArr.length; i++) {				
			for (int j = 0; j < sArr[i].length; j++) {
				// ������ ���� �� �迭�� �����Ѵ�.
				sArr[i][j] = arr[i][j];						
			}
		}

		// ��ǥ�� 0���� 8���� �������� ���� ( �� , �� ) -> ( 0 ~ 8 , 0 ~ 8 ) 
		for (int i = 0; i < num; i++) {						
			int num1 = (int) (Math.random() * 9);
			int num2 = (int) (Math.random() * 9);
			// 0�� �־� ������� �����.
			sArr[num1][num2] = 0;							
		}
	}

	
	// �ǵ��ư� �� �̹� �Էµ� ���� ����� �޼���
	// i �� 0�� ����ֱ� ���� �� ���ȣ
	private void first(int[][] arr, int i) {			
		for (; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				// 0�� ���� ������ �ʱ�ȭ
				arr[i][j] = 0;							
			}
		}
	}

	
	
	// y���� �˻��ϴ� �޼���  :  �迭���� ��������鼭 ���ʿ�
//	private boolean rowCheck(int[][] arr, int y) { // ����� 
//		boolean[] check = new boolean[9];
//		for (int i = 0; i < 9; i++) {
//			if (arr[y][i] != 0) {
//				if (!check[arr[y][i] - 1]){
//					check[arr[y][i] - 1] = true;
//				}
//				else {
//					return true;
//				}
//			}
//		}
//
//		return false;
//	}

	// x���� �˻��ϴ� �޼��� : ���� ���ڸ� �迭�� �� ��ȣ�� �Ͽ� �� ���� �����Ͽ� ���� ���� ��� �������� Ȯ��
	// push �� ���� �迭�� �ε��� ������ �־� ���� ���� ����ִ��� �˻�
	private boolean colCheck(int[][] arr, int x) { 
		// ���ڸ� üũ �� boolean �迭����
		boolean[] check = new boolean[9];			
		// 9���� ���� �˻�
		for (int i = 0; i < 9; i++) {				
			// ������� ���� ���(0�� �ƴ� ���)
			if (arr[i][x] != 0) {					
				// ó�� ���� ���̸�
				if (!check[arr[i][x] - 1]){			
					// �� �ڸ��� ���� true�� �ٲٰ�
					check[arr[i][x] - 1] = true;	
					// �̹� ���� ���� ������
				}else {								
					// �ǵ��� ���� ���� true�� ��ȯ�Ѵ�.
					return true;					
				}
			}
		}
		// ���� ���� ������ false�� ��ȯ�Ѵ�.
		return false;								
	}

	
	// 3 X 3 ������ �˻��ϴ� �޼���
	private boolean blockCheck(int[][] arr, int x, int y) {
		// ���ڸ� üũ �� boolean �迭����
		boolean[] check = new boolean[9];				

		// ���� 3�������� ������ �ڽ��� ���� �ִ� ���� ���� ���� ����
		for (int i = ((x) / 3) * 3; i < ((x) / 3) * 3 + 3; i++) { 	 
			// �� 3�������� ������ �ڽ��� ���� �ִ� ���� ���� ���� ����
			for (int j = ((y) / 3) * 3; j < ((y) / 3) * 3 + 3; j++) {	

				if (arr[i][j] != 0) {
					if (!check[arr[i][j] - 1]){
						check[arr[i][j] - 1] = true;
					}else {
						return true;
					}
				}
			}
		}
		return false;
	}

}
