package com.github.adetiamarhadi.paymentservice.event;

import com.github.adetiamarhadi.paymentservice.data.PaymentEntity;
import com.github.adetiamarhadi.paymentservice.data.PaymentsRepository;
import com.github.adetiamarhadi.sagacore.events.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsHandler {

    private final PaymentsRepository paymentsRepository;

    public PaymentEventsHandler(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {

        PaymentEntity paymentEntity = new PaymentEntity(paymentProcessedEvent.getPaymentId(),
                paymentProcessedEvent.getOrderId());

        paymentsRepository.save(paymentEntity);
    }
}
