package org.example.model;

import lombok.Data;

@Data
public class FirebaseProperties {
    int sessionExpiryInDays;
    String databaseUrl;
    boolean enableStrictServerSession;
    boolean enableCheckSession;
    boolean enableLogoutEverywhere;
    boolean isEnableCheckSessionRevoked;

    public int getSessionExpiryInDays() {
        return sessionExpiryInDays;
    }

    public void setSessionExpiryInDays(int sessionExpiryInDays) {
        this.sessionExpiryInDays = sessionExpiryInDays;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public boolean isEnableStrictServerSession() {
        return enableStrictServerSession;
    }

    public void setEnableStrictServerSession(boolean enableStrictServerSession) {
        this.enableStrictServerSession = enableStrictServerSession;
    }

    public boolean isEnableCheckSession() {
        return enableCheckSession;
    }

    public void setEnableCheckSession(boolean enableCheckSession) {
        this.enableCheckSession = enableCheckSession;
    }

    public boolean isEnableLogoutEverywhere() {
        return enableLogoutEverywhere;
    }

    public void setEnableLogoutEverywhere(boolean enableLogoutEverywhere) {
        this.enableLogoutEverywhere = enableLogoutEverywhere;
    }

    public boolean isEnableCheckSessionRevoked() {
        return isEnableCheckSessionRevoked;
    }

    public void setEnableCheckSessionRevoked(boolean enableCheckSessionRevoked) {
        isEnableCheckSessionRevoked = enableCheckSessionRevoked;
    }
}
