package com.hib.hibenatemysql.domains.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class PreDefined {

    @Column(length = 20)
    @CreatedBy
    private String createdBy;
    @Column(length = 20)
    @LastModifiedBy
    private String modifiedBy;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;

}
