package com.todolist.todolist.dao.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is created in order to avoid boilerplate codes which will be used almost each class
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "reg_user", nullable = false, updatable = false)
    protected U regUserId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime regDate;

    @LastModifiedBy
    @Column(name = "edit_user")
    protected U editUserId;

    @LastModifiedDate
    @Column(name = "edit_date")
    protected LocalDateTime editDate;

    @Column(name = "in_power")
    protected Boolean inPower;

}
