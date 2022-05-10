package org.acme.orderms.infra.api;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.orderms.domain.OrderEntity;
import org.acme.orderms.domain.OrderService;
import org.acme.orderms.infra.api.dto.OrderDTO;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/api/v1/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OrderResource {
    private static final Logger logger = Logger.getLogger(OrderResource.class.getName());

    @Inject
    public OrderService service;
    
    @GET
    public List<OrderDTO> getAllActiveOrders() {
        List<OrderDTO> l = new ArrayList<OrderDTO>();
        for (OrderEntity order : service.getAllOrders()) {
            l.add(OrderDTO.fromEntity(order));
        }
        return l;
    }

    @GET
    @Path("/{id}")
    public OrderDTO get(@PathParam("id") String id) {
        logger.info("In get order with id: " + id);
        OrderEntity order = service.findById(id);
        if (order == null) {
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
     
        }
        return OrderDTO.fromEntity(order);
    }

    @POST
    @LRA(value = LRA.Type.REQUIRED, end=false)
    @Counted(name = "performedNewOrderCreation", description = "How many post new order have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the operation.", unit = MetricUnits.MILLISECONDS)
    public OrderDTO saveNewOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        logger.info("POST operation " + lraId + " " + order.toString());
        OrderEntity entity = OrderDTO.toEntity(order);
        entity.lraID = lraId;
        return OrderDTO.fromEntity(service.createOrder(entity));
    }

    @PUT
    @LRA(value = LRA.Type.REQUIRED, end=false)
    public OrderDTO updateExistingOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        logger.info("PUT operation " + order.toString());
        OrderEntity entity = OrderDTO.toEntity(order);
        return OrderDTO.fromEntity(service.updateOrder(entity));
    }


    @PUT
    @Path("compensate")
    @Compensate
    public Response compensateWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.printf("compensating %s%n", lraId);
        return Response.ok(lraId.toASCIIString()).build();
    }

    // Step 2e: An optional callback notifying that the LRA is closing
    @PUT
    @Path("complete")
    @Complete
    public Response completeWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
        System.out.printf("completing %s%n", lraId);
        return Response.ok(lraId.toASCIIString()).build();
    }
}