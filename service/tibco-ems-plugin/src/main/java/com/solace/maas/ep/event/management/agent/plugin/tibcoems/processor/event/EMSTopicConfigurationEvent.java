package com.solace.maas.ep.event.management.agent.plugin.tibcoems.processor.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Attributes parsed from the response.
 */
@Data
public class EMSTopicConfigurationEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 255842056103154192L;
    private String name;
    private Map<String, Object> configuration;
}