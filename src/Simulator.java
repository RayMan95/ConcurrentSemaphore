
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


/**
 * Main class for project
 *
 * @author Rayaan Fakier FKRRAY001
 * @version 2017-06-10
 */
public class Simulator {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if(args.length < 1) throw new IllegalArgumentException();
        File file = new File(args[0]);
//        file = new File("busy.txt"); // TODO: remove
        file = new File("not_as_busy.txt"); // TODO: remove
//        file = new File("idle.txt"); // TODO: remove
        
        BufferedReader br = new BufferedReader(new FileReader(file));

        
        final int M = Integer.parseInt(br.readLine());
        final int N = Integer.parseInt(br.readLine());
        
        ArrayList<Person> pal = new ArrayList<>();
        
        
//        Person.receivingSemaphore = SEM;
        
        String line = "";
        for(int i = 0; i < M; ++i){
            line = br.readLine();
            String[] parts = line.split(" \\(");
            ArrayDeque<Visit> adv = new ArrayDeque<>();
            for (int j = 1; j < parts.length; ++j){
                
                int b = Integer.parseInt(parts[j].split(",")[0]);
//                System.out.println(b);
                int d = Integer.parseInt(parts[j].split(",")[1].substring(1, parts[j].split(",")[1].lastIndexOf(")")));
//                System.out.println(d);
                
                adv.add(new Visit(b,d));
            }
            Semaphore SEM = new Semaphore(0); // semaphores of bound 1 for blocking
            pal.add(new Person(adv,SEM));
        }
        
        Branch[] branches = new Branch[N];
        for(int i = 0; i < N; ++i){
            branches[i] = new Branch();
        }
        branches[0].add(pal); // add all workers intially to HQ
//        for(Person p : pal){
//            System.out.println(p.toString());
//        }
        
        Trace TRACE = new Trace(System.currentTimeMillis());
        Person.TRACE = TRACE;

        final Taxi t = new Taxi(branches, pal, TRACE);
        Person.TAXI = t;
        Taxi.stillWorking = pal.size(); // uncomment
//        Taxi.stillWorking = 1;
        t.start();
        for(Person p : pal) p.start(); // uncomment
//        pal.get(0).start();
//        pal.get(1).start();
//        pal.get(2).start();
        
        
        
        
//        System.out.println(t);
        
//        catch(IOException ioe){
//            
//        }
        
    }
    
}
