package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Attributes parsed from the response.
 */
@Data
public class EMSTopicEvent implements Serializable {

    @Serial
    private static final long serialVersionUID  = -5127409557140067324L;

    private String name;
}