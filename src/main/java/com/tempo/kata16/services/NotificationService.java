package com.tempo.kata16.services;

import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.Membership;

public interface NotificationService {
	void notify(Customer customer, Membership membership);
}
