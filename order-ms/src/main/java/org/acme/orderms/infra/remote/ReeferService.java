package org.acme.orderms.infra.remote;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/reefers")
@RegisterRestClient(configKey = "reeferservice-api")
@Singleton
@RegisterClientHeaders(ReeferLRAHeaderFactory.class)
public interface ReeferService {
    

    @POST
    @Path("/assignOrder")
    public OrderForReefer callAssignOrder(OrderForReefer order);
}
