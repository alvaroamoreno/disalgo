package P1;

import java.util.Scanner;

public class Main {
	
	public static void main(String [] args) {
		double result;
		double error = 10e-4;
		String eq = "(X*X*X)+10*X-5";
		Scanner sc = new Scanner (System.in);
		long t1,t2;
		boolean exit = false;
		
		System.out.println("Nuestra ecuación es X^3+10X-5.");
		System.out.println("Introduzca el extremo izquierdo del intervalo para la bisección.");
		double a = sc.nextDouble();
		System.out.println("Ahora, el extremo derecho del intervalo.");
		double b = sc.nextDouble();
		biseccion bis = new biseccion (a, b, error);
		
		do {
			System.out.println();
			System.out.println("Seleccione la forma de resolver el problema:\n1. Recursive method.\n2. Simulación con pilas.\n3. Cambiar intervalo.\n4. Cambiar error.\n5. Salir.");
			int op = sc.nextInt();
			
			switch (op) {
				case 1:
					t1 = System.nanoTime();
					result = bis.recurbisec();
					t2 = System.nanoTime();
					
					System.out.println("La raíz de " + eq + " es: " + result);
					System.out.println("Para el método recursivo llevó: " + (t2-t1) + " ns, " + (t2-t1)/10e6 + " ms.");
					break;
				case 2:
					t1 = System.nanoTime();
					result = bis.stackbisec();
					t2 = System.nanoTime();
					
					System.out.println("La raíz de " + eq + " es: " + result);
					System.out.println("Para la simulación de pilas llevó: " + (t2-t1) + " ns, " + (t2-t1)/10e6 + " ms.");
					break;
				case 3:
					System.out.println("Introduzca el extremo izquierdo del intervalo para la bisección.");
					bis.setA(sc.nextDouble());
					System.out.println("Ahora, el extremo derecho del intervalo..");
					bis.setB(sc.nextDouble());
					break;
				case 4:
					System.out.println("Introduzca el nuevo error para el problema.");
					error = sc.nextDouble();
					break;
				case 5:
					exit = true;
					break;
				default:
					System.out.println("Select a correct option.");
					break;
			}
		} while (exit == false);
		
	}
	
}
