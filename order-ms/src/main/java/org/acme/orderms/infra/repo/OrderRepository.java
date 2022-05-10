package org.acme.orderms.infra.repo;

import java.util.List;

import org.acme.orderms.domain.OrderEntity;

public interface OrderRepository {
    public List<OrderEntity> getAll();
    public void addOrder(OrderEntity entity);
    public void updateOrder(OrderEntity entity);
    public OrderEntity findById(String key);
}
