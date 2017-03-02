
package javaapplication22;

public class Client implements FileMonitorClient{

    @Override
    public int fileEvent(int eventType, String fname) {
        
        if(eventType == ADD) System.out.println(fname +" added to directory");
        else if(eventType == DEL) System.out.println(fname +" deleted from directory");
        
        return eventType;
    }
        
}
