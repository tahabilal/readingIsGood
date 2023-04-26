package com.example.readingisgood.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Audited
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Book extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2341232435L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    private LocalDate publishDate;
    private Integer stock;
    private Double price;

    @Version
    private Long version;

    public Book(String name, String author, LocalDate publishDate, int stock, double price){
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.stock = stock;
        this.price = price;
    }

    public void reduceStock(int bookAmount) throws Exception {
        if(this.stock - bookAmount < 0) throw new Exception("There is no enough stock for this order");
        this.stock -= bookAmount;
    }
}
