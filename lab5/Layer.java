package _5v2;

import java.util.ArrayList;

public class Layer {
	/* hidden - is layer hidden
	 * nOfW - number of weight
	 * aOfW - amount of weights / amount of input data
	 * nOfNeur - number of neuron
	 * aOfNeur - amount of neurons / amount of output data
	 * d[][] - matrix of correct reactions
	 * nu - velocity
	 * successS - amount of successfully teached neurons
	 * successN - amount of successfully teached symbols
	 */
	public boolean hidden = false;	
	public Neur N[];
	public int d[][], aOfW, aOfNeur;
	public double d_y[], e = 2.71828, nu = 0.1;
	public static int successS, successN;
	public ArrayList<double[]> x = new ArrayList<double[]>();
	public ArrayList<String> nameOfSymb = new ArrayList<String>();
	
	public Layer(boolean _h, int _a_W, int _a_N){
		hidden = _h;
		aOfNeur = _a_N;
		aOfW = _a_W;

		N = new Neur[aOfNeur];
		for (int i = 0; i < aOfNeur; i++) {
			N[i] = new Neur(aOfW);
			if (!hidden) {
				x.add(new double[aOfW]);
				
			}
		}
		
		if (!hidden) {
			d = new int[aOfNeur][aOfNeur];
			for (int n1OfNeur = 0; n1OfNeur < aOfNeur; n1OfNeur++)
				for (int n2OfNeur = 0; n2OfNeur < aOfNeur; n2OfNeur++)
					d[n1OfNeur][n2OfNeur] = (n1OfNeur == n2OfNeur)? 1 : 0 ;
		}
	}
	
	public void Y(Layer oL, int nS) {
		double inp[] = new double[aOfNeur];
		for (int nN = 0; nN < aOfNeur; nN++) {
			N[nN].Y(x.get(nS));
			inp[nN] = N[nN].y;
		}
		oL.x.set(nS, inp);
	}

	public void Y(int nS) {
		for (int nN = 0; nN < aOfNeur; nN++)
			N[nN].Y(x.get(nS));
	}
	
	public void Delta(Layer oL, int nS) {
		double sum;
		for (int nN = 0; nN < aOfNeur; nN++) {
			sum = 0;
			for (int nW = 0; nW < oL.aOfNeur; nW++)
				sum += oL.N[nW].delta * oL.N[nW].w[nN];
			N[nN].delta = N[nN].y * (1-N[nN].y) * sum;
		}
	}
	
	public void Delta(int nS) {
		for (int nN = 0; nN < aOfNeur; nN++) {
			N[nN].delta = N[nN].y * (1-N[nN].y) * (d[nN][nS]-N[nN].y);
			N[nN].delta = Math.round(N[nN].delta * 1000000.0)/1000000.0;
			if (Math.abs(d[nN][nS] - N[nN].y) <= 0.1) {
				successN++;
			}
			else
				successN = 0; 
		}
	}
	
	public void newW(int nS) {
		for (int nN = 0; nN < aOfNeur; nN++) {
			for (int nW = 0; nW < N[nN].w.length-1; nW++) 
				N[nN].w[nW] += N[nN].delta  * x.get(nS)[nW];
			N[nN].w[N[nN].w.length-1] += N[nN].delta;
		}
	}
	
	public int Check(Layer oL, double inp[]) {
		double newInp[] = new double[aOfNeur];
		for (int nN = 0; nN < aOfNeur; nN++) {
			N[nN].Y(inp);
			newInp[nN] = N[nN].y;
		}
		return oL.Check(newInp);
	}

	public int Check(double inp[]) {
		int ret = -1;
		double dY;
		for (int nN = 0; nN < aOfNeur; nN++) {
			dY = Math.round(N[nN].Y(inp) * 100000.0)/100000.0;
			System.out.println(nameOfSymb.get(nN) + " - " + dY);
			if (N[nN].Y(inp) > 0.5)
				ret = nN;
		}
		return ret;
	}
}