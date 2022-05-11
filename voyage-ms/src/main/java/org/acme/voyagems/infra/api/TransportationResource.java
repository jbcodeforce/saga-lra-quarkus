package org.acme.voyagems.infra.api;

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

import org.acme.voyagems.domain.TransportDefinition;
import org.acme.voyagems.domain.TransportationService;
import org.acme.voyagems.infra.api.dto.OrderDTO;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/api/v1/voyages")
public class TransportationResource {
    private static Logger logger = Logger.getLogger("TransportationResource");
    @Inject
    TransportationService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransportDefinition> getCurrentTransportDefinitions() {
        return service.getAllTransportDefinitions();
    }

    @GET
    @Path("/{voyageID}")
    public TransportDefinition getVoyageById(@PathParam String voyageID) {
        return service.getVoyageById(voyageID);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveNewTransportation(TransportDefinition transport) {
        service.saveNewTransportation(transport);
    }

    @LRA(value = LRA.Type.REQUIRED, end=false)
    @POST
    @Path("/assignOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        logger.info("processOrder new order " + order.toString());
        logger.info("processOrder LRA " + lraId.toString());
        if (order.destinationCity.equals("NoDestination")) {
            return Response.serverError().build();
        }
        service.computeBestVoyage(lraId.toString(),order);
        return Response.ok().build();
    }

    @Compensate
    @Path("/compensateOrder")
    @PUT
    public Response compensateOrder(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId, OrderDTO order) {
        service.compensateVoyageAllocation(lraId.toString(),order);
        return Response.ok(ParticipantStatus.Compensated.name()).build();
    }

}