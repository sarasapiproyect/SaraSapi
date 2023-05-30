package com.sara.services.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String SPRING_PATH = "/img4/";
    public static final String SPRING_PATH_ALL = SPRING_PATH + "**";
    public static final String BASE_PROFILE_IMAGE_TEXT = "img.png";
    public static final String BASE_IMAGE_IMAGE_GIF = "a.gif";
    public static final String BASE_IMAGE_IMAGE_MP3 = "m.mp3";

    private Constants() {}
}
