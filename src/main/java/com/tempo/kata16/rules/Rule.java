package com.tempo.kata16.rules;

import com.tempo.kata16.domain.Payment;

public interface Rule {
    void run(final Payment payment);
}
