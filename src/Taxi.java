
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
    
    public static volatile int stillWorking = 0;
    
    private final HashMap<Integer, HashSet<Integer>> branchHaileeIDMap = new HashMap<>(); // branch : PIDs
    private final ArrayList<Person> passengers = new ArrayList<>();
    
    
    public Taxi(Branch[] b, ArrayList<Person> ppl){
        inStops = new PriorityQueue<>(Branch.inwardComparator());
        outStops = new PriorityQueue<>(Branch.inwardComparator());
        branches = b;
        passengerSemaphores = new HashMap<>();
        direction = 1;
        
        for (Person p : ppl) passengerSemaphores.put(p, p.getSemaphore());
        
        setupBranchHailees(ppl);
    }
    
    private void setupBranchHailees(ArrayList<Person> ppl){
        HashSet<Integer> pids = new HashSet<>();
        for (Person p : ppl) // for HQ
            pids.add(p.getPID());
        branchHaileeIDMap.put(0, pids);
    }
    public void embark(Person p) throws InterruptedException{ 
//        passengers.add(p);
        p.block();
        System.out.println("Embarking: pid=" + p.getPID());
    }
    
    
    public boolean disembark(Person p){ return passengers.remove(p);}
    
//    public static void setSemaphore(Semaphore sem){ Taxi.sendingSemaphore = sem;}
    
    public boolean headingOut(){
        return direction == 1;
    }
    
    public void changeDirection(){
//        System.out.println("Changing direction");
        direction *= -1;
    }
    
    synchronized public void hail(int bid, Person p) throws InterruptedException{
//        if(!inStops.contains(branches[bid])){
//            // Add to stops
//            inStops.add(branches[bid]);
//            outStops.add(branches[bid]);
//        }
        addHailee(bid, p.getPID());
//        System.out.println("Hailed by pid=" + p.getPID() +" at BID=" + bid);
//        p.block();
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
    
    private void removeHailee(int bid, int pid){
        HashSet<Integer> haileeIDs = branchHaileeIDMap.get(bid);
        haileeIDs.remove(pid);
        branchHaileeIDMap.replace(bid, haileeIDs);
    }
    
    @Override
    public void run(){
        try {
            sleep(50);
//            Semaphore s = passengerSemaphores.get(passengerSemaphores.keySet().iterator().next());
//            s.release();
//            int i = 0;
            while(Taxi.stillWorking > 0){
                
//            while(i < 30){$
//                boolean stopping = false;
//                System.out.println(this);
                
                Branch currentBranch = branches[currentBranchID];
//                if(direction > 0){ // heading outward
//                    if(outStops.contains(currentBranch)) stopping = true;
//                }
//                else{ // heading inward
//                    if(inStops.contains(currentBranch)) stopping = true;
//                }
//                if(!stopping) continue;
//                System.out.println("At branch: " + currentBranch);
                
                synchronized (this){
                    ArrayList<Person> disembarkList = new ArrayList<>();
                    if(!passengers.isEmpty()){
                        for(Iterator<Person> it = passengers.iterator(); it.hasNext(); ){ // disembarking
                            Person p = it.next();
                            if(p.stopHere(currentBranchID)){
//                                System.out.println("Releasing pid=" + p.getPID());
                                p.getSemaphore().release();
                                
                                disembarkList.add(p);
    //                            p.disembark();
//                                System.out.println("Dropping off pid=" + p.getPID() + " at BID=" + currentBranchID);
    //                            it.remove();
//                                p.work();
                            }
                        }
                        currentBranch.getWorkers().addAll(disembarkList);
                        passengers.removeAll(disembarkList);
                    }
                
                
                    
                    if(branchHaileeIDMap.containsKey(currentBranchID)){ // current branch has hailees
                        ArrayList<Person> embarkList = new ArrayList<>();
//                        ArrayList<Integer> embarkIDList = new ArrayList<>();
                        
                        for (Iterator<Person> it = currentBranch.getWorkers().iterator(); it.hasNext(); ){ // embarking
                            Person p = it.next();
                            if(branchHaileeIDMap.get(currentBranchID).contains(p.getPID())){ // if Person's ID in branchHaileeIDMap
//                                System.out.println("Releasing pid=" + p.getPID());
                                p.getSemaphore().release();
//                                System.out.println("Picking up pid="+p.getPID() + " at BID=" + currentBranchID);
                                embarkList.add(p);
//                                embarkIDList.add(p.getPID());
    //                            it.remove();
    //                            currentBranch.remove(p);
    //                            p.getSemaphore().acquire();
    //                            p.embark();
                            }
                        }
                        
                        currentBranch.getWorkers().removeAll(embarkList);
                        passengers.addAll(embarkList);
                        branchHaileeIDMap.remove(currentBranchID); // remove branch from stops
                    }
                }
                    
//                System.out.println(currentBranch);
//                for(Iterator<Person> it = passengerSemaphores.keySet().iterator(); it.hasNext();){
//                    Person p = it.next();
//                    this.addHailee(0, p.getPID());
//                }
                while(branchHaileeIDMap.isEmpty() && passengers.isEmpty() && Taxi.stillWorking > 0){
                    sleep(1); // Taxi idle
                }
                
                currentBranchID += direction;
                if((currentBranchID == (branches.length-1)) || (currentBranchID == 0))
                    changeDirection(); // when hitting bounds
                
//                ++i;           
            }
        }
        catch(Exception e){
            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, e);
        }
//        catch (InterruptedException iex) {
//            Logger.getLogger(Taxi.class.getName()).log(Level.SEVERE, null, iex);
//        }
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
