package com.wp.finki.ukim.mk.catalogware.model;

import javax.persistence.*;

/**
 * Created by Borce on 30.08.2016.
 */
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public BaseModel() {
    }

    public BaseModel(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
