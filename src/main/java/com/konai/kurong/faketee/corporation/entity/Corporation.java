package com.konai.kurong.faketee.corporation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COR")
public class Corporation {
    @Id
    @SequenceGenerator(name = "COR_ID_GENERATOR", sequenceName = "COR_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COR_ID_GENERATOR")
    private Long id;

    private String name;

    @Column(name = "CRE_DTTM")
    private LocalDateTime createdDateTime;

    @Column(name = "CRE_ID")
    private String createdId;

    @Column(name = "UPD_DTTM")
    private LocalDateTime updateDateTime;

    @Column(name = "UPD_ID")
    private String updatedId;

}
