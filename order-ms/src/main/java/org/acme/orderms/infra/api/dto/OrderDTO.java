package org.acme.orderms.infra.api.dto;

import java.util.UUID;

import org.acme.orderms.domain.Address;
import org.acme.orderms.domain.OrderEntity;


/**
 * Data Transfert Object used to do not expose the Entity as-is.
 */
public class OrderDTO {
	public String orderID;
	public String customerID;
	public String productID;
	public int quantity;
	public String pickupCity;
	public String containerIDs;
	public String voyageID;
	public Address destinationAddress;
	public String creationDate;
	
	
	public OrderDTO() {
		// needed for jaxrs serialization
	}
	 
	public OrderDTO(String customerID, String productID, int quantity, String pickupCity, Address destinationAddress) {
		super();
		this.customerID = customerID;
		this.productID = productID;
		this.quantity = quantity;
		this.pickupCity = pickupCity;
		this.destinationAddress = destinationAddress;
	}
	
	public static OrderDTO fromEntity(OrderEntity order){
		OrderDTO dto = new OrderDTO();
		dto.orderID = order.orderID;
		dto.customerID = order.customerID;
		dto.productID = order.productID;
		dto.destinationAddress = order.deliveryAddress;
		dto.quantity = order.quantity;
		dto.pickupCity = order.pickupCity;
		dto.containerIDs = order.containerIDs;
		dto.voyageID = order.voyageID;
		dto.creationDate = order.creationDate;
		return dto;
	}

	public static OrderEntity toEntity(OrderDTO orderDTO) {
		OrderEntity orderEntity;
		if (orderDTO.orderID == null) {
			orderEntity = new OrderEntity(UUID.randomUUID().toString(),
								orderDTO.getProductID(),
								orderDTO.getCustomerID(),
								orderDTO.getQuantity(),
								orderDTO.getPickupCity(),
								orderDTO.getDestinationAddress(),
								orderDTO.creationDate,
								OrderEntity.PENDING_STATUS);
		} else {
			orderEntity = new OrderEntity(
								orderDTO.orderID,
								orderDTO.getProductID(),
								orderDTO.getCustomerID(),
								orderDTO.getQuantity(),
								orderDTO.getPickupCity(),
								orderDTO.getDestinationAddress(),
								orderDTO.creationDate,
								OrderEntity.PENDING_STATUS);
		}
		return orderEntity;
	}

	public String toString(){
		return "OrderDTO: { orderid: " + this.orderID + ", customer: " + this.customerID + ", product: " + this.productID + "}";
	}

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
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
	public Address getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(Address destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getCreationDate(){
		return creationDate;
	}

	public String getPickupCity() {
		return pickupCity;
	}

	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}
}
