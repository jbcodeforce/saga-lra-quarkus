package org.acme.orderms.infra.remote;

import org.acme.orderms.domain.OrderEntity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderForReefer {
    public String orderID;
	public String productID;
	public int quantity;
	public String destinationCity;
    public String pickupCity;
	public String containerIDs;
    public String voyageID;
	public String creationDate;


    public OrderForReefer() {
    }

    public static OrderForReefer fromOrder(OrderEntity oe) {
        OrderForReefer ofr = new OrderForReefer();
        ofr.orderID = oe.orderID;
        ofr.productID = oe.productID;
        ofr.quantity= oe.quantity;
        ofr.pickupCity = oe.pickupCity;
        ofr.containerIDs = oe.containerIDs;
        ofr.destinationCity = oe.getDeliveryAddress().getCity();
        ofr.creationDate = oe.creationDate;
        return ofr;
    }

    public String toString(){
		return "OrderDTO: { orderid: " + this.orderID + ", origin city: " + this.pickupCity + ", target city: " + this.destinationCity + ", containers: " + this.containerIDs + "}";
	}
    
}
