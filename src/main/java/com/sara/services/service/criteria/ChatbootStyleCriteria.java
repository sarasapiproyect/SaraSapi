package com.sara.services.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.ChatbootStyle} entity. This class is used
 * in {@link com.sara.services.web.rest.ChatbootStyleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chatboot-styles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChatbootStyleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameProperties;

    private StringFilter value;

    private Boolean distinct;

    public ChatbootStyleCriteria() {}

    public ChatbootStyleCriteria(ChatbootStyleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameProperties = other.nameProperties == null ? null : other.nameProperties.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ChatbootStyleCriteria copy() {
        return new ChatbootStyleCriteria(this);
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

    public StringFilter getNameProperties() {
        return nameProperties;
    }

    public StringFilter nameProperties() {
        if (nameProperties == null) {
            nameProperties = new StringFilter();
        }
        return nameProperties;
    }

    public void setNameProperties(StringFilter nameProperties) {
        this.nameProperties = nameProperties;
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
        final ChatbootStyleCriteria that = (ChatbootStyleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nameProperties, that.nameProperties) &&
            Objects.equals(value, that.value) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProperties, value, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatbootStyleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nameProperties != null ? "nameProperties=" + nameProperties + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
