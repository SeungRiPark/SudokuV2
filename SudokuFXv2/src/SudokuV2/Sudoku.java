package SudokuV2;

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

		int count = 0;						// �Է�üũ���� �ݺ��� ���ϸ� ������ ���� ���� ������ �̵� 

		for (int i = 0; i < arr.length; i++) {
			int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };		 // �Է��Ҷ� �ߺ� ������ ���� �Է������� ����

			for (int j = 0; j < arr[i].length; j++) {

				int tmp = ((int) (Math.random() * num.length));			// �������� ���ڸ� ��� �ε����� ���
				arr[i][j] = num[tmp];

				if (colCheck(arr, j) || blockCheck(arr, i, j)) {		// �� üũ�� �� üũ�� �ؼ�  
					
					count++;											// �ߺ��� ������ true
					if (count > 9) {								// ���� 9ĭ�� 3�������� ����������
						i = (i / 3) * 3 - 1;						// �ڽ��� �ִ� ������ Ȯ�� �Ѵ�. -1��  for ������ +1�� �Ǳ� 
						first(arr, i + 1);							// ������ �������.
						break;
					}
					if (j != 0) {									// ���� ù��°�� �ƴϸ� �ٽ� �ѹ� �ݺ��Ѵ�.
						j--;											//���� �ڷ� ��ĭ �ű��.
						continue;
					} 
					else {										// ���� ù��°�̸� ���� ���� ������ ���� �ٽ� �Ѵ�. 
						if (i != 0) {								// ù��° ���� �ƴϸ�
							i--;									// ���� ���������� �̵�
							j = arr[i].length - 1;
							break;									// ���� �ٲٴ� �� ������ ���� ���´�.
						}
					}
				}
				count = 0;												// ������ �̻��� ������ �ݺ� ī��Ʈ �ʱ�ȭ
				num[tmp] = 0;											// num�� �Էµ�  �� ����
				if (num.length > 0){									// num�迭�� 0�� �ƴϸ�
					num = arrChg(num);									// ������ �迭�� �ٽ� �迭�� �ִ´�.
				}// ���� ������ ���̱�
			}
		}
		makeSudoku(arr);												// ���� �����Ǹ� ������ ������
	}
	
	//�迭���� 0�� �ִ� �ڸ��� �����ϰ� ��ȯ�ϴ� �޼ҵ�
	private int[] arrChg(int[] arr) {
		int cArr[] = new int[arr.length - 1]; 	// �迭�� 1������ ���� �迭�� ����
		int i;									// index
		int p = 0;								// 0�� �ִ� �ڸ��� ã�� ����Ʈ
			
		for (i = 0; i < arr.length; i++) {		// 0�� ��� �ִ� �ڸ��� p�� �ִ´�.
			if (arr[i] == 0) {						// 0�� ã�´�
				p = i;								// 0�� ��� �ִ� �ڸ��� ã�´�.
				break;								// 0�� ã���� ���� ���´�.
			}
		}
		if (i == 0) {												// �� ���ڸ� ����
			System.arraycopy(arr, 1, cArr, 0, arr.length - 1);		// 0�� �迭�� ù��°�� ������� ��  1���� ������ ����
		} else if (i == arr.length - 1) {							// ������ �ڸ� ����
			System.arraycopy(arr, 0, cArr, 0, arr.length - 1);		// 0�� �������� ������� �� 0�� ���� arr.length -1���� ����
		} else {													// p�� �߰��� ���������
			System.arraycopy(arr, 0, cArr, 0, p);					// 0���� p-1 ���� ����
			System.arraycopy(arr, p + 1, cArr, p, arr.length - 1 - p);	// ������ �κ� ����
		}
		return cArr;												// �迭�� �����Ѵ�.
	}


	// ���� ä���� �迭���� �� ������ ����� ������ ����
	private void makeSudoku(int[][] arr) {							

		sArr = new int[arr.length][arr[0].length];			// �迭�� ũ�� ��ŭ�� �迭�� �����.	
												// ������ ���� ����
		for (int i = 0; i < sArr.length; i++) {				
			for (int j = 0; j < sArr[i].length; j++) {
				sArr[i][j] = arr[i][j];						// ������ ���� �� �迭�� �����Ѵ�.
			}
		}

		for (int i = 0; i < num; i++) {						// ��ǥ�� �������� ����
			int num1 = (int) (Math.random() * 9);
			int num2 = (int) (Math.random() * 9);
			sArr[num1][num2] = 0;							// 0�� �־� ������� �����.
		}
	}

	
	// �ǵ��ư� �� �̹� �Էµ� ���� ����� �޼���
	private void first(int[][] arr, int i) {			// i �� 0�� ����ֱ� ���� �� ���ȣ
		for (; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = 0;							// 0�� ����ִ´�.
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
	private boolean colCheck(int[][] arr, int x) { // push �� ���� �迭�� �ε��� ������ �־� ���� ���� ����ִ��� �̻�
		boolean[] check = new boolean[9];			// ���ڸ� üũ �� boolean �迭����
		for (int i = 0; i < 9; i++) {				// 9���� ���� �˻�
			if (arr[i][x] != 0) {					// ������� ���� ���
				if (!check[arr[i][x] - 1]){			// ó�� ���� ���̸�
					check[arr[i][x] - 1] = true;	// �� ���� true�� �ٲٰ�
				}
				else {								// �̹� ���� ���� ������ 
					return true;					// true�� ��ȯ�Ѵ�.
				}
			}
		}
		return false;								// ���� ���� ������ false�� ��ȯ�Ѵ�.
	}

	
	// 3 X 3 ������ �˻��ϴ� �޼���
	private boolean blockCheck(int[][] arr, int x, int y) {
		boolean[] check = new boolean[9];				// ���ڸ� üũ �� boolean �迭����

		for (int i = ((x) / 3) * 3; i < ((x) / 3) * 3 + 3; i++) { 	 // �� 3�������� ������ �ڽ��� ���� �ִ� ���� ���� ���� ����
			for (int j = ((y) / 3) * 3; j < ((y) / 3) * 3 + 3; j++) {	// �� 3�������� ������ �ڽ��� ���� �ִ� ���� ���� ���� ����

				if (arr[i][j] != 0) {
					if (!check[arr[i][j] - 1]){
						check[arr[i][j] - 1] = true;
					}
					else {
						return true;
					}
				}
			}
		}

		return false;
	}

}
