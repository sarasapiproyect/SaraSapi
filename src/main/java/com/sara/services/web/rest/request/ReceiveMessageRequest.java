package com.sara.services.web.rest.request;

public class ReceiveMessageRequest {

	private String valueRequest;

	private String sourceChannel;
	
	private String sourceInfo;

	public String getValueRequest() {
		return valueRequest;
	}

	public void setValueRequest(String valueRequest) {
		this.valueRequest = valueRequest;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getSourceInfo() {
		return sourceInfo;
	}

	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	
}
