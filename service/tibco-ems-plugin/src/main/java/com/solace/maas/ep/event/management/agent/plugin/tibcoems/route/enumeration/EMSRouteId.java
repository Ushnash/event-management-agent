package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration;

public enum EMSRouteId {
    TIBCOEMS_QUEUE_LISTING("EMSQueueNameListing"),
    TIBCOEMS_TOPIC("EMSTopicListing");

    public final String label;

    EMSRouteId(String label) {
        this.label = label;
    }
}
