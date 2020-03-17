package com.tempo.kata16.paymenthandlers;

import com.tempo.kata16.domain.Payment;

public interface PaymentHandler {
    void run(final Payment payment);
}
