package com.sara.services.web.rest.response;

import com.sara.services.domain.enumeration.Priority;

public class ResponseMessage {
	
	private String valueResponse;

	private Priority priority;

	private byte[] multimedia;

	private String multimediaContentType;

	private byte[] multimediaVoice;

	private String multimediaVoiceContentType;

	private byte[] saraAnimation;

	private String saraAnimationContentType;

	private Boolean isEndConversation;
	
	

	public ResponseMessage() {
		super();
	}

	public String getValueResponse() {
		return valueResponse;
	}

	public void setValueResponse(String valueResponse) {
		this.valueResponse = valueResponse;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public byte[] getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(byte[] multimedia) {
		this.multimedia = multimedia;
	}

	public String getMultimediaContentType() {
		return multimediaContentType;
	}

	public void setMultimediaContentType(String multimediaContentType) {
		this.multimediaContentType = multimediaContentType;
	}

	public byte[] getMultimediaVoice() {
		return multimediaVoice;
	}

	public void setMultimediaVoice(byte[] multimediaVoice) {
		this.multimediaVoice = multimediaVoice;
	}

	public String getMultimediaVoiceContentType() {
		return multimediaVoiceContentType;
	}

	public void setMultimediaVoiceContentType(String multimediaVoiceContentType) {
		this.multimediaVoiceContentType = multimediaVoiceContentType;
	}

	public byte[] getSaraAnimation() {
		return saraAnimation;
	}

	public void setSaraAnimation(byte[] saraAnimation) {
		this.saraAnimation = saraAnimation;
	}

	public String getSaraAnimationContentType() {
		return saraAnimationContentType;
	}

	public void setSaraAnimationContentType(String saraAnimationContentType) {
		this.saraAnimationContentType = saraAnimationContentType;
	}

	public Boolean getIsEndConversation() {
		return isEndConversation;
	}

	public void setIsEndConversation(Boolean isEndConversation) {
		this.isEndConversation = isEndConversation;
	}
	
	

}