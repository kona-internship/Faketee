package com.konai.kurong.faketee.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="EMP_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="EMP_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "EMP")
public class Employee {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "EMP_ID_GENERATOR"
    )
    private Long id;
}
