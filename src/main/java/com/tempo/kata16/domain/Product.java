package com.tempo.kata16.domain;

public class Product{
    public Product(String sku){
        _sku = sku;
    }

    private String _sku;
    public String getSku(){
        return _sku;
    }
}