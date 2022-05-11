package org.acme.orderms.infra.remote;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/voyages")
@RegisterRestClient(configKey = "voyageservice-api")
@Singleton
@RegisterClientHeaders(ReeferLRAHeaderFactory.class)
public interface VoyageService {
    @POST
    @Path("/assignOrder")
    public OrderForReefer callAssignOrder(OrderForReefer order);
}
