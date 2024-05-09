package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration;

public enum EMSRouteType {
    TIBCOEMS_QUEUE_LISTING("queueNameListing"),
    TIBCOEMS_TOPIC("topicListing");

    public final String label;

    EMSRouteType(String label) {
        this.label = label;
    }
}
