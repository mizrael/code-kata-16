package com.tempo.kata16.paymenthandlers;

import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.services.PackingSlipService;

import org.junit.Test;
import static org.mockito.Mockito.*;

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

        PackingSlipService service = mock(PackingSlipService.class);
        
        PaymentHandler sut = new PackingSlipHandler(service);
        sut.run(payment);

        verify(service, never()).generateForShipping(payment);
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

        PackingSlipService service = mock(PackingSlipService.class);
        
        PaymentHandler sut = new PackingSlipHandler(service);
        sut.run(payment);

        verify(service, times(1)).generateForShipping(payment);
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

        PackingSlipService service = mock(PackingSlipService.class);
        
        PaymentHandler sut = new PackingSlipHandler(service);
        sut.run(payment);

        verify(service, times(1)).generateForRoyalty(payment);
    }
}