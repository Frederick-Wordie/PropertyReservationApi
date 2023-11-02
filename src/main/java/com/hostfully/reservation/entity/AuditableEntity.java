package com.hostfully.reservation.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * This class adds support for entity listener.
 * It will track the creation date and modification date of the entity extending this class.
 */
@JsonIgnoreProperties(value = {"createdDate", "lastModifiedDate"})
@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "created_date", updatable = false)
    protected OffsetDateTime createdDate;
	
    @Column(name = "last_modified_date")
    private OffsetDateTime lastModifiedDate;

}

