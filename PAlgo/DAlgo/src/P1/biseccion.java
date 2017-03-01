package P1;

import java.util.Stack;


public class biseccion {
	
	public static void main(String [] args) {
		double result;
		double error = 10e-4;
		String eq = "(X*X*X)+10*X - 5";
		long t1,t2,t3;
		
		t1 = System.nanoTime();
		result = recurbisec(0,1,error);
		t2 = System.nanoTime();
		result = stackbisec (0,1,error);
		t3 = System.nanoTime();
		
		System.out.println("The root of " + eq + " is: " + result);
		System.out.println("Para el método recursivo llevó: " + (t2-t1) + " ns, para el de pilas: " + (t3-t2) + " ns.");
		System.out.println("Para el método recursivo llevó: " + (t2-t1)/10e6 + " ms, para el de pilas: " + (t3-t2)/10e6 + " ms.");
	}

	static double recurbisec (double a, double b, double error) {		//Complejidad T(n-1) + T(n-2), n el resto de casos.
		double valormedio = (a+b)/2;
		double sol = 0;
		if (Math.abs(eval(valormedio)) <= error){		//base case
			sol = valormedio;
		} else {

			if (eval(valormedio) * eval(a) > 0) {			//es mayor que 0
				sol = recurbisec (valormedio,b,error);

			} else						//es menor que 0
				sol = recurbisec (a,valormedio,error);	
		}

		return sol;		
	}

	static double stackbisec (double a, double b, double error) {		//Complejidad O(n); Va de 0 a n (n es la iteración en la que se cumple eval(p) <= error
		Stack <Double> left = new Stack <Double>();
		Stack <Double> right = new Stack <Double>();
		
		double p = (a+b)/2.0;
		
		left.push(a);
		right.push(b);
		
		while (Math.abs(eval(p)) > error) {
			if (eval(left.peek()) * eval(p) > 0) {
				left.push (p);
				right.push (right.peek());
			} else {
				left.push (left.peek());
				right.push (p);
			}
			p = (left.peek() + right.peek())/2.0;
		}

		return p;
	}

	static double eval(double x){

		return Math.pow(x, 3)+x-1;			//equation x^3+x-1

	}

}