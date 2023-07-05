package com.sara.services.domain;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contacts.
 */
@Entity
@Table(name = "contacts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "last_day_connection")
    private Instant lastDayConnection;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_channel")
    private SourceChannel sourceChannel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contacts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Contacts phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getLastDayConnection() {
        return this.lastDayConnection;
    }

    public Contacts lastDayConnection(Instant lastDayConnection) {
        this.setLastDayConnection(lastDayConnection);
        return this;
    }

    public void setLastDayConnection(Instant lastDayConnection) {
        this.lastDayConnection = lastDayConnection;
    }

    public SourceChannel getSourceChannel() {
        return this.sourceChannel;
    }

    public Contacts sourceChannel(SourceChannel sourceChannel) {
        this.setSourceChannel(sourceChannel);
        return this;
    }

    public void setSourceChannel(SourceChannel sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contacts)) {
            return false;
        }
        return id != null && id.equals(((Contacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contacts{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", lastDayConnection='" + getLastDayConnection() + "'" +
            ", sourceChannel='" + getSourceChannel() + "'" +
            "}";
    }
}
