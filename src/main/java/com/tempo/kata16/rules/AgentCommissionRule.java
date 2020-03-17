package com.tempo.kata16.rules;

import com.tempo.kata16.domain.Agent;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;

// If the payment is for a physical product or a book, generate a commission payment to the agent.
public class AgentCommissionRule implements Rule{

    @Override
    public void run(Payment payment) {
        Order order = payment.getOrder();
        LineItem[] lineItems = order.getLineItems();

        Boolean addCommission = false;

        for (LineItem lineItem : lineItems) {
            if(lineItem.hasCategory(ProductCategory.Books) || lineItem.hasCategory(ProductCategory.Physical)){
                addCommission = true;
                break;
            }
        }

        if(addCommission){
            Agent agent = order.getAgent();
            agent.generateCommission(order);
        }
    }

}