
import java.util.ArrayList;

/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Branch {
    ArrayList<Person> workers;
    private static int branchID = -1;
    private int myid;
    
    public Branch(){
        myid = ++Branch.branchID;
        workers = new ArrayList<>();
    }
    
    public void add(Person p){
        workers.add(p);
    }
    
    public void add(ArrayList<Person> newWorkers){
        for (Person p : newWorkers) this.add(p);
    }
    
    public boolean remove(Person p){
        return workers.remove(p);
    }
}
