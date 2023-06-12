package com.sara.services.repository;

import com.sara.services.domain.DefaultResponse;
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
public class DefaultResponseRepositoryWithBagRelationshipsImpl implements DefaultResponseRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DefaultResponse> fetchBagRelationships(Optional<DefaultResponse> defaultResponse) {
        return defaultResponse.map(this::fetchChannelMultimedias).map(this::fetchChannelVoices).map(this::fetchChannelAnimations);
    }

    @Override
    public Page<DefaultResponse> fetchBagRelationships(Page<DefaultResponse> defaultResponses) {
        return new PageImpl<>(
            fetchBagRelationships(defaultResponses.getContent()),
            defaultResponses.getPageable(),
            defaultResponses.getTotalElements()
        );
    }

    @Override
    public List<DefaultResponse> fetchBagRelationships(List<DefaultResponse> defaultResponses) {
        return Optional
            .of(defaultResponses)
            .map(this::fetchChannelMultimedias)
            .map(this::fetchChannelVoices)
            .map(this::fetchChannelAnimations)
            .orElse(Collections.emptyList());
    }

    DefaultResponse fetchChannelMultimedias(DefaultResponse result) {
        return entityManager
            .createQuery(
                "select defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelMultimedias where defaultResponse is :defaultResponse",
                DefaultResponse.class
            )
            .setParameter("defaultResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DefaultResponse> fetchChannelMultimedias(List<DefaultResponse> defaultResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, defaultResponses.size()).forEach(index -> order.put(defaultResponses.get(index).getId(), index));
        List<DefaultResponse> result = entityManager
            .createQuery(
                "select distinct defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelMultimedias where defaultResponse in :defaultResponses",
                DefaultResponse.class
            )
            .setParameter("defaultResponses", defaultResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    DefaultResponse fetchChannelVoices(DefaultResponse result) {
        return entityManager
            .createQuery(
                "select defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelVoices where defaultResponse is :defaultResponse",
                DefaultResponse.class
            )
            .setParameter("defaultResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DefaultResponse> fetchChannelVoices(List<DefaultResponse> defaultResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, defaultResponses.size()).forEach(index -> order.put(defaultResponses.get(index).getId(), index));
        List<DefaultResponse> result = entityManager
            .createQuery(
                "select distinct defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelVoices where defaultResponse in :defaultResponses",
                DefaultResponse.class
            )
            .setParameter("defaultResponses", defaultResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    DefaultResponse fetchChannelAnimations(DefaultResponse result) {
        return entityManager
            .createQuery(
                "select defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelAnimations where defaultResponse is :defaultResponse",
                DefaultResponse.class
            )
            .setParameter("defaultResponse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DefaultResponse> fetchChannelAnimations(List<DefaultResponse> defaultResponses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, defaultResponses.size()).forEach(index -> order.put(defaultResponses.get(index).getId(), index));
        List<DefaultResponse> result = entityManager
            .createQuery(
                "select distinct defaultResponse from DefaultResponse defaultResponse left join fetch defaultResponse.channelAnimations where defaultResponse in :defaultResponses",
                DefaultResponse.class
            )
            .setParameter("defaultResponses", defaultResponses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
