
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Taxi extends Thread{
    public int direction;
    private final PriorityQueue<Branch>  inStops;
    private final PriorityQueue<Branch> outStops;
    private final Branch[] branches;
    private int currentBranchID;
    private HashMap<Person, Semaphore> passengerSemaphores;
    
    private final ArrayList<Person> passengers = new ArrayList<>();
    
    
    public Taxi(Branch[] b, ArrayList<Person> ppl){
        inStops = new PriorityQueue<>(Branch.inwardComparator());
        outStops = new PriorityQueue<>(Branch.inwardComparator());
        branches = b;
        passengerSemaphores = new HashMap<>();
        direction = 1;
        for (Person p : ppl) passengerSemaphores.put(p, p.getSemaphore());
    }
    
    public void embark(Person p){ 
        passengers.add(p);
        System.out.println("Embarking: pid=" + p.getPID());
        passengerSemaphores.put(p, p.getSemaphore());
    }
    
    
    public boolean disembark(Person p){ return passengers.remove(p);}
    
//    public static void setSemaphore(Semaphore sem){ Taxi.sendingSemaphore = sem;}
    
    public int headingOut(){
        return direction;
    }
    
    public void changeDirection(){
        direction *= -1;
    }
    
    public void hail(int bid){
        
        inStops.add(branches[bid]);
        outStops.add(branches[bid]);
        System.out.println("Hailed at branchID=" + bid);
    }
    
    @Override
    public void run(){
        try {
            sleep(2000);
//            Semaphore s = passengerSemaphores.get(passengerSemaphores.keySet().iterator().next());
//            s.release();
            int i = 0;
            while(i < 10){
//                System.out.println("At branch: " + branches[currentBranchID]);
                
                currentBranchID += direction;
                if((currentBranchID == (branches.length-1)) || (currentBranchID == 0))
                    changeDirection(); // when hitting bounds
                
                if(currentBranchID == 4) {
                    passengerSemaphores.get(passengerSemaphores.keySet().iterator().next()).release();
                    System.out.println("pid="+passengerSemaphores.keySet().iterator().next().getPID() + " released");
                }
                if(direction > 0){ // heading outward
                    
                }
                else{ // heading inward
                    
                }
                ++i;
            }
            
        } 
        catch (InterruptedException iex) {
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, iex);
        }
    }
    
    @Override
    public String toString(){
        String s = "Taxi:";
        for(Person p :passengers){
            s += "\n" + p.toString();
        }
        
        return s;
    }
}
