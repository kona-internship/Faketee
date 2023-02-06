package com.konai.kurong.faketee.location.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOC")
public class Location extends BaseEntity {
    @Id
    @SequenceGenerator(name = "LOC_ID_GENERATOR", sequenceName = "LOC_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOC_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LAT")
    private BigDecimal lat;

    @Column(name = "LNG")
    private BigDecimal lng;

    @Column(name = "ADDR")
    private String address;

    @Column(name = "RAD")
    private Long radius;

    @ManyToOne
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;
}
