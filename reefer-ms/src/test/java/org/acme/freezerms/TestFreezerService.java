package org.acme.freezerms;

import com.google.inject.Inject;

import org.acme.freezerms.domain.FreezerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TestFreezerService {

    @Inject
    FreezerService serv;

    
    @Test
    public void testLoadingFreezers() {
       
        Assertions.assertEquals(3,serv.repository.getAll().size());
    }
}
