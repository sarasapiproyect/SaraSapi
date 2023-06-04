package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.Training} entity. This class is used
 * in {@link com.sara.services.web.rest.TrainingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trainings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainingCriteria implements Serializable, Criteria {

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

    private StringFilter value;

    private SourceChannelFilter sourceChannel;

    private InstantFilter creationDate;

    private StringFilter ip;

    private FloatFilter postionX;

    private FloatFilter postionY;

    private StringFilter sourceInfo;

    private Boolean distinct;

    public TrainingCriteria() {}

    public TrainingCriteria(TrainingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.sourceChannel = other.sourceChannel == null ? null : other.sourceChannel.copy();
        this.creationDate = other.creationDate == null ? null : other.creationDate.copy();
        this.ip = other.ip == null ? null : other.ip.copy();
        this.postionX = other.postionX == null ? null : other.postionX.copy();
        this.postionY = other.postionY == null ? null : other.postionY.copy();
        this.sourceInfo = other.sourceInfo == null ? null : other.sourceInfo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TrainingCriteria copy() {
        return new TrainingCriteria(this);
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

    public StringFilter getIp() {
        return ip;
    }

    public StringFilter ip() {
        if (ip == null) {
            ip = new StringFilter();
        }
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public FloatFilter getPostionX() {
        return postionX;
    }

    public FloatFilter postionX() {
        if (postionX == null) {
            postionX = new FloatFilter();
        }
        return postionX;
    }

    public void setPostionX(FloatFilter postionX) {
        this.postionX = postionX;
    }

    public FloatFilter getPostionY() {
        return postionY;
    }

    public FloatFilter postionY() {
        if (postionY == null) {
            postionY = new FloatFilter();
        }
        return postionY;
    }

    public void setPostionY(FloatFilter postionY) {
        this.postionY = postionY;
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
        final TrainingCriteria that = (TrainingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(sourceChannel, that.sourceChannel) &&
            Objects.equals(creationDate, that.creationDate) &&
            Objects.equals(ip, that.ip) &&
            Objects.equals(postionX, that.postionX) &&
            Objects.equals(postionY, that.postionY) &&
            Objects.equals(sourceInfo, that.sourceInfo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, sourceChannel, creationDate, ip, postionX, postionY, sourceInfo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (sourceChannel != null ? "sourceChannel=" + sourceChannel + ", " : "") +
            (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
            (ip != null ? "ip=" + ip + ", " : "") +
            (postionX != null ? "postionX=" + postionX + ", " : "") +
            (postionY != null ? "postionY=" + postionY + ", " : "") +
            (sourceInfo != null ? "sourceInfo=" + sourceInfo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
