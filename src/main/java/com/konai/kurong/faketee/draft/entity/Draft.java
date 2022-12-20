package com.konai.kurong.faketee.draft.entity;

import com.konai.kurong.faketee.draft.utils.*;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "DRAFT_SEQUENCE_GENERATOR",
        sequenceName = "DRAFT_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "DRAFT")
@Entity
public class Draft extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "DRAFT_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APVL_DATE")
    private LocalDateTime approvalDate;

    @Column(name = "REQ_DATE")
    private LocalDateTime requestDate;

    @Column(name = "APVL_MSG")
    private String approvalMessage;

    @Convert(converter = DraftStateCodeAttributeConverter.class)
    @Column(name = "STATE_CODE")
    private DraftStateCode stateCode;

    @Column(name = "REQ_MSG")
    private String requestMessage;

    @Convert(converter = DraftRequestTypeAttributeConverter.class)
    @Column(name = "REQ_TYPE")
    private DraftRequestType requestType;

    @Convert(converter = DraftCrudTypeAttributeConverter.class)
    @Column(name = "CRUD_TYPE")
    private DraftCrudType crudType;

    @ManyToOne
    @JoinColumn(name = "REQ_EMP_ID")
    private Employee requestEmployee;

    @ManyToOne
    @JoinColumn(name = "APVL_EMP_ID_1")
    private Employee approvalEmp1;

    @ManyToOne
    @JoinColumn(name = "APVL_EMP_ID_FIN")
    private Employee approvalEmpFin;

    @BatchSize(size = 10)
    @OneToMany(fetch = LAZY, mappedBy = "draft")
    @Builder.Default
    private List<VacRequest> vacRequestList = new ArrayList<>();

    public void updateCrudType(DraftCrudType crudType) {
        this.crudType = crudType;
    }

    public void updateStateCode(DraftStateCode stateCode) {
        this.stateCode = stateCode;
    }
}
