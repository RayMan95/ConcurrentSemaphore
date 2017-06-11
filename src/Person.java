
import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Person extends Thread{
    
    public static Taxi TAXI;
    static int persid = -1;
    private final ArrayDeque<Visit> visits;
    private final int pid;
    private final Semaphore SEM;
    
    private int currentBranchID;
    
    public Person(ArrayDeque<Visit> adv, Semaphore sem){
        pid = ++Person.persid;
        visits = adv;
        SEM = sem;
        currentBranchID = 0;
    }
    
    public boolean isDone(){
        return visits.isEmpty();
    }
    
    public Semaphore getSemaphore(){
        return SEM;
    }
    
    public int getPID(){
        return pid;
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
    
    public boolean disembark(int bid){
        if(visits.peekFirst().getBranchID() == bid){
            // remove branch from visits and set new currentBranchID
            currentBranchID = visits.pop().getBranchID();
            return TAXI.disembark(this);
        }
        else return false;
    }
    
    public void embark(){
//        System.out.println("P: Embaking");
        TAXI.embark(this);
        
    }
    
    public void block() throws InterruptedException{
        System.out.println("blocking");
        SEM.acquire();
    }
    
    @Override
    public void run(){
        try {
            block();
            System.out.println("not blocked");
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
