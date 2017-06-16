
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
    private final Semaphore MYSEM;
    public static Trace TRACE;
    
    private int currentBranchID;
    
    public Person(ArrayDeque<Visit> adv, Semaphore sem){
        pid = ++Person.persid;
        visits = adv;
        MYSEM = sem;
        currentBranchID = 0;
    }
    
    public boolean isDone(){
        return visits.isEmpty();
    }
    
    public Semaphore getSemaphore(){
        return MYSEM;
    }
    
    public int getPID(){
        return pid;
    }
    
    public boolean stopHere(int bid){
        return visits.peekFirst().getBranchID() == bid;
    }
    
    public int nextStopID(){
        return visits.peek().getBranchID();
    }
    
    public void block() throws InterruptedException{
//        System.out.println("pid=" + pid + " blocking...");
        MYSEM.acquire();
    }
    
    public void work() throws InterruptedException{
        
        Visit v = visits.pop();
        currentBranchID = v.getBranchID();
//        System.out.println("pid="+pid + " working at BID=" + currentBranchID);
        sleep(33*v.getDuration());
    }
    
    @Override
    public void run(){
        try {
            block(); // initially block at HQ
            while(true){
                synchronized (TAXI){
                    TAXI.request(currentBranchID, this.nextStopID(), pid);
                }
                block();
                
                work();
                if(isDone()) break;
                synchronized (TAXI){
                    TAXI.hail(currentBranchID, this); //
                    TRACE.logHail(currentBranchID, pid, System.currentTimeMillis());
                }
                
                block();
            }
//            System.out.println("pid=" + pid + " done working");
            
            synchronized (Person.class){
                Taxi.stillWorking = --Taxi.stillWorking;
            }
//            System.out.println("Still working: " + Taxi.stillWorking);
        } 
        catch (InterruptedException iex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, iex);
        }
        
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
