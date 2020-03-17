package com.tempo.kata16.domain;

public class Membership extends Product{

    public Membership(String sku, Membership prev) {
        super(sku);
        _previous = prev;
    }

    private Membership _previous;
	public Membership getPreviousLevel() {
		return _previous;
	}

}
