package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.delegate;

import com.solace.maas.ep.event.management.agent.plugin.route.RouteBundle;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.TibcoEmsTestConfig;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSScanType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@ActiveProfiles("TEST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TibcoEmsTestConfig.class)
public class EMSRouteDelegateImplTests {
    @InjectMocks
    private EMSRouteDelegateImpl solaceRouteDelegate;

    private final List<RouteBundle> destinations = List.of(
            RouteBundle.builder()
                    .destinations(List.of())
                    .recipients(List.of())
                    .routeId("testRoute")
                    .firstRouteInChain(false)
                    .messagingServiceId("emsService1")
                    .build()
    );

    @Test
    public void testGenerateEmsQueueListingRouteList() {
        List<RouteBundle> routeBundles =
                solaceRouteDelegate.generateRouteList(destinations, List.of(), EMSScanType.TIBCOEMS_QUEUE_LISTING.name(),
                        "emsService1");

        assertThatNoException();
        assertThat(!routeBundles.isEmpty());
    }

    @Test
    public void testGenerateEmsConfigRouteList() {
        List<RouteBundle> routeBundles =
                solaceRouteDelegate.generateRouteList(destinations, List.of(), EMSScanType.TIBCOEMS_QUEUE_CONFIGURATION.name(),
                        "emsService1");

        assertThatNoException();
        assertThat(!routeBundles.isEmpty());
    }

    @Test
    public void testGenerateEmsTopicListingRouteList() {
        List<RouteBundle> routeBundles =
                solaceRouteDelegate.generateRouteList(destinations, List.of(), EMSScanType.TIBCOEMS_TOPIC_LISTING.name(),
                        "emsService1");

        assertThatNoException();
        assertThat(!routeBundles.isEmpty());
    }

    @Test
    public void testGenerateEmsTopicConfigRouteList() {
        List<RouteBundle> routeBundles =
                solaceRouteDelegate.generateRouteList(destinations, List.of(), EMSScanType.TIBCOEMS_TOPIC_CONFIGURATION.name(),
                        "emsService1");

        assertThatNoException();
        assertThat(!routeBundles.isEmpty());
    }

    @Test
    public void testGenerateEmsAllRouteList() {
        List<RouteBundle> routeBundles =
                solaceRouteDelegate.generateRouteList(destinations, List.of(), EMSScanType.TIBCOEMS_ALL.name(),
                        "emsService1");

        assertThatNoException();
        assertThat(!routeBundles.isEmpty());
    }
}
