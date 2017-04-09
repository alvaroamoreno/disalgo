package practice2nim;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @authors Carlos Cordoba - Alvaro Moreno 
 */

public class Estado implements Interfacetypes {
    private Positiontype position;
    private ArrayList<Integer> stacks;    
    
    public Estado (ArrayList<Integer> stacks) {
        //We inicialize attributes
        this.stacks=new ArrayList<Integer>();
            position=Positiontype.NoEvaluada;
            
            for (int i=0;i<stacks.size();i++) {
                if (stacks.get(i).intValue()!=0)
                    this.stacks.add(stacks.get(i));
            }  
            //If the stack is empty we add a 0 
        if (this.stacks.isEmpty() || stacks.isEmpty()) {
            this.stacks.add(0);
            position=Positiontype.Perdedora;
        }        
        
        Collections.sort(getStacks());
    }
    public Positiontype getPosition() {
        return position;
    }
    public void setPosition(Positiontype position) {
        this.position = position;
    }    
    public ArrayList<Integer> getStacks() {
        return this.stacks;
    }  
    
    public void setStack (int pos, int n) {
        this.stacks.set(pos,n);
    }
    
    public int getStack (int pos) {
        return this.stacks.get(pos);
    }
    
    public int amountoftoothpicksticks() {
        int amount=0;
        for (int i=0;i<getStacks().size();i++) 
            amount+=getStacks().get(i);
        return amount;
    }
    public boolean isOver(Estado maximum) {        
        return ((amountoftoothpicksticks()>maximum.amountoftoothpicksticks()) 
                || (getStacks().size()>maximum.getStacks().size()));        
    }       
    @Override
    public boolean equals(Object newEstado) {
            Estado aux = (Estado) newEstado;
            return getStacks().equals(aux.getStacks());
    }

    @Override
    public String toString() {
            return getStacks().toString() +" "+getPosition();
    }
}
