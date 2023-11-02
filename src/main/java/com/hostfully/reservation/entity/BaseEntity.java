package com.hostfully.reservation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    protected String id;
}
