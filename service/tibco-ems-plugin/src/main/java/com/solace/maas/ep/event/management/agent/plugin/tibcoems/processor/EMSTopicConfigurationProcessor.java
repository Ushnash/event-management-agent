package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.processor.base.ResultProcessorImpl;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.tibco.tibjms.admin.TopicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EMSTopicConfigurationProcessor extends ResultProcessorImpl<List<Map<String, Object>>, Void> {
    private final MessagingServiceDelegateService messagingServiceDelegateService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EMSTopicConfigurationProcessor(MessagingServiceDelegateService messagingServiceDelegateService) {
        super();
        this.messagingServiceDelegateService = messagingServiceDelegateService;
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    @SuppressWarnings("PMD")
    public List<Map<String, Object>> handleEvent(Map<String, Object> properties, Void body) throws Exception {
        String messagingServiceId = (String) properties.get(RouteConstants.MESSAGING_SERVICE_ID);

        TibjmsAdmin adminClient = messagingServiceDelegateService.getMessagingServiceClient(messagingServiceId);

        TopicInfo[] queues = adminClient.getTopics(); //get a list of topics from EMS
        List<Map<String, Object>> emsTopics = new ArrayList<>();

        for (TopicInfo topic : queues) {
            Map<String, Object> topicMap = objectMapper.convertValue(topic, new TypeReference<>() {});
            emsTopics.add(topicMap);
        }

        return emsTopics;
    }
}
