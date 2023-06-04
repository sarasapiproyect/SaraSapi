package com.sara.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sara.services.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserExpresion.
 */
@Entity
@Table(name = "user_expresion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserExpresion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "value", length = 2000, nullable = false)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @ManyToMany(mappedBy = "userExpresions",fetch=FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "languaje", "userExpresions", "userResponses" }, allowSetters = true)
    private Set<Intent> intents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserExpresion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public UserExpresion value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public UserExpresion priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<Intent> getIntents() {
        return this.intents;
    }

    public void setIntents(Set<Intent> intents) {
        if (this.intents != null) {
            this.intents.forEach(i -> i.removeUserExpresion(this));
        }
        if (intents != null) {
            intents.forEach(i -> i.addUserExpresion(this));
        }
        this.intents = intents;
    }

    public UserExpresion intents(Set<Intent> intents) {
        this.setIntents(intents);
        return this;
    }

    public UserExpresion addIntent(Intent intent) {
        this.intents.add(intent);
        intent.getUserExpresions().add(this);
        return this;
    }

    public UserExpresion removeIntent(Intent intent) {
        this.intents.remove(intent);
        intent.getUserExpresions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExpresion)) {
            return false;
        }
        return id != null && id.equals(((UserExpresion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExpresion{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", priority='" + getPriority() + "'" +
            "}";
    }
}
