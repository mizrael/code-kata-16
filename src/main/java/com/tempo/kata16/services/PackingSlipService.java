package com.tempo.kata16.services;

import com.tempo.kata16.domain.PackingSlip;
import com.tempo.kata16.domain.Payment;

public interface PackingSlipService {
    PackingSlip generateForShipping(Payment payment);
    PackingSlip generateForRoyalty(Payment payment);
	PackingSlip generate(Payment payment);
}