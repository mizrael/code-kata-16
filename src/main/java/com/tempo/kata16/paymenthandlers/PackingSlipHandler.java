package com.tempo.kata16.paymenthandlers;

import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.services.PackingSlipService;
import com.tempo.kata16.services.RoyaltyService;
import com.tempo.kata16.services.ShippingService;

// If the payment is for a physical product, generate a packing slip for shipping.
// If the payment is for a book, create a duplicate packing slip for the royalty department.
// if the payment is for the video “Learning to Ski,” add a free “First Aid” video to the packing slip
public class PackingSlipHandler implements PaymentHandler{
    private final PackingSlipService _packingSlipService;
    private final ShippingService _shippingService;
    private final RoyaltyService _royaltyService;

    public PackingSlipHandler(final ShippingService shippingService, 
                              final RoyaltyService royaltyService,
                              final PackingSlipService packingSlipService){
        _shippingService = shippingService;
        _royaltyService = royaltyService;
        _packingSlipService = packingSlipService;
    }

    @Override
    public void run(final Payment payment) {
        final Order order = payment.getOrder();
        final LineItem[] lineItems = order.getLineItems();

        Boolean generateForShipping = false;
        Boolean generateForRoyalty = false;

        for (final LineItem lineItem : lineItems) {
            if(lineItem.hasCategory(ProductCategory.Physical))
                generateForShipping = true;
            if(lineItem.hasCategory(ProductCategory.Books))
                generateForRoyalty = true;

            if(lineItem.getSku() == "learning-to-ski") 
                order.addGiftBySku("first-aid");
        }

        _packingSlipService.generate(order);

        if(generateForShipping)
            _shippingService.generatePackingSlip(order);
        if(generateForRoyalty)
            _royaltyService.generatePackingSlip(order);
    }
}
