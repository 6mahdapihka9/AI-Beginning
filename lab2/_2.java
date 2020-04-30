package _2;

import java.util.Random;

public class _2 {
	static Random r = new Random();
	static  String[] x1 = 
		{"111101101101111",//0
		 "001011101001001",//1
		 "111001111100111",//2
		 "111001111001111",//3
		 "101101111001001",//4
		 "111100111001111",//5
		 "111100111101111",//6
		 "111001001001001",//7
		 "111101111101111",//8
		 "111101111001111"};//9
	static String[] x = 
		{"01110011111101111001110111111101110",//0
		"00010001100111001010000100001000010",//1
		"00000011100101000110011000111100000",//2
		"00000011100101000110000101111001100",//3
		"00000011100101001110000100001000010",//4
		"01110010000100001110000100111000000",//5
		"01110011100100011110110100111000110",//6
		"11111111110001001110011100100011000",//7
		"01110010100111001110010100111001110",//8
		"00110011101101001110000101111011110"};//9
	static int n = 35, w[] = new int[n+1], S, i, y, success;
	static int matr_D[][] = new int[10][10], eps = 0;
	
	public static void main(String args[]) {
		S = i = y = success = 0;
		
		for (int i = 0; i < 10; i++) 
			for (int k = 0; k < 10; k++) 
				matr_D[i][k] = (i == k && i%2 == 0 && k%2 == 0)? 1 : 0;
		
		for (int i = 0; i < n+1; i++) {
			w[i] = r.nextInt(5);
			//w[i] = 0;
			System.out.print(w[i] + " ");
		}
		System.out.println();
		
		while(true) {
			for (int j = 0; j < n; j++) 
				S += ((x[i].charAt(j) == '1')?1:0) * w[j];
				S += w[n];
				
			eps = matr_D[i][i] - ((S >= 0)? 1 : 0);
			if (eps==0) {
				success++;
				if (success == 10)
					break;
				i = (i < 9)? i+1 : 0;
			} else {
				for (int j = 0; j < n; j++) 
					w[j] += eps*((x[i].charAt(j) == '1')?1:0);
					w[n] += eps;
				success = 0;
			} 
			S=0;
		}
		
		for (int i = 0; i < n; i++) 
			System.out.print(w[i] + " ");
		System.out.println();
		
		for (int i = 0; i < 10; i++) {
			S = 0;
			for (int j = 0; j < n; j++) 
				S += ((x[i].charAt(j) == '1')?1:0) * w[j];
				S += w[n];
			System.out.println("i: " + i + " S: " + S);
		}
	}
}