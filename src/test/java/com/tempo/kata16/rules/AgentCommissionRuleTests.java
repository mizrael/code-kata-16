package com.tempo.kata16.rules;

import static org.mockito.Mockito.*;

import com.tempo.kata16.domain.Agent;
import com.tempo.kata16.domain.Customer;
import com.tempo.kata16.domain.LineItem;
import com.tempo.kata16.domain.Order;
import com.tempo.kata16.domain.Payment;
import com.tempo.kata16.domain.ProductCategory;

import org.junit.Test;

public class AgentCommissionRuleTests{
    @Test
    public void runShouldNotGenerateAgentCommissionIfItemsInvalid(){
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item", "item", new ProductCategory[]{
                ProductCategory.Membership,
                ProductCategory.Virtual,
            })
        };
        Customer customer = mock(Customer.class);
        Agent agent = mock(Agent.class);
        Order order = new Order(customer, lineItems, agent);
        Payment payment = new Payment(order);

        Rule sut = new AgentCommissionRule();
        sut.run(payment);

        verify(agent, never()).generateCommission(any());
    }

    @Test
    public void runShouldGenerateAgentCommissionIfBookInOrder(){
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item", "item", new ProductCategory[]{
                ProductCategory.Membership
            }),
            new LineItem("book1", "book1", new ProductCategory[]{
                ProductCategory.Books
            })
        };
        Customer customer = mock(Customer.class);
        Agent agent = mock(Agent.class);
        Order order = new Order(customer, lineItems, agent);
        Payment payment = new Payment(order);

        Rule sut = new AgentCommissionRule();
        sut.run(payment);

        verify(agent, times(1)).generateCommission(any());
    }

    @Test
    public void runShouldGenerateAgentCommissionOnceIfMultipleBooksInOrder(){
        LineItem[] lineItems = new LineItem[]{
            new LineItem("book1", "book1", new ProductCategory[]{
                ProductCategory.Books
            }),
            new LineItem("book2", "book2", new ProductCategory[]{
                ProductCategory.Books
            }),
            new LineItem("book3", "book3", new ProductCategory[]{
                ProductCategory.Books
            })
        };
        Customer customer = mock(Customer.class);
        Agent agent = mock(Agent.class);
        Order order = new Order(customer, lineItems, agent);
        Payment payment = new Payment(order);

        Rule sut = new AgentCommissionRule();
        sut.run(payment);

        verify(agent, times(1)).generateCommission(any());
    }

    @Test
    public void runShouldGenerateAgentCommissionIfPhysicalItemInOrder(){
        LineItem[] lineItems = new LineItem[]{
            new LineItem("item", "item", new ProductCategory[]{
                ProductCategory.Physical
            }),
            new LineItem("membership", "membership", new ProductCategory[]{
                ProductCategory.Membership
            })
        };
        Customer customer = mock(Customer.class);
        Agent agent = mock(Agent.class);
        Order order = new Order(customer, lineItems, agent);
        Payment payment = new Payment(order);

        Rule sut = new AgentCommissionRule();
        sut.run(payment);

        verify(agent, times(1)).generateCommission(any());
    }
}