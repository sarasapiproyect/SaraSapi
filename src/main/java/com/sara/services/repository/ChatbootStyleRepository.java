package com.sara.services.repository;

import com.sara.services.domain.ChatbootStyle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChatbootStyle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChatbootStyleRepository extends JpaRepository<ChatbootStyle, Long> {}
