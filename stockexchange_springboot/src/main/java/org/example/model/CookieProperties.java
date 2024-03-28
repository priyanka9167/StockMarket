package org.example.model;

import lombok.Data;

@Data
public class CookieProperties {
    String domain;
    String path;
    boolean httpOnly;
    boolean secure;
    int maxAgeInMinutes;
}
