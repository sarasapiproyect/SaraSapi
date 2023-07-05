package com.sara.services.repository;

import com.sara.services.domain.Contacts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contacts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long>, JpaSpecificationExecutor<Contacts> {}
