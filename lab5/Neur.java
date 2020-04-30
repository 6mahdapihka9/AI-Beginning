package _5v2;

import java.util.Random;

public class Neur {
	/*nOfW - number of weight
	 * a_W - amount of weights
	 * a_N - amount of neurs
	 */
	public double w[], y, delta, e = 2.71828;
	public Random r = new Random();
	
	public Neur(int _a_W){
		w = new double[_a_W+1];
		for (int nOfW = 0; nOfW < _a_W+1; nOfW++) {
			w[nOfW] = r.nextInt(5);w[nOfW] /= 10; w[nOfW] -= 0.25;
			//w[nOfW] = 0.2;
		}
	}
	
	public double Y(double inp[]) {
		double S = 0;
		for (int nOfW = 0; nOfW < w.length-1; nOfW++)
			S += w[nOfW] * inp[nOfW];
		S += w[w.length-1];
		y = 1/(1 + Math.pow(1/e, S));
		return y;
	}
}