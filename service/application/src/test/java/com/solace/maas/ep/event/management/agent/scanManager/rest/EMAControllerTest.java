package com.solace.maas.ep.event.management.agent.scanManager.rest;

import com.solace.maas.ep.common.model.ScanRequestDTO;
import com.solace.maas.ep.event.management.agent.TestConfig;
import com.solace.maas.ep.event.management.agent.scanManager.ScanManager;
import com.solace.maas.ep.event.management.agent.scanManager.mapper.ScanRequestMapper;
import com.solace.maas.ep.event.management.agent.scanManager.model.ScanRequestBO;
import com.solace.maas.ep.event.management.agent.util.IDGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("TEST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
public class EMAControllerTest {

    @Autowired
    private ScanRequestMapper scanRequestMapper;


    @Test
    public void EMAControllerTest() {
        ScanManager scanManager = mock(ScanManager.class);
        IDGenerator idGenerator = mock(IDGenerator.class);

        ScanRequestDTO scanRequestDTO = new ScanRequestDTO(List.of("topics"), List.of());
        ScanRequestBO scanRequestBO = new ScanRequestBO("id", "scanId",
                List.of("TEST_SCAN"), List.of());

        when(scanManager.scan(scanRequestBO))
                .thenReturn("scanId");

        EMAController controller = new EMAControllerImpl(scanRequestMapper, scanManager, idGenerator);

        ResponseEntity<String> reply =
                controller.scan("id", scanRequestDTO);

        assertThat(reply.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reply.getBody()).contains("Scan started.");

        assertThatNoException();
    }
}