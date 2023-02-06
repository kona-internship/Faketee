package com.konai.kurong.faketee.department.entity;

import com.konai.kurong.faketee.location.entity.Location;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="DEP_LOC_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="DEP_LOC_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "DEP_LOC")
public class DepLoc extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "DEP_LOC_ID_GENERATOR"
    )
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "LOC_ID")
    private Location location;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DEP_ID")
    private Department department;
}
