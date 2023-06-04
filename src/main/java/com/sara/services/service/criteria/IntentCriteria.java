package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.IntentType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.Intent} entity. This class is used
 * in {@link com.sara.services.web.rest.IntentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /intents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering IntentType
     */
    public static class IntentTypeFilter extends Filter<IntentType> {

        public IntentTypeFilter() {}

        public IntentTypeFilter(IntentTypeFilter filter) {
            super(filter);
        }

        @Override
        public IntentTypeFilter copy() {
            return new IntentTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntentTypeFilter intenType;

    private StringFilter name;

    private StringFilter description;

    private StringFilter urlRequest;

    private BooleanFilter enabled;

    private InstantFilter creationDate;

    private LongFilter languajeId;

    private LongFilter userExpresionId;

    private LongFilter userResponseId;

    private Boolean distinct;

    public IntentCriteria() {}

    public IntentCriteria(IntentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.intenType = other.intenType == null ? null : other.intenType.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.urlRequest = other.urlRequest == null ? null : other.urlRequest.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.languajeId = other.languajeId == null ? null : other.languajeId.copy();
        this.userExpresionId = other.userExpresionId == null ? null : other.userExpresionId.copy();
        this.userResponseId = other.userResponseId == null ? null : other.userResponseId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IntentCriteria copy() {
        return new IntentCriteria(this);
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

    public IntentTypeFilter getIntenType() {
        return intenType;
    }

    public IntentTypeFilter intenType() {
        if (intenType == null) {
            intenType = new IntentTypeFilter();
        }
        return intenType;
    }

    public void setIntenType(IntentTypeFilter intenType) {
        this.intenType = intenType;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getUrlRequest() {
        return urlRequest;
    }

    public StringFilter urlRequest() {
        if (urlRequest == null) {
            urlRequest = new StringFilter();
        }
        return urlRequest;
    }

    public void setUrlRequest(StringFilter urlRequest) {
        this.urlRequest = urlRequest;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public BooleanFilter enabled() {
        if (enabled == null) {
            enabled = new BooleanFilter();
        }
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public InstantFilter creationDate() {
        if (creationDate == null) {
            creationDate = new InstantFilter();
        }
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    public LongFilter getLanguajeId() {
        return languajeId;
    }

    public LongFilter languajeId() {
        if (languajeId == null) {
            languajeId = new LongFilter();
        }
        return languajeId;
    }

    public void setLanguajeId(LongFilter languajeId) {
        this.languajeId = languajeId;
    }

    public LongFilter getUserExpresionId() {
        return userExpresionId;
    }

    public LongFilter userExpresionId() {
        if (userExpresionId == null) {
            userExpresionId = new LongFilter();
        }
        return userExpresionId;
    }

    public void setUserExpresionId(LongFilter userExpresionId) {
        this.userExpresionId = userExpresionId;
    }

    public LongFilter getUserResponseId() {
        return userResponseId;
    }

    public LongFilter userResponseId() {
        if (userResponseId == null) {
            userResponseId = new LongFilter();
        }
        return userResponseId;
    }

    public void setUserResponseId(LongFilter userResponseId) {
        this.userResponseId = userResponseId;
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
        final IntentCriteria that = (IntentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(intenType, that.intenType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(urlRequest, that.urlRequest) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(languajeId, that.languajeId) &&
            Objects.equals(userExpresionId, that.userExpresionId) &&
            Objects.equals(userResponseId, that.userResponseId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            intenType,
            name,
            description,
            urlRequest,
            enabled,
            creationDate,
            languajeId,
            userExpresionId,
            userResponseId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (intenType != null ? "intenType=" + intenType + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (urlRequest != null ? "urlRequest=" + urlRequest + ", " : "") +
            (enabled != null ? "enabled=" + enabled + ", " : "") +
            (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
            (languajeId != null ? "languajeId=" + languajeId + ", " : "") +
            (userExpresionId != null ? "userExpresionId=" + userExpresionId + ", " : "") +
            (userResponseId != null ? "userResponseId=" + userResponseId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
