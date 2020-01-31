package com.can.app.swim.swimapp.entity;

import com.can.app.swim.swimapp.enums.EnumRole;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRole name;

    public Role() {
    }

    public Role(EnumRole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRole getName() {
        return name;
    }

    public void setName(EnumRole name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
