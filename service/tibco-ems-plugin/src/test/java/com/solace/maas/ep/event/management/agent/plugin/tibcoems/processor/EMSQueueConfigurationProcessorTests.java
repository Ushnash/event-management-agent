package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.TibcoEmsTestConfig;
import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.TibjmsAdmin;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ActiveProfiles("TEST")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TibcoEmsTestConfig.class)
@SuppressWarnings("PMD")
public class EMSQueueConfigurationProcessorTests {
    @Mock
    private MessagingServiceDelegateService messagingServiceDelegateService;

    @InjectMocks
    private EMSQueueConfigurationProcessor emsQueueConfigurationProcessor;

    @SneakyThrows
    @Test
    public void testHandleEvent() {
        TibjmsAdmin tibjmsAdmin = mock(TibjmsAdmin.class);

        when(messagingServiceDelegateService.getMessagingServiceClient("emsService"))
                .thenReturn(tibjmsAdmin);

        QueueInfo q1 = new QueueInfo("queue1");
        QueueInfo q2 = new QueueInfo("queue2");

        List<Map<String, Object>> expectedResult = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        expectedResult.add(objectMapper.convertValue(q1, new TypeReference<>() {}));
        expectedResult.add(objectMapper.convertValue(q2, new TypeReference<>() {}));

        when(tibjmsAdmin.getQueues()).thenReturn(new QueueInfo[]{q1, q2});

        List<Map<String, Object>> queueEventList = emsQueueConfigurationProcessor.handleEvent(
                Map.of(RouteConstants.MESSAGING_SERVICE_ID, "emsService"), null);

        assertThat(queueEventList, hasSize(2));
        assertEquals(queueEventList, expectedResult);
    }
}
