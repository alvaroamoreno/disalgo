package practice2nim;

import java.io.IOException;
import java.util.*;

/**
 * @authors Carlos Cordoba - Alvaro Moreno
 */
public class Juego implements Interfacetypes {
	Estado turnoFinal;
	Estado estadoInicial;   

	public Juego () throws IOException {
		turnoFinal = null;
		readFile();
		juego();      
	}
	//Lee el tablero con los palos de un fichero de texto
	private void readFile () throws IOException {
		ArrayList<Integer> Stacks = ReadFile.leerfichero("stacks.txt");
		this.estadoInicial=new Estado(Stacks);
	}  
	//Menu que selecciona que algoritmos hacer, el backard o el fordward
	private void juego() throws IOException {        
		Scanner sc=new Scanner(System.in);
		String option="";
		int opt=0;
		boolean stroption;
		do {
			System.out.println("Seleccione modo de juego (tipo de algoritmo desea que se ejecute para el tablero) : "+this.estadoInicial.getStacks());
			System.out.println("\t1. Backward");
			System.out.println("\t2. Fordward");
			System.out.println("\t3. Salir");
			do {
				System.out.println("\n Haga su eleccion: ");
				option=sc.next();

				if (isNumeric(option)) {
					opt=Integer.parseInt(option);
					if (opt>=1 && opt<=3)
						stroption=true;
					else {
						System.out.println("Su elección se encuentra fuera del intervalo.");
						stroption=false;
					}
				}
				else {
					System.out.println("Entrada no numérica.");
					stroption=false;

				}
			} while(!stroption);           
			switch (opt) {
			case 1:
				System.out.println(empezarJuego('B'));
				new Juego();
				break;
			case 2:
				System.out.println(empezarJuego('F'));
				new Juego();
				break;
			case 3:
				System.out.println("FIN DEL JUEGO"); 
				System.out.println("Realizado por: Alvaro Angel-Moreno Pinilla & Carlos Cordoba Ruiz ");
				break;
			}
		} while (opt!=3);
	}

	//Juego entre 2 oponentes
	private String empezarJuego(char algorithm) {
		int turn = 0;
		ArrayList<Estado> listaEstados;
                                            Scanner sc=new Scanner(System.in);
                                            boolean goon = false;
                                            
                                            int row = -1; int remove = -1;
		while (estadoInicial.amountoftoothpicksticks()> 0) {
			listaEstados = new ArrayList<Estado>();
			turnoFinal=null;
			turn++;
			System.out.println("___________________________");
			System.out.print("Turno:" + turn );
			if (turn % 2 == 0) {
                                                                            System.out.println(" Jugador 2:");      //Usuario
                                                                            System.out.println("    Palillos:" + estadoInicial.getStacks());
                                                                            
                                                                            do {
                                                                                System.out.println("Selecciona la fila de la que remover palillos.");
                                                                                row = sc.nextInt() -1;
                                                                                
                                                                                if (row < 0 && row > estadoInicial.getStacks().size())
                                                                                    System.out.println("Introduce un dato válido.");
                                                                                else
                                                                                    goon = true;
                                                                            } while (goon == false);
                                                                            
                                                                            do {
                                                                                goon = false;
                                                                                System.out.println("Selecciona cuántos palillos remover.");
                                                                                remove = sc.nextInt();
                                                                                
                                                                                if (remove < 0 && remove > estadoInicial.getStack(row))
                                                                                    System.out.println("Introduce un dato válido.");
                                                                                else
                                                                                    goon = true;
                                                                            } while (goon == false);
                                                                            estadoInicial.setStack(row, estadoInicial.getStack(row) - remove);
                                                                            
                                                                            turnoFinal = new Estado(estadoInicial.getStacks());
                                                                            turnoFinal.setPosition(Positiontype.Ganadora);
			} else {
                                                                             System.out.println(" Jugador 1:");      //Máquina
                                                                            System.out.println("    Palillos:" + estadoInicial.getStacks());
                                                                            if (algorithm == 'B') {                           
                                                                                    listaEstados.add(estadoInicial);
                                                                                    backwardMethod(estadoInicial, listaEstados);
                                                                            }
                                                                            else {
                                                                                    listaEstados.add(new Estado(new ArrayList<Integer>()));
                                                                                    forwardMethod(listaEstados);
                                                                            }
                                                                  }
		//Si no hemos encontrado una solucion
			if (turnoFinal == null) {
				System.out.println("Movimiento aleatorio");
				// Se selecciona la fila con mas palos para quitarle 1, alargando al
				// maximo la partida y esperar error del oponente.
				randomMove(estadoInicial);
			} else {
				//La jugada es ganadora
				System.out.println("Jugada ganadora");
			}
			
			estadoInicial = turnoFinal;
			//Como queda el tablero despu�s de la jugada
			System.out.println("We get in the game : "+estadoInicial);
		}

		String resultado;
		if (turn % 2 == 0) {
			resultado = "Gana el segundo jugador (máquina)";
		} else {
			resultado = "¡Has ganado! (usuario).";
		}
		return resultado;
	}

	//Se utiliza para la situacion aleatoria
	private void randomMove(Estado estadoInicial) {
		ArrayList<Integer> Stacksone = new ArrayList<Integer>(estadoInicial.getStacks());
		//Elimina un palo del mont�n m�s grande 
		int biggerrow = Stacksone.indexOf(Collections.max(estadoInicial.getStacks()));
		Stacksone.set(biggerrow, Stacksone.get(biggerrow) - 1);
		turnoFinal = new Estado(Stacksone);
		turnoFinal.setPosition(Positiontype.Ganadora);
	}


	private void forwardMethod(ArrayList<Estado> Estadolist) {
		int cont = 0;
		while (cont < Estadolist.size() && turnoFinal == null) {
			Estado actualSituation = Estadolist.get(cont);
			
			for (Estado previous : estadosAnterioresPosibles(actualSituation)) {
				//Si encontramos un estado lo cogemos, si no, lo a�adimos a la lista
				if (Estadolist.contains(previous)) {
					previous = Estadolist.get(Estadolist.indexOf(previous));
				} else {
					Estadolist.add(previous);
				}
				// Si la situacion actual es perdedora,
				// convierte a sus anterioras en ganadoras.
				if (actualSituation.getPosition()==Positiontype.Perdedora) {
					previous.setPosition(Positiontype.Ganadora);
					
				// Si la situacion anterior que se hace ganadora es la
				// inicial se selecciona como jugada y se sale del bucle.
					if (previous.equals(estadoInicial)) {
						turnoFinal = actualSituation;
					}
				}
			}
			cont++;
		}
	}

	/**
	 * @param actual
	 * @return
	 * Generacion de anteriores
	 */
	public ArrayList<Estado> estadosAnterioresPosibles(Estado actual) {
		ArrayList<Estado> previousEstados = new ArrayList<Estado>();
		ArrayList<Integer> previousstack;
		// Se a�aden palos a cada fila hasta alcanzar la cantidad
			// que tiene la situacion inicial a la que queremos llegar.
		for (int monton = 0; monton < actual.getStacks().size(); monton++) {
			for (int toothpick = actual.getStacks().get(monton) + 1; toothpick <= Collections.max(estadoInicial.getStacks()); toothpick++) {
				previousstack = new ArrayList<Integer>(actual.getStacks());
				previousstack.set(monton, toothpick);
				Estado previous = new Estado(previousstack);
				// We should control not to get over the amount of the stacks
				if (!previousEstados.contains(previous) && !previous.isOver(estadoInicial)) {
					previous.setPosition(Positiontype.Perdedora);
					previousEstados.add(previous);
				}
			}
		}
		// new stacks of toothpick sticks is created 
		for (int toothpick = 1; toothpick <= Collections.max(estadoInicial.getStacks()); toothpick++) {
			previousstack = new ArrayList<Integer>(actual.getStacks());
			previousstack.add(toothpick);
			Estado anterior = new Estado(previousstack);
			//// We should control not to get over the amount of the stacks
			if (!previousEstados.contains(anterior) && !anterior.isOver(estadoInicial)) {
				anterior.setPosition(Positiontype.Perdedora);
				previousEstados.add(anterior);
			}
		}		

	/*// MOSTRAR LA GENERACION DE ANTERIORES
		System.out.print("Anteriores de ");
		System.out.println(actual);
		for (int i = 0; i < previousEstados.size(); i++) {
			System.out.println(previousEstados.get(i).toString());
		}
		System.out.println("----------------");
*/
		return previousEstados;
	}    

	private void backwardMethod(Estado first, ArrayList<Estado> listaEstados) {
		// para cada uno de los posibles estados
		for (Estado siguiente : EstadosSiguientes(first)) {
			//Cuando encontramos una solucion no entra en el bucle ni genera un estado nuevo.
			if (turnoFinal == null) {
				// Si ya exsiste el estado, se recupera, si no, se a�ade.
				if (listaEstados.contains(siguiente)) {
					siguiente = listaEstados.get(listaEstados.indexOf(siguiente));
				} else {
					listaEstados.add(siguiente);
				}
				// Si la situacion siguiente no esta calculada, se recurre.
				if (siguiente.getPosition()==Positiontype.NoEvaluada){                                      
					backwardMethod(siguiente, listaEstados);
				}
				// Si alguna situacion siguiente es perdedora, la actual se
				// convierte en ganadora.
				// Y si la actual es la inicial, se apunta como jugada final,
				// deteniendo la recursion.
				if (siguiente.getPosition()==Positiontype.Perdedora) {
					first.setPosition(Positiontype.Ganadora);
					if (first.equals(this.estadoInicial)) {
						turnoFinal = siguiente;
					}
				}
				// Si ninguna situacion ha hecho ganadora a la actual, se
				// convierte en perdedora.
				if (first.getPosition()!=Positiontype.Ganadora) {
					first.setPosition(Positiontype.Perdedora);
				}
			}
		}
	}

	private ArrayList<Estado> EstadosSiguientes(Estado actual) {
		// ArrayList Variables
		ArrayList<Estado> estadosSiguientes = new ArrayList<Estado>();
		ArrayList<Integer> siguientestack;
		//Para cada monton de los palos generamos desde 0 hasta el valor m�ximo.
		for (int monton = 0; monton < actual.getStacks().size(); monton++) {
			for (int npalos = 0; npalos < actual.getStacks().get(monton); npalos++) {
				siguientestack = new ArrayList<Integer>(actual.getStacks());
				siguientestack.set(monton, npalos);
				Estado siguiente = new Estado(siguientestack);
				// Controlamos no generar el mismo estado
				if (!estadosSiguientes.contains(siguiente)) {
					estadosSiguientes.add(siguiente);
				}
			}
		}
		
		
/*//		// MOSTRAR LA GENERACION DE SIGUIENTES
	System.out.print("Siguientes de ");
		System.out.println(actual);
		for (int i = 0; i < estadosSiguientes.size(); i++) {
			System.out.println(estadosSiguientes.get(i));
		}
		System.out.println("----------------");

		*/
		
		return estadosSiguientes;
	}

	public boolean isNumeric (String valor) {
		try {
			int numero=Integer.parseInt(valor);
			return true;
		}
		catch (NumberFormatException nfe){
			return false;
		}
	}
}
