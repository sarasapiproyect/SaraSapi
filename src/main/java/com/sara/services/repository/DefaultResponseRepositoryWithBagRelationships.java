package com.sara.services.repository;

import com.sara.services.domain.DefaultResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DefaultResponseRepositoryWithBagRelationships {
    Optional<DefaultResponse> fetchBagRelationships(Optional<DefaultResponse> defaultResponse);

    List<DefaultResponse> fetchBagRelationships(List<DefaultResponse> defaultResponses);

    Page<DefaultResponse> fetchBagRelationships(Page<DefaultResponse> defaultResponses);
}
