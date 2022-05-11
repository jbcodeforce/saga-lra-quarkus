package org.acme.voyagems.infra.api.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Data Transfert Object used to do not expose the Entity as-is.
 */
@RegisterForReflection
public class OrderDTO {
	public String orderID;
	public String productID;
	public int quantity;
	public String destinationCity;
	public String pickupCity;
	public String containerIDs;
	public String creationDate;
	
	
	public OrderDTO() {
		// needed for jaxrs serialization
	}
	 
	public OrderDTO( String productID, int quantity, String destinationCity, String pickupCity) {
		super();
		this.productID = productID;
		this.quantity = quantity;
		this.destinationCity = destinationCity;
		this.pickupCity = pickupCity;
	}
	
	public String toString(){
		return "OrderDTO: { orderid: " + this.orderID + ", origin city: " + this.pickupCity + ", target city: " + this.destinationCity + ", product: " + this.productID + "}";
	}

	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCreationDate(){
		return creationDate;
	}
}
