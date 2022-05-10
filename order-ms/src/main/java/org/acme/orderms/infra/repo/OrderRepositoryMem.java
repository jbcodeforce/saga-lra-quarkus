package org.acme.orderms.infra.repo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.acme.orderms.domain.OrderEntity;

@Singleton
public class OrderRepositoryMem implements OrderRepository {
    private static ConcurrentHashMap<String,OrderEntity> repo = new ConcurrentHashMap<String,OrderEntity>();

    private static ObjectMapper mapper = new ObjectMapper();
    

    public OrderRepositoryMem() {
        super();
        InputStream is = getClass().getClassLoader().getResourceAsStream("orders.json");
        if (is == null) 
            throw new IllegalAccessError("file not found for order json");
        try {
            List<OrderEntity> currentDefinitions = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, OrderEntity.class));
            currentDefinitions.stream().forEach( (t) -> repo.put(t.getOrderID(),t));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repo.values().stream().forEach(v -> System.out.println(v.orderID));
    }

    public List<OrderEntity> getAll(){
        return new ArrayList<OrderEntity>(repo.values());
    }

    public void addOrder(OrderEntity entity) {
        repo.put(entity.getOrderID(), entity);
    }

    public void updateOrder(OrderEntity entity) {
        repo.put(entity.getOrderID(), entity);
    }

    @Override
    public OrderEntity findById(String key) {
        return repo.get(key);
    }
}
