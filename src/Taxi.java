
import java.util.ArrayList;



/**
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Taxi {
    public static boolean headingOut;
    
    private static ArrayList<Person> passengers;
    
    public Taxi(){
        passengers = new ArrayList<>();
    }
    
    public void add(Person p){ passengers.add(p);}
    
    public void add(ArrayList<Person> newPassengers){
        for (Person p : newPassengers) add(p);
    }
    
    public boolean remove(Person p){ return passengers.remove(p);}
    
    public boolean headingOut(){
        return headingOut;
    }
    
    public void changeDirection(){
        headingOut = !headingOut;
    }
}
