package com.tempo.kata16.rules;

import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Membership;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.repositories.MembershipRepository;
import com.tempo.kata16.services.NotificationService;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class MembershipActivateRuleTests{
    @Test
    public void runShouldDoNothingIfNoMembershipsInOrder() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Physical
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        MembershipRepository service = mock(MembershipRepository.class);
        NotificationService notificationService = mock(NotificationService.class);
        Rule sut = new MembershipActivateRule(service, notificationService);
        sut.run(payment);

        verify(customer, never()).addMembership(any(), any());
    }

    @Test
    public void runShouldActivateMembershipIfInOrder() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Membership
            })
        };
        Customer customer = mock(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        MembershipRepository repo = mock(MembershipRepository.class);
        Membership membership = new Membership("item1", null);
        when(repo.findBySku("item1")).thenReturn(membership);

        NotificationService notificationService = mock(NotificationService.class);

        Rule sut = new MembershipActivateRule(repo, notificationService);
        sut.run(payment);

        verify(customer, times(1)).addMembership(membership, notificationService);
    }

    @Test
    public void runShouldNotifyCustomer() throws Exception {
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item1", "item1", new ProductCategory[]{
                ProductCategory.Membership
            })
        };
        Customer customer = spy(Customer.class);
        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        MembershipRepository repo = mock(MembershipRepository.class);
        Membership membership = new Membership("item1", null);
        when(repo.findBySku("item1")).thenReturn(membership);

        NotificationService notificationService = mock(NotificationService.class);

        Rule sut = new MembershipActivateRule(repo, notificationService);
        sut.run(payment);

        verify(notificationService, times(1)).notify(customer, membership);
    }
}