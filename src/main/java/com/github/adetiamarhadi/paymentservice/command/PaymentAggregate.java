package com.github.adetiamarhadi.paymentservice.command;

import com.github.adetiamarhadi.sagacore.commands.ProcessPaymentCommand;
import com.github.adetiamarhadi.sagacore.events.PaymentProcessedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    public PaymentAggregate() {}

    @CommandHandler
    public void handle(ProcessPaymentCommand processPaymentCommand) {

        if (processPaymentCommand.getOrderId() == null || processPaymentCommand.getOrderId().isBlank()) {
            throw new IllegalArgumentException("order id can not be empty.");
        }

        if (processPaymentCommand.getPaymentId() == null || processPaymentCommand.getPaymentId().isBlank()) {
            throw new IllegalArgumentException("payment id can not be empty.");
        }

        if (processPaymentCommand.getPaymentDetails() == null) {
            throw new IllegalArgumentException("payment details can not be empty.");
        }

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                .paymentId(processPaymentCommand.getPaymentId())
                .orderId(processPaymentCommand.getOrderId())
                .build();

        AggregateLifecycle.apply(paymentProcessedEvent);
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}
