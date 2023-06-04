package com.sara.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sara.services.domain.enumeration.IntentType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Intent.
 */
@Entity
@Table(name = "intent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "inten_type")
    private IntentType intenType;

    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "name", length = 2000, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "url_request", length = 300, nullable = false)
    private String urlRequest;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    private Language languaje;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name = "rel_intent__user_expresion",
        joinColumns = @JoinColumn(name = "intent_id"),
        inverseJoinColumns = @JoinColumn(name = "user_expresion_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intents" }, allowSetters = true)
    private Set<UserExpresion> userExpresions = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name = "rel_intent__user_response",
        joinColumns = @JoinColumn(name = "intent_id"),
        inverseJoinColumns = @JoinColumn(name = "user_response_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intents" }, allowSetters = true)
    private Set<UserResponse> userResponses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Intent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntentType getIntenType() {
        return this.intenType;
    }

    public Intent intenType(IntentType intenType) {
        this.setIntenType(intenType);
        return this;
    }

    public void setIntenType(IntentType intenType) {
        this.intenType = intenType;
    }

    public String getName() {
        return this.name;
    }

    public Intent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Intent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlRequest() {
        return this.urlRequest;
    }

    public Intent urlRequest(String urlRequest) {
        this.setUrlRequest(urlRequest);
        return this;
    }

    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public Intent enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Intent creationDate(Instant creationDate) {
        this.setCreationDate(creationDate);
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Language getLanguaje() {
        return this.languaje;
    }

    public void setLanguaje(Language language) {
        this.languaje = language;
    }

    public Intent languaje(Language language) {
        this.setLanguaje(language);
        return this;
    }

    public Set<UserExpresion> getUserExpresions() {
        return this.userExpresions;
    }

    public void setUserExpresions(Set<UserExpresion> userExpresions) {
        this.userExpresions = userExpresions;
    }

    public Intent userExpresions(Set<UserExpresion> userExpresions) {
        this.setUserExpresions(userExpresions);
        return this;
    }

    public Intent addUserExpresion(UserExpresion userExpresion) {
        this.userExpresions.add(userExpresion);
        userExpresion.getIntents().add(this);
        return this;
    }

    public Intent removeUserExpresion(UserExpresion userExpresion) {
        this.userExpresions.remove(userExpresion);
        userExpresion.getIntents().remove(this);
        return this;
    }

    public Set<UserResponse> getUserResponses() {
        return this.userResponses;
    }

    public void setUserResponses(Set<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public Intent userResponses(Set<UserResponse> userResponses) {
        this.setUserResponses(userResponses);
        return this;
    }

    public Intent addUserResponse(UserResponse userResponse) {
        this.userResponses.add(userResponse);
        userResponse.getIntents().add(this);
        return this;
    }

    public Intent removeUserResponse(UserResponse userResponse) {
        this.userResponses.remove(userResponse);
        userResponse.getIntents().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intent)) {
            return false;
        }
        return id != null && id.equals(((Intent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Intent{" +
            "id=" + getId() +
            ", intenType='" + getIntenType() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", urlRequest='" + getUrlRequest() + "'" +
            ", enabled='" + getEnabled() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
