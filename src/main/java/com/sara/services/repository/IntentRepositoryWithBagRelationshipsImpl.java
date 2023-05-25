package com.sara.services.repository;

import com.sara.services.domain.Intent;
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
public class IntentRepositoryWithBagRelationshipsImpl implements IntentRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Intent> fetchBagRelationships(Optional<Intent> intent) {
        return intent.map(this::fetchUserExpresions).map(this::fetchUserResponses);
    }

    @Override
    public Page<Intent> fetchBagRelationships(Page<Intent> intents) {
        return new PageImpl<>(fetchBagRelationships(intents.getContent()), intents.getPageable(), intents.getTotalElements());
    }

    @Override
    public List<Intent> fetchBagRelationships(List<Intent> intents) {
        return Optional.of(intents).map(this::fetchUserExpresions).map(this::fetchUserResponses).orElse(Collections.emptyList());
    }

    Intent fetchUserExpresions(Intent result) {
        return entityManager
            .createQuery("select intent from Intent intent left join fetch intent.userExpresions where intent is :intent", Intent.class)
            .setParameter("intent", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Intent> fetchUserExpresions(List<Intent> intents) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, intents.size()).forEach(index -> order.put(intents.get(index).getId(), index));
        List<Intent> result = entityManager
            .createQuery(
                "select distinct intent from Intent intent left join fetch intent.userExpresions where intent in :intents",
                Intent.class
            )
            .setParameter("intents", intents)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Intent fetchUserResponses(Intent result) {
        return entityManager
            .createQuery("select intent from Intent intent left join fetch intent.userResponses where intent is :intent", Intent.class)
            .setParameter("intent", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Intent> fetchUserResponses(List<Intent> intents) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, intents.size()).forEach(index -> order.put(intents.get(index).getId(), index));
        List<Intent> result = entityManager
            .createQuery(
                "select distinct intent from Intent intent left join fetch intent.userResponses where intent in :intents",
                Intent.class
            )
            .setParameter("intents", intents)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
