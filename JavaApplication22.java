
package javaapplication22;

import java.io.*;

public class JavaApplication22 {

    public static void main(String[] args) {
        // TODO code application logic here
        try(FileMonitor server = new FileMonitor(".")){
            server.addClient(new Client());
            
            server.addClient((x,s)->{
                System.out.println(s+" "+x);
                        return x;
             });
            
            server.spyOnDirectory();
        }
        catch(FileNotFoundException ex) {
            System.out.println(ex.getMessage());            
        }
        catch(IOException ex) {
            System.out.println("File system watch error");
        }
        catch(InterruptedException ex){
            System.out.println("Thread malfunction");
        }
        catch(Exception ex){
            System.out.println("FATAL ERROR");
        }
    }
    
}
