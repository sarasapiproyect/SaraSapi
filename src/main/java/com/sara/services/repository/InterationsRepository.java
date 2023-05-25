package com.sara.services.repository;

import com.sara.services.domain.Interations;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Interations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterationsRepository extends JpaRepository<Interations, Long> {}
