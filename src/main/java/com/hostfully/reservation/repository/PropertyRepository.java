package com.hostfully.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hostfully.reservation.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String>{

}
