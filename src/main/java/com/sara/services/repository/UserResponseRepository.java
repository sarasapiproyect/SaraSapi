package com.sara.services.repository;

import com.sara.services.domain.UserResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {}
