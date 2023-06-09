package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.MultimediaType;
import com.sara.services.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.DefaultResponse} entity. This class is used
 * in {@link com.sara.services.web.rest.DefaultResponseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /default-responses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DefaultResponseCriteria implements Serializable, Criteria {

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

    private StringFilter defaultValueResponse;

    private PriorityFilter priority;

    private BooleanFilter isEndConversation;

    private StringFilter multimediaUrl;

    private StringFilter multimediaVoiceUrl;

    private StringFilter saraAnimationUrl;

    private MultimediaTypeFilter multimediaType;

    private BooleanFilter showMultimedia;

    private LongFilter channelMultimediaId;

    private LongFilter channelVoiceId;

    private LongFilter channelAnimationId;

    private Boolean distinct;

    public DefaultResponseCriteria() {}

    public DefaultResponseCriteria(DefaultResponseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.defaultValueResponse = other.defaultValueResponse == null ? null : other.defaultValueResponse.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.isEndConversation = other.isEndConversation == null ? null : other.isEndConversation.copy();
        this.multimediaUrl = other.multimediaUrl == null ? null : other.multimediaUrl.copy();
        this.multimediaVoiceUrl = other.multimediaVoiceUrl == null ? null : other.multimediaVoiceUrl.copy();
        this.saraAnimationUrl = other.saraAnimationUrl == null ? null : other.saraAnimationUrl.copy();
        this.multimediaType = other.multimediaType == null ? null : other.multimediaType.copy();
        this.showMultimedia = other.showMultimedia == null ? null : other.showMultimedia.copy();
        this.channelMultimediaId = other.channelMultimediaId == null ? null : other.channelMultimediaId.copy();
        this.channelVoiceId = other.channelVoiceId == null ? null : other.channelVoiceId.copy();
        this.channelAnimationId = other.channelAnimationId == null ? null : other.channelAnimationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DefaultResponseCriteria copy() {
        return new DefaultResponseCriteria(this);
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

    public StringFilter getDefaultValueResponse() {
        return defaultValueResponse;
    }

    public StringFilter defaultValueResponse() {
        if (defaultValueResponse == null) {
            defaultValueResponse = new StringFilter();
        }
        return defaultValueResponse;
    }

    public void setDefaultValueResponse(StringFilter defaultValueResponse) {
        this.defaultValueResponse = defaultValueResponse;
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

    public LongFilter getChannelMultimediaId() {
        return channelMultimediaId;
    }

    public LongFilter channelMultimediaId() {
        if (channelMultimediaId == null) {
            channelMultimediaId = new LongFilter();
        }
        return channelMultimediaId;
    }

    public void setChannelMultimediaId(LongFilter channelMultimediaId) {
        this.channelMultimediaId = channelMultimediaId;
    }

    public LongFilter getChannelVoiceId() {
        return channelVoiceId;
    }

    public LongFilter channelVoiceId() {
        if (channelVoiceId == null) {
            channelVoiceId = new LongFilter();
        }
        return channelVoiceId;
    }

    public void setChannelVoiceId(LongFilter channelVoiceId) {
        this.channelVoiceId = channelVoiceId;
    }

    public LongFilter getChannelAnimationId() {
        return channelAnimationId;
    }

    public LongFilter channelAnimationId() {
        if (channelAnimationId == null) {
            channelAnimationId = new LongFilter();
        }
        return channelAnimationId;
    }

    public void setChannelAnimationId(LongFilter channelAnimationId) {
        this.channelAnimationId = channelAnimationId;
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
        final DefaultResponseCriteria that = (DefaultResponseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(defaultValueResponse, that.defaultValueResponse) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(isEndConversation, that.isEndConversation) &&
            Objects.equals(multimediaUrl, that.multimediaUrl) &&
            Objects.equals(multimediaVoiceUrl, that.multimediaVoiceUrl) &&
            Objects.equals(saraAnimationUrl, that.saraAnimationUrl) &&
            Objects.equals(multimediaType, that.multimediaType) &&
            Objects.equals(showMultimedia, that.showMultimedia) &&
            Objects.equals(channelMultimediaId, that.channelMultimediaId) &&
            Objects.equals(channelVoiceId, that.channelVoiceId) &&
            Objects.equals(channelAnimationId, that.channelAnimationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            defaultValueResponse,
            priority,
            isEndConversation,
            multimediaUrl,
            multimediaVoiceUrl,
            saraAnimationUrl,
            multimediaType,
            showMultimedia,
            channelMultimediaId,
            channelVoiceId,
            channelAnimationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultResponseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (defaultValueResponse != null ? "defaultValueResponse=" + defaultValueResponse + ", " : "") +
            (priority != null ? "priority=" + priority + ", " : "") +
            (isEndConversation != null ? "isEndConversation=" + isEndConversation + ", " : "") +
            (multimediaUrl != null ? "multimediaUrl=" + multimediaUrl + ", " : "") +
            (multimediaVoiceUrl != null ? "multimediaVoiceUrl=" + multimediaVoiceUrl + ", " : "") +
            (saraAnimationUrl != null ? "saraAnimationUrl=" + saraAnimationUrl + ", " : "") +
            (multimediaType != null ? "multimediaType=" + multimediaType + ", " : "") +
            (showMultimedia != null ? "showMultimedia=" + showMultimedia + ", " : "") +
            (channelMultimediaId != null ? "channelMultimediaId=" + channelMultimediaId + ", " : "") +
            (channelVoiceId != null ? "channelVoiceId=" + channelVoiceId + ", " : "") +
            (channelAnimationId != null ? "channelAnimationId=" + channelAnimationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
