package org.acme.orderms.infra.remote;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@ApplicationScoped
public class ReeferLRAHeaderFactory implements ClientHeadersFactory {

    public URI lraID;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {
         MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.add(LRA_HTTP_CONTEXT_HEADER,lraID.toString());
        return result;
    }
    
}
