package _3v2;

import java.util.Random;

public class _3Pers {
	double w[];
	static Random r = new Random();
	
	_3Pers(int _n){
		w = new double[_n+1];
		for (int i = 0; i < _n+1; i++) 
			w[i] = r.nextInt(5);
	}
}