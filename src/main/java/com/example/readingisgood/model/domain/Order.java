package com.example.readingisgood.model.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "orders")
@Data
@Audited
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Order extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 45689453044029485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private Double totalPrice;

    @NotAudited
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks = new ArrayList<>();

    @NotAudited
    @ManyToOne
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addOrderBook(OrderBook orderBook){
        orderBooks.add(orderBook);
    }
}
