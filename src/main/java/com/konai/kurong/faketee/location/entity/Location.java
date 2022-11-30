package com.konai.kurong.faketee.location.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOC")
public class Location extends BaseUserEntity {
    @Id
    @SequenceGenerator(name = "LOC_ID_GENERATOR", sequenceName = "LOC_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOC_ID_GENERATOR")
    private Long id;

    private String name;

    private BigDecimal lat;

    private BigDecimal lng;

    @Column(name = "ADDR")
    private String address;
    @Column(name = "RAD")
    private Long radius;

    @ManyToOne
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;
}
