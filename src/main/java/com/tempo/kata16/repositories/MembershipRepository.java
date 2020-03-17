package com.tempo.kata16.repositories;

import com.tempo.kata16.domain.Membership;

public interface MembershipRepository{
	Membership findBySku(String sku);
}