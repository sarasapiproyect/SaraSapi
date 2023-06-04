package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.Interations} entity. This class is used
 * in {@link com.sara.services.web.rest.InterationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterationsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SourceChannel
     */
    public static class SourceChannelFilter extends Filter<SourceChannel> {

        public SourceChannelFilter() {}

        public SourceChannelFilter(SourceChannelFilter filter) {
            super(filter);
        }

        @Override
        public SourceChannelFilter copy() {
            return new SourceChannelFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter valueRequest;

    private StringFilter sourceInfo;

    private StringFilter valueResponse;

    private SourceChannelFilter sourceChannel;

    private Boolean distinct;

    public InterationsCriteria() {}

    public InterationsCriteria(InterationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.valueRequest = other.valueRequest == null ? null : other.valueRequest.copy();
        this.sourceInfo = other.sourceInfo == null ? null : other.sourceInfo.copy();
        this.valueResponse = other.valueResponse == null ? null : other.valueResponse.copy();
        this.sourceChannel = other.sourceChannel == null ? null : other.sourceChannel.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterationsCriteria copy() {
        return new InterationsCriteria(this);
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

    public StringFilter getValueRequest() {
        return valueRequest;
    }

    public StringFilter valueRequest() {
        if (valueRequest == null) {
            valueRequest = new StringFilter();
        }
        return valueRequest;
    }

    public void setValueRequest(StringFilter valueRequest) {
        this.valueRequest = valueRequest;
    }

    public StringFilter getSourceInfo() {
        return sourceInfo;
    }

    public StringFilter sourceInfo() {
        if (sourceInfo == null) {
            sourceInfo = new StringFilter();
        }
        return sourceInfo;
    }

    public void setSourceInfo(StringFilter sourceInfo) {
        this.sourceInfo = sourceInfo;
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

    public SourceChannelFilter getSourceChannel() {
        return sourceChannel;
    }

    public SourceChannelFilter sourceChannel() {
        if (sourceChannel == null) {
            sourceChannel = new SourceChannelFilter();
        }
        return sourceChannel;
    }

    public void setSourceChannel(SourceChannelFilter sourceChannel) {
        this.sourceChannel = sourceChannel;
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
        final InterationsCriteria that = (InterationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(valueRequest, that.valueRequest) &&
            Objects.equals(sourceInfo, that.sourceInfo) &&
            Objects.equals(valueResponse, that.valueResponse) &&
            Objects.equals(sourceChannel, that.sourceChannel) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valueRequest, sourceInfo, valueResponse, sourceChannel, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (valueRequest != null ? "valueRequest=" + valueRequest + ", " : "") +
            (sourceInfo != null ? "sourceInfo=" + sourceInfo + ", " : "") +
            (valueResponse != null ? "valueResponse=" + valueResponse + ", " : "") +
            (sourceChannel != null ? "sourceChannel=" + sourceChannel + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
