
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
        if(args.length < 1) throw new RuntimeException();
        File file = new File(args[0]);
        
        BufferedReader br = new BufferedReader(new FileReader(file));

        
        final int M = Integer.parseInt(br.readLine());
        final int N = Integer.parseInt(br.readLine());
        
        ArrayList<Person> pal = new ArrayList<>();
        
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
            
            pal.add(new Person(adv));
        }
        
        for(Person p : pal){
            System.out.println(p.toString());
        }


        Semaphore semaphore = new Semaphore(M);
//        catch(IOException ioe){
//            
//        }
        
    }
    
}
