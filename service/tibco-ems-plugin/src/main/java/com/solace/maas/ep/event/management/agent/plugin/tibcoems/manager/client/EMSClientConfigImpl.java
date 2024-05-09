package com.solace.maas.ep.event.management.agent.plugin.tibcoems.manager.client;

import com.solace.maas.ep.event.management.agent.plugin.manager.client.MessagingServiceClientConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EMSClientConfigImpl extends MessagingServiceClientConfig {
    protected EMSClientConfigImpl() {
        super("TIBCOEMS", new EMSClientManagerImpl());
    }
}