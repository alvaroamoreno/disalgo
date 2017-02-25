package P1;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class bisection {
	public static void main(String [] args) {
		double result;
		double error=10e-4;
		String eq="(X*X*X)+10*X - 5";
		result = recurbisec(0,1,error);
		
		System.out.println("The root of " + eq + " is: " + result);
	}

	static double recurbisec(double a,double b,double error) {
		double valormedio = (a+b)/2;
		double sol = 0;
		double fa = eval(a);
		double fb = eval(b);
		double fc = eval(valormedio);
		if( Math.abs(fc) <= error){	//base case
			sol = valormedio;
		}else{

			if(fc * fa > 0){		//es mayor que 0
				sol = recurbisec (valormedio,b,error);

			}else					//es menor que 0
				sol = recurbisec (a,valormedio,error);	
		}
		return sol;		
	}


	static double eval(double x){

		return Math.pow(x, 3)+x-1;			//equation x^3+x-1

	}
}
