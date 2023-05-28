package com.sara.services.domain.enumeration;

/**
 * The SourceChannel enumeration.
 */
public enum SourceChannel {
	WHATSAPP("WHATSAPP"),
	TELEGRAM("TELEGRAM"),
	WEB("WEB");

	private final String value;

	SourceChannel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
