
import java.util.HashSet;

/*
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Visit {
    private final int branch;
    private final int duration;
    
    public Visit(int N, int time){
        branch = N;
        duration = time;        
    }
    
    public int getBranchID(){
        return branch;
    }
    
    public int getDuration(){
        return duration;
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
