package com.tempo.kata16.paymenthandlers;

import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.services.PackingSlipService;
import com.tempo.kata16.services.RoyaltyService;
import com.tempo.kata16.services.ShippingService;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class PackingSlipHandlerTests{
    public PackingSlipHandlerTests(){}

    @Test
    public void runShouldNotGenerateForShippingWhenNoValidItemsAvailable() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Membership
            }),
            new LineItem("item2", "item2", new ProductCategory[]{
                ProductCategory.Virtual,
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        PackingSlipService packingSlipService = mock(PackingSlipService.class);
        ShippingService shippingService = mock(ShippingService.class);
        RoyaltyService royaltyService = mock(RoyaltyService.class);
        
        PaymentHandler sut = new PackingSlipHandler(shippingService, royaltyService, packingSlipService);
        sut.run(payment);

        verify(shippingService, never()).generatePackingSlip(order);
    }

    @Test
    public void runShouldGenerateForShippingWhenPhysicalAvailable() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Physical
            }),
            new LineItem("item2", "item2", new ProductCategory[]{
                ProductCategory.Membership,
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        PackingSlipService packingSlipService = mock(PackingSlipService.class);
        ShippingService shippingService = mock(ShippingService.class);
        RoyaltyService royaltyService = mock(RoyaltyService.class);
        
        PaymentHandler sut = new PackingSlipHandler(shippingService, royaltyService, packingSlipService);
        sut.run(payment);

        verify(shippingService, times(1)).generatePackingSlip(order);
    }

    @Test
    public void runShouldGenerateForRoyaltyWhenBooksAvailable() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Books
            }),
            new LineItem("item2", "item2", new ProductCategory[]{
                ProductCategory.Membership,
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        PackingSlipService packingSlipService = mock(PackingSlipService.class);
        ShippingService shippingService = mock(ShippingService.class);
        RoyaltyService royaltyService = mock(RoyaltyService.class);
        
        PaymentHandler sut = new PackingSlipHandler(shippingService, royaltyService, packingSlipService);
        sut.run(payment);

        verify(royaltyService, times(1)).generatePackingSlip(order);
    }

    @Test
    public void runShouldAddFirstAidGiftWhenRequested() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("learning-to-ski", "Learning to Ski", new ProductCategory[]{
                ProductCategory.Videos
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        PackingSlipService packingSlipService = mock(PackingSlipService.class);
        ShippingService shippingService = mock(ShippingService.class);
        RoyaltyService royaltyService = mock(RoyaltyService.class);
        
        PaymentHandler sut = new PackingSlipHandler(shippingService, royaltyService, packingSlipService);
        sut.run(payment);

        String[] gifts = order.getGiftSkus();
        assertNotNull(gifts);
        assertTrue(gifts.length == 1);
        assertTrue(Arrays.binarySearch(gifts, "first-aid") == 0); 
    }

    @Test
    public void runShouldGeneratePackingSlip() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Physical
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        PackingSlipService packingSlipService = mock(PackingSlipService.class);
        ShippingService shippingService = mock(ShippingService.class);
        RoyaltyService royaltyService = mock(RoyaltyService.class);
        
        PaymentHandler sut = new PackingSlipHandler(shippingService, royaltyService, packingSlipService);
        sut.run(payment);

        verify(packingSlipService, times(1)).generate(order);
    }
}