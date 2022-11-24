package com.konai.kurong.faketee.util.jpa_auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    private LocalDateTime CRE_DTTM;

    @LastModifiedDate
    private LocalDateTime UPD_DTTM;

    @CreatedBy
    private Long CRE_ID;

    @LastModifiedBy
    private Long UPD_ID;
}
