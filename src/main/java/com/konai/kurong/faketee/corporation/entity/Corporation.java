package com.konai.kurong.faketee.corporation.entity;

import com.konai.kurong.faketee.utils.jpa_auditing.BaseUserEntity;
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
public class Corporation extends BaseUserEntity {
    @Id
    @SequenceGenerator(name = "COR_ID_GENERATOR", sequenceName = "COR_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COR_ID_GENERATOR")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
