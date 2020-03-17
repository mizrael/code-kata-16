package com.tempo.kata16.services;

import com.tempo.kata16.domain.PackingSlip;
import com.tempo.kata16.domain.Order;

public interface RoyaltyService{
    PackingSlip generatePackingSlip(Order order);
}