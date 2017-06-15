/**
 * Trace class
 * 
 * @author Rayaan Fakier
 * @version 2017-06-15
 */
public class Trace {
//    private String s;
    private final long startTime;
    
    
    public Trace(long currTime){
        startTime = currTime;
    }
    
    private String formatTime(long time){
        String temp = "";
//        int t = (int)(System.currentTimeMillis() - startTime);
        long diff = time - startTime;
        int min = 0, hr = 9;
        while( diff > 33){
            diff -= 33;
            min += 1;
            if(min > 59){ 
                hr += 1;
                min = 0;
            }
        }
        if (diff > 16) ++min;
        
        String minS = min+"";
        if (min < 10) minS = "0"+minS;
        if (hr < 10) temp += "0";
        
        temp += hr +":" + minS;
        return temp;
    }
    
    public void logHail(int bid, int pid, long time){
        String s = "";
        s += formatTime(time);
        s += " branch " + bid + ": person " + pid + " hail";
        System.out.println(s);
    }
    
    public void logTaxi(boolean arriving, int bid, long time){
        String s = "";
        s += formatTime(time);
        s += " branch " + bid + ": taxi ";
        if(arriving) s += "arrive";
        else s += "depart";
        
        System.out.println(s);
    }
}
