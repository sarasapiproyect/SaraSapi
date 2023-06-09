package com.sara.services.repository;

import com.sara.services.domain.UserResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserResponseRepositoryWithBagRelationships {
    Optional<UserResponse> fetchBagRelationships(Optional<UserResponse> userResponse);

    List<UserResponse> fetchBagRelationships(List<UserResponse> userResponses);

    Page<UserResponse> fetchBagRelationships(Page<UserResponse> userResponses);
}
