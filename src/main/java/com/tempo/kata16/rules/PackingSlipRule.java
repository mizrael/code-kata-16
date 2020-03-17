package com.tempo.kata16.rules;

import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.services.PackingSlipService;

// If the payment is for a physical product, generate a packing slip for shipping.
// If the payment is for a book, create a duplicate packing slip for the royalty department.
public class PackingSlipRule implements Rule{
    private final PackingSlipService _service;

    public PackingSlipRule(final PackingSlipService service){
        _service = service;
    }

    @Override
    public void run(final Payment payment) {
        Order order = payment.getOrder();
        LineItem[] lineItems = order.getLineItems();

        Boolean generateForShipping = false;
        Boolean generateForRoyalty = false;

        for (LineItem lineItem : lineItems) {
            if(lineItem.hasCategory(ProductCategory.Physical))
                generateForShipping = true;
            if(lineItem.hasCategory(ProductCategory.Books))
                generateForRoyalty = true;
        }

        _service.generate(payment);

        if(generateForShipping)
            _service.generateForShipping(payment);
        if(generateForRoyalty)
            _service.generateForRoyalty(payment);
    }
}
