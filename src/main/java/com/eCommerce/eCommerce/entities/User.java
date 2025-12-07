package com.eCommerce.eCommerce.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.cdi.Eager;

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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Profile profile;


    @OneToMany(mappedBy = "user")
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
            name = "favoriteProduct",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns =  @JoinColumn(name = "product_id")
    )
    private Set<Product> favoiriteProduct = new HashSet<>();

    public boolean verfiyPassword(String password){
        return this.password.equals( password);

    }





}
