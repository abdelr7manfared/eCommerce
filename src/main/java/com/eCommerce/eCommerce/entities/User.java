package com.eCommerce.eCommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private Long id;

    @JoinColumn(name = "name")
    private String name;


    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "password")
    private String password;

    @OneToOne(mappedBy = "user_id")
    private Profile profile;

    @OneToMany(mappedBy = "user_id")
    private List<Addresses> addresses = new ArrayList<>();

    public void addAddresses(Addresses address){
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddresses(Addresses address){
        addresses.remove(address);
        address.setUser(null);
    }

    @ManyToMany
    @JoinTable(
            name = "favoiriteProduct",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "product_id")
    )
    private Set<Product> favoiriteProduct = new HashSet<>();





}
