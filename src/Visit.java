
import java.util.HashSet;

/*
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Visit {
    private final int branch;
    private final int duration;
    private boolean completed = false;
    
    public Visit(int N, int time){
        branch = N;
        duration = time;        
    }
    
    public void complete(){
        completed = true;
    }
    
    public boolean isComplete(){
        return completed;
    }
    
    @Override
    public String toString(){
        String s = "(";
        s += branch;
        s += ", ";
        s += duration;
        s += ")"; 
        
        return s;
    }
    
}
