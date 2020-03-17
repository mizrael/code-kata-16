package com.tempo.kata16.rules;

import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Membership;
import com.tempo.kata16.domain.ProductCategory;
import com.tempo.kata16.repositories.MembershipRepository;
import com.tempo.kata16.services.NotificationService;

// If the payment is an upgrade to a membership, apply the upgrade.
// If the payment is for a membership or upgrade, e-mail the owner and inform them of the activation/upgrade.
public class MembershipUpgradeRule implements Rule {

    private final MembershipRepository _repo;
    private final NotificationService _notificationService;
    
    public MembershipUpgradeRule(final MembershipRepository repo, final NotificationService notificationService) {
        _repo = repo;
        _notificationService = notificationService;
	}

	@Override
    public void run(Payment payment) {
        Order order = payment.getOrder();
        Customer customer = order.getCustomer();
        LineItem[] lineItems = order.getLineItems();
        for (LineItem lineItem : lineItems) {
            if(!lineItem.hasCategory(ProductCategory.Membership))
                continue;

            Membership membership = _repo.findBySku(lineItem.getSku());
            Membership previousLevel = membership.getPreviousLevel();
            if(customer.hasMembership(previousLevel))
                customer.addMembership(membership, _notificationService);
        }
    }
}