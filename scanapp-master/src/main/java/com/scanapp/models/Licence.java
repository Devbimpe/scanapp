package com.scanapp.models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "licence")
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  Long id ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

