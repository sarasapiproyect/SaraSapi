package com.sara.services.domain;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Interations.
 */
@Entity
@Table(name = "interations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Interations implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 2000)
    @Column(name = "value_request", length = 2000)
    private String valueRequest;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "source_info", length = 300, nullable = false)
    private String sourceInfo;

    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "value_response", length = 2000, nullable = false)
    private String valueResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_channel")
    private SourceChannel sourceChannel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Interations id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueRequest() {
        return this.valueRequest;
    }

    public Interations valueRequest(String valueRequest) {
        this.setValueRequest(valueRequest);
        return this;
    }

    public void setValueRequest(String valueRequest) {
        this.valueRequest = valueRequest;
    }

    public String getSourceInfo() {
        return this.sourceInfo;
    }

    public Interations sourceInfo(String sourceInfo) {
        this.setSourceInfo(sourceInfo);
        return this;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public String getValueResponse() {
        return this.valueResponse;
    }

    public Interations valueResponse(String valueResponse) {
        this.setValueResponse(valueResponse);
        return this;
    }

    public void setValueResponse(String valueResponse) {
        this.valueResponse = valueResponse;
    }

    public SourceChannel getSourceChannel() {
        return this.sourceChannel;
    }

    public Interations sourceChannel(SourceChannel sourceChannel) {
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
        if (!(o instanceof Interations)) {
            return false;
        }
        return id != null && id.equals(((Interations) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interations{" +
            "id=" + getId() +
            ", valueRequest='" + getValueRequest() + "'" +
            ", sourceInfo='" + getSourceInfo() + "'" +
            ", valueResponse='" + getValueResponse() + "'" +
            ", sourceChannel='" + getSourceChannel() + "'" +
            "}";
    }
}
