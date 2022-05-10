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
    private static ConcurrentHashMap<String,Freezer> repo = new ConcurrentHashMap<String,Freezer>();
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    public FreezerRepository() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("reefers.json");
        if (is == null) 
            throw new IllegalAccessError("file not found for reefer json");
        try {
            List<Freezer> reefers = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, Freezer.class));
            reefers.stream().forEach( (t) -> repo.put(t.reeferID,t));
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
}
