package com.can.app.swim.swimapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "children")
@Data
@NoArgsConstructor
public class Children extends UserBase{

    @JsonIgnore
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    protected User parent;

}
