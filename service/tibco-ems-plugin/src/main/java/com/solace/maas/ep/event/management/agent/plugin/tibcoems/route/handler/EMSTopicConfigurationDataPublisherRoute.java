package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.handler;

import com.solace.maas.ep.event.management.agent.plugin.processor.ScanTypeDescendentsProcessor;
import com.solace.maas.ep.event.management.agent.plugin.processor.logging.MDCProcessor;
import com.solace.maas.ep.event.management.agent.plugin.route.handler.base.DataPublisherRouteBuilder;
import com.solace.maas.ep.event.management.agent.plugin.route.manager.RouteManager;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.EMSTopicConfigurationProcessor;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteId;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EMSTopicConfigurationDataPublisherRoute extends DataPublisherRouteBuilder {

    @Autowired
    public EMSTopicConfigurationDataPublisherRoute(EMSTopicConfigurationProcessor processor, RouteManager routeManager,
                                                   MDCProcessor mdcProcessor, ScanTypeDescendentsProcessor scanTypeDescendentsProcessor) {
        super(processor, EMSRouteId.TIBCOEMS_TOPIC_CONFIGURATION.label, EMSRouteType.TIBCOEMS_TOPIC_CONFIGURATION.label,
                routeManager, mdcProcessor, scanTypeDescendentsProcessor);

    }
}
