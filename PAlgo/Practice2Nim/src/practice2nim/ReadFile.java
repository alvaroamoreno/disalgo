package practice2nim;

import java.io.*;
import java.util.*;

public class ReadFile {
	//FROM STACKOVERFLOW
    public static ArrayList<Integer> leerfichero (String file) throws IOException{
        //Leemos diferentes montones de un fichero de texto
        ArrayList<Integer> stacksoftoothpicks = new ArrayList <Integer>();
        String linea;
        BufferedReader br = new BufferedReader (new FileReader (file));
        linea = br.readLine();
        StringTokenizer st = new StringTokenizer (linea);    
        while (st.hasMoreTokens()) {
            stacksoftoothpicks.add(Integer.parseInt(st.nextToken()));
        }
        br.close();
        return stacksoftoothpicks;
    }  
}
  
 