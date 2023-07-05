package com.sara.services.service.criteria;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sara.services.domain.Contacts} entity. This class is used
 * in {@link com.sara.services.web.rest.ContactsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactsCriteria implements Serializable, Criteria {

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

    private StringFilter phoneNumber;

    private InstantFilter lastDayConnection;

    private SourceChannelFilter sourceChannel;

    private Boolean distinct;

    public ContactsCriteria() {}

    public ContactsCriteria(ContactsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.lastDayConnection = other.lastDayConnection == null ? null : other.lastDayConnection.copy();
        this.sourceChannel = other.sourceChannel == null ? null : other.sourceChannel.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ContactsCriteria copy() {
        return new ContactsCriteria(this);
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

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public InstantFilter getLastDayConnection() {
        return lastDayConnection;
    }

    public InstantFilter lastDayConnection() {
        if (lastDayConnection == null) {
            lastDayConnection = new InstantFilter();
        }
        return lastDayConnection;
    }

    public void setLastDayConnection(InstantFilter lastDayConnection) {
        this.lastDayConnection = lastDayConnection;
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
        final ContactsCriteria that = (ContactsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(lastDayConnection, that.lastDayConnection) &&
            Objects.equals(sourceChannel, that.sourceChannel) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, lastDayConnection, sourceChannel, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (lastDayConnection != null ? "lastDayConnection=" + lastDayConnection + ", " : "") +
            (sourceChannel != null ? "sourceChannel=" + sourceChannel + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
