package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "TMP_POS_SEQUENCE_GENERATOR",
        sequenceName = "TMP_POS_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "TMP_POS")
@Entity
public class TemplatePosition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "TMP_POS_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POS_ID")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "TMP_ID")
    private Template template;

}
