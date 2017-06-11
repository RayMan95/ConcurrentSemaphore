
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Taxi extends Thread{
    public int headingOut;
    private final ArrayList<Branch> stops;
    private final Branch[] branches;
    private int currentBranchID;
    private HashMap<Integer, Semaphore> passengerSemaphores;
    
    private final ArrayList<Person> passengers = new ArrayList<>();
    
    
    public Taxi(Branch[] b){
        stops = new ArrayList<>();
        branches = b;
    }
    
    public void embark(Person p){ 
        passengers.add(p); 
        System.out.println("Embarking: pid=" + p.getPID());
        passengerSemaphores.put(p.getPID(), p.getSemaphore());
    }
    
//    public static void add(ArrayList<Person> newPassengers){
//        for (Person p : newPassengers) Taxi.add(p);
//    }
    
    public boolean disembark(Person p){ return passengers.remove(p);}
    
//    public static void setSemaphore(Semaphore sem){ Taxi.sendingSemaphore = sem;}
    
    public int headingOut(){
        return headingOut;
    }
    
    public void changeDirection(){
        headingOut *= -1;
        System.out.println("Changing direction");
    }
    
    public void hail(int bid){
//        if ()
        // TODO: check direction
        stops.add(branches[bid]);
        System.out.println("Hailed at branchID=" + bid);
    }
    
    @Override
    public void run(){
        try {
            sleep(2000);
//            release();
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String toString(){
        String s = "Taxi:\n";
        for(Person p :passengers){
            s += p.toString();
            s += "\n";
        }
        
        return s;
    }
}
