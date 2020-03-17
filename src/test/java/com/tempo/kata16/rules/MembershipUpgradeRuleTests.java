package com.tempo.kata16.rules;

import static org.mockito.Mockito.*;

import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Membership;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.repositories.MembershipRepository;
import com.tempo.kata16.services.NotificationService;

import org.junit.Test;

public class MembershipUpgradeRuleTests{
    @Test
    public void runShouldUpgradeMembership() throws Exception {
        Membership membershipSilver = new Membership("membership-silver", null);
        Membership membershipGold = new Membership("membership-gold", membershipSilver);

        LineItem[] lineItems = new LineItem[]{
            new LineItem(membershipGold.getSku(), "gold", new ProductCategory[]{
                ProductCategory.Membership
            })
        };
        Customer customer = mock(Customer.class);
        when(customer.hasMembership(membershipSilver)).thenReturn(true);

        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        MembershipRepository repo = mock(MembershipRepository.class);
        when(repo.findBySku(membershipGold.getSku())).thenReturn(membershipGold);
        
        NotificationService notificationService = mock(NotificationService.class);

        Rule sut = new MembershipUpgradeRule(repo, notificationService);
        sut.run(payment);

        verify(customer, times(1)).addMembership(membershipGold, notificationService);
    }

    @Test
    public void runShouldNotifyCustomer() throws Exception {
        Membership membershipSilver = new Membership("membership-silver", null);
        Membership membershipGold = new Membership("membership-gold", membershipSilver);

        LineItem[] lineItems = new LineItem[]{
            new LineItem(membershipGold.getSku(), "gold", new ProductCategory[]{
                ProductCategory.Membership
            })
        };
        Customer customer = spy(Customer.class);
        when(customer.hasMembership(membershipSilver)).thenReturn(true);

        Order order = new Order(customer, lineItems, null);
        Payment payment = new Payment(order);

        MembershipRepository repo = mock(MembershipRepository.class);
        when(repo.findBySku(membershipGold.getSku())).thenReturn(membershipGold);
        
        NotificationService notificationService = mock(NotificationService.class);

        Rule sut = new MembershipUpgradeRule(repo, notificationService);
        sut.run(payment);

        verify(notificationService, times(1)).notify(customer, membershipGold);
    }
}