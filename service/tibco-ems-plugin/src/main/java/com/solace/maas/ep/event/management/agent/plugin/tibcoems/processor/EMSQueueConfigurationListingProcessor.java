package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor;

import com.solace.maas.ep.event.management.agent.plugin.constants.RouteConstants;
import com.solace.maas.ep.event.management.agent.plugin.processor.base.ResultProcessorImpl;
import com.solace.maas.ep.event.management.agent.plugin.service.MessagingServiceDelegateService;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event.EMSQueueConfigurationEvent;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event.EMSQueueNameEvent;
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
public class EMSQueueConfigurationListingProcessor extends ResultProcessorImpl<List<EMSQueueConfigurationEvent>, Void> {
    private final MessagingServiceDelegateService messagingServiceDelegateService;

    @Autowired
    public EMSQueueConfigurationListingProcessor(MessagingServiceDelegateService messagingServiceDelegateService) {
        super();
        this.messagingServiceDelegateService = messagingServiceDelegateService;
    }

    @Override
    @SuppressWarnings("PMD")
    public List<EMSQueueConfigurationEvent> handleEvent(Map<String, Object> properties, Void body) throws Exception {
        String messagingServiceId = (String) properties.get(RouteConstants.MESSAGING_SERVICE_ID);

        TibjmsAdmin adminClient = messagingServiceDelegateService.getMessagingServiceClient(messagingServiceId);

        QueueInfo[] queues = adminClient.getQueues(); //get a list of queues from EMS
        List<EMSQueueConfigurationEvent> queueList = new ArrayList<>();

        for (QueueInfo queue : queues) {
            EMSQueueConfigurationEvent q = new EMSQueueConfigurationEvent();
            q.setName(queue.getName());
            q.setConfiguration(queue);
            queueList.add(q);
        }

        return queueList;
    }
}
