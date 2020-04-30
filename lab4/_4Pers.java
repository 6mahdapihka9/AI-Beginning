package _4;

import java.util.Random;

public class _4Pers {
	double w[], y, S;
	static Random r = new Random();
	
	_4Pers(int _n){
		w = new double[_n+1];
		for (int i = 0; i < _n+1; i++) {
			w[i] = r.nextInt(5);
			w[i] /= 10;
		System.out.print(w[i]);
		}
		System.out.println();
	}
}