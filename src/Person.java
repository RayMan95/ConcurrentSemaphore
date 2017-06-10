
import java.util.ArrayDeque;

/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Person {
    
    static int persid = -1;
    private ArrayDeque<Visit> visits;
    private int pid;
    
    public Person( ArrayDeque<Visit> adv){
        pid = ++Person.persid;
        visits = adv;
    }
    
    @Override
    public String toString(){
        String s = "pid=";
        s += pid;
        s +=" visits:";
        for (Visit v : visits){
            s += v;
        }
        
        return s;
    }
}
