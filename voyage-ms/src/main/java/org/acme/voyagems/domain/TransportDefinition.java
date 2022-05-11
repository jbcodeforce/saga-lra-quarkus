package org.acme.voyagems.domain;

import io.quarkus.runtime.annotations.RegisterForReflection;

// {"lane_id":"1","from_loc":"Beerse, Belgium","to_loc":"Paris, France","transit_time":2,"reefer_cost":10,"fixed_cost":20}
@RegisterForReflection
public class TransportDefinition {
    public String voyageID;
    public String lane_id;
    public String from_loc;
    public String to_loc;
    public Double transit_time;
    public Double fixed_cost;
    public Double reefer_cost;

    public TransportDefinition(){}
}