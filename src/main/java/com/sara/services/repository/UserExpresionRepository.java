package com.sara.services.repository;

import com.sara.services.domain.UserExpresion;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserExpresion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExpresionRepository extends JpaRepository<UserExpresion, Long> {
	
	  @Query(value = "SELECT * FROM user_expresion s WHERE s.value = ?1 order by s.id desc",
	            nativeQuery = true)
	List<UserExpresion> findByValue(String value);
}
