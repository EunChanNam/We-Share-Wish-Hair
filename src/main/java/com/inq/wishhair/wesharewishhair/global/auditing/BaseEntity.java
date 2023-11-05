package com.inq.wishhair.wesharewishhair.global.auditing;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @Column(updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
