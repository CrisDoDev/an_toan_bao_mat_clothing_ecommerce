package model;

import java.sql.Timestamp;

public class UserKey {
    private int keyId;
    private int userId;
    private String publicKeyText;
    private Timestamp createdAt;
    private Timestamp revokedAt;
    private String status;

    public UserKey() {
    }

    public UserKey(int keyId, int userId, String publicKeyText, Timestamp createdAt, Timestamp revokedAt, String status) {
        this.keyId = keyId;
        this.userId = userId;
        this.publicKeyText = publicKeyText;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.status = status;
    }

    public int getKeyId() { return keyId; }
    public void setKeyId(int keyId) { this.keyId = keyId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPublicKeyText() { return publicKeyText; }
    public void setPublicKeyText(String publicKeyText) { this.publicKeyText = publicKeyText; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getRevokedAt() { return revokedAt; }
    public void setRevokedAt(Timestamp revokedAt) { this.revokedAt = revokedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}