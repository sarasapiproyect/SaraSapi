package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.UserExpresion} entity. This class is used
 * in {@link com.sara.services.web.rest.UserExpresionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-expresions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExpresionCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Priority
     */
    public static class PriorityFilter extends Filter<Priority> {

        public PriorityFilter() {}

        public PriorityFilter(PriorityFilter filter) {
            super(filter);
        }

        @Override
        public PriorityFilter copy() {
            return new PriorityFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private PriorityFilter priority;

    private LongFilter intentId;

    private Boolean distinct;

    public UserExpresionCriteria() {}

    public UserExpresionCriteria(UserExpresionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.intentId = other.intentId == null ? null : other.intentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserExpresionCriteria copy() {
        return new UserExpresionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public StringFilter value() {
        if (value == null) {
            value = new StringFilter();
        }
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public PriorityFilter getPriority() {
        return priority;
    }

    public PriorityFilter priority() {
        if (priority == null) {
            priority = new PriorityFilter();
        }
        return priority;
    }

    public void setPriority(PriorityFilter priority) {
        this.priority = priority;
    }

    public LongFilter getIntentId() {
        return intentId;
    }

    public LongFilter intentId() {
        if (intentId == null) {
            intentId = new LongFilter();
        }
        return intentId;
    }

    public void setIntentId(LongFilter intentId) {
        this.intentId = intentId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserExpresionCriteria that = (UserExpresionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(intentId, that.intentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, priority, intentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExpresionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (intentId != null ? "intentId=" + intentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
