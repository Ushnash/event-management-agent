package com.solace.maas.ep.event.management.agent.service;

import com.solace.maas.ep.event.management.agent.TestConfig;
import com.solace.maas.ep.event.management.agent.logging.StreamingAppender;
import com.solace.maas.ep.event.management.agent.plugin.route.RouteBundle;
import com.solace.maas.ep.event.management.agent.processor.RouteCompleteProcessorImpl;
import com.solace.maas.ep.event.management.agent.repository.model.route.RouteEntity;
import com.solace.maas.ep.event.management.agent.repository.model.scan.ScanEntity;
import com.solace.maas.ep.event.management.agent.repository.scan.ScanRepository;
import com.solace.maas.ep.event.management.agent.service.logging.LoggingService;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("TEST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfig.class)
@SuppressWarnings("CPD-START")
public class ScanServiceTests {
    private final String routeId = UUID.randomUUID().toString();

    @Mock
    LoggingService loggingService;

    @Mock
    StreamingAppender streamingAppender;

    @Mock
    private ScanRepository scanRepository;

    @Mock
    private RouteService routeService;

    @Mock
    private ProducerTemplate producerTemplate;

    @Mock
    private ScanRouteService scanRouteService;

    @Mock
    private RouteCompleteProcessorImpl routeCompleteProcessor;

    @InjectMocks
    private ScanService scanService;

    @Test
    public void testSingleScanWithRouteBundle() throws Exception {
        RouteBundle routeBundleChained = RouteBundle.builder()
                .messagingServiceId("service1")
                .routeId("route1")
                .scanType("topicListing")
                .destinations(List.of())
                .recipients(List.of())
                .build();

        RouteBundle routeBundleDestination = RouteBundle.builder()
                .messagingServiceId("service1")
                .routeId("log:deadend")
                .scanType("none")
                .destinations(List.of())
                .recipients(List.of())
                .build();

        RouteBundle routeBundle = RouteBundle.builder()
                .messagingServiceId("service1")
                .routeId("route1")
                .scanType("topicListing")
                .destinations(List.of(routeBundleDestination))
                .recipients(List.of(routeBundleChained))
                .build();

        RouteEntity returnedEntity = RouteEntity.builder()
                .id(routeId)
                .childRouteIds("")
                .active(true)
                .build();

        ScanEntity scanEntity = ScanEntity.builder()
                .id(UUID.randomUUID().toString())
                .route(List.of(returnedEntity))
                .active(true)
                .build();

        when(routeService.findById(any(String.class)))
                .thenReturn(Optional.of(returnedEntity));
        when(scanRouteService.saveDestinations(any(List.class)))
                .thenReturn(List.of("log:deadend", List.of()));
        when(scanRepository.save(any(ScanEntity.class)))
                .thenReturn(scanEntity);
        when(scanRepository.findById(any(String.class)))
                .thenReturn(Optional.of(scanEntity));
        when(producerTemplate.asyncSend(any(String.class), any(Processor.class)))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(scanRepository.save(scanEntity))
                .thenReturn(scanEntity);

        scanService.singleScan(List.of(routeBundle), "groupId", "scanId");

        assertThatNoException();
    }

    @Test
    public void testFindById() {
        when(scanRepository.findById(any(String.class)))
                .thenReturn(Optional.of(ScanEntity.builder().build()));

        scanService.findById("scan1");

        assertThatNoException();
    }
}