
import java.util.ArrayDeque;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ray
 */
public class Person {
    
    static int persid = 0;
    private ArrayDeque<Visit> visits;
    private int pid;
    
    public Person(int id, ArrayDeque<Visit> adv){
        pid = id;
        visits = adv;
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
