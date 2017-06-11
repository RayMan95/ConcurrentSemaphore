
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Branch{
    ArrayList<Person> workers;
    private static int branchID = -1;
    private final int myid;
    
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
    
    public int getBID(){
        return myid;
    }
    
    public static Comparator<Branch> inwardComparator(){
        return new Comparator<Branch>(){
            @Override
            public int compare(Branch b1, Branch b2){
                if(b1.getBID() == b2.getBID()) return 0;
                else return b1.getBID() < b2.getBID() ? 1:-1; 
            };
        };
    }
    
    public static Comparator<Branch> outwardComparator(){
        return new Comparator<Branch>(){
            @Override
            public int compare(Branch b1, Branch b2){
                if(b1.getBID() == b2.getBID()) return 0;
                else return b1.getBID() > b2.getBID() ? 1:-1; 
            };
        };
    }
    
    @Override
    public String toString(){
        String s = "BID=" + myid;
        if (!workers.isEmpty()){
            s += "\nWorkers:\n";
            for (Person p : workers){
                s += p.getPID()+",";
            }
        }
        
        return s;
    }
}
