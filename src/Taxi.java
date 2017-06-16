
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Taxi extends Thread{
    public int direction;
    private final PriorityQueue<Integer> stops;
    private final Branch[] branches;
    private int currentBranchID;
    private final Trace TRACE;
    
    public static volatile int stillWorking = 0;
    
    private final HashMap<Integer, HashSet<Integer>> branchHaileeIDMap = new HashMap<>(); // branch : PIDs
    private final ArrayList<Person> passengers = new ArrayList<>();
    
    
    public Taxi(Branch[] b, ArrayList<Person> ppl, Trace t){
        stops = new PriorityQueue<>();
        stops.add(0); // stop at HQ first
        branches = b;
        direction = 1;
        
        setupBranchHailees(ppl);
        
        TRACE = t;
    }
    
    private void setupBranchHailees(ArrayList<Person> ppl){
        HashSet<Integer> pids = new HashSet<>();
        for (Person p : ppl) // for HQ
            pids.add(p.getPID());
        branchHaileeIDMap.put(0, pids);
    }
    
    public boolean disembark(Person p){ return passengers.remove(p);}
    
    public boolean headingOut(){
        return direction == 1;
    }
    
    public void changeDirection(){
//        System.out.println("Changing direction");
        direction *= -1;
    }
    
    synchronized public void hail(int bid, Person p) throws InterruptedException{
        if(!stops.contains(bid)){
            // Add to stops
            stops.add(bid);
        }
        addHailee(bid, p.getPID());
//        System.out.println("Hailed by pid=" + p.getPID() +" at BID=" + bid);
    }
    
    private void addHailee(int bid, int pid){
        HashSet<Integer> haileeIDs = branchHaileeIDMap.get(bid);
        if(haileeIDs != null){
            haileeIDs.add(pid);
            branchHaileeIDMap.replace(bid, haileeIDs);
        }
        else{
            haileeIDs = new HashSet<>();
            haileeIDs.add(pid);
            branchHaileeIDMap.put(bid, haileeIDs);
        }
    }
    
    synchronized public void request(int at, int to, int pid){
        TRACE.logRequest(at, to, pid, System.currentTimeMillis());
        if(!stops.contains(to))
            stops.add(to);
    }
    
    @Override
    public void run(){
        try {
//            sleep(50);
            
            while(Taxi.stillWorking > 0){
                boolean stopping = false;
                
                Branch currentBranch = branches[currentBranchID];
                
                if(stops.contains(currentBranchID)) stopping = true;
                if(!stopping){
                    nextBranch();
                    continue;
                }
                // only log arrive when stopped there
                TRACE.logTaxi(true, currentBranchID, System.currentTimeMillis());
//                System.out.println("At branch: " + currentBranch);
                sleep(33); // wait 1 minute
                synchronized (this){
                    ArrayList<Person> disembarkList = new ArrayList<>();
                    if(!passengers.isEmpty()){
                        for(Iterator<Person> it = passengers.iterator(); it.hasNext(); ){ // disembarking
                            Person p = it.next();
                            if(p.stopHere(currentBranchID)){
//                                System.out.println("Releasing pid=" + p.getPID());
//                                System.out.println("Dropping off pid="+p.getPID());
                                p.getSemaphore().release();
                                
                                disembarkList.add(p);
                            }
                        }
                        currentBranch.getWorkers().addAll(disembarkList);
                        passengers.removeAll(disembarkList);
                    }
                
                
                    
                    if(branchHaileeIDMap.containsKey(currentBranchID)){ // current branch has hailees
                        ArrayList<Person> embarkList = new ArrayList<>();
                        
                        for (Iterator<Person> it = currentBranch.getWorkers().iterator(); it.hasNext(); ){ // embarking
                            Person p = it.next();
                            if(branchHaileeIDMap.get(currentBranchID).contains(p.getPID())){ // if Person's ID in branchHaileeIDMap
//                                System.out.println("Releasing pid=" + p.getPID());
                                p.getSemaphore().release();
//                                System.out.println("Picking up pid="+p.getPID() + " at BID=" + currentBranchID);
                                embarkList.add(p);
                            }
                        }
                        
                        currentBranch.getWorkers().removeAll(embarkList);
                        passengers.addAll(embarkList);
                        branchHaileeIDMap.remove(currentBranchID); // remove branch from hailees
                    }
                    stops.remove(currentBranchID); // remove branch from stops
                }
                
                boolean logged = false;
                while(branchHaileeIDMap.isEmpty() && passengers.isEmpty() && Taxi.stillWorking > 0){
                    if(!logged){ 
                        TRACE.logIdle(currentBranchID, System.currentTimeMillis());
                        logged = true;
                    }
                    sleep(1); // Taxi idle
                }
                // only log depart when stopped there
                TRACE.logTaxi(false, currentBranchID, System.currentTimeMillis()); 
                nextBranch(); // sleeps
            }
        }
        catch(Exception e){
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void nextBranch() throws InterruptedException{
        
        currentBranchID += direction;
        if((currentBranchID == (branches.length-1)) || (currentBranchID == 0))
            changeDirection(); // when hitting bounds
         sleep(66); // 2 min travel time
    }
    
    @Override
    public String toString(){
        String s = "Taxi at BID="+branches[currentBranchID].getBID()+ "\n";
        for(Person p :passengers){
           s += "pid=" + p.getPID() + ",";
        }
        
        return s;
    }
}
