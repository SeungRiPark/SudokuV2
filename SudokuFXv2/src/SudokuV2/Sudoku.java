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


	// 숫자를 차례대로 넣는 메서드
	public void inputNum() {

		int count = 0;						// 입력체크에서 반복이 심하면 전이전 구역 시작 점으로 이동 

		for (int i = 0; i < arr.length; i++) {
			int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };		 // 입력할때 중복 방지와 빠른 입력을위해 생성

			for (int j = 0; j < arr[i].length; j++) {

				int tmp = ((int) (Math.random() * num.length));			// 랜덤으로 숫자를 출력 인덱스로 사용
				arr[i][j] = num[tmp];

				if (colCheck(arr, j) || blockCheck(arr, i, j)) {		// 열 체크와 블럭 체크를 해서  
					
					count++;											// 중보이 있으면 true
					if (count > 9) {								// 행을 9칸을 3구역으로 나누었을때
						i = (i / 3) * 3 - 1;						// 자신이 있는 구역을 확인 한다. -1은  for 문에서 +1이 되기 
						first(arr, i + 1);							// 때문에 내려줬다.
						break;
					}
					if (j != 0) {									// 열이 첫번째가 아니면 다시 한번 반복한다.
						j--;											//열을 뒤로 한칸 옮긴다.
						continue;
					} 
					else {										// 열이 첫번째이면 전번 행의 마지막 부터 다시 한다. 
						if (i != 0) {								// 첫번째 행이 아니면
							i--;									// 전행 마지막으로 이동
							j = arr[i].length - 1;
							break;									// 행을 바꾸니 열 삽입을 빠져 나온다.
						}
					}
				}
				count = 0;												// 진행이 이상이 없으면 반복 카운트 초기화
				num[tmp] = 0;											// num에 입력된  값 비우기
				if (num.length > 0){									// num배열이 0이 아니면
					num = arrChg(num);									// 수정한 배열을 다시 배열에 넣는다.
				}// 버퍼 사이즈 줄이기
			}
		}
		makeSudoku(arr);												// 답이 생성되면 문제를 생성함
	}
	
	//배열에서 0이 있는 자리를 제거하고 반환하는 메소드
	private int[] arrChg(int[] arr) {
		int cArr[] = new int[arr.length - 1]; 	// 배열을 1사이즈 작은 배열을 생성
		int i;									// index
		int p = 0;								// 0이 있는 자리를 찾는 포인트
			
		for (i = 0; i < arr.length; i++) {		// 0이 들어 있는 자리를 p에 넣는다.
			if (arr[i] == 0) {						// 0을 찾는다
				p = i;								// 0이 들어 있는 자리를 찾는다.
				break;								// 0을 찾으면 빠져 나온다.
			}
		}
		if (i == 0) {												// 맨 앞자리 제거
			System.arraycopy(arr, 1, cArr, 0, arr.length - 1);		// 0이 배열의 첫번째에 들어있을 때  1부터 끝까지 복사
		} else if (i == arr.length - 1) {							// 마지막 자리 제거
			System.arraycopy(arr, 0, cArr, 0, arr.length - 1);		// 0이 마지막에 들어있을 때 0번 부터 arr.length -1까지 복사
		} else {													// p가 중간에 들어있을때
			System.arraycopy(arr, 0, cArr, 0, p);					// 0부터 p-1 까지 복사
			System.arraycopy(arr, p + 1, cArr, p, arr.length - 1 - p);	// 나머지 부분 복사
		}
		return cArr;												// 배열을 리턴한다.
	}


	// 값이 채워진 배열에서 빈 공간을 만들어 문제를 생성
	private void makeSudoku(int[][] arr) {							

		sArr = new int[arr.length][arr[0].length];			// 배열의 크기 만큼의 배열을 만든다.	
												// 공백을 만들 개수
		for (int i = 0; i < sArr.length; i++) {				
			for (int j = 0; j < sArr[i].length; j++) {
				sArr[i][j] = arr[i][j];						// 문제를 생성 할 배열을 복사한다.
			}
		}

		for (int i = 0; i < num; i++) {						// 좌표를 랜덤으로 생성
			int num1 = (int) (Math.random() * 9);
			int num2 = (int) (Math.random() * 9);
			sArr[num1][num2] = 0;							// 0을 넣어 빈공간을 만든다.
		}
	}

	
	// 되돌아갈 때 이미 입력된 값을 지우는 메서드
	private void first(int[][] arr, int i) {			// i 는 0을 집어넣기 시작 할 행번호
		for (; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = 0;							// 0을 집어넣는다.
			}
		}
	}

	
	
	// y행을 검사하는 메서드  :  배열값을 집어넣으면서 불필요
//	private boolean rowCheck(int[][] arr, int y) { // 행고정 
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

	// x열을 검사하는 메서드 : 넣을 숫자를 배열의 방 번호로 하여 그 값을 변도하여 같은 값이 들어 오는지를 확인
	private boolean colCheck(int[][] arr, int x) { // push 할 값을 배열에 인덱스 값으로 넣어 같은 값이 들어있는지 겁사
		boolean[] check = new boolean[9];			// 숫자를 체크 할 boolean 배열선언
		for (int i = 0; i < 9; i++) {				// 9개의 행을 검사
			if (arr[i][x] != 0) {					// 비어있지 않은 경우
				if (!check[arr[i][x] - 1]){			// 처음 들어온 값이면
					check[arr[i][x] - 1] = true;	// 그 값을 true로 바꾸고
				}
				else {								// 이미 들어온 값이 있으면 
					return true;					// true를 반환한다.
				}
			}
		}
		return false;								// 값은 값이 없으면 false를 반환한다.
	}

	
	// 3 X 3 구역을 검사하는 메서드
	private boolean blockCheck(int[][] arr, int x, int y) {
		boolean[] check = new boolean[9];				// 숫자를 체크 할 boolean 배열선언

		for (int i = ((x) / 3) * 3; i < ((x) / 3) * 3 + 3; i++) { 	 // 행 3구역으로 나누고 자신의 속해 있는 구역 시작 부터 시작
			for (int j = ((y) / 3) * 3; j < ((y) / 3) * 3 + 3; j++) {	// 열 3구역으로 나누고 자신의 속해 있는 구역 시작 부터 시작

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
