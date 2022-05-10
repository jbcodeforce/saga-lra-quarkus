package org.acme.freezerms.domain;

import java.net.URI;
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


    public void computeBestFreezerToShip(URI lraId, OrderDTO order) {
        logger.info("compute best freezer");
    }


    public void compensateFreezerOrder(String lraId, OrderDTO order) {
        logger.info("compensate freezer allocation");
    }
}
