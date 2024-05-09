package com.solace.maas.ep.event.management.agent.plugin.tibcoems.route.enumeration;

public enum EMSRouteType {
    TIBCOEMS_QUEUE_LISTING("queueNameListing"),
    TIBCOEMS_QUEUE_CONFIGURATION("queueConfigurationListing"),
    TIBCOEMS_TOPIC_LISTING("topicListing");

    public final String label;

    EMSRouteType(String label) {
        this.label = label;
    }
}
