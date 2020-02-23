package com.can.app.swim.swimapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "amount")
    private BigDecimal amount;

}
