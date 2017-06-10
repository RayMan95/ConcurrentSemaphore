
import java.util.HashSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ray
 */
public class Visit {
    private int branch;
    private int duration;
    private boolean completed = false;
    
    public Visit(int N, int time){
        branch = N;
        duration = time;        
    }
    
    public void complete(){
        completed = true;
    }
    
    public boolean isComplete(){
        return completed;
    }
    
    @Override
    public String toString(){
        String s = "(";
        s += branch;
        s += ", ";
        s += duration;
        s += ")"; 
        
        return s;
    }
    
}
