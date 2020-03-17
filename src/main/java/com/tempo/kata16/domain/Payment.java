package com.tempo.kata16.domain;

import com.tempo.kata16.paymenthandlers.PaymentHandler;

public class Payment {
    public Payment(final Order order){
        _order = order;
    }

    private final Order _order;
    public Order getOrder(){
        return _order;
    }

    public void Process(PaymentHandler[] rules){
        for(PaymentHandler rule : rules)
            rule.run(this);
    }
}