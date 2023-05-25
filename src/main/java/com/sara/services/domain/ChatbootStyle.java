package com.sara.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChatbootStyle.
 */
@Entity
@Table(name = "chatboot_style")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChatbootStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "name_properties", length = 300, nullable = false)
    private String nameProperties;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "value", length = 300, nullable = false)
    private String value;

    @Lob
    @Column(name = "multimedia")
    private byte[] multimedia;

    @Column(name = "multimedia_content_type")
    private String multimediaContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChatbootStyle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProperties() {
        return this.nameProperties;
    }

    public ChatbootStyle nameProperties(String nameProperties) {
        this.setNameProperties(nameProperties);
        return this;
    }

    public void setNameProperties(String nameProperties) {
        this.nameProperties = nameProperties;
    }

    public String getValue() {
        return this.value;
    }

    public ChatbootStyle value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getMultimedia() {
        return this.multimedia;
    }

    public ChatbootStyle multimedia(byte[] multimedia) {
        this.setMultimedia(multimedia);
        return this;
    }

    public void setMultimedia(byte[] multimedia) {
        this.multimedia = multimedia;
    }

    public String getMultimediaContentType() {
        return this.multimediaContentType;
    }

    public ChatbootStyle multimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
        return this;
    }

    public void setMultimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatbootStyle)) {
            return false;
        }
        return id != null && id.equals(((ChatbootStyle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatbootStyle{" +
            "id=" + getId() +
            ", nameProperties='" + getNameProperties() + "'" +
            ", value='" + getValue() + "'" +
            ", multimedia='" + getMultimedia() + "'" +
            ", multimediaContentType='" + getMultimediaContentType() + "'" +
            "}";
    }
}
