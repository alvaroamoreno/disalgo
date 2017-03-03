package P1;

import java.util.Stack;


public class biseccion {
	
	private double a;
	private double b;
	private double error;
	
	public biseccion (double a, double b, double error) {
		this.a = a;
		this.b = b;
		this.error = error;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getError() {
		return error;
	}

	public double recurbisec () {		//Complejidad T(n-1) + T(n-2), n el resto de casos.
		double p = (a+b)/2;
		double sol = 0;
		if (Math.abs(eval(p)) <= error) 		//base case
			sol = p;
		else {
			if (eval(p) * eval(a) > 0) {			//es mayor que 0
				setA (p);
				sol = recurbisec ();
			} else {					//es menor que 0
				setB (p);
				sol = recurbisec ();
			}
		}

		return sol;
	}

	public double stackbisec () {		//Complejidad O(n); Va de 0 a n (n es la iteración en la que se cumple eval(p) <= error
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
	
//	public static void main(String [] args) {
//	double result;
//	double error = 10e-4;
//	String eq = "(X*X*X)+10*X - 5";
//	long t1,t2,t3;
//	
//	t1 = System.nanoTime();
//	result = recurbisec(0,1,error);
//	t2 = System.nanoTime();
//	result = stackbisec (0,1,error);
//	t3 = System.nanoTime();
//	
//	System.out.println("The root of " + eq + " is: " + result);
//	System.out.println("Para el método recursivo llevó: " + (t2-t1) + " ns, para el de pilas: " + (t3-t2) + " ns.");
//	System.out.println("Para el método recursivo llevó: " + (t2-t1)/10e6 + " ms, para el de pilas: " + (t3-t2)/10e6 + " ms.");
//}

}