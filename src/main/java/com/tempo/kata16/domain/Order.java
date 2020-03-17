package com.tempo.kata16.domain;

public class Order{
    public Order(final Customer customer, final LineItem[] lineItems, final Agent agent) throws IllegalArgumentException {
        if (lineItems == null || lineItems.length == 0)
            throw new IllegalArgumentException("line items are required");
        _lineItems = lineItems;

        _customer = customer;
        _agent = agent;
    }

    private final Customer _customer;
    public Customer getCustomer(){
        return _customer;
    }

    private final LineItem[] _lineItems;
    public LineItem[] getLineItems(){
        return _lineItems;
    }

    private final Agent _agent;
	public Agent getAgent() {
		return _agent;
	}
}