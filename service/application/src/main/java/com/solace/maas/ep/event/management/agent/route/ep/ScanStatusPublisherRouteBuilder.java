package com.solace.maas.ep.event.management.agent.route.ep;

import com.solace.maas.ep.event.management.agent.processor.RouteCompleteProcessorImpl;
import com.solace.maas.ep.event.management.agent.processor.ScanStatusProcessor;
import com.solace.maas.ep.event.management.agent.route.ep.exceptionHandlers.ScanStatusExceptionHandler;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import static org.apache.camel.support.builder.PredicateBuilder.or;

@Component
@ConditionalOnExpression("${eventPortal.gateway.messaging.standalone} == false")
public class ScanStatusPublisherRouteBuilder extends RouteBuilder {
    private final ScanStatusProcessor scanStatusProcessor;

    private final RouteCompleteProcessorImpl routeCompleteProcessor;

    @Autowired
    public ScanStatusPublisherRouteBuilder(ScanStatusProcessor scanStatusProcessor,
                                           RouteCompleteProcessorImpl routeCompleteProcessor) {
        super();
        this.scanStatusProcessor = scanStatusProcessor;
        this.routeCompleteProcessor = routeCompleteProcessor;
    }

    @Override
    public void configure() {
        from("direct:processScanStatus")
                .choice().when(or(header("DATA_PROCESSING_COMPLETE").isEqualTo(true),
                        header("FILE_IMPORTING_COMPLETE").isEqualTo(true)))
                .to("direct:processEndOfRoute")
                .endChoice()
                .end();

        from("direct:processEndOfRoute")
                .process(routeCompleteProcessor)
                .onException(Exception.class)
                .process(new ScanStatusExceptionHandler())
                .continued(true)
                .end()
                .to("direct:scanStatusPublisher");

        from("direct:scanStatusPublisher")
                .routeId("scanStatusPublisher")
                .process(scanStatusProcessor)
                .onException(Exception.class)
                .process(new ScanStatusExceptionHandler())
                .continued(true)
                .end();
    }
}