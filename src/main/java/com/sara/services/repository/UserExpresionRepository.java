package com.sara.services.repository;

import com.sara.services.domain.UserExpresion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserExpresion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExpresionRepository extends JpaRepository<UserExpresion, Long> {}
