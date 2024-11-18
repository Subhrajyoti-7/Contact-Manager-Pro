package com.scm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Feedback {

    @Id
    private String fid;

    @Column(length = 60)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 1000)
    private String msg;

}
