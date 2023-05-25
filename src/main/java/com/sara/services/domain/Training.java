package com.sara.services.domain;

import com.sara.services.domain.enumeration.SourceChannel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "value", length = 300, nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_channel")
    private SourceChannel sourceChannel;

    @Column(name = "creation_date")
    private Instant creationDate;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "ip", length = 300, nullable = false)
    private String ip;

    @Column(name = "postion_x")
    private Float postionX;

    @Column(name = "postion_y")
    private Float postionY;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "source_info", length = 300, nullable = false)
    private String sourceInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Training id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public Training value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SourceChannel getSourceChannel() {
        return this.sourceChannel;
    }

    public Training sourceChannel(SourceChannel sourceChannel) {
        this.setSourceChannel(sourceChannel);
        return this;
    }

    public void setSourceChannel(SourceChannel sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Training creationDate(Instant creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getIp() {
        return this.ip;
    }

    public Training ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Float getPostionX() {
        return this.postionX;
    }

    public Training postionX(Float postionX) {
        this.setPostionX(postionX);
        return this;
    }

    public void setPostionX(Float postionX) {
        this.postionX = postionX;
    }

    public Float getPostionY() {
        return this.postionY;
    }

    public Training postionY(Float postionY) {
        this.setPostionY(postionY);
        return this;
    }

    public void setPostionY(Float postionY) {
        this.postionY = postionY;
    }

    public String getSourceInfo() {
        return this.sourceInfo;
    }

    public Training sourceInfo(String sourceInfo) {
        this.setSourceInfo(sourceInfo);
        return this;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Training)) {
            return false;
        }
        return id != null && id.equals(((Training) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", sourceChannel='" + getSourceChannel() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", ip='" + getIp() + "'" +
            ", postionX=" + getPostionX() +
            ", postionY=" + getPostionY() +
            ", sourceInfo='" + getSourceInfo() + "'" +
            "}";
    }
}
