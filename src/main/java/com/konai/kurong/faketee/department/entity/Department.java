package com.konai.kurong.faketee.department.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="DEP_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="DEP_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "DEP")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "DEP_ID_GENERATOR"
    )
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEP_LEVEL")
    private Long level;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DEP_ID")
    private Department superDepartment;

    @OneToMany(fetch = LAZY, mappedBy = "superDepartment", orphanRemoval = true)
    @Builder.Default
    private List<Department> childDepartments = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;

    @OneToMany(mappedBy = "department", fetch = LAZY)
    private List<TemplateDepartment> templateDepartments = new ArrayList<>();

    public void changeName(String name){
        this.name = name;
    }
}
