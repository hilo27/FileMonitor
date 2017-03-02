
package javaapplication22;

import java.nio.file.*;
import java.util.*;
import java.io.*;

public class FileMonitor implements AutoCloseable{
    
    private Path path;
    private List<FileMonitorClient> clients;
    private boolean can_work = true;
    
    public FileMonitor(String dirName) throws FileNotFoundException {
        path = Paths.get(dirName);
        if(!Files.exists(path)) throw new FileNotFoundException(dirName + " - no such directiry");
        
        clients = new ArrayList<>();        
    }

    public void switchOff(boolean can_work) {
        this.can_work = false;
    }
      
    public boolean addClient(FileMonitorClient client){
        if(client != null){            
            return clients.add(client);
        }
        else return false; //можно не писать
    }
    
    public boolean removeClient(FileMonitorClient client) {
        if(client != null){
            return clients.remove(client);
        } 
        else return false; //можно не писать
    }
    
    protected void fireEvent(String fname, int evType) {
        // лямбда выражение
        clients.stream().forEach((client) -> {
            client.fileEvent(evType, fname);
        });
    }
    
    public void spyOnDirectory() throws IOException, InterruptedException{
        WatchService watch = FileSystems.getDefault().newWatchService();
        path.register(watch, StandardWatchEventKinds.ENTRY_CREATE, 
                             StandardWatchEventKinds.ENTRY_DELETE);
        
        while(can_work) {            
            WatchKey key = watch.take();
            for(WatchEvent event : key.pollEvents()) 
                if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    fireEvent(event.context().toString(), FileMonitorClient.ADD);
                }
                else if (event.kind()== StandardWatchEventKinds.ENTRY_DELETE) {
                    fireEvent(event.context().toString(), FileMonitorClient.DEL);
                }
            
            key.reset();
        }
        
        watch.close();
        
    }

    @Override
    public void close() throws Exception {
      clients.clear();
        
    }
      
}
