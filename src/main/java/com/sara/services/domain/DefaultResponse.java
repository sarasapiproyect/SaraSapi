package com.sara.services.domain;

import com.sara.services.domain.enumeration.Priority;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DefaultResponse.
 */
@Entity
@Table(name = "default_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DefaultResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "default_value_response", length = 500, nullable = false)
    private String defaultValueResponse;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DefaultResponse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultValueResponse() {
        return this.defaultValueResponse;
    }

    public DefaultResponse defaultValueResponse(String defaultValueResponse) {
        this.setDefaultValueResponse(defaultValueResponse);
        return this;
    }

    public void setDefaultValueResponse(String defaultValueResponse) {
        this.defaultValueResponse = defaultValueResponse;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public DefaultResponse priority(Priority priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public byte[] getMultimedia() {
        return this.multimedia;
    }

    public DefaultResponse multimedia(byte[] multimedia) {
        this.setMultimedia(multimedia);
        return this;
    }

    public void setMultimedia(byte[] multimedia) {
        this.multimedia = multimedia;
    }

    public String getMultimediaContentType() {
        return this.multimediaContentType;
    }

    public DefaultResponse multimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
        return this;
    }

    public void setMultimediaContentType(String multimediaContentType) {
        this.multimediaContentType = multimediaContentType;
    }

    public byte[] getMultimediaVoice() {
        return this.multimediaVoice;
    }

    public DefaultResponse multimediaVoice(byte[] multimediaVoice) {
        this.setMultimediaVoice(multimediaVoice);
        return this;
    }

    public void setMultimediaVoice(byte[] multimediaVoice) {
        this.multimediaVoice = multimediaVoice;
    }

    public String getMultimediaVoiceContentType() {
        return this.multimediaVoiceContentType;
    }

    public DefaultResponse multimediaVoiceContentType(String multimediaVoiceContentType) {
        this.multimediaVoiceContentType = multimediaVoiceContentType;
        return this;
    }

    public void setMultimediaVoiceContentType(String multimediaVoiceContentType) {
        this.multimediaVoiceContentType = multimediaVoiceContentType;
    }

    public byte[] getSaraAnimation() {
        return this.saraAnimation;
    }

    public DefaultResponse saraAnimation(byte[] saraAnimation) {
        this.setSaraAnimation(saraAnimation);
        return this;
    }

    public void setSaraAnimation(byte[] saraAnimation) {
        this.saraAnimation = saraAnimation;
    }

    public String getSaraAnimationContentType() {
        return this.saraAnimationContentType;
    }

    public DefaultResponse saraAnimationContentType(String saraAnimationContentType) {
        this.saraAnimationContentType = saraAnimationContentType;
        return this;
    }

    public void setSaraAnimationContentType(String saraAnimationContentType) {
        this.saraAnimationContentType = saraAnimationContentType;
    }

    public Boolean getIsEndConversation() {
        return this.isEndConversation;
    }

    public DefaultResponse isEndConversation(Boolean isEndConversation) {
        this.setIsEndConversation(isEndConversation);
        return this;
    }

    public void setIsEndConversation(Boolean isEndConversation) {
        this.isEndConversation = isEndConversation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultResponse)) {
            return false;
        }
        return id != null && id.equals(((DefaultResponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultResponse{" +
            "id=" + getId() +
            ", defaultValueResponse='" + getDefaultValueResponse() + "'" +
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
    
    public static DefaultResponse getRandomElement(List<DefaultResponse> defaultResponses)
    {
        Random rand = new Random();
        return defaultResponses.get(rand.nextInt(defaultResponses.size()));
    }
}
