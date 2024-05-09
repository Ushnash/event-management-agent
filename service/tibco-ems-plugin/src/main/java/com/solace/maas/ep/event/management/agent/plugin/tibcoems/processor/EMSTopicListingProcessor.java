package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.processor.base.ResultProcessorImpl;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event.EMSTopicEvent;
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
public class EMSTopicListingProcessor extends ResultProcessorImpl<List<EMSTopicEvent>, Void> {
    private final MessagingServiceDelegateService messagingServiceDelegateService;

    @Autowired
    public EMSTopicListingProcessor(MessagingServiceDelegateService messagingServiceDelegateService) {
        super();
        this.messagingServiceDelegateService = messagingServiceDelegateService;
    }

    @Override
    public List<EMSTopicEvent> handleEvent(Map<String, Object> properties, Void body) throws Exception {
        String messagingServiceId = (String) properties.get(RouteConstants.MESSAGING_SERVICE_ID);

        TibjmsAdmin adminClient = messagingServiceDelegateService.getMessagingServiceClient(messagingServiceId);

        TopicInfo[] topics = adminClient.getTopics(); //get a list of topics from EMS
        List<EMSTopicEvent> topicList = new ArrayList<EMSTopicEvent>();

        for (TopicInfo topic : topics) {
            EMSTopicEvent t = new EMSTopicEvent();
            t.setName(topic.getName());
            topicList.add(t);
        }

        return topicList;
    }
}
