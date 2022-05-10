package org.acme.freezerms.infra.api.dto;

/**
 * Data Transfert Object used to do not expose the Entity as-is.
 */
public class OrderDTO {
	public String orderID;
	public String productID;
	public int quantity;
	public String destinationCity;
	public String creationDate;
	
	
	public OrderDTO() {
		// needed for jaxrs serialization
	}
	 
	public OrderDTO( String productID, int quantity, String destinationCity) {
		super();
		this.productID = productID;
		this.quantity = quantity;
		this.destinationCity = destinationCity;
	}
	
	public String toString(){
		return "OrderDTO: { orderid: " + this.orderID + ", city: " + this.destinationCity + ", product: " + this.productID + "}";
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
