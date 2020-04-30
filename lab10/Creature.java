package _10v2;

import java.util.Random;

public class Creature {
	double y, x;
	int fen[];
	Random r = new Random();
	
	Creature(int _aOfChorm){
		fen = new int[_aOfChorm];
		x = r.nextInt(50);
		for (int i = 0, k = 128; i < fen.length; i++, k /= 2)
			if (((int)x & k) != 0)
				fen[i] = 1;
			else 
				fen[i] = 0;
		y = -Math.abs(Math.sin(2*x)+x-25)+25;
	}
}