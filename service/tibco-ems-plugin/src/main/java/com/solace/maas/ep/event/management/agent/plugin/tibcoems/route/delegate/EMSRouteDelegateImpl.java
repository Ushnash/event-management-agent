package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.delegate;

import com.solace.maas.ep.event.management.agent.plugin.jacoco.ExcludeFromJacocoGeneratedReport;
import com.solace.maas.ep.event.management.agent.plugin.route.RouteBundle;
import com.solace.maas.ep.event.management.agent.plugin.route.delegate.base.MessagingServiceRouteDelegateImpl;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteId;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSRouteType;
import com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration.EMSScanType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ExcludeFromJacocoGeneratedReport
@SuppressWarnings("CPD-START")
@Component
@Slf4j
public class EMSRouteDelegateImpl extends MessagingServiceRouteDelegateImpl {

    public EMSRouteDelegateImpl() {
        super("TIBCOEMS");
        log.debug("### PLUGIN `TIBCOEMS` HAS LOADED ###");
    }

    @Override
    public List<RouteBundle> generateRouteList(List<RouteBundle> destinations, List<RouteBundle> recipients,
                                               String scanType, String messagingServiceId) {
        List<RouteBundle> result = new ArrayList<>();

        switch (EMSScanType.valueOf(scanType)) {
            case TIBCOEMS_ALL:
                result.add(queueRouteBundle(destinations, recipients, messagingServiceId));
                result.add(subscriptionRouteBundle(destinations, recipients, messagingServiceId));
                break;
            case TIBCOEMS_QUEUE:
                result.add(queueRouteBundle(destinations, recipients, messagingServiceId));
                break;
            case TIBCOEMS_TOPIC:
                result.add(subscriptionRouteBundle(destinations, recipients, messagingServiceId));
                break;
        }

        return result;
    }

    private RouteBundle queueRouteBundle(List<RouteBundle> destinations, List<RouteBundle> recipients,
                                         String messagingServiceId) {
        return createRouteBundle(destinations, recipients, EMSRouteType.TIBCOEMS_QUEUE.label, messagingServiceId,
                EMSRouteId.TIBCOEMS_QUEUE.label, true);
    }

    private RouteBundle subscriptionRouteBundle(List<RouteBundle> destinations, List<RouteBundle> recipients,
                                                String messagingServiceId) {
        return createRouteBundle(destinations, recipients, EMSRouteType.TIBCOEMS_TOPIC.label, messagingServiceId,
                EMSRouteId.TIBCOEMS_TOPIC.label, false);
    }
}
