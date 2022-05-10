package org.acme.orderms.infra.remote;

import org.acme.orderms.domain.OrderEntity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderForReefer {
    public String orderID;
	public String productID;
	public int quantity;
	public String destinationCity;
	public String creationDate;

    public OrderForReefer() {
    }

    public static OrderForReefer fromOrder(OrderEntity oe) {
        OrderForReefer ofr = new OrderForReefer();
        ofr.orderID = oe.orderID;
        ofr.productID = oe.productID;
        ofr.quantity= oe.quantity;
        ofr.destinationCity = oe.getDeliveryAddress().getCity();
        ofr.creationDate = oe.creationDate;
        return ofr;
    }
    
}
