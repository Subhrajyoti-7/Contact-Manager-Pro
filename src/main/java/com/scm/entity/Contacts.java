package com.scm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contacts {

    @Id
    @Column(length = 100)
    private String cid;

    @Column(length = 60)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 200)
    private String address;

    @Column(length = 200)
    private String pic;

    @Column(length = 200)
    private String facebook;

    @Column(length = 200)
    private String instagram;

    @Builder.Default
    private boolean favorite = false;

    private String cloudinaryImagePublicId;

    // If there are many links we will use this for another table
    // @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY, orphanRemoval = true)
    // private List<SocialLinks> socialLinks = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private Users user;

}
