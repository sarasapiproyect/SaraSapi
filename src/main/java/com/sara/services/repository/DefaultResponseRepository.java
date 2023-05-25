package com.sara.services.repository;

import com.sara.services.domain.DefaultResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DefaultResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefaultResponseRepository extends JpaRepository<DefaultResponse, Long> {}
