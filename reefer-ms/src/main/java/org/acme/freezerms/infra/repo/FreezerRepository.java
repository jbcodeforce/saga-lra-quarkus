package org.acme.freezerms.infra.repo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.acme.freezerms.domain.Freezer;


@Singleton
public class FreezerRepository {
    public  static ConcurrentHashMap<String,Freezer> repo = new ConcurrentHashMap<String,Freezer>();
    public  static ConcurrentHashMap<String,List<Freezer>> byLocation = new ConcurrentHashMap<String,List<Freezer>>();
    private ConcurrentHashMap<String, List<Freezer>> currentOrderBacklog = new ConcurrentHashMap<String,List<Freezer>>();
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public FreezerRepository() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("reefers.json");
        if (is == null) 
            throw new IllegalAccessError("file not found for reefer json");
        try {
            List<Freezer> reefers = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, Freezer.class));
            reefers.stream().forEach( (t) -> { 
                    repo.put(t.reeferID,t);
                    if (t.location != null) {
                        if (byLocation.get(t.location) == null) 
                            byLocation.put(t.location, new ArrayList<Freezer>());
                        byLocation.get(t.location).add(t);
                    }
                    
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public List<Freezer> getAll(){
        return new ArrayList<Freezer>(repo.values());
    }

    public void addReefer(Freezer p) {
        repo.put(p.reeferID, p);
    }

    public Freezer getById(String key){
        return repo.get(key);
    }

    /**
     * Search reefers free in the pickup location that has support capacity
     * @param puck up location
     * @param expectedCapacity
     * @return list of freezers support this expected catacity and at the expected location
     */
    public  List<Freezer>  assignFreezerForALocation(String transactionID,String pickupLocation,long expectedCapacity) {
        List<Freezer> potentials = new ArrayList<Freezer>();
        if (pickupLocation == null) return potentials;
        if (byLocation.get(pickupLocation) != null) {
            currentOrderBacklog.put(transactionID, potentials);
            for (Freezer reefer : byLocation.get(pickupLocation)) {
                if (reefer.status.equals(Freezer.FREE)) {
                   
                    reefer.status = Freezer.ALLOCATED;
                    potentials.add(reefer);
                    if (expectedCapacity > reefer.capacity) {
                        reefer.currentFreeCapacity = 0;
                        expectedCapacity -= reefer.capacity;
                    } else {
                        reefer.currentFreeCapacity = reefer.capacity - expectedCapacity;
                        return potentials;
                    }
                }
            } 
        } 
        // this is possible to still have capacity to address... but makes it simple
        return potentials;
    }

    public List<Freezer> getFreezersForTransaction(String transactionID) {
        return currentOrderBacklog.get(transactionID);
    }

    public Freezer updateFreezer(Freezer f) {
        repo.put(f.reeferID,f);
        return f;
    }

    public void cleanTransaction(String transactionID) {
        this.currentOrderBacklog.remove(transactionID);
    }
}
