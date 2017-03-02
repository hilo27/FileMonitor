
package javaapplication22;

public interface FileMonitorClient {
    
    public final static int ADD =1;
    public final static int DEL =0;
    
    public int fileEvent(int eventType, String fname);
    
}
