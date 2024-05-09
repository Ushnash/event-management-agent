package com.solace.maas.ep.event.management.agent.plugin.tibcoems.manager.client;

import com.solace.maas.ep.event.management.agent.plugin.manager.client.MessagingServiceClientManager;
import com.solace.maas.ep.event.management.agent.plugin.messagingService.event.AuthenticationDetailsEvent;
import com.solace.maas.ep.event.management.agent.plugin.messagingService.event.ConnectionDetailsEvent;
import com.solace.maas.ep.event.management.agent.plugin.util.MessagingServiceConfigurationUtil;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.tibco.tibjms.admin.TibjmsAdminException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Data
@Component
public class EMSClientManagerImpl implements MessagingServiceClientManager<TibjmsAdmin> {

    public EMSClientManagerImpl() {
    }

    @Override
    public TibjmsAdmin getClient(ConnectionDetailsEvent connectionDetailsEvent) {

        log.trace("Creating TIBCO EMS client for event broker [{}].", connectionDetailsEvent.getMessagingServiceId());

        //get authentication details from config file
        AuthenticationDetailsEvent authenticationDetailsEvent = connectionDetailsEvent.getAuthenticationDetails()
                .stream()
                .findFirst().orElseThrow(() -> {
                    String message = String.format("Could not find authentication details for service with id [%s].",
                            connectionDetailsEvent.getMessagingServiceId());
                    log.error(message);
                    return new NoSuchElementException(message);
                });

        String url = connectionDetailsEvent.getUrl();
        String username = MessagingServiceConfigurationUtil.getUsername(authenticationDetailsEvent);
        String password = MessagingServiceConfigurationUtil.getPassword(authenticationDetailsEvent);

        try {
            return new TibjmsAdmin(url, username, password);
        } catch (TibjmsAdminException ex) {
            log.error("Error connecting to event broker:{}", ex.getMessage());
            return null;
        }
    }
}