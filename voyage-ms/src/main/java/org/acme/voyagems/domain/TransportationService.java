package org.acme.voyagems.domain;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.voyagems.infra.api.dto.OrderDTO;
import org.acme.voyagems.infra.repo.TransportRepository;
import org.jboss.logging.Logger;

@ApplicationScoped
public class TransportationService {
    Logger logger = Logger.getLogger(TransportationService.class.getName());

    @Inject
    TransportRepository repository;
    
    public TransportationService() {
 
    }

    public List<TransportDefinition> getAllTransportDefinitions(){
        return repository.getAll();
    }

    public void saveNewTransportation(TransportDefinition td) {
        repository.addTransport(td);
    }

    public void computeBestVoyage(String lraId, OrderDTO order) {
        logger.info("compute best voyage " + lraId);
        logger.info(order.toString());
    }


    public void compensateVoyageAllocation(String lraId, OrderDTO order) {
        logger.info("compensate voyage allocation " + lraId);
        logger.info(order.toString());
    }

    public TransportDefinition getVoyageById(String voyageID) {
        logger.info("Search voyage: " + voyageID);
        
        return repository.getById(voyageID);
    }

}
