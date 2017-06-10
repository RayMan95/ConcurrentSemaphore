
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        if(args.length < 2) throw new RuntimeException();
        File file = new File(args[1]);
        
        BufferedReader br = new BufferedReader(new FileReader(file));

        
        final int M = Integer.parseInt(br.readLine());
        final int N = Integer.parseInt(br.readLine());
        
        
        
        String line = "";
        for(int i = 0; i < M; ++i){
            line = br.readLine();
            String[] parts = line.split(" ");
//            for (int j = 1; j < parts.length;)
        }


        Semaphore semaphore = new Semaphore(M);
//        catch(IOException ioe){
//            
//        }
        
    }
    
}
