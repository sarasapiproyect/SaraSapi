package com.sara.services.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "iso_value", length = 50, nullable = false)
    private String isoValue;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "iconsrc", length = 200, nullable = false)
    private String iconsrc;

    @Lob
    @Column(name = "img_blog_icon")
    private byte[] imgBlogIcon;

    @Column(name = "img_blog_icon_content_type")
    private String imgBlogIconContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Language id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Language name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoValue() {
        return this.isoValue;
    }

    public Language isoValue(String isoValue) {
        this.setIsoValue(isoValue);
        return this;
    }

    public void setIsoValue(String isoValue) {
        this.isoValue = isoValue;
    }

    public String getDescription() {
        return this.description;
    }

    public Language description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconsrc() {
        return this.iconsrc;
    }

    public Language iconsrc(String iconsrc) {
        this.setIconsrc(iconsrc);
        return this;
    }

    public void setIconsrc(String iconsrc) {
        this.iconsrc = iconsrc;
    }

    public byte[] getImgBlogIcon() {
        return this.imgBlogIcon;
    }

    public Language imgBlogIcon(byte[] imgBlogIcon) {
        this.setImgBlogIcon(imgBlogIcon);
        return this;
    }

    public void setImgBlogIcon(byte[] imgBlogIcon) {
        this.imgBlogIcon = imgBlogIcon;
    }

    public String getImgBlogIconContentType() {
        return this.imgBlogIconContentType;
    }

    public Language imgBlogIconContentType(String imgBlogIconContentType) {
        this.imgBlogIconContentType = imgBlogIconContentType;
        return this;
    }

    public void setImgBlogIconContentType(String imgBlogIconContentType) {
        this.imgBlogIconContentType = imgBlogIconContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return id != null && id.equals(((Language) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isoValue='" + getIsoValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", iconsrc='" + getIconsrc() + "'" +
            ", imgBlogIcon='" + getImgBlogIcon() + "'" +
            ", imgBlogIconContentType='" + getImgBlogIconContentType() + "'" +
            "}";
    }
}
