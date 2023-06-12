package com.sara.services.repository;

import com.sara.services.domain.UserResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserResponseRepositoryWithBagRelationshipsImpl implements UserResponseRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserResponse> fetchBagRelationships(Optional<UserResponse> userResponse) {
        return userResponse.map(this::fetchChannelMultimedias).map(this::fetchChannelVoices).map(this::fetchChannelAnimations);
    }

    @Override
    public Page<UserResponse> fetchBagRelationships(Page<UserResponse> userResponses) {
        return new PageImpl<>(
            fetchBagRelationships(userResponses.getContent()),
            userResponses.getPageable(),
            userResponses.getTotalElements()
        );
    }

    @Override
    public List<UserResponse> fetchBagRelationships(List<UserResponse> userResponses) {
        return Optional
            .of(userResponses)
            .map(this::fetchChannelMultimedias)
            .map(this::fetchChannelVoices)
            .map(this::fetchChannelAnimations)
            .orElse(Collections.emptyList());
    }

    UserResponse fetchChannelMultimedias(UserResponse result) {
        return entityManager
            .createQuery(
                "select userResponse from UserResponse userResponse left join fetch userResponse.channelMultimedias where userResponse is :userResponse",
                UserResponse.class
            )
            .setParameter("userResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserResponse> fetchChannelMultimedias(List<UserResponse> userResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userResponses.size()).forEach(index -> order.put(userResponses.get(index).getId(), index));
        List<UserResponse> result = entityManager
            .createQuery(
                "select distinct userResponse from UserResponse userResponse left join fetch userResponse.channelMultimedias where userResponse in :userResponses",
                UserResponse.class
            )
            .setParameter("userResponses", userResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    UserResponse fetchChannelVoices(UserResponse result) {
        return entityManager
            .createQuery(
                "select userResponse from UserResponse userResponse left join fetch userResponse.channelVoices where userResponse is :userResponse",
                UserResponse.class
            )
            .setParameter("userResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserResponse> fetchChannelVoices(List<UserResponse> userResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userResponses.size()).forEach(index -> order.put(userResponses.get(index).getId(), index));
        List<UserResponse> result = entityManager
            .createQuery(
                "select distinct userResponse from UserResponse userResponse left join fetch userResponse.channelVoices where userResponse in :userResponses",
                UserResponse.class
            )
            .setParameter("userResponses", userResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    UserResponse fetchChannelAnimations(UserResponse result) {
        return entityManager
            .createQuery(
                "select userResponse from UserResponse userResponse left join fetch userResponse.channelAnimations where userResponse is :userResponse",
                UserResponse.class
            )
            .setParameter("userResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserResponse> fetchChannelAnimations(List<UserResponse> userResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userResponses.size()).forEach(index -> order.put(userResponses.get(index).getId(), index));
        List<UserResponse> result = entityManager
            .createQuery(
                "select distinct userResponse from UserResponse userResponse left join fetch userResponse.channelAnimations where userResponse in :userResponses",
                UserResponse.class
            )
            .setParameter("userResponses", userResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
