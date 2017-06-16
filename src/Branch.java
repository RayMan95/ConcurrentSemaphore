
import java.util.ArrayList;

/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Branch{
    private ArrayList<Person> workers = new ArrayList<>();
    private static int branchID = -1;
    private final int bid;
    
    public Branch(){
        bid = ++Branch.branchID;
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
    
    public ArrayList<Person> getWorkers(){
        return workers;
    }
    
    public int getBID(){
        return bid;
    }
    
    @Override
    public String toString(){
        String s = "BID=" + bid;
        if (!workers.isEmpty()){
            s += "\nWorkers:\n";
            for (Person p : workers){
                s += p.getPID()+",";
            }
        }
        
        return s;
    }
}
