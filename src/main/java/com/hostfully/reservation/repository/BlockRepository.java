package com.hostfully.reservation.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hostfully.reservation.entity.Block;

@Repository
public interface BlockRepository extends JpaRepository<Block, String>{

	@Query(value = "SELECT COALESCE((SELECT COUNT(*) FROM block b WHERE b.property_id = :propertyId AND " +
	        " :date BETWEEN b.start_date AND b.end_date GROUP BY b.property_id), 0) > 0",
	        nativeQuery = true)
	    Boolean doesOverlap(@Param(value = "date") OffsetDateTime date, @Param(value = "propertyId") String propertyId);
}
