package org.acme.freezerms.domain;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.freezerms.infra.api.dto.OrderDTO;
import org.acme.freezerms.infra.repo.FreezerRepository;
import org.jboss.logging.Logger;

@ApplicationScoped
public class FreezerService {
    private static Logger logger = Logger.getLogger("ReeferService");

    @Inject
    public FreezerRepository repository;

    public FreezerService(FreezerRepository repo) {
        this.repository = repo;
    }

    public Freezer getReeferById(String id) {
        return repository.getById(id);
    }

  
    public List<Freezer> getAllReefers() {
        return repository.getAll();
    }

    public Freezer saveReefer(Freezer r){
        repository.addReefer(r);
        return r;
    }


    public OrderDTO computeBestFreezerToShip(String transactionID, OrderDTO order) {
        logger.info("compute best freezer " + transactionID);
        List<Freezer> freezers = repository.assignFreezerForALocation(transactionID,order.pickupCity,order.quantity);
        order.containerIDs = "";
        for (Freezer f : freezers) {
            order.containerIDs = order.containerIDs + f.reeferID + ",";
        }
        if (order.containerIDs.length() > 0) {
            order.containerIDs=order.containerIDs.substring(0, order.containerIDs.lastIndexOf(","));
        }
        logger.info("--> " + order.toString());
        return order;
    }


    public OrderDTO compensateFreezerOrder(String transactionID, OrderDTO order) {
        logger.info("compensate freezer allocation " + transactionID);
        order.containerIDs = "";
        List<Freezer> freezers = repository.getFreezersForTransaction(transactionID);
        for (Freezer f : freezers) {
            f.status = Freezer.FREE;
            f.currentFreeCapacity = f.capacity;
            repository.updateFreezer(f);
        }
        logger.info("--> " + order.toString());
        return order;
    }

    public Freezer updateFreezer(Freezer newFreezer) {
        return repository.updateFreezer(newFreezer);
    }
}
