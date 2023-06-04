package com.sara.services.domain.enumeration;

/**
 * The MultimediaType enumeration.
 */
public enum MultimediaType {
    AUDIO("AUDIO"),
    VISUAL("VISUAL");
    
    private final String value;

    MultimediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
