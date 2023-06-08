package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.MultimediaType;
import com.sara.services.domain.enumeration.Priority;
import com.sara.services.domain.enumeration.ResponseType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.UserResponse} entity. This class is used
 * in {@link com.sara.services.web.rest.UserResponseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-responses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserResponseCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering ResponseType
     */
    public static class ResponseTypeFilter extends Filter<ResponseType> {

        public ResponseTypeFilter() {}

        public ResponseTypeFilter(ResponseTypeFilter filter) {
            super(filter);
        }

        @Override
        public ResponseTypeFilter copy() {
            return new ResponseTypeFilter(this);
        }
    }

    /**
     * Class for filtering MultimediaType
     */
    public static class MultimediaTypeFilter extends Filter<MultimediaType> {

        public MultimediaTypeFilter() {}

        public MultimediaTypeFilter(MultimediaTypeFilter filter) {
            super(filter);
        }

        @Override
        public MultimediaTypeFilter copy() {
            return new MultimediaTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter valueResponse;

    private PriorityFilter priority;

    private BooleanFilter isEndConversation;

    private ResponseTypeFilter responseType;

    private StringFilter url;

    private StringFilter multimediaUrl;

    private StringFilter multimediaVoiceUrl;

    private StringFilter saraAnimationUrl;

    private MultimediaTypeFilter multimediaType;

    private BooleanFilter showMultimedia;

    private LongFilter intentId;

    private Boolean distinct;

    public UserResponseCriteria() {}

    public UserResponseCriteria(UserResponseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.valueResponse = other.valueResponse == null ? null : other.valueResponse.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.isEndConversation = other.isEndConversation == null ? null : other.isEndConversation.copy();
        this.responseType = other.responseType == null ? null : other.responseType.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.multimediaUrl = other.multimediaUrl == null ? null : other.multimediaUrl.copy();
        this.multimediaVoiceUrl = other.multimediaVoiceUrl == null ? null : other.multimediaVoiceUrl.copy();
        this.saraAnimationUrl = other.saraAnimationUrl == null ? null : other.saraAnimationUrl.copy();
        this.multimediaType = other.multimediaType == null ? null : other.multimediaType.copy();
        this.showMultimedia = other.showMultimedia == null ? null : other.showMultimedia.copy();
        this.intentId = other.intentId == null ? null : other.intentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserResponseCriteria copy() {
        return new UserResponseCriteria(this);
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

    public StringFilter getValueResponse() {
        return valueResponse;
    }

    public StringFilter valueResponse() {
        if (valueResponse == null) {
            valueResponse = new StringFilter();
        }
        return valueResponse;
    }

    public void setValueResponse(StringFilter valueResponse) {
        this.valueResponse = valueResponse;
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

    public BooleanFilter getIsEndConversation() {
        return isEndConversation;
    }

    public BooleanFilter isEndConversation() {
        if (isEndConversation == null) {
            isEndConversation = new BooleanFilter();
        }
        return isEndConversation;
    }

    public void setIsEndConversation(BooleanFilter isEndConversation) {
        this.isEndConversation = isEndConversation;
    }

    public ResponseTypeFilter getResponseType() {
        return responseType;
    }

    public ResponseTypeFilter responseType() {
        if (responseType == null) {
            responseType = new ResponseTypeFilter();
        }
        return responseType;
    }

    public void setResponseType(ResponseTypeFilter responseType) {
        this.responseType = responseType;
    }

    public StringFilter getUrl() {
        return url;
    }

    public StringFilter url() {
        if (url == null) {
            url = new StringFilter();
        }
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getMultimediaUrl() {
        return multimediaUrl;
    }

    public StringFilter multimediaUrl() {
        if (multimediaUrl == null) {
            multimediaUrl = new StringFilter();
        }
        return multimediaUrl;
    }

    public void setMultimediaUrl(StringFilter multimediaUrl) {
        this.multimediaUrl = multimediaUrl;
    }

    public StringFilter getMultimediaVoiceUrl() {
        return multimediaVoiceUrl;
    }

    public StringFilter multimediaVoiceUrl() {
        if (multimediaVoiceUrl == null) {
            multimediaVoiceUrl = new StringFilter();
        }
        return multimediaVoiceUrl;
    }

    public void setMultimediaVoiceUrl(StringFilter multimediaVoiceUrl) {
        this.multimediaVoiceUrl = multimediaVoiceUrl;
    }

    public StringFilter getSaraAnimationUrl() {
        return saraAnimationUrl;
    }

    public StringFilter saraAnimationUrl() {
        if (saraAnimationUrl == null) {
            saraAnimationUrl = new StringFilter();
        }
        return saraAnimationUrl;
    }

    public void setSaraAnimationUrl(StringFilter saraAnimationUrl) {
        this.saraAnimationUrl = saraAnimationUrl;
    }

    public MultimediaTypeFilter getMultimediaType() {
        return multimediaType;
    }

    public MultimediaTypeFilter multimediaType() {
        if (multimediaType == null) {
            multimediaType = new MultimediaTypeFilter();
        }
        return multimediaType;
    }

    public void setMultimediaType(MultimediaTypeFilter multimediaType) {
        this.multimediaType = multimediaType;
    }

    public BooleanFilter getShowMultimedia() {
        return showMultimedia;
    }

    public BooleanFilter showMultimedia() {
        if (showMultimedia == null) {
            showMultimedia = new BooleanFilter();
        }
        return showMultimedia;
    }

    public void setShowMultimedia(BooleanFilter showMultimedia) {
        this.showMultimedia = showMultimedia;
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
        final UserResponseCriteria that = (UserResponseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(valueResponse, that.valueResponse) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(isEndConversation, that.isEndConversation) &&
            Objects.equals(responseType, that.responseType) &&
            Objects.equals(url, that.url) &&
            Objects.equals(multimediaUrl, that.multimediaUrl) &&
            Objects.equals(multimediaVoiceUrl, that.multimediaVoiceUrl) &&
            Objects.equals(saraAnimationUrl, that.saraAnimationUrl) &&
            Objects.equals(multimediaType, that.multimediaType) &&
            Objects.equals(showMultimedia, that.showMultimedia) &&
            Objects.equals(intentId, that.intentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            valueResponse,
            priority,
            isEndConversation,
            responseType,
            url,
            multimediaUrl,
            multimediaVoiceUrl,
            saraAnimationUrl,
            multimediaType,
            showMultimedia,
            intentId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserResponseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (valueResponse != null ? "valueResponse=" + valueResponse + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (isEndConversation != null ? "isEndConversation=" + isEndConversation + ", " : "") +
            (responseType != null ? "responseType=" + responseType + ", " : "") +
            (url != null ? "url=" + url + ", " : "") +
            (multimediaUrl != null ? "multimediaUrl=" + multimediaUrl + ", " : "") +
            (multimediaVoiceUrl != null ? "multimediaVoiceUrl=" + multimediaVoiceUrl + ", " : "") +
            (saraAnimationUrl != null ? "saraAnimationUrl=" + saraAnimationUrl + ", " : "") +
            (multimediaType != null ? "multimediaType=" + multimediaType + ", " : "") +
            (showMultimedia != null ? "showMultimedia=" + showMultimedia + ", " : "") +
            (intentId != null ? "intentId=" + intentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
