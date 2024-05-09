package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.processor.base.ResultProcessorImpl;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event.EMSTopicNameEvent;
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
public class EMSTopicNameListingProcessor extends ResultProcessorImpl<List<EMSTopicNameEvent>, Void> {
    private final MessagingServiceDelegateService messagingServiceDelegateService;

    @Autowired
    public EMSTopicNameListingProcessor(MessagingServiceDelegateService messagingServiceDelegateService) {
        super();
        this.messagingServiceDelegateService = messagingServiceDelegateService;
    }

    @Override
    public List<EMSTopicNameEvent> handleEvent(Map<String, Object> properties, Void body) throws Exception {
        String messagingServiceId = (String) properties.get(RouteConstants.MESSAGING_SERVICE_ID);

        TibjmsAdmin adminClient = messagingServiceDelegateService.getMessagingServiceClient(messagingServiceId);

        TopicInfo[] topics = adminClient.getTopics(); //get a list of topics from EMS
        List<EMSTopicNameEvent> topicList = new ArrayList<EMSTopicNameEvent>();

        for (TopicInfo topic : topics) {
            EMSTopicNameEvent t = new EMSTopicNameEvent();
            t.setName(topic.getName());
            topicList.add(t);
        }

        return topicList;
    }
}
