package org.acme.orderms.infra.remote;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@Singleton
public class ReeferLRAHeaderFactory implements ClientHeadersFactory {

    public URI lraID;

    public ReeferLRAHeaderFactory(){}

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {
         MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.add(LRA_HTTP_CONTEXT_HEADER,lraID.toString());
        return result;
    }
    
    public void setLRA(URI lraID){
        this.lraID = lraID;
    }

    public String getLRAasString(){
        if (lraID != null)
            return lraID.toString();
        return "";
    }
}
