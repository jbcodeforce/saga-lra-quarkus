package org.acme.orderms.infra.remote;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/reefers")
@RegisterRestClient(configKey = "reeferservice-api")
@Singleton
@RegisterClientHeaders(ReeferLRAHeaderFactory.class)
public interface ReeferService {
    
    @POST
    public Response callAssignOrder(OrderForReefer order);
}
