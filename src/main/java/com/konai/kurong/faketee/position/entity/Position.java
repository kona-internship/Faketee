package com.konai.kurong.faketee.position.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="POS_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="POS_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "POS")
public class Position {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "POS_ID_GENERATOR"
    )
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;

    @Column(name = "CRE_DTTM")
    private LocalDateTime createdDateTime;

    @Column(name = "CRE_ID")
    private String createdId;

    @Column(name = "UPD_DTTM")
    private LocalDateTime updateDateTime;

    @Column(name = "UPD_ID")
    private String updatedId;

}
