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


	// 숫자를 차례대로 넣는 메서드
	public void inputNum() {

		// 입력체크에서 반복이 심하면 전이전 구역 시작 점으로 이동
		int count = 0;						 

		for (int i = 0; i < arr.length; i++) {
			// 입력할때 중복 방지와 빠른 입력을위해 생성
			int[] numArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };		 

			for (int j = 0; j < arr[i].length; j++) {

				// 랜덤으로 숫자를 출력 인덱스로 사용
				int tmp = ((int) (Math.random() * numArray.length));			
				arr[i][j] = numArray[tmp];

				// 열 체크와 블럭 체크를 해서
				if (colCheck(arr, j) || blockCheck(arr, i, j)) {		  
					
					// 중복이 있으면 true
					count++;										
					// 행을 9칸을 3구역으로 나누었을때
					if (count > 9) {								
						// 자신이 있는 구역을 확인 한다. -1은  for 문에서 +1이 되기
						i = (i / 3) * 3 - 1;						
						// 때문에 내려줬다.
						first(arr, i + 1);							
						break;
					}
					// 열이 첫번째가 아니면 다시 한번 반복한다.
					if (j != 0) {									
						//열을 뒤로 한칸 옮긴다.
						j--;											
						continue;
						// 열이 첫번째이면 전번 행의 마지막 부터 다시 한다.
					} else {										 
						// 첫번째 행이 아니면
						if (i != 0) {								
							// 전행 마지막으로 이동
							i--;									
							j = arr[i].length - 1;
							// 행을 바꾸니 열확인 을 빠져 나온다.
							break;									
						}
					}
				}
				// 진행이 이상이 없으면 반복 카운트 초기화
				count = 0;												
				// num에 입력된  값 비우기
				numArray[tmp] = 0;											
				// num배열이 0이 아니면
				if (numArray.length > 0){									
					// 수정한 배열을 다시 배열에 넣는다.
					// 배열 사이즈 줄이기
					numArray = arrChg(numArray);									
				}
			}
		}
		// 답이 생성되면 문제를 생성함
		makeSudoku(arr);												
	}
	
	//배열에서 0이 있는 자리를 제거하고 반환하는 메소드
	private int[] arrChg(int[] arr) {
		// 배열을 1사이즈 작은 배열을 생성
		int[] cArr = new int[arr.length - 1]; 	
		// index
		int i;									
		// 0이 있는 자리를 찾는 포인트
		int p = 0;								
			
		// 0이 들어 있는 자리를 p에 넣는다.
		for (i = 0; i < arr.length; i++) {		
			// 0을 찾는다
			if (arr[i] == 0) {						
				// 0이 들어 있는 자리를 찾는다.
				p = i;								
				// 0을 찾으면 빠져 나온다.
				break;								
			}
		}
		// 맨 앞자리 제거
		if (i == 0) {												
			// 0이 배열의 첫번째에 들어있을 때  1부터 끝까지 복사
			System.arraycopy(arr, 1, cArr, 0, arr.length - 1);		
			// 마지막 자리 제거
		} else if (i == arr.length - 1) {							
			// 0이 마지막에 들어있을 때 0번 부터 arr.length -1까지 복사
			System.arraycopy(arr, 0, cArr, 0, arr.length - 1);		
			// p가 중간에 들어있을때
		} else {													
			// 0부터 p-1 까지 복사
			System.arraycopy(arr, 0, cArr, 0, p);					
			// 나머지 부분 복사
			System.arraycopy(arr, p + 1, cArr, p, arr.length - 1 - p);	
		}
		// 배열을 리턴한다.
		return cArr;												
	}


	// 값이 채워진 배열에서 빈 공간을 만들어 문제를 생성
	private void makeSudoku(int[][] arr) {							
		// 배열의 크기 만큼의 배열을 만든다.
		sArr = new int[arr.length][arr[0].length];				
												
		for (int i = 0; i < sArr.length; i++) {				
			for (int j = 0; j < sArr[i].length; j++) {
				// 문제를 생성 할 배열을 복사한다.
				sArr[i][j] = arr[i][j];						
			}
		}

		// 좌표를 0부터 8까지 랜덤으로 생성 ( 행 , 열 ) -> ( 0 ~ 8 , 0 ~ 8 ) 
		for (int i = 0; i < num; i++) {						
			int num1 = (int) (Math.random() * 9);
			int num2 = (int) (Math.random() * 9);
			// 0을 넣어 빈공간을 만든다.
			sArr[num1][num2] = 0;							
		}
	}

	
	// 되돌아갈 때 이미 입력된 값을 지우는 메서드
	// i 는 0을 집어넣기 시작 할 행번호
	private void first(int[][] arr, int i) {			
		for (; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				// 0을 집어 넣으며 초기화
				arr[i][j] = 0;							
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
	// push 할 값을 배열에 인덱스 값으로 넣어 같은 값이 들어있는지 검사
	private boolean colCheck(int[][] arr, int x) { 
		// 숫자를 체크 할 boolean 배열선언
		boolean[] check = new boolean[9];			
		// 9개의 행을 검사
		for (int i = 0; i < 9; i++) {				
			// 비어있지 않은 경우(0이 아닌 경우)
			if (arr[i][x] != 0) {					
				// 처음 들어온 값이면
				if (!check[arr[i][x] - 1]){			
					// 그 자리의 값을 true로 바꾸고
					check[arr[i][x] - 1] = true;	
					// 이미 들어온 값이 있으면
				}else {								
					// 되돌아 가기 위해 true를 반환한다.
					return true;					
				}
			}
		}
		// 같은 값이 없으면 false를 반환한다.
		return false;								
	}

	
	// 3 X 3 구역을 검사하는 메서드
	private boolean blockCheck(int[][] arr, int x, int y) {
		// 숫자를 체크 할 boolean 배열선언
		boolean[] check = new boolean[9];				

		// 열을 3구역으로 나누고 자신의 속해 있는 구역 시작 부터 시작
		for (int i = ((x) / 3) * 3; i < ((x) / 3) * 3 + 3; i++) { 	 
			// 행 3구역으로 나누고 자신의 속해 있는 구역 시작 부터 시작
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
