package com.sara.services.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sara.services.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserResponse.
 */
@Entity
@Table(name = "user_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "value_response", length = 300, nullable = false)
    private String valueResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Lob
    @Column(name = "multimedia")
    private byte[] multimedia;

    @Column(name = "multimedia_content_type")
    private String multimediaContentType;

    @Lob
    @Column(name = "multimedia_voice")
    private byte[] multimediaVoice;

    @Column(name = "multimedia_voice_content_type")
    private String multimediaVoiceContentType;

    @Lob
    @Column(name = "sara_animation")
    private byte[] saraAnimation;

    @Column(name = "sara_animation_content_type")
    private String saraAnimationContentType;

    @Column(name = "is_end_conversation")
    private Boolean isEndConversation;

    @ManyToMany(mappedBy = "userResponses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "languaje", "userExpresions", "userResponses" }, allowSetters = true)
    private Set<Intent> intents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueResponse() {
        return this.valueResponse;
    }

    public UserResponse valueResponse(String valueResponse) {
        this.setValueResponse(valueResponse);
        return this;
    }

    public void setValueResponse(String valueResponse) {
        this.valueResponse = valueResponse;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public UserResponse priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public byte[] getMultimedia() {
        return this.multimedia;
    }

    public UserResponse multimedia(byte[] multimedia) {
        this.setMultimedia(multimedia);
        return this;
    }

    public void setMultimedia(byte[] multimedia) {
        this.multimedia = multimedia;
    }

    public String getMultimediaContentType() {
        return this.multimediaContentType;
    }

    public UserResponse multimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
        return this;
    }

    public void setMultimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
    }

    public byte[] getMultimediaVoice() {
        return this.multimediaVoice;
    }

    public UserResponse multimediaVoice(byte[] multimediaVoice) {
        this.setMultimediaVoice(multimediaVoice);
        return this;
    }

    public void setMultimediaVoice(byte[] multimediaVoice) {
        this.multimediaVoice = multimediaVoice;
    }

    public String getMultimediaVoiceContentType() {
        return this.multimediaVoiceContentType;
    }

    public UserResponse multimediaVoiceContentType(String multimediaVoiceContentType) {
        this.multimediaVoiceContentType = multimediaVoiceContentType;
        return this;
    }

    public void setMultimediaVoiceContentType(String multimediaVoiceContentType) {
        this.multimediaVoiceContentType = multimediaVoiceContentType;
    }

    public byte[] getSaraAnimation() {
        return this.saraAnimation;
    }

    public UserResponse saraAnimation(byte[] saraAnimation) {
        this.setSaraAnimation(saraAnimation);
        return this;
    }

    public void setSaraAnimation(byte[] saraAnimation) {
        this.saraAnimation = saraAnimation;
    }

    public String getSaraAnimationContentType() {
        return this.saraAnimationContentType;
    }

    public UserResponse saraAnimationContentType(String saraAnimationContentType) {
        this.saraAnimationContentType = saraAnimationContentType;
        return this;
    }

    public void setSaraAnimationContentType(String saraAnimationContentType) {
        this.saraAnimationContentType = saraAnimationContentType;
    }

    public Boolean getIsEndConversation() {
        return this.isEndConversation;
    }

    public UserResponse isEndConversation(Boolean isEndConversation) {
        this.setIsEndConversation(isEndConversation);
        return this;
    }

    public void setIsEndConversation(Boolean isEndConversation) {
        this.isEndConversation = isEndConversation;
    }

    public Set<Intent> getIntents() {
        return this.intents;
    }

    public void setIntents(Set<Intent> intents) {
        if (this.intents != null) {
            this.intents.forEach(i -> i.removeUserResponse(this));
        }
        if (intents != null) {
            intents.forEach(i -> i.addUserResponse(this));
        }
        this.intents = intents;
    }

    public UserResponse intents(Set<Intent> intents) {
        this.setIntents(intents);
        return this;
    }

    public UserResponse addIntent(Intent intent) {
        this.intents.add(intent);
        intent.getUserResponses().add(this);
        return this;
    }

    public UserResponse removeIntent(Intent intent) {
        this.intents.remove(intent);
        intent.getUserResponses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResponse)) {
            return false;
        }
        return id != null && id.equals(((UserResponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserResponse{" +
            "id=" + getId() +
            ", valueResponse='" + getValueResponse() + "'" +
            ", priority='" + getPriority() + "'" +
            ", multimedia='" + getMultimedia() + "'" +
            ", multimediaContentType='" + getMultimediaContentType() + "'" +
            ", multimediaVoice='" + getMultimediaVoice() + "'" +
            ", multimediaVoiceContentType='" + getMultimediaVoiceContentType() + "'" +
            ", saraAnimation='" + getSaraAnimation() + "'" +
            ", saraAnimationContentType='" + getSaraAnimationContentType() + "'" +
            ", isEndConversation='" + getIsEndConversation() + "'" +
            "}";
    }
    
    public static UserResponse getRandomElement(List<UserResponse> userResponses)
    {
        Random rand = new Random();
        return userResponses.get(rand.nextInt(userResponses.size()));
    }
}