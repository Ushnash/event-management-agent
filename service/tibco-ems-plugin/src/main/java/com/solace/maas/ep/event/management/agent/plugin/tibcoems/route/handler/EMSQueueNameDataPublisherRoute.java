package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.handler;

import com.solace.maas.ep.event.management.agent.plugin.processor.ScanTypeDescendentsProcessor;
import com.solace.maas.ep.event.management.agent.plugin.processor.logging.MDCProcessor;
import com.solace.maas.ep.event.management.agent.plugin.route.handler.base.DataPublisherRouteBuilder;
import com.solace.maas.ep.event.management.agent.plugin.route.manager.RouteManager;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.EMSQueueNameListingProcessor;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteId;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EMSQueueNameDataPublisherRoute extends DataPublisherRouteBuilder {

    @Autowired
    public EMSQueueNameDataPublisherRoute(EMSQueueNameListingProcessor processor, RouteManager routeManager,
                                          MDCProcessor mdcProcessor, ScanTypeDescendentsProcessor scanTypeDescendentsProcessor) {
        super(processor, EMSRouteId.TIBCOEMS_QUEUE_LISTING.label, EMSRouteType.TIBCOEMS_QUEUE_LISTING.label,
                routeManager, mdcProcessor, scanTypeDescendentsProcessor);

    }
}