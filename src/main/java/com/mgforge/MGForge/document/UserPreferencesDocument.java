package com.mgforge.MGForge.document;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "userPreferences")
public class UserPreferencesDocument extends BaseMongoAuditDocument{

    @Id
    private String id;

    private String tenantId;
    private String userId;
    private String timezone;
    private String units;
    private Map<String, Object> notifications;
    private Map<String, Object> ui;

    public UserPreferencesDocument() {
    }

    public UserPreferencesDocument(String id, String tenantId, String userId, String timezone, String units, Map<String, Object> notifications, Map<String, Object> ui) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.timezone = timezone;
        this.units = units;
        this.notifications = notifications;
        this.ui = ui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Map<String, Object> getNotifications() {
        return notifications;
    }

    public void setNotifications(Map<String, Object> notifications) {
        this.notifications = notifications;
    }

    public Map<String, Object> getUi() {
        return ui;
    }

    public void setUi(Map<String, Object> ui) {
        this.ui = ui;
    }
}
