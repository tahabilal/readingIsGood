package com.example.readingisgood.model.domain;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Customer  implements Serializable {

    private static final long serialVersionUID = 6794563434657L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String address;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    private User user;
}
