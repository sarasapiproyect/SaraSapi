package com.sara.services.repository;

import com.sara.services.domain.Intent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IntentRepositoryWithBagRelationships {
    Optional<Intent> fetchBagRelationships(Optional<Intent> intent);

    List<Intent> fetchBagRelationships(List<Intent> intents);

    Page<Intent> fetchBagRelationships(Page<Intent> intents);
}
