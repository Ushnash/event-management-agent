package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.processor.base.ResultProcessorImpl;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.TibjmsAdmin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EMSQueueConfigurationProcessor extends ResultProcessorImpl<List<Map<String, Object>>, Void> {
    private final MessagingServiceDelegateService messagingServiceDelegateService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EMSQueueConfigurationProcessor(MessagingServiceDelegateService messagingServiceDelegateService) {
        super();
        this.messagingServiceDelegateService = messagingServiceDelegateService;
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    @SuppressWarnings("PMD")
    public List<Map<String, Object>> handleEvent(Map<String, Object> properties, Void body) throws Exception {
        String messagingServiceId = (String) properties.get(RouteConstants.MESSAGING_SERVICE_ID);

        TibjmsAdmin adminClient = messagingServiceDelegateService.getMessagingServiceClient(messagingServiceId);

        QueueInfo[] queues = adminClient.getQueues(); //get a list of queues from EMS
        List<Map<String, Object>> emsQueues = new ArrayList<>();

        for (QueueInfo queue : queues) {
            Map<String, Object> qMap = objectMapper.convertValue(queue, new TypeReference<>() {});
            emsQueues.add(qMap);
        }

        return emsQueues;
    }
}
