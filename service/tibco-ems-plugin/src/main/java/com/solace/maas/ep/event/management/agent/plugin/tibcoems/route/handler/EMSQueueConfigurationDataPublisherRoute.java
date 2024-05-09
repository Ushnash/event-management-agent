package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.handler;

import com.solace.maas.ep.event.management.agent.plugin.processor.ScanTypeDescendentsProcessor;
import com.solace.maas.ep.event.management.agent.plugin.processor.logging.MDCProcessor;
import com.solace.maas.ep.event.management.agent.plugin.route.handler.base.DataPublisherRouteBuilder;
import com.solace.maas.ep.event.management.agent.plugin.route.manager.RouteManager;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.EMSQueueConfigurationProcessor;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteId;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EMSQueueConfigurationDataPublisherRoute extends DataPublisherRouteBuilder {

    @Autowired
    public EMSQueueConfigurationDataPublisherRoute(EMSQueueConfigurationProcessor processor, RouteManager routeManager,
                                                   MDCProcessor mdcProcessor, ScanTypeDescendentsProcessor scanTypeDescendentsProcessor) {
        super(processor, EMSRouteId.TIBCOEMS_QUEUE_CONFIGURATION.label, EMSRouteType.TIBCOEMS_QUEUE_CONFIGURATION.label,
                routeManager, mdcProcessor, scanTypeDescendentsProcessor);

    }
}
