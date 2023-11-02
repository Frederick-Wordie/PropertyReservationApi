package com.hostfully.reservation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Property Object
 */

@Entity
@Getter
@Setter
@Table(name = "property")
public class Property extends AuditableEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Column
    private String name;
	
    @Column
	private String address;
	
    @Column
	private String description;
}
