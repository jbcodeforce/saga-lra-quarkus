package org.acme.freezerms.infra.api;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.freezerms.domain.Freezer;
import org.acme.freezerms.domain.FreezerService;
import org.acme.freezerms.infra.api.dto.OrderDTO;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.resteasy.annotations.jaxrs.PathParam;


@Path("/api/v1/reefers")
@Produces(MediaType.APPLICATION_JSON)
public class FreezerResource {
    private static Logger logger = Logger.getLogger("FreezerResource");
    @Inject
    public FreezerService service;


    @GET
    public List<Freezer> getAll() {
        return service.getAllReefers();
    }

    @GET
    @Path("/{reeferId}")
    public Freezer getReeferById(@PathParam String reeferId) {
        return service.getReeferById(reeferId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Freezer saveNewFreezer( Freezer newFreezer) {
        logger.info("Save new freezer " + newFreezer.toString());
        return service.saveReefer(newFreezer);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Freezer updateFreezer( Freezer newFreezer) {
        logger.info("Update freezer " + newFreezer.toString());
        return service.updateFreezer(newFreezer);
    }

    /**
     * Method to support the order management saga. Allocate a refrigerator container from the 
     * order destination and pickup up area.
     * @param lraId
     * @param order
     * @return
     */
    @LRA(value = LRA.Type.REQUIRED, end=false)
    @POST
    @Path("/assignOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        logger.info("processOrder new freezer " + order.toString());
        logger.info("processOrder LRA " + lraId.toString());
        if (order.destinationCity.equals("ABadDestination")) {
            return Response.serverError().build();
        }
        OrderDTO updatedOrder = service.computeBestFreezerToShip(lraId.toString(),order);
        return Response.ok().entity(updatedOrder).build();
    }

    @Compensate
    @Path("/compensateOrder")
    @PUT
    public Response compensateOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        service.compensateFreezerOrder(lraId.toString(),order);
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }
}